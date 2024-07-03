package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import smarthome.domain.value.ImpFactoryPeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.jpa.datamodel.MapperPeriodTimeValueDataModel;
import smarthome.persistence.jpa.datamodel.PeriodTimeValueDataModel;
import smarthome.persistence.repositoriesmem.PeriodTimeValueRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the PeriodTimeValueRepository using Spring Data.
 */
@Repository
@Profile("!test")
public class PeriodTimeValueRepositorySpringDataImp implements PeriodTimeValueRepository {

    /**
     * The repository for PeriodTimeValueDataModel instances.
     */
    PeriodTimeValueRepositorySpringData periodTimeValueRepositorySpringData;

    /**
     * The factory for creating value instances.
     */
    ImpFactoryPeriodTimeValue factoryValue;

    /**
     * The mapper for PeriodTimeValueDataModel.
     */
    MapperPeriodTimeValueDataModel mapperPeriodTimeValueDataModel;


    /**
     * Constructor for the ImpPeriodTimeValueRepositorySpringData class.
     *
     * @param periodTimeValueRepositorySpringData The repository for PeriodTimeValueDataModel instances.
     * @param factoryValue The factory for creating value instances.
     * @param mapperPeriodTimeValueDataModel The mapper for PeriodTimeValueDataModel.
     */
    public PeriodTimeValueRepositorySpringDataImp(
            PeriodTimeValueRepositorySpringData periodTimeValueRepositorySpringData,
            ImpFactoryPeriodTimeValue factoryValue,
            MapperPeriodTimeValueDataModel mapperPeriodTimeValueDataModel) {
        this.periodTimeValueRepositorySpringData = periodTimeValueRepositorySpringData;
        this.factoryValue = factoryValue;
        this.mapperPeriodTimeValueDataModel = mapperPeriodTimeValueDataModel;
    }

    /**
     * Retrieves a list of values associated with a given sensor ID.
     *
     * @param sensorID The sensor ID to search for.
     * @return A list of values associated with the sensor ID.
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        Iterable<PeriodTimeValueDataModel> lstPeriodTimeValueDataModel =
                this.periodTimeValueRepositorySpringData.findBySensorID(sensorID.toString());
        return (List<Value>) mapperPeriodTimeValueDataModel.toDomainList(factoryValue,
                lstPeriodTimeValueDataModel);
    }

    /**
     * Retrieves a list of values associated with a given sensor ID and that occurred
     * between the provided start and end timestamps.
     *
     * @param sensorID The sensor ID to search for.
     * @param start    The start timestamp of the period to search.
     * @param end      The end timestamp of the period to search.
     * @return A list of values associated with the sensor ID within the specified period.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end) {

        List<PeriodTimeValueDataModel> listPeriodTimeValueDataModel =
                this.periodTimeValueRepositorySpringData
                        .findBySensorIDAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
                                sensorID.toString(), start, end);
        return (List<Value>) mapperPeriodTimeValueDataModel.toDomainList(factoryValue,
                listPeriodTimeValueDataModel);
    }

    /**
     * Retrieves the value associated with the given ID.
     *
     * @param id The ID of the value to retrieve.
     * @return An optional containing the value, if found.
     */
    @Override
    public Optional<Value> findEntityByID(ValueID id) {
        Optional<PeriodTimeValueDataModel> optPeriodTimeValueDataModel =
                this.periodTimeValueRepositorySpringData.findById(id.toString());
        if (optPeriodTimeValueDataModel.isPresent()) {
            Value periodTimeValueDomain =
                    mapperPeriodTimeValueDataModel.toDomain(factoryValue,
                            optPeriodTimeValueDataModel.get());
            return Optional.of(periodTimeValueDomain);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all values stored in the repository.
     *
     * @return An iterable containing all values in the repository.
     */
    @Override
    public Iterable<Value> findAllEntities() {
        Iterable<PeriodTimeValueDataModel> lstPeriodTimeValueDataModel =
                this.periodTimeValueRepositorySpringData.findAll();

        return mapperPeriodTimeValueDataModel.toDomainList(factoryValue, lstPeriodTimeValueDataModel);
    }

    /**
     * Checks if the repository contains a value with the given ID.
     *
     * @param id The ID to check.
     * @return True if the repository contains the value, otherwise false.
     */
    @Override
    public boolean containsEntityByID(ValueID id) {
        return this.periodTimeValueRepositorySpringData.existsById(id.toString());
    }
}
