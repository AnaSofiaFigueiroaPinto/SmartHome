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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import smarthome.mapper.SensorFunctionalityDTO;
import smarthome.util.config.TestRepositoryConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the SensorFunctionality entity.
 */
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestRepositoryConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SensorFunctionalityIntegrationTest {

    /**
     * Attribute of MockMvc.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Attribute of ObjectMapper.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Attribute of WebApplicationContext.
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Method that sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Method that tests the successful retrieval of a list of SensorFunctionality objects.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void successfulGetListOfSensorFunctionalities() throws Exception {
        // Prepare expected SensorFunctionalityDTO objects
        List<SensorFunctionalityDTO> expectedSensorFunctionalityDTOs = Arrays.asList(
                new SensorFunctionalityDTO("TemperatureCelsius"),
                new SensorFunctionalityDTO("Sunset"),
                new SensorFunctionalityDTO("BinaryStatus"),
                new SensorFunctionalityDTO("SpecificTimePowerConsumption"),
                new SensorFunctionalityDTO("WindSpeedAndDirection"),
                new SensorFunctionalityDTO("PowerAverage"),
                new SensorFunctionalityDTO("DewPointCelsius"),
                new SensorFunctionalityDTO("HumidityPercentage"),
                new SensorFunctionalityDTO("Sunrise"),
                new SensorFunctionalityDTO("Scale"),
                new SensorFunctionalityDTO("ElectricEnergyConsumption"),
                new SensorFunctionalityDTO("SolarIrradiance")
        );

        // Perform GET request to /sensorfunctionality endpoint
        MvcResult result = mockMvc.perform(get("/sensorfunctionality")
                        .contentType(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Extract the content of the response
        String content = result.getResponse().getContentAsString();

        // Extract the list of SensorFunctionalityDTO objects from the response
        JsonNode jsonNode = objectMapper.readTree(content);
        JsonNode embedded = jsonNode.get("_embedded");
        JsonNode sensorFunctionalityList = embedded.get("sensorFunctionalityDTOList");

        // Deserialize the list of SensorFunctionalityDTO objects
        List<SensorFunctionalityDTO> actualSensorFunctionalityDTOs = objectMapper.readValue(
                sensorFunctionalityList.toString(),
                TypeFactory.defaultInstance().constructCollectionType(List.class, SensorFunctionalityDTO.class)
        );

        List<String> expectedNames = expectedSensorFunctionalityDTOs.stream()
                .map(dto -> dto.sensorFunctionalityName)
                .toList();

        List<String> actualNames = actualSensorFunctionalityDTOs.stream()
                .map(dto -> dto.sensorFunctionalityName)
                .toList();

        // Check if the expected and actual lists are equal
        assertEquals(expectedSensorFunctionalityDTOs.size(), actualSensorFunctionalityDTOs.size());
        assertTrue(expectedNames.containsAll(actualNames) && actualNames.containsAll(expectedNames),
                "Expected and actual lists should contain the same elements");
    }

    /**
     * Method that tests the successful retrieval of a SensorFunctionality object by its ID.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void successfulGetSensorFunctionality() throws Exception {
        // Prepare expected SensorFunctionalityDTO object
        SensorFunctionalityDTO expectedSensorFunctionalityDTO = new SensorFunctionalityDTO("TemperatureCelsius");

        // Perform GET request to /sensorfunctionality/{id} endpoint
        MvcResult result = mockMvc.perform(get("/sensorfunctionality/TemperatureCelsius")
                        .contentType(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Extract the content of the response
        String content = result.getResponse().getContentAsString();

        // Deserialize the SensorFunctionalityDTO object
        SensorFunctionalityDTO actualSensorFunctionalityDTO = objectMapper.readValue(content, SensorFunctionalityDTO.class);

        // Check if the expected and actual SensorFunctionalityDTO objects are equal
        assertEquals(expectedSensorFunctionalityDTO, actualSensorFunctionalityDTO);
    }

}
