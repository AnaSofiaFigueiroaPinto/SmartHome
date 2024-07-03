package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.valueobjects.HouseID;
import smarthome.persistence.jpa.datamodel.HouseDataModel;
import smarthome.persistence.jpa.datamodel.MapperHouseDataModel;
import smarthome.util.exceptions.SingleHouseViolationException;

import java.util.List;
import java.util.Optional;

/**
 * The ImpHouseRepositorySpringData class is a repository for House entities that uses Spring Data.
 */
@Repository
@Profile("!test")
public class HouseRepositorySpringDataImp implements HouseRepository
{

    /**
     * The repository for accessing house data managed by Spring Data.
     */
    HouseRepositorySpringData houseRepositorySpringData;

    /**
     * Factory for creating House entities.
     */
    FactoryHouse factoryHouse;

    /**
     * The house data model for update.
     */
    HouseDataModel houseDataModelForUpdate;

    /**
     * Mapper House Data Model instance for managing persistence operations.
     */
    MapperHouseDataModel mapperHouseDataModel;

    /**
     * Constructs a new ImpHouseRepositorySpringData with the specified RepositoryHouseSpringData and FactoryHouse.
     *
     * @param factoryHouse The factory for creating House entities.
     * @param houseRepositorySpringData The repository for accessing house data managed by Spring Data.
     * @param mapperHouseDataModel The mapper for House Data Model.
     */
    public HouseRepositorySpringDataImp(FactoryHouse factoryHouse, HouseRepositorySpringData houseRepositorySpringData,
                                        MapperHouseDataModel mapperHouseDataModel)
    {
        this.factoryHouse = factoryHouse;
        this.houseRepositorySpringData = houseRepositorySpringData;
        this.mapperHouseDataModel = mapperHouseDataModel;
    }

    /**
     * Saves the specified house.
     * @param house The house to save.
     * @return The saved house.
     */
    @Override
    public House save(House house)
    {
        if(!this.houseRepositorySpringData.findAll().isEmpty())
            throw new SingleHouseViolationException();

        HouseDataModel houseDataModel = new HouseDataModel(house);
        this.houseRepositorySpringData.save(houseDataModel);
        return house;
    }

    /**
     * Updates the specified house.
     * @param house The house to update.
     * @return The updated house.
     */
    @Override
    public House update(House house) {
        Optional<HouseDataModel> houseDataModel = houseRepositorySpringData.findById(house.identity().toString());

        if (houseDataModel.isPresent()) {
            var houseDataModelExisting = houseDataModel.get();
            boolean isUpdated = houseDataModelExisting.updateFromDomain(house);

            if (isUpdated) {
                this.houseRepositorySpringData.save(houseDataModelExisting);
                return house;
            } else
                return null;
        } else
            return null;
    }

    /**
     * Updates a reserved House entity in the repository.
     * @param house The house to update.
     * @return The updated house.
     */

    @Override
    public House updateReserved(House house)
    {
        if (houseDataModelForUpdate != null)
        {
            boolean isUpdated = houseDataModelForUpdate.updateFromDomain(house);

            if (isUpdated) {
                this.houseRepositorySpringData.save(houseDataModelForUpdate);
                return house;
            } else
                return null;
        } else
            return null;
    }

    /**
     * Finds all House entities.
     * @return An Iterable containing all House entities.
     */
    @Override
    public Iterable<House> findAllEntities()
    {
        List<HouseDataModel> houseDataModelList = this.houseRepositorySpringData.findAll();
        return mapperHouseDataModel.toDomainList(factoryHouse, houseDataModelList);
    }

    /**
     * Retrieves a House entity by its ID from the repository.
     *
     * @param houseID The ID of the House entity to retrieve.
     * @return An Optional containing the retrieved House entity, or empty if not found.
     */

    @Override
    public Optional<House> findEntityByID(HouseID houseID)
    {
        Optional<HouseDataModel> houseDataModelSaved = this.houseRepositorySpringData.findById(houseID.toString());

        if (houseDataModelSaved.isPresent())
        {
            House houseDomain = mapperHouseDataModel.toDomain(factoryHouse, houseDataModelSaved.get());
            return Optional.of(houseDomain);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Retrieves a reserved House entity by its ID from the repository.
     *
     * @param houseID The ID of the House entity to retrieve.
     * @return An Optional containing the retrieved House entity, or empty if not found.
     */

    @Override
    public Optional<House> findEntityByIDAndReserve(HouseID houseID)
    {
        Optional<HouseDataModel> houseDataModelSaved = this.houseRepositorySpringData.findById(houseID.toString());

        if (houseDataModelSaved.isPresent())
        {
            House houseDomain = mapperHouseDataModel.toDomain(factoryHouse, houseDataModelSaved.get());
            this.houseDataModelForUpdate = houseDataModelSaved.get();

            return Optional.of(houseDomain);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Checks if a House entity with a specified ID exists in the repository.
     * @param houseID The ID of the House entity to check for.
     * @return True if the House entity exists, false otherwise.
     */

    @Override
    public boolean containsEntityByID(HouseID houseID)
    {
        return this.houseRepositorySpringData.existsById(houseID.toString());
    }
}