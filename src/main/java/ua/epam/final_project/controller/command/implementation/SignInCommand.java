package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;

public class SignInCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(SignInCommand.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.REDIRECT);
        IUserService userService = ServiceFactory.getUserService();
        
        String login = content.getReqParameters().get("login")[0];
        String password = content.getReqParameters().get("password")[0];

        if (login == null || password == null) {
            result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("auth.login.fail"));
            return result;
        }

        try {
            UserDto userDto = userService.findUserByLoginPassword(login, password);
            if (userDto != null) {
                content.getSessionAttributes().put("user", userDto);
            }

        } catch (UnknownUserException e) {
            logger.warn(e);
            result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("auth.login.fail"));
        }

        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("auth.login.success"));

        return result;
    }
}
