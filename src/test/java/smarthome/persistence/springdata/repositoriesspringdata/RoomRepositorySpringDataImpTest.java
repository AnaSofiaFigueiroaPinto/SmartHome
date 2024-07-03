package smarthome.persistence.springdata.repositoriesspringdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;
import smarthome.persistence.jpa.datamodel.MapperRoomDataModel;
import smarthome.persistence.jpa.datamodel.RoomDataModel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link RoomRepositorySpringDataImp}.
 */

@SpringBootTest(classes = {RoomRepositorySpringDataImp.class})
class RoomRepositorySpringDataImpTest {

    @MockBean
    private RoomRepositorySpringData roomRepositorySpringData;

    @MockBean
    private FactoryRoom factoryRoom;

    @MockBean
    private MapperRoomDataModel mapperRoomDataModel;

    @MockBean
    private RoomDataModel roomDataModel;

    @InjectMocks
    private RoomRepositorySpringDataImp roomRepositorySpringDataImp;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for successfully saving a room in the repository.
     */
    @Test
    void successSaveRoomInRepo() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);
        when(roomRepositorySpringData.save(roomDataModel)).thenReturn(roomDataModel);

        Room savedRoom = roomRepositorySpringDataImp.save(roomDouble);

        assertNotNull(savedRoom);
    }

    /**
     * Test for unsuccessfully saving a room in the repository with a null room.
     */
    @Test
    void unsuccessfullySaveRoomInRepo() {
        assertThrows(IllegalArgumentException.class, () -> {
            roomRepositorySpringDataImp.save(null);
        });
    }

    /**
     * Test for successfully finding all rooms in the repository.
     */
    @Test
    void successFindAllEntities() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);
        when(roomDataModel.getRoomID()).thenReturn("roomID");
        when(roomDataModel.getFloor()).thenReturn(2);
        when(roomDataModel.getHeight()).thenReturn(3.0);
        when(roomDataModel.getWidth()).thenReturn(3.0);
        when(roomDataModel.getLength()).thenReturn(3.0);
        when(roomDataModel.getHouseID()).thenReturn("houseID");

        when(roomRepositorySpringData.findAll()).thenReturn(List.of(roomDataModel));
        when(mapperRoomDataModel.toDomainList(factoryRoom, List.of(roomDataModel))).thenReturn(List.of(roomDouble));

        Iterable<Room> rooms = roomRepositorySpringDataImp.findAllEntities();

        assertNotNull(rooms);
    }

    /**
     * Test for successfully finding a room by ID in the repository.
     */
    @Test
    void successFindEntityByID() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);
        when(roomDataModel.getRoomID()).thenReturn("roomID");
        when(roomDataModel.getFloor()).thenReturn(2);
        when(roomDataModel.getHeight()).thenReturn(3.0);
        when(roomDataModel.getWidth()).thenReturn(3.0);
        when(roomDataModel.getLength()).thenReturn(3.0);
        when(roomDataModel.getHouseID()).thenReturn("houseID");


        Optional<RoomDataModel> optionalRoomDataModel = Optional.of(roomDataModel);
        when(roomRepositorySpringData.findById("roomID")).thenReturn(optionalRoomDataModel);
        when(mapperRoomDataModel.toDomain(factoryRoom, optionalRoomDataModel.get())).thenReturn(roomDouble);

        Optional<Room> roomOptional = roomRepositorySpringDataImp.findEntityByID(roomIDDouble);
        assertNotNull(roomOptional);
    }

    /**
     * Test for unsuccessfully finding a room by ID in the repository.
     */
    @Test
    void unsuccessfullyFindEntityByID() {
        RoomID roomIDDouble = mock(RoomID.class);
        when(roomIDDouble.toString()).thenReturn("roomID");

        Optional<RoomDataModel> roomDataModelOptional = Optional.empty();
        when(roomRepositorySpringData.findById("roomID")).thenReturn(roomDataModelOptional);

        Optional<Room> roomOptional = roomRepositorySpringDataImp.findEntityByID(roomIDDouble);
        assertNotNull(roomOptional);
    }

    /**
     * Test for successfully checking if a room exists by ID in the repository.
     */

    @Test
    void successContainsEntityByID() {
        RoomID roomIDDouble = mock(RoomID.class);
        when(roomIDDouble.toString()).thenReturn("roomID");

        when(roomRepositorySpringData.existsById("roomID")).thenReturn(true);

        boolean contains = roomRepositorySpringDataImp.containsEntityByID(roomIDDouble);
        assertTrue(contains);
    }

    /**
     * Test for unsuccessfully checking if a room exists by ID in the repository (room not found).
     */
    @Test
    void unsuccessfullyContainsEntityByID() {
        RoomID roomIDDouble = mock(RoomID.class);
        when(roomIDDouble.toString()).thenReturn("roomID");

        when(roomRepositorySpringData.existsById("roomID")).thenReturn(false);

        boolean contains = roomRepositorySpringDataImp.containsEntityByID(roomIDDouble);
        assertFalse(contains);
    }

    /**
     * Test for successfully updating a room in the repository.
     */
    @Test
    void successUpdateRoom() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);
        when(roomDataModel.getRoomID()).thenReturn("roomID");
        when(roomDataModel.getFloor()).thenReturn(2);
        when(roomDataModel.getHeight()).thenReturn(3.0);
        when(roomDataModel.getWidth()).thenReturn(3.0);
        when(roomDataModel.getLength()).thenReturn(3.0);
        when(roomDataModel.getHouseID()).thenReturn("houseID");

        Optional<RoomDataModel> roomDataModelOptional = Optional.of(roomDataModel);
        when(roomRepositorySpringData.findById("roomID")).thenReturn(roomDataModelOptional);
        when(roomDataModel.updateFromDomain(roomDouble)).thenReturn(true);
        when(roomRepositorySpringData.save(roomDataModel)).thenReturn(roomDataModel);

        Room updatedRoom = roomRepositorySpringDataImp.update(roomDouble);
        assertNotNull(updatedRoom);
    }

    /**
     * Test for unsuccessfully updating a room in the repository (update failed).
     */
    @Test
    void failUpdateRoom() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);
        when(roomDataModel.getRoomID()).thenReturn("roomID");
        when(roomDataModel.getFloor()).thenReturn(2);
        when(roomDataModel.getHeight()).thenReturn(3.0);
        when(roomDataModel.getWidth()).thenReturn(3.0);
        when(roomDataModel.getLength()).thenReturn(3.0);
        when(roomDataModel.getHouseID()).thenReturn("houseID");

        Optional<RoomDataModel> roomDataModelOptional = Optional.of(roomDataModel);
        when(roomRepositorySpringData.findById("roomID")).thenReturn(roomDataModelOptional);
        when(roomDataModel.updateFromDomain(roomDouble)).thenReturn(false);
        when(roomRepositorySpringData.save(roomDataModel)).thenReturn(roomDataModel);

        Room updatedRoom = roomRepositorySpringDataImp.update(roomDouble);
        assertNull(updatedRoom);
    }

    /**
     * Test for unsuccessfully updating a room in the repository (room not found).
     */
    @Test
    void failUpdateRoomNotFoundInRepo() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);

        Optional<RoomDataModel> roomDataModelOptional = Optional.empty();
        when(roomRepositorySpringData.findById("roomID")).thenReturn(roomDataModelOptional);

        Room updatedRoom = roomRepositorySpringDataImp.update(roomDouble);
        assertNull(updatedRoom);
    }

    /**
     * Test for successfully updating a reserved room in the repository.
     */
    @Test
    void sucessfullyUpdateReservedRoom() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);

        when(roomDataModel.updateFromDomain(roomDouble)).thenReturn(true);
        when(roomDataModel.getRoomID()).thenReturn("roomID2");
        when(roomDataModel.getFloor()).thenReturn(2);
        when(roomDataModel.getHeight()).thenReturn(3.0);
        when(roomDataModel.getWidth()).thenReturn(3.0);
        when(roomDataModel.getLength()).thenReturn(3.0);
        when(roomDataModel.getHouseID()).thenReturn("houseID");
        when(roomRepositorySpringData.save(roomDataModel)).thenReturn(roomDataModel);

        Room updatedRoom = roomRepositorySpringDataImp.updateReserved(roomDouble);
        assertNotNull(updatedRoom);
    }

    /**
     * Test for unsuccessfully updating a reserved room in the repository (update failed).
     */
    @Test
    void failUpdateRoomReserved() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);

        when(roomDataModel.updateFromDomain(roomDouble)).thenReturn(false);
        when(roomDataModel.getRoomID()).thenReturn("roomID2");
        when(roomDataModel.getFloor()).thenReturn(2);
        when(roomDataModel.getHeight()).thenReturn(3.0);
        when(roomDataModel.getWidth()).thenReturn(3.0);
        when(roomDataModel.getLength()).thenReturn(3.0);
        when(roomDataModel.getHouseID()).thenReturn("houseID");
        when(roomRepositorySpringData.save(roomDataModel)).thenReturn(roomDataModel);

        Room updatedRoom = roomRepositorySpringDataImp.updateReserved(roomDouble);
        assertNull(updatedRoom);
    }

    /**
     * Test for successfully finding and reserving a room by ID in the repository.
     */
    @Test
    void successfullyFindEntityByIDAndReserve() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);

        when(roomDataModel.updateFromDomain(roomDouble)).thenReturn(true);
        when(roomDataModel.getRoomID()).thenReturn("roomID2");
        when(roomDataModel.getFloor()).thenReturn(2);
        when(roomDataModel.getHeight()).thenReturn(3.0);
        when(roomDataModel.getWidth()).thenReturn(3.0);
        when(roomDataModel.getLength()).thenReturn(3.0);
        when(roomDataModel.getHouseID()).thenReturn("houseID");
        when(roomRepositorySpringData.save(roomDataModel)).thenReturn(roomDataModel);

        Optional<RoomDataModel> optionalRoomDataModel = Optional.of(roomDataModel);
        when(roomRepositorySpringData.findById("roomID")).thenReturn(optionalRoomDataModel);
        when(mapperRoomDataModel.toDomain(factoryRoom, optionalRoomDataModel.get())).thenReturn(roomDouble);

        Optional<Room> roomOptional = roomRepositorySpringDataImp.findEntityByIDAndReserve(roomIDDouble);
        assertNotNull(roomOptional);
    }

    /**
     * Test for unsuccessfully finding and reserving a room by ID in the repository (room not found).
     */
    @Test
    void failFindEntityByIDAndReserve() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);
        Room roomDouble = mock(Room.class);

        when(roomIDDouble.toString()).thenReturn("roomID");
        when(roomFloorDouble.toString()).thenReturn("2");
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(3.0);
        when(roomDimensionsDouble.getLength()).thenReturn(3.0);
        when(houseIDDouble.toString()).thenReturn("houseID");
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);

        Optional<RoomDataModel> optionalRoomDataModel = Optional.empty();
        when(roomRepositorySpringData.findById("roomID")).thenReturn(optionalRoomDataModel);

        Optional<Room> roomOptional = roomRepositorySpringDataImp.findEntityByIDAndReserve(roomIDDouble);
        assertNotNull(roomOptional);
    }

    /**
     * Test for successfully finding all rooms of a given house in the repository.
     */

    @Test
    void successFindRoomsOfAHouse() {
        Room roomDouble = mock(Room.class);
        HouseID houseIDDouble = mock(HouseID.class);

        when(roomRepositorySpringData.findAllByHouseID(houseIDDouble.toString())).thenReturn(List.of(roomDataModel));
        when(mapperRoomDataModel.toDomainList(factoryRoom, List.of(roomDataModel))).thenReturn(List.of(roomDouble));

        Iterable<Room> roomList = roomRepositorySpringDataImp.findByHouseID(houseIDDouble);
        assertNotNull(roomList);
    }
}