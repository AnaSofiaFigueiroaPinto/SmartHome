package smarthome.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.FactoryActuator;
import smarthome.domain.device.Device;
import smarthome.domain.repository.ActuatorFunctionalityRepository;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.service.internaldto.InternalActuatorDTO;
import smarthome.util.exceptions.ActuatorAlreadyExistsException;
import smarthome.util.exceptions.ActuatorNotFoundException;
import smarthome.util.exceptions.DeviceInactiveException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for the {@code ActuatorService} class.
 */
@SpringBootTest(classes = {ActuatorService.class})
class ActuatorServiceTest {

    @MockBean
    private ActuatorRepository actuatorRepoDouble;

    @MockBean
    private FactoryActuator factoryDouble;

    @MockBean
    private DeviceRepository deviceRepoDouble;

    @MockBean
    private ActuatorFunctionalityRepository actuatorFuncRepoDouble;

    @InjectMocks
    private ActuatorService actuatorService;

    /**
     * Set up the test ActuatorServiceTest
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Construct ActuatorService object using valid parameters
     */
    @Test
    void successConstructActuatorService() {
        assertDoesNotThrow(() -> new ActuatorService(factoryDouble, actuatorRepoDouble, deviceRepoDouble, actuatorFuncRepoDouble));
    }

    /**
     * Test for creating and saving a new actuator when it does not already exist in the repository
     */
    @Test
    void successCreateActuatorAndSaveSuccess() {
        ActuatorProperties propertiesDouble = mock(ActuatorProperties.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);
        ActuatorFunctionalityID actuatorFunctionalityIDDouble = mock(ActuatorFunctionalityID.class);
        Device deviceDouble = mock(Device.class);
        Actuator actuatorDouble = mock(Actuator.class);
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);

        when(deviceDouble.isActive()).thenReturn(true);

        String actuatorClass = "smarthome.domain.actuators.BlindSetterActuator";
        when(actuatorFuncRepoDouble.getClassNameForActuatorFunctionalityID(actuatorFunctionalityIDDouble)).thenReturn(actuatorClass);

        when(deviceRepoDouble.findEntityByID(deviceIDDouble)).thenReturn(Optional.ofNullable(deviceDouble));

        when(actuatorDouble.identity()).thenReturn(actuatorIDDouble);

        when(actuatorIDDouble.toString()).thenReturn("BlindSetterActuator");

        when(actuatorRepoDouble.containsEntityByID(actuatorIDDouble)).thenReturn(false);
        when(actuatorRepoDouble.save(actuatorDouble)).thenReturn(actuatorDouble);

        when(factoryDouble.createActuator(actuatorIDDouble, actuatorFunctionalityIDDouble, propertiesDouble, deviceIDDouble, actuatorClass)).thenReturn(actuatorDouble);

        // Call method under test
        ActuatorID result = actuatorService.createActuatorAndSave(actuatorFunctionalityIDDouble, actuatorIDDouble, propertiesDouble, deviceIDDouble);
        // Verify that actuator is created and saved
        assertNotNull(result);
    }

    /**
     * Test fail when saving an actuator when it already exists in the repository
     */
    @Test
    void failCreateActuatorAndSaveAlreadyExists() {
        ActuatorProperties propertiesDouble = mock(ActuatorProperties.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);
        ActuatorFunctionalityID actuatorFunctionalityIDDouble = mock(ActuatorFunctionalityID.class);
        Device deviceDouble = mock(Device.class);
        Actuator actuatorDouble = mock(Actuator.class);
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);

        String actuatorClass = "smarthome.domain.actuators.BlindSetterActuator";
        when(actuatorFuncRepoDouble.getClassNameForActuatorFunctionalityID(actuatorFunctionalityIDDouble)).thenReturn(actuatorClass);

        when(deviceDouble.isActive()).thenReturn(true);

        when(deviceRepoDouble.findEntityByID(deviceIDDouble)).thenReturn(Optional.ofNullable(deviceDouble));

        when(actuatorDouble.identity()).thenReturn(actuatorIDDouble);

        when(actuatorIDDouble.toString()).thenReturn("BlindSetterActuator");

        when(actuatorRepoDouble.containsEntityByID(actuatorIDDouble)).thenReturn(true);
        when(actuatorRepoDouble.save(actuatorDouble)).thenReturn(actuatorDouble);

        when(factoryDouble.createActuator(actuatorIDDouble, actuatorFunctionalityIDDouble, propertiesDouble, deviceIDDouble, actuatorClass)).thenReturn(actuatorDouble);

        // Call method under test
        assertThrows(ActuatorAlreadyExistsException.class, () -> actuatorService.createActuatorAndSave(actuatorFunctionalityIDDouble, actuatorIDDouble, propertiesDouble, deviceIDDouble));

    }

    /**
     * Test fail when creating an actuator in a deactivated device
     */
    @Test
    void failCreateActuatorAndSaveDeactivatedDevice() {
        ActuatorProperties propertiesDouble = mock(ActuatorProperties.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);
        ActuatorFunctionalityID actuatorFunctionalityIDDouble = mock(ActuatorFunctionalityID.class);
        Device deviceDouble = mock(Device.class);
        Actuator actuatorDouble = mock(Actuator.class);
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);

        when(deviceDouble.isActive()).thenReturn(false);

        String actuatorClass = "smarthome.domain.actuators.BlindSetterActuator";
        when(actuatorFuncRepoDouble.getClassNameForActuatorFunctionalityID(actuatorFunctionalityIDDouble)).thenReturn(actuatorClass);


        when(deviceRepoDouble.findEntityByID(deviceIDDouble)).thenReturn(Optional.ofNullable(deviceDouble));

        when(actuatorDouble.identity()).thenReturn(actuatorIDDouble);

        when(actuatorIDDouble.toString()).thenReturn("BlindSetterActuator");

        when(actuatorRepoDouble.containsEntityByID(actuatorIDDouble)).thenReturn(false);
        when(actuatorRepoDouble.save(actuatorDouble)).thenReturn(actuatorDouble);

        when(factoryDouble.createActuator(actuatorIDDouble, actuatorFunctionalityIDDouble, propertiesDouble, deviceIDDouble, actuatorClass)).thenReturn(actuatorDouble);

        // Call method under test
        assertThrows(DeviceInactiveException.class, () -> actuatorService.createActuatorAndSave(actuatorFunctionalityIDDouble, actuatorIDDouble, propertiesDouble, deviceIDDouble));
    }

    /**
     * Test case that checks that the method returns true when it can find an actuator by its ID.
     */
    @Test
    void testSuccessCheckIfActuatorExistsByID() {
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        when(actuatorRepoDouble.containsEntityByID(actuatorIDDouble)).thenReturn(true);
        boolean result = actuatorService.checkIfActuatorExistsByID(actuatorIDDouble);
        assertTrue(result);
    }

    /**
     * Test case that checks that the method returns false when it can't find an actuator by its ID.
     */
    @Test
    void testSuccessFindActuatorsByDeviceID() {
        DeviceID deviceIDDouble = mock(DeviceID.class);
        Actuator actuatorDouble = mock(Actuator.class);
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);

        when(actuatorDouble.identity()).thenReturn(actuatorIDDouble);
        when(actuatorRepoDouble.findByDeviceID(deviceIDDouble)).thenReturn(List.of(actuatorDouble));

        List<ActuatorID> result = actuatorService.findActuatorsIDsOfADevice(deviceIDDouble);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(actuatorIDDouble));
    }

    /**
     * Test case that checks that the method returns an empty list when it can't find any actuators by its ID.
     */
    @Test
    void testFailFindActuatorsByDeviceID() {
        DeviceID deviceIDDouble = mock(DeviceID.class);

        when(actuatorRepoDouble.findByDeviceID(deviceIDDouble)).thenReturn(Collections.emptyList());

        List<ActuatorID> result = actuatorService.findActuatorsIDsOfADevice(deviceIDDouble);

        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }

    /**
     * Test case that verifies that the Actuator info is returned successfully when the actuator is found by its ID.
     */
    @Test
    void successfullyFundActuatorIDInfo() {
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        Actuator actuatorDouble = mock(Actuator.class);

        when(actuatorRepoDouble.findEntityByID(actuatorIDDouble)).thenReturn(Optional.of(actuatorDouble));

        InternalActuatorDTO result = actuatorService.findActuatorIDInfo(actuatorIDDouble);

        assertNotNull(result);
    }

    /**
     * Test case that verifies that a ActuatorNotFoundException is thrown when the actuator is not found by its ID.
     */
    @Test
    void failToFindActuatorIDInfo() {
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);

        when(actuatorRepoDouble.findEntityByID(actuatorIDDouble)).thenReturn(Optional.empty());

        assertThrows(ActuatorNotFoundException.class, () -> actuatorService.findActuatorIDInfo(actuatorIDDouble));
    }
}
