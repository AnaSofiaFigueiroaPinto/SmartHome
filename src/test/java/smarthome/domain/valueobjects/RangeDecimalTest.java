package smarthome.domain.valueobjects;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeDecimalTest {

    /**
     * Tests the constructor of the RangeDecimal object with valid arguments
     */
    @Test
    void validConstructorArguments() {
        RangeDecimal rangeDecimal = new RangeDecimal(100.5, 50.5, 2);
        assertEquals(100.5, rangeDecimal.getUpperLimitDecimal(), 0.01);
        assertEquals(50.5, rangeDecimal.getLowerLimitDecimal(), 0.01);
        assertEquals(2, rangeDecimal.getPrecision());
    }

    /**
     * Tests the constructor of the RangeDecimal object with invalid arguments
     */
    @Test
    void invalidConstructorArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RangeDecimal(100.5, 50.5, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new RangeDecimal(50.5, 100.5, 2);
        });
    }

    /**
     * Tests the constructor of the RangeDecimal object with equal decimal values and zero precision
     */
    @Test
    public void validConstructorWithSameLimitsAndZeroPrecision() {
        RangeDecimal rangeDecimal = new RangeDecimal(50.5, 50.5, 0);
        assertEquals(50.5, rangeDecimal.getUpperLimitDecimal(), 0.01);
        assertEquals(50.5, rangeDecimal.getLowerLimitDecimal(), 0.01);
        assertEquals(0, rangeDecimal.getPrecision());
    }

    /**
     * Test getUpperLimitDecimal method of RangeDecimal object
     */
    @Test
    void getUpperLimitDecimal() {
        RangeDecimal rangeDecimal = new RangeDecimal(100.5, 50.5, 2);
        assertEquals(100.5, rangeDecimal.getUpperLimitDecimal(), 0.01);

    }

    /**
     * Test getLowerLimitDecimal method of RangeDecimal object
     */
    @Test
    void getLowerLimitDecimal() {
        RangeDecimal rangeDecimal = new RangeDecimal(100.5, 50.5, 2);
        assertEquals(50.5, rangeDecimal.getLowerLimitDecimal(), 0.01);
    }

    /**
     * Tests getPrecision method of RangeDecimal object
     */
    @Test
    void getPrecision() {
        RangeDecimal rangeDecimal = new RangeDecimal(100.5, 50.5, 2);
        assertEquals(2, rangeDecimal.getPrecision());
    }
}