package smarthome.domain.room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@SpringBootTest (classes = {ImpFactoryRoom.class})
class ImpFactoryRoomTest {

    @InjectMocks
    ImpFactoryRoom factoryRoomImp;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test cases for the {@code createRoom} method of {@code ImpFactoryRoom} class.
     */
    @Test
    void testValidCreateRoom() {
        RoomID roomID = mock(RoomID.class);
        RoomFloor roomFloor = mock(RoomFloor.class);
        RoomDimensions roomDimensions = mock(RoomDimensions.class);
        HouseID houseID = mock(HouseID.class);

        Room room = factoryRoomImp.createRoom(roomID, roomFloor, roomDimensions, houseID);

        assertNotNull(room);
    }

    /**
     * Test case for the {@code createRoom} method of {@code ImpFactoryRoom} class with null RoomID.
     */
    @Test
    void testCreateRoomRoomIDNull() {
        RoomFloor roomFloor = mock(RoomFloor.class);
        RoomDimensions roomDimensions =mock(RoomDimensions.class);
        HouseID houseID = mock(HouseID.class);

        // Act
        Room room = factoryRoomImp.createRoom(null, roomFloor, roomDimensions, houseID);

        // Assert
        assertNull(room);
    }

    /**
     * Test case for the {@code createRoom} method of {@code ImpFactoryRoom} class with null RoomID.
     */
    @Test
    void testCreateRoomRoomFloorNull() {
        RoomID roomID = mock(RoomID.class);
        RoomDimensions roomDimensions = mock(RoomDimensions.class);
        HouseID houseID = mock(HouseID.class);

        Room room = factoryRoomImp.createRoom(roomID, null, roomDimensions, houseID);

        assertNull(room);
    }

    /**
     * Test case for the {@code createRoom} method of {@code ImpFactoryRoom} class with null RoomDimensions.
     */
    @Test
    void testCreateRoomRoomDimensionsNull() {
        RoomID roomID = mock(RoomID.class);
        RoomFloor roomFloor = mock(RoomFloor.class);
        HouseID houseID = mock(HouseID.class);

        Room room = factoryRoomImp.createRoom(roomID, roomFloor, null, houseID);

        assertNull(room);
    }

    /**
     * Test case for the {@code createRoom} method of {@code ImpFactoryRoom} class with null HouseID.
     */
    @Test
    void testCreateRoomHouseIDNull() {
        RoomID roomID = mock(RoomID.class);
        RoomFloor roomFloor = mock(RoomFloor.class);
        RoomDimensions roomDimensions = mock(RoomDimensions.class);

        Room room = factoryRoomImp.createRoom(roomID, roomFloor, roomDimensions, null);

        assertNull(room);
    }
}