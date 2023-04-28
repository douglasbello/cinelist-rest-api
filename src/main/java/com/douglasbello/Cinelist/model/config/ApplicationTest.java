package com.douglasbello.Cinelist.model.config;

import com.douglasbello.Cinelist.model.entities.Comment;
import com.douglasbello.Cinelist.model.entities.Movie;
import com.douglasbello.Cinelist.model.entities.TVShow;
import com.douglasbello.Cinelist.model.entities.User;
import com.douglasbello.Cinelist.model.services.CommentService;
import com.douglasbello.Cinelist.model.services.MovieService;
import com.douglasbello.Cinelist.model.services.TVShowService;
import com.douglasbello.Cinelist.model.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class ApplicationTest implements CommandLineRunner {

    private final UserService userService;

    private final MovieService movieService;

    private final CommentService commentService;

    private final TVShowService tvShowService;

    public ApplicationTest(UserService userService, MovieService movieService, CommentService commentService, TVShowService tvShowService) {
        this.userService = userService;
        this.movieService = movieService;
        this.commentService = commentService;
        this.tvShowService = tvShowService;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User("douglasbelloalv1@outlook.com","douglasbello","password");

        Movie movie = new Movie("Tropa de elite", "brasil");

        userService.save(user);
        movieService.save(movie);

        Comment comment = new Comment(user,movie, "JASHDHASJHD");
        commentService.save(comment);

        TVShow tvShow = new TVShow("gurizes","bah meu");
        tvShow.getSeasonsAndEpisodes().put(3,9);
        tvShow.getSeasonsAndEpisodes().put(2,9);
        tvShowService.save(tvShow);
    }
}