package smarthome.controllercli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import smarthome.domain.house.FactoryHouse;
import smarthome.domain.house.House;
import smarthome.domain.house.ImpFactoryHouse;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.*;
import smarthome.persistence.jpa.datamodel.HouseDataModel;
import smarthome.persistence.jpa.datamodel.MapperHouseDataModel;
import smarthome.persistence.jpa.datamodel.MapperRoomDataModel;
import smarthome.persistence.jpa.repositoriesjpa.HouseRepositoryJPAImp;
import smarthome.persistence.jpa.repositoriesjpa.RoomRepositoryJPAImp;
import smarthome.persistence.repositoriesmem.HouseRepositoryMem;
import smarthome.persistence.repositoriesmem.RoomRepositoryMem;
import smarthome.persistence.springdata.repositoriesspringdata.HouseRepositorySpringData;
import smarthome.persistence.springdata.repositoriesspringdata.HouseRepositorySpringDataImp;
import smarthome.persistence.springdata.repositoriesspringdata.RoomRepositorySpringData;
import smarthome.persistence.springdata.repositoriesspringdata.RoomRepositorySpringDataImp;
import smarthome.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.mapper.RoomDTO;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for the {@code AddNewRoomToHouseController} class.
 */
class AddNewRoomToHouseControllerTest {

    /**
     * Controller instance to be used in the tests.
     */
    private AddNewRoomToHouseController controller;

    /**
     * Sets up the test environment before each test method.
     * <p>
     * This method initializes various dependencies required
     * for testing the {@code AddNewRoomToHouseController}
     * class. It creates a house instance using the factory,
     * adds it to the house repository, and obtains its ID.
     * It also initializes a room repository and populates
     * it with a sample room.
     * Finally, it sets up the room service and controller
     * instances for testing.
     */
    @BeforeEach
    void setUp() {
        // Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        // Instantiate a house object
        House house = factoryHouse.createHouseWithOutLocation();

        // Instantiate a house repository with a blank data map and save house
        Map<HouseID, House> houseData = new HashMap<>();
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem(houseData);
        HouseID houseID = house.identity();
        houseData.put(houseID, house);

        // Instantiate a room repository with a blank data map
        Map<RoomID, Room> roomData = new HashMap<>();
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem(roomData);

        RoomService services = new RoomService(roomRepositoryMem, factoryRoom, houseRepositoryMem);

        controller = new AddNewRoomToHouseController(services, houseID);
    }

    /**
     * Tests the creation and addition of a new room to a house,
     * verifying success.
     * <p>
     * If the operation is successful, the method returns a roomDTO
     * equal to the given RoomDTO, indicating that the new room was
     * created and added to the house successfully.
     */
    @Test
    void testCreationAndAdditionOfANewRoomSuccess() {
        String roomID = "Living Room";
        int roomFloor = 1;
        double length = 1.0;
        double width = 2.0;
        double height = 3.0;
        RoomDTO roomDTO = new RoomDTO(roomID, roomFloor, length, width, height);

        RoomDTO result = controller.createRoomAndSaveInRepository(roomDTO);

        assertEquals(roomDTO.roomName, result.roomName, "The roomDTO should " +
                "be the same");
    }

    /**
     * Tests the failure scenario of creating and adding a new room
     * with an already existing ID.
     * <p>
     * Since the ID "Bedroom" already exists in the repository,
     * the operation should fail, and the method returns a null roomDTO.
     */
    @Test
    void testFailCreationAndAdditionOfANewRoomAlreadyExistingID() {
        String roomID = "Bedroom";
        int roomFloor = 1;
        double length = 1.0;
        double width = 2.0;
        double height = 3.0;

        RoomDTO roomDTO = new RoomDTO(roomID, roomFloor, length, width, height);
        controller.createRoomAndSaveInRepository(roomDTO);
        RoomDTO result = controller.createRoomAndSaveInRepository(roomDTO);

        assertNull(result, "The RoomDTO should be Null");
    }

    /**
     * Tests the failure scenario of creating and adding a new room
     * giving a null RoomDTO.
     * <p>
     * Since the roomDTO is null, the operation should fail, and
     * the method returns a null roomDTO.
     */
    @Test
    void testFailCreationAndAdditionOfANewRoomNullRoomDTO() {
        RoomDTO result = controller.createRoomAndSaveInRepository(null);

        assertNull(result, "The RoomDTO should be Null");
    }

    /**
     * Tests the failure scenario of creating and adding a new
     * room with invalid dimensions.
     * <p>
     * Since the provided dimensions are invalid, the creation of
     * the room fails, and the method returns a null roomDTO.
     */
    @Test
    void testFailCreationAndAdditionOfANewRoomInvalidDimensions() {
        String roomID = "Bedroom";
        int roomFloor = 1;
        double length = 0.0;
        double width = 0.0;
        double height = -0.1;

        RoomDTO roomDTO = new RoomDTO(roomID, roomFloor, length, width,
                height);
        RoomDTO result = controller.createRoomAndSaveInRepository(
                roomDTO);

        assertNull(result, "The RoomDTO should be Null");
    }

    //---------------------- INTEGRATION TESTS WITH REPOSITORIES JPA ---------------------------------------------------

    /**
     * Successfully added a new room to House using JPA repositories.
     */
    @Test
    void successfullyAddNewRoomToHouseJPARepository() {
        // Create double for EntityManager to substitute the database dependency
        EntityManager manager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(manager.getTransaction()).thenReturn(transaction);

        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        //Instantiation of necessary Repositories
        HouseRepository houseRepository = new HouseRepositoryJPAImp(factoryHouse, manager);
        RoomRepository roomRepository = new RoomRepositoryJPAImp(factoryRoom, manager);

        //Instantiation of necessary services
        RoomService roomService = new RoomService(roomRepository, factoryRoom, houseRepository);
        HouseService houseService = new HouseService(houseRepository, factoryHouse);

        // HOUSE -------------------------------------------------------------------------------------------------------
        // Create house
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007", "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));
        // Set behaviour for save() method in RepositoryJPA to find zero existing houses
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(manager.createQuery("SELECT COUNT(e) FROM HouseDataModel e", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);

        // Set behaviour when checking if house exists - containsEntityByID() in HouseRepositoryJPAImp:
        // use factory to create an object that is equal to the one created by services
        House house = factoryHouse.createHouseWithOrWithoutLocation(houseID, location);
        //HouseDataModel
        HouseDataModel houseDataModel = new HouseDataModel(house);
        // set the find method to return the data model
        when(manager.find(HouseDataModel.class, houseID.toString())).thenReturn(houseDataModel);

        //Instantiate controller
        AddNewRoomToHouseController controller = new AddNewRoomToHouseController(roomService, houseID);

        //////////////////// TEST CASE ////////////////////

        //RoomDTO to be created
        RoomDTO roomDTO = new RoomDTO("BedRoom", 1, 2, 2, 2);

        //Act
        RoomDTO result = controller.createRoomAndSaveInRepository(roomDTO);

        //Assert
        assertEquals(roomDTO.roomName, result.roomName, "Room added successfully");
    }

    //-------------------- INTEGRATION TESTS WITH REPOSITORIES SPRING --------------------------------------------------

    /**
     * Successfully added a new room to House using SpringData repositories.
     */
    @Test
    void successfullyAddNewRoomToHouseSpringDataRepository() {
        //Instantiate all needed factories
        FactoryHouse factoryHouse = new ImpFactoryHouse();
        FactoryRoom factoryRoom = new ImpFactoryRoom();

        //Instantiation of Mappers
        MapperHouseDataModel mapperHouseDataModel = new MapperHouseDataModel();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();

        //Instantiation of necessary Repositories
        HouseRepositorySpringData houseRepositorySpringDataDouble = mock(HouseRepositorySpringData.class);
        HouseRepository houseRepositorySpringDataImp = new HouseRepositorySpringDataImp(factoryHouse, houseRepositorySpringDataDouble, mapperHouseDataModel);

        RoomRepositorySpringData roomRepositorySpringDataDouble = mock(RoomRepositorySpringData.class);
        RoomRepository roomRepositorySpringDataImp = new RoomRepositorySpringDataImp(
                roomRepositorySpringDataDouble, factoryRoom, mapperRoomDataModel);

        // Instantiate needed services
        HouseService houseService = new HouseService(houseRepositorySpringDataImp, factoryHouse);
        RoomService roomService = new RoomService(roomRepositorySpringDataImp, factoryRoom, houseRepositorySpringDataImp);

        // HOUSE -------------------------------------------------------------------------------------------------------
        // Create house
        Location location = new Location(new Address("Rua do Ouro", "27", "4000-007",
                "Porto", "Portugal"), new GPSCode(41.178553, -8.608035));

        HouseID houseID = houseService.createAndSaveHouseWithLocation(location);
        // Set behaviour - containsEntityByID() in HouseRepository:
        when(houseRepositorySpringDataDouble.existsById(houseID.toString())).thenReturn(true);

        //Instantiate controller
        AddNewRoomToHouseController controller = new AddNewRoomToHouseController(roomService, houseID);

        //////////////////// TEST CASE ////////////////////

        //RoomDTO to be created
        RoomDTO roomDTO = new RoomDTO("Kitchen",
                1, 20.0, 15.0, 10.0);

        //Act
        RoomDTO result = controller.createRoomAndSaveInRepository(roomDTO);

        //Assert
        assertEquals(roomDTO.roomName, result.roomName, "Room edited successfully");
    }
}