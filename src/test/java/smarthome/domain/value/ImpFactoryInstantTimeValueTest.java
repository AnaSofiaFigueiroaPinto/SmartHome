package smarthome.domain.value;

import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ImpFactoryInstantTimeValueTest {
    /**
     * Tests the creation of a Value object With ID with valid parameters.
     */
    @Test
    void testCreateValueWithIDValidParameters() {
        ImpFactoryInstantTimeValue factory = new ImpFactoryInstantTimeValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        Value valueWithID = factory.createValue(valueID, sensorID, reading, timestamp1);

        assertNotNull(valueWithID);
    }

    /**
     * Tests the creation of a Value object Without ID with valid parameters.
     */
    @Test
    void testCreateValueWithoutIDValidParameters() {
        ImpFactoryInstantTimeValue factory = new ImpFactoryInstantTimeValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        Value valueWithoutID = factory.createValue(sensorID, reading, timestamp1);

        assertNotNull(valueWithoutID);
    }

    /**
     * Tests the creation of a Value object With ID when invalid valueID are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersValueID() {
        ImpFactoryInstantTimeValue factory = new ImpFactoryInstantTimeValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(null, sensorID, reading, timestamp1));
    }

    /**
     * Tests the creation of a Value object With ID when invalid sensorID are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersSensorID() {
        ImpFactoryInstantTimeValue factory = new ImpFactoryInstantTimeValue();
        ValueID valueID = mock(ValueID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, null, reading, timestamp1));
    }

    /**
     * Tests the creation of a Value object With ID when invalid reading are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersReading() {
        ImpFactoryInstantTimeValue factory = new ImpFactoryInstantTimeValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, sensorID, null, timestamp1));
    }

    /**
     * Tests the creation of a Value object With ID when invalid timestamp are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersTimestamp1() {
        ImpFactoryInstantTimeValue factory = new ImpFactoryInstantTimeValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, sensorID, reading, null));
    }

    /**
     * Tests the creation of a Value object With ID when invalid sensorID are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersSensorID() {
        ImpFactoryInstantTimeValue factory = new ImpFactoryInstantTimeValue();
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(null, reading, timestamp1));
    }

    /**
     * Tests the creation of a Value object With ID when invalid reading are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersReading() {
        ImpFactoryInstantTimeValue factory = new ImpFactoryInstantTimeValue();
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> factory.createValue(sensorID, null, timestamp1));
    }

    /**
     * Tests the creation of a Value object With ID when invalid timestamp are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersTimestamp1() {
        ImpFactoryInstantTimeValue factory = new ImpFactoryInstantTimeValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(sensorID, reading, null));
    }
}