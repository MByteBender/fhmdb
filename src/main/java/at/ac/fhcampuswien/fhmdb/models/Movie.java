package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Movie {

    private final String id;
    private final String title;
    private final String description;
    private final List<Genre> genres;
    private final int releaseYear;
    public String imgURL;
    private final int lengthInMinutes;
    public List<String> directors;
    public List<String> writers;
    public List<String> mainCast;
    private final double rating;


    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.id = null;
        this.releaseYear = 0;
        this.imgURL = "";
        this.lengthInMinutes = 0;
        this.rating = 0;
    }


    public Movie(String title, String description, List<Genre> genres, int releaseYear, String id, String imgURL, int lengthInMinutes, List<String> directors, List<String> writers, List<String> mainCast, double rating) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.id = id;
        this.imgURL = imgURL;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;

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
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Movie other)) {
            return false;
        }
        return this.title.equals(other.title) &&
                this.description.equals(other.description) &&
                this.genres.equals(other.genres);
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


    public String getId() {
        return id;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() {
        return imgURL;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public double getRating() {
        return rating;
    }


}
