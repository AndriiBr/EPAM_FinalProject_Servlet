package ua.epam.final_project.config;

import java.util.ResourceBundle;

public class ResourceConfiguration {

    private final ResourceBundle pages;


    public ResourceConfiguration() {
        pages = ResourceBundle.getBundle("pages");
    }

    public String getPage(String pageName) {
        return pages.getString(pageName);
    }
}
