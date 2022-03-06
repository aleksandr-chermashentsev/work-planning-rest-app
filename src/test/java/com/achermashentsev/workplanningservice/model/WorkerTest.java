package com.achermashentsev.workplanningservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkerTest {

    @Test
    public void shouldAssignShift() {
        var worker = new Worker(new PersonalInfo("John", "Doe"));
        var shift = new Shift(LocalDate.of(2022, 1, 1), ShiftSlot.EIGHT);
        assertTrue(worker.assignShift(shift));
        var shifts = worker.getAllShifts().collect(Collectors.toList());
        assertEquals(1, shifts.size());
        assertEquals(shift, shifts.get(0));
    }

    @Test
    public void shouldAssignTwoShiftsDifferentDays() {
        var worker = new Worker(new PersonalInfo("John", "Doe"));
        var shift1 = new Shift(LocalDate.of(2022, 1, 1), ShiftSlot.EIGHT);
        var shift2 = new Shift(LocalDate.of(2022, 1, 2), ShiftSlot.EIGHT);
        assertTrue(worker.assignShift(shift1));
        assertTrue(worker.assignShift(shift2));
        var shifts = worker.getAllShifts().collect(Collectors.toList());
        assertEquals(2, shifts.size());
        assertTrue(shifts.contains(shift1));
        assertTrue(shifts.contains(shift2));
    }

    @Test
    public void shouldNotAssignTwoShiftsTheSameDay() {
        var worker = new Worker(new PersonalInfo("John", "Doe"));
        var shift1 = new Shift(LocalDate.of(2022, 1, 1), ShiftSlot.EIGHT);
        var shift2 = new Shift(LocalDate.of(2022, 1, 1), ShiftSlot.EIGHT);
        assertTrue(worker.assignShift(shift1));
        assertFalse(worker.assignShift(shift2));
        var shifts = worker.getAllShifts().collect(Collectors.toList());
        assertEquals(1, shifts.size());
        assertTrue(shifts.contains(shift1));
    }

    @Test
    public void shouldUnassignShift() {
        var worker = new Worker(new PersonalInfo("John", "Doe"));
        var shift1 = new Shift(LocalDate.of(2022, 1, 1), ShiftSlot.EIGHT);
        assertTrue(worker.assignShift(shift1));
        assertTrue(worker.unassignShift(shift1.getDate()));
        var shifts = worker.getAllShifts().collect(Collectors.toList());
        assertEquals(0, shifts.size());
    }

    @Test
    public void shouldNotUnassignShift() {
        var worker = new Worker(new PersonalInfo("John", "Doe"));
        var shift1 = new Shift(LocalDate.of(2022, 1, 1), ShiftSlot.EIGHT);
        assertTrue(worker.assignShift(shift1));
        assertFalse(worker.unassignShift(LocalDate.of(2022, 1, 12)));
        var shifts = worker.getAllShifts().collect(Collectors.toList());
        assertEquals(1, shifts.size());
        assertTrue(shifts.contains(shift1));
    }

}