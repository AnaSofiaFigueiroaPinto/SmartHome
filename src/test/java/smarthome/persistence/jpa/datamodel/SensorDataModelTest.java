package smarthome.persistence.jpa.datamodel;

import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorDataModelTest {
    private Sensor sensor;
    private DeviceID usedDeviceID;

    private SensorFunctionalityID usedSensorFunctionalityID;

    /**
     * SetUp Before Each test that instantiates the declared arguments that will be used in tests.
     */
    @BeforeEach
    void setup() {
        usedSensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");
        String validClassName = "smarthome.domain.sensor.TemperatureCelsiusSensor";
        usedDeviceID = new DeviceID("deviceID");
        FactorySensor factorySensor = new FactorySensor();
        String sensorName = "sensorName";
        SensorID sensorID = new SensorID(sensorName);
        sensor = factorySensor.createSensor(sensorID, usedDeviceID, usedSensorFunctionalityID, validClassName);
    }

    /**
     * Test that get the SensorID from Sensor Data Model.
     */
    @Test
    void getSensorID() {
        SensorDataModel sensorDataModel = new SensorDataModel(sensor);
        SensorID sensorID = new SensorID(sensorDataModel.getSensorID());
        assertNotNull(sensorID);
    }

    /**
     * Test that get the DeviceID from Sensor Data Model.
     */
    @Test
    void getDeviceID() {
        SensorDataModel sensorDataModel = new SensorDataModel(sensor);
        DeviceID deviceID = new DeviceID(sensorDataModel.getDeviceID());
        assertEquals(usedDeviceID, deviceID);

    }

    /**
     * Test that get the SensorFunctionalityID from Sensor Data Model.
     */
    @Test
    void getSensorFunctionalityID() {
        SensorDataModel sensorDataModel = new SensorDataModel(sensor);
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorDataModel.getSensorFunctionalityID());
        assertEquals(usedSensorFunctionalityID, sensorFunctionalityID);
    }

    /**
     * Test case for Room Data Model empty constructor.
     */
    @Test
    void testEmptyConstructor() {
        SensorDataModel sensorDataModel = new SensorDataModel();

        assertNotNull(sensorDataModel);
    }

    @Test
    void verifyUpdateFromDomain() {
        Sensor newSensor = sensor;
        SensorDataModel sensorDataModel = new SensorDataModel(sensor);

        boolean updateResult = sensorDataModel.updateFromDomain(newSensor);

        assertTrue(updateResult);
    }

    @Test
    void failUpdateFromDomain() {
        SensorDataModel sensorDataModel = new SensorDataModel(sensor);

        boolean updateResult = sensorDataModel.updateFromDomain(null);

        assertFalse(updateResult);
    }

}