package ru.apashkevich.cityapp.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ru.apashkevich.cityapp.exception.CityDatabaseException;
import ru.apashkevich.cityapp.exception.EntityNotFoundException;
import ru.apashkevich.cityapp.model.City;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CityDaoAdapter {

    private final CityDao cityDao;

    @Cacheable("rowCache")
    public Integer countRows(String searchText) {
        try {
            return cityDao.countRows(searchText);
        } catch (DataAccessException e) {
            throw new CityDatabaseException("An error occurred during count rows", e);
        }
    }

    public List<City> getCities(Integer offset, Integer limit, String searchText) {
        try {
            return cityDao.getCities(offset, limit, searchText);
        } catch (DataAccessException e) {
            throw new CityDatabaseException("An error occurred during getting cities", e);
        }
    }

    public void updateCity(Integer id, String cityName, String cityPhoto) {
        try {
            int i = cityDao.updateCity(id, cityName, cityPhoto);
            if (i == 0) {
                throw new EntityNotFoundException("City not found with id: " + id);
            }
        } catch (DataAccessException e) {
            throw new CityDatabaseException("An error occurred during updating city", e);
        }
    }
}
