package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.DBConnection;
import sample.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    private Employee employee;

    private int stage=0;
    private ObservableList<Object> reservations,flights,clients; // where employees are being retrieved from the admin instance(composition pattern)
    private Connection connection;

    public EmployeeController(){
        connection= DBConnection.getConnection();
        reservations= Reservation.loadReservations(connection);
        flights= Flight.loadFlights(connection);
        clients= Client.loadClients(connection);
    }
    @FXML
    private Label username;
    @FXML
    private ImageView profilePhoto,add;
    @FXML
    private Button buttonTitle;
    @FXML
    private Button buttonReservations,buttonFlights,buttonClients,buttonSignOut;
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
        setAdd(false);
        TableColumn<Object, String> columnOne = new TableColumn<>(), columnTwo = new TableColumn<>(), columnThree = new TableColumn<>(), columnFour = new TableColumn<>(),
                columnFive = new TableColumn<>();
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
    public void showClients() {
        initialize(buttonClients,"/sample/images/customer.png","Clients");
        stage=1;
        setAdd(true);
        TableColumn<Object, String> columnOne = new TableColumn<>(), columnTwo = new TableColumn<>(), columnThree = new TableColumn<>(), columnFour = new TableColumn<>(),
                columnFive = new TableColumn<>(), columnSix = new TableColumn<>(),columnSeven=new TableColumn<>();
        columnOne.setText("First Name");
        columnTwo.setText("Last Name");
        columnThree.setText("Phone");
        columnFour.setText("Address");
        columnFive.setText("Passport");
        columnSix.setText("Birthday");
        columnSeven.setText("Email");

        columnOne.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnTwo.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnThree.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnFour.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnFive.setCellValueFactory(new PropertyValueFactory<>("passport"));
        columnSix.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        columnSeven.setCellValueFactory(new PropertyValueFactory<>("email"));
        table.getColumns().addAll(columnOne, columnTwo, columnThree, columnFour, columnFive, columnSix,columnSeven);
        table.setItems(clients);
    }

    @FXML
    public void showReservations(){
        initialize(buttonReservations,"/sample/images/reserve.png","Reservations");
        stage=2;
        setAdd(true);
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
    public void signOut(){
        stage=3;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));
            Stage stage = (Stage) buttonSignOut.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Flights Agency");
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    @FXML
    public void newRecord(){
        Parent root=null;
        FXMLLoader loader=null;
        Stage newStage=new Stage();
        try {
            switch (stage) {
                case 1: //add client
                    loader =new FXMLLoader(getClass().getResource("/sample/view/add_client.fxml"));
                    root=loader.load();
                    AddClientController flightController=loader.getController();
                    flightController.setValues(this);
                    break;
                case 2: //add reservation
                    loader =new FXMLLoader(getClass().getResource("/sample/view/add_reservation.fxml"));
                    root=loader.load();
                    AddReservationController reservationController=loader.getController();
                    reservationController.setValues(employee,clients,flights,this);
            }
            newStage.setScene(new Scene(root));
            newStage.show();
        }catch (Exception e){e.printStackTrace();}
    }

    public void initialize(Button button,String imageSource, String name){ //function used to initialize the buttons and image and title
        buttonFlights.setEffect(null);
        buttonClients.setEffect(null);
        buttonReservations.setEffect(null);
        table.getColumns().clear();
        table.setItems(null);
        button.setEffect(new InnerShadow());
        ((ImageView)buttonTitle.getGraphic()).setImage(new Image(imageSource));
        buttonTitle.setText(name);
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
        username.setText(employee.getFullName());
        try{profilePhoto.setImage(new Image("/sample/images/"+employee.getId()+".png"));} // putted in try catch, an admin may not have an image yet
        catch (Exception e){}
    }

    public void setAdd(boolean b){
        add.setVisible(b);
        add.setDisable(!b);
    }

    public TableView<Object> getTable() {
        return table;
    }

    public ObservableList<Object> getClients(){
        return clients;
    }

    public void setClients(ObservableList<Object> clients) {
        this.clients=clients;
    }

    public ObservableList<Object> getReservations() {
        return reservations;
    }

    public void setReservations(ObservableList<Object> reservations) {
        this.reservations = reservations;
    }
}
