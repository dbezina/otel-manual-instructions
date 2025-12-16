package org.bezina.actor.service;



import org.bezina.actor.dto.ActorDto;
import org.bezina.actor.mapper.EntityDtoMapper;
import org.bezina.actor.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Optional<ActorDto> getActor(Integer id) {
        this.simulateProcessingTime(id);
        return this.actorRepository.findById(id)
                                   .map(EntityDtoMapper::toDto);
    }

    // simulate slow processing
    private void simulateProcessingTime(Integer id){
        if(id <= 14)
            return;
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<List<ActorDto>> getActors() {
        return Optional.of(this.actorRepository.findAll()
                .stream().map(EntityDtoMapper::toDto).toList());
    }

}
