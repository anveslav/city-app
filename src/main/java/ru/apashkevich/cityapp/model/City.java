package ru.apashkevich.cityapp.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class City {
    private Integer id;
    private String name;
    private String photo;
}
