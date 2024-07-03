package smarthome.domain.valueobjects;

import smarthome.ddd.ValueObject;

/**
 * Represents the target value for an Actuator.
 */
public class ActuatorTarget implements ValueObject {
    /**
     * The target value.
     */
    private final double targetValue;

    /**
     * Constructor of Actuator Target as a Value Object
     *
     * @param targetValue a double value that sets the target value
     */
    public ActuatorTarget(double targetValue) {
        this.targetValue = targetValue;
    }

    /**
     * Gets the target value of the Actuator.
     *
     * @return The target value.
     */
    public double getTargetValue() {
        return targetValue;
    }
}
