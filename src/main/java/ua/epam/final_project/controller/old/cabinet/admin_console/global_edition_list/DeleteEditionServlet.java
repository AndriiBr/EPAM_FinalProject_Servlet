package ua.epam.final_project.controller.old.cabinet.admin_console.global_edition_list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.util.DeleteImageFromExternalDirectory;
import ua.epam.final_project.entity.Edition;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = DELETE_EDITION_URL)
public class DeleteEditionServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DeleteEditionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int editionId;
        try {
            editionId = Integer.parseInt(req.getParameter("edition_id"));
        } catch (NumberFormatException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
            return;
        }

        IEditionService editionService = ServiceFactory.getEditionService();

        try {
            Edition edition = editionService.findEditionById(editionId);
            if (editionService.deleteEdition(edition)) {
                DeleteImageFromExternalDirectory.delete(edition.getImagePath());
                resp.sendRedirect(ADMIN_EDITION_LIST_URL);
            } else {
                resp.sendRedirect(UNKNOWN_ERROR_URL);
            }
        } catch (UnknownEditionException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}
