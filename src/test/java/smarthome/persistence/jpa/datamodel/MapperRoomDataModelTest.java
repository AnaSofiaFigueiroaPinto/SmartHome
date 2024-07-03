package smarthome.persistence.jpa.datamodel;

import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MapperRoomDataModelTest {

	/**
	 * The variables used in the tests.
	 */
	private FactoryRoom factoryRoomDouble;
	private RoomDataModel roomDataModelDouble;
	private Room roomDouble;
	private MapperRoomDataModel mapper;
	private String roomName;
	private int floorNumber;
	private double length;
	private double width;
	private double height;
	private String houseID;

	/**
	 *  Sets up the necessary test environment before each test case.
	 *  Initializes mock objects and defines behavior for their methods to simulate real objects.
	 */
	@BeforeEach
	void setUp() {
		// Data for the RoomDataModel
		roomName = "LivingRoom";
		floorNumber = 2;
		length= 2.0;
		width = 2.0;
		height = 3.0;
		houseID = "house123";

		// Creating the class to test
		mapper = new MapperRoomDataModel();

		// Creating necessary doubles
		roomDataModelDouble = mock(RoomDataModel.class);
		factoryRoomDouble = mock(ImpFactoryRoom.class);
		roomDouble = mock(Room.class);
	}

	/**
	 * Test case to verify the successful conversion of a RoomDataModel object to a Room object.
	 * <p>
	 * This test ensures that the toDomain method of the DataModelRoomMapper class correctly converts
	 * a RoomDataModel object to a Room object using the provided factoryRoomDouble. It verifies that
	 * the returned Room object matches the expected roomDouble object.
	 */

	@Test
	void successConvertRoomDataModelToDomain () {
		try (MockedConstruction<HouseID> houseIDMockedConstruction = mockConstruction(HouseID.class, (mock, context) -> {
			when(roomDataModelDouble.getHouseID()).thenReturn(houseID);

		})) {
			try (MockedConstruction<RoomID> roomIDMockedConstruction = mockConstruction(RoomID.class, (mock, context) -> {
				when(roomDataModelDouble.getRoomID()).thenReturn(roomName);

			})) {
				try (MockedConstruction<RoomFloor> roomFloorMockedConstruction = mockConstruction(RoomFloor.class, (mock, context) -> {
					when(roomDataModelDouble.getFloor()).thenReturn(floorNumber);

				})) {
					try (MockedConstruction<RoomDimensions> roomDimensionsMockedConstruction = mockConstruction(RoomDimensions.class, (mock, context) -> {
						when(roomDataModelDouble.getLength()).thenReturn(length);
						when(roomDataModelDouble.getWidth()).thenReturn(width);
						when(roomDataModelDouble.getHeight()).thenReturn(height);

						when(factoryRoomDouble.createRoom(any(), any(), any(), any())).thenReturn(roomDouble);

					})) {
						Room result = mapper.toDomain(factoryRoomDouble, roomDataModelDouble);
						assertEquals(roomDouble, result);
					}
				}
			}
		}
	}

	/**
	 * Test case to verify the failure of converting a null RoomDataModel object to a Room object.
	 * <p>
	 * This test ensures that the toDomain method of the DataModelRoomMapper class returns null
	 * when a null RoomDataModel object is provided as input.
	 */
	@Test
	void failConvertDataModelRoomToDomainDataModelNull(){

		Room result = mapper.toDomain(factoryRoomDouble, null);
		assertNull(result);
	}

	/**
	 * Test case to verify the failure of converting a null RoomDataModel object to a Room object.
	 * <p>
	 * This test ensures that the toDomain method of the DataModelRoomMapper class returns null
	 * when a null FactoryRoom object is provided as input.
	 */
	@Test
	void failConvertDataModelRoomToDomainFactoryNull(){
		try (MockedConstruction<HouseID> houseIDMockedConstruction = mockConstruction(HouseID.class, (mock, context) -> {
			when(roomDataModelDouble.getHouseID()).thenReturn(houseID);

		})) {
			try (MockedConstruction<RoomID> roomIDMockedConstruction = mockConstruction(RoomID.class, (mock, context) -> {
				when(roomDataModelDouble.getRoomID()).thenReturn(roomName);

			})) {
				try (MockedConstruction<RoomFloor> roomFloorMockedConstruction = mockConstruction(RoomFloor.class, (mock, context) -> {
					when(roomDataModelDouble.getFloor()).thenReturn(floorNumber);

				})) {
					try (MockedConstruction<RoomDimensions> roomDimensionsMockedConstruction = mockConstruction(RoomDimensions.class, (mock, context) -> {
						when(roomDataModelDouble.getLength()).thenReturn(length);
						when(roomDataModelDouble.getWidth()).thenReturn(width);
						when(roomDataModelDouble.getHeight()).thenReturn(height);

					})) {
						Room result = mapper.toDomain(null, roomDataModelDouble);
						assertNull(result);
					}
				}
			}
		}
	}

	/**
	 * Test case to verify the successful conversion of a list of RoomDataModel objects to Room objects.
	 * <p>
	 * This test ensures that the toDomainList method of the DataModelRoomMapper class
	 * correctly converts a list of RoomDataModel objects to Room objects using the provided factoryRoomDouble.
	 * It verifies that the returned Iterable of Room objects content is the same as the expected list of Room objects.
	 */
	@Test
	void successConvertRoomDataModelListToRoomDomainList() {
		List<RoomDataModel> roomDataModelList = Arrays.asList(roomDataModelDouble);

		try (MockedConstruction<HouseID> houseIDMockedConstruction = mockConstruction(HouseID.class, (mock, context) -> {
			when(roomDataModelDouble.getHouseID()).thenReturn(houseID);

		})) {
			try (MockedConstruction<RoomID> roomIDMockedConstruction = mockConstruction(RoomID.class, (mock, context) -> {
				when(roomDataModelDouble.getRoomID()).thenReturn(roomName);

			})) {
				try (MockedConstruction<RoomFloor> roomFloorMockedConstruction = mockConstruction(RoomFloor.class, (mock, context) -> {
					when(roomDataModelDouble.getFloor()).thenReturn(floorNumber);

				})) {
					try (MockedConstruction<RoomDimensions> roomDimensionsMockedConstruction = mockConstruction(RoomDimensions.class, (mock, context) -> {
						when(roomDataModelDouble.getLength()).thenReturn(length);
						when(roomDataModelDouble.getWidth()).thenReturn(width);
						when(roomDataModelDouble.getHeight()).thenReturn(height);

						when(factoryRoomDouble.createRoom(any(), any(), any(), any())).thenReturn(roomDouble);

					})) {

						List<Room> expected = Collections.singletonList(roomDouble);

						Iterable<Room> result = mapper.toDomainList(factoryRoomDouble, roomDataModelList);

						assertEquals(expected, result);
					}
				}
			}
		}
	}

	/**
	 * Test case to verify the failure of converting a null list of RoomDataModel objects to Room objects.
	 * <p>
	 * This test ensures that the toDomainList method of the DataModelRoomMapper class returns null
	 * when a null list of RoomDataModel objects is provided as input.
	 */
	@Test
	void failConvertRoomDataModelListToRoomDomainListWhenListNull() {
		Iterable<Room> result = mapper.toDomainList(factoryRoomDouble, null);

		assertNull(result);
	}

	/**
	 * Test case to verify the failure of converting a list of RoomDataModel objects when the factory is null.
	 * <p>
	 * This test ensures that the toDomainList method of the DataModelRoomMapper class returns null
	 * when a null factoryRoom object is provided as input.
	 */
	@Test
	void failConvertRoomDataModelListToRoomDomainListWhenFactoryNull() {
		List<RoomDataModel> roomDataModelList = Arrays.asList(roomDataModelDouble);

		try (MockedConstruction<HouseID> houseIDMockedConstruction = mockConstruction(HouseID.class, (mock, context) -> {
			when(roomDataModelDouble.getHouseID()).thenReturn(houseID);

		})) {
			try (MockedConstruction<RoomID> roomIDMockedConstruction = mockConstruction(RoomID.class, (mock, context) -> {
				when(roomDataModelDouble.getRoomID()).thenReturn(roomName);

			})) {
				try (MockedConstruction<RoomFloor> roomFloorMockedConstruction = mockConstruction(RoomFloor.class, (mock, context) -> {
					when(roomDataModelDouble.getFloor()).thenReturn(floorNumber);

				})) {
					try (MockedConstruction<RoomDimensions> roomDimensionsMockedConstruction = mockConstruction(RoomDimensions.class, (mock, context) -> {
						when(roomDataModelDouble.getLength()).thenReturn(length);
						when(roomDataModelDouble.getWidth()).thenReturn(width);
						when(roomDataModelDouble.getHeight()).thenReturn(height);

					})) {

						List<Room> expected = Collections.singletonList(roomDouble);

						Iterable<Room> result = mapper.toDomainList(null, roomDataModelList);

						assertNull(result);
					}
				}
			}
		}

	}

}