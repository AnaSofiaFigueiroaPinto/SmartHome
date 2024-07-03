package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing an Actuator.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ActuatorDTO extends RepresentationModel<ActuatorDTO> {

    /**
     * The name and identifier of the actuator.
     */
    public final String actuatorName;

    /**
     * The functionality name of the actuator.
     */
    public String actuatorFunctionalityName;

    /**
     * The name of the device associated with the actuator.
     */
    public String deviceName;

    /**
     * The upper limit for integer values.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int upperLimitInt;

    /**
     * The lower limit for integer values.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int lowerLimitInt;

    /**
     * The upper limit for decimal values.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double upperLimitDecimal;

    /**
     * The lower limit for decimal values.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double lowerLimitDecimal;

    /**
     * The precision of the measurements.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int precision;

    /**
     * No-param constructor for json serialization and deserialization purposes.
     */
    public ActuatorDTO(){
        this.actuatorName = null;
        this.actuatorFunctionalityName = null;
        this.deviceName = null;
        this.upperLimitInt = 0;
        this.lowerLimitInt = 0;
        this.upperLimitDecimal = 0;
        this.lowerLimitDecimal = 0;
        this.precision = 0;
    }

    /**
     * Constructor for SensorDTO object using attributes
     *
     * @param actuatorName    String that identifies the Domain object
     * @param actuatorFunctionalityName String that identifies the functionality of the Actuator
     * @param deviceName String that identifies the device to which the Actuator belongs
     */
    public ActuatorDTO(String actuatorName, String actuatorFunctionalityName, String deviceName, int upperLimitInt, int lowerLimitInt, double upperLimitDecimal, double lowerLimitDecimal, int precision) {
        this.actuatorName = actuatorName;
        this.actuatorFunctionalityName = actuatorFunctionalityName;
        this.deviceName = deviceName;
        this.upperLimitInt = upperLimitInt;
        this.lowerLimitInt = lowerLimitInt;
        this.upperLimitDecimal = upperLimitDecimal;
        this.lowerLimitDecimal = lowerLimitDecimal;
        this.precision = precision;
    }

    /**
     * ActuatorDTO constructor for decimalSetterActuator type.
     *
     * @param actuatorName Actuator identifier
     * @param actuatorFunctionalityName Actuator functionality identifier
     * @param deviceName Device identifier
     * @param upperLimitDecimal Upper limit for decimal values
     * @param lowerLimitDecimal Lower limit for decimal values
     * @param precision Precision of the values inserted
     */
    public ActuatorDTO(String actuatorName, String actuatorFunctionalityName, String deviceName, double upperLimitDecimal, double lowerLimitDecimal, int precision) {
        this.actuatorName = actuatorName;
        this.actuatorFunctionalityName = actuatorFunctionalityName;
        this.deviceName = deviceName;
        this.upperLimitDecimal = upperLimitDecimal;
        this.lowerLimitDecimal = lowerLimitDecimal;
        this.precision = precision;
    }

    /**
     * ActuatorDTO constructor with actuatorName as parameter.
     * @param actuatorName String that identifies the Actuator.
     */
    public ActuatorDTO(@JsonProperty("actuatorName") String actuatorName) {
        this.actuatorName = actuatorName;
    }

    /**
     * Method to compare two ActuatorDTO objects by their attributes
     * @param obj Object to be compared with the ActuatorDTO
     * @return boolean that represents the comparison result between the two ActuatorDTO objects
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        ActuatorDTO that = (ActuatorDTO) obj;
        return upperLimitInt == that.upperLimitInt && lowerLimitInt == that.lowerLimitInt &&
                Double.compare(upperLimitDecimal, that.upperLimitDecimal) == 0 &&
                Double.compare(lowerLimitDecimal, that.lowerLimitDecimal) == 0 && precision == that.precision &&
                Objects.equals(actuatorName, that.actuatorName) && Objects.equals(actuatorFunctionalityName, that.actuatorFunctionalityName) &&
                Objects.equals(deviceName, that.deviceName);
    }

    /**
     * Method to generate the hashcode of an ActuatorDTO object
     * @return int that represents the hashcode of an ActuatorDTO object
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), actuatorName, actuatorFunctionalityName, deviceName,
                upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);
    }
}
