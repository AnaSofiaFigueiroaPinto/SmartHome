package smarthome.controllercli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.ImpFactoryHouse;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.house.House;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.*;

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

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for the {@code EditRoomController} class.
 */
class EditRoomControllerTest {
    private RoomService roomServices;


    /**
     * Test case to verify that the controller successfully
     * edits a selected room.
     */
    @Test
    void successfullyEditSelectedRoom() {
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

        roomServices = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);

        EditRoomController controller = new EditRoomController(roomServices);
        RoomDTO roomDTO = new RoomDTO("Living Room",
                1, 20.0, 15.0, 10.0);

        RoomDTO result = controller.editSelectedRoomAndSaveInRepository(roomDTO);

        assertEquals(roomDTO.roomName, result.roomName, "Room edited successfully");
    }

    /**
     * Test case to verify that the controller fails to edit a
     * room when the room is not found.
     */
    @Test
    void failToEditSelectedNullRoom() {
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

        roomServices = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
        EditRoomController controller = new EditRoomController(roomServices);

        RoomDTO roomDTO = null;

        RoomDTO result = controller.editSelectedRoomAndSaveInRepository(roomDTO);

        assertNull(result, "Null room returned");
    }

    /**
     * Test case to verify that the controller fails to edit a
     * room when the room is not found.
     */
    @Test
    void failToEditSelectedRoomNotFound() {
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

        roomServices = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
        EditRoomController controller = new EditRoomController(roomServices);

        RoomDTO roomDTO = new RoomDTO("Kitchen", 2,
                15.0, 12.0, 8.0);

        RoomDTO result = controller.editSelectedRoomAndSaveInRepository(roomDTO);

        assertNull(result, "Null room returned");
    }

    /**
     * Test case to verify that the controller fails to edit a
     * room when the length is invalid.
     */
    @Test
    void failToEditSelectedRoomInvalidLength() {
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

        roomServices = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
        EditRoomController controller = new EditRoomController(roomServices);

        RoomDTO roomDTO = new RoomDTO("Living Room", 1,
                0.0, 18.0, 12.0);

        RoomDTO result = controller.editSelectedRoomAndSaveInRepository(roomDTO);
        assertNull(result, "Invalid room length");
    }

    /**
     * Test case to verify that the controller fails to edit a
     * room when the width is invalid.
     */
    @Test
    void failToEditSelectedRoomInvalidWidth() {
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

        roomServices = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
        EditRoomController controller = new EditRoomController(roomServices);

        RoomDTO roomDTO = new RoomDTO("Bedroom", 1,
                50.0, 0.0, 12.0);

        RoomDTO result = controller.editSelectedRoomAndSaveInRepository(roomDTO);
        assertNull(result, "Invalid room width");
    }

    /**
     * Test case to verify that the controller fails to edit a room when the height is invalid.
     */
    @Test
    void failToEditSelectedRoomInvalidHeigth() {
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepository houseRepositoryMem = new HouseRepositoryMem(houseData);
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        House house = factoryHouse.createHouseWithOutLocation();
        HouseID houseID = house.identity();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        Map<RoomID, Room> roomData = new HashMap<>();
        RoomID roomID1 = new RoomID("Bedroom");
        RoomFloor roomFloor1 = new RoomFloor(2);

        RoomID roomID2 = new RoomID("Living Room");
        RoomFloor roomFloor2 = new RoomFloor(1);

        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);

        roomData.put(roomID1, factoryRoom.createRoom(roomID1,roomFloor1, roomDimensions, houseID));
        roomData.put(roomID2, factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID));
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

        roomServices = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);
        EditRoomController controller = new EditRoomController(roomServices);

        RoomDTO roomDTO = new RoomDTO("Bedroom", 1,
                50.0, 10.0, -2.0);

        RoomDTO result = controller.editSelectedRoomAndSaveInRepository(roomDTO);
        assertNull(result, "Invalid room height");
    }

    //---------------------- INTEGRATION TESTS WITH REPOSITORIES JPA ---------------------------------------------------

    /**
     * Test case to verify that the controller successfully
     * edits a selected room, using JPA repository.
     */
    @Test
    void successfullyEditSelectedRoomJPARepository() {
        // Create double for EntityManager to substitute the database dependency
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.getTransaction()).thenReturn(transaction);

        // Create factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        // Create repositories JPA
        HouseRepository houseRepositoryJPA = new HouseRepositoryJPAImp(factoryHouse, manager);
        RoomRepository roomRepositoryJPA = new RoomRepositoryJPAImp(factoryRoom,manager);

        // Create services
        HouseService houseService = new HouseService(houseRepositoryJPA, factoryHouse);
        roomServices = new RoomService(roomRepositoryJPA, factoryRoom, houseRepositoryJPA);

        // HOUSE -------------------------------------------------------------------------------------------------------
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007",
                "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));
        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(manager.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        // Create house
        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        // Set behaviour when checking if house exists - containsEntityByID() in HouseRepositoryJPAImp:
        // use factory to create an object that is equal to the one created by services
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseID, location);
        HouseDataModel houseDataModel = new HouseDataModel(house);
        when(manager.find(HouseDataModel.class, houseID.toString())).thenReturn(houseDataModel);

        // ROOM --------------------------------------------------------------------------------------------------------
        //Create room
        RoomID roomID2 = new RoomID("Kitchen");
        RoomFloor roomFloor2 = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);
        roomServices.createRoomAndSave(roomID2, roomFloor2, roomDimensions, houseID);

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepositoryJPAImp:
        Query queryRoomDouble = mock(Query.class);
        when(manager.createQuery("SELECT e FROM RoomDataModel e WHERE e.houseID = :houseID")).thenReturn(queryRoomDouble);
        // use factory to create an object that is equal to the first one created by services
        Room room = factoryRoom.createRoom(roomID2, roomFloor2, roomDimensions, houseID);
        // create a list of data models to return
        RoomDataModel roomDataModel = new RoomDataModel(room);
        List<RoomDataModel> listOfRoomDataModelsInRepo = List.of(roomDataModel);
        when(queryRoomDouble.getResultList()).thenReturn(listOfRoomDataModelsInRepo);

        // Set behaviour to find room when editing - findEntityByID() in RoomRepository:
        when(manager.find(RoomDataModel.class, roomID2.toString())).thenReturn(roomDataModel);

        //Instantiate controller to be tested
        EditRoomController controller = new EditRoomController(roomServices);

        //////////////////// TEST CASE ////////////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomServices.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        //DTO with new room data for editing
        RoomDTO roomDTO = new RoomDTO(selectedRoomID.toString(),
                1, 20.0, 15.0, 10.0);

        RoomDTO result = controller.editSelectedRoomAndSaveInRepository(roomDTO);

        assertEquals(roomDTO.roomName, result.roomName, "Room edited successfully");
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES SPRING --------------------------------------------------

    /**
     * Test case to verify that the controller successfully
     * edits a selected room, using Spring Data repository.
     */
    @Test
    void successfullyEditSelectedRoomSpringDataRepository() {
        //Instantiation of Mappers
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();

        // Create factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        // Create repositories Spring
        HouseRepositorySpringData houseRepositorySpringDataDouble = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepositorySpringDataImp = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringDataDouble, mapperHouseDataModel);

        RoomRepositorySpringData roomRepositorySpringDataDouble = mock(RoomRepositorySpringData.class);
        RoomRepository roomRepositorySpringDataImp = new RoomRepositorySpringDataImp(roomRepositorySpringDataDouble, factoryRoom, mapperRoomDataModel);

        // Create services
        HouseService houseService = new HouseService(houseRepositorySpringDataImp, factoryHouse);
        roomServices = new RoomService(roomRepositorySpringDataImp, factoryRoom, houseRepositorySpringDataImp);

        // HOUSE -------------------------------------------------------------------------------------------------------
        // Create house
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007",
                "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));

        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);
        // Set behaviour - containsEntityByID() in HouseRepository:
        when(houseRepositorySpringDataDouble.existsById(houseID.toString())).thenReturn(true);

        // ROOM --------------------------------------------------------------------------------------------------------
        // Create room
        RoomID roomID2 = new RoomID("Kitchen");
        RoomFloor roomFloor2 = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(15.0, 12.0, 8.0);

        RoomID roomID = roomServices.createRoomAndSave(roomID2, roomFloor2, roomDimensions, houseID);
        when(roomRepositorySpringDataDouble.existsById(roomID.toString())).thenReturn(true);

        // Set behaviour to find room when editing - findEntityByID() in RoomRepository:
        // use factory to create an object that is equal to the one created by services
        Room roomEdited = factoryRoom.createRoom(roomID, roomFloor2, roomDimensions, houseID);
        RoomDataModel roomDataModel = new RoomDataModel(roomEdited);
        when(roomRepositorySpringDataDouble.findById(roomID.toString())).thenReturn(Optional.of(roomDataModel));

        // Set behaviour to list Rooms in House - findByHouseID() in RoomRepository:
        List<RoomDataModel> listOfRoomDataModelsInRepo = List.of(roomDataModel);
        when(roomRepositorySpringDataDouble.findAllByHouseID(houseID.toString())).thenReturn(listOfRoomDataModelsInRepo);

        EditRoomController controller = new EditRoomController(roomServices);

        //////////////////// TEST CASE ////////////////////

        // List rooms to select one
        List<RoomID> listOfRooms = roomServices.getListOfRoomsInHouse(houseID);
        RoomID selectedRoomID = listOfRooms.get(0);

        //DTO with new room data for editing
        RoomDTO roomDTO = new RoomDTO(selectedRoomID.toString(),
                1, 20.0, 15.0, 10.0);

        // Act
        RoomDTO result = controller.editSelectedRoomAndSaveInRepository(roomDTO);

        // Assert
        assertEquals(roomDTO.roomName, result.roomName, "Room edited successfully");
    }


}