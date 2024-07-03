package smarthome.controllerweb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.*;
import smarthome.service.*;
import smarthome.service.internaldto.InternalDeviceDTO;
import smarthome.util.exceptions.HouseNotFoundException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {HouseControllerWeb.class})
class HouseControllerWebTest {

    @MockBean
    private HouseService houseService;

    @MockBean
    private ValueService valueService;

    @MockBean
    private RoomService roomService;

    @MockBean
    private DeviceService deviceService;

    @MockBean
    private ListAllDevicesInHouseByFunctionalityService listAllDevicesInHouseByFunctionalityService;

    @MockBean
    private MaxTempDifOutsideInsideService maxTempDifOutsideInsideService;

    @MockBean
    private WeatherAPIService weatherAPIService;

    @MockBean
    private RoomMapperDTO roomMapperDTO;

    @MockBean
    private DeviceMapperDTO deviceMapperDTO;

    @MockBean
    private ActuatorFunctionalityMapperDTO actuatorFunctionalityMapperDTO;

    @MockBean
    private SensorFunctionalityMapperDTO sensorFunctionalityMapperDTO;

    @Autowired
    private HouseControllerWeb houseControllerWeb;

    /**
     * Test to edit/configure the house location.
     * It verifies that the controller returns updated house object.
     */
    @Test
    void successConfigureHouseLocation() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Data for LocationDTO
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 41.14961;
        double longitude = -8.61099;

        GPSCode gpsCode = new GPSCode(latitude, longitude);
        Address address = new Address(street, doorNumber, zipCode, city, country);
        Location location = new Location(address, gpsCode);

        LocationDTO locationDTO = new LocationDTO(street, doorNumber, zipCode, city, country, latitude, longitude);

        HouseID houseInRepoID = new HouseID("14cd1339-e6f3-4a90-b3a4-b86209530020");

        // Setting behavior for service
        when(houseService.getHouseIDFromRepository()).thenReturn(houseInRepoID);
        when(houseService.editLocation(houseInRepoID, address, gpsCode)).thenReturn(location);

        // Act
        ResponseEntity<Object> responseEntity = houseControllerWeb.configureHouseLocation(locationDTO);
        HouseDTO result = (HouseDTO) responseEntity.getBody();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(street, result.street);
    }

    /**
     * Test to edit/configure the house location with invalid parameters.
     * It verifies that the controller returns an error message.
     */
    @Test
    void failConfigureHouseLocationInvalidParameter() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Act
        ResponseEntity<Object> responseEntity = houseControllerWeb.configureHouseLocation(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }


    /**
     * Test to get a list of rooms in a house.
     * It verifies that the controller returns a list of rooms.
     */
    @Test
    public void successfullyGetListOfRoomsDTOInHouse() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        HouseID houseID = new HouseID("House1");

        // Stub behavior of roomService
        RoomID roomID1 = new RoomID("Living Room");
        RoomID roomID2 = new RoomID("Bedroom");
        List<RoomID> mockRoomIDs = Arrays.asList(roomID1, roomID2);
        when(roomService.getListOfRoomsInHouse(houseID)).thenReturn(mockRoomIDs);

        // Stub behavior of roomMapperDTO
        RoomDTO roomDTO1 = new RoomDTO("Living Room");
        RoomDTO roomDTO2 = new RoomDTO("Bedroom");
        List<RoomDTO> mockRoomDTOList = Arrays.asList(roomDTO1, roomDTO2);
        when(roomMapperDTO.roomIDsToDTOList(mockRoomIDs)).thenReturn(mockRoomDTOList);

        // Invoke the method under test
        ResponseEntity<Object> response = houseControllerWeb.getListOfRoomsInHouse("House1",null);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoomDTOList, response.getBody());
        List<RoomDTO> returnedRoomDTOList = (List<RoomDTO>) response.getBody();
        assertEquals(2, returnedRoomDTOList.size());
        assertEquals("Living Room", returnedRoomDTOList.get(0).roomName);
        assertEquals("Bedroom", returnedRoomDTOList.get(1).roomName);

        // Verify that the self link is added to each RoomDTO
        returnedRoomDTOList.forEach(roomDTO -> {
            assertNotNull(roomDTO.getLinks());
            assertTrue(roomDTO.getLinks().hasLink("self"));
        });
    }

    /**
     * Test to get an empty list o f rooms in a house.
     * It verifies that the controller returns an empty list of rooms.
     */
    @Test
    public void successfullyGetEmptyListOfRoomsDTOInHouse() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        List<RoomID> mockRoomIDs = Arrays.asList();
        List<RoomDTO> mockRoomDTOList = Arrays.asList();

        HouseID houseID = new HouseID("House1");

        // Mock behavior of roomService
        when(roomService.getListOfRoomsInHouse(houseID)).thenReturn(mockRoomIDs);

        // Mock behavior of roomMapperDTO
        when(roomMapperDTO.roomIDsToDTOList(mockRoomIDs)).thenReturn(mockRoomDTOList);

        // Invoke the method under test
        ResponseEntity<Object> response = houseControllerWeb.getListOfRoomsInHouse("House1",null);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<RoomDTO> returnedRoomDTOList = (List<RoomDTO>) response.getBody();
        assertEquals(0, returnedRoomDTOList.size());

        // Verify that the self link is added to each RoomDTO
        returnedRoomDTOList.forEach(roomDTO -> {
            assertNotNull(roomDTO.getLinks());
            assertTrue(roomDTO.getLinks().hasLink("self"));
        });
    }

    /**
     * Test to get a list of rooms of a house that is not in repository.
     * It verifies that the controller returns an appropriate error response.
     */
    @Test
    public void failGetListOfRoomsDTOInHouseWithoutHouseID() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        HouseID houseID = new HouseID("House1");

        // Mock behavior of roomService
        when(roomService.getListOfRoomsInHouse(houseID)).thenThrow(new HouseNotFoundException());

        // Invoke the method under test
        ResponseEntity<Object> response = houseControllerWeb.getListOfRoomsInHouse("House1",null);

        // Verify the result
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("House not found in Repository", response.getBody());
    }

    /**
     * Test to get a list of inside rooms in a house.
     * It verifies that the controller returns a list of inside rooms.
     */
    @Test
    public void successfullyGetListOfRoomsDTOInside() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        HouseID houseID = new HouseID("House1");

        //Mock behaviour of houseService
        when(houseService.getHouseIDFromRepository()).thenReturn(houseID);

        // Stub behavior of roomService
        RoomID roomID1 = new RoomID("Living Room");
        RoomID roomID2 = new RoomID("Bedroom");
        List<RoomID> mockRoomIDs = Arrays.asList(roomID1, roomID2);
        when(roomService.getListOfRoomsInsideOrOutsideHouse(houseID, true)).thenReturn(mockRoomIDs);

        // Stub behavior of roomMapperDTO
        RoomDTO roomDTO1 = new RoomDTO("Living Room");
        RoomDTO roomDTO2 = new RoomDTO("Bedroom");
        List<RoomDTO> mockRoomDTOList = Arrays.asList(roomDTO1, roomDTO2);
        when(roomMapperDTO.roomIDsToDTOList(mockRoomIDs)).thenReturn(mockRoomDTOList);

        // Invoke the method under test
        ResponseEntity<Object> response = houseControllerWeb.getListOfRoomsInHouse("House1",true);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoomDTOList, response.getBody());
        List<RoomDTO> returnedRoomDTOList = (List<RoomDTO>) response.getBody();
        assertEquals(2, returnedRoomDTOList.size());
        assertEquals("Living Room", returnedRoomDTOList.get(0).roomName);
        assertEquals("Bedroom", returnedRoomDTOList.get(1).roomName);

        // Verify that the self link is added to each RoomDTO
        returnedRoomDTOList.forEach(roomDTO -> {
            assertNotNull(roomDTO.getLinks());
            assertTrue(roomDTO.getLinks().hasLink("self"));
        });
    }


    /**
     * Test to get a list of outside rooms in a house.
     * It verifies that the controller returns a list of rooms outside.
     */
    @Test
    public void successfullyGetListOfRoomsDTOOutside() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        HouseID houseID = new HouseID("House1");

        //Mock behaviour of houseService
        when(houseService.getHouseIDFromRepository()).thenReturn(houseID);

        // Mock data
        RoomID roomID1 = new RoomID("Garden");
        List<RoomID> mockRoomIDs = List.of(roomID1);

        // Mock behavior of roomService
        when(roomService.getListOfRoomsInsideOrOutsideHouse(houseID, false)).thenReturn(mockRoomIDs);

        RoomDTO roomDTO1 = new RoomDTO("Garden");
        List<RoomDTO> mockRoomDTOList = List.of(roomDTO1);

        // Mock behavior of roomMapperDTO
        when(roomMapperDTO.roomIDsToDTOList(mockRoomIDs)).thenReturn(mockRoomDTOList);

        // Invoke the method under test
        ResponseEntity<Object> response = houseControllerWeb.getListOfRoomsInHouse("House1",false);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoomDTOList, response.getBody());
        List<RoomDTO> returnedRoomDTOList = (List<RoomDTO>) response.getBody();
        assertEquals(1, returnedRoomDTOList.size());
        assertEquals("Garden", returnedRoomDTOList.get(0).roomName);

        // Verify that the self link is added to each RoomDTO
        returnedRoomDTOList.forEach(roomDTO -> {
            assertNotNull(roomDTO.getLinks());
            assertTrue(roomDTO.getLinks().hasLink("self"));
        });
    }

    /**
     * Test to get a list of inside rooms of a house that is not in repository.
     * It verifies that the controller returns an appropriate error response.
     */
    @Test
    public void failGetListOfRoomsDTOInsideWithoutHouseID() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        HouseID houseID = new HouseID("House1");

        //Mock behaviour of houseService
        when(houseService.getHouseIDFromRepository()).thenReturn(houseID);

        // Mock behavior of roomService
        when(roomService.getListOfRoomsInsideOrOutsideHouse(houseID, true)).thenThrow(new HouseNotFoundException());

        // Invoke the method under test
        ResponseEntity<Object> response = houseControllerWeb.getListOfRoomsInHouse("House1",true);

        // Verify the result
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("House not found in Repository", response.getBody());
    }

    /**
     * Test to get a list of all devices in a house.
     * It verifies that the controller returns the appropriate HTTP response.
     */
    @Test
    void successGetListOfAllDevicesInHouse() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        HouseID houseID = new HouseID("House001");

        // Mock behaviour of service
        InternalDeviceDTO internalDeviceDTO1 = new InternalDeviceDTO(new DeviceID("Device001"), new DeviceModel("Model1"), DeviceStatus.ACTIVE, new RoomID("Room001"));
        InternalDeviceDTO internalDeviceDTO2 = new InternalDeviceDTO(new DeviceID("Device002"), new DeviceModel("Model2"), DeviceStatus.ACTIVE, new RoomID("Room002"));

        List<InternalDeviceDTO> internalDeviceDTOList = Arrays.asList(internalDeviceDTO1, internalDeviceDTO2);
        when(deviceService.getListOfDevicesInHouse(houseID)).thenReturn(internalDeviceDTOList);

        // Act
        ResponseEntity<Object> response = houseControllerWeb.getListOfAllDevicesInHouse("House001");
        List<DeviceDTO> responseList = (List<DeviceDTO>) response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the self link is added to each RoomDTO
        responseList.forEach(roomDTO -> {
            assertNotNull(roomDTO.getLinks());
            assertTrue(roomDTO.getLinks().hasLink("self"));
        });
    }

    /**
     * Test that verifies if the DeviceWebController correctly retrieves the devices associated with a roomID.
     */
    @Test
    void getValidMapOfDevicesDTOGroupedByFunctionalityAndRoom() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        RoomDTO roomDTO1 = new RoomDTO("Room 1");
        RoomDTO roomDTO2 = new RoomDTO("Room 2");
        RoomID roomID1 = new RoomID(roomDTO1.roomName);
        RoomID roomID2 = new RoomID(roomDTO2.roomName);
        DeviceDTO deviceDTO1 = new DeviceDTO("BlindRoller");
        DeviceDTO deviceDTO2 = new DeviceDTO("Position");
        DeviceID deviceID1 = new DeviceID(deviceDTO1.deviceName);
        DeviceID deviceID2 = new DeviceID(deviceDTO2.deviceName);
        ActuatorFunctionalityDTO actuatorFunctionalityDTO = new ActuatorFunctionalityDTO("BlindSetter");
        ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID(actuatorFunctionalityDTO.actuatorFunctionalityName);
        SensorFunctionalityDTO sensorFunctionalityDTO = new SensorFunctionalityDTO("Scale");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorFunctionalityDTO.sensorFunctionalityName);

        //Setting behaviour of service and mapper
        when(actuatorFunctionalityMapperDTO.actuatorFunctionalityToDTO(actuatorFunctionalityID)).thenReturn(actuatorFunctionalityDTO);
        when(sensorFunctionalityMapperDTO.sensorFunctionalityToDTO(sensorFunctionalityID)).thenReturn(sensorFunctionalityDTO);
        when(roomMapperDTO.roomToDTO(roomID1)).thenReturn(roomDTO1);
        when(roomMapperDTO.roomToDTO(roomID2)).thenReturn(roomDTO2);
        when(deviceMapperDTO.deviceToDTO(deviceID1)).thenReturn(deviceDTO1);
        when(deviceMapperDTO.deviceToDTO(deviceID2)).thenReturn(deviceDTO2);

        Map<Object, Map<RoomID, DeviceID>> mapService = new HashMap<>();
        Map<RoomID, DeviceID> mapRoomDevice = new HashMap<>();
        mapRoomDevice.put(roomID1, deviceID1);
        mapService.put(actuatorFunctionalityID, mapRoomDevice);
        mapService.put(sensorFunctionalityID, mapRoomDevice);

        when(listAllDevicesInHouseByFunctionalityService.devicesGroupedByFunctionalityAndLocation()).thenReturn(mapService);

        ResponseEntity<Object> responseEntity = houseControllerWeb.handleHouseQuery(true, null, null, null, null);
        Map<Object, Map<RoomDTO, DeviceDTO>> result = (Map<Object, Map<RoomDTO, DeviceDTO>>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, result.size());

        result.forEach((key, value) -> {
            // Assert each value map contains the expected type of objects
            value.forEach((RoomDTO, DeviceDTO) -> {
                assertInstanceOf(DeviceDTO.class, deviceDTO1);
                assertInstanceOf(DeviceDTO.class, deviceDTO2);
                assertInstanceOf(RoomDTO.class, roomDTO1);
                assertInstanceOf(RoomDTO.class, roomDTO2);
                assertTrue(DeviceDTO.getLinks().hasLink("self"));
            });
        });
    }

    /**
     * Test that verifies if the DeviceWebController correctly retrieves an empty.
     */
    @Test
    void getEmptyMapWhenNoDevicesExists() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        RoomDTO roomDTO = new RoomDTO("Room 1");
        RoomID roomID = new RoomID(roomDTO.roomName);

        //Setting behaviour of service and mapper
        when(roomMapperDTO.roomToDTO(roomID)).thenReturn(roomDTO);

        Map<Object, Map<RoomID, DeviceID>> mapService = new HashMap<>();

        when(listAllDevicesInHouseByFunctionalityService.devicesGroupedByFunctionalityAndLocation()).thenReturn(mapService);

        ResponseEntity<Object> responseEntity = houseControllerWeb.handleHouseQuery(true, null, null, null, null);
        Map<Object, Map<RoomDTO, DeviceDTO>> result = (Map<Object, Map<RoomDTO, DeviceDTO>>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, result.size());
    }

   /**
     * Test to get the maximum temperature difference between an inside and outside sensor,
     * using valid parameters, returns a valid result and OK HTTP Status.
     */
    @Test
    void successfullyFindMaxTempDifOutsideInside () {
        //Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        //Create valid objects passed as parameters
        String insideSensorIDString = "InsideSensor";
        String outsideSensorIDString = "OutsideSensor";
        LocalDateTime givenStart = LocalDateTime.of(2024, 5, 18, 0, 0);
        LocalDateTime givenEnd = LocalDateTime.now();

        //Create objects used in service method calls
        SensorID insideSensorID = new SensorID(insideSensorIDString);
        SensorID outsideSensorID = new SensorID(outsideSensorIDString);
        Timestamp start = Timestamp.valueOf(givenStart);
        Timestamp end = Timestamp.valueOf(givenEnd);

        //Create object to be returned by service method call
        double serviceResponse = 20.0;
        //Set behaviour of service method call
        when(maxTempDifOutsideInsideService.getMaxTemperatureDifference(insideSensorID, outsideSensorID, start, end)).thenReturn(serviceResponse);

        //Test controller method
        ResponseEntity<Object> controllerResponse = houseControllerWeb.handleHouseQuery(null, givenStart, givenEnd, insideSensorIDString, outsideSensorIDString);

        //Verify response content (payload + HTTP status code)
        assertEquals(controllerResponse.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(controllerResponse.getBody(), 20.0);
    }

    /**
     * Test to get the maximum temperature difference between an inside and outside sensor,
     * using an invalid response from the service, returns an error message and BAD REQUEST HTTP Status.
     */
    @Test
    void failFindMaxTempDifOutsideInside () {
        //Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        //Create valid objects passed as parameters
        String insideSensorIDString = "InsideSensor";
        String outsideSensorIDString = "OutsideSensor";
        LocalDateTime givenStart = LocalDateTime.of(2024, 5, 18, 0, 0);
        LocalDateTime givenEnd = LocalDateTime.now();

        //Create objects used in service method calls
        SensorID insideSensorID = new SensorID(insideSensorIDString);
        SensorID outsideSensorID = new SensorID(outsideSensorIDString);
        Timestamp start = Timestamp.valueOf(givenStart);
        Timestamp end = Timestamp.valueOf(givenEnd);

        //Create object to be returned by service method call
        double serviceResponse = -1.0;
        //Set behaviour of service method call
        when(maxTempDifOutsideInsideService.getMaxTemperatureDifference(insideSensorID, outsideSensorID, start, end)).thenReturn(serviceResponse);

        //Test controller method
        ResponseEntity<Object> controllerResponse = houseControllerWeb.handleHouseQuery(null, givenStart, givenEnd, insideSensorIDString, outsideSensorIDString);

        //Verify response content (payload + HTTP status code)
        assertEquals(HttpStatus.BAD_REQUEST, controllerResponse.getStatusCode());
        assertEquals(controllerResponse.getBody(), "Failed to calculate difference in set period");
    }

    /**
     * Test if the method returns the expected result when the service method returns a valid value.
     * The expected result is the peak power consumption in the given period.
     */
    @Test
    void successfullyGetPeakPowerConsumption() {
        // Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Define the input parameters
        LocalDateTime givenStart = LocalDateTime.of(2024, 4, 1, 12, 15);
        LocalDateTime givenEnd = LocalDateTime.of(2025, 5, 1, 12, 40);

        Timestamp startPeriod = Timestamp.valueOf(givenStart);
        Timestamp endPeriod = Timestamp.valueOf(givenEnd);

        // Define the expected result
        double expectedPeakPower = 100.0;

        // Define the behaviour of the service method
        when(valueService.getPeakPowerConsumption(startPeriod, endPeriod)).thenReturn(expectedPeakPower);

        // Call the controller method
        ResponseEntity<Object> response = houseControllerWeb.handleHouseQuery(null, givenStart, givenEnd, null, null);

        // Verify the response
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(response.getBody(), expectedPeakPower);
    }

    /**
     * Tests the {@link HouseControllerWeb#getWeatherInfo(Integer, String)} method.
     * This test verifies if the method returns the expected temperature for a given hour.
     * The expected result is a ResponseEntity with HttpStatus.OK and the expected temperature as the body.
     */
    @Test
    void getTemperatureForHourSuccess() {
        // Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Arrange
        int hour = 10;
        double expectedTemperature = 20.0;
        when(weatherAPIService.getTemperatureForHour(hour)).thenReturn(expectedTemperature);

        // Act
        ResponseEntity<Object> response = houseControllerWeb.getWeatherInfo(hour, null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTemperature, response.getBody());
    }

    /**
     * Tests the {@link HouseControllerWeb#getWeatherInfo(Integer, String)} method.
     * This test verifies if the method returns an error when the hour is invalid.
     * The expected result is a ResponseEntity with HttpStatus.BAD_REQUEST and an error message as the body.
     */
    @Test
    void getTemperatureForHourInvalidHour() {
        // Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Arrange
        int hour = 25;
        String expectedErrorMessage = "Invalid hour value. Hour must be between 0 and 23.";
        when(weatherAPIService.getTemperatureForHour(hour)).thenThrow(new IllegalArgumentException(expectedErrorMessage));

        // Act
        ResponseEntity<Object> response = houseControllerWeb.getWeatherInfo(hour, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedErrorMessage, response.getBody());
    }

    /**
     * Test to get the maximum temperature difference between an inside sensor and outside,
     * using valid parameters, returns a valid result and OK HTTP Status.
     */
    @Test
    void successfullyFindMaxTempDifOutsideInsideWeatherApi () {
        //Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        //Create valid objects passed as parameters
        String insideSensorIDString = "InsideSensor";
        LocalDateTime givenStart = LocalDateTime.of(2024, 5, 18, 0, 0);
        LocalDateTime givenEnd = LocalDateTime.now();

        //Create objects used in service method calls
        SensorID insideSensorID = new SensorID(insideSensorIDString);
        Timestamp start = Timestamp.valueOf(givenStart);
        Timestamp end = Timestamp.valueOf(givenEnd);

        //Create object to be returned by service method call
        double serviceResponse = 10.0;
        //Set behaviour of service method call
        when(weatherAPIService.getMaxTemperatureDifferenceWithWeatherService(insideSensorID, start, end)).thenReturn(serviceResponse);

        //Test controller method
        ResponseEntity<Object> controllerResponse = houseControllerWeb.handleHouseQuery(null, givenStart, givenEnd, insideSensorIDString, null);

        //Verify response content (payload + HTTP status code)
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        assertEquals(controllerResponse.getBody(), 10.0);
    }

    /**
     * Test to get the maximum temperature difference between an inside sensor and outside,
     * using an invalid response from the service, returns an error message and BAD REQUEST HTTP Status.
     */
    @Test
    void failToFindMaxTempDifOutsideInsideWeatherApi () {
        //Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        //Create valid objects passed as parameters
        String insideSensorIDString = "InsideSensor";
        LocalDateTime givenStart = LocalDateTime.of(2024, 5, 18, 0, 0);
        LocalDateTime givenEnd = LocalDateTime.now();

        //Create objects used in service method calls
        SensorID insideSensorID = new SensorID(insideSensorIDString);
        Timestamp start = Timestamp.valueOf(givenStart);
        Timestamp end = Timestamp.valueOf(givenEnd);

        //Create object to be returned by service method call
        double serviceResponse = -1.0;
        //Set behaviour of service method call
        when(weatherAPIService.getMaxTemperatureDifferenceWithWeatherService(insideSensorID, start, end)).thenReturn(serviceResponse);

        //Test controller method
        ResponseEntity<Object> controllerResponse = houseControllerWeb.handleHouseQuery(null, givenStart, givenEnd, insideSensorIDString, null);

        //Verify response content (payload + HTTP status code)
        assertEquals(HttpStatus.BAD_REQUEST, controllerResponse.getStatusCode());
        assertEquals(controllerResponse.getBody(), "Failed to calculate difference in set period");
    }

    /**
     * Tests the {@link HouseControllerWeb#getWeatherInfo(Integer, String)} method.
     * This test verifies if the method returns the expected given hour for Sunrise.
     * The expected result is a ResponseEntity with HttpStatus.OK and the expected temperature as the body.
     */
    @Test
    void getSunriseSuccess() {
        // Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Arrange
        String option = "sunrise";
        double expectedHour = 7.02;
        when(weatherAPIService.getSunriseSunsetHour(option)).thenReturn(expectedHour);

        // Act
        ResponseEntity<Object> response = houseControllerWeb.getWeatherInfo(null, option);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHour, response.getBody());
    }

    /**
     * Tests the {@link HouseControllerWeb#getWeatherInfo(Integer, String)} method.
     * This test verifies if the method returns the expected given hour for Sunset.
     * The expected result is a ResponseEntity with HttpStatus.OK and the expected temperature as the body.
     */
    @Test
    void getSunsetSuccess() {
        // Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        String option = "sunset";
        double expectedHour = 18.48;
        when(weatherAPIService.getSunriseSunsetHour(option)).thenReturn(expectedHour);

        ResponseEntity<Object> response = houseControllerWeb.getWeatherInfo(null, option);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHour, response.getBody());
    }

    /**
     * Tests the {@link HouseControllerWeb#getWeatherInfo(Integer, String)} method.
     * This test verifies if the method returns the expected given hour for Sunrise.
     * The expected result is a ResponseEntity with HttpStatus.BAD_REQUEST and the error message.
     */
    @Test
    void failToGetSunriseSunsetInvalidOption() {
        // Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        String option = "InvalidOption";
        when(weatherAPIService.getSunriseSunsetHour(option)).thenThrow(new IllegalArgumentException("Invalid option. Option must be either 'sunrise' or 'sunset'."));

        ResponseEntity<Object> response = houseControllerWeb.getWeatherInfo(null, option);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Invalid option. Option must be either 'sunrise' or 'sunset'.");
    }

    /**
     * Tests the {@link HouseControllerWeb#getWeatherInfo(Integer, String)} method.
     * This test verifies if the method returns an error message iff nothing is chosen.
     * The expected result is a ResponseEntity with HttpStatus.BAD_REQUEST and the error message.
     */
    @Test
    void failToGetWeatherInfoNothingChosen() {
        // Mock the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        ResponseEntity<Object> response = houseControllerWeb.getWeatherInfo(null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Either hour or option must be provided");
    }
}