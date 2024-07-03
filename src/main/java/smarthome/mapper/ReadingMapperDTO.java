package smarthome.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.Reading;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReadingMapperDTO {
    /**
     * Method to convert a List<Reading> to a List<ReadingDTO>
     * @param readings list of Reading objects (VO)
     * @return list of ReadingDTO where each ReadingDTO object is based on the output of the Reading object
     */
    public List<ReadingDTO> readingsToDTOList (List<Reading> readings) {
        List<ReadingDTO> listOfReadingsDTO = new ArrayList<>();
        for (Reading reading : readings) {
            ReadingDTO readingDTO = new ReadingDTO(reading.getAllValuesWithUnits());
            listOfReadingsDTO.add(readingDTO);
        }
        return listOfReadingsDTO;
    }
}
