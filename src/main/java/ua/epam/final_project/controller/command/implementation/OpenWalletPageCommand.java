package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Arrays;
import java.util.List;

public class OpenWalletPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenWalletPageCommand.class);
    private final IUserService userService;

    public OpenWalletPageCommand() {
        this.userService = ServiceFactory.getUserService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);

        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("error.unknown"));

        UserDto userDto = (UserDto) content.getSessionAttributes().get("user");

        try {
            if (userDto!= null) {
                UserDto userFromDb = userService.findUserById(userDto.getId());
                result.addSessionAttribute("user", userFromDb);
                result.setPage(ResourceConfiguration.getInstance().getPage("user.wallet"));
            }
        } catch (UnknownUserException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER, AccessLevel.BLOCKED);
    }
}
