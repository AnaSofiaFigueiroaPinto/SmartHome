package smarthome.domain.gateway;

import smarthome.service.internaldto.InstTemperatureDTO;
import smarthome.service.internaldto.InstWindSpeedAndDirectionDTO;
import smarthome.service.internaldto.MaxWindSpeedAndDirectionOverAPeriodDTO;
import smarthome.service.internaldto.SunriseSunsetDTO;
import smarthome.util.exceptions.WeatherAPIException;

public interface WeatherAPIGateway {

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
    InstTemperatureDTO getInstantaneousTemperature(int groupNumber, double latitude, double longitude, int hour);

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
    InstWindSpeedAndDirectionDTO getInstantaneousWindSpeedAndDirection(int groupNumber, double latitude, double longitude, int hour);

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
    MaxWindSpeedAndDirectionOverAPeriodDTO getMaximumWindSpeedAndDirectionOverAPeriod(int groupNumber, double latitude, double longitude, int hourStart, int hourEnd);

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
    SunriseSunsetDTO getSunriseSunsetHour(int groupNumber, double latitude, double longitude, String option);
}
