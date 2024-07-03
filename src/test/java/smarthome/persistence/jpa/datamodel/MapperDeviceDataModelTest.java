package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.DeviceStatus;
import smarthome.domain.valueobjects.RoomID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MapperDeviceDataModelTest {
    /**
     * The variables used in the tests.
     */
    private String deviceName;
    private String modelName;
    private String roomName;
    private DeviceStatus status;
    private FactoryDevice factoryDouble;
    private MapperDeviceDataModel mapper;
    private DeviceDataModel deviceDataModelDouble;
    private Device deviceDouble;

    /**
     * Sets up the necessary test environment before each test case.
     * Initializes mock objects and defines behavior for their methods to simulate real objects.
     */
    @BeforeEach
    void setUp() {
        // Mocking necessary objects
        deviceDataModelDouble = mock(DeviceDataModel.class);
        factoryDouble = mock(FactoryDevice.class);
        deviceDouble = mock(Device.class);

        // Creating the class to test
        mapper = new MapperDeviceDataModel();

        // Data for the DeviceDataModel
        deviceName = "deviceID";
        modelName = "deviceModel";
        roomName = "roomID";
        status = DeviceStatus.valueOf("ACTIVE");

        // Setting up mock behavior for the DeviceDataModel
        when(deviceDataModelDouble.getDeviceID()).thenReturn(deviceName);
        when(deviceDataModelDouble.getDeviceModel()).thenReturn(modelName);
        when(deviceDataModelDouble.getDeviceStatus()).thenReturn(valueOf(status));
    }

    /**
     * Test method to verify the conversion of a single DeviceDataModel object to a Device object.
     */

    @Test
    void successConvertDeviceDataModelToDomain() {
        try (MockedConstruction<RoomID> roomIdConstruction = mockConstruction(RoomID.class, (mock, context) -> {
            when(deviceDataModelDouble.getRoomID()).thenReturn(roomName);

        })) {
            try (MockedConstruction<DeviceModel> deviceModelConstruction = mockConstruction(DeviceModel.class, (mock, context) -> {
                when(deviceDataModelDouble.getDeviceModel()).thenReturn(modelName);

            })) {
                try (MockedConstruction<DeviceStatus> deviceStatusConstruction = mockConstruction(DeviceStatus.class, (mock, context) -> {
                    when(deviceDataModelDouble.getDeviceStatus()).thenReturn(valueOf(status));

                })) {
                    try (MockedConstruction<DeviceID> deviceIDConstruction = mockConstruction(DeviceID.class, (mock, context) -> {
                        when(deviceDataModelDouble.getDeviceID()).thenReturn(deviceName);

                        // Setting up mock behavior for the FactoryDevice
                        when(factoryDouble.createDevice(any(), any(), any(), any())).thenReturn(deviceDouble);

                    })) {
                        Device result = mapper.toDomain(factoryDouble, deviceDataModelDouble);
                        assertEquals(deviceDouble, result);
                    }
                }
            }
        }
    }

    /**
     * Test the method to convert the device data model to device model to fail when the data model is null.
     */
    @Test
    void failConvertDeviceDataModelToDomainNullDataModel() {
        Device deviceDomain = mapper.toDomain(factoryDouble, null);
        assertNull(deviceDomain);
    }

    /**
     * Test the method to convert the device data model to device model to fail when the factory is null.
     */
    @Test
    void failConvertDeviceDataModelToDomainNullFactory() {
        try (MockedConstruction<RoomID> roomIdConstruction = mockConstruction(RoomID.class, (mock, context) -> {
            when(deviceDataModelDouble.getRoomID()).thenReturn(roomName);

        })) {
            try (MockedConstruction<DeviceModel> deviceModelConstruction = mockConstruction(DeviceModel.class, (mock, context) -> {
                when(deviceDataModelDouble.getDeviceModel()).thenReturn(modelName);

            })) {
                try (MockedConstruction<DeviceStatus> deviceStatusConstruction = mockConstruction(DeviceStatus.class, (mock, context) -> {
                    when(deviceDataModelDouble.getDeviceStatus()).thenReturn(valueOf(status));

                })) {
                    try (MockedConstruction<DeviceID> deviceIDConstruction = mockConstruction(DeviceID.class, (mock, context) -> {
                        when(deviceDataModelDouble.getDeviceID()).thenReturn(deviceName);

                        // Setting up mock behavior for the FactoryDevice
                        when(factoryDouble.createDevice(any(), any(), any(), any())).thenReturn(deviceDouble);

                    })) {

                        Device deviceDomain = mapper.toDomain(null, deviceDataModelDouble);
                        assertNull(deviceDomain);
                    }
                }
            }
        }
    }


    /**
     * Test method to verify the conversion of a list of DeviceDataModel objects to a list of Device objects.
     */
    @Test
    void successConvertDeviceListToDomain() {
        // Creating a list of DeviceDataModel objects
        List<DeviceDataModel> deviceDataModelList = new ArrayList<>();
        deviceDataModelList.add(deviceDataModelDouble);

        try (MockedConstruction<RoomID> roomIdConstruction = mockConstruction(RoomID.class, (mock, context) -> {
            when(deviceDataModelDouble.getRoomID()).thenReturn(roomName);

        })) {
            try (MockedConstruction<DeviceModel> deviceModelConstruction = mockConstruction(DeviceModel.class, (mock, context) -> {
                when(deviceDataModelDouble.getDeviceModel()).thenReturn(modelName);

            })) {
                try (MockedConstruction<DeviceStatus> deviceStatusConstruction = mockConstruction(DeviceStatus.class, (mock, context) -> {
                    when(deviceDataModelDouble.getDeviceStatus()).thenReturn(valueOf(status));

                })) {
                    try (MockedConstruction<DeviceID> deviceIDConstruction = mockConstruction(DeviceID.class, (mock, context) -> {
                        when(deviceDataModelDouble.getDeviceID()).thenReturn(deviceName);

                        // Setting up mock behavior for the FactoryDevice
                        when(factoryDouble.createDevice(any(), any(), any(), any())).thenReturn(deviceDouble);

                    })) {

                        List<Device> expectedDeviceList = Collections.singletonList(deviceDouble);

                        Iterable<Device> actualDeviceList = mapper.toDomainList(factoryDouble, deviceDataModelList);

                        assertEquals(expectedDeviceList, actualDeviceList);
                    }
                }
            }
        }
    }

    /**
     * Test method to verify the conversion of a list of DeviceDataModel objects to a list of Device objects.
     * Fails when the List of Data Model is null.
     */
    @Test
    void failConvertDeviceListToDomainNullDataModelList() {
        Iterable<Device> actualDeviceList = mapper.toDomainList(factoryDouble, null);
        assertNull(actualDeviceList);
    }

    /**
     * Test method to verify the conversion of a list of DeviceDataModel objects to a list of Device objects.
     * Fails when the Factory is null.
     */
    @Test
    void failConvertDeviceListToDomainNullFactory() {
        // Creating a list of DeviceDataModel objects
        List<DeviceDataModel> deviceDataModelList = new ArrayList<>();
        deviceDataModelList.add(deviceDataModelDouble);

        try (MockedConstruction<RoomID> roomIdConstruction = mockConstruction(RoomID.class, (mock, context) -> {
            when(deviceDataModelDouble.getRoomID()).thenReturn(roomName);

        })) {
            try (MockedConstruction<DeviceModel> deviceModelConstruction = mockConstruction(DeviceModel.class, (mock, context) -> {
                when(deviceDataModelDouble.getDeviceModel()).thenReturn(modelName);

            })) {
                try (MockedConstruction<DeviceStatus> deviceStatusConstruction = mockConstruction(DeviceStatus.class, (mock, context) -> {
                    when(deviceDataModelDouble.getDeviceStatus()).thenReturn(valueOf(status));

                })) {
                    try (MockedConstruction<DeviceID> deviceIDConstruction = mockConstruction(DeviceID.class, (mock, context) -> {
                        when(deviceDataModelDouble.getDeviceID()).thenReturn(deviceName);

                    })) {

                        List<Device> expectedDeviceList = Collections.singletonList(deviceDouble);

                        Iterable<Device> result = mapper.toDomainList(null, deviceDataModelList);

                        assertNull(result);
                    }
                }
            }
        }
    }
}