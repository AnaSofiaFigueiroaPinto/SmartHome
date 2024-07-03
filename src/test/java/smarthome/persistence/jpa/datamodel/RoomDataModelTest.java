package smarthome.persistence.jpa.datamodel;

import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;

import static org.junit.jupiter.api.Assertions.*;

class RoomDataModelTest {
    private RoomDataModel roomDataModel;

    /**
     * Sets up the necessary test environment before each test case.
     */
    @BeforeEach
    void setup() {
        HouseID houseID = new HouseID("house1");
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        RoomID roomID = new RoomID("room1");
        RoomFloor roomFloor = new RoomFloor(1);
        RoomDimensions roomDimensions = new RoomDimensions(2.5, 5.0, 4.0);
        Room room = factoryRoom.createRoom(roomID, roomFloor, roomDimensions, houseID);
        roomDataModel = new RoomDataModel(room);
    }

    @Test
    void getRoomID() {
        assertEquals("room1", roomDataModel.getRoomID());
    }

    @Test
    void getHouseID() {
        assertEquals("house1", roomDataModel.getHouseID());
    }

    @Test
    void getFloor() {
        assertEquals(1, roomDataModel.getFloor());
    }

    @Test
    void getHeight() {
        assertEquals(4.0, roomDataModel.getHeight());
    }

    @Test
    void getLength() {
        assertEquals(2.5, roomDataModel.getLength());
    }

    @Test
    void getWidth() {
        assertEquals(5.0, roomDataModel.getWidth());
    }

    /**
     * Test case for Room Data Model empty constructor.
     */
    @Test
    void testEmptyConstructor() {
        RoomDataModel roomDataModel = new RoomDataModel();

        assertNotNull(roomDataModel);
    }

    /**
     * Test where the method is called with a valid Room object.
     * The method should return true, indicating that the update was successful.
     */
    @Test
    void verifyUpdateFromDomain() {
        HouseID houseID = new HouseID("house2");
        FactoryRoom factoryRoom = new ImpFactoryRoom();
        RoomID roomID = new RoomID("room2");
        RoomFloor roomFloor = new RoomFloor(2);
        RoomDimensions roomDimensions = new RoomDimensions(3.0, 6.0, 5.0);

        Room newRoom = factoryRoom.createRoom(roomID, roomFloor, roomDimensions, houseID);

        boolean roomUpdated = roomDataModel.updateFromDomain(newRoom);

        assertTrue(roomUpdated);
    }

    /**
     * Test where the method is called with a null argument.
     * The method should return false, indicating that the update failed.
     */
    @Test
    void failUpdateFromDomain() {
        boolean roomUpdated = roomDataModel.updateFromDomain(null);

        assertFalse(roomUpdated);
    }
}