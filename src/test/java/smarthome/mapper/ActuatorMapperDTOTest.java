package smarthome.mapper;

import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.ActuatorID;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code ActuatorMapperDTO} class.
 */
class ActuatorMapperDTOTest {

	/**
	 * Test case to verify if the method domainToDTO converts an ActuatorID object to an ActuatorDTO object correctly.
	 */
	@Test
	void testSuccessConvertingActuatorIDToActuatorDTO() {
		ActuatorID actuatorID = new ActuatorID("actuatorID");
		ActuatorMapperDTO actuatorMapperDTO = new ActuatorMapperDTO();
		ActuatorDTO actuatorDTO = actuatorMapperDTO.domainToDTO(actuatorID);
		assertNotNull(actuatorDTO);
		assertEquals(actuatorID.toString(), actuatorDTO.actuatorName);
	}

	/**
	 * Test case to verify if the method domainToDTO returns null when given a null ActuatorID object.
	 */
	@Test
	void testFailConvertingNullActuatorIDToActuatorDTO() {
		ActuatorID actuatorID = null;
		ActuatorMapperDTO actuatorMapperDTO = new ActuatorMapperDTO();
		ActuatorDTO actuatorDTO = actuatorMapperDTO.domainToDTO(actuatorID);
		assertNull(actuatorDTO);
	}

	@Test
	void testSuccessConvertingActuatorIDsToDTO() {
		ActuatorID actuatorID1 = new ActuatorID("actuatorID1");
		ActuatorID actuatorID2 = new ActuatorID("actuatorID2");
		ActuatorMapperDTO actuatorMapperDTO = new ActuatorMapperDTO();

		List<ActuatorID> listOfActuatorIDs = new ArrayList<>();
		listOfActuatorIDs.add(actuatorID1);
		listOfActuatorIDs.add(actuatorID2);

		List<ActuatorDTO> listOfActuatorsDTO = actuatorMapperDTO.actuatorsIDToActuatorDTO(listOfActuatorIDs);
		assertNotNull(listOfActuatorsDTO);

		assertEquals(2, listOfActuatorsDTO.size());
		assertEquals(actuatorID1.toString(), listOfActuatorsDTO.get(0).actuatorName);
		assertEquals(actuatorID2.toString(), listOfActuatorsDTO.get(1).actuatorName);
	}
}