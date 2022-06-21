package ua.epam.final_project.controller.command;

import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;

/**
 * Interface for all commands
 */
public interface ICommand {
    /**
     *
     * @param content - contains session and request parameters and attributes
     * @return result of command execution in ExecutionResult object.
     */
    ExecutionResult execute(SessionRequestContent content);

}
