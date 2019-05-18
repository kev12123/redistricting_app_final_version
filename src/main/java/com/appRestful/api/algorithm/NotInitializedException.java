package com.appRestful.api.algorithm;

public class NotInitializedException extends RuntimeException {
    public NotInitializedException() {
    }

    public NotInitializedException(String message) {
        super(message);
    }
}
