package at.ac.fhcampuswien.fhmdb.api;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class MovieApiException extends IOException {
    public MovieApiException(String message) {
        Alert alarm = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alarm.show();
    }
}