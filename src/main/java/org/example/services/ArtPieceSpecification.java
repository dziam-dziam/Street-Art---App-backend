package org.example.services;

import jakarta.persistence.criteria.JoinType;
import org.example.entities.ArtPiece;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;
import org.springframework.data.jpa.domain.Specification;

public class ArtPieceSpecification {

    public static Specification<ArtPiece> districtEquals(String districtName){
        return(root, query, criteriaBuilder) -> {
            if(districtName == null || districtName.isBlank()) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("artPieceDistrict").get("districtName"), districtName);
        };
    }

    public static Specification<ArtPiece> typeMember(ArtPieceTypes artPieceType){
        return(root,query,criteriaBuilder) -> {
            if(artPieceType == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.isMember(artPieceType, root.get("artPieceTypes"));
        };
    }

    public static Specification<ArtPiece> styleMember(ArtPieceStyles artPieceStyle){
        return(root, query, criteriaBuilder) -> {
            if(artPieceStyle == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.isMember(artPieceStyle, root.get("artPieceStyles"));
        };
    }

    public static Specification<ArtPiece> withLocationFetched(){
        return (root, query, criteriaBuilder) -> {
            root.fetch("artPieceLocation", JoinType.LEFT);
            root.fetch("artPieceDistrict", JoinType.LEFT);
            query.distinct(true);
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<ArtPiece> hasLocation() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotNull(root.get("artPieceLocation"));
    }

}
