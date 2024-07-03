package smarthome.persistence.repositoriesmem;

import org.hibernate.sql.ast.tree.insert.Values;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.value.PeriodTimeValue;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.domain.value.Value;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PeriodTimeValueRepositoryMemTest {

    /**
     * The valueMap attribute representing a map of ValueID and Value.
     */
    private Map<ValueID, Value> valueMap;
    private SensorID sensorID1;
    private SensorID sensorID2;
    private PeriodTimeValue periodTimeValue1;
    private PeriodTimeValue periodTimeValue2;
    private Value value1;
    private Value value2;
    PeriodTimeValueRepositoryMem periodTimeValueRepositoryMem;

    /**
     * Set up mock objects and repositories.
     */

    @BeforeEach
    void setUp() {
        valueMap = new HashMap<>();

        sensorID1 = mock(SensorID.class);
        sensorID2 = mock(SensorID.class);


        periodTimeValue1 = mock(PeriodTimeValue.class);
        periodTimeValue2 = mock(PeriodTimeValue.class);

        ValueID valueID1 = mock(ValueID.class);
        ValueID valueID2 = mock(ValueID.class);

        value1 = mock(Value.class);
        value2 = mock(Value.class);

        when(value1.identity()).thenReturn(valueID1);
        when(value2.identity()).thenReturn(valueID2);

        when(periodTimeValue1.getSensorID()).thenReturn(sensorID1);
        when(periodTimeValue2.getSensorID()).thenReturn(sensorID2);

        valueMap.put(value1.identity(), periodTimeValue1);
        periodTimeValueRepositoryMem = new PeriodTimeValueRepositoryMem(valueMap);
    }

    /**
     * Test if a value is successfully retrieved by SensorID.
     */
    @Test
    void successfullyRetrieveSensorIDValues() {
        List<Value> values = periodTimeValueRepositoryMem.findBySensorId(sensorID1);
        assertTrue(values.contains(periodTimeValue1));
    }

    /**
     * Test if a value is successfully retrieved by SensorID between a period of time.
     */
    @Test
    void successfullyRetrievePeriodTimeValues() {
        Timestamp timestamp1 = mock(Timestamp.class);
        when(periodTimeValue1.getStartTimeReading()).thenReturn(timestamp1);
        Timestamp timestamp2 = mock(Timestamp.class);
        when(periodTimeValue1.getEndTimeReading()).thenReturn(timestamp2);
        Timestamp start = mock(Timestamp.class);
        when(periodTimeValue1.getStartTimeReading().after(start)).thenReturn(true);
        Timestamp end = mock(Timestamp.class);
        when(periodTimeValue1.getEndTimeReading().before(end)).thenReturn(true);
        List<Value> values = periodTimeValueRepositoryMem.findBySensorIdBetweenPeriodOfTime(sensorID1, start, end);

        assertTrue(values.contains(periodTimeValue1));
    }

    /**
     * Test if an entity is retrieved or not when given a valid and an invalid ID, respectivelly.
     */
    @Test
    void findEntityByIDValidAndInvalidID() {
        assertTrue(periodTimeValueRepositoryMem.findEntityByID(value1.identity()).isPresent());
        assertFalse(periodTimeValueRepositoryMem.findEntityByID(value2.identity()).isPresent());
    }

    /**
     * Test if all entities are retrieved from the repository.
     */
    @Test
    void successfullyFindAllEntities() {
        Iterable<Value> values = periodTimeValueRepositoryMem.findAllEntities();
        assertEquals(values, valueMap.values());
    }

    /**
     * Test if the repository contains a specific ID
     */
    @Test
    void successfullyCheckIfEntityExists() {
        assertTrue(periodTimeValueRepositoryMem.containsEntityByID(value1.identity()));
        assertFalse(periodTimeValueRepositoryMem.containsEntityByID(value2.identity()));
    }
}