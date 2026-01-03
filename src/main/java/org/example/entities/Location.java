package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double locationLongitude;

    @Column
    private double locationLatitude;

    @Column
    private double locationHeight;

    //TODO w tym miejscu użytkownik dostawać będzie alert o tym że w danej lokacji już istnieje jakiś art piece.
    @OneToMany(mappedBy = "artPieceLocation", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<ArtPiece> locationArtPieces;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District locationDistrict;

}
