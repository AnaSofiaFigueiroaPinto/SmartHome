package smarthome.domain.device;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.DeviceStatus;
import smarthome.domain.valueobjects.RoomID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test cases for {@code ImpFactoryDevice} class.
 */
@SpringBootTest(classes = {ImpFactoryDeviceTest.class})
class ImpFactoryDeviceTest {

    private ImpFactoryDevice impFactoryDevice;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    public void setUp() {
        impFactoryDevice = new ImpFactoryDevice();
    }

    /**
     * Test the creation of a device with valid parameters.
     */
    @Test
    void testCreateDeviceValidParameters() {
        // Arrange
        DeviceID deviceID = mock(DeviceID.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        RoomID roomID = mock(RoomID.class);

        // Act
        Device device = impFactoryDevice.createDevice(deviceID, deviceModel, roomID);

        // Assert
        assertNotNull(device);
    }

    /**
     * Test the creation of a device with valid parameters including device status.
     */
    @Test
    void testCreateDeviceWithStatusValidParameters() {
        // Arrange
        DeviceID deviceID = mock(DeviceID.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        RoomID roomID = mock(RoomID.class);
        DeviceStatus deviceStatus = DeviceStatus.ACTIVE;

        // Act
        Device device = impFactoryDevice.createDevice(deviceID, deviceModel, roomID, deviceStatus);

        // Assert
        assertNotNull(device);
    }


    /**
     * Test the creation of a device with invalid DeviceID including device status.
     */
    @Test
    void testCreateDeviceWithStatusInvalidParametersDeviceID() {
        // Arrange
        DeviceModel deviceModel = mock(DeviceModel.class);
        RoomID roomID = mock(RoomID.class);
        DeviceStatus deviceStatus = DeviceStatus.ACTIVE;

        // Act
        Device device = impFactoryDevice.createDevice(null, deviceModel, roomID, deviceStatus);

        // Assert
        assertNull(device);
    }

    /**
     * Test the creation of a device with invalid DeviceModel including device status.
     */
    @Test
    void testCreateDeviceWithStatusInvalidParametersDeviceModel() {
        // Arrange
        DeviceID deviceID = mock(DeviceID.class);
        RoomID roomID = mock(RoomID.class);
        DeviceStatus deviceStatus = DeviceStatus.DEACTIVATED;

        // Act
        Device device = impFactoryDevice.createDevice(deviceID, null, roomID, deviceStatus);

        // Assert
        assertNull(device);
    }

    /**
     * Test the creation of a device with invalid RoomID including device status.
     */
    @Test
    void testCreateDeviceWithStatusInvalidParametersRoomID() {
        // Arrange
        DeviceID deviceID = mock(DeviceID.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        DeviceStatus deviceStatus = DeviceStatus.ACTIVE;

        // Act
        Device device = impFactoryDevice.createDevice(deviceID, deviceModel, null, deviceStatus);

        // Assert
        assertNull(device);
    }
}