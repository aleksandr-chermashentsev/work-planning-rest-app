package com.achermashentsev.workplanningservice.model;

import java.util.stream.Stream;

public interface IWorker {

    IPersonalInfo getPersonalInfo();

    boolean assignShift(IShift shift);

    boolean unassignShift(IShift shift);

    Stream<IShift> getAllShifts();
}
