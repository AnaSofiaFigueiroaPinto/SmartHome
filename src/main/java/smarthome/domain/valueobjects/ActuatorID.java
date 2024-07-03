package smarthome.domain.valueobjects;

import smarthome.ddd.DomainID;

import java.util.Objects;

/**
 * Represents a {@code ActuatorID} class within the smart home system.
 */
public class ActuatorID implements DomainID {

    /**
     * Declaring the attributes that will be used in the constructors.
     */
    private final String actuatorName;

    /**
     * Constructor for the ActuatorID object with the provided actuator name
     * @param actuatorName the name of the actuator
     * @throws IllegalArgumentException if the actuatorName is null, empty or blank
     */
    public ActuatorID(String actuatorName) {
        if (actuatorName == null || actuatorName.isEmpty() || actuatorName.isBlank()) {
            throw new IllegalArgumentException("Invalid ID! The ID cannot be null, empty!");
        }
        this.actuatorName = actuatorName;
    }

    /**
     * Compares this ActuatorID to the specified object. The result is true if the argument is not null
     * and is an ActuatorID object that represents the same actuatorName as this object.
     * @param object The object to compare this ActuatorID against.
     * @return true if the given object represents an ActuatorID equivalent to this actuatorName, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        ActuatorID actuatorID = (ActuatorID) object;
        return Objects.equals(actuatorName, actuatorID.actuatorName);
    }

    /**
     * Returns the hash code value for this ActuatorID object.
     * @return the hash code value for this ActuatorID object.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(actuatorName);
    }

    /**
     * Returns the string representation of the ActuatorID, which is its actuatorName.
     * @return The actuatorName of the ActuatorID.
     */
    @Override
    public String toString() {
        return actuatorName;
    }

}

