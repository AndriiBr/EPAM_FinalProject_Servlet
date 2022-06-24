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
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OpenUserSubscriptionsPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenUserSubscriptionsPageCommand.class);
    private static final String RECORDS_PER_PAGE = "recordsPerPage";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String GENRE_FILTER = "genreFilter";
    private static final String ORDER_BY = "orderBy";

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("shop.subscriptions"));

        String orderBy = InputValidator.extractValueFromRequest(content, ORDER_BY, "");
        int totalEditionsNumber;
        int recordsPerPage = InputValidator.extractValueFromRequest(content, RECORDS_PER_PAGE, 5);
        int currentPage = InputValidator.extractValueFromRequest(content, CURRENT_PAGE, 1);
        int genreFilter = InputValidator.extractValueFromRequest(content, GENRE_FILTER, 0);

        IEditionService editionService = ServiceFactory.getEditionService();
        IGenreService genreService = ServiceFactory.getGenreService();

        UserDto userDto = (UserDto) content.getSessionAttributes().get("user");

        try {
            List<Edition> editionList;
            List<Genre> genreList;
            if (userDto != null) {
                totalEditionsNumber = editionService.getNumberOfEditions(userDto, true, genreFilter);
                editionList = getEditionListForUser(editionService, userDto, recordsPerPage, currentPage, genreFilter, orderBy);
                genreList = genreService.findAllGenres();
            } else {
                result.setPage(ResourceConfiguration.getInstance().getPage("error.unknown"));
                return result;
            }

            int numberOfPages = (int)Math.ceil((double) totalEditionsNumber / recordsPerPage) != 0
                    ? (int)Math.ceil((double) totalEditionsNumber / recordsPerPage)
                    : 1;

            result.addRequestAttribute(CURRENT_PAGE, currentPage);
            result.addRequestAttribute("numberOfPages", numberOfPages);
            result.addRequestAttribute(GENRE_FILTER, genreFilter);
            result.addRequestAttribute(ORDER_BY, orderBy);
            result.addRequestAttribute(RECORDS_PER_PAGE, recordsPerPage);
            result.addRequestAttribute("editionList", editionList);
            result.addRequestAttribute("genresList", genreList);
        } catch (UnknownEditionException | UnknownGenreException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER, AccessLevel.BLOCKED);
    }

    /**
     * Return list of editions from DB with provided parameters
     *
     * @param editionService - service to work with Dao layer
     * @param userDto        - user to be sorted for
     * @param recordsPerPage - how many records will be rendered at the final page
     * @param page           - current page number
     * @param genreFilter    - genre filter
     * @param orderBy        - sort filter
     * @return list of editions
     */
    private List<Edition> getEditionListForUser(IEditionService editionService,
                                                UserDto userDto,
                                                int recordsPerPage,
                                                int page,
                                                int genreFilter,
                                                String orderBy) {
        List<Edition> editions;

        try {
            editions = editionService
                    .findAllEditionsFromTo(userDto, true, recordsPerPage, page, genreFilter, orderBy);
        } catch (UnknownEditionException e) {
            logger.error(e);
            return Collections.emptyList();
        }
        return editions;
    }
}
