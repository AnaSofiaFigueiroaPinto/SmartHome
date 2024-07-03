package smarthome.domain.actuators;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = FactoryActuator.class)
class FactoryActuatorTest {
    @MockBean
    private ActuatorID actuatorIDDouble;
    @MockBean
    private ActuatorProperties actuatorPropertiesDouble;
    @MockBean
    private DeviceID deviceIDDouble;
    @MockBean
    private ActuatorFunctionalityID functionalityIDDouble;
    @InjectMocks
    private FactoryActuator factoryActuator;

    /**
     * Setting up the actuator Catalogue under test
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test creation of an actuator using invalid actuator from catalogue
     */
    @Test
    void failCreateActuatorUsingInvalidFunctionalityID() {
        String actuatorName = "Switch";
        when(actuatorIDDouble.toString()).thenReturn(actuatorName);
        when(actuatorIDDouble.toString()).thenReturn(actuatorName);
        when(functionalityIDDouble.toString()).thenThrow(NullPointerException.class);

        assertNull(factoryActuator.createActuator(actuatorIDDouble,null, actuatorPropertiesDouble, deviceIDDouble, "smarthome.domain.actuators.SwitchActuator"));
    }

    /**
     * Test creation of an actuator using a null string for actuatorName
     */
    @Test
    void failCreateActuatorUsingNullActuatorName() {
        assertNull(factoryActuator.createActuator(null, functionalityIDDouble, actuatorPropertiesDouble, deviceIDDouble, "smarthome.domain.actuators.SwitchActuator"));
    }

    /**
     * Test creation of an actuator using an invalid deviceID
     */
    @Test
    void failCreateActuatorWithNullDeviceID() {
        assertNull(factoryActuator.createActuator(actuatorIDDouble, functionalityIDDouble, actuatorPropertiesDouble, null, "smarthome.domain.actuators.SwitchActuator"));
    }

    /**
     * Test successful creation of Actuator object using valid parameters.
     */
    @Test
    void successCreateActuatorWithValidParameters() {
        when(functionalityIDDouble.toString()).thenReturn("Switch");
        assertNotNull(factoryActuator.createActuator(actuatorIDDouble, functionalityIDDouble, actuatorPropertiesDouble, deviceIDDouble, "smarthome.domain.actuators.SwitchActuator"));
    }

}