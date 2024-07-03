package smarthome.persistence.jpa.datamodel;

import org.springframework.stereotype.Component;
import smarthome.domain.value.ImpFactoryInstantTimeLocationValue;
import smarthome.domain.value.InstantTimeLocationValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class that has the responsibility of converting InstantTimeValueDataModel objects to InstantTimeValue domain objects.
 */
@Component
public class MapperInstantTimeLocationValueDataModel {
    /**
     * Converts a InstantLocationTimeValueDataModel object to a corresponding InstantLocationTimeValue.
     *
     * @param factory        The factory used to create InstantLocationTimeValue instances.
     * @param valueDataModel The InstantTimeLocationValueDataModel to be converted.
     * @return Value object from domain.
     */
    public Value toDomain(ImpFactoryInstantTimeLocationValue factory, InstantTimeLocationValueDataModel valueDataModel) {
        try {
            ValueID valueID = new ValueID(valueDataModel.getValueID());

            SensorID sensorID = new SensorID(valueDataModel.getSensorID());
            Reading reading = new Reading(valueDataModel.getMeasurement(), valueDataModel.getUnit());
            GPSCode gpsCode = new GPSCode(valueDataModel.getLatitude(), valueDataModel.getLongitude());
            Timestamp instantTime = valueDataModel.getInstantTime();

            return factory.createValue(valueID, sensorID, reading, instantTime, gpsCode);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts a list of InstantTimeLocationValueDataModel objects to a list of corresponding InstantTimeLocationValue objects.
     *
     * @param factory         The factory used to create InstantTimeLocationValue instances.
     * @param valueDataModels Iterable of InstantTimeLocationValueDataModel objects to be converted.
     * @return Iterable of Value objects from domain.
     */
    public Iterable<Value> toDomainList(ImpFactoryInstantTimeLocationValue factory, Iterable<InstantTimeLocationValueDataModel> valueDataModels) {
        if (factory == null || valueDataModels == null) {
            return null;
        }

        List<Value> valueList = new ArrayList<>();
        for (InstantTimeLocationValueDataModel valueDataModel : valueDataModels) {
            InstantTimeLocationValue instantTimeLocationValue = (InstantTimeLocationValue) toDomain(factory, valueDataModel);
            if (instantTimeLocationValue != null) {
                valueList.add(instantTimeLocationValue);
            }
        }
        return valueList;
    }
}
