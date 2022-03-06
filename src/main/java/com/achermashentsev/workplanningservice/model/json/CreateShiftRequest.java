package com.achermashentsev.workplanningservice.model.json;

import com.achermashentsev.workplanningservice.model.ShiftSlot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateShiftRequest {

    LocalDate date;

    ShiftSlot slot;

}
