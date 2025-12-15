package org.bezina.movie.service;

import org.bezina.movie.client.ActorClient;
import org.bezina.movie.client.ReviewClient;
import org.bezina.movie.dto.MovieDto;
import org.bezina.movie.entity.Movie;
import org.bezina.movie.mapper.EntityDtoMapper;
import org.bezina.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository repository;
    private final ActorClient actorClient;
    private final ReviewClient reviewClient;

    public MovieService(MovieRepository repository, ActorClient actorClient, ReviewClient reviewClient) {
        this.repository = repository;
        this.actorClient = actorClient;
        this.reviewClient = reviewClient;
    }

    public Optional<MovieDto> getMovie(Integer movieId) {
        return this.repository.findById(movieId)
                              .map(this::buildDto);
    }

    private MovieDto buildDto(Movie movie) {
        var reviews = this.reviewClient.getReviews(movie.getId());
        var actors = movie.getActorIds()
                          .stream()
                          .map(this.actorClient::getActor) // intentional sequential calls
                          //.gather(Gatherers.mapConcurrent(5, this.actorClient::getActor))
                          .filter(Objects::nonNull)
                          .toList();
        return EntityDtoMapper.toDto(movie, actors, reviews);
    }

}
