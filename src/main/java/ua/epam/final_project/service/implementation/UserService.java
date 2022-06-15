package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.entity.User;

import java.util.List;

public class UserService implements IUserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    private static final DataBaseSelector DB_SOURCE = DataBaseSelector.POSTGRES;
    private DaoFactory daoFactory;
    private IUserDao userDao;
    private IUserEditionDao userEditionDao;

    public UserService() {
        try {
            daoFactory = DaoFactory.getDaoFactory(DB_SOURCE);
            userDao = daoFactory.getUserDao();
            userEditionDao = daoFactory.getUserEditionDao();
        } catch (IncorrectPropertyException | DataBaseNotSupportedException e) {
            logger.error(e);
        }
    }

    @Override
    public Integer getNumberOfUsers() throws UnknownUserException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            numberOfRows = userDao.getNumberOfUsers();
            return numberOfRows;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<User> findAllUsers() throws UnknownUserException {
        List<User> userList;
        try {
            daoFactory.getConnection();
            userList = userDao.findAllUsers();
            return userList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<User> findAllUsersFromTo(int recordsPerPage, int page) throws UnknownUserException {
        List<User> userList;
        try {
            daoFactory.getConnection();
            userList = userDao.findAllUsersFromTo(recordsPerPage, page);
            return userList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public User findUserByLoginPassword(String login, String password) throws UnknownUserException {
        User user;
        try {
            daoFactory.getConnection();
            user = userDao.findUserByLoginPassword(login, password);
            return user;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public User findUserByLogin(String login) throws UnknownUserException {
        User user;
        try {
            daoFactory.getConnection();
            user = userDao.findUserByLogin(login);
            return user;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public boolean insertUser(User user) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            operationResult = userDao.insertUser(user);
            if (operationResult) {
                daoFactory.commitTransaction();
                return true;
            } else {
                daoFactory.rollbackTransaction();
                return false;
            }
        } catch (DataBaseConnectionException e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            operationResult = userDao.updateUser(user);
            if (operationResult) {
                daoFactory.commitTransaction();
                return true;
            } else {
                daoFactory.rollbackTransaction();
                return false;
            }
        } catch (DataBaseConnectionException e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user) {
        boolean firstOperationResult;
        boolean secondOperationResult;

        try {
            daoFactory.beginTransaction();
            firstOperationResult = userEditionDao.deleteUserEditionByUser(user);
            secondOperationResult = userDao.deleteUser(user);
            if (firstOperationResult && secondOperationResult) {
                daoFactory.commitTransaction();
                return true;
            } else {
                daoFactory.rollbackTransaction();
                return false;
            }
        } catch (DataBaseConnectionException e) {
            logger.error(e);
            return false;
        }
    }
}
