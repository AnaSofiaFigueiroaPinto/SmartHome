package smarthome.domain.valueobjects;

import smarthome.ddd.ValueObject;

public class RangeInt implements ValueObject {
    private final int upperLimitInt;
    private final int lowerLimitInt;

    /**
     * Constructor of Range Int object
     *
     * @param upperLimitInt an integer value that sets the upper limit of a range
     * @param lowerLimitInt an integer value that sets the lower limit of a range
     */
    public RangeInt(int upperLimitInt, int lowerLimitInt) {
        if (!validConstructorArguments(upperLimitInt, lowerLimitInt)) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        this.upperLimitInt = upperLimitInt;
        this.lowerLimitInt = lowerLimitInt;
    }

    /**
     * Method to validate the constructor arguments of the RangeInt object
     *
     * @param upperLimitInt an integer value that sets the upper limit of a range
     * @param lowerLimitInt an integer value that sets the lower limit of a range
     * @return true if the parameters are valid false otherwise
     */
    public boolean validConstructorArguments(int upperLimitInt, int lowerLimitInt) {
        return upperLimitInt >= lowerLimitInt;
    }

    public int getUpperLimitInt() {
        return upperLimitInt;
    }

    public int getLowerLimitInt() {
        return lowerLimitInt;
    }

}
