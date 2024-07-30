package com.meta.cloud.exception;

public class AlreadyExistLoginIdException extends RuntimeException {

    private static final String MESSAGE = "User Id is already taken";

    public AlreadyExistLoginIdException() {
        super(MESSAGE);
    }
}
