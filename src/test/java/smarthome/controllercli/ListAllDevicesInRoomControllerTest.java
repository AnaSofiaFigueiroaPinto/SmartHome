package smarthome.controllercli;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
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
import smarthome.service.HouseService;
import smarthome.service.RoomService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Test cases for the {@code ListAllDevicesInRoomController} class.
 */
class ListAllDevicesInRoomControllerTest {

    /**
     * Factory  to create house objects.
     */
    private FactoryHouse factoryHouse;

    /**
     * Factory to create room objects.
     */
    private FactoryRoom factoryRoom;

    /**
     * Factory to create device objects.
     */
    private FactoryDevice factoryDevice;

    /**
     * Set up the factories needed for all tests.
     */
    @BeforeEach
    void setUp() {
        // Instantiate all needed factories
        factoryHouse = new ImpFactoryHouse();
        factoryRoom = new ImpFactoryRoom();
        factoryDevice = new ImpFactoryDevice();
    }

    //------------------- INTEGRATION TESTS WITH REPOSITORIES MEM -------------------

    /**
     * Test of success to get the list of devices in a room with devices.
     */
    @Test
    void successGetListOfDevicesFromRoomWithDevices() {
        /////////////// TEST SETUP ///////////////

        // Create blank data maps as data source for repositories
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        // Instantiate all needed services
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        // Create house
        Location location = new Location(
                new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"),
                new GPSCode(41.178553, -8.608035));
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        // Create room
        RoomID roomID = new RoomID("Living room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        // Create device
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        // Instantiate controller to be tested
        ListAllDevicesInRoomController controller = new ListAllDevicesInRoomController(deviceService);

        /////////////// TEST CASE ///////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        // List devices in selected room
        RoomDTO roomDTO = new RoomDTO(selectedRoomID.toString());
        List<DeviceDTO> result = controller.getListOfDevicesDTOInRoom(roomDTO);

        // Assert list is not empty and has the correct device
        assertEquals(1, result.size());
        assertEquals("Heater", result.get(0).deviceName);
    }

    /**
     * Test of success to get an empty list of devices in a room.
     */
    @Test
    void successGetListOfDevicesFromRoomWithoutDevices() {
        /////////////// TEST SETUP ///////////////

        // Create blank data maps as data source for repositories
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        // Instantiate all needed services
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        // Create house
        Location location = new Location(
                new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"),
                new GPSCode(41.178553, -8.608035));
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        // Create room
        RoomID roomID = new RoomID("Living room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        // No devices in room

        // Instantiate controller to be tested
        ListAllDevicesInRoomController controller = new ListAllDevicesInRoomController(deviceService);

        /////////////// TEST CASE ///////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        // List devices in selected room
        RoomDTO roomDTO = new RoomDTO(selectedRoomID.toString());
        List<DeviceDTO> result = controller.getListOfDevicesDTOInRoom(roomDTO);

        // Assert list is empty
        assertTrue(result.isEmpty());
    }

    /**
     * Test fails when trying to get the list of devices in a room that is null.
     */
    @Test
    void failGetDeviceListWhenRoomIsNull() {
        /////////////// TEST SETUP ///////////////
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        // Create blank data maps as data source for repositories
        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        // Instantiate all needed services
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);

        // Null room
        RoomDTO nullRoomDTO = null;

        // Instantiate controller to be tested
        ListAllDevicesInRoomController controller = new ListAllDevicesInRoomController(deviceService);

        /////////////// TEST CASE ///////////////

        // List devices in selected room
        List<DeviceDTO> result = controller.getListOfDevicesDTOInRoom(nullRoomDTO);

        // Assert that the list of devices of a null room is null
        assertNull(result);
    }

    //---------------------- INTEGRATION TESTS WITH REPOSITORIES JPA ---------------------------------------------------

    /**
     * Test of success to get the list of devices in a room with devices using JPA repositories.
     */
    @Test
    void successGetListOfDevicesJPARepo() {
        //////////////////// TEST SETUP ////////////////////

        // Create double for EntityManager to substitute the database dependency
        EntityManager managerDouble = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(managerDouble.getTransaction()).thenReturn(transaction);

        // Create repositories JPA
        HouseRepository houseRepositoryJPA = new HouseRepositoryJPAImp(factoryHouse, managerDouble);
        RoomRepository roomRepositoryJPA = new RoomRepositoryJPAImp(factoryRoom, managerDouble);
        DeviceRepository deviceRepositoryJPA = new DeviceRepositoryJPAImp(factoryDevice, managerDouble);

        // Instantiate all needed services
        HouseService houseService = new HouseService(houseRepositoryJPA, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryJPA, roomRepositoryJPA, houseRepositoryJPA);
        RoomService roomService = new RoomService(roomRepositoryJPA, factoryRoom, houseRepositoryJPA);

        // Instantiate controller to be tested
        ListAllDevicesInRoomController controller = new ListAllDevicesInRoomController(deviceService);

        // HOUSE -------------------------------------------------------------------------------------------------------
        // Create house
        Location location = new Location(
                new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"),
                new GPSCode(41.178553, -8.608035));
        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(managerDouble.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        // Set behaviour when checking if house exists - containsEntityByID() in HouseRepositoryJPAImp:
        // use factory to create an object that is equal to the one created by services
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseID, location);
        // create the respective datamodel
        HouseDataModel houseDataModel = new HouseDataModel(house);
        // set the find method to return the data model
        when(managerDouble.find(HouseDataModel.class, houseID.toString())).thenReturn(houseDataModel);

        // ROOM --------------------------------------------------------------------------------------------------------
        // Create room
        RoomID roomID = new RoomID("Living room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepositoryJPAImp:
        Query queryRoomDouble = mock(Query.class);
        when(managerDouble.createQuery("SELECT e FROM RoomDataModel e WHERE e.houseID = :houseID")).thenReturn(queryRoomDouble);
        // use factory to create an object that is equal to the one created by services
        Room room = factoryRoom.createRoom(roomID, roomFloor, roomDimensions, houseID);
        // create a list of data models to return
        RoomDataModel roomDataModel = new RoomDataModel(room);
        List<RoomDataModel> listOfRoomDataModelsInRepo = List.of(roomDataModel);
        when(queryRoomDouble.getResultList()).thenReturn(listOfRoomDataModelsInRepo);

        // Set behaviour to find room when creating a device - containsEntityByID() in RoomRepositoryJPAImp:
        // set the find method to return the data model
        when(managerDouble.find(RoomDataModel.class, roomID.toString())).thenReturn(roomDataModel);

        // DEVICE ------------------------------------------------------------------------------------------------------
        // Create device - The device creation relies on the prior existence of a RoomDataModel, otherwise the method will encounter a NullPointerException
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        // Set behaviour to list Devices in Room - findByRoomID() in DeviceRepositoryJPAImp:
        Query queryDeviceDouble = mock(Query.class);
        when(managerDouble.createQuery("SELECT e FROM DeviceDataModel e WHERE e.roomID = :roomID")).thenReturn(queryDeviceDouble);
        // use factory to create an object that is equal to the one created by services
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        // create a list of data models to return
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        List<DeviceDataModel> listOfDeviceDataModelsInRepo = List.of(deviceDataModel);
        when(queryDeviceDouble.getResultList()).thenReturn(listOfDeviceDataModelsInRepo);

        //////////////////// TEST CASE ////////////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        // DTO of the selected room
        RoomDTO roomDTO = new RoomDTO(selectedRoomID.toString());

        // Act
        List<DeviceDTO> result = controller.getListOfDevicesDTOInRoom(roomDTO);

        // Assert list is not empty and has the correct device
        assertEquals(1, result.size());
        assertEquals("Heater", result.get(0).deviceName);
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES SPRING --------------------------------------------------

    /**
     * Test of success to get the list of devices in a room with devices using Spring repositories.
     */
    @Test
    void successGetListOfDevicesSpringRepo() {
        //////////////////// TEST SETUP ////////////////////

        //Instantiation of Mappers
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();

        // Create repositories Spring
        HouseRepositorySpringData houseRepositorySpringDataDouble = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepositorySpring = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringDataDouble, mapperHouseDataModel);

        RoomRepositorySpringData roomRepositorySpringDataDouble = mock(RoomRepositorySpringData.class);
        RoomRepository roomRepositorySpring = new RoomRepositorySpringDataImp(roomRepositorySpringDataDouble, factoryRoom, mapperRoomDataModel);

        DeviceRepositorySpringData deviceRepositorySpringDataDouble = mock(DeviceRepositorySpringData.class);
        DeviceRepository deviceRepositorySpring = new DeviceRepositorySpringDataImp(factoryDevice, deviceRepositorySpringDataDouble, mapperDeviceDataModel);

        // Instantiate all needed services
        HouseService houseService = new HouseService(houseRepositorySpring, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositorySpring, roomRepositorySpring, houseRepositorySpring);
        RoomService roomService = new RoomService(roomRepositorySpring, factoryRoom, houseRepositorySpring);

        // Instantiate controller to be tested
        ListAllDevicesInRoomController controller = new ListAllDevicesInRoomController(deviceService);

        // HOUSE -------------------------------------------------------------------------------------------------------
        // Create house
        Location location = new Location(
                new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"),
                new GPSCode(41.178553, -8.608035));
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        // HOUSE - containsEntityByID() in HouseRepository
        when(houseRepositorySpringDataDouble.existsById(houseID.toString())).thenReturn(true);

        // ROOM --------------------------------------------------------------------------------------------------------
        // Create room
        RoomID roomID = new RoomID("Living room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepository:
        // use factory to create an object that is equal to the one created by services
        Room room = factoryRoom.createRoom(roomID, roomFloor, roomDimensions, houseID);
        // create a list of data models to return
        RoomDataModel roomDataModel = new RoomDataModel(room);
        List<RoomDataModel> listOfRoomDataModelsInRepo = List.of(roomDataModel);
        when(roomRepositorySpringDataDouble.findAllByHouseID(houseID.toString())).thenReturn(listOfRoomDataModelsInRepo);

        // Set behaviour to find room when creating a device - containsEntityByID() in RoomRepository:
        when(roomRepositorySpringDataDouble.existsById(roomID.toString())).thenReturn(true);

        // DEVICE ------------------------------------------------------------------------------------------------------
        // Create device - The device creation relies on the prior existence of a RoomDataModel, otherwise the method will encounter a NullPointerException
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        // Set behaviour to list Devices in Room - findByRoomID() in DeviceRepository:
        // use factory to create an object that is equal to the one created by services
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        // create a list of data models to return
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        List<DeviceDataModel> listOfDeviceDataModelsInRepo = List.of(deviceDataModel);
        when(deviceRepositorySpringDataDouble.findAllByRoomID(roomID.toString())).thenReturn(listOfDeviceDataModelsInRepo);

        //////////////////// TEST CASE ////////////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        // DTO of the selected room
        RoomDTO roomDTO = new RoomDTO(selectedRoomID.toString());

        // Act
        List<DeviceDTO> result = controller.getListOfDevicesDTOInRoom(roomDTO);

        // Assert list is not empty and has the correct device
        assertEquals(1, result.size());
        assertEquals("Heater", result.get(0).deviceName);
    }
}