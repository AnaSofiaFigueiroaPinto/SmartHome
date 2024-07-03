package smarthome.persistence.jpa.repositoriesjpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import smarthome.domain.value.ImpFactoryPeriodTimeValue;
import smarthome.domain.value.PeriodTimeValue;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;
import smarthome.persistence.jpa.datamodel.MapperPeriodTimeValueDataModel;
import smarthome.persistence.jpa.datamodel.PeriodTimeValueDataModel;
import smarthome.persistence.repositoriesmem.PeriodTimeValueRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a {@code PeriodTimeValueRepositoryJPAImp} for persistence in JPA.
 */
public class PeriodTimeValueRepositoryJPAImp implements PeriodTimeValueRepository {

    /**
     * The factory that creates period time value objects.
     */
    ImpFactoryPeriodTimeValue factoryValue;

    /**
     * The entity manager instance.
     */

    EntityManager entityManager;

    /**
     * Constructs a new {@code PeriodTimeValueRepositoryJPAImp} with the specified factory value.
     *
     * @param factoryValue The factory value to create period time value objects.
     */

    public PeriodTimeValueRepositoryJPAImp(ImpFactoryPeriodTimeValue factoryValue, EntityManager entityManager) {
        this.factoryValue = factoryValue;
        this.entityManager = entityManager;
    }

    /**
     * Finds a Value object in the database by its identity.
     *
     * @param id the identity of the ValueID object to find.
     * @return an Optional containing the Value object if found, or empty otherwise.
     */

    @Override
    public Optional<Value> findEntityByID(ValueID id) {
        PeriodTimeValueDataModel periodTimeValueDataModel = entityManager.find(PeriodTimeValueDataModel.class, id.toString());
        MapperPeriodTimeValueDataModel mapper = new MapperPeriodTimeValueDataModel();
        PeriodTimeValue periodTimeValue = (PeriodTimeValue) mapper.toDomain(factoryValue, periodTimeValueDataModel);
        return Optional.ofNullable(periodTimeValue);
    }

    /**
     * Find all Value objects in the database.
     *
     * @return an Iterable containing all Value objects stored in the database
     */
    @Override
    public Iterable<Value> findAllEntities() {
        Query query = entityManager.createQuery(
                "SELECT e FROM PeriodTimeValueDataModel e"
        );
        List<PeriodTimeValueDataModel> listPeriodTimeValueDataModel = query.getResultList();
        MapperPeriodTimeValueDataModel mapper = new MapperPeriodTimeValueDataModel();
        return mapper.toDomainList(factoryValue, listPeriodTimeValueDataModel);
    }

    /**
     * Verifies if a Value object with the specified identity exists in the database.
     *
     * @return true if the Value object exists, false otherwise.
     */

    @Override
    public boolean containsEntityByID(ValueID id) {
        Optional<Value> optionalPeriodValue = findEntityByID(id);

        return optionalPeriodValue.isPresent();
    }

    /**
     * Method that finds all the Value objects of a specific Sensor, taking the SensorID as a parameter.
     *
     * @param sensorID The ID of the SensorID to find the sensors for.
     * @return A List containing all values for the given sensorID.
     */

    @Override
    public List<Value> findBySensorId(SensorID sensorID) {
        List<Value> values = new ArrayList<>();

        Query query = Objects.requireNonNull(entityManager).createQuery(
                "SELECT e FROM PeriodTimeValueDataModel e WHERE e.sensorID = :sensorID"
        );
        query.setParameter("sensorID", sensorID.toString());
        List<PeriodTimeValueDataModel> listDataModel = query.getResultList();

        MapperPeriodTimeValueDataModel mapper = new MapperPeriodTimeValueDataModel();

        for (PeriodTimeValueDataModel periodTimeValueDataModel : listDataModel) {
            PeriodTimeValue periodTimeValue = (PeriodTimeValue) mapper.toDomain(factoryValue, periodTimeValueDataModel);
            values.add(periodTimeValue);
        }

        return values;
    }

    /**
     * Method that finds all the Value objects of a Sensor, taking the SensorID as a parameter and a period of time.
     *
     * @param sensorID The ID of the SensorID to find the sensors for.
     * @param start    The start of the period of time.
     * @param end      The end of the period of time.
     * @return A List containing all values for the given sensorID.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end) {
        List<Value> values = new ArrayList<>();
        Query query = Objects.requireNonNull(entityManager).createQuery(
                "SELECT e FROM PeriodTimeValueDataModel e WHERE e.sensorID = :sensorID AND e.startTime > :start AND e.endTime < :end"
        );
        query.setParameter("sensorID", sensorID.toString());
        query.setParameter("start", start);
        query.setParameter("end", end);
        List<PeriodTimeValueDataModel> listDataModel = query.getResultList();
        MapperPeriodTimeValueDataModel mapper = new MapperPeriodTimeValueDataModel();

        for (PeriodTimeValueDataModel periodTimeValueDataModel : listDataModel) {
            PeriodTimeValue periodTimeValue = (PeriodTimeValue) mapper.toDomain(factoryValue, periodTimeValueDataModel);
            values.add(periodTimeValue);
        }
        return values;
    }
}