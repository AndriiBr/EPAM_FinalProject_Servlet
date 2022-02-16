package ua.epam.final_project.controller2;

import ua.epam.final_project.command.ICommand;
import ua.epam.final_project.command.implementation.CommandOpenLoginPage;
import ua.epam.final_project.command.implementation.CommandOpenMainPage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static ua.epam.final_project.util.MainPathConstant.MAIN_URL;

public class CommandResolver {

    private static CommandResolver instance = null;
    Map<String, ICommand> commands = new HashMap<>();

    private CommandResolver() {
        commands.put("main", new CommandOpenMainPage());
        commands.put("login", new CommandOpenLoginPage());
        //Admin commands

    }

    public ICommand getCommand(HttpServletRequest request) {
//        ICommand command = commands.get(request.getParameter("command"));
        String url = request.getRequestURL().toString().replace(MAIN_URL, "");
        ICommand command;
        //To catch welcome page
        if (url.length() == 0) {
            command = commands.get("/");
        } else {
            command = commands.get(url);
        }
        return command;
    }

    public static synchronized CommandResolver getInstance() {
        if (instance == null)
            instance = new CommandResolver();
        return instance;
    }
}
