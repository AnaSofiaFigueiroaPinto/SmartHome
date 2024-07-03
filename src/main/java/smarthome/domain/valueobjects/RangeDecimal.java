package smarthome.domain.valueobjects;

import smarthome.ddd.ValueObject;

public class RangeDecimal implements ValueObject {
    private final double upperLimitDecimal;
    private final double lowerLimitDecimal;
    private final int precision;

    /**
     * Constructor of Range Decimal object
     *
     * @param upperLimitDecimal a double value that sets the upper limit of a range
     * @param lowerLimitDecimal a double value that sets the lower limit of a range
     * @param precision         an integer value that sets the precision
     */
    public RangeDecimal(double upperLimitDecimal, double lowerLimitDecimal, int precision) {
        if (!validConstructorArguments(upperLimitDecimal, lowerLimitDecimal, precision)) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        this.upperLimitDecimal = upperLimitDecimal;
        this.lowerLimitDecimal = lowerLimitDecimal;
        this.precision = precision;
    }

    /**
     * Method to validate the constructor arguments of the RangeDecimal object
     *
     * @param upperLimitDecimal a double value that sets the upper limit of a range
     * @param lowerLimitDecimal a double value that sets the lower limit of a range
     * @param precision         an integer value that sets the precision
     * @return true if the parameters are valid false otherwise
     */
    public boolean validConstructorArguments(double upperLimitDecimal, double lowerLimitDecimal, int precision) {
        if (upperLimitDecimal < lowerLimitDecimal) {
            return false;
        }
        if (precision < 0) {
            return false;
        }
        return true;
    }

    public double getUpperLimitDecimal() {
        return upperLimitDecimal;
    }

    public double getLowerLimitDecimal() {
        return lowerLimitDecimal;
    }

    public int getPrecision() {
        return precision;
    }
}
