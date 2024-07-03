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
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.DeviceMapperDTO;
import smarthome.mapper.RoomDTO;
import smarthome.service.DeviceService;
import smarthome.service.HouseService;
import smarthome.service.RoomService;
import smarthome.util.exceptions.HouseNotFoundException;
import smarthome.util.exceptions.RoomNotEditedException;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {RoomControllerWeb.class})
class RoomControllerWebTest {

    /**
     * HouseService to be used in tests
     */
    @MockBean
    private HouseService houseService;

    /**
     * RoomService to be used in tests
     */
    @MockBean
    private RoomService roomService;
    /**
     * DeviceService to be used in tests
     */
    @MockBean
    private DeviceService deviceService;

    /**
     * DeviceMapperDTO to be used in tests
     */
    @MockBean
    private DeviceMapperDTO deviceMapperDTO;

    /**
     * {@link RoomControllerWeb} class under test.
     */

    @InjectMocks
    RoomControllerWeb roomControllerWeb;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to successfully creates a room
     * It verifies that the controller returns a successful response with the correct object.
     */

    @Test
    public void successfulCreateRoom() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Prepare
        RoomDTO roomDTO = new RoomDTO("Living Room",
                1, 10.0, 8.0, 3.0, "House1");
        RoomID roomID = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(10.0, 8.0, 3.0);

        when(roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, new HouseID("House1")))
                .thenReturn(roomID);

        // Execute
        ResponseEntity<Object> responseEntity = roomControllerWeb.createRoom(roomDTO);

        // Verify
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        RoomDTO createdRoomDTO = (RoomDTO) responseEntity.getBody();
        assertEquals(roomID.toString(), createdRoomDTO.roomName);
        assertEquals(roomDTO.floorNumber, createdRoomDTO.floorNumber);
        assertEquals(roomDTO.roomLength, createdRoomDTO.roomLength);
        assertEquals(roomDTO.roomWidth, createdRoomDTO.roomWidth);
        assertEquals(roomDTO.roomHeight, createdRoomDTO.roomHeight);
    }

    /**
     * Test to verify the failure case when attempting to create a room with a null RoomDTO.
     * It checks that the controller responds with the appropriate error status and message.
     */
    @Test
    public void failCreateRoomDTONull() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Execute
        ResponseEntity<Object> responseEntity = roomControllerWeb.createRoom(null);

        // Verify
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    /**
     * Test to verify the failure case when attempting to create a room without a valid HouseID.
     * It checks that the controller responds with the appropriate error status and message.
     */
    @Test
    public void failCreateRoomWithoutHouseID() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Prepare
        RoomDTO roomDTO1 = new RoomDTO("Living Room",
                1, 10.0, 8.0, 3.0, "House1");

        RoomID roomID1 = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(10.0, 8.0, 3.0);

        HouseID houseID = new HouseID("House1");

        //Mock behaviour of houseService
        when(houseService.getHouseIDFromRepository()).thenReturn(houseID);

        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.containsEntityByID(houseID)).thenReturn(false);

        when(roomService.createRoomAndSave(roomID1, roomFloor, roomDimensions, houseID))
                .thenThrow(new HouseNotFoundException());

        // Execute
        ResponseEntity<Object> responseEntity = roomControllerWeb.createRoom(roomDTO1);

        // Verify the result
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals("House not found in Repository", responseEntity.getBody());
    }

    /**
     * Test to verify the successful editing of a room.
     * It ensures that the controller returns the updated RoomDTO with the correct details.
     */
    @Test
    public void successEditRoom() {

        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Prepare
        RoomDTO roomDTO1 = new RoomDTO("Living Room",
                1, 10.0, 8.0, 3.0);
        RoomID roomID1 = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(10.0, 8.0, 3.0);
        RoomID createdRoomID1 = new RoomID("Living Room");

        when(roomService.editRoomAndSave(roomID1, roomFloor, roomDimensions))
                .thenReturn(createdRoomID1);
        String roomID = "Living Room";
        // Execute
        ResponseEntity<Object> responseEntity = roomControllerWeb.editRoom(roomID, roomDTO1);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        RoomDTO editedRoomDTO = (RoomDTO) responseEntity.getBody();
        assertEquals(createdRoomID1.toString(), editedRoomDTO.roomName);
        assertEquals(roomDTO1.floorNumber, editedRoomDTO.floorNumber);
        assertEquals(roomDTO1.roomLength, editedRoomDTO.roomLength);
        assertEquals(roomDTO1.roomWidth, editedRoomDTO.roomWidth);
        assertEquals(roomDTO1.roomHeight, editedRoomDTO.roomHeight);
    }


    /**
     * Test to verify the failure case when attempting to edit a room with a null RoomDTO.
     * It checks that the controller responds with the appropriate error status and message.
     */
    @Test
    public void failEditRoomRoomDTONull() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        String roomID = "Living Room";

        // Execute
        ResponseEntity<Object> responseEntity = roomControllerWeb.editRoom(roomID, null);

        // Verify
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    /**
     * Test to verify the failure case when the attempt to edit a room does not result in any changes.
     * It checks that the controller responds with the appropriate error status and message.
     */
    @Test
    public void failEditRoomNullRoomCreated() {

        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Prepare
        RoomDTO roomDTO1 = new RoomDTO("Living Room",
                1, 10.0, 8.0, 3.0);
        RoomID roomID1 = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(10.0, 8.0, 3.0);

        when(roomService.editRoomAndSave(roomID1, roomFloor, roomDimensions))
                .thenThrow(new RoomNotEditedException());

        String roomID = "Living Room";

        // Execute
        ResponseEntity<Object> responseEntity = roomControllerWeb.editRoom(roomID, roomDTO1);

        // Verify the result
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals("Failed to edit Room", responseEntity.getBody());
    }


    /**
     * Test to verify the fail editing of a room.
     * It checks that the controller responds with the appropriate error status and message.
     */
    @Test
    public void failStringIDAndRoomNameDifferent() {

        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new org.springframework.web.context.request.ServletRequestAttributes(request));

        // Prepare
        RoomDTO roomDTO1 = new RoomDTO("Living Room",
                1, 10.0, 8.0, 3.0);
        RoomID roomID1 = new RoomID("Living Room");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(10.0, 8.0, 3.0);
        RoomID createdRoomID1 = new RoomID("Living Room");

        when(roomService.editRoomAndSave(roomID1, roomFloor, roomDimensions))
                .thenReturn(createdRoomID1);
        String roomID = "Garden";
        // Execute
        ResponseEntity<Object> responseEntity = roomControllerWeb.editRoom(roomID, roomDTO1);

        // Verify
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }


    /**
     * Test that verifies if the DeviceWebController correctly retrieves the devices associated with a roomID.
     */
    @Test
    void getValidListOfDevicesDTOInRoom() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        String roomName = "Room 1";
        RoomID roomID = new RoomID(roomName);
        DeviceDTO deviceDTO = new DeviceDTO("BlindRoller", "B8115", "ACTIVE", "Room 1");
        DeviceID deviceID = new DeviceID(deviceDTO.deviceName);

        //Setting behaviour of service and mapper
        when(deviceService.getListOfDevicesInRoom(roomID)).thenReturn(List.of(deviceID));
        when(deviceMapperDTO.deviceIDsToDTOList(List.of(deviceID))).thenReturn(List.of(deviceDTO));

        ResponseEntity<CollectionModel<EntityModel<DeviceDTO>>> responseEntity = roomControllerWeb.getListOfDevicesDTOInRoom(roomName);
        Collection<EntityModel<DeviceDTO>> result = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, result.size());
        assertEquals(deviceDTO, result.iterator().next().getContent());
        assertEquals("Room 1", result.iterator().next().getContent().roomName);
        assertEquals("BlindRoller", result.iterator().next().getContent().deviceName);
    }

    /**
     * Test that verifies if the controller returns an empty list in case of no devices associated with a roomID.
     */
    @Test
    void getEmptyListOfDevicesDTOInRoom() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        String roomName = "Room 1";

        ResponseEntity<CollectionModel<EntityModel<DeviceDTO>>> responseEntity = roomControllerWeb.getListOfDevicesDTOInRoom(roomName);
        Collection<EntityModel<DeviceDTO>> result = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, result.size());
    }

    /**
     * Test that verifies if the controller fails to retrieve a list of devices in case of a null roomDTO.
     */
    @Test
    void failToGetListOfDevicesDTOInvalidRoom() {
        // Mocking the HTTP request
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new org.springframework.web.context.request.ServletRequestAttributes(request));

        ResponseEntity<CollectionModel<EntityModel<DeviceDTO>>> responseEntity = roomControllerWeb.getListOfDevicesDTOInRoom(null);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}