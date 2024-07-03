package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.value.ImpFactoryPeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link MapperPeriodTimeValueDataModel} class.
 */
class MapperPeriodTimeValueDataModelTest {
    private PeriodTimeValueDataModel periodTimeValueDataModel;
    private ImpFactoryPeriodTimeValue factoryPeriodTimeValue;
    private MapperPeriodTimeValueDataModel mapperPeriodTimeValueDataModel;
    private Value periodTimeValue;

    /**
     * Sets up the test environment before each test case in {@link MapperPeriodTimeValueDataModel} class.
     */
    @BeforeEach
    void setUp() {
        factoryPeriodTimeValue = new ImpFactoryPeriodTimeValue();
        SensorID sensorID = new SensorID("PowerAverageSensor");
        Reading reading = new Reading("600", "W");
        Timestamp startTimeReading = Timestamp.valueOf("2022-01-01 00:00:00.0");
        Timestamp endTimeReading = Timestamp.valueOf("2022-01-01 01:00:00.0");

        periodTimeValue = factoryPeriodTimeValue.createValue(sensorID, reading, startTimeReading, endTimeReading);

        periodTimeValueDataModel = new PeriodTimeValueDataModel(periodTimeValue);

        mapperPeriodTimeValueDataModel = new MapperPeriodTimeValueDataModel();
    }

    /**
     * Tests the conversion of PeriodTimeValueDataModel to domain Value.
     */
    @Test
    void successConversionToDomain() {
        Value result = mapperPeriodTimeValueDataModel.toDomain(factoryPeriodTimeValue, periodTimeValueDataModel);

        assertNotNull(result);
        assertTrue(periodTimeValue.isSameAs(result));
    }

    /**
     * Tests the conversion of PeriodTimeValueDataModel to domain Value when the factory is null.
     */
    @Test
    void failConversionToDomainNullFactory() {
        Value result = mapperPeriodTimeValueDataModel.toDomain(null, periodTimeValueDataModel);

        assertNull(result);
    }

    /**
     * Tests the conversion of a list of PeriodTimeValueDataModels to a list of domain Values.
     */
    @Test
    void successConversionToDomainList() {
        List<PeriodTimeValueDataModel> dataModels = List.of(periodTimeValueDataModel);

        Iterable<Value> result = mapperPeriodTimeValueDataModel.toDomainList(factoryPeriodTimeValue, dataModels);

        assertEquals(1, ((List<Value>) result).size());
        assertTrue(periodTimeValue.isSameAs(((List<Value>) result).get(0)));
    }

    /**
     * Tests the conversion to domain Value list with null PeriodTimeValueDataModels.
     */
    @Test
    void successConversionToDomainListWithNullDevice() {
        PeriodTimeValueDataModel nullPeriodTimeValue = null;
        List<PeriodTimeValueDataModel> dataModels = new ArrayList<>();
        dataModels.add(nullPeriodTimeValue);
        dataModels.add(nullPeriodTimeValue);

        Iterable<Value> result = mapperPeriodTimeValueDataModel.toDomainList(factoryPeriodTimeValue, dataModels);

        assertTrue(((List<Value>) result).isEmpty());
    }

    /**
     * Tests the conversion of a PeriodTimeValueDataModel list to domain Value list when the list of data models is null.
     */
    @Test
    void failConversionToDomainListNullListOfDataModels() {
        List<PeriodTimeValueDataModel> dataModels = null; //provided iterable of data models is null
        Iterable<Value> result = mapperPeriodTimeValueDataModel.toDomainList(factoryPeriodTimeValue, dataModels);
        assertNull(result);
    }

    /**
     * Tests the conversion of PeriodTimeValueDataModel list to domain Value list when the factory is null.
     */
    @Test
    void failConversionToDomainListNullFactory() {
        List<PeriodTimeValueDataModel> PeriodTimeValueDataModels = List.of(periodTimeValueDataModel);
        Iterable<Value> result = mapperPeriodTimeValueDataModel.toDomainList(null, PeriodTimeValueDataModels);
        assertNull(result);
    }

}