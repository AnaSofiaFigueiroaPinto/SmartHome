package smarthome.persistence.jpa.datamodel;

import org.springframework.stereotype.Component;
import smarthome.domain.actuatorfunctionality.FactoryActuatorFunctionality;
import smarthome.domain.actuatorfunctionality.ImpFactoryActuatorFunctionality;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.FactoryActuator;
import smarthome.domain.repository.ActuatorFunctionalityRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.persistence.repositoriesmem.ActuatorFunctionalityRepositoryMem;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class to map ActuatorDataModel objects to Actuator domain objects
 */
@Component

public class MapperActuatorDataModel {

    /**
     * Mapper empty constructor
     */
    public MapperActuatorDataModel() {
    }

    /**
     * Maps an ActuatorDataModel from the persistence to an Actuator domain object
     *
     * @param factoryActuator   FactoryActuator object
     * @param actuatorDataModel ActuatorDataModel object
     * @return Actuator object from domain
     * @throws NullPointerException if the ActuatorDataModel object is null
     */
    public Actuator toDomain(FactoryActuator factoryActuator, ActuatorDataModel actuatorDataModel) {
        try {
            ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID(actuatorDataModel.getActuatorFunctionalityID());
            ActuatorProperties actuatorProperties = createActuatorProperties(actuatorDataModel);
            ActuatorID actuatorID = new ActuatorID(actuatorDataModel.getActuatorID());
            DeviceID deviceName = new DeviceID(actuatorDataModel.getDeviceID());
            FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();
            ActuatorFunctionalityRepository actuatorFunctionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);
            String actuatorClass = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID((actuatorFunctionalityID));
            return factoryActuator.createActuator(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceName, actuatorClass);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Creates an ActuatorProperties object from an ActuatorDataModel object
     *
     * @param actuatorDataModel ActuatorDataModel object
     * @return ActuatorProperties object or null if the limits are not valid
     */
    public ActuatorProperties createActuatorProperties(ActuatorDataModel actuatorDataModel) {
        if (actuatorDataModel.getUpperLimitDecimal() == null &&
                actuatorDataModel.getLowerLimitDecimal() == null &&
                actuatorDataModel.getLowerLimitInt() == null &&
                actuatorDataModel.getUpperLimitInt() == null &&
                actuatorDataModel.getPrecision() == null) {
            return new ActuatorProperties();
        }
        if (actuatorDataModel.getUpperLimitInt() == null &&
                actuatorDataModel.getLowerLimitInt() == null) {
            return new ActuatorProperties(
                    actuatorDataModel.getUpperLimitDecimal(),
                    actuatorDataModel.getLowerLimitDecimal(),
                    actuatorDataModel.getPrecision()
            );
        }
        if (actuatorDataModel.getUpperLimitDecimal() == null &&
                actuatorDataModel.getLowerLimitDecimal() == null && actuatorDataModel.getPrecision() == null){
            return new ActuatorProperties(
                    actuatorDataModel.getUpperLimitInt(),
                    actuatorDataModel.getLowerLimitInt()
            );
        } else {
            return null;
        }
    }

    /**
     * Maps a list of ActuatorDataModel objects to a list of Actuator domain objects
     *
     * @param factoryActuator    FactoryActuator object
     * @param actuatorDataModels Iterable of ActuatorDataModel objects
     * @return Iterable of Actuator objects from domain
     * @throws NullPointerException if the ActuatorDataModel object is null
     */
    public Iterable<Actuator> toDomainList(FactoryActuator factoryActuator, Iterable<ActuatorDataModel> actuatorDataModels) {
        if (factoryActuator == null || actuatorDataModels == null)
            return null;

        List<Actuator> actuators = new ArrayList<>();
        for (ActuatorDataModel actuatorDataModel : actuatorDataModels) {
            Actuator actuator = toDomain(factoryActuator, actuatorDataModel);
            if (actuator != null) {
                actuators.add(actuator);
            }
        }
        return actuators;
    }
}
