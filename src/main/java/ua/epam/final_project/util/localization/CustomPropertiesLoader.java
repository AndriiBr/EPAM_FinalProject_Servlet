package ua.epam.final_project.util.localization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class CustomPropertiesLoader {

    private CustomPropertiesLoader(){
    }

    /**
     * Reads properties file and extract it as ResourceBundle
     * @param fileName - properties file
     * @return ResourceBundle
     */
    static ResourceBundle extractPropertyFile(String fileName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(fileName);
        ResourceBundle resourceBundle = null;

        try (InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            resourceBundle = new PropertyResourceBundle(isr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourceBundle;
    }
}
