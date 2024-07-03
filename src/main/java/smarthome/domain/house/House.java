package smarthome.domain.house;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.valueobjects.Address;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;

import java.util.UUID;

/**
 * Represents a {@code House} class within the smart home system.
 * Acts as the root of its Aggregate.
 */
public class House implements AggregateRoot<HouseID> {

    /**
     * Declaring attributes that will be used in the constructors.
     */
    private HouseID houseID;
    private Location houseLocation;

    /**
     * Constructor that initializes the House object, that generates a random HouseID.
     */
    protected House() {
        houseID = new HouseID(UUID.randomUUID().toString());
    }

    /**
     * Constructor that initializes the House object with the given parameters, that generates a random HouseID.
     *
     * @param houseLocation Location object that represents the location of the House.
     * @throws IllegalArgumentException if the parameters are invalid.
     */
    protected House(Location houseLocation) {
        if (houseLocation == null) {
            throw new IllegalArgumentException("House location cannot be null.");
        }
        houseID = new HouseID(UUID.randomUUID().toString());
        this.houseLocation = houseLocation;
    }

    /**
     * Constructor that instantiates a House object  without a location by using parameters from persistence.
     *
     * @param houseID ID object that is given by persistence.
     */
    protected House(HouseID houseID) {
        this.houseID = houseID;
    }

    /**
     * Constructor that instantiates a House object with a location by using parameters from persistence.
     *
     * @param houseID       ID object that is given by persistence.
     * @param houseLocation Location object that represents the location of the House.
     */
    protected House(HouseID houseID, Location houseLocation) {
        this.houseID = houseID;
        this.houseLocation = houseLocation;
    }

    /**
     * Method that edits the location of the House.
     *
     * @param address Address object that represents the address of the House.
     * @param gpsCode GPSCode object that represents the GPS code of the House.
     * @return Location if the location was successfully edited.
     */
    public Location editLocation(Address address, GPSCode gpsCode) {
        if (address == null || gpsCode == null) {
            return null;
        } else {
            Location location = new Location(address, gpsCode);
            this.houseLocation = location;
            return location;
        }
    }

    /**
     * Method to get the House Location.
     *
     * @return House Location.
     */
    public Location getHouseLocation() {
        return houseLocation;
    }

    /**
     * Method that returns the HouseID of the House.
     *
     * @return HouseID object.
     */
    @Override
    public HouseID identity() {
        return houseID;
    }

    /**
     * Method that checks if two House objects are equal.
     *
     * @param object Object that is compared to the House.
     * @return True if the House is equal to the object. False if it is not.
     */
    @Override
    public boolean isSameAs(Object object) {
        if (object instanceof House) {
            House house = (House) object;
            return this.houseID.equals(house.identity());
        }
        return false;
    }

}
