package smarthome.service.internaldto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object (DTO) representing the sunrise/sunset measurement.
 * Extends the {@link RepresentationModel} to include HATEOAS links.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SunriseSunsetDTO extends RepresentationModel<SunriseSunsetDTO> {

    /**
     * The sunrise/Sunset measurement value.
     */
    @JsonProperty("measurement")
    public double measurement;

    /**
     * The unit of the time measurement (e.g., hour).
     */
    @JsonProperty("unit")
    public String unit;

    /**
     * Sunrise/Sunset identification.
     */
    @JsonProperty("info")
    public String info;

    /**
     * Default constructor
     */
    public SunriseSunsetDTO() {
    }

    /**
     * Constructs a new {@link SunriseSunsetDTO} with the specified measurement, unit, and additional info.
     *
     * @param measurement The sunrise/Sunset measurement value.
     * @param unit        The unit of the time measurement.
     * @param info        Sunrise/Sunset identification.
     */
    public SunriseSunsetDTO(double measurement, String unit, String info) {
        this.measurement = measurement;
        this.unit = unit;
        this.info = info;
    }
}
