package com.achermashentsev.workplanningservice.model;

import com.achermashentsev.workplanningservice.model.json.ShiftJsonModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerJsonModel {

    private int id;
    private String firstName;
    private String lastName;
    private List<ShiftJsonModel> shifts;

}
