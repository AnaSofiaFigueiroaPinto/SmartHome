package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.BlindSetterActuator;
import smarthome.domain.device.Device;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.util.exceptions.DeviceNotFoundException;

import java.util.*;

@Service
public class CloseBlindRollerService {
    /**
     * Repository used for storing actuator objects.
     */
    private ActuatorRepository actuatorRepository;

    /**
     * Repository used for storing device objects.
     */
    private DeviceRepository deviceRepository;

    /**
     * Constructor of DeviceService objects with the given factory, device repository and room repository.
     *
     * @param actuatorRepository valid instance of class used for persisting instances of actuator.
     * @param deviceRepository valid instance of class used for persisting instances of device.
     */
    public CloseBlindRollerService(ActuatorRepository actuatorRepository, DeviceRepository deviceRepository) {
        this.actuatorRepository = actuatorRepository;
        this.deviceRepository = deviceRepository;
    }

    /**
     * Method to obtain a map of DeviceID and RoomID according to the functionality of the actuator.
     *
     * @param actuatorFunctionalityID The functionality of the actuator.
     * @return A map of DeviceID and RoomID according to the functionality of the actuator.
     */
    public Map<DeviceID, RoomID> getMapOfDeviceIDAndRoomIDAccordingToFunctionality(ActuatorFunctionalityID actuatorFunctionalityID) {
        Iterable<Actuator> actuatorsWithBlindSetterFunctionality = actuatorRepository.findByActuatorFunctionalityID(actuatorFunctionalityID);


        //If the iterable has no entities, return an empty map
        if (!actuatorsWithBlindSetterFunctionality.iterator().hasNext()) {
            return Collections.emptyMap();
        }

        //Create a map to store the DeviceID and RoomID
        Map<DeviceID, RoomID> deviceIDRoomIDMap = new LinkedHashMap<>();
        List<Device> listDevices = new ArrayList<>();

        //Iterate through the list of actuators with blind setter functionality to get the correspondent deviceID
        for (Actuator actuator : actuatorsWithBlindSetterFunctionality) {
            DeviceID deviceID = actuator.getDeviceName();
            Optional<Device> deviceOptional = deviceRepository.findEntityByID(deviceID);
            //If the device is present and active, add it to the list of devices
            if (deviceOptional.isPresent() && deviceOptional.get().isActive())
                listDevices.add(deviceOptional.get());
            //Iterate through the list of devices to get the correspondent RoomID
            for (Device device : listDevices) {
                RoomID roomID = device.getRoomID();
                //Add the DeviceID and RoomID to the map
                deviceIDRoomIDMap.put(deviceID, roomID);
            }
        }
        return deviceIDRoomIDMap;
    }

    /**
     * Checks if a device is active based on its {@code DeviceID}.
     *
     * @param deviceID the ID of the device to be checked.
     * @return {@code true} if the device is found and is active; {@code false} otherwise.
     * @throws DeviceNotFoundException if the device is not found in the repository.
     */
    private boolean checkIfDeviceIsActive(DeviceID deviceID) {
        Optional<Device> device = deviceRepository.findEntityByID(deviceID);
        if (device.isPresent()) {
            return device.get().isActive();
        } else {
            throw new DeviceNotFoundException();
        }
    }

    /**
     * Method to get the BlindSetterActuator from the device and change its status.
     *
     * @param deviceID              The unique identifier of the device to deactivate.
     * @return {@code true} if the BlindSetterActuator was found and the status was changed,
     * {@code false} otherwise.
     */
    public boolean setActuatorStateOfBlindRoller(DeviceID deviceID, ActuatorFunctionalityID actuatorFunctionalityID, int percentage) {
        //If the device is not active, return false
        if (!checkIfDeviceIsActive(deviceID))
            return false;

        //Provides the actuators of the device with the given actuatorFunctionalityID
        Iterable<Actuator> actuatorsOfDevice = actuatorRepository.findByDeviceIDAndActuatorFunctionalityID(deviceID, actuatorFunctionalityID);

        if (!actuatorsOfDevice.iterator().hasNext())
            return false;
        Actuator actuator = actuatorsOfDevice.iterator().next();
        BlindSetterActuator blindSetterActuator = (BlindSetterActuator) actuator;
        return blindSetterActuator.setActuatorSpecificValue(percentage);
    }
}
