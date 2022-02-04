package ua.epam.final_project.exception;

public class DataBaseNotSupportedException extends Exception{

    public DataBaseNotSupportedException() {
        super("Unknown database");
    }

    public DataBaseNotSupportedException(String errorMessage) {
        super(errorMessage);
    }
}
