package smarthome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code HouseDTO} class.
 */
class HouseDTOTest {
    /**
     * Test case for constructing a {@link HouseDTO} with valid parameters.
     */
    @Test
    void successCreateHouseDTOWithValidParameters() {
        // Valid parameters
        String houseID = "123";
        String street = "Main Street";
        String doorNumber = "123";
        String zipCode = "12345";
        String city = "City";
        String country = "Country";
        double latitude = 50.123;
        double longitude = -70.456;

        // Construct HouseDTO object
        HouseDTO houseDTO = new HouseDTO(houseID, street, doorNumber, zipCode, city, country, latitude, longitude);

        // Check if HouseDTO object is not null
        assertNotNull(houseDTO);
    }

    @Test
    void successEqualsSameObject() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertEquals(houseDTO, houseDTO);
    }

    @Test
    void successEqualsDifferentClass() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(houseDTO, new Object());
    }

    @Test
    void successEqualsNull() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(houseDTO, null);
    }

    @Test
    void successEqualsDifferentObject() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertEquals(houseDTO, houseDTO2);
    }

    @Test
    void successEqualsDifferentLatitude() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.124, -70.456);
        assertNotEquals(houseDTO, houseDTO2);
    }

    @Test
    void successEqualsDifferentLongitude() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.457);
        assertNotEquals(houseDTO, houseDTO2);
    }

    @Test
    void successEqualsDifferentHouseID() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("124", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(houseDTO, houseDTO2);
    }

    @Test
    void successEqualsDifferentStreet() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street 2", "123", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(houseDTO, houseDTO2);
    }

    @Test
    void successEqualsDifferentDoorNumber() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street", "124", "12345", "City", "Country", 50.123, -70.456);
        assertNotEquals(houseDTO, houseDTO2);
    }

    @Test
    void successEqualsDifferentZipCode() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street", "123", "12346", "City", "Country", 50.123, -70.456);
        assertNotEquals(houseDTO, houseDTO2);
    }

    @Test
    void successEqualsDifferentCity() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street", "123", "12345", "City 2", "Country", 50.123, -70.456);
        assertNotEquals(houseDTO, houseDTO2);
    }

    @Test
    void successEqualsDifferentCountry() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country 2", 50.123, -70.456);
        assertNotEquals(houseDTO, houseDTO2);
    }

    @Test
    void successHashCodeSameAttributes() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        assertEquals(houseDTO.hashCode(), houseDTO2.hashCode());
    }

    @Test
    void successHashCodeDifferentAttributes() {
        HouseDTO houseDTO = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.123, -70.456);
        HouseDTO houseDTO2 = new HouseDTO("123", "Main Street", "123", "12345", "City", "Country", 50.124, -70.456);
        assertNotEquals(houseDTO.hashCode(), houseDTO2.hashCode());
    }

}