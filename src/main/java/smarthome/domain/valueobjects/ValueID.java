package smarthome.domain.valueobjects;

import smarthome.ddd.DomainID;

import java.util.Objects;

/**
 * Represents a {@code ValueID} class within the smart home system.
 * This class is used to represent the ID of a value.
 */
public class ValueID implements DomainID
{

    /**
     * Declaring the attributes that will be used in the constructors.
     */
    private final String valueIdentifier;

    /**
     * Constructor that receives a string and creates a new ValueID.
     *
     * @param valueIdentifier String that represents the ID of the value.
     * @throws IllegalArgumentException in case the id is not valid.
     */
    public ValueID(String valueIdentifier) {
        if (!validateID(valueIdentifier)) {
            throw new IllegalArgumentException("Please provide a valid id to the Value");
        } else {
            this.valueIdentifier = valueIdentifier;
        }
    }

    /**
     * Validates the ID string to ensure it is non-null and non-empty.
     *
     * @param id The ID string to validate.
     * @return true if id is valid false if not.
     */
    private boolean validateID(String id) {
        return id != null && !id.trim().isEmpty();
    }

    /**
     * Method that returns the hash code of the ValueID.
     *
     * @return Integer representing the hash code of the ValueID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValueID valueID)) return false;
        return Objects.equals(valueIdentifier, valueID.valueIdentifier);
    }

    /**
     * Method that returns the hash code of the ValueID.
     *
     * @return Integer representing the hash code of the ValueID.
     */
    @Override
    public int hashCode()
    {
        return valueIdentifier.hashCode();
    }

    /**
     * Method that returns the ID of the value.
     *
     * @return String representing the ID of the value.
     */
    @Override
    public String toString()
    {
        return valueIdentifier;
    }
}
