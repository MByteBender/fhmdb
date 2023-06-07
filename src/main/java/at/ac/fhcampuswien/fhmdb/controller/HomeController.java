package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.datalayer.*;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortState;
import at.ac.fhcampuswien.fhmdb.observe.ObservableMessages;
import at.ac.fhcampuswien.fhmdb.observe.Observer;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.j256.ormlite.dao.Dao;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController implements Initializable, Observer {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox<String> releaseYearComboBox;

    @FXML
    public JFXComboBox<String> ratingComboBox;
    @FXML
    public JFXButton sortBtn;

    @FXML
    public Button watchlistBtn;


    @FXML
    public VBox mainPane;
    @FXML
    public HBox welcomeHbox;
    @FXML
    public HBox menuHbox;


    public List<Movie> allMovies = Movie.initializeMovies();
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    public SortState sortedState;



    MovieAPI movieAPI = new MovieAPI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // homecontroller bekommt jetzt updates
        try {
            WatchlistRepository.getWatchlistRepositoryInstance().addObserver(this);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        try {
            initializeState();
        } catch (IOException e) {
            new MovieApiException("Unexpected error in fetching the movies from the API. Check if you have internet connection!");
        }
        initializeLayout();

        System.out.println(getLongestMovieTitle(observableMovies));
        System.out.println(getMostPopularActor(observableMovies));

        countMoviesFrom(observableMovies, "Peter Jackson");
        getMoviesBetweenYears(observableMovies, 2001, 2010).stream()
                .map(Movie::getReleaseYear)
                .forEach(System.out::println);
    }

    public void initializeState() throws IOException {
        try {
//       allMovies = Movie.initializeMovies();
            List<Movie> result = MovieAPI.getAllMovies();
            setMovies(result);
            setMovieList(result);
            ; // add all movies to the observable list
            sortedState = SortState.NONE;
        } catch (MovieApiException e) {
            new MovieApiException("Unexpected error in fetching the movies from the API. Check if you have internet connection!");
        }
    }

    public void initializeLayout() {

        movieListView.setItems(observableMovies);
          // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> {
            ClickEventHandler clickEventHandler = (clickedItem, watchlistController) ->{



                Movie temp = (Movie) clickedItem;
                WatchlistRepository tempWatchlistRepository = null;
                try {
                    tempWatchlistRepository = WatchlistRepository.getWatchlistRepositoryInstance();
                } catch (DatabaseException e) {
                     new DatabaseException("Exception");
                }

                try {
                    Dao<WatchlistEntity, Long> tempDao = Database.getInstance().getDao();
                    String title = temp.getTitle().replace("'", "''");

                    if (tempDao.queryForEq("title", title).isEmpty()) {

                        tempDao.create(tempWatchlistRepository.movieToEntity(temp));
                        // updates the message so aslo the observers get it
                        WatchlistRepository.getWatchlistRepositoryInstance().updateObserver(ObservableMessages.ADDED);
                        System.out.println("Added " + temp.getTitle() + " to Watchlist");
                        return;
                    }
                    WatchlistRepository.getWatchlistRepositoryInstance().updateObserver(ObservableMessages.ALREADY_EXISTS);

                } catch (SQLException e) {
                    new DatabaseException("Could not get Dao");
                }

            };
            return new MovieCell(false, clickEventHandler);
        });// apply custom cells to the listview

        // genre combobox
        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");

        releaseYearComboBox.getItems().add("No filter");
        List<String> yearList = new ArrayList<>();
        observableMovies.stream().map(Movie::getReleaseYear).distinct().sorted(Comparator.reverseOrder()).map(String::valueOf).forEach(yearList::add);
        this.releaseYearComboBox.getItems().addAll(yearList);
        releaseYearComboBox.setPromptText("Filter by Release Year");

        ratingComboBox.getItems().add("No filter");
        List<String> ratingList = new ArrayList<>();
        observableMovies.stream().map(Movie::getRating).distinct().sorted(Comparator.reverseOrder()).map(String::valueOf).forEach(ratingList::add);
        this.ratingComboBox.getItems().addAll(ratingList);
        ratingComboBox.setPromptText("Filter by Rating");

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


    public String validateComboboxValue(Object value) {
        if(value != null && !value.toString().equals("No filter")) {
            return value.toString();
        }
        return null;
    }


    public void searchBtnClicked(ActionEvent actionEvent) throws IOException {

        String searchQuery = searchField.getText().trim().toLowerCase();
        String releaseYear = validateComboboxValue(releaseYearComboBox.getSelectionModel().getSelectedItem());
        String ratingFrom = validateComboboxValue(ratingComboBox.getSelectionModel().getSelectedItem());
        String genreValue = validateComboboxValue(genreComboBox.getSelectionModel().getSelectedItem());

        Genre genre = null;
        if(genreValue != null) {
            genre = Genre.valueOf(genreValue);
        }


        List<Movie> movies = MovieAPI.getAllMovies(searchQuery,genre,releaseYear,ratingFrom);
        setMovieList(movies);



        if(sortedState != SortState.NONE) {
            sortMovies();
        }
    }

    public void setMovies(List<Movie> movies) {
        allMovies = movies;
    }

    public void setMovieList(List<Movie> movies) {
        observableMovies.clear();
        observableMovies.addAll(movies);
    }
    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies() {
        if (sortedState == SortState.NONE || sortedState == SortState.DESCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortState.ASCENDING;
        } else if (sortedState == SortState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortState.DESCENDING;
        }
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
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .toList();

    }


    public void loadWatchlistView() {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 890, 620);
            Stage stage = (Stage)mainPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred.");
            alert.setContentText("Error while loading.");
        }
    }


    @Override
    public void update(ObservableMessages messages) {
        if(messages == ObservableMessages.ADDED){
            new Alert(Alert.AlertType.INFORMATION, "Movie was successfully added to the Watchlist", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Movie is already in Watchlist!", ButtonType.OK).show();
        }
    }
}