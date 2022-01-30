package ua.epam.final_project.controller.admin;

import ua.epam.final_project.database.DBManager;
import ua.epam.final_project.util.ApacheDeleteImage;
import ua.epam.final_project.util.edition.Edition;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Properties;


@WebServlet(urlPatterns = "/cabinet/delete_edition")
public class DeleteEditionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String editionTitle = req.getParameter("edition_title");
        DBManager dbManager = DBManager.getInstance();

        Edition edition = null;

        try {
            edition = dbManager.getEditionByTitle(editionTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (edition != null) {
            String[] titlePath = edition.getImagePath().split("/");
            String fileName = titlePath[titlePath.length-1];

            //delete edition from DB
            //delete edition title image from external folder
            try {
                dbManager.deleteEditionByTitle(editionTitle);
                ApacheDeleteImage.delete(getExternalFolderPath(), fileName);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        resp.sendRedirect("/cabinet/edition_list");

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
