package smarthome.persistence.springdata.repositoriesspringdata.testrepositories;

import smarthome.domain.value.ImpFactoryInstantTimeLocationValue;
import smarthome.domain.value.InstantTimeLocationValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.repositoriesmem.InstantTimeLocationValueRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstantTimeLocationValueTestRepoSpringData implements InstantTimeLocationValueRepository {

    /**
     * The List of Value entities.
     */
    private final List<Value> listInstantLocationValues;

    /**
     *  Constructor of the InstantTimeLocationValueTestRepoSpringData that receives a factoryInstantTimeLocationValue
     *  and populates the listInstantLocationValues with values.
     */
    public InstantTimeLocationValueTestRepoSpringData() {
        // Initialize InstantTimeLocationValue objects for testing
        // (These InstantTimeLocationValue objects can be used to simulate data in a test environment)
        // Note: This class is designed for testing purposes and does not interact with an actual database.
        this.listInstantLocationValues = new ArrayList<>();
        ImpFactoryInstantTimeLocationValue factoryInstantTimeLocationValue = new ImpFactoryInstantTimeLocationValue();
        populateValues(factoryInstantTimeLocationValue);
    }

    /**
    * Method to populate the listInstantLocationValues with values
     * @param factoryInstantTimeLocationValue The factory for InstantTimeLocationValue.
     * This parameter is used to create the InstantTimeLocationValue objects and add them to the list.
     */
    private void populateValues(ImpFactoryInstantTimeLocationValue factoryInstantTimeLocationValue){
        //Addition of LocalDateTime to each Timestamp
        LocalDateTime localDateTime = LocalDateTime.of(2024, 4, 15, 7, 8, 0);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 5, 15, 6, 55, 0);
        LocalDateTime localDateTime3 = LocalDateTime.of(2024, 6, 5, 6, 25, 0);
        LocalDateTime localDateTime4 = LocalDateTime.of(2024, 7, 4, 5, 58, 0);
        LocalDateTime localDateTime5 = LocalDateTime.of(2024, 4, 15, 18, 45, 0);
        LocalDateTime localDateTime6 = LocalDateTime.of(2024, 5, 15, 19, 15, 0);
        LocalDateTime localDateTime7 = LocalDateTime.of(2024, 6, 5, 20, 15, 0);
        LocalDateTime localDateTime8 = LocalDateTime.of(2024, 7, 4, 21, 0, 0);

        Timestamp timestamp1 = Timestamp.valueOf(localDateTime);
        Timestamp timestamp2 = Timestamp.valueOf(localDateTime2);
        Timestamp timestamp3 = Timestamp.valueOf(localDateTime3);
        Timestamp timestamp4 = Timestamp.valueOf(localDateTime4);
        Timestamp timestamp5 = Timestamp.valueOf(localDateTime5);
        Timestamp timestamp6 = Timestamp.valueOf(localDateTime6);
        Timestamp timestamp7 = Timestamp.valueOf(localDateTime7);
        Timestamp timestamp8 = Timestamp.valueOf(localDateTime8);

        //Define SensorID for Sunrise and Sunset
        SensorID sensorIDSunrise = new SensorID("Sensor003");
        SensorID sensorIDSunset = new SensorID("Sensor004");

        //Addition of values to the list
        listInstantLocationValues.add(factoryInstantTimeLocationValue.createValue(new ValueID("Sunrise1"), sensorIDSunrise, new Reading("07:08", "h"), timestamp1, new GPSCode(50.7958, -4.2596)));
        listInstantLocationValues.add(factoryInstantTimeLocationValue.createValue(new ValueID("Sunrise2"), sensorIDSunrise, new Reading("06:55", "h"), timestamp2, new GPSCode(50.7958, -4.2596)));
        listInstantLocationValues.add(factoryInstantTimeLocationValue.createValue(new ValueID("Sunrise3"), sensorIDSunrise, new Reading("06:25", "h"), timestamp3, new GPSCode(50.7958, -4.2596)));
        listInstantLocationValues.add(factoryInstantTimeLocationValue.createValue(new ValueID("Sunrise4"), sensorIDSunrise, new Reading("05:58", "h"), timestamp4, new GPSCode(50.7958, -4.2596)));
        listInstantLocationValues.add(factoryInstantTimeLocationValue.createValue(new ValueID("Sunset1"), sensorIDSunset, new Reading("18:45", "h"), timestamp5, new GPSCode(50.7958, -4.2596)));
        listInstantLocationValues.add(factoryInstantTimeLocationValue.createValue(new ValueID("Sunset2"), sensorIDSunset, new Reading("19:15", "h"), timestamp6, new GPSCode(50.7958, -4.2596)));
        listInstantLocationValues.add(factoryInstantTimeLocationValue.createValue(new ValueID("Sunset3"), sensorIDSunset, new Reading("20:15", "h"), timestamp7, new GPSCode(50.7958, -4.2596)));
        listInstantLocationValues.add(factoryInstantTimeLocationValue.createValue(new ValueID("Sunset4"), sensorIDSunset, new Reading("21:00", "h"), timestamp8, new GPSCode(50.7958, -4.2596)));
    }

    /**
     * Finds values by sensor ID.
     * @param sensorID The unique identifier of the sensor.
     * @return A list of Value objects.
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        List<Value> returnValues = new ArrayList<>();

        for (Value value : listInstantLocationValues) {
            if (value.getSensorID().equals(sensorID)) {
                returnValues.add(value);
            }
        }
        return returnValues;
    }

    /**
     * Finds values by sensor ID between a period of time.
     * @param sensorID The unique identifier of the sensor.
     * @param start The start of the period of time.
     * @param end The end of the period of time.
     * @return A list of Value objects.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end) {
        List<Value> listOfValues = new ArrayList<>();

        for (Value value : listInstantLocationValues) {
            InstantTimeLocationValue instantValue = (InstantTimeLocationValue) value;
            String valueFromListID = value.getSensorID().toString();
            String sensorIDString = sensorID.toString();
            if (valueFromListID.equals(sensorIDString) &&
                    instantValue.getInstantTime().after(start) &&
                    instantValue.getInstantTime().before(end)) {

                listOfValues.add(value);
            }
        }

        return listOfValues;
    }

    /**
     * Finds and Optional Value entity in the repository.
     * @param id the identifier of the Value entity.
     * @return an Optional of the Value entity.
     */
    @Override
    public Optional<Value> findEntityByID(ValueID id) {
        for (Value value : listInstantLocationValues) {
            if (value.identity().equals(id)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds all values in the repository.
     * @return An Iterable containing all Value entities.
     */
    @Override
    public Iterable<Value> findAllEntities() {
        return listInstantLocationValues;
    }

    /**
     * Checks if a Value entity with the specified ID exists in the repository.
     * @param id The unique identifier of the entity to check.
     * @return True if the entity exists, otherwise false.
     */
    @Override
    public boolean containsEntityByID(ValueID id) {
        for (Value value : listInstantLocationValues) {
            if (value.identity().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to find the latest value recorded in the repository.
     *
     * @param sensorID SensorID object that the value belongs to.
     * @return Found Value object or null if no value is found.
     */
    @Override
    public Value findLastValueRecorded (SensorID sensorID) {
        InstantTimeLocationValue lastValue = null;
        for (Value value : listInstantLocationValues) {
            InstantTimeLocationValue instantTimeLocationValue = (InstantTimeLocationValue) value;

            if (value.getSensorID().equals(sensorID)) {
                if (lastValue == null) {
                    lastValue = instantTimeLocationValue;
                } else if (instantTimeLocationValue.getInstantTime().after(lastValue.getInstantTime())) {
                    lastValue = instantTimeLocationValue;
                }
            }
        }
        return lastValue;
    }
}
