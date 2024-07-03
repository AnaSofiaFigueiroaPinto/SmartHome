package smarthome.domain.repository;

import smarthome.ddd.Repository;
import smarthome.domain.actuatorfunctionality.ActuatorFunctionality;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;

public interface ActuatorFunctionalityRepository extends Repository<ActuatorFunctionalityID, ActuatorFunctionality> {

    /**
     * Retrieve the class name for the actuator functionality ID.
     * @param actuatorFunctionalityID The actuator functionality ID.
     * @return A string representing the class name.
     */
    String getClassNameForActuatorFunctionalityID (ActuatorFunctionalityID actuatorFunctionalityID);
}
