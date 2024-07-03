package smarthome.domain.repository;

import smarthome.ddd.Repository;
import smarthome.domain.house.House;
import smarthome.domain.valueobjects.HouseID;

import java.util.Optional;

/**
 * An interface that extends Repository interface. This interface contains method's signatures to save and retrieve house objects.
 */
public interface HouseRepository extends Repository<HouseID, House> {

	/**
	 * Saves the given entity in the repository.
	 * @param house The house object to save or update.
	 * @return      The saved or updated house.
	 */
	House save(House house);

	/**
	 * Updates the given house in the repository.
	 * @param house The house object to update.
	 * @return      The updated house.
	 */

	House update(House house);

	/**
	 * Updates the last edited house object that was in cache to prevent another request to the database.
	 * @param house The house object to update.
	 * @return      The updated house.
	 */
	House updateReserved(House house);

	/**
	 * Returns the house ID of the last consulted house object.
	 * @param houseID The unique identifier of the house.
	 * @return        An Optional containing the found house, or empty if not found.
	 */
	Optional<House> findEntityByIDAndReserve(HouseID houseID);
}
