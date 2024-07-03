package smarthome.domain.house;

import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.Address;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for the {@code House} class.
 */
class HouseTest {

    /**
     * Successfully test the creation of the House without location.
     */
    @Test
    void successCreateHouseWithoutLocation() {
        House house = new House();
        assertNotNull(house);
    }

    /**
     * Successfully test the creation of the House with location.
     */
    @Test
    void successCreateHouseWithLocation() {
        Location locationDouble = mock(Location.class);
        House house = new House(locationDouble);
        assertNotNull(house);
    }

    /**
     * Test to verify that the identity() method of the House class returns a non-null value.
     * The test will pass if the returned HouseID is not null.
     */
    @Test
    void successIdentityMethod() {
        House house = new House();
        assertNotNull(house.identity());
    }

    /**
     * Test to verify the behavior of the isSameAs() method of the House class.
     * The test will pass if the method returns false when comparing two different House objects, and true when comparing a House object with itself.
     */
    @Test
    void successIsSameAsMethod() {
        House house1 = new House();
        House house2 = new House();
        assertFalse(house1.isSameAs(house2));

        assertTrue(house1.isSameAs(house1));
    }

    /**
     * Test to verify the behavior of the isSameAs() method of the House class when comparing with a non-House object.
     * The test will pass if the method returns false when comparing a House object with a non-House object.
     */
    @Test
    void successIsSameAsWithNonHouseObject() {
        House house = new House();

        Object nonHouseObject = new Object();

        assertFalse(house.isSameAs(nonHouseObject));
    }

    /**
     * Test to verify the behavior of the editLocation() method of the House without an initial location.
     * The test will pass if the method returns an object when the location is successfully edited.
     */
    @Test
    void successEditLocationHouseWithoutInitialLocation() {
        House house = new House();

        Address address = mock(Address.class);
        GPSCode gpsCode = mock(GPSCode.class);

        assertNotNull(house.editLocation(address, gpsCode));
    }

    /**
     * Test to verify the behavior of the editLocation() method of the House with an initial location.
     * The test will pass if the method returns an object when the location is successfully edited.
     */
    @Test
    void successEditLocationHouseWithInitialLocation() {
        Location location = mock(Location.class);

        House house = new House(location);

        Address newAddress = mock(Address.class);
        GPSCode newGpsCode = mock(GPSCode.class);

        assertNotNull(house.editLocation(newAddress, newGpsCode));
    }

    /**
     * Test to verify the behavior of the editLocation() method of the House without an initial Location when the address is null.
     * The test will pass if the method returns null.
     */
    @Test
    void failedEditLocationNullAddress() {
        House house = new House();
        GPSCode newGPSCode = mock(GPSCode.class);

        Location editedLocation = house.editLocation(null, newGPSCode);

        assertNull(editedLocation);
    }

    /**
     * Test to verify the behavior of the editLocation() method of the House without an initial Location when the gpsCode is null.
     * The test will pass if the method returns null.
     */
    @Test
    void failedEditLocationNullGPS() {
        House house = new House();
        Address newAddress = mock(Address.class);

        Location editedLocation = house.editLocation(newAddress, null);

        assertNull(editedLocation);
    }


    /**
     * Test to verify the behavior of the editLocation() method of the House class when one of the Location parameters is null.
     * The test will pass if the method returns null.
     */
    @Test
    void failedEditLocationWithAHouseWithInitialLocation() {
        Location initialLocation = mock(Location.class);

        // Create a house with initial location
        House house = new House(initialLocation);

        // Test the editLocation method with null parameter
        Address address = mock(Address.class);
        Location editedLocation = house.editLocation(address, null);

        // Assert that the edited location is null
        assertNull(editedLocation);

    }

    /**
     * Test to verify if the House is successfully created with a HouseID for persistence.
     */
    @Test
    void successCreationOfHouseWithID() {
        HouseID houseIDDouble = mock(HouseID.class);
        House house = new House(houseIDDouble);
        assertNotNull(house);
    }

    /**
     * Test to verify if the House is successfully created with a HouseID and Location for persistence.
     */
    @Test
    void successCreationOfHouseWithIDAndParameters() {
        HouseID houseIDDouble = mock(HouseID.class);

        Location locationDouble = mock(Location.class);

        House house = new House(houseIDDouble, locationDouble);
        assertNotNull(house);
    }
}