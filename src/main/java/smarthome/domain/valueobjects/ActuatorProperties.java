package smarthome.domain.valueobjects;

import smarthome.ddd.ValueObject;

/**
 * Represents the properties of an Actuator, such as its range limits and precision.
 */
public class ActuatorProperties implements ValueObject {

    /**
     * The range for integer values.
     */
    private RangeDecimal rangeDecimal;

    /**
     * The range for decimal values.
     */
    private RangeInt rangeInt;

    /**
     * Empty constructor of Actuator Properties object
     */
    public ActuatorProperties() {
    }

    /**
     * Constructor of Actuator Properties object
     *
     * @param upperLimitInt an integer value that sets the upper limit of a range
     * @param lowerLimitInt an integer value that sets the lower limit of a range
     * @throws IllegalArgumentException if the arguments are invalid
     */
    public ActuatorProperties(int upperLimitInt, int lowerLimitInt) {
        try {
            this.rangeInt = new RangeInt(upperLimitInt, lowerLimitInt);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
    }

    /**
     * Constructor of Actuator Properties object
     *
     * @param upperLimitDecimal a double value that sets the upper limit of a range
     * @param lowerLimitDecimal a double value that sets the lower limit of a range
     * @param precision         an integer value that sets the precision
     * @throws IllegalArgumentException if the arguments are invalid
     */
    public ActuatorProperties(double upperLimitDecimal, double lowerLimitDecimal, int precision) {
        try {
            this.rangeDecimal = new RangeDecimal(upperLimitDecimal, lowerLimitDecimal, precision);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
    }

    /**
     * Get the range for integer values.
     *
     * @return The range for integer values.
     */
    public RangeInt getRangeInt() {
        return rangeInt;
    }

    /**
     * Get the range for decimal values.
     *
     * @return The range for decimal values.
     */
    public RangeDecimal getRangeDecimal() {
        return rangeDecimal;
    }
}