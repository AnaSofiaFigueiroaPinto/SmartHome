package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomFloorTest {

	/**
	 * Test case that verifies that two {@link RoomFloor} objects with the same value are considered the same.
	 */
	@Test
	void equalRooms() {
		int validRoomFloor = 3;
		RoomFloor roomFloor = new RoomFloor(validRoomFloor);
		RoomFloor roomFloor1 = new RoomFloor(validRoomFloor);
		assertEquals(roomFloor, roomFloor1);
	}

	/**
	 * Test case that verifies that {@link RoomFloor} object considered the same as itself.
	 */
	@Test
	void equalsRoomItself() {
		RoomFloor floor1 = new RoomFloor(1);
        assertEquals(floor1, floor1);
	}

	/**
	 * Test case that verifies that two {@link RoomFloor} objects with different values aren't considered equal.
	 */
	@Test
	void equalsRoomFalse() {
		RoomFloor roomFloor = new RoomFloor(2);
		RoomFloor roomFloor1 = new RoomFloor(3);
		assertNotEquals(roomFloor, roomFloor1);
	}

	/**
	 * Tests the {@link RoomFloor#equals(Object)} method with a null object.
	 */
	@Test
	void equalsNullRoom() {
		RoomFloor floor = new RoomFloor(1);
        assertNotEquals(floor, null);
	}

	/**
	 * Tests the {@link RoomFloor#equals(Object)} method with an object of a different class.
	 */
	@Test
	void equalsDifferentClass() {
		RoomFloor floor = new RoomFloor(1);
		Object obj = new Object();
        assertNotEquals(floor, obj);
	}

	/**
	 * Test case that verifies if a String is retrieved when calling method toString.
	 */
	@Test
	void verifyToString() {
		String expected = "FloorNumber{floorNumber=3}";
		RoomFloor roomFloor = new RoomFloor(3);
		String result = roomFloor.toString();
		assertEquals(expected, result);
	}

	/**
	 * Test case that verifies if roomFloor is successfully retrieved
	 */
	@Test
	void getRoomFloor() {
		RoomFloor roomFloor = new RoomFloor(3);
		int expected = 3;
		int result = roomFloor.getRoomFloor();
		assertEquals(expected, result);
	}

	@Test
	void validHashCode() {
		RoomFloor roomFloor1 = new RoomFloor(3);
		RoomFloor roomFloor2 = new RoomFloor(3);
		assertEquals(roomFloor1.hashCode(), roomFloor2.hashCode());
	}

	@Test
	void invalidHashCode() {
		RoomFloor roomFloor1 = new RoomFloor(3);
		RoomFloor roomFloor2 = new RoomFloor(4);
		assertNotEquals(roomFloor1.hashCode(), roomFloor2.hashCode());
	}
}