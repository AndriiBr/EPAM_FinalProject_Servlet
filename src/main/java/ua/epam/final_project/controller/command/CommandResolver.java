package ua.epam.final_project.controller.command;

import ua.epam.final_project.controller.command.implementation.CommandOpenMainPage;
import ua.epam.final_project.controller.command.implementation.UnknownCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Resolve requested command from HttpServletRequest
 */
public class CommandResolver {

    private static CommandResolver instance = null;
    private final Map<String, ICommand> commandList = new HashMap<>();

    /**
     * Hidden private constructor
     */
    private CommandResolver(){
        commandList.put("main", new CommandOpenMainPage());
    }

    /**
     * Get requested command from the list of commands
     * @param req - HttpServletRequest with parameter 'command'
     * @return command entity
     */
    public ICommand getCommand(HttpServletRequest req) {
        ICommand command = commandList.get(req.getParameter("command"));
        if (command == null) {
            command = new CommandOpenMainPage();
        }

        return command;
    }

    /**
     * Get instance of CommandResolver
     * @return instance
     */
    public static CommandResolver getInstance() {
        if (instance == null) {
            instance = new CommandResolver();
        }

        return instance;
    }
}
