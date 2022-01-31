package ua.epam.final_project.controller.admin;

import ua.epam.final_project.database.DBManager;

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
import java.util.UUID;
import java.util.stream.Collectors;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = ADD_NEW_EDITION_URL)
@MultipartConfig(location = "E:/Programming/EPAM_JAVA_Autumn_Final_project/external_storage/static/edition_titles")
public class AddNewEditionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();

        String currentRole = (String)session.getAttribute("role");

        if (currentRole.equals("1")) {
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

        for (Part part: req.getParts()) {
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
            } else if (part.getName().equals("price")){
                InputStream inputStream = part.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                price = new BufferedReader(isr)
                        .lines()
                        .collect(Collectors.joining("\n"));
            }
        }

        DBManager dbManager = DBManager.getInstance();
        try {
            dbManager.insertNewEdition(editionTitle, imageUUID, price);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(EDITION_LIST_URL);
        System.out.println("AddNewEditionServlet - DoPOST method: " + LocalTime.now());
    }
}
