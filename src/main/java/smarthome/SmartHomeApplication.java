package smarthome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * The main entry point for the Smart Home application.
 * This class initializes and starts the Spring Boot application.
 */
@SpringBootApplication
public class SmartHomeApplication {

    /**
     * The main method of the application.
     * It starts the Spring Boot application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SmartHomeApplication.class, args);

          // Dev Tools
          // Uncomment for Postman Testing
//        DatabaseLoader databaseLoader = applicationContext.getBean(DatabaseLoader.class);
//        databaseLoader.loadInitialData();
    }
}
