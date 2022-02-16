package ua.epam.final_project.controller2;

import ua.epam.final_project.command.ICommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(value = "/")
public class ControllerServlet extends HttpServlet {

    //    private static final Logger log = Logger.getLogger(ControllerServlet.class);
    private final CommandResolver commandResolver = CommandResolver.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionRequestContent content = new SessionRequestContent(req);
//        log.info(content);
        ICommand command = commandResolver.getCommand(req);
        ExecutionResult result = command.execute(content);
        if (result.isInvalidated())
            req.getSession(false).invalidate();
        result.updateRequest(req);

        if (result.getDirection() == Direction.FORWARD) {
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
        } else if (result.getDirection() == Direction.REDIRECT) {
            resp.sendRedirect(result.getPage());
        }
    }
}
