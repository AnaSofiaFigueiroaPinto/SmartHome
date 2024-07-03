package smarthome.persistence.springdata.repositoriesspringdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.valueobjects.Address;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;
import smarthome.persistence.jpa.datamodel.HouseDataModel;
import smarthome.persistence.jpa.datamodel.MapperHouseDataModel;
import smarthome.util.exceptions.SingleHouseViolationException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {HouseRepositorySpringDataImp.class})
class HouseRepositorySpringDataImpTest {

    /**
     * The repository for accessing house data managed by Spring Data.
     */
    @MockBean
    private HouseRepositorySpringData houseRepositorySpringData;

    /**
     * Factory for creating House entities.
     */
    @MockBean
    private FactoryHouse factoryHouse;

    /**
     * The house data model for update.
     */
    @MockBean
    private HouseDataModel houseDataModelForUpdate;

    /**
     * Mapper House Data Model instance for managing persistence operations.
     */
    @MockBean
    private MapperHouseDataModel mapperHouseDataModel;

    @InjectMocks
    private HouseRepositorySpringDataImp houseRepositorySpringDataImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test the {@code save} method when provided with a valid house entity.
     */
    @Test
    void successSaveHouse() {
        // Create doubles
        House houseDouble = mock(House.class);
        HouseID houseIDDouble = mock(HouseID.class);
        GPSCode gpsCodeDouble = mock(GPSCode.class);
        Location locationDouble = mock(Location.class);
        Address addressDouble = mock(Address.class);

        // Set behaviour when creating data model
        when(houseDouble.identity()).thenReturn(houseIDDouble);
        when(houseDouble.getHouseLocation()).thenReturn(locationDouble);
        when(locationDouble.getGpsCode()).thenReturn(gpsCodeDouble);
        when(locationDouble.getAddress()).thenReturn(addressDouble);
        when(houseDouble.getHouseLocation().getGpsCode().getLatitude()).thenReturn(41.236);
        when(houseDouble.getHouseLocation().getGpsCode().getLongitude()).thenReturn(-8.672);
        when(houseDouble.getHouseLocation().getAddress().getStreet()).thenReturn("Rua do Amial");
        when(houseDouble.getHouseLocation().getAddress().getDoorNumber()).thenReturn("123");
        when(houseDouble.getHouseLocation().getAddress().getZipCode()).thenReturn("4200-123");
        when(houseDouble.getHouseLocation().getAddress().getCity()).thenReturn("Porto");
        when(houseDouble.getHouseLocation().getAddress().getCountry()).thenReturn("Portugal");

        when(houseRepositorySpringData.findAll()).thenReturn(emptyList());

        // Call method under test
        assertNotNull(houseRepositorySpringDataImp.save(houseDouble));
    }

    /**
     * Test the {@code save} method when one house already exists in the repository.
     */
    @Test
    void failSaveHouseWhenOneAlreadyExistsInRepo() {
        // Create doubles
        House houseDouble = mock(House.class);
        HouseID houseIDDouble = mock(HouseID.class);
        GPSCode gpsCodeDouble = mock(GPSCode.class);
        Location locationDouble = mock(Location.class);
        Address addressDouble = mock(Address.class);

        // Set behaviour when creating data model
        when(houseDouble.identity()).thenReturn(houseIDDouble);
        when(houseDouble.getHouseLocation()).thenReturn(locationDouble);
        when(locationDouble.getGpsCode()).thenReturn(gpsCodeDouble);
        when(locationDouble.getAddress()).thenReturn(addressDouble);
        when(houseDouble.getHouseLocation().getGpsCode().getLatitude()).thenReturn(41.236);
        when(houseDouble.getHouseLocation().getGpsCode().getLongitude()).thenReturn(-8.672);
        when(houseDouble.getHouseLocation().getAddress().getStreet()).thenReturn("Rua do Amial");
        when(houseDouble.getHouseLocation().getAddress().getDoorNumber()).thenReturn("123");
        when(houseDouble.getHouseLocation().getAddress().getZipCode()).thenReturn("4200-123");
        when(houseDouble.getHouseLocation().getAddress().getCity()).thenReturn("Porto");
        when(houseDouble.getHouseLocation().getAddress().getCountry()).thenReturn("Portugal");

        when(houseRepositorySpringData.findAll()).thenReturn(List.of(mock(HouseDataModel.class)));

        // Act + Assert
        assertThrows(SingleHouseViolationException.class, () -> houseRepositorySpringDataImp.save(houseDouble));
    }

    /**
     * Test the {@code update} method when provided with a valid house entity.
     */
    @Test
    void successUpdateHouse() {
        //Create doubles
        House houseDouble = mock(House.class);
        HouseID houseIDDouble = mock(HouseID.class);

        //Set behaviour
        when(houseDouble.identity()).thenReturn(houseIDDouble);
        when(houseRepositorySpringData.findById(houseDouble.identity().toString())).thenReturn(Optional.of(houseDataModelForUpdate));
        when(houseDataModelForUpdate.updateFromDomain(houseDouble)).thenReturn(true);

        // Act + Assert
        assertNotNull(houseRepositorySpringDataImp.update(houseDouble));
    }

    /**
     * Test the {@code update} method when the update from domain fails.
     */
    @Test
    void failUpdateHouseWhenUpdateFromDomainFails() {
        // Create doubles
        House houseDouble = mock(House.class);
        HouseID houseIDDouble = mock(HouseID.class);

        // Set behaviour
        when(houseDouble.identity()).thenReturn(houseIDDouble);
        when(houseRepositorySpringData.findById(houseDouble.identity().toString())).thenReturn(Optional.of(houseDataModelForUpdate));
        when(houseDataModelForUpdate.updateFromDomain(houseDouble)).thenReturn(false);

        // Act + Assert
        assertNull(houseRepositorySpringDataImp.update(houseDouble));
    }

    /**
     * Test the {@code update} method when the house does not exist in the repository.
     */
    @Test
    void failUpdateHouseWhenHouseDoesNotExistInRepo() {
        //Create doubles
        House houseDouble = mock(House.class);
        HouseID houseIDDouble = mock(HouseID.class);

        //Set behaviour
        when(houseDouble.identity()).thenReturn(houseIDDouble);
        when(houseRepositorySpringData.findById(houseDouble.identity().toString())).thenReturn(Optional.empty());

        // Act + Assert
        assertNull(houseRepositorySpringDataImp.update(houseDouble));
    }

    /**
     * Test the {@code updateReserved} method when provided with a valid house entity.
     */
    @Test
    void successUpdateReservedHouse() {
        // Create doubles
        House houseDouble = mock(House.class);

        // Set behaviour
        when(houseDataModelForUpdate.updateFromDomain(houseDouble)).thenReturn(true);

        // Act + Assert
        assertNotNull(houseRepositorySpringDataImp.updateReserved(houseDouble));
    }

    /**
     * Test the {@code updateReserved} method when the update from domain fails.
     */
    @Test
    void failUpdateReservedHouseWhenUpdateFromDomainFails() {
        // Create doubles
        House houseDouble = mock(House.class);

        // Set behaviour
        when(houseDataModelForUpdate.updateFromDomain(houseDouble)).thenReturn(false);

        // Act + Assert
        assertNull(houseRepositorySpringDataImp.updateReserved(houseDouble));
    }

    /**
     * Test the {@code findAllEntities} method to retrieve all house entities.
     */
    @Test
    void successFindAllEntities() {
        // Create doubles
        HouseDataModel houseDataModelDouble = mock(HouseDataModel.class);
        House houseDouble = mock(House.class);

        // Set behaviour
        when(houseRepositorySpringData.findAll()).thenReturn(List.of(houseDataModelDouble));
        when(mapperHouseDataModel.toDomainList(factoryHouse, List.of(houseDataModelDouble))).thenReturn(List.of(houseDouble));

        // Act
        Iterable<House> result = houseRepositorySpringDataImp.findAllEntities();

        // Assert
        assertEquals(1, ((List<House>) result).size());
    }

    /**
     * Test the {@code findEntityByID} method to retrieve a house entity by its ID.
     */
    @Test
    void sucessFindEntityByID() {
        House houseDouble = mock(House.class);
        HouseID houseIDDouble = mock(HouseID.class);
        HouseDataModel houseDataModelDouble = mock(HouseDataModel.class);

        // Set behaviour
        when(houseRepositorySpringData.findById(houseIDDouble.toString())).thenReturn(Optional.of(houseDataModelDouble));
        when(mapperHouseDataModel.toDomain(factoryHouse, houseDataModelDouble)).thenReturn(houseDouble);

        // Act
        Optional<House> result = houseRepositorySpringDataImp.findEntityByID(houseIDDouble);

        //Assert
        assertTrue(result.isPresent());
    }

    /**
     * Test the {@code findEntityByID} method when failing to find a house entity that does not exist in the repository.
     */
    @Test
    void failFindEntityByIDWhenHouseDoesNotExistInRepo() {
        HouseID houseIDDouble = mock(HouseID.class);

        // Set behaviour
        when(houseRepositorySpringData.findById(houseIDDouble.toString())).thenReturn(Optional.empty());

        // Act
        Optional<House> result = houseRepositorySpringDataImp.findEntityByID(houseIDDouble);

        //Assert
        assertFalse(result.isPresent());
    }

    /**
     * Test the {@code findEntityByIDAndReserve} method to retrieve a house entity by its ID and reserve it.
     */
    @Test
    void successFindEntityByIDAndReserve() {
        House houseDouble = mock(House.class);
        HouseID houseIDDouble = mock(HouseID.class);
        HouseDataModel houseDataModelDouble = mock(HouseDataModel.class);

        // Set behaviour
        when(houseRepositorySpringData.findById(houseIDDouble.toString())).thenReturn(Optional.of(houseDataModelDouble));
        when(mapperHouseDataModel.toDomain(factoryHouse, houseDataModelDouble)).thenReturn(houseDouble);

        // Act
        Optional<House> result = houseRepositorySpringDataImp.findEntityByIDAndReserve(houseIDDouble);

        //Assert
        assertTrue(result.isPresent());
    }

    /**
     * Test the {@code findEntityByIDAndReserve} method when failing to find a house entity that does not exist in the repository.
     */
    @Test
    void failFindEntityByIDAndReserveWhenHouseDoesNotExistInRepo() {
        HouseID houseIDDouble = mock(HouseID.class);

        // Set behaviour
        when(houseRepositorySpringData.findById(houseIDDouble.toString())).thenReturn(Optional.empty());

        // Act
        Optional<House> result = houseRepositorySpringDataImp.findEntityByIDAndReserve(houseIDDouble);

        //Assert
        assertFalse(result.isPresent());
    }

    /**
     * Test the {@code containsEntityByID} method to ensure it correctly identifies if a house entity exists by its ID.
     */
    @Test
    void successContainsEntityByID() {
        HouseID houseIDDouble = mock(HouseID.class);

        // Set behaviour
        when(houseRepositorySpringData.existsById(houseIDDouble.toString())).thenReturn(true);

        // Act
        assertTrue(houseRepositorySpringDataImp.containsEntityByID(houseIDDouble));
    }

}