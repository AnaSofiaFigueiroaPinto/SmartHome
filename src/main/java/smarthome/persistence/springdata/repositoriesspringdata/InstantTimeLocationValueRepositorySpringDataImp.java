package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import smarthome.domain.value.ImpFactoryInstantTimeLocationValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.jpa.datamodel.InstantTimeLocationValueDataModel;
import smarthome.persistence.jpa.datamodel.MapperInstantTimeLocationValueDataModel;
import smarthome.persistence.repositoriesmem.InstantTimeLocationValueRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of InstantTimeLocationValueRepository using Spring Data.
 */
@Repository
@Profile("!test")
public class InstantTimeLocationValueRepositorySpringDataImp implements InstantTimeLocationValueRepository {

    /**
     * The Spring Data repository.
     */
    InstantTimeLocationValueRepositorySpringData instantTimeLocationValueRepositorySpringData;

    /**
     * The factory for InstantTimeLocationValue.
     */
    ImpFactoryInstantTimeLocationValue factoryInstantTimeLocationValue;

    /**
     * The mapper for InstantTimeLocationValueDataModel.
     */
    MapperInstantTimeLocationValueDataModel mapperInstantTimeLocationValueDataModel;

    /**
     * Constructs a new ImpInstantTimeLocationValueRepositorySpringData.
     * @param instantTimeLocationValueRepositorySpringData The Spring Data repository.
     * @param factoryInstantTimeLocationValue The factory for InstantTimeLocationValue.
     * @param mapperInstantTimeLocationValueDataModel The mapper for InstantTimeLocationValueDataModel.
     */
    public InstantTimeLocationValueRepositorySpringDataImp(
            InstantTimeLocationValueRepositorySpringData instantTimeLocationValueRepositorySpringData,
            ImpFactoryInstantTimeLocationValue factoryInstantTimeLocationValue,
            MapperInstantTimeLocationValueDataModel mapperInstantTimeLocationValueDataModel)
    {
        this.instantTimeLocationValueRepositorySpringData = instantTimeLocationValueRepositorySpringData;
        this.factoryInstantTimeLocationValue = factoryInstantTimeLocationValue;
        this.mapperInstantTimeLocationValueDataModel = mapperInstantTimeLocationValueDataModel;
    }


    /**
     * Finds values by sensor ID.
     * @param sensorID The unique identifier of the sensor.
     * @return A list of Value objects.
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID)
    {
        Iterable<InstantTimeLocationValueDataModel> listDataModel =
                this.instantTimeLocationValueRepositorySpringData.findBySensorID(sensorID.toString());

        return (List<Value>) mapperInstantTimeLocationValueDataModel
                .toDomainList(factoryInstantTimeLocationValue, listDataModel);
    }

    /**
     * Finds values by sensor ID within a period of time.
     * @param sensorID The unique identifier of the sensor.
     * @param start The start timestamp of the period.
     * @param end The end timestamp of the period.
     * @return A list of Value objects.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end)
    {
        List<InstantTimeLocationValueDataModel> listDataModel =
                this.instantTimeLocationValueRepositorySpringData
                        .findBySensorIDAndInstantTimeBetween(sensorID.toString(), start, end);

        return (List<Value>) mapperInstantTimeLocationValueDataModel
                .toDomainList(factoryInstantTimeLocationValue, listDataModel);
    }

    /**
     * Finds a value by its ID.
     * @param id The ID of the value.
     * @return An Optional containing the Value if found, otherwise empty.
     */
    @Override
    public Optional<Value> findEntityByID(ValueID id)
    {
        Optional<InstantTimeLocationValueDataModel> optionalInstantTimeLocationValueDataModel =
                this.instantTimeLocationValueRepositorySpringData.findById(id.toString());

        if (optionalInstantTimeLocationValueDataModel.isPresent())
        {
            Value value = mapperInstantTimeLocationValueDataModel.toDomain(factoryInstantTimeLocationValue,
                                                                    optionalInstantTimeLocationValueDataModel.get());
            return Optional.of(value);
        }
        else
        {
            return Optional.empty();
        }
    }

    /**
     * Finds all values.
     * @return An Iterable of Value objects.
     */
    @Override
    public Iterable<Value> findAllEntities()
    {
        Iterable<InstantTimeLocationValueDataModel> lstInstantTimeLocationValueDataModel =
                this.instantTimeLocationValueRepositorySpringData.findAll();

        return mapperInstantTimeLocationValueDataModel.toDomainList(factoryInstantTimeLocationValue,
                lstInstantTimeLocationValueDataModel);
    }

    /**
     * Checks if a value exists by its ID.
     * @param id The ID of the value.
     * @return true if the value exists, otherwise false.
     */
    @Override
    public boolean containsEntityByID(ValueID id)
    {
        return instantTimeLocationValueRepositorySpringData.existsById(id.toString());
    }

    /**
     * Retrieves the last value recorded according to instant time by sensorID.
     *
     * @param sensorID SensorID object that the value belongs to
     * @return the object value that was last recorded.
     */
    @Override
    public Value findLastValueRecorded(SensorID sensorID) {
        Optional<InstantTimeLocationValueDataModel> optValueDataModel =
                this.instantTimeLocationValueRepositorySpringData.findFirstBySensorIDOrderByInstantTimeDesc(sensorID.toString());
        InstantTimeLocationValueDataModel valueDataModel = optValueDataModel.orElse(null);
        return mapperInstantTimeLocationValueDataModel.toDomain(factoryInstantTimeLocationValue, valueDataModel);
    }

}
