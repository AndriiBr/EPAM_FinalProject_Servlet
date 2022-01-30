package ua.epam.final_project.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApacheDeleteImage {

    private ApacheDeleteImage(){}

    public static void delete(String folderPath, String fileName) {
        Path path = Paths
                .get(folderPath + fileName);
        try{
            Files.deleteIfExists(path);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
