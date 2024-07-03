package smarthome.persistence.jpa.datamodel;

import smarthome.domain.sensorfunctionality.FactorySensorFunctionality;
import smarthome.domain.sensorfunctionality.SensorFunctionality;
import smarthome.domain.valueobjects.SensorFunctionalityID;

/**
 * Mapper class to convert Strings to SensorFunctionality objects.
 */
public class MapperSensorFunctionalityPersistence {
    /**
     * Factory to create SensorFunctionality objects.
     */
    private FactorySensorFunctionality factorySensorFunctionality;

    /**
     * Constructor of MapperSensorFunctionalityPersistence.
     * @param factorySensorFunctionality factory to create SensorFunctionality objects.
     */
    public MapperSensorFunctionalityPersistence (FactorySensorFunctionality factorySensorFunctionality) {
        this.factorySensorFunctionality = factorySensorFunctionality;
    }

    /**
     * Method to convert a String to a SensorFunctionality object.
     * @param sensorFunctionalityAsString String to be converted.
     * @return a SensorFunctionality object.
     */
    public SensorFunctionality toDomain (String sensorFunctionalityAsString) {
        try {
            SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorFunctionalityAsString);
            return factorySensorFunctionality.createSensorFunctionality(sensorFunctionalityID);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
