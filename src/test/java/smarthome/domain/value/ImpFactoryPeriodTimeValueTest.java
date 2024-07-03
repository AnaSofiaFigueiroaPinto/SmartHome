package smarthome.domain.value;

import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ImpFactoryPeriodTimeValueTest {

    /**
     * Tests the creation of a Value object With ID with valid parameters.
     */
    @Test
    void testCreateValueWithIDValidParameters() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 50);

        Value valueWithID = factory.createValue(valueID, sensorID, reading, timestamp1, timestamp2);

        assertNotNull(valueWithID);
    }

    /**
     * Tests the creation of a Value object Without ID with valid parameters.
     */
    @Test
    void testCreateValueWithoutIDValidParameters() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 50);

        Value valueWithoutID = factory.createValue(sensorID, reading, timestamp1, timestamp2);

        assertNotNull(valueWithoutID);
    }

    /**
     * Tests the creation of a Value object With ID when invalid valueID are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersValueID() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 50);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(null, sensorID, reading, timestamp1, timestamp2));
    }

    /**
     * Tests the creation of a Value object With ID when invalid sensorID are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersSensorID() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        ValueID valueID = mock(ValueID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 50);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, null, reading, timestamp1, timestamp2));
    }

    /**
     * Tests the creation of a Value object With ID when invalid reading are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersReading() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 50);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, sensorID, null, timestamp1, timestamp2));
    }

    /**
     * Tests the creation of a Value object With ID when invalid timestamp are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersTimestamp1() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        ValueID valueID = mock(ValueID.class);
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 50);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(valueID, sensorID, reading, null, timestamp2));
    }

    /**
     * Tests the creation of a Value object With ID when invalid parameters are provided.
     */
    @Test
    void testCreateValueWithIDInvalidParametersTimestamp2() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
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
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 50);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(null, reading, timestamp1, timestamp2));
    }

    /**
     * Tests the creation of a Value object With ID when invalid reading are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersReading() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 50);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(sensorID, null, timestamp1, timestamp2));
    }

    /**
     * Tests the creation of a Value object With ID when invalid timestamp are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersTimestamp1() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 50);

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(sensorID, reading, null, timestamp2));
    }

    /**
     * Tests the creation of a Value object With ID when invalid parameters are provided.
     */
    @Test
    void testCreateValueWithoutIDInvalidParametersTimestamp2() {
        ImpFactoryPeriodTimeValue factory = new ImpFactoryPeriodTimeValue();
        SensorID sensorID = mock(SensorID.class);
        Reading reading = mock(Reading.class);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        assertThrows(IllegalArgumentException.class, () -> factory.createValue(sensorID, reading, timestamp1, null));
    }
}