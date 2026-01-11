package org.example.repositories;

import org.example.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT photo FROM Photo photo WHERE photo.photoUrl = :photoUrl")
    Optional<Photo> findPhotoByUrl(@Param("photoUrl") String photoUrl);
}
