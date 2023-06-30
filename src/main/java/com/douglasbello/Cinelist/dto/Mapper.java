package com.douglasbello.Cinelist.dto;

import com.douglasbello.Cinelist.entities.TVShow;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.entities.enums.Gender;

public class Mapper {

    public static TVShow tvShowDtoToEntity(TVShowDTO dto) {
        TVShow tvShow = new TVShow(dto.getTitle(),dto.getOverview(),dto.getReleaseYear(),dto.getSeasonsAndEpisodes());
        return tvShow;
    }

    public static User userDtoToUser(UserDTO dto) {
        User user = new User(dto.getEmail(), dto.getUsername(), dto.getPassword(), Gender.valueOf(dto.getGender()));
        return user;
    }
}