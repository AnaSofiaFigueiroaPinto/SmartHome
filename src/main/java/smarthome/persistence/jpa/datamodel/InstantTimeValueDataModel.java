package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.Value;

import java.sql.Timestamp;

@Entity
@Table(name = "InstantTimeValue")

/**
 * InstantTimeValueDataModel class for managing Value entities in the database in a given instant.
 */
public class InstantTimeValueDataModel implements ValueDataModel{

    @Id
    /**
     * The ID of the value.
     */
    private String valueID;

    /**
     * The value's reading.
     */
    private String measurement;

    /**
     * The unit of the reading.
     */
    private String unit;

    /**
     * The start time of the value reading.
     */
    private Timestamp instantTime;

    /**
     * The ID of the sensor that produced this value.
     */
    private String sensorID;

    /**
     * Default constructor for InstantTimeValueDataModel.
     */
    public InstantTimeValueDataModel() {
    }

    /**
     * Constructor for InstantTimeValueDataModel. Used to persist Value objects in DB.
     *
     * @param value Value object that needs to be persisted in DB.
     */
    public InstantTimeValueDataModel(Value value) {
        InstantTimeValue instantTimeValue = (InstantTimeValue) value;
        this.valueID = value.identity().toString();
        this.measurement = value.getReading().getMeasurement();
        this.unit = value.getReading().getUnit();
        this.instantTime = instantTimeValue.getInstantTimeReading();
        this.sensorID = value.getSensorID().toString();
    }

    /**
     * Retrieves the ID of this value.
     *
     * @return The ID of this value as a string.
     */
    public String getValueID() {
        return valueID;
    }

    /**
     * Retrieves the reading associated with this value.
     *
     * @return The reading associated with this value.
     */
    public String getMeasurement() {
        return measurement;
    }

    /**
     * Retrieves the unit of the reading associated with this value.
     *
     * @return The unit of the reading associated with this value.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Retrieves the start time of the value reading.
     *
     * @return The start time of the value reading.
     */
    public Timestamp getInstantTime() {
        return instantTime;
    }

    /**
     * Retrieves the ID of the sensor that produced this value.
     *
     * @return The ID of the sensor that produced this value.
     */
    public String getSensorID() {
        return sensorID;
    }

    /**
     * Updates the current InstantTimeValueDataModel instance with information from a Value domain object.
     *
     * @param value The Value domain object containing the updated information.
     * @return True if the update was successful, false otherwise.
     */
    public boolean updateFromDomain(Value value) {
        if (value == null)
            return false;

        this.valueID = value.identity().toString();
        this.measurement = value.getReading().getMeasurement();
        this.unit = value.getReading().getUnit();
        this.instantTime = ((InstantTimeValue) value).getInstantTimeReading();
        this.sensorID = value.getSensorID().toString();

        return true;
    }

}
