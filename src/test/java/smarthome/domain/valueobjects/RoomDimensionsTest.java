package smarthome.domain.valueobjects;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoomDimensionsTest {

    /**
     * Tests valid creation of a {@link RoomDimensions} object with positive dimensions.
     */
    @Test
    void validDimensionsCreation() {
        RoomDimensions dimensions = new RoomDimensions(1.0, 2.0, 0.0);
        assertNotNull(dimensions);
    }

    /**
     * Tests that creating a {@link RoomDimensions} object with a negative length throws an IllegalArgumentException.
     */
    @Test
    void failDimensionsCreationNegativeLength() {
        assertThrows(IllegalArgumentException.class, () -> new RoomDimensions(-1.0, 2.0, 3.0));
    }

    /**
     * Tests that creating a {@link RoomDimensions} object with a negative width throws an IllegalArgumentException.
     */
    @Test
    void failDimensionsCreationNegativeWidth() {
        assertThrows(IllegalArgumentException.class, () -> new RoomDimensions(1.0, -2.0, 3.0));
    }

    /**
     * Tests that creating a {@link RoomDimensions} object with a negative height throws an IllegalArgumentException.
     */
    @Test
    void failDimensionsCreationNegativeHeight() {
        assertThrows(IllegalArgumentException.class, () -> new RoomDimensions(1.0, 2.0, -3.0));
    }

    /**
     * Tests that creating a {@link RoomDimensions} object with a zero length throws an IllegalArgumentException.
     */
    @Test
    void failDimensionsCreationZeroLength() {
        assertThrows(IllegalArgumentException.class, () -> new RoomDimensions(0.0, 2.0, 3.0));
    }

    /**
     * Tests that creating a {@link RoomDimensions} object with a zero width throws an IllegalArgumentException.
     */
    @Test
    void failDimensionsCreationZeroWidth() {
        assertThrows(IllegalArgumentException.class, () -> new RoomDimensions(1.0, 0.0, 3.0));
    }

    /**
     * Tests the {@link RoomDimensions#getLength()} method to ensure it returns the correct length value for a {@link RoomDimensions} object.
     */
    @Test
    void getLength() {
        RoomDimensions dimensions = new RoomDimensions(1.0, 2.0, 3.0);
        assertEquals(1.0, dimensions.getLength(), 0.001);
    }

    /**
     * Tests the {@link RoomDimensions#getWidth()} method to ensure it returns the correct width value for a {@link RoomDimensions} object.
     */
    @Test
    void getWidth() {
        RoomDimensions dimensions = new RoomDimensions(1.0, 2.0, 3.0);
        assertEquals(2.0, dimensions.getWidth(), 0.001);
    }

    /**
     * Tests the {@link RoomDimensions#getHeight()} method to ensure it returns the correct height value for a {@link RoomDimensions} object.
     */
    @Test
    void getHeight() {
        RoomDimensions dimensions = new RoomDimensions(1.0, 2.0, 3.0);
        assertEquals(3.0, dimensions.getHeight(), 0.001);
    }

    /**
     * Tests the equals() method by comparing the object with itself.
     */
    @Test
    void equalityWithItself() {
        RoomDimensions dimensions = new RoomDimensions(1.0, 2.0, 3.0);
        assertEquals(dimensions, dimensions);
    }

    /**
     * Tests inequality between a {@link RoomDimensions} object and an object from a different class.
     */
    @Test
    void inequalityWithDifferentClass() {
        RoomDimensions dimensions = new RoomDimensions(1.0, 2.0, 3.0);
        Object obj = new Object();
        assertNotEquals(dimensions, obj);
    }

    /**
     * Tests equality between two {@link RoomDimensions} objects with the same dimensions.
     */
    @Test
    void equalityWithSameDimensions() {
        RoomDimensions dimensions1 = new RoomDimensions(1.0, 2.0, 3.0);
        RoomDimensions dimensions2 = new RoomDimensions(1.0, 2.0, 3.0);
        assertEquals(dimensions1, dimensions2);
    }

    /**
     * Tests inequality between two {@link RoomDimensions} objects with different height.
     */
    @Test
    void inequalityWithDifferentHeight() {
        RoomDimensions dimensions1 = new RoomDimensions(1.0, 2.0, 3.0);
        RoomDimensions dimensions2 = new RoomDimensions(1.0, 2.0, 4.0);
        assertNotEquals(dimensions1, dimensions2);
    }

    /**
     * Tests inequality between two {@link RoomDimensions} objects with different width.
     */
    @Test
    void inequalityWithDifferentWidth() {
        RoomDimensions dimensions1 = new RoomDimensions(1.0, 2.0, 3.0);
        RoomDimensions dimensions2 = new RoomDimensions(1.0, 5.0, 3.0);
        assertNotEquals(dimensions1, dimensions2);
    }

    /**
     * Tests inequality between two {@link RoomDimensions} objects with different width.
     */
    @Test
    void inequalityWithDifferentLength() {
        RoomDimensions dimensions1 = new RoomDimensions(1.0, 2.0, 3.0);
        RoomDimensions dimensions2 = new RoomDimensions(2.0, 2.0, 3.0);
        assertNotEquals(dimensions1, dimensions2);
    }

    /**
     * Tests the transitivity of equality in {@link RoomDimensions} objects.
     */
    @Test
    void transitivityInEquality() {
        RoomDimensions dimensions1 = new RoomDimensions(1.0, 2.0, 3.0);
        RoomDimensions dimensions2 = new RoomDimensions(1.0, 2.0, 3.0);
        RoomDimensions dimensions3 = new RoomDimensions(1.0, 2.0, 3.0);
        assertTrue(dimensions1.equals(dimensions2) && dimensions2.equals(dimensions3) && dimensions1.equals(dimensions3));
    }

    /**
     * Tests the comparison with null in {@link RoomDimensions} objects.
     */
    @Test
    void comparisonWithNull() {
        RoomDimensions roomDimensions = new RoomDimensions(1.0, 2.0, 3.0);
        assertNotEquals(null, roomDimensions);
    }

    /**
     * Tests the {@link RoomDimensions#toString()} method to ensure it returns the correct string representation of {@link RoomDimensions} object.
     */
    @Test
    void dimensionsToString() {
        RoomDimensions dimensions = new RoomDimensions(1.0, 2.0, 3.0);
        String expectedString = "RoomDimensions{length=1.0, width=2.0, height=3.0}";
        assertEquals(expectedString, dimensions.toString());
    }

    @Test
    void validHashCode() {
        RoomDimensions dimensions = new RoomDimensions(1.0, 2.0, 3.0);
        RoomDimensions dimensions2 = new RoomDimensions(1.0, 2.0, 3.0);
        assertEquals(dimensions.hashCode(), dimensions2.hashCode());
    }

    @Test
    void invalidHashCode() {
        RoomDimensions dimensions = new RoomDimensions(1.0, 2.0, 3.0);
        RoomDimensions dimensions2 = new RoomDimensions(1.0, 2.0, 4.0);
        assertNotEquals(dimensions.hashCode(), dimensions2.hashCode());
    }
}
