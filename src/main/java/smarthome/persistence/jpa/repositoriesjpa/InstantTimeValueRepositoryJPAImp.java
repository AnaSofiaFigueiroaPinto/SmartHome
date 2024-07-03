package smarthome.persistence.jpa.repositoriesjpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import smarthome.domain.value.ImpFactoryInstantTimeValue;
import smarthome.domain.value.InstantTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.jpa.datamodel.InstantTimeValueDataModel;
import smarthome.persistence.jpa.datamodel.MapperInstantTimeValueDataModel;
import smarthome.persistence.repositoriesmem.InstantTimeValueRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class to represent a repository for instant time values using JPA.
 */
public class InstantTimeValueRepositoryJPAImp implements InstantTimeValueRepository {
    /**
     * The factory for creating Value objects.
     */
    private ImpFactoryInstantTimeValue factory;

    /**
     * The EntityManager instance.
     */
    private EntityManager entityManager;

    /**
     * Constructs a new InstantTimeValueRepositoryJPAImp with the specified factory for creating value objects.
     *
     * @param factory       the factory for creating InstantTimeValue objects
     * @param entityManager the EntityManager instance
     */
    public InstantTimeValueRepositoryJPAImp(ImpFactoryInstantTimeValue factory, EntityManager entityManager) {
        this.factory = factory;
        this.entityManager = entityManager;
    }

    /**
     * Method that searches for values that belong to a specific sensor.
     *
     * @param sensorID SensorID object that the value belongs to
     * @return List of Value objects that belong to the specified sensor
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        List<Value> values = new ArrayList<>();

        Query query = entityManager.createQuery(
                "SELECT e FROM InstantTimeValueDataModel e WHERE e.sensorID = :sensorID");
        query.setParameter("sensorID", sensorID.toString());

        List<InstantTimeValueDataModel> listDataModel = query.getResultList();
        MapperInstantTimeValueDataModel mapperInstantTimeValueDataModel = new MapperInstantTimeValueDataModel();

        for (InstantTimeValueDataModel instantTimeValueDataModel : listDataModel) {
            InstantTimeValue value = (InstantTimeValue) mapperInstantTimeValueDataModel.toDomain(factory, instantTimeValueDataModel);
            values.add(value);
        }

        return values;
    }

    /**
     * Method that searches for values that belong to a specific sensor and are between a specific period of time.
     *
     * @param sensorID SensorID object that the value belongs to.
     * @param start    Timestamp object that represents the start of the period.
     * @param end      Timestamp object that represents the end of the period.
     * @return List of Value objects that belong to the specified sensor and are between the specified period.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end) {
        List<Value> values = new ArrayList<>();

        Query query = entityManager.createQuery(
                "SELECT e FROM InstantTimeValueDataModel e WHERE e.sensorID = :sensorID AND e.instantTime BETWEEN :start AND :end");

        query.setParameter("sensorID", sensorID.toString());
        query.setParameter("start", start);
        query.setParameter("end", end);

        List<InstantTimeValueDataModel> listDataModel = query.getResultList();
        MapperInstantTimeValueDataModel mapperInstantTimeValueDataModel = new MapperInstantTimeValueDataModel();

        for (InstantTimeValueDataModel instantTimeValueDataModel : listDataModel) {
            InstantTimeValue value = (InstantTimeValue) mapperInstantTimeValueDataModel.toDomain(factory, instantTimeValueDataModel);
            values.add(value);
        }

        return values;
    }

    /**
     * Finds an InstantTimeValue object in the database by its identity.
     *
     * @param id the unique identifier of the value object to find
     * @return an Optional containing the value object if found, or empty otherwise
     */
    @Override
    public Optional<Value> findEntityByID(ValueID id) {
        InstantTimeValueDataModel valueDataModel = entityManager.find(InstantTimeValueDataModel.class, id.toString());
        MapperInstantTimeValueDataModel mapperInstantTimeValueDataModel = new MapperInstantTimeValueDataModel();
        InstantTimeValue instantTimeValue = (InstantTimeValue) mapperInstantTimeValueDataModel.toDomain(factory, valueDataModel);

        return Optional.ofNullable(instantTimeValue);
    }

    /**
     * Finds all InstantTimeValue objects stored in the database.
     *
     * @return an Iterable containing all InstantTimeValue objects stored in the database.
     */
    @Override
    public Iterable<Value> findAllEntities() {
        Query query = entityManager.createQuery(
                "SELECT e FROM InstantTimeValueDataModel e");

        List<InstantTimeValueDataModel> listDataModel = query.getResultList();
        MapperInstantTimeValueDataModel mapperInstantTimeValueDataModel = new MapperInstantTimeValueDataModel();

        return mapperInstantTimeValueDataModel.toDomainList(factory, listDataModel);
    }

    /**
     * Checks if an InstantTimeValue object with the specified identity exists in the database.
     *
     * @param id the unique identifier of the value object to check
     * @return true if a value object with the specified identity exists, false otherwise
     */
    @Override
    public boolean containsEntityByID(ValueID id) {
        Optional<Value> optionalInstantTimeValue = findEntityByID(id);
        return optionalInstantTimeValue.isPresent();
    }

    /**
     * Retrieves the last value recorded according to instant time by sensorID.
     *
     * @param sensorID SensorID object that the value belongs to
     * @return the object value that was last recorded.
     */
    @Override
    public Value findLastValueRecorded(SensorID sensorID) {
        Query query = entityManager.createQuery(
                "SELECT e FROM InstantTimeValueDataModel e WHERE e.sensorID = :sensorID ORDER BY e.instantTime DESC");

        query.setParameter("sensorID", sensorID.toString());
        query.setMaxResults(1);

        InstantTimeValueDataModel valueDataModel = (InstantTimeValueDataModel) query.getResultList().stream().findFirst().orElse(null);

        MapperInstantTimeValueDataModel mapperInstantTimeValueDataModel = new MapperInstantTimeValueDataModel();
        return mapperInstantTimeValueDataModel.toDomain(factory, valueDataModel);
    }
}
