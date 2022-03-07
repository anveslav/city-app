package ru.apashkevich.cityapp.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.apashkevich.cityapp.dao.mapper.CityRowMapper;
import ru.apashkevich.cityapp.model.City;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CityDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CityRowMapper cityRowMapper;

    public Integer countRows(String searchText){
        String sql = "SELECT COUNT(*) FROM city.city " +
                (nonNull(searchText) ? "WHERE name ILIKE :searchText || '%'" :  "");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("searchText", searchText);
        return jdbcTemplate.queryForObject(sql, mapSqlParameterSource, Integer.class);
    }

    public List<City> getCities(Integer offset, Integer limit, String searchText) {
        String sql = "SELECT id, name, photo FROM city.city " +
                (nonNull(searchText) ? "WHERE name ILIKE :searchText || '%'" :  "") +
                " ORDER BY id " +
                " LIMIT :limit " +
                " OFFSET :offset";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("offset", offset)
                .addValue("limit", limit)
                .addValue("searchText", searchText);
        return jdbcTemplate.query(sql, mapSqlParameterSource, cityRowMapper);
    }

    public int updateCity(Integer id, String cityName, String cityPhoto) {
        String sql = "UPDATE city.city SET name = :cityName, photo = :cityPhoto" +
                " WHERE id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("cityName", cityName)
                .addValue("cityPhoto", cityPhoto);
        int update = jdbcTemplate.update(sql, mapSqlParameterSource);
        System.out.println(update);
        return update;
    }

}
