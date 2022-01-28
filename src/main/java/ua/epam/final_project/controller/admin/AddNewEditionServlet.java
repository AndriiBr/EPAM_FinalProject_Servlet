package ua.epam.final_project.controller.admin;

import ua.epam.final_project.database.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static ua.epam.final_project.util.JSPPathConstant.ADD_NEW_EDITION_PAGE;

@WebServlet(urlPatterns = "/add_new_edition")
@MultipartConfig(location = "E:/Programming/EPAM_JAVA_Autumn_Final_project/external_storage/static/edition_titles")
public class AddNewEditionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ADD_NEW_EDITION_PAGE).forward(req, resp);
        System.out.println("DoGET from AddNewEditionServlet Servlet: " + LocalTime.now());
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
        dbManager.insertNewEdition(editionTitle, imageUUID, price);

        resp.sendRedirect("/");
        System.out.println("DoPOST from AddNewEditionServlet Servlet: " + LocalTime.now());
    }
}
