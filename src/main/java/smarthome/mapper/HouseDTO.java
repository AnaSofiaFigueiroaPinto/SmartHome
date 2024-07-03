package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a House.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HouseDTO extends RepresentationModel<HouseDTO>
{
    /**
     * The unique identifier for the house.
     */
    public final String houseID;

    /**
     * The name of the street where the house is located.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public final String street;

    /**
     * The door number of the house.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public final String doorNumber;

    /**
     * The ZIP code of the area where the house is located.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public final String zipCode;

    /**
     * The city where the house is located.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public final String city;

    /**
     * The country where the house is located.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public final String country;

    /**
     * The latitude coordinate of the house's location.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public final double latitude;

    /**
     * The longitude coordinate of the house's location.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public final double longitude;


    /**
     * Constructor of HouseDTO object using attributes.
     *
     * @param houseID    The identifier of a houseDTO.
     * @param street     The street of a houseDTO.
     * @param doorNumber The doorNumber of a houseDTO.
     * @param zipCode    The zipCode of a houseDTO.
     * @param city       The city of a houseDTO.
     * @param country    The country of a houseDTO.
     * @param latitude   The latitude of a houseDTO.
     * @param longitude  The longitude of a houseDTO.
     */
    public HouseDTO(@JsonProperty("houseID") String houseID,
                    @JsonProperty("street") String street,
                    @JsonProperty("doorNumber") String doorNumber,
                    @JsonProperty("zipCode") String zipCode,
                    @JsonProperty("city") String city,
                    @JsonProperty("country") String country,
                    @JsonProperty("latitude") double latitude,
                    @JsonProperty("longitude") double longitude) {
        this.houseID = houseID;
        this.street = street;
        this.doorNumber = doorNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Overriding the equals method to compare two HouseDTO objects.
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        HouseDTO houseDTO = (HouseDTO) obj;
        return Double.compare(latitude, houseDTO.latitude) == 0 && Double.compare(longitude, houseDTO.longitude) == 0
                && Objects.equals(houseID, houseDTO.houseID) && Objects.equals(street, houseDTO.street) &&
                Objects.equals(doorNumber, houseDTO.doorNumber) && Objects.equals(zipCode, houseDTO.zipCode) &&
                Objects.equals(city, houseDTO.city) && Objects.equals(country, houseDTO.country);
    }

    /**
     * Overriding the hashCode method to generate a hash code for a HouseDTO object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), houseID, street, doorNumber, zipCode, city, country, latitude, longitude);
    }

}
