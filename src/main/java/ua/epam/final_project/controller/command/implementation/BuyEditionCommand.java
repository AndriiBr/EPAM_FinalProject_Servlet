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
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IUserEditionService;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Arrays;
import java.util.List;

public class BuyEditionCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(BuyEditionCommand.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("shop.edition_list"));

        UserDto userDto = (UserDto) content.getSessionAttributes().get("user");
        int editionId = Integer.parseInt(content.getReqParameters().get("buy_edition_id"));

        IUserService userService = ServiceFactory.getUserService();
        IEditionService editionService = ServiceFactory.getEditionService();
        IUserEditionService userEditionService = ServiceFactory.getUserEditionService();

        try {
            Edition edition = editionService.findEditionById(editionId);
            boolean res = false;
            if (edition != null && userDto != null) {
                 res = userEditionService.insertUserEdition(userDto, edition);
                 userDto = userService.findUserByLogin(userDto.getLogin());
                 result.addSessionAttribute("user", userDto);
            }

            if (!res) {
                result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("error.unknown"));
            }
        } catch (UnknownEditionException | UnknownUserException e) {
            logger.error(e);
            result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("error.unknown"));
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER);
    }
}
