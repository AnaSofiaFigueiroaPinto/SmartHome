package smarthome.mapper;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.DeviceStatus;
import smarthome.domain.valueobjects.RoomID;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.service.internaldto.InternalDeviceDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code DeviceMapperDTO} class.
 */
class DeviceMapperDTOTest {
    @Test
    void deviceIDToDTONullDeviceList() {
        DeviceID deviceID = null;
        //Conversion of Device object to DeviceDTO object
        DeviceMapperDTO deviceMapperDTO = new DeviceMapperDTO();
        DeviceDTO deviceDTO = deviceMapperDTO.deviceToDTO(deviceID);
        //Assert deviceDTO is null
        assertNull(deviceDTO);
    }

    /**
     * Test method for deviceToDTO(DeviceList deviceList).
     * Verifies if the deviceToDTO method converts a DeviceList object to a Map of DeviceDTO and Device objects correctly.
     */
    @Test
    void devicesConversionToDTOList() {
        //Creation of doubles and setting up their behavior
        RoomID doubleRoomID = mock(RoomID.class);
        when(doubleRoomID.toString()).thenReturn("Bedroom");

        DeviceID doubleDeviceID1 = mock(DeviceID.class);
        when(doubleDeviceID1.toString()).thenReturn("Heater1");
        Device doubleDevice1 = mock(Device.class);
        when(doubleDevice1.getDeviceName()).thenReturn("Heater1");

        DeviceID doubleDeviceID2 = mock(DeviceID.class);
        when(doubleDeviceID2.toString()).thenReturn("Heater2");
        Device doubleDevice2 = mock(Device.class);
        when(doubleDevice2.getDeviceName()).thenReturn("Heater2");

        //Creation of deviceModel and deviceStatus doubles, necessary for DTO instantiation
        DeviceModel doubleDeviceModel = mock(DeviceModel.class);
        DeviceStatus doubleDeviceStatus = mock(DeviceStatus.class);

        //Conversion of Device object to DeviceDTO object
        DeviceMapperDTO deviceMapperDTO = new DeviceMapperDTO();

        when(doubleDevice1.getDeviceName()).thenReturn("Heater1");
        when(doubleDevice2.getDeviceName()).thenReturn("Heater2");
        when(doubleDevice1.getDeviceModel()).thenReturn(doubleDeviceModel);
        when(doubleDevice2.getDeviceModel()).thenReturn(doubleDeviceModel);
        when(doubleDevice1.getDeviceStatus()).thenReturn(doubleDeviceStatus);
        when(doubleDevice2.getDeviceStatus()).thenReturn(doubleDeviceStatus);
        when(doubleDevice1.getRoomID()).thenReturn(doubleRoomID);
        when(doubleDevice2.getRoomID()).thenReturn(doubleRoomID);

        List<DeviceDTO> result = deviceMapperDTO.deviceIDsToDTOList(List.of(doubleDeviceID1, doubleDeviceID2));

        //Assert map contains the correct objects
        assertEquals(2, result.size());
        assertEquals("Heater1", result.get(0).deviceName);
        assertEquals("Heater2", result.get(1).deviceName);
    }

    /**
     * Test method for deviceToDTO(DeviceList deviceList) when the deviceList parameter is empty.
     * Verifies if the deviceToDTO method returns an empty Map when given an empty DeviceList.
     */
    @Test
    void deviceListToDTOEmptyList() {
        //Create an empty list of Device objects
        DeviceMapperDTO deviceMapperDTO = new DeviceMapperDTO();
        Iterable<DeviceID> devicesIDs = List.of();
        List<DeviceDTO> result = deviceMapperDTO.deviceIDsToDTOList(devicesIDs);

        //Assert the map is empty
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void successConversionInternalDeviceDTOToDTO() {
        //Creation of doubles and setting up their behavior
        RoomID doubleRoomID = mock(RoomID.class);
        when(doubleRoomID.toString()).thenReturn("Bedroom");

        DeviceID doubleDeviceID = mock(DeviceID.class);
        when(doubleDeviceID.toString()).thenReturn("Heater");

        //Creation of deviceModel and deviceStatus doubles, necessary for DTO instantiation
        DeviceModel doubleDeviceModel = mock(DeviceModel.class);
        DeviceStatus doubleDeviceStatus = mock(DeviceStatus.class);

        //Conversion of Device object to DeviceDTO object
        DeviceMapperDTO deviceMapperDTO = new DeviceMapperDTO();
        InternalDeviceDTO internalDeviceDTO = new InternalDeviceDTO(doubleDeviceID, doubleDeviceModel, doubleDeviceStatus, doubleRoomID);
        DeviceDTO deviceDTO = deviceMapperDTO.internalDeviceDTOToDTO(internalDeviceDTO);

        //Assert
        assertEquals(doubleDeviceID.toString(), deviceDTO.deviceName);
    }

    @Test
    void successConversionInternalDeviceDTOsToDTOList() {
        //Creation of doubles and setting up their behavior
        RoomID doubleRoomID = mock(RoomID.class);
        when(doubleRoomID.toString()).thenReturn("Bedroom");

        DeviceID doubleDeviceID1 = mock(DeviceID.class);
        when(doubleDeviceID1.toString()).thenReturn("Heater1");

        DeviceID doubleDeviceID2 = mock(DeviceID.class);
        when(doubleDeviceID2.toString()).thenReturn("Heater2");

        //Creation of deviceModel and deviceStatus doubles, necessary for DTO instantiation
        DeviceModel doubleDeviceModel = mock(DeviceModel.class);
        DeviceStatus doubleDeviceStatus = mock(DeviceStatus.class);

        //Conversion of Device object to DeviceDTO object
        DeviceMapperDTO deviceMapperDTO = new DeviceMapperDTO();
        InternalDeviceDTO internalDeviceDTO1 = new InternalDeviceDTO(doubleDeviceID1, doubleDeviceModel, doubleDeviceStatus, doubleRoomID);
        InternalDeviceDTO internalDeviceDTO2 = new InternalDeviceDTO(doubleDeviceID2, doubleDeviceModel, doubleDeviceStatus, doubleRoomID);
        List<InternalDeviceDTO> internalDeviceDTOList = List.of(internalDeviceDTO1, internalDeviceDTO2);
        List<DeviceDTO> deviceDTOList = deviceMapperDTO.internalDeviceDTOsToDTOList(internalDeviceDTOList);

        //Assert
        assertEquals(2, deviceDTOList.size());
        assertEquals(doubleDeviceID1.toString(), deviceDTOList.get(0).deviceName);
        assertEquals(doubleDeviceID2.toString(), deviceDTOList.get(1).deviceName);
    }
}
