package smarthome.controllercli;

import smarthome.domain.valueobjects.RoomID;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.DeviceMapperDTO;
import smarthome.mapper.RoomDTO;
import smarthome.service.DeviceService;

import java.util.List;

/**
* Controller class responsible to list all Devices in a Room.
*/
public class ListAllDevicesInRoomController {

    /**
     * DeviceService instance to interact with device-related operations.
     */
    private final DeviceService deviceService;

    /**
     * Constructs a new controller with the specified services.
     * @param deviceService The DeviceService instance to interact with for device-related operations.
     */
    public ListAllDevicesInRoomController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Retrieves a list of DeviceDTO objects in the specified room.
     * @param roomDTO The RoomDTO object representing the room for which to retrieve the list of devices.
     * @return A list of DeviceDTO objects associated with the specified room.
     */
    public List<DeviceDTO> getListOfDevicesDTOInRoom(RoomDTO roomDTO) {
        try {
            RoomID roomID = new RoomID(roomDTO.roomName);
            DeviceMapperDTO deviceMapperDTO = new DeviceMapperDTO();
            return deviceMapperDTO.deviceIDsToDTOList(deviceService.getListOfDevicesInRoom(roomID));
        } catch (RuntimeException e) {
            return null;
        }
    }

}
