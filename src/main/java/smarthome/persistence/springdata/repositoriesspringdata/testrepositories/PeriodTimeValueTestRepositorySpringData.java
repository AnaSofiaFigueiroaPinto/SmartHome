package smarthome.persistence.springdata.repositoriesspringdata.testrepositories;

import smarthome.domain.value.ImpFactoryPeriodTimeValue;
import smarthome.domain.value.PeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.repositoriesmem.PeriodTimeValueRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PeriodTimeValueTestRepositorySpringData implements PeriodTimeValueRepository {

    /**
     * The List of Value entities.
     */
    private final List<Value> listPeriodTimeValues;

    /**
     *  Constructor of the PeriodTimeValueTestRepositorySpringData that receives a factoryPeriodTimeValue
     *  and populates the listPeriodTimeValues with values.
     */
    public PeriodTimeValueTestRepositorySpringData() {
        // Initialize PeriodTimeLocationValue objects for testing
        // (These PeriodTimeLocationValue objects can be used to simulate data in a test environment)
        // Note: This class is designed for testing purposes and does not interact with an actual database.
        this.listPeriodTimeValues = new ArrayList<>();
        ImpFactoryPeriodTimeValue factoryPeriodTimeValue = new ImpFactoryPeriodTimeValue();
        populateValues(factoryPeriodTimeValue);
    }


    /**
     * Method to populate the listPeriodLocationValues with values
     * @param factoryPeriodTimeValue The factory for PeriodTimeLocationValue.
     * This parameter is used to create the PeriodTimeValue objects and add them to the list.
     */
    private void populateValues(ImpFactoryPeriodTimeValue factoryPeriodTimeValue){
        //Addition of LocalDateTime to each Timestamp
        LocalDateTime localDateTime = LocalDateTime.of(2024, 4, 1, 12, 15, 0);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 4, 1, 12, 30, 0);
        LocalDateTime localDateTime3 = LocalDateTime.of(2024, 4, 1, 12, 45, 0);

        Timestamp timestamp1 = Timestamp.valueOf(localDateTime);
        Timestamp timestamp2 = Timestamp.valueOf(localDateTime2);
        Timestamp timestamp3 = Timestamp.valueOf(localDateTime3);

        //Addition of values to the list
        listPeriodTimeValues.add(factoryPeriodTimeValue.createValue(new ValueID("GridValue1"), new SensorID("Sensor012"), new Reading("50", "W"), timestamp1, timestamp2));
        listPeriodTimeValues.add(factoryPeriodTimeValue.createValue(new ValueID("GridValue2"), new SensorID("Sensor012"), new Reading("60", "W"), timestamp2, timestamp3));
    }

    /**
     * Finds values by sensor ID.
     * @param sensorID The unique identifier of the sensor.
     * @return A list of Value objects.
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        List<Value> returnValues = new ArrayList<>();

        for (Value value : listPeriodTimeValues) {
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

        for (Value value : listPeriodTimeValues) {
            PeriodTimeValue periodValue = (PeriodTimeValue) value;
            String valueFromListID = value.getSensorID().toString();
            String sensorIDString = sensorID.toString();
            if (valueFromListID.equals(sensorIDString) &&
                    periodValue.getStartTimeReading().after(start) &&
                    periodValue.getEndTimeReading().before(end)
            ) {

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
        for (Value value : listPeriodTimeValues) {
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
        return listPeriodTimeValues;
    }

    /**
     * Checks if a Value entity with the specified ID exists in the repository.
     * @param id The unique identifier of the entity to check.
     * @return True if the entity exists, otherwise false.
     */
    @Override
    public boolean containsEntityByID(ValueID id) {
        for (Value value : listPeriodTimeValues) {
            if (value.identity().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
