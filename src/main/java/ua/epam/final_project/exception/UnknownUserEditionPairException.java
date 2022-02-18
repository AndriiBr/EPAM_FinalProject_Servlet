package ua.epam.final_project.exception;

public class UnknownUserEditionPairException extends Exception{

    public UnknownUserEditionPairException() {
        super("Unknown user-edition pair");
    }

    public UnknownUserEditionPairException(String errorMessage) {
        super(errorMessage);
    }
}
