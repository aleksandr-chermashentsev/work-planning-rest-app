package com.achermashentsev.workplanningservice.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkerNotFoundException extends RuntimeException {

    public WorkerNotFoundException(int id) {
        super(format("Worker with id %s not found", id));
    }
}
