package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.repositories.ActorRepository;

public class ActorService {
    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }


}