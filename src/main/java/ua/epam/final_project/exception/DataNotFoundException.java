package ua.epam.final_project.exception;

public class DataNotFoundException extends Exception{

    public DataNotFoundException() {
        super("Data not found in DB by specified key");
    }

    public DataNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
