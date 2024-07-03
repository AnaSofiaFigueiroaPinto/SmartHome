package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HouseIDTest {

    private static final String validId = "123";
    private static final String validId1 = "224";
    private static final String invalidIdEmpty = "";
    private static final String invalidIdBlank = " ";

    /**
     * Test if valid constructor of class HouseId.
     */
    @Test
    public void successHouseIdCreation() {
        new HouseID(validId);
    }

    /**
     * Test if invalid constructor of class HouseId with empty id.
     * Throws IllegalArgumentException with a message if empty id.
     */
    @Test
    public void failedHouseIdCreationEmptyId() {
        assertThrows(IllegalArgumentException.class, () -> new HouseID(invalidIdEmpty));
    }

    /**
     * Test if invalid constructor of class HouseId with blank id.
     * Throws IllegalArgumentException with a message if blank id.
     */
    @Test
    public void failedHouseIdCreationBlankId() {
        assertThrows(IllegalArgumentException.class, () -> new HouseID(invalidIdBlank));
    }

    /**
     * Test if invalid constructor of class HouseId with null id.
     * Throws IllegalArgumentException with a message if null id.
     */
    @Test
    public void failedHouseIdCreationNullId() {
        assertThrows(IllegalArgumentException.class, () -> new HouseID(null));
    }

    /**
     * Test equals method of class HouseId with same id.
     * Returns true if object is the same or have the same ID.
     */
    @Test
    public void successEqualsIfSameId() {
        HouseID houseId = new HouseID(validId);
        HouseID houseId2 = new HouseID(validId);
        assertEquals(houseId, houseId2);
        assertEquals(houseId, houseId);
    }

    /**
     * Test equals method of class HouseId with object null.
     * Returns false if object is null.
     */
    @Test
    public void failedEqualsIfNull() {
        HouseID houseId = new HouseID(validId);
        assertNotEquals(houseId, null);
    }

    /**
     * Test equals method of class HouseId with different object.
     * Returns false if object is different.
     */
    @Test
    public void failedEqualsIfDifferentHouseID() {
        HouseID houseId = new HouseID(validId);
        HouseID houseId1 = new HouseID(validId1);
        assertNotEquals(houseId, houseId1);;
    }

    /**
     * Test equals method of class HouseId with different object.
     * Returns false if object is different.
     */
    @Test
    public void failedEqualsIfDifferentClass() {
        HouseID houseId = new HouseID(validId);
        Object obj = new Object();
        assertNotEquals(houseId, obj);
    }

    /**
     * Test toString method of class HouseId.
     * Returns the id of the house in a String format.
     */
    @Test
    public void successToStringReturnIdString(){
        HouseID houseId = new HouseID(validId);
        String result = houseId.toString();
        assertEquals(validId, result);
    }

    /**
     * Test to verify that the hashCode method returns different hash codes for different HouseID objects.
     * Two HouseID objects are created with different ids.
     * The test passes if the hashCode method returns different values for these two objects.
     */
    @Test
    public void hashCodeDifferentForDifferentObjects() {
        HouseID houseId1 = new HouseID("123");
        HouseID houseId2 = new HouseID("456");
        assertNotEquals(houseId1.hashCode(), houseId2.hashCode());
    }
}