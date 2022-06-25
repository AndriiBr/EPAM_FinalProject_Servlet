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
    private final IUserService userService;
    private final IEditionService editionService;
    private final IUserEditionService userEditionService;

    public BuyEditionCommand() {
        this.userService = ServiceFactory.getUserService();
        this.editionService = ServiceFactory.getEditionService();
        this.userEditionService = ServiceFactory.getUserEditionService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("error.unknown"));

        UserDto userDto = (UserDto) content.getSessionAttributes().get("user");
        int editionId = Integer.parseInt(content.getReqParameters().get("buy_edition_id"));

        try {
            Edition edition = editionService.findEditionById(editionId);

            if (edition != null && userDto != null) {
                boolean res = userEditionService.insertUserEdition(userDto, edition);
                userDto = userService.findUserByLogin(userDto.getLogin());
                result.addSessionAttribute("user", userDto);

                if (res) {
                    result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("shop.edition_list"));
                }
            }
        } catch (UnknownEditionException | UnknownUserException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER);
    }
}
