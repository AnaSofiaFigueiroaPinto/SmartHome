package smarthome.mapper;

import org.junit.jupiter.api.Test;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;
import smarthome.persistence.repositoriesmem.RoomRepositoryMem;

import java.util.List;
import java.util.Optional;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code RoomMapperDTO} class.
 */
class RoomMapperDTOTest {

    /**
     * Test method to ensure correct conversion of Room to RoomDTO.
     * <p>
     * This test checks whether the RoomMapper's roomToDTO method
     * correctly converts a Room object into a RoomDTO object with
     * matching properties.
     */
    @Test
    void roomToDTOConverts() {
        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        when(roomIdDouble.toString()).thenReturn("Bedroom");
        when(roomFloorDouble.getRoomFloor()).thenReturn(1);
        when(roomDimensionsDouble.getLength()).thenReturn(1.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(1.0);
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(houseIdDouble.toString()).thenReturn("3");

        Room roomDouble = mock(Room.class);
        when(roomDouble.identity()).thenReturn(roomIdDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);

        RoomMapperDTO roomMapperDTO = new RoomMapperDTO();
        RoomDTO roomDTO = roomMapperDTO.roomToDTO(roomDouble);

        assertNotNull(roomDTO);
        assertEquals(roomDouble.identity().toString(), roomDTO.roomName);
        assertEquals(roomDouble.getRoomFloor().getRoomFloor(), roomDTO.floorNumber);
        assertEquals(roomDouble.getRoomDimensions().getLength(), roomDTO.roomLength);
        assertEquals(roomDouble.getRoomDimensions().getWidth(), roomDTO.roomWidth);
        assertEquals(roomDouble.getRoomDimensions().getHeight(), roomDTO.roomHeight);

    }

    /**
     * Test method to verify the behavior of RoomMapper's roomToDTO method
     * when a null Room object is provided.
     */
    @Test
    void roomToDTONullRoom() {
        Room room = null;
        RoomMapperDTO roomMapperDTO = new RoomMapperDTO();
        RoomDTO roomDTO = roomMapperDTO.roomToDTO(room);
        assertNull(roomDTO);
    }

    /**
     * Test method to verify the behavior of RoomMapper's roomToDTO method
     * when a null Room object is provided.
     * <p>
     * This test ensures that when a null Room object is passed to roomToDTO,
     * the method returns null, indicating no conversion is performed.
     */
    @Test
    void roomToDTONullRoomID() {
        RoomID roomID = null;
        RoomMapperDTO roomMapperDTO = new RoomMapperDTO();
        RoomDTO roomDTO = roomMapperDTO.roomToDTO(roomID);
        assertNull(roomDTO);
    }

    /**
     * Test method to verify the conversion of a RoomRepository to a map of RoomDTOs.
     * <p>
     * This test initializes a RoomRepository with some Room objects, then calls
     * RoomMapper's roomToDTOMap method to convert the RoomRepository to a map of
     * RoomDTOs. It checks whether the mappings in the resulting map are as expected.
     */
    @Test
    void roomIDsToDTOList() {
        RoomID roomIdDouble = mock(RoomID.class);
        RoomFloor roomFloorDouble = mock(RoomFloor.class);
        RoomDimensions roomDimensionsDouble = mock(RoomDimensions.class);
        HouseID houseIdDouble = mock(HouseID.class);

        when(roomIdDouble.toString()).thenReturn("Bedroom");
        when(roomFloorDouble.getRoomFloor()).thenReturn(1);
        when(roomDimensionsDouble.getLength()).thenReturn(1.0);
        when(roomDimensionsDouble.getWidth()).thenReturn(1.0);
        when(roomDimensionsDouble.getHeight()).thenReturn(3.0);
        when(houseIdDouble.toString()).thenReturn("3");

        Room roomDouble = mock(Room.class);
        when(roomDouble.identity()).thenReturn(roomIdDouble);
        when(roomDouble.getRoomFloor()).thenReturn(roomFloorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIdDouble);

        RoomRepositoryMem roomRepositoryMemDouble = mock(RoomRepositoryMem.class);

        //when(roomRepositoryMemDouble.findAllEntities()).thenReturn(Collections.singletonList(roomDouble));

        RoomMapperDTO roomMapperDTO = new RoomMapperDTO();

        List<RoomDTO> result = roomMapperDTO.roomIDsToDTOList(listOf(roomIdDouble));
        //Map<RoomDTO, Room> result = roomMapperDTO.roomIDsToDTOList(roomRepositoryMemDouble);

        when(roomRepositoryMemDouble.findEntityByID(roomIdDouble)).thenReturn(Optional.of(roomDouble));

        assertEquals(1, result.size());
        //assertEquals(roomRepositoryMemDouble.findEntityByID(roomIdDouble).get(), result.get(new RoomDTO("Bedroom", 1, 1.0, 1.0, 3.0)));

    }

//    /**
//     * Test method to verify the behavior of RoomMapper's roomToDTOMap method
//     * when the provided RoomRepository is empty.
//     * <p>
//     * This test ensures that when an empty RoomRepository is passed to roomToDTOMap,
//     * the method returns an empty map, indicating that no conversion is performed.
//     */
//    @Test
//    void emptyRepositoryRoomToDTO() {
//        RoomRepositoryMem roomRepositoryMemDouble = mock(RoomRepositoryMem.class);
//
//        // Call the method under test
//        RoomMapperDTO roomMapperDTO = new RoomMapperDTO();
//        Map<RoomDTO, Room> result = roomMapperDTO.roomIDsToDTOList(roomRepositoryMemDouble);
//
//        // Verify that the result is an empty map
//        assertEquals(0, result.size());
//    }
}
