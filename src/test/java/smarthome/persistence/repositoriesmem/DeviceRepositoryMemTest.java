package smarthome.persistence.repositoriesmem;

import smarthome.domain.device.Device;
import smarthome.domain.valueobjects.DeviceID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.RoomID;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for DeviceRepositoryMem.
 */
class DeviceRepositoryMemTest {
    private DeviceID deviceId1 = mock(DeviceID.class);
    private DeviceID deviceId2 = mock(DeviceID.class);
    private DeviceID deviceId3 = mock(DeviceID.class);
    private Device deviceEnt1 = mock(Device.class);
    private Device deviceEnt2 = mock(Device.class);
    private Device deviceEnt3 = mock(Device.class);
    private Map<DeviceID, Device> map = new HashMap<>();

    /**
     * Fill up the map to be used as double of data loaded into repository before each test
     */
    @BeforeEach
    void setUP() {
        map.put(deviceId1, deviceEnt1);
        map.put(deviceId2, deviceEnt2);
    }

    /**
     * Test the successful addition of a Device entity to the repository
     */
    @Test
    void successAddEntityToMap() {
        when(deviceEnt3.identity()).thenReturn(deviceId3);
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);
        deviceRepositoryMem.save(deviceEnt3);

        assertTrue(deviceRepositoryMem.containsEntityByID(deviceId3));
    }

    /**
     * Successful retrieval of all entities in the repository
     */
    @Test
    void successRetrieveAllEntitiesInData() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);

        assertEquals(deviceRepositoryMem.findAllEntities(), map.values());
    }

    /**
     * Successfully retrieve a Device entity that based on its ID
     */
    @Test
    void successFindDeviceWithSpecificId() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);

        when(deviceEnt1.identity()).thenReturn(deviceId1);
        Optional<Device> retrievedSensor = deviceRepositoryMem.findEntityByID(deviceId1);
        Device device = retrievedSensor.get();

        assertEquals(device, deviceEnt1);
    }

    /**
     * Fail to retrieve a Device entity with an ID not present in the repository
     */
    @Test
    void failFindDeviceWithSpecificId() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);
        DeviceID nonExistentID = mock(DeviceID.class);

        when(nonExistentID.toString()).thenReturn("nonExistentID");
        Optional<Device> retrievedDevice = deviceRepositoryMem.findEntityByID(nonExistentID);

        assertThrows(NoSuchElementException.class, () -> retrievedDevice.get());
    }

    /**
     * Successful find of a Device entity in the repo based in a given ID
     */
    @Test
    void successContainsEntity() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);

        when(deviceEnt1.identity()).thenReturn(deviceId1);

        assertTrue(deviceRepositoryMem.containsEntityByID(deviceId1));
    }

    /**
     * Fail to find a Device entity in the repository based on a given ID
     */
    @Test
    void failContainsEntity() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);
        DeviceID nonExistentID = mock(DeviceID.class);

        when(nonExistentID.toString()).thenReturn("nonExistentID");

        assertFalse(deviceRepositoryMem.containsEntityByID(nonExistentID));
    }

    /**
     * Tests the successful update of a Device entity in the repository.
     * Returns the updated Device entity if successful.
     */
    @Test
    void successfullyUpdateDevice() {
        when(deviceEnt1.identity()).thenReturn(deviceId1);

        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);
        assertEquals(deviceEnt1, deviceRepositoryMem.update(deviceEnt1));
    }

    /**
     * Tests the failure to update a Device entity not present in the repository.
     * Returns null since the Device entity is not present in the repository.
     */
    @Test
    void failUpdateDeviceNotInRepository() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);
        DeviceID notPresentID = mock(DeviceID.class);
        when(deviceEnt1.identity()).thenReturn(notPresentID);

        assertNull(deviceRepositoryMem.update(deviceEnt1));
    }

    /**
     * Tests the successful update of a reserved Device entity in the repository.
     * Returns the updated Device entity if successful.
     */
    @Test
    void successfullyUpdateReservedDevice() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);
        when(deviceEnt1.identity()).thenReturn(deviceId1);
        Optional<Device> foundEntity = deviceRepositoryMem.findEntityByIDAndReserve(deviceId1);
        Device device = foundEntity.get();

        assertEquals(device, deviceRepositoryMem.updateReserved(device));
    }

    /**
     * Tests the failure scenario when attempting to update a Device entity that is not reserved.
     * Return null if the Device entity is not reserved and therefore cannot be updated.
     */
    @Test
    void failUpdateReservedDeviceNotReserved() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);

        assertNull(deviceRepositoryMem.updateReserved(deviceEnt3));
    }

    /**
     * Tests the failure scenario when attempting to update a Device entity with a different ID from the reserved one.
     * Returns null if the Device entity ID does not match the reserved ID and therefore cannot be updated.
     */
    @Test
    void failUpdateReservedDeviceNotSameID() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);
        when(deviceEnt1.identity()).thenReturn(deviceId1);
        deviceRepositoryMem.findEntityByIDAndReserve(deviceId1);
        Device deviceDouble = mock(Device.class);
        DeviceID notPresentID = mock(DeviceID.class);
        when(deviceDouble.identity()).thenReturn(notPresentID);

        assertNull(deviceRepositoryMem.updateReserved(deviceDouble));
    }

    /**
     * Tests the successful retrieval and reservation of a Device entity by ID.
     * Returns the reserved Device entity if found and reserved successfully.
     */
    @Test
    void successfullyFindDeviceByIDAndReserve() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);

        Optional<Device> foundEntity = deviceRepositoryMem.findEntityByIDAndReserve(deviceId1);
        Device device = foundEntity.get();

        assertEquals(device, deviceEnt1);
    }

    /**
     * Tests the failure to find and reserve a Device entity when the ID is not present in the repository.
     * Returns an empty optional since the entity is not present in the repository.
     */
    @Test
    void failFindDeviceByIDAndReserveNotPresent() {
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);
        DeviceID notPresentID = mock(DeviceID.class);

        Optional<Device> foundEntity = deviceRepositoryMem.findEntityByIDAndReserve(notPresentID);

        assertThrows(NoSuchElementException.class, foundEntity::get);
    }

    /**
     * Successfully retrieves all devices with the specified roomID in the repository.
     */
    /**
     * Successful findByDeviceID method to successfully retrieve all sensors of a given device
     */
    @Test
    void successfullyRetrievesDeviceFindByRoomID() {
        //Mocking the deviceID
        RoomID roomID = mock(RoomID.class);
        when(roomID.toString()).thenReturn("roomID");
        when(deviceEnt1.getRoomID()).thenReturn(roomID);
        when(deviceEnt2.getRoomID()).thenReturn(roomID);

        //Repository instantiation and expected devices
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(map);
        Iterable<Device> expectedDeviceList = new ArrayList<>(map.values());

        Iterable<Device> actualDeviceList = deviceRepositoryMem.findByRoomID(roomID);

        assertEquals(expectedDeviceList, actualDeviceList);

    }
}
