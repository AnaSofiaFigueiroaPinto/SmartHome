package smarthome.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigScraperTest {
    private static final String CONFIG_FILE_PATH = "config/config.properties";
    private static final String GENERAL_FILE_PATH = "config/general.properties";
    private static final String INVALID_FILE_PATH = "Not a valid config file";

    private PropertyLoader propertyLoader;

    @BeforeEach
    void setUp() {
        propertyLoader = new PropertyLoader();
    }

    /**
     * Sucessful construction of configScraper object.
     */
    @Test
    void successfulConfigScraperConstruction() {
        assertDoesNotThrow(() -> new ConfigScraper(CONFIG_FILE_PATH, propertyLoader));
    }

    /**
     * Fail construction of configScraper object due to null String
     */
    @Test
    void failConfigScraperConstructionNullPath() {
        assertThrows(IllegalArgumentException.class, () -> new ConfigScraper(null, propertyLoader));
    }

    /**
     * Fail construction of configScraper object due to empty String
     */
    @Test
    void failConfigScraperConstructionEmptyPath() {
        assertThrows(IllegalArgumentException.class, () -> new ConfigScraper("", propertyLoader));
    }

    /**
     * Successful loadActuatorClassNames method when correct path is provided, see if the returned list isn't empty
     */
    @Test
    void successfulLoadSensorFunctionalityList() {
        ConfigScraper configScraper = new ConfigScraper(CONFIG_FILE_PATH, propertyLoader);
        List<String> returnList = configScraper.loadSensorFunctionalityList();
        assertTrue(!returnList.isEmpty());
    }

    /**
     * Test case to verify the behavior when the method fails to load the sensor functionality to unit list due to an empty config file.
     */
    @Test
    void failToLoadSensorFunctionalityList() {
        ConfigScraper configScraper = new ConfigScraper(INVALID_FILE_PATH, propertyLoader);
        List<String> returnList = configScraper.loadSensorFunctionalityList();
        assertTrue(returnList.isEmpty());
    }

    /**
     * Successful loadActuatorFunctionalityList method when correct path is provided, see if the returned list isn't empty
     */
    @Test
    void successfulLoadActuatorFunctionalityList() {
        ConfigScraper configScraper = new ConfigScraper(CONFIG_FILE_PATH, propertyLoader);
        List<String> returnList = configScraper.loadActuatorFunctionalityList();
        assertTrue(!returnList.isEmpty());
    }

    /**
     * Test case to verify the behavior when the method fails to load the actuator functionality to unit list due to an empty config file.
     */
    @Test
    void failToLoadActuatorFunctionalityList() {
        ConfigScraper configScraper = new ConfigScraper(INVALID_FILE_PATH, propertyLoader);
        List<String> returnList = configScraper.loadActuatorFunctionalityList();
        assertTrue(returnList.isEmpty());
    }

    /**
     * Test case to verify the successful loading of the sensor functionality to sensor type map.
     */
    @Test
    void successfulLoadSensorFunctionalityStringAndSensorTypeStringMap() {
        ConfigScraper configScraper = new ConfigScraper(CONFIG_FILE_PATH, propertyLoader);
        Map<String, String> sensorFunctionalityList = configScraper.loadSensorFunctionalityStringAndSensorTypeStringMap();
        assertTrue(!sensorFunctionalityList.isEmpty());
    }

    /**
     * Successful loadSensorFunctionalityStringAndValueRepoMethodStringMap method when correct path is provided, see if the returned map isn't empty
     */
    @Test
    void sucessfulLoadSensorFuncToSensorTypeMap() {
        ConfigScraper configScraper = new ConfigScraper(CONFIG_FILE_PATH, propertyLoader);
        Map<String, String> sensorFunctionalityList = configScraper.loadSensorFunctionalityStringAndSensorTypeStringMap();
        assertTrue(!sensorFunctionalityList.isEmpty());
    }

    /**
     * Successful loadSensorFunctionalityStringAndValueRepoMethodStringMap method when correct path is provided, see if the returned map isn't empty
     */
    @Test
    void successfulLoadSensorFuncToMethodNameMap() {
        ConfigScraper configScraper = new ConfigScraper(CONFIG_FILE_PATH, propertyLoader);
        Map<String, String> sensorFuncToMethodNameMap = configScraper.loadSensorFunctionalityStringAndValueServiceMethodStringMap();
        assertTrue(!sensorFuncToMethodNameMap.isEmpty());
    }

    /**
     * Successful loadSensorFunctionalityStringAndUnitStringMap method when correct path is provided, see if the returned map isn't empty
     */
    @Test
    void successfulLoadSensorFuncToUnitMap() {
        ConfigScraper configScraper = new ConfigScraper(CONFIG_FILE_PATH, propertyLoader);
        Map<String, String> sensorFuncToUnitMap = configScraper.loadSensorFunctionalityStringAndUnitStringMap();
        assertTrue(!sensorFuncToUnitMap.isEmpty());
    }

    /**
     * Test case to verify the behavior when the method fails to load the sensor functionality to unit map due to an invalid config path.
     */
    @Test
    void failToLoadSensorFuncToUnitMap() {
        ConfigScraper configScraper = new ConfigScraper(INVALID_FILE_PATH, propertyLoader);
        Map<String, String> sensorFuncToUnitMap = configScraper.loadSensorFunctionalityStringAndUnitStringMap();
        assertTrue(sensorFuncToUnitMap.isEmpty());
    }

    /**
     * Test case to verify the successful loading of the tolerance string from the configuration file.
     */
    @Test
    void successfullyLoadToleranceString() {
        ConfigScraper configScraper = new ConfigScraper(GENERAL_FILE_PATH, propertyLoader);
        String expected = "300000";
        String result = configScraper.loadTolerance();
        assertEquals(expected, result);
    }

    /**
     * Test case to fail to load the tolerance string from an invalid config file.
     */
    @Test
    void failToLoadToleranceStringNotAConfigFile() {
        ConfigScraper configScraper = new ConfigScraper(INVALID_FILE_PATH, propertyLoader);
        String result = configScraper.loadTolerance();
        assertNull(result);
    }

    /**
     * Test case to fail to load the tolerance string from config file without a tolerance field.
     */
    @Test
    void failToLoadToleranceNoToleranceString() {
        ConfigScraper configScraper = new ConfigScraper(CONFIG_FILE_PATH, propertyLoader);
        String result = configScraper.loadTolerance();
        assertNull(result);
    }

    /**
     * Test to verify that GridPowerMeterID is successfully loaded when given the correct file path
     */
    @Test
    void successfullyLoadGridPowerMeterID () {
        ConfigScraper configScraper = new ConfigScraper(GENERAL_FILE_PATH, propertyLoader);
        String expected = "Grid Power Meter";
        String result = configScraper.loadGridPowerMeterID();
        assertEquals(expected, result);
    }

    /**
     * Test to verify that GridPowerMeterCadence is successfully loaded when given the correct file path
     */
    @Test
    void successfullyLoadGridPowerCadence () {
        ConfigScraper configScraper = new ConfigScraper(GENERAL_FILE_PATH, propertyLoader);
        String expected = "900000";
        String result = configScraper.loadGridPowerMeterCadence();
        assertEquals(expected, result);
    }

    /**
     * Test to see if attempting to load GridPowerMeterID when given a non-existing file path returns null
     */
    @Test
    void failLoadGridPowerMeterIDInvalidFile () {
        ConfigScraper configScraper = new ConfigScraper(INVALID_FILE_PATH, propertyLoader);
        String expected = "Grid Power Meter";
        String result = configScraper.loadGridPowerMeterID();
        assertNotEquals(expected, result);
    }

    /**
     * Test to see if attempting to load GridPowerCadence when given a non-existing file path returns null
     */
    @Test
    void failLoadGridPowerCadenceInvalidFile () {
        ConfigScraper configScraper = new ConfigScraper(INVALID_FILE_PATH, propertyLoader);
        String expected = "900000";
        String result = configScraper.loadGridPowerMeterCadence();
        assertNotEquals(expected, result);
    }

    /**
     * Test to see if attempting to load GridPowerMeterID when given a file that has
     * no gridPowerMeterID properties returns null
     */
    @Test
    void failLoadGridPowerMeterIDNotInFile () {
        ConfigScraper configScraper = new ConfigScraper(CONFIG_FILE_PATH, propertyLoader);
        String expected = "Grid Power Meter";
        String result = configScraper.loadGridPowerMeterID();
        assertNotEquals(expected, result);
    }

    /**
     * Test to see if attempting to load GridPowerCadence when given a file that has
     * no gridPowerMeterID properties returns null
     */
    @Test
    void failLoadGridPowerCadenceNotInFile () {
        ConfigScraper configScraper = new ConfigScraper(CONFIG_FILE_PATH, propertyLoader);
        String expected = "900000";
        String result = configScraper.loadGridPowerMeterCadence();
        assertNotEquals(expected, result);
    }

    /**
     * Test to verify that GroupNumber is successfully loaded when given the correct file path
     */
    @Test
    void successfullyLoadGroupNumber () {
        ConfigScraper configScraper = new ConfigScraper(GENERAL_FILE_PATH, propertyLoader);
        String expected = "5";
        String result = configScraper.loadGroupNumber();
        assertEquals(expected, result);
    }

    /**
     * Test to see if attempting to load GroupNumber when given a non-existing file path returns null
     */
    @Test
    void failLoadGroupNumber () {
        ConfigScraper configScraper = new ConfigScraper(INVALID_FILE_PATH, propertyLoader);
        String expected = "5";
        String result = configScraper.loadGroupNumber();
        assertNotEquals(expected, result);
    }

    /**
     * Test to verify that WeatherServicesAPI HTTP URL is successfully loaded when given the correct file path
     */
    @Test
    void successfullyLoadWeatherServicesAPIHTTPURL () {
        ConfigScraper configScraper = new ConfigScraper(GENERAL_FILE_PATH, propertyLoader);
        String expected = "http://10.9.24.170:8080";
        String result = configScraper.loadWeatherServicesAPIHTTPURL();
        assertEquals(expected, result);
    }

    /**
     * Test to see if attempting to load WeatherServicesAPI HTTP URL when given a non-existing file path returns null
     */
    @Test
    void failLoadWeatherServicesAPIHTTPURL () {
        ConfigScraper configScraper = new ConfigScraper(INVALID_FILE_PATH, propertyLoader);
        String expected = "http://10.9.24.170:8080";
        String result = configScraper.loadWeatherServicesAPIHTTPURL();
        assertNotEquals(expected, result);
    }
}