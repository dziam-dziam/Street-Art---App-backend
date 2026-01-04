package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cityName;

    @Column
    private Long cityResidentsCount;

    @OneToMany(mappedBy = "appUserCity")
    private Set<AppUser> cityAppUsers;

    @OneToMany(mappedBy = "districtCity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<District> cityDistricts;

}
