package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.InstantTimeValueDataModel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface InstantTimeValueRepositorySpringData extends JpaRepository<InstantTimeValueDataModel, String> {
    /**
     * Method that returns a list of dataModels from persistence with a matching SensorID String, and the Timestamp InstantTime,
     * between the given start and endPeriod Timestamps.
     * @param sensorID SensorID the dataModels correspond to.
     * @param startPeriod Timestamp representing the start of the given period.
     * @param endPeriod Timestamp representing the end of the given period.
     * @return List containing Zero or more dataModels retrieved from persistence.
     */
    List<InstantTimeValueDataModel> findBySensorIDAndAndInstantTimeBetween
            (String sensorID, Timestamp startPeriod, Timestamp endPeriod);

    /**
     * Method to return a list of dataModels from persistence with a matching SensorID.
     * @param sensorID SensorID that should be used to query for data.
     * @return List containing Zero or more dataModels based of data retrieved from persistence.
     */
    List<InstantTimeValueDataModel> findBySensorID (String sensorID);

    /**
     * Method to return the last dataModel recorded from persistence with a matching SensorID.
     * @param sensorID SensorID that should be used to query for data.
     * @return the object dataModel retrieved from persistence.
     */
    Optional<InstantTimeValueDataModel> findFirstBySensorIDOrderByInstantTimeDesc (String sensorID);

}
