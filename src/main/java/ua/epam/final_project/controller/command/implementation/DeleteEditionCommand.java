package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.util.DeleteImageFromExternalDirectory;
import ua.epam.final_project.util.InputValidator;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Collections;
import java.util.List;

public class DeleteEditionCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(DeleteEditionCommand.class);
    private final IEditionService editionService;

    public DeleteEditionCommand() {
        this.editionService = ServiceFactory.getEditionService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("error.unknown"));

        int editionId = InputValidator.extractValueFromRequest(content, "edition_id", -1);

        try {
            Edition edition = editionService.findEditionById(editionId);
            if (edition != null) {
                boolean isSuccess = editionService.deleteEdition(edition);
                if (isSuccess) {
                    result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("admin.editions"));
                    DeleteImageFromExternalDirectory.delete(edition.getImagePath());
                }
            }
        } catch (UnknownEditionException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.ADMIN);
    }
}
