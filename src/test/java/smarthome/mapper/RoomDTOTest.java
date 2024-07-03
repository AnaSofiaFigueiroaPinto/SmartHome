package smarthome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code RoomDTO} class.
 */
class RoomDTOTest {

    /**
     * Test method for the {@code RoomDTO} constructor with all parameters.
     * Verifies if the constructor sets the room name, floor number, room length, room width and room height correctly.
     */
    @Test
    void validConstructorRoomDTOWithAllParameters() {
        // Arrange
        String roomName = "Bedroom";
        int floorNumber = 1;
        double roomLength = 2.0;
        double roomWidth = 3.0;
        double roomHeight = 2.0;

        // Act
        RoomDTO roomDTO = new RoomDTO(roomName, floorNumber, roomLength, roomWidth, roomHeight);

        // Assert
        assertEquals(roomName, roomDTO.roomName);
        assertEquals(floorNumber, roomDTO.floorNumber);
        assertEquals(roomLength, roomDTO.roomLength);
        assertEquals(roomWidth, roomDTO.roomWidth);
        assertEquals(roomHeight, roomDTO.roomHeight);
    }

    /**
     * Test method for the {@code RoomDTO} constructor with only the room name (room ID).
     * Verifies if the constructor sets the room name correctly.
     */
    @Test
    void validConstructorRoomDTOWithRoomNameOnly() {
        // Arrange
        String roomName = "Bedroom";

        // Act
        RoomDTO roomDTO = new RoomDTO(roomName);

        // Assert
        assertEquals(roomName, roomDTO.roomName);
    }

    /**
     * Test method for the {@code RoomDTO} empty constructor.
     */
    @Test
    void validNullConstructor () {
        // Act
        RoomDTO roomDTO = new RoomDTO();

        // Assert
        assertNull(roomDTO.roomName);

    }

    /**
     * Test method for the {@code RoomDTO} constructor with all parameters.
     * Verifies if the constructor sets the room name, floor number, room length, room width and room height correctly.
     */
    @Test
    void testEquals(){
        RoomDTO roomDTO = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        assertEquals(roomDTO, roomDTO2);
    }

    /**
     * Test method for the {@code equals} method of the {@code RoomDTO} class with the same object.
     * Verifies if an object is equal to itself.
     */
    @Test
    void testEqualsWithSameObject() {
        RoomDTO roomDTO = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        assertEquals(roomDTO, roomDTO);
    }

    /**
     * Test method for the {@code equals} method of the {@code RoomDTO} class with a null object.
     * Verifies if an object is not equal to null.
     */
    @Test
    void testEqualsWithNull() {
        RoomDTO roomDTO = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        assertNotEquals(roomDTO, null);
    }

    /**
     * Test method for the {@code equals} method of the {@code RoomDTO} class with a different class.
     * Verifies if an object is not equal to an object of a different class.
     */
    @Test
    void testEqualsWithDifferentClass() {
        RoomDTO roomDTO = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        assertNotEquals(roomDTO, new Object());
    }

    /**
     * Test method for the {@code equals} method of the {@code RoomDTO} class with different room names.
     * Verifies if two objects with different room names are not equal.
     */
    @Test
    void testEqualsWithDifferentRoomName() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Bed Room", 1, 5.0, 4.0, 3.0);
        assertNotEquals(roomDTO1, roomDTO2);
    }

    /**
     * Test method for the {@code equals} method of the {@code RoomDTO} class with different floor numbers.
     * Verifies if two objects with different floor numbers are not equal.
     */
    @Test
    void testEqualsWithDifferentFloorNumber() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 2, 5.0, 4.0, 3.0);
        assertNotEquals(roomDTO1, roomDTO2);
    }

    /**
     * Test method for the {@code equals} method of the {@code RoomDTO} class with different room lengths.
     * Verifies if two objects with different room lengths are not equal.
     */
    @Test
    void testEqualsWithDifferentRoomLength() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 1, 6.0, 4.0, 3.0);
        assertNotEquals(roomDTO1, roomDTO2);
    }

    /**
     * Test method for the {@code equals} method of the {@code RoomDTO} class with different room widths.
     * Verifies if two objects with different room widths are not equal.
     */
    @Test
    void testEqualsWithDifferentRoomWidth() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 1, 5.0, 5.0, 3.0);
        assertNotEquals(roomDTO1, roomDTO2);
    }

    /**
     * Test method for the {@code equals} method of the {@code RoomDTO} class with different room heights.
     * Verifies if two objects with different room heights are not equal.
     */
    @Test
    void testEqualsWithDifferentRoomHeight() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 1, 5.0, 4.0, 4.0);
        assertNotEquals(roomDTO1, roomDTO2);
    }

    /**
     * Test method for the {@code equals} method of the {@code RoomDTO} class with equal objects.
     * Verifies if two equal objects are equal.
     */
    @Test
    void testEqualsWithEqualObjects() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        assertEquals(roomDTO1, roomDTO2);
    }

    /**
     * Test method for the {@code hashCode} method of the {@code RoomDTO} class with equal objects.
     * Verifies if the hash codes of two equal objects are the same.
     */
    @Test
    void testHashCodeWithEqualObjects() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        assertEquals(roomDTO1.hashCode(), roomDTO2.hashCode());
    }

    /**
     * Test method for the {@code hashCode} method of the {@code RoomDTO} class with different room names.
     * Verifies if the hash codes of two objects with different room names are different.
     */
    @Test
    void testHashCodeWithDifferentRoomName() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Bed Room", 1, 5.0, 4.0, 3.0);
        assertNotEquals(roomDTO1.hashCode(), roomDTO2.hashCode());
    }

    /**
     * Test method for the {@code hashCode} method of the {@code RoomDTO} class with equal objects.
     * Verifies if the hash codes of two equal objects are the same.
     */
    @Test
    void testHashCodeWithDifferentFloorNumber() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 2, 5.0, 4.0, 3.0);
        assertNotEquals(roomDTO1.hashCode(), roomDTO2.hashCode());
    }

    /**
     * Test method for the {@code hashCode} method of the {@code RoomDTO} class with different room lengths.
     * Verifies if the hash codes of two objects with different room lengths are different.
     */
    @Test
    void testHashCodeWithDifferentRoomLength() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 1, 6.0, 4.0, 3.0);
        assertNotEquals(roomDTO1.hashCode(), roomDTO2.hashCode());
    }

    /**
     * Test method for the {@code hashCode} method of the {@code RoomDTO} class with different room widths.
     * Verifies if the hash codes of two objects with different room widths are different.
     */
    @Test
    void testHashCodeWithDifferentRoomWidth() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 1, 5.0, 5.0, 3.0);
        assertNotEquals(roomDTO1.hashCode(), roomDTO2.hashCode());
    }

    /**
     * Test method for the {@code hashCode} method of the {@code RoomDTO} class with different room heights.
     * Verifies if the hash codes of two objects with different room heights are different.
     */
    @Test
    void testHashCodeWithDifferentRoomHeight() {
        RoomDTO roomDTO1 = new RoomDTO("Living Room", 1, 5.0, 4.0, 3.0);
        RoomDTO roomDTO2 = new RoomDTO("Living Room", 1, 5.0, 4.0, 4.0);
        assertNotEquals(roomDTO1.hashCode(), roomDTO2.hashCode());
    }

}