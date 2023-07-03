package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.TVShow;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.entities.enums.Gender;

public class Mapper {

    public static TVShow dtoToTVShow(TVShowDTO dto) {
        TVShow tvShow = new TVShow(dto.getTitle(),dto.getOverview(),dto.getReleaseYear(),dto.getSeasonsAndEpisodes());
        return tvShow;
    }

    public static User dtoToUser(UserDTO dto) {
        User user = new User(dto.getEmail(), dto.getUsername(), dto.getPassword(), dto.getGender().getCode());
        return user;
    }
}