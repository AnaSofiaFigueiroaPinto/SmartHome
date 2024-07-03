package smarthome.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
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
import smarthome.mapper.*;
import smarthome.util.config.TestRepositoryConfig;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for device-related endpoints.
 */
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestRepositoryConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeviceIntegrationTest {
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

    /**
     * Initializes the mock environment and opens mocks before each test execution.
     *
     * @throws Exception if an error occurs during the setup.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    private void activateDevice(String deviceName) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON));
    }

    /**
     * Tests the endpoint to create a new device.
     * Verifies that the response contains the created device.
     */
    @Test
    void successfullyAddDeviceAndCreated() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO("Device012", "B8115", "ACTIVE", "Room002");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/devices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(deviceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        // Parse the actual response to a JsonNode
        JsonNode actualResponseNode = objectMapper.readTree(resultContent);
        // Remove the "_links" field from the actual response
        ((ObjectNode) actualResponseNode).remove("_links");
        // Create a DeviceDTO object from the actual response
        DeviceDTO actualResponse = objectMapper.treeToValue(actualResponseNode, DeviceDTO.class);

        assertNotNull(actualResponse);
        assertEquals(deviceDTO, actualResponse);
    }

    /**
     * Tests the endpoint to create a new device.
     * Fails to create the device when the roomID is not found in the repository, throwing an exception.
     */
    @Test
    void failAddDeviceAndCreatedRoomNotFound() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO("Device013", "H8115", "ACTIVE", "Room007");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/devices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(deviceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "Room not found in Repository";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to create a new device.
     * Fails to create the device when the deviceID already exists, throwing an exception.
     */
    @Test
    void failAddDeviceAndCreatedThatAlreadyExists() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO("Device001", "T8115", "ACTIVE", "Room001");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/devices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(deviceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "Device already exists";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to create a new device.
     * Fails to create the device when the deviceName is empty, throwing an exception.
     */
    @Test
    void failAddDeviceAndCreatedEmptyName() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO(" ", "H8115", "ACTIVE", "Room001");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/devices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(deviceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "Device name (string) is an ID and cannot be null or empty";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to create a new device.
     * Fails to create the device when the deviceModel is empty, throwing an exception.
     */
    @Test
    void failAddDeviceAndCreatedEmptyModel() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO("Device013", " ", "ACTIVE", "Room001");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/devices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(deviceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "Device Model cannot be null or empty";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to create a new device.
     * Fails to create the device when the roomName is empty, throwing an exception.
     */
    @Test
    void failAddDeviceAndCreatedEmptyRoom() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO("Device013", "H8115", "ACTIVE", " ");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/devices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(deviceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "This parameter 'roomName' must be a non-empty string.";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to deactivate a device.
     * If successful, should return true.
     */
    @Test
    void successfullyDeactivateDevice() throws Exception {
        String deviceName = "Device002";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponse = "true";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to deactivate a device.
     * Fail to deactivate a device when the deviceName is empty, throwing an exception.
     */
    @Test
    void failDeactivateDeviceEmptyString() throws Exception {
        String deviceName = " ";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "Device name (string) is an ID and cannot be null or empty";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to deactivate a device.
     * Fail to deactivate a device when the deviceID is not found in the repository, throwing an exception.
     */
    @Test
    void failDeactivateDeviceDeviceNotFound() throws Exception {
        String deviceName = "Device011";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "Device not found in Repository";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to deactivate a device.
     * Fail to deactivate a device that is already deactivated, returning false.
     */
    @Test
    void failDeactivateDeviceAlreadyDeactivated() throws Exception {
        String deviceName = "Device001";

        // first deactivate a device
        mockMvc
                .perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON));

        // then try to deactivate a device that is already deactivated
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "false";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to close blind roller device.
     * If successful, should return true.
     */
    @Test
    void successfullyCloseBlindRollerDevice() throws Exception {
        String deviceName = "Device004";
        Integer closePercentage = 20;
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                        .param("closePercentage", String.valueOf(closePercentage))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponse = "true";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to close blind roller device.
     * Fail to close blind roller device when the device is deactivated
     */
    @Test
    void failCloseBlindRollerDeactivatedDevice() throws Exception {
        String deviceName = "Device003";
        Integer closePercentage = 20;

        // first deactivate a device
        mockMvc
                .perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON));

        // try to close the blind roller
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                        .param("closePercentage", String.valueOf(closePercentage))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "false";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to close blind roller device.
     * Fail to close blind roller device when the deviceID is not found in the repository, throwing an exception.
     */
    @Test
    void failCloseBlindRollerDeviceNotFound() throws Exception {
        String deviceName = "Device020";
        Integer closePercentage = 20;
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/devices/" + deviceName)
                        .param("closePercentage", String.valueOf(closePercentage))
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "Device not found in Repository";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to retrieve a list of sensors by device ID and sensor functionality ID.
     * Verifies that the response contains the expected list of sensors.
     */
    @Test
    void shouldGetSensorList() throws Exception {
        String deviceID = "Device007";
        String sensorFunctionalityID = "TemperatureCelsius";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID + "/sensors")
                        .param("sensorFunctionalityID", sensorFunctionalityID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        // Convert the response content to a JSON object and extract the embedded
        JSONObject jsonResponse = new JSONObject(resultContent);
        JSONArray sensorDTOList = jsonResponse.getJSONObject("_embedded").getJSONArray("sensorDTOList");

        // Check the size of the sensorDTOList
        assertEquals(1, sensorDTOList.length());

        // Get the first object from the array and remove links
        JSONObject sensorDTO = sensorDTOList.getJSONObject(0);
        sensorDTO.remove("_links");

        // Construct a JSON array containing the single sensorDTO object with the actual response
        JSONArray actualResponseArray = new JSONArray();
        actualResponseArray.put(sensorDTO);

        // Construct an expected response
        SensorDTO expectedResponse = new SensorDTO("Sensor007", null, null);
        List<SensorDTO> expectedResponseList = new ArrayList<>();
        expectedResponseList.add(expectedResponse);
        String expectedResponseJson = objectMapper.writeValueAsString(expectedResponseList);

        assertEquals(expectedResponseJson.toString(), actualResponseArray.toString());
    }

    /**
     * Tests the endpoint to retrieve a list of sensors by device ID and sensor functionality ID.
     * Verifies that the response contains an empty list when no sensors are found.
     */
    @Test
    void shouldGetEmptySensorList() throws Exception {
        String deviceID = "Device011";
        String sensorFunctionalityID = "Sunset";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID + "/sensors")
                        .param("sensorFunctionalityID", sensorFunctionalityID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        // Convert the response content to a JSON object and extract the embedded
        JSONObject jsonResponse = new JSONObject(resultContent);
        jsonResponse.remove("_links");

        // Construct the expected empty JSON object
        JSONObject jsonExpected = new JSONObject();

        assertEquals(jsonExpected.toString(), jsonResponse.toString());
    }

    /**
     * Tests the endpoint to retrieve a list of sensors by device ID with an empty device ID.
     * Verifies that the response contains a bad request status and an appropriate error message.
     */
    @Test
    void failGetSensorListEmptyDeviceID() throws Exception {
        String deviceID = "  ";
        String sensorFunctionalityID = "Sunrise";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID + "/sensors")
                        .param("sensorFunctionalityID", sensorFunctionalityID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Device name (string) is an ID and cannot be null or empty";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to retrieve a list of sensors by device ID with an empty sensor functionality ID.
     * Verifies that the response contains a bad request status and an appropriate error message.
     */
    @Test
    void failGetSensorListEmptySensorFunctionalityID() throws Exception {
        String deviceID = "Device003";
        String sensorFunctionalityID = "    ";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID + "/sensors")
                        .param("sensorFunctionalityID", sensorFunctionalityID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Sensor Functionality ID cannot be null or empty";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to retrieve a list of sensors by device ID with an empty sensor functionality ID.
     * Verifies that the response contains a bad request status and an appropriate error message.
     */
    @Test
    void shouldGetAllSensorsByDevice() throws Exception {
        String deviceID = "Device003";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID + "/sensors")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        // Convert the response content to a JSON object and extract the embedded
        JSONObject jsonResponse = new JSONObject(resultContent);
        JSONArray sensorDTOList = jsonResponse.getJSONObject("_embedded").getJSONArray("sensorDTOList");

        // Check the size of the sensorDTOList
        assertEquals(1, sensorDTOList.length());

        // Get the first object from the array and remove links
        JSONObject sensorDTO = sensorDTOList.getJSONObject(0);
        sensorDTO.remove("_links");

        // Construct a JSON array containing the single sensorDTO object with the actual response
        JSONArray actualResponseArray = new JSONArray();
        actualResponseArray.put(sensorDTO);

        // Construct an expected response
        SensorDTO expectedResponse = new SensorDTO("Sensor003");
        List<SensorDTO> expectedResponseList = new ArrayList<>();
        expectedResponseList.add(expectedResponse);
        String expectedResponseJson = objectMapper.writeValueAsString(expectedResponseList);

        assertEquals(expectedResponseJson.toString(), actualResponseArray.toString());
    }

    /**
     * Tests the endpoint to retrieve a list of actuators by device ID.
     * Verifies that the response contains the expected list of actuators.
     */
    @Test
    void shouldGetActuatorList() throws Exception {
        String deviceID = "Device008";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID + "/actuators")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        // Convert the response content to a JSON object and extract the embedded
        JSONObject jsonResponse = new JSONObject(resultContent);
        JSONArray actuatorDTOList = jsonResponse.getJSONObject("_embedded").getJSONArray("actuatorDTOList");

        // Check the size of the sensorDTOList
        assertEquals(1, actuatorDTOList.length());

        // Get the first object from the array and remove links
        JSONObject actuatorDTO = actuatorDTOList.getJSONObject(0);
        actuatorDTO.remove("_links");

        // Construct a JSON array containing the single sensorDTO object with the actual response
        JSONArray actualResponseArray = new JSONArray();
        actualResponseArray.put(actuatorDTO);

        // Construct an expected response
        ActuatorDTO expectedResponse = new ActuatorDTO("Actuator008");
        List<ActuatorDTO> expectedResponseList = new ArrayList<>();
        expectedResponseList.add(expectedResponse);
        String expectedResponseJson = objectMapper.writeValueAsString(expectedResponseList);

        assertEquals(expectedResponseJson.toString(), actualResponseArray.toString());
}

    /**
     * Tests the endpoint to retrieve a list of actuators by device ID.
     * Verifies that the response contains a bad request status and an appropriate error message.
     */
    @Test
    void failGetActuatorListEmptyDeviceID() throws Exception {
        String deviceID = "  ";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID + "/actuators")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Device name (string) is an ID and cannot be null or empty";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to get an object by its ID.
     * When indicating givenStart and givenEnd, verifies that the response contains a list of measurements of a device
     * in a period of time.
     */
    @Test
    void successfullyGetAllMeasurementsOfDeviceInPeriod() throws Exception {
        String deviceID = "Device001";

        LocalDateTime givenStart = LocalDateTime.of(2024, 4, 1, 0, 0);
        LocalDateTime givenEnd = LocalDateTime.of(2024, 7, 1, 0, 0);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID)
                        .param("givenStart", givenStart.toString())
                        .param("givenEnd", givenEnd.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        // Parse the actual response to a JsonNode
        JsonNode actualResponseNode = objectMapper.readTree(resultContent);
        // Remove the "_links" field from the actual response
        ((ObjectNode) actualResponseNode).remove("_links");

        assertNotNull(resultContent);

        // Create the expected JSON structure and values
        ObjectNode expectedResponseNode = objectMapper.createObjectNode();
        ArrayNode temperatureCelsiusArray = objectMapper.createArrayNode();
        ObjectNode valueNode1 = objectMapper.createObjectNode();
        valueNode1.put("valueWithUnit", "20 Cº");
        temperatureCelsiusArray.add(valueNode1);
        ObjectNode valueNode2 = objectMapper.createObjectNode();
        valueNode2.put("valueWithUnit", "20 Cº");
        temperatureCelsiusArray.add(valueNode2);
        ObjectNode valueNode3 = objectMapper.createObjectNode();
        valueNode3.put("valueWithUnit", "20 Cº");
        temperatureCelsiusArray.add(valueNode3);
        ObjectNode valueNode4 = objectMapper.createObjectNode();
        valueNode4.put("valueWithUnit", "20 Cº");
        temperatureCelsiusArray.add(valueNode4);
        ObjectNode valueNode5 = objectMapper.createObjectNode();
        valueNode5.put("valueWithUnit", "20 Cº");
        temperatureCelsiusArray.add(valueNode5);
        expectedResponseNode.set("TemperatureCelsius", temperatureCelsiusArray);

        // Perform the assertion
        assertEquals(expectedResponseNode, actualResponseNode);
    }

    /**
     * Tests the endpoint to get an object by device id and String state.
     * When indicating a valid sensor functionalityID, verifies that the response is equal to the last reading of the ´
     * specified device.
     */
    @Test
    void successfullyGetTheLastMeasurementOfABlindRoller() throws Exception {
        String deviceID = "Device009";
        String sensorFunctionality = "Scale";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID)
                        .param("functionality", sensorFunctionality)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        assertEquals("50", resultContent.toString());
    }

    /**
     * Tests the endpoint to get an object by device id and String state.
     * When indicating a sensor functionality, verifies that the response is equal to the last reading.
     * Throws an exception when no room is found.
     */
    @Test
    void failGetTheLastMeasurementSensorNotFound() throws Exception {
        String deviceID = "Device001";
        String sensorFunctionality = "Scale";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID)
                        .param("functionality", sensorFunctionality)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Sensor not found in Repository";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to get an object by device id and String state.
     * When indicating a sensor functionality, verifies that the response is equal to the last reading.
     * Throws an exception when no value is found.
     */
    @Test
    void failGetTheLastMeasurementValueNotFound() throws Exception {
        String deviceID = "Device006";
        String sensorFunctionality = "PowerAverage";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID)
                        .param("functionality", sensorFunctionality)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Value not found in Repository";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to get a device by its ID.
     * Verifies that the response contains the object device.
     */
    @Test
    void successfullyGetADeviceByItsID() throws Exception {
        String deviceID = "Device001";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        // Parse the actual response to a JsonNode
        JsonNode actualResponseNode = objectMapper.readTree(resultContent);
        // Remove the "_links" field from the actual response
        ((ObjectNode) actualResponseNode).remove("_links");
        // Create a DeviceDTO object from the actual response
        DeviceDTO actualResponse = objectMapper.treeToValue(actualResponseNode, DeviceDTO.class);

        DeviceDTO deviceDTO = new DeviceDTO("Device001", "T8115", "ACTIVE", "Room001");

        assertNotNull(actualResponse);
        assertEquals(deviceDTO.toString(), actualResponse.toString());
    }

    /**
     * Tests the endpoint to get a device by its ID.
     * Verifies if an exception is thrown.
     */
    @Test
    void failGetADeviceByNonValidID() throws Exception {
        String deviceID = "Device014";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/devices/" + deviceID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String expectedResponse = "Device not found in Repository";

        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, resultContent);
    }
}
