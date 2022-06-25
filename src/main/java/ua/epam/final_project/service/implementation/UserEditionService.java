package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.IDaoFactory;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.dao.PostgresDaoFactory;
import ua.epam.final_project.entity.dto.UserDtoMapper;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IUserEditionService;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.UserEdition;

import java.util.List;

public class UserEditionService implements IUserEditionService {
    private static final Logger logger = LogManager.getLogger(UserEditionService.class);

    private IDaoFactory daoFactory;

    public UserEditionService() {
        try {
            daoFactory = new PostgresDaoFactory();
        } catch (IncorrectPropertyException e) {
            logger.error(e);
        }
    }
    @Override
    public Integer getNumberOfRows() throws UnknownUserEditionPairException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            numberOfRows = daoFactory.getUserEditionDao().getNumberOfRows();
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
            userEditionList = daoFactory.getUserEditionDao().findAllUserEdition();
            return userEditionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserEditionPairException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<UserEdition> findAllUserEditionByUser(UserDto userDto) throws UnknownUserEditionPairException {
        List<UserEdition> userEditionList;

        try {
            daoFactory.getConnection();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);
            userEditionList = daoFactory.getUserEditionDao().findAllUserEditionByUser(user);
            return userEditionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserEditionPairException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<UserEdition> findAllUserEditionByUserIdEditionId(UserDto userDto, Edition edition) throws UnknownUserEditionPairException {
        List<UserEdition> userEditionList;

        try {
            daoFactory.getConnection();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);
            userEditionList = daoFactory.getUserEditionDao().findAllUserEditionByUserIdEditionId(user, edition);
            return userEditionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserEditionPairException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public boolean insertUserEdition(UserDto userDto, Edition edition) {
        boolean firstOperationResult;
        boolean secondOperationResult;

        try {
            daoFactory.beginTransaction();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);

            user.setBalance(user.getBalance() - edition.getPrice());
            if(user.getBalance() < 0) {
                return false;
            }
            firstOperationResult = daoFactory.getUserDao().updateUser(user);
            secondOperationResult = daoFactory.getUserEditionDao().insertUserEdition(user, edition);
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
    public boolean deleteUserEdition(UserDto userDto, Edition edition) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);
            operationResult = daoFactory.getUserEditionDao().deleteUserEdition(user, edition);
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
            operationResult = daoFactory.getUserEditionDao().deleteUserEditionByEdition(edition);
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
    public boolean deleteUserEditionByUser(UserDto userDto) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);
            operationResult = daoFactory.getUserEditionDao().deleteUserEditionByUser(user);
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
