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
    public static void addClient(Connection connection, String firstName, String lastName, String phone, String address,String passport , String birthday, String email){
        try{
            connection.createStatement().executeUpdate("insert into `agents` values(NULL,'"+firstName+"','"+lastName+"','"+phone+"','"+address+"','"+passport+
                    "','"+birthday+"','"+email+"')");
        }catch(Exception e){e.printStackTrace();}
    }
}
