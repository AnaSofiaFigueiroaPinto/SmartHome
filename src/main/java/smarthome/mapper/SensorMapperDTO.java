package smarthome.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.SensorID;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods to map Sensor objects to SensorDTO objects.
 */
@Component
public class SensorMapperDTO {

    /**
     * Converts a SensorID object to a SensorDTO object.
     *
     * @param sensorID The SensorID object to be converted.
     * @return A SensorDTO object corresponding to the provided SensorID, or null if the input is null.
     */
    public SensorDTO sensorToDTO (SensorID sensorID) {
        if (sensorID == null ) {
            return null;
        }
        String sensorName = sensorID.toString();
        return new SensorDTO(sensorName);
    }

    /**
     * Converts a list of SensorID objects to a list of SensorDTO objects.
     *
     * @param sensorIDs The list of SensorID objects to be converted.
     * @return A list of SensorDTO objects corresponding to the provided list of SensorIDs.
     */
    public List<SensorDTO> sensorIDsToDTOList (List<SensorID> sensorIDs) {
        List<SensorDTO> listOfSensorsDTO = new ArrayList<>();
        for (SensorID sensorID : sensorIDs) {
            SensorDTO sensorDTO = sensorToDTO(sensorID);
            listOfSensorsDTO.add(sensorDTO);
        }
        return listOfSensorsDTO;
    }
}
