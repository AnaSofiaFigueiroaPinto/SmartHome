package smarthome.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.device.Device;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.domain.valueobjects.SensorFunctionalityID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code ListAllDevicesInHouseBuFunctionalityService} class.
 */
@SpringBootTest(classes = {ListAllDevicesInHouseByFunctionalityService.class})
class ListAllDevicesInHouseByFunctionalityServiceTest {

    /**
     * Double of DeviceRepository.
     */
    @MockBean
    private DeviceRepository deviceRepoDouble;

    /**
     * Double of SensorRepository.
     */
    @MockBean
    private SensorRepository sensorRepoDouble;

    /**
     * Double of ActuatorRepository.
     */
    @MockBean
    private ActuatorRepository actuatorRepoDouble;

    /**
     * Service object to be tested.
     */
    @Autowired
    private ListAllDevicesInHouseByFunctionalityService service;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Successfully retrieves all devices grouped by Sensor and Actuator functionalities and their location.
     * 2 Sensor and 2 Actuator functionalities will be associated with 3 devices in 2 rooms.
     * Room 1 - Device 1 will contain Functionality 1 and 2 in one Sensor/Actuator each.
     * Room 1 - Device 2 will contain Functionality 1 in one Sensor/Actuator.
     * Room 2 - Device 3 will contain Functionality 1 in one Sensor/Actuator.
     */
    @Test
    void obtainMapFunctionalitiesOfAllDevices() {
        SensorFunctionalityID sensorFunctionalityDouble1 = mock(SensorFunctionalityID.class);
        SensorFunctionalityID sensorFunctionalityDouble2 = mock(SensorFunctionalityID.class);

        ActuatorFunctionalityID actuatorFunctionalityDouble1 = mock(ActuatorFunctionalityID.class);
        ActuatorFunctionalityID actuatorFunctionalityDouble2 = mock(ActuatorFunctionalityID.class);

        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);

        DeviceID deviceIDDouble1 = mock(DeviceID.class);
        Device deviceDouble1 = mock(Device.class);
        DeviceID deviceIDDouble2 = mock(DeviceID.class);
        Device deviceDouble2 = mock(Device.class);
        DeviceID deviceIDDouble3 = mock(DeviceID.class);
        Device deviceDouble3 = mock(Device.class);

        Sensor sensorDouble1 = mock(Sensor.class);
        Sensor sensorDouble2 = mock(Sensor.class);
        Sensor sensorDouble3 = mock(Sensor.class);
        Sensor sensorDouble4 = mock(Sensor.class);

        Actuator actuatorDouble1 = mock(Actuator.class);
        Actuator actuatorDouble2 = mock(Actuator.class);
        Actuator actuatorDouble3 = mock(Actuator.class);
        Actuator actuatorDouble4 = mock(Actuator.class);

        //When calling findEntityByID() with the DeviceID, the respective Device is returned
        when(deviceRepoDouble.findEntityByID(deviceIDDouble1)).thenReturn(Optional.of(deviceDouble1));
        when(deviceRepoDouble.findEntityByID(deviceIDDouble2)).thenReturn(Optional.of(deviceDouble2));
        when(deviceRepoDouble.findEntityByID(deviceIDDouble3)).thenReturn(Optional.of(deviceDouble3));

        //When calling getRoomID() on the Device, the RoomID is returned
        when(deviceDouble1.getRoomID()).thenReturn(roomIDDouble1);
        when(deviceDouble2.getRoomID()).thenReturn(roomIDDouble1);
        when(deviceDouble3.getRoomID()).thenReturn(roomIDDouble2);

        //findAllEntities() returns a list with all sensors
        when(sensorRepoDouble.findAllEntities()).thenReturn(List.of(sensorDouble1, sensorDouble2, sensorDouble3, sensorDouble4));

        //findAllEntities() returns a list with all actuators
        when(actuatorRepoDouble.findAllEntities()).thenReturn(List.of(actuatorDouble1, actuatorDouble2, actuatorDouble3, actuatorDouble4));

        //when calling getSensorFunctionalityID() on the Sensor, the SensorFunctionalityID is returned
        when(sensorDouble1.getSensorFunctionalityID()).thenReturn(sensorFunctionalityDouble1);
        when(sensorDouble2.getSensorFunctionalityID()).thenReturn(sensorFunctionalityDouble2);
        when(sensorDouble3.getSensorFunctionalityID()).thenReturn(sensorFunctionalityDouble1);
        when(sensorDouble4.getSensorFunctionalityID()).thenReturn(sensorFunctionalityDouble1);

        //When calling getDeviceID() on the Sensor, the DeviceID is returned
        when(sensorDouble1.getDeviceID()).thenReturn(deviceIDDouble1);
        when(sensorDouble2.getDeviceID()).thenReturn(deviceIDDouble1);
        when(sensorDouble3.getDeviceID()).thenReturn(deviceIDDouble2);
        when(sensorDouble4.getDeviceID()).thenReturn(deviceIDDouble3);

        //When calling getActuatorFunctionalityID() on the Actuator, the ActuatorFunctionalityID is returned
        when(actuatorDouble1.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityDouble1);
        when(actuatorDouble2.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityDouble2);
        when(actuatorDouble3.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityDouble1);
        when(actuatorDouble4.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityDouble1);

        //When calling getDeviceName() on the Actuator, the DeviceID is returned
        when(actuatorDouble1.getDeviceName()).thenReturn(deviceIDDouble1);
        when(actuatorDouble2.getDeviceName()).thenReturn(deviceIDDouble1);
        when(actuatorDouble3.getDeviceName()).thenReturn(deviceIDDouble2);
        when(actuatorDouble4.getDeviceName()).thenReturn(deviceIDDouble3);

        Map<Object, Map<RoomID, DeviceID>> resultMap = service.devicesGroupedByFunctionalityAndLocation();

        //Verify that the number of keys is the same as the number of functionalities (Sensor and Actuator)
        assertEquals(4, resultMap.size());
    }

    /**
     * Retrieve empty list of devices grouped by Sensor and Actuator functionalities and their location.
     * List is empty due to no Sensors/Actuators being present in repository.
     */
    @Test
    void obtainEmptyMapFunctionalitiesOfAllDevices() {
        //When findAllEntities() is called on Sensor/Actuator Repo return empty list
        when(sensorRepoDouble.findAllEntities()).thenReturn(new ArrayList<>());
        when(actuatorRepoDouble.findAllEntities()).thenReturn(new ArrayList<>());

        Map<Object, Map<RoomID, DeviceID>> resultMap = service.devicesGroupedByFunctionalityAndLocation();

        assertEquals(0, resultMap.size());
    }

}