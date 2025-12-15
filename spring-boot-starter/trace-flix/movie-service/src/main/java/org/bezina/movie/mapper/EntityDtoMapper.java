package org.bezina.movie.mapper;


import org.bezina.movie.dto.ActorDto;
import org.bezina.movie.dto.MovieDto;
import org.bezina.movie.dto.ReviewDto;
import org.bezina.movie.entity.Movie;

import java.util.List;

public class EntityDtoMapper {

    public static MovieDto toDto(Movie movie, List<ActorDto> actors, List<ReviewDto> reviews){
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                actors,
                reviews
        );
    }

}
