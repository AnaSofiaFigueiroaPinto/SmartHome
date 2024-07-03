package smarthome.service.internaldto;

import smarthome.domain.valueobjects.HouseID;

public class InternalHouseDTO {
    /**
     * The unique identifier for the house.
     */
    public HouseID houseID;
    /**
     * The name of the street where the house is located.
     */
    public String street;

    /**
     * The door number of the house.
     */
    public String doorNumber;

    /**
     * The ZIP code of the area where the house is located.
     */
    public String zipCode;

    /**
     * The city where the house is located.
     */
    public String city;

    /**
     * The country where the house is located.
     */
    public String country;

    /**
     * The latitude coordinate of the house's location.
     */
    public double latitude;

    /**
     * The longitude coordinate of the house's location.
     */
    public double longitude;

    public InternalHouseDTO(HouseID houseID, String street, String doorNumber, String zipCode, String city, String country, double latitude, double longitude) {
        this.houseID = houseID;
        this.street = street;
        this.doorNumber = doorNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public InternalHouseDTO(HouseID houseID) {
        this.houseID = houseID;
    }
}
