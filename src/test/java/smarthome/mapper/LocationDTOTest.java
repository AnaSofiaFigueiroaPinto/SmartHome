package smarthome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code LocationDTO} class.
 */
class LocationDTOTest {
    /**
     * Test case for constructing a {@link LocationDTO} with valid parameters.
     */
    @Test
    void successCreateLocationDTOWithValidParameters() {
        // Valid parameters
        String street = "Main Street";
        String doorNumber = "123";
        String zipCode = "12345";
        String city = "City";
        String country = "Country";
        double latitude = 50.123;
        double longitude = -70.456;

        // Construct LocationDTO object
        LocationDTO locationDTO = new LocationDTO(street, doorNumber, zipCode, city, country, latitude, longitude);

        // Check if LocationDTO object is not null
        assertNotNull(locationDTO);
    }

    /**
     * Test case for the equals method of the LocationDTO class.
     * This test checks if two identical LocationDTO objects are considered equal,
     * and if changing a parameter in one of the objects makes them not equal.
     */
    @Test
    void testEquals() {
        // Construct two identical LocationDTO objects
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);

        // Check if both objects are equal
        assertEquals(locationDTO1, locationDTO2);

        // Change a parameter in the second object
        locationDTO2 = new LocationDTO("Different Street", "123", "12345", "City", "Country", 50.123, -70.456);

        // Check if both objects are not equal
        assertNotEquals(locationDTO1, locationDTO2);
    }

    /**
     * Test case for the hashCode method of the LocationDTO class.
     * This test checks if two identical LocationDTO objects have the same hash code,
     * and if changing a parameter in one of the objects makes their hash codes different.
     */
    @Test
    void testHashCode() {
        // Construct two identical LocationDTO objects
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);

        // Check if both objects have the same hash code
        assertEquals(locationDTO1.hashCode(), locationDTO2.hashCode());

        // Change a parameter in the second object
        locationDTO2 = new LocationDTO("Different Street", "123", "12345", "City", "Country", 50.123, -70.456);

        // Check if both objects have different hash codes
        assertNotEquals(locationDTO1.hashCode(), locationDTO2.hashCode());
    }

    /**
     * Test case for the {@code equals} method of the {@code LocationDTO} class with a null object.
     * This test checks if the {@code equals} method correctly handles comparison with a null object.
     */
    @Test
    void testEqualsWithNull() {
        LocationDTO locationDTO = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO, null);
    }

    /**
     * Test case for the {@code equals} method of the {@code LocationDTO} class with an object of a different class.
     * This test checks if the {@code equals} method correctly handles comparison with an object of a different class.
     */
    @Test
    void testEqualsWithDifferentClass() {
        LocationDTO locationDTO = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO, new Object());
    }

    /**
     * Test case for the {@code equals} method of the {@code LocationDTO} class with the same object.
     * This test checks if the {@code equals} method correctly handles comparison with itself.
     */
    @Test
    void testEqualsWithSameObject() {
        LocationDTO locationDTO = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertEquals(locationDTO, locationDTO);
    }

    /**
     * Test case for the {@code equals} method of the {@code LocationDTO} class with different latitude.
     * This test checks if changing the latitude of one {@code LocationDTO} object makes them not equal.
     */
    @Test
    void testEqualsWithDifferentLatitude() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.124, -70.456);
        assertNotEquals(locationDTO1, locationDTO2);
    }

    /**
     * Test case for the {@code equals} method of the {@code LocationDTO} class with different longitude.
     * This test checks if changing the longitude of one {@code LocationDTO} object makes them not equal.
     */
    @Test
    void testEqualsWithDifferentLongitude() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.457);
        assertNotEquals(locationDTO1, locationDTO2);
    }

    /**
     * Test case for the {@code equals} method of the {@code LocationDTO} class with different street.
     * This test checks if changing the street of one {@code LocationDTO} object makes them not equal.
     */
    @Test
    void testEqualsWithDifferentStreet() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Different Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO1, locationDTO2);
    }

    /**
     * Test case for the {@code equals} method of the {@code LocationDTO} class with different door number.
     * This test checks if changing the door number of one {@code LocationDTO} object makes them not equal.
     */
    @Test
    void testEqualsWithDifferentDoorNumber() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "124", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO1, locationDTO2);
    }

    /**
     * Test case for the {@code equals} method of the {@code LocationDTO} class with different zip code.
     * This test checks if changing the zip code of one {@code LocationDTO} object makes them not equal.
     */
    @Test
    void testEqualsWithDifferentZipCode() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12346", "City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO1, locationDTO2);
    }

    /**
     *  Test case for the {@code equals} method of the {@code LocationDTO} class with different city.
     *  This test checks if changing the city of one {@code LocationDTO} object makes them not equal.
     */
    @Test
    void testEqualsWithDifferentCity() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "Different City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO1, locationDTO2);
    }

    /**
     * Test case for the {@code equals} method of the {@code LocationDTO} class with different country.
     * This test checks if changing the country of one {@code LocationDTO} object makes them not equal.
     */
    @Test
    void testEqualsWithDifferentCountry() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "City", "Different Country", 50.123, -70.456);
        assertNotEquals(locationDTO1, locationDTO2);
    }

    /**
     * Test case for the {@code hashCode} method of the {@code LocationDTO} class with null.
     * This test checks if the hash code of a {@code LocationDTO} object is not equal to the hash code of a null object.
     */
    @Test
    void testHashCodeWithDifferentLatitude() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.124, -70.456);
        assertNotEquals(locationDTO1.hashCode(), locationDTO2.hashCode());
    }

    /**
     * Test case for the {@code hashCode} method of the {@code LocationDTO} class with different longitude.
     * This test checks if the hash code of a {@code LocationDTO} object is not equal to the hash code of another object with different longitude.
     */
    @Test
    void testHashCodeWithDifferentLongitude() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.457);
        assertNotEquals(locationDTO1.hashCode(), locationDTO2.hashCode());
    }

    /**
     * Test case for the {@code hashCode} method of the {@code LocationDTO} class with different street.
     * This test checks if the hash code of a {@code LocationDTO} object is not equal to the hash code of another object with different street.
     */
    @Test
    void testHashCodeWithDifferentStreet() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Different Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO1.hashCode(), locationDTO2.hashCode());
    }

    /**
     * Test case for the {@code hashCode} method of the {@code LocationDTO} class with different door number.
     * This test checks if the hash code of a {@code LocationDTO} object is not equal to the hash code of another object with different door number.
     */
    @Test
    void testHashCodeWithDifferentDoorNumber() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "124", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO1.hashCode(), locationDTO2.hashCode());
    }

    /**
     * Test case for the {@code hashCode} method of the {@code LocationDTO} class with different zip code.
     * This test checks if the hash code of a {@code LocationDTO} object is not equal to the hash code of another object with different zip code.
     */
    @Test
    void testHashCodeWithDifferentZipCode() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12346", "City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO1.hashCode(), locationDTO2.hashCode());
    }

    /**
     * Test case for the {@code hashCode} method of the {@code LocationDTO} class with different city.
     * This test checks if the hash code of a {@code LocationDTO} object is not equal to the hash code of another object with different city.
     */
    @Test
    void testHashCodeWithDifferentCity() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "Different City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO1.hashCode(), locationDTO2.hashCode());
    }

    /**
     * Test case for the {@code hashCode} method of the {@code LocationDTO} class with different country.
     * This test checks if the hash code of a {@code LocationDTO} object is not equal to the hash code of another object with different country.
     */
    @Test
    void testHashCodeWithDifferentCountry() {
        LocationDTO locationDTO1 = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        LocationDTO locationDTO2 = new LocationDTO("Main Street", "123", "12345", "City", "Different Country", 50.123, -70.456);
        assertNotEquals(locationDTO1.hashCode(), locationDTO2.hashCode());
    }

    /**
     * Test case for the {@code hashCode} method of the {@code LocationDTO} class with a null object.
     * This test checks if the {@code hashCode} method correctly handles comparison with a null object's hash code.
     */
    @Test
    void testHashCodeWithNull() {
        LocationDTO locationDTO = new LocationDTO("Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(locationDTO.hashCode(), new Object().hashCode());
    }

}