package smarthome.persistence.jpa.datamodel;

import org.springframework.stereotype.Component;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensorfunctionality.FactorySensorFunctionality;
import smarthome.domain.sensorfunctionality.ImpFactorySensorFunctionality;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.persistence.repositoriesmem.SensorFunctionalityRepositoryMem;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class that has the responsibility of converting SensorDataModel objects to Sensor domain objects.
 */
@Component
public class MapperSensorDataModel {

    /**
     * Converts a SensorDataModel object to a corresponding Sensor.
     *
     * @param factorySensor   The factory used to create Sensor instances.
     * @param sensorDataModel The SensorDataModel to be converted.
     * @return Sensor object from domain.
     */
    public Sensor toDomain(FactorySensor factorySensor, SensorDataModel sensorDataModel) {
        try {
            //Instantiating a SensorID
            SensorID sensorID = new SensorID(sensorDataModel.getSensorID());
            //Instantiating a DeviceID
            DeviceID deviceID = new DeviceID(sensorDataModel.getDeviceID());
            //Instantiating a SensorFunctionalityID
            SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorDataModel.getSensorFunctionalityID());

            FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();
            SensorFunctionalityRepositoryMem sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);

            String sensorClass = sensorFunctionalityRepositoryMem.getClassNameForSensorFunctionalityID(sensorFunctionalityID);

            return factorySensor.createSensor(sensorID, deviceID, sensorFunctionalityID, sensorClass);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts a list of SensorDataModel objects to a list of corresponding Sensor objects.
     *
     * @param factorySensor    The factory used to create Sensor instances.
     * @param sensorDataModels Iterable of SensorDataModel objects to be converted.
     * @return Iterable of Sensor objects from domain.
     */
    public Iterable<Sensor> toDomainList(FactorySensor factorySensor, Iterable<SensorDataModel> sensorDataModels) {
        if (sensorDataModels == null || factorySensor == null) {
            return null;
        }

        List<Sensor> sensorList = new ArrayList<>();
        for (SensorDataModel sensorDataModel : sensorDataModels) {
            Sensor sensor = toDomain(factorySensor, sensorDataModel);
            if (sensor != null) {
                sensorList.add(sensor);
            }
        }
        return sensorList;
    }
}
