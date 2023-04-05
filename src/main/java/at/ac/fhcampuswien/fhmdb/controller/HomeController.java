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

    public List releaseYear = new ArrayList(124);


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
        for (int i = 0; i < 124; i++){
            releaseYear.add(i,2023 - i);

        }
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell());// apply custom cells to the listview
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("No filter");
        genreComboBox.getItems().addAll(Genre.values());
        releaseYearComboBox.setPromptText("Filter by Release Year");
        releaseYearComboBox.getItems().add("No filter");
        releaseYearComboBox.getItems().addAll(releaseYear);
        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().add("No filter");
        ratingComboBox.getItems().addAll("1","2", "3", "4", "5");



    }




    public List<Movie> filterByQueryStream(List<Movie> movies, String query){
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


    public List<Movie> filterByGenreStream(List<Movie> movies, Genre genre){
        if(genre == null) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getGenres().contains(genre))
                .toList();
    }


    public List<Movie> filterByRatingStream(List<Movie> movies, int rating){


        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> (int) movie.getRating() == rating)
                .toList();

    }

    public List<Movie> filterWithAPI(String query, Genre genre, String releaseYear, String ratingFrom){
        return MovieAPI.getAllMovies(query, genre, releaseYear, ratingFrom);
    }
    public void applyAllFilters(String searchQuery, Object genre, Object rating) {
        List<Movie> filteredMovies = allMovies;

        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQueryStream(filteredMovies, searchQuery);
        }

        if (genre != null && !genre.toString().equals("No filter")) {
            filteredMovies = filterByGenreStream(filteredMovies, Genre.valueOf(genre.toString()));
        }
        if (rating != null && !rating.toString().equals("No filter")) {
            try {
                int ratingValue = Integer.parseInt(rating.toString());
                filteredMovies = filterByRatingStream(filteredMovies, ratingValue);
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

        String searchQuery = searchField.getText();
        if (searchQuery.isEmpty()){
            searchQuery = null;
        }
        Object genre = genreComboBox.getSelectionModel().getSelectedItem();
        if (genre != "No filter" && genre != null){
            genre = Genre.valueOf(genreComboBox.getSelectionModel().getSelectedItem().toString());
        } else genre = null;

        Object releaseYear = releaseYearComboBox.getSelectionModel().getSelectedItem();
        if (releaseYear != "No filter" && releaseYear != null){
            releaseYear = String.valueOf(releaseYearComboBox.getSelectionModel().getSelectedItem().toString());
        } else releaseYear = null;

        Object ratingFrom = releaseYearComboBox.getSelectionModel().getSelectedItem();
        if (ratingFrom != "No filter" && ratingFrom != null){
            ratingFrom = String.valueOf(releaseYearComboBox.getSelectionModel().getSelectedItem().toString());
        } else ratingFrom = null;

        observableMovies.clear();
        observableMovies.addAll(filterWithAPI(searchQuery, (Genre) genre, (String) releaseYear, (String) ratingFrom));


    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        reverseMovies();
    }


}