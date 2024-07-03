package smarthome.persistence.springdata.repositoriesspringdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.value.ImpFactoryPeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.jpa.datamodel.MapperPeriodTimeValueDataModel;
import smarthome.persistence.jpa.datamodel.PeriodTimeValueDataModel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest (classes = {PeriodTimeValueRepositorySpringDataImp.class})
class PeriodTimeValueRepositorySpringDataImpTest {
    /**
     * Mocked bean of PeriodTimeValueRepositorySpringData
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    PeriodTimeValueRepositorySpringData periodTimeValueRepositorySpringData;

    /**
     * Mocked bean of ImpFactoryPeriodTimeValue
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    ImpFactoryPeriodTimeValue factoryValue;

    /**
     * Mocked bean of MapperPeriodTimeValueDataModel
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    MapperPeriodTimeValueDataModel mapperPeriodTimeValueDataModel;

    /**
     * Class under test where mocked beans (attributes of the class) should be injected by Spring into.
     */
    @InjectMocks
    PeriodTimeValueRepositorySpringDataImp periodTimeValueRepositorySpringDataImp;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    void setUp () {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Successfully retrieve Value entity that belongs to a given SensorID.
     * Retrieved entity list should be 1 entity long.
     */
    @Test
    void successfullyFindBySensorID () {
        SensorID sensorIDDouble = mock(SensorID.class);
        String sensorIDDoubleAsString = "sensorID";
        when(sensorIDDouble.toString()).thenReturn(sensorIDDoubleAsString);


        PeriodTimeValueDataModel valueDataModelDouble = mock(PeriodTimeValueDataModel.class);
        List<PeriodTimeValueDataModel> valueDataModelList = List.of(valueDataModelDouble);
        Value valueDouble = mock(Value.class);

        when(periodTimeValueRepositorySpringData.findBySensorID(sensorIDDoubleAsString)).thenReturn(valueDataModelList);
        when(mapperPeriodTimeValueDataModel.toDomainList(factoryValue, valueDataModelList)).thenReturn(List.of(valueDouble));

        List<Value> values = periodTimeValueRepositorySpringDataImp.findBySensorId(sensorIDDouble);
        assertEquals(1, values.size());
    }

    /**
     * Successfully retrieve all values for a given SensorID in a given period.
     * Simulated DB should contain 1 value within period that should be returned.
     */
    @Test
    void successfullyFindAllValuesForSensorIDInPeriod () {
        Timestamp startPeriod = new Timestamp(0);
        Timestamp endPeriod = new Timestamp(10000);

        SensorID sensorIDDouble1 = mock(SensorID.class);
        String sensorIDString1 = "sensorID1";
        when(sensorIDDouble1.toString()).thenReturn(sensorIDString1);

        PeriodTimeValueDataModel valueDataModelDouble = mock(PeriodTimeValueDataModel.class);
        List<PeriodTimeValueDataModel> valueDataModelList = List.of(valueDataModelDouble);
        Value valueDouble1 = mock(Value.class);

        when(periodTimeValueRepositorySpringData.findBySensorIDAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
                sensorIDDouble1.toString(), startPeriod, endPeriod)).thenReturn(valueDataModelList);
        when(mapperPeriodTimeValueDataModel.toDomainList(factoryValue, valueDataModelList)).thenReturn(List.of(valueDouble1));

        List<Value> values = periodTimeValueRepositorySpringDataImp.findBySensorIdBetweenPeriodOfTime(sensorIDDouble1, startPeriod, endPeriod);
        assertEquals(1, values.size());
    }

    /**
     * Successfully find by ValueID.
     * Find entity in simulated database with ValueID equal to the one requested
     */
    @Test
    void successfullyFindValueByIDInDatabase () {
        ValueID valueIDDouble = mock(ValueID.class);
        String valueIDDoubleString = "valueID";
        when(valueIDDouble.toString()).thenReturn(valueIDDoubleString);

        PeriodTimeValueDataModel valueDataModelDouble = mock(PeriodTimeValueDataModel.class);
        when(periodTimeValueRepositorySpringData.findById(valueIDDoubleString)).thenReturn(Optional.of(valueDataModelDouble));

        Value foundValue = mock(Value.class);
        when(mapperPeriodTimeValueDataModel.toDomain(factoryValue, valueDataModelDouble)).thenReturn(foundValue);

        Optional<Value> value = periodTimeValueRepositorySpringDataImp.findEntityByID(valueIDDouble);
        assertTrue(value.isPresent());
    }

    /**
     * Fail to find by ValueID.
     * Find entity in simulated database with ValueID different from the one requested
     */
    @Test
    void failToFindValueByIDInDatabase () {
        ValueID valueIDDouble = mock(ValueID.class);
        String valueIDDoubleString = "valueID";
        when(valueIDDouble.toString()).thenReturn(valueIDDoubleString);

        when(periodTimeValueRepositorySpringData.findById(valueIDDoubleString)).thenReturn(Optional.empty());

        Optional<Value> value = periodTimeValueRepositorySpringDataImp.findEntityByID(valueIDDouble);
        assertTrue(value.isEmpty());
    }

    /**
     * Successfully retrieve all entities from persistence.
     * Retrieved entity list should be 1 entity long.
     */
    @Test
    void successfullyFindAllEntities () {
        PeriodTimeValueDataModel valueDataModelDouble = mock(PeriodTimeValueDataModel.class);
        Value valueDouble = mock(Value.class);

        when(periodTimeValueRepositorySpringData.findAll()).thenReturn(List.of(valueDataModelDouble));
        when(mapperPeriodTimeValueDataModel.toDomainList(factoryValue, List.of(valueDataModelDouble))).thenReturn(List.of(valueDouble));

        Iterable<Value> values = periodTimeValueRepositorySpringDataImp.findAllEntities();
        assertEquals(1, ((List<Value>) values).size());
    }

    /**
     * Confirm that an entity is present within the database
     */
    @Test
    void confirmThatEntityExistsInDB () {
        ValueID valueIDDouble = mock(ValueID.class);
        String valueIDDoubleString = "valueID";
        when(valueIDDouble.toString()).thenReturn(valueIDDoubleString);

        when(periodTimeValueRepositorySpringData.existsById(valueIDDoubleString)).thenReturn(true);

        boolean isEntInDB = periodTimeValueRepositorySpringDataImp.containsEntityByID(valueIDDouble);
        assertTrue(isEntInDB);
    }
}