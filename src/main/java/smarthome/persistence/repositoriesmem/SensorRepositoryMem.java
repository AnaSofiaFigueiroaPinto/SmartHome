package smarthome.persistence.repositoriesmem;

import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An implementation of a repository for managing Sensor entities.
 */
public class SensorRepositoryMem implements SensorRepository
{
    /**
     * Map of SensorID and Sensor objects.
     */
    private Map<SensorID, Sensor> sensorData;

    /**
     * Constructor for SensorRepository object. Currently stands in this way as there doesn't exist a database yet.
     * @param sensorData Map of SensorID and Sensor objects.
     */
    public SensorRepositoryMem(Map<SensorID, Sensor> sensorData)
    {
        this.sensorData = sensorData;
    }

    /**
     * Saves the given entity in the repository.
     *
     * @param entity The entity to save.
     * @return The saved  entity.
     */
    @Override
    public Sensor save(Sensor entity)
    {
        sensorData.put(entity.identity(), entity);
        return entity;
    }

    /**
     * Retrieves all entities from the repository.
     *
     * @return An Iterable containing all entities in the repository.
     */
    @Override
    public Iterable<Sensor> findAllEntities()
    {
        return sensorData.values();
    }

    /**
     * Finds an entity in the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */
    @Override
    public Optional<Sensor> findEntityByID(SensorID id) {
        return Optional.ofNullable(sensorData.get(id));
    }

    /**
     * Checks whether an entity with the given identifier exists in the repository.
     *
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
     */
    @Override
    public boolean containsEntityByID(SensorID id) {
        return sensorData.containsKey(id);
    }

    /**
     * Method that finds all the Sensors of a Device, taking the DeviceID as a parameter.
     * @param deviceID The ID of the device to find the sensors for.
     * @return An Iterable containing all sensors with the given device ID.
     */
    @Override
    public Iterable<Sensor> findByDeviceID(DeviceID deviceID)
    {
        List<Sensor> sensorsOfDevice = new ArrayList<>();
        for (Sensor sensor : sensorData.values()) {
            if (sensor.getDeviceID().equals(deviceID)) {
                sensorsOfDevice.add(sensor);
            }
        }
        return sensorsOfDevice;
    }

    /**
     * Method that finds all the Sensors of a SensorFunctionality, taking the SensorFunctionalityID as a parameter.
     * @param sensorFunctionalityID The sensorFunctionalityID to find the sensors for.
     * @return An Iterable containing all sensors with the given device ID.
     */
    @Override
    public Iterable<Sensor> findBySensorFunctionality(SensorFunctionalityID sensorFunctionalityID)
    {
        List<Sensor> sensorsOfDevice = new ArrayList<>();
        for (Sensor sensor : sensorData.values()) {
            if (sensor.getSensorFunctionalityID().equals(sensorFunctionalityID)) {
                sensorsOfDevice.add(sensor);
            }
        }
        return sensorsOfDevice;
    }

    /**
     * Method that finds all the Sensors of a device and SensorFunctionality, taking the DeviceID and SensorFunctionalityID as a parameters.
     * @param deviceID The ID of the device to find the sensors for.
     * @param sensorFunctionalityID The ID of the sensorFunctionality to find the sensors for.
     * @return An Iterable containing all sensors with the given device ID.
     */
    @Override
    public Iterable<Sensor> findByDeviceIDAndSensorFunctionality(DeviceID deviceID, SensorFunctionalityID sensorFunctionalityID)
    {
        List<Sensor> sensorsOfDevice = new ArrayList<>();
        for (Sensor sensor : sensorData.values()) {
            if (sensor.getDeviceID().equals(deviceID) && sensor.getSensorFunctionalityID().equals(sensorFunctionalityID)) {
                sensorsOfDevice.add(sensor);
            }
        }
        return sensorsOfDevice;
    }
}
