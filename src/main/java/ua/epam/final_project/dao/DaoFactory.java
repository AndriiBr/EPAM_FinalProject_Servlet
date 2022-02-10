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
     * Creates Genre DAO
     * @return Genre DAO
     */
    public abstract IGenreDao getGenreDao();

    /**
     * Creates User-Edition DAO
     * @return User-Edition DAO
     */
    public abstract IUserEditionDao getUserEditionDao();

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

    public static DaoFactory getDaoFactory(DataBaseSelector dataBase) throws DataBaseNotSupportedException {
        switch (dataBase) {
            case MY_SQL:
                return new MySQLDaoFactory();
            case ORACLE:
                throw new DataBaseNotSupportedException(dataBase.toString());
            default:
                throw new DataBaseNotSupportedException("Unknown Database type");
        }
    }
}
