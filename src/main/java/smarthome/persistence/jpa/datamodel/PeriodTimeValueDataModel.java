package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.value.PeriodTimeValue;
import smarthome.domain.value.Value;

import java.sql.Timestamp;

@Entity
@Table(name = "PeriodTimeValue")

/**
 * PeriodTimeValueDataModel class for managing Value entities in the database within a period of time.
 */
public class PeriodTimeValueDataModel implements ValueDataModel{

    @Id
    /**
     * The ID of the value.
     */
    private String valueID;

    /**
     * The value's measurement.
     */
    private String measurement;

    /**
     * The unit of the reading.
     */
    private String unit;

    /**
     * The start time of the value reading.
     */
    private Timestamp startTime;

    /**
     * The end time of the value reading.
     */
    private Timestamp endTime;

    /**
     * The ID of the sensor that produced this value.
     */
    private String sensorID;

    /**
     * Default constructor for PeriodTimeValueDataModel.
     */
    public PeriodTimeValueDataModel() {
    }

    /**
     * Constructor for PeriodTimeValueDataModel. Used to persist Value objects in DB.
     *
     * @param value Value object that needs to be persisted in DB.
     */
    public PeriodTimeValueDataModel(Value value) {
        PeriodTimeValue periodTimeValue = (PeriodTimeValue) value;
        this.valueID = value.identity().toString();
        this.measurement = value.getReading().getMeasurement();
        this.unit = value.getReading().getUnit();
        this.startTime = periodTimeValue.getStartTimeReading();
        this.endTime = periodTimeValue.getEndTimeReading();
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
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Retrieves the end time of the value reading.
     *
     * @return The end time of the value reading.
     */
    public Timestamp getEndTime() {
        return endTime;
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
     * Updates the current PeriodTimeValueDataModel instance with information from a Value domain object.
     *
     * @param value The Value domain object containing the updated information.
     * @return True if the update was successful, false otherwise.
     */
    public boolean updateFromDomain(Value value) {
        PeriodTimeValue periodTimeValue = (PeriodTimeValue) value;
        if (value == null)
            return false;

        this.valueID = value.identity().toString();
        this.measurement = value.getReading().getMeasurement();
        this.unit = value.getReading().getUnit();
        this.startTime = periodTimeValue.getStartTimeReading();
        this.endTime = periodTimeValue.getEndTimeReading();
        this.sensorID = value.getSensorID().toString();

        return true;
    }

}
