package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IRoleDao;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.IncorrectPropertyException;
import ua.epam.final_project.exception.UnknownRoleException;
import ua.epam.final_project.service.IRoleService;
import ua.epam.final_project.entity.Role;

import java.util.List;

public class RoleService implements IRoleService {
    private static final Logger logger = LogManager.getLogger(RoleService.class);

    private static final DataBaseSelector DB_SOURCE = DataBaseSelector.POSTGRES;
    private DaoFactory daoFactory;
    private IRoleDao roleDao;

    public RoleService() {
        try {
            daoFactory = DaoFactory.getDaoFactory(DB_SOURCE);
        } catch (IncorrectPropertyException | DataBaseNotSupportedException e) {
            logger.error(e);
        }
    }

    @Override
    public Integer getNumberOfRoles() throws UnknownRoleException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            roleDao = daoFactory.getRoleDao();
            numberOfRows = roleDao.getNumberOfRoles();
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
            roleDao = daoFactory.getRoleDao();
            userList = roleDao.findAllRoles();
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
            roleDao = daoFactory.getRoleDao();
            role = roleDao.findRoleById(id);
            return role;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownRoleException();
        } finally {
            daoFactory.releaseConnection();
        }
    }
}
