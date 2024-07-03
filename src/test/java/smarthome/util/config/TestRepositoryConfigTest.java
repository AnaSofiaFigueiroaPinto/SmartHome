package smarthome.util.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import smarthome.domain.repository.*;
import smarthome.persistence.springdata.repositoriesspringdata.testrepositories.InstantTimeLocationValueTestRepoSpringData;
import smarthome.persistence.springdata.repositoriesspringdata.testrepositories.InstantTimeValueTestRepositorySpringData;
import smarthome.persistence.springdata.repositoriesspringdata.testrepositories.PeriodTimeValueTestRepositorySpringData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for verifying the configuration of repository beans in the application context.
 * Tests ensure that repository beans are correctly instantiated and functional with mock data in a test profile environment.
 */
@SpringBootTest
@ActiveProfiles("test")
public class TestRepositoryConfigTest {

    /**
     * The application context used for dependency injection and retrieving beans for testing.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Test method to verify the existence and functionality of {@code HouseRepository} bean in the application context.
     * Checks if the bean is present and able to execute {@code findAllEntities} without throwing exceptions.
     */
    @Test
    public void testHouseRepositoryBeanExists() {
        HouseRepository houseRepository = applicationContext.getBean(HouseRepository.class);
        assertNotNull(houseRepository, "HouseRepository bean should be present in the application context");
        // Additional validation to ensure the bean is functional
        assertDoesNotThrow(houseRepository::findAllEntities, "HouseRepository should be functional and return a list");
    }

    /**
     * Test method to verify the existence and functionality of {@code RoomRepository} bean in the application context.
     * Checks if the bean is present and able to execute {@code findAllEntities} without throwing exceptions.
     */
    @Test
    public void testRoomRepositoryBeanExists() {
        RoomRepository roomRepository = applicationContext.getBean(RoomRepository.class);
        assertNotNull(roomRepository, "RoomRepository bean should be present in the application context");
        assertDoesNotThrow(roomRepository::findAllEntities, "RoomRepository should be functional and return a list");
    }

    /**
     * Test method to verify the existence and functionality of {@code DeviceRepository} bean in the application context.
     * Checks if the bean is present and able to execute {@code findAllEntities} without throwing exceptions.
     */
    @Test
    public void testDeviceRepositoryBeanExists() {
        DeviceRepository deviceRepository = applicationContext.getBean(DeviceRepository.class);
        assertNotNull(deviceRepository, "DeviceRepository bean should be present in the application context");
        assertDoesNotThrow(deviceRepository::findAllEntities, "DeviceRepository should be functional and return a list");
    }

    /**
     * Test method to verify the existence and functionality of {@code SensorRepository} bean in the application context.
     * Checks if the bean is present and able to execute {@code findAllEntities} without throwing exceptions.
     */
    @Test
    public void testSensorRepositoryBeanExists() {
        SensorRepository sensorRepository = applicationContext.getBean(SensorRepository.class);
        assertNotNull(sensorRepository, "SensorRepository bean should be present in the application context");
        assertDoesNotThrow(sensorRepository::findAllEntities, "SensorRepository should be functional and return a list");
    }

    /**
     * Test method to verify the existence and functionality of {@code ActuatorRepository} bean in the application context.
     * Checks if the bean is present and able to execute {@code findAllEntities} without throwing exceptions.
     */
    @Test
    public void testActuatorRepositoryBeanExists() {
        ActuatorRepository actuatorRepository = applicationContext.getBean(ActuatorRepository.class);
        assertNotNull(actuatorRepository, "ActuatorRepository bean should be present in the application context");
        assertDoesNotThrow(actuatorRepository::findAllEntities, "ActuatorRepository should be functional and return a list");
    }

    /**
     * Test method to verify the existence and functionality of {@code InstantTimeValueTestRepositorySpringData} bean
     * in the application context. Checks if the bean is present and able to execute {@code findAllEntities} without
     * throwing exceptions.
     */
    @Test
    public void testInstantTimeValueRepositoryBeanExists() {
        InstantTimeValueTestRepositorySpringData instantTimeValueRepository = applicationContext.getBean(InstantTimeValueTestRepositorySpringData.class);
        assertNotNull(instantTimeValueRepository, "InstantTimeValueTestRepositorySpringData bean should be present in the application context");
        assertDoesNotThrow(instantTimeValueRepository::findAllEntities, "InstantTimeValueTestRepositorySpringData should be functional and return a list");
    }

    /**
     * Test method to verify the existence and functionality of {@code InstantTimeLocationValueTestRepoSpringData} bean
     * in the application context. Checks if the bean is present and able to execute {@code findAllEntities} without
     * throwing exceptions.
     */
    @Test
    public void testInstantTimeLocationValueRepositoryBeanExists() {
        InstantTimeLocationValueTestRepoSpringData instantTimeLocationValueRepository = applicationContext.getBean(InstantTimeLocationValueTestRepoSpringData.class);
        assertNotNull(instantTimeLocationValueRepository, "InstantTimeLocationValueTestRepoSpringData bean should be present in the application context");
        assertDoesNotThrow(instantTimeLocationValueRepository::findAllEntities, "InstantTimeLocationValueTestRepoSpringData should be functional and return a list");
    }

    /**
     * Test method to verify the existence and functionality of {@code PeriodTimeValueTestRepositorySpringData} bean
     * in the application context. Checks if the bean is present and able to execute {@code findAllEntities} without
     * throwing exceptions.
     */
    @Test
    public void testPeriodTimeValueRepositoryBeanExists() {
        PeriodTimeValueTestRepositorySpringData periodTimeValueRepository = applicationContext.getBean(PeriodTimeValueTestRepositorySpringData.class);
        assertNotNull(periodTimeValueRepository, "PeriodTimeValueTestRepositorySpringData bean should be present in the application context");
        assertDoesNotThrow(periodTimeValueRepository::findAllEntities, "PeriodTimeValueTestRepositorySpringData should be functional and return a list");
    }
}
