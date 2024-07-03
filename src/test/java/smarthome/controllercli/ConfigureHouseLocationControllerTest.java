package smarthome.controllercli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.house.ImpFactoryHouse;
import smarthome.domain.repository.*;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.*;
import smarthome.persistence.jpa.datamodel.HouseDataModel;
import smarthome.persistence.jpa.datamodel.MapperHouseDataModel;
import smarthome.persistence.jpa.repositoriesjpa.HouseRepositoryJPAImp;
import smarthome.persistence.repositoriesmem.HouseRepositoryMem;
import smarthome.persistence.springdata.repositoriesspringdata.HouseRepositorySpringData;
import smarthome.persistence.springdata.repositoriesspringdata.HouseRepositorySpringDataImp;
import smarthome.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for the {@code ConfigureHouseLocationController} class.
 */
class ConfigureHouseLocationControllerTest {

    /**
     * The controller to be tested.
     */
    private ConfigureHouseLocationController configureLocationController;
    private ConfigureHouseLocationController configureLocationControllerWithLocation;

    /**
     * Map of HouseID and House objects.
     */
    private final Map<HouseID, House> map = new HashMap<>();
    private final Map<HouseID, House> mapWithLocation = new HashMap<>();


    /**
     * SetUp that initializes the houseNoLocation and houseWithLocation objects and the configureLocationController and configureLocationControllerWithLocation objects, as well as the primitives used for the several tests performed.
     */
    @BeforeEach
    void setUp() {
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 41.14961;
        double longitude = -8.61099;

        GPSCode gpsCode = new GPSCode(latitude, longitude);
        Address address = new Address(street, doorNumber, zipCode, city, country);

        Location location = new Location(address, gpsCode);

        FactoryHouse factoryHouse = new ImpFactoryHouse();

        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        map.put(houseID, house);
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem(map);
        HouseService houseService = new HouseService(houseRepositoryMem, factoryHouse);
        configureLocationController = new ConfigureHouseLocationController(houseService, houseID);

        House houseWithLocation = factoryHouse.createHouseWithLocation(location);
        HouseID houseIDWithLocation = houseWithLocation.identity();
        mapWithLocation.put(houseIDWithLocation, houseWithLocation);
        HouseRepositoryMem houseRepositoryMem1 = new HouseRepositoryMem(mapWithLocation);
        HouseService houseService1 = new HouseService(houseRepositoryMem1, factoryHouse);
        configureLocationControllerWithLocation = new ConfigureHouseLocationController(houseService1, houseIDWithLocation);

    }

    /**
     * Successful test of the configureHouseLocation method, using the House without location.
     * Return HouseDTO if the house location was successfully configured.
     */
    @Test
    void successConfigureHouseLocationWithoutAnInitialLocation() {
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 41.14961;
        double longitude = -8.61099;

        LocationDTO locationDTO = new LocationDTO(street, doorNumber, zipCode, city, country, latitude, longitude);

        HouseDTO result = configureLocationController.configureHouseLocation(locationDTO);

        assertNotNull(result);
    }

    /**
     * Successful test of the configureHouseLocation method, using the House with location.
     * Return HouseDTO if the house location was successfully configured.
     */
    @Test
    void successConfigureHouseLocationWithInitialLocation() {
        String street = "Rua Firmeza";
        String doorNumber = "452";
        String zipCode = "4586-120";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 42.14961;
        double longitude = -9.61099;

        LocationDTO locationDTO = new LocationDTO(street, doorNumber, zipCode, city, country, latitude, longitude);

        HouseDTO result = configureLocationControllerWithLocation.configureHouseLocation(locationDTO);

        assertNotNull(result);
    }

    /**
     * Failed test of the configureHouseLocation method, using the House without location.
     * Return null since the street is empty.
     */
    @Test
    void failedConfigureHouseLocationEmptyStreet() {
        String street = "";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 41.14961;
        double longitude = -8.61099;

        LocationDTO locationDTO = new LocationDTO(street, doorNumber, zipCode, city, country, latitude, longitude);

        HouseDTO result = configureLocationController.configureHouseLocation(locationDTO);

        assertNull(result);
    }

    /**
     * Failed test of the configureHouseLocation method, using the House without location.
     * Return null since the latitude is invalid.
     */
    @Test
    void failedConfiguredHouseLocationInvalidLatitude() {
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 91.14961;
        double longitude = -8.61099;

        LocationDTO locationDTO = new LocationDTO(street, doorNumber, zipCode, city, country, latitude, longitude);

        HouseDTO result = configureLocationController.configureHouseLocation(locationDTO);

        assertNull(result);
    }

    //---------------------- INTEGRATION TESTS WITH REPOSITORIES JPA ---------------------------------------------------

    /**
     * Test the successful configuration of a house location using the JPA repository.
     */
    @Test
    void successfullyConfigureHouseLocationUseJPARepository() {
        // Create double for EntityManager to substitute the database dependency
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.getTransaction()).thenReturn(transaction);

        // Create Factory, Repository ans Services
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        HouseRepository houseRepositoryJPA = new HouseRepositoryJPAImp(factoryHouse, manager);
        HouseService houseService = new HouseService(houseRepositoryJPA, factoryHouse);

        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007",
                "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));


        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(manager.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        HouseID houseIDCreated = houseService.createAndSaveHouseWithLocation(location);

        // Set behaviour when retrieving house from repository - findEntityByID() in HouseRepository:
        // use factory to create an object that is equal to the one created by services
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseIDCreated, location);
        HouseDataModel houseDataModel = new HouseDataModel(house);
        when(manager.find(HouseDataModel.class, houseIDCreated.toString())).thenReturn(houseDataModel);

        // Instantiate controller to be tested
        ConfigureHouseLocationController configureHouseLocationController = new ConfigureHouseLocationController(
                houseService, houseIDCreated);

        // DTO of the new location to be configured
        LocationDTO locationDTO = new LocationDTO("Rua do Ouro", "27", "4000-007",
                "Porto", "Portugal", 41.178553, -8.608035);

        // Act
        HouseDTO houseDTO = configureHouseLocationController.configureHouseLocation(locationDTO);

        // Assert
        assertEquals(houseIDCreated.toString(), houseDTO.houseID);
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES SPRING --------------------------------------------------

    /**
     * Test the successful configuration of a house location using the Spring Data repository.
     */
    @Test
    void successfullyConfigureHouseLocationUseSpringDataRepository() {
        // Instantiation of Mappers
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();

        // Instantiation of Factory
        FactoryHouse factoryHouse = new ImpFactoryHouse();

        // Create repository
        HouseRepositorySpringData houseRepositorySpringData = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepositorySpring = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringData, mapperHouseDataModel);

        // Instantiation of service
        HouseService houseService = new HouseService(houseRepositorySpring, factoryHouse);

        // Create House
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007",
                "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));

        HouseID houseIDCreated = houseService.createAndSaveHouseWithLocation(location);

        // Set behaviour when retrieving house from repository - findEntityByID() in HouseRepository:
        // use factory to create an object that is equal to the one created by services
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseIDCreated, location);
        // set the findByID method to return the respective datamodel
        when(houseRepositorySpringData.findById(
                houseIDCreated.toString())).thenReturn(Optional.of(new HouseDataModel(house)));

        // Instantiate controller to be tested
        ConfigureHouseLocationController configureHouseLocationController = new ConfigureHouseLocationController(
                houseService, houseIDCreated);

        // DTO of the new location to be configured
        LocationDTO locationDTO = new LocationDTO("Rua do Ouro", "27", "4000-007",
                "Porto", "Portugal", 41.178553, -8.608035);

        // Act
        HouseDTO houseDTO = configureHouseLocationController.configureHouseLocation(locationDTO);

        // Assert
        assertEquals(houseIDCreated.toString(), houseDTO.houseID);
    }


}