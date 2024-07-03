package smarthome.persistence.springdata.repositoriesspringdata.testrepositories;

import smarthome.domain.house.House;
import smarthome.domain.house.ImpFactoryHouse;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.valueobjects.Address;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;
import smarthome.util.exceptions.SingleHouseViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A test implementation of the HouseRepository interface for Spring Data testing purposes.
 * This class provides a set of pre-defined House objects for testing various repository methods.
 */
public class HouseTestRepositorySpringData implements HouseRepository {

    /**
     * The List of mock House entities.
     */
    private final List<House> houses;

    /**
     * The reserved house for updateReserved method.
     */
    private House reservedHouse;

    /**
     * Constructs a new HouseTestRepositorySpringData with the specified FactoryHouse.
     */
    public HouseTestRepositorySpringData()
    {
        this.houses = new ArrayList<>();
        this.reservedHouse = null;
        ImpFactoryHouse factoryHouse = new ImpFactoryHouse();
        populateHouses(factoryHouse);
    }

    /**
     * Populates the list of House objects with mock data.
     *
     * @param factoryHouse The factory to create the house.
     */
    private void populateHouses(ImpFactoryHouse factoryHouse)
    {
        houses.add(factoryHouse.createHouseWithOrWithoutLocation(new HouseID("House001") , new Location(new Address
                ("Rua do Amial", "123", "4200-123", "Porto", "Portugal"),
                new GPSCode(50.7958, -4.2596))));
    }

    /**
     * Saves the specified house.
     *
     * @param house The house object to save or update.
     * @return The saved house.
     */
    @Override
    public House save(House house)
    {
        if (!houses.isEmpty())
            throw new SingleHouseViolationException();

        houses.add(house);
        return house;
    }

    /**
     * Updates the given house in the repository.
     *
     * @param house The house object to update.
     * @return The updated house.
     */
    @Override
    public House update(House house) {
        Optional<House> optionalHouse = findEntityByID(house.identity());
        if (optionalHouse.isPresent()) {
            houses.remove(optionalHouse.get());
            houses.add(house);
            return house;
        }
        return null;
    }


    /**
     * Updates the last edited house object that was in cache to prevent another request to the database.
     *
     * @param house The house object to update.
     * @return The updated house.
     */
    @Override
    public House updateReserved(House house)
    {
        if (reservedHouse != null && reservedHouse
                .identity().equals(house.identity())) {
            houses.remove(reservedHouse);
            houses.add(house);
            return house;
        }
        return null;
    }

    /**
     * Returns a House entity by its ID and reserves it for further operations.
     *
     * @param houseID The unique identifier of the house.
     * @return An Optional containing the found house, or empty if not found.
     */
    @Override
    public Optional<House> findEntityByIDAndReserve(HouseID houseID)
    {
        Optional <House>  houseOptional = houses.stream()
                .filter(house -> houseID.equals(house.identity()))
                .findFirst();
        houseOptional.ifPresent(house -> reservedHouse = house);
        return houseOptional;
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
        return houses.stream()
                .filter(house -> houseID.equals(house.identity()))
                .findFirst();
    }

    /**
     * Retrieves all House entities from the repository.
     *
     * @return An Iterable containing all House entities.
     */
    @Override
    public Iterable<House> findAllEntities()
    {
        return houses;
    }

    /**
     * Checks if a House with the specified ID exists.
     *
     * @param houseID The ID of the House to check.
     * @return True if the House exists, otherwise false.
     */
    @Override
    public boolean containsEntityByID(HouseID houseID)
    {
        return houses.stream()
                .anyMatch(house -> houseID.equals(house.identity()));
    }

}
