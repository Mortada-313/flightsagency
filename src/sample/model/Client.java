package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;

public class Client {

    String id,firstName, lastName, phone, address, passport, birthday, email;

    public Client(String id, String firstName, String lastName, String phone, String address, String passport, String birthday, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.passport = passport;
        this.birthday = birthday;
        this.email = email;
    }

    public static ObservableList<Object> loadClients(Connection connection) {
        ObservableList<Object> clients;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM clients");
            clients = FXCollections.observableArrayList();
            while (resultSet.next()) {
                clients.add(new Client(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8)));
            }
            return clients;
        }catch (Exception e){}
        return null;
    }
    public static void addClient(Connection connection, String firstName, String lastName, String phone, String address,long passport , String birthday, String email){
        try{
            connection.createStatement().executeUpdate("insert into `clients` values(NULL,'"+firstName+"','"+lastName+"','"+phone+"','"+address+"','"+passport+
                    "','"+birthday+"','"+email+"')");
        }catch(Exception e){e.printStackTrace();}
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
