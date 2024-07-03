package smarthome.domain.valueobjects;

import smarthome.ddd.DomainID;

import java.util.Objects;

/**
 * Represents a {@code ActuatorFunctionalityID} class within the smart home system.
 */
public class ActuatorFunctionalityID implements DomainID {

    /**
     * Declaring the attributes that will be used in the constructors.
     */
    private final String actuatorFunctionalityName;

    /**
     * Constructor for the ActuatorFunctionalityID object with the provided actuator functionality ID
     *
     * @param actuatorFunctionalityName the functionality ID of the actuator
     * @throws IllegalArgumentException if the actuatorFunctionalityID is null, empty or blank
     */
    public ActuatorFunctionalityID(String actuatorFunctionalityName) {
        if (actuatorFunctionalityName == null || actuatorFunctionalityName.isEmpty() || actuatorFunctionalityName.isBlank()) {
            throw new IllegalArgumentException("Invalid ID! The ID cannot be null, empty!");
        }
        this.actuatorFunctionalityName = actuatorFunctionalityName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        ActuatorFunctionalityID actuatorFunctionalityID = (ActuatorFunctionalityID) object;
        return Objects.equals(actuatorFunctionalityName, actuatorFunctionalityID.actuatorFunctionalityName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actuatorFunctionalityName);
    }

    @Override
    public String toString() {
        return actuatorFunctionalityName;
    }
}
