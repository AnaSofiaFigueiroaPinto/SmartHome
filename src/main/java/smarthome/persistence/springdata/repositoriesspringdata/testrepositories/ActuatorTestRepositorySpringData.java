package smarthome.persistence.springdata.repositoriesspringdata.testrepositories;

import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.FactoryActuator;
import smarthome.domain.repository.ActuatorRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ActuatorRepository implementation for Spring Data testing purposes.
 * This class provides a set of pre-defined Actuator objects for testing various repository methods.
 */
public class ActuatorTestRepositorySpringData implements ActuatorRepository {

	/**
	 * List of mock Actuator entities.
	 */
	private List<Actuator> actuators;

	/**
	 * Constructs a new ActuatorTestRepositorySpringData with the specified FactoryActuator.
	 */
	public ActuatorTestRepositorySpringData() {
		// Initialize Actuator objects for testing
		// (These Actuator objects can be used to simulate data in a test environment)
		// Note: This class is designed for testing purposes and does not interact with an actual database.
		this.actuators = new ArrayList<>();
		FactoryActuator factoryActuator = new FactoryActuator();
		populateActuators(factoryActuator);
	}

	/**
	 * Populates the list of Actuator objects with mock data.
	 */
	private void populateActuators (FactoryActuator factoryActuator) {
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator001"), new ActuatorFunctionalityID("DecimalSetter"), new ActuatorProperties(30.0, 10.0, 1), new DeviceID("Device001"), "smarthome.domain.actuators.DecimalSetterActuator"));
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator002"), new ActuatorFunctionalityID("Switch"), new ActuatorProperties(), new DeviceID("Device002"), "smarthome.domain.actuators.SwitchActuator"));
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator003"), new ActuatorFunctionalityID("BlindSetter"), new ActuatorProperties(), new DeviceID("Device003"), "smarthome.domain.actuators.BlindSetterActuator"));
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator004"), new ActuatorFunctionalityID("BlindSetter"), new ActuatorProperties(), new DeviceID("Device004"), "smarthome.domain.actuators.BlindSetterActuator"));
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator005"), new ActuatorFunctionalityID("DecimalSetter"), new ActuatorProperties(25.0, 16.0, 1), new DeviceID("Device005"), "smarthome.domain.actuators.DecimalSetterActuator"));
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator006"), new ActuatorFunctionalityID("Switch"), new ActuatorProperties(), new DeviceID("Device006"), "smarthome.domain.actuators.SwitchActuator"));
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator007"), new ActuatorFunctionalityID("DecimalSetter"), new ActuatorProperties(30.0, 10.0, 1), new DeviceID("Device007"), "smarthome.domain.actuators.DecimalSetterActuator"));
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator008"), new ActuatorFunctionalityID("Switch"), new ActuatorProperties(), new DeviceID("Device008"), "smarthome.domain.actuators.SwitchActuator"));
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator009"), new ActuatorFunctionalityID("BlindSetter"), new ActuatorProperties(), new DeviceID("Device009"), "smarthome.domain.actuators.BlindSetterActuator"));
		actuators.add(factoryActuator.createActuator(new ActuatorID("Actuator010"), new ActuatorFunctionalityID("DecimalSetter"), new ActuatorProperties(25.0, 16.0, 1), new DeviceID("Device010"), "smarthome.domain.actuators.DecimalSetterActuator"));
	}

	/**
	 * Saves an Actuator entity in the repository.
	 * @param actuator The Actuator entity to save.
	 * @return The saved Actuator entity.
	 */
	@Override
	public Actuator save(Actuator actuator) {
		actuators.add(actuator);
		return actuator;
	}

	/**
	 * Retrieves all Actuator entities with the specified ActuatorFunctionalityID.
	 * @param actuatorFunctionalityID The ActuatorFunctionalityID to search for.
	 * @return Iterable of Actuator entities with the specified ActuatorFunctionalityID.
	 */
	@Override
	public Iterable<Actuator> findByActuatorFunctionalityID(ActuatorFunctionalityID actuatorFunctionalityID) {
		List<Actuator> actuatorsWithFunctionality = new ArrayList<>();
		for (Actuator actuator : actuators) {
			if(actuatorFunctionalityID.equals(actuator.getActuatorFunctionalityID())) {
				actuatorsWithFunctionality.add(actuator);
			}
		}
		return actuatorsWithFunctionality;
	}

	/**
	 * Retrieves all Actuator entities with the specified DeviceID.
	 * @param deviceID The DeviceID to search for.
	 * @return Iterable of Actuator entities with the specified DeviceID.
	 */
	@Override
	public Iterable<Actuator> findByDeviceID(DeviceID deviceID) {
		List<Actuator> actuatorsOfDevice = new ArrayList<>();
		for (Actuator actuator : actuators) {
			if(deviceID.equals(actuator.getDeviceName())) {
				actuatorsOfDevice.add(actuator);
			}
		}
		return actuatorsOfDevice;
	}

	@Override
	public Iterable<Actuator> findByDeviceIDAndActuatorFunctionalityID(DeviceID deviceID, ActuatorFunctionalityID actuatorFunctionalityID) {
		List<Actuator> actuatorsOfDevice = new ArrayList<>();
		for (Actuator actuator : actuators) {
			if(deviceID.equals(actuator.getDeviceName()) && actuatorFunctionalityID.equals(actuator.getActuatorFunctionalityID())) {
				actuatorsOfDevice.add(actuator);
			}
		}
		return actuatorsOfDevice;
	}

	/**
	 * Retrieves the Actuator entity with the specified identity.
	 * @param id The unique identifier of the entity to find.
	 * @return An Optional with the Actuator entity if found, an empty Optional otherwise.
	 */
	@Override
	public Optional<Actuator> findEntityByID(ActuatorID id) {
		return actuators.stream()
				.filter(actuator -> id.equals(actuator.identity()))
				.findFirst();
	}

	/**
	 * Retrieves all Actuator entities from the repository.
	 * @return An Iterable containing all Actuator entities.
	 */
	@Override
	public Iterable<Actuator> findAllEntities() {
		return actuators;
	}

	/**
	 * Checks if the Actuator entity with the specified identity exists in the repository.
	 * @param id The unique identifier of the entity to check.
	 * @return True if the Actuator entity exists, false otherwise.
	 */
	@Override
	public boolean containsEntityByID(ActuatorID id) {
		return actuators.stream()
				.anyMatch(actuator -> id.equals(actuator.identity()));
	}
}
