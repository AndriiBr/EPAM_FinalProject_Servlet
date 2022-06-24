package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.util.InputValidator;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.Genre;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Collections;
import java.util.List;

public class OpenAdminEditionConsoleCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenAdminEditionConsoleCommand.class);

    private static final String RECORDS_PER_PAGE = "recordsPerPage";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String GENRE_FILTER = "genreFilter";
    private static final String ORDER_BY = "orderBy";

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("admin.editions"));

        int genreFilter = InputValidator.extractValueFromRequest(content, GENRE_FILTER, 0);
        String orderBy = InputValidator.extractValueFromRequest(content, ORDER_BY, "");
        int totalEditionsNumber;
        int recordsPerPage = InputValidator.extractValueFromRequest(content, RECORDS_PER_PAGE, 5);
        int currentPage = InputValidator.extractValueFromRequest(content, CURRENT_PAGE, 1);

        IEditionService editionService = ServiceFactory.getEditionService();
        IGenreService genreService = ServiceFactory.getGenreService();

        try {
            List<Edition> editionList = editionService
                    .findAllEditionsFromTo(recordsPerPage, currentPage, genreFilter, orderBy);
            totalEditionsNumber = editionService.getNumberOfEditions(genreFilter);
            List<Genre> genreList = genreService.findAllGenres();

            int numberOfPages = (int)Math.ceil((double) totalEditionsNumber / recordsPerPage) != 0
                    ? (int)Math.ceil((double) totalEditionsNumber / recordsPerPage)
                    : 1;

            result.addRequestAttribute("genresList", genreList);
            result.addRequestAttribute("editionList", editionList);
            result.addRequestAttribute(CURRENT_PAGE, currentPage);
            result.addRequestAttribute("numberOfPages", numberOfPages);
            result.addRequestAttribute(RECORDS_PER_PAGE, recordsPerPage);
            result.addRequestAttribute(GENRE_FILTER, genreFilter);
            result.addRequestAttribute(ORDER_BY, orderBy);

        } catch (UnknownEditionException | UnknownGenreException e) {
            logger.error(e);
            result.setPage(ResourceConfiguration.getInstance().getPage("error.unknown"));
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.ADMIN);
    }
}
