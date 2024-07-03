package smarthome.persistence.jpa.datamodel;

import org.springframework.stereotype.Component;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.DeviceStatus;
import smarthome.domain.valueobjects.RoomID;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperDeviceDataModel {


    /**
     * Converts a DeviceDataModel object to a corresponding Device object using the provided FactoryDevice instance.
     *
     * @param factoryDevice   The factory used to create Device instances.
     * @param deviceDataModel The DeviceDataModel to be converted.
     * @return The Device object converted from the DeviceDataModel.
     */

    public Device toDomain(FactoryDevice factoryDevice, DeviceDataModel deviceDataModel)
    {
        try {
            String deviceName = deviceDataModel.getDeviceID();
            String model = deviceDataModel.getDeviceModel();
            DeviceStatus deviceStatus = DeviceStatus.valueOf(deviceDataModel.getDeviceStatus());
            String roomName = deviceDataModel.getRoomID();

            DeviceID deviceID = new DeviceID(deviceName);
            DeviceModel deviceModel = new DeviceModel(model);
            RoomID roomID = new RoomID(roomName);

            return factoryDevice.createDevice(deviceID, deviceModel, roomID, deviceStatus);

        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts a list of DeviceDataModel objects to a list of corresponding Device objects using the provided FactoryDevice instance.
     *
     * @param factoryDevice       The factory used to create Device instances.
     * @param deviceDataModelList The list of DeviceDataModel objects to be converted.
     * @return An Iterable containing the converted Device objects.
     */
    public Iterable<Device> toDomainList(FactoryDevice factoryDevice, Iterable<DeviceDataModel> deviceDataModelList)
    {
        if (factoryDevice == null || deviceDataModelList == null) {
            return null;
        }

        List<Device> deviceList = new ArrayList<>();
        deviceDataModelList.forEach(deviceDataModel -> {
            Device device = toDomain(factoryDevice, deviceDataModel);
            deviceList.add(device);
        });
        return deviceList;

    }
}
