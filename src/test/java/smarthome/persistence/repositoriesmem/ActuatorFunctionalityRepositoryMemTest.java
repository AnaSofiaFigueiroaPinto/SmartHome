package smarthome.persistence.repositoriesmem;

import org.mockito.MockedConstruction;
import smarthome.domain.actuatorfunctionality.ActuatorFunctionality;
import smarthome.domain.actuatorfunctionality.FactoryActuatorFunctionality;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.persistence.jpa.datamodel.MapperActuatorFunctionalityPersistence;
import smarthome.util.ConfigScraper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ActuatorFunctionalityRepositoryMemTest {
    private FactoryActuatorFunctionality factory;
    private ActuatorFunctionality actuatorFunctionalityDouble;
    private ActuatorFunctionalityID actuatorFunctionalityIDDouble;

    /**
     *  setUp for all tests, creating a double for ActuatorFunctionality and ActuatorFunctionalityID and double
     *  for FactoryActuatorFunctionality to be able to load the config file
     */
    @BeforeEach
    void setUp() {
        factory = mock(FactoryActuatorFunctionality.class);
        actuatorFunctionalityDouble = mock(ActuatorFunctionality.class);
        actuatorFunctionalityIDDouble = mock(ActuatorFunctionalityID.class);
        when(factory.createActuatorFunctionality(any(ActuatorFunctionalityID.class))).thenReturn(actuatorFunctionalityDouble);
        when(actuatorFunctionalityDouble.identity()).thenReturn(actuatorFunctionalityIDDouble);
    }

    /**
     * Success construction of ActuatorFunctionalityRepository with valid FactoryActuatorFunctionality parameter
     */
    @Test
    void successConstructRepository() {
        List<String> actuatorFunctionalityIDList = List.of("Switch", "Dimmer", "Blinds", "Thermostat");

        try (MockedConstruction<ConfigScraper> mockedConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
            when(mock.loadActuatorFunctionalityList()).thenReturn(actuatorFunctionalityIDList);
        })) {
            assertDoesNotThrow(() -> new ActuatorFunctionalityRepositoryMem(factory));
        }
    }

    /**
     * Fail construction of ActuatorFunctionalityRepository with invalid (null) FactoryActuatorFunctionality parameter
     */
    @Test
    void failConstructRepository() {
        List<String> actuatorFunctionalityIDList = List.of("Switch", "Dimmer", "Blinds", "Thermostat");

        try (MockedConstruction<ConfigScraper> mockedConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
            when(mock.loadActuatorFunctionalityList()).thenReturn(actuatorFunctionalityIDList);
        })) {
            assertThrows(IllegalArgumentException.class, () -> new ActuatorFunctionalityRepositoryMem(null));
        }
    }

    /**
     * Successful retrieval of all entities in the repository
     */
    @Test
    void successRetrieveAllEntitiesInData() {
        List<String> actuatorFunctionalityIDList = List.of("Switch", "Dimmer", "Blinds", "Thermostat");
        try (MockedConstruction<MapperActuatorFunctionalityPersistence> mocked = mockConstruction(MapperActuatorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(actuatorFunctionalityDouble);
        })) {
            try (MockedConstruction<ConfigScraper> mockedConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadActuatorFunctionalityList()).thenReturn(actuatorFunctionalityIDList);
            })) {
                ActuatorFunctionalityRepositoryMem repo = new ActuatorFunctionalityRepositoryMem(factory);
                Iterable<ActuatorFunctionality> allEntities = repo.findAllEntities();
                assertNotNull(allEntities);
            }
        }
    }

    /**
     * Successfully retrieve an Actuator Functionality entity that based on its ID
     */

    @Test
    void successFindsActuatorFunctionalityByID() {
        List<String> actuatorFunctionalityIDList = List.of("Switch", "Dimmer", "Blinds", "Thermostat");
        try (MockedConstruction<MapperActuatorFunctionalityPersistence> mocked = mockConstruction(MapperActuatorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(actuatorFunctionalityDouble);
        })) {
            try (MockedConstruction<ConfigScraper> mockedConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadActuatorFunctionalityList()).thenReturn(actuatorFunctionalityIDList);
            })) {
                ActuatorFunctionalityRepositoryMem repo = new ActuatorFunctionalityRepositoryMem(factory);
                Optional<ActuatorFunctionality> retrievedActuatorFunc = repo.findEntityByID(actuatorFunctionalityIDDouble);
                assertTrue(retrievedActuatorFunc.isPresent());
            }
        }
    }

    /**
     * Fail to retrieve an Actuator Functionality entity with an ID not present in the repository
     */
    @Test
    void failFindActuatorFunctionalityWithSpecificId() {
        List<String> actuatorFunctionalityIDList = List.of("Switch", "Dimmer", "Blinds", "Thermostat");
        try (MockedConstruction<MapperActuatorFunctionalityPersistence> mocked = mockConstruction(MapperActuatorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(actuatorFunctionalityDouble);
        })) {
            try (MockedConstruction<ConfigScraper> mockedConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadActuatorFunctionalityList()).thenReturn(actuatorFunctionalityIDList);
            })) {
                ActuatorFunctionalityRepositoryMem repo = new ActuatorFunctionalityRepositoryMem(factory);
                ActuatorFunctionalityID actuatorFunctionalityIDDouble = mock(ActuatorFunctionalityID.class);
                Optional<ActuatorFunctionality> retrievedActuatorFunc = repo.findEntityByID(actuatorFunctionalityIDDouble);
                assertTrue(retrievedActuatorFunc.isEmpty());
            }
        }
    }

    /**
     * Successfully finds an Actuator Functionality entity in the repo based in a given ID
     */
    @Test
    void successContainsEntity() {
        List<String> actuatorFunctionalityIDList = List.of("Switch", "Dimmer", "Blinds", "Thermostat");
        try (MockedConstruction<MapperActuatorFunctionalityPersistence> mocked = mockConstruction(MapperActuatorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(actuatorFunctionalityDouble);
        })) {
            try (MockedConstruction<ConfigScraper> mockedConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadActuatorFunctionalityList()).thenReturn(actuatorFunctionalityIDList);
            })) {
                ActuatorFunctionalityRepositoryMem repo = new ActuatorFunctionalityRepositoryMem(factory);
                assertTrue(repo.containsEntityByID(actuatorFunctionalityIDDouble));
            }
        }
    }

    /**
     * Fail to find an Actuator Functionality entity in the repository based on a given ID
     */
    @Test
    void failContainsEntity() {
        List<String> actuatorFunctionalityIDList = List.of("Switch", "Dimmer", "Blinds", "Thermostat");
        try (MockedConstruction<MapperActuatorFunctionalityPersistence> mocked = mockConstruction(MapperActuatorFunctionalityPersistence.class, (mock, context) -> {
            when(mock.toDomain(anyString())).thenReturn(actuatorFunctionalityDouble);
        })) {
            try (MockedConstruction<ConfigScraper> mockedConfigScraper = mockConstruction(ConfigScraper.class, (mock, context) -> {
                when(mock.loadActuatorFunctionalityList()).thenReturn(actuatorFunctionalityIDList);
            })) {
                ActuatorFunctionalityRepositoryMem repo = new ActuatorFunctionalityRepositoryMem(factory);
                ActuatorFunctionalityID actuatorFunctionalityIDDouble = mock(ActuatorFunctionalityID.class);
                assertFalse(repo.containsEntityByID(actuatorFunctionalityIDDouble));
            }
        }
    }

}