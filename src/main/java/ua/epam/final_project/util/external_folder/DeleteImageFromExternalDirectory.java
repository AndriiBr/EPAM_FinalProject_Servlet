package ua.epam.final_project.util.external_folder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.controller.login.UserRegistrationServlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteImageFromExternalDirectory {
    private static final Logger logger = LogManager.getLogger(DeleteImageFromExternalDirectory.class);

    private DeleteImageFromExternalDirectory() {
    }

    public static boolean delete(String folderPath, String imagePath) {
        String[] titlePath = imagePath.split("/");
        String fileName = titlePath[titlePath.length - 1];

        Path path = Paths.get(folderPath + fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            logger.warn(e);
            return false;
        }
        return true;
    }
}
