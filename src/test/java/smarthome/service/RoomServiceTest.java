package smarthome.service;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.service.internaldto.InternalRoomDTO;
import smarthome.util.exceptions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the {@link RoomService} class, responsible
 * for testing the functionality of room management operations.
 */
@SpringBootTest(classes = {RoomService.class})
class RoomServiceTest {

    /**
     * The object under test for this test class.
     */
    @MockBean
    private RoomRepository roomRepositoryDouble;

    @MockBean
    private FactoryRoom impFactoryRoomDouble;

    @MockBean
    private HouseRepository houseRepositoryDouble;

    @InjectMocks
    private RoomService roomServices;


    /**
     * Sets up the test environment before each test method.
     * Set up method executed before each test case to
     * initialize test dependencies.
     * This method creates mock objects for HouseID, ImpFactoryRoom,
     * and RoomRepository to be used during testing. It then
     * initializes a RoomService object with these mocked dependencies.
     * This ensures that each test case starts with a clean and
     * consistent state for testing the RoomService functionality.
     */

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for creating RoomService with valid parameters.
     */
    @Test
    void testConstructRoomServiceWithValidParameters() {
        assertDoesNotThrow(() -> {
            roomServices = new RoomService(roomRepositoryDouble, impFactoryRoomDouble,
                    houseRepositoryDouble);
        }, "RoomService creation should not throw any exceptions");
    }

    /**
     * Test method to verify creating and saving a room in the
     * repository successfully.
     * <p>
     * The test asserts that calling {@code createRoomAndSave} with
     * specified parameters returns the roomID of the created room.
     */
    @Test
    void testCreatingAndSavingRoomInRepositorySuccess() {
        // Create class to test
        RoomService roomServices = new RoomService(roomRepositoryDouble,
                impFactoryRoomDouble, houseRepositoryDouble);

        // Create doubles of objects that are method parameters
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);

        // Set houseID double behaviour
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(true);

        // Create and set behaviour of room and repository
        Room roomDouble = mock(Room.class);
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomRepositoryDouble.containsEntityByID(roomIDDouble)).thenReturn(false);

        // Double factory output
        when(impFactoryRoomDouble.createRoom(roomIDDouble, roomFloorDouble, roomDimensionsDouble, houseIDDouble)).
                thenReturn(roomDouble);

        // Act
        RoomID result = roomServices.createRoomAndSave(roomIDDouble, roomFloorDouble, roomDimensionsDouble,
                houseIDDouble);

        // Assert
        assertNotNull(result);
        assertEquals(roomIDDouble, result, "RoomID returned from createRoomAndSave");
    }

    /**
     * Tests the failure scenario of creating a room with
     * invalid dimensions.
     */

    @Test
    void failRoomCreationInvalidParameter() {
        // Create class to test
        RoomService roomServices = new RoomService(roomRepositoryDouble,
                impFactoryRoomDouble, houseRepositoryDouble);

        // Create doubles of objects that are method parameters
        RoomID roomIDDouble = mock(RoomID.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);

        // Set houseID double behaviour
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(true);

        // Create and set behaviour of room and repository
        Room roomDouble = mock(Room.class);
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomRepositoryDouble.containsEntityByID(roomIDDouble)).thenReturn(false);

        // Double factory output
        when(impFactoryRoomDouble.createRoom(roomIDDouble, null, roomDimensionsDouble, houseIDDouble)).
                thenReturn(null);

        // Assert
        assertThrows(RoomNotCreatedException.class, () -> roomServices.createRoomAndSave(roomIDDouble,
                null, roomDimensionsDouble, houseIDDouble));
    }

    /**
     * Test method to verify failing room creation due to
     * an inexistent house.
     */
    @Test
    void failRoomCreationInexistentHouse() {

        // Create class to test
        RoomService roomServices = new RoomService(roomRepositoryDouble,
                impFactoryRoomDouble, houseRepositoryDouble);

        // Create doubles of objects that are method parameters
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);

        // Set houseID double behaviour
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(false); // house does not exist

        // Assert
        assertThrows(HouseNotFoundException.class, () -> roomServices.createRoomAndSave(roomIDDouble, roomFloorDouble,
                roomDimensionsDouble, houseIDDouble));
    }

    /**
     * Test method to verify failing room creation due
     * to an already existing roomID.
     */

    @Test
    void testFailRoomCreationAlreadyExistingID() {

        // Create class to test
        RoomService roomServices = new RoomService(roomRepositoryDouble,
                impFactoryRoomDouble, houseRepositoryDouble);

        // Create doubles of objects that are method parameters
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);

        // Set houseID double behaviour
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(true);

        // Create and set behaviour of room and repository
        Room roomDouble = mock(Room.class);
        when(roomDouble.identity()).thenReturn(roomIDDouble);
        when(roomRepositoryDouble.containsEntityByID(roomIDDouble)).thenReturn(true); // room already exists

        // Double factory output
        when(impFactoryRoomDouble.createRoom(roomIDDouble, roomFloorDouble, roomDimensionsDouble, houseIDDouble)).
                thenReturn(roomDouble);

        // Assert
        assertThrows(RoomAlreadyExistsException.class, () -> roomServices.createRoomAndSave(roomIDDouble,
                roomFloorDouble, roomDimensionsDouble, houseIDDouble));
    }

    /**
     * Test method to verify rooms in HouseID.
     * <p>
     * The test asserts returns the expected
     * list of Room IDs with correct size.
     */
    @Test
    void successGetListOfRoomsInHouse() {
        // Create double of HouseID and set its behaviour
        HouseID houseIDDouble = mock(HouseID.class);
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(true);

        // Create room doubles and set behaviour to obtain the list of all rooms
        List<Room> listOfRoomsInRepo = new ArrayList<>();
        Room roomDouble1 = mock(Room.class);
        Room roomDouble2 = mock(Room.class);
        listOfRoomsInRepo.add(roomDouble1);
        listOfRoomsInRepo.add(roomDouble2);
        when(roomRepositoryDouble.findByHouseID(houseIDDouble)).thenReturn(listOfRoomsInRepo);

        // Create roomID doubles and set behaviour to obtain the roomIDs for the list to return
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);
        when(roomDouble1.identity()).thenReturn(roomIDDouble1);
        when(roomDouble2.identity()).thenReturn(roomIDDouble2);

        // Act
        List<RoomID> result = roomServices.getListOfRoomsInHouse(houseIDDouble);

        // Assert
        assertEquals(2, result.size(), "List size should be 2");
        assertTrue(result.get(0).equals(roomIDDouble1) || result.get(0).equals(roomIDDouble2));
    }

    /**
     * Test method to verify rooms in a specific House
     * the corresponding house does not exist in the repository.
     */

    @Test
    void testEmptyFilterRoomsByHouseIDNonExistingHouseID() {
        // Create double of HouseID and set its behaviour
        HouseID houseIDDouble = mock(HouseID.class);
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(false); // house does not exist

        // Create room doubles and set behaviour to obtain the list of all rooms
        List<Room> listOfRoomsInRepo = new ArrayList<>();
        Room roomDouble1 = mock(Room.class);
        Room roomDouble2 = mock(Room.class);
        listOfRoomsInRepo.add(roomDouble1);
        listOfRoomsInRepo.add(roomDouble2);
        when(roomRepositoryDouble.findByHouseID(houseIDDouble)).thenReturn(listOfRoomsInRepo);

        // Create roomID doubles and set behaviour to obtain the roomIDs for the list to return
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);
        when(roomDouble1.identity()).thenReturn(roomIDDouble1);
        when(roomDouble2.identity()).thenReturn(roomIDDouble2);

        // Assert
        assertThrows(HouseNotFoundException.class, () -> roomServices.getListOfRoomsInHouse(houseIDDouble));
    }

    /**
     * Test method to verify rooms in a specific House when
     * a null HouseID is provided.
     */

    @Test
    void testThrowsFilterRoomsByHouseIDNullHouseID() {
        // Create double of HouseID and set its behaviour
        HouseID houseIDDouble = null;

        // Create room doubles and set behaviour to obtain the list of all rooms
        List<Room> listOfRoomsInRepo = new ArrayList<>();
        Room roomDouble1 = mock(Room.class);
        Room roomDouble2 = mock(Room.class);
        listOfRoomsInRepo.add(roomDouble1);
        listOfRoomsInRepo.add(roomDouble2);
        when(roomRepositoryDouble.findByHouseID(houseIDDouble)).thenReturn(listOfRoomsInRepo);

        // Create roomID doubles and set behaviour to obtain the roomIDs for the list to return
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);
        when(roomDouble1.identity()).thenReturn(roomIDDouble1);
        when(roomDouble2.identity()).thenReturn(roomIDDouble2);

        // Assert
        assertThrows(HouseNotFoundException.class, () -> roomServices.getListOfRoomsInHouse(houseIDDouble));
    }

    /**
     * Test method to verify successfully editing a room.
     * <p>
     * This test ensures that the {@code editRoomAndSave} method
     * of the {@link RoomService} class correctly handles the
     * scenario where a room is successfully edited and saved.
     * The test calls {@code editRoomAndSave} with the provided
     * RoomID and asserts that the result is true, indicating
     * successful editing and saving of the room.
     */
    @Test
    void testSuccessfullyEditRoom() {
        //Create doubles of objects that are method parameters
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);

        //Create double of room and set repository behaviour
        Room roomDouble = mock(Room.class);
        when(roomRepositoryDouble.findEntityByID(roomIDDouble)).thenReturn(Optional.of(roomDouble));

        //Set behaviour of room to edit room configuration
        when(roomDouble.editRoom(roomFloorDouble, roomDimensionsDouble)).thenReturn(roomDouble);

        //Set behaviour to obtain roomID to return
        when(roomDouble.identity()).thenReturn(roomIDDouble);

        RoomID result = roomServices.editRoomAndSave(roomIDDouble, roomFloorDouble, roomDimensionsDouble);

        assertEquals(result, roomIDDouble, "RoomID should match");
    }


    /**
     * Test method to verify failing to edit a room due to
     * not found room.
     */

    @Test
    void testFailToEditRoomNotFound() {
        //Create doubles of objects that are method parameters
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);

        //Create double of room and set repository behaviour
        Room roomDouble = mock(Room.class);
        when(roomRepositoryDouble.findEntityByID(roomIDDouble)).thenReturn(Optional.empty()); // room not found

        //Set behaviour of room to edit room configuration
        when(roomDouble.editRoom(roomFloorDouble, roomDimensionsDouble)).thenReturn(roomDouble);

        //Set behaviour to obtain roomID to return
        when(roomDouble.identity()).thenReturn(roomIDDouble);

        assertThrows(RoomNotFoundException.class, () -> roomServices.editRoomAndSave(roomIDDouble,
                roomFloorDouble, roomDimensionsDouble));
    }

    /**
     * Test method to verify failing to edit a room due
     * to invalid dimensions.
     */

    @Test
    void testFailToEditInvalidDimensions() {
        //Create doubles of objects that are method parameters
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = null;
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);

        //Create double of room and set repository behaviour
        Room roomDouble = mock(Room.class);
        when(roomRepositoryDouble.findEntityByID(roomIDDouble)).thenReturn(Optional.of(roomDouble));

        assertThrows((RoomNotEditedException.class), () -> roomServices.editRoomAndSave(roomIDDouble,
                roomFloorDouble, roomDimensionsDouble));
    }


    /**
     * Test method that returns a List of Rooms inside of House
     * Should return a List of RoomID objects.
     */
    @Test
    void successfulGetListOfRoomsInsideHouse() {
        // Create double of HouseID and set its behaviour
        HouseID houseIDDouble = mock(HouseID.class);
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(true);

        // Create room doubles and set behaviour to obtain the list of all rooms
        List<Room> listOfRoomsInRepo = new ArrayList<>();
        Room roomDouble1 = mock(Room.class);
        Room roomDouble2 = mock(Room.class);
        Room roomDouble3 = mock(Room.class);
        listOfRoomsInRepo.add(roomDouble1);
        listOfRoomsInRepo.add(roomDouble2);
        listOfRoomsInRepo.add(roomDouble3);
        when(roomRepositoryDouble.findByHouseID(houseIDDouble)).thenReturn(listOfRoomsInRepo);

        // Create roomID doubles and set behaviour to obtain the roomIDs for the list to return
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);
        RoomID roomIDDouble3 = mock(RoomID.class);
        when(roomDouble1.identity()).thenReturn(roomIDDouble1);
        when(roomDouble2.identity()).thenReturn(roomIDDouble2);
        when(roomDouble3.identity()).thenReturn(roomIDDouble3);

        // Create Room Dimensions and set behaviour
        RoomDimensions roomDimensionsDouble1 = mock(RoomDimensions.class);
        when(roomDimensionsDouble1.getHeight()).thenReturn(3.0);
        when(roomDouble1.getRoomDimensions()).thenReturn(roomDimensionsDouble1);

        RoomDimensions roomDimensionsDouble2 = mock(RoomDimensions.class);
        when(roomDimensionsDouble2.getHeight()).thenReturn(3.5);
        when(roomDouble2.getRoomDimensions()).thenReturn(roomDimensionsDouble2);

        RoomDimensions roomDimensionsDouble3 = mock(RoomDimensions.class);
        when(roomDimensionsDouble3.getHeight()).thenReturn(0.0);
        when(roomDouble3.getRoomDimensions()).thenReturn(roomDimensionsDouble3);

        // Act
        List<RoomID> result = roomServices.getListOfRoomsInsideOrOutsideHouse(houseIDDouble, true);

        // Assert
        assertEquals(2, result.size(), "List size should be 2");
        assertTrue(result.get(0).equals(roomIDDouble1) || result.get(0).equals(roomIDDouble2));
    }

    /**
     * Test method that returns a List of Rooms outside of House
     * Should return a List of RoomID objects.
     */
    @Test
    void successfulGetListOfRoomsOutsideHouse() {
        // Create double of HouseID and set its behaviour
        HouseID houseIDDouble = mock(HouseID.class);
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(true);

        // Create room doubles and set behaviour to obtain the list of all rooms
        List<Room> listOfRoomsInRepo = new ArrayList<>();
        Room roomDouble1 = mock(Room.class);
        Room roomDouble2 = mock(Room.class);
        Room roomDouble3 = mock(Room.class);
        Room roomDouble4 = mock(Room.class);
        listOfRoomsInRepo.add(roomDouble1);
        listOfRoomsInRepo.add(roomDouble2);
        listOfRoomsInRepo.add(roomDouble3);
        listOfRoomsInRepo.add(roomDouble4);
        when(roomRepositoryDouble.findByHouseID(houseIDDouble)).thenReturn(listOfRoomsInRepo);

        // Create roomID doubles and set behaviour to obtain the roomIDs for the list to return
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);
        RoomID roomIDDouble3 = mock(RoomID.class);
        RoomID roomIDDouble4 = mock(RoomID.class);
        when(roomDouble1.identity()).thenReturn(roomIDDouble1);
        when(roomDouble2.identity()).thenReturn(roomIDDouble2);
        when(roomDouble3.identity()).thenReturn(roomIDDouble3);
        when(roomDouble4.identity()).thenReturn(roomIDDouble4);

        // Create Room Dimensions and set behaviour
        RoomDimensions roomDimensionsDouble1 = mock(RoomDimensions.class);
        when(roomDimensionsDouble1.getHeight()).thenReturn(3.0);
        when(roomDouble1.getRoomDimensions()).thenReturn(roomDimensionsDouble1);

        RoomDimensions roomDimensionsDouble2 = mock(RoomDimensions.class);
        when(roomDimensionsDouble2.getHeight()).thenReturn(0.0);
        when(roomDouble2.getRoomDimensions()).thenReturn(roomDimensionsDouble2);

        RoomDimensions roomDimensionsDouble3 = mock(RoomDimensions.class);
        when(roomDimensionsDouble3.getHeight()).thenReturn(0.0);
        when(roomDouble3.getRoomDimensions()).thenReturn(roomDimensionsDouble3);

        RoomDimensions roomDimensionsDouble4 = mock(RoomDimensions.class);
        when(roomDimensionsDouble4.getHeight()).thenReturn(0.0);
        when(roomDouble4.getRoomDimensions()).thenReturn(roomDimensionsDouble4);

        // Act
        List<RoomID> result = roomServices.getListOfRoomsInsideOrOutsideHouse(houseIDDouble, false);

        // Assert
        assertEquals(3, result.size(), "List size should be 3");
        assertTrue(result.get(0).equals(roomIDDouble3) || result.get(0).equals(roomIDDouble2) || result.get(0).equals(roomIDDouble4));
    }

    /**
     * Test method to verify failing to get a list of rooms
     * because there's no rooms inside the house.
     * Should return an empty list.
     */
    @Test
    void failedGetEmptyListOfRoomsInsideHouse() {
        // Create double of HouseID and set its behaviour
        HouseID houseIDDouble = mock(HouseID.class);
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(true);

        // Create room doubles and set behaviour to obtain the list of all rooms
        List<Room> listOfRoomsInRepo = new ArrayList<>();
        Room roomDouble1 = mock(Room.class);
        Room roomDouble2 = mock(Room.class);
        Room roomDouble3 = mock(Room.class);
        listOfRoomsInRepo.add(roomDouble1);
        listOfRoomsInRepo.add(roomDouble2);
        listOfRoomsInRepo.add(roomDouble3);
        when(roomRepositoryDouble.findByHouseID(houseIDDouble)).thenReturn(listOfRoomsInRepo);

        // Create roomID doubles and set behaviour to obtain the roomIDs for the list to return
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);
        RoomID roomIDDouble3 = mock(RoomID.class);
        when(roomDouble1.identity()).thenReturn(roomIDDouble1);
        when(roomDouble2.identity()).thenReturn(roomIDDouble2);
        when(roomDouble3.identity()).thenReturn(roomIDDouble3);

        // Create Room Dimensions and set behaviour
        RoomDimensions roomDimensionsDouble1 = mock(RoomDimensions.class);
        when(roomDimensionsDouble1.getHeight()).thenReturn(0.0);
        when(roomDouble1.getRoomDimensions()).thenReturn(roomDimensionsDouble1);

        RoomDimensions roomDimensionsDouble2 = mock(RoomDimensions.class);
        when(roomDimensionsDouble2.getHeight()).thenReturn(0.0);
        when(roomDouble2.getRoomDimensions()).thenReturn(roomDimensionsDouble2);

        RoomDimensions roomDimensionsDouble3 = mock(RoomDimensions.class);
        when(roomDimensionsDouble3.getHeight()).thenReturn(0.0);
        when(roomDouble3.getRoomDimensions()).thenReturn(roomDimensionsDouble3);

        // Act
        List<RoomID> result = roomServices.getListOfRoomsInsideOrOutsideHouse(houseIDDouble, true);

        // Assert
        assertEquals(0, result.size(), "List size should be 0");
    }

    /**
     * Test method to verify failing to get a list of rooms
     * because there's no rooms outside the house.
     * Should return an empty list.
     */
    @Test
    void failedGetEmptyListOfRoomsOutsideHouse() {
        // Create double of HouseID and set its behaviour
        HouseID houseIDDouble = mock(HouseID.class);
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(true);

        // Create room doubles and set behaviour to obtain the list of all rooms
        List<Room> listOfRoomsInRepo = new ArrayList<>();
        Room roomDouble1 = mock(Room.class);
        Room roomDouble2 = mock(Room.class);
        Room roomDouble3 = mock(Room.class);
        listOfRoomsInRepo.add(roomDouble1);
        listOfRoomsInRepo.add(roomDouble2);
        listOfRoomsInRepo.add(roomDouble3);
        when(roomRepositoryDouble.findByHouseID(houseIDDouble)).thenReturn(listOfRoomsInRepo);

        // Create roomID doubles and set behaviour to obtain the roomIDs for the list to return
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);
        RoomID roomIDDouble3 = mock(RoomID.class);
        when(roomDouble1.identity()).thenReturn(roomIDDouble1);
        when(roomDouble2.identity()).thenReturn(roomIDDouble2);
        when(roomDouble3.identity()).thenReturn(roomIDDouble3);

        // Create Room Dimensions and set behaviour
        RoomDimensions roomDimensionsDouble1 = mock(RoomDimensions.class);
        when(roomDimensionsDouble1.getHeight()).thenReturn(3.0);
        when(roomDouble1.getRoomDimensions()).thenReturn(roomDimensionsDouble1);

        RoomDimensions roomDimensionsDouble2 = mock(RoomDimensions.class);
        when(roomDimensionsDouble2.getHeight()).thenReturn(3.0);
        when(roomDouble2.getRoomDimensions()).thenReturn(roomDimensionsDouble2);

        RoomDimensions roomDimensionsDouble3 = mock(RoomDimensions.class);
        when(roomDimensionsDouble3.getHeight()).thenReturn(3.0);
        when(roomDouble3.getRoomDimensions()).thenReturn(roomDimensionsDouble3);

        // Act
        List<RoomID> result = roomServices.getListOfRoomsInsideOrOutsideHouse(houseIDDouble, false);

        // Assert
        assertEquals(0, result.size(), "List size should be 0");
    }

    /**
     * Test method to get rooms in a House
     * The corresponding house does not exist in the repository.
     */

    @Test
    void failGetRoomsByHouseIDNonExistingHouseID() {
        // Create double of HouseID and set its behaviour
        HouseID houseIDDouble = mock(HouseID.class);
        when(houseRepositoryDouble.containsEntityByID(houseIDDouble)).thenReturn(false); // house does not exist

        // Create room doubles and set behaviour to obtain the list of all rooms
        List<Room> listOfRoomsInRepo = new ArrayList<>();
        Room roomDouble1 = mock(Room.class);
        Room roomDouble2 = mock(Room.class);
        listOfRoomsInRepo.add(roomDouble1);
        listOfRoomsInRepo.add(roomDouble2);
        when(roomRepositoryDouble.findByHouseID(houseIDDouble)).thenReturn(listOfRoomsInRepo);

        // Create roomID doubles and set behaviour to obtain the roomIDs for the list to return
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);
        when(roomDouble1.identity()).thenReturn(roomIDDouble1);
        when(roomDouble2.identity()).thenReturn(roomIDDouble2);

        // Assert
        assertThrows(HouseNotFoundException.class, () -> roomServices.getListOfRoomsInsideOrOutsideHouse(houseIDDouble, true));
    }

    /**
     * Test to find info of a room by its RoomID.
     * Should return an InternalRoomDTO object.
     */

    @Test
    void successFindRoomIDInfo() {
        // Create double of RoomID and set its behaviour
        RoomID roomIDDouble = mock(RoomID.class);

        // Create room double and set behaviour to obtain the room info
        Room roomDouble = mock(Room.class);
        when(roomRepositoryDouble.findEntityByID(roomIDDouble)).thenReturn(Optional.of(roomDouble));

        // Create InternalRoomDTO double and set behaviour to obtain the room info
        InternalRoomDTO result = roomServices.findRoomIDInfo(roomIDDouble);

        // Assert
        assertNotNull(result);
    }

    @Test
    void failFindRoomIDInfo() {
        // Create double of RoomID and set its behaviour
        RoomID roomIDDouble = mock(RoomID.class);

        // Create room double and set bhaviour to obtain the room info
        when(roomRepositoryDouble.findEntityByID(roomIDDouble)).thenReturn(Optional.empty());

        // Assert
        assertThrows(RoomNotFoundException.class, () -> roomServices.findRoomIDInfo(roomIDDouble));
    }

}
