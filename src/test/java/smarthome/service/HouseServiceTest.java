package smarthome.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.valueobjects.Address;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;
import smarthome.service.internaldto.InternalHouseDTO;
import smarthome.util.exceptions.NoHouseInRepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for the {@code HouseService} class.
 */
@SpringBootTest(classes = {HouseService.class})
class HouseServiceTest {

    /**
     * {@code HouseService} class under test.
     */
    @Autowired
    private HouseService houseService;

    /**
     * Double of House repository to be used in tests.
     */
    @MockBean
    private HouseRepository houseRepository;

    /**
     * Double of the House factory to be used in tests.
     */
    @MockBean
    private FactoryHouse factoryHouse;

    private HouseID houseID;
    private House houseDouble;
    private Location locationDouble;
    private Address addressDouble;
    private GPSCode gpsCodeDouble;


    /**
     * SetUp method to initialize the variables for each test.
     */
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        houseID = new HouseID("5c43a801-87d3-4193-b62c-d629e891d682");

        addressDouble = mock(Address.class);

        gpsCodeDouble = mock(GPSCode.class);

        locationDouble = mock(Location.class);

        houseDouble = mock(House.class);
        when(houseDouble.getHouseLocation()).thenReturn(locationDouble);

        houseService = new HouseService(houseRepository, factoryHouse);
    }

    /**
     * Test the successful creation of a HouseService object.
     * verifies that no exception was thrown.
     */
    @Test
    void successHouseServiceCreation() {
        assertNotNull(houseService);
    }

    /**
     * Test the failed creation of a HouseService object.
     * Verifies that an IllegalArgumentException was thrown since the HouseRepository is null.
     */
    @Test
    void failedHouseServiceCreationHouseRepositoryNull() {
        assertThrows(IllegalArgumentException.class, () -> new HouseService(null, factoryHouse));
    }

    /**
     * Test the failed creation of a HouseService object.
     * Verifies that an IllegalArgumentException was thrown since the FactoryHouse is null.
     */
    @Test
    void failedHouseServiceCreationFactoryHouseNull() {
        assertThrows(IllegalArgumentException.class, () -> new HouseService(houseRepository, null));
    }

    /**
     * Test the successful creation of a House object without location, using the method from the service.
     * Verifies that the object was created.
     */
    @Test
    void successNewHouseWithoutLocation() {
        // Set behavior for dependencies
        when(factoryHouse.createHouseWithOutLocation()).thenReturn(houseDouble);
        when(houseRepository.save(houseDouble)).thenReturn(houseDouble);

        // Set house double behavior
        when(houseDouble.identity()).thenReturn(houseID);

        HouseID createdHouseID = houseService.createAndSaveHouseWithoutLocation();

        assertNotNull(createdHouseID);
    }

    /**
     * Test the successful creation of a House object with location, using the method from the service.
     * Verifies that the object was created.
     */
    @Test
    void successNewHouseWithLocation() {
        try (MockedConstruction<Location> locationMockedConstruction = mockConstruction(Location.class, (mock, context) -> {
            when(mock.getAddress()).thenReturn(addressDouble);
            when(mock.getGpsCode()).thenReturn(gpsCodeDouble);

        })) {
            try (MockedConstruction<Address> addressMockedConstruction = mockConstruction(Address.class, (mock, context) -> {
                when(mock.getStreet()).thenReturn("Rua do Ouro");
                when(mock.getDoorNumber()).thenReturn("123");
                when(mock.getZipCode()).thenReturn("4000-000");
                when(mock.getCity()).thenReturn("Porto");
                when(mock.getCountry()).thenReturn("Portugal");
            })) {
                try (MockedConstruction<GPSCode> gpsCodeMockedConstruction = mockConstruction(GPSCode.class, (mock, context) -> {
                    when(mock.getLatitude()).thenReturn(41.14961);
                    when(mock.getLongitude()).thenReturn(-8.61099);
                })) {
                    when(factoryHouse.createHouseWithLocation(locationDouble)).thenReturn(houseDouble);
                    when(houseRepository.save(houseDouble)).thenReturn(houseDouble);
                    when(houseDouble.identity()).thenReturn(houseID);

                    HouseID createdHouseID = houseService.createAndSaveHouseWithLocation(locationDouble);

                    assertNotNull(createdHouseID);
                }
            }
        }
    }

    /**
     * Test the failed creation of a House object when location is null.
     * Verifies that the object created by the service is null.
     */
    @Test
    void failedNewHouseWithLocationNullLocation() {
        assertNull(houseService.createAndSaveHouseWithLocation(null));
    }

    /**
     * Successfully edits the location of the House.
     * Verifies that the method returns the Location object.
     */
    @Test
    void successEditLocation() {
        // Mock behavior of houseService and houseRepository
        when(houseRepository.findEntityByID(houseID)).thenReturn(Optional.of(houseDouble));

        // Mock behavior of house.editLocation to return a new LocationDTO
        when(houseDouble.editLocation(addressDouble, gpsCodeDouble)).thenReturn(locationDouble);

        when(houseRepository.update(houseDouble)).thenReturn(houseDouble);

        // Call the method under test
        Location editedLocation = houseService.editLocation(houseID, addressDouble, gpsCodeDouble);

        // Assert that the editedLocationDTO is not null
        assertNotNull(editedLocation);

    }

    /**
     * Failed to edit the location of the House.
     * Verifies that the method returns null when address is null.
     */
    @Test
    void failedEditLocation() {
        // Mock behavior of houseService and houseRepository
        when(houseRepository.findEntityByID(houseID)).thenReturn(Optional.of(houseDouble));

        when(houseDouble.editLocation(addressDouble, gpsCodeDouble)).thenReturn(null);

        when(houseRepository.update(houseDouble)).thenReturn(houseDouble);

        assertNull(houseService.editLocation(houseID, addressDouble, gpsCodeDouble));
    }

    /**
     * Failed to edit the location of the House.
     * Verifies that the method returns null when house isn't in repository.
     */
    @Test
    void failedEditLocationNullHouse() {
        // Mock behavior of houseService and houseRepository
        when(houseRepository.findEntityByID(houseID)).thenReturn(Optional.empty());

        assertNull(houseService.editLocation(houseID, addressDouble, gpsCodeDouble));
    }

    /**
     * Test the successful retrieval of a House object from the repository.
     * Verifies that the method returns the House object.
     */

    @Test
    void testGetHouseFromRepository() {
        // Prepare mock data
        House mockHouse1 = mock(House.class);
        List<House> mockHouseList = List.of(mockHouse1);

        HouseID mockHouseID = mock(HouseID.class);

        // Mock behavior of houseRepository
        when(houseRepository.findAllEntities()).thenReturn(mockHouseList);
        when(mockHouse1.identity()).thenReturn(mockHouseID);

        // Execute
        HouseID result = houseService.getHouseIDFromRepository();

        // Verify
        assertNotNull(result);
        assertEquals(mockHouseID, result);
    }

    /**
     * Test the failed retrieval of a House object from the repository.
     * Verifies that the method throws a HouseNotFoundException.
     */
    @Test
    void failGetHouseFromRepositoryNoHouse() {
        // Prepare mock data
        List<House> mockHouseList = new ArrayList<>();
        // Mock behavior of houseRepository to return an empty list
        when(houseRepository.findAllEntities()).thenReturn(mockHouseList);

        // Execute and verify that NoHouseInRepositoryException is thrown
        assertThrows(NoHouseInRepositoryException.class, () -> houseService.getHouseIDFromRepository());
    }

    /**
     * Test the successful retrieval of a House object that has the attribute Location, from the repository.
     * Verifies that the method returns an InternalHouseDTO object containing all tthe attributes of the house in the repository.
     */
    @Test
    void successGetInternalHouseWithLocation() {
        when(houseRepository.findAllEntities()).thenReturn(List.of(houseDouble));
        when(houseDouble.identity()).thenReturn(houseID);
        when(houseDouble.getHouseLocation()).thenReturn(locationDouble);
        when(locationDouble.getAddress()).thenReturn(addressDouble);
        when(locationDouble.getGpsCode()).thenReturn(gpsCodeDouble);
        when(addressDouble.getStreet()).thenReturn("Rua do Ouro");
        when(addressDouble.getDoorNumber()).thenReturn("123");
        when(addressDouble.getZipCode()).thenReturn("4000-000");
        when(addressDouble.getCity()).thenReturn("Porto");
        when(addressDouble.getCountry()).thenReturn("Portugal");
        when(gpsCodeDouble.getLatitude()).thenReturn(41.14961);
        when(gpsCodeDouble.getLongitude()).thenReturn(-8.61099);

        // Act
        InternalHouseDTO result = houseService.getHouse();

        // Assert
        assertNotNull(result);
        assertEquals("Rua do Ouro", result.street);
        assertEquals("123", result.doorNumber);
        assertEquals("4000-000", result.zipCode);
        assertEquals("Porto", result.city);
        assertEquals("Portugal", result.country);
        assertEquals(41.14961, result.latitude);
        assertEquals(-8.61099, result.longitude);
    }

    /**
     * Test the successful retrieval of a House object that do not have the attribute Location, from the repository.
     * Verifies that the method returns an InternalHouseDTO object containing all tthe attributes of the house in the repository.
     */
    @Test
    void successGetInternalHouseWithoutLocation() {
        when(houseRepository.findAllEntities()).thenReturn(List.of(houseDouble));
        when(houseDouble.getHouseLocation()).thenReturn(null);
        when(houseDouble.identity()).thenReturn(houseID);

        // Act
        InternalHouseDTO result = houseService.getHouse();

        // Assert
        assertNotNull(result);
        assertEquals(houseID.toString(), result.houseID.toString());
    }

    /**
     * Test the failed retrieval of a House object from the repository.
     * Verifies that the method throws a NoHouseInRepositoryException.
     */
    @Test
    void failGetInternalHouseWithoutLocation() {
        when(houseRepository.findAllEntities()).thenReturn(new ArrayList<>());

        // Assert
        assertThrows(NoHouseInRepositoryException.class, () -> houseService.getHouse());
    }

}