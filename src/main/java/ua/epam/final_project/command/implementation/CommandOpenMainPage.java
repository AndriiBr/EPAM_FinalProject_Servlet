package ua.epam.final_project.command.implementation;

import ua.epam.final_project.command.ICommand;
import ua.epam.final_project.config.Configuration;
import ua.epam.final_project.controller2.Direction;
import ua.epam.final_project.controller2.ExecutionResult;
import ua.epam.final_project.controller2.SessionRequestContent;

public class CommandOpenMainPage implements ICommand {

    private static final String PAGE_URL = "main";

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        result.setPage(Configuration.getInstance().getPage(PAGE_URL));
        result.setUrl(PAGE_URL);
        result.setDirection(Direction.REDIRECT);
        return result;
    }
}
