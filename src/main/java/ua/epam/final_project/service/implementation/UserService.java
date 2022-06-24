package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.entity.dto.UserDtoMapper;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.entity.User;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserDto> findAllUsers() throws UnknownUserException {
        List<UserDto> userList;
        try {
            daoFactory.getConnection();
            userList = userDao.findAllUsers()
                    .stream()
                    .map(UserDtoMapper::convertEntityIntoDto)
                    .collect(Collectors.toList());
            return userList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<UserDto> findAllUsersFromTo(int recordsPerPage, int page) throws UnknownUserException {
        List<UserDto> userList;
        try {
            daoFactory.getConnection();
            userList = userDao.findAllUsersFromTo(recordsPerPage, page)
                    .stream()
                    .map(UserDtoMapper::convertEntityIntoDto)
                    .collect(Collectors.toList());
            return userList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public UserDto findUserByLoginPassword(String login, String password) throws UnknownUserException {
        try {
            daoFactory.getConnection();
            return UserDtoMapper.convertEntityIntoDto(userDao.findUserByLoginPassword(login, password));
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public UserDto findUserByLogin(String login) throws UnknownUserException {
        try {
            daoFactory.getConnection();
            return UserDtoMapper.convertEntityIntoDto(userDao.findUserByLogin(login));
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public UserDto findUserById(int id) throws UnknownUserException {
        try {
            daoFactory.getConnection();
            return UserDtoMapper.convertEntityIntoDto(userDao.findUserById(id));
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownUserException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public boolean insertUser(UserDto userDto) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);
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
    public boolean updateUser(UserDto userDto) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);
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
    public boolean deleteUser(UserDto userDto) {
        boolean firstOperationResult;
        boolean secondOperationResult;

        try {
            daoFactory.beginTransaction();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);
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
