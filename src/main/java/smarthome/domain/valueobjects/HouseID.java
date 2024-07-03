package smarthome.domain.valueobjects;

import smarthome.ddd.DomainID;

import java.util.Objects;

/**
 * Represents a {@code HouseID} class within the smart home system.
 */
public class HouseID implements DomainID {

    /**
     * Declaring the attributes that will be used in the constructors.
     */
    private final String houseIdentifier;

    /**
     * Constructor that receives a string and creates a new HouseId.
     * @param houseIdentifier String that represents the id of the house.
     * @throws IllegalArgumentException in case the id is not valid.
     */
    public HouseID(String houseIdentifier) {
        if (houseIdentifier !=null && !houseIdentifier.isEmpty() && !houseIdentifier.isBlank()) {
            this.houseIdentifier = houseIdentifier;
        } else {
            throw new IllegalArgumentException("Please provide a valid id to the house");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        HouseID houseId = (HouseID) object;
        return Objects.equals(houseIdentifier, houseId.houseIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseIdentifier);
    }


    @Override
    public String toString() {
        return houseIdentifier;
    }
}
