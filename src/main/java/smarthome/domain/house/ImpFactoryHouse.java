package smarthome.domain.house;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;

/**
 * This implementation of Factory House manages the creation of a new house with or without a location.
 */
@Component
public class ImpFactoryHouse implements FactoryHouse {

    /**
     * Method that creates a House object without a location.
     *
     * @return House object without a location.
     */
    @Override
    public House createHouseWithOutLocation() {
        return new House();
    }


    /**
     * Method that creates a House object with a location.
     *
     * @param location The instance of a Location object.
     * @return House object with a location.
     */
    @Override
    public House createHouseWithLocation(Location location) {
        try {
            return new House(location);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Method that creates a House object with a HouseID and a location using persistence parameters.
     *
     * @param houseID  ID object that is given by persistence.
     * @param location The instance of a Location object.
     * @return House object.
     */
    @Override
    public House createHouseWithOrWithoutLocation(HouseID houseID, Location location) {
        if (houseID == null)
            return null;
        if (location == null)
            return new House(houseID);
        return new House(houseID, location);
    }
}
