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
import smarthome.domain.value.ImpFactoryInstantTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.SensorDTO;
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

/**
 * Test class for {@link MaxTempDifOutsideInsideController}.
 */
class MaxTempDifOutsideInsideControllerTest {

    /**
     * Test to check if the controller returns successfully the maximum difference temperature when given a RoomDTO.
     */
    @Test
    void successfullyFindMaxTempDifOutsideInside() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepositoryMem = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepositoryMem = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepo = new SensorRepositoryMem(sensorData);

        Map<ValueID, Value> valueData = new HashMap<>();
        InstantTimeValueRepository valueRepositoryMem = new InstantTimeValueRepositoryMem(valueData);

        SensorFunctionalityRepository sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        MaxTempDifOutsideInsideService maxTempDifOutsideInsideService = new MaxTempDifOutsideInsideService(valueRepositoryMem);

        HouseService houseService = new HouseService(houseRepositoryMem, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryMem, roomRepositoryMem, houseRepositoryMem);
        RoomService roomService = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
        SensorService sensorService = new SensorService(factorySensor, sensorRepo, deviceRepositoryMem, sensorFunctionalityRepositoryMem);

        //Instantiate controller
        MaxTempDifOutsideInsideController controller = new MaxTempDifOutsideInsideController(maxTempDifOutsideInsideService);

        //Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        //Create rooms
        RoomID insideRoomID = new RoomID("Living Room");
        RoomFloor insideRoomFloor = new RoomFloor(1);
        RoomDimensions insideRoomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(insideRoomID, insideRoomFloor, insideRoomDimensions, houseID);
        RoomID outsideRoomID = new RoomID("Garden");
        RoomFloor outsideRoomFloor = new RoomFloor(0);
        RoomDimensions outsideRoomDimensions = new RoomDimensions(15, 15, 0);
        roomService.createRoomAndSave(outsideRoomID, outsideRoomFloor, outsideRoomDimensions, houseID);

        //Create devices
        DeviceID insideDeviceID = new DeviceID("RoomThermostat");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(insideDeviceID, deviceModel, insideRoomID);
        DeviceID outsideDeviceID = new DeviceID("GardenThermostat");
        deviceService.createDeviceAndSaveToRepository(outsideDeviceID, deviceModel, insideRoomID);

        //Create sensors
        SensorID insideSensorID = new SensorID("RoomTemperature");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");
        sensorService.createSensorAndSave(insideDeviceID, sensorFunctionalityID, insideSensorID);
        SensorID outsideSensorID = new SensorID("OutsideTemperature");
        sensorService.createSensorAndSave(outsideDeviceID, sensorFunctionalityID, outsideSensorID);

        //Create Inside SensorDTO
        SensorDTO insideSensorDTO = new SensorDTO("RoomTemperature");

        //Create Inside Readings and Values
        Reading insideReading1 = new Reading("20", "Cº");
        Timestamp insideTimestamp1 = Timestamp.valueOf("2024-04-15 08:00:00.0");

        Reading insideReading2 = new Reading("20", "Cº");
        Timestamp insideTimestamp2 = Timestamp.valueOf("2024-04-15 10:00:00.0");

        Reading insideReading3 = new Reading("20", "Cº");
        Timestamp insideTimestamp3 = Timestamp.valueOf("2024-04-15 12:00:00.0");

        Reading insideReading4 = new Reading("20", "ºC");
        Timestamp insideTimestamp4 = Timestamp.valueOf("2024-04-15 14:00:00.0");

        Reading insideReading5 = new Reading("20", "ºC");
        Timestamp insideTimestamp5 = Timestamp.valueOf("2024-04-15 16:00:00.0");

        ImpFactoryInstantTimeValue factoryValueImp = new ImpFactoryInstantTimeValue();
        Value insideValue1 = factoryValueImp.createValue(insideSensorID, insideReading1, insideTimestamp1);
        Value insideValue2 = factoryValueImp.createValue(insideSensorID, insideReading2, insideTimestamp2);
        Value insideValue3 = factoryValueImp.createValue(insideSensorID, insideReading3, insideTimestamp3);
        Value insideValue4 = factoryValueImp.createValue(insideSensorID, insideReading4, insideTimestamp4);
        Value insideValue5 = factoryValueImp.createValue(insideSensorID, insideReading5, insideTimestamp5);

        valueData.put(insideValue1.identity(), insideValue1);
        valueData.put(insideValue2.identity(), insideValue2);
        valueData.put(insideValue3.identity(), insideValue3);
        valueData.put(insideValue4.identity(), insideValue4);
        valueData.put(insideValue5.identity(), insideValue5);

        //Create Inside SensorDTO
        SensorDTO outsideSensorDTO = new SensorDTO("OutsideTemperature");

        //Create Outside Readings and Values
        Reading readingFirst = new Reading("14", "Cº");
        Timestamp timestampFirst = Timestamp.valueOf("2024-04-15 08:00:00.0");

        Reading readingScn = new Reading("17", "Cº");
        Timestamp timestampScn = Timestamp.valueOf("2024-04-15 09:30:00.0");

        Reading readingTrd = new Reading("18", "Cº");
        Timestamp timestampTrd = Timestamp.valueOf("2024-04-15 10:50:00.0");

        Reading readingFor = new Reading("22", "ºC");
        Timestamp timestampFor = Timestamp.valueOf("2024-04-15 12:35:00.0");

        Reading readingFth = new Reading("24", "ºC");
        Timestamp timestampFth = Timestamp.valueOf("2024-04-15 14:05:00.0");

        Reading readingSth = new Reading("24", "ºC");
        Timestamp timestampSth = Timestamp.valueOf("2024-04-15 15:30:00.0");

        Reading readingSeth = new Reading("21", "ºC");
        Timestamp timestampSeth = Timestamp.valueOf("2024-04-15 17:00:00.0");

        Reading readingEth = new Reading("18", "ºC");
        Timestamp timestampEth = Timestamp.valueOf("2024-04-15 18:45:00.0");

        Reading readingNth = new Reading("16", "ºC");
        Timestamp timestampNth = Timestamp.valueOf("2024-04-15 20:00:00.0");

        Value valFirst = factoryValueImp.createValue(outsideSensorID, readingFirst, timestampFirst);
        Value valScn = factoryValueImp.createValue(outsideSensorID, readingScn, timestampScn);
        Value valTrd = factoryValueImp.createValue(outsideSensorID, readingTrd, timestampTrd);
        Value valFor = factoryValueImp.createValue(outsideSensorID, readingFor, timestampFor);
        Value valFth = factoryValueImp.createValue(outsideSensorID, readingFth, timestampFth);
        Value valSth = factoryValueImp.createValue(outsideSensorID, readingSth, timestampSth);
        Value valSeth = factoryValueImp.createValue(outsideSensorID, readingSeth, timestampSeth);
        Value valEht = factoryValueImp.createValue(outsideSensorID, readingEth, timestampEth);
        Value valNth = factoryValueImp.createValue(outsideSensorID, readingNth, timestampNth);

        valueData.put(valFirst.identity(), valFirst);
        valueData.put(valScn.identity(), valScn);
        valueData.put(valTrd.identity(), valTrd);
        valueData.put(valFor.identity(), valFor);
        valueData.put(valFth.identity(), valFth);
        valueData.put(valSth.identity(), valSth);
        valueData.put(valSeth.identity(), valSeth);
        valueData.put(valEht.identity(), valEht);
        valueData.put(valNth.identity(), valNth);

        // Define the start and end time for the test
        Timestamp startTime = Timestamp.valueOf("2024-04-15 07:00:00.0");
        Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

        double result = controller.maxTempDifOutsideInside(insideSensorDTO, outsideSensorDTO, startTime, endTime);

        assertEquals(6, result, 0.01);
    }

    /**
     * Test to check if the controller returns -1 when no readings are found in the tolerance
     */
    @Test
    void failFindMaxTempDifOutsideInsideNoReadingsInTolerance() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepositoryMem = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepositoryMem = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepo = new SensorRepositoryMem(sensorData);

        Map<ValueID, Value> valueData = new HashMap<>();
        InstantTimeValueRepository valueRepositoryMem = new InstantTimeValueRepositoryMem(valueData);

        SensorFunctionalityRepository sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        MaxTempDifOutsideInsideService maxTempDifOutsideInsideService = new MaxTempDifOutsideInsideService(valueRepositoryMem);

        HouseService houseService = new HouseService(houseRepositoryMem, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryMem, roomRepositoryMem, houseRepositoryMem);
        RoomService roomService = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
        SensorService sensorService = new SensorService(factorySensor, sensorRepo, deviceRepositoryMem, sensorFunctionalityRepositoryMem);

        //Instantiate controller
        MaxTempDifOutsideInsideController controller = new MaxTempDifOutsideInsideController(maxTempDifOutsideInsideService);

        //Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        //Create rooms
        RoomID insideRoomID = new RoomID("Living Room");
        RoomFloor insideRoomFloor = new RoomFloor(1);
        RoomDimensions insideRoomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(insideRoomID, insideRoomFloor, insideRoomDimensions, houseID);
        RoomID outsideRoomID = new RoomID("Garden");
        RoomFloor outsideRoomFloor = new RoomFloor(0);
        RoomDimensions outsideRoomDimensions = new RoomDimensions(15, 15, 0);
        roomService.createRoomAndSave(outsideRoomID, outsideRoomFloor, outsideRoomDimensions, houseID);

        //Create devices
        DeviceID insideDeviceID = new DeviceID("RoomThermostat");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(insideDeviceID, deviceModel, insideRoomID);
        DeviceID outsideDeviceID = new DeviceID("GardenThermostat");
        deviceService.createDeviceAndSaveToRepository(outsideDeviceID, deviceModel, insideRoomID);

        //Create sensors
        SensorID insideSensorID = new SensorID("RoomTemperature");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");
        sensorService.createSensorAndSave(insideDeviceID, sensorFunctionalityID, insideSensorID);
        SensorID outsideSensorID = new SensorID("OutsideTemperature");
        sensorService.createSensorAndSave(outsideDeviceID, sensorFunctionalityID, outsideSensorID);

        //Create Inside SensorDTO
        SensorDTO insideSensorDTO = new SensorDTO("RoomTemperature");

        //Create Inside Readings and Values
        Reading insideReading1 = new Reading("20", "Cº");
        Timestamp insideTimestamp1 = Timestamp.valueOf("2024-04-15 08:10:00.0");

        Reading insideReading2 = new Reading("20", "Cº");
        Timestamp insideTimestamp2 = Timestamp.valueOf("2024-04-15 10:00:00.0");

        Reading insideReading3 = new Reading("20", "Cº");
        Timestamp insideTimestamp3 = Timestamp.valueOf("2024-04-15 12:00:00.0");

        Reading insideReading4 = new Reading("20", "ºC");
        Timestamp insideTimestamp4 = Timestamp.valueOf("2024-04-15 14:20:00.0");

        Reading insideReading5 = new Reading("20", "ºC");
        Timestamp insideTimestamp5 = Timestamp.valueOf("2024-04-15 16:00:00.0");

        ImpFactoryInstantTimeValue factoryValueImp = new ImpFactoryInstantTimeValue();
        Value insideValue1 = factoryValueImp.createValue(insideSensorID, insideReading1, insideTimestamp1);
        Value insideValue2 = factoryValueImp.createValue(insideSensorID, insideReading2, insideTimestamp2);
        Value insideValue3 = factoryValueImp.createValue(insideSensorID, insideReading3, insideTimestamp3);
        Value insideValue4 = factoryValueImp.createValue(insideSensorID, insideReading4, insideTimestamp4);
        Value insideValue5 = factoryValueImp.createValue(insideSensorID, insideReading5, insideTimestamp5);

        valueData.put(insideValue1.identity(), insideValue1);
        valueData.put(insideValue2.identity(), insideValue2);
        valueData.put(insideValue3.identity(), insideValue3);
        valueData.put(insideValue4.identity(), insideValue4);
        valueData.put(insideValue5.identity(), insideValue5);

        //Create Inside SensorDTO
        SensorDTO outsideSensorDTO = new SensorDTO("OutsideTemperature");

        //Create Outside Readings and Values
        Reading readingFirst = new Reading("14", "Cº");
        Timestamp timestampFirst = Timestamp.valueOf("2024-04-15 08:00:00.0");

        Reading readingScn = new Reading("17", "Cº");
        Timestamp timestampScn = Timestamp.valueOf("2024-04-15 09:30:00.0");

        Reading readingTrd = new Reading("18", "Cº");
        Timestamp timestampTrd = Timestamp.valueOf("2024-04-15 10:50:00.0");

        Reading readingFor = new Reading("22", "ºC");
        Timestamp timestampFor = Timestamp.valueOf("2024-04-15 12:35:00.0");

        Reading readingFth = new Reading("24", "ºC");
        Timestamp timestampFth = Timestamp.valueOf("2024-04-15 14:05:00.0");

        Reading readingSth = new Reading("24", "ºC");
        Timestamp timestampSth = Timestamp.valueOf("2024-04-15 15:30:00.0");

        Reading readingSeth = new Reading("21", "ºC");
        Timestamp timestampSeth = Timestamp.valueOf("2024-04-15 17:00:00.0");

        Reading readingEth = new Reading("18", "ºC");
        Timestamp timestampEth = Timestamp.valueOf("2024-04-15 18:45:00.0");

        Reading readingNth = new Reading("16", "ºC");
        Timestamp timestampNth = Timestamp.valueOf("2024-04-15 20:00:00.0");

        Value valFirst = factoryValueImp.createValue(outsideSensorID, readingFirst, timestampFirst);
        Value valScn = factoryValueImp.createValue(outsideSensorID, readingScn, timestampScn);
        Value valTrd = factoryValueImp.createValue(outsideSensorID, readingTrd, timestampTrd);
        Value valFor = factoryValueImp.createValue(outsideSensorID, readingFor, timestampFor);
        Value valFth = factoryValueImp.createValue(outsideSensorID, readingFth, timestampFth);
        Value valSth = factoryValueImp.createValue(outsideSensorID, readingSth, timestampSth);
        Value valSeth = factoryValueImp.createValue(outsideSensorID, readingSeth, timestampSeth);
        Value valEht = factoryValueImp.createValue(outsideSensorID, readingEth, timestampEth);
        Value valNth = factoryValueImp.createValue(outsideSensorID, readingNth, timestampNth);

        valueData.put(valFirst.identity(), valFirst);
        valueData.put(valScn.identity(), valScn);
        valueData.put(valTrd.identity(), valTrd);
        valueData.put(valFor.identity(), valFor);
        valueData.put(valFth.identity(), valFth);
        valueData.put(valSth.identity(), valSth);
        valueData.put(valSeth.identity(), valSeth);
        valueData.put(valEht.identity(), valEht);
        valueData.put(valNth.identity(), valNth);

        // Define the start and end time for the test
        Timestamp startTime = Timestamp.valueOf("2024-04-15 07:00:00.0");
        Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

        double result = controller.maxTempDifOutsideInside(insideSensorDTO, outsideSensorDTO, startTime, endTime);

        assertEquals(-1, result, 0.01);
    }

    /**
     * Test to check if the controller returns -1 when no readings are found in interval
     */
    @Test
    void failFindMaxTempDifOutsideInsideNoReadingsInInterval() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepositoryMem = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepositoryMem = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepo = new SensorRepositoryMem(sensorData);

        Map<ValueID, Value> valueData = new HashMap<>();
        InstantTimeValueRepository valueRepositoryMem = new InstantTimeValueRepositoryMem(valueData);

        SensorFunctionalityRepository sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        MaxTempDifOutsideInsideService maxTempDifOutsideInsideService = new MaxTempDifOutsideInsideService(valueRepositoryMem);

        HouseService houseService = new HouseService(houseRepositoryMem, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryMem, roomRepositoryMem, houseRepositoryMem);
        RoomService roomService = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
        SensorService sensorService = new SensorService(factorySensor, sensorRepo, deviceRepositoryMem, sensorFunctionalityRepositoryMem);

        //Instantiate controller
        MaxTempDifOutsideInsideController controller = new MaxTempDifOutsideInsideController(maxTempDifOutsideInsideService);

        //Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        //Create rooms
        RoomID insideRoomID = new RoomID("Living Room");
        RoomFloor insideRoomFloor = new RoomFloor(1);
        RoomDimensions insideRoomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(insideRoomID, insideRoomFloor, insideRoomDimensions, houseID);
        RoomID outsideRoomID = new RoomID("Garden");
        RoomFloor outsideRoomFloor = new RoomFloor(0);
        RoomDimensions outsideRoomDimensions = new RoomDimensions(15, 15, 0);
        roomService.createRoomAndSave(outsideRoomID, outsideRoomFloor, outsideRoomDimensions, houseID);

        //Create devices
        DeviceID insideDeviceID = new DeviceID("RoomThermostat");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(insideDeviceID, deviceModel, insideRoomID);
        DeviceID outsideDeviceID = new DeviceID("GardenThermostat");
        deviceService.createDeviceAndSaveToRepository(outsideDeviceID, deviceModel, insideRoomID);

        //Create sensors
        SensorID insideSensorID = new SensorID("RoomTemperature");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");
        sensorService.createSensorAndSave(insideDeviceID, sensorFunctionalityID, insideSensorID);
        SensorID outsideSensorID = new SensorID("OutsideTemperature");
        sensorService.createSensorAndSave(outsideDeviceID, sensorFunctionalityID, outsideSensorID);

        //Create Inside SensorDTO
        SensorDTO insideSensorDTO = new SensorDTO("RoomTemperature");

        //Create Inside Readings and Values
        Reading insideReading1 = new Reading("20", "Cº");
        Timestamp insideTimestamp1 = Timestamp.valueOf("2024-04-14 08:10:00.0");

        ImpFactoryInstantTimeValue factoryValueImp = new ImpFactoryInstantTimeValue();
        Value insideValue1 = factoryValueImp.createValue(insideSensorID, insideReading1, insideTimestamp1);

        valueData.put(insideValue1.identity(), insideValue1);

        //Create Inside SensorDTO
        SensorDTO outsideSensorDTO = new SensorDTO("OutsideTemperature");

        //Create Outside Readings and Values
        Reading readingFirst = new Reading("14", "Cº");
        Timestamp timestampFirst = Timestamp.valueOf("2024-04-16 08:00:00.0");

        Value valFirst = factoryValueImp.createValue(outsideSensorID, readingFirst, timestampFirst);

        valueData.put(valFirst.identity(), valFirst);

        // Define the start and end time for the test
        Timestamp startTime = Timestamp.valueOf("2024-04-15 07:00:00.0");
        Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

        double result = controller.maxTempDifOutsideInside(insideSensorDTO, outsideSensorDTO, startTime, endTime);

        assertEquals(-1, result, 0.01);
    }

    /**
     * Test to check if the controller returns -1 when given an invalid SensorDTO.
     */
    @Test
    void failToFindMaxTempDifOutsideInsideSensorDTONull() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepository roomRepositoryMem = new RoomRepositoryMem(roomData);

        Map<DeviceID, Device> deviceData = new HashMap<>();
        DeviceRepository deviceRepositoryMem = new DeviceRepositoryMem(deviceData);

        Map<SensorID, Sensor> sensorData = new HashMap<>();
        SensorRepository sensorRepo = new SensorRepositoryMem(sensorData);

        Map<ValueID, Value> valueData = new HashMap<>();
        InstantTimeValueRepository valueRepositoryMem = new InstantTimeValueRepositoryMem(valueData);

        SensorFunctionalityRepository sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        MaxTempDifOutsideInsideService maxTempDifOutsideInsideService = new MaxTempDifOutsideInsideService(valueRepositoryMem);

        HouseService houseService = new HouseService(houseRepositoryMem, factoryHouse);
        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryMem, roomRepositoryMem, houseRepositoryMem);
        RoomService roomService = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
        SensorService sensorService = new SensorService(factorySensor, sensorRepo, deviceRepositoryMem, sensorFunctionalityRepositoryMem);

        //Instantiate controller
        MaxTempDifOutsideInsideController controller = new MaxTempDifOutsideInsideController(maxTempDifOutsideInsideService);

        //Create house
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();

        //Create rooms
        RoomID insideRoomID = new RoomID("Living Room");
        RoomFloor insideRoomFloor = new RoomFloor(1);
        RoomDimensions insideRoomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(insideRoomID, insideRoomFloor, insideRoomDimensions, houseID);
        RoomID outsideRoomID = new RoomID("Garden");
        RoomFloor outsideRoomFloor = new RoomFloor(0);
        RoomDimensions outsideRoomDimensions = new RoomDimensions(15, 15, 0);
        roomService.createRoomAndSave(outsideRoomID, outsideRoomFloor, outsideRoomDimensions, houseID);

        //Create devices
        DeviceID insideDeviceID = new DeviceID("RoomThermostat");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(insideDeviceID, deviceModel, insideRoomID);
        DeviceID outsideDeviceID = new DeviceID("GardenThermostat");
        deviceService.createDeviceAndSaveToRepository(outsideDeviceID, deviceModel, insideRoomID);

        //Create sensors
        SensorID insideSensorID = new SensorID("RoomTemperature");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");
        sensorService.createSensorAndSave(insideDeviceID, sensorFunctionalityID, insideSensorID);
        SensorID outsideSensorID = new SensorID("OutsideTemperature");
        sensorService.createSensorAndSave(outsideDeviceID, sensorFunctionalityID, outsideSensorID);

        // Define the start and end time for the test
        Timestamp startTime = Timestamp.valueOf("2024-04-15 07:00:00.0");
        Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

        //Create Inside SensorDTO
        SensorDTO outsideSensorDTO = new SensorDTO("OutsideTemperature");

        double result = controller.maxTempDifOutsideInside(null, outsideSensorDTO, startTime, endTime);

        assertEquals(-1, result, 0.01);
    }


    /**
     * Test to check if the controller returns successfully the maximum difference temperature when given a RoomDTO using JPA Repositories.
     */
    @Test
    void successfullyFindMaxTempDifOutsideInsideJPARepo() {
        // Create double for EntityManager to substitute the database dependency
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.getTransaction()).thenReturn(transaction);

        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        ImpFactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        //Instantiate repositories JPA
        HouseRepository houseRepositoryJPAImp = new HouseRepositoryJPAImp(factoryHouse, manager);
        RoomRepository roomRepositoryJPAImp = new RoomRepositoryJPAImp(factoryRoom, manager);
        DeviceRepository deviceRepositoryJPAImp = new DeviceRepositoryJPAImp(factoryDevice, manager);
        SensorRepository sensorRepositoryJPAImp = new SensorRepositoryJPAImp(factorySensor, manager);
        InstantTimeValueRepository instantTimeValueRepositoryJPAImp = mock(InstantTimeValueRepositoryJPAImp.class); //////////////////////////////////////////////////////////////////////
        SensorFunctionalityRepository sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        MaxTempDifOutsideInsideService maxTempDifOutsideInsideService = new MaxTempDifOutsideInsideService(instantTimeValueRepositoryJPAImp);

        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryJPAImp, roomRepositoryJPAImp, houseRepositoryJPAImp);
        RoomService roomService = new RoomService(roomRepositoryJPAImp, factoryRoom, houseRepositoryJPAImp);
        SensorService sensorService = new SensorService(factorySensor, sensorRepositoryJPAImp, deviceRepositoryJPAImp, sensorFunctionalityRepositoryMem);
        HouseService houseService = new HouseService(houseRepositoryJPAImp, factoryHouse);

        //Instantiate controller
        MaxTempDifOutsideInsideController controller = new MaxTempDifOutsideInsideController(maxTempDifOutsideInsideService);

        //Instantiation of House object
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));
        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(manager.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        // Set behaviour when checking if house exists - containsEntityByID() in HouseRepositoryJPAImp:
        // use factory to create an object that is equal to the one created by services
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseID, location);
        // create the respective datamodel
        HouseDataModel houseDataModel = new HouseDataModel(house);
        // set the find method to return the data model
        when(manager.find(HouseDataModel.class, houseID.toString())).thenReturn(houseDataModel);

        //Create rooms
        RoomID insideRoomID = new RoomID("Living Room");
        RoomFloor insideRoomFloor = new RoomFloor(1);
        RoomDimensions insideRoomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(insideRoomID, insideRoomFloor, insideRoomDimensions, houseID);
        RoomID outsideRoomID = new RoomID("Garden");
        RoomFloor outsideRoomFloor = new RoomFloor(0);
        RoomDimensions outsideRoomDimensions = new RoomDimensions(15, 15, 0);
        roomService.createRoomAndSave(outsideRoomID, outsideRoomFloor, outsideRoomDimensions, houseID);

        // ROOM - findAllEntities() in RoomRepositoryJPAImp:
        // use factory to create an object that is equal to the one created by services
        Room roomInside = factoryRoom.createRoom(insideRoomID, insideRoomFloor, insideRoomDimensions, houseID);
        Room roomOutside = factoryRoom.createRoom(outsideRoomID, outsideRoomFloor, outsideRoomDimensions, houseID);
        // create a data models to return
        RoomDataModel roomDataModelIn = new RoomDataModel(roomInside);
        RoomDataModel roomDataModelOut = new RoomDataModel(roomOutside);

        // Set behaviour to find room when creating a device - containsEntityByID() in RoomRepositoryJPAImp:
        // set the find method to return the data model
        when(manager.find(RoomDataModel.class, insideRoomID.toString())).thenReturn(roomDataModelIn);
        when(manager.find(RoomDataModel.class, outsideRoomID.toString())).thenReturn(roomDataModelOut);

        //Create devices
        DeviceID insideDeviceID = new DeviceID("RoomThermostat");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(insideDeviceID, deviceModel, insideRoomID);
        Device insideDevice = factoryDevice.createDevice(insideDeviceID, deviceModel, insideRoomID);
        DeviceDataModel insideDeviceDataModel = new DeviceDataModel(insideDevice);
        when(manager.find(DeviceDataModel.class, "RoomThermostat")).thenReturn(insideDeviceDataModel);

        DeviceID outsideDeviceID = new DeviceID("GardenThermostat");
        deviceService.createDeviceAndSaveToRepository(outsideDeviceID, deviceModel, insideRoomID);
        Device outsideDevice = factoryDevice.createDevice(outsideDeviceID,deviceModel, insideRoomID);
        DeviceDataModel outsideDeviceDataModel = new DeviceDataModel(outsideDevice);
        when(manager.find(DeviceDataModel.class, "GardenThermostat")).thenReturn(outsideDeviceDataModel);

        //Create sensors
        SensorID insideSensorID = new SensorID("RoomTemperature");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");
        sensorService.createSensorAndSave(insideDeviceID, sensorFunctionalityID, insideSensorID);
        SensorID outsideSensorID = new SensorID("OutsideTemperature");
        sensorService.createSensorAndSave(outsideDeviceID, sensorFunctionalityID, outsideSensorID);

        //Create Inside SensorDTO
        SensorDTO insideSensorDTO = new SensorDTO("RoomTemperature");

        //Create Inside Readings and Values
        Reading insideReading1 = new Reading("20", "Cº");
        Timestamp insideTimestamp1 = Timestamp.valueOf("2024-04-15 08:00:00.0");

        Reading insideReading2 = new Reading("20", "Cº");
        Timestamp insideTimestamp2 = Timestamp.valueOf("2024-04-15 10:00:00.0");

        Reading insideReading3 = new Reading("20", "Cº");
        Timestamp insideTimestamp3 = Timestamp.valueOf("2024-04-15 12:00:00.0");

        Reading insideReading4 = new Reading("20", "ºC");
        Timestamp insideTimestamp4 = Timestamp.valueOf("2024-04-15 14:00:00.0");

        Reading insideReading5 = new Reading("20", "ºC");
        Timestamp insideTimestamp5 = Timestamp.valueOf("2024-04-15 16:00:00.0");

        ImpFactoryInstantTimeValue factoryValueImp = new ImpFactoryInstantTimeValue();
        Value insideValue1 = factoryValueImp.createValue(insideSensorID, insideReading1, insideTimestamp1);
        Value insideValue2 = factoryValueImp.createValue(insideSensorID, insideReading2, insideTimestamp2);
        Value insideValue3 = factoryValueImp.createValue(insideSensorID, insideReading3, insideTimestamp3);
        Value insideValue4 = factoryValueImp.createValue(insideSensorID, insideReading4, insideTimestamp4);
        Value insideValue5 = factoryValueImp.createValue(insideSensorID, insideReading5, insideTimestamp5);

        List<Value> insideValues = new ArrayList<>();
        insideValues.add(insideValue1);
        insideValues.add(insideValue2);
        insideValues.add(insideValue3);
        insideValues.add(insideValue4);
        insideValues.add(insideValue5);
        when(instantTimeValueRepositoryJPAImp.findBySensorId(insideSensorID)).thenReturn(insideValues);

        //Create Inside SensorDTO
        SensorDTO outsideSensorDTO = new SensorDTO("OutsideTemperature");

        //Create Outside Readings and Values
        Reading readingFirst = new Reading("14", "Cº");
        Timestamp timestampFirst = Timestamp.valueOf("2024-04-15 08:00:00.0");

        Reading readingScn = new Reading("17", "Cº");
        Timestamp timestampScn = Timestamp.valueOf("2024-04-15 09:30:00.0");

        Reading readingTrd = new Reading("18", "Cº");
        Timestamp timestampTrd = Timestamp.valueOf("2024-04-15 10:50:00.0");

        Reading readingFor = new Reading("22", "ºC");
        Timestamp timestampFor = Timestamp.valueOf("2024-04-15 12:35:00.0");

        Reading readingFth = new Reading("24", "ºC");
        Timestamp timestampFth = Timestamp.valueOf("2024-04-15 14:05:00.0");

        Reading readingSth = new Reading("24", "ºC");
        Timestamp timestampSth = Timestamp.valueOf("2024-04-15 15:30:00.0");

        Reading readingSeth = new Reading("21", "ºC");
        Timestamp timestampSeth = Timestamp.valueOf("2024-04-15 17:00:00.0");

        Reading readingEth = new Reading("18", "ºC");
        Timestamp timestampEth = Timestamp.valueOf("2024-04-15 18:45:00.0");

        Reading readingNth = new Reading("16", "ºC");
        Timestamp timestampNth = Timestamp.valueOf("2024-04-15 20:00:00.0");

        Value valFirst = factoryValueImp.createValue(outsideSensorID, readingFirst, timestampFirst);
        Value valScn = factoryValueImp.createValue(outsideSensorID, readingScn, timestampScn);
        Value valTrd = factoryValueImp.createValue(outsideSensorID, readingTrd, timestampTrd);
        Value valFor = factoryValueImp.createValue(outsideSensorID, readingFor, timestampFor);
        Value valFth = factoryValueImp.createValue(outsideSensorID, readingFth, timestampFth);
        Value valSth = factoryValueImp.createValue(outsideSensorID, readingSth, timestampSth);
        Value valSeth = factoryValueImp.createValue(outsideSensorID, readingSeth, timestampSeth);
        Value valEht = factoryValueImp.createValue(outsideSensorID, readingEth, timestampEth);
        Value valNth = factoryValueImp.createValue(outsideSensorID, readingNth, timestampNth);

        List<Value> outsideValue = new ArrayList<>();
        outsideValue.add(valFirst);
        outsideValue.add(valScn);
        outsideValue.add(valTrd);
        outsideValue.add(valFor);
        outsideValue.add(valFth);
        outsideValue.add(valSth);
        outsideValue.add(valSeth);
        outsideValue.add(valEht);
        outsideValue.add(valNth);
        when(instantTimeValueRepositoryJPAImp.findBySensorId(outsideSensorID)).thenReturn(outsideValue);

        // Define the start and end time for the test
        Timestamp startTime = Timestamp.valueOf("2024-04-15 07:00:00.0");
        Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

        double result = controller.maxTempDifOutsideInside(insideSensorDTO, outsideSensorDTO, startTime, endTime);

        assertEquals(6, result, 0.01);
    }

    /**
     * Test to check if the controller returns successfully the maximum difference temperature when given a RoomDTO using Spring Repositories.
     */
    @Test
    void successfullyFindMaxTempDifOutsideInsideSpringRepo() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        ImpFactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactorySensor factorySensor = new FactorySensor();
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();

        //Instantiation of Mappers
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        MapperSensorDataModel mapperSensorDataModel = new MapperSensorDataModel();

        //Instantiation of Repositories
        HouseRepositorySpringData houseRepositorySpringData = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepositorySpringDataImp = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringData, mapperHouseDataModel);

        RoomRepositorySpringData roomRepositorySpringData = mock(RoomRepositorySpringData.class);
        RoomRepository roomRepositorySpringDataImp = new RoomRepositorySpringDataImp(roomRepositorySpringData, factoryRoom, mapperRoomDataModel);

        DeviceRepositorySpringData deviceRepositorySpringData = mock(DeviceRepositorySpringData.class);
        DeviceRepository deviceRepositorySpringDataImp = new DeviceRepositorySpringDataImp(factoryDevice, deviceRepositorySpringData, mapperDeviceDataModel);

        SensorRepositorySpringData sensorRepositorySpringData = mock(SensorRepositorySpringData.class);
        SensorRepository sensorRepositorySpringDataImp = new SensorRepositorySpringDataImp(sensorRepositorySpringData, factorySensor, mapperSensorDataModel);

        InstantTimeValueRepository instantTimeLocationValueRepositorySpringDataImp = mock(InstantTimeValueRepository.class);

        SensorFunctionalityRepository sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

        //Instantiation of needed services
        MaxTempDifOutsideInsideService maxTempDifOutsideInsideService = new MaxTempDifOutsideInsideService(instantTimeLocationValueRepositorySpringDataImp);

        DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositorySpringDataImp, roomRepositorySpringDataImp, houseRepositorySpringDataImp);
        RoomService roomService = new RoomService(roomRepositorySpringDataImp, factoryRoom, houseRepositorySpringDataImp);
        SensorService sensorService = new SensorService(factorySensor, sensorRepositorySpringDataImp, deviceRepositorySpringDataImp, sensorFunctionalityRepositoryMem);
        HouseService houseService = new HouseService(houseRepositorySpringDataImp, factoryHouse);

        //Instantiate controller
        MaxTempDifOutsideInsideController controller = new MaxTempDifOutsideInsideController(maxTempDifOutsideInsideService);

        //Instantiation of House object
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);
        when(houseRepositorySpringData.existsById(houseID.toString())).thenReturn(true);

        //Create rooms
        RoomID insideRoomID = new RoomID("Living Room");
        RoomFloor insideRoomFloor = new RoomFloor(1);
        RoomDimensions insideRoomDimensions = new RoomDimensions(2, 2, 2);
        roomService.createRoomAndSave(insideRoomID, insideRoomFloor, insideRoomDimensions, houseID);
        RoomID outsideRoomID = new RoomID("Garden");
        RoomFloor outsideRoomFloor = new RoomFloor(0);
        RoomDimensions outsideRoomDimensions = new RoomDimensions(15, 15, 0);
        roomService.createRoomAndSave(outsideRoomID, outsideRoomFloor, outsideRoomDimensions, houseID);

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepository:
        // use factory to create an object that is equal to the one created by services
        Room insideRoom = factoryRoom.createRoom(insideRoomID, insideRoomFloor, insideRoomDimensions, houseID);
        Room outsideRoom = factoryRoom.createRoom(outsideRoomID, outsideRoomFloor, outsideRoomDimensions, houseID);
        // create a list of data models to return
        RoomDataModel roomDataModel1 = new RoomDataModel(insideRoom);
        RoomDataModel roomDataModel2 = new RoomDataModel(outsideRoom);
        List<RoomDataModel> listOfRoomDataModelsInRepo = List.of(roomDataModel1, roomDataModel2);
        when(roomRepositorySpringData.findAllByHouseID(houseID.toString())).thenReturn(listOfRoomDataModelsInRepo);

        // Set behaviour to find rooms when creating devices - containsEntityByID() in RoomRepository:
        when(roomRepositorySpringData.existsById(insideRoomID.toString())).thenReturn(true);
        when(roomRepositorySpringData.existsById(outsideRoomID.toString())).thenReturn(true);

        //Create devices
        DeviceID insideDeviceID = new DeviceID("RoomThermostat");
        DeviceModel deviceModel = new DeviceModel("H8115");
        deviceService.createDeviceAndSaveToRepository(insideDeviceID, deviceModel, insideRoomID);
        // use factory to create an object that is equal to the one created by services
        Device insideDevice = factoryDevice.createDevice(insideDeviceID,deviceModel, insideRoomID);
        DeviceDataModel insideDeviceDataModel = new DeviceDataModel(insideDevice);
        // Set behaviour to list Devices in inside Room - findByRoomID() in DeviceRepository:
        List<DeviceDataModel> listOfDeviceDataModelsInside = List.of(insideDeviceDataModel);
        when(deviceRepositorySpringData.findAllByRoomID(insideRoomID.toString())).thenReturn(listOfDeviceDataModelsInside);
        // Set behaviour to find device when creating a sensor - findEntityByID() in DeviceRepository:
        when(deviceRepositorySpringData.findById("RoomThermostat")).thenReturn(Optional.of(insideDeviceDataModel));

        DeviceID outsideDeviceID = new DeviceID("GardenThermostat");
        deviceService.createDeviceAndSaveToRepository(outsideDeviceID, deviceModel, outsideRoomID);
        // use factory to create an object that is equal to the one created by services
        Device outsideDevice = factoryDevice.createDevice(outsideDeviceID,deviceModel, outsideRoomID);
        DeviceDataModel outsideDeviceDataModel = new DeviceDataModel(outsideDevice);
        // Set behaviour to list Devices in outside Room - findByRoomID() in DeviceRepository:
        List<DeviceDataModel> listOfDeviceDataModelsOutside = List.of(outsideDeviceDataModel);
        when(deviceRepositorySpringData.findAllByRoomID(outsideRoomID.toString())).thenReturn(listOfDeviceDataModelsOutside);
        // Set behaviour to find device when creating a sensor - findEntityByID() in DeviceRepository:
        when(deviceRepositorySpringData.findById("GardenThermostat")).thenReturn(Optional.of(outsideDeviceDataModel));

        //Create sensors
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");

        SensorID insideSensorID = new SensorID("RoomTemperature");
        sensorService.createSensorAndSave(insideDeviceID, sensorFunctionalityID, insideSensorID);
        // Set  behaviour to list Sensors of Device inside - findByDeviceIDAndSensorFunctionality() in SensorRepository:
        // use factory to create an object that is equal to the one created by services
        String sensorClass = sensorFunctionalityRepositoryMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID);
        Sensor sensorInside = factorySensor.createSensor(insideSensorID, insideDeviceID, sensorFunctionalityID, sensorClass);
        // create a list of data models to return
        SensorDataModel sensorDataModelInside = new SensorDataModel(sensorInside);
        List<SensorDataModel> listOfSensorDataModelsInside = List.of(sensorDataModelInside);
        when(sensorRepositorySpringData.findByDeviceIDAndSensorFunctionalityID(insideDeviceID.toString(), sensorFunctionalityID.toString())).thenReturn(listOfSensorDataModelsInside);

        SensorID outsideSensorID = new SensorID("OutsideTemperature");
        sensorService.createSensorAndSave(outsideDeviceID, sensorFunctionalityID, outsideSensorID);
        // Set  behaviour to list Sensors of Device outside - findByDeviceIDAndSensorFunctionality() in SensorRepository:
        // use factory to create an object that is equal to the one created by services
        Sensor sensorOutside = factorySensor.createSensor(outsideSensorID, outsideDeviceID, sensorFunctionalityID, sensorClass);
        // create a list of data models to return
        SensorDataModel sensorDataModelOutside = new SensorDataModel(sensorOutside);
        List<SensorDataModel> listOfSensorDataModelsOutside = List.of(sensorDataModelOutside);
        when(sensorRepositorySpringData.findByDeviceIDAndSensorFunctionalityID(outsideDeviceID.toString(), sensorFunctionalityID.toString())).thenReturn(listOfSensorDataModelsOutside);

        //Create Inside Readings and Values
        Reading insideReading1 = new Reading("20", "Cº");
        Timestamp insideTimestamp1 = Timestamp.valueOf("2024-04-15 08:00:00.0");

        Reading insideReading2 = new Reading("20", "Cº");
        Timestamp insideTimestamp2 = Timestamp.valueOf("2024-04-15 10:00:00.0");

        Reading insideReading3 = new Reading("20", "Cº");
        Timestamp insideTimestamp3 = Timestamp.valueOf("2024-04-15 12:00:00.0");

        Reading insideReading4 = new Reading("20", "ºC");
        Timestamp insideTimestamp4 = Timestamp.valueOf("2024-04-15 14:00:00.0");

        Reading insideReading5 = new Reading("20", "ºC");
        Timestamp insideTimestamp5 = Timestamp.valueOf("2024-04-15 16:00:00.0");

        ImpFactoryInstantTimeValue factoryValueImp = new ImpFactoryInstantTimeValue();
        Value insideValue1 = factoryValueImp.createValue(insideSensorID, insideReading1, insideTimestamp1);
        Value insideValue2 = factoryValueImp.createValue(insideSensorID, insideReading2, insideTimestamp2);
        Value insideValue3 = factoryValueImp.createValue(insideSensorID, insideReading3, insideTimestamp3);
        Value insideValue4 = factoryValueImp.createValue(insideSensorID, insideReading4, insideTimestamp4);
        Value insideValue5 = factoryValueImp.createValue(insideSensorID, insideReading5, insideTimestamp5);

        List<Value> insideValues = new ArrayList<>();
        insideValues.add(insideValue1);
        insideValues.add(insideValue2);
        insideValues.add(insideValue3);
        insideValues.add(insideValue4);
        insideValues.add(insideValue5);
        when(instantTimeLocationValueRepositorySpringDataImp.findBySensorId(insideSensorID)).thenReturn(insideValues);

        //Create Outside Readings and Values
        Reading readingFirst = new Reading("14", "Cº");
        Timestamp timestampFirst = Timestamp.valueOf("2024-04-15 08:00:00.0");

        Reading readingScn = new Reading("17", "Cº");
        Timestamp timestampScn = Timestamp.valueOf("2024-04-15 09:30:00.0");

        Reading readingTrd = new Reading("18", "Cº");
        Timestamp timestampTrd = Timestamp.valueOf("2024-04-15 10:50:00.0");

        Reading readingFor = new Reading("22", "ºC");
        Timestamp timestampFor = Timestamp.valueOf("2024-04-15 12:35:00.0");

        Reading readingFth = new Reading("24", "ºC");
        Timestamp timestampFth = Timestamp.valueOf("2024-04-15 14:05:00.0");

        Reading readingSth = new Reading("24", "ºC");
        Timestamp timestampSth = Timestamp.valueOf("2024-04-15 15:30:00.0");

        Reading readingSeth = new Reading("21", "ºC");
        Timestamp timestampSeth = Timestamp.valueOf("2024-04-15 17:00:00.0");

        Reading readingEth = new Reading("18", "ºC");
        Timestamp timestampEth = Timestamp.valueOf("2024-04-15 18:45:00.0");

        Reading readingNth = new Reading("16", "ºC");
        Timestamp timestampNth = Timestamp.valueOf("2024-04-15 20:00:00.0");

        Value valFirst = factoryValueImp.createValue(outsideSensorID, readingFirst, timestampFirst);
        Value valScn = factoryValueImp.createValue(outsideSensorID, readingScn, timestampScn);
        Value valTrd = factoryValueImp.createValue(outsideSensorID, readingTrd, timestampTrd);
        Value valFor = factoryValueImp.createValue(outsideSensorID, readingFor, timestampFor);
        Value valFth = factoryValueImp.createValue(outsideSensorID, readingFth, timestampFth);
        Value valSth = factoryValueImp.createValue(outsideSensorID, readingSth, timestampSth);
        Value valSeth = factoryValueImp.createValue(outsideSensorID, readingSeth, timestampSeth);
        Value valEht = factoryValueImp.createValue(outsideSensorID, readingEth, timestampEth);
        Value valNth = factoryValueImp.createValue(outsideSensorID, readingNth, timestampNth);

        List<Value> outsideValue = new ArrayList<>();
        outsideValue.add(valFirst);
        outsideValue.add(valScn);
        outsideValue.add(valTrd);
        outsideValue.add(valFor);
        outsideValue.add(valFth);
        outsideValue.add(valSth);
        outsideValue.add(valSeth);
        outsideValue.add(valEht);
        outsideValue.add(valNth);
        when(instantTimeLocationValueRepositorySpringDataImp.findBySensorId(outsideSensorID)).thenReturn(outsideValue);

        //////////////////// TEST CASE ////////////////////

        // List rooms inside to select one
        List<RoomID> listOfRoomsInside = roomService.getListOfRoomsInsideOrOutsideHouse(houseID, true);
        RoomID selectedInsideRoomID = listOfRoomsInside.get(0);

        // List rooms outside to select one
        List<RoomID> listOfRoomsOutside = roomService.getListOfRoomsInsideOrOutsideHouse(houseID, false);
        RoomID selectedOutsideRoomID = listOfRoomsOutside.get(0);

        // List devices in the selected inside room
        List<DeviceID> listOfDevicesInRoomInside = deviceService.getListOfDevicesInRoom(selectedInsideRoomID);
        DeviceID selectedInsideDeviceID = listOfDevicesInRoomInside.get(0);

        // List devices in the selected outside room
        List<DeviceID> listOfDevicesInRoomOutside = deviceService.getListOfDevicesInRoom(selectedOutsideRoomID);
        DeviceID selectedOutsideDeviceID = listOfDevicesInRoomOutside.get(0);

        // List temperature sensors in selected inside device
        List<SensorID> listOfInsideSensors = sensorService.findSensorsIDOfADeviceBySensorFunctionality(selectedInsideDeviceID, sensorFunctionalityID);
        SensorID selectedInsideSensorID = listOfInsideSensors.get(0);
        //Corresponding SensorDTO
        SensorDTO selectedInsideSensorDTO = new SensorDTO(selectedInsideSensorID.toString());

        // List temperature sensors in selected inside device
        List<SensorID> listOfOutsideSensors = sensorService.findSensorsIDOfADeviceBySensorFunctionality(selectedOutsideDeviceID, sensorFunctionalityID);
        SensorID selectedOutsideSensorID = listOfOutsideSensors.get(0);
        //Corresponding SensorDTO
        SensorDTO selectedOutsideSensorDTO = new SensorDTO(selectedOutsideSensorID.toString());

        // Define the start and end time for the test
        Timestamp startTime = Timestamp.valueOf("2024-04-15 07:00:00.0");
        Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

        // Act
        double result = controller.maxTempDifOutsideInside(selectedInsideSensorDTO, selectedOutsideSensorDTO, startTime, endTime);

        // Assert
        assertEquals(6, result, 0.01);
    }
}