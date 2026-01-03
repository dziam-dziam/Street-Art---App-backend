package org.example.repositories;

import org.example.entities.ArtPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtPieceRepository extends JpaRepository<ArtPiece, Long> {
}
