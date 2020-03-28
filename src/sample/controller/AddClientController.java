package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.DBConnection;
import sample.model.Client;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AddClientController implements Initializable {
    private EmployeeController employeeController;
    private Connection connection;
    @FXML
    private TextField firstName,lastName,phone,address,birthday,passport,email;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection= DBConnection.getConnection();
    }
    public void addClient(){
        Client.addClient(connection,firstName.getText(),lastName.getText(),phone.getText(),address.getText(),Long.parseLong(passport.getText()),birthday.getText(),email.getText());
        employeeController.setClients(Client.loadClients(connection));
        employeeController.getTable().setItems(employeeController.getClients());
//        firstName.clear();lastName.clear();phone.clear();address.clear();birthday.clear();passport.clear();email.clear();
        firstName.getScene().getWindow().hide();
    }

    public void setValues(EmployeeController employeeController) {
        this.employeeController=employeeController;
    }
}
