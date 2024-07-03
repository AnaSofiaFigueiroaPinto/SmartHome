package smarthome.domain.repository;

import smarthome.ddd.Repository;
import smarthome.domain.sensorfunctionality.SensorFunctionality;
import smarthome.domain.valueobjects.SensorFunctionalityID;

/**
 * Represents the SensorFunctionalityRepository interface for persistence.
 */
public interface SensorFunctionalityRepository extends Repository<SensorFunctionalityID, SensorFunctionality> {

    /**
     * Retrieve the class name for the sensor functionality ID.
     * @param sensorFunctionalityID The sensor functionality ID.
     * @return A string representing the class name.
     */
    String getClassNameForSensorFunctionalityID (SensorFunctionalityID sensorFunctionalityID);

    /**
     * Retrieve the service method to call for the sensor functionality ID.
     * @param sensorFunctionalityID The sensor functionality ID.
     * @return A string representing the service method to call.
     */
    String getServiceMethodToCallForSensorFunctionalityID(SensorFunctionalityID sensorFunctionalityID);

}
