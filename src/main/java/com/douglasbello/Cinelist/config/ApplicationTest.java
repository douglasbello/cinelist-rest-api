package com.douglasbello.Cinelist.config;

import com.douglasbello.Cinelist.dtos.ActorDTO;
import com.douglasbello.Cinelist.dtos.UserDTO;
import com.douglasbello.Cinelist.entities.*;
import com.douglasbello.Cinelist.entities.enums.Gender;
import com.douglasbello.Cinelist.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("test")
public class ApplicationTest implements CommandLineRunner {

    private final UserService userService;

    private final MovieService movieService;

    private final CommentService commentService;

    private final TVShowService tvShowService;

    private final ActorService actorService;

    private final GenresService genresService;

    private final DirectorService directorService;


    public ApplicationTest(UserService userService, MovieService movieService,
                           CommentService commentService, TVShowService tvShowService,
                           ActorService actorService, GenresService genresService, DirectorService directorService) {
        this.userService = userService;
        this.movieService = movieService;
        this.commentService = commentService;
        this.tvShowService = tvShowService;
        this.actorService = actorService;
        this.genresService = genresService;
        this.directorService = directorService;
    }

    @Override
    public void run(String... args) {
//
        User user = new User("user01@outlook.com","user01","user01", Gender.MALE);
        userService.insert(user);

        Genres scienceFiction = new Genres(null,"Science fiction");
        Genres adventure = new Genres(null,"Adventure");
        Genres crime = new Genres(null,"Crime");
        genresService.insertAll(Arrays.asList(scienceFiction,adventure,crime));

        LocalDate christopherBirthDate = LocalDate.of(1970,07,30);
        Director christopherNolan = new Director(null,"Christopher Nolan",christopherBirthDate,Gender.MALE);
        christopherNolan = directorService.insert(christopherNolan);

        LocalDate matthewBirthDate = LocalDate.of(1969,11,4);
        Actor matthew = new Actor(null,"Matthew McConaughey",matthewBirthDate,Gender.MALE);
        matthew = actorService.insert(new ActorDTO(matthew));
        LocalDate jessicaBirthDate = LocalDate.of(1977,3,24);
        Actor jessica = new Actor(null, "Jessica Chastain", jessicaBirthDate, Gender.FEMALE);
        jessica = actorService.insert(new ActorDTO(jessica));
        LocalDate anneBirthDate = LocalDate.of(1982,11,12);
        Actor anne = new Actor(null, "Anne Hathaway", anneBirthDate, Gender.FEMALE);
        anne = actorService.insert(new ActorDTO(anne));

        Movie movie = new Movie("Interstellar", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.", "2014");
        movie = movieService.insert(movie);
        movie.getGenre().add(scienceFiction);
        movie.getGenre().add(adventure);
        movie.getDirectors().add(christopherNolan);
        movie.getActors().addAll(Arrays.asList(matthew,jessica,anne));
        movie = movieService.insert(movie);
//
//
//        Comment comment = new Comment(user,movie, "Great movie!");
//        commentService.insert(comment);
//
//        TVShow tvShow = new TVShow("Breaking Bad","A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future.","Vince Gilligan", "2008");
//        tvShow.putSeasonAndEpisodeAndUpdate(1,7);
//        tvShow.putSeasonAndEpisodeAndUpdate(2,13);
//        tvShow.putSeasonAndEpisodeAndUpdate(3,13);
//        tvShow.putSeasonAndEpisodeAndUpdate(4,13);
//        tvShow.putSeasonAndEpisodeAndUpdate(5,16);
//        tvShowService.insert(tvShow);
//        tvShow.getGenre().add(genre3);
//        tvShowService.insert(tvShow);
//
//        Comment comment2 = new Comment(user, tvShow,"Great show!");
//        commentService.insert(comment2);
//
//        Admin admin = new Admin("admin01","admin01");
//        Admin obj = adminService.insert(admin);
//        System.out.println("Admin token: " + obj.getToken());
//
//        movie = movieService.insert(movie);
//        Director director = new Director(null, "Quentin Tarantino", "1963");
//        director.setGender(Gender.MALE.getCode());
//        director = directorService.insert(director);
//        Actor actor = new Actor(null, "Keanu Reeves",  "1964", Gender.MALE.getCode());
//        actor = actorService.insert(new ActorDTO(actor));
//        movie.getDirectors().add(director);
//        Director director1 = new Director(null, "kdaskdask","1203");
//        director1.setGender(Gender.MALE.getCode());
//        director1 = directorService.insert(director1);
        Genres horror = new Genres(null, "Horror");
        genresService.insert(horror);
//        movie.getGenre().add(horror);
//        movie.getDirectors().add(director1);
//        movie.getActors().add(actor);
//        movieService.insert(movie);
//
        Map<Integer, Integer> maps = new HashMap<>();
        TVShow tv = new TVShow("The Office", "bla bla bla", "2003", maps);
        tv.putSeasonAndEpisodeAndUpdate(1,24);
        tvShowService.insert(tv);
        tv.getDirectors().add(christopherNolan);

        tv.getGenre().add(horror);
        tv.getActors().add(anne);
        tvShowService.insert(tv);

        User user2 = new User("user02", "user02", "user02", Gender.FEMALE);
        UserDTO dto = new UserDTO(user2);
        User obj = userService.signIn(dto);
        Comment comment = new Comment(obj,movie, "didn't understand shit.");
        commentService.insert(comment);
    }
}
