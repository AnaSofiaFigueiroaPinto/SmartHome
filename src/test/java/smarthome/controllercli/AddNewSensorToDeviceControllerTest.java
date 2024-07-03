package smarthome.controllercli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.device.ImpFactoryDevice;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.house.ImpFactoryHouse;
import smarthome.domain.repository.*;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensorfunctionality.FactorySensorFunctionality;
import smarthome.domain.sensorfunctionality.ImpFactorySensorFunctionality;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.SensorDTO;
import smarthome.persistence.jpa.datamodel.*;
import smarthome.persistence.jpa.repositoriesjpa.DeviceRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.HouseRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.RoomRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.SensorRepositoryJPAImp;
import smarthome.persistence.repositoriesmem.*;
import smarthome.persistence.springdata.repositoriesspringdata.*;
import smarthome.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for the {@code AddNewSensorToDeviceController} class.
 */
class AddNewSensorToDeviceControllerTest {

    /**
     * Successfully add valid Sensor to an existing device in an existing room using Memory repositories.
     */
    @Test
    void successCreateSensorWithMemoryRepositories() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        //Setup Repositories
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepository = new SensorRepositoryMem(sensorData);

        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        SensorService sensorService = new SensorService(factorySensor, sensorRepository, deviceRepository, sensorFunctionalityRepository);

        SensorFunctionalityService sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepository);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        HouseService houseService = new HouseService(houseRepository, factoryHouse);

        //Instantiate controller
        AddNewSensorToDeviceController controller = new AddNewSensorToDeviceController(sensorService);

        //Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        //Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        //Create device
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(deviceID,deviceModel, roomID);

        /////////////// TEST CASE ///////////////

        //Retrieve list of rooms in house
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID roomID1 = listOfRooms.get(0);

        //Retrieve list of devices in room
        List<DeviceID> listOfDevicesIDInRoom = deviceService.getListOfDevicesInRoom(roomID1);
        DeviceID selectedDevice = listOfDevicesIDInRoom.get(0);

        //DTO corresponding to the selected device
        DeviceDTO deviceDTO = new DeviceDTO(selectedDevice.toString());

        //Retrieve sensor functionalities
        List<SensorFunctionalityID> sensorFunctionalities = sensorFunctionalityService.getListOfSensorFunctionalities();

        //Select sensor functionality from list
        SensorFunctionalityID sensorFunctionality = sensorFunctionalities.get(0);

        SensorDTO createdSensorDTO = controller.createSensorAndSaveToRepository(deviceDTO, "Thermometer", sensorFunctionality.toString());

        assertNotNull(createdSensorDTO);
    }

    /**
     * Fail to add Sensor to an existing device in an existing room using Memory repositories, due to SensorFunctionalityDTO not existing.
     */

    @Test
    void failToCreateSensorInvalidFuncWithMemoryRepositories() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        //Setup Repositories
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepository = new SensorRepositoryMem(sensorData);

        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        SensorService sensorService = new SensorService(factorySensor, sensorRepository, deviceRepository, sensorFunctionalityRepository);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        HouseService houseService = new HouseService(houseRepository, factoryHouse);

        //Instantiate controller
        AddNewSensorToDeviceController controller = new AddNewSensorToDeviceController(sensorService);

        //Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        //Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        //Create device
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(deviceID,deviceModel, roomID);

        /////////////// TEST CASE ///////////////

        //Retrieve list of rooms in house
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID roomID1 = listOfRooms.get(0);

        //Retrieve list of devices in room
        List<DeviceID> listOfDevicesIDInRoom = deviceService.getListOfDevicesInRoom(roomID1);
        DeviceID selectedDevice = listOfDevicesIDInRoom.get(0);

        //DTO corresponding to the selected device
        DeviceDTO deviceDTO = new DeviceDTO(selectedDevice.toString());

        String invalidSensorFunctionality = "NonExistingFunctionality";

        SensorDTO createdSensorDTO = controller.createSensorAndSaveToRepository(deviceDTO, "Thermometer", invalidSensorFunctionality);

        assertNull(createdSensorDTO);
    }


    /**
     * Fail to add Sensor to a device using Memory repositories, due to deviceDTO not representing domain object.
     */

    @Test
    void failToCreateSensorInvalidDeviceWithMemoryRepositories() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        //Setup Repositories
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepository = new SensorRepositoryMem(sensorData);

        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        SensorService sensorService = new SensorService(factorySensor, sensorRepository, deviceRepository, sensorFunctionalityRepository);

        SensorFunctionalityService sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        HouseService houseService = new HouseService(houseRepository, factoryHouse);

        //Instantiate controller
        AddNewSensorToDeviceController controller = new AddNewSensorToDeviceController(sensorService);

        //Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        //Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        /////////////// TEST CASE ///////////////

        //DTO that does not match a domain object
        DeviceDTO deviceDTO2 = new DeviceDTO("TV");

        //Retrieve sensor functionalities
        List<SensorFunctionalityID> sensorFunctionalities = sensorFunctionalityService.getListOfSensorFunctionalities();

        //Select sensor functionality from list
        SensorFunctionalityID sensorFunctionality = sensorFunctionalities.get(0);

        SensorDTO createdSensorDTO = controller.createSensorAndSaveToRepository(deviceDTO2, "Thermometer", sensorFunctionality.toString());

        assertNull(createdSensorDTO);
    }

    /**
     * Fail to create and add a new  instance using Memory repositories due to created Sensor Name being null
     */
    @Test
    void failToCreateSensorNullSensorNameWithMemoryRepositories() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        //Setup Repositories
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepository = new SensorRepositoryMem(sensorData);

        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        SensorService sensorService = new SensorService(factorySensor, sensorRepository, deviceRepository, sensorFunctionalityRepository);

        SensorFunctionalityService sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepository);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        HouseService houseService = new HouseService(houseRepository, factoryHouse);

        //Instantiate controller
        AddNewSensorToDeviceController controller = new AddNewSensorToDeviceController(sensorService);

        //Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        //Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        //Create device
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");

        deviceService.createDeviceAndSaveToRepository(deviceID,deviceModel, roomID);

        /////////////// TEST CASE ///////////////

        //Retrieve list of rooms in house
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID roomID1 = listOfRooms.get(0);

        //Retrieve list of devices in room
        List<DeviceID> listOfDevicesIDInRoom = deviceService.getListOfDevicesInRoom(roomID1);
        DeviceID selectedDevice = listOfDevicesIDInRoom.get(0);

        //DTO corresponding to the selected device
        DeviceDTO deviceDTO = new DeviceDTO(selectedDevice.toString());

        //Retrieve sensor functionalities
        List<SensorFunctionalityID> sensorFunctionalities = sensorFunctionalityService.getListOfSensorFunctionalities();

        //Select sensor functionality from list
        SensorFunctionalityID sensorFunctionality = sensorFunctionalities.get(0);

        SensorDTO createdSensorDTO = controller.createSensorAndSaveToRepository(deviceDTO, null, sensorFunctionality.toString());

        assertNull(createdSensorDTO);
    }

    //---------------------- INTEGRATION TESTS WITH REPOSITORIES JPA ---------------------------------------------------

    /**
     * Successfully add valid Sensor to an existing device in an existing room using JPA repositories.
     */
    @Test
    void successCreateSensorWithJPARepositories() {
        // Create double for EntityManager to substitute the database dependency
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.getTransaction()).thenReturn(transaction);

        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        //Instantiation of necessary Repositories
        HouseRepository houseRepository = new HouseRepositoryJPAImp(factoryHouse, manager);
        RoomRepository roomRepository = new RoomRepositoryJPAImp(factoryRoom, manager);
        DeviceRepository deviceRepository = new DeviceRepositoryJPAImp(factoryDevice, manager);
        SensorRepository sensorRepository = new SensorRepositoryJPAImp(factorySensor, manager);
        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of necessary services
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        SensorService sensorService = new SensorService(factorySensor, sensorRepository, deviceRepository, sensorFunctionalityRepository);
        SensorFunctionalityService sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepository);

        // HOUSE -------------------------------------------------------------------------------------------------------
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));
        //Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(manager.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        //Create house
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        //Set behaviour when checking if house exists - containsEntityByID() in HouseRepositoryJPAImp:
        //use factory to create an object that is equal to the one created by services
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseID, location);
        //HouseDataModel
        HouseDataModel houseDataModel = new HouseDataModel(house);
        when(manager.find(HouseDataModel.class, houseID.toString())).thenReturn(houseDataModel);

        // ROOM --------------------------------------------------------------------------------------------------------
        //Create Room
        RoomID roomID1 = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        RoomID roomID = roomService.createRoomAndSave(roomID1, roomFloor, roomDimensions, houseID);

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepositoryJPAImp:
        Query queryRoomDouble = mock(Query.class);
        when(manager.createQuery("SELECT e FROM RoomDataModel e WHERE e.houseID = :houseID")).thenReturn(queryRoomDouble);
        // use factory to create an object that is equal to the one created by services
        Room room = factoryRoom.createRoom(roomID, roomFloor, roomDimensions, houseID);
        // create a list of data models to return
        RoomDataModel roomDataModel = new RoomDataModel(room);
        List<RoomDataModel> listOfRoomDataModelsInRepo = List.of(roomDataModel);
        when(queryRoomDouble.getResultList()).thenReturn(listOfRoomDataModelsInRepo);

        // Set behaviour to find room when creating a device - containsEntityByID() in RoomRepositoryJPAImp:
        when(manager.find(RoomDataModel.class, roomID.toString())).thenReturn(roomDataModel);

        // DEVICE ------------------------------------------------------------------------------------------------------
        //Create Device
        DeviceID deviceID1 = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");
        DeviceID deviceID = deviceService.createDeviceAndSaveToRepository(deviceID1, deviceModel, room.identity());

        // Set behaviour to list Devices in Room - findByRoomID() in DeviceRepositoryJPAImp:
        Query queryDeviceDouble = mock(Query.class);
        when(manager.createQuery("SELECT e FROM DeviceDataModel e WHERE e.roomID = :roomID")).thenReturn(queryDeviceDouble);
        // use factory to create an object that is equal to the one created by services
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        // create a list of data models to return
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        List<DeviceDataModel> listOfDeviceDataModelsInRepo = List.of(deviceDataModel);
        when(queryDeviceDouble.getResultList()).thenReturn(listOfDeviceDataModelsInRepo);

        // Set behaviour to find device when creating a sensor - findEntityByID() in DeviceRepositoryJPAImp:
        when(manager.find(DeviceDataModel.class, deviceID.toString())).thenReturn(deviceDataModel);

        //Instantiate controller
        AddNewSensorToDeviceController controller = new AddNewSensorToDeviceController(sensorService);

        //////////////////// TEST CASE ////////////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        // List devices in selected room to select one
        List<DeviceID> listOfDevicesInRoom = deviceService.getListOfDevicesInRoom(selectedRoomID);
        DeviceID selectedDeviceID = listOfDevicesInRoom.get(0);

        //List sensor functionalities to select one
        List<SensorFunctionalityID> sensorFunctionalityIDs = sensorFunctionalityService.getListOfSensorFunctionalities();
        SensorFunctionalityID selectedSensorFunctionality = sensorFunctionalityIDs.get(0);

        //DTO corresponding to the selected device
        DeviceDTO deviceDTO = new DeviceDTO(selectedDeviceID.toString());

        //Act
        SensorDTO sensorDTOCreated = controller.createSensorAndSaveToRepository(deviceDTO, "Heater", selectedSensorFunctionality.toString());

        //Assert
        assertEquals("Heater", sensorDTOCreated.sensorName);
        assertEquals(deviceID.toString(), sensorDTOCreated.deviceID);
        assertEquals(selectedSensorFunctionality.toString(), sensorDTOCreated.functionalityID);
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES SPRING --------------------------------------------------

    /**
     * Successfully add valid Sensor to an existing device in an existing room using SpringData repositories.
     */
    @Test
    void successCreateSensorWithSpringDataRepositories() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        //Instantiation of Mappers
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();

        //Instantiation of necessary Repositories
        HouseRepositorySpringData houseRepositorySpringData = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepositorySpring = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringData, mapperHouseDataModel);

        RoomRepositorySpringData roomRepositorySpringData = mock(RoomRepositorySpringData.class);
        RoomRepository roomRepositorySpring = new RoomRepositorySpringDataImp(roomRepositorySpringData, factoryRoom, mapperRoomDataModel);

        DeviceRepositorySpringData deviceRepositorySpringData = mock(DeviceRepositorySpringData.class);
        DeviceRepository deviceRepositorySpring = new DeviceRepositorySpringDataImp(factoryDevice, deviceRepositorySpringData, mapperDeviceDataModel);

        SensorRepositorySpringData sensorRepositorySpringData = mock(SensorRepositorySpringData.class);
        SensorRepository sensorRepositorySpring = new SensorRepositorySpringDataImp(sensorRepositorySpringData, factorySensor, mapperSensorDataModel);

        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of necessary services
        HouseService houseService = new HouseService(houseRepositorySpring, factoryHouse);
        RoomService roomService = new RoomService(roomRepositorySpring, factoryRoom, houseRepositorySpring);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositorySpring, roomRepositorySpring, houseRepositorySpring);
        SensorService sensorService = new SensorService(factorySensor, sensorRepositorySpring, deviceRepositorySpring, sensorFunctionalityRepository);
        SensorFunctionalityService sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepository);

        // HOUSE -------------------------------------------------------------------------------------------------------
        //Instantiation of House object
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        // Set behaviour - containsEntityByID() in HouseRepository:
        when(houseRepositorySpringData.existsById(houseID.toString())).thenReturn(true);

        // ROOM --------------------------------------------------------------------------------------------------------
        //Create Room
        RoomID roomID1 = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        RoomID roomID = roomService.createRoomAndSave(roomID1, roomFloor, roomDimensions, houseID);

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepository:
        // use factory to create an object that is equal to the one created by services
        Room room = factoryRoom.createRoom(roomID, roomFloor, roomDimensions, houseID);
        // create a list of data models to return
        RoomDataModel roomDataModel = new RoomDataModel(room);
        List<RoomDataModel> listOfRoomDataModelsInRepo = List.of(roomDataModel);
        when(roomRepositorySpringData.findAllByHouseID(houseID.toString())).thenReturn(listOfRoomDataModelsInRepo);

        // Set behaviour to find room when creating a device - containsEntityByID() in RoomRepository:
        when(roomRepositorySpringData.existsById(roomID.toString())).thenReturn(true);

        // DEVICE ------------------------------------------------------------------------------------------------------
        //Create Device
        DeviceID deviceID1 = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");
        DeviceID deviceID = deviceService.createDeviceAndSaveToRepository(deviceID1, deviceModel, roomID);

        // Set behaviour to list Devices in Room - findByRoomID() in DeviceRepository:
        // use factory to create an object that is equal to the one created by services
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        // create a list of data models to return
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        List<DeviceDataModel> listOfDeviceDataModelsInRepo = List.of(deviceDataModel);
        when(deviceRepositorySpringData.findAllByRoomID(roomID.toString())).thenReturn(listOfDeviceDataModelsInRepo);

        // Set behaviour to find device when creating a sensor - findEntityByID() in DeviceRepository:
        when(deviceRepositorySpringData.findById(deviceID.toString())).thenReturn(Optional.of(deviceDataModel));

        //Instantiate controller
        AddNewSensorToDeviceController controller = new AddNewSensorToDeviceController(sensorService);

        //////////////////// TEST CASE ////////////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        // List devices in selected room to select one
        List<DeviceID> listOfDevicesInRoom = deviceService.getListOfDevicesInRoom(selectedRoomID);
        DeviceID selectedDeviceID = listOfDevicesInRoom.get(0);

        //List sensor functionalities to select one
        List<SensorFunctionalityID> sensorFunctionalityIDs = sensorFunctionalityService.getListOfSensorFunctionalities();
        SensorFunctionalityID selectedSensorFunctionality = sensorFunctionalityIDs.get(0);

        //DTO corresponding to the selected device
        DeviceDTO deviceDTO = new DeviceDTO(selectedDeviceID.toString());

        //Act
        SensorDTO sensorDTOCreated = controller.createSensorAndSaveToRepository(deviceDTO, "Heater", selectedSensorFunctionality.toString());

        //Assert
        assertEquals("Heater", sensorDTOCreated.sensorName);
        assertEquals(deviceID.toString(), sensorDTOCreated.deviceID);
        assertEquals(selectedSensorFunctionality.toString(), sensorDTOCreated.functionalityID);
    }

}