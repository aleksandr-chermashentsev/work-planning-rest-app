package com.achermashentsev.workplanningservice.model.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateWorkerRequest {
    private String firstName;
    private String lastName;
}
