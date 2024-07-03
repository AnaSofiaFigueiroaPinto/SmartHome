package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorPropertiesTest {

    /**
     * Tests the empty constructor of the ActuatorProperties object
     */
    @Test
    public void verifyEmptyConstructor() {
        ActuatorProperties properties = new ActuatorProperties();
        assertNotNull(properties);
    }

    /**
     * Tests the constructor of the ActuatorProperties object with integer values
     */
    @Test
    public void validIntConstructor() {
        ActuatorProperties properties = new ActuatorProperties(100, 50);
        assertEquals(100, properties.getRangeInt().getUpperLimitInt());
        assertEquals(50, properties.getRangeInt().getLowerLimitInt());
    }

    /**
     * Tests the constructor of the ActuatorProperties object with equal integer values
     */
    @Test
    public void validIntegerConstructorWithSameLimits() {
        ActuatorProperties properties = new ActuatorProperties(1, 1);
        assertEquals(1, properties.getRangeInt().getUpperLimitInt());
        assertEquals(1, properties.getRangeInt().getLowerLimitInt());
    }

    /**
     * Tests the constructor of the ActuatorProperties object with decimal values
     */
    @Test
    public void validDecimalConstructor() {
        ActuatorProperties properties = new ActuatorProperties(100.5, 50.5, 2);
        assertEquals(100.5, properties.getRangeDecimal().getUpperLimitDecimal(), 0.01);
        assertEquals(50.5, properties.getRangeDecimal().getLowerLimitDecimal(), 0.01);
        assertEquals(2, properties.getRangeDecimal().getPrecision());
    }

    /**
     * Tests the constructor of the ActuatorProperties object with equal decimal values
     */
    @Test
    public void validDecimalConstructorWithSameLimitsAndZeroPrecision() {
        ActuatorProperties properties = new ActuatorProperties(50.5, 50.5, 0);
        assertEquals(50.5, properties.getRangeDecimal().getUpperLimitDecimal(), 0.01);
        assertEquals(50.5, properties.getRangeDecimal().getLowerLimitDecimal(), 0.01);
        assertEquals(0, properties.getRangeDecimal().getPrecision());
    }

    @Test
    public void invalidIntConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new ActuatorProperties(50, 100));
    }

    @Test
    public void invalidDecimalConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new ActuatorProperties(50.5, 100.5, 2));
    }

}