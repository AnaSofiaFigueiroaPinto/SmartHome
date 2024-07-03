package smarthome.mapper;

import org.junit.jupiter.api.Test;
import smarthome.domain.actuatorfunctionality.ActuatorFunctionality;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code ActuatorFunctionalityMapperDTO} class.
 */
class ActuatorFunctionalityMapperDTOTest {


    /**
     * Test case to verify conversion from {@link ActuatorFunctionalityID} to {@link ActuatorFunctionalityDTO}
     */
    @Test
    void actuatorFunctionalityIDToDTO() {
        ActuatorFunctionalityID actuatorFunctionalityIDDouble = mock(ActuatorFunctionalityID.class);
        String actuatorFunctionalityName = "switch";
        when(actuatorFunctionalityIDDouble.toString()).thenReturn(actuatorFunctionalityName);

        ActuatorFunctionalityMapperDTO mapper = new ActuatorFunctionalityMapperDTO();
        ActuatorFunctionalityDTO actuatorFunctionalityDTO = mapper.actuatorFunctionalityToDTO(actuatorFunctionalityIDDouble);
        assertEquals("switch", actuatorFunctionalityDTO.actuatorFunctionalityName);
    }

    /**
     * Test case to verify conversion from {@link ActuatorFunctionality} to {@link ActuatorFunctionalityDTO}
     */
    @Test
    void actuatorFunctionalityIDToDTOWhenNull() {
        ActuatorFunctionalityID actuatorFunctionalityID = null;

        ActuatorFunctionalityMapperDTO mapper = new ActuatorFunctionalityMapperDTO();
        ActuatorFunctionalityDTO actuatorFunctionalityDTO = mapper.actuatorFunctionalityToDTO(actuatorFunctionalityID);
        assertNull(actuatorFunctionalityDTO);
    }

    /**
     * Test case to verify conversion of objects using {@link ActuatorFunctionalityMapperDTO}
     */
    @Test
    void actuatorFunctionalityToDTOListConversion() {
        ActuatorFunctionalityMapperDTO actuatorFunctionalityMapperDTO = new ActuatorFunctionalityMapperDTO();

        ActuatorFunctionalityID actuatorFunctionalityIDDouble1 = mock(ActuatorFunctionalityID.class);
        ActuatorFunctionalityID actuatorFunctionalityIDDouble2 = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityIDDouble1.toString()).thenReturn("id1");
        when(actuatorFunctionalityIDDouble2.toString()).thenReturn("id2");

        List<ActuatorFunctionalityID> actuatorFunctionalityIDs = new ArrayList<>();
        actuatorFunctionalityIDs.add(actuatorFunctionalityIDDouble1);
        actuatorFunctionalityIDs.add(actuatorFunctionalityIDDouble2);

        List<ActuatorFunctionalityDTO> actuatorFunctionalities = actuatorFunctionalityMapperDTO.actuatorFunctionalityToDTOList(actuatorFunctionalityIDs);
        assertFalse(actuatorFunctionalities.isEmpty());
    }

}