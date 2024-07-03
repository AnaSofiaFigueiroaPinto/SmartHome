package smarthome.persistence.springdata.repositoriesspringdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.value.ImpFactoryInstantTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.jpa.datamodel.InstantTimeValueDataModel;
import smarthome.persistence.jpa.datamodel.MapperInstantTimeValueDataModel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest (classes = {InstantTimeValueRepositorySpringDataImp.class})
class InstantTimeValueTestRepositorySpringDataImpTest {

    /**
     * Mocked bean of InstantTimeValueRepositorySpringData
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    InstantTimeValueRepositorySpringData repositorySpringData;

    /**
     * Mocked bean of ImpFactoryInstantTimeValue
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    ImpFactoryInstantTimeValue factorySpringData;

    /**
     * Mocked bean of MapperInstantTimeValueDataModel
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    MapperInstantTimeValueDataModel mapperInstantTimeValueDataModel;

    /**
     * Class under test where mocked beans (attributes of the class) should be injected by Spring into.
     */
    @InjectMocks
    InstantTimeValueRepositorySpringDataImp instantTimeValueRepositorySpringDataImp;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    void setUp () {
        MockitoAnnotations.openMocks(this);
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

        InstantTimeValueDataModel instantTimeValueDataModelDouble = mock(InstantTimeValueDataModel.class);
        when(repositorySpringData.findById(valueIDDoubleString)).thenReturn(Optional.of(instantTimeValueDataModelDouble));
        Value foundValue = mock(Value.class);
        when(mapperInstantTimeValueDataModel.toDomain(factorySpringData, instantTimeValueDataModelDouble)).thenReturn(foundValue);

        Optional<Value> value = instantTimeValueRepositorySpringDataImp.findEntityByID(valueIDDouble);
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

        when(repositorySpringData.findById(valueIDDoubleString)).thenReturn(Optional.empty());

        Optional<Value> value = instantTimeValueRepositorySpringDataImp.findEntityByID(valueIDDouble);
        assertTrue(value.isEmpty());
    }

    /**
     * Successfully retrieve all entities from persistance.
     * Retrieved entity list should be 1 entity long.
     */
    @Test
    void successfullyFindAllEntities () {
        InstantTimeValueDataModel valueDataModelDouble = mock(InstantTimeValueDataModel.class);
        Value valueDouble = mock(Value.class);

        when(repositorySpringData.findAll()).thenReturn(List.of(valueDataModelDouble));
        when(mapperInstantTimeValueDataModel.toDomainList(factorySpringData, List.of(valueDataModelDouble))).thenReturn(List.of(valueDouble));

        Iterable<Value> values = instantTimeValueRepositorySpringDataImp.findAllEntities();
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

        when(repositorySpringData.existsById(valueIDDoubleString)).thenReturn(true);

        boolean isEntInDB = instantTimeValueRepositorySpringDataImp.containsEntityByID(valueIDDouble);
        assertTrue(isEntInDB);
    }

    /**
     * Successfully retrieve Value entity that belongs to a given SensorID.
     * Retrieved entity list should be 1 entity long.
     */
    @Test
    void successfullyFindBySensorID () {
        InstantTimeValueDataModel valueDataModelDouble = mock(InstantTimeValueDataModel.class);
        Value valueDouble = mock(Value.class);

        SensorID sensorIDDouble = mock(SensorID.class);
        String sensorIDString = "sensorID";
        when(sensorIDDouble.toString()).thenReturn(sensorIDString);

        when(repositorySpringData.findBySensorID(sensorIDDouble.toString())).thenReturn(List.of(valueDataModelDouble));
        when(mapperInstantTimeValueDataModel.toDomainList(factorySpringData, List.of(valueDataModelDouble))).thenReturn(List.of(valueDouble));

        List<Value> values = instantTimeValueRepositorySpringDataImp.findBySensorId(sensorIDDouble);
        assertEquals(1, values.size());
    }

    /**
     * Successfully retrieve all values for a given SensorID in a given period.
     * Simulated DB should contain 1 value within period and 1 outside.
     */
    @Test
    void successfullyFindAllValuesForSensorIDInPeriod () {
        Timestamp startDouble = new Timestamp(0);
        Timestamp endDouble = new Timestamp(10000);

        SensorID sensorIDDouble = mock(SensorID.class);
        String sensorIDDoubleAsString = "SensorID1";
        when(sensorIDDouble.toString()).thenReturn(sensorIDDoubleAsString);

        InstantTimeValueDataModel valueDataModelDouble1 = mock(InstantTimeValueDataModel.class);
        Value valueDouble1 = mock(Value.class);

        when(repositorySpringData.findBySensorIDAndAndInstantTimeBetween(
                sensorIDDouble.toString(), startDouble, endDouble)).thenReturn(List.of(valueDataModelDouble1));
        when(mapperInstantTimeValueDataModel.toDomainList(factorySpringData, List.of(valueDataModelDouble1))).thenReturn(List.of(valueDouble1));


        List<Value> values = instantTimeValueRepositorySpringDataImp.findBySensorIdBetweenPeriodOfTime(sensorIDDouble, startDouble, endDouble);
        assertEquals(1, values.size());
    }

    /**
     * Validate method that retrieves List of Value type object from repo that belong to a specific SensorID.
     * Returns the last value recorded with the SensorID specified.
     */
    @Test
    void successfullyRetrieveLastValueBySensorID() {
        SensorID sensorIDDouble = mock(SensorID.class);
        String sensorIDString = "sensorID1";
        when(sensorIDDouble.toString()).thenReturn(sensorIDString);

        InstantTimeValueDataModel valueDataModelDouble1 = mock(InstantTimeValueDataModel.class);
        when(valueDataModelDouble1.getInstantTime()).thenReturn(Timestamp.valueOf("2024-04-17 10:00:00.0"));
        when(valueDataModelDouble1.getSensorID()).thenReturn(sensorIDString);

        Value valueDouble1 = mock(Value.class);

        when(repositorySpringData.findFirstBySensorIDOrderByInstantTimeDesc("sensorID1")).thenReturn(Optional.of(valueDataModelDouble1));
        when(mapperInstantTimeValueDataModel.toDomain(factorySpringData, valueDataModelDouble1)).thenReturn(valueDouble1);

        Value resultValue = instantTimeValueRepositorySpringDataImp.findLastValueRecorded(sensorIDDouble);
        assertEquals(valueDouble1, resultValue);
    }
}