package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * A DTO for the ActuatorFunctionality entity.
 */
@JsonIgnoreProperties("_links")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ActuatorFunctionalityDTO extends RepresentationModel<ActuatorFunctionalityDTO>
{

    /**
     * The functionality of the actuator.
     */
    public String actuatorFunctionalityName;

    /**
     * Empty constructor required by Jackson for deserialization.
     */
    public ActuatorFunctionalityDTO()
    {
    }
    /**
     * Constructor for ActuatorFunctionalityDTO
     * @param actuatorFunctionalityName the ActuatorFunctionality object to be mapped to a DTO
     */
    public ActuatorFunctionalityDTO(@JsonProperty("actuatorFunctionality") String actuatorFunctionalityName)
    {
        this.actuatorFunctionalityName = actuatorFunctionalityName;
    }

    /**
     *  Overriding the equals method to compare two ActuatorFunctionalityDTO objects.
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        ActuatorFunctionalityDTO that = (ActuatorFunctionalityDTO) obj;
        return Objects.equals(actuatorFunctionalityName, that.actuatorFunctionalityName);
    }

    /**
     * Overriding the hashCode method to generate a hash code for an ActuatorFunctionalityDTO object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), actuatorFunctionalityName);
    }
}
