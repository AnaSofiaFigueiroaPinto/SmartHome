package smarthome.persistence.jpa.datamodel;

import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.device.ImpFactoryDevice;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.DeviceStatus;
import smarthome.domain.valueobjects.RoomID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceDataModelTest {

    /**
     * Test to verify if the DeviceDataModel object is being created.
     */
    @Test
    void testDeviceDataModel() {
        DeviceDataModel deviceDataModel = new DeviceDataModel();
        assertNotNull(deviceDataModel);
    }

    /**
     * Test to verify if the DeviceDataModel object is being created with the correct values.
     */
    @Test
    void verifyIfDeviceDataModelConversion() {

        DeviceID deviceID = new DeviceID("deviceID");
        DeviceModel deviceModel = new DeviceModel("deviceModel");
        RoomID roomID = new RoomID("Kitchen");

        FactoryDevice factoryDevice = new ImpFactoryDevice();
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);

        assertEquals("deviceID", deviceDataModel.getDeviceID());
        assertEquals("deviceModel", deviceDataModel.getDeviceModel());
        assertEquals(DeviceStatus.ACTIVE.toString(), deviceDataModel.getDeviceStatus());
        assertEquals("Kitchen", deviceDataModel.getRoomID());
    }

    /**
     * Test where the  method is called with a valid Device object.
     * The method should return true, indicating that the update was successful.
     */
    @Test
    void verifyUpdatedFromDomain() {
        DeviceID deviceID = new DeviceID("deviceID");
        DeviceModel deviceModel = new DeviceModel("deviceModel");
        RoomID roomID = new RoomID("Kitchen");

        FactoryDevice factoryDevice = new ImpFactoryDevice();
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        DeviceDataModel deviceDataModel = new DeviceDataModel();

        boolean deviceUpdated = deviceDataModel.updatedFromDomain(device);

        assertTrue(deviceUpdated);
    }

    /**
     * Test where the method is called with a null argument.
     * The method should return false, indicating that the update failed.
     */
    @Test
    void failUpdatedFromDomain() {
        DeviceDataModel deviceDataModel = new DeviceDataModel();
        boolean deviceUpdated = deviceDataModel.updatedFromDomain(null);

        assertFalse(deviceUpdated);
    }
}