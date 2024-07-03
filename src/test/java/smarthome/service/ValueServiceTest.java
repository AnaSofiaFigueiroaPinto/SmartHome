package smarthome.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.repository.SensorFunctionalityRepository;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.PeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.*;
import smarthome.persistence.repositoriesmem.*;
import smarthome.util.ConfigScraper;
import smarthome.util.exceptions.SensorNotFoundException;
import smarthome.util.exceptions.ValueNotFoundException;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest (classes = ValueService.class)
class ValueServiceTest {

    @MockBean
    SensorRepository sensorRepositoryDouble;

    @MockBean
    SensorFunctionalityRepository sensorFunctionalityRepositoryDouble;

    @MockBean
    InstantTimeValueRepository instantValueRepositoryDouble;

    @MockBean
    PeriodTimeValueRepository periodValueRepositoryDouble;

    @MockBean
    InstantTimeLocationValueRepository instantTimeLocationValueRepositoryDouble;

    @InjectMocks
    ValueService valueService;

    @BeforeEach
    void setUp () {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Verify that the constructor of the ListValuesForDeviceService class is working correctly.
     */
    @Test
    void successfullyConstructService () {
        assertNotNull(valueService);
    }

    /**
     * Verify that getAllMeasurementsForDeviceBetweenPeriod method correctly handles all data and calls other methods dynamically.
     *
     * The provided DeviceID has 6 sensors, each of a different type (Instant, Period, InstantTimeLocation).
     *
     * Sensors are organized in pairs of 2 (1 for each type). One sensor has values and the other does not.
     *
     * Sensor1 (3 Values) and Sensor4 (0 Values) are Instant sensors;
     * Sensor2 (2 Values) and Sensor5 (0 Values) are Period sensors;
     * Sensor3 (1 Value)  and Sensor6 (0 Values) are InstantTimeLocation sensors
     *
     * To assert, test iterates through the List<Reading> and check that each entry is present in the expected List<Reading>, built
     * using the created Reading doubles.
     */
    @Test
    void successfullyRetrieveReadingsForDeviceID () {
        //----------------Double behaviour standardization and creation of outputs------------------------------
        //-------------------SensorRepository-------------------
        //DeviceID
        DeviceID deviceIDDouble1 = mock(DeviceID.class);

        //List of Sensors

        //Instant Sensors
        //Has values
        Sensor sensorDouble1 = mock(Sensor.class);
        //Does not have values
        Sensor sensorDouble4 = mock(Sensor.class);

        //Period Sensors
        //Has values
        Sensor sensorDouble2 = mock(Sensor.class);
        //Does not have values
        Sensor sensorDouble5 = mock(Sensor.class);

        //InstantTimeLocation Sensors
        //Has values
        Sensor sensorDouble3 = mock(Sensor.class);
        //Does not have values
        Sensor sensorDouble6 = mock(Sensor.class);

        Iterable<Sensor> listOfSensorDoublesForDeviceIDDouble1 = List.of(sensorDouble1, sensorDouble2, sensorDouble3,sensorDouble4, sensorDouble5, sensorDouble6);
        when(sensorRepositoryDouble.findByDeviceID(deviceIDDouble1)).thenReturn(listOfSensorDoublesForDeviceIDDouble1);

        //-------------------Sensor-------------------
        //SensorFunctionalityID
        SensorFunctionalityID sensorFunctionalityIDDouble1 = mock(SensorFunctionalityID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble2 = mock(SensorFunctionalityID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble3 = mock(SensorFunctionalityID.class);

        when(sensorDouble1.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble1);
        when(sensorDouble2.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble2);
        when(sensorDouble3.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble3);
        when(sensorDouble4.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble1);
        when(sensorDouble5.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble2);
        when(sensorDouble6.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble3);

        //SensorID
        SensorID sensorIDDouble1 = mock(SensorID.class);
        SensorID sensorIDDouble2 = mock(SensorID.class);
        SensorID sensorIDDouble3 = mock(SensorID.class);
        SensorID sensorIDDouble4 = mock(SensorID.class);
        SensorID sensorIDDouble5 = mock(SensorID.class);
        SensorID sensorIDDouble6 = mock(SensorID.class);

        when(sensorDouble1.identity()).thenReturn(sensorIDDouble1);
        when(sensorDouble2.identity()).thenReturn(sensorIDDouble2);
        when(sensorDouble3.identity()).thenReturn(sensorIDDouble3);
        when(sensorDouble4.identity()).thenReturn(sensorIDDouble4);
        when(sensorDouble5.identity()).thenReturn(sensorIDDouble5);
        when(sensorDouble6.identity()).thenReturn(sensorIDDouble6);

        //-------------------SensorFunctionalityRepository-------------------
        //ServiceMethodToCall
        String serviceMethodToCallDouble1 = "listInstantValuesForSensorID";
        String serviceMethodToCallDouble2 = "listPeriodValuesForSensorID";
        String serviceMethodToCallDouble3 = "listInstantLocationValuesForSensorID";

        when(sensorFunctionalityRepositoryDouble.getServiceMethodToCallForSensorFunctionalityID(sensorFunctionalityIDDouble1)).thenReturn(serviceMethodToCallDouble1);
        when(sensorFunctionalityRepositoryDouble.getServiceMethodToCallForSensorFunctionalityID(sensorFunctionalityIDDouble2)).thenReturn(serviceMethodToCallDouble2);
        when(sensorFunctionalityRepositoryDouble.getServiceMethodToCallForSensorFunctionalityID(sensorFunctionalityIDDouble3)).thenReturn(serviceMethodToCallDouble3);

        //-----------------------------------------Values-----------------------------------------------------

        //Create Timestamps here
        Timestamp startInterval = new Timestamp(0);
        Timestamp endInterval = new Timestamp(1000);

        //----------------------------------------InstantTimeValueRepository--------------------------------------
        //Create Value List
        Value valueDouble1 = mock(Value.class);
        Value valueDouble2 = mock(Value.class);
        Value valueDouble3 = mock(Value.class);
        List<Value> valueListInstantTimeDoubleWithValues = List.of(valueDouble1, valueDouble2, valueDouble3);
        List<Value> valueInstantTimeDoubleEmpty = List.of();

        //Stub for Sensor1
        when(instantValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble1, startInterval, endInterval)).thenReturn(valueListInstantTimeDoubleWithValues);
        //Stub for Sensor4
        when(instantValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble4, startInterval, endInterval)).thenReturn(valueInstantTimeDoubleEmpty);

        //----------------------------------------PeriodTimeValueRepository--------------------------------------
        //Create Value List
        Value valueDouble4 = mock(Value.class);
        Value valueDouble5 = mock(Value.class);

        List<Value> valueListPeriodTimeDoubleWithValues = List.of(valueDouble4, valueDouble5);
        List<Value> valuePeriodTimeDoubleEmpty = List.of();

        //Stub for Sensor2
        when(periodValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble2, startInterval, endInterval)).thenReturn(valueListPeriodTimeDoubleWithValues);
        //Stub for Sensor5
        when(periodValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble5, startInterval, endInterval)).thenReturn(valuePeriodTimeDoubleEmpty);

        //----------------------------------------InstantTimeLocationValueRepository--------------------------------------
        //Create Value List
        Value valueDouble6 = mock(Value.class);

        List<Value> valueListInstantTimeLocationDoubleWithValues = List.of(valueDouble6);
        List<Value> valueInstantTimeLocationDoubleEmpty = List.of();

        //Stub for Sensor3
        when(instantTimeLocationValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble3, startInterval, endInterval)).thenReturn(valueListInstantTimeLocationDoubleWithValues);
        //Stub for Sensor6
        when(instantTimeLocationValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble6, startInterval, endInterval)).thenReturn(valueInstantTimeLocationDoubleEmpty);

        //-----------------------------------------Readings-----------------------------------------------------
        Reading readingDouble1 = mock(Reading.class);
        Reading readingDouble2 = mock(Reading.class);
        Reading readingDouble3 = mock(Reading.class);
        Reading readingDouble4 = mock(Reading.class);
        Reading readingDouble5 = mock(Reading.class);
        Reading readingDouble6 = mock(Reading.class);

        when(valueDouble1.getReading()).thenReturn(readingDouble1);
        when(valueDouble2.getReading()).thenReturn(readingDouble2);
        when(valueDouble3.getReading()).thenReturn(readingDouble3);
        when(valueDouble4.getReading()).thenReturn(readingDouble4);
        when(valueDouble5.getReading()).thenReturn(readingDouble5);
        when(valueDouble6.getReading()).thenReturn(readingDouble6);

        //----------------Create service and call method to test------------------------------------------------
        Map<SensorFunctionalityID, List<Reading>> returnMap = valueService.getAllMeasurementsForDeviceBetweenPeriod(deviceIDDouble1, startInterval, endInterval);

        //Prep assert
        //Create expected List<Reading>
        List<Reading> expectedListReading = new ArrayList<>();
        expectedListReading.add(readingDouble1);
        expectedListReading.add(readingDouble2);
        expectedListReading.add(readingDouble3);
        expectedListReading.add(readingDouble4);
        expectedListReading.add(readingDouble5);
        expectedListReading.add(readingDouble6);

        //Extract all readings from map into a single list
        List<Reading> resultListReading = new ArrayList<>();
        for (List<Reading>  list : returnMap.values()) {
            resultListReading.addAll(list);
        }

        //Iterate over all entries in List derived from method call result and compare to built list of expected readings
        for (Reading resultReading : resultListReading) {
            assertTrue(expectedListReading.contains(resultReading));
        }
    }

    @Test
    void successGetPeakPowerConsumption() {
        //Create double of ConfigScraper
        try (MockedConstruction<ConfigScraper> ConfigScraper = mockConstruction(ConfigScraper.class,
                (mock, context) -> {
                    //Stub methods to get the Strings set in the config file, for the expected name for the Grid Power
                    // Meter device and the grid meter cadence (in min.) to be used as the time defined for an "instant".
                    when(mock.loadGridPowerMeterID()).thenReturn("Grid Power Meter");
                    when(mock.loadGridPowerMeterCadence()).thenReturn("900000");
                })) {

            //Create objects created with "new" keyword in the method
            DeviceID deviceIDDoubleGrid = new DeviceID("Grid Power Meter");
            DeviceID deviceIDDoubleSource2 = new DeviceID("Power Source 2");
            DeviceID deviceIDDoubleSource3 = new DeviceID("Power Source 3");

            SensorFunctionalityID gridPowerMeterSensorFunctionality = new SensorFunctionalityID("PowerAverage");
            SensorFunctionalityID powerSourceSensorFunctionality = new SensorFunctionalityID("SpecificTimePowerConsumption");

            //SENSORS---------------------------------------------------------------------------------------------------
            //Create double of PowerGrid sensor - Period Sensors (functionality: PowerAverage - US25)
            Sensor sensorDouble1 = mock(Sensor.class);

            //Create double of Power Sources sensors - Instant Sensors (functionality: SpecificTimePowerConsumption - US24)
            Sensor sensorDouble2 = mock(Sensor.class);
            Sensor sensorDouble3 = mock(Sensor.class);
            Sensor sensorDouble4 = mock(Sensor.class);

            //Respective SensorIDs of all sensors
            SensorID sensorIDDouble1 = mock(SensorID.class);
            SensorID sensorIDDouble2 = mock(SensorID.class);
            SensorID sensorIDDouble3 = mock(SensorID.class);
            SensorID sensorIDDouble4 = mock(SensorID.class);

            //VALUES----------------------------------------------------------------------------------------------------
            //List of values of Grid Power Meter sensor
            PeriodTimeValue gridValueDouble1 = mock(PeriodTimeValue.class); //value for sensor 1
            PeriodTimeValue gridValueDouble2 = mock(PeriodTimeValue.class); //value for sensor 1
            List<Value> valueListGridPowerMeterSensor = List.of(gridValueDouble1, gridValueDouble2);

            //List of values of Power Source sensors
            InstantTimeValue sensor2ValueDouble1 = mock(InstantTimeValue.class); //value for sensor 2
            InstantTimeValue sensor2ValueDouble2 = mock(InstantTimeValue.class); //value for sensor 2
            InstantTimeValue sensor3ValueDouble1 = mock(InstantTimeValue.class); //value for sensor 3
            InstantTimeValue sensor4ValueDouble1 = mock(InstantTimeValue.class); //value for sensor 4

            List<Value> listOfValuesForPowerSourceSensor2 = List.of(sensor2ValueDouble1, sensor2ValueDouble2);
            List<Value> listOfValuesForPowerSourceSensor3 = List.of(sensor3ValueDouble1);
            List<Value> listOfValuesForPowerSourceSensor4 = List.of(sensor4ValueDouble1);

            //----------------------------------------------------------------------------------------------------------

            //Find sensor of functionality "PowerAverage" (US25) from grid power meter device
            when(sensorRepositoryDouble.findByDeviceIDAndSensorFunctionality(deviceIDDoubleGrid, gridPowerMeterSensorFunctionality)).thenReturn(List.of(sensorDouble1));

            //Find ID of the found Grid Power Meter device
            when(sensorDouble1.identity()).thenReturn(sensorIDDouble1);

            //Get values from Grid Power Meter sensor
            when(periodValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble1, Timestamp.valueOf("2024-04-01 12:15:00"), Timestamp.valueOf("2024-04-01 12:40:00"))).thenReturn(valueListGridPowerMeterSensor);

            //Find sensors from functionality "SpecificTimePowerConsumption" (US24)
            when(sensorRepositoryDouble.findBySensorFunctionality(powerSourceSensorFunctionality)).thenReturn(List.of(sensorDouble2, sensorDouble3, sensorDouble4));

            //For each sensor in ListOfSensorsFromPowerSources, get the deviceID
            when(sensorDouble2.getDeviceID()).thenReturn(deviceIDDoubleSource2); //sensor 2 (from Power Source) belongs to device 2
            when(sensorDouble3.getDeviceID()).thenReturn(deviceIDDoubleSource2); //sensor 3 (from Power Source) belongs to device 2
            when(sensorDouble4.getDeviceID()).thenReturn(deviceIDDoubleSource3); //sensor 4 (from Power Source) belongs to device 3

            //For each sensor in ListOfSensorsFromPowerSources, get the sensorID
            when(sensorDouble2.identity()).thenReturn(sensorIDDouble2);
            when(sensorDouble3.identity()).thenReturn(sensorIDDouble3);
            when(sensorDouble4.identity()).thenReturn(sensorIDDouble4);

            Timestamp startPeriod = Timestamp.valueOf("2024-04-01 12:15:00");
            Timestamp endPeriod = Timestamp.valueOf("2024-04-01 12:40:00");

            //For each sensor in ListOfSensorsFromPowerSources, get the values
            when(instantValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble2, startPeriod, endPeriod)).thenReturn(listOfValuesForPowerSourceSensor2);
            when(instantValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble3, startPeriod, endPeriod)).thenReturn(listOfValuesForPowerSourceSensor3);
            when(instantValueRepositoryDouble.findBySensorIdBetweenPeriodOfTime(sensorIDDouble4, startPeriod, endPeriod)).thenReturn(listOfValuesForPowerSourceSensor4);

            //For each value, get the instantTimeValue
            when(sensor2ValueDouble1.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2024-04-01 12:16:00")); //first instant
            when(sensor2ValueDouble2.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2024-04-01 12:21:00")); //first instant
            when(sensor3ValueDouble1.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2024-04-01 12:35:00")); //second instant
            when(sensor4ValueDouble1.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2024-04-01 12:55:00")); //out of given period


            /////CalculatePeakPowerConsumption()------------------------------------------------------------------------

            //For each value of Grid Power Meter values, get end time
            when(gridValueDouble1.getEndTimeReading()).thenReturn(Timestamp.valueOf("2024-04-01 12:30:00")); //first instant
            when(gridValueDouble2.getEndTimeReading()).thenReturn(Timestamp.valueOf("2024-04-01 12:45:00")); //second instant

            //For each value of Grid Power Meter values, get measurement
            when(gridValueDouble1.getReading()).thenReturn(mock(Reading.class));
            when(gridValueDouble2.getReading()).thenReturn(mock(Reading.class));
            when(gridValueDouble1.getReading().getMeasurement()).thenReturn("50");
            when(gridValueDouble2.getReading().getMeasurement()).thenReturn("60");

            //For each value of Power Source Sensors, get measurement
            when(sensor2ValueDouble1.getReading()).thenReturn(mock(Reading.class));
            when(sensor2ValueDouble2.getReading()).thenReturn(mock(Reading.class));
            when(sensor3ValueDouble1.getReading()).thenReturn(mock(Reading.class));
            when(sensor4ValueDouble1.getReading()).thenReturn(mock(Reading.class));

            when(sensor2ValueDouble1.getReading().getMeasurement()).thenReturn("10");
            when(sensor2ValueDouble2.getReading().getMeasurement()).thenReturn("20");
            when(sensor3ValueDouble1.getReading().getMeasurement()).thenReturn("30");
            when(sensor4ValueDouble1.getReading().getMeasurement()).thenReturn("40");

            //Act
            double peakPowerConsumption = valueService.getPeakPowerConsumption(startPeriod, endPeriod);

            //Assert
            assertEquals(90, peakPowerConsumption);
        }
    }

    /**
     * Retrieves the most recent measurement recorded by a sensor identified by its deviceID and sensor functionalityID.
     * In this case, a value of instant time type.
     */
    @Test
    void successfullyGetLastInstantTimeMeasurement() {
        //Create doubles and setting their behaviour
        DeviceID deviceID = mock(DeviceID.class);
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);

        Sensor sensor1 = mock(Sensor.class);
        SensorID sensorID1 = mock(SensorID.class);
        when(sensor1.identity()).thenReturn(sensorID1);

        Sensor sensor2 = mock(Sensor.class);
        SensorID sensorID2 = mock(SensorID.class);
        when(sensor2.identity()).thenReturn(sensorID2);

        Value value = mock(Value.class);
        Reading reading = mock(Reading.class);
        when(value.getReading()).thenReturn(reading);
        when(reading.getMeasurement()).thenReturn("50");

        //Setting the behaviour of dependencies
        when(sensorRepositoryDouble.findByDeviceIDAndSensorFunctionality(deviceID, sensorFunctionalityID)).thenReturn(List.of(sensor1, sensor2));
        when(instantValueRepositoryDouble.findLastValueRecorded(sensorID1)).thenReturn(value);
        when(instantTimeLocationValueRepositoryDouble.findLastValueRecorded(sensorID1)).thenReturn(null);

        //Act
        String result = valueService.getLastMeasurementOfASensor(deviceID, sensorFunctionalityID);

        //Assert
        assertEquals("50", result);
    }

    /**
     * Retrieves the most recent measurement recorded by a sensor identified by its deviceID and sensor functionalityID.
     * In this case, a value of instant time location type.
     */
    @Test
    void successfullyGetLastInstantTimeLocationMeasurement() {
        //Create doubles and setting their behaviour
        DeviceID deviceID = mock(DeviceID.class);
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);

        Sensor sensor1 = mock(Sensor.class);
        SensorID sensorID1 = mock(SensorID.class);
        when(sensor1.identity()).thenReturn(sensorID1);

        Sensor sensor2 = mock(Sensor.class);
        SensorID sensorID2 = mock(SensorID.class);
        when(sensor2.identity()).thenReturn(sensorID2);

        Value value = mock(Value.class);
        Reading reading = mock(Reading.class);
        when(value.getReading()).thenReturn(reading);
        when(reading.getMeasurement()).thenReturn("50");

        //Setting the behaviour of dependencies
        when(sensorRepositoryDouble.findByDeviceIDAndSensorFunctionality(deviceID, sensorFunctionalityID)).thenReturn(List.of(sensor1, sensor2));
        when(instantValueRepositoryDouble.findLastValueRecorded(sensorID1)).thenReturn(null);
        when(instantTimeLocationValueRepositoryDouble.findLastValueRecorded(sensorID1)).thenReturn(value);

        //Act
        String result = valueService.getLastMeasurementOfASensor(deviceID, sensorFunctionalityID);

        //Assert
        assertEquals("50", result);
    }

    /**
     * Retrieves the most recent measurement recorded by a sensor identified by its deviceID and sensor functionalityID.
     * If no such measurement is found in the repository, a 'ValueNotFoundException' exception is thrown, indicating that
     * the value does not belong to either instant time or instant time location types, or that no value has been recorded.
     */
    @Test
    void failGetLastMeasurementNoValueFound() {
        //Create doubles and setting their behaviour
        DeviceID deviceID = mock(DeviceID.class);
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);

        Sensor sensor1 = mock(Sensor.class);
        SensorID sensorID1 = mock(SensorID.class);
        when(sensor1.identity()).thenReturn(sensorID1);

        Sensor sensor2 = mock(Sensor.class);
        SensorID sensorID2 = mock(SensorID.class);
        when(sensor2.identity()).thenReturn(sensorID2);

        Value value = mock(Value.class);
        Reading reading = mock(Reading.class);
        when(value.getReading()).thenReturn(reading);
        when(reading.getMeasurement()).thenReturn("50");

        //Setting the behaviour of dependencies
        when(sensorRepositoryDouble.findByDeviceIDAndSensorFunctionality(deviceID, sensorFunctionalityID)).thenReturn(List.of(sensor1, sensor2));
        when(instantValueRepositoryDouble.findLastValueRecorded(sensorID1)).thenReturn(null);
        when(instantTimeLocationValueRepositoryDouble.findLastValueRecorded(sensorID1)).thenReturn(null);

        assertThrows(ValueNotFoundException.class, () -> valueService.getLastMeasurementOfASensor(deviceID, sensorFunctionalityID));
    }

    /**
     * Retrieves the most recent measurement recorded by a sensor identified by its deviceID and sensor functionalityID.
     * If no sensor is found in the repository, a 'SensorNotFoundException' exception is thrown.
     */
    @Test
    void failGetLastMeasurementNoSensorFound() {
        //Create doubles and setting their behaviour
        DeviceID deviceID = mock(DeviceID.class);
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);

        Sensor sensor1 = mock(Sensor.class);
        SensorID sensorID1 = mock(SensorID.class);
        when(sensor1.identity()).thenReturn(sensorID1);

        Sensor sensor2 = mock(Sensor.class);
        SensorID sensorID2 = mock(SensorID.class);
        when(sensor2.identity()).thenReturn(sensorID2);

        Value value = mock(Value.class);
        Reading reading = mock(Reading.class);
        when(value.getReading()).thenReturn(reading);
        when(reading.getMeasurement()).thenReturn("50");

        //Setting the behaviour of dependencies
        when(sensorRepositoryDouble.findByDeviceIDAndSensorFunctionality(deviceID, sensorFunctionalityID)).thenReturn(List.of());

        assertThrows(SensorNotFoundException.class, () -> valueService.getLastMeasurementOfASensor(deviceID, sensorFunctionalityID));
    }
}