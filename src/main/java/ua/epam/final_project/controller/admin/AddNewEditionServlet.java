package ua.epam.final_project.controller.admin;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
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

@WebServlet(urlPatterns = ADD_NEW_EDITION_URL)
@MultipartConfig(location = "E:/Programming/EPAM_JAVA_Autumn_Final_project/external_storage/static/edition_titles")
public class AddNewEditionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        String currentRole = (String) session.getAttribute("role");

        if (currentRole.equals("1")) {
            List<String> genres = null;
            Map<Integer, String> genreMap;
            try {
                DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
                daoFactory.beginTransaction();
                genreMap = daoFactory.getGenreDao().findAllGenres();
                daoFactory.commitTransaction();
                genres = new ArrayList<>(genreMap.values());
            } catch (DataBaseNotSupportedException | SQLException | DataBaseConnectionException e) {
                e.printStackTrace();
            }
            req.setAttribute("genresList", genres);
            req.getRequestDispatcher(ADD_NEW_EDITION_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(ERROR_404_PAGE);
        }

        System.out.println("AddNewEditionServlet - DoGET method: " + LocalTime.now());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        DaoFactory daoFactory;

        String editionTitle = "";
        String imageUUID = "";
        String price = "";
        int genreId = 0;
        Edition edition = null;

        Map<Integer, String> genreMap = null;

        //Error flag to catch different errors in the user input data:
        //         * 0 - everything correct
        //         * 1 - title is already exist
        String newEditionErrorFlag = "0";
        List<Edition> editionList = null;

        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            daoFactory.beginTransaction();
            genreMap = daoFactory.getGenreDao().findAllGenres();
            editionList = daoFactory.getEditionDao().findAllEditions();
            daoFactory.commitTransaction();
        } catch (DataBaseNotSupportedException | SQLException | DataBaseConnectionException e) {
            e.printStackTrace();
        }

        //Read input data from the html-form
        for (Part part : req.getParts()) {
            if (part.getName().equals("title")) {
                InputStream inputStream = part.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                editionTitle = new BufferedReader(isr)
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
                genreId = getKeyByValue(genreMap, genreName);
            }
        }

        //Validate input data before insert new edition in DB.
        String finalEditionTitle = editionTitle;
        if (editionList != null && editionList.stream().anyMatch(x -> x.getTitle().equals(finalEditionTitle))) {
            newEditionErrorFlag = "1";
            session.setAttribute("editionTitle", finalEditionTitle);
        }
        session.setAttribute("editionErrorFlag", newEditionErrorFlag);


        if (newEditionErrorFlag.equals("0")) {
            edition = new Edition();
            edition.setTitle(editionTitle);
            edition.setImagePath(getAbsoluteImagePath(imageUUID));
            edition.setGenreId(genreId);
            edition.setPrice(Integer.parseInt(price));
            session.setAttribute("newEdition", edition);
        }

        if (edition != null) {
            try {
                daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
                daoFactory.beginTransaction();
                daoFactory.getEditionDao().insertNewEdition(edition);
                daoFactory.commitTransaction();
                resp.sendRedirect(ADD_NEW_EDITION_SUCCESS_URL);
            } catch (SQLException | DataBaseNotSupportedException | DataBaseConnectionException e) {
                e.printStackTrace();
            }
        } else {
            resp.sendRedirect(ADD_NEW_EDITION_FAILURE_URL);
        }

        System.out.println("AddNewEditionServlet - DoPOST method: " + LocalTime.now());
    }

    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
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
