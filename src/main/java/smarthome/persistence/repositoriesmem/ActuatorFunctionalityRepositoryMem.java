package smarthome.persistence.repositoriesmem;

import org.springframework.stereotype.Repository;
import smarthome.domain.actuatorfunctionality.ActuatorFunctionality;
import smarthome.domain.actuatorfunctionality.FactoryActuatorFunctionality;
import smarthome.domain.repository.ActuatorFunctionalityRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.persistence.jpa.datamodel.MapperActuatorFunctionalityPersistence;
import smarthome.util.ConfigScraper;
import smarthome.util.PropertyLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of Repository interface for managing ActuatorFunctionality objects in memory.
 */
@Repository
public class ActuatorFunctionalityRepositoryMem implements ActuatorFunctionalityRepository
{
    /**
     * Map to store ActuatorFunctionality entities, keyed by their ActuatorFunctionalityID.
     */
    private Map<ActuatorFunctionalityID, ActuatorFunctionality> dataMap;

    /**
     * A constant string indicating the path to the configuration properties file
     */
    private static final String CONFIG_PROPERTIES = "config/config.properties";

    /**
     * Map to store the association between ActuatorFunctionalityID and ActuatorType.
     */
    private final Map<ActuatorFunctionalityID, String> actuatorFunctionalityIDSensorTypeMap;

    /**
     * ConfigScraper object to read configuration properties.
     */
    private final ConfigScraper configScraper;

    /**
     * Constructor of Actuator Functionality Repository that loads the config.properties file
     *
     * @param factory the Actuator Functionality factory needs to be injected in the constructor
     */
    public ActuatorFunctionalityRepositoryMem(FactoryActuatorFunctionality factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Invalid factory");
        }

        MapperActuatorFunctionalityPersistence mapper = new MapperActuatorFunctionalityPersistence(factory);
        PropertyLoader propertyLoader = new PropertyLoader();
        this.configScraper = new ConfigScraper(CONFIG_PROPERTIES, propertyLoader);
        this.dataMap = loadActuatorFunctionalityMap(mapper);
        this.actuatorFunctionalityIDSensorTypeMap = loadActuatorFunctionalityIDSensorTypeMap(mapper);
    }

    /**
     * Method to load the available Actuator Functionalities from the config.properties file.
     * The method loads the Strings representing ActuatorFunctionalityID objects from the ConfigScraper class,
     * calls MapperActuatorFunctionalityPersistence class to convert the list of Strings into a list of
     * ActuatorFunctionalityID objects.
     *
     * @return A list of ActuatorFunctionalityID objects.
     */
    private Map<ActuatorFunctionalityID, ActuatorFunctionality> loadActuatorFunctionalityMap(MapperActuatorFunctionalityPersistence mapper)
    {
        Map<ActuatorFunctionalityID, ActuatorFunctionality> actuatorFunctionalityMap = new HashMap<>();

        List<String> repoActuatorFunctionalityIDList = configScraper.loadActuatorFunctionalityList();

        for (String actuatorFunctionalityString : repoActuatorFunctionalityIDList)
        {
            ActuatorFunctionality actuatorFunctionality = mapper.toDomain(actuatorFunctionalityString);
            actuatorFunctionalityMap.put(actuatorFunctionality.identity(), actuatorFunctionality);
        }

        return actuatorFunctionalityMap;
    }

    /**
     * Retrieves a hashMap with all entities from the repository.
     *
     * @return An Iterable containing all entities in the repository.
     */
    @Override
    public Iterable<ActuatorFunctionality> findAllEntities()
    {
        return dataMap.values();
    }

    /**
     * Finds an entity in the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */
    @Override
    public Optional<ActuatorFunctionality> findEntityByID(ActuatorFunctionalityID id)
    {
        return Optional.ofNullable(dataMap.get(id));
    }

    /**
     * Checks whether an entity with the given identifier exists in the repository.
     *
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
     */
    @Override
    public boolean containsEntityByID(ActuatorFunctionalityID id)
    {
        return dataMap.containsKey(id);
    }


    /**
     * Retrieve the class name for the actuator functionality ID.
     * @param actuatorFunctionalityID The actuator functionality ID.
     * @return A string representing the class name.
     */
    @Override
    public String getClassNameForActuatorFunctionalityID(ActuatorFunctionalityID actuatorFunctionalityID) {
        return actuatorFunctionalityIDSensorTypeMap.get(actuatorFunctionalityID);
    }

    /**
     * Use ConfigLoader class to load list of ActuatorFunctionalities from file and create private Map<ActuatorFunctionalityID, String>.
     * Strings are the class paths to the respective Actuator type for a given ActuatorFunctionality.
     *
     * @return Returns a Map<ActuatorFunctionalityID, String>. Map will be empty if no ActuatorFunctionalities are found in file.
     */

    private Map<ActuatorFunctionalityID, String> loadActuatorFunctionalityIDSensorTypeMap (MapperActuatorFunctionalityPersistence mapper)
    {
        Map<ActuatorFunctionalityID, String> actuatorFunctionalityIDActuatorTypeMap = new HashMap<>();

        Map<String, String> actuatorFunctionalityStringAndActuatorTypeStringMap = configScraper.loadActuatorFunctionalityStringAndSensorTypeStringMap();

        if (actuatorFunctionalityStringAndActuatorTypeStringMap.isEmpty())
        {
            return actuatorFunctionalityIDActuatorTypeMap;
        }

        for (Map.Entry<String, String> entry : actuatorFunctionalityStringAndActuatorTypeStringMap.entrySet())
        {
            ActuatorFunctionality actuatorFunctionality = mapper.toDomain(entry.getKey());
            actuatorFunctionalityIDActuatorTypeMap.put(actuatorFunctionality.identity(), entry.getValue());
        }
        return actuatorFunctionalityIDActuatorTypeMap;
    }
}
