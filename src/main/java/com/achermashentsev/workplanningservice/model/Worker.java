package com.achermashentsev.workplanningservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Worker implements IWorker {

    @Getter private final IPersonalInfo personalInfo;

    private final Map<LocalDate, IShift> shiftsMap = new HashMap<>();

    @Override
    public boolean assignShift(IShift shift) {
        if (shiftsMap.containsKey(shift.getDate())) {
            return false;
        }
        shiftsMap.put(shift.getDate(), shift);
        return true;
    }

    @Override
    public boolean unassignShift(LocalDate shiftDate) {
        return shiftsMap.remove(shiftDate) != null;
    }

    @Override
    public Stream<IShift> getAllShifts() {
        return shiftsMap.values().stream();
    }

}
