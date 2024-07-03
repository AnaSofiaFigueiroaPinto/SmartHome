package smarthome.domain.room;

import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

/**
 * This class contains unit tests for the Room class.
 */
class RoomTest {
    /**
     * Test method to verify the construction of a
     * Room object with valid parameters.
     * <p>
     * This test ensures that a Room object can be
     * successfully constructed when valid
     * parameters are provided, including a non-null
     * RoomID, RoomFloor, RoomDimensions,
     * and HouseID. It uses mocking to create doubles
     * for these dependencies and asserts that no exception
     * is thrown during the construction process.
     */
    @Test
    void testConstructRoomWithValidParameters() {
        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        assertDoesNotThrow(() -> new Room(
                roomIdDouble, roomFloorDouble,
                roomDimensionsDouble, houseIdDouble));
    }

    /**
     * Test method to verify successful editing of a Room's configuration.
     * <p>
     * The test asserts that the edit operation returns the edited Room,
     * indicating successful configuration update.
     */
    @Test
    void testSuccessfulEditRoom() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        when(roomFloorDouble.getRoomFloor()).thenReturn(0);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        when(roomDimensionsDouble.getHeight()).thenReturn(4.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(8.0);
        when(roomDimensionsDouble.getLength()).thenReturn(8.0);
        HouseID houseIDDouble = mock(HouseID.class);

        RoomFloor roomFloorDouble2 = mock(RoomFloor.class);
        when(roomFloorDouble2.getRoomFloor()).thenReturn(1);
        RoomDimensions roomDimensionsDouble2 = mock(RoomDimensions.class);
        when(roomDimensionsDouble2.getHeight()).thenReturn(5.0);
        when(roomDimensionsDouble2.getWidth()).thenReturn(10.0);
        when(roomDimensionsDouble2.getLength()).thenReturn(10.0);

        Room room = new Room(roomIDDouble, roomFloorDouble,
                roomDimensionsDouble, houseIDDouble);

        Room expected = new Room(roomIDDouble, roomFloorDouble2,
                roomDimensionsDouble2, houseIDDouble);
        Room result = room.editRoom(roomFloorDouble2,
                roomDimensionsDouble2);

        assertTrue(result.isSameAs(expected),
                "Room should have been edited");
    }

    /**
     * Test method to verify failing to edit a Room's configuration with a
     * null RoomFloor.
     * <p>
     * The test asserts that the edit operation
     * returns Null, indicating that the configuration update failed.
     */
    @Test
    void testFailToEditRoomConfigurationNullRoomFloor() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);

        Room room = new Room(roomIDDouble, roomFloorDouble,
                roomDimensionsDouble, houseIDDouble);

        Room result = room.editRoom(null,
                roomDimensionsDouble);

        assertNull(result);
    }

    /**
     * Test method to verify failing to edit a Room's configuration
     * with null RoomDimensions.
     * <p>
     * The test asserts that the edit operation returns Null,
     * indicating that the configuration update failed.
     */
    @Test
    void testFailToEditRoomConfigurationNullRoomDimensions() {
        RoomID roomIDDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIDDouble = mock(HouseID.class);

        Room room = new Room(roomIDDouble, roomFloorDouble,
                roomDimensionsDouble, houseIDDouble);

        Room result = room.editRoom(roomFloorDouble,
                null);

        assertNull(result);
    }

    /**
     * Test method to verify getting the identity of a Room.
     * <p>
     * The test then asserts that calling the identity
     * method returns the expected
     * RoomID.
     */
    @Test
    void testGetRoomIdentity() {

        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        when(roomIdDouble.toString()).thenReturn("Bedroom");

        Room room = new Room(roomIdDouble, roomFloorDouble,
                roomDimensionsDouble, houseIdDouble);

        assertEquals(roomIdDouble, room.identity());
    }

    /**
     * Test method to verify the functionality of
     * the {@code getRoomFloor()} method in the {@link Room} class.
     * <p>
     * The test then asserts that calling the {@code getRoomFloor()}
     * method returns the expected RoomFloor object.
     */
    @Test
    void testGetRoomFloor() {
        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        when(roomFloorDouble.getRoomFloor()).thenReturn(1);

        Room room = new Room(roomIdDouble, roomFloorDouble,
                roomDimensionsDouble, houseIdDouble);

        assertEquals(roomFloorDouble, room.getRoomFloor());
    }

    /**
     * Test method to verify the functionality of the
     * {@code getRoomDimensions()} method in the {@link Room} class.
     * <p>
     * The test then asserts that calling the
     * {@code getRoomDimensions()} method returns the
     * expected RoomDimensions object.
     */
    @Test
    void testGetRoomDimensions() {
        double length = 2;
        double width = 3;
        double height = 2;

        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        when(roomDimensionsDouble.getLength()).thenReturn(length);
        when(roomDimensionsDouble.getWidth()).thenReturn(width);
        when(roomDimensionsDouble.getHeight()).thenReturn(height);

        Room room = new Room(roomIdDouble, roomFloorDouble,
                roomDimensionsDouble, houseIdDouble);

        assertEquals(roomDimensionsDouble, room.getRoomDimensions());
    }

    /**
     * Test method to verify the functionality of the
     * {@code getHouseID()} method in the {@link Room} class.
     * <p>
     * The test then asserts that calling the
     * {@code getHouseID()} method returns the
     * expected HouseID object.
     */
    @Test
    void testGetHouseID() {
        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        when(houseIdDouble.toString()).thenReturn("Test House ID");

        Room room = new Room(roomIdDouble, roomFloorDouble,
                roomDimensionsDouble, houseIdDouble);

        assertEquals(houseIdDouble, room.getHouseID());
    }

    /**
     * Test method to verify if a Room object is
     * considered the same as another object.
     * <p>
     * The test asserts that calling the {@code isSameAs}
     * method with room2 as the argument returns true,
     * indicating that room1 and room2 reference the same Room instance.
     */
    @Test
    void testVerifyIfRoomIsSameAsObject() {
        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        Room room1 = new Room(roomIdDouble, roomFloorDouble,
                roomDimensionsDouble, houseIdDouble);
        Room room2 = new Room(roomIdDouble, roomFloorDouble,
                roomDimensionsDouble, houseIdDouble);
        assertTrue(room1.isSameAs(room2));
    }

    /**
     * Test method to verify if a Room object is
     * considered the same as itself.
     * <p>
     * The test asserts that calling the {@code isSameAs}
     * method with room as the argument returns true,
     * indicating that room1 as the same reference as itself.
     */
    @Test
    void testVerifyIfRoomIsSameAsItself() {
        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        Room room = new Room(roomIdDouble, roomFloorDouble,
                roomDimensionsDouble, houseIdDouble);
        assertTrue(room.isSameAs(room));
    }

    /**
     * Test method to verify failing when a Room object
     * is not considered the same as another object.
     * <p>
     * The test asserts that calling the {@code isSameAs} method with
     * the other object as the argument returns false, indicating
     * that they do not reference the same Room instance.
     *
     */
    @Test
    void testFailIfRoomIsNotSameAsObject() {
        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(
                RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        Room room = new Room(roomIdDouble, roomFloorDouble,
                roomDimensionsDouble, houseIdDouble);

        Object object = new Object();

        assertFalse(room.isSameAs(object));
    }

    /**
     * Test case to verify that the {@code isSameAs} method returns
     * false when comparing two rooms with different room dimensions.
     */
    @Test
    void testIsSameAsDifferentRoomDimensions() {
        RoomID roomID = mock(RoomID.class);
        RoomFloor roomFloor = mock(RoomFloor.class);
        RoomDimensions roomDimensions1 = mock(
                RoomDimensions.class);
        RoomDimensions roomDimensions2 = mock(
                RoomDimensions.class);
        HouseID houseID = mock(HouseID.class);
        Room room1 = new Room(roomID, roomFloor,
                roomDimensions1, houseID);
        Room room2 = new Room(roomID, roomFloor,
                roomDimensions2, houseID);

        assertFalse(room1.isSameAs(room2));
    }

    /**
     * Test case to verify that the {@code isSameAs} method
     * returns false when comparing a room with a null object.
     */
    @Test
    void testIsSameAsNullComparison() {
        RoomID roomID = mock(RoomID.class);
        RoomFloor roomFloor = mock(RoomFloor.class);
        RoomDimensions roomDimensions = mock(
                RoomDimensions.class);
        HouseID houseID = mock(HouseID.class);
        Room room1 = new Room(roomID, roomFloor,
                roomDimensions, houseID);

        assertFalse(room1.isSameAs(null));
    }

    /**
     * Test case to verify that the {@code isSameAs} method returns false
     * when comparing two rooms with different room IDs.
     */
    @Test
    void testIsSameAsDifferentRoomID() {
        RoomID roomID1 = mock(RoomID.class);
        RoomID roomID2 = mock(RoomID.class);
        RoomFloor roomFloor = mock(RoomFloor.class);
        RoomDimensions roomDimensions = mock(
                RoomDimensions.class);
        HouseID houseID = mock(HouseID.class);
        Room room1 = new Room(roomID1, roomFloor,
                roomDimensions, houseID);
        Room room2 = new Room(roomID2, roomFloor,
                roomDimensions, houseID);

        assertFalse(room1.isSameAs(room2));
    }

    /**
     * Test case to verify that the {@code isSameAs} method
     * returns false when comparing two rooms with different room floors.
     */
    @Test
    void testIsSameAsDifferentRoomFloor() {

        RoomID roomID = mock(RoomID.class);
        RoomFloor roomFloor1 = mock(RoomFloor.class);
        RoomFloor roomFloor2 = mock(RoomFloor.class);
        RoomDimensions roomDimensions = mock(
                RoomDimensions.class);
        HouseID houseID = mock(HouseID.class);
        Room room1 = new Room(roomID, roomFloor1,
                roomDimensions, houseID);
        Room room2 = new Room(roomID, roomFloor2,
                roomDimensions, houseID);

        assertFalse(room1.isSameAs(room2));
    }

    /**
     * Test case to verify that the {@code isSameAs} method
     * returns false when comparing two rooms with different HouseID.
     */
    @Test
    void testIsSameAsDifferentHouseID() {

        RoomID roomID = mock(RoomID.class);
        RoomFloor roomFloor = mock(RoomFloor.class);
        RoomDimensions roomDimensions = mock(
                RoomDimensions.class);
        HouseID houseID1 = mock(HouseID.class);
        HouseID houseID2 = mock(HouseID.class);
        Room room1 = new Room(roomID, roomFloor,
                roomDimensions, houseID1);
        Room room2 = new Room(roomID, roomFloor,
                roomDimensions, houseID2);

        assertFalse(room1.isSameAs(room2));
    }
}