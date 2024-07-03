package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.*;
import smarthome.domain.sensor.Sensor;

@Entity
@Table(name = "Sensor")
public class SensorDataModel
{
    @Id

    private String sensorID;
    private String deviceID;
    private String sensorFunctionalityID;

    public SensorDataModel ()
    {
    }

    /**
     * Constructor for SensorDataModel. Used to persist Sensor objects in DB.
     * @param sensor Sensor object that needs to be persisted in DB.
     */
    public SensorDataModel (Sensor sensor)
    {
        this.sensorID = sensor.identity().toString();
        this.deviceID = sensor.getDeviceID().toString();
        this.sensorFunctionalityID = sensor.getSensorFunctionalityID().toString();
    }

    public String getSensorID()
    {
        return sensorID;
    }

    public String getDeviceID()
    {
        return deviceID;
    }

    public String getSensorFunctionalityID()
    {
        return sensorFunctionalityID;
    }

    /**
     * Updates the current SensorEntity instance with information from a Sensor domain object.
     * <p>
     *
     * @param sensor The Sensor domain object containing the updated information.
     * @return True if the update was successful, false otherwise.
     */
    public boolean updateFromDomain(Sensor sensor)
    {
        if (sensor == null){
            return false;
        }
        this.sensorID = sensor.identity().toString();
        this.deviceID = sensor.getDeviceID().toString();
        this.sensorFunctionalityID = sensor.getSensorFunctionalityID().toString();

        return true;
    }
}
