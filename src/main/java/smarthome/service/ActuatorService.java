package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.FactoryActuator;
import smarthome.domain.device.Device;
import smarthome.domain.repository.ActuatorFunctionalityRepository;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.service.internaldto.InternalActuatorDTO;
import smarthome.util.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ActuatorService class provides methods to create and manage actuator.
 */
@Service
public class ActuatorService {

    /**
     * The factory responsible for creating actuators.
     */
    private FactoryActuator factoryActuator;

    /**
     * The repository of the actuator.
     */
    private ActuatorRepository actuatorRepo;

    /**
     * The repository of the device.
     */
    private DeviceRepository deviceRepo;

    /**
     * The repository of the actuatorFunctionalities.
     */
    private ActuatorFunctionalityRepository actuatorFunctionalityRepository;

    /**
     * Constructor of the ActuatorService class that receives the factoryActuator,
     * actuatorRepository and deviceRepository.
     *
     * @param factoryActuator the factory of the actuator.
     * @param actuatorRepo    the repository of the actuator.
     * @param deviceRepo      the repository of the device.
     * @param actuatorFunctionalityRepository the repository of the actuator functionalities.
     */

    public ActuatorService(
            FactoryActuator factoryActuator,
            ActuatorRepository actuatorRepo,
            DeviceRepository deviceRepo,
            ActuatorFunctionalityRepository actuatorFunctionalityRepository
    ) {
        this.factoryActuator = factoryActuator;
        this.actuatorRepo = actuatorRepo;
        this.deviceRepo = deviceRepo;
        this.actuatorFunctionalityRepository = actuatorFunctionalityRepository;
    }

    /**
     * Method that creates an actuator and saves it in the repository.
     *
     * @param actuatorFunctionalityID The functionality of the actuator.
     * @param actuatorID              The name of the actuator.
     * @param actuatorProperties      The properties of the actuator.
     * @param deviceID                The device to which the actuator belongs.
     * @return An ActuatorID representing the identifier of the created actuator if successful, otherwise null.
     */
    public ActuatorID createActuatorAndSave(
            ActuatorFunctionalityID actuatorFunctionalityID,
            ActuatorID actuatorID,
            ActuatorProperties actuatorProperties,
            DeviceID deviceID
    ) {
        //Find device with that ID
        Device device = deviceRepo.findEntityByID(deviceID).orElse(null);
        if (device == null)
            throw new DeviceNotFoundException();

        if (!device.isActive())
            throw new DeviceInactiveException();

        //Get String with actuator class for that sensorFunctionalityID
        String actuatorClass = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID((actuatorFunctionalityID));
        if (actuatorClass == null) {
            throw new ActuatorFunctionalityNotListedException();
        }

        //Call creation of actuator through factory class
        Actuator actuator = factoryActuator.createActuator(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceID, actuatorClass);

        //Verifies if the actuator already exists in the repository
        if (actuatorRepo.containsEntityByID(actuator.identity()))
            throw new ActuatorAlreadyExistsException();

        //Saves the created actuator and return true after creating and saving with success
        actuatorRepo.save(actuator);

        return actuator.identity();
    }

    /**
     * Method to check if an actuator exists, using its ID.
     * @param actuatorID unique identifier of the actuator.
     * @return true if the actuator exists, false otherwise.
     */
    public boolean checkIfActuatorExistsByID(ActuatorID actuatorID) {
        return actuatorRepo.containsEntityByID(actuatorID);
    }

    /**
     * Method to find the actuators by device ID.
     * @param deviceID the ID of the device to find the actuators for.
     * @return a list of ActuatorID representing the identifiers of the actuators found.
     */
    public List<ActuatorID> findActuatorsIDsOfADevice(DeviceID deviceID) {
        Iterable<Actuator> actuatorsList = actuatorRepo.findByDeviceID(deviceID);
        List<ActuatorID> actuatorIDS = new ArrayList<>();
        for (Actuator actuator : actuatorsList) {
            actuatorIDS.add(actuator.identity());
        }
        return actuatorIDS;
    }

    /**
     * Method to find the actuator information by its ID.
     *
     * @param actuatorID The unique identifier of the actuator.
     * @return InternalActuatorDTO object with the actuator information.
     */
    public InternalActuatorDTO findActuatorIDInfo(ActuatorID actuatorID) {
        Optional<Actuator> actuator = actuatorRepo.findEntityByID(actuatorID);
        if (actuator.isPresent()) {
            ActuatorFunctionalityID actuatorFunctionalityID = actuator.get().getActuatorFunctionalityID();
            ActuatorProperties actuatorProperties = actuator.get().getActuatorProperties();
            DeviceID deviceID = actuator.get().getDeviceName();
            return new InternalActuatorDTO(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceID);
        }
        else {
            throw new ActuatorNotFoundException();
        }
    }
}