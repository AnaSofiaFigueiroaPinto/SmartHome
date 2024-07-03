package smarthome.persistence.jpa.repositoriesjpa;


import jakarta.persistence.*;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.valueobjects.HouseID;
import smarthome.persistence.jpa.datamodel.HouseDataModel;
import smarthome.persistence.jpa.datamodel.MapperHouseDataModel;
import smarthome.util.EntityUpdater;
import smarthome.util.exceptions.SingleHouseViolationException;

import java.util.List;
import java.util.Optional;

/**
 * A class that implements the HouseRepository interface and provides methods to save and retrieve house objects from the database.
 */
public class HouseRepositoryJPAImp implements HouseRepository
{
    /**
     * The factory that creates house objects.
     */
    FactoryHouse factoryHouse;

    /**
     * The house data model used for updating houses.
     */
    HouseDataModel houseDataModelForUpdate;

    /**
     * Entity Manager instance for managing persistence operations.
     */
    EntityManager entityManager;

    /**
     * Constructor for HouseRepositoryJPAImp.
     *
     * @param factoryHouse the factory that creates house objects.
     * @param entityManager The Entity Manager instance for managing persistence operations.
     */
    public HouseRepositoryJPAImp(FactoryHouse factoryHouse, EntityManager entityManager)
    {
        this.factoryHouse = factoryHouse;
        this.entityManager = entityManager;
    }

    /**
     * Method that saves a house object to the database.
     *
     * @param house the house object to save.
     * @return the saved house object.
     */
    public House save(House house) {
        Long houseCount = entityManager.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)
                .getSingleResult();
        if(houseCount > 0) {
            throw new SingleHouseViolationException();
        }

        HouseDataModel houseDataModel = new HouseDataModel(house);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(houseDataModel);
        entityTransaction.commit();
        entityManager.close();

        return house;
    }

    /**
     * Method that updates a house object in the database.
     *
     * @param house the house object to update.
     * @return the updated house object.
     */

    @Override
    public House update(House house)
    {
        HouseDataModel houseDataModel = entityManager.find(HouseDataModel.class, house.identity().toString());
        return houseUpdater(house, houseDataModel);
    }

    /**
     * Retrieves the updated House object after updating its data from the corresponding HouseDataModel.
     * If the update is successful, the changes are persisted using EntityUpdater.
     *
     * @param house          The House object to be updated.
     * @param houseDataModel The corresponding HouseDataModel object containing updated data.
     * @return The updated House object if the update is successful and persisted, otherwise null.
     */
    private House houseUpdater(House house, HouseDataModel houseDataModel)
    {
        if (houseDataModel == null)
        {
            return null;
        }
        if (!houseDataModel.updateFromDomain(house))
        {
            return null;
        }
        // Utilize EntityUpdater for persistence
        EntityUpdater entityUpdater = new EntityUpdater();
        if (entityUpdater.persistEntity(houseDataModel, entityManager))
        {
            return house;
        }
        return null;
    }

        /**
         * Updates the last edited house object that was in cache to prevent another request to the database.
         *
         * @param house the house object to update.
         * @return the updated house object.
         */

        @Override
        public House updateReserved (House house)
        {
            return houseUpdater(house, houseDataModelForUpdate);
        }

        /**
         * Finds all House objects stored in the database.
         *
         * @return an Iterable containing all House objects stored in the database
         */
        @Override
        public Iterable<House> findAllEntities ()
        {
            Query query = entityManager.createQuery(
                    "SELECT e FROM HouseDataModel e"
            );
            List<HouseDataModel> listDataModel = query.getResultList();
            MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
            return mapperHouseDataModel.toDomainList(factoryHouse, listDataModel);
        }

        /**
         * Method that retrieves a house object from the database with the given id.
         *
         * @param id the id of the house
         * @return the house object with the specified id.
         */
        public Optional<House> findEntityByID (HouseID id)
        {
            HouseDataModel houseDataModel = entityManager.find(HouseDataModel.class, id.toString());

            if (houseDataModel != null) {
                MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
                House houseDomain = mapperHouseDataModel.toDomain(factoryHouse, houseDataModel);
                return Optional.of(houseDomain);
            } else {
                return Optional.empty();
            }
        }

        /**
         * Method that retrieves a house object from the database with the given id and reserves it for update.
         * @param houseID the id of the house
         * @return the house object with the specified id.
         */

        @Override
        public Optional<House> findEntityByIDAndReserve (HouseID houseID)
        {
            HouseDataModel houseDataModel = entityManager.find(HouseDataModel.class, houseID.toString());

            if (houseDataModel != null) {
                MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
                House houseDomain = mapperHouseDataModel.toDomain(factoryHouse, houseDataModel);

                this.houseDataModelForUpdate = houseDataModel;

                return Optional.of(houseDomain);
            } else {
                return Optional.empty();
            }
        }

        /**
         * Method that checks if a house object with the given id exists in the database.
         *
         * @param id the id of the house
         * @return true if the house object with the specified id exists, false otherwise.
         */
        public boolean containsEntityByID (HouseID id)
        {
            Optional<House> optionalHouse = findEntityByID(id);

            return optionalHouse.isPresent();
        }
    }