package smarthome.controllerweb;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.AssemblerActuatorProperties;
import smarthome.service.ActuatorService;
import smarthome.service.internaldto.InternalActuatorDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/actuators")
public class ActuatorControllerWeb {

	/**
	 * ActuatorService instance to interact with for actuator-related operations.
	 */
	private ActuatorService actuatorService;

	/**
	 * ActuatorPropertiesMapperDTO instance to convert ActuatorProperties domain to DTO.
	 */
	private AssemblerActuatorProperties assemblerActuatorProperties;

	/**
	 * Constructs a new ActuatorWebController with the specified services.
	 *
	 * @param actuatorService The ActuatorService instance for actuator-related operations.
	 * @param assemblerActuatorProperties The Assembler Actuator Properties instance for handling Actuator properties conversion.
	 */
	public ActuatorControllerWeb(ActuatorService actuatorService, AssemblerActuatorProperties assemblerActuatorProperties) {
		this.actuatorService = actuatorService;
		this.assemblerActuatorProperties = assemblerActuatorProperties;
	}

	/**
	 * Method to add a new actuator to a device. The actuatorDTO object is passed as a request body.
	 * The actuatorDTO object is then converted to Actuator domain object and saved in the database.
	 *
	 * @param actuatorDTO The ActuatorDTO object to be added.
	 * @return ResponseEntity with the created ActuatorDTO object and HttpStatus.CREATED if the actuator is created successfully.
	 * ResponseEntity with the error message and HttpStatus.NOT_FOUND if the actuator is not created successfully.
	 */
	@PostMapping("")
	public ResponseEntity<Object> addNewActuatorToDevice(@RequestBody ActuatorDTO actuatorDTO) {
		try {
			ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID(actuatorDTO.actuatorFunctionalityName);
			ActuatorID actuatorID = new ActuatorID(actuatorDTO.actuatorName);
			DeviceID deviceID = new DeviceID(actuatorDTO.deviceName);

			ActuatorProperties actuatorProperties = assemblerActuatorProperties.createActuatorPropertiesFromActuatorDTO(actuatorDTO);

			ActuatorID createdActuatorID = actuatorService.createActuatorAndSave(actuatorFunctionalityID, actuatorID, actuatorProperties, deviceID);

			ActuatorDTO createdActuatorDTO = new ActuatorDTO(createdActuatorID.toString(), actuatorDTO.actuatorFunctionalityName, actuatorDTO.deviceName, actuatorDTO.upperLimitInt, actuatorDTO.lowerLimitInt, actuatorDTO.upperLimitDecimal, actuatorDTO.lowerLimitDecimal, actuatorDTO.precision);

			Link selfLink = linkTo(ActuatorControllerWeb.class).slash(createdActuatorDTO.actuatorName).withSelfRel();
			createdActuatorDTO.add(selfLink);

			return new ResponseEntity<>(createdActuatorDTO, HttpStatus.CREATED);

		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * Method to get an actuator by its unique identifier. The actuatorID is passed as a path variable.
	 *
	 * @param actuatorID The unique identifier of the actuator.
	 * @return ResponseEntity with the ActuatorDTO object and HttpStatus.OK if the actuator is found.
	 *         ResponseEntity with HttpStatus.NOT_FOUND if the actuator is not found.
	 *         ResponseEntity with the error message and HttpStatus.UNPROCESSABLE_ENTITY if the actuatorID is invalid.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getActuatorByID(@PathVariable ("id") String actuatorID) {
		try {
			ActuatorID chosenActuatorID = new ActuatorID(actuatorID);

			if (actuatorService.checkIfActuatorExistsByID(chosenActuatorID)) {
				InternalActuatorDTO internalActuatorDTO = actuatorService.findActuatorIDInfo(chosenActuatorID);
				String actuatorFunctionalityID = internalActuatorDTO.actuatorFunctionalityID.toString();
				double upperLimitDecimal = internalActuatorDTO.actuatorProperties.getRangeDecimal().getUpperLimitDecimal();
				double lowerLimitDecimal = internalActuatorDTO.actuatorProperties.getRangeDecimal().getLowerLimitDecimal();
				int precision = internalActuatorDTO.actuatorProperties.getRangeDecimal().getPrecision();
				String deviceID = internalActuatorDTO.deviceID.toString();

				ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorID, actuatorFunctionalityID, deviceID, upperLimitDecimal, lowerLimitDecimal, precision);

				Link selfLink = linkTo(ActuatorControllerWeb.class).slash(actuatorDTO.actuatorName).withSelfRel();
				actuatorDTO.add(selfLink);

				return new ResponseEntity<>(actuatorDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}