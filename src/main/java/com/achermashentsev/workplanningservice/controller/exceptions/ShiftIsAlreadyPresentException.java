package com.achermashentsev.workplanningservice.controller.exceptions;

import com.achermashentsev.workplanningservice.model.ShiftSlot;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShiftIsAlreadyPresentException
        extends RuntimeException {

    public ShiftIsAlreadyPresentException(LocalDate date, ShiftSlot slot) {
        super(format("Shift with date %s already present, so we can't book slot %s", date, slot));
    }

}
