package smarthome.persistence.jpa.repositoriesjpa;

import jakarta.persistence.*;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.persistence.jpa.datamodel.MapperSensorDataModel;
import smarthome.persistence.jpa.datamodel.SensorDataModel;
import smarthome.util.EntityUpdater;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a {@code SensorRepositoryJPAImp} for persistence in JPA.
 */
public class SensorRepositoryJPAImp implements SensorRepository
{
    /**
     * The factory for creating sensor objects.
     */
    FactorySensor factorySensor;

    /**
     * Entity Manager instance for managing persistence operations.
     */
    EntityManager entityManager;

    /**
     * Constructs a new SensorRepositoryJPAImp with the specified factory.
     *
     * @param factorySensor the factory for creating sensor objects.
     * @param entityManager Entity Manager instance for managing persistence operations.
     */
    public SensorRepositoryJPAImp(FactorySensor factorySensor, EntityManager entityManager)
    {
        this.factorySensor = factorySensor;
        this.entityManager = entityManager;
    }

    /**
     * Saves or updates a sensor entity to the database.
     *
     * @param sensor The entity to save or update.
     * @return The saved or updated entity.
     * @throws IllegalArgumentException if the sensor object is null.
     */
    @Override
    public Sensor save(Sensor sensor)
    {
        if (sensor == null)
            throw new IllegalArgumentException();

        SensorDataModel sensorDataModel = new SensorDataModel(sensor);

        EntityUpdater entityUpdater = new EntityUpdater();
        if (entityUpdater.persistEntity(sensorDataModel, entityManager)) {
            return sensor;
        }
        return null;
    }

    /**
     * Retrieves all sensor objects from the database.
     *
     * @return An Iterable containing all sensor objects in the database.
     */
    @Override
    public Iterable<Sensor> findAllEntities()
    {
        Query query = Objects.requireNonNull(entityManager).createQuery("SELECT e FROM SensorDataModel e");
        List<SensorDataModel> listDataModel = query.getResultList();

        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();
        return mapperSensorDataModel.toDomainList(factorySensor, listDataModel);
    }

    /**
     * Finds a sensor object in the database by its unique identifier.
     *
     * @param sensorID The unique identifier of the entity to find.
     * @return An Optional containing the found sensor, or empty if not found.
     * @throws NullPointerException if the EntityManager is null.
     */
    @Override
    public Optional<Sensor> findEntityByID(SensorID sensorID)
    {
        SensorDataModel sensorDataModel = Objects.requireNonNull(entityManager).find(SensorDataModel.class, sensorID.toString());
        if (sensorDataModel == null)
            return Optional.empty();

        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();
        Sensor sensor = mapperSensorDataModel.toDomain(factorySensor, sensorDataModel);
        return Optional.of(sensor);
    }

    /**
     * Checks whether a sensor object with the given identity exists in the database.
     *
     * @param sensorID the identity of the sensor object to check.
     * @return true if the sensor object exists in the database, false otherwise.
     */
    @Override
    public boolean containsEntityByID(SensorID sensorID)
    {
        Optional<Sensor> sensor = findEntityByID(sensorID);
        return sensor.isPresent();
    }

    /**
     * Method that finds all the Sensors of a Device, taking the DeviceID as a parameter.
     * @param deviceID The ID of the device to find the sensors for.
     *@return An Iterable containing all sensors with the given device ID.
     */
    @Override
    public Iterable<Sensor> findByDeviceID(DeviceID deviceID)
    {
        Query query = Objects.requireNonNull(entityManager).createQuery("SELECT e FROM SensorDataModel e WHERE e.deviceID = :deviceID");
        query.setParameter("deviceID", deviceID);
        List<SensorDataModel> listDataModel = query.getResultList();

        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();
        return mapperSensorDataModel.toDomainList(factorySensor, listDataModel);
    }

    /**
     * Method that finds all the Sensors of a SensorFunctionality, taking the SensorFunctionalityID as a parameter.
     * @param sensorFunctionalityID The sensorFunctionalityID to find the sensors for.
     * @return An Iterable containing all sensors with the given device ID.
     */
    @Override
    public Iterable<Sensor> findBySensorFunctionality(SensorFunctionalityID sensorFunctionalityID)
    {
        Query query = Objects.requireNonNull(entityManager).createQuery("SELECT e FROM SensorDataModel e WHERE e.sensorFunctionalityID = :sensorFunctionalityID");
        query.setParameter("sensorFunctionalityID", sensorFunctionalityID);
        List<SensorDataModel> listDataModel = query.getResultList();

        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();
        return mapperSensorDataModel.toDomainList(factorySensor, listDataModel);
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
        Query query = Objects.requireNonNull(entityManager)
                .createQuery("SELECT e FROM SensorDataModel e WHERE e.deviceID = :deviceID AND e.sensorFunctionalityID = :sensorFunctionalityID");
        query.setParameter("deviceID", deviceID);
        query.setParameter("sensorFunctionalityID", sensorFunctionalityID);
        List<SensorDataModel> listDataModel = query.getResultList();

        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();
        return mapperSensorDataModel.toDomainList(factorySensor, listDataModel);
    }
}
