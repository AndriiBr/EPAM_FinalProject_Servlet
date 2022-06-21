package ua.epam.final_project.controller.util;

import ua.epam.final_project.controller.command.CommandResolver;
import ua.epam.final_project.controller.command.ICommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandHandler {

    private static final CommandResolver commandResolver = CommandResolver.getInstance();

    private CommandHandler() {}

    public static void handleGetRequest (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ICommand command = commandResolver.initGetCommand(req);
        executePreparedCommand(req, resp, command);
    }

    public static void handlePostRequest (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ICommand command = commandResolver.initPostCommand(req);
        executePreparedCommand(req, resp, command);
    }

    private static void executePreparedCommand(HttpServletRequest req, HttpServletResponse resp, ICommand command) throws ServletException, IOException {
        SessionRequestContent content = new SessionRequestContent(req);
        ExecutionResult result = command.execute(content);
        result.updateRequest(req);

        if (result.getDirection() == Direction.FORWARD) {
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
        } else if (result.getDirection() == Direction.REDIRECT) {
            resp.sendRedirect(result.getRedirectUrl());
        }
    }
}
