package smarthome.domain.sensorfunctionality;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.valueobjects.SensorFunctionalityID;

/**
 * Represents a {@code SensorFunctionality} class within the smart home system.
 * Acts as the root of its Aggregate.
 */
public class SensorFunctionality implements AggregateRoot<SensorFunctionalityID> {

    private final SensorFunctionalityID id;

    /**
     * Creates a new SensorFunctionality object with the specified ID.
     *
     * @param sensorFunctionalityID the ID representing the sensor functionality
     */

    protected SensorFunctionality(SensorFunctionalityID sensorFunctionalityID) {
        if (sensorFunctionalityID == null) {
            throw new IllegalArgumentException("Invalid SensorFunctionalityID");
        } else {
            this.id = sensorFunctionalityID;
        }
    }

    /**
     * Returns the identity of the sensor functionality.
     *
     * @return the {@code SensorFunctionalityID} representing the identity of the sensor functionality
     */

    @Override
    public SensorFunctionalityID identity() {
        return id;
    }

    /**
     * Checks if the specified object is the same as this SensorFunctionality.
     *
     * @param object the object to compare with
     * @return {@code true} if the specified object is the same as this SensorFunctionality,
     * {@code false} otherwise
     */

    @Override
    public boolean isSameAs(Object object) {
        if (object instanceof SensorFunctionality) {
            SensorFunctionality objectSensorFunct = (SensorFunctionality) object;

            return this.id.equals(objectSensorFunct.identity());

        }
        return false;
    }

}
