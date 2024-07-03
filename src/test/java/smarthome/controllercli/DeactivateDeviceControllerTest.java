package smarthome.controllercli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.device.ImpFactoryDevice;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.house.ImpFactoryHouse;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.*;
import smarthome.persistence.jpa.datamodel.DeviceDataModel;
import smarthome.persistence.jpa.datamodel.MapperDeviceDataModel;
import smarthome.persistence.jpa.datamodel.MapperHouseDataModel;
import smarthome.persistence.jpa.datamodel.MapperRoomDataModel;
import smarthome.persistence.jpa.repositoriesjpa.DeviceRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.HouseRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.RoomRepositoryJPAImp;
import smarthome.persistence.repositoriesmem.DeviceRepositoryMem;
import smarthome.persistence.repositoriesmem.HouseRepositoryMem;
import smarthome.persistence.repositoriesmem.RoomRepositoryMem;
import smarthome.persistence.springdata.repositoriesspringdata.*;
import smarthome.service.DeviceService;
import smarthome.service.RoomService;
import org.junit.jupiter.api.Test;
import smarthome.mapper.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code DeactivateDeviceController} class.
 */
class DeactivateDeviceControllerTest {

    /**
     * Test the method to deactivate a device by its object reference.
     */
     @Test
     void successDeviceDeactivation() {
         // Instantiate needed factories
         FactoryRoom factoryRoom = new ImpFactoryRoom();
         FactoryDevice factoryDevice = new ImpFactoryDevice();
         FactoryHouse factoryHouse = new ImpFactoryHouse();

         // Instantiate House object
         House house = factoryHouse.createHouseWithOutLocation();

         // Instantiate needed repositories with a blank data map
         Map<DeviceID, Device> deviceData = new HashMap<>();
         DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(deviceData);

         Map<HouseID, House> houseData = new HashMap<>();
         HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem(houseData);
         HouseID houseID = house.identity();
         houseData.put(houseID, house);

         Map<RoomID, Room> roomData = new HashMap<>();
         RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

         // Instantiate needed services
         RoomService roomService = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
         DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryMem, roomRepositoryMem, houseRepositoryMem);

         // Instantiate controller
         DeactivateDeviceController controller = new DeactivateDeviceController(deviceService);

         // Create room
         RoomID roomID = new RoomID("Bedroom");
         RoomFloor roomFloor = new RoomFloor(2);
         RoomDimensions roomDimensions = new RoomDimensions(2, 1, 2);
         roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, house.identity());

         // Create device
         DeviceID deviceID = new DeviceID("Heater");
         DeviceModel deviceModel = new DeviceModel("H8115");
         deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);


         // Retrieve list of rooms in house and choose one
         List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
         roomID = listOfRooms.get(0);

         // Retrieve list of devices in room
         List<DeviceID> listOfDevicesIDInRoom = deviceService.getListOfDevicesInRoom(roomID);
         // Create deviceDTO that matches a domain object
         DeviceDTO deviceDTO = new DeviceDTO(listOfDevicesIDInRoom.get(0).toString(), "H8115", "ACTIVE", roomID.toString());

         // Act
         boolean wasDeactivated = controller.deactivateDevice(deviceDTO);

         // Assert
         assertTrue(wasDeactivated);
     }

     /**
      * Test if the method fail to deactivate a device when the device is already deactivated.
      */
     @Test
     void failToDeactivateDeviceAlreadyDeactivated() {
         // Instantiate needed factories
         FactoryRoom factoryRoom = new ImpFactoryRoom();
         FactoryDevice factoryDevice = new ImpFactoryDevice();
         FactoryHouse factoryHouse = new ImpFactoryHouse();

         // Instantiate House object
         House house = factoryHouse.createHouseWithOutLocation();

         // Instantiate needed repositories with a blank data map
         Map<DeviceID, Device> deviceData = new HashMap<>();
         DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(deviceData);

         Map<HouseID, House> houseData = new HashMap<>();
         HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem(houseData);
         HouseID houseID = house.identity();
         houseData.put(houseID, house);

         Map<RoomID, Room> roomData = new HashMap<>();
         RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

         // Instantiate needed services
         RoomService roomService = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
         DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryMem, roomRepositoryMem, houseRepositoryMem);

         // Instantiate controller
         DeactivateDeviceController controller = new DeactivateDeviceController(deviceService);

         // Create room
         RoomID roomID = new RoomID("Bedroom");
         RoomFloor roomFloor = new RoomFloor(2);
         RoomDimensions roomDimensions = new RoomDimensions(2, 1, 2);
         roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, house.identity());

         // Create device
         DeviceID deviceID = new DeviceID("Heater");
         DeviceModel deviceModel = new DeviceModel("H8115");
         deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);


         // Retrieve list of rooms in house and choose one
         List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
         roomID = listOfRooms.get(0);

         // Retrieve list of devices in room
         List<DeviceID> listOfDevicesIDInRoom = deviceService.getListOfDevicesInRoom(roomID);
         // Create deviceDTO that matches a domain object
         DeviceDTO deviceDTO = new DeviceDTO(listOfDevicesIDInRoom.get(0).toString(), "H8115", "ACTIVE", roomID.toString());

         //Deactivate device
         controller.deactivateDevice(deviceDTO);

         // Act
         boolean wasDeactivated = controller.deactivateDevice(deviceDTO);

         // Assert
         assertFalse(wasDeactivated);
     }

     /**
      * Test if the method fail to deactivate a device when the device does not exists.
      */
     @Test
     void failToDeactivateNonExistingDevice() {
         // Instantiate needed factories
         FactoryRoom factoryRoom = new ImpFactoryRoom();
         FactoryDevice factoryDevice = new ImpFactoryDevice();
         FactoryHouse factoryHouse = new ImpFactoryHouse();

         // Instantiate House object
         House house = factoryHouse.createHouseWithOutLocation();

         // Instantiate needed repositories with a blank data map
         Map<DeviceID, Device> deviceData = new HashMap<>();
         DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem(deviceData);

         Map<HouseID, House> houseData = new HashMap<>();
         HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem(houseData);
         HouseID houseID = house.identity();
         houseData.put(houseID, house);

         Map<RoomID, Room> roomData = new HashMap<>();
         RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

         // Instantiate needed services
         RoomService roomService = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
         DeviceService deviceService = new DeviceService(factoryDevice, deviceRepositoryMem, roomRepositoryMem, houseRepositoryMem);

         // Instantiate controller
         DeactivateDeviceController controller = new DeactivateDeviceController(deviceService);

         // Create room
         RoomID roomID = new RoomID("Bedroom");
         RoomFloor roomFloor = new RoomFloor(2);
         RoomDimensions roomDimensions = new RoomDimensions(2, 1, 2);
         roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, house.identity());

         // Create device
         DeviceID deviceID = new DeviceID("Heater");
         DeviceModel deviceModel = new DeviceModel("H8115");
         deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

         // Retrieve list of rooms in house and choose one
         List<RoomID> listOfRooms = roomService.getListOfRoomsInHouse(houseID);
         roomID = listOfRooms.get(0);

         // Create deviceDTO that does not match a domain object
         DeviceDTO deviceDTO2 = new DeviceDTO("TV", "T8115", "ACTIVE", roomID.toString());

         boolean wasDeactivated = controller.deactivateDevice(deviceDTO2);
         assertFalse(wasDeactivated);
     }

     /**
      * Test if the method deactivate a device using the JPA Repository.
      */

    @Test
    void successDeviceDeactivationJPARepo() {
        //Device Data
        DeviceID deviceIDJPA = new DeviceID("HeaterJPA");
        DeviceModel deviceModelJPA = new DeviceModel("H8115JPA");
        RoomID roomIDJPA = new RoomID("BedroomJPA");

        //Instantiate needed factories
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryHouse factoryHouse = new ImpFactoryHouse();

        //Create a Device instance using the factory
        Device device = factoryDevice.createDevice(deviceIDJPA, deviceModelJPA, roomIDJPA);

        //Mocking the EntityManager and the EntityTransaction
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.find(DeviceDataModel.class, "HeaterJPA")).thenReturn(new DeviceDataModel(device));
        when(manager.getTransaction()).thenReturn(transaction);

        //Instantiate necessary repositories
        RoomRepository roomRepositoryJPAImp = new RoomRepositoryJPAImp(factoryRoom, manager);
        DeviceRepository deviceRepositoryJPAImp = new DeviceRepositoryJPAImp(factoryDevice, manager);
        HouseRepository houseRepositoryJPAImp = new HouseRepositoryJPAImp(factoryHouse, manager);

        //Instantiate needed service
        DeviceService deviceServiceJPA = new DeviceService(factoryDevice, deviceRepositoryJPAImp, roomRepositoryJPAImp, houseRepositoryJPAImp);

        //Controller
        DeactivateDeviceController controllerJPA = new DeactivateDeviceController(deviceServiceJPA);

        //Creation of the DeviceDTO
        DeviceDTO deviceDTO = new DeviceDTO("HeaterJPA", "H8115", "ACTIVE", roomIDJPA.toString());

        //Act
        boolean wasDeactivated = controllerJPA.deactivateDevice(deviceDTO);

        // Assert
        assertTrue(wasDeactivated);
    }

    /**
     * Test if the method deactivate a device using the Spring Data Repository.
     */

    @Test
    void successDeviceDeactivationSpringDataRepo() {
        //Device Data
        DeviceID deviceID = new DeviceID("HeaterJPA");
        DeviceModel deviceModel = new DeviceModel("H8115JPA");
        RoomID roomID = new RoomID("BedroomJPA");

        //Instantiate needed factories
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        FactoryHouse factoryHouse = new ImpFactoryHouse();

        //Create a Device instance using the factory
        Device deviceToMock = factoryDevice.createDevice(deviceID, deviceModel, roomID);

        //Mock the EntityManager and the EntityTransaction
        DeviceRepositorySpringData deviceRepositorySpringData = mock(DeviceRepositorySpringData.class);
        when(deviceRepositorySpringData.findById("HeaterJPA")).thenReturn(Optional.of(new DeviceDataModel(deviceToMock)));

        //MapperDataModelDouble
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();

        //Instantiate necessary repositories
        RoomRepositorySpringData roomRepositorySpringDouble = mock(RoomRepositorySpringData.class);
        RoomRepository roomRepositorySpringData = new RoomRepositorySpringDataImp(roomRepositorySpringDouble, factoryRoom, mapperRoomDataModel);

        HouseRepositorySpringData houseRepositorySpringDouble = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepositorySpringData = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringDouble, mapperHouseDataModel);

        //Repo
        DeviceRepository deviceRepositorySpringDataImp = new DeviceRepositorySpringDataImp(factoryDevice, deviceRepositorySpringData, mapperDeviceDataModel);

        //Instantiate needed service
        DeviceService deviceServiceSpringData = new DeviceService(factoryDevice, deviceRepositorySpringDataImp, roomRepositorySpringData, houseRepositorySpringData);

        //Controller
        DeactivateDeviceController controllerSpring = new DeactivateDeviceController(deviceServiceSpringData);

        //Creation of the DeviceDTO
        DeviceDTO deviceDTO = new DeviceDTO("HeaterJPA", "H8115", "ACTIVE", "BedroomJPA");

        //Act
        boolean wasDeactivated = controllerSpring.deactivateDevice(deviceDTO);

        // Assert
        assertTrue(wasDeactivated);
    }

}