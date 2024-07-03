package smarthome.persistence.repositoriesmem;

import org.springframework.stereotype.Repository;
import smarthome.domain.repository.SensorFunctionalityRepository;
import smarthome.domain.sensorfunctionality.FactorySensorFunctionality;
import smarthome.domain.sensorfunctionality.SensorFunctionality;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.persistence.jpa.datamodel.MapperSensorFunctionalityPersistence;
import smarthome.util.ConfigScraper;
import smarthome.util.PropertyLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of Repository interface for managing SensorFunctionality objects in memory.
 */
@Repository
public class SensorFunctionalityRepositoryMem implements SensorFunctionalityRepository
{

    /**
     * Map to store SensorFunctionality entities, keyed by their SensorFunctionalityID.
     */
    private final Map<SensorFunctionalityID, SensorFunctionality> sensorFunctionalityMap;

    /**
     * Map to store the association between SensorFunctionalityID and SensorType.
     */
    private final Map<SensorFunctionalityID, String> sensorFunctionalityIDSensorTypeMap;

    /**
     * Map to store the association between SensorFunctionalityID and Repository method calls.
     */
    private final Map<SensorFunctionalityID, String> sensorFunctionalityIDServiceMethodCallMap;

    /**
     * Map to store the association between SensorFunctionalityID and units.
     */
    private final Map<SensorFunctionalityID, String> sensorFunctionalityIDUnitMap;

    /**
     * A constant string indicating the path to the configuration properties file
     */
    private static final String CONFIG_PROPERTIES = "config/config.properties";

    /**
     * ConfigScraper object to read configuration properties.
     */
    private final ConfigScraper configScraper;

    /**
     * Constructor of Sensor Functionality Repository Object.
     *
     * @param factory the Sensor Functionality factory needs to be injected in the constructor.
     */
    public SensorFunctionalityRepositoryMem(FactorySensorFunctionality factory) {
        if (factory == null)
        {
            throw new IllegalArgumentException("FactorySensorFunctionality cannot be invalid.");
        }

        MapperSensorFunctionalityPersistence mapper = new MapperSensorFunctionalityPersistence(factory);

        PropertyLoader propertyLoader = new PropertyLoader();
        this.configScraper = new ConfigScraper(CONFIG_PROPERTIES, propertyLoader);
        this.sensorFunctionalityMap = loadSensorFunctionalityMap(mapper);
        this.sensorFunctionalityIDSensorTypeMap = loadSensorFunctionalityIDSensorTypeMap(mapper);
        this.sensorFunctionalityIDServiceMethodCallMap = loadServiceMethodCallStringsMap(mapper);
        this.sensorFunctionalityIDUnitMap = loadUnitsMap(mapper);
    }

    /**
     * Use ConfigLoader class to load list of sensorFunctionalities from file and create private Map<SensorFunctionalityID, SensorFunctionality>
     * @return Returns a Map<SensorFunctionalityID, SensorFunctionality>. Map will be empty if no sensorFunctionalities are found in file.
     */
    private Map<SensorFunctionalityID, SensorFunctionality> loadSensorFunctionalityMap (MapperSensorFunctionalityPersistence mapper)
    {
        Map<SensorFunctionalityID, SensorFunctionality> sensorFunctionalityMap = new HashMap<>();

        List<String> sensorFunctionalityAsStringList = configScraper.loadSensorFunctionalityList();

        if (sensorFunctionalityAsStringList.isEmpty())
        {
            return sensorFunctionalityMap;
        }

        for (String sensorFunctionalityAsString : sensorFunctionalityAsStringList)
        {
            SensorFunctionality sensorFunctionality = mapper.toDomain(sensorFunctionalityAsString);
            sensorFunctionalityMap.put(sensorFunctionality.identity(), sensorFunctionality);
        }
        return sensorFunctionalityMap;
    }

    /**
     * Use ConfigLoader class to load list of sensorFunctionalities from file and create private Map<SensorFunctionalityID, String>.
     * Strings are the class paths to the respective Sensor type for a given SensorFunctionality.
     *
     * @return Returns a Map<SensorFunctionalityID, String>. Map will be empty if no sensorFunctionalities are found in file.
     */

    private Map<SensorFunctionalityID, String> loadSensorFunctionalityIDSensorTypeMap (MapperSensorFunctionalityPersistence mapper)
    {
        Map<SensorFunctionalityID, String> sensorFunctionalityIDSensorTypeMap = new HashMap<>();

        Map<String, String> sensorFunctionalityStringAndSensorTypeStringMap = configScraper.loadSensorFunctionalityStringAndSensorTypeStringMap();

        if (sensorFunctionalityStringAndSensorTypeStringMap.isEmpty())
        {
            return sensorFunctionalityIDSensorTypeMap;
        }

        for (Map.Entry<String, String> entry : sensorFunctionalityStringAndSensorTypeStringMap.entrySet())
        {
            SensorFunctionality sensorFunctionality = mapper.toDomain(entry.getKey());
            sensorFunctionalityIDSensorTypeMap.put(sensorFunctionality.identity(), entry.getValue());
        }
        return sensorFunctionalityIDSensorTypeMap;
    }

    /**
     * Use ConfigLoader class to load list of sensorFunctionalities from file and create private Map<SensorFunctionalityID, String>.
     * Strings are the method names to be called when loading a Value object from the repository for a given SensorFunctionality.
     *
     * @return Returns a Map<SensorFunctionalityID, String>. Map will be empty if no sensorFunctionalities are found in file.
     */
    private Map<SensorFunctionalityID, String> loadServiceMethodCallStringsMap (MapperSensorFunctionalityPersistence mapper)
    {
        Map<SensorFunctionalityID, String> sensorFunctionalityIDSensorTypeMap = new HashMap<>();
        Map<String, String> sensorFunctionalityStringAndRepoMethodString = configScraper.loadSensorFunctionalityStringAndValueServiceMethodStringMap();

        if (sensorFunctionalityStringAndRepoMethodString.isEmpty())
        {
            return sensorFunctionalityIDSensorTypeMap;
        }

        for (Map.Entry<String, String> entry : sensorFunctionalityStringAndRepoMethodString.entrySet())
        {
            SensorFunctionality sensorFunctionality = mapper.toDomain(entry.getKey());
            sensorFunctionalityIDSensorTypeMap.put(sensorFunctionality.identity(), entry.getValue());
        }
        return sensorFunctionalityIDSensorTypeMap;
    }

    /**
     * Use ConfigLoader class to load list of sensorFunctionalities from file and create private Map<SensorFunctionalityID, String>.
     * Strings are the units to be used when displaying a Value object for a given SensorFunctionality.
     * @return Returns a Map<SensorFunctionalityID, String>.
     */
    private Map<SensorFunctionalityID, String> loadUnitsMap (MapperSensorFunctionalityPersistence mapper)
    {
        Map<SensorFunctionalityID, String> sensorFunctionalityIDUnitMap = new HashMap<>();
        Map<String, String> sensorFunctionalityStringAndUnitStringMap = configScraper.loadSensorFunctionalityStringAndUnitStringMap();

        if (sensorFunctionalityStringAndUnitStringMap.isEmpty())
        {
            return sensorFunctionalityIDUnitMap;
        }

        for (Map.Entry<String, String> entry : sensorFunctionalityStringAndUnitStringMap.entrySet())
        {
            SensorFunctionality sensorFunctionality = mapper.toDomain(entry.getKey());
            sensorFunctionalityIDUnitMap.put(sensorFunctionality.identity(), entry.getValue());
        }
        return sensorFunctionalityIDUnitMap;
    }

    public String getClassNameForSensorFunctionalityID (SensorFunctionalityID sensorFunctionalityID)
    {
        return sensorFunctionalityIDSensorTypeMap.get(sensorFunctionalityID);
    }

    public String getServiceMethodToCallForSensorFunctionalityID (SensorFunctionalityID sensorFunctionalityID) {
        return sensorFunctionalityIDServiceMethodCallMap.get(sensorFunctionalityID);
    }

    /**
     * Retrieves all entities from the repository.
     *
     * @return An Iterable containing all entities in the repository.
     */
    @Override
    public Iterable<SensorFunctionality> findAllEntities()
    {
        return sensorFunctionalityMap.values();
    }

    /**
     * Finds an entity in the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */
    @Override
    public Optional<SensorFunctionality> findEntityByID(SensorFunctionalityID id)
    {
        return Optional.ofNullable(sensorFunctionalityMap.get(id));
    }

    /**
     * Checks whether an entity with the given identifier exists in the repository.
     *
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
     */
    @Override
    public boolean containsEntityByID(SensorFunctionalityID id)
    {
        return sensorFunctionalityMap.containsKey(id);
    }
}
