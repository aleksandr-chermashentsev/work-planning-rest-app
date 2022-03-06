package com.achermashentsev.workplanningservice.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShiftNotFoundException extends RuntimeException {

    public ShiftNotFoundException(int workerId, LocalDate shiftDate) {
        super(format("Shift at date %s not found for worker %s", shiftDate, workerId));
    }
}
