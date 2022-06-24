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
import ua.epam.final_project.util.InputValidator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OpenEditEditionPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenEditEditionPageCommand.class);
    private static final String ERROR_UNKNOWN = "error.unknown";

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("admin.edit-edition"));

        int editEditionId = InputValidator.extractValueFromRequest(content, "edit_edition_id", -1);

        IEditionService editionService = ServiceFactory.getEditionService();
        IGenreService genreService = ServiceFactory.getGenreService();

        try {
            Edition edition = editionService.findEditionById(editEditionId);
            List<Genre> genreList = genreService.findAllGenres();

            if (edition != null && !genreList.isEmpty()) {
                result.addRequestAttribute("editEdition", edition);
                result.addRequestAttribute("genreList", genreList);
            } else {
                result.setDirection(Direction.REDIRECT);
                result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl(ERROR_UNKNOWN));
                return result;
            }
        } catch (UnknownEditionException | UnknownGenreException e) {
            logger.error(e);
            result.setDirection(Direction.REDIRECT);
            result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl(ERROR_UNKNOWN));
            return result;
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.ADMIN);
    }
}
