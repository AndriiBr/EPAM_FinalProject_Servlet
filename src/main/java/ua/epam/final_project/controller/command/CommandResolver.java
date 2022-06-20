package ua.epam.final_project.controller.command;

import ua.epam.final_project.controller.command.implementation.OpenLoginPageCommand;
import ua.epam.final_project.controller.command.implementation.OpenMainPageCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        commandList.put("main", new OpenMainPageCommand());
        commandList.put("login", new OpenLoginPageCommand());
    }

    /**
     * Get requested command from the list of commands
     * @param req - HttpServletRequest with parameter 'command'
     * @return command entity
     */
    public ICommand getCommand(HttpServletRequest req) {
        HttpSession session = req.getSession();
        CommandHistory commandHistory = (CommandHistory) session.getAttribute("commandHistory");

        if (commandHistory == null) {
            commandHistory = new CommandHistory();
            commandHistory.addNewCommand("main");
            session.setAttribute("commandHistory", commandHistory);
        }

        String reqCommand = req.getParameter("command");

        if (reqCommand == null) {
            reqCommand = commandHistory.getLastCommand();
        }

        ICommand command = commandList.get(reqCommand);
        if (command == null) {
            throw new RuntimeException();
        }
        commandHistory.addNewCommand(reqCommand);

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
