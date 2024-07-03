package smarthome.controllercli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.device.ImpFactoryDevice;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.house.ImpFactoryHouse;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.RoomDTO;
import smarthome.persistence.jpa.datamodel.*;
import smarthome.persistence.jpa.repositoriesjpa.DeviceRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.HouseRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.RoomRepositoryJPAImp;
import smarthome.persistence.repositoriesmem.DeviceRepositoryMem;
import smarthome.persistence.repositoriesmem.HouseRepositoryMem;
import smarthome.persistence.repositoriesmem.RoomRepositoryMem;
import smarthome.persistence.springdata.repositoriesspringdata.*;
import smarthome.service.DeviceService;
import smarthome.service.RoomService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code AddNewDeviceToRoomController} class.
 */
class AddNewDeviceToRoomControllerTest {

    /**
     * Controller instance to be used in the tests.
     */
    private AddNewDeviceToRoomController controller;

    @BeforeEach
    void setUp() {
        //Instantiate needed factories
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryHouse factoryHouse = new ImpFactoryHouse();

        //Instantiate needed repositories with a blank data map
        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(deviceData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem(houseData);

        //Instantiate House object
        House house = factoryHouse.createHouseWithOutLocation();
        houseData.put(house.identity(), house);

        //Instantiate needed services
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryMem, roomRepositoryMem, houseRepositoryMem);
        RoomService roomService = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);

        //Instantiate controllers
        controller = new AddNewDeviceToRoomController(deviceService);

        //Create rooms
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);
        RoomDimensions roomDimensions1 = new RoomDimensions(2, 1, 2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);
        RoomDimensions roomDimensions2 = new RoomDimensions(20.0, 15.0, 10.0);

        roomService.createRoomAndSave(roomID1, roomFloor1, roomDimensions1, house.identity());
        roomService.createRoomAndSave(roomID2, roomFloor2, roomDimensions2, house.identity());
    }

    /**
     * Test case to verify that the object device is created with success
     */
    @Test
    void successDeviceCreationInRoomRepoMemory() {
        // Arrange
        String deviceName = "Lamp";
        String model = "Lamp Model";
        // Create roomDTO that matches a domain object
        RoomDTO roomDTO = new RoomDTO("Bedroom", 2, 2, 1, 2);

        // Act
        DeviceDTO deviceDTO = controller.createDeviceAndSaveToRepository(roomDTO, deviceName, model);

        // Assert
        assertNotNull(deviceDTO);
    }

    /**
     * Test case to verify that the creation of device fails when the room is null
     */
    @Test
    void failDeviceCreationNullRoomRepoMemory() {
        // Arrange
        String deviceName = "Lamp";
        String model = "Lamp Model";

        // Act
        DeviceDTO deviceDTO = controller.createDeviceAndSaveToRepository(null, deviceName, model);

        // Assert
        assertNull(deviceDTO);
    }

    /**
     * Test case to verify that the creation of device fails when the room does not exist
     */
    @Test
    void failDeviceCreationNonExistentRoomRepoMemory() {
        //Arrange
        String deviceName = "Lamp";
        String model = "Lamp Model";
        //Create roomDTO that does not match a domain object
        RoomDTO roomDTO = new RoomDTO("Inexistent Room", 2, 2, 1, 2);

        // Act
        DeviceDTO deviceDTO = controller.createDeviceAndSaveToRepository(roomDTO, deviceName, model);

        // Assert
        assertNull(deviceDTO);
    }

    /**
     * Test case to verify that the creation of device fails when the device is null
     */
    @Test
    void failDeviceCreationNullDeviceNameRepoMemory() {
        // Arrange
        String model = "Lamp Model";
        // Create roomDTO that matches a domain object
        RoomDTO roomDTO = new RoomDTO("Bedroom", 2, 2, 1, 2);

        // Act
        DeviceDTO deviceDTO = controller.createDeviceAndSaveToRepository(roomDTO, null, model);

        // Assert
        assertNull(deviceDTO);
    }

    /**
     * Test case to verify that the creation of device fails when the model is null
     */
    @Test
    void failDeviceCreationNullModelRepoMemory() {
        // Arrange
        String deviceName = "Lamp";
        // Create roomDTO that matches a domain object
        RoomDTO roomDTO = new RoomDTO("Bedroom", 2, 2, 1, 2);

        // Act
        DeviceDTO deviceDTO = controller.createDeviceAndSaveToRepository(roomDTO, deviceName, null);

        // Assert
        assertNull(deviceDTO);
    }

    /**
     * Test to ensure successful creation of a device in RoomRepositoryJPA.
     * This test checks if a device can be created in a room using JPA implementation.
     */
    @Test
    void successDeviceCreationInRoomRepoJPA() {
        String deviceName = "Lamp";
        String model = "Lamp Model";
        RoomID roomID = new RoomID("Bedroom");
        RoomFloor roomFloor= new RoomFloor(2);
        RoomDimensions roomDimensions= new RoomDimensions(2,1,2);
        HouseID houseID = new HouseID("houseID");

        //Instantiate needed factories
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryRoom roomFactory = new ImpFactoryRoom();
        FactoryHouse houseFactory = new ImpFactoryHouse();

        // Creation RoomDataModel
        Room room = roomFactory.createRoom(roomID, roomFloor, roomDimensions, houseID);
        RoomDataModel roomDataModel = new RoomDataModel(room);

        // Mock Entity Manager and Transaction
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.getTransaction()).thenReturn(transaction);
        when(manager.find(RoomDataModel.class, "Bedroom")).thenReturn(roomDataModel);
        when(manager.find(DeviceDataModel.class, deviceName)).thenReturn(null);

        //Repository
        DeviceRepository deviceRepository = new DeviceRepositoryJPAImp(factoryDevice, manager);
        RoomRepository roomRepository = new RoomRepositoryJPAImp(roomFactory, manager);
        HouseRepository houseRepository = new HouseRepositoryJPAImp(houseFactory, manager);

        // Device Service
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);

        // Controller
        AddNewDeviceToRoomController controller = new AddNewDeviceToRoomController(deviceService);

        // Creation RoomDTO
        RoomDTO roomDTO = new RoomDTO("Bedroom", 2, 2, 1, 2);

        //Act
        DeviceDTO deviceDTO = controller.createDeviceAndSaveToRepository(roomDTO, deviceName, model);

        assertNotNull(deviceDTO);
    }

    /**
     * Test to ensure successful creation of a device in RoomRepositorySpringData.
     * This test checks if a device can be created in a room using Spring Data implementation.
     */
    @Test
    void successDeviceCreationInRoomRepoSpringData() {
        String deviceName = "Lamp";
        String model = "Lamp Model";
        String roomName = "Bedroom";
        DeviceID deviceID= new DeviceID(deviceName);
        DeviceModel deviceModel= new DeviceModel(model);
        RoomID roomID= new RoomID(roomName);

        //Instantiate needed factories
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryHouse factoryHouse = new ImpFactoryHouse();

        // HouseRepository
        HouseRepositorySpringData houseRepositorySpringDataDouble = mock(HouseRepositorySpringData.class);
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        HouseRepository houseRepository = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringDataDouble, mapperHouseDataModel);

        //Instantiation of RoomMapper
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();

        // RoomRepository
        RoomRepositorySpringData roomRepositorySpringDataDouble = mock(RoomRepositorySpringData.class);

        when(roomRepositorySpringDataDouble.existsById(roomName)).thenReturn(true);
        RoomRepository roomRepository = new RoomRepositorySpringDataImp(roomRepositorySpringDataDouble,
                factoryRoom, mapperRoomDataModel);

        // Creation DeviceDataModel
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);

        //MapperDeviceDataDoubleDouble
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();

        // Device Repository
        DeviceRepositorySpringData deviceRepositorySpringDataDouble = mock(DeviceRepositorySpringData.class);

        DeviceRepository deviceRepository = new DeviceRepositorySpringDataImp(factoryDevice,
                deviceRepositorySpringDataDouble, mapperDeviceDataModel);

        when(deviceRepositorySpringDataDouble.existsById(deviceName)).thenReturn(false);
        when(deviceRepositorySpringDataDouble.save(deviceDataModel)).thenReturn(deviceDataModel);

        // Instantiate needed services
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);

        //Instantiate controllers
        AddNewDeviceToRoomController controller = new AddNewDeviceToRoomController(deviceService);

        // Create roomDTO
        RoomDTO roomDTO = new RoomDTO(roomName, 2, 2, 1, 2);

        // Act
        DeviceDTO deviceDTO = controller.createDeviceAndSaveToRepository(roomDTO, deviceName, model);

        assertNotNull(deviceDTO);
    }
}