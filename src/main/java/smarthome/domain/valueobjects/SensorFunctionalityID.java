package smarthome.domain.valueobjects;

import smarthome.ddd.DomainID;

import java.util.Objects;

public class SensorFunctionalityID implements DomainID {

    private final String sensorFunctionalityID;

    /**
     * Constructs a new SensorFunctionalityID object with the specified ID.
     *
     * @param sensorFunctionalityID the ID string representing the sensor functionality
     * @throws IllegalArgumentException if the provided ID is null or empty
     */

    public SensorFunctionalityID(String sensorFunctionalityID) {
        if (sensorFunctionalityID == null || sensorFunctionalityID.isEmpty() || sensorFunctionalityID.isBlank()) {
            throw new IllegalArgumentException("Sensor Functionality ID cannot be null or empty");
        }
        this.sensorFunctionalityID = sensorFunctionalityID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorFunctionalityID that = (SensorFunctionalityID) o;
        return Objects.equals(sensorFunctionalityID, that.sensorFunctionalityID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sensorFunctionalityID);
    }

    @Override
    public String toString() {
        return sensorFunctionalityID;
    }
}
