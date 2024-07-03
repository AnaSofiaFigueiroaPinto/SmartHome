package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.valueobjects.Address;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;
import smarthome.service.internaldto.InternalHouseDTO;
import smarthome.util.exceptions.NoHouseInRepositoryException;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing House.
 */
@Service
public class HouseService {

    private final HouseRepository houseRepository;
    private final FactoryHouse factoryHouse;


    /**
     * Constructor for HouseService object.
     *
     * @param houseRepository Service for House objects.
     * @param factoryHouse    Factory for House objects.
     */
    public HouseService(HouseRepository houseRepository, FactoryHouse factoryHouse) {
        if (houseRepository == null || factoryHouse == null) {
            throw new IllegalArgumentException("HouseRepository and/or FactoryHouse cannot be null.");
        }
        this.houseRepository = houseRepository;
        this.factoryHouse = factoryHouse;
    }

    /**
     * Method that creates a new House object without a location.
     * Note: house object created in the factory is never null because it is created with a random UUID as the only attribute.
     *
     * @return HouseID object that represents the House created without location.
     */
    public HouseID createAndSaveHouseWithoutLocation() {
        House house = factoryHouse.createHouseWithOutLocation();
        houseRepository.save(house);
        return house.identity();
    }

    /**
     * Method that creates a new House object with a location.
     *
     * @param location The object that represents the location of the House to be created.
     * @return HouseID object that represents the House created with location.
     * If there's an exception thrown, returns null.
     */
    public HouseID createAndSaveHouseWithLocation(Location location) {
        House house = factoryHouse.createHouseWithLocation(location);
        if (house != null) {
            houseRepository.save(house);
            return house.identity();
        } else {
            return null;
        }
    }

    /**
     * Method that edits the Location of the House.
     *
     * @param houseID Identifier object of house instance.
     * @param address The address object that composes the Location.
     * @param gpsCode The gpsCode object that composes the Location.
     * @return the created instance of object Location.
     */
    public Location editLocation(HouseID houseID, Address address, GPSCode gpsCode) {
        House house = houseRepository.findEntityByID(houseID).orElse(null);
        if (house == null) {
            return null;
        }
        Location location = house.editLocation(address, gpsCode);
        //Saves the House with the new configured Location and return true after updating with success
        houseRepository.update(house);
        return location;
    }

    /**
     * Method that retrieves the first house from the repository.
     *
     * @return House object representing the first house in the repository.
     */
    public HouseID getHouseIDFromRepository () {
        // Retrieve all houses from the repository
        Iterable<House> allHouses = houseRepository.findAllEntities();

        List<House> houseList = new ArrayList<>();
        //Add all houses to the list
        allHouses.forEach(houseList::add);

        //If the list is empty, return a custom exception
        if (houseList.isEmpty()) {
            throw new NoHouseInRepositoryException();
        }
        //Return the first house in the list
        return houseList.get(0).identity();
    }

    /**
     * Method that retrieves the house from the repository, with or without location.
     *
     * @return InternalHouseDTO object containing all the attributes of the house in the repository.
     */
    public InternalHouseDTO getHouse() {
        Iterable<House> housesInRepository = houseRepository.findAllEntities();
        if (!housesInRepository.iterator().hasNext()) {
            throw new NoHouseInRepositoryException();
        }
        House house = housesInRepository.iterator().next();
        Location houseLocation = house.getHouseLocation();
        if (houseLocation == null) {
            return new InternalHouseDTO(house.identity());
        } else {
            Address address = houseLocation.getAddress();
            GPSCode gpsCode = houseLocation.getGpsCode();
            return new InternalHouseDTO(house.identity(),
                    address.getStreet(),
                    address.getDoorNumber(),
                    address.getZipCode(),
                    address.getCity(),
                    address.getCountry(),
                    gpsCode.getLatitude(),
                    gpsCode.getLongitude());
        }
    }
}