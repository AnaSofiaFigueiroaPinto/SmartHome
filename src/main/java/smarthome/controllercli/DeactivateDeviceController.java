package smarthome.controllercli;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.mapper.DeviceDTO;
import smarthome.service.DeviceService;

/**
 * Controller class responsible for deactivate an existent device.
 */
public class DeactivateDeviceController {
    /**
     * DeviceService instance to interact with device-related operations.
     */
    private final DeviceService deviceService;

    /**
     * Constructs a new DeactivateDeviceController with the specified device service.
     *
     * @param deviceService The DeviceService instance to interact with for device-related operations.
     */
    public DeactivateDeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Method to deactivate a Device.
     *
     * @param deviceDTO DeviceDTO object to identify the device to be deactivated.
     * @return {@code true} if successful, {@code false} otherwise.
     */
    public boolean deactivateDevice(DeviceDTO deviceDTO) {
        try {
            DeviceID deviceID = new DeviceID(deviceDTO.deviceName);
            return deviceService.deactivateDevice(deviceID);
        } catch (RuntimeException e) {
            return false;
        }
    }
}
