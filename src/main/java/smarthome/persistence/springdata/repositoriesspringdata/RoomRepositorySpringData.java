package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.RoomDataModel;

public interface RoomRepositorySpringData extends JpaRepository<RoomDataModel, String>{

    /**
     * Method that finds all Room entities in a specified houseID using the JPARepository.
     *
     * @param houseID The houseID to search for.
     * @return an Iterable containing all rooms found for that houseID.
     */

    Iterable<RoomDataModel> findAllByHouseID (String houseID);
}
