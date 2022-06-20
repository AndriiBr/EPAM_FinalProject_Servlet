package ua.epam.final_project.config;

import java.util.ResourceBundle;

public class ResourceConfiguration {

    private static ResourceConfiguration configurationInstance;
    private final ResourceBundle pages;


    private ResourceConfiguration() {
        pages = ResourceBundle.getBundle("pages");
    }

    public static ResourceConfiguration getInstance() {
        if (configurationInstance == null) {
            return new ResourceConfiguration();
        }
        return configurationInstance;
    }

    public String getPage(String pageName) {
        return pages.getString(pageName);
    }
}
