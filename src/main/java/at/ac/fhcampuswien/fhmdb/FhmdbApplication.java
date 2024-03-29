package at.ac.fhcampuswien.fhmdb;
import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.controller.HomeController;
import at.ac.fhcampuswien.fhmdb.datalayer.Database;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ControllerFactory controllerFactory = new ControllerFactory();
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        fxmlLoader.setControllerFactory(controllerFactory);
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();
        Database db = Database.getInstance();

    }


    public static void main(String[] args) {launch();}
}