package ua.epam.final_project.command;

import ua.epam.final_project.controller2.ExecutionResult;
import ua.epam.final_project.controller2.SessionRequestContent;

public interface ICommand {

    ExecutionResult execute(SessionRequestContent content);
}
