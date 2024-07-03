package smarthome.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.house.House;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Location;
import smarthome.domain.gateway.WeatherAPIGateway;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.service.internaldto.InstTemperatureDTO;
import smarthome.persistence.repositoriesmem.InstantTimeValueRepository;
import smarthome.service.internaldto.SunriseSunsetDTO;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link WeatherAPIService} class.
 */
@SpringBootTest(classes = {WeatherAPIService.class})
public class WeatherAPIServiceTest {
    /**
     * Mocked gateway for interacting with the Weather API.
     */
    @MockBean
    private WeatherAPIGateway weatherAPIGateway;

    /**
     * Mocked repository for managing house data.
     */
    @MockBean
    private HouseRepository houseRepository;

    /**
     * Mocked repository for managing value data.
     */
    @MockBean
    private InstantTimeValueRepository valueRepository;

    /**
     * Service under test, which uses the mocked dependencies.
     */
    @InjectMocks
    private WeatherAPIService weatherAPIService;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the getTemperatureForHour method with a valid hour.
     */
    @Test
    public void validGetTemperatureForHour() {
        int hour = 2;
        int groupNumber = 5;
        double latitude = 50.7958;
        double longitude = -4.2596;
        House house = mock(House.class);
        Location location = mock(Location.class);
        GPSCode gpsCode = mock(GPSCode.class);
        when(houseRepository.findAllEntities()).thenReturn(Collections.singletonList(house));
        when(house.getHouseLocation()).thenReturn(location);
        when(location.getGpsCode()).thenReturn(gpsCode);
        when(gpsCode.getLatitude()).thenReturn(latitude);
        when(gpsCode.getLongitude()).thenReturn(longitude);
        InstTemperatureDTO responsePayload = new InstTemperatureDTO(4.0, "Celsius", "Living room temperature");

        when(weatherAPIGateway.getInstantaneousTemperature(groupNumber, latitude, longitude, hour)).thenReturn(responsePayload);

        double temperature = weatherAPIService.getTemperatureForHour(hour);

        assertEquals(4.0, temperature, 0.01);
    }

    /**
     * Tests the getTemperatureForHour method with a valid hour lower limit.
     */
    @Test
    public void validGetTemperatureForHourLowerLimit() {
        int hour = 0;
        int groupNumber = 5;
        double latitude = 50.7958;
        double longitude = -4.2596;
        House house = mock(House.class);
        Location location = mock(Location.class);
        GPSCode gpsCode = mock(GPSCode.class);
        when(houseRepository.findAllEntities()).thenReturn(Collections.singletonList(house));
        when(house.getHouseLocation()).thenReturn(location);
        when(location.getGpsCode()).thenReturn(gpsCode);
        when(gpsCode.getLatitude()).thenReturn(latitude);
        when(gpsCode.getLongitude()).thenReturn(longitude);
        InstTemperatureDTO responsePayload = new InstTemperatureDTO(5.0, "Celsius", "Living room temperature");

        when(weatherAPIGateway.getInstantaneousTemperature(groupNumber, latitude, longitude, hour)).thenReturn(responsePayload);

        double temperature = weatherAPIService.getTemperatureForHour(hour);

        assertEquals(5.0, temperature, 0.01);
    }

    /**
     * Tests the getTemperatureForHour method with a valid hour lower limit.
     */
    @Test
    public void validGetTemperatureForHourUpperLimit() {
        int hour = 23;
        int groupNumber = 5;
        double latitude = 50.7958;
        double longitude = -4.2596;
        House house = mock(House.class);
        Location location = mock(Location.class);
        GPSCode gpsCode = mock(GPSCode.class);
        when(houseRepository.findAllEntities()).thenReturn(Collections.singletonList(house));
        when(house.getHouseLocation()).thenReturn(location);
        when(location.getGpsCode()).thenReturn(gpsCode);
        when(gpsCode.getLatitude()).thenReturn(latitude);
        when(gpsCode.getLongitude()).thenReturn(longitude);
        InstTemperatureDTO responsePayload = new InstTemperatureDTO(4.0, "Celsius", "Living room temperature");

        when(weatherAPIGateway.getInstantaneousTemperature(groupNumber, latitude, longitude, hour)).thenReturn(responsePayload);

        double temperature = weatherAPIService.getTemperatureForHour(hour);

        assertEquals(4.0, temperature, 0.01);
    }

    /**
     * Tests the getTemperatureForHour method with an invalid hour after upper limit.
     */
    @Test
    public void invalidGetTemperatureForHourUpper() {
        int invalidHour = 24;

        assertThrows(IllegalArgumentException.class, () -> weatherAPIService.getTemperatureForHour(invalidHour));
    }

    /**
     * Tests the getTemperatureForHour method with an invalid hour under the limit.
     */
    @Test
    public void invalidGetTemperatureForHourUnder() {
        int invalidHour = -1;

        assertThrows(IllegalArgumentException.class, () -> weatherAPIService.getTemperatureForHour(invalidHour));
    }

    /**
     * Tests the getMaxTemperatureDifferenceWithWeatherService method with valid inputs and successful result.
     */
    @Test
    public void getMaxTemperatureDifferenceWithWeatherService() {
        double latitude = 50.7958;
        double longitude = -4.2596;
        House house = mock(House.class);
        Location location = mock(Location.class);
        GPSCode gpsCode = mock(GPSCode.class);
        when(houseRepository.findAllEntities()).thenReturn(Collections.singletonList(house));
        when(house.getHouseLocation()).thenReturn(location);
        when(location.getGpsCode()).thenReturn(gpsCode);
        when(gpsCode.getLatitude()).thenReturn(latitude);
        when(gpsCode.getLongitude()).thenReturn(longitude);
        SensorID insideSensorID = mock(SensorID.class);
        Timestamp startTime = Timestamp.valueOf("2023-06-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-06-01 23:59:59");

        InstantTimeValue value1 = mock(InstantTimeValue.class);
        InstantTimeValue value2 = mock(InstantTimeValue.class);
        Reading reading1 = mock(Reading.class);
        Reading reading2 = mock(Reading.class);
        InstTemperatureDTO instTemperatureDTO1 = mock(InstTemperatureDTO.class);
        instTemperatureDTO1.measurement = 11.0;
        InstTemperatureDTO instTemperatureDTO2 = mock(InstTemperatureDTO.class);
        instTemperatureDTO2.measurement = 13.0;

        when(value1.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2023-06-01 10:00:00"));
        when(value1.getReading()).thenReturn(reading1);
        when(reading1.getMeasurement()).thenReturn("25.0");
        when(value2.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2023-06-01 14:00:00"));
        when(value2.getReading()).thenReturn(reading2);
        when(reading2.getMeasurement()).thenReturn("20.0");
        List<Value> insideValues = Arrays.asList(value1, value2);

        when(valueRepository.findBySensorId(insideSensorID)).thenReturn(insideValues);
        when(weatherAPIGateway.getInstantaneousTemperature(5, latitude, longitude, 10)).thenReturn(instTemperatureDTO1);
        when(weatherAPIGateway.getInstantaneousTemperature(5, latitude, longitude, 14)).thenReturn(instTemperatureDTO2);

        double maxDifference = weatherAPIService.getMaxTemperatureDifferenceWithWeatherService(insideSensorID, startTime, endTime);

        assertEquals(14.0, maxDifference, 0.01);
    }

    /**
     * Tests the getMaxTemperatureDifferenceWithWeatherService method when no values are in the specified time range.
     */
    @Test
    public void failToGetMaxTemperatureDifferenceWithWeatherServiceNoValuesInRangeUnder() {
        SensorID insideSensorID = mock(SensorID.class);
        Timestamp startTime = Timestamp.valueOf("2023-06-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-06-01 23:59:59");

        InstantTimeValue value1 = mock(InstantTimeValue.class);
        Reading reading1 = mock(Reading.class);

        when(value1.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2023-05-25 10:20:00"));
        when(value1.getReading()).thenReturn(reading1);
        when(reading1.getMeasurement()).thenReturn("25.0");

        List<Value> insideValues = Collections.singletonList(value1);

        when(valueRepository.findBySensorId(insideSensorID)).thenReturn(insideValues);

        double maxDifference = weatherAPIService.getMaxTemperatureDifferenceWithWeatherService(insideSensorID, startTime, endTime);

        assertEquals(-1, maxDifference, 0.01);
    }

    /**
     * Tests the getMaxTemperatureDifferenceWithWeatherService method when no values are in the specified time range.
     */
    @Test
    public void failToGetMaxTemperatureDifferenceWithWeatherServiceNoValuesInRangeOver() {
        SensorID insideSensorID = mock(SensorID.class);
        Timestamp startTime = Timestamp.valueOf("2023-06-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-06-01 23:59:59");

        InstantTimeValue value1 = mock(InstantTimeValue.class);
        Reading reading1 = mock(Reading.class);

        when(value1.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2023-07-25 10:20:00"));
        when(value1.getReading()).thenReturn(reading1);
        when(reading1.getMeasurement()).thenReturn("25.0");

        List<Value> insideValues = Collections.singletonList(value1);

        when(valueRepository.findBySensorId(insideSensorID)).thenReturn(insideValues);

        double maxDifference = weatherAPIService.getMaxTemperatureDifferenceWithWeatherService(insideSensorID, startTime, endTime);

        assertEquals(-1, maxDifference, 0.01);
    }

    /**
     * Tests the getMaxTemperatureDifferenceWithWeatherService method when the tolerance is not met.
     */
    @Test
    public void failToGetMaxTemperatureDifferenceWithWeatherServiceToleranceNotMet() {
        double latitude = 50.7958;
        double longitude = -4.2596;
        House house = mock(House.class);
        Location location = mock(Location.class);
        GPSCode gpsCode = mock(GPSCode.class);
        when(houseRepository.findAllEntities()).thenReturn(Collections.singletonList(house));
        when(house.getHouseLocation()).thenReturn(location);
        when(location.getGpsCode()).thenReturn(gpsCode);
        when(gpsCode.getLatitude()).thenReturn(latitude);
        when(gpsCode.getLongitude()).thenReturn(longitude);
        SensorID insideSensorID = mock(SensorID.class);
        Timestamp startTime = Timestamp.valueOf("2023-06-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-06-01 23:59:59");

        InstantTimeValue value1 = mock(InstantTimeValue.class);
        Reading reading1 = mock(Reading.class);
        InstTemperatureDTO instTemperatureDTO = mock(InstTemperatureDTO.class);
        instTemperatureDTO.measurement = 11.0;

        when(value1.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2023-06-01 10:20:00"));
        when(value1.getReading()).thenReturn(reading1);
        when(reading1.getMeasurement()).thenReturn("25.0");

        List<Value> insideValues = Collections.singletonList(value1);

        when(valueRepository.findBySensorId(insideSensorID)).thenReturn(insideValues);
        when(weatherAPIGateway.getInstantaneousTemperature(5, latitude, longitude, 10)).thenReturn(instTemperatureDTO);


        double maxDifference = weatherAPIService.getMaxTemperatureDifferenceWithWeatherService(insideSensorID, startTime, endTime);

        assertEquals(-1, maxDifference, 0.01);
    }

    /**
     * Tests the getMaxTemperatureDifferenceWithWeatherService method with multiple values.
     * First isn't in tolerance range. The third difference is higher than first.
     */
    @Test
    public void getMaxTemperatureDifferenceWithWeatherServiceWithMultipleValues() {
        double latitude = 50.7958;
        double longitude = -4.2596;
        House house = mock(House.class);
        Location location = mock(Location.class);
        GPSCode gpsCode = mock(GPSCode.class);
        when(houseRepository.findAllEntities()).thenReturn(Collections.singletonList(house));
        when(house.getHouseLocation()).thenReturn(location);
        when(location.getGpsCode()).thenReturn(gpsCode);
        when(gpsCode.getLatitude()).thenReturn(latitude);
        when(gpsCode.getLongitude()).thenReturn(longitude);
        SensorID insideSensorID = mock(SensorID.class);
        Timestamp startTime = Timestamp.valueOf("2023-06-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-06-01 23:59:59");

        InstantTimeValue value1 = mock(InstantTimeValue.class);
        InstantTimeValue value2 = mock(InstantTimeValue.class);
        InstantTimeValue value3 = mock(InstantTimeValue.class);
        Reading reading1 = mock(Reading.class);
        Reading reading2 = mock(Reading.class);
        Reading reading3 = mock(Reading.class);
        InstTemperatureDTO instTemperatureDTO1 = mock(InstTemperatureDTO.class);
        instTemperatureDTO1.measurement = 5.0;
        InstTemperatureDTO instTemperatureDTO2 = mock(InstTemperatureDTO.class);
        instTemperatureDTO2.measurement = 14.0;

        when(value1.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2023-06-01 00:00:00"));
        when(value1.getReading()).thenReturn(reading1);
        when(reading1.getMeasurement()).thenReturn("25.0");
        when(value2.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2023-06-01 14:45:00"));
        when(value2.getReading()).thenReturn(reading2);
        when(reading2.getMeasurement()).thenReturn("18.0");
        when(value3.getInstantTimeReading()).thenReturn(Timestamp.valueOf("2023-06-01 23:59:59"));
        when(value3.getReading()).thenReturn(reading3);
        when(reading3.getMeasurement()).thenReturn("25.0");

        List<Value> insideValues = Arrays.asList(value1, value2, value3);

        when(valueRepository.findBySensorId(insideSensorID)).thenReturn(insideValues);
        when(weatherAPIGateway.getInstantaneousTemperature(5, latitude, longitude, 0)).thenReturn(instTemperatureDTO1);
        when(weatherAPIGateway.getInstantaneousTemperature(5, latitude, longitude, 15)).thenReturn(instTemperatureDTO2);

        double maxDifference = weatherAPIService.getMaxTemperatureDifferenceWithWeatherService(insideSensorID, startTime, endTime);

        assertEquals(20.0, maxDifference, 0.01);
    }

    /**
     * Tests the {@link WeatherAPIService#getSunriseSunsetHour(String)} method
     * for successful retrieval of sunrise hour.
     */
    @Test
    public void testGetSunriseHour() {
        int groupNumber = 5;
        double latitude = 50.7958;
        double longitude = -4.2596;
        House house = mock(House.class);
        Location location = mock(Location.class);
        GPSCode gpsCode = mock(GPSCode.class);
        when(houseRepository.findAllEntities()).thenReturn(Collections.singletonList(house));
        when(house.getHouseLocation()).thenReturn(location);
        when(location.getGpsCode()).thenReturn(gpsCode);
        when(gpsCode.getLatitude()).thenReturn(latitude);
        when(gpsCode.getLongitude()).thenReturn(longitude);
        String option = "sunrise";
        double expectedMeasurement = 7.02;
        SunriseSunsetDTO responsePayload = new SunriseSunsetDTO(expectedMeasurement, "h", "sunrise");

        when(weatherAPIGateway.getSunriseSunsetHour(groupNumber, 50.7958, -4.2596, option))
                .thenReturn(responsePayload);

        double response = weatherAPIService.getSunriseSunsetHour(option);

        assertEquals(expectedMeasurement, response);
    }

    /**
     * Tests the {@link WeatherAPIService#getSunriseSunsetHour(String)} method
     * for successful retrieval of sunset hour.
     */
    @Test
    public void testGetSunsetHour() {
        int groupNumber = 5;
        double latitude = 50.7958;
        double longitude = -4.2596;
        House house = mock(House.class);
        Location location = mock(Location.class);
        GPSCode gpsCode = mock(GPSCode.class);
        when(houseRepository.findAllEntities()).thenReturn(Collections.singletonList(house));
        when(house.getHouseLocation()).thenReturn(location);
        when(location.getGpsCode()).thenReturn(gpsCode);
        when(gpsCode.getLatitude()).thenReturn(latitude);
        when(gpsCode.getLongitude()).thenReturn(longitude);
        String option = "sunset";
        double expectedMeasurement = 18.48;
        SunriseSunsetDTO responsePayload = new SunriseSunsetDTO(expectedMeasurement, "h", "sunset");

        when(weatherAPIGateway.getSunriseSunsetHour(groupNumber, 50.7958, -4.2596, option))
                .thenReturn(responsePayload);

        double response = weatherAPIService.getSunriseSunsetHour(option);

        assertEquals(expectedMeasurement, response);
    }
}
