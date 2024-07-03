package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.value.ImpFactoryInstantTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link MapperInstantTimeValueDataModel} class.
 */
class MapperInstantTimeValueDataModelTest {
    private InstantTimeValueDataModel InstantTimeValueDataModel;
    private ImpFactoryInstantTimeValue factoryInstantTimeValue;
    private MapperInstantTimeValueDataModel mapperInstantTimeValueDataModel;
    private Value InstantTimeValue;

    /**
     * Sets up the test environment before each test case in {@link MapperInstantTimeValueDataModel} class.
     */
    @BeforeEach
    void setUp() {
        factoryInstantTimeValue = new ImpFactoryInstantTimeValue();
        SensorID sensorID = new SensorID("TemperatureSensor");
        Reading reading = new Reading("30", "ºC");
        Timestamp instantTimeReading = Timestamp.valueOf("2022-01-01 00:00:00.0");

        InstantTimeValue = factoryInstantTimeValue.createValue(sensorID, reading, instantTimeReading);

        InstantTimeValueDataModel = new InstantTimeValueDataModel(InstantTimeValue);

        mapperInstantTimeValueDataModel = new MapperInstantTimeValueDataModel();
    }

    /**
     * Tests the conversion of InstantTimeValueDataModel to domain Value.
     */
    @Test
    void successConversionToDomain() {
        Value result = mapperInstantTimeValueDataModel.toDomain(factoryInstantTimeValue, InstantTimeValueDataModel);

        assertNotNull(result);
        assertTrue(InstantTimeValue.isSameAs(result));
    }

    /**
     * Tests the conversion of InstantTimeValueDataModel to domain Value when the factory is null.
     */
    @Test
    void failConversionToDomainNullFactory() {
        Value result = mapperInstantTimeValueDataModel.toDomain(null, InstantTimeValueDataModel);

        assertNull(result);
    }

    /**
     * Tests the conversion of a list of InstantTimeValueDataModels to a list of domain Values.
     */
    @Test
    void successConversionToDomainList() {
        List<InstantTimeValueDataModel> dataModels = List.of(InstantTimeValueDataModel);

        Iterable<Value> result = mapperInstantTimeValueDataModel.toDomainList(factoryInstantTimeValue, dataModels);

        assertEquals(1, ((List<Value>) result).size());
        assertTrue(InstantTimeValue.isSameAs(((List<Value>) result).get(0)));
    }

    /**
     * Tests the conversion to domain Value list with null InstantTimeValueDataModels.
     */
    @Test
    void successConversionToDomainListWithNullDevice() {
        InstantTimeValueDataModel nullInstantTimeValue = null;
        List<InstantTimeValueDataModel> dataModels = new ArrayList<>();
        dataModels.add(nullInstantTimeValue);
        dataModels.add(nullInstantTimeValue);

        Iterable<Value> result = mapperInstantTimeValueDataModel.toDomainList(factoryInstantTimeValue, dataModels);

        assertTrue(((List<Value>) result).isEmpty());
    }

    /**
     * Tests the conversion of a InstantTimeValueDataModel list to domain Value list when the list of data models is null.
     */
    @Test
    void failConversionToDomainListNullListOfDataModels() {
        List<InstantTimeValueDataModel> dataModels = null; //provided iterable of data models is null
        Iterable<Value> result = mapperInstantTimeValueDataModel.toDomainList(factoryInstantTimeValue, dataModels);
        assertNull(result);
    }

    /**
     * Tests the conversion of InstantTimeValueDataModel list to domain Value list when the factory is null.
     */
    @Test
    void failConversionToDomainListNullFactory() {
        List<InstantTimeValueDataModel> InstantTimeValueDataModels = List.of(InstantTimeValueDataModel);
        Iterable<Value> result = mapperInstantTimeValueDataModel.toDomainList(null, InstantTimeValueDataModels);
        assertNull(result);
    }

}