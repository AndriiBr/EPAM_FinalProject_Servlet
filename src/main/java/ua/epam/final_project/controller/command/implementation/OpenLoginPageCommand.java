package ua.epam.final_project.controller.command.implementation;

import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.Direction;
import ua.epam.final_project.controller.ExecutionResult;
import ua.epam.final_project.controller.SessionRequestContent;
import ua.epam.final_project.controller.command.ICommand;

public class OpenLoginPageCommand implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);

        result.setPage(ResourceConfiguration.getInstance().getPage("auth.login"));

        return result;
    }
}
