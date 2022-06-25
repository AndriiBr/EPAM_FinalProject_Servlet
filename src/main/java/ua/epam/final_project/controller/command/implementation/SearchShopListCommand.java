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

import java.nio.charset.StandardCharsets;
import java.util.List;

public class SearchShopListCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(SearchShopListCommand.class);
    private static final String CURRENT_PAGE = "currentPage";
    private static final String RECORDS_PER_PAGE = "recordsPerPage";
    private final IEditionService editionService;
    private final IGenreService genreService;

    public SearchShopListCommand() {
        this.editionService = ServiceFactory.getEditionService();
        this.genreService = ServiceFactory.getGenreService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("shop.edition_list"));

        int totalEditionsNumber;
        int recordsPerPage = InputValidator.extractValueFromRequest(content, RECORDS_PER_PAGE, 5);
        int currentPage = InputValidator.extractValueFromRequest(content, CURRENT_PAGE, 1);
        String searchName = new String(
                content
                        .getReqParameters()
                        .get("search")
                        .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String field = extractFieldWhereToSearch(content);

        try {
            List<Edition> editionList;
            editionList = editionService.findAllEditionsByName(field, searchName);
            totalEditionsNumber = editionList.size();
            List<Genre> genreList = genreService.findAllGenres();
            result.addRequestAttribute("editionList", editionList);
            result.addRequestAttribute("genresList", genreList);
            result.addRequestAttribute(CURRENT_PAGE, currentPage);
            result.addRequestAttribute("numberOfPages", (int)Math.ceil((double) totalEditionsNumber / recordsPerPage ));
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
