package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtPiece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String artPieceAddress;
    @Column
    private String artPieceName;
    @Column
    private boolean artPieceContainsText;
    @Column
    private String artPiecePosition;
    @ColumnDefault("'Lack of description'")
    private String artPieceUserDescription;

    @ElementCollection
    @CollectionTable(name = "art_piece_text_language", joinColumns = @JoinColumn(name = "art_piece_id"))
    @Column(name = "text_language")
    private Set<String> artPieceTextLanguages;

    @ElementCollection
    @CollectionTable(name = "art_piece_type", joinColumns = @JoinColumn(name = "art_piece_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Set<ArtPieceTypes> artPieceTypes;

    @ElementCollection
    @CollectionTable(name = "art_piece_style", joinColumns = @JoinColumn(name = "art_piece_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "style", nullable = false)
    private Set<ArtPieceStyles> artPieceStyles;

    @OneToMany(mappedBy = "artPieceOnPhoto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Photo> artPiecePhotos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser artPieceAppUserWhoAddedIt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_id", nullable = false)
    private District artPieceDistrict;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location artPieceLocation;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<? extends ArtPiece> thisClass = Hibernate.getClass(this);
        Class<?> otherClass = Hibernate.getClass(object);
        if (thisClass != otherClass) return false;
        ArtPiece other = (ArtPiece) object;
        return id != null && id.equals(other.id);
    }

    @Override
    public final int hashCode() {
        return org.hibernate.Hibernate.getClass(this).hashCode();
    }

}
