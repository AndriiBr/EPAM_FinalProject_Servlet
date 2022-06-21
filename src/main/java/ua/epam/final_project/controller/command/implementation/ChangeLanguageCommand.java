package ua.epam.final_project.controller.command.implementation;

import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.controller.command.ICommand;

public class ChangeLanguageCommand implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ResourceConfiguration configuration = ResourceConfiguration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.REDIRECT);

        return null;
    }
}
