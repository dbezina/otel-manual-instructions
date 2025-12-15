package main.java.org.bezina.review.mapper;

import main.java.org.bezina.review.dto.ReviewDto;
import main.java.org.bezina.review.entity.Review;

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
