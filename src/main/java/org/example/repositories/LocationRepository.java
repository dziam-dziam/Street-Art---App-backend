package org.example.repositories;

import org.example.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    //TODO ZROBIĆ FINDBY BY H,L,L ABY NIE TWORZYĆ DUPLIKATÓW
    // I ABY WYSYŁAĆ INFO ŻE JUŻ ISTNIEJE JAKIŚ ART PIECE W TEJ LOKACJI
}
