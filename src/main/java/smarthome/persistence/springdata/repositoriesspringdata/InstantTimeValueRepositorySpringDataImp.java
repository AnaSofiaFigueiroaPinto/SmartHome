package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import smarthome.domain.value.ImpFactoryInstantTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.jpa.datamodel.InstantTimeValueDataModel;
import smarthome.persistence.jpa.datamodel.MapperInstantTimeValueDataModel;
import smarthome.persistence.repositoriesmem.InstantTimeValueRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the InstantValueRepository interface using Spring Data JPA.
 */
@Repository
@Profile("!test")
public class InstantTimeValueRepositorySpringDataImp implements InstantTimeValueRepository {
    /**
     * The repository for accessing InstantTimeValue data managed by Spring Data.
     */
    InstantTimeValueRepositorySpringData repositorySpringData;

    /**
     * Factory for creating InstantTimeValue entities.
     */
    ImpFactoryInstantTimeValue factorySpringData;

    /**
     * Mapper for InstantTimeValueDataModel.
     */
    MapperInstantTimeValueDataModel mapperInstantTimeValueDataModel;

    /**
     * Constructs a new ImpRepositorySensorSpringData with the specified RepositoryInstantValueSpringData and ImpFactoryInstantTimeValue.
     *
     * @param repositorySpringData The Spring Data repository for InstantTimeValue entities.
     * @param factorySpringData    The factory for creating InstantTimeValue entities.
     * @param mapperInstantTimeValueDataModel The mapper for InstantTimeValueDataModel.
     */
    public InstantTimeValueRepositorySpringDataImp(
            InstantTimeValueRepositorySpringData repositorySpringData,
            ImpFactoryInstantTimeValue factorySpringData,
            MapperInstantTimeValueDataModel mapperInstantTimeValueDataModel
    ) {
        this.repositorySpringData = repositorySpringData;
        this.factorySpringData = factorySpringData;
        this.mapperInstantTimeValueDataModel = mapperInstantTimeValueDataModel;
    }

    /**
     * Retrieves a InstantTimeValue entity by its ID from the repository.
     *
     * @param id The ID of the InstantTimeValue entity to retrieve.
     * @return An Optional containing the retrieved InstantTimeValue entity, or empty if not found.
     */
    @Override
    public Optional<Value> findEntityByID(ValueID id) {
        Optional<InstantTimeValueDataModel> optInstantTimeDataModelSaved = this.repositorySpringData.findById(id.toString());

        if (optInstantTimeDataModelSaved.isPresent()) {
            Value instantValueDomain = mapperInstantTimeValueDataModel.toDomain(factorySpringData, optInstantTimeDataModelSaved.get());
            return Optional.of(instantValueDomain);
        } else
            return Optional.empty();
    }

    /**
     * Retrieves all InstantTimeValue entities from the repository.
     *
     * @return An Iterable containing all InstantTimeValue entities.
     */
    @Override
    public Iterable<Value> findAllEntities() {
        Iterable<InstantTimeValueDataModel> listInstantTimeDataModel = this.repositorySpringData.findAll();

        return mapperInstantTimeValueDataModel.toDomainList(factorySpringData, listInstantTimeDataModel);
    }

    /**
     * Checks if a InstantTimeValue entity with the specified ID exists in the repository.
     *
     * @param id The ID of the InstantTimeValue entity to check.
     * @return True if the InstantTimeValue entity exists, false otherwise.
     */
    @Override
    public boolean containsEntityByID(ValueID id) {
        return repositorySpringData.existsById(id.toString());
    }

    /**
     * Method that finds all the InstantTimeValue of a Sensor, taking the SensorID as a parameter.
     *
     * @param sensorID The ID of the Sensor to find the values for.
     * @return A List containing all values with the given sensor ID.
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        Iterable<InstantTimeValueDataModel> listInstantTimeDataModel =
                this.repositorySpringData.findBySensorID(sensorID.toString());

        return (List<Value>) mapperInstantTimeValueDataModel.toDomainList(factorySpringData, listInstantTimeDataModel);
    }

    /**
     * Method that finds all the InstantTimeValue of a Sensor measured between an interval, taking the SensorID as a parameter.
     *
     * @param sensorID SensorID object that the value belongs to.
     * @param start    Timestamp object that represents the start of the period.
     * @param end      Timestamp object that represents the end of the period.
     * @return A List containing all values with the given sensor ID.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end) {
        Iterable<InstantTimeValueDataModel> listInstantTimeDataModel =
                this.repositorySpringData.findBySensorIDAndAndInstantTimeBetween
                        (sensorID.toString(), start, end);

        return (List<Value>) mapperInstantTimeValueDataModel.toDomainList(factorySpringData, listInstantTimeDataModel);
    }

    /**
     * Retrieves the last value recorded according to instant time by sensorID.
     *
     * @param sensorID SensorID object that the value belongs to
     * @return the object value that was last recorded.
     */
    @Override
    public Value findLastValueRecorded(SensorID sensorID) {
        Optional<InstantTimeValueDataModel> optInstantTimeDataModel =
                this.repositorySpringData.findFirstBySensorIDOrderByInstantTimeDesc(sensorID.toString());
        InstantTimeValueDataModel valueDataModel = optInstantTimeDataModel.orElse(null);
        return mapperInstantTimeValueDataModel.toDomain(factorySpringData, valueDataModel);
    }
}
