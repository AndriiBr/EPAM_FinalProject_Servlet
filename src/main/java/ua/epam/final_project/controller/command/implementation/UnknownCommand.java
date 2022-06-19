package ua.epam.final_project.controller.command.implementation;

import ua.epam.final_project.controller.ExecutionResult;
import ua.epam.final_project.controller.SessionRequestContent;
import ua.epam.final_project.controller.command.ICommand;

public class UnknownCommand implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        return null;
    }
}
