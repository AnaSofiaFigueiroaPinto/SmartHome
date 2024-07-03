package smarthome.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PropertyLoader} class.
 */
class PropertyLoaderTest {

    private PropertyLoader propertyLoader;

    @BeforeEach
    void setUp() {
        propertyLoader = new PropertyLoader();
    }

    /**
     * Test to verify that a {@link BufferedReader} is successfully created for a valid file path.
     */
    @Test
    void testGetBufferedReaderValidFile() {
        String validFilePath = "config/config.properties";
        assertDoesNotThrow(() -> {
            BufferedReader reader = propertyLoader.getBufferedReader(validFilePath);
            assertNotNull(reader);
            reader.close();
        });
    }

    /**
     * Test to verify that an {@link IOException} is thrown for an invalid file path.
     */
    @Test
    void testGetBufferedReaderInvalidFile() {
        String invalidFilePath = "nonexistent/file.properties";
        Exception exception = assertThrows(IOException.class, () -> {
            propertyLoader.getBufferedReader(invalidFilePath);
        });
        assertEquals("Property file '" + invalidFilePath + "' not found in the classpath", exception.getMessage());
    }

    /**
     * Test to verify that the {@link BufferedReader} reads the correct content from the file.
     */
    @Test
    void testBufferedReaderContent() throws IOException {
        String validFilePath = "config/config.properties";
        try (BufferedReader reader = propertyLoader.getBufferedReader(validFilePath)) {
            assertNotNull(reader);
            String firstLine = reader.readLine();
            assertNotNull(firstLine);
        }
    }
}