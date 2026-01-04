package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String districtName;

    @Column
    private String districtZipCode;

    @Column
    private Long districtArtPiecesCount;

    @Column
    private Long districtResidentsCount;

    @OneToMany(mappedBy = "artPieceDistrict", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArtPiece> districtArtPieces;

    @OneToMany(mappedBy = "appUserLiveInDistrict")
    private List<AppUser> districtAppUsers;

    @OneToMany(mappedBy = "commuteThroughDistrict", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Commute> districtThroughCommutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City districtCity;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<? extends District> thisClass = Hibernate.getClass(this);
        Class<?> otherClass = Hibernate.getClass(object);
        if (thisClass != otherClass) return false;
        District other = (District) object;
        return id != null && id.equals(other.id);
    }

    @Override
    public final int hashCode() {
        return org.hibernate.Hibernate.getClass(this).hashCode();
    }

    public void addArtPiece(ArtPiece artPiece){
        getDistrictArtPieces().add(artPiece);
        artPiece.setArtPieceDistrict(this);
        Long artPiecesInDistrictCurrent = getDistrictArtPiecesCount();
        setDistrictArtPiecesCount(artPiecesInDistrictCurrent + 1);
    }

}
