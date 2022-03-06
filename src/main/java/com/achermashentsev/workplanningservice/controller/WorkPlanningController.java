package com.achermashentsev.workplanningservice.controller;

import com.achermashentsev.workplanningservice.controller.exceptions.WorkerNotFoundException;
import com.achermashentsev.workplanningservice.model.WorkerJsonModel;
import com.achermashentsev.workplanningservice.model.json.CreateShiftRequest;
import com.achermashentsev.workplanningservice.model.json.CreateWorkerRequest;
import com.achermashentsev.workplanningservice.model.json.ShiftJsonModel;
import com.achermashentsev.workplanningservice.service.IWorkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("worker")
public class WorkPlanningController {

    @Autowired
    private IWorkersService workersService;

    @GetMapping
    public List<WorkerJsonModel> getAllWorkers() {
        return workersService.getAllWorkers()
                             .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WorkerJsonModel getWorker(@PathVariable int id) {
        return workersService.getWorker(id);
    }

    @PostMapping()
    public WorkerJsonModel newWorker(@RequestBody CreateWorkerRequest request) {
        return workersService.createWorker(request.getFirstName(), request.getLastName());
    }

    @ResponseBody
    @DeleteMapping(path="/{id}")
    public void removeWorker(@PathVariable int id) {
        workersService.removeWorker(id);
    }

    @GetMapping("/{workerId}/shift")
    public List<ShiftJsonModel> getAllShifts(@PathVariable int workerId) {
        return workersService.getShifts(workerId).collect(Collectors.toList());
    }

    @PostMapping("/{workerId}/shift")
    public ShiftJsonModel newShift(@PathVariable int workerId, @RequestBody CreateShiftRequest request) {
        return workersService.newShift(workerId, request.getDate(), request.getSlot());
    }

    @DeleteMapping("/{workerId}/shift/{shiftDate}")
    public void removeShift(@PathVariable int workerId, @PathVariable String shiftDate) {
        workersService.removeShift(workerId, LocalDate.parse(shiftDate, DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
