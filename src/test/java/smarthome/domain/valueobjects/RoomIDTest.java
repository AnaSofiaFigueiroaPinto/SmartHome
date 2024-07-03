package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code RoomID} class.
 */
class RoomIDTest {
    /**
     * Declaring the attributes that will be used in test cases.
     */
    private static final String validId1 = "Kitchen";
    private static final String validId2 = "Living Room";

    /**
     * Test if the hash code of two RoomID with the same id is the same.
     */
    @Test
    void validHashCode() {
        RoomID roomID1 = new RoomID(validId1);
        RoomID roomID2 = new RoomID(validId1);
        assertEquals(roomID1.hashCode(), roomID2.hashCode());
    }

    /**
     * Test if the hash code of an instance of RoomID isn't equal to a different instance of RoomID.
     */
    @Test
    void invalidHashCode() {
        RoomID roomID1 = new RoomID(validId1);
        RoomID roomID2 = new RoomID(validId2);
        assertNotEquals(roomID1.hashCode(), roomID2.hashCode());
    }

    /**
     * Test case to verify the equals method of the {@link RoomID} class when comparing two RoomID objects with the same valid ID.
     * Returns true if object is the same or have the same ID.
     */
    @Test
    void successEqualsRoomID() {
        RoomID roomID1 = new RoomID(validId1);
        RoomID roomID2 = new RoomID(validId1);
        assertEquals(roomID1, roomID2);
        assertEquals(roomID1, roomID1);
    }

    /**
     * Test case to verify the equals method of the {@link RoomID} class when comparing two different RoomID objects.
     * Verifies that two RoomID objects constructed with different IDs are not equal.
     */
    @Test
    void failEqualsDifferentRoomID() {
        RoomID roomID1 = new RoomID(validId1);
        RoomID roomID2 = new RoomID(validId2);
        assertNotEquals(roomID1, roomID2);
    }

    /**
     * Test case to verify the equals method of the {@link RoomID} class when comparing with a null object.
     * Verifies that calling equals method with a null object returns false.
     */
    @Test
    void failEqualsNull() {
        RoomID roomID = new RoomID(validId1);
        assertNotEquals(roomID, null);
    }

    /**
     * Test case to verify the equals method of the {@link RoomID} class when comparing with an object of a different class.
     * Verifies that calling equals method with an object of a different class returns false.
     */
    @Test
    void failEqualsDifferentClass() {
        RoomID roomID = new RoomID(validId1);
        Object obj = new Object();
        assertNotEquals(roomID, obj);
    }

    /**
     * Test if invalid constructor of class RoomId with empty id.
     */
    @Test
    void testInvalidConstructorEmptyId() {
        assertThrows(IllegalArgumentException.class, () -> new RoomID(""));
    }

    /**
     * Test if invalid constructor of class RoomId with
     * null id. Throws IllegalArgumentException when id is null.
     */
    @Test
    void testInvalidConstructorNullId() {
        assertThrows(IllegalArgumentException.class, () -> new RoomID(null));
    }

    /**
     * Test if invalid constructor of class RoomId with blank id.
     * Throws IllegalArgumentException with a message if blank id.
     */
    @Test
    void testInvalidConstructorBlankId() {
        assertThrows(IllegalArgumentException.class, () -> new RoomID("  "));
    }

    /**
     * Test if valid constructor of class RoomId.
     */
    @Test
    void testRoomValidConstructor() {
        new RoomID(validId1);
        assertNotNull(validId1);
    }

    /**
     * Test case that verifies if RoomID is successfully retrieved (string roomName);
     */
    @Test
    void testToString() {
        RoomID roomID = new RoomID(validId1);
        String expected = "Kitchen";
        String result = roomID.toString();
        assertEquals(expected, result);
    }
}