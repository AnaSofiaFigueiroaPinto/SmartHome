package smarthome.controllercli;

import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.mapper.*;
import smarthome.service.ListAllDevicesInHouseByFunctionalityService;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class responsible to list all devices in House according to functionality.
 */
public class ListAllDevicesInHouseByFunctionalityController {

    private final ListAllDevicesInHouseByFunctionalityService listAllDevicesInHouseByFunctionalityService;


    /**
     * Constructor for ListAllDevicesInHouseByFunctionality controller object.
     */
    public ListAllDevicesInHouseByFunctionalityController(ListAllDevicesInHouseByFunctionalityService listAllDevicesInHouseByFunctionalityService) {
        this.listAllDevicesInHouseByFunctionalityService = listAllDevicesInHouseByFunctionalityService;
    }

    /**
     * Method to get all devices in house and their room location, grouped by sensor functionality.
     *
     * @return A map with Object as key (ActuatorFunctionalityDTO or SensorFunctionalityDTO) and a Map <RoomDTO,DeviceDTO> as value.
     */

    public Map<Object, Map<RoomDTO, DeviceDTO>> getListOfDevicesByFunctionality() {
        Map<Object, Map<RoomID, DeviceID>> devicesWithRoomGroupedByFunctionalityMap = listAllDevicesInHouseByFunctionalityService.devicesGroupedByFunctionalityAndLocation();

        ActuatorFunctionalityMapperDTO actuatorFunctionalityMapperDTO = new ActuatorFunctionalityMapperDTO();
        SensorFunctionalityMapperDTO sensorFunctionalityMapperDTO = new SensorFunctionalityMapperDTO();
        RoomMapperDTO roomMapperDTO = new RoomMapperDTO();
        DeviceMapperDTO deviceMapperDTO = new DeviceMapperDTO();

        //Convert to a map with DTOs
        Map<Object, Map<RoomDTO, DeviceDTO>> devicesDTOWithRoomDTOGroupedByFunctionalityDTOMap = new HashMap<>();
        devicesWithRoomGroupedByFunctionalityMap.forEach((functionality, roomAndDevicesMap) -> {
            Object functionalityDTO = new Object();
            if (functionality instanceof ActuatorFunctionalityID actuatorFunctionalityID)
                functionalityDTO = actuatorFunctionalityMapperDTO.actuatorFunctionalityToDTO(actuatorFunctionalityID);
            if (functionality instanceof SensorFunctionalityID sensorFunctionalityID)
                functionalityDTO = sensorFunctionalityMapperDTO.sensorFunctionalityToDTO(sensorFunctionalityID);
            Map<RoomDTO, DeviceDTO> roomAndDevicesDTOMap = new HashMap<>();
            roomAndDevicesMap.forEach((roomID, deviceID) -> {
                RoomDTO roomDTO = roomMapperDTO.roomToDTO(roomID);
                DeviceDTO deviceDTO = deviceMapperDTO.deviceToDTO(deviceID);
                roomAndDevicesDTOMap.put(roomDTO, deviceDTO);
            });
            devicesDTOWithRoomDTOGroupedByFunctionalityDTOMap.put(functionalityDTO, roomAndDevicesDTOMap);
        });
        return devicesDTOWithRoomDTOGroupedByFunctionalityDTOMap;
    }
}
