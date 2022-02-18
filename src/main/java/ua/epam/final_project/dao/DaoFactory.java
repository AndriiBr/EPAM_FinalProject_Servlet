package ua.epam.final_project.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.IncorrectPropertyException;

public abstract class DaoFactory {

    private static final Logger logger = LogManager.getLogger(DaoFactory.class);

    /**
     * Creates User DAO
     * @return User DAO
     */
    public abstract IUserDao getUserDao();

    /**
     * Creates Edition DAO
     * @return Edition DAO
     */
    public abstract IEditionDao getEditionDao();

    /**
     * Creates User-Edition DAO
     * @return User-Edition DAO
     */
    public abstract IUserEditionDao getUserEditionDao();

    /**
     * Creates Genre DAO
     * @return Genre DAO
     */
    public abstract IGenreDao getGenreDao();

    /**
     * Creates Role DAO
     * @return Role DAO
     */
    public abstract IRoleDao getRoleDao();

    /**
     * Get new connection from ConnectionPoll
     */
    public abstract void getConnection();

    /**
     * return connection to ConnectionPoll
     */
    public abstract void releaseConnection();

    /**
     * Opens DB data transaction
     * @throws DataBaseConnectionException if unable to open data transaction or get connection to Database
     */
    public abstract void beginTransaction() throws DataBaseConnectionException;

    /**
     * Commits transaction results and closes transaction
     * @throws DataBaseConnectionException if unable to commit data transaction or get connection to Database
     */
    public abstract void commitTransaction() throws DataBaseConnectionException;

    /**
     * Rollbacks transaction results and closes transaction
     * @throws DataBaseConnectionException if unable to rollback transaction or get connection to Database
     */
    public abstract void rollbackTransaction() throws DataBaseConnectionException;

    public static DaoFactory getDaoFactory(DataBaseSelector dataBase)
            throws DataBaseNotSupportedException,
            IncorrectPropertyException {

        switch (dataBase) {
            case MY_SQL:
                return new MySQLDaoFactory();
            case ORACLE:
                logger.error("Database - {} not supported yet!", dataBase);
                throw new DataBaseNotSupportedException(dataBase + "Database not supported");
            default:
                logger.error("No database selected!");
                throw new DataBaseNotSupportedException("Unknown Database type");
        }
    }
}
