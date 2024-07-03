package smarthome.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import smarthome.mapper.ActuatorFunctionalityDTO;
import smarthome.util.config.TestRepositoryConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestRepositoryConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

 class ActuatorFunctionalityIntegrationTest {
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
     * Attribute of WebApplicationContext.
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Initializes the mock environment and opens mocks before each test execution.
     *
     * @throws Exception if an error occurs during the setup.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Tests the getActuatorFunctionalityList method of the ActuatorFunctionalityController class.
     * The method should return a list of actuator functionalities.
     * The test performs an HTTP GET request to the /actuatorfunctionality endpoint.
     */
    @Test
    void shouldGetActuatorFunctionalityList() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/actuatorfunctionality")
                        .contentType(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ActuatorFunctionalityDTO expectedActuatorFunctionalityDTO1 =
                new ActuatorFunctionalityDTO("DecimalSetter");
        ActuatorFunctionalityDTO expectedActuatorFunctionalityDTO2 =
                new ActuatorFunctionalityDTO("Switch");
        ActuatorFunctionalityDTO expectedActuatorFunctionalityDTO3 =
                new ActuatorFunctionalityDTO("IntegerSetter");
        ActuatorFunctionalityDTO expectedActuatorFunctionalityDTO4 =
                new ActuatorFunctionalityDTO("BlindSetter");

        List<ActuatorFunctionalityDTO> expectedActuatorFunctionalityDTOList = new ArrayList<>();
        expectedActuatorFunctionalityDTOList.add(expectedActuatorFunctionalityDTO1);
        expectedActuatorFunctionalityDTOList.add(expectedActuatorFunctionalityDTO2);
        expectedActuatorFunctionalityDTOList.add(expectedActuatorFunctionalityDTO3);
        expectedActuatorFunctionalityDTOList.add(expectedActuatorFunctionalityDTO4);

        // Extract the list of SensorFunctionalityDTO objects from the response
        JsonNode jsonNode = objectMapper.readTree(content);
        JsonNode embedded = jsonNode.get("_embedded");
        JsonNode actuatorFunctionalityList = embedded.get("actuatorFunctionalityDTOList");

        // Deserialize the list of SensorFunctionalityDTO objects
        List<ActuatorFunctionalityDTO> actualActuatorFunctionalityDTOs = objectMapper.readValue(
                actuatorFunctionalityList.toString(),
                TypeFactory.defaultInstance().constructCollectionType(List.class, ActuatorFunctionalityDTO.class)
        );

        List<String> expected = expectedActuatorFunctionalityDTOList.stream()
                .map(dto -> dto.actuatorFunctionalityName)
                .toList();

        List<String> actual = actualActuatorFunctionalityDTOs.stream()
                .map(dto -> dto.actuatorFunctionalityName)
                .toList();

        assertEquals(expected, actual);
    }

    /**
     * Tests the getActuatorFunctionality method of the ActuatorFunctionalityController class.
     * The method should return an actuator functionality.
     * The test performs an HTTP GET request to the /actuatorfunctionality/{id} endpoint.
     */
    @Test
    void shouldGetActuatorFunctionality() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/actuatorfunctionality/DecimalSetter")
                        .contentType(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ActuatorFunctionalityDTO expectedActuatorFunctionalityDTO =
                new ActuatorFunctionalityDTO("DecimalSetter");

        ActuatorFunctionalityDTO actualActuatorFunctionalityDTO = objectMapper.readValue(content, ActuatorFunctionalityDTO.class);

        assertEquals(expectedActuatorFunctionalityDTO, actualActuatorFunctionalityDTO);
    }

    /**
     * Tests the getActuatorFunctionalityMap method of the ActuatorFunctionalityController class.
     * The method should return a map of device ID and room ID according to the functionality.
     * The test performs an HTTP GET request to the /actuatorfunctionality/BlindSetter?type=map endpoint.
     */
    @Test
    void shouldGetActuatorFunctionalityMap() throws Exception {
        String deviceNameToDeactivate = "Device003";

        // first deactivate a device
        mockMvc.perform(MockMvcRequestBuilders.patch("/devices/" + deviceNameToDeactivate)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/actuatorfunctionality/BlindSetter?type=map")
                        .contentType(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        // Parse the actual response to extract device ID and room ID pairs
        Map<String, String> actualDeviceIDRoomIDMap = new HashMap<>();
        JsonNode rootNode = objectMapper.readTree(content);
        JsonNode embeddedNode = rootNode.get("_embedded");
        if (embeddedNode != null) {
            JsonNode blindRollerDTOList = embeddedNode.get("blindRollerDTOList");
            if (blindRollerDTOList != null) {
                for (JsonNode node : blindRollerDTOList) {
                    String deviceName = node.get("deviceName").asText();
                    String roomName = node.get("roomName").asText();
                    actualDeviceIDRoomIDMap.put(deviceName, roomName);
                }
            }
        }

        // Define the expected device ID and room ID map
        Map<String, String> expectedDeviceIDRoomIDMap = Map.of(
                "Device009", "Room005",
                "Device004", "Room002"
        );

        // Assert that the actual map matches the expected map
        assertEquals(expectedDeviceIDRoomIDMap, actualDeviceIDRoomIDMap);
    }

    /**
     * Tests the getActuatorFunctionality method of the ActuatorFunctionalityController class.
     * The method should return a bad request for a non-existent actuator functionality.
     * The test performs an HTTP GET request to the /actuatorfunctionality/InvalidActuator endpoint.
     */
    @Test
    void shouldReturnActuatorBadRequest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/actuatorfunctionality/InvalidActuator")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("Actuator Functionality not found", content);

    }

}