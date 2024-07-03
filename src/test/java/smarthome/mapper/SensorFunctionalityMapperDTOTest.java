package smarthome.mapper;

import smarthome.domain.sensorfunctionality.SensorFunctionality;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.SensorFunctionalityID;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code SensorFunctionalityMapperDTO} class.
 */
class SensorFunctionalityMapperDTOTest {

    /**
     * Tests the conversion of SensorFunctionality to SensorFunctionalityDTO.
     * It verifies that the SensorFunctionalityDTO object is not null and contains the correct sensor functionality.
     */
    @Test
    void sensorFunctionalityToDTO() {
        SensorFunctionality sensorFunctionalityDouble = mock(SensorFunctionality.class);
        String sensorFunctionalityName = "Temperature";
        when(sensorFunctionalityDouble.toString()).thenReturn(sensorFunctionalityName);

        SensorFunctionalityMapperDTO mapper = new SensorFunctionalityMapperDTO();
        SensorFunctionalityDTO sensorFunctionalityDTO = mapper.sensorFunctionalityToDTO(sensorFunctionalityDouble);
        assertEquals("Temperature", sensorFunctionalityDTO.sensorFunctionalityName);
    }

    /**
     * Tests the conversion of null SensorFunctionality to SensorFunctionalityDTO.
     */
    @Test
    void sensorSensorFunctionalityToDTO_withNull() {
        SensorFunctionalityMapperDTO sensorFunctionalityMapperDTO = new SensorFunctionalityMapperDTO();
        SensorFunctionality functionality = null;
        SensorFunctionalityDTO result = sensorFunctionalityMapperDTO.sensorFunctionalityToDTO(functionality);
        assertNull(result);
    }

    /**
     * Tests the conversion of SensorFunctionalityID to SensorFunctionalityDTO.
     * It verifies that the SensorFunctionalityDTO object is not null and contains the correct sensor functionality.
     */
    @Test
    void sensorFunctionalityIDToDTO() {
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        String sensorFunctionalityName = "Temperature";
        when(sensorFunctionalityIDDouble.toString()).thenReturn(sensorFunctionalityName);

        SensorFunctionalityMapperDTO mapper = new SensorFunctionalityMapperDTO();
        SensorFunctionalityDTO sensorFunctionalityDTO = mapper.sensorFunctionalityToDTO(sensorFunctionalityIDDouble);
        assertEquals("Temperature", sensorFunctionalityDTO.sensorFunctionalityName);
    }

    @Test
    void sensorFunctionalityIDToDTOWhenNull() {
        SensorFunctionalityID sensorFunctionalityID = null;

        SensorFunctionalityMapperDTO mapper = new SensorFunctionalityMapperDTO();
        SensorFunctionalityDTO sensorFunctionalityDTO = mapper.sensorFunctionalityToDTO(sensorFunctionalityID);
        assertNull(sensorFunctionalityDTO);
    }

    /**
     * Tests the conversion of a list of SensorFunctionality objects to a map of SensorFunctionalityDTO to SensorFunctionality.
     * It verifies that the conversion is performed correctly and the resulting map is not null and contains the expected number of entries.
     */
    @Test
    void sensorFunctionalityToDTOListConversion() {
        SensorFunctionalityID sensorFunctionalityIDDouble1 = mock(SensorFunctionalityID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble2 = mock(SensorFunctionalityID.class);
        when(sensorFunctionalityIDDouble1.toString()).thenReturn("Temperature");
        when(sensorFunctionalityIDDouble2.toString()).thenReturn("Temperature2");

        List<SensorFunctionalityID> sensorFunctionalityIDList = new ArrayList<>();
        sensorFunctionalityIDList.add(sensorFunctionalityIDDouble1);
        sensorFunctionalityIDList.add(sensorFunctionalityIDDouble2);

        SensorFunctionalityMapperDTO sensorFunctionalityMapperDTO = new SensorFunctionalityMapperDTO();

        List<SensorFunctionalityDTO> listOfSensorFunctionalities = sensorFunctionalityMapperDTO.sensorFunctionalityToDTOList(sensorFunctionalityIDList);
        assertEquals(2, listOfSensorFunctionalities.size());
        assertNotNull(listOfSensorFunctionalities);
    }
}