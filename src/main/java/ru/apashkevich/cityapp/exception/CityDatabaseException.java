package ru.apashkevich.cityapp.exception;

public class CityDatabaseException extends CityBaseException {

    public CityDatabaseException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public CityDatabaseException(String errorMessage) {
        super(errorMessage);
    }

}
