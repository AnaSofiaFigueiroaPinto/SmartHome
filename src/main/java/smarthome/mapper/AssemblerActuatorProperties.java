package smarthome.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.ActuatorProperties;

/**
 * This class is responsible for mapping ActuatorProperties objects to ActuatorPropertiesDTO objects.
 */
@Component
public class AssemblerActuatorProperties {
    /**
     * Converts an ActuatorProperties object to an ActuatorPropertiesDTO object.
     *
     * @param actuatorDTO The object ActuatorPropertyDTO.
     * @return The value object Actuator Property.
     */
    public ActuatorProperties createActuatorPropertiesFromActuatorDTO(ActuatorDTO actuatorDTO) {
        if (actuatorDTO == null) {
            return null;
        }
        ActuatorProperties actuatorProperties = new ActuatorProperties();
        if(actuatorDTO.upperLimitInt > actuatorDTO.lowerLimitInt) {
            actuatorProperties = new ActuatorProperties(actuatorDTO.upperLimitInt, actuatorDTO.lowerLimitInt);
        } else if (actuatorDTO.upperLimitDecimal > actuatorDTO.lowerLimitDecimal) {
            actuatorProperties = new ActuatorProperties(actuatorDTO.upperLimitDecimal, actuatorDTO.lowerLimitDecimal, actuatorDTO.precision);
        }
        return actuatorProperties;
    }

}
