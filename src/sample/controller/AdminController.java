package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DBConnection;
import sample.model.Reservation;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    ObservableList<Reservation> reservations;
    private Connection connection;
    public AdminController(){
        connection= DBConnection.getConnection();
    }
    @FXML
    private TableView<Reservation> adminReservationsTable;
    @FXML
    private TableColumn<Reservation,String> aRFN,aRLN,aRP,aRC,aRDD,aRD;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            ResultSet resultSet=connection.createStatement().executeQuery("SELECT c.first_name, c.last_name, c.phone, r.cost, f.departure_date, f.departure_time, f.destination\n" +
                    "FROM clients AS c,reservations AS r, flights AS f\n" +
                    "where c.id=r.clients_id AND f.id=r.flights_id");
            reservations= FXCollections.observableArrayList();
            while(resultSet.next()) {
                reservations.add(new Reservation(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5) + " " +
                        resultSet.getString(6), resultSet.getString(7)));
            }
            aRFN.setCellValueFactory(new PropertyValueFactory<>("fname"));
            aRLN.setCellValueFactory(new PropertyValueFactory<>("lname"));
            aRP.setCellValueFactory(new PropertyValueFactory<>("phone"));
            aRC.setCellValueFactory(new PropertyValueFactory<>("Cost"));
            aRDD.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
            aRD.setCellValueFactory(new PropertyValueFactory<>("destination"));

            adminReservationsTable.setItems(reservations);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
