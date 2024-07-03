package smarthome.controllerweb;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.mapper.SensorFunctionalityDTO;
import smarthome.mapper.SensorFunctionalityMapperDTO;
import smarthome.service.SensorFunctionalityService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * A controller class for the SensorFunctionality entity.
 */
@RestController
@RequestMapping(path = "/sensorfunctionality")
public class SensorFunctionalityControllerWeb {

    /**
     * The SensorFunctionalityService attribute.
     */
    private SensorFunctionalityService sensorFunctionalityService;

    /**
     * Constructs a new SensorFunctionalityControllerWeb object with the provided SensorFunctionalityService.
     * @param sensorFunctionalityService The SensorFunctionalityService to be used in this controller.
     */
    public SensorFunctionalityControllerWeb(SensorFunctionalityService sensorFunctionalityService) {
        this.sensorFunctionalityService = sensorFunctionalityService;
    }

    /**
     * Method that gets the list of SensorFunctionality objects.
     * @return The list of SensorFunctionalityDTO objects.
     */
    @GetMapping("")
    public ResponseEntity<CollectionModel<EntityModel<SensorFunctionalityDTO>>> getListOfSensorFunctionalities() {
        // Get the list of SensorFunctionality objects and map them to SensorFunctionalityDTO objects
        List<SensorFunctionalityID> sensorFunctionalitiesIDs = sensorFunctionalityService.getListOfSensorFunctionalities();
        List<EntityModel<SensorFunctionalityDTO>> sensorFunctionalityModels = sensorFunctionalitiesIDs.stream().map(sensorFunctionalityID -> {
            SensorFunctionalityMapperDTO sensorFunctionalityMapperDTO = new SensorFunctionalityMapperDTO();
            SensorFunctionalityDTO dto = sensorFunctionalityMapperDTO.sensorFunctionalityToDTO(sensorFunctionalityID);
            EntityModel<SensorFunctionalityDTO> resource = EntityModel.of(dto);

            // Add self link to each SensorFunctionalityDTO
            resource.add(linkTo(methodOn(SensorFunctionalityControllerWeb.class)
                    .getSensorFunctionality(dto.sensorFunctionalityName)).withSelfRel());
            return resource;
        }).collect(Collectors.toList());

        // Wrap the list in a CollectionModel and add a self link to the list
        CollectionModel<EntityModel<SensorFunctionalityDTO>> collectionModel = CollectionModel.of(sensorFunctionalityModels);
        collectionModel.add(linkTo(methodOn(SensorFunctionalityControllerWeb.class).getListOfSensorFunctionalities()).withSelfRel());

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    /**
     * Method that gets a SensorFunctionality object by its ID.
     * @param functionality The ID of the SensorFunctionality object.
     * @return The SensorFunctionalityDTO object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SensorFunctionalityDTO> getSensorFunctionality(@PathVariable("id") String functionality) {
        // Get the SensorFunctionality object by its ID and map it to a SensorFunctionalityDTO object
        SensorFunctionalityDTO sensorFunctionality = sensorFunctionalityService.getListOfSensorFunctionalities().stream()
                .filter(sensorFunctionalityID -> sensorFunctionalityID.toString().equals(functionality))
                .map(sensorFunctionalityID -> {
                    SensorFunctionalityMapperDTO sensorFunctionalityMapperDTO = new SensorFunctionalityMapperDTO();
                    return sensorFunctionalityMapperDTO.sensorFunctionalityToDTO(sensorFunctionalityID);
                }).findFirst().orElse(null);

        if (sensorFunctionality == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Link selfLink = Link.of(linkTo(methodOn(SensorFunctionalityControllerWeb.class)
                .getSensorFunctionality(functionality)).toUri().toString(), "self");
        sensorFunctionality.add(selfLink);

        return new ResponseEntity<>(sensorFunctionality, HttpStatus.OK);
    }

}
