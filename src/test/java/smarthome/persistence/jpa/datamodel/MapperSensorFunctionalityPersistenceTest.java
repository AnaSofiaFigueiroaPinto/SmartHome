package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.Test;
import smarthome.domain.sensorfunctionality.FactorySensorFunctionality;
import smarthome.domain.sensorfunctionality.SensorFunctionality;
import smarthome.domain.valueobjects.SensorFunctionalityID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the MapperSensorFunctionalityPersistence class.
 */
class MapperSensorFunctionalityPersistenceTest {

	/**
	 * Test case for successfully convert of a String to an SensorFunctionality object.
	 */
	@Test
	public void testToDomain_ValidInput_ReturnsSensorFunctionality() {
		FactorySensorFunctionality factoryMock = mock(FactorySensorFunctionality.class);
		SensorFunctionality expectedSensorFunctionality = mock(SensorFunctionality.class);
		when(factoryMock.createSensorFunctionality(any())).thenReturn(expectedSensorFunctionality);

		MapperSensorFunctionalityPersistence mapper = new MapperSensorFunctionalityPersistence(factoryMock);

		String validSensorFunctionalityAsString = "someValidString";

		SensorFunctionality result = mapper.toDomain(validSensorFunctionalityAsString);

		// Verify that the dependency method was called with the correct argument
		verify(factoryMock).createSensorFunctionality(any(SensorFunctionalityID.class));

		assertNotNull(result);
		assertEquals(expectedSensorFunctionality, result);
	}

	/**
	 * Test case for failing to convert a null String to an SensorFunctionality object.
	 */
	@Test
	void failToConvertStringToDomainNullString() {
		FactorySensorFunctionality factoryMock = mock(FactorySensorFunctionality.class);
		when(factoryMock.createSensorFunctionality(any())).thenThrow(IllegalArgumentException.class);

		MapperSensorFunctionalityPersistence mapper = new MapperSensorFunctionalityPersistence(factoryMock);

		SensorFunctionality result = mapper.toDomain(null);

		assertNull(result);
	}
}