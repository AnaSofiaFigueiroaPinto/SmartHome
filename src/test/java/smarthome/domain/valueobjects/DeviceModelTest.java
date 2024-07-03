package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code DeviceModel} class.
 */
class DeviceModelTest {


    private static final String strDeviceModel1 = "H8115";
    private static final String strDeviceModel2 = "MVP5";

    /**
     * Test case that checks if Device Model object is successfully created.
     */
    @Test
    void successDeviceModelCreation() {
        DeviceModel deviceModel = new DeviceModel(strDeviceModel1);
        assertNotNull(deviceModel);
    }

    /**
     * Test case that verifies that a DeviceModel object can't be created if model attribute is null.
     */
    @Test
    void failDeviceModelCreationNullModel() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DeviceModel(null);
        });
    }

    /**
     * Test case that verifies that a DeviceModel object can't be created if model attribute is empty.
     */
    @Test
    void failDeviceModelCreationEmptyModel() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DeviceModel("");
        });
    }

    /**
     * Test case that verifies that a DeviceModel object can't be created if model attribute is blank.
     */
    @Test
    void failDeviceModelCreationBlankModel() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DeviceModel("  ");
        });
    }

    /**
     * Test case to ensure that comparing an object with itself returns true.
     */
    @Test
    void testEqualsSameObject() {
        DeviceModel deviceModel = new DeviceModel(strDeviceModel1);
        assertEquals(deviceModel, deviceModel);
    }

    /**
     * Test case to ensure that comparing with a null object returns false.
     */
    @Test
    void testEqualsNullObject() {
    DeviceModel deviceModel = new DeviceModel(strDeviceModel1);
    assertNotEquals(deviceModel, null);
    }

    /**
     * Test case to ensure that comparing with a different class object returns false.
     */
    @Test
    void testEqualsDifferentClass() {
        Object obj = new Object();
        DeviceModel deviceModel = new DeviceModel(strDeviceModel1);

        // Act & Assert
        assertNotEquals(deviceModel, obj);
    }

    /**
     * Test case to ensure that comparing two objects with the same attributes returns true.
     */
    @Test
    void testEqualsSameAttributes() {
        DeviceModel deviceModelA = new DeviceModel(strDeviceModel1);
        DeviceModel deviceModelB = new DeviceModel(strDeviceModel1);
        assertEquals(deviceModelA, deviceModelB);
    }

    /**
     * Test case to ensure that the hashcode of two objects with the same attributes is the same.
     */
    @Test
    void testHashcodeSameDeviceModels() {
        DeviceModel deviceModelA = new DeviceModel(strDeviceModel1);
        DeviceModel deviceModelB = new DeviceModel(strDeviceModel1);
        assertEquals(deviceModelA, deviceModelB);
        assertEquals(deviceModelA.hashCode(), deviceModelB.hashCode());
    }

    /**
     * Test case to ensure that the hashcode of two objects with different attributes is not the same.
     */
    @Test
    void testHashcodeDifferentDeviceModels() {
        DeviceModel deviceModelA = new DeviceModel(strDeviceModel1);
        DeviceModel deviceModelB = new DeviceModel(strDeviceModel2);
        assertNotEquals(deviceModelA, deviceModelB);
        assertNotEquals(deviceModelA.hashCode(), deviceModelB.hashCode());
    }

    /**
     * Test case that verifies that the method {@code toString} returns a String with the model of a device.
     */
    @Test
    void testToString() {
        DeviceModel deviceModel = new DeviceModel(strDeviceModel1);
        assertEquals(deviceModel.toString(), strDeviceModel1);
    }

}