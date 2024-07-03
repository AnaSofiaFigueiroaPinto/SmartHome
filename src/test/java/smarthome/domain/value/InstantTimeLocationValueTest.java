package smarthome.domain.value;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the InstantTimeLocationValue class.
 */
class InstantTimeLocationValueTest {

    /**
     * The InstantTimeReading mock.
     */
    private Timestamp instantTimeReadingDouble;
    /**
     * The gpsCode mock.
     */
    private GPSCode gpsCodeDouble;

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
     * The InstantTimeLocationValue object.
     */
    private InstantTimeLocationValue instantTimeLocationValue;

    /**
     * Set up mock objects and instantiate InstantTimeLocationValue for each test.
     */
    @BeforeEach
    void setUp()
    {
        instantTimeReadingDouble = mock (Timestamp.class);
        gpsCodeDouble = mock(GPSCode.class);
        readingDouble = mock(Reading.class);
        valueIDDouble = mock(ValueID.class);
        sensorIDDouble = mock(SensorID.class);

        instantTimeLocationValue = new InstantTimeLocationValue(valueIDDouble, sensorIDDouble, readingDouble, instantTimeReadingDouble, gpsCodeDouble);
    }

    /**
     * Tests success of InstantTimeLocationValue creation.
     */
    @Test
    void successInstantTimeLocationValueCreation()
    {
        assertNotNull(instantTimeLocationValue);
    }

    /**
     * Test success of InstantTimeLocationValue creation with randomly generated ValueID.
     */
    @Test
    void successInstantTimeLocationValueCreationRandomValueID()
    {
        InstantTimeLocationValue instantTimeLocationValue = new InstantTimeLocationValue(sensorIDDouble, readingDouble, instantTimeReadingDouble, gpsCodeDouble);
        assertNotNull(instantTimeLocationValue);
    }

    /**
     * Test identity method.
     */
    @Test
    void getInstantTimeLocationValueIdentity()
    {
        assertEquals(valueIDDouble, instantTimeLocationValue.identity());
    }

    /**
     * Test getInstantTime method.
     */
    @Test
    void getInstantTimeLocationReading()
    {
        assertEquals(instantTimeReadingDouble, instantTimeLocationValue.getInstantTime());
    }

    /**
     * Test getReading method.
     */
    @Test
    void getReading()
    {
        assertEquals(readingDouble, instantTimeLocationValue.getReading());
    }

    /**
     * Test getSensorID method.
     */
    @Test
    void getSensorID()
    {
        assertEquals(sensorIDDouble, instantTimeLocationValue.getSensorID());
    }

    /**
     * Test getGpsCode method.
     */
    @Test
    void getGpsCode()
    {
        assertEquals(gpsCodeDouble, instantTimeLocationValue.getGpsCode());
    }

    /**
     * Test success of isSameAs method with the same object.
     */
    @Test
    void successInstantTimeLocationValueIsSameAsObject()
    {
        InstantTimeLocationValue instantTimeLocationValue1 = new InstantTimeLocationValue(valueIDDouble, sensorIDDouble, readingDouble, instantTimeReadingDouble, gpsCodeDouble);
        assertTrue(instantTimeLocationValue.isSameAs(instantTimeLocationValue1));
    }

    /**
     * Test failure of isSameAs method with different ValueID.
     */
    @Test
    void failInstantTimeLocationValueIsSameAsObject()
    {
        ValueID valueID1 = mock(ValueID.class);
        InstantTimeLocationValue instantTimeLocationValue1 = new InstantTimeLocationValue(valueID1, sensorIDDouble, readingDouble, instantTimeReadingDouble, gpsCodeDouble);
        assertFalse(instantTimeLocationValue.isSameAs(instantTimeLocationValue1));
    }

    /**
     * Test failure of isSameAs method with null object.
     */
    @Test
    void failInstantTimeLocationValueIsSameAsObjectNull()
    {
        assertFalse(instantTimeLocationValue.isSameAs(null));
    }

}