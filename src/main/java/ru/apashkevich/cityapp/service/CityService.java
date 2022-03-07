package ru.apashkevich.cityapp.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.apashkevich.cityapp.dao.CityDaoAdapter;
import ru.apashkevich.cityapp.dto.CityResponseDto;
import ru.apashkevich.cityapp.exception.ValidationException;
import ru.apashkevich.cityapp.model.City;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CityService {

    private final static Integer DEFAULT_LIMIT = 100;
    private final static Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");

    private final CityDaoAdapter cityDaoAdapter;
    private final CacheManager cacheManager;

    public CityResponseDto getCities(Integer offset, Integer limit, String searchText) {
        limit = limit > DEFAULT_LIMIT ? DEFAULT_LIMIT : limit;
        Integer totalRows = cityDaoAdapter.countRows(prepareText(searchText));
        List<City> cities = cityDaoAdapter.getCities(offset, limit, searchText);
        return CityResponseDto.builder()
                .cities(cities)
                .total(totalRows)
                .build();
    }

    public void updateCity(Integer id, City city) {
        String cityName = StringUtils.trimToNull(city.getName());
        String photoName = StringUtils.trimToNull(city.getPhoto());
        if (isNull(cityName) || isNull(photoName)) {
            throw new ValidationException("Passed parameters are not valid");
        }
        cityDaoAdapter.updateCity(id, cityName, photoName);
        cacheManager.getCache("rowCache").clear();
    }

    private String prepareText(String text){
        text = StringUtils.trimToNull(text);
        if (isNull(text)){
            return null;
        }
        return SPECIAL_REGEX_CHARS.matcher(text).replaceAll("\\\\$0");
    }
}
