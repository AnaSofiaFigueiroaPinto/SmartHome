package smarthome.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.*;
import smarthome.service.*;

import java.util.List;

/**
 * Class responsible for initializing the database with initial data on application startup.
 */
@Component
public class DatabaseLoader {

    /**
     * The logger instance for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseLoader.class);

    /**
     * Message logged when an object is created.
     */
    private static final String ROOM_CREATED_MESSAGE = "Room created with ID: {}";
    private static final String DEVICE_CREATED_MESSAGE = "Device created with ID: {}";
    private static final String SENSOR_CREATED_MESSAGE = "Sensor created with ID: {}";
    private static final String ACTUATOR_CREATED_MESSAGE = "Actuator created with ID: {}";

    /**
     * Identifiers for the devices.
     */
    private static final String DEVICE_001 = "Device001";
    private static final String DEVICE_002 = "Device002";
    private static final String DEVICE_003 = "Device003";
    private static final String DEVICE_004 = "Device004";
    private static final String DEVICE_005 = "Device005";
    private static final String DEVICE_006 = "Device006";
    private static final String DEVICE_007 = "Device007";
    private static final String DEVICE_008 = "Device008";
    private static final String DEVICE_009 = "Device009";
    private static final String DEVICE_010 = "Device010";

    /**
     * The functionalities of the actuators
     */
    private static final String DECIMAL_SETTER = "DecimalSetter";
    private static final String SWITCH = "Switch";
    private static final String BLIND_SETTER = "BlindSetter";

    /**
     * Identifiers for the rooms.
     */
    private static final String ROOM_001 = "Room001";
    private static final String ROOM_002 = "Room002";
    private static final String ROOM_003 = "Room003";
    private static final String ROOM_004 = "Room004";
    private static final String ROOM_005 = "Room005";

    /**
     * The service responsible for house-related operations.
     */
    private final HouseService houseService;

    /**
     * The service responsible for room-related operations.
     */
    private final RoomService roomService;

    /**
     * The service responsible for device-related operations.
     */
    private final DeviceService deviceService;

    /**
     * The service responsible for sensor-related operations.
     */
    private final SensorService sensorService;

    /**
     * The service responsible for actuator-related operations.
     */
    private final ActuatorService actuatorService;

    /**
     * The loader for properties files.
     */
    private final PropertyLoader propertyLoader;

    /**
     * Constructs a DatabaseLoader with the required services and PropertyLoader.
     *
     * @param houseService    The service responsible for house-related operations.
     * @param roomService     The service responsible for room-related operations.
     * @param deviceService   The service responsible for device-related operations.
     * @param sensorService   The service responsible for sensor-related operations.
     * @param actuatorService The service responsible for actuator-related operations.
     * @param propertyLoader  The loader for properties files.
     */
    @Autowired
    public DatabaseLoader(HouseService houseService, RoomService roomService, DeviceService deviceService,
                          SensorService sensorService, ActuatorService actuatorService, PropertyLoader propertyLoader) {
        this.houseService = houseService;
        this.roomService = roomService;
        this.deviceService = deviceService;
        this.sensorService = sensorService;
        this.actuatorService = actuatorService;
        this.propertyLoader = propertyLoader;
    }

    /**
     * Initializes the database with initial data on application startup.
     *
     */
    public void loadInitialData() {
        LOGGER.info("Starting data initialization...");

        // Add House
        HouseID houseID = houseService.createAndSaveHouseWithoutLocation();
        LOGGER.info("House created with ID: {}", houseID);

        // Add Rooms
        addRooms(houseID);

        // Add Devices
        addDevices();

        // Add Sensors
        addSensors();

        // Add Actuators
        addActuators();

        // Load configurations
        ConfigScraper configScraper = new ConfigScraper("config/config.properties", propertyLoader);

        // List ActuatorFunctionalities
        List<String> actuatorFunctionalityList = configScraper.loadActuatorFunctionalityList();
        LOGGER.info("Actuator functionalities: {}", actuatorFunctionalityList);

        // List SensorFunctionalities
        List<String> sensorFunctionalityList = configScraper.loadSensorFunctionalityList();
        LOGGER.info("Sensor functionalities: {}", sensorFunctionalityList);

        LOGGER.info("Data initialization completed.");
    }

    /**
     * Adds rooms to the house with the specified house ID.
     * This method creates and saves five rooms with predefined IDs, floors, and dimensions.
     * Each room is associated with the provided house ID.
     *
     * @param houseID the ID of the house to which the rooms will be added
     */
    private void addRooms(HouseID houseID) {
        createAndSaveRoom(ROOM_001, 1, 2.5, 3.0, 4.0, houseID);
        createAndSaveRoom(ROOM_002, 2, 2.7, 3.5, 4.5, houseID);
        createAndSaveRoom(ROOM_003, 0, 2.4, 2.8, 3.8, houseID);
        createAndSaveRoom(ROOM_004, 0, 5.0, 4.0, 0.0, houseID);
        createAndSaveRoom(ROOM_005, -1, 2.2, 3.2, 4.2, houseID);
    }

    /**
     * Adds devices to the system.
     * This method creates and saves multiple devices with predefined IDs, models, and room associations.
     * Each device is associated with a specific room.
     */
    private void addDevices() {
        createAndSaveDevice(DEVICE_001, "T8115", ROOM_001);
        createAndSaveDevice(DEVICE_002, "SL8115", ROOM_001);
        createAndSaveDevice(DEVICE_003, "B8115", ROOM_002);
        createAndSaveDevice(DEVICE_004, "B8115", ROOM_002);
        createAndSaveDevice(DEVICE_005, "A8115", ROOM_003);
        createAndSaveDevice(DEVICE_006, "S8115", ROOM_003);
        createAndSaveDevice(DEVICE_007, "T8115", ROOM_004);
        createAndSaveDevice(DEVICE_008, "SL8115", ROOM_004);
        createAndSaveDevice(DEVICE_009, "S8115", ROOM_005);
        createAndSaveDevice(DEVICE_010, "A8115", ROOM_005);
        //POWER GRID
        createAndSaveDevice("Grid Power Meter", "G8115", ROOM_002);
        //POWER SOURCE
        createAndSaveDevice("Power Source 1", "P8115", ROOM_005);
        createAndSaveDevice("Power Source 2", "P8115", ROOM_004);
    }

    /**
     * Adds sensors to the system.
     * This method creates and saves multiple sensors with predefined IDs, functionalities, and device associations.
     * Each sensor is associated with a specific device.
     */
    private void addSensors() {
        createAndSaveSensor("Sensor001", "TemperatureCelsius", DEVICE_001);
        createAndSaveSensor("Sensor002", "WindSpeedAndDirection", DEVICE_002);
        createAndSaveSensor("Sensor003", "Sunrise", DEVICE_003);
        createAndSaveSensor("Sensor004", "Sunset", DEVICE_004);
        createAndSaveSensor("Sensor005", "ElectricEnergyConsumption", DEVICE_005);
        createAndSaveSensor("Sensor006", "PowerAverage", DEVICE_006);
        createAndSaveSensor("Sensor007", "TemperatureCelsius", DEVICE_007);
        createAndSaveSensor("Sensor008", "BinaryStatus", DEVICE_008);
        createAndSaveSensor("Sensor009", "BinaryStatus", DEVICE_009);
        createAndSaveSensor("Sensor010", "ElectricEnergyConsumption", DEVICE_010);
        createAndSaveSensor("Sensor011", "Scale", DEVICE_009);
        //POWER GRID
        createAndSaveSensor("Sensor012", "PowerAverage", "Grid Power Meter");
        //POWER SOURCE
        createAndSaveSensor("Sensor013", "SpecificTimePowerConsumption", "Power Source 1");
        createAndSaveSensor("Sensor014", "SpecificTimePowerConsumption", "Power Source 2");
    }

    /**
     * Adds actuators to the system.
     * This method creates and saves multiple actuators with predefined IDs, functionalities, and device associations.
     * Each actuator is associated with a specific device.
     */
    private void addActuators() {
        createAndSaveActuator("Actuator001", DECIMAL_SETTER, new ActuatorProperties(30.0, 10.0, 1), DEVICE_001);
        createAndSaveActuator("Actuator002", SWITCH, new ActuatorProperties(), DEVICE_002);
        createAndSaveActuator("Actuator003", BLIND_SETTER, new ActuatorProperties(), DEVICE_003);
        createAndSaveActuator("Actuator004", BLIND_SETTER, new ActuatorProperties(), DEVICE_004);
        createAndSaveActuator("Actuator005", DECIMAL_SETTER, new ActuatorProperties(25.0, 16.0, 1), DEVICE_005);
        createAndSaveActuator("Actuator006", SWITCH, new ActuatorProperties(), DEVICE_006);
        createAndSaveActuator("Actuator007", DECIMAL_SETTER, new ActuatorProperties(30.0, 10.0, 1), DEVICE_007);
        createAndSaveActuator("Actuator008", SWITCH, new ActuatorProperties(), DEVICE_008);
        createAndSaveActuator("Actuator009", BLIND_SETTER, new ActuatorProperties(), DEVICE_009);
        createAndSaveActuator("Actuator010", DECIMAL_SETTER, new ActuatorProperties(25.0, 16.0, 1), DEVICE_010);
    }

    /**
     * Helper method to create and save a room.
     *
     * @param roomIDStr the ID of the room
     * @param roomFloorInt the floor of the room
     * @param length the length dimension of the room
     * @param width the width dimension of the room
     * @param height the height dimension of the room
     * @param houseID the ID of the house to which the room will be added
     */
    private void createAndSaveRoom(String roomIDStr, int roomFloorInt, double length, double width, double height, HouseID houseID) {
        RoomID roomID = new RoomID(roomIDStr);
        RoomFloor roomFloor = new RoomFloor(roomFloorInt);
        RoomDimensions roomDimensions = new RoomDimensions(length, width, height);
        roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);
        LOGGER.info(ROOM_CREATED_MESSAGE, roomID);
    }

    /**
     * Helper method to create and save a device.
     *
     * @param deviceIDStr the ID of the device
     * @param deviceModelStr the model of the device
     * @param roomIDStr the ID of the room the device is associated with
     */
    private void createAndSaveDevice(String deviceIDStr, String deviceModelStr, String roomIDStr) {
        DeviceID deviceID = new DeviceID(deviceIDStr);
        DeviceModel deviceModel = new DeviceModel(deviceModelStr);
        RoomID roomID = new RoomID(roomIDStr);
        deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);
        LOGGER.info(DEVICE_CREATED_MESSAGE, deviceID);
    }

    /**
     * Helper method to create and save a sensor.
     *
     * @param sensorIDStr the ID of the sensor
     * @param sensorFunctionalityIDStr the functionality ID of the sensor
     * @param deviceIDStr the ID of the device the sensor is associated with
     */
    private void createAndSaveSensor(String sensorIDStr, String sensorFunctionalityIDStr, String deviceIDStr) {
        SensorID sensorID = new SensorID(sensorIDStr);
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorFunctionalityIDStr);
        DeviceID deviceID = new DeviceID(deviceIDStr);
        sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, sensorID);
        LOGGER.info(SENSOR_CREATED_MESSAGE, sensorID);
    }

    /**
     * Helper method to create and save an actuator.
     *
     * @param actuatorIDStr the ID of the actuator
     * @param actuatorFunctionalityIDStr the functionality ID of the actuator
     * @param actuatorProperties the properties of the actuator
     * @param deviceIDStr the ID of the device the actuator is associated with
     */
    private void createAndSaveActuator(String actuatorIDStr, String actuatorFunctionalityIDStr, ActuatorProperties actuatorProperties, String deviceIDStr) {
        ActuatorID actuatorID = new ActuatorID(actuatorIDStr);
        ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID(actuatorFunctionalityIDStr);
        DeviceID deviceID = new DeviceID(deviceIDStr);
        actuatorService.createActuatorAndSave(actuatorFunctionalityID, actuatorID, actuatorProperties, deviceID);
        LOGGER.info(ACTUATOR_CREATED_MESSAGE, actuatorID);
    }
}