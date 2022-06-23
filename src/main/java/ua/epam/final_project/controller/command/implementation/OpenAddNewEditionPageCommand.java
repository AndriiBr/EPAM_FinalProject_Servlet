package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.Genre;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Collections;
import java.util.List;

public class OpenAddNewEditionPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenAddNewEditionPageCommand.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("admin.new-edition"));

        IGenreService genreService = ServiceFactory.getGenreService();

        try {
            List<Genre> genreList = genreService.findAllGenres();
            result.addRequestAttribute("genreList", genreList);

        } catch (UnknownGenreException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.ADMIN);
    }
}
