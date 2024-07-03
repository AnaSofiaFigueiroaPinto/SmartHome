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
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smarthome.mapper.SensorDTO;
import smarthome.util.config.TestRepositoryConfig;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for sensor-related endpoints.
 * This class tests the functionality of the sensor endpoints in a Spring Boot environment.
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestRepositoryConfig.class)
class SensorIntegrationTest {

    /**
     * MockMvc instance for performing HTTP requests in the tests.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * ObjectMapper instance for converting objects to JSON and vice versa.
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private org.springframework.context.ApplicationContext context;

    @Autowired
    private Environment environment;
    /**
     * Initializes the mock environment and opens mocks before each test execution.
     *
     * @throws Exception if an error occurs during the setup.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        System.out.println("Active profiles: " + Arrays.toString(environment.getActiveProfiles()));
    }

    @Test
    void logBeans() {
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }

    /**
     * Tests the endpoint to create a new sensor.
     * Verifies that the response contains the created sensor and a created status.
     */
    @Test
    void shouldCreateSensor() throws Exception {
        SensorDTO sensorInfo = new SensorDTO("New Sensor", "Device005", "HumidityPercentage");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/sensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorInfo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        // Parse the actual response to a JsonNode
        JsonNode actualResponseNode = objectMapper.readTree(resultContent);

        // Remove the "_links" field from the actual response
        ((ObjectNode) actualResponseNode).remove("_links");

        // Create a SensorDTO object from the actual response
        SensorDTO actualResponse = objectMapper.treeToValue(actualResponseNode, SensorDTO.class);

        // Create the expected response
        SensorDTO expectedResponse = new SensorDTO("New Sensor", "Device005", "HumidityPercentage");

        // Compare the actual and expected responses
        assertEquals(expectedResponse, actualResponse);
    }

    /**
     * Tests the endpoint to create a new sensor with a non-existent device.
     * Verifies that the response contains an unprocessable entity status and an appropriate error message.
     */
    @Test
    void failToCreateSensorNoDevice() throws Exception {
        SensorDTO sensorInfo = new SensorDTO("New Sensor", "Device015", "SolarIrradiance");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/sensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorInfo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Device not found in Repository";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to create a new sensor with an invalid sensor functionality ID.
     * Verifies that the response contains an unprocessable entity status and an appropriate error message.
     */
    @Test
    void failToCreateSensorInvalidFunctionality() throws Exception {
        SensorDTO sensorInfo = new SensorDTO("New Sensor", "Device005", "YarnViewer");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/sensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorInfo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Sensor functionality not present in the list";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to create a new sensor with an empty sensor name.
     * Verifies that the response contains an unprocessable entity status and an appropriate error message.
     */
    @Test
    void failToCreateSensorEmptySensor() throws Exception {
        SensorDTO sensorInfo = new SensorDTO("     ", "Device005", "SpecificTimePowerConsumption");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/sensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorInfo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Sensor name (string) is an ID and cannot be null or empty";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to create a new sensor with an empty device ID.
     * Verifies that the response contains an unprocessable entity status and an appropriate error message.
     */
    @Test
    void failToCreateSensorEmptyDevice() throws Exception {
        SensorDTO sensorInfo = new SensorDTO("New Sensor", "      ", "WindSpeedAndDirection");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/sensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorInfo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Device name (string) is an ID and cannot be null or empty";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to create a new sensor with an empty sensor functionality ID.
     * Verifies that the response contains an unprocessable entity status and an appropriate error message.
     */
    @Test
    void failToCreateSensorEmptyFunctionality() throws Exception {
        SensorDTO sensorInfo = new SensorDTO("New Sensor", "Device005", "     ");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/sensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorInfo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Sensor Functionality ID cannot be null or empty";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the successful retrieval of sensor information by ID.
     * Verifies that the method returns a response with status OK and the correct sensor data in JSON format.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetSensorByIDSuccess() throws Exception {
        String sensorID = "Sensor005";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/sensor/" + sensorID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        SensorDTO expectedResponse = new SensorDTO("Sensor005", "Device005", "ElectricEnergyConsumption");

        // Ignore the _links attribute
        JsonNode expectedJson = objectMapper.readTree(objectMapper.writeValueAsString(expectedResponse));
        JsonNode actualJson = objectMapper.readTree(resultContent);
        ((ObjectNode) actualJson).remove("_links"); // Removing _links from actual JSON

        assertEquals(expectedJson, actualJson);
    }

    /**
     * Tests the case when attempting to retrieve sensor information with a non-existent ID.
     * Verifies that the method returns a response with status 422 Unprocessable Entity
     * and the appropriate error message.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetSensorByIDFailure() throws Exception {
        String sensorID = "NonExistentSensor"; // This sensor ID does not exist in the system

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/sensor/" + sensorID)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Sensor not found in Repository";

        assertEquals(expectedResponse, resultContent);
    }
}
