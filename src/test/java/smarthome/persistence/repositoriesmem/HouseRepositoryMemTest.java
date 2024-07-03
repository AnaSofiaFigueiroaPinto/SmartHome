package smarthome.persistence.repositoriesmem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.House;
import smarthome.domain.valueobjects.HouseID;
import smarthome.util.exceptions.SingleHouseViolationException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the operations of the HouseRepositoryMem class to ensure its correct behavior.
 */
class HouseRepositoryMemTest {

    private final HouseID houseId = mock(HouseID.class);
    private final HouseID houseId1 = mock(HouseID.class);
    private final House houseEntity = mock(House.class);
    private final House houseEntity1 = mock(House.class);
    private final Map<HouseID, House> map = new HashMap<>();

    /**
     * Sets up the test environment by initializing required objects.
     */
    @BeforeEach
    void setUp() {
        map.put(houseId, houseEntity);
    }

    /**
     * Test the successful addition of a House entity to the repository.
     * Returns true if the entity is added to the map.
     */
    @Test
    void successAddEntityToMap(){
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(new HashMap<>());

        House savedEntity = repositoryHouse.save(houseEntity);
        assertEquals(houseEntity, savedEntity);
    }

    /**
     * Test the failure to add a House entity to the repository when a house already exists.
     * Throws an exception if one house already exists in the repository.
     */
    @Test
    void failsAddEntityWhenOneHouseAlreadyExists(){
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        when(houseEntity.identity()).thenReturn(houseId);

        assertThrows(SingleHouseViolationException.class, () -> repositoryHouse.save(houseEntity1));
    }

    /**
     * Tests if an entity is contained in the repository after its addition.
     * The test asserts true if the entity is found in the repository.
     */
    @Test
    void entityIsContainedInRepositoryAfterAddition() {
        HouseRepositoryMem emptyRepository = new HouseRepositoryMem(new HashMap<>());
        when(houseEntity1.identity()).thenReturn(houseId1);
        emptyRepository.save(houseEntity1);
        boolean isContained = emptyRepository.containsEntityByID(houseId1);
        assertTrue(isContained);
    }

    /**
     * Tests the addition of a null entity to the map.
     * The test throws an exception since a null entity cannot be added to the map.
     */
    @Test
    void invalidAddNullEntityToMap() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(new HashMap<>());

        assertThrows(NullPointerException.class, () -> repositoryHouse.save(null));
    }

    /**
     * Test the successful retrieval of all entities in the repository.
     * Returns true if the entities are retrieved from the map.
     */
    @Test
    void successRetrieveAllEntitiesInData() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        assertEquals(repositoryHouse.findAllEntities(), map.values());
    }

    /**
     * Test the successful retrieval of a House entity with a specific id.
     * Returns true if the entity is retrieved from the map.
     */
    @Test
    void successGetHouseWithSpecificId() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        Optional<House> foundEntity = repositoryHouse.findEntityByID(houseId);
        House house = foundEntity.get();
        assertEquals(house, houseEntity);
    }

    /**
     * Test the failure of retrieval of a House entity with a specific id.
     * Throws an exception if the entity is not retrieved from the map.
     */
    @Test
    void failGetHouseWithSpecificId() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        HouseID notPresentID = mock(HouseID.class);

        Optional<House> foundEntity = repositoryHouse.findEntityByID(notPresentID);

        assertThrows(NoSuchElementException.class, () -> foundEntity.get());
    }

    /**
     * Test the successful check of the existence of a House entity in the repository.
     * Returns true if the entity exists in the map.
     */
    @Test
    void successMapContainsEntity() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        assertTrue(repositoryHouse.containsEntityByID(houseId));
    }

    /**
     * Test the failure to check the existence of a House entity in the repository.
     * Returns false if the entity does not exist in the map.
     */
    @Test
    void failMapContainsEntity() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        HouseID notPresentID = mock(HouseID.class);

        assertFalse(repositoryHouse.containsEntityByID(notPresentID));
    }

    /**
     * Tests the successful update of a house entity in the repository.
     * Returns the updated house entity if successful.
     */
    @Test
    void successfullyUpdateHouse() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        when(houseEntity.identity()).thenReturn(houseId);
        assertEquals(houseEntity, repositoryHouse.update(houseEntity));
    }

    /**
     * Tests the failure to update a house entity not present in the repository.
     * Returns null since the house entity is not present in the repository.
     */
    @Test
    void failUpdateHouseNotInRepository() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        when(houseEntity.identity()).thenReturn(houseId);

        assertNull(repositoryHouse.update(houseEntity1));
    }

    /**
     * Tests the successful update of a reserved house entity in the repository.
     * Returns the updated house entity if successful.
     */
    @Test
    void successfullyUpdateReservedHouse() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        when(houseEntity.identity()).thenReturn(houseId);
        Optional<House> foundEntity = repositoryHouse.findEntityByIDAndReserve(houseId);
        House house = foundEntity.get();

        assertEquals(house, repositoryHouse.updateReserved(house));
    }

    /**
     * Tests the failure scenario when attempting to update a house entity that is not reserved.
     * Return null if the house entity is not reserved and therefore cannot be updated.
     */
    @Test
    void failUpdateReservedHouseNotReserved() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        when(houseEntity.identity()).thenReturn(houseId);
        assertNull(repositoryHouse.updateReserved(houseEntity));
    }

    /**
     * Tests the failure scenario when attempting to update a house entity with a different ID from the reserved one.
     * Returns null if the house entity ID does not match the reserved ID and therefore cannot be updated.
     */
    @Test
    void failUpdateReservedHouseNotSameID() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        when(houseEntity.identity()).thenReturn(houseId);
        repositoryHouse.findEntityByIDAndReserve(houseId);
        House houseDouble = mock(House.class);
        HouseID notPresentID = mock(HouseID.class);
        when(houseDouble.identity()).thenReturn(notPresentID);

        assertNull(repositoryHouse.updateReserved(houseDouble));
    }

    /**
     * Tests the successful retrieval and reservation of a house entity by ID.
     * Returns the reserved house entity if found and reserved successfully.
     */
    @Test
    void successfullyFindEntityByIDAndReserve() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        Optional<House> foundEntity = repositoryHouse.findEntityByIDAndReserve(houseId);
        House house = foundEntity.get();

        assertEquals(house, houseEntity);
    }

    /**
     * Tests the failure to find and reserve a house entity when the ID is not present in the repository.
     * Returns an empty optional since the entity is not present in the repository.
     */
    @Test
    void failFindEntityByIDAndReserveNotPresent() {
        HouseRepositoryMem repositoryHouse = new HouseRepositoryMem(map);
        HouseID notPresentID = mock(HouseID.class);

        Optional<House> foundEntity = repositoryHouse.findEntityByIDAndReserve(notPresentID);

        assertThrows(NoSuchElementException.class, foundEntity::get);
    }
}