package smarthome.domain.repository;

import smarthome.ddd.Repository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

/**
 * Represents an interface {@code SensorRepository} for persistence in JPA.
 */
public interface SensorRepository extends Repository<SensorID, Sensor> {
    /**
     * Saves the given entity in the repository.
     *
     * @param sensor The entity to save.
     * @return The saved entity.
     */
    Sensor save(Sensor sensor);

    /**
     * Retrieves a list of Sensor entities by their device ID.
     * @param deviceID The device ID to search for.
     * @return An Iterable containing all sensors with the given device ID.
     */
    Iterable<Sensor> findByDeviceID(DeviceID deviceID);

    /**
     * Method that finds all the Sensors of a SensorFunctionality, taking the SensorFunctionalityID as a parameter.
     * @param sensorFunctionalityID The sensorFunctionalityID to find the sensors for.
     * @return An Iterable containing all sensors with the given device ID.
     */
    Iterable<Sensor> findBySensorFunctionality(SensorFunctionalityID sensorFunctionalityID);

    /**
     * Method that finds all the Sensors of a Device and SensorFunctionality, taking the DeviceID and SensorFunctionalityID as parameters.
     * @param deviceID The device ID to search for.
     * @param sensorFunctionalityID The sensorFunctionalityID to find the sensors for.
     * @return An Iterable containing all sensors with the given device ID.
     */
    Iterable<Sensor> findByDeviceIDAndSensorFunctionality(DeviceID deviceID, SensorFunctionalityID sensorFunctionalityID);
}
