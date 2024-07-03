package smarthome.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.LocationDTO;
import smarthome.mapper.RoomDTO;
import smarthome.util.config.TestRepositoryConfig;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestRepositoryConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HouseIntegrationTest {
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
     * Test for configuring the location of a house.
     * The test sends a PATCH request to the "/house" endpoint.
     * The expected response is a HouseID object representing the configured house.
     */
    @Test
    void successConfigureHouseLocation() throws Exception {
        // Data for LocationDTO
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 41.14961;
        double longitude = -8.61099;

        LocationDTO locationDTO = new LocationDTO(street, doorNumber, zipCode, city, country, latitude, longitude);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Get the content of the response
        String resultContent = result.getResponse().getContentAsString();

        // Parse the response JSON to a JsonNode
        JsonNode jsonNode = objectMapper.readTree(resultContent);

        // Convert the JsonNode to an ObjectNode to manipulate it
        ObjectNode objectNode = (ObjectNode) jsonNode;

        // Remove the "_links" field from the ObjectNode
        objectNode.remove("_links");

        // Convert the modified ObjectNode back to a string
        String updatedResultContent = objectMapper.writeValueAsString(objectNode);

        // Parse the response JSON to a DTO object
        HouseDTO houseDTO = objectMapper.readValue(updatedResultContent, HouseDTO.class);

        // Assert if the configured house parameters matches the chosen values
        assertNotNull(houseDTO);
        assertEquals(street, houseDTO.street);
        assertEquals(doorNumber, houseDTO.doorNumber);
        assertEquals(zipCode, houseDTO.zipCode);
        assertEquals(city, houseDTO.city);
        assertEquals(country, houseDTO.country);
        assertEquals(latitude, houseDTO.latitude);
        assertEquals(longitude, houseDTO.longitude);
    }

    /**
     * Test for configuring the location of a house with an invalid zip code.
     * The test sends a PATCH request to the "/house" endpoint.
     * The expected response is an error message indicating that the zip code is invalid.
     */
    @Test
    void failConfigureHouseLocationInvalidZipCode() throws Exception {
        // Data for LocationDTO
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "5c89K00";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 41.14961;
        double longitude = -8.61099;

        LocationDTO locationDTO = new LocationDTO(street, doorNumber, zipCode, city, country, latitude, longitude);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.patch("/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Get the content of the response
        String resultContent = result.getResponse().getContentAsString();

        // Assert error
        assertEquals("Invalid zip code for the country", resultContent);
    }

    /**
     * Test for listing all rooms in a house.
     * The test sends a GET request to the "/rooms/listallrooms" endpoint.
     * The expected response is a list of RoomDTO objects representing the rooms in the house.
     */
    @Test
    void successGetListOfRoomsInHouse() throws Exception {
        String houseID = "House001";
        // Perform the GET request
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house/" + houseID + "/rooms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response content
        String resultContent = result.getResponse().getContentAsString();

        assertNotNull(resultContent);
    }


    /**
     * Test for listing rooms in a house.
     * The test sends a GET request to the "/rooms/list" endpoint with a boolean parameter.
     * The expected response is a list of RoomDTO objects representing the rooms in the house.
     */
    @Test
    void successGetListOfRoomsInHouseInside() throws Exception {
        String houseID = "House001";
        boolean areRoomsInside = true;
        // Perform the GET request
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house/" + houseID + "/rooms")
                        .param("areRoomsInside", String.valueOf(areRoomsInside))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response content
        String resultContent = result.getResponse().getContentAsString();

        assertNotNull(resultContent);
    }

    /**
     * Test for listing rooms in a house.
     * The test sends a GET request to the "/rooms/list" endpoint with a boolean parameter.
     * The expected response is a list of RoomDTO objects representing the rooms in the house.
     */
    @Test
    void successGetListOfRoomsInHouseOutside() throws Exception {
        String houseID = "House001";
        boolean areRoomsInside = false;
        // Perform the GET request
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house/" + houseID + "/rooms")
                        .param("areRoomsInside", String.valueOf(areRoomsInside))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Get the content of the response
        String resultContent = result.getResponse().getContentAsString();

        // Convert the response content to a JSON array
        JSONArray roomDTOList = new JSONArray(resultContent);

        // Get the first object from the array and remove links for comparison
        JSONObject roomDTO = roomDTOList.getJSONObject(0);
        roomDTO.remove("links");

        RoomDTO expectedRoom = new RoomDTO("Room004", 0, 0, 0, 0);

        String expectedResponseJson = objectMapper.writeValueAsString(expectedRoom);
        JSONObject expectedRoomDTO = new JSONObject(expectedResponseJson);

        assertEquals(expectedRoomDTO.toString(), roomDTO.toString());
    }


    /**
     * Test for retrieving all devices in a house.
     * The test sends a GET request to the "/rooms/{houseID}/devices" endpoint.
     * The expected response is a list of DeviceDTO objects representing the devices in the house.
     */
    @Test
    void successGetListOfAllDevicesInHouse () throws Exception {
        String houseID = "House001";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house/" + houseID + "/devices")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        //Deserialize the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        List<DeviceDTO> devices = objectMapper.readValue(resultContent, new TypeReference<>() {});

        //Assert
        assertTrue(resultContent.contains("Grid Power Meter"));
        assertTrue(devices.size() > 10);
    }

    /**
     * Test for retrieving all devices in a house.
     * The test sends a GET request to the "/rooms/{houseID}/devices" endpoint.
     * The expected response is an error message indicating that the house was not found.
     */
    @Test
    void failGetListOfAllDevicesInHouse () throws Exception {
        String houseID = "House003";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house/" + houseID + "/devices")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "House not found in Repository";

        //Assert
        assertEquals(expectedResponse, resultContent);
    }


    @Test
    void successGetHouseDetails() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Get the content of the response
        String resultContent = result.getResponse().getContentAsString();

        // Deserialize the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(resultContent);
        JsonNode bodyNode = jsonNode.get("body");
        String houseID = bodyNode.get("houseID").asText();

        // Assert that the houseDTO has the expected houseID
        assertEquals("House001", houseID);
    }

    /**
     * Tests the endpoint to retrieve a list of devices grouped by functionality and room ID.
     * Successfully retrieves the list of devices.
     */
    @Test
    void successfullyRetrievesDeviceListedByFunctionality() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house")
                        .param("groupedByFunctionality", String.valueOf(true))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        // Deserialize the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<Object, Map<RoomDTO, DeviceDTO>>> typeRef = new TypeReference<>() {};
        Map<Object, Map<RoomDTO, DeviceDTO>> responseMap = objectMapper.readValue(resultContent, typeRef);

        assertNotNull(resultContent);
        assertFalse(responseMap.isEmpty());
    }

    @Test
    void successGetPeakPowerConsumption() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house")
                        .param("givenStart", "2024-04-01T12:14:00")
                        .param("givenEnd", "2024-04-01T12:40:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        double expectedPowerConsumption = 70.0;
        double actualPowerConsumption = Double.parseDouble(resultContent);

        assertEquals(expectedPowerConsumption, actualPowerConsumption, 0.001);
    }

    @Test
    void successMaxTempDifOutsideInside() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house")
                        .param("insideSensorID", "Sensor001")
                        .param("outsideSensorID", "Sensor007")
                        .param("givenStart", "2024-04-15T08:00:00")
                        .param("givenEnd", "2024-04-15T21:40:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        double resultContent = Double.parseDouble(result.getResponse().getContentAsString());
        double expectedResponse = 6.0;

        assertEquals(expectedResponse, resultContent);
    }

    @Test
    void failMaxTempDifOutsideInside() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house")
                        .param("insideSensorID", "sensor1")
                        .param("outsideSensorID", "sensor2")
                        .param("givenStart", "2024-04-15T08:00:00")
                        .param("givenEnd", "2024-04-15T21:40:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String expectedResponse = "Failed to calculate difference in set period";

        assertEquals(expectedResponse, resultContent);
    }

    @Test
    void failHandleHouseQuery() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/house")
                        .param("givenStart", "datetimenotvalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        int statusCode = result.getResponse().getStatus();

        assertEquals(HttpStatus.BAD_REQUEST.value(), statusCode);
    }

}