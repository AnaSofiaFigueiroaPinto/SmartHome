package smarthome.persistence.repositoriesmem;

import smarthome.domain.house.House;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.valueobjects.HouseID;
import smarthome.util.exceptions.SingleHouseViolationException;

import java.util.Map;
import java.util.Optional;

/**
 * An implementation of a repository for managing House entities.
 */
public class HouseRepositoryMem implements HouseRepository
{

    /**
     * Map of HouseID and House objects.
     */
    private Map<HouseID, House> houseData;

    /**
     * The house entity used for updating a reserved house.
     */
    private House houseForUpdate;

    /**
     * Constructor for HouseRepository object. Currently, stands this way since there's no database for the time being.
     * @param houseData Map of HouseID and House objects.
     */
    public HouseRepositoryMem(Map<HouseID, House> houseData)
    {
        this.houseData = houseData;
    }

    /**
     * Saves the given entity in the repository.
     * @param entity The entity to save.
     * @return The saved entity.
     */
    @Override
    public House save(House entity) {
        if(houseData.size() > 0) {
            throw new SingleHouseViolationException();
        }
        houseData.put(entity.identity(), entity);
        return entity;
    }

    /**
     * Updates the provided house entity in the repository.
     *
     * @param house The house entity to be updated.
     * @return The updated house entity if it exists in the repository, otherwise returns null.
     */
    @Override
    public House update(House house)
    {
        if(houseData.containsKey(house.identity())){
            houseData.put(house.identity(), house);
            return house;
        }
        return null;
    }

    /**
     * Updates a reserved house with new information.
     *
     * @param house The house containing updated information.
     * @return The updated house entity if the update is successful,
     * null if the house is not reserved or null if the identity doesn't match.
     */
    @Override
    public House updateReserved(House house)
    {
        // Check if the repository contains the reserved house
        if (houseForUpdate != null)
        {
            // Check if they have the same identification
            if (houseForUpdate.identity() == house.identity()) {
                // Update the reserved house entity with the new information
                houseData.put(houseForUpdate.identity(), house);
                // Return the updated house
                return house;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Retrieves all house entities stored in the repository.
     *
     * @return An Iterable containing all the house entities.
     */
    @Override
    public Iterable<House> findAllEntities()
    {
        return houseData.values();
    }

    /**
     * Finds an entity in the repository by its unique identifier.
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */
    @Override
    public Optional<House> findEntityByID(HouseID id)
    {
        if (!containsEntityByID(id))
        {
            return Optional.empty();
        }
        return Optional.of(houseData.get(id));
    }

    /**
     * Checks whether an entity with the given identifier exists in the repository.
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
     */
    @Override
    public boolean containsEntityByID(HouseID id)
    {
        return houseData.containsKey(id);
    }

    /**
     * Finds a house entity by its ID and reserves it for further processing.
     *
     * @param houseID The ID of the house entity to find and reserve.
     * @return An Optional containing the reserved house entity if found, or empty if not found.
     */
    @Override
    public Optional<House> findEntityByIDAndReserve(HouseID houseID)
    {
        if (!containsEntityByID(houseID))
        {
            return Optional.empty();
        }
        this.houseForUpdate = houseData.get(houseID);
        return Optional.of(houseForUpdate);
    }
}
