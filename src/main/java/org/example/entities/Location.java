package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "artPieceLocation")
    @Builder.Default
    private Set<ArtPiece> locationArtPieces = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District locationDistrict;

    //TODO DODAĆ METODĘ ADD ART PIECE ŻEBY Z TEJ STRONY ART PIECE TEŻ BYŁ POŁĄCZONY Z LOCATION
    //TODO DODAĆ METODĘ ADD LOCATION W DISTRICT ABY OD STRONY DISTRICT LOCATION BYŁO RÓWNIEŻ PODŁĄCZONE

    public void addArtPiece(ArtPiece artPiece){
        if (artPiece == null) throw new IllegalArgumentException("ArtPiece entity is null");
        locationArtPieces.add(artPiece);
        artPiece.setArtPieceLocation(this);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<? extends Location> thisClass = Hibernate.getClass(this);
        Class<?> otherClass = Hibernate.getClass(object);
        if (thisClass != otherClass) return false;
        Location other = (Location) object;
        return id != null && this.id.equals(other.id);
    }

    @Override
    public final int hashCode() {
        return Hibernate.getClass(this).hashCode();
    }

}
