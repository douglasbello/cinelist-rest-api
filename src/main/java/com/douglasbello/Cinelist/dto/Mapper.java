package com.douglasbello.Cinelist.dto;

import com.douglasbello.Cinelist.entities.TVShow;

public class Mapper {

    public static TVShow tvShowDtoToEntity(TVShowDTO dto) {
        TVShow tvShow = new TVShow(dto.getTitle(),dto.getOverview(),dto.getReleaseYear(),dto.getSeasonsAndEpisodes());
        return tvShow;
    }
}