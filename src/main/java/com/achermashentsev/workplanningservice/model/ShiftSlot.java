package com.achermashentsev.workplanningservice.model;

import org.apache.tomcat.jni.Local;

import java.time.LocalTime;

public enum ShiftSlot {
    EIGHT(LocalTime.of(0, 0), LocalTime.of(8, 0)),
    SIXTEEN(LocalTime.of(8, 0), LocalTime.of(16, 0)),
    TWENTY_FOUR(LocalTime.of(16, 0), LocalTime.of(0, 0));

    private LocalTime start;
    private LocalTime end;
    ShiftSlot(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }
}
