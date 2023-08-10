# Cinelist Rest API

## Description
Cinelist is a Rest API built with [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and [Spring Framework](https://spring.io/). With this API users can create accounts, add movies and tvshows to their favorite list or watched list, rate movies and shows and also comment into these movies and shows.<br>
  Admins can create new admins, create new movies or shows, create new directors, actors, genres, add these directors, actors and genres to movies and shows, delete directors, delete shows, delete movies, delete genres.

## API paths that normal users can access

| METHOD | PATH | REQUEST BODY | 2xx | 4xx |
|----------|----------|----------|----------|----------|
| Dado 1   | Dado 2   | Dado 3   | Dado 4   | Dado 5   |
| GET   | /api/users   |    | 200   | 403   |
| GET   | /api/users/watched-movies   |    | 200   | 403   |
| GET   | /api/users/favorite-movies   |    | 200   | 403   |
| GET   | /api/users/watched-shows   |    | 200   | 403   |
| GET   | /api/users/favorite-shows   |    | 200   | 403   |
| GET   | /api/movies   |    | 200   | 403   |
| GET   | /api/movies/{id}   |    | 200   | 403, 404   |
| GET   | /api/movies/title/{title}   |    | 200   | 403, 404   |
| GET   | /api/movies/directors/id/{directorId}   |    | 200   | 403, 404   |
| GET   | /api/movies/directors/name/{directorName}   |    | 200   | 403, 404   |
| GET   | /api/movies/{movieId}/actors   |    | 200   | 403, 404   |
| GET   | /api/movies/actors/id/{actorId}  |    | 200   | 403, 404   |
| GET   | /api/movies/actors/name/{actorName}  |    | 200   | 403, 404   |
| GET   | /api/movies/genres/id/{genreId}  |    | 200   | 403, 404   |
| GET   | /api/movies/genres/name/{genreName}  |    | 200   | 403, 404   |
| GET   | /api/shows/{id}   |    | 200   | 403, 404   |
| GET   | /api/shows/title/{title}   |    | 200   | 403, 404   |
| GET   | /api/shows/directors/id/{directorId}   |    | 200   | 403, 404   |
| GET   | /api/shows/directors/name/{directorName}   |    | 200   | 403, 404   |
| GET   | /api/shows/{showId}/actors   |    | 200   | 403, 404   |
| GET   | /api/shows/actors/id/{actorId}  |    | 200   | 403, 404   |
| GET   | /api/shows/actors/name/{actorName}  |    | 200   | 403, 404   |
| GET   | /api/shows/genres/id/{genreId}  |    | 200   | 403, 404   |
| GET   | /api/shows/genres/name/{genreName}  |    | 200   | 403, 404   |
| GET   | /api/actors   |    | 200   | 403   |
| GET   | /api/actors/{id}   |    | 200   | 403, 404   |
| GET   | /api/actors/name/{name}   |    | 200   | 403, 404   |
| GET   | /api/directors   |    | 200   | 403   |
| GET   | /api/directors/{id}   |    | 200   | 403, 404   |
| GET   | /api/genres   |    | 200   | 403   |
| GET   | /api/genres/id/{id}   |    | 200   | 403, 404   |
| GET   | /api/genres/name/{name}   |    | 200   | 403, 404   |
| GET   | /api/comments/movie/{movieId}   |    | 200   | 403, 404   |
| GET   | /api/comments/show/{showId}   |    | 200   | 403, 404   |
| GET   | /api/comments/user/{userId}   |    | 200   | 403, 404   |
| POST   | /api/users/sign-in   | UserSignInDTO   | 201   | 400, 409   |
| POST   | /api/users/login   | LoginDTO   | 200   | 400  |
