package ua.epam.final_project.controller.command.implementation;

import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.controller.command.ICommand;

public class UnknownCommand implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        return null;
    }
}
