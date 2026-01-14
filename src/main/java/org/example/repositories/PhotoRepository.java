package org.example.repositories;

import org.example.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Set<Photo> findAllByArtPieceOnPhoto_Id(Long artPieceId);
}
