package ua.epam.final_project.controller.cabinet_page.admin_console.global_edition_list_page;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.Genre;
import ua.epam.final_project.util.external_folder.DeleteImageFromExternalDirectory;
import ua.epam.final_project.util.entity.Edition;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = EDIT_EDITION_URL)
@MultipartConfig(location = "E:/Programming/EPAM_JAVA_Autumn_Final_project/external_storage/static/edition_titles")
public class EditEditionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int editionId = Integer.parseInt(req.getParameter("edit_edition_id"));

        Edition edition = null;
        List<Genre> genreList = null;

        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            genreList = daoFactory.getGenreDao().findAllGenres();
            edition = daoFactory.getEditionDao().getEditionByTitle(editionId);
        } catch (DataBaseNotSupportedException | SQLException e) {
            e.printStackTrace();
        }

        if (edition != null) {
            session.setAttribute("editionEntity", edition);
            req.setAttribute("genresList", genreList);
            req.getRequestDispatcher(EDIT_EDITION_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(MAIN_URL);
        }

        System.out.println("EditEditionServlet - DoGET method: " + LocalTime.now());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DaoFactory daoFactory = null;

        String editionTitleEn = "";
        String editionTitleUa = "";
        String imageUUID = "";
        String price = "";
        int genreId = 0;
        Edition edition = (Edition) session.getAttribute("editionEntity");
        List<Genre> genreList = null;

        //Error flag to catch different errors in the user input data:
        //         * 0 - everything correct
        //         * 1 - title is already exist
        String newEditionErrorFlag = "0";
        List<Edition> editionList = null;

        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            genreList = daoFactory.getGenreDao().findAllGenres();
            editionList = daoFactory.getEditionDao().findAllEditions();
        } catch (DataBaseNotSupportedException | SQLException e) {
            e.printStackTrace();
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
                    DeleteImageFromExternalDirectory.delete(getExternalFolderPath(), edition.getImagePath());
                    imageUUID = UUID.randomUUID() + part.getSubmittedFileName();
                    part.write(imageUUID);
                    edition.setImagePath(getAbsoluteImagePath(imageUUID));
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

        edition.setTitleEn(editionTitleEn);
        edition.setTitleUa(editionTitleUa);
        edition.setPrice(Integer.parseInt(price));
        edition.setGenreId(genreId);

        //Validate input data before insert new edition in DB.
        String finalEditionTitleEn = editionTitleEn;
        String finalEditionTitleUa = editionTitleUa;
        if (editionList != null &&
                (editionList.stream().anyMatch(x -> x.getTitleEn().equals(finalEditionTitleEn)) ||
                        editionList.stream().anyMatch(x -> x.getTitleUa().equals(finalEditionTitleUa)))) {
            newEditionErrorFlag = "1";

        }
        session.setAttribute("edition", edition);

        if (newEditionErrorFlag.equals("0") && daoFactory != null) {
            try {
                daoFactory.beginTransaction();
                daoFactory.getEditionDao().updateEdition(edition);
                daoFactory.commitTransaction();
                resp.sendRedirect(ADD_NEW_EDITION_SUCCESS_URL);
            } catch (DataBaseConnectionException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            resp.sendRedirect(ADD_NEW_EDITION_FAILURE_URL);
        }


        System.out.println("EditEditionServlet - DoGET method: " + LocalTime.now());
    }

    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String getExternalFolderPath() {
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
