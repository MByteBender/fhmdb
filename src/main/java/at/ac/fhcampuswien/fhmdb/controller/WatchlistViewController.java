package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.datalayer.Database;
import at.ac.fhcampuswien.fhmdb.datalayer.DatabaseException;
import at.ac.fhcampuswien.fhmdb.datalayer.WatchlistEntity;
import at.ac.fhcampuswien.fhmdb.datalayer.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.j256.ormlite.dao.Dao;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.h2.jdbc.JdbcSQLException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

public class WatchlistViewController {
    @FXML
    public Button watchlistBtn;
    @FXML
    public VBox mainPane;
    @FXML
    public JFXListView movieWatchlistView;
    WatchlistRepository repo;


    public void initialize() throws DatabaseException {
        System.out.println("WatchlistViewController initialized");

        repo = new WatchlistRepository();
        List<WatchlistEntity> watchlist = new ArrayList<>();


        try {
            watchlist = repo.getAll();
        } catch (SQLException e) {
            new DatabaseException("Unexpected error in fetching elements from the database");
        }

        ObservableList<Movie> movies = FXCollections.observableArrayList(
                watchlist.stream()
                        .map(WatchlistEntity::toMovie)
                        .collect(Collectors.toList())
        );

        movieWatchlistView.setItems(movies);
        movieWatchlistView.setCellFactory(movieListView -> {
            ClickEventHandler clickEventHandler = (clickedItem, observableList) -> {
                Movie temp = (Movie) clickedItem;

                String title = temp.getTitle().replace("'", "''");
                List<WatchlistEntity> movieList = null;
                try {
                    Dao<WatchlistEntity, Long> tempDao = Database.getInstance().getDao();
                    movieList = tempDao.queryForEq("title", title);
                    if (!movies.isEmpty()) {
                        try {
                            tempDao.delete(movieList);
                        } catch (SQLException e) {
                            new DatabaseException("Exception");
                        }
                        System.out.println("Deleted " + temp.getTitle() + " from Watchlist");
                    }
                } catch (DatabaseException e) {
                    new DatabaseException("Exception");
                } catch (SQLException e) {
                     new RuntimeException(e);
                }
                this.movies = movieList;
                movieWatchlistView.setItems(observableList);

            };
            try {
                return new MovieCell(true, clickEventHandler);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void loadHomeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        try{
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


}
