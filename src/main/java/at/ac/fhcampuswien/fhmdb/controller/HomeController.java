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

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXComboBox<String> releaseYearComboBox;

    @FXML
    public JFXComboBox<String> ratingComboBox;
    @FXML
    public JFXButton sortBtn;


    public List<Movie> allMovies = Movie.initializeMovies();
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    public SortState sortState = SortState.NONE;

    MovieAPI movieAPI = new MovieAPI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeLayout();
        sortMovies(observableMovies);
        countMoviesFrom(observableMovies, "Peter Jackson");
        getMoviesBetweenYears(observableMovies, 2001, 2010).stream()
                .map(Movie::getReleaseYear)
                .forEach(System.out::println);
    }

    public void initializeState() throws IOException {
//        allMovies = Movie.initializeMovies();
        allMovies = movieAPI.getFullMovieList();
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortState = SortState.NONE;
    }

    public void initializeLayout() {

        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell());// apply custom cells to the listview

        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.getSelectionModel().select(Genre.No_Genre_Filter);
        releaseYearComboBox.getItems().add("No release year filter");
        releaseYearComboBox.getSelectionModel().select("No release year filter");
        ratingComboBox.getItems().add("No rating filter");
        ratingComboBox.getSelectionModel().select("No rating filter");

        List<String> yearList = new ArrayList<>();
        observableMovies.stream().map(Movie::getReleaseYear).distinct().sorted(Comparator.reverseOrder()).map(String::valueOf).forEach(yearList::add);
        this.releaseYearComboBox.getItems().addAll(yearList);

        List<String> ratingList = new ArrayList<>();
        observableMovies.stream().map(Movie::getRating).distinct().sorted(Comparator.reverseOrder()).map(String::valueOf).forEach(ratingList::add);
        this.ratingComboBox.getItems().addAll(ratingList);

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

    public void searchBtnClicked(ActionEvent actionEvent) throws IOException {

        observableMovies.clear();
        observableMovies.addAll(movieAPI.getFilteredMovieList(searchField.getText(),
                genreComboBox.getValue(), releaseYearComboBox.getValue(),
                ratingComboBox.getValue()));
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        reverseMovies();
    }

    public String getMostPopularActor(List<Movie> movies) {
        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }
        return movies.stream()
                .flatMap(m -> m.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("No main cast found"));
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }
        return movies.stream()
                .map(Movie::getTitle)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {

        if(director == null || director.isEmpty()) return 0;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        long numberOfFilms = movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
        System.out.println(numberOfFilms);
        return numberOfFilms;
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getReleaseYear() > startYear && movie.getReleaseYear() < endYear)
                .toList();

    }


}