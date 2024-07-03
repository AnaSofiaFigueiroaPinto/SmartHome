package smarthome.persistence.repositoriesmem;

import smarthome.domain.actuators.Actuator;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.DeviceID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An implementation of a repository for managing Actuator entities.
 */
public class ActuatorRepositoryMem implements ActuatorRepository
{
    /**
     * Map to store Actuator entities, keyed by their ActuatorID.
     */
    private final Map<ActuatorID, Actuator> actuatorData;

    /**
     * Constructor for ActuatorRepository object. Currently stands in this way as there doesn't exist a database yet.
     */
    public ActuatorRepositoryMem(Map<ActuatorID, Actuator> actuatorData)
    {
        this.actuatorData = actuatorData;
    }

    /**
     * Saves the given entity in the repository.
     *
     * @param entity The entity to save.
     * @return the saved entity.
     */
    @Override
    public Actuator save(Actuator entity)
    {
        actuatorData.put(entity.identity(), entity);
        return entity;
    }

    /**
     * Retrieves all entities from the repository.
     *
     * @return an iterable containing all entities in the repository.
     */
    @Override
    public Iterable<Actuator> findAllEntities()
    {
        return actuatorData.values();
    }

    /**
     * Finds an entity in the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to find.
     * @return an optional containing the found entity, or empty if not found.
     */
    @Override
    public Optional<Actuator> findEntityByID(ActuatorID id)
    {
        return Optional.ofNullable(actuatorData.get(id));
    }

    /**
     * Checks whether an entity with the given identifier exists in the repository.
     *
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
     */
    @Override
    public boolean containsEntityByID(ActuatorID id)
    {
        return actuatorData.containsKey(id);
    }

    /**
     * Get the map containing Actuator entities.
     *
     * @return The map containing Actuator entities, keyed by their ActuatorID.
     */
    public Map<ActuatorID, Actuator> getActuatorData()
    {
        return actuatorData;
    }


    /**
     * Retrieves all Actuator entities with a certain ActuatorFunctionalityID.
     *
     * @param actuatorFunctionalityID The ActuatorFunctionalityID to search for.
     * @return a list of Actuator entities with the given ActuatorFunctionalityID.
     * @throws IllegalArgumentException if the provided ActuatorFunctionalityID is null.
     */
    @Override
    public Iterable<Actuator> findByActuatorFunctionalityID(ActuatorFunctionalityID actuatorFunctionalityID)
    { if(actuatorFunctionalityID == null) {
        throw new NullPointerException("ActuatorFunctionalityID cannot be null");
    }
        List<Actuator> actuatorWithCertainFunctionality = new ArrayList<>();
        for (Actuator actuator : actuatorData.values()) {
            if (actuator.getActuatorFunctionalityID().equals(actuatorFunctionalityID)) {
                actuatorWithCertainFunctionality.add(actuator);
            }
        }
        return actuatorWithCertainFunctionality;
    }

    /**
     * Retrieves all Actuator entities with a certain DeviceID.
     *
     * @param deviceID The DeviceID to search for.
     * @return a list of Actuator entities with the given DeviceID.
     * @throws IllegalArgumentException if the provided DeviceID is null.
     */
    @Override
    public Iterable<Actuator> findByDeviceID(DeviceID deviceID) {
        if (deviceID == null) {
            throw new IllegalArgumentException();
        }
        List<Actuator> listActuators = new ArrayList<>();
        for (Actuator actuator : actuatorData.values()) {
            if (actuator.getDeviceName().equals(deviceID)) {
                listActuators.add(actuator);
            }
        }
        return listActuators;
    }

    /**
     * Retrieves all Actuator entities with a certain DeviceID and ActuatorFunctionalityID.
     *
     * @param deviceID The DeviceID to search for.
     * @param actuatorFunctionalityID The ActuatorFunctionalityID to search for.
     * @return an iterable of Actuator entities with the given DeviceID and ActuatorFunctionalityID.
     */
    @Override
    public Iterable<Actuator> findByDeviceIDAndActuatorFunctionalityID(DeviceID deviceID, ActuatorFunctionalityID actuatorFunctionalityID) {
        List<Actuator> listActuatorsOfDevice = new ArrayList<>();
        for (Actuator actuator : actuatorData.values()) {
            if (actuator.getDeviceName().equals(deviceID) && actuator.getActuatorFunctionalityID().equals(actuatorFunctionalityID)) {
                listActuatorsOfDevice.add(actuator);
            }
        }
        return listActuatorsOfDevice;
    }

}
