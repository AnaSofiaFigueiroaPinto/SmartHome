package smarthome.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.actuatorfunctionality.ActuatorFunctionality;
import smarthome.domain.repository.ActuatorFunctionalityRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code ActuatorFunctionalityService} class.
 */
@SpringBootTest(classes = {ActuatorFunctionalityService.class})
class ActuatorFunctionalityServiceTest {

    /**
     * The {@code ActuatorFunctionalityRepository} mock.
     */
    @MockBean
   private ActuatorFunctionalityRepository actuatorFunctionalityRepositoryDouble;

    /**
     * The {@code ActuatorFunctionalityService} instance to be tested.
     */
    @InjectMocks
    private ActuatorFunctionalityService actuatorFunctionalityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case to verify the retrieval of a list of actuator functionalities
     * from {@link ActuatorFunctionalityService}
     */
    @Test
    void getActuatorFunctionalityList() {
        ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
        ActuatorFunctionality actuatorFunctionality = mock(ActuatorFunctionality.class);
        when(actuatorFunctionality.identity()).thenReturn(actuatorFunctionalityID);

        ActuatorFunctionalityID actuatorFunctionalityID2 = mock(ActuatorFunctionalityID.class);
        ActuatorFunctionality actuatorFunctionality2 = mock(ActuatorFunctionality.class);
        when(actuatorFunctionality2.identity()).thenReturn(actuatorFunctionalityID2);

        Iterable<ActuatorFunctionality> actuatorFunctionalityList =
                List.of(actuatorFunctionality, actuatorFunctionality2);
        when(actuatorFunctionalityRepositoryDouble.findAllEntities()).thenReturn(actuatorFunctionalityList);

        actuatorFunctionalityService = new ActuatorFunctionalityService(actuatorFunctionalityRepositoryDouble);

        assertNotNull(actuatorFunctionalityService.getListOfActuatorFunctionalities());
        assertEquals(2, actuatorFunctionalityService.getListOfActuatorFunctionalities().size());
    }

    /**
     * Test case to verify the retrieval of an empty list of actuator functionalities
     * from {@link ActuatorFunctionalityService}
     */
    @Test
    void getActuatorFunctionalityListEmpty() {
        Iterable<ActuatorFunctionality> actuatorFunctionality = List.of();
        when(actuatorFunctionalityRepositoryDouble.findAllEntities()).thenReturn(actuatorFunctionality);

        actuatorFunctionalityService = new ActuatorFunctionalityService(actuatorFunctionalityRepositoryDouble);

        assertNotNull(actuatorFunctionalityService.getListOfActuatorFunctionalities());
        assertEquals(0, actuatorFunctionalityService.getListOfActuatorFunctionalities().size());
    }
}
