# Cinelist Rest API

## Description
- Cinelist is a Rest API built with [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and [Spring Framework](https://spring.io/).
- With this API users can create accounts, add movies and tvshows to their favorite list or watched list, rate movies and shows and also comment into these movies and shows.<br>
- Admins can create new admins, create new movies or shows, create new directors, actors, genres, add these directors, actors and genres to movies and shows, delete directors, shows, movies, genres and actors.

## The security of this API
- Built with [Spring Security](https://spring.io/projects/spring-security).
- This API uses a stateless security authentication and authorization, that means that we don't keep data, so for each request, the user must provide his token.
- The only paths that don't require an token in this API are the sign-up and login paths. All the other paths in this app require an token, the user can get this token by login.
- The token of this application is a JWT Token of type Bearer Token.
---
## Paths that normal users can access

| METHOD | PATH | REQUEST BODY | 2xx | 4xx |
|----------|----------|----------|----------|----------|
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
| POST   | /api/users/sign-up   | UserSignInDTO   | 201   | 400, 409   |
| POST   | /api/users/login   | LoginDTO   | 200   | 400  |
| POST   | /api/movies/rate   | RateDTO   | 200   | 400, 403, 404  |

## Paths that only admins can access
| METHOD | PATH | REQUEST BODY | 2xx | 4xx |
|----------|----------|----------|----------|----------|
| POST   | /api/admins   | LoginDTO   | 201   | 400, 403   |
| POST   | /api/movies   | MovieDTO   | 201   | 400, 403   |
| POST   | /api/movies/{movieId}/actors   | Set<UUID> actorsIds   | 200   | 400, 404   |
| POST   | /api/shows   | TVShowDTO   | 201   | 400, 403   |
| POST   | /api/directors   | DirectorInputDTO   | 201   | 400, 403   |
| POST   | /api/genres   | String genreName   | 201   | 400, 403   |
| POST   | /api/actors   | ActorInputDTO   | 201   | 400, 403   |
| PUT   | /api/shows/{id}   |  TVShowDTO  | 200   | 403, 404   |
| DELETE   | /api/shows/{id}   |    | 204   | 403, 404   |
| DELETE   | /api/directors/{id}   |    | 204   | 403, 404   |
---
## How to run the project
```bash
  $ git clone git@github.com:douglasbello/cinelist.git
  $ cd cinelist
  $ mvn spring-boot:run
```
>*<p>If you don't have maven installed in your machine, you can run the project in a IDE</p>*
---
## Technology used
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Validation](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html)
- [H2 Database](https://www.h2database.com/html/main.html)
- [PostgreSQL](https://www.postgresql.org/)
- [JWT Token](https://github.com/auth0/java-jwt)
