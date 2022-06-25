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
import ua.epam.final_project.util.InputValidator;

import java.util.Collections;
import java.util.List;

public class BlockUnblockUserCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(BlockUnblockUserCommand.class);
    private final IUserService userService;

    public BlockUnblockUserCommand() {
        this.userService = ServiceFactory.getUserService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("error.unknown"));

        int userId = InputValidator.extractValueFromRequest(content, "user_block", -1);

        try {
            UserDto userDto = userService.findUserById(userId);

            if (userDto != null && !userDto.getRole().equals("3")) {
                userDto.setRole(userDto.getRole().equals("2") ? "0" : "2");
                boolean isSuccess = userService.updateUser(userDto);

                if (isSuccess) {
                    result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("admin.user-list"));
                }
            }
        } catch (UnknownUserException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.ADMIN);
    }
}
