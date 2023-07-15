package com.douglasbello.Cinelist.mock;

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

        Genres horror = new Genres(null, "Horror");
        genresService.insert(horror);
        
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
