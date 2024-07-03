package smarthome.service.internaldto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object (DTO) representing the instantaneous wind speed and direction measurement.
 * Extends the {@link RepresentationModel} to include HATEOAS links.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InstWindSpeedAndDirectionDTO extends RepresentationModel<InstWindSpeedAndDirectionDTO> {

    /**
     * The wind speed measurement value.
     */
    @JsonProperty("measurement")
    public double measurement;

    /**
     * The unit of the wind speed measurement (e.g., km/h).
     */
    @JsonProperty("unit")
    public String unit;

    /**
     * Additional information about the wind speed and direction measurement.
     */
    @JsonProperty("info")
    public String info;

    /**
     * Default constructor
     */
    public InstWindSpeedAndDirectionDTO() {
    }

    /**
     * Constructs a new {@link InstWindSpeedAndDirectionDTO} with the specified measurement, unit, and additional info.
     *
     * @param measurement The wind speed measurement value.
     * @param unit        The unit of the wind speed measurement.
     * @param info        Additional information about the wind speed and direction measurement.
     */
    public InstWindSpeedAndDirectionDTO(double measurement, String unit, String info) {
        this.measurement = measurement;
        this.unit = unit;
        this.info = info;
    }
}
