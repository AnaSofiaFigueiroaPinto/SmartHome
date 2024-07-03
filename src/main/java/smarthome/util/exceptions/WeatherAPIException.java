package smarthome.util.exceptions;

/**
 * General exception thrown when the Weather API returns an error.
 */
public class WeatherAPIException extends RuntimeException {
    public WeatherAPIException(String message) {
        super(message);
    }
}