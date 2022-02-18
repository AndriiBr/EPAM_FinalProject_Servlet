package ua.epam.final_project.dao;

import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.util.entity.Role;

import java.util.List;

public interface IRoleDao {

    /**
     * Gets number of rows in 'role' table from DB
     * @return number of rows
     * @throws DataNotFoundException if nothing was found in Db
     */
    Integer getNumberOfRoles() throws DataNotFoundException;

    /**
     * Gets list of all roles from DB
     * @return list of roles
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<Role> findAllRoles() throws DataNotFoundException;

    /**
     * Gets single role entity by id
     * @param id role id
     * @return role entity
     * @throws DataNotFoundException if nothing was found in Db
     */
    Role findRoleById(int id) throws DataNotFoundException;
}
