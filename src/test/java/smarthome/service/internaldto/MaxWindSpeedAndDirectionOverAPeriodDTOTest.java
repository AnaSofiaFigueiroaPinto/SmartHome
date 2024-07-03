package smarthome.service.internaldto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link MaxWindSpeedAndDirectionOverAPeriodDTO} class.
 */
public class MaxWindSpeedAndDirectionOverAPeriodDTOTest {

    /**
     * Tests the default constructor of {@link MaxWindSpeedAndDirectionOverAPeriodDTO}.
     * Ensures that a new instance is created and is not null.
     */
    @Test
    public void testDefaultConstructor() {
        MaxWindSpeedAndDirectionOverAPeriodDTO dto = new MaxWindSpeedAndDirectionOverAPeriodDTO();
        assertNotNull(dto);
    }

    /**
     * Tests the parameterized constructor of {@link MaxWindSpeedAndDirectionOverAPeriodDTO}.
     * Ensures that the fields are correctly initialized.
     */
    @Test
    public void testParameterizedConstructor() {
        double measurement = 20.0;
        String unit = "km/h";
        String info = "Southwest wind";

        MaxWindSpeedAndDirectionOverAPeriodDTO dto = new MaxWindSpeedAndDirectionOverAPeriodDTO(measurement, unit, info);

        assertEquals(measurement, dto.measurement);
        assertEquals(unit, dto.unit);
        assertEquals(info, dto.info);
    }

    /**
     * Tests the JSON serialization of {@link MaxWindSpeedAndDirectionOverAPeriodDTO}.
     * Ensures that the object is correctly serialized to a JSON string.
     *
     * @throws Exception if the serialization fails
     */
    @Test
    public void testJsonSerialization() throws Exception {
        double measurement = 20.0;
        String unit = "km/h";
        String info = "Southwest wind";

        MaxWindSpeedAndDirectionOverAPeriodDTO dto = new MaxWindSpeedAndDirectionOverAPeriodDTO(measurement, unit, info);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(dto);

        String expectedJson = "{\"measurement\":20.0,\"unit\":\"km/h\",\"info\":\"Southwest wind\"}";
        assertEquals(expectedJson, jsonString);
    }

    /**
     * Tests the JSON deserialization of {@link MaxWindSpeedAndDirectionOverAPeriodDTO}.
     * Ensures that the JSON string is correctly deserialized to an object.
     *
     * @throws Exception if the deserialization fails
     */
    @Test
    public void testJsonDeserialization() throws Exception {
        String jsonString = "{\"measurement\":20.0,\"unit\":\"km/h\",\"info\":\"Southwest wind\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        MaxWindSpeedAndDirectionOverAPeriodDTO dto = objectMapper.readValue(jsonString, MaxWindSpeedAndDirectionOverAPeriodDTO.class);

        assertEquals(20.0, dto.measurement);
        assertEquals("km/h", dto.unit);
        assertEquals("Southwest wind", dto.info);
    }
}
