package smarthome.persistence.springdata.repositoriesspringdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.RoomID;
import smarthome.persistence.jpa.datamodel.DeviceDataModel;
import smarthome.persistence.jpa.datamodel.MapperDeviceDataModel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static smarthome.domain.valueobjects.DeviceStatus.ACTIVE;

@SpringBootTest(classes = {DeviceRepositorySpringDataImp.class})
class DeviceRepositorySpringDataImpTest {
    /**
     * Mocked beans to be injected into the {@code DeviceRepositorySpringDataImp} class under test.
     */
    @MockBean
    private DeviceRepositorySpringData deviceRepositorySpringData;

    @MockBean
    private FactoryDevice factoryDevice;

    @MockBean
    private DeviceDataModel deviceDataModel;

    @MockBean
    private MapperDeviceDataModel mapperDeviceDataModel;

    /**
     * DeviceRepositorySpringDataImp class under test where mocked beans (attributes of the class) should be injected by Spring into.
     */
    @InjectMocks
    private DeviceRepositorySpringDataImp deviceRepositorySpringDataImp;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Successfully saves a device.
     */
    @Test
    void successSaveDevice() {
        //Create doubles to be used by method under test
        DeviceModel deviceModel = mock(DeviceModel.class);
        RoomID roomID = mock(RoomID.class);

        Device device = mock(Device.class);
        when(device.getDeviceModel()).thenReturn(deviceModel);
        when(device.getDeviceStatus()).thenReturn(ACTIVE);
        when(device.getRoomID()).thenReturn(roomID);

        assertNotNull(deviceRepositorySpringDataImp.save(device));
    }

    /**
     * Successfully updates a device.
     */
    @Test
    void successUpdateDevice() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceID);

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.findById(device.identity().toString())).thenReturn(Optional.of(deviceDataModel));
        when(deviceDataModel.updatedFromDomain(device)).thenReturn(true);

        assertNotNull(deviceRepositorySpringDataImp.update(device));
    }

    /**
     * Fail to update a device that does not exist within the repository.
     */
    @Test
    void failUpdateDeviceDoesNotExistInRepo() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceID);

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.findById(device.identity().toString())).thenReturn(Optional.empty());

        assertNull(deviceRepositorySpringDataImp.update(device));
    }

    /**
     * Fail to update a device. Device Data Model is not updated.
     */
    @Test
    void failUpdateDeviceDeviceDataModelNotUpdated() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceID);

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.findById(device.identity().toString())).thenReturn(Optional.of(deviceDataModel));
        when(deviceDataModel.updatedFromDomain(device)).thenReturn(false);

        assertNull(deviceRepositorySpringDataImp.update(device));
    }

    /**
     * Successfully updates a reserved device.
     */
    @Test
    void successUpdateReservedDevice() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceID);

        //Set behaviour of mocked beans
        when(deviceDataModel.updatedFromDomain(device)).thenReturn(true);

        assertNotNull(deviceRepositorySpringDataImp.updateReserved(device));
    }

    /**
     * Fail to update a reserved device. Device Data Model is not updated.
     */
    @Test
    void failUpdateReservedDeviceDataModelNotUpdated() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);
        when(device.identity()).thenReturn(deviceID);

        //Set behaviour of mocked beans
        when(deviceDataModel.updatedFromDomain(device)).thenReturn(false);

        assertNull(deviceRepositorySpringDataImp.updateReserved(device));
    }

    /**
     * Successfully retrieves all devices in the repository.
     */
    @Test
    void successFindAllEntities() {
        //Create double to be returned by injected mockedBeans.
        Device device = mock(Device.class);

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.findAll()).thenReturn(List.of(deviceDataModel));
        when(mapperDeviceDataModel.toDomainList(factoryDevice, List.of(deviceDataModel))).thenReturn(List.of(device));

        Iterable<Device> deviceList = deviceRepositorySpringDataImp.findAllEntities();
        assertNotNull(deviceList);
    }

    /**
     * Successfully finds a device by its DeviceID.
     */
    @Test
    void successFindEntityByID() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);
        when(deviceID.toString()).thenReturn("B8115");

        Optional<DeviceDataModel> optDeviceDataModel = Optional.of(deviceDataModel);

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.findById(deviceID.toString())).thenReturn(optDeviceDataModel);
        when(mapperDeviceDataModel.toDomain(factoryDevice, optDeviceDataModel.get())).thenReturn(device);

        Optional<Device> optDevice = deviceRepositorySpringDataImp.findEntityByID(deviceID);
        assertTrue(optDevice.isPresent());
    }

    /**
     * Fail to find a device by its DeviceID.
     */
    @Test
    void failFindEntityByID() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);
        when(deviceID.toString()).thenReturn("B8115");

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.findById(deviceID.toString())).thenReturn(Optional.empty());

        Optional<Device> optDevice = deviceRepositorySpringDataImp.findEntityByID(deviceID);
        assertFalse(optDevice.isPresent());
    }

    /**
     * Successfully finds a device by its DeviceID and reserves.
     */
    @Test
    void successFindEntityByIDAndReserve() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);
        when(deviceID.toString()).thenReturn("B8115");

        Optional<DeviceDataModel> optDeviceDataModel = Optional.of(deviceDataModel);

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.findById(deviceID.toString())).thenReturn(optDeviceDataModel);
        when(mapperDeviceDataModel.toDomain(factoryDevice, optDeviceDataModel.get())).thenReturn(device);

        Optional<Device> optDevice = deviceRepositorySpringDataImp.findEntityByIDAndReserve(deviceID);
        assertTrue(optDevice.isPresent());
    }

    /**
     * Fail to find a device by its DeviceID and reserves.
     */
    @Test
    void failFindEntityByIDAndReserve() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        Device device = mock(Device.class);
        when(deviceID.toString()).thenReturn("B8115");

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.findById(deviceID.toString())).thenReturn(Optional.empty());

        Optional<Device> optDevice = deviceRepositorySpringDataImp.findEntityByIDAndReserve(deviceID);
        assertFalse(optDevice.isPresent());
    }

    /**
     * Test if a device exists within the database.If device exists, the method should return true.
     */
    @Test
    void successContainsEntityById() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.existsById(deviceID.toString())).thenReturn(true);

        assertTrue(deviceRepositorySpringDataImp.containsEntityByID(deviceID));
    }

    /**
     * Successfully retrieves all devices with the specified roomID in the repository.
     */
    @Test
    void successFindEntityByRoomID() {
        //Create double to be returned by injected mockedBeans.
        Device device = mock(Device.class);
        RoomID roomID = mock(RoomID.class);

        //Set behaviour of mocked beans
        when(deviceRepositorySpringData.findAllByRoomID(roomID.toString())).thenReturn(List.of(deviceDataModel));
        when(mapperDeviceDataModel.toDomainList(factoryDevice, List.of(deviceDataModel))).thenReturn(List.of(device));

        Iterable<Device> deviceList = deviceRepositorySpringDataImp.findByRoomID(roomID);
        assertNotNull(deviceList);
    }

}