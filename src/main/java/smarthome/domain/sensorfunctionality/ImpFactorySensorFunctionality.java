package smarthome.domain.sensorfunctionality;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.SensorFunctionalityID;
@Component
public class ImpFactorySensorFunctionality implements FactorySensorFunctionality {

    /**
     * Creates a new SensorFunctionality object with the specified ID.
     *
     * @param sensorFunctionalityID the ID string representing the sensor functionality
     * @return a new instance of {@code SensorFunctionality} with the specified ID
     */

    public SensorFunctionality createSensorFunctionality(SensorFunctionalityID sensorFunctionalityID) {
        try {
            return new SensorFunctionality(sensorFunctionalityID);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

}
