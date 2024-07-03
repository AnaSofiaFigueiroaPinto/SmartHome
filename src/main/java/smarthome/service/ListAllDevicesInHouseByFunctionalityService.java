package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.device.Device;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.domain.valueobjects.SensorFunctionalityID;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for listing all devices in house grouped by functionality.
 */
@Service
public class ListAllDevicesInHouseByFunctionalityService {

    /**
     * Repository used for storing device objects.
     */
    private DeviceRepository deviceRepository;

    /**
     * Repository used for storing Actuator objects.
     */
    private ActuatorRepository actuatorRepository;

    /**
     * Repository used for storing Sensor objects.
     */
    private SensorRepository sensorRepository;


    /**
     * Constructor for ListAllDevicesInHouseByFunctionalityService object.
     *
     * @param deviceRepository Repository for persisting Device objects.
     * @param sensorRepository Repository for persisting Sensor objects.
     * @param actuatorRepository Repository for persisting Actuator objects.
     */
    public ListAllDevicesInHouseByFunctionalityService(DeviceRepository deviceRepository,
                                                       SensorRepository sensorRepository,
                                                       ActuatorRepository actuatorRepository) {
        this.sensorRepository = sensorRepository;
        this.deviceRepository = deviceRepository;
        this.actuatorRepository = actuatorRepository;
    }

    /**
     * Method that groups all functionalities (ActuatorFunctionality and SensorFunctionality) present in House by RoomID and DeviceID.
     * @return Map<Object, Map<RoomID, DeviceID>> Object as key (ActuatorFunctionalityID or SensorFunctionalityID), Map<RoomID, DeviceID> as value.
     * Map may be empty if nothing is found.
     */
    public Map<Object, Map<RoomID, DeviceID>> devicesGroupedByFunctionalityAndLocation () {
        Map<Object, Map<RoomID, DeviceID>> mainMap = new HashMap<>();

        Map<SensorFunctionalityID, Map<RoomID, DeviceID>> sensorFunctionalityMap = sensorFunctionalityMap();
        Map<ActuatorFunctionalityID, Map<RoomID, DeviceID>> actuatorFunctionalityMap = actuatorFunctionalityMap();

        mainMap.putAll(sensorFunctionalityMap);

        mainMap.putAll(actuatorFunctionalityMap);

        return mainMap;
    }

    /**
     * Aux method to group all Sensors by SensorFunctionalityID, RoomID and DeviceID.
     * Method retrieves all Sensors from repository and iterates through each of them.
     * For each Sensor, it extracts associated SensorFunctionalityID, DeviceID and RoomID associated with extracted DeviceID.
     * Using computeIfAbsent method (to avoid lengthy if-else nesting needed to avoid overwrites):
     *      1 Checks if SensorFunctionalityID is already in the mainMap;
     *      2 If not, places SensorFunctionalityID as key creates new subMap (new HashMap<>()) and places it as value and moves onto (4). If not moves to (4);
     *      4 Puts to the subMap in the Value field the RoomID as key and DeviceID as value.
     *
     * @return Map<SensorFunctionalityID, Map<RoomID, DeviceID>> Map (mainMap) with SensorFunctionalityID as key, Map<RoomID, DeviceID> (subMap) as value.
     */
    private Map<SensorFunctionalityID, Map<RoomID, DeviceID>> sensorFunctionalityMap () {
        Map<SensorFunctionalityID, Map<RoomID, DeviceID>> mainSensorMap = new HashMap<>();

        Iterable<Sensor> sensors = sensorRepository.findAllEntities();
        for (Sensor sensor : sensors) {

            SensorFunctionalityID sensorFunctionalityID = sensor.getSensorFunctionalityID();
            DeviceID deviceID = sensor.getDeviceID();
            Optional<Device> deviceOptional = deviceRepository.findEntityByID(deviceID);
            boolean present = deviceOptional.isPresent();
            if (present) {
                RoomID roomID = deviceOptional.get().getRoomID();

                mainSensorMap
                        .computeIfAbsent(sensorFunctionalityID, k -> new HashMap<>())
                        .put(roomID, deviceID);
            }
        }
        return mainSensorMap;
    }

    /**
     * Aux method to group all Sensors by ActuatorFunctionalityID, RoomID and DeviceID.
     * Method retrieves all Actuators from repository and iterates through each of them.
     * For each Actuator, it extracts associated ActuatorFunctionalityID, DeviceID and RoomID associated with extracted DeviceID.
     * Using computeIfAbsent method (to avoid lengthy if-else nesting needed to avoid overwrites):
     *      1 Checks if ActuatorFunctionalityID is already in the mainMap;
     *      2 If not, places ActuatorFunctionalityID as key creates new subMap (new HashMap<>()) and places it as value and moves onto (4). If yes moves to (4);
     *      4 Puts to the subMap in the Value field the RoomID as key and DeviceID as value.
     *
     * @return Map<ActuatorFunctionalityID, Map<RoomID, DeviceID>> Map (mainMap) with ActuatorFunctionalityID as key, Map<RoomID, DeviceID> (subMap) as value.
     */
    private Map<ActuatorFunctionalityID, Map<RoomID, DeviceID>> actuatorFunctionalityMap () {
        Map<ActuatorFunctionalityID, Map<RoomID, DeviceID>> mainActuatorMap = new HashMap<>();

        Iterable<Actuator> actuators = actuatorRepository.findAllEntities();
        for (Actuator actuator : actuators) {

            ActuatorFunctionalityID actuatorFunctionalityID = actuator.getActuatorFunctionalityID();
            DeviceID deviceID = actuator.getDeviceName();
            Optional<Device> deviceOptional = deviceRepository.findEntityByID(deviceID);
            boolean present = deviceOptional.isPresent();
            if (present) {
                RoomID roomID = deviceOptional.get().getRoomID();

                mainActuatorMap
                        .computeIfAbsent(actuatorFunctionalityID, k -> new HashMap<>())
                        .put(roomID, deviceID);
            }
        }
        return mainActuatorMap;
    }
}
