package ru.apashkevich.cityapp.exception;

public class ValidationException extends CityBaseException {

    public ValidationException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public ValidationException(String errorMessage) {
        super(errorMessage);
    }
}
