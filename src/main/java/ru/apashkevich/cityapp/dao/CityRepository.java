package ru.apashkevich.cityapp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.apashkevich.cityapp.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    Page<City> findAllByNameStartsWithIgnoreCase(Pageable pageable, String name);

}
