package smarthome.gateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import smarthome.domain.gateway.WeatherAPIGateway;
import smarthome.service.internaldto.InstTemperatureDTO;
import smarthome.service.internaldto.InstWindSpeedAndDirectionDTO;
import smarthome.service.internaldto.MaxWindSpeedAndDirectionOverAPeriodDTO;
import smarthome.service.internaldto.SunriseSunsetDTO;
import smarthome.util.ConfigScraper;
import smarthome.util.PropertyLoader;
import smarthome.util.exceptions.WeatherAPIException;

/**
 * Gateway class for interacting with the Weather API.
 */
@Component
public class WeatherAPIGatewayHttpImpl implements WeatherAPIGateway {

    /**
     * The base URL for the Weather API.
     */
    private final String BASE_URL;

    /**
     * The RestTemplate instance used for making HTTP requests.
     */
    private RestTemplate restTemplate;

    /**
     * Constructs a WeatherService with the given RestTemplate instance.
     *
     */
    public WeatherAPIGatewayHttpImpl() {
        this.restTemplate = new RestTemplate();
        PropertyLoader propertyLoader = new PropertyLoader();
        ConfigScraper configScraper = new ConfigScraper("config/general.properties", propertyLoader);
        this.BASE_URL = configScraper.loadWeatherServicesAPIHTTPURL();
    }

    /**
     * Sends a GET request to the /InstantaneousTemperature endpoint with the specified group number,
     * latitude, longitude, and hour.
     *
     * @param groupNumber the group number to fetch the temperature for
     * @param latitude    the latitude of the location
     * @param longitude   the longitude of the location
     * @param hour        the specific hour to fetch the temperature for
     * @return the response containing the instantaneous temperature measurement and additional info
     * @throws WeatherAPIException if the API call fails or the response entity is null
     */
    @Override
    public InstTemperatureDTO getInstantaneousTemperature(int groupNumber, double latitude, double longitude, int hour) {
        String url = BASE_URL + "/InstantaneousTemperature?groupNumber=" + groupNumber + "&latitude=" + latitude + "&longitude=" + longitude + "&hour=" + hour;
        ResponseEntity<InstTemperatureDTO> responseEntity = restTemplate.getForEntity(url, InstTemperatureDTO.class);
        return handleResponseEntity(responseEntity);
    }

    /**
     * Sends a GET request to the /InstantaneousWindSpeedAndDirection endpoint with the specified group number,
     * latitude, longitude, and hour.
     *
     * @param groupNumber the group number to fetch the wind speed and direction for
     * @param latitude    the latitude of the location
     * @param longitude   the longitude of the location
     * @param hour        the specific hour to fetch the wind speed and direction for
     * @return the response containing the instantaneous wind speed and direction measurement and additional info
     * @throws WeatherAPIException if the API call fails or the response entity is null
     */
    @Override
    public InstWindSpeedAndDirectionDTO getInstantaneousWindSpeedAndDirection(int groupNumber, double latitude, double longitude, int hour) {
        String url = BASE_URL + "/InstantaneousWindSpeedAndDirection?groupNumber=" + groupNumber + "&latitude=" + latitude + "&longitude=" + longitude + "&hour=" + hour;
        ResponseEntity<InstWindSpeedAndDirectionDTO> responseEntity = restTemplate.getForEntity(url, InstWindSpeedAndDirectionDTO.class);
        return handleResponseEntity(responseEntity);
    }

    /**
     * Sends a GET request to the /MaximumWindSpeedAndDirectionOverAPeriod endpoint with the specified group number,
     * latitude, longitude, start hour, and end hour.
     *
     * @param groupNumber the group number to fetch the maximum wind speed and direction for
     * @param latitude    the latitude of the location
     * @param longitude   the longitude of the location
     * @param hourStart   the start hour of the period to fetch the maximum wind speed and direction for
     * @param hourEnd     the end hour of the period to fetch the maximum wind speed and direction for
     * @return the response containing the maximum wind speed and direction measurement and additional info
     * @throws WeatherAPIException if the API call fails or the response entity is null
     */
    @Override
    public MaxWindSpeedAndDirectionOverAPeriodDTO getMaximumWindSpeedAndDirectionOverAPeriod(int groupNumber, double latitude, double longitude, int hourStart, int hourEnd) {
        String url = BASE_URL + "/MaximumWindSpeedAndDirectionOverAPeriod?groupNumber=" + groupNumber + "&latitude=" + latitude + "&longitude=" + longitude + "&hourStart=" + hourStart + "&hourEnd=" + hourEnd;
        ResponseEntity<MaxWindSpeedAndDirectionOverAPeriodDTO> responseEntity = restTemplate.getForEntity(url, MaxWindSpeedAndDirectionOverAPeriodDTO.class);
        return handleResponseEntity(responseEntity);
    }

    /**
     * Handles the ResponseEntity.
     *
     * @param <T>           the type of the response body
     * @param responseEntity the ResponseEntity
     * @return the response body if the response is not null, otherwise throws WeatherAPIException
     * @throws WeatherAPIException if the response entity is null
     */
    private <T> T handleResponseEntity(ResponseEntity<T> responseEntity) {
        if (responseEntity == null) {
            throw new WeatherAPIException("Failed to receive response from the Weather API");
        }
        return responseEntity.getBody();
    }

    /**
     * Sets the RestTemplate instance to be used for making HTTP requests.
     *
     * @param restTemplate the RestTemplate instance to be set
     */
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Sends a GET request to the /SunriseOrSunsetTime endpoint with the specified group number,
     * latitude, longitude, and option.
     *
     * @param groupNumber the group number to fetch the hour for
     * @param latitude    the latitude of the location
     * @param longitude   the longitude of the location
     * @param option      the specific option to fetch the Sunrise or Sunset
     * @return the response containing the hour measurement and additional info
     * @throws WeatherAPIException if the API call fails or the response entity is null
     */
    @Override
    public SunriseSunsetDTO getSunriseSunsetHour(int groupNumber, double latitude, double longitude, String option) {
        String url = BASE_URL + "/SunriseOrSunsetTime?groupNumber=" + groupNumber + "&latitude=" + latitude + "&longitude=" + longitude + "&option=" + option;
        ResponseEntity<SunriseSunsetDTO> responseEntity = restTemplate.getForEntity(url, SunriseSunsetDTO.class);
        return handleResponseEntity(responseEntity);
    }
}
