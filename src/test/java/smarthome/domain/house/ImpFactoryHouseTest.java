package smarthome.domain.house;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for the ImpFactoryHouse class.
 */
@SpringBootTest(classes = {ImpFactoryHouse.class})
class ImpFactoryHouseTest {

    /**
     * Tests the creation of a House object without a location.
     */
    @Test
    void testCreateHouseWithOutLocation() {
        // Arrange
        FactoryHouse factory = new ImpFactoryHouse();

        // Act
        House house = factory.createHouseWithOutLocation();

        // Assert
        assertNotNull(house);
        assertNull(house.getHouseLocation());
    }

    /**
     * Tests the creation of a House object with a location.
     */
    @Test
    void testCreateHouseWithLocation() {
        // Arrange
        FactoryHouse factory = new ImpFactoryHouse();
        Location locationDouble = mock(Location.class);

        // Act
        House house = factory.createHouseWithLocation(locationDouble);

        // Assert
        assertNotNull(house);
        assertEquals(locationDouble, house.getHouseLocation());
    }

    /**
     * Tests the behavior when a null location is provided to createHouseWithLocation method.
     */
    @Test
    void testCreateHouseWithoutLocationConstructor() {
        // Arrange
        FactoryHouse factory = new ImpFactoryHouse();

        // Act
        House house = factory.createHouseWithLocation(null);

        // Assert
        assertNull(house);
    }

    /**
     * Tests the creation of a House object with both a HouseID and a location.
     */
    @Test
    void testCreateHouseWithOrWithoutLocation_WithHouseIDAndLocation() {
        // Arrange
        FactoryHouse factory = new ImpFactoryHouse();
        HouseID houseID = new HouseID("Test HouseID");
        Location locationDouble = mock(Location.class);

        // Act
        House house = factory.createHouseWithOrWithoutLocation(houseID, locationDouble);

        // Assert
        assertNotNull(house);
        assertEquals(houseID, house.identity());
        assertEquals(locationDouble, house.getHouseLocation());
    }

    /**
     * Tests the behavior when a null HouseID is provided to createHouseWithOrWithoutLocation method.
     */
    @Test
    void testCreateHouseWithOrWithoutLocation_WithNullHouseID() {
        // Arrange
        FactoryHouse factory = new ImpFactoryHouse();
        Location locationDouble = mock(Location.class);

        // Act
        House house = factory.createHouseWithOrWithoutLocation(null, locationDouble);

        // Assert
        assertNull(house);
    }

    /**
     * Tests the behavior when a null location is provided to createHouseWithOrWithoutLocation method.
     */
    @Test
    void testCreateHouseWithOrWithoutLocation_WithNullLocation() {
        // Arrange
        FactoryHouse factory = new ImpFactoryHouse();
        HouseID houseID = new HouseID("Test HouseID");

        // Act
        House house = factory.createHouseWithOrWithoutLocation(houseID, null);

        // Assert
        assertNotNull(house);
        assertEquals(houseID, house.identity());
        assertNull(house.getHouseLocation());
    }
}