package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.util.InputValidator;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.entity.dto.UserDtoMapper;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Collections;
import java.util.List;

public class SignUpCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(SignUpCommand.class);
    private final IUserService userService;

    public SignUpCommand() {
        this.userService = ServiceFactory.getUserService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);

        //Set result as failed before all validation will be done
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("auth.registration.fail"));

        String login = content.getReqParameters().get("login");
        String email = content.getReqParameters().get("email");
        String password = content.getReqParameters().get("password");
        String passwordConfirm = content.getReqParameters().get("password_confirm");
        boolean validation = InputValidator.validateRegistrationForm(login, email, password, passwordConfirm);

        User user = new User(login, password, email);

        try {
            if (validation) {
                UserDto newUser = UserDtoMapper.convertEntityIntoDto(user);
                newUser.setPassword(user.getPassword());
                boolean userAdded = userService.insertUser(newUser);
                UserDto userDto = userService.findUserByLogin(login);

                if (userAdded && userDto != null) {
                    result.addSessionAttribute("user", userDto);
                    result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("auth.registration.success"));
                } else if (!userAdded) {
                    result.addSessionAttribute("errorFlag", "exist");
                    result.addSessionAttribute("errorFlagValue", login);
                }
            } else {
                result.addSessionAttribute("errorFlag", "input");
            }
        } catch (UnknownUserException e) {
            logger.warn(e);
        }

        return result;
    }
    
    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.GUEST);
    }
}
