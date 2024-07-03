package smarthome.persistence.springdata.repositoriesspringdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.actuators.Actuator;
import smarthome.domain.actuators.BlindSetterActuator;
import smarthome.domain.actuators.FactoryActuator;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.persistence.jpa.datamodel.ActuatorDataModel;
import smarthome.persistence.jpa.datamodel.MapperActuatorDataModel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ActuatorRepositorySpringDataImp.class})
class ActuatorRepositorySpringDataImpTest {

	@MockBean
	private ActuatorRepositorySpringData actuatorRepositorySpringDataInt;

	@MockBean
	private FactoryActuator factoryActuator;

	@MockBean
	private MapperActuatorDataModel mapperActuatorDataModel;

	@InjectMocks
	private ActuatorRepositorySpringDataImp actuatorRepositorySpringDataImp;

	/**
	 * Set up the test ActuatorRepositorySpringDataImpTest
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void saveValidActuatorSuccessTest() {
		ActuatorID actuatorID = mock(ActuatorID.class);
		when(actuatorID.toString()).thenReturn("actuatorID");

		ActuatorProperties actuatorProperties = mock(ActuatorProperties.class);
		when(actuatorProperties.toString()).thenReturn("actuatorProperties");

		ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
		when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

		DeviceID deviceName = mock(DeviceID.class);
		when(deviceName.toString()).thenReturn("deviceName");

		Actuator actuator = mock(BlindSetterActuator.class);
		when(actuator.identity()).thenReturn(actuatorID);
		when(actuator.getActuatorProperties()).thenReturn(actuatorProperties);
		when(actuator.getDeviceName()).thenReturn(deviceName);
		when(actuator.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID);
		when(factoryActuator.createActuator(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceName, "BlindSetter" )).thenReturn(actuator);

		Actuator result = actuatorRepositorySpringDataImp.save(actuator);
		assertNotNull(result);
	}


	@Test
	void findAllEntitiesSuccessTest() {
		ActuatorID actuatorID = mock(ActuatorID.class);
		when(actuatorID.toString()).thenReturn("actuatorID");

		ActuatorProperties actuatorProperties = mock(ActuatorProperties.class);
		when(actuatorProperties.toString()).thenReturn("actuatorProperties");

		ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
		when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

		DeviceID deviceName = mock(DeviceID.class);
		when(deviceName.toString()).thenReturn("deviceName");

		//Create actuatorDouble
		Actuator actuatorDouble = mock(BlindSetterActuator.class);
		when(actuatorDouble.identity()).thenReturn(actuatorID);
		when(actuatorDouble.getActuatorProperties()).thenReturn(actuatorProperties);
		when(actuatorDouble.getDeviceName()).thenReturn(deviceName);
		when(actuatorDouble.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID);
		when(factoryActuator.createActuator(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceName, "BlindSetter")).thenReturn(actuatorDouble);

		ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);

		when(actuatorRepositorySpringDataInt.findAll()).thenReturn(List.of(actuatorDataModel));
		when(mapperActuatorDataModel.toDomainList(factoryActuator, List.of(actuatorDataModel))).thenReturn(List.of(actuatorDouble));

		actuatorRepositorySpringDataImp.save(actuatorDouble);

		Iterable<Actuator> result = actuatorRepositorySpringDataImp.findAllEntities();

		assertEquals(1, ((List<Actuator>) result).size());
	}

	@Test
	void findEntityByIDSuccessTest() {
		ActuatorID actuatorID = mock(ActuatorID.class);
		when(actuatorID.toString()).thenReturn("actuatorID");

		ActuatorProperties actuatorProperties = mock(ActuatorProperties.class);
		when(actuatorProperties.toString()).thenReturn("actuatorProperties");

		ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
		when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");

		DeviceID deviceName = mock(DeviceID.class);
		when(deviceName.toString()).thenReturn("deviceName");

		//Create actuatorDouble
		Actuator actuatorDouble = mock(BlindSetterActuator.class);
		when(actuatorDouble.identity()).thenReturn(actuatorID);
		when(actuatorDouble.getActuatorProperties()).thenReturn(actuatorProperties);
		when(actuatorDouble.getDeviceName()).thenReturn(deviceName);
		when(actuatorDouble.getActuatorFunctionalityID()).thenReturn(actuatorFunctionalityID);
		when(factoryActuator.createActuator(actuatorID, actuatorFunctionalityID, actuatorProperties, deviceName, "BlindSetter")).thenReturn(actuatorDouble);

		ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);

		when(actuatorRepositorySpringDataInt.findById(actuatorID.toString())).thenReturn(Optional.of(actuatorDataModel));
		when(mapperActuatorDataModel.toDomain(factoryActuator, actuatorDataModel)).thenReturn(actuatorDouble);
		actuatorRepositorySpringDataImp.save(actuatorDouble);

		Optional<Actuator> result = actuatorRepositorySpringDataImp.findEntityByID(actuatorID);

		assertTrue(result.isPresent());
	}

@Test
void failFindEntityByIDDataModelIsNotPresentTest() {
	ActuatorID actuatorID = mock(ActuatorID.class);
	when(actuatorID.toString()).thenReturn("actuatorID");

	when(actuatorRepositorySpringDataInt.findById(actuatorID.toString())).thenReturn(Optional.empty());

	Optional<Actuator> result = actuatorRepositorySpringDataImp.findEntityByID(actuatorID);

	assertTrue(result.isEmpty());
}

	@Test
	void containsEntityByIDSuccessTest() {
		ActuatorID actuatorID = mock(ActuatorID.class);
		when(actuatorID.toString()).thenReturn("actuatorID");

		ActuatorProperties actuatorProperties = mock(ActuatorProperties.class);
		when(actuatorProperties.toString()).thenReturn("actuatorProperties");

		ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
		when(actuatorFunctionalityID.toString()).thenReturn("actuatorFunctionalityID");

		DeviceID deviceName = mock(DeviceID.class);
		when(deviceName.toString()).thenReturn("deviceName");

		when(actuatorRepositorySpringDataImp.containsEntityByID(actuatorID)).thenReturn(true);

		assertTrue(actuatorRepositorySpringDataImp.containsEntityByID(actuatorID));
	}

	/**
	 * Tests the {@code getListOfActuatorsByActuatorFunctionalityID} method when successfully retrieving an iterable of actuators by their ActuatorFunctionalityID.
	 */
	@Test
	void successfulGetListOfActuatorsByActuatorFunctionalityID() {
		//Create doubles to be used by method under test
		ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
		ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);
		Actuator actuator = mock(Actuator.class);

		//Set behaviour of mocked entities
		when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");
		when(actuatorDataModel.getActuatorFunctionalityID()).thenReturn("BlindSetter");

		//Set behaviour of mocked beans
		when(actuatorRepositorySpringDataInt.findByActuatorFunctionalityID(actuatorFunctionalityID.toString())).thenReturn(List.of(actuatorDataModel));
		when(mapperActuatorDataModel.toDomainList(factoryActuator,List.of(actuatorDataModel))).thenReturn(List.of(actuator));

		// Invoke the method under test
		Iterable<Actuator> result = actuatorRepositorySpringDataImp.findByActuatorFunctionalityID(actuatorFunctionalityID);
		assertEquals(1, ((List<Actuator>) result).size());
	}

	/**
	 * Tests the {@code findByDeviceID} method when successfully retrieving an iterable of actuators by their DeviceID.
	 */
	@Test
	void successfulGetListOfActuatorsByDeviceID() {
		//Create doubles to be used by method under test
		DeviceID deviceID = mock(DeviceID.class);
		ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);
		Actuator actuator = mock(Actuator.class);

		//Set behaviour of mocked entities
		when(deviceID.toString()).thenReturn("BlindRoller");
		when(actuatorDataModel.getDeviceID()).thenReturn("BlindRoller");

		//Set behaviour of mocked beans
		when(actuatorRepositorySpringDataInt.findByDeviceID(deviceID.toString())).thenReturn(List.of(actuatorDataModel));
		when(mapperActuatorDataModel.toDomainList(factoryActuator,List.of(actuatorDataModel))).thenReturn(List.of(actuator));

		// Invoke the method under test
		Iterable<Actuator> result = actuatorRepositorySpringDataImp.findByDeviceID(deviceID);
		assertEquals(1, ((List<Actuator>) result).size());
	}

	/**
	 * Tests the {@code findByDeviceIDAndActuatorFunctionalityID} method when successfully retrieving an iterable of actuators by their DeviceID and ActuatorFunctionalityID.
	 * The provided DeviceID and ActuatorFunctionalityID are valid.
	 */
	@Test
	void successfulGetListOfActuatorsByDeviceIDAndActuatorFunctionalityID() {
		//Create doubles to be used by method under test
		DeviceID deviceID = mock(DeviceID.class);
		ActuatorFunctionalityID actuatorFunctionalityID = mock(ActuatorFunctionalityID.class);
		ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);
		Actuator actuator = mock(Actuator.class);

		//Set behaviour of mocked entities
		when(deviceID.toString()).thenReturn("BlindRoller");
		when(actuatorFunctionalityID.toString()).thenReturn("BlindSetter");
		when(actuatorDataModel.getDeviceID()).thenReturn("BlindRoller");
		when(actuatorDataModel.getActuatorFunctionalityID()).thenReturn("BlindSetter");

		//Set behaviour of mocked beans
		when(actuatorRepositorySpringDataInt.findByDeviceIDAndActuatorFunctionalityID(deviceID.toString(), actuatorFunctionalityID.toString())).thenReturn(List.of(actuatorDataModel));
		when(mapperActuatorDataModel.toDomainList(factoryActuator,List.of(actuatorDataModel))).thenReturn(List.of(actuator));

		// Invoke the method under test
		Iterable<Actuator> result = actuatorRepositorySpringDataImp.findByDeviceIDAndActuatorFunctionalityID(deviceID, actuatorFunctionalityID);
		assertEquals(1, ((List<Actuator>) result).size());
	}


}