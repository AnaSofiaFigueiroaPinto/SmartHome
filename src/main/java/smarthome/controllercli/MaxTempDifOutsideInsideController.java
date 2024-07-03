package smarthome.controllercli;

import smarthome.domain.valueobjects.SensorID;
import smarthome.mapper.SensorDTO;
import smarthome.service.MaxTempDifOutsideInsideService;

import java.sql.Timestamp;

/**
 * Controller class responsible for handling requests related to maximum temperature differences
 * between outside and inside sensors.
 */
public class MaxTempDifOutsideInsideController {


    /**
     * The service responsible for calculating maximum temperature differences between outside and inside sensors.
     */
    private final MaxTempDifOutsideInsideService maxTempDifOutsideInsideService;

    /**
     * Constructs a MaxTempDifOutsideInsideController with the specified services.
     *
     * @param maxTempDifOutsideInsideService The service responsible for calculating maximum temperature differences
     *                                       between outside and inside sensors.
     */
    public MaxTempDifOutsideInsideController(MaxTempDifOutsideInsideService maxTempDifOutsideInsideService) {
        this.maxTempDifOutsideInsideService = maxTempDifOutsideInsideService;
    }

    /**
     * Calculates the maximum temperature difference between outside and inside sensors within the specified time range.
     *
     * @param insideSensorDTO The SensorDTO object representing the sensor inside the house for which the maximum temperature difference
     *                  is to be calculated.
     * @param outsideSensorDTO The SensorDTO object representing the sensor outside the house for which the maximum temperature difference
     *                  is to be calculated.
     * @param start     The start timestamp of the time range.
     * @param end       The end timestamp of the time range.
     * @return The maximum temperature difference between outside and inside sensors within the specified
     * time range, or -1 if an error occurs during calculation.
     */
    public double maxTempDifOutsideInside(SensorDTO insideSensorDTO, SensorDTO outsideSensorDTO, Timestamp start, Timestamp end) {
        try {
            SensorID insideSensorID = new SensorID(insideSensorDTO.sensorName);
            SensorID outsideSensorID = new SensorID(outsideSensorDTO.sensorName);
            return maxTempDifOutsideInsideService.getMaxTemperatureDifference(insideSensorID, outsideSensorID, start, end);
        } catch (RuntimeException e) {
            return -1;
        }
    }

}
