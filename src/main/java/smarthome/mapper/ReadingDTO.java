package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * Data transfer object for the Reading class.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReadingDTO extends RepresentationModel<ReadingDTO>
{

    /**
     * The value of the reading with the unit.
     */
    public final String valueWithUnit;

    /**
     * Method to compare two ReadingDTO objects.
     * @param o The object to compare.
     * @return True if the objects are equal, false if not.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReadingDTO that = (ReadingDTO) o;
        return Objects.equals(valueWithUnit, that.valueWithUnit);
    }

    /**
     * Method to generate the hash code of a ReadingDTO object.
     * @return The hash code of the ReadingDTO object.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), valueWithUnit);
    }

    /**
     * Constructor for the ReadingDTO class.
     * @param valueWithUnit The Reading object to be converted.
     */
    public ReadingDTO(String valueWithUnit)
    {
        this.valueWithUnit = valueWithUnit;
    }
}
