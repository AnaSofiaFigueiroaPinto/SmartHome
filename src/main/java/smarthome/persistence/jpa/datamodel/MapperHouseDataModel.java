package smarthome.persistence.jpa.datamodel;

import org.springframework.stereotype.Component;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.valueobjects.Address;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@code MapperHouseDataModel} class within the smart home system.
 */
@Component
public class MapperHouseDataModel {
    /**
     * Method to receive a House Data Model and create a House object from domain.
     *
     * @param factory        factory of House.
     * @param houseDataModel object of House Data Model.
     * @return the House object from domain.
     */
    public House toDomain(FactoryHouse factory, HouseDataModel houseDataModel) {
        try {
            if (factory == null || houseDataModel == null)
                return null;

            HouseID houseID = new HouseID(houseDataModel.getHouseID());

            String street = houseDataModel.getStreet();
            String doorNumber = houseDataModel.getDoorNumber();
            String zipCode = houseDataModel.getZipCode();
            String city = houseDataModel.getCity();
            String country = houseDataModel.getCountry();
            double latitude = houseDataModel.getLatitude();
            double longitude = houseDataModel.getLongitude();

            Address address = new Address(street, doorNumber, zipCode, city, country);
            GPSCode gpsCode = new GPSCode(latitude, longitude);
            Location location = new Location(address, gpsCode);

            return factory.createHouseWithOrWithoutLocation(houseID, location);
        } catch (IllegalArgumentException e) {
            //If HouseDataModel persist a House without Location and IllegalArgumentException is caught
            HouseID houseID = new HouseID(houseDataModel.getHouseID());
            return factory.createHouseWithOrWithoutLocation(houseID, null);
        }
    }

    /**
     * Converts a list of HouseDataModel objects to a list of corresponding House objects.
     *
     * @param factoryHouse    The factory used to create House instances.
     * @param houseDataModels Iterable of HouseDataModel objects to be converted.
     * @return Iterable of House objects from domain.
     */
    public Iterable<House> toDomainList(FactoryHouse factoryHouse, Iterable<HouseDataModel> houseDataModels) {
        if (houseDataModels == null || factoryHouse == null) {
            return null;
        }

        List<House> houseList = new ArrayList<>();
        for (HouseDataModel houseDataModel : houseDataModels) {
            House house = toDomain(factoryHouse, houseDataModel);
            if (house != null) {
                houseList.add(house);
            }
        }
        return houseList;
    }

}
