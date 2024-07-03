package smarthome.controllercli;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorMapperDTO;
import smarthome.service.SensorService;

import java.util.Collections;
import java.util.List;

public class ListTemperatureSensorsInDeviceController {

    /**
     * The service responsible for handling sensor-related operations.
     */
    private final SensorService sensorService;

    /**
     * Constructs a ListTemperatureSensorsInDeviceController with the specified service.
     *
     * @param sensorService The service responsible for handling sensor-related operations.
     */
    public ListTemperatureSensorsInDeviceController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * Retrieves a list of SensorDTO objects representing temperature sensors associated with the specified device.
     *
     * @param deviceDTO The DeviceDTO object representing the device for which temperature sensors are to be retrieved.
     * @return A list of SensorDTO objects representing temperature sensors associated with the device,
     * or null if an error occurs during retrieval.
     */
    public List<SensorDTO> getListOfTemperatureSensorDTOInDevice(DeviceDTO deviceDTO) {
        try {
            DeviceID deviceID = new DeviceID(deviceDTO.deviceName);
            SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");
            SensorMapperDTO sensorMapperDTO = new SensorMapperDTO();
            List<SensorID> sensorIDs = sensorService.findSensorsIDOfADeviceBySensorFunctionality(deviceID, sensorFunctionalityID);
            return sensorMapperDTO.sensorIDsToDTOList(sensorIDs);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }
}
