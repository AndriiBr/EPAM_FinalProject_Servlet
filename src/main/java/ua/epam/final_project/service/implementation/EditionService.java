package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;

import java.util.List;

public class EditionService implements IEditionService {

    private static final Logger logger = LogManager.getLogger(EditionService.class);

    private static final DataBaseSelector DB_SOURCE = DataBaseSelector.POSTGRES;
    private DaoFactory daoFactory;
    private IEditionDao editionDao;

    public EditionService() {
        try {
            daoFactory = DaoFactory.getDaoFactory(DB_SOURCE);
        } catch (IncorrectPropertyException | DataBaseNotSupportedException e) {
            logger.error(e);
        }
    }

    @Override
    public Integer getNumberOfEditions(String genreFilter) throws UnknownEditionException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            editionDao = daoFactory.getEditionDao();
            numberOfRows = editionDao.getNumberOfEditions(genreFilter);
            return numberOfRows;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public Integer getNumberOfEditions(User user, boolean has, String genreFilter) throws UnknownEditionException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            editionDao = daoFactory.getEditionDao();
            numberOfRows = editionDao.getNumberOfEditions(user, has, genreFilter);
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
            editionDao = daoFactory.getEditionDao();
            editionList = editionDao.findAllEditions();
            return editionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, String genreFilter, String orderBy) throws UnknownEditionException {
        List<Edition> editionList;
        try {
            daoFactory.getConnection();
            editionDao = daoFactory.getEditionDao();
            editionList = editionDao.findAllEditionsFromTo(recordsPerPage, page, genreFilter, orderBy);
            return editionList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownEditionException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<Edition> findAllEditionsFromTo(User user, boolean has, int recordsPerPage, int page, String genreFilter, String orderBy) throws UnknownEditionException {
        List<Edition> editionList;
        try {
            daoFactory.getConnection();
            editionDao = daoFactory.getEditionDao();
            editionList = editionDao.findAllEditionsFromTo(user, has, recordsPerPage, page, genreFilter, orderBy);
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
            editionDao = daoFactory.getEditionDao();
            edition = editionDao.findEditionById(id);
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
            editionDao = daoFactory.getEditionDao();
            operationResult = editionDao.insertNewEdition(edition);
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
            editionDao = daoFactory.getEditionDao();
            operationResult = editionDao.updateEdition(edition);
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
            editionDao = daoFactory.getEditionDao();
            IUserEditionDao userEditionDao = daoFactory.getUserEditionDao();
            firstOperationResult = userEditionDao.deleteUserEditionByEdition(edition);
            secondOperationResult = editionDao.deleteEdition(edition);
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
