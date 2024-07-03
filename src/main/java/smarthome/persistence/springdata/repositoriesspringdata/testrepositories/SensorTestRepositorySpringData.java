package smarthome.persistence.springdata.repositoriesspringdata.testrepositories;

import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A test implementation of the SensorRepository interface for Spring Data testing purposes.
 * This class provides a set of pre-defined Sensor objects for testing various repository methods.
 */
public class SensorTestRepositorySpringData implements SensorRepository {

    /**
     * The List of mock Sensor entities.
     */
    private final List<Sensor> sensors;

    /**
     * Constructs a new SensorTestRepositorySpringData with the specified FactorySensor.
     */
    public SensorTestRepositorySpringData() {
        // Initialize Sensor objects for testing
        // (These Sensor objects can be used to simulate data in a test environment)
        // Note: This class is designed for testing purposes and does not interact with an actual database.
        this.sensors = new ArrayList<>();
        FactorySensor factorySensor = new FactorySensor();
        populateSensors(factorySensor);
    }

    private void populateSensors(FactorySensor factorySensor) {
        sensors.add(factorySensor.createSensor(new SensorID("Sensor001"), new DeviceID("Device001"), new SensorFunctionalityID("TemperatureCelsius"), "smarthome.domain.sensor.TemperatureCelsiusSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor002"), new DeviceID("Device002"), new SensorFunctionalityID("WindSpeedAndDirection"), "smarthome.domain.sensor.WindSpeedAndDirectionSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor003"), new DeviceID("Device003"), new SensorFunctionalityID("Sunrise"), "smarthome.domain.sensor.SunriseSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor004"), new DeviceID("Device004"), new SensorFunctionalityID("Sunset"), "smarthome.domain.sensor.SunsetSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor005"), new DeviceID("Device005"), new SensorFunctionalityID("ElectricEnergyConsumption"), "smarthome.domain.sensor.ElectricEnergyConsumptionSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor006"), new DeviceID("Device006"), new SensorFunctionalityID("PowerAverage"), "smarthome.domain.sensor.PowerAverageSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor007"), new DeviceID("Device007"), new SensorFunctionalityID("TemperatureCelsius"), "smarthome.domain.sensor.TemperatureCelsiusSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor008"), new DeviceID("Device008"), new SensorFunctionalityID("BinaryStatus"), "smarthome.domain.sensor.BinaryStatusSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor009"), new DeviceID("Device009"), new SensorFunctionalityID("BinaryStatus"), "smarthome.domain.sensor.BinaryStatusSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor010"), new DeviceID("Device010"), new SensorFunctionalityID("ElectricEnergyConsumption"), "smarthome.domain.sensor.ElectricEnergyConsumptionSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor011"), new DeviceID("Device009"), new SensorFunctionalityID("Scale"), "smarthome.domain.sensor.ScaleSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor012"), new DeviceID("Grid Power Meter"), new SensorFunctionalityID("PowerAverage"), "smarthome.domain.sensor.PowerAverageSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor013"), new DeviceID("Power Source 1"), new SensorFunctionalityID("SpecificTimePowerConsumption"), "smarthome.domain.sensor.SpecificTimePowerConsumptionSensor"));
        sensors.add(factorySensor.createSensor(new SensorID("Sensor014"), new DeviceID("Power Source 2"), new SensorFunctionalityID("SpecificTimePowerConsumption"), "smarthome.domain.sensor.SpecificTimePowerConsumptionSensor"));
    }

    /**
     * Saves a Sensor entity in the repository.
     * @param sensor The Sensor entity to save.
     * @return The saved Sensor entity.
     */
    @Override
    public Sensor save(Sensor sensor) {
        sensors.add(sensor);
        return sensor;
    }

    /**
     * Retrieves all Sensor entities from the repository.
     * @return An Iterable containing all Sensor entities.
     */
    @Override
    public Iterable<Sensor> findAllEntities() {
        return sensors;
    }

    /**
     * Retrieves a Sensor by its ID.
     * @param id The ID of the Sensor to retrieve.
     * @return An Optional containing the Sensor if found, or empty if not found.
     */
    @Override
    public Optional<Sensor> findEntityByID(SensorID id) {
        return sensors.stream()
                .filter(sensor -> id.equals(sensor.identity()))
                .findFirst();
    }

    /**
     * Checks if a Sensor with the specified ID exists.
     * @param id The ID of the Sensor to check.
     * @return True if the Sensor exists, otherwise false.
     */
    @Override
    public boolean containsEntityByID(SensorID id) {
        return sensors.stream()
                .anyMatch(sensor -> id.equals(sensor.identity()));
    }

    /**
     * Retrieves all Sensors associated with a specific DeviceID.
     * @param id The DeviceID to search for.
     * @return An Iterable containing all Sensors associated with the specified DeviceID.
     */
    @Override
    public Iterable<Sensor> findByDeviceID(DeviceID id) {
        List<Sensor> foundSensors = new ArrayList<>();
        for (Sensor sensor : sensors) {
            if (id.equals(sensor.getDeviceID())) {
                foundSensors.add(sensor);
            }
        }
        return foundSensors;
    }

    /**
     * Retrieves all Sensors associated with a specific SensorFunctionalityID.
     * @param id The SensorFunctionalityID to search for.
     * @return An Iterable containing all Sensors associated with the specified SensorFunctionalityID.
     */
    @Override
    public Iterable<Sensor> findBySensorFunctionality(SensorFunctionalityID id) {
        List<Sensor> foundSensors = new ArrayList<>();
        for (Sensor sensor : sensors) {
            if (id.equals(sensor.getSensorFunctionalityID())) {
                foundSensors.add(sensor);
            }
        }
        return foundSensors;
    }

    /**
     * Retrieves all Sensors associated with a specific DeviceID and SensorFunctionalityID.
     * @param deviceID The DeviceID to search for.
     * @param sensorFunctionalityID The SensorFunctionalityID to search for.
     * @return An Iterable containing all Sensors associated with the specified DeviceID and SensorFunctionalityID.
     */
    @Override
    public Iterable<Sensor> findByDeviceIDAndSensorFunctionality(DeviceID deviceID, SensorFunctionalityID sensorFunctionalityID) {
        List<Sensor> foundSensors = new ArrayList<>();
        for (Sensor sensor : sensors) {
            if (sensor.getDeviceID().equals(deviceID) && sensor.getSensorFunctionalityID().equals(sensorFunctionalityID)) {
                foundSensors.add(sensor);
            }
        }
        return foundSensors;
    }
}
