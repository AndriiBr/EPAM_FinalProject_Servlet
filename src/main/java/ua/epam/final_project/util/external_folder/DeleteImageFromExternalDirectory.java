package ua.epam.final_project.util.external_folder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteImageFromExternalDirectory {

    private DeleteImageFromExternalDirectory() {
    }

    public static void delete(String folderPath, String imagePath) {
        String[] titlePath = imagePath.split("/");
        String fileName = titlePath[titlePath.length - 1];

        Path path = Paths
                .get(folderPath + fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
