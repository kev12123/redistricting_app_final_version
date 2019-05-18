package com.appRestful.api.component.data;

public class NoDataException extends RuntimeException{
    public NoDataException() {
    }

    public NoDataException(String message) {
        super(message);
    }
}
