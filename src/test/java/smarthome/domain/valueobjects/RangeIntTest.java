package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeIntTest {
    /**
     * Tests the constructor of the RangeInt object with valid arguments
     */
    @Test
    void validConstructorArguments() {
        RangeInt rangeInt = new RangeInt(100, 50);
        assertEquals(100, rangeInt.getUpperLimitInt());
        assertEquals(50, rangeInt.getLowerLimitInt());
    }

    /**
     * Tests the constructor of the RangeInt object with invalid arguments
     */
    @Test
    void invalidConstructorArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RangeInt(10, 500);
        });
    }

    /**
     * Test getUpperLimitInt method of RangeInt object
     */
    @Test
    void getUpperLimitInt() {
        RangeInt rangeInt = new RangeInt(100, 50);
        assertEquals(100, rangeInt.getUpperLimitInt());

    }

    /**
     * Test getLowerLimitInt method of RangeInt object
     */
    @Test
    void getLowerLimitInt() {
        RangeInt rangeInt = new RangeInt(100, 50);
        assertEquals(50, rangeInt.getLowerLimitInt());
    }
}