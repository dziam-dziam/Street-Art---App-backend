package org.example.repositories;

import org.example.entities.ArtPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtPieceRepository extends JpaRepository<ArtPiece, Long> {

    @Query("SELECT artpiece FROM ArtPiece artpiece WHERE artpiece.artPieceDistrict.districtName = :districtName ")
    List<ArtPiece> getArtPiecesFromDistrict(@Param("districtName") String districtName);
}
