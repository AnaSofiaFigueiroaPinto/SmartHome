package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * The RoomDTO class represents a Data Transfer Object for Room domain objects.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoomDTO extends RepresentationModel<RoomDTO>
{

    /**
     * The name and identifier of the room.
     */
    public String roomName;

    /**
     * The house floor on which the room is located.
     */
    public int floorNumber;

    /**
     * The length of the room.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double roomLength;

    /**
     * The width of the room.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double roomWidth;

    /**
     * The height of the room.
     */
    public double roomHeight;

    /**
     * The UUID of the house where the room is located.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String houseUUID;

    /**
     * Empty constructor required by Jackson for deserialization.
     */
    public RoomDTO() {
    }

    /**
     * Overriding the equals method to compare two RoomDTO objects.
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return floorNumber == roomDTO.floorNumber && Double.compare(roomLength, roomDTO.roomLength) == 0 && Double.compare(roomWidth, roomDTO.roomWidth) == 0 && Double.compare(roomHeight, roomDTO.roomHeight) == 0 && Objects.equals(roomName, roomDTO.roomName);
    }

    /**
     * Overriding the hashCode method to generate a hash code for a RoomDTO object.
     * @return the hash code of the RoomDTO object.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), roomName, floorNumber, roomLength, roomWidth, roomHeight);
    }

    /**
     * Constructs a new RoomDTO with the specified parameters.
     * @param roomName    The name of the room.
     * @param floorNumber The house floor on which the room is located.
     * @param roomLength  The length of the room.
     * @param roomWidth   The width of the room.
     * @param roomHeight  The height of the room.
     */
    public RoomDTO(@JsonProperty("roomName") String roomName,
                   @JsonProperty("floorNumber") int floorNumber,
                   @JsonProperty("roomLength") double roomLength,
                   @JsonProperty("roomWidth") double roomWidth,
                   @JsonProperty("roomHeight") double roomHeight)
    {
        this.roomName = roomName;
        this.floorNumber = floorNumber;
        this.roomLength = roomLength;
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
    }

    /**
     * Constructs a new RoomDTO with the specified parameters.
     * @param roomName    The name of the room.
     * @param floorNumber The house floor on which the room is located.
     * @param roomLength  The length of the room.
     * @param roomWidth   The width of the room.
     * @param roomHeight  The height of the room.
     * @param houseUUID   The UUID of the house where the room is located.
     */
    @JsonCreator
    public RoomDTO(@JsonProperty("roomName") String roomName,
                   @JsonProperty("floorNumber") int floorNumber,
                   @JsonProperty("roomLength") double roomLength,
                   @JsonProperty("roomWidth") double roomWidth,
                   @JsonProperty("roomHeight") double roomHeight,
                   @JsonProperty("houseUUID") String houseUUID)
    {
        this.roomName = roomName;
        this.floorNumber = floorNumber;
        this.roomLength = roomLength;
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        this.houseUUID = houseUUID;
    }

    public RoomDTO(String roomName)
    {
        this.roomName = roomName;
    }
}

