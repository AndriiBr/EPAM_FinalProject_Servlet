package ua.epam.final_project.controller.command.implementation;

import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;

import java.util.Collections;
import java.util.List;

public class OpenRegistrationSuccessPageCommand implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);

        result.setPage(ResourceConfiguration.getInstance().getPage("auth.registration.success"));

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.USER);
    }
}
