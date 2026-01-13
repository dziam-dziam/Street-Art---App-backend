package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.MeansOfTransport;
import org.hibernate.Hibernate;

import java.util.Set;


@Entity
@Builder
@Getter
@Setter
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

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<? extends Commute> thisClass = Hibernate.getClass(this);
        Class<?> otherClass = Hibernate.getClass(object);
        if (thisClass != otherClass) return false;
        Commute other = (Commute) object;
        return id != null && id.equals(other.id);
    }

    @Override
    public final int hashCode() {
        return Hibernate.getClass(this).hashCode();
    }

}
