package smarthome.persistence.repositoriesmem;

import smarthome.domain.value.InstantTimeLocationValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the InstantTimeLocationValueRepositoryMem class for persistence in memory.
 */
public class InstantTimeLocationValueRepositoryMem implements InstantTimeLocationValueRepository{

    /**
     * Map of ValueID and Value objects.
     */
    private Map<ValueID, Value> valueMap;

    /**
     * Constructs an InstantTimeLocationValueRepositoryMem with the provided value map.
     *
     * @param valueMap The map containing ValueID as keys and Value objects as values.
     */
    public InstantTimeLocationValueRepositoryMem(Map<ValueID, Value> valueMap) {
        this.valueMap = valueMap;
    }

    /**
     * Method to find a value associated to a SensorID.
     *
     * @param sensorID SensorID object that the value is associated with
     * @return List of InstantTimeLocationValue type objects. List may be empty if Values are not found.
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        List<Value> values = new ArrayList<>();
        for (Value value : valueMap.values()) {
            InstantTimeLocationValue instantTimeLocationValue = (InstantTimeLocationValue) value;
            if (instantTimeLocationValue.getSensorID().equals(sensorID)) {
                values.add(value);
            }
        }
        return values;
    }

    /**
     * Method to find a value associated to a SensorID and where the timestamp1 variable present in persistence
     * is between the start and end variables.
     *
     * @param sensorID SensorID object that the value is associated with
     * @param start    Timestamp object that represents the start of the period
     * @param end      Timestamp object that represents the end of the period
     * @return List of Value type objects. List may be empty if Values are not found.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end) {
        List<Value> values = new ArrayList<>();
        for (Value value : valueMap.values()) {
            InstantTimeLocationValue instantTimeLocationValue = (InstantTimeLocationValue) value;
            if (instantTimeLocationValue.getSensorID().equals(sensorID) &&
                    instantTimeLocationValue.getInstantTime().after(start) &&
                    instantTimeLocationValue.getInstantTime().before(end)) {
                values.add(value);
            }
        }
        return values;
    }

    /**
     * Finds a value by its ID.
     *
     * @param id The ID of the value to find.
     * @return An Optional containing the value if found, otherwise empty.
     */
    @Override
    public Optional<Value> findEntityByID(ValueID id) {
        return Optional.ofNullable(valueMap.get(id));
    }

    /**
     * Finds all values in the repository.
     *
     * @return An Iterable containing all values in the repository.
     */
    @Override
    public Iterable<Value> findAllEntities() {
        return valueMap.values();
    }

    /**
     * Checks if the repository contains a value with the specified ID.
     *
     * @param id The ID of the value to check.
     * @return true if the repository contains the value, otherwise false.
     */
    @Override
    public boolean containsEntityByID(ValueID id) {
        return valueMap.containsKey(id);
    }

    /**
     * Retrieves the last value recorded according to instant time by sensorID.
     *
     * @param sensorID SensorID object that the value belongs to
     * @return the object value that was last recorded.
     */
    @Override
    public Value findLastValueRecorded(SensorID sensorID) {
        List<InstantTimeLocationValue> values = new ArrayList<>();
        for (Value value : valueMap.values()) {
            if (value.getSensorID().equals(sensorID)) {
                values.add((InstantTimeLocationValue) value);
            }
        }
        //Bubble sort to order the list of values by timestamp descending
        for (int i = 0; i < values.size() - 1; i++) {
            for (int j = 0; j < values.size() - i - 1; j++) {
                InstantTimeLocationValue value1 = values.get(j);
                InstantTimeLocationValue value2 = values.get(j + 1);

                Timestamp timestamp1 = value1.getInstantTime();
                Timestamp timestamp2 = value2.getInstantTime();
                Instant instant1 = timestamp1.toInstant();
                Instant instant2 = timestamp2.toInstant();

                if (instant1.compareTo(instant2) < 0) {
                    InstantTimeLocationValue temp = values.get(j);
                    values.set(j, values.get(j + 1));
                    values.set(j + 1, temp);
                }
            }
        }
        if (!values.isEmpty())
            return values.get(0);
        return null;
    }
}
