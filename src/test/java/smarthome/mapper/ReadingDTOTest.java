package smarthome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadingDTOTest {

    /**
     * Validate the construction of a ReadingDTO object.
     */
    @Test
    void successfulConstructionOfReadingDTO() {
        String valueWithUnit = "10.0 C";
        ReadingDTO readingDTO = new ReadingDTO(valueWithUnit);
        assertEquals(valueWithUnit, readingDTO.valueWithUnit);
    }

    /**
     * Validate the equals method of a ReadingDTO object.
     */
    @Test
    void successfulEqualsOfReadingDTO() {
        ReadingDTO readingDTO1 = new ReadingDTO("10.0 C");
        ReadingDTO readingDTO2 = new ReadingDTO("10.0 C");
        assertTrue(readingDTO1.equals(readingDTO2));
    }

    /**
     * Validate the hashCode method of a ReadingDTO object.
     */
    @Test
    void successfulHashCodeOfReadingDTO() {
        ReadingDTO readingDTO = new ReadingDTO("10.0 C");
        assertEquals(readingDTO.hashCode(), readingDTO.hashCode());
    }

    /**
     * Validate the toString method of a ReadingDTO object.
     */
    @Test
    void testEqualsWithSameObject() {
        ReadingDTO readingDTO = new ReadingDTO("10.0 C");
        assertEquals(readingDTO, readingDTO);
    }

    /**
     * Validate the equals method of a ReadingDTO object with a null object.
     */
    @Test
    void testEqualsWithNull() {
        ReadingDTO readingDTO = new ReadingDTO("10.0 C");
        assertNotEquals(readingDTO, null);
    }

    /**
     * Validate the equals method of a ReadingDTO object with an object of a different class.
     */
    @Test
    void testEqualsWithDifferentClass()
    {
        ReadingDTO readingDTO = new ReadingDTO("10.0 C");
        assertNotEquals(readingDTO, new Object());
    }

}