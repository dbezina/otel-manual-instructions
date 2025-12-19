package org.bezina.actor.controller;


import org.bezina.actor.dto.ActorDto;
import org.bezina.actor.exception.ActorNotFoundException;
import org.bezina.actor.service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActorController {

    private static final Logger log = LoggerFactory.getLogger(ActorController.class);

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/api/actors/{id}")
    public ResponseEntity<ActorDto> getActor(@PathVariable Integer id) {
        log.info("request received for actor id: {}", id);
        System.out.println("request received for actor id: " + id);
        return this.actorService.getActor(id)
                                .map(ResponseEntity::ok)
                                .orElseThrow(() -> new ActorNotFoundException(id));
    }

    @GetMapping("/api/actors" )
    public ResponseEntity<List<ActorDto>> getActors() throws Exception {
        log.info("request received for actors");
        return this.actorService.getActors()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("can't return actors"));
    }

}
