package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * This class is responsible for mapping Device objects to DeviceDTO objects.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BlindRollerDTO extends RepresentationModel<BlindRollerDTO> {

        /**
         * The name and identifier of the device.
         */
        public String deviceName;

        /**
         * The name of the room the device is in.
         */
        public String roomName;

        /**
         * Empty constructor of the BlindRollerDTO class.
         */
        public BlindRollerDTO() {
        }

        /**
         * Constructor of the BlindRollerDTO class.
         * @param deviceName the name of the device.
         * @param roomName the name of the room.
         */
        public BlindRollerDTO(@JsonProperty("deviceName") String deviceName,
                              @JsonProperty("roomName") String roomName)
        {
            this.deviceName = deviceName;
            this.roomName = roomName;
        }


        /**
         * Overriding the equals method to compare two BlindRollerDTO objects.
         * @param obj the object to compare.
         * @return true if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            if (!super.equals(obj)) return false;
            BlindRollerDTO that = (BlindRollerDTO) obj;
            return Objects.equals(deviceName, that.deviceName) && Objects.equals(roomName, that.roomName);

        }

        /**
         * Overriding the hashCode method to return the hash code of a BlindRollerDTO object.
         * @return a hash code value for this object.
         */
        @Override
        public int hashCode()
        {
            return Objects.hash(super.hashCode(), deviceName, roomName);
        }

    }

