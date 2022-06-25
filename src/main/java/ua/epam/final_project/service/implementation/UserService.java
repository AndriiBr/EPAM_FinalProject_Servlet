package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.IDaoFactory;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.dao.PostgresDaoFactory;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.entity.dto.UserDtoMapper;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    private IDaoFactory daoFactory;

    public UserService() {
        try {
            daoFactory = new PostgresDaoFactory();
        } catch (IncorrectPropertyException e) {
            logger.error(e);
        }
    }

    @Override
    public Integer getNumberOfUsers() throws UnknownUserException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            numberOfRows = daoFactory.getUserDao().getNumberOfUsers();
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
            userList = daoFactory.getUserDao().findAllUsers()
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
            userList = daoFactory.getUserDao().findAllUsersFromTo(recordsPerPage, page)
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
            return UserDtoMapper.convertEntityIntoDto(daoFactory.getUserDao().findUserByLoginPassword(login, password));
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
            return UserDtoMapper.convertEntityIntoDto(daoFactory.getUserDao().findUserByLogin(login));
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
            return UserDtoMapper.convertEntityIntoDto(daoFactory.getUserDao().findUserById(id));
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
            operationResult = daoFactory.getUserDao().insertUser(user);
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
            operationResult = daoFactory.getUserDao().updateUser(user);
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
            firstOperationResult = daoFactory.getUserEditionDao().deleteUserEditionByUser(user);
            secondOperationResult = daoFactory.getUserDao().deleteUser(user);
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
