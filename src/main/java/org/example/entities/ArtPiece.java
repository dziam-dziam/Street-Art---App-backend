package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
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
    private List<Photo> artPiecePhotos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser artPieceAppUserWhoAddedIt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_id", nullable = false)
    private District artPieceDistrict;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location artPieceLocation;

}
