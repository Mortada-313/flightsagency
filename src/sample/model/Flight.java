package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {
    private String destination,companyName,departureDate,cost,tripTime;

    public Flight(String destination, String companyName, String departureDate, String cost, String tripTime) {
        this.destination = destination;
        this.companyName = companyName;
        this.departureDate = departureDate;
        this.cost = cost;
        this.tripTime = tripTime;
    }

    public static ObservableList<Object> loadFlights(Connection connection) {
        ObservableList<Object> flights;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String[] strings = dtf.format(now).split(" ");// at index 0: date years-months-days ... at index 1: time hours:minutes:seconds
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * \n" +
                    "FROM flights\n" +
                    "where departure_date>='" + strings[0] + "'");
            flights = FXCollections.observableArrayList();
            while (resultSet.next()) {
                flights.add(new Flight(resultSet.getString(2), resultSet.getString(4),
                        resultSet.getString(5) + " " + resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8)));
                return flights;
            }
        }catch(Exception e){}
        return null;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }
}
