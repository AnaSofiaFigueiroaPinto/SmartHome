package smarthome.domain.actuators;

import smarthome.domain.valueobjects.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class representing a DecimalSetterActuator
 */
public class DecimalSetterActuator extends Actuator implements ActuatorSpecificDecimal {

    /**
     * The properties associated with the actuator
     */
    private final ActuatorProperties actuatorProperties;

    /**
     * The target value of the actuator
     */
    private ActuatorTarget targetValue;

    /**
     * Constructor for the DecimalSetterActuator object
     *
     * @param actuatorID            The identifier of the actuator
     * @param actuatorProperties      The properties associated with the actuator
     * @param deviceID              The identifier of the device the actuator belongs to
     * @param actuatorFunctionalityID The functionality ID associated with the actuator
     * @throws IllegalArgumentException if the parameters are null or blank
     */
    public DecimalSetterActuator(ActuatorID actuatorID, ActuatorFunctionalityID actuatorFunctionalityID, ActuatorProperties actuatorProperties, DeviceID deviceID) {
        super(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceID);
        if (Double.isNaN(actuatorProperties.getRangeDecimal().getUpperLimitDecimal()) || Double.isNaN(actuatorProperties.getRangeDecimal().getLowerLimitDecimal()))
            throw new IllegalArgumentException("Invalid Actuator parameters!");
        this.actuatorProperties = actuatorProperties;
    }

    /**
     * Method to set the target value of the actuator
     *
     * @param newValue The new target value of the actuator
     * @return true if the new value is within the range of the actuator, false otherwise
     */
    @Override
    public boolean setDecimalValue(double newValue) {
        if (newValue < actuatorProperties.getRangeDecimal().getLowerLimitDecimal() || newValue > actuatorProperties.getRangeDecimal().getUpperLimitDecimal())
            return false;
        //Round the value according to the precision and keep it in a new variable called targetValue
        targetValue = new ActuatorTarget(BigDecimal.valueOf(newValue)
                .setScale(actuatorProperties.getRangeDecimal().getPrecision(), RoundingMode.HALF_UP)
                .doubleValue());
        return true;
    }

    /**
     * Retrieves the target value set for the actuator.
     *
     * @return The ActuatorTarget object representing the target value.
     */
    public ActuatorTarget getActuatorTarget() {
        return targetValue;
    }
}
