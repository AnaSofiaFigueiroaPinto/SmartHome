package smarthome.controllerweb;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.DeviceMapperDTO;
import smarthome.mapper.RoomDTO;
import smarthome.service.DeviceService;
import smarthome.service.RoomService;
import smarthome.service.internaldto.InternalRoomDTO;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller class for handling room-related HTTP requests.
 * Provides methods for listing rooms in a house and creating and saving a room.
 */
@RestController
@RequestMapping(path = "/rooms")

public class RoomControllerWeb {

    /**
     * Service layer responsible for handling room-related business logic.
     */
    private RoomService roomService;
    /**
     * DeviceService instance to interact with device-related operations.
     */
    private DeviceService deviceService;

    /**
     * DeviceMapperDTO responsible for converting device-related data transfer objects (DTOs).
     */
    private DeviceMapperDTO deviceMapperDTO;

    /**
     * Constructor for RoomControllerWeb class.
     *
     * @param roomService   The service layer responsible for handling room-related business logic.
     * @param deviceService The service layer responsible for handling device-related business logic.
     * @param deviceMapperDTO The mapper responsible for converting device-related DTOs.
     */
    public RoomControllerWeb(RoomService roomService,
                             DeviceService deviceService,
                             DeviceMapperDTO deviceMapperDTO) {
        this.roomService = roomService;
        this.deviceService = deviceService;
        this.deviceMapperDTO = deviceMapperDTO;
    }


    /**
     * Endpoint for creating and saving a room.
     *
     * @param roomDTO The data transfer object representing the room.
     * @return ResponseEntity containing the RoomDTO representing the created room if successful,
     * otherwise an error message with HttpStatus.UNPROCESSABLE_ENTITY.
     */
    @PostMapping("")
    public ResponseEntity<Object> createRoom(@RequestBody RoomDTO roomDTO) {
        try {
            HouseID houseID = new HouseID(roomDTO.houseUUID);
            RoomID roomID = new RoomID(roomDTO.roomName);
            RoomFloor roomFloor = new RoomFloor(roomDTO.floorNumber);
            RoomDimensions roomDimensions = new RoomDimensions(
                    roomDTO.roomLength,
                    roomDTO.roomWidth,
                    roomDTO.roomHeight);

            RoomID createdRoomID = roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);
            RoomDTO newRoomDTO = new RoomDTO(createdRoomID.toString(), roomDTO.floorNumber, roomDTO.roomLength,
                    roomDTO.roomWidth, roomDTO.roomHeight, houseID.toString());

            //Create a self link for the device created.
            Link selflink = linkTo(RoomControllerWeb.class).slash(newRoomDTO.roomName).withSelfRel();
            newRoomDTO.add(selflink);

            return new ResponseEntity<>(newRoomDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    /**
     * Endpoint for editing a room.
     *
     * @param id      The String representing the room name for which to retrieve the list of devices.
     * @param roomDTO The data transfer object representing the room.
     * @return ResponseEntity containing the RoomDTO representing the edited room if successful,
     * otherwise an error message with HttpStatus.UNPROCESSABLE_ENTITY.
     */

    @PatchMapping("/{id}")
    public ResponseEntity<Object> editRoom(@PathVariable String id, @RequestBody RoomDTO roomDTO) {
        try {

            if (!id.equals(roomDTO.roomName)) {
                return new ResponseEntity<>("RoomID in the path and Room Name in Room DTO do not match", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            RoomID roomID = new RoomID(id);
            RoomFloor roomFloor = new RoomFloor(roomDTO.floorNumber);
            RoomDimensions roomDimensions = new RoomDimensions(
                    roomDTO.roomLength,
                    roomDTO.roomWidth,
                    roomDTO.roomHeight);

            RoomID editedRoomID = roomService.editRoomAndSave(roomID, roomFloor, roomDimensions);
            RoomDTO editedRoomDTO = new RoomDTO(editedRoomID.toString(), roomDTO.floorNumber, roomDTO.roomLength,
                    roomDTO.roomWidth, roomDTO.roomHeight);

            return new ResponseEntity<>(editedRoomDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    /**
     * Retrieves a list of DeviceDTO objects in the specified room.
     *
     * @param roomName The String representing the room name for which to retrieve the list of devices.
     * @return ResponseEntity containing a list of DeviceDTO objects associated with the specified room if successful,
     * otherwise an error message.
     */

    @GetMapping("/{id}/devices")
    public ResponseEntity<CollectionModel<EntityModel<DeviceDTO>>> getListOfDevicesDTOInRoom
    (@PathVariable("id") String roomName) {
        try {
            RoomID roomID = new RoomID(roomName);
            List<DeviceID> deviceIDList = deviceService.getListOfDevicesInRoom(roomID);
            List<DeviceDTO> deviceDTOList = deviceMapperDTO.deviceIDsToDTOList(deviceIDList);

            List<EntityModel<DeviceDTO>> deviceDTOEntities = new ArrayList<>();
            for (DeviceDTO deviceDTO : deviceDTOList) {
                Link deviceLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(DeviceControllerWeb.class).getObjectByDeviceID(deviceDTO.deviceName, null, null, null)
                ).withSelfRel();
                EntityModel<DeviceDTO> deviceEntity = EntityModel.of(deviceDTO, deviceLink);
                deviceDTOEntities.add(deviceEntity);
            }

            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(RoomControllerWeb.class).getListOfDevicesDTOInRoom(roomName)).withSelfRel();

            CollectionModel<EntityModel<DeviceDTO>> collectionModel = CollectionModel.of(deviceDTOEntities, selfLink);

            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves a RoomDTO object by the specified room name.
     *
     * @param roomName The String representing the room name for which to retrieve the RoomDTO object.
     * @return ResponseEntity containing the RoomDTO object associated with the specified room if successful,
     * otherwise an error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoomsByID(@PathVariable("id") String roomName) {
            try {
                RoomID roomID = new RoomID(roomName);

                InternalRoomDTO internalRoomDTO = roomService.findRoomIDInfo(roomID);
                RoomFloor roomFloor = internalRoomDTO.roomFloor;
                RoomDimensions roomDimensions = internalRoomDTO.roomDimensions;
                HouseID houseID = internalRoomDTO.houseID;

                RoomDTO roomDTO = new RoomDTO(roomName, roomFloor.getRoomFloor(), roomDimensions.getLength(),
                        roomDimensions.getWidth(), roomDimensions.getHeight(), houseID.toString());

                Link selflink = linkTo(RoomControllerWeb.class).slash(roomDTO.roomName).withSelfRel();
                roomDTO.add(selflink);

                Link createDeviceLink = linkTo(methodOn(DeviceControllerWeb.class).createDeviceAndSaveToRepository(null)).withRel("createDevice");
                roomDTO.add(createDeviceLink);

                return new ResponseEntity<>(roomDTO, HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            }
    }
}
