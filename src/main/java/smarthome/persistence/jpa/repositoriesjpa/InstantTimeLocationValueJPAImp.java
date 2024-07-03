package smarthome.persistence.jpa.repositoriesjpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
 * Class responsible for the instantiation of the repository of InstantTimeLocationValue.
 */
public class InstantTimeLocationValueJPAImp implements InstantTimeLocationValueRepository
{
    /**
     * ImpFactoryInstantTimeLocationValue object.
     */
    ImpFactoryInstantTimeLocationValue impFactoryInstantTimeLocationValue;

    /**
     * Entity Manager instance for managing persistence operations.
     */
    EntityManager entityManager;


    /**
     * Instantiates a new InstantTimeLocationValueJPAImp.
     * @param impFactoryInstantTimeLocationValue The ImpFactoryInstantTimeLocationValue object.
     * @param entityManager The EntityManager object.
     */
    public InstantTimeLocationValueJPAImp(
            ImpFactoryInstantTimeLocationValue impFactoryInstantTimeLocationValue,
            EntityManager entityManager)
    {
        this.impFactoryInstantTimeLocationValue = impFactoryInstantTimeLocationValue;
        this.entityManager = entityManager;
    }


    /**
     * Finds an entity by ID.
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the Value if found, otherwise empty.
     */
    @Override
    public Optional<Value> findEntityByID(ValueID id)
    {
        InstantTimeLocationValueDataModel instantTimeLocationValueDataModel =
                entityManager.find(InstantTimeLocationValueDataModel.class, id.toString());
        if (instantTimeLocationValueDataModel == null)
            return Optional.empty();
        MapperInstantTimeLocationValueDataModel mapperInstantTimeLocationValueDataModel =
                                                new MapperInstantTimeLocationValueDataModel();
        Value value = mapperInstantTimeLocationValueDataModel.toDomain(impFactoryInstantTimeLocationValue,
                                                                        instantTimeLocationValueDataModel);
        return Optional.of(value);
    }

    /**
     * Finds all entities.
     * @return An Iterable of Value.
     */
    @Override
    public Iterable<Value> findAllEntities()
    {
        Query query = entityManager.createQuery(
                "SELECT e FROM InstantTimeValueDataModel e"
        );
        List<InstantTimeLocationValueDataModel> listDataModel = query.getResultList();

        MapperInstantTimeLocationValueDataModel mapperInstantTimeLocationValueDataModel =
                                            new MapperInstantTimeLocationValueDataModel();
        return mapperInstantTimeLocationValueDataModel.toDomainList(impFactoryInstantTimeLocationValue, listDataModel);
    }

    /**
     * Checks if an entity exists by ID.
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists, otherwise false.
     */
    @Override
    public boolean containsEntityByID(ValueID id)
    {
        Optional<Value> optionalValue = findEntityByID(id);

        return optionalValue.isPresent();

    }

    /**
     * Finds values by sensor ID.
     * @param sensorID The unique identifier of the sensor.
     * @return A List of Value.
     */
    @Override
    public List<Value> findBySensorId(SensorID sensorID)
    {
        Query query = entityManager.createQuery(
                "SELECT e FROM InstantTimeValueDataModel e WHERE e.sensorID = :sensorID"
        );
        query.setParameter("sensorID", sensorID);
        List<InstantTimeLocationValueDataModel> listDataModel = query.getResultList();

        MapperInstantTimeLocationValueDataModel mapperInstantTimeLocationValueDataModel =
                                            new MapperInstantTimeLocationValueDataModel();
        return (List<Value>) mapperInstantTimeLocationValueDataModel.toDomainList(impFactoryInstantTimeLocationValue,
                                                                                  listDataModel);

    }

    /**
     * Finds values by sensor ID within a period of time.
     * @param sensorID  The SensorID object that the value belongs to.
     * @param start     The Timestamp object that represents the start of the period.
     * @param end       The Timestamp object that represents the end of the period.
     * @return A List of Value.
     */
    @Override
    public List<Value> findBySensorIdBetweenPeriodOfTime(SensorID sensorID, Timestamp start, Timestamp end)
    {
        Query query = entityManager.createQuery(
                "SELECT e FROM InstantTimeValueDataModel e WHERE e.sensorID =" +
                        " :sensorID AND e.instantTime BETWEEN :start AND :end"
        );
        query.setParameter("sensorID", sensorID);
        query.setParameter("start", start);
        query.setParameter("end", end);
        List<InstantTimeLocationValueDataModel> listDataModel = query.getResultList();
        entityManager.close();

        MapperInstantTimeLocationValueDataModel mapper = new MapperInstantTimeLocationValueDataModel();
        return (List<Value>) mapper.toDomainList(impFactoryInstantTimeLocationValue, listDataModel);
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
                "SELECT e FROM InstantTimeLocationValueDataModel e WHERE e.sensorID = :sensorID ORDER BY e.instantTime DESC");

        query.setParameter("sensorID", sensorID.toString());
        query.setMaxResults(1);

        InstantTimeLocationValueDataModel valueDataModel = (InstantTimeLocationValueDataModel) query.getResultList().stream().findFirst().orElse(null);

        MapperInstantTimeLocationValueDataModel mapper = new MapperInstantTimeLocationValueDataModel();
        return mapper.toDomain(impFactoryInstantTimeLocationValue, valueDataModel);
    }

}
