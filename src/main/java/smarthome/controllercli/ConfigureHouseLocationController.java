package smarthome.controllercli;

import smarthome.domain.valueobjects.Address;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.LocationDTO;
import smarthome.service.HouseService;

/**
 * Controller class responsible for configure a House Location.
 */
public class ConfigureHouseLocationController {

    private final HouseService houseService;

    /**
     * HouseID representing the house to which the rooms belong.
     */
    private final HouseID houseID;

    public ConfigureHouseLocationController(HouseService houseService, HouseID houseID) {
        this.houseService = houseService;
        this.houseID = houseID;
    }

    /**
     * Method that configures the house location with the given parameters in the LocationDTO.
     *
     * @param locationDTO DTO that contains the parameters to configure the house location and a houseID.
     * @return HouseDTO object with the new Location.
     */
    public HouseDTO configureHouseLocation(LocationDTO locationDTO) {
        try {
            Address address = new Address(
                    locationDTO.street,
                    locationDTO.doorNumber,
                    locationDTO.zipCode,
                    locationDTO.city,
                    locationDTO.country);

            GPSCode gpsCode = new GPSCode(locationDTO.latitude, locationDTO.longitude);

            Location location = houseService.editLocation(houseID, address, gpsCode);

            return new HouseDTO(
                    houseID.toString(),
                    location.getAddress().getStreet(),
                    location.getAddress().getDoorNumber(),
                    location.getAddress().getZipCode(),
                    location.getAddress().getCity(),
                    location.getAddress().getCountry(),
                    location.getGpsCode().getLatitude(),
                    location.getGpsCode().getLongitude());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

