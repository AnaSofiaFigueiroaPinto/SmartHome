package smarthome.mapper;

import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.SensorID;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorMapperDTOTest {

    @Test
    void sensorToDTOConversion() {
        //Creation of doubles and setting up their behavior
        SensorID doubleSensorID1 = mock(SensorID.class);
        when(doubleSensorID1.toString()).thenReturn("TemperatureSensor");

        SensorID doubleSensorID2 = mock(SensorID.class);
        when(doubleSensorID2.toString()).thenReturn("HumiditySensor");


        //Conversion of Device object to DeviceDTO object
        SensorMapperDTO sensorMapperDTO = new SensorMapperDTO();
        SensorDTO sensorDTO1 = sensorMapperDTO.sensorToDTO(doubleSensorID1);
        SensorDTO sensorDTO2 = sensorMapperDTO.sensorToDTO(doubleSensorID2);

        //Assert the conversion
        assertNotNull(sensorDTO1);
        assertNotNull(sensorDTO2);
        assertEquals(doubleSensorID1.toString(), sensorDTO1.sensorName);
    }

    @Test
    void sensorToDTONullSensor() {
        //Creation of a null SensorID object
        SensorID nullSensorID = null;

        //Conversion of null SensorID object to SensorDTO object
        SensorMapperDTO sensorMapperDTO = new SensorMapperDTO();
        SensorDTO sensorDTO = sensorMapperDTO.sensorToDTO(nullSensorID);

        //Assert the conversion
        assertNull(sensorDTO);
    }

    @Test
    void sensorToDTOListConversion() {
        //Creation of doubles and setting up their behavior
        SensorID doubleSensorID1 = mock(SensorID.class);
        when(doubleSensorID1.toString()).thenReturn("TemperatureSensor");

        SensorID doubleSensorID2 = mock(SensorID.class);
        when(doubleSensorID2.toString()).thenReturn("HumiditySensor");

        //Creation of a list of SensorID objects
        List<SensorID> sensorIDs = new ArrayList<>();
        sensorIDs.add(doubleSensorID1);
        sensorIDs.add(doubleSensorID2);

        //Conversion of list of SensorID objects to list of SensorDTO objects
        SensorMapperDTO sensorMapperDTO = new SensorMapperDTO();
        List<SensorDTO> sensorDTOs = sensorMapperDTO.sensorIDsToDTOList(sensorIDs);

        //Assert the conversion
        assertNotNull(sensorDTOs);
        assertEquals(doubleSensorID1.toString(), sensorDTOs.get(0).sensorName);
    }



}