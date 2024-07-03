package smarthome.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for scraping config.properties file for pertinent and providing data in specified format.
 * Class only provides strings. Classes that call this class will be responsible for using the strings to construct respective domain objects (eg. List<String> -> List<SensorFunctionality>).
 */
public class ConfigScraper {
    /**
     * File path variable injected at runtime.
     */
    private String filePath;

    /**
     * Constants used to mark opening tags of blocks and property separators in config.properties file.
     */
    private static final String openingMainBlockTag = "= {";
    private static final String closingMainBlockTag = "}";
    private static final String propertySplitter = "=";

    /**
     * Constants used to mark sensor block
     */
    private static final String sensorBlock = "sensor";

    /**
     * Constants used to mark actuator block
     */
    private static final String actuatorBlock = "actuator";

    /**
     * Constants used to mark block that contains general properties of the application
     */
    private static final String generalPropertiesBlock = "generalProperties";

    /**
     * Constants used to mark functionality property
     */
    private static String functionalityProperty = "functionality";

    /**
     * PropertyLoader instance used for loading properties and files
     */
    private PropertyLoader propertyLoader;

    /**
     * Constructs a ConfigScraper instance with the specified file path and PropertyLoader.
     *
     * @param filePath       the path to the configuration file
     * @param propertyLoader the PropertyLoader instance used for loading properties and files
     * @throws IllegalArgumentException if the file path is null or empty
     */
    public ConfigScraper(String filePath, PropertyLoader propertyLoader) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }
        this.filePath = filePath;
        this.propertyLoader = propertyLoader;
    }

    /**
     * Method load all functionality Strings belonging to a sensor from a text based config file.
     *
     * @return List with Strings that represent sensor functionality. If any exceptions are encountered an empty list is returned.
     */
    public List<String> loadSensorFunctionalityList() {
        return loadFunctionalityList(sensorBlock, functionalityProperty);
    }

    /**
     * Method load all functionality Strings belonging to an actuator from a text based config file.
     */
    public List<String> loadActuatorFunctionalityList() {
        return loadFunctionalityList(actuatorBlock, functionalityProperty);
    }

    /**
     * Method load all functionality Strings belonging to a sensor or actuator block from a text based config file.
     *
     * @param mainBlockTag the main block tag in the configuration file containing the functionality strings
     * @param propertyTag  the property tag indicating the functionality strings to be extracted
     * @return a list of functionality strings extracted from the configuration file
     */
    private List<String> loadFunctionalityList(String mainBlockTag, String propertyTag) {
        try (BufferedReader reader = propertyLoader.getBufferedReader(this.filePath)) {

            List<String> functionalityAsStringList = new ArrayList<>();
            mainBlockTag = mainBlockTag + " " + this.openingMainBlockTag;
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().equals(mainBlockTag)) {

                    while ((line = reader.readLine()) != null && !line.trim().equals(closingMainBlockTag)) {

                        if (line.contains(propertyTag)) {

                            String[] parts = line.split(propertySplitter);

                            if (parts.length >= 2) {

                                String functionality = parts[1].trim();
                                functionalityAsStringList.add(functionality);
                            }
                        }
                    }
                }
            }
            return functionalityAsStringList;

        } catch (IOException exception) {
            return new ArrayList<>();
        }
    }

    /**
     * Method to load all functionality Strings (key) and sensorType Strings (value) belonging to a sensor block from a text based config file and create a Map<String, String>.
     *
     * @return Map<String, String> Key: functionality String, Value: sensorType String.
     */

    public Map<String, String> loadSensorFunctionalityStringAndSensorTypeStringMap() {
        return scrapeFileAndBuildPropertyStringToPropertyStringMap(sensorBlock, functionalityProperty, "sensorType");
    }

    /**
     * Method to load all functionality Strings (key) and actuatorType Strings (value) belonging to a sensor block from a text based config file and create a Map<String, String>.
     *
     * @return Map<String, String> Key: functionality String, Value: actuatorType String.
     */

    public Map<String, String> loadActuatorFunctionalityStringAndSensorTypeStringMap() {
        return scrapeFileAndBuildPropertyStringToPropertyStringMap(actuatorBlock, functionalityProperty, "actuatorType");
    }

    /**
     * Method to load all functionality Strings (key) and serviceMethodToCallForUC33 Strings (value) belonging to a sensor block from a text based config file and create a Map<String, String>.
     *
     * @return Map<String, String> Key: functionality String, Value: serviceMethodToCallForUC33 String.
     */
    public Map<String, String> loadSensorFunctionalityStringAndValueServiceMethodStringMap() {
        return scrapeFileAndBuildPropertyStringToPropertyStringMap(sensorBlock, functionalityProperty, "serviceRepoCall");
    }

    /**
     * Method to load all functionality Strings (key) and unit Strings (value) belonging to a sensor block from a text based config file and create a Map<String, String>.
     *
     * @return Map<String, String> Key: functionality String, Value: unit String.
     */

    public Map<String, String> loadSensorFunctionalityStringAndUnitStringMap() {
        return scrapeFileAndBuildPropertyStringToPropertyStringMap(sensorBlock, functionalityProperty, "unit");
    }

    /**
     * Method used to scrape a given text file and build a Map<String, String>.
     * The Key value of the map is the string passed as propertyTag1 and the Value is the string passed as propertyTag2.
     * When calling method a String mainBlockTag must be provided, so scrapper knows where to start searching for the property tags.
     *
     * @param mainBlockTag String that indicates the main block tag used in config.properties file in ./config/config.properties.
     * @param propertyTag1 String that indicates the first property tag to be found when searching inside the main block.
     * @param propertyTag2 String that indicates the second property tag to be found when searching inside the main block.
     * @return Map<String, String> Key: properties String, Value: properties String.
     */
    private Map<String, String> scrapeFileAndBuildPropertyStringToPropertyStringMap(String mainBlockTag, String propertyTag1, String propertyTag2) {
        try (BufferedReader reader = propertyLoader.getBufferedReader(this.filePath)) {
            String line;
            Map<String, String> propertyToPropertyMap = new HashMap<>();
            mainBlockTag = mainBlockTag + " " + this.openingMainBlockTag;

            while ((line = reader.readLine()) != null) {

                if (line.trim().equals(mainBlockTag)) {
                    String property1 = "";
                    String property2 = "";

                    while ((line = reader.readLine()) != null && !line.trim().equals(this.closingMainBlockTag)) {

                        if (line.contains(propertyTag1)) {

                            String[] parts = line.split(this.propertySplitter);

                            if (parts.length >= 2) {
                                property1 = parts[1].trim();
                            }

                        } else if (line.contains(propertyTag2)) {

                            String[] parts = line.split(this.propertySplitter);

                            if (parts.length >= 2) {
                                property2 = parts[1].trim();

                                if (!property1.isEmpty() && !property2.isEmpty()) {
                                    propertyToPropertyMap.put(property1, property2);
                                    property1 = "";
                                    property2 = "";
                                }
                            }
                        }
                    }
                }
            }
            return propertyToPropertyMap;

        } catch (IOException exception) {
            return new HashMap<>();
        }
    }

    /**
     * Method to load the tolerance property located within the config.properties file
     * @return String that represents the property known as tolerance
     */
    public String loadTolerance() {
        return loadSingleProperty(generalPropertiesBlock, "tolerance");
    }

    /**
     * Method to load the gridPowerMeterID property located within the config.properties file
     * @return String that represents the property gridPowerMeterID that represents the name of the grid power meter
     */
    public String loadGridPowerMeterID () {
        return loadSingleProperty(generalPropertiesBlock, "gridPowerMeterID");
    }

    /**
     * Method to load the gridPowerMeterCadence property located within the config.properties file
     * @return String that represents the property gridPowerMeterCadence that is
     * the interval between the grid power meter readings
     */
    public  String loadGridPowerMeterCadence () {
        return loadSingleProperty(generalPropertiesBlock, "gridPowerMeterCadence");
    }

    /**
     * Method to load the groupNumber property located within the config.properties file
     * @return String that represents the property groupNumber that is
     * the identification of the group responsible for the project
     */
    public  String loadGroupNumber () {
        return loadSingleProperty(generalPropertiesBlock, "groupNumber");
    }

    /**
     * Method to load the WeatherServicesAPI HTTP URL property located within the config.properties file
     * @return String that represents the WeatherServicesAPI HTTP URL
     */
    public  String loadWeatherServicesAPIHTTPURL () {
        return loadSingleProperty(generalPropertiesBlock, "weatherServiceAPIHTTPURL");
    }

    /**
     * Method to load a single property String located in a given main block from the config file.
     *
     * @param mainBlock the main block in the configuration file containing the desired property
     * @param property  the specific property whose value is to be retrieved
     * @return the value of the specified property as a {@link String}, or {@code null} if the property is not found
     */
    private String loadSingleProperty (String mainBlock, String property) {
        try (BufferedReader reader = propertyLoader.getBufferedReader(this.filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(mainBlock)) {
                    while ((line = reader.readLine()) != null && !line.trim().equals(closingMainBlockTag)) {
                        if (line.contains(property)) {
                            String[] parts = line.split(propertySplitter);
                            if (parts.length >= 2) {
                                return parts[1].trim();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
}
