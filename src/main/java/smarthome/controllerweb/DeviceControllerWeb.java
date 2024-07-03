package smarthome.controllerweb;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.*;
import smarthome.service.*;
import smarthome.service.internaldto.InternalDeviceDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller class for handling device-related HTTP requests.
 * Provides methods for listing devices in a room, creating and saving a device, and deactivating a device.
 */
@RestController
@RequestMapping(path = "/devices")
public class DeviceControllerWeb {

    /**
     * DeviceService instance to interact with device-related operations.
     */
    private DeviceService deviceService;

    /**
     * CloseBlindRollerService instance to interact with device-related operations.
     */
    private CloseBlindRollerService closeBlindRollerService;

    /**
     * Service layer responsible for handling sensor-related business logic.
     */
    private SensorService sensorService;

    /**
     * Mapper responsible for converting sensor-related data transfer objects (DTOs).
     */
    private SensorMapperDTO sensorMapperDTO;

    /**
     * ActuatorService instance to interact with for actuator-related operations.
     */
    private ActuatorService actuatorService;

    /**
     * ActuatorMapperDTO responsible for converting actuator-related data transfer objects (DTOs).
     */
    private ActuatorMapperDTO actuatorMapperDTO;

    /**
     * ValueService instance to interact with value-related operations.
     */
    private ValueService valueService;

    /**
     * Mapper responsible for converting reading-related data transfer objects (DTOs).
     */
    private SensorFunctionalityMapperDTO mapperSensorFunctionality;

    /**
     * Mapper responsible for converting reading-related data transfer objects (DTOs).
     */
    private ReadingMapperDTO mapperReading;


    /**
     * Constructor for DeviceControllerWeb class.
     *
     * @param deviceService             The service layer to interact with for device-related operations.
     * @param closeBlindRollerService   The service layer to closeBlindRollerService.
     * @param sensorService             The service layer to interact with for sensor-related operations.
     * @param sensorMapperDTO           The mapper responsible for converting sensor-related DTOs.
     * @param actuatorService           The service layer to interact with for actuator-related operations.
     * @param actuatorMapperDTO         The mapper responsible for converting actuator-related DTOs.
     * @param valueService              The service layer to interact with value-related operations.
     * @param mapperSensorFunctionality The mapper responsible for converting sensor functionality-related DTOs.
     * @param mapperReading             The mapper responsible for converting reading-related DTOs.
     */
    public DeviceControllerWeb(DeviceService deviceService,
                               CloseBlindRollerService closeBlindRollerService,
                               SensorService sensorService,
                               SensorMapperDTO sensorMapperDTO,
                               ActuatorService actuatorService,
                               ActuatorMapperDTO actuatorMapperDTO,
                               ValueService valueService,
                               SensorFunctionalityMapperDTO mapperSensorFunctionality,
                               ReadingMapperDTO mapperReading) {
        this.deviceService = deviceService;
        this.closeBlindRollerService = closeBlindRollerService;
        this.sensorService = sensorService;
        this.sensorMapperDTO = sensorMapperDTO;
        this.actuatorService = actuatorService;
        this.actuatorMapperDTO = actuatorMapperDTO;
        this.valueService = valueService;
        this.mapperSensorFunctionality = mapperSensorFunctionality;
        this.mapperReading = mapperReading;
    }

    /**
     * Method to create a new device in a room.
     *
     * @param deviceDTO DTO containing the name, model and roomID of the device to be created.
     * @return ResponseEntity containing the DTO representing the created device if successful, otherwise an error message.
     */
    @PostMapping("")
    public ResponseEntity<Object> createDeviceAndSaveToRepository(@RequestBody DeviceDTO deviceDTO) {
        try {
            if (deviceDTO == null) {
                return new ResponseEntity<>("DeviceDTO cannot be null or empty", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            DeviceID deviceID = new DeviceID(deviceDTO.deviceName);
            DeviceModel deviceModel = new DeviceModel(deviceDTO.model);
            RoomID roomID = new RoomID(deviceDTO.roomName);

            DeviceID createdDeviceID = deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

            DeviceDTO savedDeviceDTO = new DeviceDTO(createdDeviceID.toString(), deviceModel.toString(), "ACTIVE", roomID.toString());

            //Create a self link for the device created.
            Link selflink = linkTo((DeviceControllerWeb.class)).slash(savedDeviceDTO.deviceName).withSelfRel();
            savedDeviceDTO.add(selflink);

            return new ResponseEntity<>(savedDeviceDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Method to get a list by a chosen sensor functionality.
     * Method to get a list of sensors by device ID.
     *
     * @param deviceName            The device identification representing the device.
     * @param sensorFunctionalityID The chosen Sensor Functionality
     * @return ResponseEntity returning the list of Sensors DTO.
     */
    @GetMapping("/{id}/sensors")
    public ResponseEntity<Object> getListOfSensors(@PathVariable("id") String deviceName,
                                                   @RequestParam(required = false) String sensorFunctionalityID) {
        try {
            List<SensorID> sensorIDs;
            if (sensorFunctionalityID != null) {
                sensorIDs = listSensorsOfDeviceWithSensorFunctionality(deviceName, sensorFunctionalityID);
            } else {
                sensorIDs = listSensorsOfDevice(deviceName);
            }
            List<SensorDTO> sensorDTOs = sensorMapperDTO.sensorIDsToDTOList(sensorIDs);

            // Add self links to each SensorDTO
            for (SensorDTO sensorDTO : sensorDTOs) {
                sensorDTO.add(linkTo(SensorControllerWeb.class).slash(sensorDTO.sensorName).withSelfRel());
            }

            // Create a CollectionModel for the list of SensorDTOs
            CollectionModel<SensorDTO> collectionModel = CollectionModel.of(sensorDTOs);
            collectionModel.add(linkTo(methodOn(DeviceControllerWeb.class)
                    .getListOfSensors(deviceName, "{sensorFunctionalityID}")).withSelfRel());

            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method for listing sensors in a device of a chosen functionality.
     *
     * @param deviceName            The device identification representing the device.
     * @param sensorFunctionalityID The chosen Sensor Functionality
     * @return ResponseEntity containing a list of SensorDTOs representing sensors in the device of a chosen functionality if successful,
     * otherwise an error message with HttpStatus.BAD_REQUEST.
     */
    private List<SensorID> listSensorsOfDeviceWithSensorFunctionality(String deviceName, String sensorFunctionalityID) {
            DeviceID deviceIDObject = new DeviceID(deviceName);
            SensorFunctionalityID sensorFunctionalityIDObject = new SensorFunctionalityID(sensorFunctionalityID);
            return sensorService.findSensorsIDOfADeviceBySensorFunctionality(deviceIDObject, sensorFunctionalityIDObject);
    }

    /**
     * Method for listing sensors in a device.
     *
     * @param deviceName The device identification representing the device.
     * @return ResponseEntity containing a list of SensorDTOs representing sensors in the device if successful,
     * otherwise an error message with HttpStatus.BAD_REQUEST.
     */
    private List<SensorID> listSensorsOfDevice(String deviceName) {
            DeviceID deviceIDObject = new DeviceID(deviceName);
            return sensorService.findSensorsIDOfADevice(deviceIDObject);
    }

    /**
     * Method to list actuators of a device.
     *
     * @param deviceName The device identification representing the deviceID.
     * @return ResponseEntity containing a list of ActuatorDTOs representing actuators in the device if successful,
     */
    @GetMapping("/{id}/actuators")
    public ResponseEntity<Object> getListOfActuators(@PathVariable("id") String deviceName) {
        try {
            DeviceID deviceID = new DeviceID(deviceName);
            List<ActuatorID> actuatorIDs = actuatorService.findActuatorsIDsOfADevice(deviceID);
            List<ActuatorDTO> actuatorDTOs = actuatorMapperDTO.actuatorsIDToActuatorDTO(actuatorIDs);

            // Add self links to each SensorDTO
            for (ActuatorDTO actuatorDTO : actuatorDTOs) {
                actuatorDTO.add(linkTo(ActuatorControllerWeb.class).slash(actuatorDTO.actuatorName).withSelfRel());
            }

            // Create a CollectionModel for the list of SensorDTOs
            CollectionModel<ActuatorDTO> collectionModel = CollectionModel.of(actuatorDTOs);
            collectionModel.add(linkTo(methodOn(DeviceControllerWeb.class).getListOfActuators(deviceName)).withSelfRel());

            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to close a blind roller.
     * Method to deactivate a device.
     *
     * @param deviceName String object to identify the device to be deactivated.
     * @return ResponseEntity indicating true if successful, and false otherwise for both requests.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> editDevice(@PathVariable("id") String deviceName,
                                             @RequestParam(required = false) Integer closePercentage) {
        try {
            if (closePercentage != null) {
                return closeBlindRollerDevice(deviceName, closePercentage);
            } else {
                return deactivateDevice(deviceName);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Method to set a blind roller device. To open/close the blind roller successfully,
     * the device has to be active and the percentage is limited from 0 to 100%.
     *
     * @param deviceID        Unique identifier of chosen device.
     * @param percentage BlindSetter value to open/close the chosen device, in case it is a blind roller device.
     */
    private ResponseEntity<Object> closeBlindRollerDevice(String deviceID, int percentage) {
            DeviceID chosenDeviceID = new DeviceID(deviceID);
            ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID("BlindSetter");

            if (closeBlindRollerService.setActuatorStateOfBlindRoller(chosenDeviceID, actuatorFunctionalityID, percentage))
                return new ResponseEntity<>(true, HttpStatus.OK);
            else
                return new ResponseEntity<>(false, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Method to deactivate a Device.
     *
     * @param deviceName String object to identify the device to be deactivated.
     * @return ResponseEntity indicating true if successful, and false otherwise.
     */
    private ResponseEntity<Object> deactivateDevice(String deviceName) {
        DeviceID deviceID = new DeviceID(deviceName);

        if (deviceService.deactivateDevice(deviceID))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Method to get all values for all sensors present within a device, within a given period.
     * Method to get a Device by its ID, along with hypermedia.
     *
     * @param deviceName String that represents the ID of the device.
     * @param givenStart LocalDateTime object representing the start of the "given period".
     * @param givenEnd   LocalDateTime object representing the end of the "given period".
     * @param sensorFunctionality   String that represents the sensor functionality ID.
     * @return Map with SensorFunctionalityDTO as key and List of ReadingDTO as value.
     * Return the DeviceDTO with associated links.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getObjectByDeviceID(@PathVariable("id") String deviceName,
                                                      @RequestParam(name = "givenStart", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime givenStart,
                                                      @RequestParam(name = "givenEnd", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime givenEnd,
                                                      @RequestParam(name = "functionality", required = false) String sensorFunctionality
    ) {
        try {
            if (givenStart != null && givenEnd != null)
                return listAllMeasurementsOfDeviceInPeriod(deviceName, givenStart, givenEnd);
            else if (sensorFunctionality != null)
                return getCurrentMeasurementOfADeviceByID(deviceName, sensorFunctionality);
            else
                return deviceByID(deviceName);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Method to get all values for all sensors present within a device, within a given period.
     * If no device is found/no sensors are found/no values within period are found, list will be empty.
     *
     * @param deviceIDString String that represents the ID of the device.
     * @param givenStart     LocalDateTime object representing the start of the "given period".
     * @param givenEnd       LocalDateTime object representing the end of the "given period".
     * @return List with DeviceMeasurementsDTO object that contain String SensorFunctionality and String Reading.
     */
    private ResponseEntity<Object> listAllMeasurementsOfDeviceInPeriod(String deviceIDString, LocalDateTime givenStart, LocalDateTime givenEnd) {
        //Convert local time that is sent to Timestamps
        Timestamp start = Timestamp.valueOf(givenStart);
        Timestamp end = Timestamp.valueOf(givenEnd);

        DeviceID deviceID = new DeviceID(deviceIDString);
        Map<SensorFunctionalityID, List<Reading>> domainVOMap =
                valueService.
                        getAllMeasurementsForDeviceBetweenPeriod(deviceID, start, end);

        Map<SensorFunctionalityDTO, List<ReadingDTO>> convertedDTOMap = convertToValueDTO(domainVOMap);

        return new ResponseEntity<>(convertedDTOMap, HttpStatus.OK);
    }


    /**
     * Helper method to convert Map<SensorFunctionalityID, List<Reading>> resulting from the service call to
     * Map<SensorFunctionalityDTO, List<ReadingDTO> containing the DTO objects.
     *
     * @param readings Map<SensorFunctionalityID, List<Reading>> containing VO SensorFunctionalityID and List of VO Reading objects for each.
     * @return Map<SensorFunctionalityDTO, List < ReadingDTO>> using DTO objects converted based on the given VOs.
     */
    private Map<SensorFunctionalityDTO, List<ReadingDTO>>
    convertToValueDTO(Map<SensorFunctionalityID, List<Reading>> readings) {

        Map<SensorFunctionalityDTO, List<ReadingDTO>> convertedDTOMap = new HashMap<>();

        for (Map.Entry<SensorFunctionalityID, List<Reading>> entry : readings.entrySet()) {

            SensorFunctionalityDTO sensorFunctionalityDTO =
                    mapperSensorFunctionality.sensorFunctionalityToDTO(entry.getKey());
            List<Reading> listOfReadings = entry.getValue();
            List<ReadingDTO> listReadingsDTO = mapperReading.readingsToDTOList(listOfReadings);

            convertedDTOMap.put(sensorFunctionalityDTO, listReadingsDTO);
        }

        return convertedDTOMap;
    }

    /**
     * Method to retrieve the current measurement of a device.
     *
     * @param deviceName String object to identify the device.
     * @param sensorFunctionality String object to identify the sensor functionality.
     * @return A String with the current measurement of a device.
     */
    private ResponseEntity<Object> getCurrentMeasurementOfADeviceByID(String deviceName, String sensorFunctionality) {
        DeviceID deviceID = new DeviceID(deviceName);
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorFunctionality);

        String currentPosition = valueService.getLastMeasurementOfASensor(deviceID, sensorFunctionalityID);
        return new ResponseEntity<>(currentPosition, HttpStatus.OK);
    }

    /**
     * Method to get a Device with hypermedia.
     *
     * @param deviceName String object to identify the device.
     * @return ResponseEntity returning the DeviceDTO with associated links.
     */
    private ResponseEntity<Object> deviceByID(String deviceName) {
        DeviceID deviceID = new DeviceID(deviceName);

        InternalDeviceDTO internalDeviceDTO = deviceService.getDeviceByID(deviceID);

        DeviceDTO deviceDTO = new DeviceDTO(internalDeviceDTO.deviceID.toString(),
                internalDeviceDTO.deviceModel.toString(),
                internalDeviceDTO.deviceStatus.toString(),
                internalDeviceDTO.roomID.toString());

        Link selflink = linkTo(DeviceControllerWeb.class).slash(deviceDTO.deviceName).withSelfRel();
        deviceDTO.add(selflink);

        Link listSensorLink = linkTo(methodOn(DeviceControllerWeb.class)
                .getListOfSensors(deviceDTO.deviceName, "{sensorFunctionalityID}"))
                .withRel("listSensors");
        deviceDTO.add(listSensorLink);

        Link listActuatorLink = linkTo(methodOn(DeviceControllerWeb.class)
                .getListOfActuators(deviceDTO.deviceName))
                .withRel("listActuators");
        deviceDTO.add(listActuatorLink);

        Link editLink = linkTo(methodOn(DeviceControllerWeb.class)
                .editDevice(deviceDTO.deviceName, null))
                .withRel("editDevice");
        deviceDTO.add(editLink);

        Link addSensorLink = linkTo(methodOn(SensorControllerWeb.class)
                .addSensorToDevice(null))
                .withRel("addSensor");
        deviceDTO.add(addSensorLink);

        Link addActuatorLink = linkTo(methodOn(ActuatorControllerWeb.class)
                .addNewActuatorToDevice(null))
                .withRel("addActuator");
        deviceDTO.add(addActuatorLink);

        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }
}
