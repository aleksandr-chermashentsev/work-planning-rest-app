package com.achermashentsev.workplanningservice.model;

import lombok.Value;

@Value
public class PersonalInfo
        implements IPersonalInfo {

    String firstName;

    String lastName;

}
