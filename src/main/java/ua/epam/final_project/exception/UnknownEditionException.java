package ua.epam.final_project.exception;

public class UnknownEditionException extends Exception{

    public UnknownEditionException() {
        super("Unknown edition");
    }

    public UnknownEditionException(String errorMessage) {
        super(errorMessage);
    }
}
