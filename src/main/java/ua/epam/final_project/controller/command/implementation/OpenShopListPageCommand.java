package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.Genre;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Collections;
import java.util.List;

public class OpenShopListPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenShopListPageCommand.class);
    private static final String RECORDS_PER_PAGE = "recordsPerPage";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String GENRE_FILTER = "genreFilter";
    private static final String ORDER_BY = "orderBy";

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);

        int totalEditionsNumber;
        int recordsPerPage = extractValueFromRequest(content, RECORDS_PER_PAGE, 5);
        int currentPage = extractValueFromRequest(content, CURRENT_PAGE, 1);
        String genreFilter = extractValueFromRequest(content, GENRE_FILTER, "");
        String orderBy = extractValueFromRequest(content, ORDER_BY, "");

        IEditionService editionService = ServiceFactory.getEditionService();
        IGenreService genreService = ServiceFactory.getGenreService();

        UserDto userDto = (UserDto) content.getSessionAttributes().get("user");

        try {
            List<Edition> editionList;
            if (userDto != null) {
                totalEditionsNumber = editionService.getNumberOfEditions(userDto, false, genreFilter);
                editionList = getEditionListForUser(editionService, userDto, recordsPerPage, currentPage, genreFilter, orderBy);
            } else {
                totalEditionsNumber = editionService.getNumberOfEditions(genreFilter);
                editionList = getEditionListForGuest(editionService, recordsPerPage, currentPage, genreFilter, orderBy);
            }

            List<Genre> genreList = genreService.findAllGenres();
            result.addRequestAttribute("editionList", editionList);
            result.addRequestAttribute("genresList", genreList);
            result.addRequestAttribute(CURRENT_PAGE, currentPage);
            result.addRequestAttribute("numberOfPages", (int)Math.ceil((double) totalEditionsNumber / recordsPerPage ));
            result.addRequestAttribute(GENRE_FILTER, genreFilter);
            result.addRequestAttribute(ORDER_BY, orderBy);
        } catch (UnknownEditionException e) {
            logger.error(e);
        } catch (UnknownGenreException e) {
            logger.error(e);
        }

        result.setPage(ResourceConfiguration.getInstance().getPage("shop.edition_list"));

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return null;
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
                                                String genreFilter,
                                                String orderBy) {
        List<Edition> editions;

        try {
            editions = editionService
                    .findAllEditionsFromTo(userDto, false, recordsPerPage, page, genreFilter, orderBy);
        } catch (UnknownEditionException e) {
            logger.error(e);
            return Collections.emptyList();
        }
        return editions;
    }

    /**
     * Return list of editions from DB with provided parameters but for GUEST
     *
     * @param editionService - service to work with Dao layer
     * @param recordsPerPage - how many records will be rendered at the final page
     * @param page           - current page number
     * @param genreFilter    - genre filter
     * @param orderBy        - sort filter
     * @return list of editions
     */
    private List<Edition> getEditionListForGuest(IEditionService editionService,
                                                 int recordsPerPage,
                                                 int page,
                                                 String genreFilter,
                                                 String orderBy) {
        List<Edition> editions;

        try {
            editions = editionService.findAllEditionsFromTo(recordsPerPage, page, genreFilter, orderBy);
        } catch (UnknownEditionException e) {
            logger.error(e);
            return Collections.emptyList();
        }

        return editions;
    }

    private String extractValueFromRequest(SessionRequestContent content, String key, String defaultValue) {
        String result;

        if (content.getReqParameters().get(key) != null) {
            result = content.getReqParameters().get(key);
        } else if (content.getReqAttributes().get(key) != null) {
            result = (String) content.getReqAttributes().get(key);
        } else {
            result = defaultValue;
        }

        if (result.equals("title")) {
            String lang = (String) content.getSessionAttributes().get("language");
            result = result.concat("_").concat(lang);
        }

        return result;
    }

    private int extractValueFromRequest(SessionRequestContent content, String key, int defaultValue) {
        int result;

        if (content.getReqParameters().get(key) != null) {
            result = Integer.parseInt(content.getReqParameters().get(key));
        } else if (content.getReqAttributes().get(key) != null) {
            result = Integer.parseInt((String) content.getReqAttributes().get(key));
        } else {
            result = defaultValue;
        }

        return result;
    }

    private String extractFieldWhereToSearch(SessionRequestContent content) {
        String lang = (String) content.getSessionAttributes().get("language");

        return "title_".concat(lang);
    }
}
