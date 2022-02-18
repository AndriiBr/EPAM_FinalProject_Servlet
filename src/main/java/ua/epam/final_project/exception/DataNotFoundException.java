package ua.epam.final_project.exception;

public class DataNotFoundException extends Exception{

    public DataNotFoundException() {
        super("No data found in DB");
    }

    public DataNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
