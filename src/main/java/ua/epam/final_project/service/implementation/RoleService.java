package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.IDaoFactory;
import ua.epam.final_project.dao.IRoleDao;
import ua.epam.final_project.dao.PostgresDaoFactory;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.IncorrectPropertyException;
import ua.epam.final_project.exception.UnknownRoleException;
import ua.epam.final_project.service.IRoleService;
import ua.epam.final_project.entity.Role;

import java.util.List;

public class RoleService implements IRoleService {
    private static final Logger logger = LogManager.getLogger(RoleService.class);

    private IDaoFactory daoFactory;

    public RoleService() {
        try {
            daoFactory = new PostgresDaoFactory();
        } catch (IncorrectPropertyException e) {
            logger.error(e);
        }
    }

    @Override
    public Integer getNumberOfRoles() throws UnknownRoleException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            numberOfRows = daoFactory.getRoleDao().getNumberOfRoles();
            return numberOfRows;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownRoleException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<Role> findAllRoles() throws UnknownRoleException {
        List<Role> userList;
        try {
            daoFactory.getConnection();
            userList = daoFactory.getRoleDao().findAllRoles();
            return userList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownRoleException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public Role findRoleById(int id) throws UnknownRoleException {
        Role role;
        try {
            daoFactory.getConnection();
            role = daoFactory.getRoleDao().findRoleById(id);
            return role;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownRoleException();
        } finally {
            daoFactory.releaseConnection();
        }
    }
}
