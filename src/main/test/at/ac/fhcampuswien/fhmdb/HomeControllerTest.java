package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private static HomeController homeController;
    @BeforeAll
    static void inti() { homeController = new HomeController(); }

    @Test
    void click_on_Sort_button_when_it_shows_sortasc_sorts_movies_descending_without_a_searchterm(){
        //given


        List<Movie> movies = new ArrayList<>();
       Movie movie1 = new Movie(
                "The Matrix",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));
        Movie movie2 = new Movie(
                "Forrest Gump",
                "The presidencies of Kennedy and Johnson, the Vietnam War, and the Watergate scandal, is seen through the eyes of an Alabama man with an IQ of 75.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.COMEDY)));

        movies.add(movie2);
        movies.add(movie1);

        List<Movie> movies2 = new ArrayList<>();
        Movie movie3 = new Movie(
                "The Matrix",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));
        Movie movie4 = new Movie(
                "Forrest Gump",
                "The presidencies of Kennedy and Johnson, the Vietnam War, and the Watergate scandal, is seen through the eyes of an Alabama man with an IQ of 75.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.COMEDY)));


        homeController.sortMovies("s");

        assertEquals(homeController.sortMovies(), movies2);


    }


//
//    @Test
//    void movies_and_observableMovies_are_equal(){
//        //GIVEN
//        homeController.initializeState();
//
//        //WHEN & THEN
//        assertEquals(homeController.allMovies, homeController.getObservableMovies());
//    }

}