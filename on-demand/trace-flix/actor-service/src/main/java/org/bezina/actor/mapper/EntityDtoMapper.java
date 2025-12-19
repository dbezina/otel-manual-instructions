package org.bezina.actor.mapper;


import org.bezina.actor.dto.ActorDto;
import org.bezina.actor.entity.Actor;

public class EntityDtoMapper {

    public static ActorDto toDto(Actor actor){
        return new ActorDto(
                actor.getId(),
                actor.getName()
        );
    }

}
