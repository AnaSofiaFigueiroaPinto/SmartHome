package smarthome.controllerweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.mapper.SensorFunctionalityDTO;
import smarthome.service.SensorFunctionalityService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code SensorFunctionalityControllerWeb} class.
 */
@SpringBootTest(classes = {SensorFunctionalityControllerWeb.class})
class SensorFunctionalityControllerWebTest {

    /**
     * The SensorFunctionalityService attribute.
     */
    @MockBean
    SensorFunctionalityService sensorFunctionalityService;

    /**
     * The SensorFunctionalityControllerWeb attribute.
     */
    @InjectMocks
    SensorFunctionalityControllerWeb sensorFunctionalityControllerWeb;

    /**
     * Set up needed before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Successful test case for the {@code getListOfSensorFunctionalities} method of the {@code SensorFunctionalityControllerWeb} class.
     * Returns a list of sensor functionalities DTO.
     */
    @Test
    void successfulGetListOfSensorFunctionalities() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<SensorFunctionalityID> mockedSensorFunctionalities = List.of(
                new SensorFunctionalityID("Temperature"),
                new SensorFunctionalityID("Humidity")
        );

        when(sensorFunctionalityService.getListOfSensorFunctionalities()).thenReturn(mockedSensorFunctionalities);

        List<SensorFunctionalityDTO> expectedDTOList = List.of(
                new SensorFunctionalityDTO("Temperature"),
                new SensorFunctionalityDTO("Humidity")
        );

        ResponseEntity<CollectionModel<EntityModel<SensorFunctionalityDTO>>> responseEntity = sensorFunctionalityControllerWeb.getListOfSensorFunctionalities();

        List<SensorFunctionalityDTO> actualDTOList = responseEntity.getBody().getContent().stream()
                .map(EntityModel::getContent)
                .collect(Collectors.toList());

        assertEquals(expectedDTOList, actualDTOList);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }



    /**
     * Failed test case for the {@code getListOfSensorFunctionalities} method of the {@code SensorFunctionalityControllerWeb} class.
     * Returns an empty list of sensor functionalities DTO.
     */
    @Test
    void failedGetListOfSensorFunctionalities() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(sensorFunctionalityService.getListOfSensorFunctionalities()).thenReturn(List.of());

        List<SensorFunctionalityDTO> expectedDTOList = List.of();

        ResponseEntity<CollectionModel<EntityModel<SensorFunctionalityDTO>>> responseEntity = sensorFunctionalityControllerWeb.getListOfSensorFunctionalities();

        List<SensorFunctionalityDTO> actualDTOList = responseEntity.getBody().getContent().stream()
                .map(EntityModel::getContent)
                .collect(Collectors.toList());

        assertEquals(expectedDTOList, actualDTOList);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Successful test case for the {@code getSensorFunctionality} method of the {@code SensorFunctionalityControllerWeb} class.
     * Returns a sensor functionality DTO.
     */
    @Test
    void successfulGetSensorFunctionality() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<SensorFunctionalityID> mockedSensorFunctionalities = List.of(
                new SensorFunctionalityID("TemperatureCelsius"),
                new SensorFunctionalityID("HumidityPercentage")
        );

        when(sensorFunctionalityService.getListOfSensorFunctionalities()).thenReturn(mockedSensorFunctionalities);

        SensorFunctionalityDTO expectedDTO = new SensorFunctionalityDTO("TemperatureCelsius");

        ResponseEntity<SensorFunctionalityDTO> responseEntity = sensorFunctionalityControllerWeb.getSensorFunctionality("TemperatureCelsius");

        SensorFunctionalityDTO actualDTO = responseEntity.getBody();

        // Remove the links for comparison purposes
        expectedDTO.removeLinks();
        actualDTO.removeLinks();

        assertEquals(expectedDTO, actualDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Failed test case for the {@code getSensorFunctionality} method of the {@code SensorFunctionalityControllerWeb} class.
     * Returns a not found status.
     */
    @Test
    void failedGetSensorFunctionality() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<SensorFunctionalityID> mockedSensorFunctionalities = List.of(
                new SensorFunctionalityID("TemperatureCelsius"),
                new SensorFunctionalityID("HumidityPercentage")
        );

        when(sensorFunctionalityService.getListOfSensorFunctionalities()).thenReturn(mockedSensorFunctionalities);

        ResponseEntity<SensorFunctionalityDTO> responseEntity = sensorFunctionalityControllerWeb.getSensorFunctionality("Temperature");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}