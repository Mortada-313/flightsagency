package sample.controller;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.DBConnection;
import sample.model.Client;
import sample.model.Employee;
import sample.model.Flight;
import sample.model.Reservation;

import java.net.URL;
import java.sql.Connection;
import java.util.Collection;
import java.util.ResourceBundle;

public class AddReservationController implements Initializable {
    private Employee employee;
    private Connection connection;
    private ObservableList<Object> flights;
    private EmployeeController employeeController;
    @FXML
    private ComboBox client,flight;
    @FXML
    private TextField winRate;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection=DBConnection.getConnection();
    }
    public void addReservation(){
        String c=(String)client.getValue(), f=(String)flight.getValue();
        int rate=Integer.parseInt(winRate.getText());
        int flightCost=Integer.parseInt(((Flight)flights.get(Flight.getPosition(f.split("-")[0],flights))).getCost());
        int earn=(int)(((float)rate /100)*flightCost);
        int totalCost=flightCost+earn;
        Reservation.addReservation(connection,totalCost,rate, earn,c.split("-")[0],f.split("-")[0],employee.getId());
        employeeController.setReservations(Reservation.loadReservations(connection));
        employeeController.getTable().setItems(employeeController.getReservations());
        client.setValue("");
        flight.setValue("");
        winRate.clear();
    }
    public void setValues(Employee employee, ObservableList<Object> clients, ObservableList<Object> flights,EmployeeController employeeController){
        this.employeeController=employeeController;
        this.employee=employee;
        this.flights=flights;
        ObservableList<Object> cs= FXCollections.observableArrayList(), fs=FXCollections.observableArrayList();
        for(int i=0;i<clients.size();i++)
            cs.add(((Client)clients.get(i)).getId()+"-"+((Client)clients.get(i)).getFirstName());
        for(int i=0;i<flights.size();i++)
            fs.add(((Flight)flights.get(i)).getId()+"-"+((Flight)flights.get(i)).getDestination()+", "+((Flight)flights.get(i)).getDepartureDate());
        client.getItems().addAll(cs);
        flight.getItems().addAll(fs);
    }

}
