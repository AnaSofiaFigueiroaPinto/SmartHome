package smarthome.persistence.repositoriesmem;

import smarthome.domain.value.PeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Represents the PeriodTimeValueRepository class for persistence in memory.
 */
public class PeriodTimeValueRepositoryMem implements PeriodTimeValueRepository {

    /**
     * The valueMap attribute representing a map of ValueID and Value.
     */

    private final Map<ValueID, Value> valueMap;

    /**
     * The PeriodTimeValueRepositoryMem constructor.
     *
     * @param valueMap The map of ValueID and Value.
     */

    public PeriodTimeValueRepositoryMem(Map<ValueID, Value> valueMap) {
        this.valueMap = valueMap;
    }

    /**
     * Method to find values belonging to a specific SensorID.
     *
     * @param sensorID SensorID object that the values belongs to
     * @return List of PeriodTimeValue type objects.
     */

    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        List<Value> values = new ArrayList<>();
        for (Value value : valueMap.values()) {
            PeriodTimeValue periodTimeValue = (PeriodTimeValue) value;
            if (periodTimeValue.getSensorID().equals(sensorID)) {
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
     * @return List of Value type objects.
     */
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end) {
        List<Value> values = new ArrayList<>();
        for (Value value : valueMap.values()) {
            PeriodTimeValue periodTimeValue = (PeriodTimeValue) value;
            if (
                            periodTimeValue.getSensorID().equals(sensorID) &&
                            periodTimeValue.getStartTimeReading().after(start) &&
                            periodTimeValue.getEndTimeReading().before(end)
            ) {
                values.add(value);
            }
        }
        return values;
    }

    /**
     * Finds a value in the repository by its unique identifier.
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
     * Method to check if the repository contains a specific entity.
     *
     * @param id The unique identifier of the entity to find.
     * @return boolean value indicating if the entity is present in the repository.
     */
    @Override
    public boolean containsEntityByID(ValueID id) {
        return valueMap.containsKey(id);
    }


}
