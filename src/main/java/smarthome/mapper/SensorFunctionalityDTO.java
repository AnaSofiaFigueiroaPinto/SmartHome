package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * A DTO for the SensorFunctionality entity.
 */
@JsonIgnoreProperties("_links")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SensorFunctionalityDTO extends RepresentationModel<SensorFunctionalityDTO> {

    /**
     * The functionality of the sensor.
     */
    public String sensorFunctionalityName;

    /**
     * Method to compare two SensorFunctionalityDTO objects by their attributes
     * @param o Object to be compared with the SensorFunctionalityDTO
     * @return boolean that represents the comparison result between the two SensorFunctionalityDTO objects
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SensorFunctionalityDTO that = (SensorFunctionalityDTO) o;
        return Objects.equals(sensorFunctionalityName, that.sensorFunctionalityName);
    }


    /**
     * Method to generate the hash code of the SensorFunctionalityDTO object
     * @return int that represents the hash code of the SensorFunctionalityDTO object
     */

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sensorFunctionalityName);
    }

    /**
     * Constructs a new SensorFunctionalityDTO object based on the provided SensorFunctionality.
     *
     * @param sensorFunctionalityName The SensorFunctionality object to be encapsulated in this DTO.
     */
    public SensorFunctionalityDTO(String sensorFunctionalityName) {
        this.sensorFunctionalityName = sensorFunctionalityName;
    }

    /**
     * Empty constructor required by Json for deserialization.
     */
    public SensorFunctionalityDTO() {
        this.sensorFunctionalityName = null;
    }
}
