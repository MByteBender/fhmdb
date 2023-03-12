package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Movie {
    private String title;
    private String description;

    private List<Genre> genres;
    // TODO add more properties here

    public Movie(String title, String description, List<Genre>genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }


    @Override
    public boolean equals(Object Movie) {
        if (this == Movie) return true;
        if (Movie == null || getClass() != Movie.getClass()) return false;
        Movie movie = (Movie) Movie;
        return Objects.equals(title, movie.title) && Objects.equals(description, movie.description) && Objects.equals(genres, movie.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, genres);
    }

    public static List<Movie> initializeMovies(){
        List<Movie>  movies= new ArrayList<>();
        movies.add(new Movie(
                "The Matrix",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));
        movies.add(new Movie(
                "The Matrix 2",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));
        movies.add(new Movie(
                "Forrest Gump",
                "The presidencies of Kennedy and Johnson, the Vietnam War, and the Watergate scandal, is seen through the eyes of an Alabama man with an IQ of 75.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.COMEDY)));
        movies.add(new Movie(
                "Pulp Fiction",
                "The lives of two mob hitmen, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                Arrays.asList(Genre.DRAMA, Genre.CRIME, Genre.THRILLER)));
        movies.add(new Movie(
                "Jurassic Park",
                "A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose.",
                Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER)));
        return movies;
    }
}
