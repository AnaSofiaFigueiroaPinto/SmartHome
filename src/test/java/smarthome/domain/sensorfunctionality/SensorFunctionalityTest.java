package smarthome.domain.sensorfunctionality;

import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.SensorFunctionalityID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorFunctionalityTest {

    /**
     * Test to ensure that creating a SensorFunctionality object with a null SensorFunctionalityID
     * throws an IllegalArgumentException.
     */

    @Test
    void failSensorFunctionalityNull() {
        assertThrows(IllegalArgumentException.class, () -> new SensorFunctionality(null));
    }

    /**
     * Test to verify that the identity of a SensorFunctionality object matches the expected SensorFunctionalityID.
     */

    @Test
    void successIdentity() {
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        when(sensorFunctionalityIDDouble.toString()).thenReturn("Temperature");
        SensorFunctionality sensorFunctionality = new SensorFunctionality(sensorFunctionalityIDDouble);
        assertEquals(sensorFunctionalityIDDouble, sensorFunctionality.identity());
    }

    /**
     * Test to verify if two SensorFunctionality objects with the same SensorFunctionalityID are considered equal.
     */

    @Test
    void verifyIfSensorFunctionalityIsSameAsObject() {
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        when(sensorFunctionalityIDDouble.toString()).thenReturn("Temperature");
        SensorFunctionality sensorFunctionality1 = new SensorFunctionality(sensorFunctionalityIDDouble);
        SensorFunctionality sensorFunctionality2 = new SensorFunctionality(sensorFunctionalityIDDouble);
        assertTrue(sensorFunctionality1.isSameAs(sensorFunctionality2));
    }

    /**
     * Test to verify if two SensorFunctionality objects with different SensorFunctionalityIDs are considered not equal.
     */

    @Test
    void verifyIfSensorFunctionalityIsNotSameAsObject() {
        SensorFunctionalityID sensorFunctionalityIDDouble1 = mock(SensorFunctionalityID.class);
        when(sensorFunctionalityIDDouble1.toString()).thenReturn("Temperature");
        SensorFunctionalityID sensorFunctionalityIDDouble2 = mock(SensorFunctionalityID.class);
        when(sensorFunctionalityIDDouble1.toString()).thenReturn("Humidity");

        SensorFunctionality sensorFunctionality1 = new SensorFunctionality(sensorFunctionalityIDDouble1);
        SensorFunctionality sensorFunctionality2 = new SensorFunctionality(sensorFunctionalityIDDouble2);
        assertFalse(sensorFunctionality1.isSameAs(sensorFunctionality2));
    }


    /**
     * Fails to check if SensorFunctionality instance is the same as Object instance due to Object not being an SensorFunctionality
     */
    @Test
    void failCheckIfSensorFunctionalityIsSameAs() {
        SensorFunctionalityID sensorFunctionalityID2 = mock(SensorFunctionalityID.class);
        SensorFunctionality sensorFunctionality2 = new SensorFunctionality(sensorFunctionalityID2);
        Object object = new Object();
        assertFalse(sensorFunctionality2.isSameAs(object));
    }

}






