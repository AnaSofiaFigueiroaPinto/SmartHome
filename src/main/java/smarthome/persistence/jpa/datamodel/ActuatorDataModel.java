package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.RangeDecimal;
import smarthome.domain.valueobjects.RangeInt;

/**
 * Entity class representing the data model for an actuator.
 */
@Entity
@Table(name = "ACTUATOR")
public class ActuatorDataModel {

    /**
     * The unique identifier for the actuator.
     */
    @Id
    private String actuatorID;

    /**
     * The unique identifier for the functionality of the actuator.
     */
    @Column
    private String actuatorFunctionalityID;

    /**
     * The upper limit for integer values accepted by the actuator.
     */
    @Column(nullable = true)
    private Integer upperLimitInt;

    /**
     * The lower limit for integer values accepted by the actuator.
     */
    @Column(nullable = true)
    private Integer lowerLimitInt;

    /**
     * The upper limit for decimal values accepted by the actuator.
     */
    @Column(nullable = true)
    private Double upperLimitDecimal;

    /**
     * The lower limit for decimal values accepted by the actuator.
     */
    @Column(nullable = true)
    private Double lowerLimitDecimal;

    /**
     * The precision for decimal values accepted by the actuator.
     */
    @Column(name = "precision_value", nullable = true)
    private Integer precision;

    /**
     * The unique identifier for the device that the actuator is connected to.
     */
    @Column
    private String deviceID;

    /**
     * Empty constructor of Actuator Data Model object
     */
    public ActuatorDataModel() {
    }

    /**
     * Constructor of Actuator Data Model object
     *
     * @param actuator instance of an Actuator object from domain
     */
    public ActuatorDataModel(Actuator actuator) {
        actuatorFunctionalityID = actuator.getActuatorFunctionalityID().toString();
        actuatorID = actuator.identity().toString();
        ActuatorProperties properties = actuator.getActuatorProperties();
        if (properties.getRangeInt() != null) {
            RangeInt rangeInt = properties.getRangeInt();
            upperLimitInt = rangeInt.getUpperLimitInt();
            lowerLimitInt = rangeInt.getLowerLimitInt();
        } else if (properties.getRangeDecimal() != null) {
            RangeDecimal rangeDecimal = properties.getRangeDecimal();
            upperLimitDecimal = rangeDecimal.getUpperLimitDecimal();
            lowerLimitDecimal = rangeDecimal.getLowerLimitDecimal();
            precision = rangeDecimal.getPrecision();
        }
        deviceID = actuator.getDeviceName().toString();
    }

    /**
     * Get the actuator functionality ID.
     *
     * @return The actuator functionality ID.
     */

    public String getActuatorFunctionalityID() {
        return actuatorFunctionalityID;
    }

    /**
     * Get the actuator ID.
     *
     * @return The actuator ID.
     */

    public String getActuatorID() {
        return actuatorID;
    }

    /**
     * Get the upper limit for integer values accepted by the actuator.
     *
     * @return The upper limit for integer values.
     */

    public Integer getUpperLimitInt() {
        return upperLimitInt;
    }

    /**
     * Get the lower limit for integer values accepted by the actuator.
     *
     * @return The lower limit for integer values.
     */

    public Integer getLowerLimitInt() {
        return lowerLimitInt;
    }

    /**
     * Get the upper limit for decimal values accepted by the actuator.
     *
     * @return The upper limit for decimal values.
     */

    public Double getUpperLimitDecimal() {
        return upperLimitDecimal;
    }

    /**
     * Get the lower limit for decimal values accepted by the actuator.
     *
     * @return The lower limit for decimal values.
     */

    public Double getLowerLimitDecimal() {
        return lowerLimitDecimal;
    }

    /**
     * Get the precision of decimal values accepted by the actuator.
     *
     * @return The precision of decimal values.
     */

    public Integer getPrecision() {
        return precision;
    }

    /**
     * Get the ID of the device associated with the actuator.
     *
     * @return The device ID.
     */

    public String getDeviceID() {
        return deviceID;
    }


    /**
     * Method that updates an Actuator object based on the information kept in the Domain.
     * @param actuator Actuator object to update from.
     * @return true if correctly updated, false if not.
     */
    public boolean updateFromDomain(Actuator actuator)
    {
        if(actuator == null)
        {
            return false;
        }
        this.actuatorFunctionalityID = actuator.getActuatorFunctionalityID().toString();
        this.actuatorID = actuator.identity().toString();
        ActuatorProperties properties = actuator.getActuatorProperties();
        if (properties.getRangeInt() != null) {
            RangeInt rangeInt = properties.getRangeInt();
            this.upperLimitInt = rangeInt.getUpperLimitInt();
            this.lowerLimitInt = rangeInt.getLowerLimitInt();
        } else if (properties.getRangeDecimal() != null) {
            RangeDecimal rangeDecimal = properties.getRangeDecimal();
            this.upperLimitDecimal = rangeDecimal.getUpperLimitDecimal();
            this.lowerLimitDecimal = rangeDecimal.getLowerLimitDecimal();
            this.precision = rangeDecimal.getPrecision();
        }
        this.deviceID = actuator.getDeviceName().toString();
        return true;
    }

}
