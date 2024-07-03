package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.value.ImpFactoryInstantTimeLocationValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link MapperInstantTimeLocationValueDataModel} class.
 */
class MapperInstantTimeLocationValueDataModelTest {
    private InstantTimeLocationValueDataModel instantTimeLocationValueDataModel;
    private ImpFactoryInstantTimeLocationValue factoryInstantTimeLocationValue;
    private MapperInstantTimeLocationValueDataModel mapperInstantTimeLocationValueDataModel;
    private Value instantTimeLocationValue;

    /**
     * Sets up the test environment before each test case in {@link MapperInstantTimeLocationValueDataModel} class.
     */
    @BeforeEach
    void setUp() {
        factoryInstantTimeLocationValue = new ImpFactoryInstantTimeLocationValue();
        SensorID sensorID = new SensorID("DewPointSensor");
        Reading reading = new Reading("600", "W");
        Timestamp instantTimeReading = Timestamp.valueOf("2022-01-01 00:00:00.0");
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);

        instantTimeLocationValue = factoryInstantTimeLocationValue.createValue(sensorID, reading, instantTimeReading, gpsCode);

        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel(instantTimeLocationValue);

        mapperInstantTimeLocationValueDataModel = new MapperInstantTimeLocationValueDataModel();
    }

    /**
     * Tests the conversion of InstantTimeLocationValueDataModel to domain Value.
     */
    @Test
    void successConversionToDomain() {
        Value result = mapperInstantTimeLocationValueDataModel.toDomain(factoryInstantTimeLocationValue, instantTimeLocationValueDataModel);

        assertNotNull(result);
        assertTrue(instantTimeLocationValue.isSameAs(result));
    }

    /**
     * Tests the conversion of InstantTimeLocationValueDataModel to domain Value when the factory is null.
     */
    @Test
    void failConversionToDomainNullFactory() {
        Value result = mapperInstantTimeLocationValueDataModel.toDomain(null, instantTimeLocationValueDataModel);

        assertNull(result);
    }

    /**
     * Tests the conversion of a list of InstantTimeLocationValueDataModels to a list of domain Values.
     */
    @Test
    void successConversionToDomainList() {
        List<InstantTimeLocationValueDataModel> dataModels = List.of(instantTimeLocationValueDataModel);

        Iterable<Value> result = mapperInstantTimeLocationValueDataModel.toDomainList(factoryInstantTimeLocationValue, dataModels);

        assertEquals(1, ((List<Value>) result).size());
        assertTrue(instantTimeLocationValue.isSameAs(((List<Value>) result).get(0)));
    }

    /**
     * Tests the conversion to domain Value list with null InstantTimeLocationValueDataModels.
     */
    @Test
    void successConversionToDomainListWithNullDevice() {
        InstantTimeLocationValueDataModel nullInstantTimeLocationValue = null;
        List<InstantTimeLocationValueDataModel> dataModels = new ArrayList<>();
        dataModels.add(nullInstantTimeLocationValue);
        dataModels.add(nullInstantTimeLocationValue);

        Iterable<Value> result = mapperInstantTimeLocationValueDataModel.toDomainList(factoryInstantTimeLocationValue, dataModels);

        assertTrue(((List<Value>) result).isEmpty());
    }

    /**
     * Tests the conversion of a InstantTimeLocationValueDataModel list to domain Value list when the list of data models is null.
     */
    @Test
    void failConversionToDomainListNullListOfDataModels() {
        List<InstantTimeLocationValueDataModel> dataModels = null; //provided iterable of data models is null
        Iterable<Value> result = mapperInstantTimeLocationValueDataModel.toDomainList(factoryInstantTimeLocationValue, dataModels);
        assertNull(result);
    }

    /**
     * Tests the conversion of InstantTimeLocationValueDataModel list to domain Value list when the factory is null.
     */
    @Test
    void failConversionToDomainListNullFactory() {
        List<InstantTimeLocationValueDataModel> InstantTimeLocationValueDataModels = List.of(instantTimeLocationValueDataModel);
        Iterable<Value> result = mapperInstantTimeLocationValueDataModel.toDomainList(null, InstantTimeLocationValueDataModels);
        assertNull(result);
    }

}