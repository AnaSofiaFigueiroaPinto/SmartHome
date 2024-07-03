package smarthome.persistence.repositoriesmem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.DeviceID;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorRepositoryMemTest {
    private ActuatorRepositoryMem actuatorRepositoryMem;
    private Map<ActuatorID, Actuator> DATA;
    private final ActuatorID actuatorID1 = mock(ActuatorID.class);
    private final ActuatorID actuatorID2 = mock(ActuatorID.class);
    private final ActuatorID actuatorID3 = mock(ActuatorID.class);
    private final Actuator actuatorEnt1 = mock(Actuator.class);
    private final Actuator actuatorEnt2 = mock(Actuator.class);
    private final Actuator actuatorEnt3 = mock(Actuator.class);

    /**
     * Set up the ActuatorRepository and the Actuator entities
     */
    @BeforeEach
    void setUP () {
        // Initialize ActuatorRepository with the map
        DATA = new HashMap<>();
        actuatorRepositoryMem = new ActuatorRepositoryMem(DATA);

        DATA = actuatorRepositoryMem.getActuatorData();
        fillMapData();
    }

    void fillMapData() {
        DATA.put(actuatorID1, actuatorEnt1);
        DATA.put(actuatorID2, actuatorEnt2);
    }

    /**
     * Test the successful addition of am Actuator entity to the repository
     */
    @Test
    void successfullyAddEntityToMap () {
        when(actuatorEnt3.identity()).thenReturn(actuatorID3);

       Actuator actuator = actuatorRepositoryMem.save(actuatorEnt3);

        assertEquals(actuatorEnt3, actuator);
    }

    /**
     * Successful retrieval of all entities in the repository
     */
    @Test
    void successRetrieveAllEntitiesInData () {
      actuatorRepositoryMem.findAllEntities();
        assertEquals(actuatorRepositoryMem.findAllEntities(), DATA.values());
    }

    /**
     * Successfully retrieve an Actuator entity that based on its ID
     */
    @Test
    void successfullyFindsActuatorByID () {
        Optional<Actuator> retrievedActuator = actuatorRepositoryMem.findEntityByID(actuatorID1);
        assertTrue(retrievedActuator.isPresent());
        assertEquals(actuatorEnt1, retrievedActuator.get());
    }

    /**
     * Fail to retrieve an Actuator entity with an ID not present in the repository
     */
    @Test
    void failFindActuatorWithSpecificId () {
        ActuatorID nonExistentID = new ActuatorID("nonExistentID");

        Optional<Actuator> retrievedActuator = actuatorRepositoryMem.findEntityByID(nonExistentID);

        assertThrows(NoSuchElementException.class, retrievedActuator::get);
    }

    /**
     * Successfully finds an Actuator entity in the repo based in a given ID
     */
    @Test
    void successContainsEntity () {
        assertTrue(actuatorRepositoryMem.containsEntityByID(actuatorID1));
    }

    /**
     * Fail to find an Actuator entity in the repository based on a given ID
     */
    @Test
    void failContainsEntity () {
        ActuatorID nonExistentID = new ActuatorID("nonExistentID");
        assertFalse(actuatorRepositoryMem.containsEntityByID(nonExistentID));
    }

    /**
     * Successfully retrieves a list of Actuator entities with a specific ActuatorFunctionalityID
     */
    @Test
    void successGetListOfActuatorsByActuatorFunctionalityID () {
        //Mock ActuatorFunctionalityID and set its behavior
        ActuatorFunctionalityID actuatorFunctionalityID1 = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID1.toString()).thenReturn("BlindSetter");

        //Provide behavior for the actuator entities
        when(actuatorEnt1.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID1);
        when(actuatorEnt2.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID1);


        // Test the method
        Iterable<Actuator> result1 = actuatorRepositoryMem.findByActuatorFunctionalityID(actuatorFunctionalityID1);
        List<Actuator> resultList = new ArrayList<>();
        result1.forEach(resultList::add);

        assertEquals(2, resultList.size());
        assertTrue(resultList.containsAll(Arrays.asList(actuatorEnt1, actuatorEnt2)));
    }

    /**
     * Test the findByActuatorFunctionalityID method when the provided ActuatorFunctionalityID is null
     */
    @Test
    void failGetListOfActuatorsByActuatorFunctionalityID () {
        assertThrows(NullPointerException.class, () -> actuatorRepositoryMem.findByActuatorFunctionalityID(null));
    }

    /**
     * Test the findByDeviceID method when the provided DeviceID is valid
     * Return a list of Actuator entities with the specified DeviceID
     */
    @Test
    void successGetListOfActuatorsByDeviceID () {
        //Mock DeviceID and set its behavior
        DeviceID deviceID1 = mock(DeviceID.class);
        when(deviceID1.toString()).thenReturn("BlindRoller");

        //Provide behavior for the actuator entities
        when(actuatorEnt1.getDeviceName()).thenReturn(deviceID1);
        when(actuatorEnt2.getDeviceName()).thenReturn(deviceID1);

        // Test the method
        Iterable<Actuator> result1 = actuatorRepositoryMem.findByDeviceID(deviceID1);
        List<Actuator> resultList = new ArrayList<>();
        result1.forEach(resultList::add);

        assertEquals(2, resultList.size());
        assertTrue(resultList.containsAll(Arrays.asList(actuatorEnt1, actuatorEnt2)));
    }

    /**
     * Test the findByDeviceID method when the provided DeviceID is null
     */
    @Test
    void failGetListOfActuatorsByDeviceID () {
        assertThrows(IllegalArgumentException.class, () -> actuatorRepositoryMem.findByDeviceID(null));
    }

    /**
     * Test the findByDeviceIDAndActuatorFunctionalityID method when the provided DeviceID and ActuatorFunctionalityID are valid
     * Return a list of Actuator entities with the specified DeviceID and ActuatorFunctionalityID
     */
    @Test
    void successGetListOfActuatorsByDeviceIDAndActuatorFunctionalityID () {
        //Mock DeviceID and set its behavior
        DeviceID deviceID1 = mock(DeviceID.class);
        when(deviceID1.toString()).thenReturn("BlindRoller");

        when(actuatorEnt1.getDeviceName()).thenReturn(deviceID1);
        when(actuatorEnt2.getDeviceName()).thenReturn(deviceID1);

        //Mock ActuatorFunctionalityID and set its behavior
        ActuatorFunctionalityID actuatorFunctionalityID1 = mock(ActuatorFunctionalityID.class);
        when(actuatorFunctionalityID1.toString()).thenReturn("BlindSetter");

        //Provide behavior for the actuator entities
        when(actuatorEnt1.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID1);
        when(actuatorEnt2.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID1);

        // Test the method
        Iterable<Actuator> result1 = actuatorRepositoryMem.findByDeviceIDAndActuatorFunctionalityID(deviceID1, actuatorFunctionalityID1);
        List<Actuator> resultList = new ArrayList<>();
        result1.forEach(resultList::add);


        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(actuatorEnt1));
        assertTrue(resultList.contains(actuatorEnt2));
    }

    /**
     * Test the findByDeviceIDAndActuatorFunctionalityID method when the provided DeviceID and ActuatorFunctionalityID are null
     */
    @Test
    void failGetListOfActuatorsByDeviceIDAndActuatorFunctionalityID () {
        assertThrows(NullPointerException.class, () -> actuatorRepositoryMem.findByDeviceIDAndActuatorFunctionalityID(null, null));
    }
}