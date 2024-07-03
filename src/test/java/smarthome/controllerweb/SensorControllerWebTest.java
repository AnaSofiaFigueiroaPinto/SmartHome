package smarthome.controllerweb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.mapper.SensorDTO;
import smarthome.service.internaldto.InternalSensorDTO;
import smarthome.service.SensorService;
import smarthome.util.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SensorControllerWeb.class)
class SensorControllerWebTest {

    /**
     * SensorService double to be used in the tests.
     */
    @MockBean
    private SensorService sensorService;

    /**
     * {@code SensorWebController} class under test.
     */
    @InjectMocks
    private SensorControllerWeb sensorWebController;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the successful addition of a sensor to a device.
     * It verifies that the controller returns a successful response with the correct sensor information.
     */
    @Test
    void testSuccessfulAddSensorToDevice() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Define test data
        SensorDTO sensorInfo = new SensorDTO("sensor", "device", "TemperatureCelsius");
        DeviceID deviceID = new DeviceID(sensorInfo.deviceID);
        SensorID createdSensorID = new SensorID(sensorInfo.sensorName);
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorInfo.functionalityID);

        // Mock the behavior of the service layer method to create and save the sensor
        when(sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, createdSensorID)).thenReturn(createdSensorID);

        // Call the method under test to add the sensor to the device
        ResponseEntity<Object> responseEntity = sensorWebController.addSensorToDevice(sensorInfo);
        SensorDTO result = (SensorDTO) responseEntity.getBody();

        // Verify the response entity and its content
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("sensor", result.sensorName);
        assertEquals("device", result.deviceID);
        assertEquals("TemperatureCelsius", result.functionalityID);
    }

    /**
     * Tests the scenario where adding a sensor to a device fails due to an empty sensor functionality.
     * It verifies that the controller returns an appropriate error response.
     */
    @Test
    void testFailToAddSensorToDeviceEmptySensorFunctionality() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Define test data
        SensorDTO sensorInfo = new SensorDTO("sensor", "device", "");

        // Call the method under test to add the sensor to the device, which is expected to fail
        ResponseEntity<Object> responseEntity = sensorWebController.addSensorToDevice(sensorInfo);
        String errorMessage = "Sensor Functionality ID cannot be null or empty";

        // Verify the response entity and its content
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     * Tests the scenario where adding a sensor to a device fails due to an empty sensor name.
     * It verifies that the controller returns an appropriate error response.
     */
    @Test
    void testFailToAddSensorToDeviceEmptySensorName() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Define test data
        SensorDTO sensorInfo = new SensorDTO("", "device", "TemperatureCelsius");

        // Call the method under test to add the sensor to the device, which is expected to fail
        ResponseEntity<Object> responseEntity = sensorWebController.addSensorToDevice(sensorInfo);
        String errorMessage = "Sensor name (string) is an ID and cannot be null or empty";

        // Verify the response entity and its content
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     * Tests the scenario where adding a sensor to a device fails due to an empty device name.
     * It verifies that the controller returns an appropriate error response.
     */
    @Test
    void testFailToAddSensorToDeviceEmptyDeviceID() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Define test data
        SensorDTO sensorInfo = new SensorDTO("sensor", "", "TemperatureCelsius");

        // Call the method under test to add the sensor to the device, which is expected to fail
        ResponseEntity<Object> responseEntity = sensorWebController.addSensorToDevice(sensorInfo);
        String errorMessage = "Device name (string) is an ID and cannot be null or empty";

        // Verify the response entity and its content
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     * Tests the scenario where adding a sensor to a device fails due to a null DeviceDTO.
     * It verifies that the controller returns an appropriate error response.
     */
    @Test
    void testFailToAddSensorToDeviceNullDeviceDTO() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Call the method under test to add the sensor to the device, which is expected to fail
        ResponseEntity<Object> responseEntity = sensorWebController.addSensorToDevice(null);
        String errorMessage = "SensorDTO cannot be null or empty";
        // Verify the response entity and its content
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     Tests the scenario where adding a sensor to a device fails due to the sensor functionality not being present in the list.
     * It verifies that the controller returns an appropriate error response.
     */
    @Test
    void testFailToAddSensorToDeviceSensorFunctionalityNotInList() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Define test data
        SensorDTO sensorInfo = new SensorDTO("sensor", "device", "unknown");
        DeviceID deviceID = new DeviceID(sensorInfo.deviceID);
        SensorID createdSensorID = new SensorID(sensorInfo.sensorName);
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorInfo.functionalityID);

        // Mock the behavior of the service layer method to create and save the sensor
        when(sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, createdSensorID)).thenThrow(new SensorFunctionalityNotListedException());

        // Call the method under test to add the sensor to the device
        ResponseEntity<Object> responseEntity = sensorWebController.addSensorToDevice(sensorInfo);
        String errorMessage = "Sensor functionality not present in the list";

        // Verify the response entity and its content
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     * Tests the scenario where adding a sensor to a device fails because the device is not found in the repository.
     * It verifies that the controller returns an appropriate error message indicating that the device was not found.
     */
    @Test
    void testFailToAddSensorToDeviceNotInRepository() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Define test data
        SensorDTO sensorInfo = new SensorDTO("sensor", "unknown", "TemperatureCelsius");
        DeviceID deviceID = new DeviceID(sensorInfo.deviceID);
        SensorID createdSensorID = new SensorID(sensorInfo.sensorName);
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorInfo.functionalityID);

        // Mock the behavior of the service layer method to create and save the sensor
        when(sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, createdSensorID)).thenThrow(new DeviceNotFoundException());

        // Call the method under test to add the sensor to the device
        ResponseEntity<Object> responseEntity = sensorWebController.addSensorToDevice(sensorInfo);
        String errorMessage = "Device not found in Repository";

        // Verify the response entity and its content
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     * Tests the scenario where adding a sensor to a device fails because the device is inactive.
     * It verifies that the controller returns an appropriate error message indicating that the device is inactive.
     */
    @Test
    void testFailToAddSensorToInactiveDevice() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Define test data
        SensorDTO sensorInfo = new SensorDTO("sensor", "old device", "TemperatureCelsius");
        DeviceID deviceID = new DeviceID(sensorInfo.deviceID);
        SensorID createdSensorID = new SensorID(sensorInfo.sensorName);
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorInfo.functionalityID);

        // Mock the behavior of the service layer method to create and save the sensor
        when(sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, createdSensorID)).thenThrow(new DeviceInactiveException());

        // Call the method under test to add the sensor to the device
        ResponseEntity<Object> responseEntity = sensorWebController.addSensorToDevice(sensorInfo);
        String errorMessage = "Device is Inactive";

        // Verify the response entity and its content
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     * Tests the scenario where adding a sensor to a device fails because the sensor already exists in the repository.
     * It verifies that the controller returns an appropriate error message indicating that the sensor already exists.
     */
    @Test
    void testFailToAddSensorToDeviceSensorAlreadyInRepository() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Define test data
        SensorDTO sensorInfo = new SensorDTO("thermostat", "device", "TemperatureCelsius");
        DeviceID deviceID = new DeviceID(sensorInfo.deviceID);
        SensorID createdSensorID = new SensorID(sensorInfo.sensorName);
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorInfo.functionalityID);

        // Mock the behavior of the service layer method to create and save the sensor
        when(sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, createdSensorID)).thenThrow(new SensorAlreadyExistsException());

        // Call the method under test to add the sensor to the device
        ResponseEntity<Object> responseEntity = sensorWebController.addSensorToDevice(sensorInfo);
        String errorMessage = "Sensor already exists";

        // Verify the response entity and its content
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    /**
     * Tests the successful retrieval of SensorDTO information by ID.
     * Verifies that the method returns a ResponseEntity with status OK and the correct SensorDTO object.
     */
    @Test
    void successfullyGetSensorByIDSuccess() throws Exception {
        // Arrange
        String sensorName = "testSensor";
        SensorID sensorID = new SensorID(sensorName);
        SensorDTO expectedSensorDTO = new SensorDTO(sensorName, "testDeviceID", "Sunset");
        InternalSensorDTO internalSensorDTO = new InternalSensorDTO(sensorID, new SensorFunctionalityID("Sunset"), new DeviceID("testDeviceID"));
        when(sensorService.findSensorIDInfo(sensorID)).thenReturn(internalSensorDTO);

        // Act
        ResponseEntity<Object> responseEntity = sensorWebController.getSensorByID(sensorName);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        // Convert the actual response body to JSON and remove the _links attribute
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode actualJsonNode = objectMapper.readTree(objectMapper.writeValueAsString(responseEntity.getBody()));
        ((ObjectNode) actualJsonNode).remove("links");

        // Convert the expectedSensorDTO to JSON
        JsonNode expectedJsonNode = objectMapper.readTree(objectMapper.writeValueAsString(expectedSensorDTO));

        // Compare the two JSON nodes
        assertEquals(expectedJsonNode, actualJsonNode);
    }

    /**
     * Tests the scenario where the sensor is not found.
     * Verifies that the method returns a ResponseEntity with status UNPROCESSABLE_ENTITY and the error message.
     */
    @Test
    void failToGetSensorByIDSensorNotFound() {
        // Arrange
        String sensorName = "nonExistentSensor";
        SensorID sensorID = new SensorID(sensorName);
        when(sensorService.findSensorIDInfo(sensorID)).thenThrow(new SensorNotFoundException());

        // Act
        ResponseEntity<Object> responseEntity = sensorWebController.getSensorByID(sensorName);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
}