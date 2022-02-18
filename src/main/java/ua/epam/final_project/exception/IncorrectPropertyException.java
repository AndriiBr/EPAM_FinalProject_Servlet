package ua.epam.final_project.exception;

public class IncorrectPropertyException extends Exception{

    public IncorrectPropertyException() {
        super("Incorrect properties file");
    }

    public IncorrectPropertyException(String errorMessage) {
        super(errorMessage);
    }
}
