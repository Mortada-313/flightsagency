package sample.controller;

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
import sample.model.Employee;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginController  {
    private static int employeeId;
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
            ResultSet user=connection.createStatement().executeQuery("SELECT id,role FROM agents WHERE username='"+username.getText()+"' AND password='"+password.getText()+"'");
            if(user.next()){
                if(user.getInt(2)==0){
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/view/admin.fxml"));
                    Stage stage = (Stage) login.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else{
                    employeeId=user.getInt(1);
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/view/employee.fxml"));
                    Stage stage = (Stage) login.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (SQLException | IOException e) { e.printStackTrace(); }
    }
    public static int getEmployeeId() {
        return employeeId;
    }
}
