package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.repository.SensorFunctionalityRepository;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.PeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.persistence.repositoriesmem.InstantTimeLocationValueRepository;
import smarthome.persistence.repositoriesmem.InstantTimeValueRepository;
import smarthome.persistence.repositoriesmem.PeriodTimeValueRepository;
import smarthome.util.ConfigScraper;
import smarthome.util.PropertyLoader;
import smarthome.util.exceptions.SensorNotFoundException;
import smarthome.util.exceptions.ValueNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ValueService {
    /**
     * Repository for storing Sensor objects.
     */
    private SensorRepository sensorRepository;
    /**
     * Repository for storing SensorFunctionality objects.
     */
    private SensorFunctionalityRepository sensorFunctionalityRepository;

    /**
     * Repository for storing InstantTimeValue objects.
     */
    private InstantTimeValueRepository instantTimeValueRepository;
    /**
     * Repository for storing PeriodTimeValue objects.
     */
    private PeriodTimeValueRepository periodTimeValueRepository;
    /**
     * Repository for storing InstantTimeLocationValue objects.
     */
    private InstantTimeLocationValueRepository instantTimeLocationValueRepository;

    /**
     * A constant string indicating the path to the configuration general file
     */
    private static final String CONFIG_PROPERTIES = "config/general.properties";

    /**
     * ConfigScraper object to read configuration properties.
     */
    private final ConfigScraper configScraper;

    public ValueService(
            SensorRepository sensorRepository,
            SensorFunctionalityRepository sensorFunctionalityRepository,
            InstantTimeValueRepository instantTimeValueRepository,
            PeriodTimeValueRepository periodTimeValueRepository,
            InstantTimeLocationValueRepository instantTimeLocationValueRepository
    ) {
        this.sensorRepository = sensorRepository;
        this.sensorFunctionalityRepository = sensorFunctionalityRepository;
        this.instantTimeValueRepository = instantTimeValueRepository;
        this.periodTimeValueRepository = periodTimeValueRepository;
        this.instantTimeLocationValueRepository = instantTimeLocationValueRepository;
        PropertyLoader propertyLoader = new PropertyLoader();
        this.configScraper = new ConfigScraper(CONFIG_PROPERTIES, propertyLoader);
    }

    /**
     * Obtains a Map<SensorFunctionalityID, List<Reading>> for a given DeviceID between a start and end period.
     * Method obtains all Sensor objects belonging to the given DeviceID object and for each:
     * - Obtains its SensorFunctionalityID;
     * - Obtains the serviceMethodToCallForSensorFunctionalityID String from the SensorFunctionalityRepositoryMem as saves it to serviceMethodToCall String;
     * - At run time, using reflection, dynamically calls the method that corresponds to the serviceMethodToCall String and obtains a List<Value> for that Sensor;
     * - Converts the List<Value> to a List<Reading> and saves it to a Map<SensorFunctionalityID, List<Reading>>.
     *
     * @param deviceID The deviceID value object for which the measurements are to be obtained
     * @param startInterval Timestamp object that represents the start of the "given period"
     * @param endInterval Timestamp object that represents the end of the "given period"
     *
     * @return Map<SensorFunctionalityID, List<Reading>> Where all Reading object are grouped by the SensorFunctionalityID.
     * If a device has a Sensor with no readings in the given period, SensorFunctionalityID of Sensor will be present but the List<Reading> will be empty.
     */
    public Map<SensorFunctionalityID, List<Reading>> getAllMeasurementsForDeviceBetweenPeriod(DeviceID deviceID, Timestamp startInterval, Timestamp endInterval) {
        Iterable<Sensor> sensors = sensorRepository.findByDeviceID(deviceID);
        Map<SensorFunctionalityID, List<Reading>> returnMap = new HashMap<>();

        for (Sensor sensor : sensors) {
            SensorFunctionalityID sensorFunctionalityID = sensor.getSensorFunctionalityID();
            SensorID sensorID = sensor.identity();

            String serviceMethodToCall = sensorFunctionalityRepository.getServiceMethodToCallForSensorFunctionalityID(sensorFunctionalityID);

            List<Value> values = getAllValuesForSensorID(serviceMethodToCall, sensorID, startInterval, endInterval);

            List<Reading> readings = convertValueIterableToReadingIterable(values);
            returnMap.computeIfAbsent(sensorFunctionalityID, k -> new ArrayList<>()).addAll(readings);
        }
        return returnMap;
    }

    /**
     * Call one of the 3 Value Repository classes defined in class attribute.
     * Method uses reflection due to Repository class to be called depending on the Sensor being processed.
     * Extract all Values for a given SensorID between a start and end interval.
     *
     * @param sensorID SensorID value object
     * @param serviceMethodToCall String that represents which method to call on the ValueRepo class for that given Sensor
     * @param startInterval Timestamp object that represents the start of the "given period"
     * @param endInterval Timestamp object that represents the end of the "given period"
     * @return List<Value> list of all Values for the given SensorID in the given period. If exception is encountered null is returned.
     */
    private List<Value> getAllValuesForSensorID (String serviceMethodToCall, SensorID sensorID, Timestamp startInterval, Timestamp endInterval) {
        try {
            Method serviceMethod = this.getClass().getMethod(serviceMethodToCall, SensorID.class, Timestamp.class, Timestamp.class);
            return (List<Value>) serviceMethod.invoke(this, sensorID, startInterval, endInterval);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
             return new ArrayList<>();
        }
    }

    /**
     * Method that is called by getAllValuesForSensorID dynamically depending on the functionality-serviceMethodToCallForUC33 property relation
     * established in config/config.properties.
     * <p>
     * Functionality is derived from the Sensor that is being processed by getAllValuesForSensorID.
     * This method calls the method findBySensorIdBetweenPeriodOfTime on InstantValueRepository class attribute.
     *
     * @param sensorID SensorID value object of the current Sensor
     * @param startInterval Timestamp object that represents the start of the "given period"
     * @param endInterval Timestamp object that represents the end of the "given period"
     * @return List<Value> Returns the list of Values present in InstantValueRepository with the same SensorID and whose reading Timestamp
     * fits within the startInterval and endInterval. If no Values are found, an empty list is returned.
     */
    public List<Value> listInstantValuesForSensorID (SensorID sensorID, Timestamp startInterval, Timestamp endInterval) {
        return instantTimeValueRepository.findBySensorIdBetweenPeriodOfTime(sensorID, startInterval, endInterval);
    }

    /**
     * Method that is called by getAllValuesForSensorID dynamically depending on the functionality<->serviceMethodToCallForUC33 property relation
     * established in the config/config.properties. Functionality is derived from the Sensor that is being processed by getAllValuesForSensorID.
     * This method calls the method findBySensorIdBetweenPeriodOfTime on PeriodValueRepository class attribute.
     *
     * @param sensorID SensorID value object of the current Sensor
     * @param startInterval Timestamp object that represents the start of the "given period"
     * @param endInterval Timestamp object that represents the end of the "given period"
     * @return List<Value> Returns the list of Values present in PeriodValueRepository with the same SensorID and whose reading Timestamps
     * fit within the startInterval and endInterval. If no Values are found, an empty list is returned.
     */
    public List<Value> listPeriodValuesForSensorID (SensorID sensorID, Timestamp startInterval, Timestamp endInterval) {
         return periodTimeValueRepository.findBySensorIdBetweenPeriodOfTime(sensorID, startInterval, endInterval);
    }

    /**
     * Method that is called by getAllValuesForSensorID dynamically depending on the functionality<->serviceMethodToCallForUC33 property relation
     * established in config/config.properties. Functionality is derived from the Sensor that is being processed by getAllValuesForSensorID.
     * This method calls the method findBySensorIdBetweenPeriodOfTime on InstantTimeLocationValueRepository class attribute.
     *
     * @param sensorID SensorID value object of the current Sensor
     * @param startInterval Timestamp object that represents the start of the "given period"
     * @param endInterval Timestamp object that represents the end of the "given period"
     * @return List<Value> Returns the list of Values present in InstantValueRepository with the same SensorID and whose reading Timestamp
     * fits within the startInterval and endInterval. If no Values are found, an empty list is returned.
     */
    public List<Value> listInstantLocationValuesForSensorID(SensorID sensorID, Timestamp startInterval, Timestamp endInterval) {
        return instantTimeLocationValueRepository.findBySensorIdBetweenPeriodOfTime(sensorID, startInterval, endInterval);
    }

    /**
     * Creates a List<Reading> objects corresponding to each entry in a List<Value> objects.
     * Reading is a value object of the Value aggregate type object.
     * @param values List<Value> objects
     * @return List<Reading> objects
     */
    private List<Reading> convertValueIterableToReadingIterable (List<Value> values) {
        List<Reading> readings = new ArrayList<>();
        for (Value value : values) {
            Reading reading = value.getReading();
            readings.add(reading);
        }
        return readings;
    }

    /**
     * Method to get the peak power consumption in a given period of time.
     * Instant power consumption is calculated by summing the consumption values of the grid power meter and all power sources, within the configured tolerance for an "instant".
     * The peak power consumption is the maximum calculated power consumption among all instants within the given period.
     *
     * @param startPeriod Timestamp object that represents the start of the "given period"
     * @param endPeriod Timestamp object that represents the end of the "given period"
     * @return double value representing the peak power consumption in the given period of time.
     */
    public double getPeakPowerConsumption(Timestamp startPeriod, Timestamp endPeriod) {
        // Find sensor of functionality "PowerAverage" (US25) from grid power meter device -> ListOfSensorsFromGridPowerMeter
        String devicePowerGridName = configScraper.loadGridPowerMeterID();
        DeviceID gridPowerMeterDeviceID = new DeviceID(devicePowerGridName);
        SensorFunctionalityID gridPowerMeterSensorFunctionality = new SensorFunctionalityID("PowerAverage");
        Sensor powerGridSensor = sensorRepository.findByDeviceIDAndSensorFunctionality(gridPowerMeterDeviceID, gridPowerMeterSensorFunctionality)
                .iterator().next(); //throws a NoSuchElementException if there is no sensor

        // Get values (reading+timestamp) from that Grid Power Meter sensor -> ListOfPowerGridValues
        List<Value> listOfPowerGridValues = periodTimeValueRepository.findBySensorIdBetweenPeriodOfTime(powerGridSensor.identity(), startPeriod, endPeriod);

        // Find sensors from functionality "SpecificTimePowerConsumption" (US24) -> ListOfSensorsFromPowerSources
        SensorFunctionalityID powerSourcesSensorFunctionality = new SensorFunctionalityID("SpecificTimePowerConsumption");
        Iterable<Sensor> listOfSensorsFromPowerSources = sensorRepository.findBySensorFunctionality(powerSourcesSensorFunctionality);

        // For each sensor in ListOfSensorsFromPowerSources, get the deviceID and the values
        Map<DeviceID,Map<Timestamp, InstantTimeValue>> mapOfPowerSourcesValues = new HashMap<>();
        for (Sensor sensor : listOfSensorsFromPowerSources) {
            DeviceID deviceID = sensor.getDeviceID();
            List<Value> values = instantTimeValueRepository.findBySensorIdBetweenPeriodOfTime(
                    sensor.identity(), startPeriod, endPeriod);
            // if deviceID is not in the map already, add it
            mapOfPowerSourcesValues.computeIfAbsent(deviceID, k -> new HashMap<>());

            // for each value, add it to the map with the respective timestamp
            Map<Timestamp, InstantTimeValue> timestampAndValueMap = mapOfPowerSourcesValues.get(deviceID);
            for (Value value : values) {
                InstantTimeValue instantTimeValue = (InstantTimeValue) value;
                Timestamp timestamp = instantTimeValue.getInstantTimeReading();
                // When the same device has values with the same timestamp, overwrite the value
                timestampAndValueMap.put(timestamp, instantTimeValue);
            }
        }
        return calculatePeakPowerConsumption(listOfPowerGridValues, mapOfPowerSourcesValues);
    }

    /**
     * Method that calculates the peak power consumption, from the list of power grid and power sources consumption values.
     * Instant power consumption is calculated by summing the consumption values of the grid power meter and all power sources, within the configured tolerance for an "instant".
     * The peak power consumption is the maximum calculated power consumption among all instants within the given period.
     *
     * @param listOfPowerGridValues List of Value objects representing the power consumption of the grid power meter
     * @param mapOfPowerSourcesValues Map of DeviceID and Map of Timestamp and InstantTimeValue objects representing the power consumption of all power sources
     * @return double value representing the peak power consumption in the given period of time.
     */
    private double calculatePeakPowerConsumption(List<Value> listOfPowerGridValues, Map<DeviceID,Map<Timestamp, InstantTimeValue>> mapOfPowerSourcesValues) {

        double peakPowerConsumption = 0.0;
        // For each Power Grid value, find all values from Power Sources that are within the same instant
        for (Value powerGridValue : listOfPowerGridValues) {
            PeriodTimeValue periodTimePowerGridValue = (PeriodTimeValue) powerGridValue;
            // consider the end timestamp of Power Grid value
            Timestamp endTime = periodTimePowerGridValue.getEndTimeReading();
            // get grid meter cadence (in minutes) from config file, to be used as the time defined for an "instant"
            long tolerance = Long.parseLong(configScraper.loadGridPowerMeterCadence());
            Timestamp startTime = new Timestamp(endTime.getTime() - tolerance);

            // Calculate total consumption for that instant
            // start with the consumption of the grid power meter
            double totalConsumption = Double.parseDouble(periodTimePowerGridValue.getReading().getMeasurement());
            // add the consumption of all power sources within the same instant
            for (Map<Timestamp, InstantTimeValue> mapOfValuesPerDevice : mapOfPowerSourcesValues.values()) {
                for (Map.Entry<Timestamp, InstantTimeValue> entry : mapOfValuesPerDevice.entrySet()) {
                    Timestamp timestamp = entry.getKey();
                    // check if the timestamp of the power source value is within the same instant as the grid power meter
                    if (!timestamp.before(startTime) && !timestamp.after(endTime)) {
                        InstantTimeValue value = entry.getValue();
                        totalConsumption += Double.parseDouble(value.getReading().getMeasurement());
                    }
                }
            }
            // Update peak power consumption if the calculated consumption for that instant is higher
            peakPowerConsumption = Math.max(peakPowerConsumption, totalConsumption);
        }
        return peakPowerConsumption;
    }

    /**
     * Retrieves the most recent measurement recorded by a sensor identified by its deviceID and sensor functionalityID.
     *
     * @param deviceID              The unique identifier of the device.
     * @param sensorFunctionalityID The unique identifier of the sensorFunctionality.
     * @return a String of the last measurement recorded.
     */
    public String getLastMeasurementOfASensor(DeviceID deviceID, SensorFunctionalityID sensorFunctionalityID) {
        Iterable<Sensor> sensorList = sensorRepository.findByDeviceIDAndSensorFunctionality(deviceID, sensorFunctionalityID);
        if (!sensorList.iterator().hasNext())
            throw new SensorNotFoundException();

        SensorID sensorID = sensorList.iterator().next().identity();
        Value lastValueRecorded;
        if (instantTimeValueRepository.findLastValueRecorded(sensorID) != null)
            lastValueRecorded = instantTimeValueRepository.findLastValueRecorded(sensorID);
        else if (instantTimeLocationValueRepository.findLastValueRecorded(sensorID) != null)
            lastValueRecorded = instantTimeLocationValueRepository.findLastValueRecorded(sensorID);
        else
            throw new ValueNotFoundException();

        Reading lastReading = lastValueRecorded.getReading();
        return lastReading.getMeasurement();
    }

}