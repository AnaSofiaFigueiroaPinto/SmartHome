package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code Address} class.
 */
class AddressTest {

    /**
     * Test the successful creation of an Address object.
     * Verifies that no exception was thrown.
     */
    @Test
    void validConstructor() {
        assertDoesNotThrow(() -> new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with null street.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorStreetNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address(null, "123", "4000-000", "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with empty street.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorStreetEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Address("", "123", "4000-000", "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with blank street.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorStreetBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Address(" ", "123", "4000-000", "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with null door number.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorDoorNumberNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", null, "4000-000", "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with empty door number.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorDoorNumberEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "", "4000-000", "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with blank door number.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorDoorNumberBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", " ", "4000-000", "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with null zip code.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorZipCodeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", null, "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with empty zip code.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorZipCodeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", "", "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with blank zip code.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorZipCodeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", " ", "Porto", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with null city.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorCityNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", "4000-000", null, "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with empty city.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorCityEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", "4000-000", "", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with blank city.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorCityBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", "4000-000", " ", "Portugal"));
    }

    /**
     * Test the failed creation of an Address object with null country.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorCountryNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", "4000-000", "Porto", null));
    }

    /**
     * Test the failed creation of an Address object with empty country.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorCountryEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", "4000-000", "Porto", ""));
    }

    /**
     * Test the failed creation of an Address object with blank country.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidConstructorCountryBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", "4000-000", "Porto", " "));
    }

    /**
     * Successfully retrieves the street attribute.
     */
    @Test
    void successGetStreet() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertEquals("Rua do Ouro", address.getStreet());
    }

    /**
     * Successfully retrieves the door number attribute.
     */
    @Test
    void successGetDoorNumber() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertEquals("123", address.getDoorNumber());
    }

    /**
     * Successfully retrieves the zip code attribute.
     */
    @Test
    void successGetZipCode() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertEquals("4000-000", address.getZipCode());
    }

    /**
     * Successfully retrieves the city attribute.
     */
    @Test
    void successGetCity() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertEquals("Porto", address.getCity());
    }

    /**
     * Successfully retrieves the country attribute.
     */
    @Test
    void successGetCountry() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertEquals("Portugal", address.getCountry());
    }

    /**
     * Test the successful comparison of two equal Address objects.
     * Verifies that the objects are equal.
     */
    @Test
    void successEqualsDifferentObjectsSameContent() {
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertEquals(address1, address2);
    }

    /**
     * Failed test for the comparison of two different Address objects.
     * Verifies that the objects are not equal.
     */
    @Test
    void failEqualsDifferentObjects() {
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Ouro", "245", "4000-000", "Porto", "Portugal");
        assertNotEquals(address1, address2);
    }

    /**
     * Failed test if the Address object is null.
     */
    @Test
    void failEqualsIfObjectNull() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertNotEquals(null, address);
    }

    /**
     * Test if the Address object is equal to an object of a different class.
     */
    @Test
    void successEqualsGetClass() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertNotEquals(address, new Object());
    }

    /**
     * Successfully turns the Address object into a String.
     */
    @Test
    void successToString() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertEquals("Rua do Ouro, 123, 4000-000, Porto, Portugal", address.toString());
    }

    /**
     * Test if the object is equal to itself
     */
    @Test
    void equalsObjectTrue() {
        Address address = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertEquals(address, address);
    }

    /**
     * Test if the object is equal to another object with different street name.
     */
    @Test
    void equalsDifferentAttributesStreet() {
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Prata", "23", "4000-000", "Porto", "Portugal");
        assertNotEquals(address1, address2);
    }

    /**
     * Test if the object is equal to another object with different door number.
     */
    @Test
    void equalsDifferentAttributesDoorNumber() {
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Ouro", "23", "4000-000", "Porto", "Portugal");
        assertNotEquals(address1, address2);
    }

    /**
     * Test if the object is equal to another object with different zip code.
     */
    @Test
    void equalsDifferentAttributesZipCode() {
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Ouro", "123", "4000-010", "Porto", "Portugal");
        assertNotEquals(address1, address2);
    }

    /**
     * Test if the object is equal to another object with different city.
     */
    @Test
    void equalsDifferentAttributesCity() {
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Ouro", "123", "4000-000", "Lisboa", "Portugal");
        assertNotEquals(address1, address2);
    }

    /**
     * Test if the hash code of two Address with the same attributes are the same.
     */
    @Test
    void validHashCode() {
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertEquals(address1.hashCode(), address2.hashCode());
    }

    /**
     * Test if the hash code of an instance of Address isn't equal to a different instance of Address.
     */
    @Test
    void invalidHashCode() {
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Ouro", "123", "4000-000", "Lisboa", "Portugal");
        assertNotEquals(address1.hashCode(), address2.hashCode());
    }

    /**
     * Successfully creates an Address object with a valid Portuguese zip code.
     * Verifies that no exception was thrown.
     */
    @Test
    void validZipCodePortugal() {
        assertDoesNotThrow(() -> new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal"));
    }

    /**
     * Failed test for the creation of an Address object with an invalid Portuguese zip code.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidZipCodePortugal() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", "40000-000", "Porto", "Portugal"));
    }

    /**
     * Successfully creates an Address object with a valid USA zip code.
     * Verifies that no exception was thrown.
     */
    @Test
    void validZipCodeUSA() {
        assertDoesNotThrow(() -> new Address("Main St", "123", "12345", "New York", "USA"));
    }

    /**
     * Failed test for the creation of an Address object with an invalid USA zip code.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidZipCodeUSA() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Main St", "123", "123456", "New York", "USA"));
    }

    /**
     * Successfully creates an Address object with a valid French zip code.
     * Verifies that no exception was thrown.
     */
    @Test
    void validZipCodeFrance() {
        assertDoesNotThrow(() -> new Address("Rue de Rivoli", "123", "75001", "Paris", "France"));
    }

    /**
     * Failed test for the creation of an Address object with an invalid French zip code.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidZipCodeFrance() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rue de Rivoli", "123", "750001", "Paris", "France"));
    }

    /**
     * Successfully creates an Address object with a valid UK zip code.
     * Verifies that no exception was thrown.
     */
    @Test
    void validZipCodeUK() {
        assertDoesNotThrow(() -> new Address("Baker St", "221B", "NW1 6XE", "London", "UK"));
    }

    /**
     * Failed test for the creation of an Address object with an invalid UK zip code.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void invalidZipCodeUK() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Baker St", "221B", "NW16XE", "London", "UK"));
    }

    /**
     * Test if the object is equal to another object with different country.
     */
    @Test
    void equalsDifferentAttributesCountry() {
        Address address1 = new Address("Rua do Ouro", "123", "NW1 6XE", "Porto", "UK");
        Address address2 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        assertNotEquals(address1, address2);
    }


    /**
     * Failed test for the creation of an Address object with unsupported countries.
     * Verifies that an IllegalArgumentException was thrown.
     */
    @Test
    void failedUnsupportedCountryConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua do Ouro", "123", "4000-000", "Porto", "Spain"));
        assertThrows(IllegalArgumentException.class, () -> new Address("123 Main St", "456", "12345", "New York", "Canada"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Rue de la Liberté", "789", "75001", "Paris", "Germany"));
        assertThrows(IllegalArgumentException.class, () -> new Address("10 Downing Street", "10", "SW1A 2AA", "London", "Italy"));
    }

}