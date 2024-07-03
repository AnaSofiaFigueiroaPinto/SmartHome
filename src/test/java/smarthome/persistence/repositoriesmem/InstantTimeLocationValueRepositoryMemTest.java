package smarthome.persistence.repositoriesmem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import smarthome.domain.value.InstantTimeLocationValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.sql.Timestamp.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for InstantTimeLocationValueRepositoryMem
 */
class InstantTimeLocationValueRepositoryMemTest {

    /**
     * Represents a SensorID used for testing.
     */
    private  SensorID sensorID1;

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
    private Value nonExistentValue;

    /**
     * Represents the ID of a non-existent Value, used for testing.
     */
    private ValueID nonExistentValueID;

    /**
     * Represents an InstantTimeLocationValue used for testing.
     */
    private InstantTimeLocationValue instantTimeLocationValue;

    /**
     * Represents an instance of InstantTimeLocationValueRepositoryMem used for testing.
     */
    private InstantTimeLocationValueRepositoryMem instantTimeLocationValueRepositoryMem;

    /**
     * Map of ValueID and Value objects.
     */
    private Map<ValueID, Value> valueMap;

    /**
     * Set up method to create a new instance of InstantTimeLocationValueRepositoryMem and a map of ValueID and Value objects.
     */
    @BeforeEach
    void setUp() {
        valueMap = new HashMap<>();

        sensorID1 = mock(SensorID.class);
        timestamp1 = mock(Timestamp.class);
        ValueID valueID1 = mock(ValueID.class);
        value1 = mock(Value.class);
        instantTimeLocationValue = mock(InstantTimeLocationValue.class);

        when(value1.identity()).thenReturn(valueID1);

        when(instantTimeLocationValue.getSensorID()).thenReturn(sensorID1);
        valueMap.put(value1.identity(), instantTimeLocationValue);

        instantTimeLocationValueRepositoryMem = new InstantTimeLocationValueRepositoryMem(valueMap);
    }

    /**
     * Validate method that gives List of Value type object from repo associated to a SensorID
     */
    @Test
    void successfullyRetrieveSensorIDValues() {
        List<Value> values = instantTimeLocationValueRepositoryMem.findBySensorId(sensorID1);
        assertTrue(values.contains(instantTimeLocationValue));
    }

    /**
     * Validate method that gives List of Value type object from repo associated to a SensorID between a period of time
     */
    @Test
    void successfullyRetrieveInstantValues() {
        Timestamp start = mock(Timestamp.class);
        Timestamp end = mock(Timestamp.class);

        when(instantTimeLocationValue.getInstantTime()).thenReturn(timestamp1);
        when(instantTimeLocationValue.getInstantTime().after(start)).thenReturn(true);
        when(instantTimeLocationValue.getInstantTime().before(end)).thenReturn(true);

        List<Value> values = instantTimeLocationValueRepositoryMem.findBySensorIdBetweenPeriodOfTime(sensorID1, start, end);
        assertTrue(values.contains(instantTimeLocationValue));
    }

    /**
     * Tests the retrieval of values by valid IDs in the repository.
     */
    @Test
    void successfullyFindValueByID() {
        Optional<Value> values = instantTimeLocationValueRepositoryMem.findEntityByID(value1.identity());
        assertTrue(values.isPresent());
    }

    /**
     * Tests the failure scenario of retrieving a value by an invalid ID.
     */
    @Test
    void failFindValueByID(){
        nonExistentValueID = mock(ValueID.class);
        nonExistentValue = mock(Value.class);

        when(nonExistentValue.identity()).thenReturn(nonExistentValueID);

        Optional<Value> values = instantTimeLocationValueRepositoryMem.findEntityByID(nonExistentValue.identity());
        assertFalse(values.isPresent());
    }

    /**
     * Tests the retrieval of all entities from the repository.
     */
    @Test
    void successfullyFindAllEntities(){
        Iterable<Value> values = instantTimeLocationValueRepositoryMem.findAllEntities();
        assertEquals(values, valueMap.values());
    }

    /**
     * Tests whether the repository contains entities by valid.
     */
    @Test
    void successfullyContainsEntityByID(){
        boolean result= instantTimeLocationValueRepositoryMem.containsEntityByID(value1.identity());
        assertTrue(result);
    }

    /**
     * Tests whether the repository fails to find an entity by an invalid ID.
     */
    @Test
    void failContainsEntityByID(){
        nonExistentValueID = mock(ValueID.class);
        nonExistentValue = mock(Value.class);

        when(nonExistentValue.identity()).thenReturn(nonExistentValueID);

        boolean result= instantTimeLocationValueRepositoryMem.containsEntityByID(nonExistentValue.identity());
        assertFalse(result);
    }

    /**
     * Validate method that retrieves List of Value type object from repo that belong to a specific SensorID.
     * Returns the last value recorded with the SensorID specified.
     */
    @Test
    void successfullyRetrieveLastValueBySensorID() {
        Timestamp timestamp1 = valueOf("2024-04-17 10:00:00.0");
        when(instantTimeLocationValue.getInstantTime()).thenReturn(timestamp1);

        SensorID sensorID2 = mock(SensorID.class);

        InstantTimeLocationValue value3 = mock(InstantTimeLocationValue.class);
        ValueID valueID3 = mock(ValueID.class);
        when(value3.identity()).thenReturn(valueID3);
        when(value3.getSensorID()).thenReturn(sensorID2);
        Timestamp timestamp3 = valueOf("2024-04-20 10:00:00.0");
        when(value3.getInstantTime()).thenReturn(timestamp3);

        InstantTimeLocationValue value4 = mock(InstantTimeLocationValue.class);
        ValueID valueID4 = mock(ValueID.class);
        when(value4.identity()).thenReturn(valueID4);
        when(value4.getSensorID()).thenReturn(sensorID1);
        Timestamp timestamp4 = valueOf("2024-04-20 13:00:00.0");
        when(value4.getInstantTime()).thenReturn(timestamp4);

        InstantTimeLocationValue value5 = mock(InstantTimeLocationValue.class);
        ValueID valueID5 = mock(ValueID.class);
        when(value5.identity()).thenReturn(valueID5);
        when(value5.getSensorID()).thenReturn(sensorID1);
        Timestamp timestamp5 = valueOf("2024-04-25 13:00:00.0");
        when(value5.getInstantTime()).thenReturn(timestamp5);

        valueMap.put(valueID3, value3);
        valueMap.put(valueID4, value4);
        valueMap.put(valueID5, value5);

        Value resultValue = instantTimeLocationValueRepositoryMem.findLastValueRecorded(sensorID1);
        assertEquals(value5, resultValue);
    }

    /**
     * Validate method that retrieves List of Value type object from repo that belong to a specific SensorID.
     * Returns null if the list is empty.
     */
    @Test
    void failToRetrieveLastValueBySensorID() {
        SensorID sensorID2 = mock(SensorID.class);
        Value resultValue = instantTimeLocationValueRepositoryMem.findLastValueRecorded(sensorID2);
        assertNull(resultValue);
    }
}