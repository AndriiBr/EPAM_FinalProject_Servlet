package ua.epam.final_project.exception;

public class MultipartFormException extends Exception{

    public MultipartFormException() {
        super("Multipart extraction was interrupt");
    }

    public MultipartFormException(String errorMessage) {
        super(errorMessage);
    }
}
