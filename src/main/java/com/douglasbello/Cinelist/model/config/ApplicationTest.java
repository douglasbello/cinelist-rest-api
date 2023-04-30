package com.douglasbello.Cinelist.model.config;

import com.douglasbello.Cinelist.model.entities.*;
import com.douglasbello.Cinelist.model.services.*;
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


    public ApplicationTest(UserService userService, MovieService movieService,
                           CommentService commentService, TVShowService tvShowService) {
        this.userService = userService;
        this.movieService = movieService;
        this.commentService = commentService;
        this.tvShowService = tvShowService;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User("user01@outlook.com","user01","user01");
        Movie movie = new Movie("Interstellar", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.");
        userService.insert(user);
        movieService.insert(movie);

        Comment comment = new Comment(user,movie, "Great movie!");
        commentService.insert(comment);

        TVShow tvShow = new TVShow("Breaking Bad","A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future.");
        tvShow.putSeasonAndEpisodeAndUpdate(1,7);
        tvShow.putSeasonAndEpisodeAndUpdate(2,13);
        tvShow.putSeasonAndEpisodeAndUpdate(3,13);
        tvShow.putSeasonAndEpisodeAndUpdate(4,13);
        tvShow.putSeasonAndEpisodeAndUpdate(5,16);
        tvShowService.insert(tvShow);

        Comment comment2 = new Comment(user, tvShow,"Great show!");
        commentService.insert(comment2);

    }
}