package org.example.repositories;

import org.example.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("SELECT district FROM District district WHERE district.districtName = :districtName")
    Optional<District> findByDistrictName(@Param("districtName") String districtName);

    @Query("SELECT district FROM District district WHERE district.districtZipCode = :districtZipCode")
    Optional<District> findByDistrictZipCode(@Param("districtZipCode") String districtZipCode);
}
