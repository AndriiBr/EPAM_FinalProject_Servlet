package ua.epam.final_project.controller.command.implementation;

import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Arrays;
import java.util.List;

public class TopUpBalanceCommand implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        IUserService userService = ServiceFactory.getUserService();

        result.setDirection(Direction.REDIRECT);
        result.setRedirectUrl(ResourceConfiguration.getInstance().getUrl("user.wallet"));

        UserDto userDto = (UserDto) content.getSessionAttributes().get("user");
        String money = content.getReqParameters().get("money");


        if (validate(money) && userDto != null) {
            userDto.setBalance(userDto.getBalance() + Integer.parseInt(money));
            userService.updateUser(userDto);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER);
    }

    /**
     * Validate input to be a positive number
     * @param input - amount of money
     * @return validation result
     */
    private boolean validate(String input) {
        if (input == null || input.equals("")) {
            return false;
        }

        int inputInteger;

        try {
            inputInteger = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        return inputInteger > 0;
    }
}
