package ua.epam.final_project.service;

import ua.epam.final_project.exception.UnknownRoleException;
import ua.epam.final_project.entity.Role;

import java.util.List;

public interface IRoleService {
    /**
     * Gets number of rows in 'role' table from DB
     * @return number of rows
     * @throws UnknownRoleException if nothing was found in Db
     */
    Integer getNumberOfRoles() throws UnknownRoleException;

    /**
     * Gets list of all roles from DB
     * @return list of roles
     * @throws UnknownRoleException if nothing was found in Db
     */
    List<Role> findAllRoles() throws UnknownRoleException;

    /**
     * Gets single role entity by id
     * @param id role id
     * @return role entity
     * @throws UnknownRoleException if nothing was found in Db
     */
    Role findRoleById(int id) throws UnknownRoleException;
}
