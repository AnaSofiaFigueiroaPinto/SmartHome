package smarthome.domain.valueobjects;

import java.util.Objects;

/**
 * Represents the GPS code of a House in the system.
 */
public class GPSCode {

    /**
     * Declaring the attributes that will be used in constructors.
     */
    private final double latitude;
    private final double longitude;

    /**
     * Constructor that initializes the GPSCode object with the given parameters.
     * @param latitude double that represents the latitude of the House.
     * @param longitude double that represents the longitude of the House.
     * @throws IllegalArgumentException if the parameters are invalid.
     */
    public GPSCode(double latitude, double longitude) {
        if(latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid GPS coordinates");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Method that returns the latitude of the GPS code.
     * @return double value representing the latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Method that returns the longitude of the GPS code.
     * @return double value representing the longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Method that checks if two GPSCode objects are equal.
     *
     * @param object Object that is compared to the GPSCode.
     * @return True if the GPSCode is equal to the object. False if it is not.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        GPSCode gpsCode = (GPSCode) object;
        return Double.compare(latitude, gpsCode.latitude) == 0 && Double.compare(longitude, gpsCode.longitude) == 0;
    }

    /**
     * Method that returns the hash code of the GPSCode.
     *
     * @return Integer representing the hash code of the GPSCode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    /**
     * Method that returns the GPSCode of a house.
     * @return String representing the GPSCode of a house.
     */
    @Override
    public String toString() {
        return "Latitude: " + latitude + ", Longitude: " + longitude;
    }

}