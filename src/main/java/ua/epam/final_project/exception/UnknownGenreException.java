package ua.epam.final_project.exception;

public class UnknownGenreException extends Exception{

    public UnknownGenreException() {
        super("Unknown genre");
    }

    public UnknownGenreException(String errorMessage) {
        super(errorMessage);
    }
}
