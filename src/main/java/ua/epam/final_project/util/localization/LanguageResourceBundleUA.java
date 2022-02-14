package ua.epam.final_project.util.localization;

import java.io.Serializable;
import java.util.ResourceBundle;

public class LanguageResourceBundleUA implements Serializable {
    private static ResourceBundle resourceBundle;

    private LanguageResourceBundleUA(){
    }

    public static synchronized ResourceBundle getInstance() {
        if (resourceBundle == null) {
            resourceBundle = CustomPropertiesLoader.extractPropertyFile("localization/locale_ua.properties");
        }
        return resourceBundle;
    }
}
