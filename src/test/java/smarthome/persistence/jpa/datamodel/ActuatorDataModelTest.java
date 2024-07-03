package smarthome.persistence.jpa.datamodel;

import smarthome.domain.actuatorfunctionality.FactoryActuatorFunctionality;
import smarthome.domain.actuatorfunctionality.ImpFactoryActuatorFunctionality;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.FactoryActuator;
import smarthome.domain.repository.ActuatorFunctionalityRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.persistence.repositoriesmem.ActuatorFunctionalityRepositoryMem;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorDataModelTest {

    private ActuatorDataModel actuatorDataModel;
    private ActuatorDataModel actuatorDataModel1;
    private Actuator actuator;
    private Actuator actuator1;
    private ActuatorID actuatorID;
    private int upperLimitInt;
    private int lowerLimitInt;
    private double upperLimitDecimal;
    private double lowerLimitDecimal;
    private int precision;

    /**
     * Set up for the tests
     */
    @BeforeEach
    void setUp() {
        ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID("IntegerSetter");
        actuatorID = new ActuatorID("IntSetter");
        upperLimitInt = 10;
        lowerLimitInt = 5;
        upperLimitDecimal = 10.0;
        lowerLimitDecimal = 5.0;
        precision = 2;
        DeviceID deviceID = new DeviceID("Thermostat");
        ActuatorProperties actuatorProperties = new ActuatorProperties(upperLimitInt, lowerLimitInt);
        ActuatorProperties actuatorProperties1 = new ActuatorProperties(upperLimitDecimal, lowerLimitDecimal, precision);
        FactoryActuator factoryActuator = new FactoryActuator();
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();
        ActuatorFunctionalityRepository actuatorFunctionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);
        String actuatorClass = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID(actuatorFunctionalityID);
        actuator = factoryActuator.createActuator(actuatorID, actuatorFunctionalityID,actuatorProperties, deviceID, actuatorClass);
        actuator1 = factoryActuator.createActuator(actuatorID, actuatorFunctionalityID, actuatorProperties1, deviceID, actuatorClass);
        actuatorDataModel = new ActuatorDataModel(actuator);
        actuatorDataModel1 = new ActuatorDataModel(actuator1);
    }

    /**
     * Test if the empty constructor of ActuatorDataModel is not null
     */
    @Test
    void actuatorDataModelEmptyConstructorNotNull() {
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel();
        assertNotNull(actuatorDataModel);
    }

    /**
     * Test if the ActuatorFunctionalityID is the same in the ActuatorDataModel and the Actuator
     */
    @Test
    void getActuatorFunctionalityIDFromActuatorDataModel() {
        String result = actuatorDataModel.getActuatorFunctionalityID();
        assertEquals(actuator.getActuatorFunctionalityID().toString(), result);
    }

    /**
     * Test if the ActuatorID is the same in the ActuatorDataModel and the Actuator
     */
    @Test
    void getActuatorIDFromActuatorDataModel() {
        String result = actuatorDataModel.getActuatorID();
        assertEquals(actuator.identity().toString(), result);
    }

    /**
     * Test if the UpperLimitInt is the same in the ActuatorDataModel and the Actuator
     */
    @Test
    void getUpperLimitIntFromActuatorDataModel() {
        int result = actuatorDataModel.getUpperLimitInt();
        assertEquals(upperLimitInt, result);
    }

    /**
     * Test if the LowerLimitInt is the same in the ActuatorDataModel and the Actuator
     */
    @Test
    void getLowerLimitIntFromActuatorDataModel() {
        int result = actuatorDataModel.getLowerLimitInt();
        assertEquals(lowerLimitInt, result);
    }

    /**
     * Test if the UpperLimitDecimal is the same in the ActuatorDataModel and the Actuator
     */
    @Test
    void getUpperLimitDecimalFromActuatorDataModel() {
        double result = actuatorDataModel1.getUpperLimitDecimal();
        assertEquals(upperLimitDecimal, result);
    }

    /**
     * Test if the LowerLimitDecimal is the same in the ActuatorDataModel and the Actuator
     */
    @Test
    void getLowerLimitDecimalFromActuatorDataModel() {
        double result = actuatorDataModel1.getLowerLimitDecimal();
        assertEquals(lowerLimitDecimal, result);
    }

    /**
     * Test if the Precision is the same in the ActuatorDataModel and the Actuator
     */
    @Test
    void getPrecisionFromActuatorDataModel() {
        int result = actuatorDataModel1.getPrecision();
        assertEquals(precision, result);
    }

    /**
     * Test if the DeviceID is the same in the ActuatorDataModel and the Actuator
     */
    @Test
    void getDeviceIDFromActuatorDataModel() {
        String result = actuatorDataModel.getDeviceID();
        assertEquals(actuator.getDeviceName().toString(), result);
    }

    /**
     * Test if the ActuatorDataModel with Int is created correctly
     */
    @Test
    void ActuatorDataModelConstructor() {
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(actuator);
        assertEquals(actuator.getActuatorFunctionalityID().toString(), actuatorDataModel.getActuatorFunctionalityID());
        assertEquals(actuator.identity().toString(), actuatorDataModel.getActuatorID());
        assertEquals(upperLimitInt, actuatorDataModel.getUpperLimitInt());
        assertEquals(lowerLimitInt, actuatorDataModel.getLowerLimitInt());
        assertEquals(actuator.getDeviceName().toString(), actuatorDataModel.getDeviceID());
    }

    /**
     * Test if the ActuatorDataModel with Decimal is created correctly
     */
    @Test
    void ActuatorDataModelConstructor1() {
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(actuator1);
        assertEquals(actuator1.getActuatorFunctionalityID().toString(), actuatorDataModel.getActuatorFunctionalityID());
        assertEquals(actuator1.identity().toString(), actuatorDataModel.getActuatorID());
        assertEquals(10.0, actuatorDataModel.getUpperLimitDecimal());
        assertEquals(5.0, actuatorDataModel.getLowerLimitDecimal());
        assertEquals(2, actuatorDataModel.getPrecision());
        assertEquals(actuator1.getDeviceName().toString(), actuatorDataModel.getDeviceID());
    }

    /**
     * Test if the ActuatorDataModel Decimal is updated correctly
     */
    @Test
    void successfulUpdateFromDomainDecimal() {
        ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID("IntegerSetter");
        actuatorID = new ActuatorID("IntSetter");
        upperLimitDecimal = 20.0;
        lowerLimitDecimal = 10.0;
        precision = 2;
        DeviceID deviceID = new DeviceID("Thermostat");
        ActuatorProperties actuatorProperties1 = new ActuatorProperties(upperLimitDecimal, lowerLimitDecimal, precision);
        FactoryActuator factoryActuator = new FactoryActuator();
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();
        ActuatorFunctionalityRepository actuatorFunctionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);
        String actuatorClass = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID(actuatorFunctionalityID);
        actuator = factoryActuator.createActuator(actuatorID, actuatorFunctionalityID,actuatorProperties1, deviceID, actuatorClass);
        boolean result = actuatorDataModel.updateFromDomain(actuator);
        assertTrue(result);
        assertEquals(20.0, actuatorDataModel.getUpperLimitDecimal());
        assertEquals(10.0, actuatorDataModel.getLowerLimitDecimal());
        assertEquals(2, actuatorDataModel.getPrecision());
        assertEquals(actuator.identity().toString(), actuatorDataModel.getActuatorID());
        assertEquals(actuator.getActuatorFunctionalityID().toString(), actuatorDataModel.getActuatorFunctionalityID());
        assertEquals(actuator.getDeviceName().toString(), actuatorDataModel.getDeviceID());
    }

    /**
     * Test if the ActuatorDataModel Int is updated correctly
     * Return true if the ActuatorDataModel is updated correctly
     */
    @Test
    void successfulUpdateFromDomainInt() {
        ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID("IntegerSetter");
        actuatorID = new ActuatorID("IntSetter");
        upperLimitInt = 20;
        lowerLimitInt = 10;
        DeviceID deviceID = new DeviceID("Thermostat");
        ActuatorProperties actuatorProperties = new ActuatorProperties(upperLimitInt, lowerLimitInt);
        FactoryActuator factoryActuator = new FactoryActuator();
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();
        ActuatorFunctionalityRepository actuatorFunctionalityRepository = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);
        String actuatorClass = actuatorFunctionalityRepository.getClassNameForActuatorFunctionalityID(actuatorFunctionalityID);
        actuator = factoryActuator.createActuator(actuatorID, actuatorFunctionalityID,actuatorProperties, deviceID, actuatorClass);
        boolean result = actuatorDataModel.updateFromDomain(actuator);
        assertTrue(result);
        assertEquals(20, actuatorDataModel.getUpperLimitInt());
        assertEquals(10, actuatorDataModel.getLowerLimitInt());
        assertEquals(actuator.identity().toString(), actuatorDataModel.getActuatorID());
        assertEquals(actuator.getActuatorFunctionalityID().toString(), actuatorDataModel.getActuatorFunctionalityID());
        assertEquals(actuator.getDeviceName().toString(), actuatorDataModel.getDeviceID());
    }

    /**
     * Test if the ActuatorDataModel is not updated correctly since Actuator is null
     * Return false if the ActuatorDataModel is not updated correctly
     */
    @Test
    void unsuccessfulUpdateFromDomain() {
        boolean result = actuatorDataModel.updateFromDomain(null);
        assertFalse(result);
    }
}