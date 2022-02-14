package ua.epam.final_project.util.localization;

import java.util.ResourceBundle;

public interface LocalizationFactory {

    static ResourceBundle getLanguageResourceBundle(String language) {
        ResourceBundle localization = null;
        switch (language) {
            case "ua":
                localization = LanguageResourceBundleUA.getInstance();
                break;
            case "en":
                localization = LanguageResourceBundleEN.getInstance();
        }
        return localization;
    }
}
