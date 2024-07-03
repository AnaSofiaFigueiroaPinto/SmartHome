package smarthome.controllerweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.AssemblerActuatorProperties;
import smarthome.service.ActuatorService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest (classes = ActuatorControllerWeb.class)
class ActuatorControllerWebTest {
	/**
	 * The ActuatorService mock to interact with for actuator-related operations.
	 */
	@MockBean
	private ActuatorService actuatorService;

	/**
	 * The ActuatorPropertiesMapperDTO mock to convert actuatorProperties domain to DTO.
	 */
	@MockBean
	private AssemblerActuatorProperties propertiesMapperDTO;

	/**
	 * The ActuatorControllerWeb instance to be tested.
	 */
	@InjectMocks
	private ActuatorControllerWeb actuatorControllerWeb;

	/**
	 * Method to set up the test environment before each test.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test to verify the successful addition of a new actuator to a device.
	 */
	@Test
	void testSuccessfulAdditionOfNewActuatorToDevice() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		ActuatorDTO actuatorDTO = new ActuatorDTO("IntegerSetterActuator", "IntegerSetter", "deviceName", 2, 3, 2.0, 3.0, 1);
		ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID(actuatorDTO.actuatorFunctionalityName);
		ActuatorID actuatorID = new ActuatorID(actuatorDTO.actuatorName);
		DeviceID deviceID = new DeviceID(actuatorDTO.deviceName);
		ActuatorID actuatorIDCreated = new ActuatorID("IntegerSetterActuator");
		ActuatorProperties actuatorPropertiesDomain = new ActuatorProperties(3.0, 2.0, 1);

		//Mock to ActuatorPropertiesMapperDTO and ActuatorService
		when(propertiesMapperDTO.createActuatorPropertiesFromActuatorDTO(actuatorDTO)).thenReturn(actuatorPropertiesDomain);
		when(actuatorService.createActuatorAndSave(actuatorFunctionalityID, actuatorID, actuatorPropertiesDomain, deviceID)).thenReturn(actuatorIDCreated);

		ResponseEntity<Object> response = actuatorControllerWeb.addNewActuatorToDevice(actuatorDTO);
		ActuatorDTO result = (ActuatorDTO) response.getBody();

		assertEquals("IntegerSetterActuator", result.actuatorName);
		assertEquals("IntegerSetter", result.actuatorFunctionalityName);
		assertEquals("deviceName", result.deviceName);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	/**
	 * Test to verify the failing attempt when adding a new actuator to a device with a null actuator ID.
	 */
	@Test
	void testFailToAddNewActuatorToDeviceWhenActuatorNameNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		ActuatorDTO actuatorDTO = new ActuatorDTO(null, "IntegerDecimal", "deviceName", 2, 3, 2.0, 3.0, 1);

		ResponseEntity<Object> response = actuatorControllerWeb.addNewActuatorToDevice(actuatorDTO);
		String errorMessage = "Invalid ID! The ID cannot be null, empty!";

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(errorMessage, response.getBody());
	}

	/**
	 * Test to verify the failing attempt when adding a new actuator to a device with a null Actuator Functionality ID.
	 */
	@Test
	void testFailToAddNewActuatorToDeviceWhenActuatorFunctionalityIDNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		ActuatorDTO actuatorDTO = new ActuatorDTO("IntegerDecimal", null, "deviceName", 2, 3, 2.0, 3.0, 1);

		ResponseEntity<Object> response = actuatorControllerWeb.addNewActuatorToDevice(actuatorDTO);
		String errorMessage = "Invalid ID! The ID cannot be null, empty!";

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(errorMessage, response.getBody());
	}

	/**
	 * Test to verify the failing attempt when adding a new actuator to a device with a null device ID.
	 */
	@Test
	void testFailToAddNewActuatorToDeviceWhenDeviceNameNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		ActuatorDTO actuatorDTO = new ActuatorDTO("IntegerDecimal", "IntegerDecimalActuator", null, 2, 3, 2.0, 3.0, 1);

		ResponseEntity<Object> response = actuatorControllerWeb.addNewActuatorToDevice(actuatorDTO);
		String errorMessage = "Device name (string) is an ID and cannot be null or empty";

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(errorMessage, response.getBody());
	}

	/**
	 * Test case that verifies that a HttpStatus.NOT_FOUND status is returned when the actuator is not found by its ID.
	 */
	@Test
	void testFailGetActuatorByIDWhenActuatorNotFound() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		ActuatorDTO actuatorDTO = new ActuatorDTO("IntegerSetterActuator");
		ActuatorID actuatorID = new ActuatorID("IntegerSetterActuator");

		//Mock to ActuatorService
		when(actuatorService.checkIfActuatorExistsByID(actuatorID)).thenReturn(false);

		ResponseEntity<Object> response = actuatorControllerWeb.getActuatorByID(actuatorDTO.actuatorName);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Test case that verifies that an "Unprocessable_entity" status is returned when the actuatorID parameter is null.
	 */
	@Test
	void testFailGetActuatorByIDWhenActuatorIDIsNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		ActuatorDTO actuatorDTO = new ActuatorDTO(null);

		ResponseEntity<Object> response = actuatorControllerWeb.getActuatorByID(actuatorDTO.actuatorName);

		String errorMessage = "Invalid ID! The ID cannot be null, empty!";
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(errorMessage, response.getBody());
	}

}