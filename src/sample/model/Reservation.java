package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;

public class Reservation {
    private String id,fname,lname,phone,cost,departureDate,destination;

    public Reservation(String id, String fname, String lname, String phone, String cost, String departureDate, String destination) {
        this.id=id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.cost = cost;
        this.departureDate = departureDate;
        this.destination = destination;
    }

    public static ObservableList<Object> loadReservations(Connection connection){
        ObservableList<Object> reservations;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT r.id, c.first_name, c.last_name, c.phone, r.cost, f.departure_date," +
                    " f.departure_time, f.destination FROM clients AS c,reservations AS r, flights AS f WHERE c.id=r.clients_id AND f.id=r.flights_id");
            reservations = FXCollections.observableArrayList();
            while (resultSet.next()) {
                reservations.add(new Reservation(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6) + " " +
                        resultSet.getString(7), resultSet.getString(8)));
            }
            return reservations;
        }catch (Exception e){}
        return null;
    }

    public static void addReservation(Connection connection,int cost, int winRate, int earnings, String clientId, String flightId, int employeeId){
        try{
            connection.createStatement().executeUpdate("insert into `reservations` values(NULL,'"+cost+"','"+winRate+"','"+earnings+"','"+clientId+"','"+flightId
                    +"','"+employeeId+"',DEFAULT)");
        }catch(Exception e){e.printStackTrace();}
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
