package smarthome.controllercli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.house.ImpFactoryHouse;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.*;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.RoomDTO;
import smarthome.persistence.jpa.datamodel.HouseDataModel;
import smarthome.persistence.jpa.datamodel.MapperHouseDataModel;
import smarthome.persistence.jpa.datamodel.MapperRoomDataModel;
import smarthome.persistence.jpa.datamodel.RoomDataModel;
import smarthome.persistence.jpa.repositoriesjpa.HouseRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.RoomRepositoryJPAImp;
import smarthome.persistence.repositoriesmem.HouseRepositoryMem;
import smarthome.persistence.repositoriesmem.RoomRepositoryMem;
import smarthome.persistence.springdata.repositoriesspringdata.HouseRepositorySpringData;
import smarthome.persistence.springdata.repositoriesspringdata.HouseRepositorySpringDataImp;
import smarthome.persistence.springdata.repositoriesspringdata.RoomRepositorySpringData;
import smarthome.persistence.springdata.repositoriesspringdata.RoomRepositorySpringDataImp;
import smarthome.service.HouseService;
import smarthome.service.RoomService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListAllRoomsInsideOrOutsideOfHouseControllerTest {

    /**
     * Test case to verify that the controller gets the correct list of room DTOs that are inside the House using memory repositories.
     */
    @Test
    void successGetListOfRoomDTOInsideHouseUsingMemoryRepositories() {
        //House section setup
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        //Room section setup
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 3.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));

        //Instantiate roomRepository
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        //Instantiate roomService
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        //Instantiate controller
        ListAllRoomsInsideOrOutsideOfHouseController controller = new ListAllRoomsInsideOrOutsideOfHouseController(roomService);

        HouseDTO houseDTO = new HouseDTO(houseID.toString(), "Rua Firmeza", "24B", "4000-009", "Porto", "Portugal", 41.14961, -8.61099);

        List<RoomDTO> expectedList = new ArrayList<>();
        expectedList.add(new RoomDTO("Bedroom"));
        expectedList.add(new RoomDTO("Living Room"));

        //Call method to be tested
        List<RoomDTO> result = controller.getListOfRoomsDTOInsideOrOutsideHouse(houseDTO, true);

        assertEquals(expectedList.size(), result.size(), "There should be the same number of rooms in the list");
        assertTrue(result.get(0).roomName.equals("Bedroom") || result.get(0).roomName.equals("Living Room"));
    }

    /**
     * Test case to verify that the controller gets the correct list of room DTOs that are outside the House using memory repositories.
     */
    @Test
    void successGetListOfRoomDTOOutsideHouseUsingMemoryRepositories() {
        //House section setup
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        //Room section setup
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("RoofTop");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Balcony");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 0.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));

        //Instantiate roomRepository
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        //Instantiate roomService
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        //Instantiate controller
        ListAllRoomsInsideOrOutsideOfHouseController controller = new ListAllRoomsInsideOrOutsideOfHouseController(roomService);

        HouseDTO houseDTO = new HouseDTO(houseID.toString(), "Rua Firmeza", "24B", "4000-009", "Porto", "Portugal", 41.14961, -8.61099);

        List<RoomDTO> expectedList = new ArrayList<>();
        expectedList.add(new RoomDTO("RoofTop"));
        expectedList.add(new RoomDTO("Balcony"));

        //Call method to be tested
        List<RoomDTO> result = controller.getListOfRoomsDTOInsideOrOutsideHouse(houseDTO, false);

        assertEquals(expectedList.size(), result.size(), "There should be the same number of rooms in the list");
        assertTrue(result.get(0).roomName.equals("RoofTop") || result.get(0).roomName.equals("Garden Section"));
    }

    /**
     * Test case to verify that the controller gets an empty list of room DTOs when there's no rooms inside the House using memory repositories.
     */
    @Test
    void failedGetEmptyListOfRoomDTOInsideHouseUsingMemoryRepositories() {
        //House section setup
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        //Room section setup
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("RoofTop");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Balcony");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 0.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));

        //Instantiate roomRepository
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        //Instantiate roomService
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        //Instantiate controller
        ListAllRoomsInsideOrOutsideOfHouseController controller = new ListAllRoomsInsideOrOutsideOfHouseController(roomService);

        HouseDTO houseDTO = new HouseDTO(houseID.toString(), "Rua Firmeza", "24B", "4000-009", "Porto", "Portugal", 41.14961, -8.61099);

        //Call method to be tested
        List<RoomDTO> result = controller.getListOfRoomsDTOInsideOrOutsideHouse(houseDTO, true);

        assertEquals(0, result.size(), "There should be the same number of rooms in the list");
    }

    /**
     * Test case to verify that the controller gets an empty list of room DTOs when there's no rooms outside the House using memory repositories.
     */
    @Test
    void failedGetEmptyListOfRoomDTOOutsideHouseUsingMemoryRepositories() {
        //House section setup
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        //Room section setup
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 3.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1, roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));

        //Instantiate roomRepository
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        //Instantiate roomService
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        //Instantiate controller
        ListAllRoomsInsideOrOutsideOfHouseController controller = new ListAllRoomsInsideOrOutsideOfHouseController(roomService);

        HouseDTO houseDTO = new HouseDTO(houseID.toString(), "Rua Firmeza", "24B", "4000-009", "Porto", "Portugal", 41.14961, -8.61099);

        //Call method to be tested
        List<RoomDTO> result = controller.getListOfRoomsDTOInsideOrOutsideHouse(houseDTO, false);

        assertEquals(0, result.size(), "There should be the same number of rooms in the list");
    }


    /**
     * Test case to verify that method fails to retrieve list of room DTO when services is null using memory repositories.
     */
    @Test
    void failRetrieveListOfRoomDTOWhenHouseIDNull() {
        //House section setup
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepository = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        //Room section setup
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 3.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));

        //Instantiate roomRepository
        RoomRepository roomRepository = new RoomRepositoryMem(roomData);

        //Instantiate roomService
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);

        //Instantiate controller
        ListAllRoomsInsideOrOutsideOfHouseController controller = new ListAllRoomsInsideOrOutsideOfHouseController(roomService);

        //Call method to be tested
        List<RoomDTO> result = controller.getListOfRoomsDTOInsideOrOutsideHouse(null, false);

        assertNull(result);
    }

    //---------------------- INTEGRATION TESTS WITH REPOSITORIES JPA ---------------------------------------------------

    /**
     * Test case to verify that the controller get the correct list of room DTOs that are inside the House using JPA repositories.
     */
    @Test
    void successGetListOfRoomDTOInsideHouseUsingJPARepositories() {
        // Create double for EntityManager to substitute the database dependency
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.getTransaction()).thenReturn(transaction);

        //House section
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        HouseRepository houseRepository = new HouseRepositoryJPAImp(factoryHouse, manager);
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));

        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(manager.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseID, location);

        //HouseDataModel section
        HouseDataModel houseDataModel = new HouseDataModel(house);
        // Set behaviour when checking if house exists - containsEntityByID() in HouseRepositoryJPAImp:
        when(manager.find(HouseDataModel.class, houseID.toString())).thenReturn(houseDataModel);

        //Room section
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        RoomRepository roomRepository = new RoomRepositoryJPAImp(factoryRoom, manager);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        RoomID roomID1 = new RoomID("Living Room");
        RoomID roomID2 = new RoomID("Bedroom");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        RoomID roomID3 = roomService.createRoomAndSave(roomID1, roomFloor, roomDimensions, houseID);
        RoomID roomID4 = roomService.createRoomAndSave(roomID2, roomFloor, roomDimensions, houseID);
        Room room1 = factoryRoom.createRoom(roomID3, roomFloor, roomDimensions, houseID);
        Room room2 = factoryRoom.createRoom(roomID4, roomFloor, roomDimensions, houseID);

        //RoomDataModel section
        RoomDataModel roomDataModel1 = new RoomDataModel(room1);
        RoomDataModel roomDataModel2 = new RoomDataModel(room2);
        List<RoomDataModel> roomDataModelList = new ArrayList<>();
        roomDataModelList.add(roomDataModel1);
        roomDataModelList.add(roomDataModel2);
        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepositoryJPAImp:
        Query queryDouble = mock(Query.class);
        when(manager.createQuery("SELECT e FROM RoomDataModel e WHERE e.houseID = :houseID")).thenReturn(queryDouble);
        when(queryDouble.getResultList()).thenReturn(roomDataModelList);

        //Instantiate controller
        ListAllRoomsInsideOrOutsideOfHouseController controller = new ListAllRoomsInsideOrOutsideOfHouseController(roomService);

        //////////////////// TEST CASE ////////////////////

        HouseDTO houseDTO = new HouseDTO(houseID.toString(), "Rua Firmeza", "24B", "4000-009", "Porto", "Portugal", 41.14961, -8.61099);

        //Act
        List<RoomDTO> result = controller.getListOfRoomsDTOInsideOrOutsideHouse(houseDTO, true);

        //Assert
        assertEquals("Living Room", result.get(0).roomName);
        assertEquals("Bedroom", result.get(1).roomName);
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES SPRING --------------------------------------------------

    /**
     * Test case to verify that the controller get the correct list of room DTOs that are inside the House using Spring Data repositories.
     */
    @Test
    void successGetListOfRoomDTOInsideHouseUsingSpringDataRepositories() {
        //Instantiation of Mappers
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();

        //House section
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        HouseRepositorySpringData houseRepositorySpringData = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepository = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringData, mapperHouseDataModel);
        HouseService houseService = new HouseService(houseRepository, factoryHouse);
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);
        // Set behaviour - containsEntityByID() in HouseRepository:
        when(houseRepositorySpringData.existsById(houseID.toString())).thenReturn(true);

        //Room section
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        RoomRepositorySpringData roomRepositorySpringData = mock(RoomRepositorySpringData.class);
        RoomRepository roomRepository = new RoomRepositorySpringDataImp(roomRepositorySpringData, factoryRoom, mapperRoomDataModel);
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        RoomID roomID1 = new RoomID("Living Room");
        RoomID roomID2 = new RoomID("Bedroom");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2, 2, 2);
        RoomID roomID3 = roomService.createRoomAndSave(roomID1, roomFloor, roomDimensions, houseID);
        RoomID roomID4 = roomService.createRoomAndSave(roomID2, roomFloor, roomDimensions, houseID);
        Room room1 = factoryRoom.createRoom(roomID3, roomFloor, roomDimensions, houseID);
        Room room2 = factoryRoom.createRoom(roomID4, roomFloor, roomDimensions, houseID);

        //RoomDataModel section
        RoomDataModel roomDataModel1 = new RoomDataModel(room1);
        RoomDataModel roomDataModel2 = new RoomDataModel(room2);
        List<RoomDataModel> roomDataModelList = new ArrayList<>();
        roomDataModelList.add(roomDataModel1);
        roomDataModelList.add(roomDataModel2);

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepository:
        when(roomRepositorySpringData.findAllByHouseID(houseID.toString())).thenReturn(roomDataModelList);

        //Instantiate controller
        ListAllRoomsInsideOrOutsideOfHouseController controller = new ListAllRoomsInsideOrOutsideOfHouseController(roomService);

        //////////////////// TEST CASE ////////////////////

        HouseDTO houseDTO = new HouseDTO(houseID.toString(), "Rua Firmeza", "24B", "4000-009", "Porto", "Portugal", 41.14961, -8.61099);

        List<RoomDTO> result = controller.getListOfRoomsDTOInsideOrOutsideHouse(houseDTO, true);

        //Assert
        assertEquals("Living Room", result.get(0).roomName);
        assertEquals("Bedroom", result.get(1).roomName);    }

}