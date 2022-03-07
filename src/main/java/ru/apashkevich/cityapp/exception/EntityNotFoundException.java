package ru.apashkevich.cityapp.exception;

public class EntityNotFoundException extends CityBaseException {

    public EntityNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
