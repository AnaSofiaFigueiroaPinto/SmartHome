package smarthome.persistence.jpa.datamodel;

import org.springframework.stereotype.Component;
import smarthome.domain.value.ImpFactoryInstantTimeValue;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.Value;
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
public class MapperInstantTimeValueDataModel {
    /**
     * Converts a InstantTimeValueDataModel object to a corresponding InstantTimeValue.
     *
     * @param factory        The factory used to create InstantTimeValue instances.
     * @param valueDataModel The InstantTimeValueDataModel to be converted.
     * @return Value object from domain.
     */
    public Value toDomain(ImpFactoryInstantTimeValue factory, InstantTimeValueDataModel valueDataModel) {
        try {
            ValueID valueID = new ValueID(valueDataModel.getValueID());

            SensorID sensorID = new SensorID(valueDataModel.getSensorID());
            Reading reading = new Reading(valueDataModel.getMeasurement(), valueDataModel.getUnit());
            Timestamp instantTime = valueDataModel.getInstantTime();

            return factory.createValue(valueID, sensorID, reading, instantTime);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts a list of InstantTimeValueDataModel objects to a list of corresponding InstantTimeValue objects.
     *
     * @param factory         The factory used to create InstantTimeValue instances.
     * @param valueDataModels Iterable of InstantTimeValueDataModel objects to be converted.
     * @return Iterable of Value objects from domain.
     */
    public Iterable<Value> toDomainList(ImpFactoryInstantTimeValue factory, Iterable<InstantTimeValueDataModel> valueDataModels) {
        if (factory == null || valueDataModels == null) {
            return null;
        }

        List<Value> valueList = new ArrayList<>();
        for (InstantTimeValueDataModel valueDataModel : valueDataModels) {
            InstantTimeValue instantTimeValue = (InstantTimeValue) toDomain(factory, valueDataModel);
            if (instantTimeValue != null) {
                valueList.add(instantTimeValue);
            }
        }
        return valueList;
    }
}
