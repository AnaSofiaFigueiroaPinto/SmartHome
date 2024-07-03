package smarthome.domain.house;

import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;

/**
 * This interface of Factory House contains methods to create a new house with or without a location.
 */
public interface FactoryHouse {
    /**
     * Method to create a House without a Location.
     *
     * @return the created instance of house.
     */
    public House createHouseWithOutLocation();

    /**
     * Method to create a House with parameters for location.
     *
     * @param location The instance of a Location object.
     * @return the created instance of house.
     */
    public House createHouseWithLocation(Location location);

    /**
     * Method to create a House with parameters for location to be used in persistence.
     *
     * @param houseID  Object houseID.
     * @param location The instance of a Location object.
     * @return the created instance of house.
     */
    public House createHouseWithOrWithoutLocation(HouseID houseID, Location location);

}
