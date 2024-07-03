package smarthome.domain.sensor;


import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory class to create Sensor objects.
 */
@Component
public class FactorySensor {

    /**
     * Method to create a Sensor object from the classes available in the sensorClasses List.
     *
     * @param deviceID              ID of the device that the sensor belongs to
     * @param sensorFunctionalityID ID of the sensor functionality that the sensor has
     * @param sensorID              object ID of the new sensor instance
     * @param sensorClass           class that represents the path + name of Sensor class
     * @return Sensor object if successful, null if unsuccessful.
     */
    public Sensor createSensor (
            SensorID sensorID,
            DeviceID deviceID,
            SensorFunctionalityID sensorFunctionalityID,
            String sensorClass
    ) {
        try {
            return (Sensor) Class.forName(sensorClass).getDeclaredConstructor(
                                    SensorID.class,
                                    DeviceID.class,
                                    SensorFunctionalityID.class
                            ).newInstance(
                                    sensorID,
                                    deviceID,
                                    sensorFunctionalityID
                            );
        } catch (ClassNotFoundException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException |
                 NullPointerException exception) {
            return null;
        }
    }


}
