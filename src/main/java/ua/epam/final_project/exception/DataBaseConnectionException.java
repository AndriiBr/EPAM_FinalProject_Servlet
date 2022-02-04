package ua.epam.final_project.exception;

public class DataBaseConnectionException extends Exception {

    public DataBaseConnectionException() {
        super("Failed to connect to the database");
    }

    public DataBaseConnectionException(String errorMessage) {
        super(errorMessage);
    }
}
