package ua.epam.final_project.service;

import ua.epam.final_project.service.implementation.*;

/**
 * Factory of service layer objects
 */
public class ServiceFactory {

    private ServiceFactory() {}

    public static IUserService getUserService() {
        return new UserService();
    }

    public static IEditionService getEditionService() {
        return new EditionService();
    }

    public static IUserEditionService getUserEditionService() {
        return new UserEditionService();
    }

    public static IGenreService getGenreService() {
        return new GenreService();
    }

    public static IRoleService getRoleService() {
        return new RoleService();
    }
}
