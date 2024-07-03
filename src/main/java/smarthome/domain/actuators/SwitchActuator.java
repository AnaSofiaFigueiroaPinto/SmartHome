package smarthome.domain.actuators;

import smarthome.domain.valueobjects.*;

/**
 * Represents a Switch Actuator, which is an actuator that can be switched on or off.
 */
public class SwitchActuator extends Actuator implements ActuatorSpecificInteger {
    /**
     * The status of the switch actuator.
     */
    private SwitchActuatorStatus status;

    /**
     * Constructor for the SwitchActuator object
     *
     * @param actuatorID            The identifier of the actuator
     * @param actuatorProperties      The properties associated with the actuator
     * @param deviceID              The identifier of the device the actuator belongs to
     * @param actuatorFunctionalityID The functionality ID associated with the actuator
     * @throws IllegalArgumentException if the parameters are null or blank
     */
    public SwitchActuator(ActuatorID actuatorID, ActuatorFunctionalityID actuatorFunctionalityID, ActuatorProperties actuatorProperties, DeviceID deviceID) {
        super(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceID);
        this.status = SwitchActuatorStatus.ON;
    }

    /**
     * Sets the specific value for the switch actuator. Accepts 1 for ON and 0 for OFF.
     *
     * @param specificValue The specific value to set (1 for ON, 0 for OFF).
     * @return true if the specific value was successfully set, false otherwise.
     */
    @Override
    public boolean setActuatorSpecificValue(int specificValue) {
        if (specificValue == 1) {
            this.status = SwitchActuatorStatus.ON;
            return true;
        }
        if (specificValue == 0) {
            this.status = SwitchActuatorStatus.OFF;
            return true;
        }
        return false;
    }

    /**
     * Retrieves the status set for the actuator.
     *
     * @return The Actuator status object.
     */
    public SwitchActuatorStatus getStatus() {
        return status;
    }
}

