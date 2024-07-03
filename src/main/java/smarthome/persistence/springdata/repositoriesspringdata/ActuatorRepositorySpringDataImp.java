package smarthome.persistence.springdata.repositoriesspringdata;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.FactoryActuator;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.persistence.jpa.datamodel.ActuatorDataModel;
import smarthome.persistence.jpa.datamodel.MapperActuatorDataModel;

import java.util.List;
import java.util.Optional;
/**
 * ActuatorRepositorySpringData class for managing Actuator entities in the database.
 */
@Repository
@Profile("!test")
public class ActuatorRepositorySpringDataImp implements ActuatorRepository
{

    /**
     * The repository for accessing actuator data managed by Spring Data.
     */
    ActuatorRepositorySpringData actuatorRepositorySpringDataInt;
    /**
     * Factory for creating Actuator entities.
     */
    FactoryActuator factoryActuator;

    /**
     * Mapper for ActuatorDataModel
     */
    MapperActuatorDataModel mapperActuatorDataModel;

    /**
     * Constructs a new ImpActuatorRepositorySpringData with the specified
     * RepositoryActuatorSpringData interface and FactoryActuator.
     *
     * @param actuatorRepositorySpringDataInt The Spring Data repository interface for Actuator entities.
     * @param factoryActuator              The factory for creating Actuator entities.
     */
    public ActuatorRepositorySpringDataImp(ActuatorRepositorySpringData actuatorRepositorySpringDataInt,
                                           FactoryActuator factoryActuator,
                                           MapperActuatorDataModel mapperActuatorDataModel)
    {
        this.actuatorRepositorySpringDataInt = actuatorRepositorySpringDataInt;
        this.factoryActuator = factoryActuator;
        this.mapperActuatorDataModel = mapperActuatorDataModel;
    }

    /**
     * Saves the provided Actuator entity to the repository.
     *
     * @param actuator Actuator entity to be saved.
     * @return The saved Actuator entity.
     * @throws IllegalArgumentException if the provided Actuator is null.
     */
    @Override
    public Actuator save(Actuator actuator)
    {
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(actuator);
        this.actuatorRepositorySpringDataInt.save(actuatorDataModel);
        return actuator;
    }

    /**
     * Retrieves all Actuator entities from the repository.
     *
     * @return An Iterable of Actuator entities.
     */
    @Override
    public Iterable<Actuator> findAllEntities()
    {
        List<ActuatorDataModel> listActuatorDataModelSaved = this.actuatorRepositorySpringDataInt.findAll();
        return mapperActuatorDataModel.toDomainList(factoryActuator, listActuatorDataModelSaved);
    }

    /**
     * Retrieves the Actuator entity with the specified identity.
     *
     * @param actuatorID The identity of the Actuator entity to retrieve.
     * @return An Optional with the Actuator entity if found, an empty Optional otherwise.
     */
    @Override
    public Optional<Actuator> findEntityByID(ActuatorID actuatorID)
    {
        Optional<ActuatorDataModel> actuatorDataModel = this.actuatorRepositorySpringDataInt.findById(actuatorID.toString());

        if(actuatorDataModel.isPresent())
        {
            Actuator actuator = mapperActuatorDataModel.toDomain(factoryActuator, actuatorDataModel.get());
            return Optional.of(actuator);
        }
        else
        {
            return Optional.empty();
        }
    }

    /**
     * Checks if the Actuator entity with the specified identity exists in the repository.
     *
     * @param actuatorID The identity of the Actuator entity to check.
     * @return True if the Actuator entity exists, false otherwise.
     */
    @Override
    public boolean containsEntityByID(ActuatorID actuatorID)
    {
        return this.actuatorRepositorySpringDataInt.existsById(actuatorID.toString());
    }

    /**
     * Retrieves all Actuator entities with the specified ActuatorFunctionalityID.
     *
     * @param actuatorFunctionalityID The ActuatorFunctionalityID to search for.
     * @return An Iterable of Actuator entities with the specified ActuatorFunctionalityID.
     */
    @Override
    public Iterable<Actuator> findByActuatorFunctionalityID(ActuatorFunctionalityID actuatorFunctionalityID) {
        Iterable<ActuatorDataModel> actuatorDataModel = this.actuatorRepositorySpringDataInt.findByActuatorFunctionalityID(actuatorFunctionalityID.toString());
        return mapperActuatorDataModel.toDomainList(factoryActuator, actuatorDataModel);
    }

    /**
     * Retrieves all Actuator entities with the specified DeviceID.
     *
     * @param deviceID The DeviceID to search for.
     * @return An Iterable of Actuator entities with the specified DeviceID.
     */
    @Override
    public Iterable<Actuator> findByDeviceID(DeviceID deviceID) {
        Iterable<ActuatorDataModel> actuatorDataModelIterable = this.actuatorRepositorySpringDataInt.findByDeviceID(deviceID.toString());
        return mapperActuatorDataModel.toDomainList(factoryActuator, actuatorDataModelIterable);
    }

    /**
     * Retrieves all Actuator entities with the specified DeviceID and ActuatorFunctionalityID.
     *
     * @param deviceID The DeviceID to search for.
     * @param actuatorFunctionalityID The ActuatorFunctionalityID to search for.
     * @return An Iterable of Actuator entities with the specified DeviceID and ActuatorFunctionalityID.
     */
    @Override
    public Iterable<Actuator> findByDeviceIDAndActuatorFunctionalityID(DeviceID deviceID, ActuatorFunctionalityID actuatorFunctionalityID) {
        Iterable<ActuatorDataModel> actuatorDataModelIterable = this.actuatorRepositorySpringDataInt.findByDeviceIDAndActuatorFunctionalityID(deviceID.toString(), actuatorFunctionalityID.toString());
        return mapperActuatorDataModel.toDomainList(factoryActuator, actuatorDataModelIterable);
    }
}
