package com.achermashentsev.workplanningservice.model;

import java.time.LocalDate;

public interface IShift {

    LocalDate getDate();

    ShiftSlot getSlot();
}
