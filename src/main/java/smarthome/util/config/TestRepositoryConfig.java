package smarthome.util.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import smarthome.domain.repository.*;
import smarthome.persistence.springdata.repositoriesspringdata.testrepositories.*;
/**
 * Configuration class for setting up test-specific repository beans.
 * This class provides bean definitions for repositories used in testing environments.
 */
@Configuration
@Profile("test")
public class TestRepositoryConfig {

    /**
     * Bean definition for the HouseRepository interface.
     * @return A HouseRepository instance for testing purposes.
     */
    @Bean
    public HouseRepository houseRepository() {
        return new HouseTestRepositorySpringData();
    }

    /**
     * Bean definition for the RoomRepository interface.
     * @return A RoomRepository instance for testing purposes.
     */
    @Bean
    public RoomRepository roomRepository() {
        return new RoomTestRepositorySpringData();
    }

    /**
     * Bean definition for the DeviceRepository interface.
     * @return A DeviceRepository instance for testing purposes.
     */
    @Bean
    public DeviceRepository deviceRepository() {
        return new DeviceTestRepositorySpringData();
    }

    /**
     * Bean definition for the SensorRepository interface.
     * @return A SensorRepository instance for testing purposes.
     */
    @Bean
    public SensorRepository sensorRepository() {
        return new SensorTestRepositorySpringData();
    }

    /**
     * Bean definition for the ActuatorRepository interface.
     * @return An ActuatorRepository instance for testing purposes.
     */
    @Bean
    public ActuatorRepository actuatorRepository() {
        return new ActuatorTestRepositorySpringData();
    }

    /**
     * Bean definition for the InstantTimeValueTestRepositorySpringData class.
     * @return An InstantTimeValueTestRepositorySpringData instance for testing purposes.
     */
    @Bean
    public InstantTimeValueTestRepositorySpringData instantTimeValueRepository() {
        return new InstantTimeValueTestRepositorySpringData();
    }

    /**
     * Bean definition for the InstantTimeLocationValueTestRepoSpringData class.
     * @return An InstantTimeLocationValueTestRepoSpringData instance for testing purposes.
     */
    @Bean
    public InstantTimeLocationValueTestRepoSpringData instantTimeLocationValueRepository() {
        return new InstantTimeLocationValueTestRepoSpringData();
    }

    /**
     * Bean definition for the PeriodTimeValueTestRepositorySpringData class.
     * @return A PeriodTimeValueTestRepositorySpringData instance for testing purposes.
     */
    @Bean
    public PeriodTimeValueTestRepositorySpringData periodTimeValueRepository() {
        return new PeriodTimeValueTestRepositorySpringData();
    }
}

