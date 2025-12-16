package org.bezina.review.mapper;


import org.bezina.review.dto.ReviewDto;
import org.bezina.review.entity.Review;

public class EntityDtoMapper {

    public static ReviewDto toDto(Review review){
        return new ReviewDto(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getReviewer()
        );
    }

}
