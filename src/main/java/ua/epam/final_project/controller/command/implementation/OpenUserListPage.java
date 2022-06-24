package ua.epam.final_project.controller.command.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.Role;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownRoleException;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IRoleService;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.util.InputValidator;

import java.util.Collections;
import java.util.List;

public class OpenUserListPage implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenUserListPage.class);
    private static final String CURRENT_PAGE = "currentPage";
    private static final String RECORDS_PER_PAGE = "recordsPerPage";

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult(content);
        result.setDirection(Direction.FORWARD);
        result.setPage(ResourceConfiguration.getInstance().getPage("admin.user-list"));

        int recordsPerPage = InputValidator.extractValueFromRequest(content, RECORDS_PER_PAGE, 10);
        int currentPage = InputValidator.extractValueFromRequest(content, CURRENT_PAGE, 1);
        int totalUserNumber;

        IUserService userService = ServiceFactory.getUserService();
        IRoleService roleService = ServiceFactory.getRoleService();

        try {
            List<UserDto> userList = userService.findAllUsersFromTo(recordsPerPage, currentPage);
            List<Role> roleList = roleService.findAllRoles();
            totalUserNumber = userService.getNumberOfUsers();

            int numberOfPages = (int)Math.ceil((double) totalUserNumber / recordsPerPage) != 0
                    ? (int)Math.ceil((double) totalUserNumber / recordsPerPage)
                    : 1;

            if (!roleList.isEmpty()) {
                result.addRequestAttribute("userList", userList);
                result.addRequestAttribute("roleList", roleList);
                result.addRequestAttribute("numberOfPages", numberOfPages);
                result.addRequestAttribute(CURRENT_PAGE, currentPage);
                result.addRequestAttribute(RECORDS_PER_PAGE, recordsPerPage);
            } else {
                result.setPage(ResourceConfiguration.getInstance().getPage("error.unknown"));
                return result;
            }
        } catch (UnknownUserException | UnknownRoleException e) {
            logger.error(e);
            result.setPage(ResourceConfiguration.getInstance().getPage("error.unknown"));
            return result;
        }

        return result;
    }

    @Override
    public List<AccessLevel> getAccessLevelList() {
        return Collections.singletonList(AccessLevel.ADMIN);
    }
}
