package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
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
    private Long districtArtPiecesCount;

    @Column
    private Long districtResidentsCount;

    @OneToMany(mappedBy = "artPieceDistrict", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtPiece> districtArtPieces;

    @OneToMany(mappedBy = "appUserLiveInDistrict")
    private List<AppUser> districtAppUsers;

    @OneToMany(mappedBy = "commuteThroughDistrict", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Commute> districtThroughCommutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_dupa_id", referencedColumnName = "id")
    private City districtCity;

}
