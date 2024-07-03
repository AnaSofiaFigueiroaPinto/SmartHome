package smarthome.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smarthome.mapper.ActuatorDTO;
import smarthome.util.config.TestRepositoryConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestRepositoryConfig.class)
class ActuatorIntegrationTest {

	/**
	 * MockMvc instance to perform HTTP requests.
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * ObjectMapper instance to convert objects to JSON and vice-versa.
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Method to set up the test environment before each test.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test to create a new actuator successfully and return the created actuator.
	 * Verifies that the actuator is created successfully and the response is correct.
	 */
	@Test
	void testSuccessCreateNewActuator() throws Exception {

		String actuatorName = "DecimalSetterActuator";
		String actuatorFunctionalityID = "DecimalSetter";
		String deviceName = "Device005";
		int upperLimitInt = 0;
		int lowerLimitInt = 0;
		double upperLimitDecimal = 3.0;
		double lowerLimitDecimal = 1.0;
		int precision = 1;
		ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, actuatorFunctionalityID, deviceName, upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/actuators")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(actuatorDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();

		String resultContent = result.getResponse().getContentAsString();

		// Parse the actual response to a JsonNode
		JsonNode actualResponseNode = objectMapper.readTree(resultContent);

		// Remove the "_links" field from the actual response
		((ObjectNode) actualResponseNode).remove("_links");

		// Create a SensorDTO object from the actual response
		ActuatorDTO actualResponse = objectMapper.treeToValue(actualResponseNode, ActuatorDTO.class);

		assertEquals(actuatorDTO, actualResponse);
	}

	/**
	 * Test that fails to create a new actuator when the actuatorName is empty.
	 * Verifies that the response contains an unprocessable entity status and an appropriate error message is displayed.
	 */
	@Test
	void testFailCreateNewActuatorWhenActuatorNameEmpty() throws Exception {

		String actuatorName = " ";
		String actuatorFunctionalityID = "DecimalSetter";
		String deviceName = "Device005";
		int upperLimitInt = 0;
		int lowerLimitInt = 0;
		double upperLimitDecimal = 3.0;
		double lowerLimitDecimal = 1.0;
		int precision = 1;
		ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, actuatorFunctionalityID, deviceName, upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);


		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/actuators")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(actuatorDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity())
				.andReturn();

		String resultContent = result.getResponse().getContentAsString();
		String expectedMessage = "Invalid ID! The ID cannot be null, empty!";

		assertEquals(expectedMessage, resultContent);
	}

	/**
	 * Test that fails to create a new actuator when the actuatorFunctionalityID is empty.
	 * Verifies that the response contains an unprocessable entity status and an appropriate error message is displayed.
	 */
	@Test
	void testFailCreateNewActuatorWhenActuatorFunctionalityIDEmpty() throws Exception {

		String actuatorName = "DecimalSetterActuator";
		String actuatorFunctionalityID = " ";
		String deviceName = "Device005";
		int upperLimitInt = 0;
		int lowerLimitInt = 0;
		double upperLimitDecimal = 3.0;
		double lowerLimitDecimal = 1.0;
		int precision = 1;
		ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, actuatorFunctionalityID, deviceName, upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);


		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/actuators")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(actuatorDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity())
				.andReturn();

		String resultContent = result.getResponse().getContentAsString();
		String expectedMessage = "Invalid ID! The ID cannot be null, empty!";

		assertEquals(expectedMessage, resultContent);
	}

	/**
	 * Test that fails to create a new actuator when the deviceName is empty.
	 * Verifies that the response contains an unprocessable entity status and an appropriate error message is displayed.
	 */
	@Test
	void testFailCreateNewActuatorWhenDeviceNameIsEmpty() throws Exception {

		String actuatorName = "DecimalSetterActuator";
		String actuatorFunctionalityID = "DecimalSetter";
		String deviceName = " ";
		int upperLimitInt = 0;
		int lowerLimitInt = 0;
		double upperLimitDecimal = 3.0;
		double lowerLimitDecimal = 1.0;
		int precision = 1;
		ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, actuatorFunctionalityID, deviceName, upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);


		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/actuators")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(actuatorDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity())
				.andReturn();

		String resultContent = result.getResponse().getContentAsString();
		String expectedMessage = "Device name (string) is an ID and cannot be null or empty";

		assertEquals(expectedMessage, resultContent);
	}

	/**
	 * Test that fails to create a new actuator when the deviceName is empty.
	 * Verifies that the response contains an unprocessable entity status and an appropriate error message is displayed.
	 */
	@Test
	void testFailCreateNewActuatorWhenActuatorDTOIsNull() throws Exception {

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/actuators")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(null))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		assertEquals(400, result.getResponse().getStatus());
	}

	/**
	 * Test case that verifies that the ActuatorDTO object is returned successfully when the actuator is found by its ID.
	 * @throws Exception if an error occurs during the test.
	 */
	@Test
	void testSuccessGetActuatorByID() throws Exception {
		String actuatorName = "Actuator001";

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/actuators/" + actuatorName)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String resultContent = result.getResponse().getContentAsString();
		ActuatorDTO actuatorDTO = new ActuatorDTO("Actuator001", "DecimalSetter", "Device001", 0, 0, 30.0, 10.0, 1);

		JsonNode expectedResponse = objectMapper.readTree(objectMapper.writeValueAsString(actuatorDTO));
		JsonNode jsonNode = objectMapper.readTree(resultContent);
		// Remove the "_links" field from the actual response
		((ObjectNode) jsonNode).remove("_links");

		assertEquals(expectedResponse, jsonNode);
	}

	/**
	 * Test case that verifies that a 404 (NOT_FOUND) status and an appropriate error message are returned
	 * when the actuator is not found by its ID.
	 *
	 */
	@Test
	void testFailGetActuatorByIDWhenIDDoesNotExist() throws Exception {
		String actuatorName = "Actuator";

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/actuators/" + actuatorName)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		assertEquals(404, result.getResponse().getStatus());
	}
}
