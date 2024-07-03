package smarthome.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.*;
import smarthome.service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link DatabaseLoader} class.
 */
class DatabaseLoaderTest {

    /**
     * Service responsible for house-related operations.
     */
    private HouseService houseService;

    /**
     * Service responsible for room-related operations.
     */
    private RoomService roomService;

    /**
     * Service responsible for device-related operations.
     */
    private DeviceService deviceService;

    /**
     * Service responsible for sensor-related operations.
     */
    private SensorService sensorService;

    /**
     * Service responsible for actuator-related operations.
     */
    private ActuatorService actuatorService;

    /**
     * Instance of {@link DatabaseLoader} to be tested.
     */
    private DatabaseLoader databaseLoader;

    /**
     * Loader for properties files.
     */
    private PropertyLoader propertyLoader;

    /**
     * Sets up the test environment before each test method is executed.
     * This method initializes the mock objects and creates an instance of {@link DatabaseLoader}
     * with the mocked services.
     */
    @BeforeEach
    void setUp() {
        houseService = mock(HouseService.class);
        roomService = mock(RoomService.class);
        deviceService = mock(DeviceService.class);
        sensorService = mock(SensorService.class);
        actuatorService = mock(ActuatorService.class);
        propertyLoader = mock(PropertyLoader.class);

        databaseLoader = new DatabaseLoader(houseService, roomService, deviceService, sensorService, actuatorService, propertyLoader);
    }

    /**
     * Unit test for the {@link DatabaseLoader#loadInitialData()} method.
     * This test verifies the behavior of the {@link DatabaseLoader} class by testing its {@code loadInitialData()} method.
     * It mocks the dependencies of {@link DatabaseLoader} and verifies that the method interacts with them as expected.
     * The test covers the initialization of the database with initial data on application startup,
     * including house creation, room creation, device creation, sensor creation, actuator creation, and property loading.
     *
     * @throws IOException if there is an error during property loading (not expected in this test)
     */
    @Test
    void testLoadInitialData() throws IOException {
        // Arrange
        HouseID houseID = new HouseID("House001");
        when(houseService.createAndSaveHouseWithoutLocation()).thenReturn(houseID);

        // Mocking BufferedReader for PropertyLoader
        BufferedReader mockBufferedReader = new BufferedReader(new StringReader(""));
        when(propertyLoader.getBufferedReader("config/config.properties")).thenReturn(mockBufferedReader);

        // Mocking ConfigScraper responses
        ConfigScraper configScraper = spy(new ConfigScraper("config/config.properties", propertyLoader));
        doReturn(Collections.emptyList()).when(configScraper).loadActuatorFunctionalityList();
        doReturn(Collections.emptyList()).when(configScraper).loadSensorFunctionalityList();

        // Act
        databaseLoader.loadInitialData();

        // Assert
        // Verify house creation
        verify(houseService).createAndSaveHouseWithoutLocation();

        // Verify room creation
        verify(roomService, times(1)).createRoomAndSave(eq(new RoomID("Room001")), eq(new RoomFloor(1)), any(RoomDimensions.class), eq(houseID));
        verify(roomService, times(1)).createRoomAndSave(eq(new RoomID("Room002")), eq(new RoomFloor(2)), any(RoomDimensions.class), eq(houseID));
        verify(roomService, times(1)).createRoomAndSave(eq(new RoomID("Room003")), eq(new RoomFloor(0)), any(RoomDimensions.class), eq(houseID));
        verify(roomService, times(1)).createRoomAndSave(eq(new RoomID("Room004")), eq(new RoomFloor(0)), any(RoomDimensions.class), eq(houseID));
        verify(roomService, times(1)).createRoomAndSave(eq(new RoomID("Room005")), eq(new RoomFloor(-1)), any(RoomDimensions.class), eq(houseID));

        // Verify device creation
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device001")), eq(new DeviceModel("T8115")), eq(new RoomID("Room001")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device002")), eq(new DeviceModel("SL8115")), eq(new RoomID("Room001")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device003")), eq(new DeviceModel("B8115")), eq(new RoomID("Room002")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device004")), eq(new DeviceModel("B8115")), eq(new RoomID("Room002")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device005")), eq(new DeviceModel("A8115")), eq(new RoomID("Room003")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device006")), eq(new DeviceModel("S8115")), eq(new RoomID("Room003")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device007")), eq(new DeviceModel("T8115")), eq(new RoomID("Room004")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device008")), eq(new DeviceModel("SL8115")), eq(new RoomID("Room004")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device009")), eq(new DeviceModel("S8115")), eq(new RoomID("Room005")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Device010")), eq(new DeviceModel("A8115")), eq(new RoomID("Room005")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Grid Power Meter")), eq(new DeviceModel("G8115")), eq(new RoomID("Room002")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Power Source 1")), eq(new DeviceModel("P8115")), eq(new RoomID("Room005")));
        verify(deviceService, times(1)).createDeviceAndSaveToRepository(eq(new DeviceID("Power Source 2")), eq(new DeviceModel("P8115")), eq(new RoomID("Room004")));

        // Verify sensor creation
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device001")), eq(new SensorFunctionalityID("TemperatureCelsius")), eq(new SensorID("Sensor001")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device002")), eq(new SensorFunctionalityID("WindSpeedAndDirection")), eq(new SensorID("Sensor002")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device003")), eq(new SensorFunctionalityID("Sunrise")), eq(new SensorID("Sensor003")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device004")), eq(new SensorFunctionalityID("Sunset")), eq(new SensorID("Sensor004")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device005")), eq(new SensorFunctionalityID("ElectricEnergyConsumption")), eq(new SensorID("Sensor005")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device006")), eq(new SensorFunctionalityID("PowerAverage")), eq(new SensorID("Sensor006")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device007")), eq(new SensorFunctionalityID("TemperatureCelsius")), eq(new SensorID("Sensor007")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device008")), eq(new SensorFunctionalityID("BinaryStatus")), eq(new SensorID("Sensor008")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device009")), eq(new SensorFunctionalityID("BinaryStatus")), eq(new SensorID("Sensor009")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device010")), eq(new SensorFunctionalityID("ElectricEnergyConsumption")), eq(new SensorID("Sensor010")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Device009")), eq(new SensorFunctionalityID("Scale")), eq(new SensorID("Sensor011")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Grid Power Meter")), eq(new SensorFunctionalityID("PowerAverage")), eq(new SensorID("Sensor012")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Power Source 1")), eq(new SensorFunctionalityID("SpecificTimePowerConsumption")), eq(new SensorID("Sensor013")));
        verify(sensorService, times(1)).createSensorAndSave(eq(new DeviceID("Power Source 2")), eq(new SensorFunctionalityID("SpecificTimePowerConsumption")), eq(new SensorID("Sensor014")));

        // Verify actuator creation
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("DecimalSetter")), eq(new ActuatorID("Actuator001")), any(ActuatorProperties.class), eq(new DeviceID("Device001")));
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("Switch")), eq(new ActuatorID("Actuator002")), any(ActuatorProperties.class), eq(new DeviceID("Device002")));
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("BlindSetter")), eq(new ActuatorID("Actuator003")), any(ActuatorProperties.class), eq(new DeviceID("Device003")));
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("BlindSetter")), eq(new ActuatorID("Actuator004")), any(ActuatorProperties.class), eq(new DeviceID("Device004")));
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("DecimalSetter")), eq(new ActuatorID("Actuator005")), any(ActuatorProperties.class), eq(new DeviceID("Device005")));
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("Switch")), eq(new ActuatorID("Actuator006")), any(ActuatorProperties.class), eq(new DeviceID("Device006")));
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("DecimalSetter")), eq(new ActuatorID("Actuator007")), any(ActuatorProperties.class), eq(new DeviceID("Device007")));
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("Switch")), eq(new ActuatorID("Actuator008")), any(ActuatorProperties.class), eq(new DeviceID("Device008")));
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("BlindSetter")), eq(new ActuatorID("Actuator009")), any(ActuatorProperties.class), eq(new DeviceID("Device009")));
        verify(actuatorService, times(1)).createActuatorAndSave(eq(new ActuatorFunctionalityID("DecimalSetter")), eq(new ActuatorID("Actuator010")), any(ActuatorProperties.class), eq(new DeviceID("Device010")));
    }
}
