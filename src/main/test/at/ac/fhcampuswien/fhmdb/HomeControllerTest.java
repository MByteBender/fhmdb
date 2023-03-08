package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXComboBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private static HomeController homeController;


    private static JFXComboBox genreCombobox;
    @BeforeAll
    static void init() {
        homeController = new HomeController();
        genreCombobox.getItems().addAll(Genre.values());

    }





    @Test
    void click_on_Sort_button_when_it_shows_sortasc_sorts_movies_ascending(){
        //given


    }

    @Test
    void filter_all_movies_by_title_and_descripton_when_no_filter_is_applyed(){
        // given
        Movie.initializeMovies();

        List<Movie> filteredMovies = new ArrayList<>();
        filteredMovies = Movie.initializeMovies();

        List<Genre> genres = new ArrayList<>();
        // when

        genres.addAll(List.of(Genre.values()));
        System.out.println(genres);
        var testCombobox = new JFXComboBox();
        testCombobox.getItems().addAll(Genre.values());
        System.out.println(homeController.genreComboBox.getItems().addAll(genres));

        List<Movie> actual = homeController.filterMovies("","The Matrix");

        // then
        List<Movie> expected = new ArrayList<>();
        expected.add(new Movie(
                "The Matrix",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));
        expected.add(new Movie(
                "The Matrix 2",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));

        assertEquals(expected, actual);
    }

// TODO fix test

//    @Test
//    void check_if_list_is_not_filterd_when_no_filter_is_applyed_and_no_text_is_in_searchbox(){
//        // given
//        List<Movie> expected = new ArrayList<>();
//        expected = Movie.initializeMovies();
//
//        // when
//        List<Movie> actual = homeController.filterMovies("", "");
//
//        // then
//        assertEquals(actual, expected);
//
//
//    }

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