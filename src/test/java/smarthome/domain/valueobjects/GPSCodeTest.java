package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code GPSCode} class.
 */
class GPSCodeTest {
    /**
     * Test the successful creation of a GPSCode object.
     * Verifies that no exception was thrown.
     */
    @Test
    void validConstructor() {
        assertDoesNotThrow(() -> new GPSCode(41.14961, -8.61099));
    }

    /**
     * Test the failed creation of a GPSCode object with invalid latitude.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorLatitudeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new GPSCode(-91, -8.61099));
    }

    /**
     * Test the failed creation of a GPSCode object with invalid latitude.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorLatitudePositive() {
        assertThrows(IllegalArgumentException.class, () -> new GPSCode(91, -8.61099));
    }

    /**
     * Test the failed creation of a GPSCode object with invalid longitude.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorLongitudeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new GPSCode(41.14961, -181));
    }

    /**
     * Test the failed creation of a GPSCode object with invalid longitude.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorLongitudePositive() {
        assertThrows(IllegalArgumentException.class, () -> new GPSCode(41.14961, 181));
    }

    /**
     * Test the successful creation of a GPSCode object with border values.
     * Verifies that no exception was thrown.
     */
    @Test
    void validConstructorLatitudeBorderValues() {
        assertDoesNotThrow(() -> new GPSCode(-90, -8.61099));
        assertDoesNotThrow(() -> new GPSCode(90, -8.61099));
    }

    /**
     * Test the successful creation of a GPSCode object with border values.
     * Verifies that no exception was thrown.
     */
    @Test
    void validConstructorLongitudeBorderValues() {
        assertDoesNotThrow(() -> new GPSCode(41.14961, -180));
        assertDoesNotThrow(() -> new GPSCode(41.14961, 180));
    }

    /**
     * Test the successful retrieval of the latitude.
     * Verifies if the latitude is the same as the one given in the constructor.
     */
    @Test
    void successGetLatitude() {
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        assertEquals(41.14961, gpsCode.getLatitude());
    }

    /**
     * Test the successful retrieval of the longitude.
     * Verifies if the longitude is the same as the one given in the constructor.
     */
    @Test
    void successGetLongitude() {
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        assertEquals(-8.61099, gpsCode.getLongitude());
    }

    /**
     * Test the successful comparison of the same GPSCode object.
     * Returns true if object is the same or have the same ID.
     */
    @Test
    void successEqualsSameObject() {
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        GPSCode gpsCode2 = new GPSCode(41.14961, -8.61099);
        assertEquals(gpsCode, gpsCode2);
        assertEquals(gpsCode, gpsCode);
    }

    /**
     * Failed test of comparison of two GPSCode objects.
     * Verifies that the objects are not equal.
     */
    @Test
    void failEqualsDifferentGPSCode() {
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        GPSCode gpsCode2 = new GPSCode(45.14961, -10.61099);
        assertNotEquals(gpsCode,gpsCode2);
    }

    /**
     * Tests the equals method when the latitude values are different.
     * <p>
     * This test case verifies that when two GPSCode objects with different latitude values are compared,
     * the equals method returns false.
     */
    @Test
    void testEquals_WhenDifferentLatitude() {
        GPSCode gpsCode1 = new GPSCode(1.0, 2.0);
        GPSCode gpsCode2 = new GPSCode(3.0, 2.0);

        // Act and Assert
        assertNotEquals(gpsCode1, gpsCode2);
    }

    /**
     * Tests the equals method when the longitude values are different.
     * <p>
     * This test case verifies that when two GPSCode objects with different longitude values are compared,
     * the equals method returns false.
     */
    @Test
    void testEquals_WhenDifferentLongitude() {
        GPSCode gpsCode1 = new GPSCode(1.0, 2.0);
        GPSCode gpsCode2 = new GPSCode(1.0, 3.0);

        // Act and Assert
        assertNotEquals(gpsCode1, gpsCode2);
    }


    /**
     * Test comparison between a GPSCode object and an object of a different class.
     */
    @Test
    void failEqualsDifferentClass() {
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        Object obj = new Object();
        assertNotEquals(gpsCode,obj);
    }

    /**
     * Failed test if the GPSCode object is null.
     */
    @Test
    void failEqualsObjectNull() {
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        assertNotEquals(null,gpsCode);
    }

    /**
     * Test the successful comparison of two equal GPSCode objects.
     * Verifies that the objects are equal.
     */
    @Test
    void successEqualsDifferentObjectsSameContent() {
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        GPSCode gpsCode2 = new GPSCode(41.14961, -8.61099);
        assertEquals(gpsCode, gpsCode2);
    }

    /**
     * Test if the hash code of two GPSCode objects with the same attributes are the same.
     */
    @Test
    void validHashCode() {
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        GPSCode gpsCode2 = new GPSCode(41.14961, -8.61099);
        assertEquals(gpsCode.hashCode(), gpsCode2.hashCode());
    }

    /**
     * Test if the hash code of an instance of GPSCode isn't equal to a different instance of GPSCode.
     */
    @Test
    void invalidHashCode() {
        GPSCode gpsCode = new GPSCode(41.14950, -8.61099);
        GPSCode gpsCode2 = new GPSCode(41.14961, -8.61099);
        assertNotEquals(gpsCode.hashCode(), gpsCode2.hashCode());
    }

    /**
     * Test the successful conversion of the GPSCode object to a string.
     */
    @Test
    void successToString() {
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        assertEquals("Latitude: 41.14961, Longitude: -8.61099", gpsCode.toString());
    }

    /**
     * Test to verify that the equals method returns false when comparing two different Location objects.
     * Two Location objects are created with different Address and GPSCode values.
     * The test passes if the equals method returns false when comparing these two objects.
     */
    @Test
    void equalsDifferentForDifferentObjects() {
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        GPSCode gpsCode1 = new GPSCode(41.14961, -8.61099);
        Location location1 = new Location(address1, gpsCode1);

        Address address2 = new Address("25 RUE DE L EGLISE ", "321", "75001", "Paris", "France");
        GPSCode gpsCode2 = new GPSCode(42.14961, -9.61099);
        Location location2 = new Location(address2, gpsCode2);

        assertNotEquals(location1, location2);
    }

}