package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;

import java.util.List;

public class UnknownErrorCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(UnknownErrorCommand.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);

        result.setPage(ResourceConfiguration.getInstance().getPage("error.unknown"));
        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return null;
    }
}