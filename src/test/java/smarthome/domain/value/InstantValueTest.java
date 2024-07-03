package smarthome.domain.value;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for InstantValue.
 */
class InstantValueTest {

    /**
     * The instantTime mock.
     */
    private Timestamp instantTimeReadingDouble;

    /**
     * The Reading mock.
     */
    private Reading readingDouble;

    /**
     * The ValueID mock.
     */
    private ValueID valueIDDouble;

    /**
     * The SensorID mock.
     */
    private SensorID sensorIDDouble;

    /**
     * The InstantValue object.
     */
    private InstantTimeValue instantTimeValue;

    /**
     * Set up mock objects and instantiate InstantValue for each test.
     */
    @BeforeEach
    void setUp()
    {
        instantTimeReadingDouble = mock (Timestamp.class);
        readingDouble = mock(Reading.class);
        valueIDDouble = mock(ValueID.class);
        sensorIDDouble = mock(SensorID.class);

        instantTimeValue = new InstantTimeValue(valueIDDouble, sensorIDDouble, readingDouble, instantTimeReadingDouble);
    }

    /**
     * Test success of InstantValue creation.
     */
    @Test
    void successInstantTimeValueCreation()
    {
        assertNotNull(instantTimeValue);
    }

    /**
     * Test success of InstantValue creation with randomly generated ValueID.
     */
    @Test
    void successInstantTimeValueCreationRandomValueID()
    {
        InstantTimeValue instantTimeValue = new InstantTimeValue(sensorIDDouble, readingDouble, instantTimeReadingDouble);
        assertNotNull(instantTimeValue);
    }

    /**
     * Test identity method.
     */
    @Test
    void getInstantTimeValueIdentity()
    {
        assertEquals(valueIDDouble, instantTimeValue.identity());
    }

    /**
     * Test getInstantTimeReading method.
     */
    @Test
    void getInstantTimeReading()
    {
        assertEquals(instantTimeReadingDouble, instantTimeValue.getInstantTimeReading());
    }

    /**
     * Test getReading method.
     */
    @Test
    void getReading()
    {
        assertEquals(readingDouble, instantTimeValue.getReading());
    }

    /**
     * Test getSensorID method.
     */
    @Test
    void getSensorID()
    {
        assertEquals(sensorIDDouble, instantTimeValue.getSensorID());
    }

    /**
     * Test success of isSameAs method with the same object.
     */
    @Test
    void successInstantTimeValueIsSameAsObject()
    {
        InstantTimeValue instantTimeValue2 = new InstantTimeValue(valueIDDouble, sensorIDDouble, readingDouble, instantTimeReadingDouble);
        assertTrue(instantTimeValue.isSameAs(instantTimeValue2));
    }

    /**
     * Test failure of isSameAs method with different ValueID.
     */
    @Test
    void failInstantTimeValueIsNotSameAsObject()
    {
        ValueID valueID1 = mock(ValueID.class);
        InstantTimeValue instantTimeValue2 = new InstantTimeValue(valueID1, sensorIDDouble, readingDouble, instantTimeReadingDouble);
        assertFalse(instantTimeValue.isSameAs(instantTimeValue2));
    }

    /**
     * Test failure of isSameAs method with null object.
     */
    @Test
    void failInstantTimeValueIsNotSameAsObjectNull()
    {
        assertFalse(instantTimeValue.isSameAs(null));
    }
}