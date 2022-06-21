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
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);
        IUserService userService = ServiceFactory.getUserService();

        //Set result as failed before all validation will be done
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("auth.login.fail"));

        String login = content.getReqParameters().get("login");
        String password = content.getReqParameters().get("password");
        boolean validation = validateLoginPassword(login, password);

        try {
            if (validation) {
                UserDto userDto = userService.findUserByLoginPassword(login, password);
                if (userDto != null) {
                    result.addSessionAttribute("user", userDto);
                    result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("auth.login.success"));
                }
            }
        } catch (UnknownUserException e) {
            logger.warn(e);
        }

        return result;
    }

    private boolean validateLoginPassword(String login, String password) {
        return login != null && password != null && !login.equals("") && !password.equals("");
    }
}
