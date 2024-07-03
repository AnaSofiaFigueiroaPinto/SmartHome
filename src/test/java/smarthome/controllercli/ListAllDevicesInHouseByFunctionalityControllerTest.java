package smarthome.controllercli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import jakarta.persistence.TypedQuery;
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
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.SensorFunctionalityRepository;
import smarthome.domain.repository.ActuatorFunctionalityRepository;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensorfunctionality.FactorySensorFunctionality;
import smarthome.domain.sensorfunctionality.ImpFactorySensorFunctionality;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.*;
import smarthome.persistence.jpa.datamodel.*;
import smarthome.persistence.jpa.repositoriesjpa.*;
import smarthome.persistence.repositoriesmem.*;
import smarthome.persistence.springdata.repositoriesspringdata.*;
import smarthome.service.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code ListAllDevicesInHouseByFunctionalityController} class.
 */
class ListAllDevicesInHouseByFunctionalityControllerTest {

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES MEM -----------------------------------------------------

    /**
     * Success case for getting a list of devices by functionality.
     * The test uses in-memory repositories to isolate the test from the database.
     */
    @Test
    void successGetListOfDevicesByFunctionality() {
        // Instantiate needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactoryActuator factoryActuator = new FactoryActuator();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();

        // Create blank data maps as data source to instantiate repositories
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepository = new SensorRepositoryMem(sensorData);

        Map<ActuatorID, Actuator> actuatorData = new HashMap<>();
        ActuatorRepository actuatorRepository = new ActuatorRepositoryMem(actuatorData);

        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        ActuatorFunctionalityRepository actuatorFunctionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);

        // Instantiate needed services
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);
        SensorService sensorService = new SensorService(factorySensor, sensorRepository, deviceRepository, sensorFunctionalityRepository);
        ActuatorService actuatorService = new ActuatorService(factoryActuator, actuatorRepository, deviceRepository, actuatorFunctionalityRepository);

        ListAllDevicesInHouseByFunctionalityService listDevicesByFunctionalityService = new ListAllDevicesInHouseByFunctionalityService(deviceRepository, sensorRepository, actuatorRepository);

        // Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        // Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(2);
        RoomDimensions roomDimensions = new RoomDimensions(3, 4, 5);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        // Create device
        DeviceID deviceID = new DeviceID("Heater");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

        // Create sensor
        SensorID sensorID = new SensorID("TemperatureCelsiusSensor");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");
        sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, sensorID);

        // Create actuator
        ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID("IntegerSetter");
        ActuatorProperties actuatorProperties = new ActuatorProperties();
        ActuatorID actuatorID = new ActuatorID("IntegerSetter");
        actuatorService.createActuatorAndSave(actuatorFunctionalityID, actuatorID, actuatorProperties, deviceID);

        // Instantiate controller to be tested
        ListAllDevicesInHouseByFunctionalityController controller = new ListAllDevicesInHouseByFunctionalityController(listDevicesByFunctionalityService);

        // Call the method under test
        Map<Object, Map<RoomDTO, DeviceDTO>> result = controller.getListOfDevicesByFunctionality();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        result.forEach((key, value) -> {

            // Assert each value map contains the expected type of objects
            value.forEach((roomDTO, deviceDTO) -> {
                assertInstanceOf(DeviceDTO.class, deviceDTO);
                assertInstanceOf(RoomDTO.class, roomDTO);
            });
        });
    }

    /**
     * Success case for getting a list of devices by functionality when no devices, and therefore,
     * sensors or actuators exist.
     * The test uses in-memory repositories to isolate the test from the database.
     */
    @Test
    void successGetListOfDevicesByFunctionalityWhenNoDevicesExist() {
        // Instantiate needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        // Create blank data maps as data source to instantiate repositories
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepository = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepository = new SensorRepositoryMem(sensorData);

        Map<ActuatorID, Actuator> actuatorData = new HashMap<>();
        ActuatorRepository actuatorRepository = new ActuatorRepositoryMem(actuatorData);

        // Instantiate needed services
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        ListAllDevicesInHouseByFunctionalityService listDevicesByFunctionalityService = new ListAllDevicesInHouseByFunctionalityService(deviceRepository, sensorRepository, actuatorRepository);

        // Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        // Create room
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(2);
        RoomDimensions roomDimensions = new RoomDimensions(3, 4, 5);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);

        // No devices, nor sensors, or actuators created

        // Instantiate controller to be tested
        ListAllDevicesInHouseByFunctionalityController controller = new ListAllDevicesInHouseByFunctionalityController(listDevicesByFunctionalityService);

        // Call the method under test
        Map<Object, Map<RoomDTO, DeviceDTO>> result = controller.getListOfDevicesByFunctionality();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES JPA -----------------------------------------------------

    /**
     * Sucessfully group devices by functionality and their location using JPA repositories.
     * Due to using JPA repositories, isolation between repository class and EntityManager was applied.
     * Behaviours were set for all Entities that were saved/needed to be found during controller execution.
     */
    @Test
    void successfullyGroupByFunctionalityUseJPARepositories () {
        //ENTITY MANAGER DOUBLE ----------------------------------------------------------------------------------------
        EntityManager managerDouble = mock(EntityManager.class);
        EntityTransaction transactionDouble = mock(EntityTransaction.class);
        when(managerDouble.getTransaction()).thenReturn(transactionDouble);

        //House --------------------------------------------------------------------------------------------------------
        //Factory
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        //Repo
        HouseRepository houseRepository = new HouseRepositoryJPAImp(factoryHouse, managerDouble);

        //Room ---------------------------------------------------------------------------------------------------------
        //Factory
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        //Repo
        RoomRepository roomRepository = new RoomRepositoryJPAImp(factoryRoom, managerDouble);

        //Device -------------------------------------------------------------------------------------------------------
        //Factory
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        //Repo
        DeviceRepository deviceRepository = new DeviceRepositoryJPAImp(factoryDevice, managerDouble);

        //SensorFunctionality ------------------------------------------------------------------------------------------
        //Factory
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();
        //Repo
        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //ActuatorFunctionality ----------------------------------------------------------------------------------------
        //Factory
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();
        //Repo
        ActuatorFunctionalityRepository actuatorFunctionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);

        //Actuator -----------------------------------------------------------------------------------------------------
        //Factory
        FactoryActuator factoryActuator = new FactoryActuator();
        //Repo
        ActuatorRepository actuatorRepository = new ActuatorRepositoryJPAImp(factoryActuator, managerDouble);

        //Sensor -------------------------------------------------------------------------------------------------------
        //Factory
        FactorySensor sensorFactory = new FactorySensor();
        //Repo
        SensorRepository sensorRepository = new SensorRepositoryJPAImp(sensorFactory, managerDouble);

        // -------------------------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        //Create House:
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));
        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(managerDouble.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        //EntityManager - House ----------------------------------------------------------------------------------------
        // Use FactoryHouse to create a House object entity that is equal to the one created by the HouseService
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseID, location);
        // Use the House object created to create a HouseDataModel object
        HouseDataModel houseDM = new HouseDataModel(house);
        // Set behaviour to find method of the EntityManager to return the HouseDataModel object created
        when(managerDouble.find(HouseDataModel.class, houseID.toString())).thenReturn(houseDM);
        // -------------------------------------------------------------------------------------------------------------

        // Create Rooms:
        //RoomService
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        RoomID roomID1ToBeAdded = new RoomID("RoomForTestDevice1");
        RoomFloor roomFloor1 = new RoomFloor(1);
        RoomDimensions roomDimensions1 = new RoomDimensions(1, 2, 3);

        RoomID roomID1 = roomService.createRoomAndSave(roomID1ToBeAdded, roomFloor1, roomDimensions1, houseID);

        RoomID roomID2ToBeAdded = new RoomID("RoomForTestDevice2");
        RoomFloor roomFloor2 = new RoomFloor(2);
        RoomDimensions roomDimensions2 = new RoomDimensions(4, 5, 6);

        RoomID roomID2 = roomService.createRoomAndSave(roomID2ToBeAdded, roomFloor2, roomDimensions2, houseID);

        //EntityManager - Room -----------------------------------------------------------------------------------------
        // Use FactoryRoom to create a Room object entity that is equal to the one created by the RoomService
        Room room1 = factoryRoom.createRoom(roomID1, roomFloor1, roomDimensions1, houseID);
        Room room2 = factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions2, houseID);
        // Use the Room object created to create a RoomDataModel object
        RoomDataModel roomDM1 = new RoomDataModel(room1);
        RoomDataModel roomDM2 = new RoomDataModel(room2);
        // Set behaviour to find method of the EntityManager to return the RoomDataModel object created
        when(managerDouble.find(RoomDataModel.class, roomID1.toString())).thenReturn(roomDM1);
        when(managerDouble.find(RoomDataModel.class, roomID2.toString())).thenReturn(roomDM2);

        // -------------------------------------------------------------------------------------------------------------

        // -------------------------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        // Create services to populate with Devices, Sensors and Actuators:
        // -------------------------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------

        //Get Functionalities for Actuator and Sensors -----------------------------------------------------------------
        ActuatorFunctionalityService actuatorFunctionalityService = new ActuatorFunctionalityService(actuatorFunctionalityRepository);
        SensorFunctionalityService sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepository);

        //DeviceService
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);

        //SensorService
        SensorService sensorService = new SensorService(sensorFactory, sensorRepository, deviceRepository, sensorFunctionalityRepository);

        //ActuatorService
        ActuatorService actuatorService = new ActuatorService(factoryActuator, actuatorRepository, deviceRepository, actuatorFunctionalityRepository);

        // -------------------------------------------------------------------------------------------------------------
        //Create Devices -----------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        DeviceID deviceID1ToBeAdded = new DeviceID("TestDevice1");
        DeviceID deviceID2ToBeAdded = new DeviceID("TestDevice2");
        DeviceModel deviceModel1 = new DeviceModel("TestModel1");
        DeviceModel deviceModel2 = new DeviceModel("TestModel2");

        DeviceID deviceID1 = deviceService.createDeviceAndSaveToRepository(deviceID1ToBeAdded, deviceModel1, roomID1ToBeAdded);
        DeviceID deviceID2 = deviceService.createDeviceAndSaveToRepository(deviceID2ToBeAdded, deviceModel2, roomID2ToBeAdded);

        //EntityManager - Device ---------------------------------------------------------------------------------------
        // Use FactoryDevice to create a Device object entity that is equal to the one created by the DeviceService
        Device device1 = factoryDevice.createDevice(deviceID1, deviceModel1, roomID1);
        Device device2 = factoryDevice.createDevice(deviceID2, deviceModel2, roomID2);
        // Use the Device object created to create a DeviceDataModel object
        DeviceDataModel deviceDM1 = new DeviceDataModel(device1);
        DeviceDataModel deviceDM2 = new DeviceDataModel(device2);
        // Set behaviour to find method of the EntityManager to return the DeviceDataModel object created
        when(managerDouble.find(DeviceDataModel.class, deviceID1.toString())).thenReturn(deviceDM1);
        when(managerDouble.find(DeviceDataModel.class, deviceID2.toString())).thenReturn(deviceDM2);
        // -------------------------------------------------------------------------------------------------------------

        // -------------------------------------------------------------------------------------------------------------
        //Create Sensors and Actuators for Devices ---------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        //Device1 -- 2 Sensors (Same functionality) and 1 Actuator
        SensorFunctionalityID sensorFunctionalityID1 = sensorFunctionalityService.getListOfSensorFunctionalities().get(0);
        SensorFunctionalityID sensorFunctionalityID2 = sensorFunctionalityService.getListOfSensorFunctionalities().get(1);
        SensorID sensorID1ToBeAdded = new SensorID("TestSensor1");
        SensorID sensorID2ToBeAdded = new SensorID("TestSensor2");

        SensorID sensorID1 = sensorService.createSensorAndSave(deviceID1, sensorFunctionalityID1, sensorID1ToBeAdded);
        SensorID sensorID2 = sensorService.createSensorAndSave(deviceID1, sensorFunctionalityID2, sensorID2ToBeAdded);

        ActuatorFunctionalityID actuatorFunctionalityID1 = actuatorFunctionalityService.getListOfActuatorFunctionalities().get(0);
        ActuatorProperties actuatorProperties1 = new ActuatorProperties(20,10,30);
        ActuatorID actuatorID1ToBeAdded = new ActuatorID("TestActuator1");

        ActuatorID actuatorID1 = actuatorService.createActuatorAndSave(actuatorFunctionalityID1, actuatorID1ToBeAdded, actuatorProperties1, deviceID1);

        //Device2 -- 1 Sensor and 2 Actuator
        SensorFunctionalityID sensorFunctionalityID3 = sensorFunctionalityService.getListOfSensorFunctionalities().get(2);
        SensorID sensorID3ToBeAdded = new SensorID("TestSensor3");

        SensorID sensorID3 = sensorService.createSensorAndSave(deviceID2, sensorFunctionalityID3, sensorID3ToBeAdded);

        ActuatorFunctionalityID actuatorFunctionalityID2 = actuatorFunctionalityService.getListOfActuatorFunctionalities().get(1);
        ActuatorFunctionalityID actuatorFunctionalityID3 = actuatorFunctionalityService.getListOfActuatorFunctionalities().get(2);
        ActuatorProperties actuatorProperties2 = new ActuatorProperties();
        ActuatorProperties actuatorProperties3 = new ActuatorProperties(60,50);
        ActuatorID actuatorID2ToBeAdded = new ActuatorID("TestActuator2");
        ActuatorID actuatorID3ToBeAdded = new ActuatorID("TestActuator3");

        ActuatorID actuatorID2 = actuatorService.createActuatorAndSave(actuatorFunctionalityID2, actuatorID2ToBeAdded, actuatorProperties2, deviceID2);
        ActuatorID actuatorID3 = actuatorService.createActuatorAndSave(actuatorFunctionalityID3, actuatorID3ToBeAdded, actuatorProperties3, deviceID2);

        //EntityManager - Sensor ---------------------------------------------------------------------------------------
        // Use FactorySensor to create a Sensor object entity that is equal to the one created by the SensorService
        String sensorClassForSensor1 = sensorFunctionalityRepository.getClassNameForSensorFunctionalityID(sensorFunctionalityID1);
        Sensor sensor1 = sensorFactory.createSensor(sensorID1, deviceID1, sensorFunctionalityID1, sensorClassForSensor1);

        String sensorClassForSensor2 = sensorFunctionalityRepository.getClassNameForSensorFunctionalityID(sensorFunctionalityID2);
        Sensor sensor2 = sensorFactory.createSensor(sensorID2, deviceID1, sensorFunctionalityID2, sensorClassForSensor2);

        String sensorClassForSensor3 = sensorFunctionalityRepository.getClassNameForSensorFunctionalityID(sensorFunctionalityID3);
        Sensor sensor3 = sensorFactory.createSensor(sensorID3, deviceID2, sensorFunctionalityID3, sensorClassForSensor3);
        // Use the Sensor object created to create a SensorDataModel object
        SensorDataModel sensorDM1 = new SensorDataModel(sensor1);
        SensorDataModel sensorDM2 = new SensorDataModel(sensor2);
        SensorDataModel sensorDM3 = new SensorDataModel(sensor3);
        // Set behaviour to find method of the EntityManager to return the SensorDataModel object created
        when(managerDouble.find(SensorDataModel.class, sensorID1.toString())).thenReturn(sensorDM1);
        when(managerDouble.find(SensorDataModel.class, sensorID2.toString())).thenReturn(sensorDM2);
        when(managerDouble.find(SensorDataModel.class, sensorID3.toString())).thenReturn(sensorDM3);
        // -------------------------------------------------------------------------------------------------------------

        //EntityManager - Actuator -------------------------------------------------------------------------------------
        // Use Actuator Functionality Repository to get Actuator Class
        String actuatorClass1 = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID(actuatorFunctionalityID1);
        String actuatorClass2 = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID(actuatorFunctionalityID2);
        String actuatorClass3 = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID(actuatorFunctionalityID3);
        // Use FactoryActuator to create an Actuator object entity that is equal to the one created by the ActuatorService
        Actuator actuator1 = factoryActuator.createActuator(actuatorID1, actuatorFunctionalityID1, actuatorProperties1, deviceID1, actuatorClass1);
        Actuator actuator2 = factoryActuator.createActuator(actuatorID2, actuatorFunctionalityID2, actuatorProperties2, deviceID2, actuatorClass2);
        Actuator actuator3 = factoryActuator.createActuator(actuatorID3, actuatorFunctionalityID3, actuatorProperties3, deviceID2, actuatorClass3);

        // Use the Actuator object created to create a ActuatorDataModel object
        ActuatorDataModel actuatorDM1 = new ActuatorDataModel(actuator1);
        ActuatorDataModel actuatorDM2 = new ActuatorDataModel(actuator2);
        ActuatorDataModel actuatorDM3 = new ActuatorDataModel(actuator3);

        // Set behaviour to find method of the EntityManager to return the ActuatorDataModel object created
        when(managerDouble.find(ActuatorDataModel.class, actuatorID1.toString())).thenReturn(actuatorDM1);
        when(managerDouble.find(ActuatorDataModel.class, actuatorID2.toString())).thenReturn(actuatorDM2);
        when(managerDouble.find(ActuatorDataModel.class, actuatorID3.toString())).thenReturn(actuatorDM3);
        // -------------------------------------------------------------------------------------------------------------

        //Create Service for Controller --------------------------------------------------------------------------------
        ListAllDevicesInHouseByFunctionalityService service = new ListAllDevicesInHouseByFunctionalityService(deviceRepository, sensorRepository, actuatorRepository);

        //Create Controller --------------------------------------------------------------------------------------------
        ListAllDevicesInHouseByFunctionalityController controller = new ListAllDevicesInHouseByFunctionalityController(service);

        //Standardize findAllEntities() method for all Repositories ---------------------------------------------------
        //Return list of Room DataModels -------------------------------------------------------------------------------
        Query queryDouble = mock(Query.class);
        when(managerDouble.createQuery("SELECT e FROM RoomDataModel e")).thenReturn(queryDouble);
        List<RoomDataModel> listOfRoomDataModel = List.of(roomDM1, roomDM2);
        when(queryDouble.getResultList()).thenReturn(listOfRoomDataModel);
        // -------------------------------------------------------------------------------------------------------------

        //Return list of Device DataModels -----------------------------------------------------------------------------
        Query queryDouble2 = mock(Query.class);
        when(managerDouble.createQuery("SELECT e FROM DeviceDataModel e")).thenReturn(queryDouble2);
        List<DeviceDataModel> listOfDeviceDataModel = List.of(deviceDM1, deviceDM2);
        when(queryDouble2.getResultList()).thenReturn(listOfDeviceDataModel);
        // -------------------------------------------------------------------------------------------------------------

        //Return list of Sensor DataModels -----------------------------------------------------------------------------
        Query queryDouble3 = mock(Query.class);
        when(managerDouble.createQuery("SELECT e FROM SensorDataModel e")).thenReturn(queryDouble3);
        List<SensorDataModel> listOfSensorDataModel = List.of(sensorDM1, sensorDM2, sensorDM3);
        when(queryDouble3.getResultList()).thenReturn(listOfSensorDataModel);
        // -------------------------------------------------------------------------------------------------------------

        //Return list of Actuator DataModels ---------------------------------------------------------------------------
        Query queryDouble4 = mock(Query.class);
        when(managerDouble.createQuery("SELECT e FROM ActuatorDataModel e")).thenReturn(queryDouble4);
        List<ActuatorDataModel> listOfActuatorDataModel = List.of(actuatorDM1, actuatorDM2, actuatorDM3);
        when(queryDouble4.getResultList()).thenReturn(listOfActuatorDataModel);
        // -------------------------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------

        // Standardize findEntityByID() method for Device Repo ---------------------------------------------------------
        when(managerDouble.find(DeviceDataModel.class, deviceID1.toString())).thenReturn(deviceDM1);
        when(managerDouble.find(DeviceDataModel.class, deviceID2.toString())).thenReturn(deviceDM2);
        // -------------------------------------------------------------------------------------------------------------

        //Call the method under test -----------------------------------------------------------------------------------
        Map<Object, Map<RoomDTO, DeviceDTO>> result = controller.getListOfDevicesByFunctionality();

        //Assert that Map key is not null. Can't currently check that is instanceOf because it could be ActuatorFunctionality or SensorFunctionality
        //Assert that subMap contains instances of DeviceDTO and RoomDTO objects and that those are not null
        result.forEach((key, value) -> {
            assertNotNull(key);
            value.forEach((roomDTO, deviceDTO) -> {
                assertInstanceOf(DeviceDTO.class, deviceDTO);
                assertInstanceOf(RoomDTO.class, roomDTO);
            });
        });
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES SPRING --------------------------------------------------

    /**
     * Sucessfully group devices by functionality and their location using Spring repositories.
     * Due to using Spring repositories, isolation between repository class and RepositorySpring, of each specific entity was applied.
     * Behaviours were set for all RepositorySpring methods that are called during controller execution.
     */
    @Test
    void successfullyGroupByFunctionalityUseSpringDataRepositories () {
        //House --------------------------------------------------------------------------------------------------------
        //Factory
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        //SpringDataDouble
        HouseRepositorySpringData houseSpringDataDouble = mock(HouseRepositorySpringData.class);
        //Mapper
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        //Repo
        HouseRepository houseRepository = new HouseRepositorySpringDataImp(factoryHouse, houseSpringDataDouble, mapperHouseDataModel);

        //Room ---------------------------------------------------------------------------------------------------------
        //Factory
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        //SpringDataDouble
        RoomRepositorySpringData roomSpringDataDouble = mock(RoomRepositorySpringData.class);
        //Mapper
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
        //Repo
        RoomRepository roomRepository = new RoomRepositorySpringDataImp(roomSpringDataDouble, factoryRoom, mapperRoomDataModel);

        //Device -------------------------------------------------------------------------------------------------------
        //Factory
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        //SpringDataDouble
        DeviceRepositorySpringData deviceSpringDataDouble = mock(DeviceRepositorySpringData.class);
        //Mapper
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        //Repo
        DeviceRepository deviceRepository = new DeviceRepositorySpringDataImp(factoryDevice, deviceSpringDataDouble, mapperDeviceDataModel);

        //SensorFunctionality ------------------------------------------------------------------------------------------
        //Factory
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();
        //Repo
        SensorFunctionalityRepository sensorFunctionalityRepository = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //ActuatorFunctionality ----------------------------------------------------------------------------------------
        //Factory
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();
        //Repo
        ActuatorFunctionalityRepository actuatorFunctionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);

        //Sensor -------------------------------------------------------------------------------------------------------
        //Factory
        FactorySensor sensorFactory = new FactorySensor();
        //SpringDataDouble
        SensorRepositorySpringData sensorSpringDataDouble = mock(SensorRepositorySpringData.class);
        //Mapper
        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();
        //Repo
        SensorRepository sensorRepository = new SensorRepositorySpringDataImp(sensorSpringDataDouble, sensorFactory, mapperSensorDataModel);

        //Actuator -----------------------------------------------------------------------------------------------------
        //Factory
        FactoryActuator factoryActuator = new FactoryActuator();
        //SpringDataDouble
        ActuatorRepositorySpringData actuatorSpringDataDouble = mock(ActuatorRepositorySpringData.class);
        //Mapper
        MapperActuatorDataModel mapperActuatorDataModel = new MapperActuatorDataModel();
        //Repo
        ActuatorRepository actuatorRepository = new ActuatorRepositorySpringDataImp(actuatorSpringDataDouble, factoryActuator, mapperActuatorDataModel);

        // -------------------------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        //Create House:
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        //SpringData - House ----------------------------------------------------------------------------------------
        when(houseSpringDataDouble.existsById(houseID.toString())).thenReturn(true);
        // -------------------------------------------------------------------------------------------------------------

        // Create Rooms:
        //RoomService
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        RoomID roomID1ToBeAdded = new RoomID("RoomForTestDevice1");
        RoomID roomID2ToBeAdded = new RoomID("RoomForTestDevice2");

        RoomFloor roomFloor1 = new RoomFloor(1);
        RoomFloor roomFloor2 = new RoomFloor(2);
        RoomDimensions roomDimensions1 = new RoomDimensions(1, 2, 3);

        RoomID roomID1 = roomService.createRoomAndSave(roomID1ToBeAdded, roomFloor1, roomDimensions1, houseID);

        RoomDimensions roomDimensions2 = new RoomDimensions(4, 5, 6);

        RoomID roomID2 = roomService.createRoomAndSave(roomID2ToBeAdded, roomFloor2, roomDimensions2, houseID);

        //SpringData - Room -----------------------------------------------------------------------------------------
        when(roomSpringDataDouble.existsById(roomID1.toString())).thenReturn(true);
        when(roomSpringDataDouble.existsById(roomID2.toString())).thenReturn(true);
        // -------------------------------------------------------------------------------------------------------------

        // -------------------------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        // Create services to populate with Devices, Sensors and Actuators:
        // -------------------------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------

        //Get Functionalities for Actuator and Sensors -----------------------------------------------------------------
        ActuatorFunctionalityService actuatorFunctionalityService = new ActuatorFunctionalityService(actuatorFunctionalityRepository);
        SensorFunctionalityService sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepository);

        //DeviceService
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepository, roomRepository, houseRepository);

        //SensorService
        SensorService sensorService = new SensorService(sensorFactory, sensorRepository, deviceRepository, sensorFunctionalityRepository);

        //ActuatorService
        ActuatorService actuatorService = new ActuatorService(factoryActuator, actuatorRepository, deviceRepository, actuatorFunctionalityRepository);

        // -------------------------------------------------------------------------------------------------------------
        //Create Devices -----------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        DeviceID deviceID1ToBeAdded = new DeviceID("TestDevice1");
        DeviceID deviceID2ToBeAdded = new DeviceID("TestDevice2");
        DeviceModel deviceModel1 = new DeviceModel("TestModel1");
        DeviceModel deviceModel2 = new DeviceModel("TestModel2");

        DeviceID deviceID1 = deviceService.createDeviceAndSaveToRepository(deviceID1ToBeAdded, deviceModel1, roomID1ToBeAdded);
        DeviceID deviceID2 = deviceService.createDeviceAndSaveToRepository(deviceID2ToBeAdded, deviceModel2, roomID2ToBeAdded);

        Device device1 = factoryDevice.createDevice(deviceID1, deviceModel1, roomID1);
        Device device2 = factoryDevice.createDevice(deviceID2, deviceModel2, roomID2);

        DeviceDataModel deviceDM1 = new DeviceDataModel(device1);
        DeviceDataModel deviceDM2 = new DeviceDataModel(device2);

        //SpringData - Device ------------------------------------------------------------------------------------------
        when(deviceSpringDataDouble.findById(deviceID1.toString())).thenReturn(Optional.of(deviceDM1));
        when(deviceSpringDataDouble.findById(deviceID2.toString())).thenReturn(Optional.of(deviceDM2));

        // -------------------------------------------------------------------------------------------------------------

        //Device1 -- 2 Sensors (Same functionality) and 1 Actuator
        //Device2 -- 1 Sensor and 2 Actuator

        // -------------------------------------------------------------------------------------------------------------
        //Create Sensors -----------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        SensorFunctionalityID sensorFunctionalityID1 = sensorFunctionalityService.getListOfSensorFunctionalities().get(0);
        SensorFunctionalityID sensorFunctionalityID2 = sensorFunctionalityService.getListOfSensorFunctionalities().get(1);
        SensorFunctionalityID sensorFunctionalityID3 = sensorFunctionalityService.getListOfSensorFunctionalities().get(2);

        SensorID sensorID1ToBeAdded = new SensorID("TestSensor1");
        SensorID sensorID2ToBeAdded = new SensorID("TestSensor2");
        SensorID sensorID3ToBeAdded = new SensorID("TestSensor3");

        SensorID sensorID1 = sensorService.createSensorAndSave(deviceID1, sensorFunctionalityID1, sensorID1ToBeAdded);
        SensorID sensorID2 = sensorService.createSensorAndSave(deviceID1, sensorFunctionalityID2, sensorID2ToBeAdded);
        SensorID sensorID3 = sensorService.createSensorAndSave(deviceID2, sensorFunctionalityID3, sensorID3ToBeAdded);

        String sensorClassForSensor1 = sensorFunctionalityRepository.getClassNameForSensorFunctionalityID(sensorFunctionalityID1);
        String sensorClassForSensor2 = sensorFunctionalityRepository.getClassNameForSensorFunctionalityID(sensorFunctionalityID2);
        String sensorClassForSensor3 = sensorFunctionalityRepository.getClassNameForSensorFunctionalityID(sensorFunctionalityID3);

        Sensor sensor1 = sensorFactory.createSensor(sensorID1, deviceID1, sensorFunctionalityID1, sensorClassForSensor1);
        Sensor sensor2 = sensorFactory.createSensor(sensorID2, deviceID1, sensorFunctionalityID2, sensorClassForSensor2);
        Sensor sensor3 = sensorFactory.createSensor(sensorID3, deviceID2, sensorFunctionalityID3, sensorClassForSensor3);

        SensorDataModel sensorDM1 = new SensorDataModel(sensor1);
        SensorDataModel sensorDM2 = new SensorDataModel(sensor2);
        SensorDataModel sensorDM3 = new SensorDataModel(sensor3);


        //SpringData - Sensor ------------------------------------------------------------------------------------------
        when(sensorSpringDataDouble.findById(sensorID1.toString())).thenReturn(Optional.of(sensorDM1));
        when(sensorSpringDataDouble.findById(sensorID2.toString())).thenReturn(Optional.of(sensorDM2));
        when(sensorSpringDataDouble.findById(sensorID3.toString())).thenReturn(Optional.of(sensorDM3));
        when(sensorSpringDataDouble.findAll()).thenReturn(List.of(sensorDM1, sensorDM2, sensorDM3));
        // -------------------------------------------------------------------------------------------------------------

        // -------------------------------------------------------------------------------------------------------------
        //Create Actuators ---------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        ActuatorFunctionalityID actuatorFunctionalityID1 = actuatorFunctionalityService.getListOfActuatorFunctionalities().get(0);
        ActuatorFunctionalityID actuatorFunctionalityID2 = actuatorFunctionalityService.getListOfActuatorFunctionalities().get(1);
        ActuatorFunctionalityID actuatorFunctionalityID3 = actuatorFunctionalityService.getListOfActuatorFunctionalities().get(2);

        ActuatorProperties actuatorProperties1 = new ActuatorProperties(20,10,30);
        ActuatorProperties actuatorProperties2 = new ActuatorProperties();
        ActuatorProperties actuatorProperties3 = new ActuatorProperties(60,50);

        ActuatorID actuatorID1ToBeAdded = new ActuatorID("TestActuator1");
        ActuatorID actuatorID2ToBeAdded = new ActuatorID("TestActuator2");
        ActuatorID actuatorID3ToBeAdded = new ActuatorID("TestActuator3");

        ActuatorID actuatorID1 = actuatorService.createActuatorAndSave(actuatorFunctionalityID1, actuatorID1ToBeAdded, actuatorProperties1, deviceID1);
        ActuatorID actuatorID2 = actuatorService.createActuatorAndSave(actuatorFunctionalityID2, actuatorID2ToBeAdded, actuatorProperties2, deviceID2);
        ActuatorID actuatorID3 = actuatorService.createActuatorAndSave(actuatorFunctionalityID3, actuatorID3ToBeAdded, actuatorProperties3, deviceID2);

        String actuatorClass1 = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID(actuatorFunctionalityID1);
        String actuatorClass2 = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID(actuatorFunctionalityID2);
        String actuatorClass3 = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID(actuatorFunctionalityID3);

        Actuator actuator1 = factoryActuator.createActuator(actuatorID1, actuatorFunctionalityID1, actuatorProperties1, deviceID1, actuatorClass1);
        Actuator actuator2 = factoryActuator.createActuator(actuatorID2, actuatorFunctionalityID2, actuatorProperties2, deviceID2, actuatorClass2);
        Actuator actuator3 = factoryActuator.createActuator(actuatorID3, actuatorFunctionalityID3, actuatorProperties3, deviceID2, actuatorClass3);

        ActuatorDataModel actuatorDM1 = new ActuatorDataModel(actuator1);
        ActuatorDataModel actuatorDM2 = new ActuatorDataModel(actuator2);
        ActuatorDataModel actuatorDM3 = new ActuatorDataModel(actuator3);

        //SpringData - Actuator ----------------------------------------------------------------------------------------
        when(actuatorSpringDataDouble.findById(actuatorID1.toString())).thenReturn(Optional.of(actuatorDM1));
        when(actuatorSpringDataDouble.findById(actuatorID2.toString())).thenReturn(Optional.of(actuatorDM2));
        when(actuatorSpringDataDouble.findById(actuatorID3.toString())).thenReturn(Optional.of(actuatorDM3));
        when(actuatorSpringDataDouble.findAll()).thenReturn(List.of(actuatorDM1, actuatorDM2, actuatorDM3));
        // -------------------------------------------------------------------------------------------------------------

        //Create Service for Controller --------------------------------------------------------------------------------
        ListAllDevicesInHouseByFunctionalityService service = new ListAllDevicesInHouseByFunctionalityService(deviceRepository, sensorRepository, actuatorRepository);

        //Create Controller --------------------------------------------------------------------------------------------
        ListAllDevicesInHouseByFunctionalityController controller = new ListAllDevicesInHouseByFunctionalityController(service);

        //Call the method under test -----------------------------------------------------------------------------------
        Map<Object, Map<RoomDTO, DeviceDTO>> result = controller.getListOfDevicesByFunctionality();

        //Assert that Map key is not null. Can't currently check that is instanceOf because it could be ActuatorFunctionality or SensorFunctionality
        //Assert that subMap contains instances of DeviceDTO and RoomDTO objects and that those are not null
        result.forEach((key, value) -> {
            assertNotNull(key);
            value.forEach((roomDTO, deviceDTO) -> {
                assertInstanceOf(DeviceDTO.class, deviceDTO);
                assertInstanceOf(RoomDTO.class, roomDTO);
            });
        });
    }
}