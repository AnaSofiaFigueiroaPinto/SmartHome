package smarthome.persistence.springdata.repositoriesspringdata.testrepositories;

import smarthome.domain.value.ImpFactoryInstantTimeValue;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.repositoriesmem.InstantTimeValueRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A test implementation of the InstantTimeValueRepository interface for Spring Data testing purposes.
 * This class provides a set of pre-defined Sensor objects for testing various repository methods.
 */
public class InstantTimeValueTestRepositorySpringData implements InstantTimeValueRepository {

    /**
     * List of Predictable values that populate repository for testing
     *
     * +-------+--------------------------+--------+------------+---------------------+---------------------+
     * | Value |       ValueID            | Reading| SensorID   |      Timestamp      | Measurement Unit    |
     * +-------+--------------------------+--------+------------+---------------------+---------------------+
     * | value1| Consumption13Value1      | 10     | Sensor013  | 2024-04-01 12:00:00 | W                   |
     * | value2| Consumption13Value2      | 20     | Sensor013  | 2024-04-01 12:21:00 | W                   |
     * | value3| Consumption14Value1      | 30     | Sensor014  | 2024-04-01 12:35:00 | W                   |
     * | value4| InsideTemperatureValue1  | 20     | Sensor001  | 2024-04-15 08:00:00 | Cº                  |
     * | value5| InsideTemperatureValue2  | 20     | Sensor001  | 2024-04-15 10:00:00 | Cº                  |
     * | value6| InsideTemperatureValue3  | 20     | Sensor001  | 2024-04-15 12:00:00 | Cº                  |
     * | value7| InsideTemperatureValue4  | 20     | Sensor001  | 2024-04-15 14:00:00 | Cº                  |
     * | value8| InsideTemperatureValue5  | 20     | Sensor001  | 2024-04-15 16:00:00 | Cº                  |
     * | value9| OutsideTemperatureValue1 | 14     | Sensor007  | 2024-04-15 08:00:00 | Cº                  |
     * | value10| OutsideTemperatureValue2| 17     | Sensor007  | 2024-04-15 09:30:00 | Cº                  |
     * | value11| OutsideTemperatureValue3| 18     | Sensor007  | 2024-04-15 10:50:00 | Cº                  |
     * | value12| OutsideTemperatureValue4| 22     | Sensor007  | 2024-04-15 12:35:00 | Cº                  |
     * | value13| OutsideTemperatureValue5| 24     | Sensor007  | 2024-04-15 14:05:00 | Cº                  |
     * | value14| OutsideTemperatureValue6| 24     | Sensor007  | 2024-04-15 15:30:00 | Cº                  |
     * | value15| OutsideTemperatureValue7| 21     | Sensor007  | 2024-04-15 17:00:00 | Cº                  |
     * | value16| OutsideTemperatureValue8| 18     | Sensor007  | 2024-04-15 18:45:00 | Cº                  |
     * | value17| OutsideTemperatureValue9| 16     | Sensor007  | 2024-04-15 20:00:00 | Cº                  |
     * | value18| BlinderValue1           | 50     | Sensor011  | 2024-04-15 20:00:00 | %                   |
     * +-------+------------------------+----------+------------+---------------------+---------------------+
     */
    private List<Value> instantValueList;

    /**
     * Factory to create InstantTimeValue objects
     */
    private ImpFactoryInstantTimeValue factoryInstantTimeValue;

    /**
     * Constructor of the InstantTimeValueRepositorySpringDataImp that receives a factoryInstantTimeValue
     * and populates the instantValueList with values.
     *
     */
    public InstantTimeValueTestRepositorySpringData() {
        this.factoryInstantTimeValue = new ImpFactoryInstantTimeValue();
        this.instantValueList = populateArrayListOfValues();
    }

    /**
     * Aux method to populate the instantValueList with values for testing purposes.
     *
     * @return List of Value objects
     */
    private  List<Value> populateArrayListOfValues () {
        List<Value> instantValueList = new ArrayList<>();

        //----------------------VO---------------------------------
        //READINGS VO
        Reading reading1 = new Reading("10","W");
        Reading reading2 = new Reading("20","W");
        Reading reading3 = new Reading("30", "W");
        Reading reading4 = new Reading("20", "Cº");
        Reading reading5 = new Reading("20", "Cº");
        Reading reading6 = new Reading("20", "Cº");
        Reading reading7 = new Reading("20", "Cº");
        Reading reading8 = new Reading("20", "Cº");
        Reading reading9 = new Reading("14", "Cº");
        Reading reading10 = new Reading("17", "Cº");
        Reading reading11 = new Reading("18", "Cº");
        Reading reading12 = new Reading("22", "Cº");
        Reading reading13 = new Reading("24", "Cº");
        Reading reading14 = new Reading("24", "Cº");
        Reading reading15 = new Reading("21", "Cº");
        Reading reading16 = new Reading("18", "Cº");
        Reading reading17 = new Reading("16", "Cº");
        Reading reading18 = new Reading("50", "%");

        //VALUEID VO
        ValueID valueID1 = new ValueID("Consumption13Value1");
        ValueID valueID2 = new ValueID("Consumption13Value2");
        ValueID valueID3 = new ValueID("Consumption14Value1");
        ValueID valueID4 = new ValueID("InsideTemperatureValue1");
        ValueID valueID5 = new ValueID("InsideTemperatureValue2");
        ValueID valueID6 = new ValueID("InsideTemperatureValue3");
        ValueID valueID7 = new ValueID("InsideTemperatureValue4");
        ValueID valueID8 = new ValueID("InsideTemperatureValue5");
        ValueID valueID9 = new ValueID("OutsideTemperatureValue1");
        ValueID valueID10 = new ValueID("OutsideTemperatureValue2");
        ValueID valueID11 = new ValueID("OutsideTemperatureValue3");
        ValueID valueID12 = new ValueID("OutsideTemperatureValue4");
        ValueID valueID13 = new ValueID("OutsideTemperatureValue5");
        ValueID valueID14 = new ValueID("OutsideTemperatureValue6");
        ValueID valueID15 = new ValueID("OutsideTemperatureValue7");
        ValueID valueID16 = new ValueID("OutsideTemperatureValue8");
        ValueID valueID17 = new ValueID("OutsideTemperatureValue9");
        ValueID valueID18 = new ValueID("BlinderValue1");

        //SENSORID VO
        SensorID sensorID1 = new SensorID("Sensor013");
        SensorID sensorID2 = new SensorID("Sensor013");
        SensorID sensorID3 = new SensorID("Sensor014");
        SensorID sensorID4 = new SensorID("Sensor001");
        SensorID sensorID5 = new SensorID("Sensor001");
        SensorID sensorID6 = new SensorID("Sensor001");
        SensorID sensorID7 = new SensorID("Sensor001");
        SensorID sensorID8 = new SensorID("Sensor001");
        SensorID sensorID9 = new SensorID("Sensor007");
        SensorID sensorID10 = new SensorID("Sensor007");
        SensorID sensorID11 = new SensorID("Sensor007");
        SensorID sensorID12 = new SensorID("Sensor007");
        SensorID sensorID13 = new SensorID("Sensor007");
        SensorID sensorID14 = new SensorID("Sensor007");
        SensorID sensorID15 = new SensorID("Sensor007");
        SensorID sensorID16 = new SensorID("Sensor007");
        SensorID sensorID17 = new SensorID("Sensor007");
        SensorID sensorID18 = new SensorID("Sensor011");

        //TIMESTAMP VO
        Timestamp valueTimestamp1 = Timestamp.valueOf("2024-04-01 12:00:00");
        Timestamp valueTimestamp2 = Timestamp.valueOf("2024-04-01 12:21:00");
        Timestamp valueTimestamp3 = Timestamp.valueOf("2024-04-01 12:35:00");
        Timestamp valueTimestamp4 = Timestamp.valueOf("2024-04-15 08:00:00");
        Timestamp valueTimestamp5 = Timestamp.valueOf("2024-04-15 10:00:00");
        Timestamp valueTimestamp6 = Timestamp.valueOf("2024-04-15 12:00:00");
        Timestamp valueTimestamp7 = Timestamp.valueOf("2024-04-15 14:00:00");
        Timestamp valueTimestamp8 = Timestamp.valueOf("2024-04-15 16:00:00");
        Timestamp valueTimestamp9 = Timestamp.valueOf("2024-04-15 08:00:00");
        Timestamp valueTimestamp10 = Timestamp.valueOf("2024-04-15 09:30:00");
        Timestamp valueTimestamp11 = Timestamp.valueOf("2024-04-15 10:50:00");
        Timestamp valueTimestamp12 = Timestamp.valueOf("2024-04-15 12:35:00");
        Timestamp valueTimestamp13 = Timestamp.valueOf("2024-04-15 14:05:00");
        Timestamp valueTimestamp14 = Timestamp.valueOf("2024-04-15 15:30:00");
        Timestamp valueTimestamp15 = Timestamp.valueOf("2024-04-15 17:00:00");
        Timestamp valueTimestamp16 = Timestamp.valueOf("2024-04-15 18:45:00");
        Timestamp valueTimestamp17 = Timestamp.valueOf("2024-04-15 20:00:00");
        Timestamp valueTimestamp18 = Timestamp.valueOf("2024-04-15 20:00:00");

        //----------------------VALUES---------------------------------
        Value value1 = factoryInstantTimeValue.createValue(valueID1, sensorID1, reading1, valueTimestamp1);
        Value value2 = factoryInstantTimeValue.createValue(valueID2, sensorID2, reading2, valueTimestamp2);
        Value value3 = factoryInstantTimeValue.createValue(valueID3, sensorID3, reading3, valueTimestamp3);
        Value value4 = factoryInstantTimeValue.createValue(valueID4, sensorID4, reading4, valueTimestamp4);
        Value value5 = factoryInstantTimeValue.createValue(valueID5, sensorID5, reading5, valueTimestamp5);
        Value value6 = factoryInstantTimeValue.createValue(valueID6, sensorID6, reading6, valueTimestamp6);
        Value value7 = factoryInstantTimeValue.createValue(valueID7, sensorID7, reading7, valueTimestamp7);
        Value value8 = factoryInstantTimeValue.createValue(valueID8, sensorID8, reading8, valueTimestamp8);
        Value value9 = factoryInstantTimeValue.createValue(valueID9, sensorID9, reading9, valueTimestamp9);
        Value value10 = factoryInstantTimeValue.createValue(valueID10, sensorID10, reading10, valueTimestamp10);
        Value value11 = factoryInstantTimeValue.createValue(valueID11, sensorID11, reading11, valueTimestamp11);
        Value value12 = factoryInstantTimeValue.createValue(valueID12, sensorID12, reading12, valueTimestamp12);
        Value value13 = factoryInstantTimeValue.createValue(valueID13, sensorID13, reading13, valueTimestamp13);
        Value value14 = factoryInstantTimeValue.createValue(valueID14, sensorID14, reading14, valueTimestamp14);
        Value value15 = factoryInstantTimeValue.createValue(valueID15, sensorID15, reading15, valueTimestamp15);
        Value value16 = factoryInstantTimeValue.createValue(valueID16, sensorID16, reading16, valueTimestamp16);
        Value value17 = factoryInstantTimeValue.createValue(valueID17, sensorID17, reading17, valueTimestamp17);
        Value value18 = factoryInstantTimeValue.createValue(valueID18, sensorID18, reading18, valueTimestamp18);

        //----------------------ADD VALUES TO LIST---------------------------------
        instantValueList.add(value1);
        instantValueList.add(value2);
        instantValueList.add(value3);
        instantValueList.add(value4);
        instantValueList.add(value5);
        instantValueList.add(value6);
        instantValueList.add(value7);
        instantValueList.add(value8);
        instantValueList.add(value9);
        instantValueList.add(value10);
        instantValueList.add(value11);
        instantValueList.add(value12);
        instantValueList.add(value13);
        instantValueList.add(value14);
        instantValueList.add(value15);
        instantValueList.add(value16);
        instantValueList.add(value17);
        instantValueList.add(value18);

        return instantValueList;
    }


    /**
     * Method to find the latest value recorded in the repository.
     *
     * @param sensorID SensorID object that the value belongs to.
     * @return Found Value object or null if no value is found.
     */
    @Override
    public Value findLastValueRecorded (SensorID sensorID) {
        InstantTimeValue lastValue = null;
        for (Value value : instantValueList) {
            InstantTimeValue instantTimeValue = (InstantTimeValue) value;

            if (value.getSensorID().equals(sensorID)) {
                if (lastValue == null) {
                    lastValue = instantTimeValue;
                } else if (instantTimeValue.getInstantTimeReading().after(lastValue.getInstantTimeReading())) {
                    lastValue = instantTimeValue;
                }
            }
        }

        return lastValue;
    }

    /**
     * Method to find a value belonging to a specific SensorID.
     *
     * @param sensorID SensorID object that the value belongs to
     * @return List of Value type objects. List may be empty if no Values are found.
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        List<Value> returnValues = new ArrayList<>();

        for (Value value : instantValueList) {
            if (value.getSensorID().equals(sensorID)) {
                returnValues.add(value);
            }
        }

        return returnValues;
    }

    /**
     * Method to find a value belonging to a specific SensorID and where the value present in persistence
     * is between the start and end variables.
     *
     * @param sensorID SensorID object that the value belongs to.
     * @param start    Timestamp object that represents the start of the period.
     * @param end      Timestamp object that represents the end of the period.
     * @return List of Value type objects. List may be empty if no Values are found.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end) {
        List<Value> returnValues = new ArrayList<>();

        for (Value value : instantValueList) {
            InstantTimeValue instantTimeValue = (InstantTimeValue) value;
            String valueFromListID = value.getSensorID().toString();
            String sensorIDString = sensorID.toString();
            if (valueFromListID.equals(sensorIDString) &&
                    instantTimeValue.getInstantTimeReading().after(start) &&
                    instantTimeValue.getInstantTimeReading().before(end)) {

                returnValues.add(value);
            }
        }

        return returnValues;
    }

    /**
     * Finds an entity in the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */
    @Override
    public Optional<Value> findEntityByID(ValueID id) {
        for (Value value : instantValueList) {
            if (value.identity().equals(id)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all entity objects from the repository.
     *
     * @return an iterable collection of entity objects
     */
    @Override
    public Iterable<Value> findAllEntities() {
        return instantValueList;
    }

    /**
     * Checks whether an entity with the given identifier exists in the repository.
     *
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
     */
    @Override
    public boolean containsEntityByID(ValueID id) {
        for (Value value : instantValueList) {
            if (value.identity().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
