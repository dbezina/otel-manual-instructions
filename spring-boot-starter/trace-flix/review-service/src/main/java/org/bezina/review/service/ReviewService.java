package main.java.org.bezina.review.service;

import main.java.org.bezina.review.dto.ReviewDto;
import main.java.org.bezina.review.mapper.EntityDtoMapper;
import main.java.org.bezina.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDto> getReviews(Integer movieId) {
        return this.reviewRepository.findByMovieId(movieId)
                                    .stream()
                                    .map(EntityDtoMapper::toDto)
                                    .toList();
    }

}
