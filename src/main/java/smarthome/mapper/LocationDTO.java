package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a Location.
 */
public class LocationDTO extends RepresentationModel<LocationDTO>
{

    public final String street;
    public final String doorNumber;
    public final String zipCode;
    public final String city;
    public final String country;

    public final double latitude;
    public final double longitude;


    /**
     * Method to compare two LocationDTO objects.
     * @param o LocationDTO object to compare.
     * @return boolean value if the objects are equal or not.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LocationDTO that = (LocationDTO) o;
        return Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0 && Objects.equals(street, that.street) && Objects.equals(doorNumber, that.doorNumber) && Objects.equals(zipCode, that.zipCode) && Objects.equals(city, that.city) && Objects.equals(country, that.country);
    }

    /**
     * Method to generate hash code of LocationDTO object.
     * @return hash code of LocationDTO object.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), street, doorNumber, zipCode, city, country, latitude, longitude);
    }

    /**
     * Constructor of LocationDTO object using parameters.
     *
     * @param street     of LocationDTO object (String).
     * @param doorNumber of LocationDTO object (String).
     * @param zipCode    of LocationDTO object (String).
     * @param city       of LocationDTO object (String).
     * @param country    of LocationDTO object (String).
     * @param latitude   Latitude of LocationDTO object (double).
     * @param longitude  Longitude of LocationDTO object (double).
     */
    public LocationDTO(@JsonProperty("street") String street,
                       @JsonProperty("doorNumber") String doorNumber,
                       @JsonProperty("zipCode") String zipCode,
                       @JsonProperty("city") String city,
                       @JsonProperty("country") String country,
                       @JsonProperty("latitude") double latitude,
                       @JsonProperty("longitude") double longitude) {
        this.street = street;
        this.doorNumber = doorNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}

