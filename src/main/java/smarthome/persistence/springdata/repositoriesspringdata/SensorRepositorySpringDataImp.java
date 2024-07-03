package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.persistence.jpa.datamodel.MapperSensorDataModel;
import smarthome.persistence.jpa.datamodel.SensorDataModel;

import java.util.Optional;

/**
 * Implementation of the SensorRepository interface using Spring Data JPA.
 */
@Repository
@Profile("!test")
public class SensorRepositorySpringDataImp implements SensorRepository
{

    /**
     * The repository for accessing sensor data managed by Spring Data.
     */
    SensorRepositorySpringData sensorRepositorySpringData;

    /**
     * Factory for creating Sensor entities.
     */
    FactorySensor factorySensor;

    /**
     * Mapper for Sensor Data Model.
     */
    MapperSensorDataModel mapperSensorDataModel;

    /**
     * Constructs a new ImpRepositorySensorSpringData with the
     * specified RepositorySensorSpringData and FactorySensor.
     *
     * @param sensorRepositorySpringData The Spring Data repository for Sensor entities.
     * @param factorySensor              The factory for creating Sensor entities.
     * @param mapperSensorDataModel      Mapper that converts Sensor entities to data models.
     */
    public SensorRepositorySpringDataImp(SensorRepositorySpringData sensorRepositorySpringData,
                                         FactorySensor factorySensor, MapperSensorDataModel mapperSensorDataModel)
    {
        this.sensorRepositorySpringData = sensorRepositorySpringData;
        this.factorySensor = factorySensor;
        this.mapperSensorDataModel = mapperSensorDataModel;
    }

    /**
     * Saves a Sensor entity in the repository.
     *
     * @param sensor The Sensor entity to save.
     * @return The saved Sensor entity.
     */
    @Override
    public Sensor save(Sensor sensor)
    {
        if (sensor == null) {
            throw new IllegalArgumentException();
        }
        SensorDataModel sensorDataModel = new SensorDataModel(sensor);
        this.sensorRepositorySpringData.save(sensorDataModel);

        return sensor;
    }

    /**
     * Retrieves all Sensor entities from the repository.
     *
     * @return An Iterable containing all Sensor entities.
     */
    @Override
    public Iterable<Sensor> findAllEntities()
    {
        Iterable<SensorDataModel> lstSensorDataModel =
                this.sensorRepositorySpringData.findAll();

        return mapperSensorDataModel.toDomainList(factorySensor, lstSensorDataModel);
    }

    /**
     * Retrieves a Sensor entity by its ID from the repository.
     *
     * @param id The ID of the Sensor entity to retrieve.
     * @return An Optional containing the retrieved Sensor entity, or empty if not found.
     */
    @Override
    public Optional<Sensor> findEntityByID(SensorID id)
    {
        Optional<SensorDataModel> optSensorDataModelSaved =
                this.sensorRepositorySpringData.findById(id.toString());

        if (optSensorDataModelSaved.isPresent()) {
            Sensor sensorDomain = mapperSensorDataModel.toDomain(factorySensor, optSensorDataModelSaved.get());
            return Optional.of(sensorDomain);
        } else
            return Optional.empty();
    }

    /**
     * Checks if a Sensor entity with the specified ID exists in the repository.
     *
     * @param id The ID of the Sensor entity to check.
     * @return True if the Sensor entity exists, false otherwise.
     */
    @Override
    public boolean containsEntityByID(SensorID id)
    {
        return sensorRepositorySpringData.existsById(id.toString());
    }

    /**
     * Method that finds all the Sensors of a Device, taking the DeviceID as a parameter.
     * @param deviceID The ID of the device to find the sensors for.
     * @return An Iterable containing all sensors with the given device ID.
     */
    @Override
    public Iterable<Sensor> findByDeviceID(DeviceID deviceID)
    {
        Iterable<SensorDataModel> lstSensorDataModel =
                this.sensorRepositorySpringData.findByDeviceID(deviceID.toString());

        return mapperSensorDataModel.toDomainList(factorySensor, lstSensorDataModel);
    }

    /**
     * Method that finds all the Sensors of a SensorFunctionality, taking the SensorFunctionalityID as a parameter.
     * @param sensorFunctionalityID The ID of the sensorFunctionality to find the sensors for.
     * @return An Iterable containing all sensors with the given device ID.
     */
    @Override
    public Iterable<Sensor> findBySensorFunctionality(SensorFunctionalityID sensorFunctionalityID)
    {
        Iterable<SensorDataModel> lstSensorDataModel =
                this.sensorRepositorySpringData.findBySensorFunctionalityID(sensorFunctionalityID.toString());

        return mapperSensorDataModel.toDomainList(factorySensor, lstSensorDataModel);
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
            Iterable<SensorDataModel> lstSensorDataModel =
                    this.sensorRepositorySpringData.findByDeviceIDAndSensorFunctionalityID
                            (deviceID.toString(), sensorFunctionalityID.toString());

            return mapperSensorDataModel.toDomainList(factorySensor, lstSensorDataModel);
        }
}

