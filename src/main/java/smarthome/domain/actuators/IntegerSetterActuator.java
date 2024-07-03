package smarthome.domain.actuators;

import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;

/**
 * A class representing an actuator that sets integer values for specific functionality.
 */
public class IntegerSetterActuator extends Actuator implements ActuatorSpecificInteger {
    /**
     * The properties associated with the actuator
     */
    private final ActuatorProperties actuatorProperties;

    /**
     * Constructor for the IntegerSetterActuator object
     *
     * @param actuatorID              The identifier of the actuator
     * @param actuatorProperties      The properties associated with the actuator
     * @param deviceID                The identifier of the device the actuator belongs to
     * @param actuatorFunctionalityID The functionality ID associated with the actuator
     * @throws IllegalArgumentException if the parameters are null or blank
     */
    public IntegerSetterActuator(ActuatorID actuatorID, ActuatorFunctionalityID actuatorFunctionalityID, ActuatorProperties actuatorProperties, DeviceID deviceID) {
        super(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceID);
        this.actuatorProperties = actuatorProperties;
    }

    /**
     * Method to set the actuator specific value
     *
     * @param specificInteger the specific integer value to be set
     * @return true if the specific integer value is within the range of the actuator properties, false otherwise
     */

    @Override
    public boolean setActuatorSpecificValue(int specificInteger) {
        return specificInteger >= actuatorProperties.getRangeInt().getLowerLimitInt() && specificInteger <= actuatorProperties.getRangeInt().getUpperLimitInt();
    }

}
