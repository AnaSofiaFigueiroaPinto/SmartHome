package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.gateway.WeatherAPIGateway;
import smarthome.domain.house.House;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Location;
import smarthome.domain.valueobjects.SensorID;
import smarthome.persistence.repositoriesmem.InstantTimeValueRepository;
import smarthome.service.internaldto.InstTemperatureDTO;
import smarthome.service.internaldto.SunriseSunsetDTO;
import smarthome.util.ConfigScraper;
import smarthome.util.PropertyLoader;
import smarthome.util.exceptions.NoHouseInRepositoryException;
import smarthome.util.exceptions.NoHouseLocationDefined;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class to interact with the Weather API and manage weather-related data.
 */
@Service
public class WeatherAPIService {

    /**
     * Gateway for interacting with the Weather API.
     */
    private WeatherAPIGateway weatherAPIGateway;

    /**
     * Utility for loading configuration settings.
     */
    private ConfigScraper configScraper;

    /**
     * Repository for managing house data in memory.
     */
    private HouseRepository houseRepository;

    /**
     * Instance of ValueRepositoryMem object.
     */
    private InstantTimeValueRepository valueRepository;

    /**
     * The Latitude of the house.
     */
    private double latitude;

    /**
     * The Longitude of the house.
     */
    private double longitude;

    /**
     * A constant string indicating the path to the configuration properties file.
     */
    private static final String CONFIG_PROPERTIES = "config/general.properties";


    /**
     * Constructs a new WeatherAPIService with the given dependencies.
     *
     * @param weatherAPIGateway the gateway to interact with the weather API
     * @param houseRepository   the repository to manage house data
     * @param valueRepository   the repository to manage value data
     */
    public WeatherAPIService(WeatherAPIGateway weatherAPIGateway, HouseRepository houseRepository, InstantTimeValueRepository valueRepository) {
        this.weatherAPIGateway = weatherAPIGateway;
        this.houseRepository = houseRepository;
        this.valueRepository = valueRepository;
        PropertyLoader propertyLoader = new PropertyLoader();
        this.configScraper = new ConfigScraper(CONFIG_PROPERTIES, propertyLoader);
    }

    /**
     * Defines the group house location by sending the house's GPS coordinates to the weather API.
     *
     * @return the group number received from the weather API
     * @throws NoHouseInRepositoryException if no house is found in the repository
     * @throws NoHouseLocationDefined       if the house location is not defined
     * @throws RuntimeException             if the group number received from the weather API does not match the expected group number
     */
    private int defineGroupHouseLocation() {
        int groupNumber = Integer.parseInt(configScraper.loadGroupNumber());
        Iterable<House> housesInRepository = houseRepository.findAllEntities();
        if (!housesInRepository.iterator().hasNext()) {
            throw new NoHouseInRepositoryException();
        }
        House house = housesInRepository.iterator().next();
        Location houseLocation = house.getHouseLocation();
        if (houseLocation == null) {
            throw new NoHouseLocationDefined();
        } else {
            GPSCode gpsCode = houseLocation.getGpsCode();
            this.latitude = gpsCode.getLatitude();
            this.longitude = gpsCode.getLongitude();
        }
        return groupNumber;
    }

    /**
     * Retrieves the temperature for a given hour.
     *
     * @param hour the hour of the day (0-23)
     * @return the temperature for the specified hour
     * @throws IllegalArgumentException if the hour value is not between 0 and 23
     */
    public double getTemperatureForHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Invalid hour value. Hour must be between 0 and 23.");
        }
        int groupNumber = defineGroupHouseLocation();
        InstTemperatureDTO instTemperatureDTO = weatherAPIGateway.getInstantaneousTemperature(groupNumber, latitude, longitude, hour);
        return instTemperatureDTO.measurement;
    }

    /**
     * Calculates the maximum temperature difference between an inside sensor and the outside temperature within a given time range.
     *
     * @param insideSensorID The ID of the inside sensor for which to calculate the temperature difference.
     * @param startTime      The start time of the time range for which to calculate the temperature difference.
     * @param endTime        The end time of the time range for which to calculate the temperature difference.
     * @return The maximum temperature difference between the inside sensor and the outside temperature within the specified time range.
     */
    public double getMaxTemperatureDifferenceWithWeatherService(SensorID insideSensorID, Timestamp startTime, Timestamp endTime) {
        // Retrieve temperature readings for the inside sensor
        List<Value> insideValues = valueRepository.findBySensorId(insideSensorID);

        // Variables to store maximum temperature difference
        double maxDifference = -1;

        // Filter insideValues to only include those within the specified time range
        List<Value> filteredInsideValues = insideValues.stream()
                .filter(value -> {
                    long insideTimestamp = ((InstantTimeValue) value).getInstantTimeReading().getTime();
                    return insideTimestamp >= startTime.getTime() && insideTimestamp <= endTime.getTime();
                })
                .toList();

        // Process each inside value and fetch the corresponding outside temperature
        for (Value value : filteredInsideValues) {
            Timestamp timestamp = ((InstantTimeValue) value).getInstantTimeReading();
            long insideTimestamp = timestamp.getTime();
            // Convert Timestamp to LocalDateTime
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            if (localDateTime.getMinute() >= 45) {
                // Round up to the next hour
                localDateTime = localDateTime.plusMinutes(15);
            }
            // Truncate to the beginning of the hour
            LocalDateTime hourTruncatedDateTime = localDateTime.truncatedTo(java.time.temporal.ChronoUnit.HOURS);
            // Convert LocalDateTime back to Timestamp if needed
            Timestamp truncatedTimestamp = Timestamp.valueOf(hourTruncatedDateTime);
            int hour = truncatedTimestamp.toLocalDateTime().getHour();
            long outsideTimestamp = truncatedTimestamp.getTime();

            double outsideTemperature = getTemperatureForHour(hour);
            double insideTemperature = Double.parseDouble(value.getReading().getMeasurement());

            // Calculate the difference in milliseconds between the timestamps
            long timestampDifference = Math.abs(insideTimestamp - outsideTimestamp);

            // Compare with tolerance
            if (timestampDifference <= Long.parseLong(configScraper.loadTolerance())) {
                // Calculate temperature difference
                double difference = Math.abs(insideTemperature - outsideTemperature);
                if (difference > maxDifference) {
                    maxDifference = difference;
                }
            }
        }

        // Return the maximum temperature difference or -1 if no valid differences found
        return maxDifference;
    }

    /**
     * Retrieves the Sunrise or Sunset hour.
     *
     * @param option the option to choose sunrise or sunset
     * @return the hour of the sunrise or sunset
     */
    public double getSunriseSunsetHour(String option) {
        int groupNumber = defineGroupHouseLocation();
        if (!option.equals("sunrise") && !option.equals("sunset")) {
            throw new IllegalArgumentException("Invalid option. Option must be either 'sunrise' or 'sunset'.");
        }
        SunriseSunsetDTO sunriseSunsetDTO = weatherAPIGateway.getSunriseSunsetHour(groupNumber, latitude, longitude, option);
        return sunriseSunsetDTO.measurement;
    }
}

