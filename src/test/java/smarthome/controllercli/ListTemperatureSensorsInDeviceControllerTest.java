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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link ListTemperatureSensorsInDeviceController}.
 */
class ListTemperatureSensorsInDeviceControllerTest {

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
     * Factory to create sensor objects.
     */
    private FactorySensor factorySensor;

    /**
     * Factory to create sensor functionality objects.
     */
    private FactorySensorFunctionality factorySensorFunctionality;

    /**
     * Sets up the test environment by instantiating required objects and services.
     */
    @BeforeEach
    void setUp() {
        //Instantiate all needed factories
        factoryHouse = new ImpFactoryHouse();
        factoryRoom = new ImpFactoryRoom();
        factoryDevice = new ImpFactoryDevice();
        factorySensor = new FactorySensor();
        factorySensorFunctionality = new ImpFactorySensorFunctionality();
    }

    //---------------------- INTEGRATION TESTS WITH REPOSITORIES MEM ---------------------------------------------------

    /**
     * Test to check if the controller returns a list of SensorDTOs when given a DeviceDTO.
     */
    @Test
    void getListOfTemperatureSensorDTOInDevice() {
        /////////////// TEST SETUP ///////////////

        // Create blank data maps as data source for repositories
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepository = new SensorRepositoryMem(sensorData);

        SensorFunctionalityRepositoryMem sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        // Instantiate all needed services
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        SensorService sensorService = new SensorService(factorySensor, sensorRepository, deviceRepository, sensorFunctionalityRepositoryMem);

        // Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        //Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        //Create device
        DeviceID deviceID = new DeviceID("RoomThermostat");
        DeviceModel deviceModel = new DeviceModel("T8115");
        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        //Create sensor
        SensorID sensorID = new SensorID("RoomTemperature");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");
        sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, sensorID);

        //Instantiate controller to be tested
        ListTemperatureSensorsInDeviceController controller = new ListTemperatureSensorsInDeviceController(sensorService);

        /////////////// TEST CASE ///////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        // List devices in selected room to select one
        List<DeviceID> listOfDevicesInRoom = deviceService.getListOfDevicesInRoom(selectedRoomID);
        DeviceID selectedDeviceID = listOfDevicesInRoom.get(0);
        DeviceDTO deviceDTO = new DeviceDTO(selectedDeviceID.toString());

        // Assert list of temperature sensors has the correct size and the correct sensor
        List<SensorDTO> sensorDTOList = controller.getListOfTemperatureSensorDTOInDevice(deviceDTO);
        assertEquals(1, sensorDTOList.size());
        assertEquals("RoomTemperature", sensorDTOList.get(0).sensorName);
    }

    /**
     * Test to check if the controller returns null when given an invalid DeviceDTO.
     */

    @Test
    void getListOfTemperatureSensorDTOInDeviceWithInvalidDevice() {
        /////////////// TEST SETUP ///////////////

        // Create blank data maps as data source for repositories
        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepository = new SensorRepositoryMem(sensorData);

        SensorFunctionalityRepositoryMem sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        // Instantiate all needed services
        SensorService sensorService = new SensorService(factorySensor, sensorRepository, deviceRepository, sensorFunctionalityRepositoryMem);

        //Instantiate controller to be tested
        ListTemperatureSensorsInDeviceController controller = new ListTemperatureSensorsInDeviceController(sensorService);

        /////////////// TEST CASE ///////////////
        List<SensorDTO> sensorDTOList = controller.getListOfTemperatureSensorDTOInDevice(null);
        // Assert that the result is an empty list
        assertEquals(Collections.emptyList(), sensorDTOList);
    }

    //---------------------- INTEGRATION TESTS WITH REPOSITORIES JPA ---------------------------------------------------

    /**
     * Test of success to get a list of temperature sensors DTOs from a device using JPA repositories.
     */
    @Test
    void successGetListOfTemperatureSensorsDTOJPARepo() {
        //////////////////// TEST SETUP ////////////////////

        // Create double for EntityManager to substitute the database dependency
        EntityManager managerDouble = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(managerDouble.getTransaction()).thenReturn(transaction);

        // Create repositories JPA
        HouseRepository houseRepositoryJPA = new HouseRepositoryJPAImp(factoryHouse, managerDouble);
        RoomRepository roomRepositoryJPA = new RoomRepositoryJPAImp(factoryRoom, managerDouble);
        DeviceRepository deviceRepositoryJPA = new DeviceRepositoryJPAImp(factoryDevice, managerDouble);
        SensorRepository sensorRepositoryJPA = new SensorRepositoryJPAImp(factorySensor, managerDouble);
        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        // Instantiate all needed services
        HouseService houseService = new HouseService(houseRepositoryJPA, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryJPA, roomRepositoryJPA, houseRepositoryJPA);
        RoomService roomService = new RoomService(roomRepositoryJPA, factoryRoom, houseRepositoryJPA);
        SensorService sensorService = new SensorService(factorySensor, sensorRepositoryJPA, deviceRepositoryJPA, sensorFunctionalityRepository);
        SensorFunctionalityService sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepository);

        //Instantiate controller to be tested
        ListTemperatureSensorsInDeviceController controller = new ListTemperatureSensorsInDeviceController(sensorService);

        // HOUSE -------------------------------------------------------------------------------------------------------
        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(managerDouble.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        // Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        // Set behaviour when checking if house exists - containsEntityByID() in HouseRepositoryJPAImp:
        // use factory to create an object that is equal to the one created by services
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseID, null);
        // create the respective datamodel
        HouseDataModel houseDataModel = new HouseDataModel(house);
        // set the find method to return the data model
        when(managerDouble.find(HouseDataModel.class, houseID.toString())).thenReturn(houseDataModel);

        // ROOM --------------------------------------------------------------------------------------------------------
        //Create room
        RoomID roomID = new RoomID("Living Room");
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
        DeviceID deviceID = new DeviceID("RoomThermostat");
        DeviceModel deviceModel = new DeviceModel("T8115");
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

        // Set behaviour to find device when creating a sensor - findEntityByID() in DeviceRepositoryJPAImp:
        when(managerDouble.find(DeviceDataModel.class, "RoomThermostat")).thenReturn(deviceDataModel);

        // SENSOR ------------------------------------------------------------------------------------------------------
        //Create sensor
        SensorID sensorID = new SensorID("RoomTemperature");
        SensorFunctionalityID sensorFunctionalityID = sensorFunctionalityService.getListOfSensorFunctionalities().get(0);
        sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, sensorID);

        // Set  behaviour to list Sensors of Device - findByDeviceIDAndSensorFunctionality() in SensorRepositoryJPAImp:
        Query querySensorDouble = mock(Query.class);
        when(Objects.requireNonNull(managerDouble).createQuery("SELECT e FROM SensorDataModel e WHERE e.deviceID = :deviceID AND e.sensorFunctionalityID = :sensorFunctionalityID")).thenReturn(querySensorDouble);
        // use factory to create an object that is equal to the one created by services
        String sensorClass = sensorFunctionalityRepository.getClassNameForSensorFunctionalityID(sensorFunctionalityID);
        Sensor sensor = factorySensor.createSensor(sensorID, deviceID, sensorFunctionalityID, sensorClass);
        // create a list of data models to return
        SensorDataModel sensorDataModel = new SensorDataModel(sensor);
        List<SensorDataModel> listOfSensorDataModelsInRepo = List.of(sensorDataModel);
        when(querySensorDouble.getResultList()).thenReturn(listOfSensorDataModelsInRepo);

        /////////////// TEST CASE ///////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        // List devices in selected room to select one
        List<DeviceID> listOfDevicesInRoom = deviceService.getListOfDevicesInRoom(selectedRoomID);
        DeviceID selectedDeviceID = listOfDevicesInRoom.get(0);
        DeviceDTO deviceDTO = new DeviceDTO(selectedDeviceID.toString());

        // Assert list of temperature sensors has the correct size and the correct sensor
        List<SensorDTO> sensorDTOList = controller.getListOfTemperatureSensorDTOInDevice(deviceDTO);
        assertEquals(1, sensorDTOList.size());
        assertEquals("RoomTemperature", sensorDTOList.get(0).sensorName);
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES SPRING --------------------------------------------------

    /**
     * Test of success to get a list of temperature sensors DTOs from a device using Spring repositories.
     */
    @Test
    void successGetListOfTemperatureSensorsDTOSpringRepo() {
        //////////////////// TEST SETUP ////////////////////

        //Instantiation of Mappers
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();

        // Create repositories Spring
        HouseRepositorySpringData houseRepositorySpringDataDouble = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepositorySpring = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringDataDouble, mapperHouseDataModel);

        RoomRepositorySpringData roomRepositorySpringDataDouble = mock(RoomRepositorySpringData.class);
        RoomRepository roomRepositorySpring = new RoomRepositorySpringDataImp(roomRepositorySpringDataDouble, factoryRoom, mapperRoomDataModel);

        DeviceRepositorySpringData deviceRepositorySpringDataDouble = mock(DeviceRepositorySpringData.class);
        DeviceRepository deviceRepositorySpring = new DeviceRepositorySpringDataImp(factoryDevice, deviceRepositorySpringDataDouble, mapperDeviceDataModel);

        SensorRepositorySpringData sensorRepositorySpringDataDouble = mock(SensorRepositorySpringData.class);
        SensorRepository sensorRepositorySpring = new SensorRepositorySpringDataImp(sensorRepositorySpringDataDouble, factorySensor, mapperSensorDataModel);

        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        // Instantiate needed services
        HouseService houseService = new HouseService(houseRepositorySpring, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositorySpring, roomRepositorySpring, houseRepositorySpring);
        RoomService roomService = new RoomService(roomRepositorySpring, factoryRoom, houseRepositorySpring);
        SensorService sensorService = new SensorService(factorySensor, sensorRepositorySpring, deviceRepositorySpring, sensorFunctionalityRepository);
        SensorFunctionalityService sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepository);

        //Instantiate controller to be tested
        ListTemperatureSensorsInDeviceController controller = new ListTemperatureSensorsInDeviceController(sensorService);

        // HOUSE -------------------------------------------------------------------------------------------------------
        // Create house
        Location location = new Location(
                new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"),
                new GPSCode(41.178553, -8.608035));
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        // Set behaviour - containsEntityByID() in HouseRepository:
        when(houseRepositorySpringDataDouble.existsById(houseID.toString())).thenReturn(true);

        // ROOM --------------------------------------------------------------------------------------------------------
        // Create room
        RoomID roomID = new RoomID("Living Room");
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
        DeviceID deviceID = new DeviceID("RoomThermostat");
        DeviceModel deviceModel = new DeviceModel("T8115");
        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        // Set behaviour to list Devices in Room - findByRoomID() in DeviceRepository:
        // use factory to create an object that is equal to the one created by services
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        // create a list of data models to return
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        List<DeviceDataModel> listOfDeviceDataModelsInRepo = List.of(deviceDataModel);
        when(deviceRepositorySpringDataDouble.findAllByRoomID(roomID.toString())).thenReturn(listOfDeviceDataModelsInRepo);

        // Set behaviour to find device when creating a sensor - findEntityByID() in DeviceRepository:
        when(deviceRepositorySpringDataDouble.findById(deviceID.toString())).thenReturn(Optional.of(deviceDataModel));

        // SENSOR ------------------------------------------------------------------------------------------------------
        // Create sensor
        SensorID sensorID = new SensorID("RoomTemperature");
        SensorFunctionalityID sensorFunctionalityID = sensorFunctionalityService.getListOfSensorFunctionalities().get(0);
        sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, sensorID);

        // Set  behaviour to list Sensors of Device - findByDeviceIDAndSensorFunctionality() in SensorRepository:
        // use factory to create an object that is equal to the one created by services
        String sensorClass = sensorFunctionalityRepository.getClassNameForSensorFunctionalityID(sensorFunctionalityID);
        Sensor sensor = factorySensor.createSensor(sensorID, deviceID, sensorFunctionalityID, sensorClass);
        // create a list of data models to return
        SensorDataModel sensorDataModel = new SensorDataModel(sensor);
        List<SensorDataModel> listOfSensorDataModelsInRepo = List.of(sensorDataModel);
        when(sensorRepositorySpringDataDouble.findByDeviceIDAndSensorFunctionalityID(deviceID.toString(), sensorFunctionalityID.toString())).thenReturn(listOfSensorDataModelsInRepo);

        //////////////////// TEST CASE ////////////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        // List devices in selected room to select one
        List<DeviceID> listOfDevicesInRoom = deviceService.getListOfDevicesInRoom(selectedRoomID);
        DeviceID selectedDeviceID = listOfDevicesInRoom.get(0);
        DeviceDTO deviceDTO = new DeviceDTO(selectedDeviceID.toString());

        // Assert list of temperature sensors has the correct size and the correct sensor
        List<SensorDTO> sensorDTOList = controller.getListOfTemperatureSensorDTOInDevice(deviceDTO);
        assertEquals(1, sensorDTOList.size());
        assertEquals("RoomTemperature", sensorDTOList.get(0).sensorName);
    }

}