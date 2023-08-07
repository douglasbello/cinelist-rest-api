package com.douglasbello.Cinelist.dtos.mapper;

import com.douglasbello.Cinelist.dtos.*;
import com.douglasbello.Cinelist.entities.*;
import com.douglasbello.Cinelist.entities.enums.UserRole;
import com.douglasbello.Cinelist.services.MovieService;
import com.douglasbello.Cinelist.services.TVShowService;
import com.douglasbello.Cinelist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    @Autowired
    private static MovieService movieService;

    @Autowired
    private static TVShowService tvShowService;

    @Autowired
    private static UserService userService;

    public static TVShow dtoToTVShow(TVShowDTO dto) {
        TVShow tvShow = new TVShow(dto.getTitle(), dto.getOverview(), dto.getReleaseYear(), dto.getDirectors(),
                dto.getSeasonsAndEpisodes(), dto.getGenres(), dto.getActors());
        return tvShow;
    }

    public static Movie dtoToMovie(MovieDTO dto) {
        Movie movie = new Movie(dto.getTitle(), dto.getOverview(), dto.getReleaseYear(), dto.getDirectors(), dto.getGenres(), dto.getActors());
        return movie;
    }

    public static User dtoToUser(UserSignInDTO dto) {
        User user = new User(dto.getEmail(), dto.getUsername(), dto.getPassword(), dto.getGender(), UserRole.USER);
        return user;
    }

    public static Actor dtoToActor(ActorInputDTO dto) {
        Actor actor = new Actor(dto.getName(), dto.getGender());
        actor.setBirthDate(dto.getBirthDate());
        return actor;
    }

    public static Director dtoToDirector(DirectorInputDTO dto) {
        Director director = new Director(dto.getName(), dto.getGender());
        director.setBirthDate(dto.getBirthDate());
        return director;
    }

    public static Genres dtoToGenres(GenresDTO dto) {
        Genres genres = new Genres(dto.getGenre());
        return genres;
    }

    public static Comment dtoToComment(CommentDTO dto) {
        if ( movieService.findById(dto.getShowOrMovieId()) != null && userService.findById(dto.getUserId()) != null ) {
            User user = userService.findById(dto.getUserId());
            Movie movie = movieService.findById(dto.getShowOrMovieId());
            return new Comment(user, movie, dto.getComment());
        }
        if ( tvShowService.findById(dto.getShowOrMovieId()) != null && userService.findById(dto.getUserId()) != null ) {
            User user = userService.findById(dto.getUserId());
            TVShow tvShow = tvShowService.findById(dto.getShowOrMovieId());
            return new Comment(user, tvShow, dto.getComment());
        }
        return null;
    }
}