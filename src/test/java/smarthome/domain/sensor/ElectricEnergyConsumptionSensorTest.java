package smarthome.domain.sensor;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ElectricEnergyConsumptionSensorTest {
	private DeviceID deviceNameDouble;
	private SensorFunctionalityID sensorFunctionalityIDDouble;
	private SensorID sensorName;

	/**
	 * Setting up the FactoryValue to use in tests
	 */
	@BeforeEach
	void setUp() {
		deviceNameDouble = mock(DeviceID.class);
		sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
		sensorName = mock(SensorID.class);
	}


	/**
	 * Test to create Electric Energy Consumption sensor object with valid parameters.
	 * Verifies that no exception is thrown.
	 */
	@Test
	void validElectricEnergyConsumptionSensorArguments() {
		assertDoesNotThrow(() -> new ElectricEnergyConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble));
	}

	/**
	 * Test to create Electric Energy Consumption sensor object with null sensorName
	 * Verifies that an exception is thrown.
	 */
	@Test
	void invalidElectricEnergyConsumptionSensorNullName() {
		assertThrows(IllegalArgumentException.class, () -> new ElectricEnergyConsumptionSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
	}


	/**
	 * Test to create Electric Energy Consumption sensor object with null deviceID.
	 * Verifies that an exception is thrown.
	 */
	@Test
	void invalidElectricEnergyConsumptionSensorNulldeviceID() {
		assertThrows(IllegalArgumentException.class, () -> new ElectricEnergyConsumptionSensor(sensorName, null, sensorFunctionalityIDDouble));
	}


	/**
	 * Test to create Electric Energy Consumption sensor object with null functionality.
	 * Verifies that an exception is thrown.
	 */

	@Test
	void invalidElectricEnergyConsumptionSensorNullfunctionality() {
		assertThrows(IllegalArgumentException.class, () -> new ElectricEnergyConsumptionSensor(sensorName,  deviceNameDouble, null));
	}

	/**
	 * Test case that verifies if the method returns the name.
	 */
	@Test
	void getSensorName() {
		when(sensorName.toString()).thenReturn("ElectricEnergy");
		ElectricEnergyConsumptionSensor sensor = new ElectricEnergyConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
		assertEquals("ElectricEnergy", sensor.identity().toString());
	}

	/**
	 * Test case that verifies if the method returns the functionality.
	 */
	@Test
	void getSensorFunctionality() {
		ElectricEnergyConsumptionSensor sensor = new ElectricEnergyConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
		assertEquals(sensorFunctionalityIDDouble, sensor.getSensorFunctionalityID());
	}

	/**
	 * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
	 */
	@Test
	void getIdentityOfSensor () {
		ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
		assertEquals(sensorName, electricEnergyConsumptionSensor.identity());
	}

	/**
	 * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
	 */
	@Test
	void checkIfSensorIsSameAs () {
		ElectricEnergyConsumptionSensor sensor1 = new ElectricEnergyConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
		ElectricEnergyConsumptionSensor sensor2 = new ElectricEnergyConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
		assertTrue(sensor1.isSameAs(sensor2));
	}

	/**
	 * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
	 */
	@Test
	void failCheckIfSensorIsSameAs () {
		ElectricEnergyConsumptionSensor sensor = new ElectricEnergyConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
		Object object = new Object();
		assertFalse(sensor.isSameAs(object));
	}

	/**
	 * Successfully returns DeviceID of the sensor
	 */
	@Test
	void getDeviceID () {
		ElectricEnergyConsumptionSensor sensor = new ElectricEnergyConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
		DeviceID deviceID = sensor.getDeviceID();
		assertEquals(deviceNameDouble, deviceID);
	}
}