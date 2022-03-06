package com.achermashentsev.workplanningservice.model;

import java.time.LocalDate;

/**
 * Shift for worker
 */
public interface IShift {

    /**
     * @return date of shift
     */
    LocalDate getDate();

    /**
     * @return timeslot for shift
     */
    ShiftSlot getSlot();
}
