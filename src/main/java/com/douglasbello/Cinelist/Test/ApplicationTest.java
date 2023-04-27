package com.douglasbello.Cinelist.Test;

import com.douglasbello.Cinelist.services.CommentService;
import com.douglasbello.Cinelist.services.MovieService;
import com.douglasbello.Cinelist.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class ApplicationTest {

    private UserService userService;

    private MovieService movieService;

    private CommentService commentService;

    public ApplicationTest(UserService userService, MovieService movieService, CommentService commentService) {

    }
}