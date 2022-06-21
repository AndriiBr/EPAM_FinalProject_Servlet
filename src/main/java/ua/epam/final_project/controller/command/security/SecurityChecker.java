package ua.epam.final_project.controller.command.security;


import ua.epam.final_project.controller.command.ICommand;
import ua.epam.final_project.entity.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SecurityChecker {

    private static SecurityChecker securityChecker;

    private SecurityChecker(){}

    public static synchronized SecurityChecker getInstance() {
        if (securityChecker == null) {
            securityChecker = new SecurityChecker();
        }
        return securityChecker;
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
