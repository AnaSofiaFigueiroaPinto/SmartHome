package smarthome.persistence.jpa.datamodel;

import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.house.ImpFactoryHouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.Address;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Location;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code HouseDataModel} class.
 */
class HouseDataModelTest {
    private HouseDataModel houseDataModel;
    private House house;
    private String street;
    private String doorNumber;
    private String zipCode;
    private String city;
    private String country;
    private double latitude;
    private double longitude;

    /**
     * SetUp Before Each test that instantiates the declared arguments that will be used in each test.
     */
    @BeforeEach
    void setUp() {
        street = "Rua Dr. António Bernardino de Almeida";
        doorNumber = "24B";
        zipCode = "4200-072";
        city = "Porto";
        country = "Portugal";
        latitude = 41.236;
        longitude = 85.695;

        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode = new GPSCode(latitude, longitude);

        Location location = new Location(address, gpsCode);

        FactoryHouse factoryHouse = new ImpFactoryHouse();
        house = factoryHouse.createHouseWithLocation(location);
        houseDataModel = new HouseDataModel(house);
    }

    /**
     * Test that successfully creates a HouseDataModel using an empty constructor.
     */
    @Test
    void successCreateAHouseDataModelEmptyConstructor() {
        houseDataModel = new HouseDataModel();
        assertNotNull(houseDataModel);
    }

    @Test
    void successCreateAHouseDataModelEmptyConstructor2() {
        street = null;
        doorNumber = "24B";
        zipCode = "4200-072";
        city = "Porto";
        country = "Portugal";
        latitude = 41.236;
        longitude = 85.695;
        houseDataModel = new HouseDataModel(house);
        assertNotNull(houseDataModel);
    }

    /**
     * Test that get the HouseID from House Data Model.
     */
    @Test
    void getHouseIDFromHouseDataModel() {
        String result = houseDataModel.getHouseID();
        assertEquals(house.identity().toString(), result);
    }

    /**
     * Test that get the Street from House Data Model.
     */
    @Test
    void getStreetFromHouseDataModel() {
        String result = houseDataModel.getStreet();
        assertEquals(street, result);
    }

    /**
     * Test that get the Door Number from House Data Model.
     */
    @Test
    void getDoorNumberFromHouseDataModel() {
        assertEquals(doorNumber, houseDataModel.getDoorNumber());
    }

    /**
     * Test that get the ZipCode from House Data Model.
     */
    @Test
    void getZipCodeFromHouseDataModel() {
        assertEquals(zipCode, houseDataModel.getZipCode());
    }

    /**
     * Test that get the City from House Data Model.
     */
    @Test
    void getCityFromHouseDataModel() {
        assertEquals(city, houseDataModel.getCity());
    }

    /**
     * Test that get the Country from House Data Model.
     */
    @Test
    void getCountryFromHouseDataModel() {
        assertEquals(country, houseDataModel.getCountry());
    }

    /**
     * Test that get the Latitude from House Data Model.
     */
    @Test
    void getLatitudeFromHouseDataModel() {
        assertEquals(latitude, houseDataModel.getLatitude());
    }

    /**
     * Test that get the Longitude from House Data Model.
     */
    @Test
    void getLongitudeFromHouseDataModel() {
        assertEquals(longitude, houseDataModel.getLongitude());
    }

    /**
     * Test that successfully update the House Data Model from a House Domain.
     */
    @Test
    void successUpdateFromDomain ()
    {
        String newStreet = "Rua Dr. António Bernardino de Almeida";
        String newDoorNumber = "24B";
        String newZipCode = "4200-072";
        String newCity = "Porto";
        String newCountry = "Portugal";
        double newLatitude = 41.236;
        double newLongitude = 85.695;
        FactoryHouse factoryHouse = new ImpFactoryHouse();

        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPSCode gpsCode = new GPSCode(latitude, longitude);

        Location location = new Location(address, gpsCode);

        House newHouse = factoryHouse.createHouseWithLocation(location);
        houseDataModel.updateFromDomain(newHouse);
        assertEquals(newStreet, houseDataModel.getStreet());
        assertEquals(newDoorNumber, houseDataModel.getDoorNumber());
        assertEquals(newZipCode, houseDataModel.getZipCode());
        assertEquals(newCity, houseDataModel.getCity());
        assertEquals(newCountry, houseDataModel.getCountry());
        assertEquals(newLatitude, houseDataModel.getLatitude());
        assertEquals(newLongitude, houseDataModel.getLongitude());
    }

    /**
     * Test that unsuccessfully updates the House Data Model from a House Domain.
     */
    @Test
    void unsuccessfulUpdateFromDomain ()
    {
        House newHouse = null;
        assertFalse(houseDataModel.updateFromDomain(newHouse));
    }
}