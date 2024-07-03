package smarthome.util;

import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.Reading;

import static org.junit.jupiter.api.Assertions.*;

class ReadingUtilTest {

    /**
     * Test is method returns correct string with single value and unit
     */
    @Test
    void getReadingSingleValuesAndUnits() {
        Reading reading = new Reading("10", "C");
        String result = ReadingUtil.getReadingAsSingleString(reading);
        assertEquals("10 C", result);
    }

    /**
     * Test is method returns correct string with multiple values and units
     */
    @Test
    void getReadingMultipleValuesAndUnits() {
        Reading reading = new Reading("10;20", "C;F");
        String result = ReadingUtil.getReadingAsSingleString(reading);
        assertEquals("10 C and 20 F", result);
    }

    /**
     * Test is method returns correct string with multiple values when some values don't have units
     */
    @Test
    void getReadingValueWithout1Unit() {
        Reading reading = new Reading("10;OFF", "C;*");
        String result = ReadingUtil.getReadingAsSingleString(reading);
        assertEquals("10 C and OFF", result);
    }

    /**
     * Test is method returns correct string with multiple values when none have units
     */
    @Test
    void getReadingWithout2Units() {
        Reading reading = new Reading("ON;OFF", "*;*");
        String result = ReadingUtil.getReadingAsSingleString(reading);
        assertEquals("ON and OFF", result);
    }

    /**
     * Test is method returns correct string with single value and unit
     */
    @Test
    void getReadingSingleValueAndDoubleUnits() {
        Reading reading = new Reading("100", "C;C");
        String result = ReadingUtil.getReadingAsSingleString(reading);
        assertEquals("100 C", result);
    }

    /**
     * Test is method returns correct string with single value and unit
     */
    @Test
    void
    getReadingDoubleValueAndSingleUnits() {
        Reading reading = new Reading("4;20", "W");
        String result = ReadingUtil.getReadingAsSingleString(reading);
        assertEquals("4 W and 20 W", result);
    }
}
