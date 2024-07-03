package smarthome.domain.actuatorfunctionality;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;

@Component
public class ImpFactoryActuatorFunctionality implements FactoryActuatorFunctionality {
    /**
     * Creates a new ActuatorFunctionality object with the specified ID
     *
     * @param actuatorFunctionalityID the ID string representing the actuator functionality
     * @return a new instance of {@code ActuatorFunctionality} with the specified ID
     */

    public ActuatorFunctionality createActuatorFunctionality(ActuatorFunctionalityID actuatorFunctionalityID) {
        try {
            return new ActuatorFunctionality(actuatorFunctionalityID);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
