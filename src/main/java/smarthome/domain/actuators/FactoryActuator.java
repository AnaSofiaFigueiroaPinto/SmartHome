package smarthome.domain.actuators;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;

/**
 * Factory class that creates Actuator objects based on the ActuatorFunctionalityID
 */
@Component
public class FactoryActuator {

    public Actuator createActuator(ActuatorID actuatorID,
                                   ActuatorFunctionalityID actuatorFunctionalityID,
                                   ActuatorProperties actuatorProperties,
                                   DeviceID deviceID,
                                   String actuatorClass) {
        try {
            return (Actuator) Class.forName(actuatorClass).getDeclaredConstructor(
                    ActuatorID.class,
                    ActuatorFunctionalityID.class,
                    ActuatorProperties.class,
                    DeviceID.class).newInstance(
                    actuatorID,
                    actuatorFunctionalityID,
                    actuatorProperties,
                    deviceID
            );
        } catch (Exception exception) {
            return null;
        }
    }
}
