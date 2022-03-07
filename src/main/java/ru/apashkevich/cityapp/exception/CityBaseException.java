package ru.apashkevich.cityapp.exception;

public class CityBaseException extends RuntimeException {

    public CityBaseException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public CityBaseException(String errorMessage) {
        super(errorMessage);
    }

    public CityBaseException() {
        super();
    }
}
