package com.achermashentsev.workplanningservice.service;

import com.achermashentsev.workplanningservice.controller.exceptions.ShiftIsAlreadyPresentException;
import com.achermashentsev.workplanningservice.controller.exceptions.ShiftNotFoundException;
import com.achermashentsev.workplanningservice.controller.exceptions.WorkerNotFoundException;
import com.achermashentsev.workplanningservice.model.IWorker;
import com.achermashentsev.workplanningservice.model.ShiftSlot;
import com.achermashentsev.workplanningservice.model.Worker;
import com.achermashentsev.workplanningservice.model.WorkerJsonModel;
import com.achermashentsev.workplanningservice.model.json.ShiftJsonModel;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * Service for operations with workers
 */
public interface IWorkersService {

    /**
     * @return all workers available in system
     */
    Stream<WorkerJsonModel> getAllWorkers();

    /**
     * @param id workerId
     * @return worker for requested id
     * @throws WorkerNotFoundException in case if can't find worker
     */
    WorkerJsonModel getWorker(int id);

    /**
     * Creates new worker with first and last name
     * @param firstName first name
     * @param lastName last name
     * @return brand new worker
     */
    WorkerJsonModel createWorker(String firstName, String lastName);

    /**
     * Creates new shift for specified workerId with date and slot
     * @param workerId workerId
     * @param date date for shift
     * @param slot time slot for shift
     * @return brand new shift
     * @throws WorkerNotFoundException in case if can't find worker
     * @throws ShiftIsAlreadyPresentException in case if there is shift already exist for specified date
     */
    ShiftJsonModel newShift(int workerId, LocalDate date, ShiftSlot slot);

    /**
     * Removes worker
     * @param workerId worker id for remove
     * @throws WorkerNotFoundException in case if can't find worker
     */
    void removeWorker(int workerId);

    /**
     * Returns all shifts of worker
     * @param workerId specified worker id
     * @return stream of all shifts for worker
     * @throws WorkerNotFoundException in case if can't find worker
     */
    Stream<ShiftJsonModel> getShifts(int workerId);

    /**
     * Removes shift for worker
     * @param workerId specified worker id
     * @param shiftDate date on which shift was assigned
     * @throws WorkerNotFoundException in case if can't find worker
     * @throws ShiftNotFoundException in case if can't find shift at specified date
     */
    void removeShift(int workerId, LocalDate shiftDate);

}
