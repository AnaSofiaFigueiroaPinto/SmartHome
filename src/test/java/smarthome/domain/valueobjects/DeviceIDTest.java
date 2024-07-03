package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code DeviceID} class.
 */
class DeviceIDTest {

    private static final String strDeviceName1 = "device1";
    private static final String strDeviceName2 = "device2";

    /**
     * Test case that checks that a DeviceID is successfully created.
     */
    @Test
    void validDeviceId() {
        DeviceID deviceID = new DeviceID(strDeviceName1);
        assertNotNull(deviceID);
    }

    /**
     * Test case that checks that a DeviceID can't be created with a null name.
     */
    @Test
    void invalidIdDeviceNameNull() {
        assertThrows(IllegalArgumentException.class, () -> new DeviceID(null));
    }

    /**
     * Test case that checks that a DeviceID can't be created with a blank name.
     */
    @Test
    void invalidIdDeviceNameBlank() {
        assertThrows(IllegalArgumentException.class, () -> new DeviceID(""));
    }

    /**
     * Test case that checks that a DeviceID can't be created with an empty name.
     */
    @Test
    void invalidIdDeviceNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new DeviceID("  "));
    }

    /**
     * Test case to verify the equals method of the {@link DeviceID} class when comparing two DeviceID objects with the same valid ID.
     * Returns true if object is the same or have the same ID.
     */
    @Test
    void testEqualsTrue() {
        DeviceID deviceAid = new DeviceID(strDeviceName1);
        DeviceID deviceBid = new DeviceID(strDeviceName1);
        assertEquals(deviceAid, deviceBid);
        assertEquals(deviceAid, deviceAid);
    }

    /**
     * Test case to verify the equals method of the {@link DeviceID} class when comparing two different DeviceID objects.
     * Verifies that two DeviceID objects constructed with different IDs are not equal.
     */
    @Test
    void testEqualsFalseDifferentDeviceNames() {
        DeviceID deviceAid = new DeviceID(strDeviceName1);
        DeviceID deviceBid = new DeviceID(strDeviceName2);
        assertNotEquals(deviceAid, deviceBid);
    }

    /**
     * Test case to verify the equals method of the {@link DeviceID} class when comparing with a null object.
     * Verifies that calling equals method with a null object returns false.
     */
    @Test
    void testEqualsFalseNullDeviceID() {
        DeviceID deviceID = new DeviceID(strDeviceName1);
        assertNotEquals(deviceID, null);
    }

    /**
     * Test case to verify the equals method of the {@link DeviceID} class when comparing with an object of a different class.
     * Verifies that calling equals method with an object of a different class returns false.
     */
    @Test
    void testEqualsFalseDifferentClass() {
        DeviceID deviceID = new DeviceID(strDeviceName1);
        Object obj = new Object();
        assertNotEquals(deviceID, obj);
    }

    /**
     * Test if the hash code of two DeviceID with the same id attribute is the same.
     */
    @Test
    void testHashCodeEqual() {
        DeviceID deviceAid = new DeviceID(strDeviceName1);
        DeviceID deviceBid = new DeviceID(strDeviceName1);
        assertEquals(deviceAid.hashCode(), deviceBid.hashCode());
    }

    /**
     * Test if the hash code of an instance of DeviceID isn't equal to a different instance of DeviceID.
     */
    @Test
    void testHashCodeNotEqual() {
        DeviceID deviceAid = new DeviceID(strDeviceName1);
        DeviceID deviceBid = new DeviceID(strDeviceName2);
        assertNotEquals(deviceAid.hashCode(), deviceBid.hashCode());
    }

    /**
     * Test case that verifies that the method {@code toString} returns a String with the id of a device.
     */
    @Test
    void testToString() {
        DeviceID deviceID = new DeviceID(strDeviceName1);
        assertEquals(deviceID.toString(), strDeviceName1);
    }

    /**
     * Test case that verifies if DeviceID is successfully retrieved.
     */
    @Test
    void getDeviceID() {
        DeviceID deviceID = new DeviceID("Heater");
        String expected = "Heater";
        String result = deviceID.toString();
        assertEquals(expected, result);
    }
}