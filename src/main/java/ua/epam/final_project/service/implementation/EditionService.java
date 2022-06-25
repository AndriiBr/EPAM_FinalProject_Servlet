package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.*;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.entity.dto.UserDtoMapper;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;

import java.util.List;

public class EditionService implements IEditionService {
    private static final Logger logger = LogManager.getLogger(EditionService.class);

    private IDaoFactory daoFactory;

    public EditionService() {
        try {
            daoFactory = new PostgresDaoFactory();
        } catch (IncorrectPropertyException e) {
            logger.error(e);
        }
    }

    @Override
    public Integer getNumberOfEditions(int genreFilter) throws UnknownEditionException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            numberOfRows = daoFactory.getEditionDao().getNumberOfEditions(genreFilter);
            return numberOfRows;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public Integer getNumberOfEditions(UserDto userDto, boolean has, int genreFilter) throws UnknownEditionException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);
            numberOfRows = daoFactory.getEditionDao().getNumberOfEditions(user, has, genreFilter);
            return numberOfRows;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<Edition> findAllEditions() throws UnknownEditionException {
        List<Edition> editionList;
        try {
            daoFactory.getConnection();
            editionList = daoFactory.getEditionDao().findAllEditions();
            return editionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<Edition> findAllEditionsByName(String field, String name) throws UnknownEditionException {
        List<Edition> editionList;
        try {
            daoFactory.getConnection();
            editionList = daoFactory.getEditionDao().findAllEditionsByName(field, name);
            return editionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, int genreFilter, String orderBy) throws UnknownEditionException {
        List<Edition> editionList;
        try {
            daoFactory.getConnection();
            editionList = daoFactory.getEditionDao().findAllEditionsFromTo(recordsPerPage, page, genreFilter, orderBy);
            return editionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<Edition> findAllEditionsFromTo(UserDto userDto, boolean has, int recordsPerPage, int page, int genreFilter, String orderBy) throws UnknownEditionException {
        List<Edition> editionList;
        try {
            daoFactory.getConnection();
            User user = UserDtoMapper.convertDtoIntoEntity(userDto);
            editionList = daoFactory.getEditionDao().findAllEditionsFromTo(user, has, recordsPerPage, page, genreFilter, orderBy);
            return editionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public Edition findEditionById(int id) throws UnknownEditionException {
        Edition edition;
        try {
            daoFactory.getConnection();
            edition = daoFactory.getEditionDao().findEditionById(id);
            return edition;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public boolean insertNewEdition(Edition edition) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            operationResult = daoFactory.getEditionDao().insertNewEdition(edition);
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
    public boolean updateEdition(Edition edition) {
        boolean operationResult;

        try {
            daoFactory.beginTransaction();
            operationResult = daoFactory.getEditionDao().updateEdition(edition);
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
    public boolean deleteEdition(Edition edition) {
        boolean firstOperationResult;
        boolean secondOperationResult;

        try {
            daoFactory.beginTransaction();
            firstOperationResult = daoFactory.getUserEditionDao().deleteUserEditionByEdition(edition);
            secondOperationResult = daoFactory.getEditionDao().deleteEdition(edition);
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
