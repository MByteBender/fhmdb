module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;

    requires ormlite.jdbc;
    requires com.h2database;
    requires java.sql;


    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson;
    opens at.ac.fhcampuswien.fhmdb.datalayer to ormlite.jdbc;


    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb.controller;
    opens at.ac.fhcampuswien.fhmdb.controller to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.Exceptions to ormlite.jdbc;


}