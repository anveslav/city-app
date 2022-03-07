package ru.apashkevich.cityapp.dto;

import lombok.Builder;
import lombok.Value;
import ru.apashkevich.cityapp.model.City;

import java.util.List;

@Value
@Builder
public class CityResponseDto {
    List<City> cities;
    Integer total;
}
