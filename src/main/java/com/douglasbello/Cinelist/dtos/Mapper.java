package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.*;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static TVShow dtoToTVShow(TVShowDTO dto) {
        TVShow tvShow = new TVShow(dto.getId(),dto.getTitle(),dto.getOverview(),dto.getReleaseYear(),dto.getDirectors(),
                dto.getSeasonsAndEpisodes(),dto.getGenres());
        return tvShow;
    }

    public static User dtoToUser(UserDTO dto) {
        User user = new User(dto.getEmail(), dto.getUsername(), dto.getPassword(), dto.getGender().getCode());
        return user;
    }

    public static Actor dtoToActor(ActorDTO dto) {
        Actor actor = new Actor(dto.getId(),dto.getName(),dto.getBirthDate(),dto.getGender());
        return actor;
    }

    public static Director dtoToDirector(DirectorDTO dto) {
        Director director = new Director(dto.getId(), dto.getName(), dto.getBirthDate(), dto.getGender().getCode());
        return director;
    }

    public static Genres dtoToGenres(GenresDTO dto) {
        Genres genres = new Genres(dto.getId(), dto.getGenre());
        return genres;
    }
}