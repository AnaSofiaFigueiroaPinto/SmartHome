package smarthome.controllercli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatorfunctionality.FactoryActuatorFunctionality;
import smarthome.domain.actuatorfunctionality.ImpFactoryActuatorFunctionality;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.FactoryActuator;
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
import smarthome.domain.valueobjects.*;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.DeviceDTO;
import smarthome.persistence.jpa.datamodel.*;
import smarthome.persistence.jpa.repositoriesjpa.ActuatorRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.DeviceRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.HouseRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.RoomRepositoryJPAImp;
import smarthome.persistence.repositoriesmem.*;
import smarthome.persistence.springdata.repositoriesspringdata.*;
import smarthome.service.*;
import smarthome.util.exceptions.ActuatorFunctionalityNotListedException;
import smarthome.util.exceptions.DeviceNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code AddNewActuatorToDeviceController} class.
 */
class AddNewActuatorToDeviceControllerTest {

    /**
     * Test that successfully creates an actuator and save to the repository.
     */
    @Test
    void successCreateActuatorAndSaveRepositoryMem() {
        //Instantiation of needed factories
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryActuator factoryActuator = new FactoryActuator();
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();

        //Instantiation of needed repositories with a blank data map
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<ActuatorID, Actuator> actuatorData = new HashMap<>();
        ActuatorRepository actuatorRepository = new ActuatorRepositoryMem(actuatorData);

        ActuatorFunctionalityRepository actuatorFunctionality = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);

        //Instantiation of House object
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        //Instantiation of needed services
        ActuatorService actuatorService = new ActuatorService(factoryActuator, actuatorRepository, deviceRepository, actuatorFunctionality);
        ActuatorFunctionalityService actuatorFunctionalityService = new ActuatorFunctionalityService(actuatorFunctionality);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        //Instantiate controller
        AddNewActuatorToDeviceController controller = new AddNewActuatorToDeviceController(actuatorService);

        //Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, house.identity());

        //Create device
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");

        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        //Retrieve list of rooms in house
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID roomID2 = listOfRooms.get(0);

        //Retrieve list of devices in room
        List<DeviceID> listOfDevicesIDInRoom = deviceService.getListOfDevicesInRoom(roomID2);
        DeviceDTO deviceDTO = new DeviceDTO(listOfDevicesIDInRoom.get(0).toString(), "H8115", "ACTIVE", roomID.toString());

        //Retrieve actuator functionalities
        List<ActuatorFunctionalityID> actuatorFunctionalities = actuatorFunctionalityService.getListOfActuatorFunctionalities();

        //Select actuator functionality from list
        ActuatorFunctionalityID actuatorFunctionalityID = actuatorFunctionalities.get(0);

        // Set actuator properties
        String actuatorName = "ActuatorName";
        int upperLimitInt = 0;
        int lowerLimitInt= 0;
        double upperLimitDecimal = 2.0;
        double lowerLimitDecimal = 1.1;
        int precision = 1;

        //Create ActuatorDTO
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, actuatorFunctionalityID.toString(), deviceDTO.deviceName, upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);

        ActuatorDTO createdActuatorDTO = controller.createActuatorAndSaveToRepository(actuatorDTO);


        assertNotNull(createdActuatorDTO);
    }

    /**
     * Test that fails to create a new actuator due to invalid device.
     */
    @Test
    void failCreateActuatorInvalidDevice() {
        //Instantiation of needed factories
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryActuator factoryActuator = new FactoryActuator();
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();

        //Instantiation of needed repositories with a blank data map
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<ActuatorID, Actuator> actuatorData = new HashMap<>();
        ActuatorRepository actuatorRepository = new ActuatorRepositoryMem(actuatorData);

        ActuatorFunctionalityRepository actuatorFunctionality = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);

        //Instantiation of House object
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        //Instantiation of needed services
        ActuatorService actuatorService = new ActuatorService(factoryActuator, actuatorRepository, deviceRepository, actuatorFunctionality);
        ActuatorFunctionalityService actuatorFunctionalityService = new ActuatorFunctionalityService(actuatorFunctionality);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        //Instantiate controller
        AddNewActuatorToDeviceController controller = new AddNewActuatorToDeviceController(actuatorService);

        //Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, house.identity());

        //Create device
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");

        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        //Create deviceDTO that does not match a domain object
        DeviceDTO deviceDTO2 = new DeviceDTO("TV");

        //Retrieve list of available actuator functionalities
        List<ActuatorFunctionalityID> functionalityList = actuatorFunctionalityService.getListOfActuatorFunctionalities();

        //Select actuator functionality from list
        ActuatorFunctionalityID actuatorFunctionalityID = functionalityList.get(0);

        // Set actuator properties
        String actuatorName = "ActuatorName";
        int upperLimitInt = 0;
        int lowerLimitInt= 0;
        double upperLimitDecimal = 3.50;
        double lowerLimitDecimal = 2.50;
        int precision = 2;

        //Create ActuatorDTO
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, actuatorFunctionalityID.toString(), deviceDTO2.deviceName, upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);

        assertThrows(DeviceNotFoundException.class, () -> controller.createActuatorAndSaveToRepository(actuatorDTO));
    }

    /**
     * Test that fails to create a new actuator due to invalid functionality.
     */
    @Test
    void failCreateActuatorInvalidFunctionality() {
        //Instantiation of needed factories
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryActuator factoryActuator = new FactoryActuator();
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();

        //Instantiation of needed repositories with a blank data map
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<ActuatorID, Actuator> actuatorData = new HashMap<>();
        ActuatorRepository actuatorRepository = new ActuatorRepositoryMem(actuatorData);

        ActuatorFunctionalityRepository actuatorFunctionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);

        //Instantiation of House object
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        //Instantiation of needed services
        ActuatorService actuatorService = new ActuatorService(factoryActuator, actuatorRepository, deviceRepository, actuatorFunctionalityRepository);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        //Instantiate controller
        AddNewActuatorToDeviceController controller = new AddNewActuatorToDeviceController(actuatorService);

        //Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, house.identity());

        //Create device
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");

        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        //Retrieve list of rooms in house
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID roomID2 = listOfRooms.get(0);

        //Retrieve list of devices in room
        List<DeviceID> listOfDevicesIDInRoom = deviceService.getListOfDevicesInRoom(roomID2);
        DeviceDTO deviceDTO = new DeviceDTO(listOfDevicesIDInRoom.get(0).toString(), "H8115", "ACTIVE", roomID.toString());

        //Invalid actuator functionality
        String invalidActuatorFunctionality = "NonExistingFunctionality";

        // Set actuator properties
        String actuatorName = "Heater";
        int upperLimitInt = 0;
        int lowerLimitInt= 0;
        double upperLimitDecimal = 3.50;
        double lowerLimitDecimal = 2.50;
        int precision = 2;

        //Create ActuatorDTO
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, invalidActuatorFunctionality, deviceDTO.deviceName, upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);

        assertThrows(ActuatorFunctionalityNotListedException.class, () -> controller.createActuatorAndSaveToRepository(actuatorDTO));
    }

    /**
     * Test that fails to create a new actuator due to null actuatorID.
     */
    @Test
    void failCreateActuatorNullActuatorName() {
        //Instantiation of needed factories
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryActuator factoryActuator = new FactoryActuator();
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();

        //Instantiation of needed repositories with a blank data map
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<ActuatorID, Actuator> actuatorData = new HashMap<>();
        ActuatorRepository actuatorRepository = new ActuatorRepositoryMem(actuatorData);

        ActuatorFunctionalityRepository actuatorFunctionality = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);

        //Instantiation of House object
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        //Instantiation of needed services
        ActuatorService actuatorService = new ActuatorService(factoryActuator, actuatorRepository, deviceRepository, actuatorFunctionality);
        ActuatorFunctionalityService actuatorFunctionalityService = new ActuatorFunctionalityService(actuatorFunctionality);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        //Instantiate controller
        AddNewActuatorToDeviceController controller = new AddNewActuatorToDeviceController(actuatorService);

        //Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, house.identity());

        //Create device
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");

        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        //Retrieve list of rooms in house
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID roomID2 = listOfRooms.get(0);

        //Retrieve list of devices in room
        List<DeviceID> listOfDevicesIDInRoom = deviceService.getListOfDevicesInRoom(roomID2);
        DeviceDTO deviceDTO = new DeviceDTO(listOfDevicesIDInRoom.get(0).toString(), "H8115", "ACTIVE", roomID.toString());

        //Retrieve actuator functionalities
        List<ActuatorFunctionalityID> actuatorFunctionalities = actuatorFunctionalityService.getListOfActuatorFunctionalities();

        //Select actuator functionality from list
        ActuatorFunctionalityID actuatorFunctionalityID = actuatorFunctionalities.get(0);

        // Set actuator properties
        String actuatorName = null;
        int upperLimitInt = 0;
        int lowerLimitInt= 0;
        double upperLimitDecimal = 3.50;
        double lowerLimitDecimal = 2.50;
        int precision = 2;

        //Create ActuatorDTO
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, actuatorFunctionalityID.toString(), deviceDTO.deviceName, upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);

        ActuatorDTO createdActuatorDTO = controller.createActuatorAndSaveToRepository(actuatorDTO);

        assertNull(createdActuatorDTO);
    }

    //---------------------- INTEGRATION TESTS WITH REPOSITORIES JPA ---------------------------------------------------

    /**
     * Test that successfully creates an actuator and save to JPA repository.
     */
    @Test
    void successCreateActuatorAndSaveJPARepository() {
        // Create double for EntityManager to substitute the database dependency
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.getTransaction()).thenReturn(transaction);

        //Instantiating a House and saving in the repository
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        HouseRepository houseRepositoryJPA = new HouseRepositoryJPAImp(factoryHouse, manager);

        HouseService houseService = new HouseService(houseRepositoryJPA, factoryHouse);
        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(manager.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        HouseID houseIDCreated = houseService.createAndSaveHouseWithoutLocation();

        //Creating a HouseDataModel to simulate the behaviour in the persistence
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseIDCreated, null);
        HouseDataModel houseDataModel = new HouseDataModel(house);
        when(manager.find(HouseDataModel.class, houseIDCreated.toString())).thenReturn(houseDataModel);

        //Instantiating a Room and saving in the repository
        ImpFactoryRoom factoryRoom = new ImpFactoryRoom();
        RoomRepository roomRepositoryJPA = new RoomRepositoryJPAImp(factoryRoom, manager);

        RoomID roomID = new RoomID("Kitchen");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);

        RoomService roomService = new RoomService(roomRepositoryJPA, factoryRoom, houseRepositoryJPA);
        RoomID roomIDCreated = roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseIDCreated);

        //Creating a RoomDataModel to simulate the behaviour in the persistence
        Room room = factoryRoom.createRoom(roomIDCreated, roomFloor, roomDimensions, houseIDCreated);
        RoomDataModel roomDataModel = new RoomDataModel(room);
        when(manager.find(RoomDataModel.class, roomIDCreated.toString())).thenReturn(roomDataModel);

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepository:
        Query queryRoomDouble = mock(Query.class);
        when(manager.createQuery("SELECT e FROM RoomDataModel e WHERE e.houseID = :houseID")).thenReturn(queryRoomDouble);
        // create a list of data models to return
        List<RoomDataModel> listOfRoomDataModelsInRepo = List.of(roomDataModel);
        when(queryRoomDouble.getResultList()).thenReturn(listOfRoomDataModelsInRepo);

        //Instantiating a Device and saving in the repository
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        DeviceRepository deviceRepositoryJPA = new DeviceRepositoryJPAImp(factoryDevice, manager);

        DeviceID deviceID = new DeviceID("BlindRoller");
        DeviceModel deviceModel = new DeviceModel("B8115");

        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryJPA, roomRepositoryJPA, houseRepositoryJPA);
        DeviceID deviceIDCreated = deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomIDCreated);

        //Creating a DeviceDataModel to simulate the behaviour in the persistence
        Device device = factoryDevice.createDevice(deviceIDCreated, deviceModel, roomIDCreated);
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        when(manager.find(DeviceDataModel.class, deviceIDCreated.toString())).thenReturn(deviceDataModel);

        // Set behaviour to list Devices in Room - findByRoomID() in DeviceRepositoryJPAImp:
        Query queryDeviceDouble = mock(Query.class);
        when(manager.createQuery("SELECT e FROM DeviceDataModel e WHERE e.roomID = :roomID")).thenReturn(queryDeviceDouble);
        List<DeviceDataModel> listOfDeviceDataModelsInRepo = List.of(deviceDataModel);
        when(queryDeviceDouble.getResultList()).thenReturn(listOfDeviceDataModelsInRepo);

        //ActuatorFunctionality
        FactoryActuatorFunctionality factoryFunctionality = new ImpFactoryActuatorFunctionality();
        ActuatorFunctionalityRepository functionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryFunctionality);
        ActuatorFunctionalityService functionalityService = new ActuatorFunctionalityService(functionalityRepository);

        //Instantiating the ActuatorService
        FactoryActuator factoryActuator = new FactoryActuator();
        ActuatorRepository actuatorRepositoryJPA = new ActuatorRepositoryJPAImp(factoryActuator, manager);
        ActuatorService actuatorService = new ActuatorService(factoryActuator, actuatorRepositoryJPA, deviceRepositoryJPA, functionalityRepository);

        AddNewActuatorToDeviceController controller = new AddNewActuatorToDeviceController(actuatorService);

        //////////////////// TEST CASE ////////////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseIDCreated);
        RoomID selectedRoomID = listOfRooms.get(0);

        // List devices in selected room to select one
        List<DeviceID> listOfDevicesInRoom = deviceService.getListOfDevicesInRoom(selectedRoomID);
        DeviceID selectedDeviceID = listOfDevicesInRoom.get(0);

        // List actuator functionalities to select one
        List<ActuatorFunctionalityID> actuatorFunctionalities = functionalityService.getListOfActuatorFunctionalities();
        ActuatorFunctionalityID selectedActuatorFunctionality = actuatorFunctionalities.get(2);

        // ActuatorDTO to be created
        String actuatorName = "IntegerSetter";
        int upperLimitInt = 10;
        int lowerLimitInt= 1;
        double upperLimitDecimal = 0;
        double lowerLimitDecimal = 0;
        int precision = 0;

        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, selectedActuatorFunctionality.toString(), selectedDeviceID.toString(), upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);

        // Act
        ActuatorDTO createdActuatorDTO = controller.createActuatorAndSaveToRepository(actuatorDTO);

        // Assert
        assertNotNull(createdActuatorDTO);
        assertEquals("IntegerSetter", createdActuatorDTO.actuatorName);
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES SPRING --------------------------------------------------

    /**
     * Test that successfully creates an actuator and save to SpringData repository.
     */
    @Test
    void successCreateActuatorAndSaveSpringDataRepository() {
        //Instantiation of needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryActuator factoryActuator = new FactoryActuator();
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();

        // Instantiation of Mappers
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        MapperActuatorDataModel mapperActuatorDataModel = new MapperActuatorDataModel();

        // Create repositories Spring
        HouseRepositorySpringData houseRepositorySpringDataDouble = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepositorySpring = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringDataDouble, mapperHouseDataModel);

        RoomRepositorySpringData roomRepositorySpringDataDouble = mock(RoomRepositorySpringData.class);
        RoomRepository roomRepositorySpring = new RoomRepositorySpringDataImp(roomRepositorySpringDataDouble, factoryRoom, mapperRoomDataModel);

        DeviceRepositorySpringData deviceRepositorySpringDataDouble = mock(DeviceRepositorySpringData.class);
        DeviceRepository deviceRepositorySpring = new DeviceRepositorySpringDataImp(factoryDevice, deviceRepositorySpringDataDouble, mapperDeviceDataModel);

        ActuatorRepositorySpringData actuatorRepositorySpringDataDouble = mock(ActuatorRepositorySpringData.class);
        ActuatorRepository actuatorRepositorySpring = new ActuatorRepositorySpringDataImp(actuatorRepositorySpringDataDouble, factoryActuator, mapperActuatorDataModel);

        ActuatorFunctionalityRepository actuatorFunctionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);

        // Instantiate needed services
        HouseService houseService = new HouseService(houseRepositorySpring, factoryHouse);
        RoomService roomService = new RoomService(roomRepositorySpring, factoryRoom, houseRepositorySpring);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositorySpring, roomRepositorySpring, houseRepositorySpring);
        ActuatorService actuatorService = new ActuatorService(factoryActuator, actuatorRepositorySpring, deviceRepositorySpring, actuatorFunctionalityRepository);
        ActuatorFunctionalityService functionalityService = new ActuatorFunctionalityService(actuatorFunctionalityRepository);

        // HOUSE -------------------------------------------------------------------------------------------------------
        // Create house
        HouseID houseIDCreated = houseService.createAndSaveHouseWithoutLocation();

        // Set behaviour - containsEntityByID() in HouseRepository:
        when(houseRepositorySpringDataDouble.existsById(houseIDCreated.toString())).thenReturn(true);

        // ROOM --------------------------------------------------------------------------------------------------------
        // Create room
        RoomID roomID = new RoomID("Kitchen");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseIDCreated);

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepository:
        // use factory to create an object that is equal to the one created by services
        Room room = factoryRoom.createRoom(roomID, roomFloor, roomDimensions, houseIDCreated);
        // create a list of data models to return
        RoomDataModel roomDataModel = new RoomDataModel(room);
        List<RoomDataModel> listOfRoomDataModelsInRepo = List.of(roomDataModel);
        when(roomRepositorySpringDataDouble.findAllByHouseID(houseIDCreated.toString())).thenReturn(listOfRoomDataModelsInRepo);

        //Set behaviour to find room when creating a device - containsEntityByID() in RoomRepository:
        when(roomRepositorySpringDataDouble.existsById(roomID.toString())).thenReturn(true);

        // DEVICE ------------------------------------------------------------------------------------------------------
        // Create device
        DeviceID deviceID = new DeviceID("Boiler");
        DeviceModel deviceModel = new DeviceModel("B8115");

        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        // Set behaviour to list Devices in Room - findByRoomID() in DeviceRepository:
        // use factory to create an object that is equal to the one created by services
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        List<DeviceDataModel> listOfDeviceDataModelsInRepo = List.of(deviceDataModel);
        when(deviceRepositorySpringDataDouble.findAllByRoomID(roomID.toString())).thenReturn(listOfDeviceDataModelsInRepo);

        // Set behaviour to find device when creating a sensor - findEntityByID() in DeviceRepository:
        when(deviceRepositorySpringDataDouble.findById(deviceID.toString())).thenReturn(Optional.of(deviceDataModel));

        // Instantiate controller to be tested
        AddNewActuatorToDeviceController controller = new AddNewActuatorToDeviceController(actuatorService);

        //////////////////// TEST CASE ////////////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseIDCreated);
        RoomID selectedRoomID = listOfRooms.get(0);

        // List devices in selected room to select one
        List<DeviceID> listOfDevicesInRoom = deviceService.getListOfDevicesInRoom(selectedRoomID);
        DeviceID selectedDeviceID = listOfDevicesInRoom.get(0);

        // List actuator functionalities to select one
        List<ActuatorFunctionalityID> actuatorFunctionalities = functionalityService.getListOfActuatorFunctionalities();
        ActuatorFunctionalityID selectedActuatorFunctionality = actuatorFunctionalities.get(1);

        // ActuatorDTO to be created
        String actuatorName = "ActuatorName";
        int upperLimitInt = 0;
        int lowerLimitInt= 0;
        double upperLimitDecimal = 3.50;
        double lowerLimitDecimal = 2.50;
        int precision = 2;

        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, selectedActuatorFunctionality.toString(), selectedDeviceID.toString(), upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);

        // Act
        ActuatorDTO createdActuatorDTO = controller.createActuatorAndSaveToRepository(actuatorDTO);

        // Assert
        assertNotNull(createdActuatorDTO);
        assertEquals("ActuatorName", createdActuatorDTO.actuatorName);
    }
}