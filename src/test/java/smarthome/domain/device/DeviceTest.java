package smarthome.domain.device;

import smarthome.domain.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class to test the Device class.
 */
class DeviceTest {
    private Device device;
    private DeviceID deviceIDDouble;
    private DeviceModel deviceModelDouble;
    private RoomID roomIDDouble;

    /**
     * Setting up the devices under test, Device ID is initialized at the start of every test.
     */
    @BeforeEach
    void setUp() {
        //Set up necessary components for testing

        deviceIDDouble = mock(DeviceID.class);

        deviceModelDouble = mock(DeviceModel.class);

        roomIDDouble = mock(RoomID.class);

        device = new Device(deviceIDDouble, deviceModelDouble, roomIDDouble);
        when(deviceIDDouble.toString()).thenReturn("Heater");
    }

    /**
     * Test case for creating a device.
     */
    @Test
    void successDeviceCreation() {
        assertNotNull(device);
    }

    /**
     * Test case for creating a device with a status.
     */
    @Test
    void successDeviceCreationWithStatus() {
        DeviceStatus deviceStatus = mock(DeviceStatus.class);
        Device device = new Device(deviceIDDouble, deviceModelDouble, roomIDDouble, deviceStatus);
        assertNotNull(device);
    }

    /**
     * Test case for creating a device with a null name.
     * Verifies that an {@link IllegalArgumentException} is thrown when attempting to create a device with a null name.
     */
    @Test
    void failDeviceCreationNullID() {
        assertThrows(IllegalArgumentException.class, () -> new Device(null, deviceModelDouble, roomIDDouble));
    }

    /**
     * Test case for creating a device with a null Model.
     * Verifies that an {@link IllegalArgumentException} is thrown when attempting to create a device with a null model.
     */
    @Test
    void failDeviceCreationNullModel() {
        assertThrows(IllegalArgumentException.class, () -> new Device(deviceIDDouble, null, roomIDDouble));
    }

    /**
     * Test case for creating a device with a null RoomID.
     * Verifies that an {@link IllegalArgumentException} is thrown when attempting to create a device with a null RoomID.
     */
    @Test
    void failDeviceCreationNullRoomID() {
        assertThrows(IllegalArgumentException.class, () -> new Device(deviceIDDouble, deviceModelDouble, null));
    }

    /**
     * Test case for creating a device with a null status.
     * Verifies that an {@link IllegalArgumentException} is thrown when attempting to create a device with a null status.
     */
    @Test
    void failDeviceCreationWithStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> new Device(deviceIDDouble, deviceModelDouble, roomIDDouble, null));
    }

    /**
     * Test case for creating a device with all null parameters.
     * Verifies that an {@link IllegalArgumentException} is thrown when attempting to create a device with all null parameters.
     */
    @Test
    void failDeviceCreationWithStatusNullParameters() {
        assertThrows(IllegalArgumentException.class, () -> new Device(null, null, null, null));
    }

    /**
     * Test case for the {@link Device#getDeviceName()} method.
     * Verifies that device returns its correct name.
     */
    @Test
    void getDeviceName() {
        assertEquals(new DeviceID("Heater").toString(), device.getDeviceName());
    }

    /**
     * Test case for the {@link Device#getDeviceStatus()} method.
     * Verifies that the device returns its correct status.
     */
    @Test
    void getStatus() {
        assertEquals(DeviceStatus.ACTIVE, device.getDeviceStatus());
    }

    /**
     * Test case for the {@link Device#getRoomID()} method.
     * Verifies that the device returns its correct room ID.
     */
    @Test
    void getRoomID() {
        assertEquals(roomIDDouble, device.getRoomID());
    }

    /**
     * Test case for checking if a device is active.
     * Verifies that a new device is active by default.
     */
    @Test
    void successCheckDeviceStatusActive() {
        assertTrue(device.isActive());
    }

    /**
     * Test case for deactivating a device.
     * Verifies that a device can be successfully deactivated and its status changes accordingly.
     */
    @Test
    void successDeactivateDevice() {
        device.deactivate();
        assertFalse(device.isActive());
    }

    /**
     * Test case for trying to deactivate an already deactivated device.
     * Verifies that attempting to deactivate an already deactivated device returns false.
     */
    @Test
    void failToDeactivateDevice() {
        device.deactivate();
        assertFalse(device.deactivate());
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct device ID (deviceName).
     */
    @Test
    void getDeviceIdentity() {
        String deviceName = "Heater";
        DeviceID deviceID = device.identity();
        assertEquals(deviceName, deviceID.toString());
    }

    /**
     * Test case that checks if Device instances are the same when they have the same ID.
     */
    @Test
    void successVerifyIfDeviceIsSameAsObject() {
        Device device2 = new Device(deviceIDDouble, deviceModelDouble, roomIDDouble);
        assertTrue(device.isSameAs(device2));
    }

    /**
     * Test case that fails when checking that Device instance isn't the same as another Device instance because they have different IDs.
     */
    @Test
    void failIsSameAsIfDeviceHasSameParametersButDifferentIDs() {
        DeviceID deviceID2 = mock(DeviceID.class);
        Device device2 = new Device(deviceID2, deviceModelDouble, roomIDDouble);
        assertFalse(device.isSameAs(device2));
    }

    @Test
    void failIsSameAsDifferentDeviceModel() {
        DeviceModel deviceModelDouble2 = mock(DeviceModel.class);
        Device device2 = new Device(deviceIDDouble, deviceModelDouble2, roomIDDouble);
        assertFalse(device.isSameAs(device2));
    }

    @Test
    void failIsSameAsDifferentDeviceStatus() {
        DeviceStatus deviceStatusDouble2 = mock(DeviceStatus.class);
        Device device2 = new Device(deviceIDDouble, deviceModelDouble, roomIDDouble, deviceStatusDouble2);
        assertFalse(device.isSameAs(device2));
    }

    @Test
    void failIsSameAsDifferentRoomID() {
        DeviceID deviceID2 = new DeviceID("Heater");
        RoomID roomIDdouble2 = mock(RoomID.class);
        Device device2 = new Device(deviceIDDouble, deviceModelDouble, roomIDdouble2);
        assertFalse(device.isSameAs(device2));
    }

    /**
     * Test case that fails when checking that Device instance isn't the same as Object because Object isn't a Device.
     */
    @Test
    void failIsSameAsIfDifferentClass() {
        Device device1 = device;
        Object object = new Object();
        assertFalse(device1.isSameAs(object));
    }

    /**
     * Test case that get device model.
     */
    @Test
    void getDeviceModel() {
        assertEquals(deviceModelDouble, device.getDeviceModel());
    }

}