package ua.epam.final_project.controller.command.implementation;

import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.controller.command.ICommand;

import java.util.List;

public class OpenLoginPageCommand implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);

        result.setPage(ResourceConfiguration.getInstance().getPage("auth.login"));

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return null;
    }
}
