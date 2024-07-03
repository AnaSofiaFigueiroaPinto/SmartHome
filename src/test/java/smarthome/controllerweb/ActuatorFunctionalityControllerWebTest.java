package smarthome.controllerweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.mapper.*;
import smarthome.service.ActuatorFunctionalityService;
import smarthome.service.CloseBlindRollerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest(classes = ActuatorFunctionalityControllerWeb.class)
class ActuatorFunctionalityControllerWebTest {

    /**
     * Mock of the ActuatorFunctionalityService class.
     */
    @MockBean
    ActuatorFunctionalityService actuatorFunctionalityService;

    /**
     * Mock of the ActuatorFunctionalityMapperDTO class.
     */
    @MockBean
    ActuatorFunctionalityMapperDTO actuatorFunctionalityMapperDTO;

    /**
     * Mock of the ActuatorFunctionalityMapperDTO class.
     */
    @MockBean
    CloseBlindRollerService closeBlindRollerService;

    /**
     * The controller for the ActuatorFunctionality entities.
     */
    @InjectMocks
    ActuatorFunctionalityControllerWeb actuatorFunctionalityControllerWeb;

    /**
     * Method that runs before each test.
     * @throws Exception if something goes wrong.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to verify that the method getListOfActuatorFunctionalities
     * returns a list of ActuatorFunctionalityDTO when the service returns a list of ActuatorFunctionalityID.
     */
    @Test
    void getListOfActuatorFunctionalities() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new ServletRequestAttributes(request));

        // ActuatorFunctionalityID list
        List<ActuatorFunctionalityID> actuatorFunctionalityIDList = new ArrayList<>();
        ActuatorFunctionalityID actuatorFunctionalityID1 = new ActuatorFunctionalityID("switch");
        ActuatorFunctionalityID actuatorFunctionalityID2 = new ActuatorFunctionalityID("actuator");
        actuatorFunctionalityIDList.add(actuatorFunctionalityID1);
        actuatorFunctionalityIDList.add(actuatorFunctionalityID2);


        // Behavior of the service
        when(actuatorFunctionalityService.getListOfActuatorFunctionalities()).thenReturn(actuatorFunctionalityIDList);

        // ActuatorFunctionalityDTO list
        List<ActuatorFunctionalityDTO> actuatorFunctionalityDTOList = new ArrayList<>();

        ActuatorFunctionalityDTO actuatorFunctionalityDTO1 =
                new ActuatorFunctionalityDTO("switch");
        ActuatorFunctionalityDTO actuatorFunctionalityDTO2 =
                new ActuatorFunctionalityDTO("actuator");

        actuatorFunctionalityDTOList.add(actuatorFunctionalityDTO1);
        actuatorFunctionalityDTOList.add(actuatorFunctionalityDTO2);

        // Behavior of the mapper

        when(actuatorFunctionalityMapperDTO.actuatorFunctionalityToDTOList(actuatorFunctionalityIDList)).
                thenReturn(actuatorFunctionalityDTOList);

        // Act
        ResponseEntity<CollectionModel<EntityModel<ActuatorFunctionalityDTO>>> responseEntity =
                actuatorFunctionalityControllerWeb.getListOfActuatorFunctionalities();

        List<ActuatorFunctionalityDTO> actualActuatorFunctionalityDTOList=
                responseEntity.getBody().getContent().stream()
                        .map(EntityModel::getContent)
                        .collect(Collectors.toList());
        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(actuatorFunctionalityDTOList, actualActuatorFunctionalityDTOList);
    }

    /**
     * Test to verify that the method getListOfActuatorFunctionalities
     * returns an empty list of ActuatorFunctionalityDTO when the service returns an
     * empty list of ActuatorFunctionalityID.
     */
    @Test
    void getEmptyListActuatorFunctionalities() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new ServletRequestAttributes(request));

        // ActuatorFunctionalityID list
        List<ActuatorFunctionalityID> actuatorFunctionalityIDList = new ArrayList<>();

        // Behavior of the service
        when(actuatorFunctionalityService.getListOfActuatorFunctionalities()).thenReturn(actuatorFunctionalityIDList);

        // ActuatorFunctionalityDTO list
        List<ActuatorFunctionalityDTO> actuatorFunctionalityDTOList = new ArrayList<>();

        // Act
        ResponseEntity<CollectionModel<EntityModel<ActuatorFunctionalityDTO>>> responseEntity =
                actuatorFunctionalityControllerWeb.getListOfActuatorFunctionalities();

        List<ActuatorFunctionalityDTO> actualActuatorFunctionalityDTOList=
                responseEntity.getBody().getContent().stream()
                        .map(EntityModel::getContent)
                        .collect(Collectors.toList());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(actuatorFunctionalityDTOList, actualActuatorFunctionalityDTOList);
    }

        /**
         * Test to verify that the method getFunctionalityResources
         * returns an ActuatorFunctionalityDTO
         */
        @Test
        void getFunctionality() {
            // Mocking the HTTP request
            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            // ActuatorFunctionalityID list
            List<ActuatorFunctionalityID> actuatorFunctionalityIDList = new ArrayList<>();
            ActuatorFunctionalityID actuatorFunctionalityID1 = new ActuatorFunctionalityID("switch");
            ActuatorFunctionalityID actuatorFunctionalityID2 = new ActuatorFunctionalityID("actuator");
            actuatorFunctionalityIDList.add(actuatorFunctionalityID1);
            actuatorFunctionalityIDList.add(actuatorFunctionalityID2);

            // Behavior of the service
            when(actuatorFunctionalityService.getListOfActuatorFunctionalities()).thenReturn(actuatorFunctionalityIDList);

            // Expected ActuatorFunctionalityDTO
            ActuatorFunctionalityDTO expectedActuatorFunctionalityDTO =
                    new ActuatorFunctionalityDTO("switch");
            Link selfLink = Link.of("http://localhost/actuatorfunctionality/switch", "self");
            expectedActuatorFunctionalityDTO.add(selfLink);

            // Act
            ResponseEntity<Object> responseEntity =
                    actuatorFunctionalityControllerWeb.getObjectByActuatorFunctionalityID("switch", null);

            ActuatorFunctionalityDTO actualActuatorFunctionalityDTO = (ActuatorFunctionalityDTO) responseEntity.getBody();

            // Assert
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(expectedActuatorFunctionalityDTO, actualActuatorFunctionalityDTO);

        }

        /**
         * Test to verify that the method getFunctionalityResources
         * returns a map of DeviceID and RoomID according to actuator functionality.
         */
        @Test
        void getFunctionalityMap() {
            // Mocking the HTTP request
            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            // Prepare the input and expected output
            String functionalityId = "switch";

            ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID(functionalityId);

            // Create a mock map of DeviceID and RoomID
            DeviceID deviceID1 = new DeviceID("device1");
            RoomID roomID1 = new RoomID("room1");
            DeviceID deviceID2 = new DeviceID("device2");
            RoomID roomID2 = new RoomID("room2");

            Map<DeviceID, RoomID> deviceIDRoomIDMap = new HashMap<>();
            deviceIDRoomIDMap.put(deviceID1, roomID1);
            deviceIDRoomIDMap.put(deviceID2, roomID2);

            // Behavior of the service
            when(closeBlindRollerService.getMapOfDeviceIDAndRoomIDAccordingToFunctionality(actuatorFunctionalityID))
                    .thenReturn(deviceIDRoomIDMap);

            // Create expected EntityModels
            BlindRollerDTO blindRollerDTO1 = new BlindRollerDTO(deviceID1.toString(), roomID1.toString());
            EntityModel<BlindRollerDTO> entityModel1 = EntityModel.of(blindRollerDTO1);
            Link deviceLink1 = linkTo(methodOn(DeviceControllerWeb.class)
                    .getObjectByDeviceID(deviceID1.toString(),null,null, null)).withSelfRel();
            entityModel1.add(deviceLink1);

            BlindRollerDTO blindRollerDTO2 = new BlindRollerDTO(deviceID2.toString(), roomID2.toString());
            EntityModel<BlindRollerDTO> entityModel2 = EntityModel.of(blindRollerDTO2);
            Link deviceLink2 = linkTo(methodOn(DeviceControllerWeb.class)
                    .getObjectByDeviceID(deviceID2.toString(),null,null, null)).withSelfRel();
            entityModel2.add(deviceLink2);

            // Create expected CollectionModel
            CollectionModel<EntityModel<BlindRollerDTO>> expectedCollectionModel = CollectionModel
                    .of(List.of(entityModel1, entityModel2));

            // Add self link
            Link selfLink = linkTo(methodOn(ActuatorFunctionalityControllerWeb.class)
                    .getObjectByActuatorFunctionalityID(functionalityId, "map")).withSelfRel();
            expectedCollectionModel.add(selfLink);

            // Act
            ResponseEntity<Object> responseEntity = actuatorFunctionalityControllerWeb
                    .getObjectByActuatorFunctionalityID(functionalityId, "map");

            CollectionModel<EntityModel<BlindRollerDTO>> actualCollectionModel =
                    (CollectionModel<EntityModel<BlindRollerDTO>>) responseEntity.getBody();

            // Assert
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(expectedCollectionModel, actualCollectionModel);
        }

    /**
     * Test to verify that the method getFunctionalityResources returns a bad request
     */
    @Test
    void failGetFunctionalityMap() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Prepare the input
        String invalidType = "invalid";
        String id = "id";

        // Act
        ResponseEntity<Object> response = actuatorFunctionalityControllerWeb
                .getObjectByActuatorFunctionalityID(id, invalidType);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid type", response.getBody());
    }
}