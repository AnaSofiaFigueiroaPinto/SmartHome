package smarthome.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Component responsible for loading properties and files from the classpath.
 */
@Component
public class PropertyLoader {

    /**
     * Provides a {@link BufferedReader} for reading the contents of a specified file in the classpath.
     *
     * @param filePath the path to the file within the classpath
     * @return a {@link BufferedReader} for reading the contents of the file
     * @throws IOException if an I/O error occurs or the file is not found
     */
    public BufferedReader getBufferedReader(String filePath) throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(filePath);
        if (input == null) {
            throw new IOException("Property file '" + filePath + "' not found in the classpath");
        }
        return new BufferedReader(new InputStreamReader(input));
    }
}
