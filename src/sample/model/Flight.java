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
    private String id,destination,companyName,departureDate,cost,tripTime;

    public Flight(String id, String destination, String companyName, String departureDate, String cost, String tripTime) {
        this.id=id;
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
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * " +
                    "FROM flights "
                    +"WHERE departure_date>='" + strings[0] + "'");
            flights = FXCollections.observableArrayList();
            while (resultSet.next()) {
                flights.add(new Flight(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4) + " " + resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7)));
            }
            return flights;
        }catch(Exception e){}
        return null;
    }
    public static void addFlight(Connection connection,String destination,String company, String depDate,String depTime,String tripTime, String cost){
        try{
            connection.createStatement().executeUpdate("insert into `flights` values(NULL,'"+destination+"','"+company+"','"+depDate+"','"+depTime+"','"+cost+"','"+tripTime+"')");
        }catch(Exception e){e.printStackTrace();}
    }

    public static int getPosition(String id, ObservableList<Object> flights){
        for(int i=0; i<flights.size(); i++)
            if(id.equals(((Flight)flights.get(i)).getId()))
                return i;
        return -1;
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

    public String getId() {
        return id;
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
