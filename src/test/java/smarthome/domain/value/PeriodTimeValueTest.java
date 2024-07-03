package smarthome.domain.value;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the PeriodTimeValue class.
 */
class PeriodTimeValueTest {

    /**
     * The PeriodTimeValue object.
     */
    private PeriodTimeValue periodTimeValue;

    /**
     * The ValueID mock.
     */
    private ValueID valueIDDouble;

    /**
     * The Reading mock.
     */
    private Reading readingDouble;

    /**
     * The SensorID mock.
     */
    private SensorID sensorIDDouble;

    /**
     * The timestamp when the period started mock.
     */
    private Timestamp startTimeReadingDouble;

    /**
     * The timestamp when the period ended mock.
     */
    private Timestamp endTimeReadingDouble;

    /**
     * Set up mock objects before each test.
     */
    @BeforeEach
    void setUp() {
        valueIDDouble = mock(ValueID.class);
        readingDouble = mock(Reading.class);
        sensorIDDouble = mock(SensorID.class);
        startTimeReadingDouble = mock(Timestamp.class);
        endTimeReadingDouble = mock(Timestamp.class);

        periodTimeValue = new PeriodTimeValue(valueIDDouble, sensorIDDouble, readingDouble, startTimeReadingDouble, endTimeReadingDouble);
    }

    /**
     * Test success of InstantValue creation.
     */
    @Test
    void successPeriodTimeValueCreationCreation() {
        assertNotNull(periodTimeValue);
    }

    /**
     * Test success of InstantValue creation with randomly generated ValueID.
     */
    @Test
    void successPeriodTimeValueCreationRandomValueID() {
        PeriodTimeValue periodTimeValue1 = new PeriodTimeValue(sensorIDDouble, readingDouble, startTimeReadingDouble, endTimeReadingDouble);
        assertNotNull(periodTimeValue1);
    }

    /**
     * Test identity method.
     */
    @Test
    void getPeriodTimeValueIdentity() {
        assertEquals(valueIDDouble, periodTimeValue.identity());
    }

    /**
     * Test getStartTimeReading method.
     */
    @Test
    void getStartTimeReading() {
        assertEquals(startTimeReadingDouble, periodTimeValue.getStartTimeReading());
    }

    /**
     * Test getEndTimeReading method.
     */
    @Test
    void getEndTimeReading() {
        assertEquals(endTimeReadingDouble, periodTimeValue.getEndTimeReading());
    }

    /**
     * Test getReading method.
     */
    @Test
    void getReading() {
        assertEquals(readingDouble, periodTimeValue.getReading());
    }

    /**
     * Test getSensorID method.
     */
    @Test
    void getSensorID() {
        assertEquals(sensorIDDouble, periodTimeValue.getSensorID());
    }

    /**
     * Test success of isSameAs method with the same object.
     */
    @Test
    void successPeriodTimeValueIsSameAsObject() {
        PeriodTimeValue periodTimeValue1 = new PeriodTimeValue(valueIDDouble, sensorIDDouble, readingDouble, startTimeReadingDouble, endTimeReadingDouble);
        assertTrue(periodTimeValue.isSameAs(periodTimeValue1));
    }

    /**
     * Test failure of isSameAs method with different ValueID.
     */
    @Test
    void failPeriodTimeValueIsNotSameAsObject() {
        ValueID valueID1 = mock(ValueID.class);
        PeriodTimeValue periodTimeValue1 = new PeriodTimeValue(valueID1, sensorIDDouble, readingDouble, startTimeReadingDouble, endTimeReadingDouble);
        assertFalse(periodTimeValue.isSameAs(periodTimeValue1));
    }

    /**
     * Test failure of isSameAs method with null object.
     */
    @Test
    void failPeriodTimeValueIsNotSameAsObjectNull() {
        assertFalse(periodTimeValue.isSameAs(null));
    }
}