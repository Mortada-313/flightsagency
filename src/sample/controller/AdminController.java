package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.DBConnection;
import sample.model.Admin;
import sample.model.Employee;
import sample.model.Flight;
import sample.model.Reservation;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    private int stage=0;
    private Admin admin;
    private ObservableList<Object> reservations,flights; // where employees are being retrieved from the admin instance(composition pattern)
    private Connection connection;

    public AdminController(){
        connection= DBConnection.getConnection();
        reservations= Reservation.loadReservations(connection);
        flights= Flight.loadFlights(connection);
    }
    @FXML
    private Label username;
    @FXML
    private ImageView profilePhoto,add;
    @FXML
    private Button buttonTitle;
    @FXML
    private Button buttonReservations,buttonFlights,buttonEmployees,buttonReports,buttonSignOut;
    @FXML
    private TableView<Object> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showFlights();
    }

    @FXML
    public void showFlights() {
        initialize(buttonFlights,"/sample/images/plane.png","Flights");
        stage=0;
        setAdd(true);
        TableColumn<Object, String> columnOne = new TableColumn<>(), columnTwo = new TableColumn<>(), columnThree = new TableColumn<>(), columnFour = new TableColumn<>(), columnFive = new TableColumn<>();
        columnOne.setText("Destination");
        columnTwo.setText("Company Name");
        columnThree.setText("Departure Date");
        columnFour.setText("Cost($)");
        columnFive.setText("Trip Time");

        columnOne.setCellValueFactory(new PropertyValueFactory<>("destination"));
        columnTwo.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        columnThree.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        columnFour.setCellValueFactory(new PropertyValueFactory<>("cost"));
        columnFive.setCellValueFactory(new PropertyValueFactory<>("tripTime"));

        table.getColumns().addAll(columnOne, columnTwo, columnThree, columnFour, columnFive);
        table.setItems(flights);
    }
    @FXML
    public void showEmployees() {
        initialize(buttonEmployees,"/sample/images/employee.png","Employees");
        stage=1;
        setAdd(true);
        TableColumn<Object, String> columnOne = new TableColumn<>(), columnTwo = new TableColumn<>(), columnThree = new TableColumn<>(), columnFour = new TableColumn<>(), columnFive = new TableColumn<>(), columnSix = new TableColumn<>();
        columnOne.setText("Full Name");
        columnTwo.setText("Username");
        columnThree.setText("Password");
        columnFour.setText("Salary($)");
        columnFive.setText("Phone");
        columnSix.setText("Address");

        columnOne.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        columnTwo.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnThree.setCellValueFactory(new PropertyValueFactory<>("password"));
        columnFour.setCellValueFactory(new PropertyValueFactory<>("salary"));
        columnFive.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnSix.setCellValueFactory(new PropertyValueFactory<>("address"));

        table.getColumns().addAll(columnOne, columnTwo, columnThree, columnFour, columnFive, columnSix);
        table.setItems(admin.getEmployees());
    }
    @FXML
    public void showReservations(){
        initialize(buttonReservations,"/sample/images/reserve.png","Reservations");
        stage=2;
        setAdd(false);
        TableColumn<Object,String> columnOne=new TableColumn<>(),columnTwo=new TableColumn<>(),columnThree=new TableColumn<>(),columnFour=new TableColumn<>(),columnFive=new TableColumn<>(),columnSix=new TableColumn<>();
        columnOne.setText("First Name");
        columnTwo.setText("Last Name");
        columnThree.setText("Phone");
        columnFour.setText("Cost($)");
        columnFive.setText("Departure Date");
        columnSix.setText("Destination");

        columnOne.setCellValueFactory(new PropertyValueFactory<>("fname"));
        columnTwo.setCellValueFactory(new PropertyValueFactory<>("lname"));
        columnThree.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnFour.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        columnFive.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        columnSix.setCellValueFactory(new PropertyValueFactory<>("destination"));

        table.getColumns().addAll(columnOne, columnTwo, columnThree, columnFour, columnFive, columnSix);
        table.setItems(reservations);
    }
    @FXML
    public void showReports(){
        initialize(buttonReports,"/sample/images/report.png","Reports");
        stage=3;
        setAdd(false);
    }
    @FXML
    public void signOut(){
        stage=4;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));
            Stage stage = (Stage) buttonSignOut.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    @FXML
    public void newRecord(){
        Parent root=null;
        Stage newStage=new Stage();
        try {
            switch (stage) {
                case 0: //add flight
                    root = FXMLLoader.load(getClass().getResource("/sample/view/add_flight.fxml"));
                    break;
                case 1: //add employee
                    root = FXMLLoader.load(getClass().getResource("/sample/view/add_employee.fxml"));
            }
            newStage.setScene(new Scene(root));
            newStage.show();
        }catch (Exception e){e.printStackTrace();}
    }
    public void initialize(Button button,String imageSource, String name){ //function used to initialize the buttons and image and title
        buttonFlights.setEffect(null);
        buttonEmployees.setEffect(null);
        buttonReports.setEffect(null);
        buttonReservations.setEffect(null);
        table.getColumns().clear();
        table.setItems(null);
        button.setEffect(new InnerShadow());
        ((ImageView)buttonTitle.getGraphic()).setImage(new Image(imageSource));
        buttonTitle.setText(name);
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
        username.setText(admin.getFullName());
        admin.loadEmployees(connection);
        try{profilePhoto.setImage(new Image("/sample/images/"+admin.getId()+".png"));} // putted in try catch, an admin may not have an image yet
        catch (Exception e){e.printStackTrace();}
    }
    public void setAdd(boolean b){
        add.setVisible(b);
        add.setDisable(!b);
    }

}
