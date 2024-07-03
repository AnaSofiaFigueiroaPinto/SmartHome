package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.device.Device;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.SensorFunctionalityRepository;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.service.internaldto.InternalSensorDTO;
import smarthome.util.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SensorService class provides methods to create and manage sensors.
 */
@Service
public class SensorService {
    /**
     * The factory responsible for creating sensors.
     */
    private FactorySensor factorySensor;

    /**
     * The repository of the sensor.
     */
    private SensorRepository sensorRepo;

    /**
     * The repository of the device.
     */
    private DeviceRepository deviceRepository;

    /**
     * The repository for Sensor Functionalities
     */
    private SensorFunctionalityRepository sensorFunctionalityRepository;

    /**
     * Constructor of SensorService objects
     *
     * @param factorySensor    valid instance of class used for creating sensors
     * @param sensorRepo       valid instance of class (repo) used for persisting instances of sensor
     * @param deviceRepository valid instance of class (repo) used for persisting instances of device
     * @param sensorFunctionalityRepository valid instance of class (repo) used for persisting instances of sensor functionalities
     */
    public SensorService(
            FactorySensor factorySensor,
            SensorRepository sensorRepo,
            DeviceRepository deviceRepository,
            SensorFunctionalityRepository sensorFunctionalityRepository
    ) {
        this.factorySensor = factorySensor;
        this.sensorRepo = sensorRepo;
        this.deviceRepository = deviceRepository;
        this.sensorFunctionalityRepository = sensorFunctionalityRepository;
    }


    /**
     * Creates a new Sensor object and saves it to the relevant repository (SensorRepository).
     *
     * @param deviceID              The unique identifier of the device to which the sensor should be added.
     * @param sensorFunctionalityID The unique identifier of the functionality of the sensor.
     * @param sensorID              The identifier of the new sensor.
     * @return A SensorID representing the identifier of the created sensor if successful.
     * @throws DeviceNotFoundException               If the specified device is not found in the repository.
     * @throws DeviceInactiveException               If the specified device is inactive.
     * @throws SensorFunctionalityNotListedException If the specified sensor functionality is not present in the list of available functionalities.
     * @throws SensorAlreadyExistsException          If the sensor already exists in the repository.
     */
    public SensorID createSensorAndSave(
            DeviceID deviceID,
            SensorFunctionalityID sensorFunctionalityID,
            SensorID sensorID
    ) {
        //Find device with that ID
        Device device = deviceRepository.findEntityByID(deviceID).orElse(null);
        if (device == null)
            throw new DeviceNotFoundException();

        if (!device.isActive())
            throw new DeviceInactiveException();

        //Get String with sensor class for that sensorFunctionalityID
        String sensorClass = sensorFunctionalityRepository.getClassNameForSensorFunctionalityID(sensorFunctionalityID);
        if (sensorClass == null) {
            throw new SensorFunctionalityNotListedException();
        }

        //Call creation of sensor through factory class
        Sensor sensor = factorySensor.createSensor(sensorID, deviceID, sensorFunctionalityID, sensorClass);

        //Verifies if the sensor already exists in the repository
        if (sensorRepo.containsEntityByID(sensor.identity()))
            throw new SensorAlreadyExistsException();

        //Saves the created sensor and return true after creating and saving with success
        sensorRepo.save(sensor);

        return sensor.identity();
    }


    /**
     * Method to find the sensors of a device with a chosen functionality.
     *
     * @param deviceID              The ID of the device to find the sensors for.
     * @param sensorFunctionalityID The Functionality to find the sensors for.
     * @return List of SensorID objects.
     */
    public List<SensorID> findSensorsIDOfADeviceBySensorFunctionality(DeviceID deviceID,
                                                                      SensorFunctionalityID sensorFunctionalityID) {
        Iterable<Sensor> sensorsFromDeviceAndFunctionality =
                sensorRepo.findByDeviceIDAndSensorFunctionality(deviceID, sensorFunctionalityID);
        List<SensorID> sensorIDs = new ArrayList<>();

        for (Sensor sensor : sensorsFromDeviceAndFunctionality) {
            sensorIDs.add(sensor.identity());
        }
        return sensorIDs;
    }

    /**
     * Method to find the sensors of a device.
     *
     * @param deviceID the ID of the device whose sensors' IDs are to be found
     * @return a list of SensorIDs of the sensors associated with the given DeviceID
     */
    public List<SensorID> findSensorsIDOfADevice(DeviceID deviceID) {
        Iterable<Sensor> sensorsOfDevice = sensorRepo.findByDeviceID(deviceID);
        List<SensorID> sensorIDs = new ArrayList<>();
        for (Sensor sensor : sensorsOfDevice) {
            sensorIDs.add(sensor.identity());
        }
        return sensorIDs;
    }

    /**
     * Retrieves the sensor information for a given sensor ID.
     *
     * @param sensorID the ID of the sensor to retrieve information for
     * @return an {@link InternalSensorDTO} containing the sensor ID, sensor functionality ID, and device ID
     * @throws SensorNotFoundException if the sensor with the given ID is not found
     */
    public InternalSensorDTO findSensorIDInfo(SensorID sensorID) {
        Optional<Sensor> sensor = sensorRepo.findEntityByID(sensorID);
        if (sensor.isPresent()) {
            SensorFunctionalityID sensorFunctionalityID = sensor.get().getSensorFunctionalityID();
            DeviceID deviceID = sensor.get().getDeviceID();
            return new InternalSensorDTO(sensorID, sensorFunctionalityID, deviceID);
        }
        else {
            throw new SensorNotFoundException();
        }
    }
}

