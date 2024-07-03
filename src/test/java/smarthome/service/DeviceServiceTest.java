package smarthome.service;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.service.internaldto.InternalDeviceDTO;
import smarthome.util.exceptions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code DeviceService} class.
 */
@SpringBootTest(classes = {DeviceService.class})
class DeviceServiceTest {

    /**
     * FactoryDevice double to be used in the tests.
     */
    @MockBean
    private FactoryDevice factoryDevice;

    /**
     * DeviceRepository double to be used in the tests.
     */
    @MockBean
    private DeviceRepository deviceRepository;

    /**
     * RoomRepository double to be used in the tests.
     */
    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private HouseRepository houseRepository;

    /**
     * {@code DeviceService} class under test.
     */
    @Autowired
    private DeviceService deviceService;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for the constructor of the DeviceService class.
     */
    @Test
    void successConstructDeviceService() {
        assertNotNull(deviceService);
    }

    /**
     * Test for the successful creation of a new device in a room.
     */
    @Test
    void successDeviceCreation() {
        //Create doubles of objects that are method parameters
        DeviceID doubleDeviceID = mock(DeviceID.class);
        DeviceModel doubleDeviceModel = mock(DeviceModel.class);
        RoomID doubleRoomID = mock(RoomID.class);

        //Set repository double behaviour
        when(roomRepository.containsEntityByID(doubleRoomID)).thenReturn(true);

        //Create and set behaviour of device, necessary to set deviceRepository behaviour
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(doubleDeviceID);
        when(deviceRepository.containsEntityByID(device.identity())).thenReturn(false);
        when(deviceRepository.save(device)).thenReturn(device);

        //Double factory output
        when(factoryDevice.createDevice(doubleDeviceID, doubleDeviceModel, doubleRoomID)).thenReturn(device);

        // Act
        DeviceID deviceID = deviceService.createDeviceAndSaveToRepository(doubleDeviceID, doubleDeviceModel, doubleRoomID);

        // Assert
        assertNotNull(deviceID);
    }

    /**
     * Test for the creation of a new device with null RoomID.
     */
    @Test
    void failDeviceCreationNullRoom() {
        //Create doubles of objects that are method parameters
        DeviceID doubleDeviceID = mock(DeviceID.class);
        DeviceModel doubleDeviceModel = mock(DeviceModel.class);
        RoomID doubleRoomID = mock(RoomID.class);

        //Set repository double behaviour
        when(roomRepository.containsEntityByID(doubleRoomID)).thenReturn(true);

        //Create and set behaviour of doubleDevice, necessary to set deviceRepository behaviour
        Device doubleDevice = mock(Device.class);
        when(doubleDevice.identity()).thenReturn(doubleDeviceID);
        when(deviceRepository.containsEntityByID(doubleDevice.identity())).thenReturn(false);
        when(deviceRepository.save(doubleDevice)).thenReturn(doubleDevice);

        //Double factory output
        when(factoryDevice.createDevice(doubleDeviceID, doubleDeviceModel, null)).thenReturn(doubleDevice);

        assertThrows(RoomNotFoundException.class, () -> deviceService.createDeviceAndSaveToRepository(doubleDeviceID, doubleDeviceModel, null));
    }

    /**
     * Test for the creation of a new device in a room that doesn't exist in Room Repository.
     */
    @Test
    void failDeviceCreationNonExistentRoom() {
        //Create doubles of objects that are method parameters
        DeviceID doubleDeviceID = mock(DeviceID.class);
        DeviceModel doubleDeviceModel = mock(DeviceModel.class);
        RoomID doubleRoomID = mock(RoomID.class);

        //Set repository double behaviour
        when(roomRepository.containsEntityByID(doubleRoomID)).thenReturn(false); //room doesn't exist

        //Create and set behaviour of doubleDevice, necessary to set deviceRepository behaviour
        Device doubleDevice = mock(Device.class);
        when(doubleDevice.identity()).thenReturn(doubleDeviceID);
        when(deviceRepository.containsEntityByID(doubleDevice.identity())).thenReturn(false);
        when(deviceRepository.save(doubleDevice)).thenReturn(doubleDevice);

        //Double factory output
        when(factoryDevice.createDevice(doubleDeviceID, doubleDeviceModel, doubleRoomID)).thenReturn(doubleDevice);

        assertThrows(RoomNotFoundException.class, () -> deviceService.createDeviceAndSaveToRepository(doubleDeviceID, doubleDeviceModel, doubleRoomID));
    }

    /**
     * Test for the creation of device with an ID (deviceName) that already exists in Device Repository.
     */
    @Test
    void failCreateDeviceThatAlreadyExists() {
        //Create doubles of objects that are method parameters
        DeviceID doubleDeviceID = mock(DeviceID.class);
        DeviceModel doubleDeviceModel = mock(DeviceModel.class);
        RoomID doubleRoomID = mock(RoomID.class);

        //Set repository double behaviour
        when(roomRepository.containsEntityByID(doubleRoomID)).thenReturn(true);

        //Create and set behaviour of doubleDevice, necessary to set deviceRepository behaviour
        Device doubleDevice = mock(Device.class);
        when(doubleDevice.identity()).thenReturn(doubleDeviceID);
        when(deviceRepository.containsEntityByID(doubleDevice.identity())).thenReturn(true); //device already exists
        when(deviceRepository.save(doubleDevice)).thenReturn(doubleDevice);

        //Double factory output
        when(factoryDevice.createDevice(doubleDeviceID, doubleDeviceModel, doubleRoomID)).thenReturn(doubleDevice);

        assertThrows(DeviceAlreadyExistsException.class, () -> deviceService.createDeviceAndSaveToRepository(doubleDeviceID, doubleDeviceModel, doubleRoomID));
    }

    /**
     * Test for the creation of device with invalid parameters.
     */
    @Test
    void failCreateDeviceInvalidParametersNullDevice() {
        //Create doubles of objects that are method parameters
        DeviceID doubleDeviceID = mock(DeviceID.class);
        DeviceModel doubleDeviceModel = mock(DeviceModel.class);
        RoomID doubleRoomID = mock(RoomID.class);

        //Set repository double behaviour
        when(roomRepository.containsEntityByID(doubleRoomID)).thenReturn(true);

        //Create and set behaviour of doubleDevice, necessary to set deviceRepository behaviour
        Device doubleDevice = mock(Device.class);
        when(doubleDevice.identity()).thenReturn(doubleDeviceID);
        when(deviceRepository.containsEntityByID(doubleDevice.identity())).thenReturn(false);
        when(deviceRepository.save(doubleDevice)).thenReturn(doubleDevice);

        //Double factory output
        when(factoryDevice.createDevice(doubleDeviceID, null, doubleRoomID)).thenReturn(null);

        assertThrows(DeviceNotCreatedException.class, () -> deviceService.createDeviceAndSaveToRepository(doubleDeviceID, null, doubleRoomID));
    }

    /**
     * Test for the deactivation of a device with success.
     */
    @Test
    void successfulDeactivateDevice() {
        //Create double of deviceID
        DeviceID deviceIDDouble = mock(DeviceID.class);

        //Create double of device and set behaviour to deactivate
        Device doubleDevice = mock(Device.class);
        when(deviceRepository.findEntityByID(deviceIDDouble)).thenReturn(Optional.of(doubleDevice));
        when(doubleDevice.deactivate()).thenReturn(true);
        when(deviceRepository.update(doubleDevice)).thenReturn(doubleDevice);

        //Act
        boolean result = deviceService.deactivateDevice(deviceIDDouble);

        //Assert
        assertTrue(result);
    }

    /**
     * Test for the deactivation of a device that is already deactivated.
     */
    @Test
    void failToDeactivateDeviceAlreadyDeactivated() {
        //Create double of deviceID
        DeviceID deviceIDDouble = mock(DeviceID.class);

        //Create double of device and set behaviour to deactivate
        Device doubleDevice = mock(Device.class);
        when(deviceRepository.findEntityByID(deviceIDDouble)).thenReturn(Optional.of(doubleDevice));
        when(doubleDevice.deactivate()).thenReturn(false); //device already deactivated, returns false
        when(deviceRepository.update(doubleDevice)).thenReturn(doubleDevice);

        //Act
        boolean result = deviceService.deactivateDevice(deviceIDDouble);

        //Assert
        assertFalse(result);
    }

    /**
     * Test for the deactivation of a device that doesn't exist.
     */
    @Test
    void failToDeactivateNonexistentDevice() {
        //Create double of deviceID
        DeviceID deviceIDDouble = mock(DeviceID.class);

        //Create double of device and set behaviour to deactivate
        Device doubleDevice = mock(Device.class);
        when(deviceRepository.findEntityByID(deviceIDDouble)).thenReturn(Optional.ofNullable(null)); //device doesn't exist
        when(doubleDevice.deactivate()).thenReturn(true);
        when(deviceRepository.update(doubleDevice)).thenReturn(doubleDevice);

        assertThrows(DeviceNotFoundException.class, () -> deviceService.deactivateDevice(deviceIDDouble));
    }

    /**
     * Test for the deactivation of an invalid (null) device.
     */
    @Test
    void failToDeactivateNullDevice() {
        //Create deviceID
        DeviceID deviceIDDouble = null;

        assertThrows(DeviceNotFoundException.class, () -> deviceService.deactivateDevice(deviceIDDouble));
    }

    /**
     * Test to get the list of devices with a valid roomID.
     */
    @Test
    void successGetListOfDevicesByRoomID() {
        //Create double of RoomID and set its behaviour
        RoomID doubleRoomID = mock(RoomID.class);
        when(roomRepository.containsEntityByID(doubleRoomID)).thenReturn(true);

        //Create device doubles and set behaviour to obtain the list of all devices
        List<Device> listOfDevicesInRepo = new ArrayList<>();
        Device doubleDevice = mock(Device.class);
        listOfDevicesInRepo.add(doubleDevice);

        //Set the behaviour of method in device repository
        when(deviceRepository.findByRoomID(doubleRoomID)).thenReturn(List.of(doubleDevice));

        //Create deviceID doubles
        DeviceID deviceIDDouble = mock(DeviceID.class);
        when(doubleDevice.identity()).thenReturn(deviceIDDouble);

        //Act
        List<DeviceID> result = deviceService.getListOfDevicesInRoom(doubleRoomID);

        //Assert
        assertEquals(1, result.size());
    }

    /**
     * Fail to get the list of devices with a null roomID. The method should return null.
     */
    @Test
    void failGetDeviceInvalidRoomDTO() {
        //Set the behaviour of method in room repository with a non-existent room.
        when(roomRepository.containsEntityByID(null)).thenReturn(false);

        assertThrows(RoomNotFoundException.class, () -> deviceService.getListOfDevicesInRoom(null));
    }

    /**
     * Fail to get the list of devices in a room that is null. The method should return an empty list.
     */
    @Test
    void getDevicesEmptyWhenRoomIDNotAssociatedToDevices() {
        //Create double of RoomID and set its behaviour
        RoomID doubleRoomID = mock(RoomID.class);
        when(roomRepository.containsEntityByID(doubleRoomID)).thenReturn(true);

        //Set the behaviour of method in device repository with a room with no devices associated.
        when(deviceRepository.findByRoomID(doubleRoomID)).thenReturn(List.of());

        //Act
        List<DeviceID> result = deviceService.getListOfDevicesInRoom(doubleRoomID);

        //Assert
        assertEquals(0, result.size());
    }

    /**
     * Test to get the device of the house.
     */

    @Test
    void successGetListOfDevicesInHouse() {
        // Mock the necessary objects
        HouseID houseIdDouble = mock(HouseID.class);

        RoomID roomIdDouble = mock(RoomID.class);
        Room room = mock(Room.class);

        DeviceID deviceIdDouble = mock(DeviceID.class);
        Device deviceDouble = mock(Device.class);

        // Set behaviour for room
        when(room.identity()).thenReturn(roomIdDouble);

        // Set behaviour for repository methods
        when(houseRepository.containsEntityByID(houseIdDouble)).thenReturn(true);
        when(roomRepository.findByHouseID(houseIdDouble)).thenReturn(List.of(room));
        when(deviceRepository.findByRoomID(roomIdDouble)).thenReturn(List.of(deviceDouble));

        // Set behaviour for device
        when(deviceDouble.identity()).thenReturn(deviceIdDouble);
        when(deviceDouble.getRoomID()).thenReturn(roomIdDouble);

        // Act
        List<InternalDeviceDTO> result = deviceService.getListOfDevicesInHouse(houseIdDouble);

        // Assert
        assertEquals(1, result.size());
    }

    /**
     * Test to fail getting the device of the house.
     */

    @Test
    void failGetListOfDevicesInHouse() {

        // Mock the necessary objects
        HouseID houseIdDouble = mock(HouseID.class);

        RoomID roomIdDouble = mock(RoomID.class);
        Room room = mock(Room.class);

        DeviceID deviceIdDouble = mock(DeviceID.class);
        Device deviceDouble = mock(Device.class);

        // Set behaviour for room
        when(room.identity()).thenReturn(roomIdDouble);

        // Set behaviour for repository methods
        when(houseRepository.containsEntityByID(houseIdDouble)).thenReturn(false);

        // Assert
        assertThrows(HouseNotFoundException.class, () -> deviceService.getListOfDevicesInHouse(houseIdDouble));
    }


}