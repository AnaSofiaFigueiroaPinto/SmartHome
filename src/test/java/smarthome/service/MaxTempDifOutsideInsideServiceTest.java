package smarthome.service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.persistence.repositoriesmem.InstantTimeValueRepositoryMem;
import smarthome.util.ConfigScraper;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class MaxTempDifOutsideInsideServiceTest {

    /**
     * Test case for successfully retrieving the maximum temperature difference between inside and outside sensors.
     */
    @Test
    void testSuccessfullyGetMaxTemperatureDifference() {
        // Create mocks
        FactorySensor factorySensor = mock(FactorySensor.class);
        InstantTimeValueRepositoryMem valueRepositoryMem = mock(InstantTimeValueRepositoryMem.class);

        try (MockedConstruction<ConfigScraper> ConfigScraper = mockConstruction(ConfigScraper.class,
                (mock, context) -> {
                    when(mock.loadTolerance()).thenReturn("300000");
                })) {

            // Create the service with mocks
            MaxTempDifOutsideInsideService service = new MaxTempDifOutsideInsideService(valueRepositoryMem);

            // Prepare test data
            SensorID insideSensorID = mock(SensorID.class);
            Timestamp startTime = Timestamp.valueOf("2024-04-15 08:00:00.0");
            Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

            // Mocking value retrieval
            InstantTimeValue insideValue1 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(insideSensorID)).thenReturn(List.of(insideValue1));

            // Mocking inside value properties
            Timestamp insideValueTimestamp = Timestamp.valueOf("2024-04-15 14:00:00.0");
            when(insideValue1.getInstantTimeReading()).thenReturn(insideValueTimestamp);
            Reading reading1 = mock(Reading.class);
            when(reading1.getMeasurement()).thenReturn("18");
            when(reading1.getUnit()).thenReturn("Cº");
            when(insideValue1.getReading()).thenReturn(reading1);

            // Mocking outside value properties
            SensorID outsideSensorID = mock(SensorID.class);
            InstantTimeValue insideValue2 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(outsideSensorID)).thenReturn(List.of(insideValue2));
            Timestamp outsideValueTimestamp = Timestamp.valueOf("2024-04-15 14:05:00.0");
            when(insideValue2.getInstantTimeReading()).thenReturn(outsideValueTimestamp);
            Reading reading2 = mock(Reading.class);
            when(reading2.getMeasurement()).thenReturn("24");
            when(reading2.getUnit()).thenReturn("Cº");
            when(insideValue2.getReading()).thenReturn(reading2);

            // Invoke the method under test
            double maxTemperatureDifference = service.getMaxTemperatureDifference(insideSensorID, outsideSensorID, startTime, endTime);

            // Assert the result
            assertEquals(6.0, maxTemperatureDifference, 0.01);

            // Verify method calls
            verify(valueRepositoryMem).findBySensorId(insideSensorID);
        }
    }

    /**
     * Test case for successfully retrieving the maximum temperature difference between inside and outside sensors
     * with values in the limit of boundaries set by the period.
     */
    @Test
    void testSuccessfullyGetMaxTemperatureDifferenceBoundaries() {
        // Create mocks
        FactorySensor factorySensor = mock(FactorySensor.class);
        InstantTimeValueRepositoryMem valueRepositoryMem = mock(InstantTimeValueRepositoryMem.class);

        try (MockedConstruction<ConfigScraper> ConfigScraper = mockConstruction(ConfigScraper.class,
                (mock, context) -> {
                    when(mock.loadTolerance()).thenReturn("300000");
                })) {

            // Create the service with mocks
            MaxTempDifOutsideInsideService service = new MaxTempDifOutsideInsideService(valueRepositoryMem);

            // Prepare test data
            SensorID insideSensorID = mock(SensorID.class);
            Timestamp startTime = Timestamp.valueOf("2024-04-15 08:00:00.0");
            Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

            // Mocking value retrieval
            InstantTimeValue insideValue1 = mock(InstantTimeValue.class);
            InstantTimeValue insideValue2 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(insideSensorID)).thenReturn(List.of(insideValue1, insideValue2));

            // Mocking inside value properties
            Timestamp insideValueTimestamp = Timestamp.valueOf("2024-04-15 08:00:00.0");
            Timestamp insideValueTimestamp2 = Timestamp.valueOf("2024-04-15 21:00:00.0");
            when(insideValue1.getInstantTimeReading()).thenReturn(insideValueTimestamp);
            when(insideValue2.getInstantTimeReading()).thenReturn(insideValueTimestamp2);
            Reading reading1 = mock(Reading.class);
            when(reading1.getMeasurement()).thenReturn("18");
            when(reading1.getUnit()).thenReturn("Cº");
            when(insideValue1.getReading()).thenReturn(reading1);
            Reading reading2 = mock(Reading.class);
            when(reading2.getMeasurement()).thenReturn("24");
            when(reading2.getUnit()).thenReturn("Cº");
            when(insideValue2.getReading()).thenReturn(reading2);

            // Mocking outside value properties
            SensorID outsideSensorID = mock(SensorID.class);
            InstantTimeValue insideValue3 = mock(InstantTimeValue.class);
            InstantTimeValue insideValue4 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(outsideSensorID)).thenReturn(List.of(insideValue3, insideValue4));
            Timestamp outsideValueTimestamp = Timestamp.valueOf("2024-04-15 08:05:00.0");
            Timestamp outsideValueTimestamp2 = Timestamp.valueOf("2024-04-15 19:55:00.0");
            when(insideValue3.getInstantTimeReading()).thenReturn(outsideValueTimestamp);
            when(insideValue4.getInstantTimeReading()).thenReturn(outsideValueTimestamp2);
            Reading reading3 = mock(Reading.class);
            when(reading3.getMeasurement()).thenReturn("24");
            when(reading3.getUnit()).thenReturn("Cº");
            when(insideValue3.getReading()).thenReturn(reading3);
            Reading reading4 = mock(Reading.class);
            when(reading4.getMeasurement()).thenReturn("18");
            when(reading4.getUnit()).thenReturn("Cº");
            when(insideValue4.getReading()).thenReturn(reading4);

            // Invoke the method under test
            double maxTemperatureDifference = service.getMaxTemperatureDifference(insideSensorID, outsideSensorID, startTime, endTime);

            // Assert the result
            assertEquals(6.0, maxTemperatureDifference, 0.01);

            // Verify method calls
            verify(valueRepositoryMem).findBySensorId(insideSensorID);
        }
    }

    /**
     * Test case for failing to retrieve the maximum temperature difference when the readings are over range.
     */
    @Test
    void testFailGetMaxTemperatureDifferenceReadingsOverRange() {
        // Create mocks
        FactorySensor factorySensor = mock(FactorySensor.class);
        InstantTimeValueRepositoryMem valueRepositoryMem = mock(InstantTimeValueRepositoryMem.class);

        try (MockedConstruction<ConfigScraper> ConfigScraper = mockConstruction(ConfigScraper.class,
                (mock, context) -> {
                    when(mock.loadTolerance()).thenReturn("300000");
                })) {

            // Create the service with mocks
            MaxTempDifOutsideInsideService service = new MaxTempDifOutsideInsideService(valueRepositoryMem);

            // Prepare test data
            SensorID insideSensorID = mock(SensorID.class);
            Timestamp startTime = Timestamp.valueOf("2024-04-15 08:00:00.0");
            Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

            // Mocking value retrieval
            InstantTimeValue insideValue1 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(insideSensorID)).thenReturn(List.of(insideValue1));

            // Mocking inside value properties
            Timestamp insideValueTimestamp = Timestamp.valueOf("2024-04-15 22:00:00.0");
            when(insideValue1.getInstantTimeReading()).thenReturn(insideValueTimestamp);
            Reading reading1 = mock(Reading.class);
            when(reading1.getMeasurement()).thenReturn("25");
            when(reading1.getUnit()).thenReturn("Cº");
            when(insideValue1.getReading()).thenReturn(reading1);

            // Mocking outside value properties
            SensorID outsideSensorID = mock(SensorID.class);
            InstantTimeValue insideValue2 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(outsideSensorID)).thenReturn(List.of(insideValue2));
            Timestamp outsideValueTimestamp = Timestamp.valueOf("2024-04-15 12:05:00.0");
            when(insideValue2.getInstantTimeReading()).thenReturn(outsideValueTimestamp);
            Reading reading2 = mock(Reading.class);
            when(reading2.getMeasurement()).thenReturn("24");
            when(reading2.getUnit()).thenReturn("Cº");
            when(insideValue2.getReading()).thenReturn(reading2);

            // Invoke the method under test
            double maxTemperatureDifference = service.getMaxTemperatureDifference(insideSensorID, outsideSensorID, startTime, endTime);

            // Assert the result
            assertEquals(-1.0, maxTemperatureDifference, 0.01);

            // Verify method calls
            verify(valueRepositoryMem).findBySensorId(insideSensorID);
        }
    }

    /**
     * Test case for failing to retrieve the maximum temperature difference when the readings are before the range.
     */
    @Test
    void testFailGetMaxTemperatureDifferenceReadingsBeforeRange() {
        // Create mocks
        FactorySensor factorySensor = mock(FactorySensor.class);
        InstantTimeValueRepositoryMem valueRepositoryMem = mock(InstantTimeValueRepositoryMem.class);

        try (MockedConstruction<ConfigScraper> ConfigScraper = mockConstruction(ConfigScraper.class,
                (mock, context) -> {
                    when(mock.loadTolerance()).thenReturn("300000");
                })) {

            // Create the service with mocks
            MaxTempDifOutsideInsideService service = new MaxTempDifOutsideInsideService(valueRepositoryMem);

            // Prepare test data
            SensorID insideSensorID = mock(SensorID.class);
            Timestamp startTime = Timestamp.valueOf("2024-04-15 08:00:00.0");
            Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

            // Mocking value retrieval
            InstantTimeValue insideValue1 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(insideSensorID)).thenReturn(List.of(insideValue1));

            // Mocking inside value properties
            Timestamp insideValueTimestamp = Timestamp.valueOf("2024-04-15 07:00:00.0");
            when(insideValue1.getInstantTimeReading()).thenReturn(insideValueTimestamp);
            Reading reading1 = mock(Reading.class);
            when(reading1.getMeasurement()).thenReturn("25");
            when(reading1.getUnit()).thenReturn("Cº");
            when(insideValue1.getReading()).thenReturn(reading1);

            // Mocking outside value properties
            SensorID outsideSensorID = mock(SensorID.class);
            InstantTimeValue insideValue2 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(outsideSensorID)).thenReturn(List.of(insideValue2));
            Timestamp outsideValueTimestamp = Timestamp.valueOf("2024-04-15 10:05:00.0");
            when(insideValue2.getInstantTimeReading()).thenReturn(outsideValueTimestamp);
            Reading reading2 = mock(Reading.class);
            when(reading2.getMeasurement()).thenReturn("24");
            when(reading2.getUnit()).thenReturn("Cº");
            when(insideValue2.getReading()).thenReturn(reading2);

            // Invoke the method under test
            double maxTemperatureDifference = service.getMaxTemperatureDifference(insideSensorID, outsideSensorID, startTime, endTime);

            // Assert the result
            assertEquals(-1.0, maxTemperatureDifference, 0.01);

            // Verify method calls
            verify(valueRepositoryMem).findBySensorId(insideSensorID);
        }
    }

    /**
     * Test case for failing to retrieve the maximum temperature difference when there are no readings within the tolerance.
     */
    @Test
    void testFailGetMaxTemperatureDifferenceNoReadingsInTolerance() {
        // Create mocks
        FactorySensor factorySensor = mock(FactorySensor.class);
        InstantTimeValueRepositoryMem valueRepositoryMem = mock(InstantTimeValueRepositoryMem.class);

        try (MockedConstruction<ConfigScraper> ConfigScraper = mockConstruction(ConfigScraper.class,
                (mock, context) -> {
                    when(mock.loadTolerance()).thenReturn("300000");
                })) {

            // Create the service with mocks
            MaxTempDifOutsideInsideService service = new MaxTempDifOutsideInsideService(valueRepositoryMem);

            // Prepare test data
            SensorID insideSensorID = mock(SensorID.class);
            Timestamp startTime = Timestamp.valueOf("2024-04-15 08:00:00.0");
            Timestamp endTime = Timestamp.valueOf("2024-04-15 21:00:00.0");

            // Mocking value retrieval
            InstantTimeValue insideValue1 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(insideSensorID)).thenReturn(List.of(insideValue1));

            // Mocking inside value properties
            Timestamp insideValueTimestamp = Timestamp.valueOf("2024-04-15 17:06:00.0");
            when(insideValue1.getInstantTimeReading()).thenReturn(insideValueTimestamp);
            Reading reading1 = mock(Reading.class);
            when(reading1.getMeasurement()).thenReturn("27");
            when(reading1.getUnit()).thenReturn("Cº");
            when(insideValue1.getReading()).thenReturn(reading1);

            // Mocking outside value properties
            SensorID outsideSensorID = mock(SensorID.class);
            InstantTimeValue insideValue2 = mock(InstantTimeValue.class);
            when(valueRepositoryMem.findBySensorId(outsideSensorID)).thenReturn(List.of(insideValue2));
            Timestamp outsideValueTimestamp = Timestamp.valueOf("2024-04-15 14:05:00.0");
            when(insideValue2.getInstantTimeReading()).thenReturn(outsideValueTimestamp);
            Reading reading2 = mock(Reading.class);
            when(reading2.getMeasurement()).thenReturn("24");
            when(reading2.getUnit()).thenReturn("Cº");
            when(insideValue2.getReading()).thenReturn(reading2);

            // Invoke the method under test
            double maxTemperatureDifference = service.getMaxTemperatureDifference(insideSensorID, outsideSensorID, startTime, endTime);

            // Assert the result
            assertEquals(-1.0, maxTemperatureDifference, 0.01);

            // Verify method calls
            verify(valueRepositoryMem).findBySensorId(insideSensorID);
        }
    }
}