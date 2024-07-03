package smarthome.controllerweb;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.mapper.SensorDTO;
import smarthome.service.SensorService;
import smarthome.service.internaldto.InternalSensorDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Controller class for handling sensor-related HTTP requests.
 * Provides endpoints for listing temperature sensors in a device and adding a sensor to a device.
 */
@RestController
@RequestMapping(path = "/sensor")
public class SensorControllerWeb {

    /**
     * Service layer responsible for handling sensor-related business logic.
     */
    private SensorService sensorService;

    /**
     * Constructor for SensorControllerWeb class.
     *
     * @param sensorService   The service layer responsible for handling sensor-related business logic.
     */
    public SensorControllerWeb(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * Method to get a Sensor information from its identification.
     *
     * @param sensorName String object to identify the Sensor.
     * @return ResponseEntity returning the SensorDTO with associated links.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSensorByID(@PathVariable("id") String sensorName) {
        try {
            SensorID sensorID = new SensorID(sensorName);

            InternalSensorDTO internalSensorDTO = sensorService.findSensorIDInfo(sensorID);
            String sensorFunctionalityID = internalSensorDTO.sensorFunctionalityID.toString();
            String deviceID = internalSensorDTO.deviceID.toString();

            SensorDTO sensorDTO = new SensorDTO(sensorName, deviceID, sensorFunctionalityID);

            Link selflink = linkTo(SensorControllerWeb.class).slash(sensorDTO.sensorName).withSelfRel();
            sensorDTO.add(selflink);

            return new ResponseEntity<>(sensorDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Endpoint for adding a sensor to a device.
     *
     * @param sensorInfo The data transfer object representing the sensor.
     * @return ResponseEntity containing the created SensorDTO if successful, otherwise an error message with HttpStatus.NOT_FOUND.
     */
    @PostMapping("")
    public ResponseEntity<Object> addSensorToDevice(@RequestBody SensorDTO sensorInfo) {
        try {
            if (sensorInfo == null) {
                throw new IllegalArgumentException("SensorDTO cannot be null or empty");
            }
            SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorInfo.functionalityID);
            SensorID sensorID = new SensorID(sensorInfo.sensorName);
            DeviceID deviceID = new DeviceID(sensorInfo.deviceID);

            SensorID createdSensorID = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, sensorID);

            SensorDTO sensorDTO = new SensorDTO(createdSensorID.toString(), deviceID.toString(), sensorFunctionalityID.toString());

            Link selfLink = linkTo(SensorControllerWeb.class).slash(sensorDTO.sensorName).withSelfRel();
            sensorDTO.add(selfLink);

            return new ResponseEntity<>(sensorDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
