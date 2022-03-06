package com.achermashentsev.workplanningservice.model.json;

import com.achermashentsev.workplanningservice.model.ShiftSlot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftJsonModel {

    LocalDate date;

    ShiftSlot slot;

}
