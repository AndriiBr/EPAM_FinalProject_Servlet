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
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.ServiceFactory;

import java.util.Arrays;
import java.util.List;

public class OpenEditionBuyPageCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenEditionBuyPageCommand.class);
    private final IEditionService editionService;

    public OpenEditionBuyPageCommand() {
        this.editionService = ServiceFactory.getEditionService();
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("error.unknown"));

        int buyEditionId = Integer.parseInt(content.getReqParameters().get("buy_edition_id"));
        UserDto userDto = (UserDto) content.getSessionAttributes().get("user");

        try {
            Edition edition = editionService.findEditionById(buyEditionId);

            if (edition != null && userDto != null) {
                result.addRequestAttribute("edition", edition);
                result.addRequestAttribute("remainingBalance", userDto.getBalance() - edition.getPrice());
                result.setPage(ResourceConfiguration.getInstance().getPage("shop.buy"));
            }
        } catch (UnknownEditionException e) {
            logger.error(e);
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER);
    }
}
