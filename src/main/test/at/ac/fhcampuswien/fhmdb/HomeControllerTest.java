package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private static HomeController homeController;


    @BeforeAll
    static void init() {
        homeController = new HomeController();

    }

    ObservableList<Movie> testList = FXCollections.observableArrayList(
            new Movie(
                    "The Matrix",
                    "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                    Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)
            ),
            new Movie(
                    "Jurassic Park",
                    "A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose.",
                    Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER)
            ),
            new Movie(
                    "Forrest Gump",
                    "The presidencies of Kennedy and Johnson, the Vietnam War, and the Watergate scandal, is seen through the eyes of an Alabama man with an IQ of 75.",
                    Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.COMEDY)
            )
    );

    @Test
    void list_will_not_be_filterd_when_no_filter_is_applied_and_no_searchterm_is_in_searchbox(){
        // given
        List<Movie> allMovies = Movie.initializeMovies();

        // when
        List<Movie> actual = homeController.filterMovies("NO_GENRE_FILTER","", allMovies);

        // then
        List<Movie> expected = Movie.initializeMovies();
        assertEquals(expected, actual);

    }


    @Test
    void filter_movies_list_is_empty_when_a_wrong_searchTerm_is_entered(){
        // given
        List<Movie> allMovies = Movie.initializeMovies();

        // when
        List<Movie> actual = homeController.filterMovies("ACTION","asdfasdfsaf", allMovies);

        // then
        List<Movie> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }
    @Test
    void filter_movies_after_genre_when_a_Genre_is_selcted_and_no_searchterm_is_in_searchbox(){
        // given
        List<Movie> allMovies = Movie.initializeMovies();

        // when
        List<Movie> actual = homeController.filterMovies("ACTION","", allMovies);

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

    @Test
    void filter_movies_after_genre_when_a_Genre_is_selcted_and_a_searchterm_is_in_searchbox_and_ignore_searterm_Case(){
        // given
        List<Movie> allMovies = Movie.initializeMovies();

        // when
        List<Movie> actual = homeController.filterMovies("ADVENTURE","JuRaSSic PARk", allMovies);

        // then
        List<Movie> expected = new ArrayList<>();
        expected.add(new Movie(
                "Jurassic Park",
                "A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose.",
                Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER)
        ));
        assertEquals(expected, actual);
    }
    @Test
    void filter_movies_after_genre_when_a_Genre_is_selcted_and_a_searchterm_is_in_searchbox(){
        // given
        List<Movie> allMovies = Movie.initializeMovies();

        // when
        List<Movie> actual = homeController.filterMovies("ACTION","The Matrix 2", allMovies);

        // then
        List<Movie> expected = new ArrayList<>();
        expected.add(new Movie(
                "The Matrix 2",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));
        assertEquals(expected, actual);
    }

    @Test
    void filter_all_movies_by_title_and_descripton_and_ignore_genre_when_no_filter_is_applyed(){
        // given
        List<Movie> allMovies = Movie.initializeMovies();

        // when
        List<Movie> actual = homeController.filterMovies("NO_GENRE_FILTER","li", allMovies);

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
        expected.add(new Movie(
                "Pulp Fiction",
                "The lives of two mob hitmen, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                Arrays.asList(Genre.DRAMA, Genre.CRIME, Genre.THRILLER)));

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted_list_ascending_after_title(){
        // given
        ObservableList<Movie> sortList = testList;

        // when
        homeController.sortMovies(sortList);
        ObservableList<Movie> actual = sortList;

        // then
        List<Movie> expected = new ArrayList<>();
        expected.add(new Movie(
                "Forrest Gump",
                "The presidencies of Kennedy and Johnson, the Vietnam War, and the Watergate scandal, is seen through the eyes of an Alabama man with an IQ of 75.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.COMEDY)));
        expected.add(new Movie(
                "Jurassic Park",
                "A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose.",
                Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER)));
        expected.add(new Movie(
                "The Matrix",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));

        assertEquals(expected, actual);
    }

    @Test
    void check_if_List_is_reversed(){
        // given

        ObservableList<Movie>  unsortedList= FXCollections.observableArrayList(
                new Movie(
                        "Forrest Gump",
                        "The presidencies of Kennedy and Johnson, the Vietnam War, and the Watergate scandal, is seen through the eyes of an Alabama man with an IQ of 75.",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.COMEDY)
                ),
                new Movie(
                        "Jurassic Park",
                        "A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose.",
                        Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER)
                ),
                new Movie(
                    "The Matrix",
                    "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                    Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)
        )
        );

        ObservableList<Movie>  expected = FXCollections.observableArrayList(
                new Movie(
                        "The Matrix",
                        "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                        Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)
                ),
                new Movie(
                        "Jurassic Park",
                        "A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose.",
                        Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER)
                ),
                new Movie(
                        "Forrest Gump",
                        "The presidencies of Kennedy and Johnson, the Vietnam War, and the Watergate scandal, is seen through the eyes of an Alabama man with an IQ of 75.",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.COMEDY)
                )
        );
        // when
        homeController.sortState = SortState.DESCENDING;
        homeController.observableMovies.setAll(unsortedList);
        homeController.reverseMovies();

        // then
        assertEquals(expected,homeController.observableMovies);
    }
    @Test
    void when_sort_button_text_is_decending_then_list_is_sorted_ascending(){
        // given
        homeController.sortState = SortState.DESCENDING;
        // when
        String actual = homeController.reverseMovies();

        // then
        String expected = "Sort (desc)";
        assertEquals(expected,actual);
    }

    @Test
    void when_sort_button_text_is_ascending_then_list_is_sorted_decending(){
        // given
        homeController.sortState = SortState.ASCENDING;
        // when
        String actual = homeController.reverseMovies();


        // then
        String expected = "Sort (asc)";
        assertEquals(expected,actual);
    }



    @Test
    void check_if_Illegal_Argument_Exception_is_thrown_when_invalid_sort_state_is_given(){
        // given
        homeController.sortState = SortState.NONE;
        // when & then
        assertThrows(IllegalArgumentException.class, ()-> homeController.reverseMovies());
    }


}