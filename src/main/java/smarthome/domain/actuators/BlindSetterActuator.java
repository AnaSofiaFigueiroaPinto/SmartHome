package smarthome.domain.actuators;

import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;

/**
 * Class representing the BlindSetterActuator entity
 */
public class BlindSetterActuator extends Actuator implements ActuatorSpecificInteger {

    /**
     * Constructor for the BlindSetterActuator object
     *
     * @param actuatorID            The identifier of the actuator
     * @param actuatorProperties      The properties associated with the actuator
     * @param deviceID              The identifier of the device the actuator belongs to
     * @param actuatorFunctionalityID The functionality ID associated with the actuator
     * @throws IllegalArgumentException if the parameters are null or blank
     */
    public BlindSetterActuator(ActuatorID actuatorID, ActuatorFunctionalityID actuatorFunctionalityID, ActuatorProperties actuatorProperties, DeviceID deviceID) {
        super(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceID);
    }

    /**
     * Method to set the specific value of the actuator
     *
     * @param specificInteger the specific value to be set
     * @return true if the specific value is set, false otherwise
     */
    @Override
    public boolean setActuatorSpecificValue(int specificInteger) {
        return specificInteger >= 0 && specificInteger <= 100;
    }
}
