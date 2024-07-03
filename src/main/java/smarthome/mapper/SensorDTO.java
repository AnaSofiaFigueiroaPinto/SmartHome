package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * A DTO for the Sensor entity.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SensorDTO extends RepresentationModel<SensorDTO>
{
    /**
     * The name and identifier of the sensor.
     */
    public final String sensorName;

    /**
     * The identifier of the device to which the sensor belongs.
     */
    public String deviceID;

    /**
     * The identifier of the functionality associated with the sensor.
     */
    public String functionalityID;


    /**
     * Empty constructor required by Jackson for deserialization.
     */
    public SensorDTO() {
        this.sensorName = null;
        this.deviceID = null;
        this.functionalityID = null;
    }

    /**
     * Constructor for SensorDTO object using attributes.
     *
     * @param sensorName      String that identifies the Domain object.
     * @param deviceID        String that identifies the Domain object.
     * @param functionalityID String that identifies the Domain object.
     */
    public SensorDTO(@JsonProperty("sensorName") String sensorName,
                     @JsonProperty("deviceID") String deviceID,
                     @JsonProperty("functionalityID") String functionalityID) {
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.functionalityID = functionalityID;
    }

    /**
     * Overriding the equals method to compare two SensorDTO objects.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SensorDTO sensorDTO = (SensorDTO) o;
        return Objects.equals(sensorName, sensorDTO.sensorName) && Objects.equals(deviceID, sensorDTO.deviceID) && Objects.equals(functionalityID, sensorDTO.functionalityID);
    }

    /**
     * Overriding the hashCode method to generate a hash code for the SensorDTO object.
     * @return a hash code value for the SensorDTO object.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), sensorName, deviceID, functionalityID);
    }

    /**
     * Constructor for SensorDTO object using attributes
     *
     * @param sensorName      String that identifies the Domain object
     */
    public SensorDTO(String sensorName)
    {
        this.sensorName = sensorName;
    }
}
