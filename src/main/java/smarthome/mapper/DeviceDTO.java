package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * This class is responsible for mapping Device objects to DeviceDTO objects.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeviceDTO extends RepresentationModel<DeviceDTO>
{

    /**
     * The name and identifier of the device.
     */
    public String deviceName;

    /**
     * The model of the device.
     */
    public String model;

    /**
     * The status of the device.
     */
    public String status;

    /**
     * The name of the room the device is in.
     */
    public String roomName;

    public DeviceDTO() {
    }

    /**
     * Constructor for DeviceDTO object using attributes.
     * @param deviceName    The name of the device (device ID).
     * @param model         The model of the device.
     * @param status  The status of the device.
     * @param roomName        The roomID of the room where the device is located.
     */
    public DeviceDTO(@JsonProperty("deviceName") String deviceName,
                     @JsonProperty("model") String model,
                     @JsonProperty("status") String status,
                     @JsonProperty("roomName") String roomName)
    {
        this.deviceName = deviceName;
        this.model = model;
        this.status = status;
        this.roomName = roomName;
    }

    /**
     * Constructor for DeviceDTO object using only the device name (device ID).
     * @param deviceName The name of the device.
     */
    public DeviceDTO(String deviceName)
    {
        this.deviceName = deviceName;
    }

    /**
     * Overriding the equals method to compare two DeviceDTO objects.
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        DeviceDTO deviceDTO = (DeviceDTO) obj;
        return Objects.equals(deviceName, deviceDTO.deviceName) && Objects.equals(model, deviceDTO.model)
                && Objects.equals(status, deviceDTO.status) && Objects.equals(roomName, deviceDTO.roomName);
    }

    /**
     * Overriding the hashCode method to return the hash code of a DeviceDTO object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), deviceName, model, status, roomName);
    }
}
