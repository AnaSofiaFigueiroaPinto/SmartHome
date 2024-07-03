package smarthome.persistence.repositoriesmem;

import org.mockito.MockedConstruction;
import smarthome.domain.sensorfunctionality.FactorySensorFunctionality;
import smarthome.domain.sensorfunctionality.SensorFunctionality;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.persistence.jpa.datamodel.MapperSensorFunctionalityPersistence;
import smarthome.util.ConfigScraper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SensorFunctionalityRepositoryMemTest {
    private FactorySensorFunctionality factory;
    private SensorFunctionality doubleSensorFunctionality;
    private SensorFunctionalityID doubleSensorFunctionalityID;

    /**
     *  setUp for all tests, creating a double for SensorFunctionality and SensorFunctionalityID and double
     *  for FactorySensorFunctionality to be able to load the config file
     */
    @BeforeEach
    void setUp() {
        doubleSensorFunctionality = mock(SensorFunctionality.class);
        doubleSensorFunctionalityID = mock(SensorFunctionalityID.class);
        factory = mock(FactorySensorFunctionality.class);
        when(factory.createSensorFunctionality(any(SensorFunctionalityID.class))).thenReturn(doubleSensorFunctionality);
        when(doubleSensorFunctionality.identity()).thenReturn(doubleSensorFunctionalityID);
    }

    /**
     * Success construction of SensorFunctionalityRepository with valid FactorySensorFunctionality parameter
     */
    @Test
    void constructorValidParameters () {
        List<String> stringList = List.of("item1","item2");

        try (MockedConstruction<MapperSensorFunctionalityPersistence> mocksOfMapper = mockConstruction(MapperSensorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(doubleSensorFunctionality);
        })) {
            try (MockedConstruction<ConfigScraper> mocksOfConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadSensorFunctionalityList()).thenReturn(stringList);
            })) {
                assertDoesNotThrow(() -> new SensorFunctionalityRepositoryMem(factory));
            }
        }
    }

    /**
     * Fail construction of SensorFunctionalityRepository with invalid (null) FactorySensorFunctionality parameter
     */
    @Test
    void constructorInvalidParametersNulFactory () {
        List<String> stringList = List.of("item1","item2");

        try (MockedConstruction<MapperSensorFunctionalityPersistence> mocksOfMapper = mockConstruction(MapperSensorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(doubleSensorFunctionality);
        })) {
            try (MockedConstruction<ConfigScraper> mocksOfConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadSensorFunctionalityList()).thenReturn(stringList);
            })) {
                assertThrows(IllegalArgumentException.class, () -> new SensorFunctionalityRepositoryMem(null));
            }
        }
    }

    /**
     * Success retrieval of all entities in repository
     */
    @Test
    void retrieveAllEntitiesIn () throws IOException {
        List<String> stringList = List.of("item1","item2");

        try (MockedConstruction<MapperSensorFunctionalityPersistence> mocksOfMapper = mockConstruction(MapperSensorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(doubleSensorFunctionality);
        })) {
            try (MockedConstruction<ConfigScraper> mocksOfConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadSensorFunctionalityList()).thenReturn(stringList);
            })) {
                SensorFunctionalityRepositoryMem repo = new SensorFunctionalityRepositoryMem(factory);
                Iterable<SensorFunctionality> sensorFunctionalities = repo.findAllEntities();
                assertNotNull(sensorFunctionalities);
            }
        }
    }

    /**
     * Successfully find of entity with an existing ID.
     * From the given optional check that it is not empty and therefore contains a SensorFunctionality object
     */
    @Test
    void retrieveExistingEntityWithValidID () throws IOException {
        List<String> stringList = List.of("item1","item2");

        try (MockedConstruction<MapperSensorFunctionalityPersistence> mocksOfMapper = mockConstruction(MapperSensorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(doubleSensorFunctionality);
        })) {
            try (MockedConstruction<ConfigScraper> mocksOfConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadSensorFunctionalityList()).thenReturn(stringList);
            })) {
                SensorFunctionalityRepositoryMem repo = new SensorFunctionalityRepositoryMem(factory);
                Optional<SensorFunctionality> optSensorFunc = repo.findEntityByID(doubleSensorFunctionalityID);
                assertTrue(optSensorFunc.isPresent());
            }
        }
    }

    /**
     * Fail to find an entity with a non-existing ID.
     * From the given optional retrieve check that it is empty and therefore does not contain a SensorFunctionality object
     */
    @Test
    void failToRetrieveEntityWithInvalidID () throws IOException {
        List<String> stringList = List.of("item1","item2");

        try (MockedConstruction<MapperSensorFunctionalityPersistence> mocksOfMapper = mockConstruction(MapperSensorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(doubleSensorFunctionality);
        })) {
            try (MockedConstruction<ConfigScraper> mocksOfConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadSensorFunctionalityList()).thenReturn(stringList);
            })) {
                SensorFunctionalityRepositoryMem repo = new SensorFunctionalityRepositoryMem(factory);
                SensorFunctionalityID fakeSensorID = mock(SensorFunctionalityID.class);
                Optional<SensorFunctionality> optSensorFunc = repo.findEntityByID(fakeSensorID);
                assertTrue(optSensorFunc.isEmpty());
            }
        }
    }

    /**
     * Verifies successfully that an entity exists in repo when searching with valid ID
     */
    @Test
    void checkContainsEntityWithValidID () throws IOException {
        List<String> stringList = List.of("item1","item2");

        try (MockedConstruction<MapperSensorFunctionalityPersistence> mocksOfMapper = mockConstruction(MapperSensorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(doubleSensorFunctionality);
        })) {
            try (MockedConstruction<ConfigScraper> mocksOfConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadSensorFunctionalityList()).thenReturn(stringList);
            })) {
                SensorFunctionalityRepositoryMem repo = new SensorFunctionalityRepositoryMem(factory);
                assertTrue(repo.containsEntityByID(doubleSensorFunctionalityID));
            }
        }
    }

    /**
     * Fails to verify existence that an entity exists in repo when searching with valid ID
     */
    @Test
    void failsToFindEntityWithInvalidID () throws IOException {
        List<String> stringList = List.of("item1","item2");

        try (MockedConstruction<MapperSensorFunctionalityPersistence> mocksOfMapper = mockConstruction(MapperSensorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(doubleSensorFunctionality);
        })) {
            try (MockedConstruction<ConfigScraper> mocksOfConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadSensorFunctionalityList()).thenReturn(stringList);
            })) {
                SensorFunctionalityRepositoryMem repo = new SensorFunctionalityRepositoryMem(factory);
                SensorFunctionalityID fakeSensorID = mock(SensorFunctionalityID.class);
                assertFalse(repo.containsEntityByID(fakeSensorID));
            }
        }
    }


}