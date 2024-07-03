package smarthome.domain.actuatorfunctionality;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;

public class ActuatorFunctionality implements AggregateRoot<ActuatorFunctionalityID> {
    private final ActuatorFunctionalityID actuatorFunctionalityID;

    /**
     * Constructor for ActuatorFunctionality object
     *
     * @param actuatorFunctionalityID value Object Actuator Functionality ID
     */

    protected ActuatorFunctionality(ActuatorFunctionalityID actuatorFunctionalityID) {
        if (actuatorFunctionalityID == null) {
            throw new IllegalArgumentException("Invalid ActuatorFunctionalityID");
        }
        this.actuatorFunctionalityID = actuatorFunctionalityID;
    }

    /**
     * Returns the identity of the actuator functionality
     *
     * @return the {@code ActuatorFunctionalityID} representing the identity of the actuator functionality
     */

    @Override
    public ActuatorFunctionalityID identity() {
        return actuatorFunctionalityID;
    }

    /**
     * Checks if the specified object is the same as this ActuatorFunctionality
     *
     * @param object the object to compare with
     * @return {@code true} if the specified object is the same as this ActuatorFunctionality
     * {@code false} otherwise
     */
    @Override
    public boolean isSameAs(Object object) {
        if (object instanceof ActuatorFunctionality) {
            ActuatorFunctionality actuatorFunctionality = (ActuatorFunctionality) object;
            return this.actuatorFunctionalityID.equals(actuatorFunctionality.identity());
        }
        return false;
    }
}
