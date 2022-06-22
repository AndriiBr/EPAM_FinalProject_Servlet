package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.Genre;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.List;

public class SearchShopListCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(SearchShopListCommand.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("shop.edition_list"));

        IEditionService editionService = ServiceFactory.getEditionService();
        IGenreService genreService = ServiceFactory.getGenreService();

        String searchName = content.getReqParameters().get("search");
        String field = extractFieldWhereToSearch(content);

        try {
            List<Edition> editionList;
            editionList = editionService.findAllEditionsByName(field, searchName);
            List<Genre> genreList = genreService.findAllGenres();
            result.addRequestAttribute("editionList", editionList);
            result.addRequestAttribute("genresList", genreList);
        } catch (UnknownEditionException | UnknownGenreException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return null;
    }

    private String extractFieldWhereToSearch(SessionRequestContent content) {
        String lang = (String) content.getSessionAttributes().get("language");

        return "title_".concat(lang);
    }
}
