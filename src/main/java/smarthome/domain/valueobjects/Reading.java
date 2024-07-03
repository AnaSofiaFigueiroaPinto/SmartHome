package smarthome.domain.valueobjects;

import smarthome.ddd.ValueObject;
import smarthome.util.ReadingUtil;

public class Reading implements ValueObject {

    private final String measurement;
    private final String unit;

    /**
     * Constructor for Reading objects. Value attribute is set as a String so it works for numeric/non-numeric values.
     * Unit is defined within config.properties file and depends on the sensorFunctionality of the Value type Object.
     * @param measurement String representing the read value. Can be numeric or non-numeric.
     * @param unit String representing the unit of the Sensor that the Value is associated with.
     */
    public Reading(String measurement, String unit) {
        if (!validConstructorArguments(measurement, unit)) {
            throw new IllegalArgumentException("Invalid value or unit");
        }
        this.measurement = measurement;
        this.unit = unit;
    }

    /**
     * Checks if the constructor arguments are valid.
     * @param value value of the Reading object.
     * @param unit unit of the Reading object.
     * @return True if all arguments are not null, false otherwise.
     */
    private boolean validConstructorArguments(String value, String unit) {
        return value != null && !value.isEmpty() && !value.isBlank() && unit != null && !unit.isEmpty() && !unit.isBlank();
    }

    /**
     * Retrieves the measurement of the Reading object.
     * @return String representing the value of the Reading object.
     */
    public String getMeasurement() {
        return measurement;
    }

    /**
     * Retrieves the unit of the Reading object.
     * @return String representing the unit of the Reading object.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Uses the method getReadingAsSingleString to return all values with respective units of a Reading object.
     * If there are multiple values or units separated by ";" method will separate them with "and".
     * @return a String representing all values with respective units.
     */
    public String getAllValuesWithUnits() {
        return ReadingUtil.getReadingAsSingleString(this);
    }
}
