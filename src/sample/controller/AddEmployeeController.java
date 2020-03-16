package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import sample.DBConnection;
import sample.model.Employee;

import java.net.URL;
import java.sql.Connection;

import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {
    private Connection connection;
    @FXML
    private TextField fullName,address,phone,salary,username,password;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection= DBConnection.getConnection();
    }
    public void addEmployee(){
        Employee.addEmployee(connection,  fullName.getText(),  phone.getText(),  address.getText(),  salary.getText(),  username.getText(),  password.getText());
    }
}
