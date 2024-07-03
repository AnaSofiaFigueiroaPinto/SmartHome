package smarthome.persistence.jpa.datamodel;

import org.springframework.stereotype.Component;
import smarthome.domain.value.ImpFactoryPeriodTimeValue;
import smarthome.domain.value.PeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class that has the responsibility of converting PeriodTimeValueDataModel objects to PeriodTimeValue domain objects.
 */
@Component
public class MapperPeriodTimeValueDataModel {

    /**
     * Converts a PeriodTimeValueDataModel object to a corresponding PeriodTimeValue.
     *
     * @param factory        The factory used to create PeriodTimeValue instances.
     * @param valueDataModel The PeriodTimeValueDataModel to be converted.
     * @return Value object from domain.
     */
    public Value toDomain(ImpFactoryPeriodTimeValue factory, PeriodTimeValueDataModel valueDataModel) {
        try {
            ValueID valueID = new ValueID(valueDataModel.getValueID());

            SensorID sensorID = new SensorID(valueDataModel.getSensorID());
            Reading reading = new Reading(valueDataModel.getMeasurement(), valueDataModel.getUnit());
            Timestamp starTime = valueDataModel.getStartTime();
            Timestamp endTime = valueDataModel.getEndTime();

            return factory.createValue(valueID, sensorID, reading, starTime, endTime);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts a list of PeriodTimeValueDataModel objects to a list of corresponding PeriodTimeValue objects.
     *
     * @param factory         The factory used to create PeriodTimeValue instances.
     * @param valueDataModels Iterable of PeriodTimeValueDataModel objects to be converted.
     * @return Iterable of Value objects from domain.
     */
    public Iterable<Value> toDomainList(ImpFactoryPeriodTimeValue factory, Iterable<PeriodTimeValueDataModel> valueDataModels) {
        if (factory == null || valueDataModels == null) {
            return null;
        }

        List<Value> valueList = new ArrayList<>();
        for (PeriodTimeValueDataModel valueDataModel : valueDataModels) {
            PeriodTimeValue periodTimeValue = (PeriodTimeValue) toDomain(factory, valueDataModel);
            if (periodTimeValue != null) {
                valueList.add(periodTimeValue);
            }
        }
        return valueList;
    }

}
