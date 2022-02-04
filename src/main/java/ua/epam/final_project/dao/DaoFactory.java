package ua.epam.final_project.dao;


import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;

public abstract class DaoFactory {

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
     * Opens connection to Data Source
     * @throws DataBaseConnectionException if unable to open connection
     */
    public abstract void openConnection() throws DataBaseConnectionException;

    /**
     * Closes connection to Data Source
     * @throws DataBaseConnectionException if unable to close connection
     */
    public abstract void closeConnection() throws DataBaseConnectionException;

    /**
     * Opens DB data transaction
     * @throws DataBaseConnectionException if unable to open data transaction
     */
    public abstract void beginTransaction() throws DataBaseConnectionException;

    /**
     * Commits transaction results and closes transaction
     * @throws DataBaseConnectionException if unable to commit data transaction
     */
    public abstract void commitTransaction() throws DataBaseConnectionException;

    /**
     * Rollbacks transaction results and closes transaction
     * @throws DataBaseConnectionException if unable to rollback transaction
     */
    public abstract void rollbackTransaction() throws DataBaseConnectionException;

    public static DaoFactory getDaoFactory(DataBaseSelector dataBase) throws
            DataBaseNotSupportedException,
            DataBaseConnectionException {
        switch (dataBase) {
            case MY_SQL:
                return MySQLDaoFactory.getInstance();
            case ORACLE:
                throw new DataBaseNotSupportedException(dataBase.toString());
            default:
                throw new DataBaseNotSupportedException("Unknown Database type");
        }
    }
}
