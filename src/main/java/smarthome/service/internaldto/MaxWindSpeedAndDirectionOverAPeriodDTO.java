package smarthome.service.internaldto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object (DTO) representing the maximum wind speed and direction measurement over a specified period.
 * Extends the {@link RepresentationModel} to include HATEOAS links.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MaxWindSpeedAndDirectionOverAPeriodDTO extends RepresentationModel<MaxWindSpeedAndDirectionOverAPeriodDTO> {

    /**
     * The maximum wind speed measurement value over the specified period.
     */
    @JsonProperty("measurement")
    public double measurement;

    /**
     * The unit of the wind speed measurement (e.g., km/h).
     */
    @JsonProperty("unit")
    public String unit;

    /**
     * Information about the wind direction measurement.
     */
    @JsonProperty("info")
    public String info;

    /**
     * Default constructor
     */
    public MaxWindSpeedAndDirectionOverAPeriodDTO() {
    }

    /**
     * Constructs a new {@link MaxWindSpeedAndDirectionOverAPeriodDTO} with the specified measurement, unit, and additional info.
     *
     * @param measurement The maximum wind speed measurement value.
     * @param unit        The unit of the wind speed measurement.
     * @param info        Additional information about the wind speed and direction measurement.
     */
    public MaxWindSpeedAndDirectionOverAPeriodDTO(double measurement, String unit, String info) {
        this.measurement = measurement;
        this.unit = unit;
        this.info = info;
    }
}
