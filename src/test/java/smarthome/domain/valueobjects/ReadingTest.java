package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadingTest {

    /**
     * Successfully create Reading object
     */
    @Test
    void successfullyCreateReading() {
        String readingValue = "100";
        String readingUnit = "C";
        Reading reading = new Reading(readingValue, readingUnit);
        assertEquals(readingValue, reading.getMeasurement());
        assertEquals(readingUnit, reading.getUnit());
    }

    /**
     * Fail to create Reading object due to empty value
     */
    @Test
    void failToCreateReadingDueToEmptyValue() {
        assertThrows(IllegalArgumentException.class,() -> new Reading("", "C"));
    }

    /**
     * Fail to create Reading object due to blank value
     */
    @Test
    void failToCreateReadingDueToBlankValue() {
        assertThrows(IllegalArgumentException.class,() -> new Reading(" ", "C"));
    }


    /**
     * Fail to create Reading object due to null value
     */
    @Test
    void failToCreateReadingDueToNullValue() {
        assertThrows(IllegalArgumentException.class,() -> new Reading(null, "C"));
    }

    /**
     * Fail to create Reading object due to empty unit
     */
    @Test
    void failToCreateReadingDueToEmptyUnit() {
        assertThrows(IllegalArgumentException.class,() -> new Reading("100", ""));
    }

    /**
     * Fail to create Reading object due to blank unit
     */
    @Test
    void failToCreateReadingDueToBlankUnit() {
        assertThrows(IllegalArgumentException.class,() -> new Reading("100", " "));
    }

    /**
     * Fail to create Reading object due to null unit
     */
    @Test
    void failToCreateReadingDueToNullUnit() {
        assertThrows(IllegalArgumentException.class,() -> new Reading("100", null));
    }

    /**
     * Successfully retrieve value from Reading object
     */
    @Test
    void successfullyRetrieveValue() {
        String readingValue = "100";
        String readingUnit = "C";
        Reading reading = new Reading(readingValue, readingUnit);
        assertEquals(readingValue, reading.getMeasurement());
    }

    /**
     * Successfully retrieve unit from Reading object
     */
    @Test
    void successfullyRetrieveUnit() {
        String readingValue = "100";
        String readingUnit = "C";
        Reading reading = new Reading(readingValue, readingUnit);
        assertEquals(readingUnit, reading.getUnit());
    }

    @Test
    void successfulGetAllValuesWithAllUnitsAsString() {
        String value = "100;ON";
        String unit = "%;*";
        Reading reading = new Reading(value, unit);
        String expected = "100 % and ON";
        assertEquals(expected, reading.getAllValuesWithUnits());
    }


}