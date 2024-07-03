package smarthome.domain.actuators;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;

/**
 * Class representing the Actuator abstract entity
 */
public abstract class Actuator implements AggregateRoot<ActuatorID> {
    /**
     * The identifier of the actuator
     */
    private final ActuatorID actuatorName;

    /**
     * The functionality ID associated with the actuator
     */
    private final ActuatorFunctionalityID actuatorFunctionalityID;

    /**
     * The properties associated with the actuator
     */
    private final ActuatorProperties actuatorProperties;

    /**
     * The identifier of the device the actuator belongs to
     */
    private final DeviceID deviceName;

    /**
     * Constructor for the Actuator object
     *
     * @param actuatorID            The identifier of the actuator
     * @param actuatorProperties      The properties associated with the actuator
     * @param deviceID              The identifier of the device the actuator belongs to
     * @param actuatorFunctionalityID The functionality ID associated with the actuator
     * @throws IllegalArgumentException if the parameters are null or blank
     */

    protected Actuator(ActuatorID actuatorID, ActuatorFunctionalityID actuatorFunctionalityID, ActuatorProperties actuatorProperties, DeviceID deviceID){
        if (!validConstructorArguments(actuatorID, deviceID, actuatorFunctionalityID)) {
            throw new IllegalArgumentException("Invalid Actuator parameters!");
        }
        this.actuatorFunctionalityID = actuatorFunctionalityID;
        this.actuatorProperties = actuatorProperties;
        this.deviceName = deviceID;
        this.actuatorName = actuatorID;
    }

    /**
     * Method to validate the parameters of the constructor for the BlindSetterActuator
     *
     * @param actuatorName            the name of the actuator
     * @param deviceName              The identifier of the device
     * @param actuatorFunctionalityID The functionality ID associated with the actuator
     * @return true if the parameter is not null or empty,false otherwise.
     */
    private boolean validConstructorArguments(ActuatorID actuatorName, DeviceID deviceName, ActuatorFunctionalityID actuatorFunctionalityID) {
        if (actuatorName == null)
            return false;
        if (deviceName == null)
            return false;
        return actuatorFunctionalityID != null;
    }

    /**
     * Method to get the name of the actuator
     *
     * @return the name of the actuator
     */
    public String getActuatorName() {
        return this.actuatorName.toString();
    }

    /**
     * Method to get the functionality ID of the actuator
     *
     * @return the functionality ID of the actuator
     */

    public ActuatorFunctionalityID getActuatorFunctionalityID() {
        return this.actuatorFunctionalityID;
    }

    /**
     * Method to get the properties of the actuator
     *
     * @return the properties of the actuator
     */

    public ActuatorProperties getActuatorProperties() {
        return actuatorProperties;
    }

    /**
     * Method to get the specific value of the actuator
     *
     * @return the specific value of the actuator
     */
    public DeviceID getDeviceName() {
        return deviceName;
    }

    /**
     * Method to get the identifier attribute (ID) of the object
     *
     * @return ActuatorID object which is the name of the actuator instance
     */
    public ActuatorID identity() {
        return actuatorName;
    }

    /**
     * Method to check if two objects of this type are the same. Since objects are entities, similarity is determined by the ID attribute
     *
     * @param object instance of BlindSetterActuator
     * @return true if the objects are the same, false if they are not
     */
    @Override
    public boolean isSameAs(Object object) {
        if (object instanceof Actuator) {
            Actuator actuator = (Actuator) object;
            return this.actuatorName.equals(actuator.identity());

        }
        return false;
    }
}
