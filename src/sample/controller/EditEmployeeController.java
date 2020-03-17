package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import sample.DBConnection;
import sample.model.Admin;
import sample.model.Employee;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {
    private Connection connection;
    private AdminController adminController;
    private Employee employee;
    private Admin admin;
    private int position;
    @FXML
    private TextField fullName,address,phone,salary,username,password;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection= DBConnection.getConnection();
    }
    public void editEmployee(){
        Employee.editEmployee(connection,employee.getId(),  fullName.getText(),  phone.getText(),  address.getText(),  salary.getText(),  username.getText(),  password.getText());
        employee.setFullName(fullName.getText());
        employee.setAddress(address.getText());
        employee.setPhone(phone.getText());
        employee.setSalary(Integer.parseInt(salary.getText()));
        employee.setUsername(username.getText());
        employee.setPassword(password.getText());
        adminController.getTable().refresh();
    }
    public void removeEmployee(){
        Employee.removeEmployee(connection,employee.getId());
        admin.getEmployees().remove(position);
        fullName.getScene().getWindow().hide();
    }
    public void setValues(Admin admin,Employee employee, int position, AdminController adminController){
        this.admin=admin;
        this.employee=employee;
        this.position=position;
        this.adminController=adminController;
        fullName.setText(employee.getFullName());
        address.setText(employee.getAddress());
        phone.setText(employee.getPhone());
        salary.setText(""+employee.getSalary());
        username.setText(employee.getUsername());
        password.setText(employee.getPassword());
    }
}
