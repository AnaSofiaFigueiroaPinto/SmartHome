package smarthome.persistence.jpa.repositoriesjpa;

import jakarta.persistence.*;
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
 * ActuatorRepositoryJPAImp class for managing Actuator entities in the database.
 */

public class ActuatorRepositoryJPAImp implements ActuatorRepository {

    /**
     * The factory for creating Actuator objects.
     */
    FactoryActuator factoryActuator;

    /**
     * Entity Manager instance for managing persistence operations.
     */
    EntityManager entityManager;

    /**
     * Constructs a RepositoryActuatorJPAImpl object with the provided FactoryActuator.
     *
     * @param factoryActuator FactoryActuator instance to be used for creating Actuator objects.
     * @param entityManager Entity Manager instance for managing persistence operations.
     */

    public ActuatorRepositoryJPAImp(FactoryActuator factoryActuator, EntityManager entityManager) {
        this.factoryActuator = factoryActuator;
        this.entityManager = entityManager;
    }

    /**
     * Saves the provided Actuator entity to the database.
     *
     * @param actuator Actuator entity to be saved.
     * @return The saved Actuator entity.
     * @throws IllegalArgumentException if the provided Actuator is null.
     */
    @Override
    public Actuator save(Actuator actuator) {
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(actuator);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(actuatorDataModel);
        transaction.commit();
        entityManager.close();

        return actuator;
    }

    /**
     * Retrieves all Actuator entities from the database.
     *
     * @return An Iterable of Actuator entities.
     */
    @Override
    public Iterable<Actuator> findAllEntities() {
        Query query = entityManager.createQuery(
                "SELECT e FROM ActuatorDataModel e");
        List<ActuatorDataModel> listDataModel = query.getResultList();

        MapperActuatorDataModel mapperActuatorDataModel = new MapperActuatorDataModel();
        return mapperActuatorDataModel.toDomainList(factoryActuator, listDataModel);
    }

    /**
     * Retrieves an Actuator entity based on its ActuatorID.
     *
     * @param actuatorID ActuatorID of the Actuator entity to be retrieved.
     * @return An Optional containing the Actuator entity if found, otherwise empty.
     */
    @Override
    public Optional<Actuator> findEntityByID(ActuatorID actuatorID) {
        ActuatorDataModel actuatorDataModel = entityManager.find(ActuatorDataModel.class, actuatorID.toString());

        if (actuatorDataModel == null) {
            return Optional.empty();
        }
        MapperActuatorDataModel mapperActuatorDataModel = new MapperActuatorDataModel();
        Actuator actuatorDomain = mapperActuatorDataModel.toDomain(factoryActuator, actuatorDataModel);
        return Optional.of(actuatorDomain);
    }

    /**
     * Checks if an Actuator entity with the provided ActuatorID exists in the database.
     *
     * @param actuatorID ActuatorID to check existence.
     * @return true if an Actuator entity with the provided ActuatorID exists, otherwise false.
     */
    @Override
    public boolean containsEntityByID(ActuatorID actuatorID) {
        Optional<Actuator> optionalActuator = findEntityByID(actuatorID);
        return optionalActuator.isPresent();
    }

    /**
     * Retrieves a list of Actuator entities based on their ActuatorFunctionalityID.
     *
     * @param actuatorFunctionalityID ActuatorFunctionalityID to filter Actuator entities by.
     * @return A list of Actuator entities with the provided ActuatorFunctionalityID.
     * @throws IllegalArgumentException if the provided ActuatorFunctionalityID is null.
     */
    @Override
    public Iterable<Actuator> findByActuatorFunctionalityID(ActuatorFunctionalityID actuatorFunctionalityID) {
        if (actuatorFunctionalityID == null) {
            throw new IllegalArgumentException();
        }
        Query query = entityManager.createQuery(
                "SELECT e FROM ActuatorDataModel e WHERE e.actuatorFunctionalityID = :actuatorFunctionalityID");
        query.setParameter("actuatorFunctionalityID", actuatorFunctionalityID.toString());
        List<ActuatorDataModel> listDataModel = query.getResultList();
        MapperActuatorDataModel mapperActuatorDataModel = new MapperActuatorDataModel();
        return mapperActuatorDataModel.toDomainList(factoryActuator, listDataModel);
    }

    /**
     * Retrieves all Actuator entities from the database based on their DeviceID.
     *
     * @param deviceID DeviceID to filter Actuator entities by.
     * @return An Iterable of Actuator entities with the provided DeviceID.
     * @throws IllegalArgumentException if the provided DeviceID is null.
     */
    @Override
    public Iterable<Actuator> findByDeviceID(DeviceID deviceID) {
        if(deviceID == null)
            throw new IllegalArgumentException();
        Query query = entityManager.createQuery(
                "SELECT e FROM ActuatorDataModel e WHERE e.deviceID = :deviceID");
        query.setParameter("deviceID", deviceID.toString());
        List<ActuatorDataModel> listDataModel = query.getResultList();
        MapperActuatorDataModel mapperActuatorDataModel = new MapperActuatorDataModel();
        return mapperActuatorDataModel.toDomainList(factoryActuator, listDataModel);
    }

    /**
     * Retrieves all Actuator entities from the database based on their DeviceID and ActuatorFunctionalityID.
     *
     * @param deviceID DeviceID to filter Actuator entities by.
     * @param actuatorFunctionalityID ActuatorFunctionalityID to filter Actuator entities by.
     * @return An Iterable of Actuator entities with the provided DeviceID and ActuatorFunctionalityID.
     * @throws IllegalArgumentException if the provided DeviceID or ActuatorFunctionalityID is null.
     */
    @Override
    public Iterable<Actuator> findByDeviceIDAndActuatorFunctionalityID(DeviceID deviceID, ActuatorFunctionalityID actuatorFunctionalityID) {
        if(deviceID == null || actuatorFunctionalityID == null)
            throw new IllegalArgumentException();
        Query query = entityManager.createQuery(
                "SELECT e FROM ActuatorDataModel e WHERE e.deviceID = :deviceID AND e.actuatorFunctionalityID = :actuatorFunctionalityID");
        query.setParameter("deviceID", deviceID);
        query.setParameter("actuatorFunctionalityID", actuatorFunctionalityID);
        List<ActuatorDataModel> listDataModel = query.getResultList();
        MapperActuatorDataModel mapperActuatorDataModel = new MapperActuatorDataModel();
        return mapperActuatorDataModel.toDomainList(factoryActuator, listDataModel);
    }
}
