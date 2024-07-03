package smarthome.persistence.jpa.datamodel;

import org.springframework.stereotype.Component;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperRoomDataModel {

	/**
	 * Method that receives a Room Data Model and creates a domain Room object with the Data Model information.
	 *
	 * @param factoryRoom   factory of domain Room objects.
	 * @param roomDataModel Room Data Model object to convert.
	 * @return The Room object created from the RoomDataModel, or null if any required attribute is null.
	 */

	public Room toDomain(FactoryRoom factoryRoom, RoomDataModel roomDataModel) {
		try {
			RoomID roomID = new RoomID(roomDataModel.getRoomID());
			RoomFloor roomFloor = new RoomFloor(roomDataModel.getFloor());
			RoomDimensions roomDimensions = new RoomDimensions(
					roomDataModel.getLength(),
					roomDataModel.getWidth(),
					roomDataModel.getHeight());
			HouseID houseID = new HouseID(roomDataModel.getHouseID());
			return factoryRoom.createRoom(roomID, roomFloor, roomDimensions, houseID);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Converts an Iterable of RoomDataModel objects to an Iterable of Room objects.
	 *
	 * @param factoryRoom   The factory to create Room objects.
	 * @param listDataModel The Iterable of RoomDataModel objects to convert.
	 * @return An Iterable containing Room objects created from the RoomDataModel objects,
	 * or null if any required attribute is null.
	 */
	public Iterable<Room> toDomainList(FactoryRoom factoryRoom, Iterable<RoomDataModel> listDataModel) {
		if (factoryRoom == null || listDataModel == null) {
			return null;
		}

		List<Room> listRooms = new ArrayList<>();
		listDataModel.forEach(roomDataModel -> {
			Room roomDomain = toDomain(factoryRoom, roomDataModel);
			if (roomDomain != null) {
				listRooms.add(roomDomain);
			}
		});
		return listRooms;

	}
}
