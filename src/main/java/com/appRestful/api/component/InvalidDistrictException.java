package com.appRestful.api.component;

public class InvalidDistrictException extends RuntimeException{
    public InvalidDistrictException() {
    }

    public InvalidDistrictException(String message) {
        super(message);
    }
}
