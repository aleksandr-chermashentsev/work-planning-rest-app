package com.achermashentsev.workplanningservice.model;

import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * An model for worker. Could have only one shift per day
 */
public interface IWorker {

    /**
     * @return personal info of worker such as name/age etc.
     */
    IPersonalInfo getPersonalInfo();

    /**
     * @param shift shift for assign
     * @return {@code true} if successfully assign new shift to worker {@code false} otherwise
     */
    boolean assignShift(IShift shift);

    /**
     * @param date date for which required to unassign shift
     * @return {@code true} if successfully unassign shift from worker {@code false} otherwise
     */
    boolean unassignShift(LocalDate date);

    /**
     * @return all active shifts for worker
     */
    Stream<IShift> getAllShifts();
}
