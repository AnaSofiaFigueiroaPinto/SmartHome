package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.HouseDataModel;

public interface HouseRepositorySpringData extends JpaRepository<HouseDataModel, String> {

}
