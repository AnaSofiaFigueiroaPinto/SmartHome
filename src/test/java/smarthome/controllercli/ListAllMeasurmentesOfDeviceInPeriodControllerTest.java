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
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensorfunctionality.FactorySensorFunctionality;
import smarthome.domain.sensorfunctionality.ImpFactorySensorFunctionality;
import smarthome.domain.value.ImpFactoryInstantTimeLocationValue;
import smarthome.domain.value.ImpFactoryInstantTimeValue;
import smarthome.domain.value.ImpFactoryPeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.ReadingDTO;
import smarthome.mapper.SensorFunctionalityDTO;
import smarthome.persistence.jpa.datamodel.*;
import smarthome.persistence.jpa.repositoriesjpa.*;
import smarthome.persistence.repositoriesmem.*;
import smarthome.persistence.springdata.repositoriesspringdata.*;
import smarthome.service.*;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListAllMeasurmentesOfDeviceInPeriodControllerTest {

    /**
     * Test validates the successful retrieval of all values associated with the sensors present
     * in a given Device within a given period. Test is done using a Memory Repositories.
     * <p>
     * The Device used in the test contains 4 sensors, each with a different SensorFunctionality:
     * - Sensor1 -> TemperatureCelsius;
     * - Sensor2 -> BinaryStatus;
     * - Sensor3 -> WindSpeedAndDirection;
     * - Sensor4 -> DewPointCelsius.
     * <p>
     * The test creates and adds to Value persistence:
     * - 3 Values for Sensor1:
     * - 2 Values within the given period (1 value with 1 unit) [26 Celsius] and [12 Celsius]
     * - 1 Value outside the given period (1 value with 1 unit) [-100 Celsius]
     * - 1 Value for Sensor2:
     * - 1 Value within the given period (1 value with no(*) unit) [OFF]
     * - 1 Value for Sensor3:
     * - 1 Value within the given period (2 values with 2 units) [10 m/s and 3π/4 rad]
     * - 0 Values for Sensor4.
     * <p>
     * To assert that all measurements are present, List of readings is extracted from the return map and
     * Strings are compared to the expected values.
     */
    @Test
    void successfullyListAllValuesForDeviceMemRepository() {
        //Device ------------------------------------------------------------------------------------------------
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        RoomID roomID = new RoomID("Living Room");
        DeviceID deviceID = new DeviceID("DeviceForTest");
        DeviceModel deviceModel = new DeviceModel("TestModel");
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        Map<DeviceID, Device> deviceData = new HashMap<>();
        deviceData.put(deviceID, device);
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(deviceData);

        //Sensor ------------------------------------------------------------------------------------------------
        //Sensor Functionality
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();
        SensorFunctionalityRepositoryMem sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Sensor
        FactorySensor factorySensor = new FactorySensor();
        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepositoryMem sensorRepo = new SensorRepositoryMem(sensorData);

        //Setup SensorService
        SensorService sensorService = new SensorService(factorySensor, sensorRepo, deviceRepositoryMem, sensorFunctionalityRepositoryMem);


        //Create 4 Sensors using the service to assure they are saved in SensorRepo
        //Create Sensor1
        SensorID sensorID1 = new SensorID("Sensor1");
        SensorFunctionalityID sensorFunctionalityID1 = new SensorFunctionalityID("TemperatureCelsius");
        sensorService.createSensorAndSave(deviceID, sensorFunctionalityID1, sensorID1);

        //Create Sensor2
        SensorID sensorID2 = new SensorID("Sensor2");
        SensorFunctionalityID sensorFunctionalityID2 = new SensorFunctionalityID("BinaryStatus");
        sensorService.createSensorAndSave(deviceID, sensorFunctionalityID2, sensorID2);

        //Create Sensor3
        SensorID sensorID3 = new SensorID("Sensor3");
        SensorFunctionalityID sensorFunctionalityID3 = new SensorFunctionalityID("WindSpeedAndDirection");
        sensorService.createSensorAndSave(deviceID, sensorFunctionalityID3, sensorID3);

        //Create Sensor4
        SensorID sensorID4 = new SensorID("Sensor4");
        SensorFunctionalityID sensorFunctionalityID4 = new SensorFunctionalityID("DewPointCelsius");
        sensorService.createSensorAndSave(deviceID, sensorFunctionalityID4, sensorID4);


        //Set up Value Repos ------------------------------------------------------------------------------------

        //Factories Value
        ImpFactoryInstantTimeValue factoryInstantTimeValue = new ImpFactoryInstantTimeValue();

        //Timestamps
        Timestamp instantWithinRange = new Timestamp(System.currentTimeMillis());
        Timestamp instantOutOfRange = new Timestamp(System.currentTimeMillis() - 300000);

        //InstantValueRepo
        //Values for Sensor1
        ValueID valueID1 = new ValueID("Value1Sensor1");
        Reading readingValue1 = new Reading("26", "Celsius");
        Value value1ForSensor1 = factoryInstantTimeValue.createValue(valueID1, sensorID1, readingValue1, instantWithinRange);

        ValueID valueID2 = new ValueID("Value2Sensor1");
        Reading readingValue2 = new Reading("12", "Celsius");
        Value value2ForSensor1 = factoryInstantTimeValue.createValue(valueID2, sensorID1, readingValue2, instantWithinRange);

        ValueID valueID3 = new ValueID("Value3Sensor1");
        Reading readingValue3 = new Reading("-100", "Celsius");
        Value value3ForSensor1 = factoryInstantTimeValue.createValue(valueID3, sensorID1, readingValue3, instantOutOfRange);

        //Values for Sensor2
        ValueID valueID4 = new ValueID("Value1Sensor2");
        Reading readingValue4 = new Reading("OFF", "*");
        Value value1ForSensor2 = factoryInstantTimeValue.createValue(valueID4, sensorID2, readingValue4, instantWithinRange);

        //Values for Sensor3
        ValueID valueID5 = new ValueID("Value1Sensor3");
        Reading readingValue5 = new Reading("10;3π/4", "m/s;rad");
        Value value1ForSensor3 = factoryInstantTimeValue.createValue(valueID5, sensorID3, readingValue5, instantWithinRange);

        Map<ValueID, Value> instantValueMap = new HashMap<>();

        instantValueMap.put(valueID1, value1ForSensor1);
        instantValueMap.put(valueID2, value2ForSensor1);
        instantValueMap.put(valueID3, value3ForSensor1);
        instantValueMap.put(valueID4, value1ForSensor2);
        instantValueMap.put(valueID5, value1ForSensor3);

        InstantTimeValueRepository instantTimeValueRepository = new InstantTimeValueRepositoryMem(instantValueMap);

        //PeriodValueRepo
        Map<ValueID, Value> periodValueMap = new HashMap<>();

        PeriodTimeValueRepository periodTimeValueRepository = new PeriodTimeValueRepositoryMem(periodValueMap);

        //InstantTimeLocationRepo
        Map<ValueID, Value> instantTimeLocationValueMap = new HashMap<>();

        InstantTimeLocationValueRepository instantTimeLocationValueRepository = new InstantTimeLocationValueRepositoryMem(instantTimeLocationValueMap);

        //Create Service
        ValueService service = new ValueService(sensorRepo, sensorFunctionalityRepositoryMem, instantTimeValueRepository, periodTimeValueRepository, instantTimeLocationValueRepository);


        //Create controller ------------------------------------------------------------------------------------------
        ListAllMeasurmentesOfDeviceInPeriodController controller = new ListAllMeasurmentesOfDeviceInPeriodController(service);

        //Start Timestamp
        Timestamp start = new Timestamp(System.currentTimeMillis() - 200000);
        //End Timestamp
        Timestamp end = new Timestamp(System.currentTimeMillis() + 200000);

        //DeviceDTO
        DeviceDTO deviceDTO = new DeviceDTO(deviceID.toString());

        //Call controller method and get result ------------------------------------------------------------------
        Map<SensorFunctionalityDTO, List<ReadingDTO>> result = controller.listAllMeasurmentesOfDeviceInPeriod(deviceDTO, start, end);

        //Create expected result
        String reading1AsString = readingValue1.getAllValuesWithUnits();
        String reading2AsString = readingValue2.getAllValuesWithUnits();
        String reading3AsString = readingValue3.getAllValuesWithUnits();
        String reading4AsString = readingValue4.getAllValuesWithUnits();
        String reading5AsString = readingValue5.getAllValuesWithUnits();

        //Extract necessary comparison from List
        List<String> resultsAsList = new ArrayList<>();

        for (Map.Entry<SensorFunctionalityDTO, List<ReadingDTO>> entry : result.entrySet()) {
            for (ReadingDTO readingDTO : entry.getValue()) {
                resultsAsList.add(readingDTO.valueWithUnit);
            }
        }

        //Values that exist associated with the sensor and are within given period
        assertTrue(resultsAsList.contains(reading1AsString));
        assertTrue(resultsAsList.contains(reading2AsString));
        assertTrue(resultsAsList.contains(reading4AsString));
        assertTrue(resultsAsList.contains(reading5AsString));

        //Values that exist but are not within given period
        assertFalse(resultsAsList.contains(reading3AsString));
    }

    /**
     * Fail to retrieve values for a Device that is null.
     */
    @Test
    void failToRetrieveValuesForNullDevice() {
        Map<ValueID, Value> instantValueMap = new HashMap<>();
        InstantTimeValueRepository instantTimeValueRepository = new InstantTimeValueRepositoryMem(instantValueMap);

        Map<ValueID, Value> periodValueMap = new HashMap<>();
        PeriodTimeValueRepository periodTimeValueRepository = new PeriodTimeValueRepositoryMem(periodValueMap);

        Map<ValueID, Value> instantTimeLocationValueMap = new HashMap<>();
        InstantTimeLocationValueRepository instantTimeLocationValueRepository = new InstantTimeLocationValueRepositoryMem(instantTimeLocationValueMap);

        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();
        SensorFunctionalityRepositoryMem sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepositoryMem sensorRepo = new SensorRepositoryMem(sensorData);

        ValueService service = new ValueService(sensorRepo, sensorFunctionalityRepositoryMem, instantTimeValueRepository, periodTimeValueRepository, instantTimeLocationValueRepository);
        ListAllMeasurmentesOfDeviceInPeriodController controller = new ListAllMeasurmentesOfDeviceInPeriodController(service);

        DeviceDTO deviceDTO = null;

        Timestamp start = new Timestamp(System.currentTimeMillis() - 200000);
        Timestamp end = new Timestamp(System.currentTimeMillis() + 200000);

        assertNull(controller.listAllMeasurmentesOfDeviceInPeriod(deviceDTO, start, end));
    }

    /**
     * Test validates the successful retrieval of all values associated with the sensors present
     * in a given Device within a given period. Test is done using a JPA Repository.
     * <p>
     * The Device used in the test contains 4 sensors, each with a different SensorFunctionality:
     * - Sensor1 -> TemperatureCelsius;
     * - Sensor2 -> BinaryStatus;
     * - Sensor3 -> WindSpeedAndDirection;
     * - Sensor4 -> DewPointCelsius.
     * <p>
     * The test creates and adds to Value persistence:
     * - 3 Values for Sensor1:
     * - 2 Values within the given period (1 value with 1 unit) [26 Celsius] and [12 Celsius]
     * - 1 Value outside the given period (1 value with 1 unit)
     * - 1 Value for Sensor2:
     * - 1 Value within the given period (1 value with no(*) unit) [OFF]
     * - 1 Value for Sensor3:
     * - 1 Value within the given period (2 values with 2 units) [10 m/s and 3π/4 rad]
     * - 0 Values for Sensor4.
     * NOTE: No values outside the given period are returned from persistence
     * since database query would be made in such a way where this would not happen.
     * <p>
     * To assert that all measurements are present, List of readings is extracted from the return map and
     * Strings are compared to the expected values.
     */
    @Test
    void successfullyListAllValuesForDeviceJPARepository() {
        //Creating entity manager double
        //ENTITY MANAGER DOUBLE ---------------------------------------------------------------------------------
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.getTransaction()).thenReturn(transaction);

        //House -------------------------------------------------------------------------------------------------
        //Instantiating a House and saving in the repository
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        HouseRepository houseRepositoryJPA = new HouseRepositoryJPAImp(factoryHouse, manager);

        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007",
                "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));

        HouseService houseService = new HouseService(houseRepositoryJPA, factoryHouse);
        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(manager.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        HouseID houseIDCreated = houseService.createAndSaveHouseWithLocation(location);

        //Creating a HouseDataModel to simulate the behaviour in the persistence
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseIDCreated, location);
        HouseDataModel houseDataModel = new HouseDataModel(house);
        when(manager.find(HouseDataModel.class, houseIDCreated.toString())).thenReturn(houseDataModel);

        //Room --------------------------------------------------------------------------------------------------
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

        //Device ------------------------------------------------------------------------------------------------
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

        //Sensor ------------------------------------------------------------------------------------------------
        //Sensor Functionality
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();
        SensorFunctionalityRepositoryMem sensorFunctionalityRepoMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Sensor
        FactorySensor factorySensor = new FactorySensor();
        SensorRepository sensorRepositoryJPA = new SensorRepositoryJPAImp(factorySensor, manager);

        //Setup SensorService
        SensorService sensorService = new SensorService(factorySensor, sensorRepositoryJPA, deviceRepositoryJPA, sensorFunctionalityRepoMem);

        //Create 4 Sensors using the service to assure they are saved in SensorRepo
        //Create Sensor1
        SensorID sensorID1ForSensor1 = new SensorID("Sensor1");
        SensorFunctionalityID sensorFunctionalityID1 = new SensorFunctionalityID("TemperatureCelsius");
        SensorID sensorID1 = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID1, sensorID1ForSensor1);
        String sensorClassForSensor1 = sensorFunctionalityRepoMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID1);
        Sensor sensor1 = factorySensor.createSensor(sensorID1, deviceID, sensorFunctionalityID1, sensorClassForSensor1);

        //Create Sensor2
        SensorID sensorID2ForSensor2 = new SensorID("Sensor2");
        SensorFunctionalityID sensorFunctionalityID2 = new SensorFunctionalityID("BinaryStatus");
        SensorID sensorID2 = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID2, sensorID2ForSensor2);
        String sensorClassForSensor2 = sensorFunctionalityRepoMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID2);
        Sensor sensor2 = factorySensor.createSensor(sensorID2, deviceID, sensorFunctionalityID2, sensorClassForSensor2);

        //Create Sensor3
        SensorID sensorID3ForSensor3 = new SensorID("Sensor3");
        SensorFunctionalityID sensorFunctionalityID3 = new SensorFunctionalityID("WindSpeedAndDirection");
        SensorID sensorID3 = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID3, sensorID3ForSensor3);
        String sensorClassForSensor3 = sensorFunctionalityRepoMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID3);
        Sensor sensor3 = factorySensor.createSensor(sensorID3, deviceID, sensorFunctionalityID3, sensorClassForSensor3);

        //Create Sensor4
        SensorID sensorID4ForSensor4 = new SensorID("Sensor4");
        SensorFunctionalityID sensorFunctionalityID4 = new SensorFunctionalityID("DewPointCelsius");
        SensorID sensorID4 = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID4, sensorID4ForSensor4);
        String sensorClassForSensor4 = sensorFunctionalityRepoMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID4);
        Sensor sensor4 = factorySensor.createSensor(sensorID4, deviceID, sensorFunctionalityID4, sensorClassForSensor4);

        //STUB TO RETURN SENSORS WHEN REPO IS CALLED -------------------------------------------------------------------
        //Sensor Data Models
        SensorDataModel sensorDM1 = new SensorDataModel(sensor1);
        when(manager.find(SensorDataModel.class, sensorID1.toString())).thenReturn(sensorDM1);
        SensorDataModel sensorDM2 = new SensorDataModel(sensor2);
        when(manager.find(SensorDataModel.class, sensorID2.toString())).thenReturn(sensorDM2);
        SensorDataModel sensorDM3 = new SensorDataModel(sensor3);
        when(manager.find(SensorDataModel.class, sensorID3.toString())).thenReturn(sensorDM3);
        SensorDataModel sensorDM4 = new SensorDataModel(sensor4);
        when(manager.find(SensorDataModel.class, sensorID4.toString())).thenReturn(sensorDM4);

        Query querySensorDouble = mock(Query.class);
        when(manager.createQuery("SELECT e FROM SensorDataModel e WHERE e.deviceID = :deviceID")).thenReturn(querySensorDouble);
        when(querySensorDouble.getResultList()).thenReturn(List.of(sensorDM1, sensorDM2, sensorDM3, sensorDM4));

        //--------------------------------------------------------------------------------------------------------------

        //Set up Value Repos -------------------------------------------------------------------------------------------

        //Factories Value
        ImpFactoryInstantTimeValue factoryInstantTimeValue = new ImpFactoryInstantTimeValue();

        //Timestamps
        Timestamp instantWithinRange = new Timestamp(System.currentTimeMillis());

        //InstantValueRepo
        //Values for Sensor1
        ValueID valueID1 = new ValueID("Value1Sensor1");
        Reading readingValue1 = new Reading("26", "Celsius");
        Value value1ForSensor1 = factoryInstantTimeValue.createValue(valueID1, sensorID1, readingValue1, instantWithinRange);

        ValueID valueID2 = new ValueID("Value2Sensor1");
        Reading readingValue2 = new Reading("12", "Celsius");
        Value value2ForSensor1 = factoryInstantTimeValue.createValue(valueID2, sensorID1, readingValue2, instantWithinRange);

        //Values for Sensor2
        ValueID valueID3 = new ValueID("Value1Sensor2");
        Reading readingValue3 = new Reading("OFF", "*");
        Value value1ForSensor2 = factoryInstantTimeValue.createValue(valueID3, sensorID2, readingValue3, instantWithinRange);

        //Values for Sensor3
        ValueID valueID4 = new ValueID("Value1Sensor3");
        Reading readingValue4 = new Reading("10;3π/4", "m/s;rad");
        Value value1ForSensor3 = factoryInstantTimeValue.createValue(valueID4, sensorID3, readingValue4, instantWithinRange);

        //Repository
        InstantTimeValueRepository instantTimeValueRepository = new InstantTimeValueRepositoryJPAImp(factoryInstantTimeValue, manager);

        //Create DataModels for Values
        InstantTimeValueDataModel valueDM1 = new InstantTimeValueDataModel(value1ForSensor1);
        when(manager.find(InstantTimeValueDataModel.class, valueID1)).thenReturn(valueDM1);
        InstantTimeValueDataModel valueDM2 = new InstantTimeValueDataModel(value2ForSensor1);
        when(manager.find(InstantTimeValueDataModel.class, valueID2)).thenReturn(valueDM2);
        InstantTimeValueDataModel valueDM3 = new InstantTimeValueDataModel(value1ForSensor2);
        when(manager.find(InstantTimeValueDataModel.class, valueID3)).thenReturn(valueDM3);
        InstantTimeValueDataModel valueDM4 = new InstantTimeValueDataModel(value1ForSensor3);
        when(manager.find(InstantTimeValueDataModel.class, valueID4)).thenReturn(valueDM4);

        //Behaviour for instantTimeValueRepositoryJPA
        Query queryValueDouble = mock(Query.class);
        when(manager.createQuery("SELECT e FROM InstantTimeValueDataModel e WHERE e.sensorID = :sensorID AND e.instantTime BETWEEN :start AND :end")).thenReturn(queryValueDouble);
        when(queryValueDouble.getResultList()).thenReturn(List.of(valueDM1, valueDM2, valueDM3, valueDM4));

        //PeriodValueRepo
        ImpFactoryPeriodTimeValue factoryPeriodTimeValue = new ImpFactoryPeriodTimeValue();
        PeriodTimeValueRepository periodTimeValueRepository = new PeriodTimeValueRepositoryJPAImp(factoryPeriodTimeValue, manager);

        //InstantTimeLocationRepo
        ImpFactoryInstantTimeLocationValue factoryInstantTimeLocationValue = new ImpFactoryInstantTimeLocationValue();
        InstantTimeLocationValueRepository instantTimeLocationValueRepository = new InstantTimeLocationValueJPAImp(factoryInstantTimeLocationValue, manager);

        //Create Service
        ValueService service = new ValueService(sensorRepositoryJPA, sensorFunctionalityRepoMem, instantTimeValueRepository, periodTimeValueRepository, instantTimeLocationValueRepository);

        //Create controller ------------------------------------------------------------------------------------------
        ListAllMeasurmentesOfDeviceInPeriodController controller = new ListAllMeasurmentesOfDeviceInPeriodController(service);

        //Start Timestamp
        Timestamp start = new Timestamp(System.currentTimeMillis() - 200000);
        //End Timestamp
        Timestamp end = new Timestamp(System.currentTimeMillis() + 200000);

        //DeviceDTO
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDCreated.toString());

        //Call controller method and get result ------------------------------------------------------------------
        Map<SensorFunctionalityDTO, List<ReadingDTO>> result = controller.listAllMeasurmentesOfDeviceInPeriod(deviceDTO, start, end);

        //Create expected result
        String reading1AsString = readingValue1.getAllValuesWithUnits();
        String reading2AsString = readingValue2.getAllValuesWithUnits();
        String reading3AsString = readingValue3.getAllValuesWithUnits();
        String reading4AsString = readingValue4.getAllValuesWithUnits();

        //Extract necessary comparison from List
        List<String> resultsAsList = new ArrayList<>();

        for (Map.Entry<SensorFunctionalityDTO, List<ReadingDTO>> entry : result.entrySet()) {
            for (ReadingDTO readingDTO : entry.getValue()) {
                resultsAsList.add(readingDTO.valueWithUnit);
            }
        }

        //Values that exist associated with the sensor and are within given period
        assertTrue(resultsAsList.contains(reading1AsString));
        assertTrue(resultsAsList.contains(reading2AsString));
        assertTrue(resultsAsList.contains(reading3AsString));
        assertTrue(resultsAsList.contains(reading4AsString));
    }

    /**
     * Test validates the successful retrieval of all values associated with the sensors present
     * in a given Device within a given period. Test is done using a Spring Repositories where available.
     * <p>
     * The Device used in the test contains 4 sensors, each with a different SensorFunctionality:
     * - Sensor1 -> TemperatureCelsius;
     * - Sensor2 -> BinaryStatus;
     * - Sensor3 -> WindSpeedAndDirection;
     * - Sensor4 -> DewPointCelsius.
     * <p>
     * The test creates and adds to Value persistence:
     * - 3 Values for Sensor1:
     * - 2 Values within the given period (1 value with 1 unit) [26 Celsius] and [12 Celsius]
     * - 1 Value for Sensor2:
     * - 1 Value within the given period (1 value with no(*) unit) [OFF]
     * - 1 Value for Sensor3:
     * - 1 Value within the given period (2 values with 2 units) [10 m/s and 3π/4 rad]
     * - 0 Values for Sensor4.
     * NOTE: No values outside the given period are returned from persistence
     * since database query would be made in such a way where this would not happen.
     * <p>
     * To assert that all measurements are present, List of readings is extracted from the return map and
     * Strings are compared to the expected values.
     */
    @Test
    void successfullyListAllValuesForDeviceSpringDataRepository() {

        //Start Timestamp
        Timestamp start = new Timestamp(System.currentTimeMillis() - 200000);
        //End Timestamp
        Timestamp end = new Timestamp(System.currentTimeMillis() + 200000);

        //Device ------------------------------------------------------------------------------------------------
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        RoomID roomID = new RoomID("Living Room");
        DeviceID deviceID = new DeviceID("DeviceForTest");
        DeviceModel deviceModel = new DeviceModel("TestModel");

        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);

        //MapperDeviceDataDoubleDouble
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();

        //Repository
        DeviceRepositorySpringData deviceSpringDataDouble = mock(DeviceRepositorySpringData.class);
        DeviceRepository deviceRepository = new DeviceRepositorySpringDataImp(factoryDevice, deviceSpringDataDouble, mapperDeviceDataModel);

        //STUB TO RETURN DEVICES WHEN REPO IS CALLED -------------------------------------------------------------------
        DeviceDataModel deviceDM = new DeviceDataModel(device);
        when(deviceSpringDataDouble.findById(deviceID.toString())).thenReturn(Optional.of(deviceDM));


        //Sensor ------------------------------------------------------------------------------------------------
        //Sensor Functionality
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();
        SensorFunctionalityRepositoryMem sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Sensor
        FactorySensor factorySensor = new FactorySensor();

        //Repository
        SensorRepositorySpringData sensorSpringDataDouble = mock(SensorRepositorySpringData.class);
        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();
        SensorRepository sensorRepo = new SensorRepositorySpringDataImp(sensorSpringDataDouble, factorySensor, mapperSensorDataModel);

        //Setup SensorService
        SensorService sensorService = new SensorService(factorySensor, sensorRepo, deviceRepository, sensorFunctionalityRepositoryMem);


        //Create 4 Sensors using the service to assure they are saved in SensorRepo
        //Create Sensor1
        String sensorID1AsString = "Sensor1";
        SensorID sensorID1ForSensor1 = new SensorID(sensorID1AsString);
        SensorFunctionalityID sensorFunctionalityID1 = new SensorFunctionalityID("TemperatureCelsius");
        SensorID sensorID1 = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID1, sensorID1ForSensor1);
        when(sensorID1.toString()).thenReturn(sensorID1AsString);
        String sensorClassForSensor1 = sensorFunctionalityRepositoryMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID1);
        Sensor sensor1 = factorySensor.createSensor(sensorID1, deviceID, sensorFunctionalityID1, sensorClassForSensor1);

        //Create Sensor2
        String sensorID2AsString = "Sensor2";
        SensorID sensorID2ForSensor2 = new SensorID(sensorID2AsString);
        SensorFunctionalityID sensorFunctionalityID2 = new SensorFunctionalityID("BinaryStatus");
        SensorID sensorID2 = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID2, sensorID2ForSensor2);
        when(sensorID2.toString()).thenReturn(sensorID2AsString);
        String sensorClassForSensor2 = sensorFunctionalityRepositoryMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID2);
        Sensor sensor2 = factorySensor.createSensor(sensorID2, deviceID, sensorFunctionalityID2, sensorClassForSensor2);

        //Create Sensor3
        String sensorID3AsString = "Sensor3";
        SensorID sensorID3ForSensor3 = new SensorID(sensorID3AsString);
        SensorFunctionalityID sensorFunctionalityID3 = new SensorFunctionalityID("WindSpeedAndDirection");
        SensorID sensorID3 = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID3, sensorID3ForSensor3);
        when(sensorID3.toString()).thenReturn(sensorID3AsString);
        String sensorClassForSensor3 = sensorFunctionalityRepositoryMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID3);
        Sensor sensor3 = factorySensor.createSensor(sensorID3, deviceID, sensorFunctionalityID3, sensorClassForSensor3);

        //Create Sensor4
        String sensorID4AsString = "Sensor4";
        SensorID sensorID4ForSensor4 = new SensorID(sensorID4AsString);
        SensorFunctionalityID sensorFunctionalityID4 = new SensorFunctionalityID("DewPointCelsius");
        SensorID sensorID4 = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID4, sensorID4ForSensor4);
        when(sensorID4.toString()).thenReturn(sensorID4AsString);
        String sensorClassForSensor4 = sensorFunctionalityRepositoryMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID4);
        Sensor sensor4 = factorySensor.createSensor(sensorID4, deviceID, sensorFunctionalityID4, sensorClassForSensor4);

        //STUB TO RETURN SENSORS WHEN REPO IS CALLED -------------------------------------------------------------------
        //Sensor Data Models
        SensorDataModel sensorDM1 = new SensorDataModel(sensor1);
        SensorDataModel sensorDM2 = new SensorDataModel(sensor2);
        SensorDataModel sensorDM3 = new SensorDataModel(sensor3);
        SensorDataModel sensorDM4 = new SensorDataModel(sensor4);
        when(sensorSpringDataDouble.findByDeviceID(deviceID.toString())).thenReturn(List.of(sensorDM1, sensorDM2, sensorDM3, sensorDM4));
        //--------------------------------------------------------------------------------------------------------------

        //Set up Value Repos -------------------------------------------------------------------------------------------

        //Factories Value
        ImpFactoryInstantTimeValue factoryInstantTimeValue = new ImpFactoryInstantTimeValue();

        //Timestamps
        Timestamp instantWithinRange = new Timestamp(System.currentTimeMillis());

        //InstantValueRepo
        //Values for Sensor1
        ValueID valueID1 = new ValueID("Value1Sensor1");
        Reading readingValue1 = new Reading("26", "Celsius");
        Value value1ForSensor1 = factoryInstantTimeValue.createValue(valueID1, sensorID1ForSensor1, readingValue1, instantWithinRange);

        ValueID valueID2 = new ValueID("Value2Sensor1");
        Reading readingValue2 = new Reading("12", "Celsius");
        Value value2ForSensor1 = factoryInstantTimeValue.createValue(valueID2, sensorID1ForSensor1, readingValue2, instantWithinRange);

        //Values for Sensor2
        ValueID valueID3 = new ValueID("Value1Sensor2");
        Reading readingValue3 = new Reading("OFF", "*");
        Value value1ForSensor2 = factoryInstantTimeValue.createValue(valueID3, sensorID2ForSensor2, readingValue3, instantWithinRange);

        //Values for Sensor3
        ValueID valueID4 = new ValueID("Value1Sensor3");
        Reading readingValue4 = new Reading("10;3π/4", "m/s;rad");
        Value value1ForSensor3 = factoryInstantTimeValue.createValue(valueID4, sensorID3ForSensor3, readingValue4, instantWithinRange);

        //Repository
        InstantTimeValueRepositorySpringData instantTimeValueRepositorySpringDataDouble = mock(InstantTimeValueRepositorySpringData.class);
        MapperInstantTimeValueDataModel mapperInstantTimeValueDataModelDouble = new MapperInstantTimeValueDataModel();
        InstantTimeValueRepository instantTimeValueRepository = new InstantTimeValueRepositorySpringDataImp(instantTimeValueRepositorySpringDataDouble, factoryInstantTimeValue, mapperInstantTimeValueDataModelDouble);

        //Create DataModels for Values
        InstantTimeValueDataModel valueDM1 = new InstantTimeValueDataModel(value1ForSensor1);
        InstantTimeValueDataModel valueDM2 = new InstantTimeValueDataModel(value2ForSensor1);
        InstantTimeValueDataModel valueDM3 = new InstantTimeValueDataModel(value1ForSensor2);
        InstantTimeValueDataModel valueDM4 = new InstantTimeValueDataModel(value1ForSensor3);

        //Behaviour for instantTimeValueRepositorySpringDataDouble per sensor
        //Sensor1
        when(instantTimeValueRepositorySpringDataDouble
                .findBySensorIDAndAndInstantTimeBetween(sensorID1.toString(), start, end))
                .thenReturn(List.of(valueDM1, valueDM2));
        //Sensor2
        when(instantTimeValueRepositorySpringDataDouble
                .findBySensorIDAndAndInstantTimeBetween(sensorID2.toString(), start, end))
                .thenReturn(List.of(valueDM3));
        //Sensor3
        when(instantTimeValueRepositorySpringDataDouble
                .findBySensorIDAndAndInstantTimeBetween(sensorID3.toString(), start, end))
                .thenReturn(List.of(valueDM4));
        //Sensor4
        when(instantTimeValueRepositorySpringDataDouble
                .findBySensorIDAndAndInstantTimeBetween(sensorID4.toString(), start, end))
                .thenReturn(List.of());

        //PeriodValueRepo
        ImpFactoryPeriodTimeValue factoryPeriodTimeValue = new ImpFactoryPeriodTimeValue();
        PeriodTimeValueRepositorySpringData periodTimeValueRepositorySpringDataDouble = mock(PeriodTimeValueRepositorySpringData.class);
        MapperPeriodTimeValueDataModel mapperPeriodTimeValueDataModelDouble = new MapperPeriodTimeValueDataModel();
        PeriodTimeValueRepository periodTimeValueRepository = new PeriodTimeValueRepositorySpringDataImp(periodTimeValueRepositorySpringDataDouble, factoryPeriodTimeValue, mapperPeriodTimeValueDataModelDouble);

        //InstantTimeLocationRepo
        ImpFactoryInstantTimeLocationValue factoryInstantTimeLocationValue = new ImpFactoryInstantTimeLocationValue();
        InstantTimeLocationValueRepositorySpringData instantTimeLocationValueRepositorySpringDataDouble = mock(InstantTimeLocationValueRepositorySpringData.class);
        MapperInstantTimeLocationValueDataModel mapperInstantTimeLocationValueDataModelDouble = new MapperInstantTimeLocationValueDataModel();
        InstantTimeLocationValueRepository instantTimeLocationValueRepository = new InstantTimeLocationValueRepositorySpringDataImp(instantTimeLocationValueRepositorySpringDataDouble, factoryInstantTimeLocationValue, mapperInstantTimeLocationValueDataModelDouble);

        //Create Service
        ValueService service = new ValueService(sensorRepo, sensorFunctionalityRepositoryMem, instantTimeValueRepository, periodTimeValueRepository, instantTimeLocationValueRepository);

        //Create controller ------------------------------------------------------------------------------------------
        ListAllMeasurmentesOfDeviceInPeriodController controller = new ListAllMeasurmentesOfDeviceInPeriodController(service);

        //DeviceDTO
        DeviceDTO deviceDTO = new DeviceDTO(deviceID.toString());

        //Call controller method and get result ------------------------------------------------------------------
        Map<SensorFunctionalityDTO, List<ReadingDTO>> result = controller.listAllMeasurmentesOfDeviceInPeriod(deviceDTO, start, end);

        //Create expected result
        String reading1AsString = readingValue1.getAllValuesWithUnits();
        String reading2AsString = readingValue2.getAllValuesWithUnits();
        String reading3AsString = readingValue3.getAllValuesWithUnits();
        String reading4AsString = readingValue4.getAllValuesWithUnits();

        //Extract necessary comparison from List
        List<String> resultsAsList = new ArrayList<>();

        for (Map.Entry<SensorFunctionalityDTO, List<ReadingDTO>> entry : result.entrySet()) {
            for (ReadingDTO readingDTO : entry.getValue()) {
                resultsAsList.add(readingDTO.valueWithUnit);
            }
        }

        //Values that exist associated with the sensor and are within given period
        assertTrue(resultsAsList.contains(reading1AsString));
        assertTrue(resultsAsList.contains(reading2AsString));
        assertTrue(resultsAsList.contains(reading3AsString));
        assertTrue(resultsAsList.contains(reading4AsString));
    }
}