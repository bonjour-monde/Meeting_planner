package com.project.planner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NoPossibleRoomException extends RuntimeException {
    public NoPossibleRoomException(String message) {
        super(message);
    }
}
