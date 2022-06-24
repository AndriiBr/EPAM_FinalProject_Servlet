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

public class OpenWalletTopUpPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenWalletTopUpPageCommand.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);

        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("user.wallet.top_up"));



        UserDto userDto = (UserDto) content.getSessionAttributes().get("user");

        try {
            IUserService userService = ServiceFactory.getUserService();
            if (userDto!= null) {
                UserDto userFromDb = userService.findUserById(userDto.getId());
                result.addSessionAttribute("user", userFromDb);
            }
        } catch (UnknownUserException e) {
            logger.error(e);
            result.setPage(ResourceConfiguration.getInstance().getPage("error.unknown"));
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER);
    }
}
