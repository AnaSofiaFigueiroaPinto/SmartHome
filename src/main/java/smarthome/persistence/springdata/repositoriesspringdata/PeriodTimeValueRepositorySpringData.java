package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.PeriodTimeValueDataModel;

import java.sql.Timestamp;
import java.util.List;

public interface PeriodTimeValueRepositorySpringData extends JpaRepository<PeriodTimeValueDataModel, String> {
    /**
     * Method that returns a list of dataModels from persistence with a matching SensorID String,
     * Timestamp startTime greater or equal to startPeriod and
     * Timestamp endTime less or equal to endPeriod.
     * @param sensorID SensorID the dataModels correspond to.
     * @param startPeriod Timestamp representing the start of the given period.
     * @param endPeriod Timestamp representing the end of the given period.
     * @return List containing Zero or more dataModels retrieved from persistence.
     */
    List<PeriodTimeValueDataModel> findBySensorIDAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual
            (String sensorID, Timestamp startPeriod, Timestamp endPeriod);

    /**
     * Method to return a list of dataModels from persistence with a matching SensorID.
     * @param sensorID SensorID that should be used to query for data.
     * @return List containing Zero or more dataModels based of data retrieved from persistence.
     */
    List<PeriodTimeValueDataModel> findBySensorID (String sensorID);
}
