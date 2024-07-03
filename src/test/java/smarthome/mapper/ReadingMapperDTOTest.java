package smarthome.mapper;

import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.Reading;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReadingMapperDTOTest {
    @Test
    void readingsToDTOList() {
        Reading reading1 = mock(Reading.class);
        String valueWithUnit1 = "10 C";
        when(reading1.getAllValuesWithUnits()).thenReturn(valueWithUnit1);
        Reading reading2 = mock(Reading.class);
        String valueWithUnit2 = "20 C and OFF";
        when(reading2.getAllValuesWithUnits()).thenReturn(valueWithUnit2);

        List<Reading> listOfReadings = List.of(reading1, reading2);

        ReadingMapperDTO readingMapperDTO = new ReadingMapperDTO();

        List<ReadingDTO> listOfReadingsDTO = readingMapperDTO.readingsToDTOList(listOfReadings);
        assertTrue(listOfReadings.size() == listOfReadingsDTO.size());

    }
}