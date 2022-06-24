package ua.epam.final_project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DeleteImageFromExternalDirectory {
    private static final Logger logger = LogManager.getLogger(DeleteImageFromExternalDirectory.class);

    private DeleteImageFromExternalDirectory() {
    }

    public static boolean delete(String imagePath) {
        if (imagePath == null || imagePath.equals("") || imagePath.equals("no image")) {
            return false;
        }

        String[] titlePath = imagePath.split("/");
        String fileName = titlePath[titlePath.length - 1];

        Path path = Paths.get(getExternalFolderPath() + fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            logger.warn(e);
            return false;
        }
        return true;
    }

    private static String getExternalFolderPath() {
        String imageFolderName = "image_folder.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();

        String folderPath;

        try (InputStream resourceStream = loader.getResourceAsStream(imageFolderName)) {
            prop.load(resourceStream);
            folderPath = prop.getProperty("title_image_folder_path");
            return folderPath;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
