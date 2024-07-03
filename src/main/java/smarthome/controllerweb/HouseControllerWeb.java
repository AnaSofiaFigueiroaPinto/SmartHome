package smarthome.controllerweb;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.*;
import smarthome.service.*;
import smarthome.service.internaldto.InternalDeviceDTO;
import smarthome.service.internaldto.InternalHouseDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/house")
public class HouseControllerWeb {
    /**
     * HouseService instance.
     */
    private final HouseService houseService;

    /**
     * Service to obtain list of values
     */
    private final ValueService valueService;

    /**
     * RoomService instance.
     */
    private final RoomService roomService;

    /**
     * DeviceService instance.
     */
    private final DeviceService deviceService;

    /**
     * MaxTempDifOutsideInsideService instance.
     */
    private final MaxTempDifOutsideInsideService maxTempDifOutsideInsideService;

    /**
     * ListAllDevicesInHouseByFunctionalityService instance.
     */
    private final ListAllDevicesInHouseByFunctionalityService listDevicesByFunctionalityService;

    /**
     * WeatherAPIService instance.
     */
    private final WeatherAPIService weatherAPIService;

    /**
     * RoomMapperDTO responsible for converting room-related data transfer objects (DTOs).
     */
    private final RoomMapperDTO roomMapperDTO;

    /**
     * DeviceMapperDTO responsible for converting device-related data transfer objects (DTOs).
     */
    private final DeviceMapperDTO deviceMapperDTO;

    /**
     * ActuatorFunctionalityMapperDTO responsible for converting actuatorFunctionality-related data transfer objects (DTOs).
     */
    private final ActuatorFunctionalityMapperDTO actuatorFunctionalityMapperDTO;

    /**
     * SensorFunctionalityMapperDTO responsible for converting sensorFunctionality-related data transfer objects (DTOs).
     */
    private final SensorFunctionalityMapperDTO sensorFunctionalityMapperDTO;

    /**
     * Constructor for HouseControllerWeb class.
     *
     * @param houseService                      The service layer to interact with for house-related operations.
     * @param roomService                       The service layer to interact with for room-related operations.
     * @param deviceService                     The service layer to interact with for device-related operations.
     * @param listDevicesByFunctionalityService The service layer to list the devices by functionality.
     * @param maxTempDifOutsideInsideService    The service layer to get the maximum temperature difference from inside to outside.
     * @param roomMapperDTO                     The mapper responsible for converting room-related DTOs.
     * @param deviceMapperDTO                   The mapper responsible for converting device-related DTOs.
     * @param actuatorFunctionalityMapperDTO    The mapper responsible for converting actuatorFunctionality-related DTOs.
     * @param sensorFunctionalityMapperDTO      The mapper responsible for converting sensorFunctionality-related DTOs.
     * @param valueService                      The service layer to interact with for value-related operations.
     * @param weatherAPIService                 The service layer to interact with for weather service-related operations.
     */
    public HouseControllerWeb(HouseService houseService,
                              RoomService roomService,
                              DeviceService deviceService,
                              ListAllDevicesInHouseByFunctionalityService listDevicesByFunctionalityService,
                              MaxTempDifOutsideInsideService maxTempDifOutsideInsideService,
                              RoomMapperDTO roomMapperDTO,
                              DeviceMapperDTO deviceMapperDTO,
                              ActuatorFunctionalityMapperDTO actuatorFunctionalityMapperDTO,
                              SensorFunctionalityMapperDTO sensorFunctionalityMapperDTO,
                              ValueService valueService,
                              WeatherAPIService weatherAPIService) {
        this.houseService = houseService;
        this.roomService = roomService;
        this.deviceService = deviceService;
        this.listDevicesByFunctionalityService = listDevicesByFunctionalityService;
        this.maxTempDifOutsideInsideService = maxTempDifOutsideInsideService;
        this.roomMapperDTO = roomMapperDTO;
        this.deviceMapperDTO = deviceMapperDTO;
        this.actuatorFunctionalityMapperDTO = actuatorFunctionalityMapperDTO;
        this.sensorFunctionalityMapperDTO = sensorFunctionalityMapperDTO;
        this.valueService = valueService;
        this.weatherAPIService = weatherAPIService;
    }

    /**
     * Configures the house location with the given parameters in the LocationDTO.
     * @param locationDTO DTO that contains the parameters to configure the house location and a houseID.
     * @return ResponseEntity containing the object HouseDTO with the configured location, if successful.
     */
    @PatchMapping("")
    public ResponseEntity<Object> configureHouseLocation(@RequestBody LocationDTO locationDTO) {
        try {
            Address address = new Address(
                    locationDTO.street,
                    locationDTO.doorNumber,
                    locationDTO.zipCode,
                    locationDTO.city,
                    locationDTO.country);

            GPSCode gpsCode = new GPSCode(locationDTO.latitude, locationDTO.longitude);

            HouseID houseID = houseService.getHouseIDFromRepository();

            Location location = houseService.editLocation(houseID, address, gpsCode);

            HouseDTO houseDTO = new HouseDTO(
                    houseID.toString(),
                    location.getAddress().getStreet(),
                    location.getAddress().getDoorNumber(),
                    location.getAddress().getZipCode(),
                    location.getAddress().getCity(),
                    location.getAddress().getCountry(),
                    location.getGpsCode().getLatitude(),
                    location.getGpsCode().getLongitude());

            //Create a self link to the configured house
            Link selfLink = linkTo(HouseControllerWeb.class).withSelfRel();
            houseDTO.add(selfLink);

            return new ResponseEntity<>(houseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the requested list of rooms in the house, according to the query parameters.
     * @param areRoomsInside Boolean parameter indicating whether to retrieve rooms inside or outside the house, or the non-filtered list of all rooms if null.
     * @return ResponseEntity containing the list of rooms requested, if successful.
     */
    @GetMapping("/{id}/rooms")
    //http://localhost:8080/api/house/House001/rooms -> call getListOfRoomsInHouse()
    //http://localhost:8080/api/house/House001/rooms?areRoomsInside=true -> call getListOfRoomsInsideOrOutsideHouse()
    public ResponseEntity<Object> getListOfRoomsInHouse(@PathVariable("id") String houseIDstr,
                                                        @RequestParam(required = false) Boolean areRoomsInside) {
        try {
            List<RoomDTO> roomDTOList;
            if (areRoomsInside != null) {
                roomDTOList = getListOfRoomsInsideOrOutsideHouse(houseIDstr, areRoomsInside);
            } else {
                roomDTOList = getListOfRoomsInHouse(houseIDstr);
            }

            //Create link to each room details getRoomsByID
            roomDTOList.forEach(roomDTO -> roomDTO.add(linkTo(RoomControllerWeb.class).slash(roomDTO.roomName).withSelfRel()));

            return new ResponseEntity<>(roomDTOList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves a list of all rooms in the house.
     * @return List of RoomDTOs representing rooms in the house.
     */
    private List<RoomDTO> getListOfRoomsInHouse(String houseIDstr) {
        HouseID houseID = new HouseID(houseIDstr);
        List<RoomID> listOfRoomsIDs = roomService.getListOfRoomsInHouse(houseID);
        return roomMapperDTO.roomIDsToDTOList(listOfRoomsIDs);
    }

    /**
     * Retrieves a list of rooms inside or outside the house, depending on areRoomsInside parameter.
     * @param areRoomsInside Boolean parameter indicating whether to retrieve rooms inside or outside the house.
     * @return List of RoomDTOs representing rooms inside or outside the house.
     */
    private List<RoomDTO> getListOfRoomsInsideOrOutsideHouse(String houseIDstr, boolean areRoomsInside) {
        HouseID houseID = new HouseID(houseIDstr);
        List<RoomID> listOfRoomsIDs = roomService.getListOfRoomsInsideOrOutsideHouse(houseID, areRoomsInside);
        return roomMapperDTO.roomIDsToDTOList(listOfRoomsIDs);
    }

    /**
     * Retrieves the list of all devices in the house.
     * @param houseIDstr String representing the house ID.
     * @return ResponseEntity containing the list of devices in the house, if successful.
     */
    @GetMapping("/{id}/devices")
    //http://localhost:8080/api/house/House001/devices
    public ResponseEntity<Object> getListOfAllDevicesInHouse(@PathVariable("id") String houseIDstr) {
        try {
            HouseID houseID = new HouseID(houseIDstr);
            List<InternalDeviceDTO> listOfInternalDevices = deviceService.getListOfDevicesInHouse(houseID);
            List<DeviceDTO> listOfDevicesDTO = deviceMapperDTO.internalDeviceDTOsToDTOList(listOfInternalDevices);
            for (DeviceDTO deviceDTO : listOfDevicesDTO) {
                deviceDTO.add(linkTo(DeviceControllerWeb.class).slash(deviceDTO.deviceName).withSelfRel());
            }
            return new ResponseEntity<>(listOfDevicesDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the information requested in the query parameters.
     * @param groupedByFunctionality Boolean parameter indicating whether to group devices by functionality.
     * @param givenStart LocalDateTime object representing the start of the time range.
     * @param givenEnd LocalDateTime object representing the end of the time range.
     * @param insideSensorID String representing the sensor inside the house.
     * @param outsideSensorID String representing the sensor outside the house.
     * @return ResponseEntity containing the requested information, if successful.
     */
    @GetMapping("")
    //http://localhost:8080/api/house -> call getHouseDetails()
    //http://localhost:8080/api/house?groupedByFunctionality=true -> call getListOfDevicesByFunctionality()
    //http://localhost:8080/api/house?givenStart=2024-04-01T12:14:00&givenEnd=2024-04-01T12:40:00 -> call getPeakPowerConsumption()
    //http://localhost:8080/api/house?insideSensorID=sensor1&outsideSensorID=sensor2&givenStart=2024-04-15T08:00:00&givenEnd=2024-04-15T21:40:00 -> call maxTempDifOutsideInside()
    //http://localhost:8080/api/house?insideSensorID=sensor1&givenStart=2024-04-15T08:00:00&givenEnd=2024-04-15T21:40:00 -> call maxTempDifOutsideInsideWeatherServiceAPI()
    public ResponseEntity<Object> handleHouseQuery (@RequestParam(required = false) Boolean groupedByFunctionality,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime givenStart,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime givenEnd,
                                                @RequestParam(required = false) String insideSensorID,
                                                @RequestParam(required = false) String outsideSensorID) {
        try {
            // Call getListOfDevicesByFunctionality(), if groupedByFunctionality is true
            if (groupedByFunctionality != null && groupedByFunctionality) {
                return new ResponseEntity<>(getListOfDevicesByFunctionality(), HttpStatus.OK);
            }
            // Call maxTempDifOutsideInside(), if inside+outside sensors and givenStart+End times are provided
            if (insideSensorID != null && outsideSensorID != null && givenStart != null && givenEnd != null) {
                double maxTempDifference = maxTempDifOutsideInside(insideSensorID, outsideSensorID, givenStart, givenEnd);
                if (maxTempDifference == -1) {
                    return new ResponseEntity<>("Failed to calculate difference in set period", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(maxTempDifference, HttpStatus.OK);
            }

            // Call maxTempDifOutsideInsideWeatherServiceAPI(), if inside sensor and givenStart+End times are provided
            if (insideSensorID != null && givenStart != null && givenEnd != null) {
                double maxTempDifference = maxTempDifOutsideInsideWeatherServiceAPI(insideSensorID, givenStart, givenEnd);
                if (maxTempDifference == -1) {
                    return new ResponseEntity<>("Failed to calculate difference in set period", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(maxTempDifference, HttpStatus.OK);
            }

            // Call getPeakPowerConsumption(), if only givenStart+End times are provided
            if (givenStart != null && givenEnd != null) {
                double peakPowerConsumption = getPeakPowerConsumption(givenStart, givenEnd);
                return new ResponseEntity<>(peakPowerConsumption, HttpStatus.OK);
            }

            // Retrieve the house details, if no query parameters are provided
            return new ResponseEntity<>(getHouse(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all devices in house and their room location, grouped by sensor functionality.
     * @return A map with Object as key (ActuatorFunctionalityDTO or SensorFunctionalityDTO) and a Map <RoomDTO,DeviceDTO> as value.
     */
    private Map<Object, Map<RoomDTO, DeviceDTO>> getListOfDevicesByFunctionality() {
        Map<Object, Map<RoomID, DeviceID>> devicesWithRoomGroupedByFunctionalityMap =
                listDevicesByFunctionalityService.devicesGroupedByFunctionalityAndLocation();

        //Convert to a map with DTOs
        Map<Object, Map<RoomDTO, DeviceDTO>> devicesDTOWithRoomDTOGroupedByFunctionalityDTOMap = new HashMap<>();
        devicesWithRoomGroupedByFunctionalityMap.forEach((functionality, roomAndDevicesMap) -> {
            Object functionalityDTO = new Object();
            if (functionality instanceof ActuatorFunctionalityID actuatorFunctionalityID)
                functionalityDTO = actuatorFunctionalityMapperDTO.actuatorFunctionalityToDTO(actuatorFunctionalityID);
            if (functionality instanceof SensorFunctionalityID sensorFunctionalityID)
                functionalityDTO = sensorFunctionalityMapperDTO.sensorFunctionalityToDTO(sensorFunctionalityID);

            Map<RoomDTO, DeviceDTO> roomAndDevicesDTOMap = new HashMap<>();
            roomAndDevicesMap.forEach((roomID, deviceID) -> {
                RoomDTO roomDTO = roomMapperDTO.roomToDTO(roomID);
                DeviceDTO deviceDTO = deviceMapperDTO.deviceToDTO(deviceID);
                // Adding self-links to each DeviceDTO
                deviceDTO.add(linkTo(DeviceControllerWeb.class).slash(deviceDTO.deviceName).withSelfRel());
                // Adding rooms and devices into the map
                roomAndDevicesDTOMap.put(roomDTO, deviceDTO);
            });
            devicesDTOWithRoomDTOGroupedByFunctionalityDTOMap.put(functionalityDTO, roomAndDevicesDTOMap);
        });
        return devicesDTOWithRoomDTOGroupedByFunctionalityDTOMap;
    }

    /**
     * Method to get the maximum temperature difference between a sensor inside and a sensor outside the House entity,
     * within a given period.
     *
     * @param insideSensorIDString String representing the sensor inside the house
     * for which the maximum temperature difference is to be calculated.
     * @param outsideSensorIDString String representing the sensor outside the house
     * for which the maximum temperature difference is to be calculated.
     * @param givenStart The start (LocalDateTime) of the time range. These are required to be passed.
     * @param givenEnd The end (LocalDateTime) of the time range. These are required to be passed.
     * @return The maximum temperature difference between outside and inside sensors within the specified
     * time range, or -1 if an error occurs during calculation.
     */
    private double maxTempDifOutsideInside(String insideSensorIDString,
                                           String outsideSensorIDString,
                                           LocalDateTime givenStart,
                                           LocalDateTime givenEnd
    ) {
        //Convert local time that is sent to Timestamps
        Timestamp start = Timestamp.valueOf(givenStart);
        Timestamp end = Timestamp.valueOf(givenEnd);

        SensorID insideSensorID = new SensorID(insideSensorIDString);
        SensorID outsideSensorID = new SensorID(outsideSensorIDString);

        return maxTempDifOutsideInsideService.getMaxTemperatureDifference(insideSensorID, outsideSensorID, start, end);
    }

    /**
     * Method to get the maximum temperature difference between a sensor and the outside temperature
     * given by the WeatherServiceAPI within a given period.
     *
     * @param sensorIDString String representing the sensor inside the house
     * for which the maximum temperature difference is to be calculated.
     * @param givenStart The start (LocalDateTime) of the time range. These are required to be passed.
     * @param givenEnd The end (LocalDateTime) of the time range. These are required to be passed.
     * @return The maximum temperature difference between the sensor and outside temperature within the specified
     * time range, or -1 if an error occurs during calculation.
     */
    private double maxTempDifOutsideInsideWeatherServiceAPI(String sensorIDString,
                                           LocalDateTime givenStart,
                                           LocalDateTime givenEnd
    ) {
        //Convert local time that is sent to Timestamps
        Timestamp start = Timestamp.valueOf(givenStart);
        Timestamp end = Timestamp.valueOf(givenEnd);

        SensorID insideSensorID = new SensorID(sensorIDString);


        return weatherAPIService.getMaxTemperatureDifferenceWithWeatherService(insideSensorID, start, end);
    }

    /**
     * Retrieves the peak power consumption in a given period.
     * @param givenStart LocalDateTime object representing the start of the "given period".
     * @param givenEnd   LocalDateTime object representing the end of the "given period".
     * @return The peak power consumption in the given period.
     */
    private double getPeakPowerConsumption(LocalDateTime givenStart, LocalDateTime givenEnd) {
        // Convert local time that is sent to Timestamps
        Timestamp start = Timestamp.valueOf(givenStart);
        Timestamp end = Timestamp.valueOf(givenEnd);

        // Call the service method to get the peak power consumption
        return valueService.getPeakPowerConsumption(start, end);
    }

    /**
     * Retrieves the details of the house.
     * @return ResponseEntity containing details about the house if successful
     */
    private ResponseEntity<Object> getHouse() {
        try {
            InternalHouseDTO internalHouseDTO = houseService.getHouse();

            HouseDTO houseDTO = new HouseDTO(
                    internalHouseDTO.houseID.toString(),
                    internalHouseDTO.street,
                    internalHouseDTO.doorNumber,
                    internalHouseDTO.zipCode,
                    internalHouseDTO.city,
                    internalHouseDTO.country,
                    internalHouseDTO.latitude,
                    internalHouseDTO.longitude);

            //ADD HATEOAS LINKS TO THE HOUSE DTO
            //(provided parameters in the linked methods serve only as placeholders for the actual parameters that will be passed)

            //Create a self link to the house
            Link selfLink = linkTo(HouseControllerWeb.class).withSelfRel();
            houseDTO.add(selfLink);

            //Create additional link to configure the location of the house
            Link configureLocationLink = linkTo(methodOn(HouseControllerWeb.class)
                    .configureHouseLocation(null)).withRel("configureLocation");
            houseDTO.add(configureLocationLink);

            //Create additional link to list all devices in the house grouped by functionality
            Link listDevicesByFunctionalityLink = linkTo(methodOn(HouseControllerWeb.class)
                    .handleHouseQuery(true, null, null, null, null)).withRel("listDevicesByFunctionality");
            houseDTO.add(listDevicesByFunctionalityLink);

            //Create additional link to get the maximum temperature difference between a sensor inside and a sensor outside the house
            Link maxTempDifOutsideInsideLink = linkTo(methodOn(HouseControllerWeb.class)
                    .handleHouseQuery(null, null, null, "sensor1", "sensor2")).withRel("maxTempDifOutsideInside");
            houseDTO.add(maxTempDifOutsideInsideLink);

            //Create additional link to get the peak power consumption in a given period
            Link peakPowerConsumptionLink = linkTo(methodOn(HouseControllerWeb.class)
                    .handleHouseQuery(null, LocalDateTime.now(), LocalDateTime.now(), null, null)).withRel("peakPowerConsumption");
            houseDTO.add(peakPowerConsumptionLink);

            //Create additional link to create a new room in the House
            Link createRoomLink = linkTo(methodOn(RoomControllerWeb.class)
                    .createRoom(new RoomDTO("roomName", 0, 0, 0, 0, houseDTO.houseID))).withRel("createRoom");
            houseDTO.add(createRoomLink);

            return new ResponseEntity<>(houseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the temperature for a given hour or sunrise/sunset time based on the provided parameters.
     *
     * @param hour   The hour of the day (0-23).
     * @param option The option to choose sunrise or sunset.
     * @return ResponseEntity containing the temperature or time if successful.
     */
    @GetMapping("/weather")
    //http://localhost:8080/api/house/weather?hour=15  -> call getTemperatureForHour()
    //http://localhost:8080/api/house/weather?option=sunrise  -> call getSunriseSunsetHour()
    public ResponseEntity<Object> getWeatherInfo(@RequestParam(required = false) Integer hour,
                                                        @RequestParam(required = false) String option) {
        try {
            if (hour != null) {
                double temperature = weatherAPIService.getTemperatureForHour(hour);
                return new ResponseEntity<>(temperature, HttpStatus.OK);
            }
            if (option != null) {
                double time = weatherAPIService.getSunriseSunsetHour(option);
                return new ResponseEntity<>(time, HttpStatus.OK);
            }
            return new ResponseEntity<>("Either hour or option must be provided", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
