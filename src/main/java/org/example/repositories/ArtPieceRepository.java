package org.example.repositories;

import org.example.dtos.artpiece.ArtPieceMapPointDto;
import org.example.entities.ArtPiece;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtPieceRepository extends JpaRepository<ArtPiece, Long> {

    @Query("SELECT artpiece FROM ArtPiece artpiece WHERE artpiece.artPieceDistrict.districtName = :districtName ")
    List<ArtPiece> getArtPiecesFromDistrict(@Param("districtName") String districtName);

    @Query("SELECT artpiece FROM ArtPiece artpiece WHERE " +
            "artpiece.artPieceLocation.locationLongitude = :artPieceLongitude " +
            "AND artpiece.artPieceLocation.locationLatitude = :artPieceLatitude")
    List<ArtPiece> getArtPiecesByLocationLatitudeAndLongitude(@Param("artPieceLatitude") double artPieceLatitude,
                                                              @Param("artPieceLongitude") double artPieceLongitude);
    @Query("""
        select new org.example.dtos.artpiece.ArtPieceMapPointDto(
            a.id,
            a.artPieceName,
            a.artPieceAddress,
            d.districtName,
            l.locationLatitude,
            l.locationLongitude
        )
        from ArtPiece a
        join a.artPieceLocation l
        join a.artPieceDistrict d
        where a.artPieceLocation is not null
    """)
    List<ArtPieceMapPointDto> findAllMapPoints();

    @EntityGraph(attributePaths = {
            "artPieceDistrict",
            "artPieceDistrict.districtCity",
            "artPieceTypes",
            "artPieceStyles",
            "artPieceTextLanguages",
            "artPiecePhotos"
    })
    @Query("select a from ArtPiece a where a.id = :id")
    Optional<ArtPiece> findDetailsById(@Param("id") Long id);

}
