package smarthome.domain.actuatorfunctionality;

import smarthome.domain.valueobjects.ActuatorFunctionalityID;

public interface FactoryActuatorFunctionality {
    ActuatorFunctionality createActuatorFunctionality(ActuatorFunctionalityID actuatorFunctionalityID);
}
