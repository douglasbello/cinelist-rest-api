package com.douglasbello.Cinelist.config;

import com.douglasbello.Cinelist.entities.*;
import com.douglasbello.Cinelist.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class ApplicationTest implements CommandLineRunner {

    private final UserService userService;

    private final MovieService movieService;

    private final CommentService commentService;

    private final TVShowService tvShowService;

    private final AdminService adminService;

    private final GenresService genresService;


    public ApplicationTest(UserService userService, MovieService movieService,
                           CommentService commentService, TVShowService tvShowService,
                           AdminService adminService, GenresService genresService) {
        this.userService = userService;
        this.movieService = movieService;
        this.commentService = commentService;
        this.tvShowService = tvShowService;
        this.adminService = adminService;
        this.genresService = genresService;
    }

    @Override
    public void run(String... args) {

        User user = new User("user01@outlook.com","user01","user01");
        userService.insert(user);

        Genres genre1 = new Genres(null,"Science fiction");
        Genres genre2 = new Genres(null,"Adventure");
        Genres genre3 = new Genres(null,"Crime");
        genresService.insertAll(Arrays.asList(genre1,genre2,genre3));

        Movie movie = new Movie("Interstellar", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.","Cristopher Nolan", "2014");
        movieService.insert(movie);
        movie.getGenre().add(genre1);
        movie.getGenre().add(genre2);
        movieService.insert(movie);


        Comment comment = new Comment(user,movie, "Great movie!");
        commentService.insert(comment);

        TVShow tvShow = new TVShow("Breaking Bad","A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future.","Vince Gilligan", "2008");
        tvShow.putSeasonAndEpisodeAndUpdate(1,7);
        tvShow.putSeasonAndEpisodeAndUpdate(2,13);
        tvShow.putSeasonAndEpisodeAndUpdate(3,13);
        tvShow.putSeasonAndEpisodeAndUpdate(4,13);
        tvShow.putSeasonAndEpisodeAndUpdate(5,16);
        tvShowService.insert(tvShow);
        tvShow.getGenre().add(genre3);
        tvShowService.insert(tvShow);

        Comment comment2 = new Comment(user, tvShow,"Great show!");
        commentService.insert(comment2);

        Admin admin = new Admin("admin01","admin01");
        Admin obj = adminService.insert(admin);
        System.out.println("Admin token: " + obj.getToken());
    }
}
