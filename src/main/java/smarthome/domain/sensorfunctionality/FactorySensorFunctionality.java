package smarthome.domain.sensorfunctionality;

import smarthome.domain.valueobjects.SensorFunctionalityID;

public interface FactorySensorFunctionality {
    SensorFunctionality createSensorFunctionality(SensorFunctionalityID sensorFunctionalityID);
}
