package smarthome.persistence.jpa.datamodel;

import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MapperSensorDataModelTest {
    private SensorDataModel sensorDataModelDouble;
    private FactorySensor factorySensorDouble;
    private MapperSensorDataModel mapperSensorDataModel;
    private Sensor sensorDouble;

    @BeforeEach
    void setUp() {
        sensorDataModelDouble = mock(SensorDataModel.class);
        factorySensorDouble = mock(FactorySensor.class);
        sensorDouble = mock(Sensor.class);

        mapperSensorDataModel = new MapperSensorDataModel();

        //Set behaviour for the SensorDataModel
        when(sensorDataModelDouble.getSensorID()).thenReturn("sensorOfTemperature");
        when(sensorDataModelDouble.getDeviceID()).thenReturn("thermometer");
        when(sensorDataModelDouble.getSensorFunctionalityID()).thenReturn("TemperatureCelsius");
    }

    @Test
    void successConversionToDomain() {
        // Mock DeviceID construction
        try (MockedConstruction<DeviceID> deviceIDMockConstruction =
                     mockConstruction(DeviceID.class, (mock, context) -> {
                     })) {
            // Mock FactorySensor behaviour
            when(factorySensorDouble.createSensor(any(), any(), any(), any())).thenReturn(sensorDouble);
            //Act
            Sensor result = mapperSensorDataModel.toDomain(factorySensorDouble, sensorDataModelDouble);
            //Assert
            assertNotNull(result);
            assertEquals(result, sensorDouble);
        }
    }

    @Test
    void failConversionToDomainNullFactory() {
        // Mock DeviceID construction
        try (MockedConstruction<DeviceID> deviceIDMockConstruction =
                     mockConstruction(DeviceID.class, (mock, context) -> {
                     })) {
            // Mock FactorySensor behaviour
            when(factorySensorDouble.createSensor(any(), any(), any(), any())).thenReturn(sensorDouble);
            //Act
            Sensor result = mapperSensorDataModel.toDomain(null, sensorDataModelDouble);
            //Assert
            assertNull(result);
        }
    }

    @Test
    void successConversionToDomainList() {
        List<SensorDataModel> sensorDataModels = List.of(sensorDataModelDouble);
        // Mock DeviceID construction
        try (MockedConstruction<DeviceID> deviceIDMockConstruction =
                     mockConstruction(DeviceID.class, (mock, context) -> {
                     })) {
            // Mock FactorySensor behaviour
            when(factorySensorDouble.createSensor(any(), any(), any(), any())).thenReturn(sensorDouble);
            //Act
            Iterable<Sensor> result = mapperSensorDataModel.toDomainList(factorySensorDouble, sensorDataModels);
            //Assert
            assertEquals(1, ((List<Sensor>) result).size());
            assertEquals(sensorDouble, ((List<Sensor>) result).get(0));
        }
    }

    @Test
    void successConversionToDomainListWithNullDevice() {
        SensorDataModel nullSensor = null;
        List<SensorDataModel> sensorDataModels = new ArrayList<>();
        sensorDataModels.add(nullSensor);
        sensorDataModels.add(nullSensor);

        // Mock DeviceID construction
        try (MockedConstruction<DeviceID> deviceIDMockConstruction =
                     mockConstruction(DeviceID.class, (mock, context) -> {
                     })) {
            // Mock FactorySensor behaviour
            when(factorySensorDouble.createSensor(any(), any(), any(), any())).thenReturn(sensorDouble);
            //Act
            Iterable<Sensor> result = mapperSensorDataModel.toDomainList(factorySensorDouble, sensorDataModels);
            //Assert
            assertTrue(((List<Sensor>) result).isEmpty());
        }
    }

    @Test
    void failConversionToDomainListNullListOfDataModels() {
        List<SensorDataModel> sensorDataModels = null; //provided iterable of data models is null
        // Mock DeviceID construction
        try (MockedConstruction<DeviceID> deviceIDMockConstruction =
                     mockConstruction(DeviceID.class, (mock, context) -> {
                     })) {
            // Mock FactorySensor behaviour
            when(factorySensorDouble.createSensor(any(), any(), any(), any())).thenReturn(sensorDouble);
            //Act
            Iterable<Sensor> result = mapperSensorDataModel.toDomainList(factorySensorDouble, sensorDataModels);
            //Assert
            assertNull(result);
        }
    }

    @Test
    void failConversionToDomainListNullFactory() {
        List<SensorDataModel> sensorDataModels = List.of(sensorDataModelDouble);
        // Mock DeviceID construction
        try (MockedConstruction<DeviceID> deviceIDMockConstruction =
                     mockConstruction(DeviceID.class, (mock, context) -> {
                     })) {
            // Mock FactorySensor behaviour
            when(factorySensorDouble.createSensor(any(), any(), any(), any())).thenReturn(sensorDouble);
            //Act
            Iterable<Sensor> result = mapperSensorDataModel.toDomainList(null, sensorDataModels);
            //Assert
            assertNull(result);
        }
    }
}