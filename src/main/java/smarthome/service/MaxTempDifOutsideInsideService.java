package smarthome.service;

import org.springframework.stereotype.Component;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.persistence.repositoriesmem.InstantTimeValueRepository;
import smarthome.util.ConfigScraper;
import smarthome.util.PropertyLoader;

import java.sql.Timestamp;
import java.util.List;

@Component
public class MaxTempDifOutsideInsideService {
    /**
     * Instance of ValueRepositoryMem object.
     */
    private InstantTimeValueRepository valueRepository;

    /**
     * ConfigScraper object to read configuration properties.
     */
    private final ConfigScraper configScraper;

    /**
     * A constant string indicating the path to the configuration properties file
     */
    private static final String CONFIG_PROPERTIES = "config/general.properties";

    /**
     *
     * Constructor for MaxTempDifOutsideInside object.
     */
    public MaxTempDifOutsideInsideService(InstantTimeValueRepository valueRepository)
    {
        this.valueRepository = valueRepository;
        PropertyLoader propertyLoader = new PropertyLoader();
        this.configScraper = new ConfigScraper(CONFIG_PROPERTIES, propertyLoader);
    }

    /**
     * Calculates the maximum temperature difference between an inside sensor and the closest outside sensor readings within a given time range.
     *
     * @param insideSensorID The ID of the inside sensor for which to calculate the temperature difference.
     * @param outsideSensorID The ID of the outside sensor for which to calculate the temperature difference.
     * @param startTime      The start time of the time range for which to calculate the temperature difference.
     * @param endTime        The end time of the time range for which to calculate the temperature difference.
     * @return The maximum temperature difference between the inside and outside sensors within the specified time range.
     */
    public double getMaxTemperatureDifference(SensorID insideSensorID, SensorID outsideSensorID,
                                              Timestamp startTime, Timestamp  endTime) {

        // Retrieve temperature readings for inside and outside sensors
        List<Value> insideValues = valueRepository.findBySensorId(insideSensorID);
        List<Value> outsideValues = valueRepository.findBySensorId(outsideSensorID);

        // Variables to store maximum temperature difference and corresponding timestamps
        double maxDifference = -1;

        // Iterate through the temperature readings
        for (Value value : insideValues) {
            // Cast it to InstantValue and retrieve the reading instant
            long insideTimestamp = ((InstantTimeValue) value).getInstantTimeReading().getTime();
            if (insideTimestamp < startTime.getTime() || insideTimestamp > endTime.getTime()) {
                continue;
            }

            // Find the closest outside temperature reading within tolerance
            String valueString = value.getReading().getMeasurement();
            double insideTemperature = (Double.parseDouble(valueString));
            double outsideTemperature = findClosestTemperature(outsideValues, insideTimestamp);

            // Calculate temperature difference
            double difference = Math.abs(insideTemperature - outsideTemperature);

            // Update maximum temperature difference if necessary
            if (difference > maxDifference) {
                maxDifference = difference;
            }
        }

        return maxDifference;
    }

    /**
     * Finds the closest temperature reading to a given timestamp within a tolerance from a list of temperature values.
     *
     * @param values    The list of temperature values.
     * @param timestamp The timestamp for which to find the closest temperature reading.
     * @return The temperature value closest to the given timestamp within the specified tolerance.
     */
    private double findClosestTemperature(List<Value> values, long timestamp) {
        double closestTemperature = Double.NaN;

        for (Value value : values) {
            long currentTimestampDiff = Math.abs((((InstantTimeValue) value).getInstantTimeReading().getTime() - timestamp));
            if (currentTimestampDiff <= Long.parseLong(configScraper.loadTolerance())) {
                closestTemperature = Double.parseDouble((value).getReading().getMeasurement());
            }
        }
        return closestTemperature;
    }
}
