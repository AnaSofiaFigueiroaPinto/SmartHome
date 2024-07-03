package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.ActuatorDataModel;

/**
 * ActuatorRepositorySpringData interface for managing Actuator entities in the database.
 */
public interface ActuatorRepositorySpringData extends JpaRepository<ActuatorDataModel, String> {

    /**
     * Finds all ActuatorDataModel entities with the specified ActuatorFunctionalityID.
     *
     * @param actuatorFunctionalityID The ActuatorFunctionalityID to search for.
     * @return A list of ActuatorDataModel entities with the specified ActuatorFunctionalityID.
     */
    Iterable<ActuatorDataModel> findByActuatorFunctionalityID(String actuatorFunctionalityID);

    /**
     * Finds all ActuatorDataModel entities with the specified DeviceID.
     *
     * @param deviceID The DeviceID to search for.
     * @return A list of ActuatorDataModel entities with the specified DeviceID.
     */
    Iterable<ActuatorDataModel> findByDeviceID(String deviceID);

    /**
     * Finds all ActuatorDataModel entities with the specified DeviceID and ActuatorFunctionalityID.
     *
     * @param deviceID The DeviceID to search for.
     * @param actuatorFunctionalityID The ActuatorFunctionalityID to search for.
     * @return A list of ActuatorDataModel entities with the specified DeviceID and ActuatorFunctionalityID.
     */
    Iterable <ActuatorDataModel> findByDeviceIDAndActuatorFunctionalityID(String deviceID, String actuatorFunctionalityID);

}
