package smarthome.domain.repository;

import smarthome.ddd.Repository;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.util.List;

/**
 * Represents the ValueRepository interface for persistence.
 */
public interface ValueRepository extends Repository<ValueID, Value> {
    /**
     * Method to find a value belonging to a specific SensorID.
     * @param sensorID SensorID object that the value belongs to
     * @return List of Value type objects. List may be empty if no Values are found.
     */
    List<Value> findBySensorId (SensorID sensorID);

    /**
     * Method to find a value belonging to a specific SensorID and where the value present in persistence
     * is between the start and end variables.
     * @param sensorID  SensorID object that the value belongs to.
     * @param start     Timestamp object that represents the start of the period.
     * @param end       Timestamp object that represents the end of the period.
     * @return  List of Value type objects. List may be empty if no Values are found.
     */
    List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end);

}
