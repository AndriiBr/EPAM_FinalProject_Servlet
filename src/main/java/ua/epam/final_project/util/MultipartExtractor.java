package ua.epam.final_project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.Genre;
import ua.epam.final_project.exception.MultipartFormException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class to work with enctype="multipart/form-data" from JSP form.
 * Be sure to use @MultipartConfig annotation in your responsible ServletController
 */
public class MultipartExtractor {

    private static final Logger logger = LogManager.getLogger(MultipartExtractor.class);

    private MultipartExtractor() {
    }

    /**
     * Extract single values from JSP form with enctype="multipart/form-data" using HttpServletRequest.
     * @param content - Repository with HttpServletRequest
     * @return - map with extracted values
     * @throws MultipartFormException - exception work as a trigger to interrupt following activities in Command
     */
    public static Map<String, String> extractMultipart(SessionRequestContent content) throws MultipartFormException {
        Map<String, String> fieldMap = new HashMap<>();

        HttpServletRequest req = content.getReq();
        try {
            Collection<Part> parts = req.getParts();

            if (parts != null) {
                for (Part part : parts) {
                    if (part.getName().equals("title_en")) {
                        fieldMap.put("title_en", extractStringPart(part));
                    } else if (part.getName().equals("title_ua")) {
                        fieldMap.put("title_ua", extractStringPart(part));
                    } else if (part.getName().equals("text_en")) {
                        fieldMap.put("text_en", extractStringPart(part));
                    } else if (part.getName().equals("text_ua")) {
                        fieldMap.put("text_ua", extractStringPart(part));
                    } else if (part.getName().equals("price")) {
                        fieldMap.put("price", extractStringPart(part));
                    } else if (part.getName().equals("genre")) {
                        fieldMap.put("genre", String.valueOf(extractGenreId(part)));
                    } else if (part.getName().equals("file-name")) {
                        fieldMap.put("file-name", extractFile(part));
                    }
                }
            }
        } catch (ServletException | UnknownGenreException | IOException e) {
            logger.error(e);
            throw new MultipartFormException();
        }
        return fieldMap;
    }

    private static String extractStringPart(Part part) throws IOException {
        InputStream inputStream = part.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        return new BufferedReader(isr)
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static String extractFile(Part part) throws IOException {
        String imagePath;
        if (part.getSize() != 0) {
            imagePath = UUID.randomUUID() + part.getSubmittedFileName();
            part.write(imagePath);
        } else {
            imagePath = "no image";
        }
        return imagePath;
    }

    private static int extractGenreId(Part part) throws UnknownGenreException, IOException {
        IGenreService genreService = ServiceFactory.getGenreService();
        List<Genre> genreListDb = genreService.findAllGenres();
        InputStream inputStream = part.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        String genreName = new BufferedReader(isr)
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
        return genreListDb.stream()
                .filter(x -> x.getGenreEn().equals(genreName) || x.getGenreUa().equals(genreName))
                .collect(Collectors.toList()).get(0).getId();

    }

    public static String getAbsoluteImagePath(String imagePath) {
        String newImagePath = "";

        String imageFolderName = "image_folder.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();

        try (InputStream resourceStream = loader.getResourceAsStream(imageFolderName)) {
            prop.load(resourceStream);

            if (imagePath.equals("no image") || imagePath.equals("")) {
                newImagePath = imagePath;
            } else {
                newImagePath = prop.getProperty("title_image_folder") + imagePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImagePath;
    }
}
