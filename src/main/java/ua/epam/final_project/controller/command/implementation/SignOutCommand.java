package ua.epam.final_project.controller.command.implementation;

import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;

import java.util.Arrays;
import java.util.List;

public class SignOutCommand implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);

        result.invalidateSession();

        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("shop.edition_list"));

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER, AccessLevel.BLOCKED);
    }
}
