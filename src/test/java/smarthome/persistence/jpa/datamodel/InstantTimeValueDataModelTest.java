package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.value.ImpFactoryInstantTimeValue;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing the ValueDataModel class.
 */
class InstantTimeValueDataModelTest {

	/**
	 * The Value object used for testing.
	 */
	private InstantTimeValue value;

	/**
	 * The factory used for creating the Value object.
	 */
	private ImpFactoryInstantTimeValue factory;

	/**
	 * The ValueDataModel object used for testing.
	 */
	private InstantTimeValueDataModel instantTimeValueDataModel;

	/**
	 * Set up for each test case.
	 */
	@BeforeEach
	void setUp() {
		String valueID = "valueID";
		String unit = "unit";
		String readingValue = "reading value";

		ValueID valueID1 = new ValueID(valueID);
		SensorID sensorID = new SensorID("sensorID");
		Reading reading = new Reading(readingValue, unit);

		LocalDateTime localDateTime1 = LocalDateTime.of(2024, 3, 6, 0, 0);
		Timestamp startTime1 = Timestamp.valueOf(localDateTime1);

		factory = new ImpFactoryInstantTimeValue();
		value = (InstantTimeValue) factory.createValue(valueID1, sensorID, reading, startTime1);

	}
	/**
	 * Test to verify that the InstantTimeValueDataModel class can be successfully constructed with no parameters.
	 */
	@Test
	void testSuccessToConstructEmptyValueDataModel() {
		instantTimeValueDataModel = new InstantTimeValueDataModel();
		assertNotNull(instantTimeValueDataModel);
	}

	/**
	 * Test to verify that the InstantTimeValueDataModel class can be successfully constructed with a Value object.
	 */
	@Test
	void testSuccessConstructValueDataModel() {
		instantTimeValueDataModel = new InstantTimeValueDataModel(value);
		assertNotNull(instantTimeValueDataModel);
	}

	/**
	 * Test to verify the successful retrieving of the valueID from the InstantTimeValueDataModel class.
	 */
	@Test
	void testSuccessToGetValueID() {
		instantTimeValueDataModel = new InstantTimeValueDataModel(value);
		assertEquals(value.identity().toString(), instantTimeValueDataModel.getValueID());
	}

	/**
	 * Test to verify the successful retrieving of the Reading from the InstantTimeValueDataModel class.
	 */
	@Test
	void testSuccessToGetReading() {
		instantTimeValueDataModel = new InstantTimeValueDataModel(value);
		assertEquals(value.getReading().getMeasurement(), instantTimeValueDataModel.getMeasurement());
	}

	/**
	 * Test to verify the successful retrieving of the Unit of the reading from the InstantTimeValueDataModel class.
	 */
	@Test
	void testSuccessToGetUnit() {
		instantTimeValueDataModel = new InstantTimeValueDataModel(value);
		assertEquals(value.getReading().getUnit(), instantTimeValueDataModel.getUnit());
	}

	/**
	 * Test to verify the successful retrieving of the SensorID that produces instant time values.
	 */

	@Test
	void testSuccessToGetSensorID() {
		instantTimeValueDataModel = new InstantTimeValueDataModel(value);
		assertEquals(value.getSensorID().toString(), instantTimeValueDataModel.getSensorID());
	}

	/**
	 * Test to verify the successful retrieving of the InstantTime of the reading.
	 */
	@Test
	void testSuccessToGetInstantTime() {
		instantTimeValueDataModel = new InstantTimeValueDataModel(value);
		assertEquals(value.getInstantTimeReading(), instantTimeValueDataModel.getInstantTime());
	}

	/**
	 * Test to verify the successful updating of the InstantTimeValueDataModel class from a Value object.
	 */
	@Test
	void testSuccessUpdateFromDomain() {
		instantTimeValueDataModel = new InstantTimeValueDataModel();
		boolean result = instantTimeValueDataModel.updateFromDomain(value);
		assertTrue(result);
	}

	/**
	 * Test to verify the unsuccessful updating of the InstantTimeValueDataModel class from a null Value object.
	 */
	@Test
	void failToUpdateFromDomain() {
		instantTimeValueDataModel = new InstantTimeValueDataModel();
		boolean result = instantTimeValueDataModel.updateFromDomain(null);
		assertFalse(result);
	}
}