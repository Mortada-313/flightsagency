package sample.controller;

import javafx.fxml.Initializable;
import sample.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {
    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection= DBConnection.getConnection();
    }

}
