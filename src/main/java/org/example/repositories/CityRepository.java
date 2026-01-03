package org.example.repositories;

import org.example.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT city FROM City city WHERE city.cityName = :cityName")
    Optional<City> findByCityName(@Param("cityName") String cityName);
}
