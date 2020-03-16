package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.DBConnection;
import sample.model.Flight;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AddFlightController implements Initializable {
    Connection connection;
    @FXML
    private TextField destination,company,depDate,depTime,tripTime,cost;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection= DBConnection.getConnection();
    }
    public void addFlight(){
        Flight.addFlight(connection,destination.getText(),company.getText(),depDate.getText(),depTime.getText(),tripTime.getText(),cost.getText());
    }
}
