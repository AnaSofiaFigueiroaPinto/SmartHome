package smarthome.domain.valueobjects;

import smarthome.ddd.ValueObject;

import java.util.Objects;

public class Location implements ValueObject {

    private final GPSCode gpsCode;
    private final Address address;

    /**
     * Constructor that initializes the Location instance with the GPSCode and Address as parameters
     *
     * @param address Address object that represents the address of the Location.
     * @param gpsCode GPSCode object that represents the GPSCode of the Location.
     */

    public Location(Address address, GPSCode gpsCode) {
        if (address == null || gpsCode == null) {
            throw new IllegalArgumentException("Invalid address or gpsCode parameters");
        }
        this.address = address;
        this.gpsCode = gpsCode;
    }

    public Address getAddress() {
        return this.address;
    }

    public GPSCode getGpsCode() {
        return this.gpsCode;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Location location = (Location) object;
        return Objects.equals(gpsCode, location.gpsCode) && Objects.equals(address, location.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gpsCode, address);
    }

    @Override
    public String toString() {
        return "Location: " + address + " " + gpsCode;
    }
}
