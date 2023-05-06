package at.ac.fhcampuswien.fhmdb.datalayer;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;

public class DatabaseException extends SQLException {
    public DatabaseException(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        a.show();
    }
}