package ua.epam.final_project.exception;

public class UnknownUserException extends Exception{

    public UnknownUserException() {
        super("Unknown user");
    }

    public UnknownUserException(String errorMessage) {
        super(errorMessage);
    }
}
