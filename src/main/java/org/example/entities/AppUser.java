package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String appUserName;

    @Column(unique = true)
    @Email
    private String appUserEmail;

    @Column
    private String appUserPassword;

    @Column
    private String appUserNationality;

    @ElementCollection
    @CollectionTable(name = "languages_spoken", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "languages_spoken")
    private Set<String> appUserLanguagesSpoken;

    @OneToMany(mappedBy = "artPieceAppUserWhoAddedIt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtPiece> appUserArtPiecesAdded;

    @OneToMany(mappedBy = "commutingAppUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Commute> appUserCommutes;

    @ManyToOne()
    @JoinColumn(name = "city_id")
    private City appUserCity;

    @ManyToOne()
    @JoinColumn(name = "live_in_district_id")
    private District appUserLiveInDistrict;

}
