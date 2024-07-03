package smarthome.persistence.repositoriesmem;

import smarthome.domain.value.InstantTimeValue;
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
 * Represents the InstantTimeValueRepositoryMem class for persistence in memory.
 */
public class InstantTimeValueRepositoryMem implements InstantTimeValueRepository {
    /**
     * Map of ValueID and Value objects.
     */
    private Map<ValueID, Value> valueMap;

    /**
     * Constructor for InstantTimeValueRepositoryMem object. Currently, built this way as there doesn't exist a database yet.
     *
     * @param valueMap with ValueID as key and Value as value.
     */
    public InstantTimeValueRepositoryMem(Map<ValueID, Value> valueMap) {
        this.valueMap = valueMap;
    }

    /**
     * Method to find a value belonging to a specific SensorID.
     *
     * @param sensorID SensorID object that the value belongs to
     * @return List of InstantTimeValue type objects. List may be empty if no Values are found.
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        List<Value> values = new ArrayList<>();
        for (Value value : valueMap.values()) {
            InstantTimeValue instantTimeValue = (InstantTimeValue) value;
            if (instantTimeValue.getSensorID().equals(sensorID)) {
                values.add(value);
            }
        }
        return values;
    }

    /**
     * Method to find a value belonging to a specific SensorID and where the timestamp1 variable present in persistence
     * is between the start and end variables.
     *
     * @param sensorID SensorID object that the value belongs to
     * @param start    Timestamp object that represents the start of the period
     * @param end      Timestamp object that represents the end of the period
     * @return List of Value type objects. List may be empty if no Values are found.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end) {
        List<Value> values = new ArrayList<>();
        for (Value value : valueMap.values()) {
            InstantTimeValue instantTimeValue = (InstantTimeValue) value;
            if (
                    instantTimeValue.getSensorID().equals(sensorID) &&
                            instantTimeValue.getInstantTimeReading().after(start) &&
                            instantTimeValue.getInstantTimeReading().before(end)
            ) {
                values.add(value);
            }
        }
        return values;
    }

    /**
     * Finds an entity in the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */
    @Override
    public Optional<Value> findEntityByID(ValueID id) {
        return Optional.ofNullable(valueMap.get(id));
    }

    /**
     * Retrieves all entity objects from the repository.
     *
     * @return an iterable collection of entity objects
     */
    @Override
    public Iterable<Value> findAllEntities() {
        return valueMap.values();
    }

    /**
     * Checks whether an entity with the given identifier exists in the repository.
     *
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
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
        List<InstantTimeValue> values = new ArrayList<>();
        for (Value value : valueMap.values()) {
            if (value.getSensorID().equals(sensorID)) {
                values.add((InstantTimeValue) value);
            }
        }
        //Bubble sort to order the list of values by timestamp descending
        for (int i = 0; i < values.size() - 1; i++) {
            for (int j = 0; j < values.size() - i - 1; j++) {
                InstantTimeValue value1 = values.get(j);
                InstantTimeValue value2 = values.get(j + 1);

                Timestamp timestamp1 = value1.getInstantTimeReading();
                Timestamp timestamp2 = value2.getInstantTimeReading();
                Instant instant1 = timestamp1.toInstant();
                Instant instant2 = timestamp2.toInstant();

                if (instant1.compareTo(instant2) < 0) {
                    InstantTimeValue temp = values.get(j);
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
