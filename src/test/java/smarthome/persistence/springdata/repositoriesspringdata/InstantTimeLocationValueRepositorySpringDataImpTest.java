package smarthome.persistence.springdata.repositoriesspringdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.value.ImpFactoryInstantTimeLocationValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.jpa.datamodel.InstantTimeLocationValueDataModel;
import smarthome.persistence.jpa.datamodel.MapperInstantTimeLocationValueDataModel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest (classes = {InstantTimeLocationValueRepositorySpringDataImp.class})
class InstantTimeLocationValueRepositorySpringDataImpTest {

    /**
     * Mocked bean of InstantTimeLocationValueRepositorySpringData
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    InstantTimeLocationValueRepositorySpringData instantTimeLocationValueRepositorySpringData;

    /**
     * Mocked bean of ImpFactoryInstantTimeLocationValue
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    ImpFactoryInstantTimeLocationValue factoryInstantTimeLocationValue;

    /**
     * Mocked bean of MapperInstantTimeLocationValueDataModel
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    MapperInstantTimeLocationValueDataModel mapperInstantTimeLocationValueDataModel;

    /**
     * Class under test where mocked beans (attributes of the class) should be injected by Spring into.
     */
    @InjectMocks
    InstantTimeLocationValueRepositorySpringDataImp instantTimeLocationValueRepositorySpringDataImp;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /**
     * Successfully retrieve Value entity that belongs to a given SensorID.
     * Retrieved entity list should be 1 entity long.
     */
    @Test
    void successfullyFindBySensorID () {
        //Create doubles to be used by method under test
        SensorID sensorIDDouble = mock(SensorID.class);
        String sensorIDString = "sensorID";
        when(sensorIDDouble.toString()).thenReturn(sensorIDString);

        //Create doubles to be returned by injected mockedBeans.
        InstantTimeLocationValueDataModel valueDataModelDouble = mock(InstantTimeLocationValueDataModel.class);
        Value valueDouble = mock(Value.class);

        //Set behaviour of mocked beans
        when(instantTimeLocationValueRepositorySpringData
                .findBySensorID(sensorIDDouble.toString()))
                .thenReturn(List.of(valueDataModelDouble));

        when(mapperInstantTimeLocationValueDataModel
                .toDomainList(factoryInstantTimeLocationValue,
                        List.of(valueDataModelDouble))).thenReturn(List.of(valueDouble));

        List<Value> values = instantTimeLocationValueRepositorySpringDataImp.findBySensorId(sensorIDDouble);
        assertEquals(1, values.size());

    }

    /**
     * Successfully retrieve all values for a given SensorID in a given period.
     * Simulated DB should contain 1 value within period and 1 outside.
     */
    @Test
    void successfullyFindAllValuesForSensorIDInPeriod () {

        Timestamp start = new Timestamp(0);
        Timestamp end = new Timestamp(10000);

        //Create doubles to be used by method under test
        //Value in period
        SensorID sensorIDDouble = mock(SensorID.class);
        String sensorIDString = "sensorID1";
        when(sensorIDDouble.toString()).thenReturn(sensorIDString);

        //Create doubles to be returned by injected mockedBeans.
        InstantTimeLocationValueDataModel valueDataModelDouble = mock(InstantTimeLocationValueDataModel.class);
        Value valueDouble = mock(Value.class);

        when(instantTimeLocationValueRepositorySpringData
                .findBySensorIDAndInstantTimeBetween(sensorIDDouble.toString(), start, end))
                .thenReturn(List.of(valueDataModelDouble));
        when(mapperInstantTimeLocationValueDataModel.toDomainList(factoryInstantTimeLocationValue, List.of(valueDataModelDouble))).thenReturn(List.of(valueDouble));



        List<Value> values = instantTimeLocationValueRepositorySpringDataImp.findBySensorIdBetweenPeriodOfTime(sensorIDDouble, start, end);
        assertEquals(1, values.size());

    }

    /**
     * Successfully find by ValueID.
     * Find entity in simulated database with ValueID equal to the one requested
     */
    @Test
    void successfullyFindValueByIDInDatabase () {
        //Create doubles to be used by method under test
        ValueID valueIDDouble = mock(ValueID.class);
        String valueIDDoubleString = "valueID";
        when(valueIDDouble.toString()).thenReturn(valueIDDoubleString);

        //Create doubles to be returned by injected mockedBeans.
        InstantTimeLocationValueDataModel valueDataModelDouble = mock(InstantTimeLocationValueDataModel.class);
        when(instantTimeLocationValueRepositorySpringData.findById(valueIDDoubleString)).thenReturn(Optional.of(valueDataModelDouble));
        Value foundValue = mock(Value.class);
        when(mapperInstantTimeLocationValueDataModel.toDomain(factoryInstantTimeLocationValue, valueDataModelDouble)).thenReturn(foundValue);

        Optional<Value> value = instantTimeLocationValueRepositorySpringDataImp.findEntityByID(valueIDDouble);
        assertTrue(value.isPresent());
    }

    /**
     * Fail to find by ValueID.
     * Can't retrieve from database so returned optional should be empty.
     */
    @Test
    void failToFindByValueIDInDatabase () {
        //Create doubles to be used by method under test
        ValueID valueIDDouble = mock(ValueID.class);
        String valueIDDoubleString = "valueID";
        when(valueIDDouble.toString()).thenReturn(valueIDDoubleString);

        //Create doubles to be returned by injected mockedBeans.
        when(instantTimeLocationValueRepositorySpringData.findById(valueIDDoubleString)).thenReturn(Optional.empty());

        Optional<Value> value = instantTimeLocationValueRepositorySpringDataImp.findEntityByID(valueIDDouble);
        assertTrue(value.isEmpty());
    }

    /**
     * Successfully retrieve all entities from persistence.
     * Retrieved entity list should be 1 entity long.
     */
    @Test
    void successfullyFindAllEntities () {

        //Create doubles to be returned by injected mockedBeans.
        InstantTimeLocationValueDataModel valueDataModelDouble = mock(InstantTimeLocationValueDataModel.class);
        Value valueDouble = mock(Value.class);

        //Set behaviour of mocked beans
        when(instantTimeLocationValueRepositorySpringData.findAll()).thenReturn(List.of(valueDataModelDouble));
        when(mapperInstantTimeLocationValueDataModel.toDomainList(factoryInstantTimeLocationValue, List.of(valueDataModelDouble))).thenReturn(List.of(valueDouble));

        Iterable<Value> values = instantTimeLocationValueRepositorySpringDataImp.findAllEntities();
        assertEquals(1, ((List<Value>) values).size());
    }

    /**
     * Confirm that an entity is present within the database
     */
    @Test
    void confirmThatEntityExistsInDB () {
        //Create doubles to be used by method under test
        ValueID valueIDDouble = mock(ValueID.class);
        String valueIDDoubleString = "valueID";
        when(valueIDDouble.toString()).thenReturn(valueIDDoubleString);

        //Set behaviour of mocked beans
        when(instantTimeLocationValueRepositorySpringData.existsById(valueIDDouble.toString())).thenReturn(true);

        boolean exists = instantTimeLocationValueRepositorySpringDataImp.containsEntityByID(valueIDDouble);
        assertTrue(exists);
    }

}