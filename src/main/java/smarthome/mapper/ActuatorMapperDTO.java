package smarthome.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.ActuatorID;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping Actuator objects to ActuatorDTO objects.
 */
@Component
public class ActuatorMapperDTO {

	/**
	 * Method that converts an ActuatorID object to an ActuatorDTO object.
	 * @param actuatorID unique identifier of the actuator.
	 * @return ActuatorDTO representing the converted ActuatorID.
	 */
	public ActuatorDTO domainToDTO (ActuatorID actuatorID) {
		if (actuatorID == null) {
			return null;
		}
		return new ActuatorDTO(actuatorID.toString());
	}

	/**
	 * Method that converts a list of ActuatorID objects to a list of ActuatorDTO objects.
	 * @param listOfActuatorIDs list of unique identifiers of the actuators.
	 * @return list of ActuatorDTO representing the converted ActuatorIDs.
	 */
	public List<ActuatorDTO> actuatorsIDToActuatorDTO(List<ActuatorID> listOfActuatorIDs) {
		List<ActuatorDTO> listOfActuatorsDTO = new ArrayList<>();
		for (ActuatorID actuatorID : listOfActuatorIDs) {
			ActuatorDTO actuatorDTO = domainToDTO(actuatorID);
			listOfActuatorsDTO.add(actuatorDTO);
		}
		return listOfActuatorsDTO;
	}
}
