package smarthome.domain.valueobjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code Location} class.
 */
class LocationTest {

    /**
     * Declaring attributes that will be used in test cases.
     */
    private String street;
    private String doorNumber;
    private String zipCode;
    private String city;
    private String country;
    private double latitude;
    private double longitude;


    /**
     * Sets up the test environment by creating a location dependent on GPSCode and Address objects.
     */
    @BeforeEach
    void setUp() {
        street = "Rua do Ouro";
        doorNumber = "123";
        zipCode = "4000-000";
        city = "Porto";
        country = "Portugal";
        latitude = 41.14961;
        longitude = -8.61099;
    }


    /**
     * Tests the successful creation of a Location object.
     */
    @Test
    void successLocationCreation() {
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode = new GPSCode(latitude, longitude);
        assertDoesNotThrow(() -> new Location(address, gpsCode));
    }

    /**
     * Tests the fail attempt of creating a Location object with a null Address.
     */
    @Test
    void failLocationCreationAddressNull() {
        GPSCode gpsCode = new GPSCode(latitude, longitude);
        assertThrows(IllegalArgumentException.class, () -> new Location(null, gpsCode));
    }

    /**
     * Test case fail attempt of creating a Location with a null GPS Code.
     */
    @Test
    void failLocationCreationGpsCodeNull() {
        Address address = new Address(street, doorNumber, zipCode, city, country);
        assertThrows(IllegalArgumentException.class, () -> new Location(address, null));
    }

    /**
     * Tests the equals method with the same Location object, expecting success.
     */
    @Test
    void successEqualsSameObject() {
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode = new GPSCode(latitude, longitude);
        Location location = new Location(address, gpsCode);
        assertEquals(location, location);
    }

    /**
     * Tests the equals method with a null object, expecting failure.
     */
    @Test
    void failedEqualsIfObjectNull() {
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode = new GPSCode(latitude, longitude);
        Location location = new Location(address, gpsCode);
        assertNotEquals(null, location);
    }

    /**
     * Tests the equals method with equal Location objects, expecting success.
     */
    @Test
    void successEqualsMethodWithEqualObjects() {
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 41.14961;
        double longitude = -8.61099;

        Address address1 = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode1 = new GPSCode(latitude, longitude);

        Location location1 = new Location(address1, gpsCode1);
        Location location2 = new Location(address1, gpsCode1);

        assertEquals(location1, location2);
    }

    /**
     * Tests the equals method with different Location objects, expecting them not to be equal.
     */
    @Test
    void failedEqualsMethodWithDifferentObjectsShouldNotBeEqual() {
        String street1 = "Rua do Firmeza";
        String doorNumber1 = "123";
        String zipCode1 = "4000-000";
        String city1 = "Porto";
        String country1 = "Portugal";
        double latitude1 = 41.14961;
        double longitude1 = -8.61099;

        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode = new GPSCode(latitude, longitude);
        Location location1 = new Location(address, gpsCode);

        Address address1 = new Address(street1, doorNumber1, zipCode1, city1, country1);
        GPSCode gpsCode1 = new GPSCode(latitude1, longitude1);
        Location location2 = new Location(address1, gpsCode1);

        assertNotEquals(location1, location2);

    }

    /**
     * Tests the toString method of the Location class, expecting success.
     */
    @Test
    void successToStringMethod() {
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode = new GPSCode(latitude, longitude);
        Location location = new Location(address, gpsCode);
        assertEquals("Location: " + address + " " + gpsCode, location.toString());
    }

    /**
     * Tests the getAddress method of the Location class, expecting the returned Address to be equal to the one used in the constructor.
     */
    @Test
    void successGetAddress() {
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode = new GPSCode(latitude, longitude);
        Location location = new Location(address, gpsCode);
        assertEquals(address, location.getAddress());
    }

    /**
     * Tests the getGpsCode method of the Location class, expecting the returned GPSCode to be equal to the one used in the constructor.
     */
    @Test
    void successGetGpsCode() {
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode = new GPSCode(latitude, longitude);
        Location location = new Location(address, gpsCode);
        assertEquals(gpsCode, location.getGpsCode());
    }

    /**
     * Test to verify that the equals method returns false when the object compared is null.
     * A Location object is created and compared with null.
     * The test passes if the equals method returns false.
     */
    @Test
    void equalsReturnsFalseForNullObject() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        Location location = new Location(address, gpsCode);

        assertFalse(location.equals(null));
    }

    /**
     * Test to verify that the equals method returns false when the object compared is of a different class.
     * A Location object is created and compared with an instance of the Object class.
     * The test passes if the equals method returns false.
     */
    @Test
    void equalsReturnsFalseForDifferentClassObject() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        Location location = new Location(address, gpsCode);

        assertFalse(location.equals(new Object()));
    }

    /**
     * Test if the hash code of two Location objects with the same attributes are the same.
     */
    @Test
    void validHashCode() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);

        Location location = new Location(address, gpsCode);

        Location location2 = new Location(address, gpsCode);
        assertEquals(location.hashCode(), location2.hashCode());
    }

    /**
     * Test if the hash code of an instance of Location isn't equal to a different instance of Location.
     */
    @Test
    void invalidHashCode() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        GPSCode gpsCode = new GPSCode(41.14961, -8.61099);
        Location location = new Location(address, gpsCode);

        GPSCode gpsCode2 = new GPSCode(41.14959, -8.61099);
        Location location2 = new Location(address, gpsCode2);
        assertNotEquals(location.hashCode(), location2.hashCode());
    }

}