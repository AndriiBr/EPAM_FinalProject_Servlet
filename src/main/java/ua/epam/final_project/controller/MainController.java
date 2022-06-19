package ua.epam.final_project.controller;

import ua.epam.final_project.controller.command.CommandResolver;
import ua.epam.final_project.controller.command.ICommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainController extends HttpServlet {

    private final CommandResolver commandResolver = CommandResolver.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionRequestContent content = new SessionRequestContent(req);
        ICommand command = commandResolver.getCommand(req);
        ExecutionResult result = command.execute(content);

        if (result.isInvalidated()) {
            req.getSession(false).invalidate();
        }

        result.updateRequest(req);

        if (result.getDirection() == Direction.FORWARD) {
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
        } else if (result.getDirection() == Direction.REDIRECT) {
            resp.sendRedirect(result.getPage());
        }
    }
}
