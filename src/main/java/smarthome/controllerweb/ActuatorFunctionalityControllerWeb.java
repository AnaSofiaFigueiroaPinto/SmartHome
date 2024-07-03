package smarthome.controllerweb;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.mapper.ActuatorFunctionalityDTO;
import smarthome.mapper.ActuatorFunctionalityMapperDTO;
import smarthome.mapper.BlindRollerDTO;
import smarthome.service.ActuatorFunctionalityService;
import smarthome.service.CloseBlindRollerService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/actuatorfunctionality")
public class ActuatorFunctionalityControllerWeb {
    /**
     * The service for the ActuatorFunctionality entities.
     */
    private ActuatorFunctionalityService actuatorFunctionalityService;

    /**
     * CloseBlindRollerService instance to interact with device-related operations.
     */

    private CloseBlindRollerService closeBlindRollerService;


    /**
     * Constructor of the ActuatorFunctionalityControllerWeb class.
     */
    public ActuatorFunctionalityControllerWeb(ActuatorFunctionalityService actuatorFunctionalityService,
                                              CloseBlindRollerService closeBlindRollerService) {
        this.actuatorFunctionalityService = actuatorFunctionalityService;
        this.closeBlindRollerService = closeBlindRollerService;

    }

    /**
     * Method to get the list of actuator functionalities.
     * @return ResponseEntity<Object> with the list of actuator functionalities or an
     * empty list if there are no actuator functionalities.
     */
    @GetMapping("")
    public ResponseEntity<CollectionModel<EntityModel<ActuatorFunctionalityDTO>>> getListOfActuatorFunctionalities() {

            List<ActuatorFunctionalityID> listOfActuatorFunctionalitiesIDs =
                    actuatorFunctionalityService.getListOfActuatorFunctionalities();
        List<EntityModel<ActuatorFunctionalityDTO>> listActuatorFunctionalityDTO =
                listOfActuatorFunctionalitiesIDs.stream().map(actuatorFunctionalityID -> {
                    ActuatorFunctionalityMapperDTO actuatorFunctionalityMapperDTO =
                            new ActuatorFunctionalityMapperDTO();

                    ActuatorFunctionalityDTO actuatorFunctionalityDTO =
                            actuatorFunctionalityMapperDTO.actuatorFunctionalityToDTO(actuatorFunctionalityID);
                    EntityModel<ActuatorFunctionalityDTO> resource = EntityModel.of(actuatorFunctionalityDTO);


                    resource.add(linkTo(methodOn(ActuatorFunctionalityControllerWeb.class)
                            .getObjectByActuatorFunctionalityID(actuatorFunctionalityID.toString(), null)).withSelfRel());
                    return resource;
                }).collect(Collectors.toList());

        CollectionModel<EntityModel<ActuatorFunctionalityDTO>> collectionModel =
                CollectionModel.of(listActuatorFunctionalityDTO);

        collectionModel.add(linkTo(methodOn(ActuatorFunctionalityControllerWeb.class)
                .getListOfActuatorFunctionalities()).withSelfRel());

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

        /**
         * Get functionality resources based on the provided type.
         *
         * @param id   The functionality ID.
         * @param type The type of the resource (optional).
         *             If null or empty, returns the functionality ID.
         *             If "map", returns a list of device ID and room ID according to the functionality.
         * @return A ResponseEntity containing the functionality resources or an error message if the type is invalid.
         */
        @GetMapping("/{id}")
        public ResponseEntity<Object> getObjectByActuatorFunctionalityID(@PathVariable("id") String id,
                                                                         @RequestParam(value = "type", required = false) String type) {

            if (type == null || type.isEmpty()) {

                // If the type is null or empty, execute the first endpoint
                return getFunctionalityID(id);

            } else if (type.equals("map")) {

                // If the type is "map", execute the second endpoint
                return getListOfDeviceIDAndRoomIDAccordingToFunctionality(id);

            } else {
                // If the type is invalid, return a bad request
                return new ResponseEntity<>("Invalid type", HttpStatus.BAD_REQUEST);
            }
        }

        /**
         * Get the functionality ID.
         *
         * @param id The functionality ID.
         * @return A ResponseEntity with the functionality ID.
         */
        private ResponseEntity<Object> getFunctionalityID(String id) {

            ActuatorFunctionalityDTO actuatorFunctionalityDto = actuatorFunctionalityService
                    .getListOfActuatorFunctionalities().stream()
                    .filter(actuatorFunctionalityID -> actuatorFunctionalityID.toString().equals(id))
                    .map(actuatorFunctionalityID -> {
                        ActuatorFunctionalityMapperDTO actuatorFunctionalityMapperDTO =
                                new ActuatorFunctionalityMapperDTO();
                        return actuatorFunctionalityMapperDTO.actuatorFunctionalityToDTO(actuatorFunctionalityID);
                    }).findFirst().orElse(null);

            if (actuatorFunctionalityDto == null) {
                return new ResponseEntity<>("Actuator Functionality not found",HttpStatus.BAD_REQUEST);
            }

            Link selfLink = Link.of(linkTo(methodOn(ActuatorFunctionalityControllerWeb.class)
                    .getObjectByActuatorFunctionalityID(id, null)).toUri().toString(), "self");
            actuatorFunctionalityDto.add(selfLink);

            return new ResponseEntity<>(actuatorFunctionalityDto, HttpStatus.OK);

        }
        /**
         * Get a map of device ID and room ID according to the functionality.
         *
         * @param id The functionality ID.
         * @return A ResponseEntity with the map of device ID and room ID.
         */

        private ResponseEntity<Object> getListOfDeviceIDAndRoomIDAccordingToFunctionality(String id) {

            ActuatorFunctionalityID actuatorFunctionality = new ActuatorFunctionalityID(id);
            Map<DeviceID, RoomID> deviceIDRoomIDMap = closeBlindRollerService
                    .getMapOfDeviceIDAndRoomIDAccordingToFunctionality(actuatorFunctionality);

            // Create a list of BlindRollerDTO objects
            Collection<EntityModel<BlindRollerDTO>> blindRollerDTOs = deviceIDRoomIDMap.entrySet().stream()
                    .map(entry -> {
                        BlindRollerDTO blindRollerDTO = new BlindRollerDTO(entry.getKey().toString(), entry.getValue().toString());
                        EntityModel<BlindRollerDTO> entityModel = EntityModel.of(blindRollerDTO);

                        // Add a self link for each device
                        Link deviceLink = linkTo(methodOn(DeviceControllerWeb.class)
                                .getObjectByDeviceID(entry.getKey().toString(),null,null, null))
                                .withRel("self");
                        entityModel.add(deviceLink);

                        return entityModel;
                    })
                    .collect(Collectors.toList());

            CollectionModel<EntityModel<BlindRollerDTO>> result = CollectionModel.of(blindRollerDTOs);

            result.add(linkTo(methodOn(ActuatorFunctionalityControllerWeb.class)
                    .getObjectByActuatorFunctionalityID(id, "map")).withSelfRel());

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
