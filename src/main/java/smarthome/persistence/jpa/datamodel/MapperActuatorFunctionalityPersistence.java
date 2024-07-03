package smarthome.persistence.jpa.datamodel;

import smarthome.domain.actuatorfunctionality.ActuatorFunctionality;
import smarthome.domain.actuatorfunctionality.FactoryActuatorFunctionality;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;

/**
 * Mapper class that has the responsibility of converting String to ActuatorFunctionalityID value objects.
 */
public class MapperActuatorFunctionalityPersistence {
	/**
	 * ActuatorFunctionality factory.
	 */
	private FactoryActuatorFunctionality factory;

	/**
	 * Constructor of the MapperActuatorFunctionalityPersistence class.
	 * @param factory ActuatorFunctionality factory.
	 */
	public MapperActuatorFunctionalityPersistence(FactoryActuatorFunctionality factory) {
		this.factory = factory;
	}

	/**
	 * Method that converts a String to an ActuatorFunctionalityID object.
	 *
	 * @param actuatorFunctionalityString The String to be converted to ActuatorFunctionality object.
	 * @return The ActuatorFunctionalityID object. If the input is null, an exception is thrown.
	 */
	public ActuatorFunctionality toDomain(String actuatorFunctionalityString) {
		try {
			ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID(actuatorFunctionalityString);
			return factory.createActuatorFunctionality(actuatorFunctionalityID);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
