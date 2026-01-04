package org.example.repositories;

import org.example.entities.Commute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommuteRepository extends JpaRepository<Commute, Long> {
}
