package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.value.ImpFactoryPeriodTimeValue;
import smarthome.domain.value.PeriodTimeValue;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PeriodTimeValueDataModel.
 */
class PeriodTimeValueDataModelTest {

	/**
	 * The Value object used for testing.
	 */
	private PeriodTimeValue value;

	/**
	 * The factory used for creating the Value object.
	 */
	private ImpFactoryPeriodTimeValue factory;

	/**
	 * The ValueDataModel object used for testing.
	 */
	private PeriodTimeValueDataModel periodTimeValueDataModel;

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

		LocalDateTime localDateTime2 = LocalDateTime.of(2024, 3, 6, 1, 0);
		Timestamp endTime1 = Timestamp.valueOf(localDateTime2);

		factory = new ImpFactoryPeriodTimeValue();
		value = (PeriodTimeValue) factory.createValue(valueID1, sensorID, reading, startTime1, endTime1);
	}

	/**
	 * Test to verify that the PeriodTimeValueDataModel class can be successfully constructed with no parameters.
	 */
	@Test
	void testSuccessToConstructEmptyValueDataModel() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel();
		assertNotNull(periodTimeValueDataModel);
	}

	/**
	 * Test to verify that the PeriodTimeValueDataModel class can be successfully constructed with a Value object.
	 */
	@Test
	void testSuccessToConstructValueDataModel() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel(value);
		assertNotNull(periodTimeValueDataModel);
	}

	/**
	 * Test to verify the successful retrieving of the valueID from the PeriodTimeValueDataModel class.
	 */
	@Test
	void testGetValueID() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel(value);
		assertEquals(value.identity().toString(), periodTimeValueDataModel.getValueID());
	}

	/**
	 * Test to verify the successful retrieving of the reading from the PeriodTimeValueDataModel class.
	 */
	@Test
	void testGetReading() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel(value);
		assertEquals(value.getReading().getMeasurement(), periodTimeValueDataModel.getMeasurement());
	}

	/**
	 * Test to verify the successful retrieving of the Unit from the PeriodTimeValueDataModel class.
	 */
	@Test
	void testGetUnit() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel(value);
		assertEquals(value.getReading().getUnit(), periodTimeValueDataModel.getUnit());
	}

	/**
	 * Test to verify the successful retrieving of the start time of the reading from the PeriodTimeValueDataModel class.
	 */
	@Test
	void testGetStartTime() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel(value);
		assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 3, 6, 0, 0)), periodTimeValueDataModel.getStartTime());
	}

	/**
	 * Test to verify the successful retrieving of the end time of the reading from the PeriodTimeValueDataModel class.
	 */
	@Test
	void testGetEndTime() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel(value);
		assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 3, 6, 1, 0)), periodTimeValueDataModel.getEndTime());
	}

	/**
	 * Test to verify the successful retrieving of the SensorID that produces values from a period of time.
	 */
	@Test
	void testGetSensorID() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel(value);
		assertEquals(value.getSensorID().toString(), periodTimeValueDataModel.getSensorID());
	}

	/**
	 * Test to verify the successful update of the PeriodTimeValueDataModel class with a Value object.
	 */
	@Test
	void testSuccessUpdateFromDomain() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel();
		boolean result = periodTimeValueDataModel.updateFromDomain(value);
		assertTrue(result);
	}

	/**
	 * Test to verify the unsuccessful update of the PeriodTimeValueDataModel class with a Value object.
	 */
	@Test
	void testUnsuccessfulUpdateFromDomain() {
		periodTimeValueDataModel = new PeriodTimeValueDataModel();
		boolean result = periodTimeValueDataModel.updateFromDomain(null);
		assertFalse(result);
	}

}