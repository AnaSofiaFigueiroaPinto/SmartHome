package smarthome.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;

import java.util.ArrayList;
import java.util.List;

/**
 * A mapper class for the ActuatorFunctionality entity.
 */
@Component
public class ActuatorFunctionalityMapperDTO {

    /**
     * Converts an ActuatorFunctionalityID object to an ActuatorFunctionalityDTO object.
     * @param actuatorFunctionalityID The ActuatorFunctionalityID to be converted.
     * @return The corresponding ActuatorFunctionalityDTO object, or null if the input is null.
     */
    public ActuatorFunctionalityDTO actuatorFunctionalityToDTO(ActuatorFunctionalityID actuatorFunctionalityID) {
        if (actuatorFunctionalityID == null) {
            return null;
        }
        String actuatorFunctionalityName = actuatorFunctionalityID.toString();
        return new ActuatorFunctionalityDTO(actuatorFunctionalityName);
    }

    /**
     * Converts a list of ActuatorFunctionalityIDs to a list of ActuatorFunctionalityDTO objects.
     * @param actuatorFunctionalitiesIDs The list of ActuatorFunctionality IDs to
     * @return The list of ActuatorFunctionalityDTO objects.
     */
    public List<ActuatorFunctionalityDTO> actuatorFunctionalityToDTOList(List<ActuatorFunctionalityID> actuatorFunctionalitiesIDs) {
        List<ActuatorFunctionalityDTO> actuatorFunctionalities = new ArrayList<>();
        for (ActuatorFunctionalityID actuatorFunctionalityID : actuatorFunctionalitiesIDs) {
            ActuatorFunctionalityDTO actuatorFunctionalityDTO = actuatorFunctionalityToDTO(actuatorFunctionalityID);
            actuatorFunctionalities.add(actuatorFunctionalityDTO);
        }
        return actuatorFunctionalities;
    }

}

