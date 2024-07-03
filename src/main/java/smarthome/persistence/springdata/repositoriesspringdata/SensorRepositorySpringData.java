package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.SensorDataModel;

import java.util.List;

public interface SensorRepositorySpringData extends JpaRepository<SensorDataModel, String> {
    /**
     * Method to return a list of dataModels from persistence with a matching DeviceID.
     * @param deviceID DeviceID that should be used to query for data.
     * @return List containing Zero or more dataModels based of data retrieved from persistence.
     */
    List<SensorDataModel> findByDeviceID (String deviceID);

    /**
     * Method to return a list of dataModels from persistence with a matching a Sensor Functionality.
     * @param sensorFunctionalityID SensorFunctionalityID that should be used to query for data.
     * @return List containing Zero or more dataModels based of data retrieved from persistence.
     */
    List<SensorDataModel> findBySensorFunctionalityID (String sensorFunctionalityID);

    /**
     * Method to return a list of dataModels from persistence with a matching a DeviceID and Sensor Functionality.
     * @param deviceID DeviceID that should be used to query for data.
     * @param sensorFunctionalityID SensorFunctionalityID that should be used to query for data.
     * @return List containing Zero or more dataModels based of data retrieved from persistence.
     */
    List<SensorDataModel> findByDeviceIDAndSensorFunctionalityID (String deviceID, String sensorFunctionalityID);
}
