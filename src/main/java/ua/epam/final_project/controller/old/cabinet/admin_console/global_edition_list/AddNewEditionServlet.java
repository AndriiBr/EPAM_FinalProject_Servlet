package ua.epam.final_project.controller.old.cabinet.admin_console.global_edition_list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.Genre;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = ADD_NEW_EDITION_URL)
@MultipartConfig(location = "E:/Programming/EPAM_JAVA_Autumn_Final_project/external_storage/static/edition_titles")
public class AddNewEditionServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AddNewEditionServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IGenreService genreService = ServiceFactory.getGenreService();

        try {
            List<Genre> genreList = genreService.findAllGenres();
            req.setAttribute("genresList", genreList);
            req.getRequestDispatcher(ADD_NEW_EDITION_PAGE).forward(req, resp);
        } catch (UnknownGenreException | ServletException | IOException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        String editionTitleEn = "";
        String editionTitleUa = "";
        String imageUUID = "";
        String price = "";
        int genreId = 0;

        IEditionService editionService = ServiceFactory.getEditionService();
        IGenreService genreService = ServiceFactory.getGenreService();

        List<Edition> editionList;
        List<Genre> genreList;

        try {
            editionList = editionService.findAllEditions();
            genreList = genreService.findAllGenres();
        } catch (UnknownEditionException | UnknownGenreException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
            return;
        }

        //Read input data from the html-form
        for (Part part : req.getParts()) {
            if (part.getName().equals("titleEn")) {
                InputStream inputStream = part.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                editionTitleEn = new BufferedReader(isr)
                        .lines()
                        .collect(Collectors.joining("\n"));
            } else if (part.getName().equals("titleUa")) {
                InputStream inputStream = part.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                editionTitleUa = new BufferedReader(isr)
                        .lines()
                        .collect(Collectors.joining("\n"));
            } else if (part.getName().equals("file-name")) {
                if (part.getSize() != 0) {
                    imageUUID = UUID.randomUUID() + part.getSubmittedFileName();
                    part.write(imageUUID);
                } else {
                    imageUUID = "no image";
                }
            } else if (part.getName().equals("price")) {
                InputStream inputStream = part.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                price = new BufferedReader(isr)
                        .lines()
                        .collect(Collectors.joining("\n"));
            } else if (part.getName().equals("genre")) {
                InputStream inputStream = part.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                String genreName = new BufferedReader(isr)
                        .lines()
                        .collect(Collectors.joining("\n"));
                genreId = genreList.stream()
                        .filter(x -> x.getGenreEn().equals(genreName) || x.getGenreUa().equals(genreName))
                        .collect(Collectors.toList()).get(0).getId();
            }
        }

        Edition edition = new Edition();
        edition.setTitleEn(editionTitleEn);
        edition.setTitleUa(editionTitleUa);
        edition.setImagePath(getAbsoluteImagePath(imageUUID));
        edition.setPrice(Integer.parseInt(price));
        edition.setGenreId(genreId);

        //Validate input data before insert new edition in DB.
        //Error flag to catch different errors in the user input data:
        //         * 0 - everything correct
        //         * 1 - title is already exist
        String newEditionErrorFlag = validateInputData(edition, editionList);
        session.setAttribute("edition", edition);

        if (newEditionErrorFlag.equals("0")) {
            if (editionService.insertNewEdition(edition)) {
                resp.sendRedirect(ADD_NEW_EDITION_SUCCESS_URL);
            } else {
                resp.sendRedirect(UNKNOWN_ERROR_URL);
            }
        } else {
            resp.sendRedirect(ADD_NEW_EDITION_FAILURE_URL);
        }
    }

    private String validateInputData(Edition edition, List<Edition> editionList) {
        String validatingResult;

        if (validateEditionTitleEn(edition.getTitleEn(), editionList)
                || validateEditionTitleUa(edition.getTitleUa(), editionList)) {
            validatingResult = "1";
        } else {
            validatingResult = "0";
        }
        return validatingResult;
    }

    private boolean validateEditionTitleEn(String titleEn, List<Edition> editionList) {
        return editionList.stream().anyMatch(x -> x.getTitleEn().equals(titleEn));
    }

    private boolean validateEditionTitleUa(String titleUa, List<Edition> editionList) {
        return editionList.stream().anyMatch(x -> x.getTitleUa().equals(titleUa));
    }

    private String getAbsoluteImagePath(String imagePath) {
        String newImagePath = "";

        String imageFolderName = "image_folder.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();

        try (InputStream resourceStream = loader.getResourceAsStream(imageFolderName)) {
            prop.load(resourceStream);

            if (imagePath.equals("no image")) {
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
