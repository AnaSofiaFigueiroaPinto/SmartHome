package smarthome.integration;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.hateoas.*;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.RoomDTO;
import smarthome.util.config.TestRepositoryConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for sensor-related endpoints.
 * This class tests the functionality of the sensor endpoints in a Spring Boot environment.
 */
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestRepositoryConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomIntegrationTest {

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

    /**
     * Test for creating a room.
     * The test sends a POST request to the "/rooms/add" endpoint with a RoomDTO object in the request body.
     * The expected response is a RoomDTO object representing the created room.
     */

    @Test
    void shouldCreateRoom() throws Exception {
        RoomDTO roomInfo = new RoomDTO("NewRoom", 1, 10.0, 10.0, 10.0, "House001");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomInfo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        // Parse the actual response to a JsonNode
        JsonNode actualResponseNode = objectMapper.readTree(resultContent);

        // Remove the "_links" field from the actual response
        ((ObjectNode) actualResponseNode).remove("_links");

        // Create a SensorDTO object from the actual response
        RoomDTO actualResponse = objectMapper.treeToValue(actualResponseNode, RoomDTO.class);

        // Create the expected response
        RoomDTO expectedResponse = new RoomDTO("NewRoom", 1, 10.0, 10.0, 10.0, "House001");

        // Compare the actual and expected responses
        assertEquals(expectedResponse, actualResponse);
    }

    /**
     * Test for creating a room with invalid dimensions.
     * The test sends a POST request to the "/rooms/add" endpoint with a RoomDTO object in the request body.
     * The expected response is an error message with HttpStatus.UNPROCESSABLE_ENTITY.
     */

    @Test
    void failToCreateRoom() throws Exception {
        RoomDTO roomInfo = new RoomDTO("NewRoom", 1, 10.0, 10.0, -1, "House001");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomInfo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Dimension values invalid";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Test for editing a room.
     * The test sends a PATCH request to the "/rooms/{id}" endpoint with a RoomDTO object in the request body.
     * The expected response is a RoomDTO object representing the edited room.
     */
    @Test
    void shouldEditRoom() throws Exception {
        // Create a new RoomDTO object representing the edited room
        RoomDTO newRoom = new RoomDTO("Room001", 1, 10.0, 10.0, 10.0);

        // Define the room ID to be edited
        String roomID = "Room001";

        // Perform the PATCH request to edit the room
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/rooms/{id}", roomID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRoom))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Get the content of the response
        String resultContent = result.getResponse().getContentAsString();

        // Parse the actual response to a RoomDTO object
        RoomDTO actualResponse = objectMapper.readValue(resultContent, RoomDTO.class);

        // Verify if the edited room matches the expected response
        assertEquals(newRoom, actualResponse);
    }

    /**
     * Test for editing a room with invalid room ID.
     * The test sends a PATCH request to the "/rooms/{id}" endpoint with a RoomDTO object in the request body.
     * The expected response is an error message with HttpStatus.UNPROCESSABLE_ENTITY.
     */
    @Test
    void failEditRoom() throws Exception {
        // Create a new RoomDTO object representing the edited room
        RoomDTO newRoom = new RoomDTO("Room007", 1, 10.0, 10.0, 10.0);

        // Define the room ID to be edited
        String roomID = "Room001";

        // Perform the PATCH request to edit the room
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/rooms/{id}", roomID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRoom))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        // Get the content of the response
        String resultContent = result.getResponse().getContentAsString();

        String expectedResponse = "RoomID in the path and Room Name in Room DTO do not match";

        assertEquals(expectedResponse, resultContent);
    }

    /**
     * Tests the endpoint to retrieve a list of devices by room ID.
     * Successfully retrieves the list of devices.
     */
    @Test
    void successfullyRetrievesDeviceList() throws Exception {
        String roomName = "Room001";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/rooms/{id}/devices", roomName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        // Define the expected devices
        DeviceDTO expectedDTO1 = new DeviceDTO("Device001");
        DeviceDTO expectedDTO2 = new DeviceDTO("Device002");
        List<DeviceDTO> expectedResponseList = new ArrayList<>();
        expectedResponseList.add(expectedDTO1);
        expectedResponseList.add(expectedDTO2);

        // Deserialize the actual response content
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jackson2HalModule());
        PagedModel<EntityModel<DeviceDTO>> actualResponse = mapper.readValue(resultContent, new TypeReference<PagedModel<EntityModel<DeviceDTO>>>() {
        });

        // Extract the device list from the actual response
        List<DeviceDTO> actualDevices = actualResponse.getContent().stream()
                .map(EntityModel::getContent)
                .collect(Collectors.toList());

        assertEquals(expectedResponseList.toString(), actualDevices.toString());

    }

    /**
     * Tests the endpoint to retrieve a list of devices by room ID.
     * Fails to retrieve the list of devices when the roomID is not found in the repository, throwing an exception.
     * <p>
     * Tests the endpoint to retrieve a list of devices by room ID.
     * Fails to retrieve the list of devices when the roomName is empty, throwing an exception.
     */
    @Test
    void failToRetrievesDeviceListRoomNotFound() throws Exception {
        String roomName = "Room009";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/rooms/{id}/devices", roomName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        int statusCode = result.getResponse().getStatus();

        // Compare the actual device list with the expected device list
        assertEquals(HttpStatus.BAD_REQUEST.value(), statusCode);
    }


    /**
     * Tests the endpoint to retrieve a list of devices by room ID.
     * Fails to retrieve the list of devices when the roomName is empty, throwing an exception.
     */
    @Test
    void failToRetrievesDeviceListInvalidRoom() throws Exception {
        String roomName = "   ";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/rooms/{id}/devices", roomName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        int resultContent = result.getResponse().getStatus();

        assertEquals(HttpStatus.BAD_REQUEST.value(), resultContent);
    }

    /**
     * Tests the endpoint to retrieve a list of devices by room ID.
     * Successfully retrieves the list of devices.
     */
    @Test
    void successfullyRetrievesRoomByID() throws Exception {

        String roomName = "Room001";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/rooms/{id}", roomName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        RoomDTO expectedResponse = new RoomDTO("Room001", 1, 2.5, 3.0, 4.0, "House001");

        // Ignore the _links attribute
        JsonNode expectedJson = objectMapper.readTree(objectMapper.writeValueAsString(expectedResponse));
        JsonNode actualJson = objectMapper.readTree(resultContent);
        ((ObjectNode) actualJson).remove("_links"); // Removing _links from actual JSON

        assertEquals(expectedJson, actualJson);
    }

    /**
     * Tests the endpoint to retrieve a list of devices by room ID.
     * Fails to retrieve the list of devices when the roomID is not found in the repository, throwing an exception.
     */

    @Test
    void failGetRoomByID() throws Exception {
        String roomName = "Room010";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/rooms/{id}", roomName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Room not found in Repository";
        assertEquals(expectedResponse, resultContent);
    }

}

