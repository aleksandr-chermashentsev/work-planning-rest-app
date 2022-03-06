package com.achermashentsev.workplanningservice.service;

import com.achermashentsev.workplanningservice.controller.exceptions.ShiftIsAlreadyPresentException;
import com.achermashentsev.workplanningservice.controller.exceptions.ShiftNotFoundException;
import com.achermashentsev.workplanningservice.controller.exceptions.WorkerNotFoundException;
import com.achermashentsev.workplanningservice.model.*;
import com.achermashentsev.workplanningservice.model.json.ShiftJsonModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorkersService
        implements IWorkersService {

    private final Map<Integer, Worker> workers = new ConcurrentHashMap<>();

    private final AtomicInteger workersIdSupplier = new AtomicInteger();

    @Override
    public Stream<WorkerJsonModel> getAllWorkers() {
        return workers.entrySet().stream()
                      .map(entry -> toJsonModel(entry.getKey(), entry.getValue()));
    }

    @Override
    public WorkerJsonModel getWorker(int workerId) {
        return toJsonModel(workerId, getWorkerModel(workerId));
    }

    @Override
    public WorkerJsonModel createWorker(String firstName, String lastName) {
        var nextId = workersIdSupplier.incrementAndGet();
        var worker = new Worker(new PersonalInfo(firstName, lastName));
        workers.put(nextId, worker);
        return toJsonModel(nextId, worker);
    }

    @Override
    public ShiftJsonModel newShift(int workerId, LocalDate date, ShiftSlot slot) {
        var worker = getWorkerModel(workerId);
        var shift = new Shift(date, slot);
        if (worker.assignShift(shift)) {
            return toJsonModel(shift);
        }
        throw new ShiftIsAlreadyPresentException(date, slot);
    }

    private Worker getWorkerModel(int workerId) {
        return Optional.ofNullable(workers.get(workerId))
                       .orElseThrow(() -> new WorkerNotFoundException(workerId));
    }

    @Override
    public void removeWorker(int workerId) {
        if (workers.remove(workerId) == null) {
            throw new WorkerNotFoundException(workerId);
        }
    }

    @Override
    public Stream<ShiftJsonModel> getShifts(int workerId) {
        return getWorkerModel(workerId).getAllShifts().map(this::toJsonModel);
    }

    @Override
    public void removeShift(int workerId, LocalDate shiftDate) {
        if (!getWorkerModel(workerId).unassignShift(shiftDate)) {
            throw new ShiftNotFoundException(workerId, shiftDate);
        }
    }

    private WorkerJsonModel toJsonModel(int workerId, IWorker worker) {
        return new WorkerJsonModel(
                workerId,
                worker.getPersonalInfo().getFirstName(),
                worker.getPersonalInfo().getLastName(),
                worker.getAllShifts().map(this::toJsonModel).collect(Collectors.toList())
        );
    }

    private ShiftJsonModel toJsonModel(IShift shift) {
        return new ShiftJsonModel(shift.getDate(), shift.getSlot());
    }

}
