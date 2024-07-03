package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.*;
import smarthome.domain.room.Room;

@Entity
@Table(name = "ROOM")
public class RoomDataModel {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)

    private String roomID;
    private String houseID;
    private int roomFloor;
    private double height;
    private double width;
    private double length;

    public RoomDataModel() {
    }

    /**
     * Constructor of Room Data Model Object.
     *
     * @param room Room object to convert to Room Data Model.
     */
    public RoomDataModel(Room room) {
        roomID = room.identity().toString();
        roomFloor = room.getRoomFloor().getRoomFloor();
        length = room.getRoomDimensions().getLength();
        width = room.getRoomDimensions().getWidth();
        height = room.getRoomDimensions().getHeight();
        houseID = room.getHouseID().toString();
    }


    /**
        * Method that returns the roomID of a room.
        *
        * @return roomID.
        */

    public String getRoomID() {
        return roomID;
    }

    /**
     * Method that returns the floor of a room.
     *
     * @return room floor.
     */

    public int getFloor() {
        return roomFloor;
    }

    /**
     * Method that returns the height of a room.
     *
     * @return height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Method that returns the length of a room.
     *
     * @return length.
     */
    public double getLength() {
        return length;
    }

    /**
     * Method that returns the width of a room.
     *
     * @return width.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Method that returns the houseID of a room.
     *
     * @return houseID.
     */
    public String getHouseID() {
        return houseID;
    }

    /**
     * Method that updates the Room Data Model object from a Room object.
     *
     * @param room
     * @return true if the Room Data Model object was updated, false otherwise.
     */

    public boolean updateFromDomain(Room room) {
        if (room == null) {
            return false;
        } else {
            this.roomID = room.identity().toString();
            this.height = room.getRoomDimensions().getHeight();
            this.width = room.getRoomDimensions().getWidth();
            this.length = room.getRoomDimensions().getLength();
            this.roomFloor = room.getRoomFloor().getRoomFloor();
            this.houseID = room.getHouseID().toString();
            return true;
        }
    }

}