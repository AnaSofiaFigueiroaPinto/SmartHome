package smarthome.controllercli;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.mapper.*;
import smarthome.service.ValueService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller responsible for listing all measurements of a Device in a given period.
 */
public class ListAllMeasurmentesOfDeviceInPeriodController {

    /**
     * Service used by controller
     */
    private ValueService valueService;

    /**
     * Constructor of the controller
     * @param valueService Service used by controller
     */
    public ListAllMeasurmentesOfDeviceInPeriodController
    (ValueService valueService) {
        this.valueService = valueService;
    }

    /**
     * Method retrieves all measurements(Readings) for all Sensor objects present in Device.
     *
     * @param deviceDTO DeviceDTO object containing the ID of the device.
     * @param start Timestamp object representing the start of the "given period".
     * @param end Timestamp object representing the end of the "given period".
     *
     * @return Map with SensorFunctionalityDTO as key and List of ReadingDTO as value.
     */
    public Map<SensorFunctionalityDTO, List<ReadingDTO>>
    listAllMeasurmentesOfDeviceInPeriod (DeviceDTO deviceDTO, Timestamp start, Timestamp end) {
        try {
            String deviceName = deviceDTO.deviceName;
            DeviceID deviceID = new DeviceID(deviceName);

            Map<SensorFunctionalityID, List<Reading>> domainVOMap =
                    valueService.
                            getAllMeasurementsForDeviceBetweenPeriod(deviceID, start, end);

            Map<SensorFunctionalityDTO, List<ReadingDTO>> convertedDTOMap = convertToDTO(domainVOMap);

            return convertedDTOMap;
        } catch (NullPointerException exception) {
            return null;
        }
    }

    /**
     * Method that converts the given Map with VO objects to a Map with DTO objects.
     *
     * @param readings Map<SensorFunctionalityID, List<Reading>>
     * containing VO SensorFunctionalityID and List of VO Reading objects.
     *
     * @return Map<SensorFunctionalityDTO, List<ReadingDTO>>
     * using DTO objects converted based on the given VOs.
     */
    private Map<SensorFunctionalityDTO, List<ReadingDTO>>
    convertToDTO (Map<SensorFunctionalityID, List<Reading>> readings) {

        Map<SensorFunctionalityDTO, List<ReadingDTO>> convertedDTOMap = new HashMap<>();

        SensorFunctionalityMapperDTO mapperSensorFunc = new SensorFunctionalityMapperDTO();
        ReadingMapperDTO mapperReading = new ReadingMapperDTO();

        for (Map.Entry<SensorFunctionalityID, List<Reading>> entry : readings.entrySet()) {

            SensorFunctionalityDTO sensorFunctionalityDTO =
                    mapperSensorFunc.sensorFunctionalityToDTO(entry.getKey());
            List<Reading> listOfReadings = entry.getValue();
            List<ReadingDTO> listReadingsDTO = mapperReading.readingsToDTOList(listOfReadings);

            convertedDTOMap.put(sensorFunctionalityDTO, listReadingsDTO);
        }

        return convertedDTOMap;
    }
}
