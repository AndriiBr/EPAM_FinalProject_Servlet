package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IUserEditionService;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;
import ua.epam.final_project.util.entity.UserEdition;

import java.util.List;

public class UserEditionService implements IUserEditionService {
    private static final Logger logger = LogManager.getLogger(UserEditionService.class);

    private static final DataBaseSelector DB_SOURCE = DataBaseSelector.MY_SQL;
    private DaoFactory daoFactory;
    private IUserEditionDao userEditionDao;

    public UserEditionService() {
        try {
            daoFactory = DaoFactory.getDaoFactory(DB_SOURCE);
        } catch (IncorrectPropertyException | DataBaseNotSupportedException e) {
            logger.error(e);
        }
    }
    @Override
    public Integer getNumberOfRows() throws UnknownUserEditionPairException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            userEditionDao = daoFactory.getUserEditionDao();
            numberOfRows = userEditionDao.getNumberOfRows();
            return numberOfRows;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserEditionPairException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<UserEdition> findAllUserEdition() throws UnknownUserEditionPairException {
        List<UserEdition> userEditionList;
        try {
            daoFactory.getConnection();
            userEditionDao = daoFactory.getUserEditionDao();
            userEditionList = userEditionDao.findAllUserEdition();
            return userEditionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserEditionPairException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<UserEdition> findAllUserEditionByUser(User user) throws UnknownUserEditionPairException {
        List<UserEdition> userEditionList;

        try {
            daoFactory.getConnection();
            userEditionDao = daoFactory.getUserEditionDao();
            userEditionList = userEditionDao.findAllUserEditionByUser(user);
            return userEditionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserEditionPairException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<UserEdition> findAllUserEditionByUserIdEditionId(User user, Edition edition) throws UnknownUserEditionPairException {
        List<UserEdition> userEditionList;

        try {
            daoFactory.getConnection();
            userEditionDao = daoFactory.getUserEditionDao();
            userEditionList = userEditionDao.findAllUserEditionByUserIdEditionId(user, edition);
            return userEditionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserEditionPairException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public boolean insertUserEdition(User user, Edition edition) {
        boolean firstOperationResult;
        boolean secondOperationResult;

        try {
            daoFactory.beginTransaction();
            userEditionDao = daoFactory.getUserEditionDao();
            IUserDao userDao = daoFactory.getUserDao();
            firstOperationResult = userDao.updateUser(user);
            secondOperationResult = userEditionDao.insertUserEdition(user, edition);
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

    @Override
    public boolean deleteUserEdition(User user, Edition edition) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            userEditionDao = daoFactory.getUserEditionDao();
            operationResult = userEditionDao.deleteUserEdition(user, edition);
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
    public boolean deleteUserEditionByEdition(Edition edition) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            userEditionDao = daoFactory.getUserEditionDao();
            operationResult = userEditionDao.deleteUserEditionByEdition(edition);
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
    public boolean deleteUserEditionByUser(User user) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            userEditionDao = daoFactory.getUserEditionDao();
            operationResult = userEditionDao.deleteUserEditionByUser(user);
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
}
