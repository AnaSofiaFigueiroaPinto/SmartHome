package smarthome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code DeviceDTO} class.
 */
class DeviceDTOTest {

    /**
     * Test method for the {@code DeviceDTO} constructor with all parameters.
     * Verifies if the constructor sets the device name correctly.
     */
    @Test
    void validConstructorDeviceDTOWithAllParameters() {
        String deviceName = "Heater";
        String model = "H8115";
        String status = "ACTIVE";
        String roomName = "Bedroom";

        DeviceDTO deviceDTO = new DeviceDTO(deviceName, model, status, roomName);
        assertNotNull(deviceDTO);
        assertEquals(deviceName, deviceDTO.deviceName);
        assertEquals(model, deviceDTO.model);
        assertEquals(status, deviceDTO.status);
    }

    /**
     * Test method for the {@code DeviceDTO} constructor with only the device name (deviceID).
     * Verifies if the constructor sets the device name correctly.
     */
    @Test
    void validConstructorDeviceDTOWithDeviceNameOnly() {
        String deviceName = "Heater";

        DeviceDTO deviceDTO = new DeviceDTO(deviceName);
        assertNotNull(deviceDTO);
        assertEquals(deviceName, deviceDTO.deviceName);
    }

    /**
     * Tests if the equals method returns true when comparing an object with itself.
     */
    @Test
    void successEqualsSameObject() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        assertEquals(deviceDTO, deviceDTO);
    }

    /**
     * Tests if the equals method returns false when comparing with an object of another class.
     */
    @Test
    void successEqualsDifferentClass() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        assertNotEquals(deviceDTO, new Object());
    }

    /**
     * Tests if the equals method returns false when comparing with a null object.
     */
    @Test
    void equalsNull() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        assertNotEquals(deviceDTO, null);
    }

    /**
     * Tests if the equals method returns true when comparing two objects with the same attributes.
     */
    @Test
    void successEqualsSameAttributes() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        DeviceDTO deviceDTO2 = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        assertEquals(deviceDTO, deviceDTO2);
    }

    /**
     * Tests if the equals method returns false when comparing two objects with different attributes.
     */
    @Test
    void successEqualsDifferentAttributes() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        DeviceDTO deviceDTO2 = new DeviceDTO("Heater", "H8115", "INACTIVE", "Bedroom");
        assertNotEquals(deviceDTO, deviceDTO2);
    }

    /**
     * Tests if the equals method returns false when comparing two objects with different device names.
     */
    @Test
    void successEqualsDifferentDeviceName() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        DeviceDTO deviceDTO2 = new DeviceDTO("Fan", "H8115", "ACTIVE", "Bedroom");
        assertNotEquals(deviceDTO, deviceDTO2);
    }

    /**
     * Tests if the equals method returns false when comparing two objects with different models.
     */
    @Test
    void successEqualsDifferentModel() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        DeviceDTO deviceDTO2 = new DeviceDTO("Heater", "H8116", "ACTIVE", "Bedroom");
        assertNotEquals(deviceDTO, deviceDTO2);
    }

    /**
     * Tests if the equals method returns false when comparing two objects with different statuses.
     */
    @Test
    void successEqualsDifferentStatus() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        DeviceDTO deviceDTO2 = new DeviceDTO("Heater", "H8115", "INACTIVE", "Bedroom");
        assertNotEquals(deviceDTO, deviceDTO2);
    }

    /**
     * Tests if the equals method returns false when comparing two objects with different room names.
     */
    @Test
    void successEqualsDifferentRoomName() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        DeviceDTO deviceDTO2 = new DeviceDTO("Heater", "H8115", "ACTIVE", "Living Room");
        assertNotEquals(deviceDTO, deviceDTO2);
    }

    /**
     * Tests if the hashCode method returns the same value for two objects with the same attributes.
     */
    @Test
    void successHashCode() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        DeviceDTO deviceDTO2 = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        assertEquals(deviceDTO.hashCode(), deviceDTO2.hashCode());
    }

    /**
     * Tests if the hashCode method returns different values for two objects with different attributes.
     */
    @Test
    void successtHashCodeDifferentAttributes() {
        DeviceDTO deviceDTO = new DeviceDTO("Heater", "H8115", "ACTIVE", "Bedroom");
        DeviceDTO deviceDTO2 = new DeviceDTO("Heater", "H8116", "INACTIVE", "Bathroom");
        assertNotEquals(deviceDTO.hashCode(), deviceDTO2.hashCode());
    }

}