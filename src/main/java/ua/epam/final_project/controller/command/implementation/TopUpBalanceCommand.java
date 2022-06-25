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

import java.util.Arrays;
import java.util.List;

public class TopUpBalanceCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(TopUpBalanceCommand.class);
    private final IUserService userService;

    public TopUpBalanceCommand() {
        this.userService = ServiceFactory.getUserService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content)  {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.REDIRECT);
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("error.unknown"));

        UserDto userDto = (UserDto) content.getSessionAttributes().get("user");
        String money = content.getReqParameters().get("money");

        boolean isValid = InputValidator.validateMoney(money);

        if (isValid && userDto != null) {
            userDto.setBalance(userDto.getBalance() + Integer.parseInt(money));
            boolean isSuccess =  userService.updateUser(userDto);

            if (isSuccess) {
                try {
                    userDto = userService.findUserByLogin(userDto.getLogin());
                    result.addSessionAttribute("user", userDto);
                    result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("user.wallet"));
                } catch (UnknownUserException e) {
                    logger.error(e);
                }
            }
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER);
    }
}
