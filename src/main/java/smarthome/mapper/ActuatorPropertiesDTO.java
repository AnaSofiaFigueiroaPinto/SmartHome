package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * Actuator Properties (DTO) representing the Actuator Properties.
 */
public class ActuatorPropertiesDTO extends RepresentationModel<ActuatorPropertiesDTO> {
    /**
     * Declaring all public attributes of actuator properties.
     */
    public int upperLimitInt;
    public int lowerLimitInt;
    public double upperLimitDecimal;
    public double lowerLimitDecimal;
    public int precision;

    /**
     * Constructor of ActuatorPropertiesDTO without attributes.
     */
    public ActuatorPropertiesDTO() {
    }

    /**
     * Constructor of ActuatorPropertiesDTO with integer attributes.
     *
     * @param upperLimitInt The upper limit of an integer range.
     * @param lowerLimitInt The lower limit of an integer range.
     */
    public ActuatorPropertiesDTO(int upperLimitInt, int lowerLimitInt) {
        this.upperLimitInt = upperLimitInt;
        this.lowerLimitInt = lowerLimitInt;
    }

    /**
     * Constructor of ActuatorPropertiesDTO with decimal attributes and precision.
     *
     * @param upperLimitDecimal The upper limit of a decimal range.
     * @param lowerLimitDecimal The lower limit of a decimal range.
     * @param precision         The precision of a value between a range.
     */
    public ActuatorPropertiesDTO(double upperLimitDecimal, double lowerLimitDecimal, int precision) {
        this.upperLimitDecimal = upperLimitDecimal;
        this.lowerLimitDecimal = lowerLimitDecimal;
        this.precision = precision;
    }

    /**
     * Overriding the equals method to compare two ActuatorPropertiesDTO objects.
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        ActuatorPropertiesDTO that = (ActuatorPropertiesDTO) obj;
        return upperLimitInt == that.upperLimitInt && lowerLimitInt == that.lowerLimitInt &&
                Double.compare(upperLimitDecimal, that.upperLimitDecimal) == 0 &&
                Double.compare(lowerLimitDecimal, that.lowerLimitDecimal) == 0 && precision == that.precision;
    }

    /**
     * Overriding the hashCode method to generate a hash code for an ActuatorPropertiesDTO object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);
    }

}
