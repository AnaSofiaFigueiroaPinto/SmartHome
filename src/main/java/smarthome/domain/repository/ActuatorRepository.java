package smarthome.domain.repository;

import smarthome.ddd.Repository;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.DeviceID;

/**
 * Interface for a repository managing Actuator entities using JPA (Java Persistence API).
 */
public interface ActuatorRepository extends Repository<ActuatorID, Actuator> {

    /**
     * Saves an Actuator entity.
     *
     * @param actuator The Actuator entity to save.
     * @return The saved Actuator entity.
     */
    Actuator save(Actuator actuator);

    /**
     * Retrieves a list of Actuator entities with a specific ActuatorFunctionalityID.
     *
     * @param actuatorFunctionalityID The ActuatorFunctionalityID to search for.
     * @return A list of Actuator entities with the specified ActuatorFunctionalityID.
     */
    Iterable<Actuator> findByActuatorFunctionalityID(ActuatorFunctionalityID actuatorFunctionalityID);

    /**
     * Retrieves a list of Actuator entities of a specific DeviceID.
     *
     * @param deviceID The DeviceID to search for.
     * @return A list of Actuator entities with the specified DeviceID.
     */
    Iterable<Actuator> findByDeviceID(DeviceID deviceID);

    Iterable<Actuator> findByDeviceIDAndActuatorFunctionalityID(DeviceID deviceID, ActuatorFunctionalityID actuatorFunctionalityID);
}
