package smarthome.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.sensorfunctionality.SensorFunctionality;
import smarthome.domain.valueobjects.SensorFunctionalityID;

import java.util.ArrayList;
import java.util.List;

/**
 * A mapper class for the SensorFunctionality entity.
 */
@Component
public class SensorFunctionalityMapperDTO {

    /**
     * Converts a SensorFunctionality entity to a SensorFunctionalityDTO object.
     * @param sensorFunctionality The SensorFunctionality entity to be converted.
     * @return The corresponding SensorFunctionalityDTO object, or null if the input is null.
     */
    public SensorFunctionalityDTO sensorFunctionalityToDTO(SensorFunctionality sensorFunctionality) {
        if (sensorFunctionality == null) {
            return null;
        }
        return new SensorFunctionalityDTO(sensorFunctionality.toString());
    }

    /**
     * Converts a SensorFunctionalityID object to a SensorFunctionalityDTO object.
     * @param sensorFunctionalityID The SensorFunctionalityID to be converted.
     * @return The corresponding SensorFunctionalityDTO object, or null if the input is null.
     */
    public SensorFunctionalityDTO sensorFunctionalityToDTO(SensorFunctionalityID sensorFunctionalityID) {
        if (sensorFunctionalityID == null) {
            return null;
        }
        return new SensorFunctionalityDTO(sensorFunctionalityID.toString());
    }

    /**
     * Converts a list of SensorFunctionality objects to a list of SensorFunctionalityDTO objects.
     * @param sensorFunctionalitiesIDs The list of SensorFunctionality objects to be converted.
     * @return The list of SensorFunctionalityDTO objects with the corresponding SensorFunctionality objects.
     */
    public List<SensorFunctionalityDTO> sensorFunctionalityToDTOList(List<SensorFunctionalityID> sensorFunctionalitiesIDs) {
        List<SensorFunctionalityDTO> sensorFunctionalities = new ArrayList<>();
        for (SensorFunctionalityID sensorFunctionalityID : sensorFunctionalitiesIDs) {
            SensorFunctionalityDTO sensorFunctionalityDTO = sensorFunctionalityToDTO(sensorFunctionalityID);
            sensorFunctionalities.add(sensorFunctionalityDTO);
        }
        return sensorFunctionalities;
    }

}
