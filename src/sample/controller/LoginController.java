package sample.controller;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DBConnection;
import sample.model.Admin;
import sample.model.Employee;
import sun.rmi.runtime.Log;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginController  {
    private Connection connection;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;

    public LoginController(){
        connection= DBConnection.getConnection();
    }
    @FXML
    public void login(ActionEvent event){
        try {
            ResultSet user=connection.createStatement().executeQuery("SELECT * FROM agents WHERE username='"+username.getText()+"' AND password='"+password.getText()+"'");
            if(user.next()){
                if(user.getInt(8)==0){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/admin.fxml"));
                    Parent root = loader.load();
                    AdminController adminController=loader.getController();
                    adminController.setAdmin(new Admin(user.getInt(1),user.getInt(5),user.getInt(8),user.getString(2),user.getString(3),user.getString(4),username.getText(),password.getText()));
                    Stage stage = (Stage) login.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else{
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("/sample/view/employee.fxml"));
                    Parent root = loader.load();
                    EmployeeController employeeController=loader.getController();
                    employeeController.setEmployee(new Employee(user.getInt(1),user.getInt(5),user.getInt(8),user.getString(2),user.getString(3),user.getString(4),username.getText(),password.getText()));
                    employeeController.setUsername(user.getString(2));
                    Stage stage = (Stage) login.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (SQLException | IOException e) { e.printStackTrace(); }
    }
}
