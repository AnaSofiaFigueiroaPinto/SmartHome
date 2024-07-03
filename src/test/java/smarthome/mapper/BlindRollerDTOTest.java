package smarthome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlindRollerDTOTest {
    /**
     * Test case for the {@code BlindRollerDTO} constructor.
     */
    @Test
    void validConstructorBlindRollerDTODTO() {
        BlindRollerDTO blindRollerDTO = new BlindRollerDTO("Device1", "Room1");
        assertEquals("Device1", blindRollerDTO.deviceName);
        assertEquals("Room1", blindRollerDTO.roomName);
    }

    /**
     * Test case for the {@code BlindRollerDTO} empty constructor.
     */

    @Test
    void validEmptyConstructorBlindRollerDTODTO() {
        BlindRollerDTO blindRollerDTO = new BlindRollerDTO();
        assertNull(blindRollerDTO.deviceName);
        assertNull(blindRollerDTO.roomName);
    }

    /**
     * Test for the equals method with two BlindRollerDTO objects that have the same attributes.
     */
    @Test
    void successEquals_SameObjects() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device1", "Room1");

        assertTrue(blindRollerDTO1.equals(blindRollerDTO2));
    }

    /**
     * Test for the equals method with the same BlindRollerDTO object.
     */
    @Test
    void successEquals_Reflexivity() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        assertTrue(blindRollerDTO1.equals(blindRollerDTO1));
    }

    /**
     * Test for the equals method with a null object.
     */
    @Test
    void successEquals_NullObject() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        assertFalse(blindRollerDTO1.equals(null));
    }

    /**
     * Test for the equals method with an object of a different class.
     */
    @Test
    void successEquals_DifferentClass() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        assertFalse(blindRollerDTO1.equals(new Object()));
    }

    /**
     * Test for the equals method with two BlindRollerDTO objects with different functionality names.
     */
    @Test
    void successEquals_DifferentFunctionalityName() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device2", "Room2");

        assertFalse(blindRollerDTO1.equals(blindRollerDTO2));
    }

    /**
     * Test for the equals method with two BlindRollerDTO objects with different room names.
     */
    @Test
    void equals_DifferentDeviceNameSameRoomName() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device2", "Room1");

        assertFalse(blindRollerDTO1.equals(blindRollerDTO2));
    }

    /**
     * Test for the equals method with two BlindRollerDTO objects with different room names.
     */
    @Test
    void equals_SameDeviceNameDifferentRoomName() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device1", "Room2");

        assertFalse(blindRollerDTO1.equals(blindRollerDTO2));
    }

    /**
     * Test for the equals method with two BlindRollerDTO objects with different room names (one of them null).
     */
    @Test
    void equals_NullDeviceName() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO(null, "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device1", "Room1");

        assertFalse(blindRollerDTO1.equals(blindRollerDTO2));
    }

    /**
     * Test for the equals method with two BlindRollerDTO objects with different room names (one of them null).
     */

    @Test
    void equals_NullRoomName() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", null);
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device1", "Room1");

        assertFalse(blindRollerDTO1.equals(blindRollerDTO2));
    }

    /**
     * Test for the equals method with two BlindRollerDTO objects with both room names null.
     */

    @Test
    void equals_BothNullRoomNameDifferentDeviceName() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", null);
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device2", null);

        assertFalse(blindRollerDTO1.equals(blindRollerDTO2));
    }

    /**
     * Test for the equals method with two BlindRollerDTO objects with both room names null.
     */
    @Test
    void equals_BothNullDeviceNameDifferentRoomName() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO(null, "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO(null, "Room2");

        assertFalse(blindRollerDTO1.equals(blindRollerDTO2));
    }

    /**
     * Test for the hashCode method with two BlindRollerDTO objects that have the same attributes.
     */
    @Test
    void successHashCode() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device1", "Room1");
        assertEquals(blindRollerDTO1.hashCode(), blindRollerDTO2.hashCode());
    }

    /**
     * Test for the hashCode method with two BlindRollerDTO objects that have different device names.
     */
    @Test
    void hashCode_DifferentObjects() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device2", "Room1");
        assertNotEquals(blindRollerDTO1.hashCode(), blindRollerDTO2.hashCode());
    }

    /**
     * Test for the hashCode method with two BlindRollerDTO objects that have different room names.
     */
    @Test
    void hashCode_DifferentRoomName() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device1", "Room2");
        assertNotEquals(blindRollerDTO1.hashCode(), blindRollerDTO2.hashCode());
    }

    /**
     * Test for the hashCode method with two BlindRollerDTO objects when both fields are null
     */
    @Test
    void hashCode_BothNull() {
        BlindRollerDTO blindRollerDTO = new BlindRollerDTO(null, null);
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO(null, null);
        assertEquals(blindRollerDTO.hashCode(), blindRollerDTO2.hashCode());
    }

    /**
     * Test for the hashCode method with two BlindRollerDTO objects when deviceNames fields are null
     */
    @Test
    void hashCode_OneField() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO("Device1", null);
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO("Device1", null);
        assertEquals(blindRollerDTO1.hashCode(), blindRollerDTO2.hashCode());
    }

    /**
     * Test for the hashCode method with two BlindRollerDTO objects when deviceNames fields are null
     */
    @Test
    void hashCode_OnlyRoomName() {
        BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO(null, "Room1");
        BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO(null, "Room1");
        assertEquals(blindRollerDTO1.hashCode(), blindRollerDTO2.hashCode());
    }

}