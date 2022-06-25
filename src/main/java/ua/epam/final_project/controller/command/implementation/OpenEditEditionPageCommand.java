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

public class OpenEditEditionPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenEditEditionPageCommand.class);
    private static final String ERROR_UNKNOWN = "error.unknown";
    private final IEditionService editionService;
    private final IGenreService genreService;

    public OpenEditEditionPageCommand() {
        this.editionService = ServiceFactory.getEditionService();
        this.genreService = ServiceFactory.getGenreService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl(ERROR_UNKNOWN));

        int editEditionId = InputValidator.extractValueFromRequest(content, "edit_edition_id", -1);

        try {
            Edition edition = editionService.findEditionById(editEditionId);
            List<Genre> genreList = genreService.findAllGenres();

            if (edition != null && !genreList.isEmpty()) {
                result.addRequestAttribute("editEdition", edition);
                result.addRequestAttribute("genreList", genreList);

                result.setDirection(Direction.FORWARD);
                result.setPage(ResourceConfiguration.getInstance().getPage("admin.edit-edition"));
            }
        } catch (UnknownEditionException | UnknownGenreException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.ADMIN);
    }
}
