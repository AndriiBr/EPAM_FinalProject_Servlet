package ua.epam.final_project.util.localization;

import java.io.Serializable;
import java.util.ResourceBundle;

public class LanguageResourceBundleEN implements Serializable {
    private static ResourceBundle resourceBundle;

    private LanguageResourceBundleEN(){
    }

    public static synchronized ResourceBundle getInstance() {
        if (resourceBundle == null) {
            resourceBundle = CustomPropertiesLoader.extractPropertyFile("localization/locale.properties");
        }
        return resourceBundle;
    }
}
