package ua.epam.final_project.controller.command;


import ua.epam.final_project.entity.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Security {

    private static Security security;

    private Security(){}

    public static synchronized Security getInstance() {
        if (security == null) {
            security = new  Security();
        }
        return security;
    }

    /**
     * Check if current user have access to execute this operation
     * @param req - HttpServletRequest
     * @param command - required command
     * @return True - if access granted / False - if not.
     */
    public boolean checkSecurity(HttpServletRequest req, ICommand command) {
        UserDto userDto = (UserDto) req.getSession(false).getAttribute("user");
        List<AccessLevel> accessLevelList = command.getAccessLevelList();

        int userRole;

        //null means everyone has access
        if (accessLevelList == null) {
            return true;
        }

        //if user in session = null, that means it is guest
        if (userDto == null) {
            userRole = 1;
        } else {
            userRole = Integer.parseInt(userDto.getRole());
        }

        return accessLevelList.stream().anyMatch(accessLevel -> accessLevel.getLevel() == userRole);
    }
}
