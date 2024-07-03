package smarthome.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import smarthome.service.internaldto.InstTemperatureDTO;
import smarthome.service.internaldto.InstWindSpeedAndDirectionDTO;
import smarthome.service.internaldto.MaxWindSpeedAndDirectionOverAPeriodDTO;
import smarthome.service.internaldto.SunriseSunsetDTO;
import smarthome.util.exceptions.WeatherAPIException;

import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link WeatherAPIGatewayHttpImpl} class.
 */
public class WeatherAPIGatewayHttpImplTest {

    private static final String BASE_URL = "http://10.9.24.170:8080";

    private WeatherAPIGatewayHttpImpl weatherAPIGateway;

    private RestTemplate restTemplate;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        weatherAPIGateway = new WeatherAPIGatewayHttpImpl();
        weatherAPIGateway.setRestTemplate(restTemplate);
    }

    /**
     * Tests the {@link WeatherAPIGatewayHttpImpl#getInstantaneousTemperature(int, int)} method
     * for successful retrieval of instantaneous temperature.
     */
    @Test
    public void testGetInstantaneousTemperature() {
        InstTemperatureDTO responsePayload = new InstTemperatureDTO(23.5, "Celsius", "Living room temperature");
        String url = BASE_URL + "/InstantaneousTemperature?groupNumber=5&latitude=50.7958&longitude=-4.2596&hour=2";

        when(restTemplate.getForEntity(eq(url), eq(InstTemperatureDTO.class)))
                .thenReturn(new ResponseEntity<>(responsePayload, HttpStatus.OK));

        InstTemperatureDTO response = weatherAPIGateway.getInstantaneousTemperature(5, 50.7958, -4.2596, 2);

        assertEquals(responsePayload, response);
    }

    /**
     * Tests the {@link WeatherAPIGatewayHttpImpl#getInstantaneousTemperature(int, int)} method
     * for handling invalid arguments.
     */
    @Test
    public void testFailGetInstantaneousTemperature() {
        InstTemperatureDTO responsePayload = new InstTemperatureDTO(NaN, "", "Invalid arguments.");
        String url = BASE_URL + "/InstantaneousTemperature?groupNumber=5&latitude=50.7958&longitude=-4.2596&hour=2";

        when(restTemplate.getForEntity(eq(url), eq(InstTemperatureDTO.class)))
                .thenReturn(null);

        assertThrows(WeatherAPIException.class, () -> weatherAPIGateway.getInstantaneousTemperature(5, 50.7958, -4.2596, 25));
    }

    /**
     * Tests the {@link WeatherAPIGatewayHttpImpl#getInstantaneousWindSpeedAndDirection(int, int)} method
     * for successful retrieval of instantaneous wind speed and direction.
     */
    @Test
    public void testGetInstantaneousWindSpeedAndDirection() {
        InstWindSpeedAndDirectionDTO responsePayload = new InstWindSpeedAndDirectionDTO(15.2, "km/h", "North");
        String url = BASE_URL + "/InstantaneousWindSpeedAndDirection?groupNumber=5&latitude=50.7958&longitude=-4.2596&hour=2";

        when(restTemplate.getForEntity(eq(url), eq(InstWindSpeedAndDirectionDTO.class)))
                .thenReturn(new ResponseEntity<>(responsePayload, HttpStatus.OK));

        InstWindSpeedAndDirectionDTO response = weatherAPIGateway.getInstantaneousWindSpeedAndDirection(5, 50.7958, -4.2596, 2);

        assertEquals(responsePayload, response);
    }

    /**
     * Tests the {@link WeatherAPIGatewayHttpImpl#getInstantaneousWindSpeedAndDirection(int, int)} method
     * for invalid arguments.
     */
    @Test
    public void testFailGetInstantaneousWindSpeedAndDirection() {
        InstWindSpeedAndDirectionDTO responsePayload = new InstWindSpeedAndDirectionDTO(15.2, "km/h", "North");
        String url = BASE_URL + "/InstantaneousWindSpeedAndDirection?groupNumber=5&latitude=50.7958&longitude=-4.2596&hour=2";

        when(restTemplate.getForEntity(eq(url), eq(InstWindSpeedAndDirectionDTO.class)))
                .thenReturn(null);

        assertThrows(WeatherAPIException.class, () -> weatherAPIGateway.getInstantaneousWindSpeedAndDirection(5, 50.7958, -4.2596, 25));
    }

    /**
     * Tests the {@link WeatherAPIGatewayHttpImpl#getMaximumWindSpeedAndDirectionOverAPeriod(int, int, int)} method
     * for successful retrieval of maximum wind speed and direction over a period.
     */
    @Test
    public void testGetMaximumWindSpeedAndDirectionOverAPeriod() {
        MaxWindSpeedAndDirectionOverAPeriodDTO responsePayload = new MaxWindSpeedAndDirectionOverAPeriodDTO(20.0, "km/h", "North-East");
        String url = BASE_URL + "/MaximumWindSpeedAndDirectionOverAPeriod?groupNumber=5&latitude=50.7958&longitude=-4.2596&hourStart=1&hourEnd=12";

        when(restTemplate.getForEntity(eq(url), eq(MaxWindSpeedAndDirectionOverAPeriodDTO.class)))
                .thenReturn(new ResponseEntity<>(responsePayload, HttpStatus.OK));

        MaxWindSpeedAndDirectionOverAPeriodDTO response = weatherAPIGateway.getMaximumWindSpeedAndDirectionOverAPeriod(5, 50.7958, -4.2596, 1, 12);

        assertEquals(responsePayload, response);
    }

    /**
     * Tests the {@link WeatherAPIGatewayHttpImpl#getMaximumWindSpeedAndDirectionOverAPeriod(int, int, int)} method
     * for successful retrieval of maximum wind speed and direction over a period.
     */
    @Test
    public void testFailGetMaximumWindSpeedAndDirectionOverAPeriod() {
        MaxWindSpeedAndDirectionOverAPeriodDTO responsePayload = new MaxWindSpeedAndDirectionOverAPeriodDTO(20.0, "km/h", "North-East");
        String url = BASE_URL + "/MaximumWindSpeedAndDirectionOverAPeriod?groupNumber=5&latitude=50.7958&longitude=-4.2596&hourStart=1&hourEnd=12";

        when(restTemplate.getForEntity(eq(url), eq(MaxWindSpeedAndDirectionOverAPeriodDTO.class)))
                .thenReturn(null);

        assertThrows(WeatherAPIException.class, () -> weatherAPIGateway.getMaximumWindSpeedAndDirectionOverAPeriod(5, 50.7958, -4.2596, 1, 25));
    }

    /**
     * Tests the {@link WeatherAPIGatewayHttpImpl#getSunriseSunsetHour(int, double, double, String)} method
     * for successful retrieval of sunrise/sunset hour.
     */
    @Test
    public void testGetSunriseSunsetHour() {
        SunriseSunsetDTO responsePayload = new SunriseSunsetDTO(7.2, "h", "sunrise");
        String url = BASE_URL + "/SunriseOrSunsetTime?groupNumber=5&latitude=50.7958&longitude=-4.2596&option=sunrise";

        when(restTemplate.getForEntity(eq(url), eq(SunriseSunsetDTO.class)))
                .thenReturn(new ResponseEntity<>(responsePayload, HttpStatus.OK));

        SunriseSunsetDTO response = weatherAPIGateway.getSunriseSunsetHour(5, 50.7958, -4.2596, "sunrise");

        assertEquals(responsePayload, response);
    }

    /**
     * Tests the {@link WeatherAPIGatewayHttpImpl#getSunriseSunsetHour(int, double, double, String)} method
     * for handling invalid arguments.
     */
    @Test
    public void testFailGetSunriseSunsetHour() {
        String url = BASE_URL + "/SunriseOrSunsetTime?groupNumber=5&latitude=50.7958&longitude=-4.2596&option=InvalidOption";

        when(restTemplate.getForEntity(eq(url), eq(SunriseSunsetDTO.class)))
                .thenReturn(null);

        assertThrows(WeatherAPIException.class, () -> weatherAPIGateway.getSunriseSunsetHour(5, 50.7958, -4.2596, "InvalidOption"));
    }
}
