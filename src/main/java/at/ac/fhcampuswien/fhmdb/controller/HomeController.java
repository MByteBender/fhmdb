package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox releaseYearComboBox;

    @FXML
    public JFXComboBox ratingComboBox;
    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    public SortState sortState = SortState.NONE;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
        sortMovies(observableMovies);
    }

    public void initializeState() {
//        allMovies = Movie.initializeMovies();
        allMovies = MovieAPI.getAllMovies();
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortState = SortState.NONE;
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell());// apply custom cells to the listview
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("No filter");
        genreComboBox.getItems().addAll(Genre.values());
        releaseYearComboBox.setPromptText("Filter by Release Year");
        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().add("No Rating Filter");
        ratingComboBox.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
    }




    public List<Movie> filterByQuery(List<Movie> movies, String query){
        if(query == null || query.isEmpty()) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie ->
                        movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                movie.getDescription().toLowerCase().contains(query.toLowerCase())
                )
                .toList();
    }


    public List<Movie> filterByGenre(List<Movie> movies, Genre genre){
        if(genre == null) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getGenres().contains(genre))
                .toList();
    }


    public List<Movie> filterByRating(List<Movie> movies, int rating){


        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> (int) movie.getRating() == rating)
                .toList();

    }
    public void applyAllFilters(String searchQuery, Object genre, Object rating) {
        List<Movie> filteredMovies = allMovies;

        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQuery(filteredMovies, searchQuery);
        }

        if (genre != null && !genre.toString().equals("No filter")) {
            filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
        }
        if (rating != null && !rating.toString().equals("No filter")) {
            try {
                int ratingValue = Integer.parseInt(rating.toString());
                filteredMovies = filterByRating(filteredMovies, ratingValue);
            } catch (NumberFormatException e) {
                // ignore the exception and don't filter by rating
            }
        }

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }



    /** Sorts Movies ascending
     * @param observableMovies
     */
    public void sortMovies(ObservableList<Movie> observableMovies){
        observableMovies.sort (Comparator.comparing(Movie::getTitle));
        sortState = SortState.ASCENDING;
    }


    /** reverses Movielist depending on SortState and
     * @return correct Sortstate String
     */
    public String reverseMovies() throws IllegalArgumentException {
        if (this.sortState == SortState.ASCENDING) {
            FXCollections.reverse(observableMovies);
            this.sortState = SortState.DESCENDING;
            return "Sort (desc)";
        } else if (this.sortState == SortState.DESCENDING) {
            FXCollections.reverse(observableMovies);
            this.sortState = SortState.ASCENDING;
            return "Sort (asc)";
        } else throw new IllegalArgumentException("Kein gültiger Sortstate "+ sortState.toString());
    }

    public void searchBtnClicked(ActionEvent actionEvent){
        String searchQuery = searchField.getText().trim().toLowerCase();
        Object genre = genreComboBox.getSelectionModel().getSelectedItem();
        Object rating =  ratingComboBox.getSelectionModel().getSelectedItem();

        applyAllFilters(searchQuery, genre, rating);


    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        reverseMovies();
    }


}