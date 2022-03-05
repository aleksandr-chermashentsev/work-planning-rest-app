package com.achermashentsev.workplanningservice.model;

import lombok.Value;

import java.time.LocalDate;

@Value
public class Shift implements IShift {

    LocalDate date;

    ShiftSlot slot;

}
