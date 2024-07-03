package smarthome.domain.value;

import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ImpFactoryInstantTimeLocationValueTest {

    /**
     * Tests the creation of a Value object With ID with valid parameters.
     */
    @Test
    void testCreateValueWithIDValidParameters() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        GPSCode gpsCode = mock(GPSCode.class);

        Value valueWithID = factory.createValue(valueID, sensorID, reading, timestamp,gpsCode);

        assertNotNull(valueWithID);
    }

    /**
     * Tests the creation of a Value object Without ID with valid parameters.
     */
    @Test
    void testCreateValueWithoutIDValidParameters() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        GPSCode gpsCode = mock(GPSCode.class);

        Value valueWithoutID = factory.createValue(sensorID, reading, timestamp1, gpsCode);

        assertNotNull(valueWithoutID);
    }

    /**
     * Tests the creation of a Value object With ID when invalid valueID are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersValueID() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        GPSCode gpsCode = mock(GPSCode.class);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(null, sensorID, reading, timestamp1, gpsCode));
    }

    /**
     * Tests the creation of a Value object With ID when invalid sensorID are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersSensorID() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        ValueID valueID = mock(ValueID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        GPSCode gpsCode = mock(GPSCode.class);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, null, reading, timestamp1, gpsCode));
    }

    /**
     * Tests the creation of a Value object With ID when invalid reading are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersReading() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        GPSCode gpsCode = mock(GPSCode.class);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, sensorID, null, timestamp1, gpsCode));
    }

    /**
     * Tests the creation of a Value object With ID when invalid timestamp are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersTimestamp1() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        GPSCode gpsCode = mock(GPSCode.class);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, sensorID, reading, null, gpsCode));
    }

    /**
     * Tests the creation of a Value object With ID when invalid GPSCode are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersGPSCode() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, sensorID, reading, timestamp1, null));
    }

    /**
     * Tests the creation of a Value object With ID when invalid sensorID are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersSensorID() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        GPSCode gpsCode = mock(GPSCode.class);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(null, reading, timestamp1, gpsCode));
    }

    /**
     * Tests the creation of a Value object With ID when invalid reading are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersReading() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        GPSCode gpsCode = mock(GPSCode.class);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(sensorID, null, timestamp1, gpsCode));
    }

    /**
     * Tests the creation of a Value object With ID when invalid timestamp are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersTimestamp1() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        GPSCode gpsCode = mock(GPSCode.class);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(sensorID, reading, null, gpsCode));
    }

    /**
     * Tests the creation of a Value object With ID when invalid GPSCode are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersGPSCode() {
        ImpFactoryInstantTimeLocationValue factory = new ImpFactoryInstantTimeLocationValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(sensorID, reading, timestamp1, null));
    }
}