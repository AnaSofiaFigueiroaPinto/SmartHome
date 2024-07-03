package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.*;
import smarthome.domain.house.House;

@Entity
@Table(name = "HOUSE")
public class HouseDataModel {

    @Id

    private String houseID;
    private double latitude;
    private double longitude;
    private String street;
    private String doorNumber;
    private String zipCode;
    private String city;
    private String country;

    /**
     * Empty constructor of House Data Model Object.
     */
    public HouseDataModel() {
    }

    /**
     * Constructor of House Data Model Object.
     *
     * @param house instance of House object from domain.
     */
    public HouseDataModel(House house) {
        if (house.getHouseLocation() == null) {
            houseID = house.identity().toString();
        } else {
            houseID = house.identity().toString();
            latitude = house.getHouseLocation().getGpsCode().getLatitude();
            longitude = house.getHouseLocation().getGpsCode().getLongitude();
            street = house.getHouseLocation().getAddress().getStreet();
            doorNumber = house.getHouseLocation().getAddress().getDoorNumber();
            zipCode = house.getHouseLocation().getAddress().getZipCode();
            city = house.getHouseLocation().getAddress().getCity();
            country = house.getHouseLocation().getAddress().getCountry();
        }
    }

    /**
     * Method that gets the HouseID from House Data Model.
     *
     * @return HouseID.
     */
    public String getHouseID() {
        return houseID;
    }

    /**
     * Method that gets the Latitude from House Data Model.
     *
     * @return Latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Method that gets the Longitude from House Data Model.
     *
     * @return Longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Method that gets the Street from House Data Model.
     *
     * @return Street.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Method that gets the Door Number from House Data Model.
     *
     * @return Door Number.
     */
    public String getDoorNumber() {
        return doorNumber;
    }

    /**
     * Method that gets the Zip Code from House Data Model.
     *
     * @return Zip Code.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Method that gets the City from House Data Model.
     *
     * @return City.
     */
    public String getCity() {
        return city;
    }

    /**
     * Method that gets the Country from House Data Model.
     *
     * @return Country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method that updates the House Data Model from a House object from domain.
     *
     * @param house instance of House object from domain.
     * @return true if the update is successful, false otherwise.
     */

    public boolean updateFromDomain(House house) {
        if (house == null) {
            return false;
        }
        this.houseID = house.identity().toString();
        this.latitude = house.getHouseLocation().getGpsCode().getLatitude();
        this.longitude = house.getHouseLocation().getGpsCode().getLongitude();
        this.street = house.getHouseLocation().getAddress().getStreet();
        this.doorNumber = house.getHouseLocation().getAddress().getDoorNumber();
        this.zipCode = house.getHouseLocation().getAddress().getZipCode();
        this.city = house.getHouseLocation().getAddress().getCity();
        this.country = house.getHouseLocation().getAddress().getCountry();

        return true;
    }
}