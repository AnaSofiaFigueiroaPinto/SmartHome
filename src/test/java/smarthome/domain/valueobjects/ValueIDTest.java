package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code ValueID} class.
 */
class ValueIDTest
{

    /**
     * The valid value ID used for testing.
     */
    private static final String validValueID1 = "1";

    /**
     * Test case to verify the constructor of the {@link ValueID} class with a valid ID.
     * Verifies that the constructor creates a new {@code ValueID} object with a valid ID.
     */
    @Test
    void validConstructor()
    {
        ValueID valueID = new ValueID(validValueID1);
        assertEquals(valueID.toString(), validValueID1);
    }

    /**
     * Test case to verify the constructor of the {@link ValueID} class with an invalid ID.
     * Verifies that the constructor throws an {@link IllegalArgumentException} when the ID is invalid.
     * The ID is invalid when it is null, empty, or blank.
     */
    @Test
    void invalidConstructor()
    {
        assertThrows(IllegalArgumentException.class, () -> new ValueID(null));
        assertThrows(IllegalArgumentException.class, () -> new ValueID(""));
        assertThrows(IllegalArgumentException.class, () -> new ValueID(" "));
    }

    /**
     * Test if two {@code ValueID} objects with the same ID have the same hash code.
     */
    @Test
    void equalValueIDItself()
    {
        ValueID valueID1 = new ValueID(validValueID1);
        assertEquals(valueID1, valueID1);
    }

    @Test
    void equalsWithDifferentIDs()
    {
        ValueID valueID1 = new ValueID("1");
        ValueID valueID2 = new ValueID("2");
        assertNotEquals(valueID1, valueID2);
    }

    /**
     * Test if two {@code ValueID} objects with different IDs are not equal.
     */
    @Test
    void invalidEquals()
    {
        ValueID valueID1 = new ValueID(validValueID1);
        String validValueID2 = "2";
        ValueID valueID2 = new ValueID(validValueID2);
        assertNotEquals(valueID1, valueID2);
        assertNotEquals(valueID2, valueID1);
    }

    /**
     * Test if a {@code ValueID} object is not equal to null.
     */
    @Test
    void invalidEqualsNull()
    {
        ValueID valueID1 = new ValueID(validValueID1);
        assertNotEquals(valueID1, null);
    }

    /**
     * Test if a {@code ValueID} object is not equal to an object of a different class.
     */
    @Test
    void invalidEqualsDifferentClass()
    {
        ValueID valueID1 = new ValueID(validValueID1);
        assertNotEquals(valueID1, new Object());
    }

    /**
     * Test the hashCode method of {@code ValueID} objects with the same ID.
     */
    @Test
    void validHashCode()
    {
        ValueID valueID1 = new ValueID(validValueID1);
        ValueID valueID2 = new ValueID(validValueID1);
        assertEquals(valueID1.hashCode(), valueID2.hashCode());
    }

    /**
     * Test the hashCode method of {@code ValueID} objects with different IDs.
     */
    @Test
    void hashCodeWithDifferentIDs()
    {
        ValueID valueID1 = new ValueID("1");
        ValueID valueID2 = new ValueID("2");
        assertNotEquals(valueID1.hashCode(), valueID2.hashCode());
    }

    /**
     * Test the equals method of {@code ValueID} objects with different instances but the same ID.
     */
    @Test
    void equalsWithDifferentInstancesButSameIDs()
    {
        ValueID valueID1 = new ValueID("1");
        ValueID valueID2 = new ValueID("1");
        assertEquals(valueID1, valueID2);
    }

    /**
     * Test the toString method of {@code ValueID} objects.
     */
    @Test
    void testToString()
    {
        ValueID valueID1 = new ValueID(validValueID1);
        assertEquals(valueID1.toString(), validValueID1);
    }
}