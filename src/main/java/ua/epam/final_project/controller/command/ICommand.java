package ua.epam.final_project.controller.command;

import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;

import java.util.List;

/**
 * Interface for all commands
 */
public interface ICommand {

    /**
     * Create ExecutionResult statement based on provided parameters, attributes and command
     * @param content - contains session and request parameters and attributes
     * @return result of command execution in ExecutionResult object.
     */
    ExecutionResult execute(SessionRequestContent content);

    /**
     * Provide list with allowed access levels
     * You can leave it null - that will allow access to everyone
     * @return list with access levels
     */
    List<AccessLevel> getAccessLevelList();
}
