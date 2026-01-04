package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
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
    private Set<Commute> appUserCommutes = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "city_id")
    private City appUserCity;

    @ManyToOne()
    @JoinColumn(name = "live_in_district_id")
    private District appUserLiveInDistrict;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<? extends AppUser> thisClass = Hibernate.getClass(this);
        Class<?> otherClass = Hibernate.getClass(object);
        if (thisClass != otherClass) return false;
        AppUser other = (AppUser) object;
        return id != null && id.equals(other.id);
    }

    @Override
    public final int hashCode() {
        return org.hibernate.Hibernate.getClass(this).hashCode();
    }

    public void addCommute(Commute commute) {
        getAppUserCommutes().add(commute);
        commute.setCommutingAppUser(this);
    }

}
