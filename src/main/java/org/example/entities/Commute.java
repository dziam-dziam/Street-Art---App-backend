package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.MeansOfTransport;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int commuteTripsPerWeek;
    @Column
    private int commuteStartHour;
    @Column
    private int commuteStopHour;

    @ElementCollection(targetClass = MeansOfTransport.class)
    @CollectionTable(name = "commute_transport", joinColumns = @JoinColumn(name = "commute_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "means")
    private Set<MeansOfTransport> commuteMeansOfTransport;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser commutingAppUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_id", nullable = false)
    private District commuteThroughDistrict;
}
