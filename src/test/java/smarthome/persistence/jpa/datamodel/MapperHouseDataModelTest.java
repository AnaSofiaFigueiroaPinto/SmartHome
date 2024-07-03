package smarthome.persistence.jpa.datamodel;

import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.house.ImpFactoryHouse;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class MapperHouseDataModelTest {
    private String street;
    private String doorNumber;
    private String zipCode;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
    private House houseDouble;
    private HouseDataModel dataModelDouble;
    private FactoryHouse factoryDouble;
    private MapperHouseDataModel mapperHouseDataModel;


    @BeforeEach
    void setUp() {
        street = "Rua Dr. António Bernardino de Almeida";
        doorNumber = "24B";
        zipCode = "4200-072";
        city = "Porto";
        country = "Portugal";
        latitude = 41.236;
        longitude = 85.695;

        houseDouble = mock(House.class);

        dataModelDouble = mock(HouseDataModel.class);
        when(dataModelDouble.getStreet()).thenReturn(street);
        when(dataModelDouble.getDoorNumber()).thenReturn(doorNumber);
        when(dataModelDouble.getZipCode()).thenReturn(zipCode);
        when(dataModelDouble.getCity()).thenReturn(city);
        when(dataModelDouble.getCountry()).thenReturn(country);
        when(dataModelDouble.getLatitude()).thenReturn(latitude);
        when(dataModelDouble.getLongitude()).thenReturn(longitude);

        factoryDouble = mock(ImpFactoryHouse.class);
        mapperHouseDataModel = new MapperHouseDataModel();
    }

    /**
     * Tests if the Data Model is successfully converted to the respective Domain object.
     */
    @Test
    void successConvertDataModelToDomain() {
        try (MockedConstruction<HouseID> houseIdConstruction = mockConstruction(HouseID.class, (mock, context) -> {
            when(dataModelDouble.getHouseID()).thenReturn("1");

            when(mock.toString()).thenReturn("1");

            when(houseDouble.identity()).thenReturn(mock);

        })) {
            try (MockedConstruction<Location> LocationConstruction = mockConstruction(Location.class, (mock, context) -> {
                when(factoryDouble.createHouseWithOrWithoutLocation(any(), any())).thenReturn(houseDouble);

            })) {
                House result = mapperHouseDataModel.toDomain(factoryDouble, dataModelDouble);
                assertNotNull(result);
            }
        }
    }

    /**
     * Tests if the Null Pointer Exception is caught when the Factory is null.
     */
    @Test
    void failConvertFactoryNull() {
        try (MockedConstruction<HouseID> houseIdConstruction = mockConstruction(HouseID.class, (mock, context) -> {
            when(dataModelDouble.getHouseID()).thenReturn("1");

            when(mock.toString()).thenReturn("1");

            when(houseDouble.identity()).thenReturn(mock);

        })) {
            try (MockedConstruction<Location> LocationConstruction = mockConstruction(Location.class, (mock, context) -> {
                when(factoryDouble.createHouseWithOrWithoutLocation(any(), any())).thenReturn(houseDouble);

            })) {
                House result = mapperHouseDataModel.toDomain(null, dataModelDouble);
                assertNull(result);
            }
        }
    }

    /**
     * Tests if the Null Pointer Exception is caught when the House Data Model is null.
     */
    @Test
    void failConvertHouseDataModelNull() {
        House result = mapperHouseDataModel.toDomain(factoryDouble, null);
        assertNull(result);
    }

    /**
     * Test method to verify the conversion of a list of HouseDataModel objects to a list of House objects.
     */
    @Test
    void successConvertHouseListToDomain() {
        List<HouseDataModel> houseDataModelList = new ArrayList<>();
        houseDataModelList.add(dataModelDouble);

        try (MockedConstruction<HouseID> houseIdConstruction = mockConstruction(HouseID.class, (mock, context) -> {
            when(dataModelDouble.getHouseID()).thenReturn("1");

            when(mock.toString()).thenReturn("1");

            when(houseDouble.identity()).thenReturn(mock);

        })) {
            try (MockedConstruction<Location> LocationConstruction = mockConstruction(Location.class, (mock, context) -> {
                when(factoryDouble.createHouseWithOrWithoutLocation(any(), any())).thenReturn(houseDouble);

            })) {

                Iterable<House> actualHouseList = mapperHouseDataModel.toDomainList(factoryDouble, houseDataModelList);

                assertNotNull(actualHouseList);
            }
        }
    }

    /**
     * Test method to verify the conversion of a list of HouseDataModel objects to a list of House objects.
     * Fails when the List of Data Model is null.
     */
    @Test
    void failConvertHouseListToDomainNullDataModelList() {
        Iterable<House> actualHouseList = mapperHouseDataModel.toDomainList(factoryDouble, null);

        assertNull(actualHouseList);
    }

    /**
     * Test method to verify the conversion of a list of HouseDataModel objects to a list of House objects.
     * Fails when the Factory is null.
     */
    @Test
    void failConvertHouseListToDomainNullFactory() {
        // Creating a list of HouseDataModel objects
        List<HouseDataModel> houseDataModelList = new ArrayList<>();
        houseDataModelList.add(dataModelDouble);

        // Act
        Iterable<House> actualHouseList = mapperHouseDataModel.toDomainList(null, houseDataModelList);

        // Assert
        assertNull(actualHouseList);
    }


}