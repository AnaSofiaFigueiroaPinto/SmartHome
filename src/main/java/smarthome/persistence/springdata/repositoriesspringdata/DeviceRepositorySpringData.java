package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.DeviceDataModel;

public interface DeviceRepositorySpringData extends JpaRepository<DeviceDataModel, String> {
    /**
     * Method that finds all Device entities in a specified roomID using the JPARepository.
     *
     * @param roomID The String of roomID to search for.
     * @return an Iterable containing all devices found for that roomID.
     */
    Iterable<DeviceDataModel> findAllByRoomID(String roomID);
}
