package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.Genre;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.List;

public class OpenShopListPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenShopListPageCommand.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);

        IEditionService editionService = ServiceFactory.getEditionService();
        IGenreService genreService = ServiceFactory.getGenreService();

        try {
            List<Edition> editionList = editionService.findAllEditionsFromTo(10, 1, "", "");
            List<Genre> genreList = genreService.findAllGenres();
            result.addRequestAttribute("editionList", editionList);
            result.addRequestAttribute("genresList", genreList);
        } catch (UnknownEditionException e) {
            logger.error(e);
        } catch (UnknownGenreException e) {
            logger.error(e);
        }

        result.setPage(ResourceConfiguration.getInstance().getPage("shop.edition_list"));

        return result;
    }
}
