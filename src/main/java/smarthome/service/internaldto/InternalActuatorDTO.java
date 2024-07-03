package smarthome.service.internaldto;

import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;

/**
 * InternalActuatorDTO is a Data Transfer Object (DTO) used for internal communication
 * within the application. It encapsulates the information related to an actuator, including
 * its ID, functionality ID, properties and the device ID to which it is associated.
 */
public class InternalActuatorDTO {

	/**
	 * The unique identifier of the actuator.
	 */
	public ActuatorID actuatorID;

	/**
	 * The unique identifier of the actuator functionality.
	 */
	public ActuatorFunctionalityID actuatorFunctionalityID;

	/**
	 * The properties associated with the actuator.
	 */
	public ActuatorProperties actuatorProperties;

	/**
	 * The unique identifier of the device associated with the sensor.
	 */
	public DeviceID deviceID;

	/**
	 * Constructs a new InternalActuatorDTO with the specified actuator ID, actuator functionality ID,
	 * actuator properties, and device ID.
	 *
	 * @param actuatorID The unique identifier of the actuator.
	 * @param actuatorFunctionalityID The unique identifier of the actuator functionality.
	 * @param actuatorProperties The properties associated with the actuator.
	 * @param deviceID The unique identifier of the device associated with the actuator.
	 */
	public InternalActuatorDTO(ActuatorID actuatorID, ActuatorFunctionalityID actuatorFunctionalityID, ActuatorProperties actuatorProperties, DeviceID deviceID) {
		this.actuatorID = actuatorID;
		this.actuatorFunctionalityID = actuatorFunctionalityID;
		this.actuatorProperties = actuatorProperties;
		this.deviceID = deviceID;
	}
}
