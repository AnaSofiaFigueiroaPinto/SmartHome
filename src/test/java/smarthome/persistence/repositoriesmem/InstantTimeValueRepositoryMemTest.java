package smarthome.persistence.repositoriesmem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.Value;

import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Timestamp.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for InstantTimeValueRepositoryMem
 */
class InstantTimeValueRepositoryMemTest {

    /**
     * Map of ValueID and Value objects.
     */
    private Map<ValueID, Value> valueMap;

    /**
     * Represents a SensorID used for testing.
     */
    private SensorID sensorID1;

    /**
     * Represents a Timestamp used for testing.
     */
    private Timestamp timestamp1;

    /**
     * Represents a Value used for testing.
     */
    private Value value1;

    /**
     * Represents a Value that does not exist in the repository, used for testing.
     */
    private Value value2;

    /**
     * Represents the ID of a non-existent Value, used for testing.
     */
    private ValueID valueID2;

    /**
     * Represents an InstantTimeValue used for testing.
     */
    private InstantTimeValue instantTimeValue;

    /**
     * Represents an instance of InstantTimeValueRepositoryMem used for testing.
     */
    private InstantTimeValueRepositoryMem instantTimeValueRepositoryMem;

    /**
     * Set up method to create instances of objects used in the tests.
     */
    @BeforeEach
    void setUp() {
        valueMap = new HashMap<>();

        sensorID1 = mock(SensorID.class);
        timestamp1 = mock(Timestamp.class);
        ValueID valueID1 = mock(ValueID.class);
        value1 = mock(Value.class);
        instantTimeValue = mock(InstantTimeValue.class);

        when(value1.identity()).thenReturn(valueID1);

        when(instantTimeValue.getSensorID()).thenReturn(sensorID1);
        valueMap.put(value1.identity(), instantTimeValue);

        instantTimeValueRepositoryMem = new InstantTimeValueRepositoryMem(valueMap);
    }

    /**
     * Validate method that retrieves List of Value type object from repo that belong to a specific SensorID
     */
    @Test
    void successfullyRetrieveSensorIDValues() {
        List<Value> values = instantTimeValueRepositoryMem.findBySensorId(sensorID1);
        assertTrue(values.contains(instantTimeValue));
    }

    /**
     * Validate method that retrieves List of Value type object from repo that belong to a specific SensorID and where
     * the time of the reading is between the start and end timestamps.
     */
    @Test
    void successfullyRetrieveInstantValues() {
        ;
        Timestamp start = mock(Timestamp.class);
        Timestamp end = mock(Timestamp.class);

        when(instantTimeValue.getInstantTimeReading()).thenReturn(timestamp1);
        when(instantTimeValue.getInstantTimeReading().after(start)).thenReturn(true);
        when(instantTimeValue.getInstantTimeReading().before(end)).thenReturn(true);

        List<Value> values = instantTimeValueRepositoryMem.findBySensorIdBetweenPeriodOfTime(sensorID1, start, end);
        assertTrue(values.contains(instantTimeValue));
    }

    /**
     * Check if method returns an entity when given a valid ID and doesn't return anything when given an invalid ID
     */
    @Test
    void findByIDValidIDAndInvalidID() {
        value2 = mock(Value.class);
        valueID2 = mock(ValueID.class);

        when(value2.identity()).thenReturn(valueID2);

        assertTrue(instantTimeValueRepositoryMem.findEntityByID(value1.identity()).isPresent());
        assertFalse(instantTimeValueRepositoryMem.findEntityByID(value2.identity()).isPresent());
    }

    /**
     * Check if method returns all entities in repo
     */
    @Test
    void findAllEntities() {
        Iterable<Value> values = instantTimeValueRepositoryMem.findAllEntities();
        assertEquals(values, valueMap.values());
    }

    /**
     * Validate that an entity exists in the repo when given its ID and doesn't exist when given an invalid ID
     */
    @Test
    void containsEntityByIDValidIDAndInvalidID() {
        value2 = mock(Value.class);
        valueID2 = mock(ValueID.class);

        when(value2.identity()).thenReturn(valueID2);

        assertTrue(instantTimeValueRepositoryMem.containsEntityByID(value1.identity()));
        assertFalse(instantTimeValueRepositoryMem.containsEntityByID(value2.identity()));
    }

    /**
     * Validate method that retrieves List of Value type object from repo that belong to a specific SensorID.
     * Returns the last value recorded with the SensorID specified.
     */
    @Test
    void successfullyRetrieveLastValueBySensorID() {
        Timestamp timestamp1 = valueOf("2024-04-17 10:00:00.0");
        when(instantTimeValue.getInstantTimeReading()).thenReturn(timestamp1);

        SensorID sensorID2 = mock(SensorID.class);

        InstantTimeValue value3 = mock(InstantTimeValue.class);
        ValueID valueID3 = mock(ValueID.class);
        when(value3.identity()).thenReturn(valueID3);
        when(value3.getSensorID()).thenReturn(sensorID2);
        Timestamp timestamp3 = valueOf("2024-04-20 10:00:00.0");
        when(value3.getInstantTimeReading()).thenReturn(timestamp3);

        InstantTimeValue value4 = mock(InstantTimeValue.class);
        ValueID valueID4 = mock(ValueID.class);
        when(value4.identity()).thenReturn(valueID4);
        when(value4.getSensorID()).thenReturn(sensorID1);
        Timestamp timestamp4 = valueOf("2024-04-20 13:00:00.0");
        when(value4.getInstantTimeReading()).thenReturn(timestamp4);

        InstantTimeValue value5 = mock(InstantTimeValue.class);
        ValueID valueID5 = mock(ValueID.class);
        when(value5.identity()).thenReturn(valueID5);
        when(value5.getSensorID()).thenReturn(sensorID1);
        Timestamp timestamp5 = valueOf("2024-04-25 13:00:00.0");
        when(value5.getInstantTimeReading()).thenReturn(timestamp5);

        valueMap.put(valueID3, value3);
        valueMap.put(valueID4, value4);
        valueMap.put(valueID5, value5);

        Value resultValue = instantTimeValueRepositoryMem.findLastValueRecorded(sensorID1);
        assertEquals(value5, resultValue);
    }

    /**
     * Validate method that retrieves List of Value type object from repo that belong to a specific SensorID.
     * Returns null if the list is empty.
     */
    @Test
    void failToRetrieveLastValueBySensorID() {
        SensorID sensorID2 = mock(SensorID.class);
        Value resultValue = instantTimeValueRepositoryMem.findLastValueRecorded(sensorID2);
        assertNull(resultValue);
    }
}