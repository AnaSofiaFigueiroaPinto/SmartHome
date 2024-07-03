package smarthome.controllerweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.*;
import smarthome.service.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {DeviceControllerWeb.class})
class DeviceControllerWebTest {
    /**
     * DeviceService double to be used in the tests.
     */
    @MockBean
    private DeviceService deviceService;

    /**
     * CloseBlindRollerService double to be used in the tests
     */
    @MockBean
    private CloseBlindRollerService closeBlindRollerService;

    /**
     * SensorService double to be used in the tests.
     */
    @MockBean
    private SensorService sensorService;

    /**
     * SensorMapperDTO double to be used in the tests.
     */
    @MockBean
    private SensorMapperDTO sensorMapperDTO;

    /**
     * ActuatorService double to be used in the tests.
     */
    @MockBean
    private ActuatorService actuatorService;

    /**
     * ActuatorMapperDTO double be used in the tests.
     */
    @MockBean
    private ActuatorMapperDTO actuatorMapperDTO;

    /**
     * MockBean of ListValuesForDeviceService used by ValueControllerWeb
     */
    @MockBean
    private ValueService valueService;

    /**
     * MockBean of SensorFunctionalityMapperDTO used by ValueControllerWeb
     */
    @MockBean
    private SensorFunctionalityMapperDTO mapperSensorFunc;

    /**
     * MockBean of ReadingMapperDTO used by ValueControllerWeb
     */
    @MockBean
    private ReadingMapperDTO mapperReading;

    /**
     * {@code DeviceWebController} class under test.
     */
    @InjectMocks
    private DeviceControllerWeb deviceControllerWeb;


    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test the successful creation of a Device with valid parameters.
     */
    @Test
    void successCreateADeviceAndSaveToRepository() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        DeviceDTO deviceDTO = new DeviceDTO("BlindRoller", "B8115", "ACTIVE", "Room 1");
        DeviceID deviceID = new DeviceID(deviceDTO.deviceName);
        DeviceModel deviceModel = new DeviceModel(deviceDTO.model);
        RoomID roomID = new RoomID(deviceDTO.roomName);
        DeviceID deviceIDCreated = new DeviceID("BlindRoller");

        //Setting behaviour of service
        when(deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID)).thenReturn(deviceIDCreated);

        ResponseEntity<Object> responseEntity = deviceControllerWeb.createDeviceAndSaveToRepository(deviceDTO);
        DeviceDTO result = (DeviceDTO) responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("BlindRoller", result.deviceName);
    }

    /**
     * Test fails when trying to create a Device with invalid DTO parameters.
     */
    @Test
    void failCreateADeviceWithInvalidDeviceDTOInfo() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        ResponseEntity<Object> responseEntity = deviceControllerWeb.createDeviceAndSaveToRepository(null);
        String errorMessage = "DeviceDTO cannot be null or empty";

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     * Test that verifies if the DeviceWebController correctly deactivate a device.
     */
    @Test
    void successfullyDeactivateDevice() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        String deviceName = "BlindRoller";
        DeviceID deviceID = new DeviceID(deviceName);

        //Setting behaviour of service
        when(deviceService.deactivateDevice(deviceID)).thenReturn(true);

        ResponseEntity<Object> responseEntity = deviceControllerWeb.editDevice(deviceName, null);
        Boolean result = (Boolean) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, result);
    }

    /**
     * Test that verifies if the DeviceWebController fails to deactivate a device that not exists in repository or its already deactivated.
     */
    @Test
    void failToDeactivateNotFoundOrDeactivateDevice() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        String deviceName = "BlindRoller";
        DeviceID deviceID = new DeviceID(deviceName);

        //Setting behaviour of service
        when(deviceService.deactivateDevice(deviceID)).thenReturn(false);

        ResponseEntity<Object> responseEntity = deviceControllerWeb.editDevice(deviceName, null);
        Boolean result = (Boolean) responseEntity.getBody();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(false, result);
    }

    /**
     * Test that verifies if the DeviceWebController fails to deactivate an invalid device.
     */
    @Test
    void failToDeactivateInvalidDevice() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        ResponseEntity<Object> responseEntity = deviceControllerWeb.editDevice(null, null);
        String errorMessage = "Device name (string) is an ID and cannot be null or empty";

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     * Tests the listing of temperature sensors in a device through the SensorWebController.
     * This test verifies that the SensorWebController correctly lists temperature sensors associated with a device.
     */
    @Test
    void testValidFilledListTemperatureSensorsInDevice() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Mocking necessary DTO and ID objects
        String deviceID = "device";
        String sensorFunctionalityID = "TemperatureCelsius";
        DeviceID deviceIDObject = new DeviceID(deviceID);
        SensorFunctionalityID sensorFunctionalityIDObject = new SensorFunctionalityID(sensorFunctionalityID);
        SensorDTO sensorDTO = new SensorDTO("sensor", "device", "TemperatureCelsius");
        SensorID sensorID = new SensorID(sensorDTO.sensorName);

        // Mocking service responses
        List<SensorID> sensorIDList = List.of(sensorID);
        when(sensorService.findSensorsIDOfADeviceBySensorFunctionality(deviceIDObject, sensorFunctionalityIDObject)).thenReturn(sensorIDList);
        List<SensorDTO> sensorDTOs = List.of(sensorDTO);
        when(sensorMapperDTO.sensorIDsToDTOList(sensorIDList)).thenReturn(sensorDTOs);

        // Performing the request to list temperature sensors in the device
        ResponseEntity<Object> responseEntity = deviceControllerWeb.getListOfSensors(deviceID, sensorFunctionalityID);
        CollectionModel<SensorDTO> collectionModel = (CollectionModel<SensorDTO>) responseEntity.getBody();

        // Asserting the response entity and its content
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(collectionModel);
        //Converting the Collections into a list
        List<SensorDTO> result = new ArrayList<>(collectionModel.getContent());
        assertEquals(1, result.size());
        assertEquals("sensor", result.get(0).sensorName);
        assertEquals("device", result.get(0).deviceID);
        assertEquals("TemperatureCelsius", result.get(0).functionalityID);
    }

    /**
     * Tests the case when there are no temperature sensors associated with a device.
     * It verifies that the controller returns an empty list as expected.
     */
    @Test
    void testValidEmptyListTemperatureSensorsInDevice() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Mocking necessary DTO and ID objects
        String deviceID = "device";
        String sensorFunctionalityID = "TemperatureCelsius";

        // Performing the request to list temperature sensors in the device
        ResponseEntity<Object> responseEntity = deviceControllerWeb.getListOfSensors(deviceID, sensorFunctionalityID);
        CollectionModel<SensorDTO> collectionModel = (CollectionModel<SensorDTO>) responseEntity.getBody();

        // Asserting the response entity and its content
        assertNotNull(collectionModel);
        //Converting the Collections into a list
        List<SensorDTO> result = new ArrayList<>(collectionModel.getContent());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, result.size());
    }

    /**
     * Test to verify the successful action of closing a blind roller device to a specific percentage
     */
    @Test
    void testSuccessCloseBlindRollerDevice() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String deviceName = "deviceID";
        DeviceID deviceID = new DeviceID(deviceName);
        ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID("BlindSetter");
        int closePercentage = 20;

        when(closeBlindRollerService.setActuatorStateOfBlindRoller(deviceID, actuatorFunctionalityID, closePercentage)).thenReturn(true);

        ResponseEntity<Object> result = deviceControllerWeb.editDevice(deviceName, closePercentage);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    /**
     * Test case to verify the failing attempt when closing a blind roller device there isn't active.
     */
    @Test
    void testFailCloseBlindRollerDeviceWhenDeviceNotActive() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String deviceName = "deviceID";
        DeviceID deviceID = new DeviceID(deviceName);
        ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID("BlindSetter");
        int closePercentage = 20;

        when(closeBlindRollerService.setActuatorStateOfBlindRoller(deviceID, actuatorFunctionalityID, closePercentage)).thenReturn(false);

        ResponseEntity<Object> result = deviceControllerWeb.editDevice(deviceName, closePercentage);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
    }

    /**
     * Test case that verifies that an "Unprocessable_entity" status is returned when the actuator fails to change the
     * status of the blind roller device.
     */
    @Test
    void testFailCloseBlindRollerDeviceWhenActuatorFailsToChangeBlindRollerDeviceStatus() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String deviceName = "deviceID";
        DeviceID deviceID = new DeviceID(deviceName);
        ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID("BlindSetter");
        int closePercentage = 20;

        when(closeBlindRollerService.setActuatorStateOfBlindRoller(deviceID, actuatorFunctionalityID, closePercentage)).thenReturn(false);

        ResponseEntity<Object> result = deviceControllerWeb.editDevice(deviceName, closePercentage);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
    }

    /**
     * Test case that verifies that an "Unprocessable_entity" status is returned when the deviceID parameter is empty.
     */
    @Test
    void testFailCloseBlindRollerDeviceWhenDeviceIDIsEmpty() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String deviceID = " ";
        int closePercentage = 20;

        ResponseEntity<Object> result = deviceControllerWeb.editDevice(deviceID, closePercentage);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
    }

    /**
     * Test case that verifies that an "Unprocessable_entity" status is returned when the deviceID parameter is null.
     */
    @Test
    void testFailCloseBlindRollerDeviceWhenDeviceIDIsNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String deviceID = null;
        int closePercentage = 20;

        ResponseEntity<Object> result = deviceControllerWeb.editDevice(deviceID, closePercentage);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
    }

     /**
     * Test method to validate capability to list all values read by sensors in a Device for a given period.
     * Calls method with valid parameters and checks if the response is as expected.
     * Expected response must contain HTTP code 200 (OK) and the DTO content (Keys and Values)
     */
     @Test
     void successfullyGetListAllValuesForDevice () {
         //Mock the HTTP request
         MockHttpServletRequest request = new MockHttpServletRequest();
         RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

         //Create variables passed to controller method as url parameters
         String deviceIDAsString = "Device1";
         LocalDateTime givenStart = LocalDateTime.of(2021, 1, 1, 0, 0);
         LocalDateTime givenEnd = LocalDateTime.now();

         //Create a standard map response from service method call
         Map<SensorFunctionalityID, List<Reading>> serviceResponse = new HashMap<>();

         //Sensor functionalities
         SensorFunctionalityID sensorFunctionalityID1 = new SensorFunctionalityID("Func1");
         SensorFunctionalityID sensorFunctionalityID2 = new SensorFunctionalityID("Func2");

         //Readings
         //Func 1
         Reading reading1 = new Reading("1.0", "Unit1");
         Reading reading2 = new Reading("2.0", "Unit2");
         List<Reading> readingsFunc1 = List.of(reading1, reading2);

         //Func 2
         Reading reading3 = new Reading("3.0", "Unit3");
         List<Reading> readingsFunc2 = List.of(reading3);

         serviceResponse.put(sensorFunctionalityID1, readingsFunc1);
         serviceResponse.put(sensorFunctionalityID2, readingsFunc2);

         //Create value objects passed to service method call based on input parameters so that behaviour can be created
         DeviceID deviceID = new DeviceID(deviceIDAsString);
         Timestamp start = Timestamp.valueOf(givenStart);
         Timestamp end = Timestamp.valueOf(givenEnd);

         //Create behaviour for service method call
         when(valueService.getAllMeasurementsForDeviceBetweenPeriod(deviceID, start, end)).thenReturn(serviceResponse);

         //Create behaviour for mapper method calls
         //SensorFunctionality
         SensorFunctionalityDTO sensorFunctionalityDTO1 = new SensorFunctionalityDTO("Func1");
         SensorFunctionalityDTO sensorFunctionalityDTO2 = new SensorFunctionalityDTO("Func2");

         when(mapperSensorFunc.sensorFunctionalityToDTO(sensorFunctionalityID1)).thenReturn(sensorFunctionalityDTO1);
         when(mapperSensorFunc.sensorFunctionalityToDTO(sensorFunctionalityID2)).thenReturn(sensorFunctionalityDTO2);

         //Reading
         ReadingDTO readingDTO1 = new ReadingDTO("1.0 Unit1");
         ReadingDTO readingDTO2 = new ReadingDTO("2.0 Unit2");
         ReadingDTO readingDTO3 = new ReadingDTO("3.0 Unit3");

         when(mapperReading.readingsToDTOList(readingsFunc1)).thenReturn(List.of(readingDTO1, readingDTO2));
         when(mapperReading.readingsToDTOList(readingsFunc2)).thenReturn(List.of(readingDTO3));

         ResponseEntity<Object> response = deviceControllerWeb.getObjectByDeviceID(deviceIDAsString, givenStart, givenEnd, null);

         //Assert HTTP code response (OK => 200)
         assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

         //Assert that content of response is as expected
         Map<SensorFunctionalityDTO, List<ReadingDTO>> responseContent = (Map<SensorFunctionalityDTO, List<ReadingDTO>>) response.getBody();

         //See if both keys (SensorFunctionalityDTO) are present in the response
         assertTrue(responseContent.containsKey(sensorFunctionalityDTO1));
         assertTrue(responseContent.containsKey(sensorFunctionalityDTO2));

         //See if both values (List<ReadingDTO>) are present in the response
         List<ReadingDTO> listReadingDTO1FromResponse = responseContent.get(sensorFunctionalityDTO1);
         assertTrue(listReadingDTO1FromResponse.containsAll(List.of(readingDTO1, readingDTO2)));

         List<ReadingDTO> listReadingDTO2FromResponse = responseContent.get(sensorFunctionalityDTO2);
         assertTrue(listReadingDTO2FromResponse.contains(readingDTO3));
     }
}