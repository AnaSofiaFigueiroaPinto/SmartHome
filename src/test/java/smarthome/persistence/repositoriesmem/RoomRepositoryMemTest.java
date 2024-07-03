package smarthome.persistence.repositoriesmem;

import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomRepositoryMemTest {
    private RoomID roomID = mock(RoomID.class);
    private RoomID roomID1 = mock(RoomID.class);
    private RoomID roomID2 = mock(RoomID.class);
    private Room roomEntity = mock(Room.class);
    private Room roomEntity1 = mock(Room.class);
    private Room roomEntity2 = mock(Room.class);

    private Map<RoomID, Room> mapData = new HashMap<>();

    void fillMapData() {
        mapData.put(roomID, roomEntity);
        mapData.put(roomID1, roomEntity1);
    }

    /**
     * Fill up the map to be used as double of data loaded into repository before each test
     */
    @BeforeEach
    void setUP() {
        fillMapData();
    }

    /**
     * Test the successful addition of a Room entity to the repository.
     */
    @Test
    void successAddEntityToMap() {
        when(roomEntity2.identity()).thenReturn(roomID2);
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        repository.save(roomEntity2);
        assertTrue(repository.containsEntityByID(roomID2));
    }

    /**
     * Test the fail addition of a null Room entity to the repository.
     */
    @Test
    void invalidAddNullEntityToMap() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        assertNull(repository.save(null));
        assertEquals(2, mapData.size());
    }

    /**
     * Successful retrieval of all entities in the repository
     */
    @Test
    void successFindingAllEntitiesInRepository() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        assertEquals(repository.findAllEntities(), mapData.values());
    }

    /**
     * Successfully retrieve a Room entity based on its ID
     */
    @Test
    void successFindingSpecificEntity() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        Optional<Room> specificRoom = repository.findEntityByID(roomID);
        Room room = specificRoom.get();
        assertEquals(roomEntity, room);
    }

    /**
     * Fails to retrieve a Room entity which ID isn't in repository.
     */
    @Test
    void failsFindingSpecificEntity() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        RoomID nonExistentID = mock(RoomID.class);
        Optional<Room> specificRoom = repository.findEntityByID(nonExistentID);

        assertThrows(NoSuchElementException.class, specificRoom::get);
    }

    /**
     * Checks (successfully) if a Room entity exists in the repository based in a given ID.
     */
    @Test
    void successExistentEntity() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        assertTrue(repository.containsEntityByID(roomID));
    }

    /**
     * Fails if a Room entity doesn't exist in the repository based in a given ID.
     */
    @Test
    void failNonExistentEntity() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        RoomID nonExistentID = mock(RoomID.class);
        assertFalse(repository.containsEntityByID(nonExistentID));
    }

    /**
     * Tests the successful update of a Room entity in the repository.
     * Returns the updated Room entity if successful.
     */
    @Test
    void successfullyUpdateDevice() {
        when(roomEntity.identity()).thenReturn(roomID);

        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        assertEquals(roomEntity, repository.update(roomEntity));
    }

    /**
     * Tests the failure to update a Room entity not present in the repository.
     * Returns null since the Room entity is not present in the repository.
     */
    @Test
    void failUpdateRoomNotInRepository() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        RoomID notPresentID = mock(RoomID.class);
        when(roomEntity.identity()).thenReturn(notPresentID);

        assertNull(repository.update(roomEntity));
    }

    /**
     * Tests the successful update of a reserved Room entity in the repository.
     * Returns the updated Room entity if successful.
     */
    @Test
    void successfullyUpdateReservedRoom() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        when(roomEntity.identity()).thenReturn(roomID);
        Optional<Room> foundEntity = repository.findEntityByIDAndReserve(roomID);
        Room room = foundEntity.get();

        assertEquals(room, repository.updateReserved(room));
    }

    /**
     * Tests the failure scenario when attempting to update a Room entity that is not reserved.
     * Return null if the Room entity is not reserved and therefore cannot be updated.
     */
    @Test
    void failUpdateReservedRoomNotReserved() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);

        assertNull(repository.updateReserved(roomEntity2));
    }

    /**
     * Tests the failure scenario when attempting to update a Room entity with a different ID from the reserved one.
     * Returns null if the Room entity ID does not match the reserved ID and therefore cannot be updated.
     */
    @Test
    void failUpdateReservedRoomNotSameID() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        when(roomEntity.identity()).thenReturn(roomID);
        repository.findEntityByIDAndReserve(roomID);
        Room roomDouble = mock(Room.class);
        RoomID notPresentID = mock(RoomID.class);
        when(roomDouble.identity()).thenReturn(notPresentID);

        assertNull(repository.updateReserved(roomDouble));
    }

    /**
     * Tests the successful retrieval and reservation of a Room entity by ID.
     * Returns the reserved Room entity if found and reserved successfully.
     */
    @Test
    void successfullyFindRoomByIDAndReserve() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);

        Optional<Room> foundEntity = repository.findEntityByIDAndReserve(roomID);
        Room room = foundEntity.get();

        assertEquals(room, roomEntity);
    }

    /**
     * Tests the failure to find and reserve a Room entity when the ID is not present in the repository.
     * Returns an empty optional since the Room is not present in the repository.
     */
    @Test
    void failFindRoomByIDAndReserveNotPresent() {
        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        RoomID notPresentID = mock(RoomID.class);

        Optional<Room> foundEntity = repository.findEntityByIDAndReserve(notPresentID);

        assertThrows(NoSuchElementException.class, foundEntity::get);
    }


    /**
     * Tests the successful return of all Room entities of a given House in the repository.
     */
    @Test
    void successfullyRetrievesRoomOfAHouse() {

        HouseID houseIDDouble = mock(HouseID.class);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomEntity.getHouseID()).thenReturn(houseIDDouble);
        when(roomEntity1.getHouseID()).thenReturn(houseIDDouble);

        RoomRepositoryMem repository = new RoomRepositoryMem(mapData);
        Iterable<Room> expectedRoomList = new ArrayList<>(mapData.values());
        Iterable<Room> actualRoomList = repository.findByHouseID(houseIDDouble);

        assertEquals(expectedRoomList, actualRoomList);
    }
}