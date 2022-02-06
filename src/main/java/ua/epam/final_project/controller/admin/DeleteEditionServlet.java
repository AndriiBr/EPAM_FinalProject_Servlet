package ua.epam.final_project.controller.admin;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.DeleteImageFromExternalDirectory;
import ua.epam.final_project.util.entity.Edition;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Properties;

import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = DELETE_EDITION_URL)
public class DeleteEditionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String editionTitle = req.getParameter("edition_title");

        DaoFactory daoFactory = null;

        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
        } catch (DataBaseNotSupportedException | DataBaseConnectionException e) {
            e.printStackTrace();
        }

        Edition edition = null;

        try {
            assert daoFactory != null;
            edition = daoFactory.getEditionDao().getEditionByTitle(editionTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (edition != null) {
            String[] titlePath = edition.getImagePath().split("/");
            String fileName = titlePath[titlePath.length-1];

            //delete edition from DB
            //delete edition title image from external folder
            try {
                daoFactory.getEditionDao().deleteEditionByTitle(editionTitle);
                DeleteImageFromExternalDirectory.delete(getExternalFolderPath(), fileName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect(EDITION_LIST_URL);

        System.out.println("DeleteEditionServlet - doPOST method: " + LocalTime.now());
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
}
