package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.BeforeEach;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.FactoryActuator;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class MapperActuatorDataModelTest {

    /**
     * Attribute to be used in the tests.
     */
    MapperActuatorDataModel mapperActuatorDataModel;

    /**
     * SetUp necessary for each test
     */
    @BeforeEach
    void setUp() {
        mapperActuatorDataModel = new MapperActuatorDataModel();
    }

    /**
     * Test if the conversion from ActuatorDataModel using Decimal to Actuator is successful
     */
    @Test
    void successfulConversionToDomainDecimal() {
        String actuatorID = "actuatorID";
        String deviceID = "deviceID";

        // Set up needed objects for mock
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);
        Actuator actuatorDouble = mock(Actuator.class);

        // Set up gets for actuatorDataModel
        when(actuatorDataModel.getActuatorID()).thenReturn(actuatorID);
        when(actuatorDataModel.getUpperLimitDecimal()).thenReturn(10.0);
        when(actuatorDataModel.getLowerLimitDecimal()).thenReturn(5.0);
        when(actuatorDataModel.getPrecision()).thenReturn(2);
        when(actuatorDataModel.getDeviceID()).thenReturn(deviceID);

        // Mock ActuatorFunctionalityID construction
        try (MockedConstruction<ActuatorFunctionalityID> actuatorFunctionalityIDMockedConstruction =
                     mockConstruction(ActuatorFunctionalityID.class, (mock, context) -> {
                     })) {
            // Mock ActuatorProperties construction
            try (MockedConstruction<ActuatorProperties> actuatorPropertiesMockedConstruction =
                         mockConstruction(ActuatorProperties.class, (mock2, context2) -> {
                         })) {
                // Mock DeviceID construction
                try (MockedConstruction<DeviceID> deviceIDMockedConstruction =
                             mockConstruction(DeviceID.class, (mock1, context1) -> {
                             })) {
                    // Mock FactoryActuator behavior
                    when(factoryActuator.createActuator(any(), any(), any(), any(), any())).thenReturn(actuatorDouble);
                    Actuator actuator = mapperActuatorDataModel.toDomain(factoryActuator, actuatorDataModel);
                    assertNotNull(actuator);
                    assertEquals(actuatorDouble, actuator);
                }
            }
        }
    }


    /**
     * Test if the conversion from ActuatorDataModel using Int to Actuator is successful
     */
    @Test
    void successfulConversionToDomainInt() {
        String actuatorID = "actuatorID";
        String deviceID = "deviceID";

        // Set up needed objects for mock
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);
        Actuator actuatorDouble = mock(Actuator.class);

        // Set up gets for actuatorDataModel
        when(actuatorDataModel.getActuatorID()).thenReturn(actuatorID);
        when(actuatorDataModel.getUpperLimitInt()).thenReturn(10);
        when(actuatorDataModel.getLowerLimitInt()).thenReturn(5);
        when(actuatorDataModel.getDeviceID()).thenReturn(deviceID);

        // Mock ActuatorFunctionalityID construction
        try (MockedConstruction<ActuatorFunctionalityID> actuatorFunctionalityIDMockedConstruction =
                     mockConstruction(ActuatorFunctionalityID.class, (mock, context) -> {
                     })) {
            // Mock ActuatorProperties construction
            try (MockedConstruction<ActuatorProperties> actuatorPropertiesMockedConstruction =
                         mockConstruction(ActuatorProperties.class, (mock2, context2) -> {
                         })) {
                // Mock DeviceID construction
                try (MockedConstruction<DeviceID> deviceIDMockedConstruction =
                             mockConstruction(DeviceID.class, (mock1, context1) -> {
                             })) {
                    // Mock ActuatorID construction
                    try (MockedConstruction<ActuatorID> actuatorIDMockedConstruction =
                                 mockConstruction(ActuatorID.class, (mock1, context1) -> {
                                 })) {
                        // Mock FactoryActuator behavior
                        when(factoryActuator.createActuator(any(), any(), any(), any(), any())).thenReturn(actuatorDouble);
                        Actuator actuator = mapperActuatorDataModel.toDomain(factoryActuator, actuatorDataModel);
                        assertNotNull(actuator);
                        assertEquals(actuatorDouble, actuator);
                    }
                }
            }
        }
    }

    /**
     * Test if the conversion from ActuatorDataModel to Actuator is unsuccessful if the Actuator is null after its creation
     */
    @Test
    void unsuccessfulConversionToDomain() {
        String actuatorID = "actuatorID";
        String deviceID = "deviceID";

        // Set up needed objects for mock
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);

        // Set up gets for actuatorDataModel
        when(actuatorDataModel.getActuatorID()).thenReturn(actuatorID);
        when(actuatorDataModel.getUpperLimitInt()).thenReturn(10);
        when(actuatorDataModel.getLowerLimitInt()).thenReturn(5);
        when(actuatorDataModel.getDeviceID()).thenReturn(deviceID);

        // Mock ActuatorFunctionalityID construction
        try (MockedConstruction<ActuatorFunctionalityID> actuatorFunctionalityIDMockedConstruction =
                     mockConstruction(ActuatorFunctionalityID.class, (mock, context) -> {
                     })) {
            // Mock ActuatorProperties construction
            try (MockedConstruction<ActuatorProperties> actuatorPropertiesMockedConstruction =
                         mockConstruction(ActuatorProperties.class, (mock2, context2) -> {
                         })) {
                // Mock DeviceID construction
                try (MockedConstruction<DeviceID> deviceIDMockedConstruction =
                             mockConstruction(DeviceID.class, (mock1, context1) -> {
                             })) {
                    // Mock ActuatorID construction
                    try (MockedConstruction<ActuatorID> actuatorIDMockedConstruction =
                                 mockConstruction(ActuatorID.class, (mock1, context1) -> {
                                 })) {
                        // Mock FactoryActuator behavior
                        when(factoryActuator.createActuator(any(), any(), any(), any(), any())).thenReturn(null);
                        Actuator actuator = mapperActuatorDataModel.toDomain(factoryActuator, actuatorDataModel);
                        assertNull(actuator);
                    }
                }
            }
        }
    }

    /**
     * Test if the conversion from ActuatorDataModel to Actuator is unsuccessful if the ActuatorDataModel is null
     */
    @Test
    void unsuccessfulConversionToDomainNullActuatorDataModel() {
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        Actuator actuator = mapperActuatorDataModel.toDomain(factoryActuator, null);
        assertNull(actuator);
    }


    @Test
    void successfulCreateActuatorPropertiesDouble() {
        // Set up needed objects for mock
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);

        // Set up gets for actuatorDataModel
        when(actuatorDataModel.getUpperLimitDecimal()).thenReturn(11.3);
        when(actuatorDataModel.getLowerLimitDecimal()).thenReturn(5.3);
        when(actuatorDataModel.getPrecision()).thenReturn(1);
        when(actuatorDataModel.getUpperLimitInt()).thenReturn(null);
        when(actuatorDataModel.getLowerLimitInt()).thenReturn(null);

        ActuatorProperties actuatorProperties = mapperActuatorDataModel.createActuatorProperties(actuatorDataModel);
        assertNotNull(actuatorProperties);
    }

    @Test
    void unsuccessfulCreateActuatorPropertiesDouble() {

        // Set up needed objects for mock
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);

        // Set up gets for actuatorDataModel
        when(actuatorDataModel.getUpperLimitDecimal()).thenReturn(Double.NaN);
        when(actuatorDataModel.getLowerLimitDecimal()).thenReturn(5.3);
        when(actuatorDataModel.getPrecision()).thenReturn(1);

        ActuatorProperties actuatorProperties = mapperActuatorDataModel.createActuatorProperties(actuatorDataModel);
        assertNull(actuatorProperties);
    }

    @Test
    void unsuccessfulCreateActuatorPropertiesInt() {

        // Set up needed objects for mock
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);

        // Set up gets for actuatorDataModel
        when(actuatorDataModel.getUpperLimitInt()).thenReturn(5);
        when(actuatorDataModel.getLowerLimitInt()).thenReturn(10);

        ActuatorProperties actuatorProperties = mapperActuatorDataModel.createActuatorProperties(actuatorDataModel);
        assertNull(actuatorProperties);
    }


    @Test
    void successfulCreateActuatorPropertiesInt() {

        // Set up needed objects for mock
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);

        // Set up gets for actuatorDataModel
        when(actuatorDataModel.getUpperLimitInt()).thenReturn(10);
        when(actuatorDataModel.getLowerLimitInt()).thenReturn(5);
        when(actuatorDataModel.getUpperLimitDecimal()).thenReturn(null);
        when(actuatorDataModel.getLowerLimitDecimal()).thenReturn(null);
        when(actuatorDataModel.getPrecision()).thenReturn(null);

        ActuatorProperties actuatorProperties = mapperActuatorDataModel.createActuatorProperties(actuatorDataModel);
        assertNotNull(actuatorProperties);
    }


    /**
     * Test successful conversion of a list of ActuatorDataModel to a list of Actuator objects
     */
    @Test
    void successfulConversionListToDomain() {
        String actuatorID = "actuatorID";
        String deviceID = "deviceID";

        // Set up needed objects for mock
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);
        Actuator actuatorDouble = mock(Actuator.class);

        // Set up gets for actuatorDataModel
        when(actuatorDataModel.getActuatorID()).thenReturn(actuatorID);
        when(actuatorDataModel.getUpperLimitInt()).thenReturn(10);
        when(actuatorDataModel.getLowerLimitInt()).thenReturn(5);
        when(actuatorDataModel.getDeviceID()).thenReturn(deviceID);

        // Mock ActuatorFunctionalityID construction
        try (MockedConstruction<ActuatorFunctionalityID> actuatorFunctionalityIDMockedConstruction =
                     mockConstruction(ActuatorFunctionalityID.class, (mock, context) -> {
                     })) {
            // Mock ActuatorProperties construction
            try (MockedConstruction<ActuatorProperties> actuatorPropertiesMockedConstruction =
                         mockConstruction(ActuatorProperties.class, (mock2, context2) -> {
                         })) {
                // Mock DeviceID construction
                try (MockedConstruction<DeviceID> deviceIDMockedConstruction =
                             mockConstruction(DeviceID.class, (mock1, context1) -> {
                             })) {
                    // Mock ActuatorID construction
                    try (MockedConstruction<ActuatorID> actuatorIDMockedConstruction =
                                 mockConstruction(ActuatorID.class, (mock1, context1) -> {
                                 })) {
                        // Mock FactoryActuator behavior
                        when(factoryActuator.createActuator(any(), any(), any(), any(), any())).thenReturn(actuatorDouble);

                        //Add actuatorDataModel to list
                        ArrayList<ActuatorDataModel> actuatorDataModels = new ArrayList<>();
                        actuatorDataModels.add(actuatorDataModel);

                        //Call the method to test
                        Iterable<Actuator> actuators = mapperActuatorDataModel.toDomainList(factoryActuator, actuatorDataModels);

                        assertNotNull(actuators);
                        assertEquals(1, ((ArrayList<Actuator>) actuators).size());
                        assertEquals(actuatorDouble, ((ArrayList<Actuator>) actuators).get(0));
                    }
                }
            }
        }
    }

    /**
     * Test successful conversion of a list of ActuatorDataModel to a list of Actuator objects with two actuators in it
     */
    @Test
    void successfulConversionListToDomainTwoActuators() {
        String actuatorID = "actuatorID";
        String deviceID = "deviceID";

        // Set up needed objects for mock
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);
        Actuator actuatorDouble = mock(Actuator.class);
        Actuator actuatorDouble2 = mock(Actuator.class);

        // Set up gets for actuatorDataModel
        when(actuatorDataModel.getActuatorID()).thenReturn(actuatorID);
        when(actuatorDataModel.getUpperLimitInt()).thenReturn(10);
        when(actuatorDataModel.getLowerLimitInt()).thenReturn(5);
        when(actuatorDataModel.getDeviceID()).thenReturn(deviceID);

        // Mock ActuatorFunctionalityID construction
        try (MockedConstruction<ActuatorFunctionalityID> actuatorFunctionalityIDMockedConstruction =
                     mockConstruction(ActuatorFunctionalityID.class, (mock, context) -> {
                     })) {
            // Mock ActuatorProperties construction
            try (MockedConstruction<ActuatorProperties> actuatorPropertiesMockedConstruction =
                         mockConstruction(ActuatorProperties.class, (mock2, context2) -> {
                         })) {
                // Mock DeviceID construction
                try (MockedConstruction<DeviceID> deviceIDMockedConstruction =
                             mockConstruction(DeviceID.class, (mock1, context1) -> {
                             })) {
                    // Mock ActuatorID construction
                    try (MockedConstruction<ActuatorID> actuatorIDMockedConstruction =
                                 mockConstruction(ActuatorID.class, (mock1, context1) -> {
                                 })) {
                        // Mock FactoryActuator behavior
                        when(factoryActuator.createActuator(any(), any(), any(), any(), any())).thenReturn(actuatorDouble).thenReturn(actuatorDouble2);

                        //Add actuatorDataModel to list
                        ArrayList<ActuatorDataModel> actuatorDataModels = new ArrayList<>();
                        actuatorDataModels.add(actuatorDataModel);
                        actuatorDataModels.add(actuatorDataModel);

                        //Call the method to test
                        Iterable<Actuator> actuators = mapperActuatorDataModel.toDomainList(factoryActuator, actuatorDataModels);

                        assertNotNull(actuators);
                        assertEquals(2, ((ArrayList<Actuator>) actuators).size());
                        assertEquals(actuatorDouble, ((ArrayList<Actuator>) actuators).get(0));
                        assertEquals(actuatorDouble2, ((ArrayList<Actuator>) actuators).get(1));
                    }
                }
            }
        }
    }

    /**
     * Test successful conversion of a list of ActuatorDataModel to a list of Actuator objects with no actuators in it
     */
    @Test
    void successfulConversionListToDomainEmptyList() {
        // Set up needed objects for mock
        FactoryActuator factoryActuator = mock(FactoryActuator.class);

        //Add actuatorDataModel to list
        ArrayList<ActuatorDataModel> actuatorDataModels = new ArrayList<>();

        //Call the method to test
        Iterable<Actuator> actuators = mapperActuatorDataModel.toDomainList(factoryActuator, actuatorDataModels);

        assertNotNull(actuators);
        assertEquals(0, ((ArrayList<Actuator>) actuators).size());
    }

    /**
     * Test if the conversion from ActuatorDataModelList to ActuatorList is unsuccessful if the ActuatorDataModel is null
     */
    @Test
    void unsuccessfulConversionListToDomainNullList() {
        FactoryActuator factoryActuator = mock(FactoryActuator.class);
        Iterable<Actuator> actuators = mapperActuatorDataModel.toDomainList(factoryActuator, null);
        assertNull(actuators);
    }
}