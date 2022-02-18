package ua.epam.final_project.exception;

public class UnknownRoleException extends Exception{

    public UnknownRoleException() {
        super("Unknown role");
    }

    public UnknownRoleException(String errorMessage) {
        super(errorMessage);
    }
}
