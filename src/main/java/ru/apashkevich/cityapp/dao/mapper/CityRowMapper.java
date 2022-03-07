package ru.apashkevich.cityapp.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.apashkevich.cityapp.model.City;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        return City.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .photo(rs.getString("photo"))
                .build();

    }
}
