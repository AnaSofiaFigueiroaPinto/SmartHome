package smarthome.service.internaldto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object (DTO) representing the instantaneous temperature measurement.
 * Extends the {@link RepresentationModel} to include HATEOAS links.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InstTemperatureDTO extends RepresentationModel<InstTemperatureDTO> {

    /**
     * The temperature measurement value.
     */
    @JsonProperty("measurement")
    public double measurement;

    /**
     * The unit of the temperature measurement (e.g., Celsius).
     */
    @JsonProperty("unit")
    public String unit;

    /**
     * Additional information about the temperature measurement.
     */
    @JsonProperty("info")
    public String info;

    /**
     * Default constructor
     */
    public InstTemperatureDTO() {
    }

    /**
     * Constructs a new {@link InstTemperatureDTO} with the specified measurement, unit, and additional info.
     *
     * @param measurement The temperature measurement value.
     * @param unit        The unit of the temperature measurement.
     * @param info        Additional information about the temperature measurement.
     */
    public InstTemperatureDTO(double measurement, String unit, String info) {
        this.measurement = measurement;
        this.unit = unit;
        this.info = info;
    }
}
