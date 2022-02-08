package ua.epam.final_project.controller.admin;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;

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
            Map<Integer, String> genreMap = null;
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
        String editionTitle = "";
        String imageUUID = "";
        String price = "";
        int genreId = 0;
        Map<Integer, String> genreMap = null;

        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            daoFactory.beginTransaction();
            genreMap = daoFactory.getGenreDao().findAllGenres();
            daoFactory.commitTransaction();
        } catch (DataBaseNotSupportedException | SQLException | DataBaseConnectionException e) {
            e.printStackTrace();
        }

        for (Part part : req.getParts()) {
            if (part.getName().equals("edition-title")) {
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

        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            daoFactory.beginTransaction();
            daoFactory.getEditionDao().insertNewEdition(editionTitle, imageUUID, genreId, price);
            daoFactory.commitTransaction();
        } catch (SQLException | DataBaseNotSupportedException | DataBaseConnectionException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(MAIN_EDITION_LIST_URL);
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
}
