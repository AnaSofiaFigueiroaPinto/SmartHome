package smarthome.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.BlindSetterActuator;
import smarthome.domain.device.Device;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.valueobjects.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code CloseBlindRollerService} class.
 */
@SpringBootTest(classes = {CloseBlindRollerService.class})
class CloseBlindRollerServiceTest {
    /**
     * ActuatorRepository attribute.
     */
    @MockBean
    private ActuatorRepository actuatorRepository;

    /**
     * DeviceRepository attribute.
     */
    @MockBean
    private DeviceRepository deviceRepository;

    /**
     * {@code CloseBlindRollerService} class under test.
     */
    @InjectMocks
    private CloseBlindRollerService closeBlindRollerService;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to get a map of DeviceID and RoomID according to the functionality of the actuator.
     * Returns a map with one DeviceID and RoomID, if a Device has a certain ActuatorFunctionalityID.
     */
    @Test
    void successfulGetMapOfDeviceIDAndRoomIDByActuatorFunctionality() {
        //Instantiate the actuatorFunctionalityID and set up its behavior
        ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

        //Instantiate the actuator and set up its behavior
        Actuator actuator = mock(Actuator.class);
        when(actuator.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID);

        //Instantiate the deviceID and set up its behavior
        DeviceID deviceID = mock(DeviceID.class);
        when(deviceID.toString()).thenReturn("BlindRoller");
        when(actuator.getDeviceName()).thenReturn(deviceID);
        when(actuatorRepository.findByActuatorFunctionalityID(actuatorFunctionalityID)).thenReturn(List.of(actuator));

        //Instantiate the device and set up its behavior
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceID);
        when(device.getDeviceName()).thenReturn("BlindRoller");
        when(device.isActive()).thenReturn(true);
        Optional<Device> deviceOptional = Optional.of(device);
        when(deviceRepository.findEntityByID(deviceID)).thenReturn(deviceOptional);

        //Instantiate the roomID and set up its behavior
        RoomID roomID = mock(RoomID.class);
        when(roomID.toString()).thenReturn("LivingRoom");
        when(device.getRoomID()).thenReturn(roomID);

        //Create expected map
        Map<DeviceID,RoomID> expectedMap = Map.of(deviceID, roomID);

        //Test the method
        Map<DeviceID,RoomID> deviceIDRoomIDMap = closeBlindRollerService.getMapOfDeviceIDAndRoomIDAccordingToFunctionality(actuatorFunctionalityID);

        assertEquals(expectedMap, deviceIDRoomIDMap);
    }

    /**
     * Test to get a map of multiple DeviceID and RoomID according to the functionality of the actuator.
     * Returns a map with multiple DeviceID and RoomID, if multiple Devices have a certain ActuatorFunctionalityID.
     */
    @Test
    void successfulGetMapOfMultipleDeviceIDAndMultipleRoomIDByActuatorFunctionality() {
        //Instantiate the actuatorFunctionalityID and set up its behavior
        ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

        //Instantiate the actuator and set up its behavior
        Actuator actuator = mock(Actuator.class);
        when(actuator.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID);
        Actuator actuator2 = mock(Actuator.class);
        when(actuator2.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID);

        //Instantiate the deviceID's and set up their behavior
        DeviceID deviceID = mock(DeviceID.class);
        when(deviceID.toString()).thenReturn("BlindRoller");
        when(actuator.getDeviceName()).thenReturn(deviceID);

        DeviceID deviceID2 = mock(DeviceID.class);
        when(deviceID2.toString()).thenReturn("BlindRoller2");
        when(actuator2.getDeviceName()).thenReturn(deviceID2);
        when(actuatorRepository.findByActuatorFunctionalityID(actuatorFunctionalityID)).thenReturn(List.of(actuator,actuator2));

        //Instantiate the devices and set up their behavior
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceID);
        when(device.getDeviceName()).thenReturn("BlindRoller");
        when(device.isActive()).thenReturn(true);
        Optional<Device> deviceOptional = Optional.of(device);
        when(deviceRepository.findEntityByID(deviceID)).thenReturn(deviceOptional);

        Device device2 = mock(Device.class);
        when(device2.identity()).thenReturn(deviceID2);
        when(device2.getDeviceName()).thenReturn("BlindRoller2");
        Optional<Device> deviceOptional2 = Optional.of(device2);
        when(deviceRepository.findEntityByID(deviceID2)).thenReturn(deviceOptional2);

        //Instantiate the roomID's and set up their behavior
        RoomID roomID = mock(RoomID.class);
        when(roomID.toString()).thenReturn("LivingRoom");
        when(device.getRoomID()).thenReturn(roomID);

        RoomID roomID2 = mock(RoomID.class);
        when(roomID2.toString()).thenReturn("LivingRoom");
        when(device2.getRoomID()).thenReturn(roomID2);

        //Create expected map
        Map<DeviceID, RoomID> expectedMap = new LinkedHashMap<>();
        expectedMap.put(deviceID, roomID);
        expectedMap.put(deviceID2, roomID2);

        //Test the method
        Map<DeviceID, RoomID> deviceIDRoomIDMap = closeBlindRollerService.getMapOfDeviceIDAndRoomIDAccordingToFunctionality(actuatorFunctionalityID);

        assertEquals(expectedMap.toString(), deviceIDRoomIDMap.toString());
    }

    /**
     * Test to get an empty map of DeviceID and RoomID according to the functionality of the actuator, if there's no Actuator.
     * Returns an empty map, if there's no Actuator with a certain ActuatorFunctionalityID.
     */
    @Test
    void failedMapOfDeviceIDAndRoomIDIfNoActuator() {
        //Instantiate the actuatorFunctionalityID and set up its behavior
        ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

        //Set up the actuatorRepository behavior
        when(actuatorRepository.findByActuatorFunctionalityID(actuatorFunctionalityID)).thenReturn(List.of());

        //Create expected map
        Map<DeviceID,RoomID> expectedMap = Map.of();

        //Test the method
        Map<DeviceID,RoomID> deviceIDRoomIDMap = closeBlindRollerService.getMapOfDeviceIDAndRoomIDAccordingToFunctionality(actuatorFunctionalityID);

        assertEquals(expectedMap, deviceIDRoomIDMap);
    }

    /**
     * Test to get an empty map of DeviceID and RoomID according to the functionality of the actuator, if there's no Device.
     * Returns an empty map, if there's no Device with a certain DeviceID.
     */
    @Test
    void failedMapOfDeviceIDAndRoomIDIfNoDevice() {
        //Instantiate the actuatorFunctionalityID and set up its behavior
        ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

        //Instantiate the actuator and set up its behavior
        Actuator actuator = mock(Actuator.class);
        when(actuator.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID);

        //Instantiate the deviceID and set up its behavior
        DeviceID deviceID = mock(DeviceID.class);
        when(deviceID.toString()).thenReturn("BlindRoller");
        when(actuator.getDeviceName()).thenReturn(deviceID);

        //Set up the actuatorRepository behavior
        when(actuatorRepository.findByActuatorFunctionalityID(actuatorFunctionalityID)).thenReturn(List.of(actuator));

        //Set up the deviceRepository behavior
        when(deviceRepository.findEntityByID(deviceID)).thenReturn(Optional.empty());

        //Create expected map
        Map<DeviceID,RoomID> expectedMap = Map.of();

        //Test the method
        Map<DeviceID,RoomID> deviceIDRoomIDMap = closeBlindRollerService.getMapOfDeviceIDAndRoomIDAccordingToFunctionality(actuatorFunctionalityID);

        assertEquals(expectedMap, deviceIDRoomIDMap);
    }


    /**
     * Test to get a boolean result if the ActuatorFunctionalityID is BlindSetter.
     * Return true if the close percentage is valid.
     */
    @Test
    void successfulValidateClosePercentageIfActuatorFunctionalityIDIsBlindSetter() {
        // Mock DeviceID and set up its behavior
        DeviceID deviceID = mock(DeviceID.class);

        // Mock Device and set up its behavior
        Device device = mock(Device.class);

        // Mock behavior for the device repository and device
        when(deviceRepository.findEntityByID(deviceID)).thenReturn(Optional.of(device));
        when(device.isActive()).thenReturn(true);

        // Mock ActuatorFunctionalityID and set up its behavior
        ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

        // Mock BlindSetterActuator directly
        BlindSetterActuator blindSetterActuator = mock(BlindSetterActuator.class);
        when(blindSetterActuator.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID);
        when(blindSetterActuator.setActuatorSpecificValue(40)).thenReturn(true);

        // Set up actuatorRepository behavior to return the mocked BlindSetterActuator
        when(actuatorRepository.findByDeviceIDAndActuatorFunctionalityID(deviceID,actuatorFunctionalityID)).thenReturn(List.of(blindSetterActuator));

        // Set up DeviceID behavior regarding the actuator

        // Set up deviceRepository behavior
        when(deviceRepository.findEntityByID(deviceID)).thenReturn(Optional.of(device));

        // Test the method
        boolean result = closeBlindRollerService.setActuatorStateOfBlindRoller(deviceID, actuatorFunctionalityID,40);

        // Assert that the method returns true
        assertTrue(result);
    }


    /**
     * Test to get a boolean result if the close percentage is not valid.
     * Return false if the close percentage is not valid.
     */
    @Test
    void failedValidateClosePercentageIfClosePercentageIsNotValid() {
        //Instantiate the deviceID and set up its behavior
        DeviceID deviceID = mock(DeviceID.class);

        //Instantiate the device and set up its behavior
        Device device = mock(Device.class);

        // Mock behavior for the device repository and device
        when(deviceRepository.findEntityByID(deviceID)).thenReturn(Optional.of(device));
        when(device.isActive()).thenReturn(true);

        //Instantiate the actuatorFunctionalityID and set up its behavior
        ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

        // Mock BlindSetterActuator instead of Actuator
        BlindSetterActuator blindSetterActuator = mock(BlindSetterActuator.class);
        when(blindSetterActuator.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID);
        when(blindSetterActuator.setActuatorSpecificValue(110)).thenReturn(false);

        //Set up the actuatorRepository behavior
        when(actuatorRepository.findByDeviceIDAndActuatorFunctionalityID(deviceID, actuatorFunctionalityID)).thenReturn(List.of(blindSetterActuator));

        //Set up the deviceRepository behavior
        when(deviceRepository.findEntityByID(deviceID)).thenReturn(Optional.of(device));

        //Test the method
        boolean result = closeBlindRollerService.setActuatorStateOfBlindRoller(deviceID, actuatorFunctionalityID,110);

        assertFalse(result);
    }

    /**
     * Test to get a boolean result if the device does not have any actuator.
     * Return false if the device has not have any actuator.
     */
    @Test
    void failedValidateClosePercentageIfDeviceHasNoActuators() {
        //Instantiate the deviceID and set up its behavior
        DeviceID deviceID = mock(DeviceID.class);

        //Instantiate the device and set up its behavior
        Device device = mock(Device.class);

        // Mock behavior for the device repository and device
        when(deviceRepository.findEntityByID(deviceID)).thenReturn(Optional.of(device));
        when(device.isActive()).thenReturn(true);

        //Instantiate the actuatorFunctionalityID and set up its behavior
        ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

        //Set up the actuatorRepository behavior
        when(actuatorRepository.findByDeviceIDAndActuatorFunctionalityID(deviceID,actuatorFunctionalityID)).thenReturn(List.of());

        //Test the method
        boolean result = closeBlindRollerService.setActuatorStateOfBlindRoller(deviceID, actuatorFunctionalityID,40);

        assertFalse(result);
    }

    /**
     * Tests the {@code checkIfDeviceIsActive} method to verify if a device is active.
     */
    @Test
    void failedCloseBlindRollerIfDeviceIsDeactivate() {
        // Create mock DeviceID and Device
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);

        // Mock behavior for the device repository and device
        when(deviceRepository.findEntityByID(deviceID)).thenReturn(Optional.of(device));
        when(device.isActive()).thenReturn(false);

        // Mock ActuatorFunctionalityID and set up its behavior
        ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

        // Test the method
        boolean result = closeBlindRollerService.setActuatorStateOfBlindRoller(deviceID, actuatorFunctionalityID,40);

        // Assert that the method returns false
        assertFalse(result);
    }

}