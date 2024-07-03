package smarthome.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.service.internaldto.InternalDeviceDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping Device objects to DeviceDTO objects.
 */
@Component
public class DeviceMapperDTO {

    /**
     * Converts a DeviceID object to a DeviceDTO object.
     * @param deviceID The DeviceID to be converted.
     * @return DeviceDTO representing the converted DeviceID.
     */
    public DeviceDTO deviceToDTO (DeviceID deviceID) {
        if (deviceID == null) {
            return null;
        }
        String deviceName = deviceID.toString();
        return new DeviceDTO(deviceName);
    }

    /**
     * Converts a collection of DeviceIDs to a list of DeviceDTO objects.
     * @param devicesIDs The collection of DeviceIDs to be converted.
     * @return List of DeviceDTO objects.
     */
    public List<DeviceDTO> deviceIDsToDTOList(Iterable<DeviceID> devicesIDs) {
        List<DeviceDTO> listOfDevicesDTO = new ArrayList<>();
        for (DeviceID deviceID : devicesIDs) {
            DeviceDTO deviceDTO = deviceToDTO(deviceID);
            listOfDevicesDTO.add(deviceDTO);
        }
        return listOfDevicesDTO;
    }

    /**
     * Converts an InternalDeviceDTO object to a DeviceDTO object.
     * @param internalDeviceDTO The InternalDeviceDTO to be converted.
     * @return DeviceDTO representing the converted InternalDeviceDTO.
     */
    public DeviceDTO internalDeviceDTOToDTO(InternalDeviceDTO internalDeviceDTO) {
        if (internalDeviceDTO == null) {
            return null;
        }
        return new DeviceDTO(internalDeviceDTO.deviceID.toString(), internalDeviceDTO.deviceModel.toString(), internalDeviceDTO.deviceStatus.toString(), internalDeviceDTO.roomID.toString());
    }

    /**
     * Converts a collection of InternalDeviceDTO objects to a list of DeviceDTO objects.
     * @param internalDevicesDTO The collection of InternalDeviceDTO objects to be converted.
     * @return List of DeviceDTO objects.
     */
    public List<DeviceDTO> internalDeviceDTOsToDTOList(Iterable<InternalDeviceDTO> internalDevicesDTO) {
        List<DeviceDTO> listOfDevicesDTO = new ArrayList<>();
        for (InternalDeviceDTO internalDeviceDTO : internalDevicesDTO) {
            DeviceDTO deviceDTO = internalDeviceDTOToDTO(internalDeviceDTO);
            listOfDevicesDTO.add(deviceDTO);
        }
        return listOfDevicesDTO;
    }

}