package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.DBConnection;
import sample.model.*;

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
    private ObservableList<Object> reservations,flights,reports; // where employees are being retrieved from the admin instance(composition pattern)

    private Connection connection;
    private EventHandler<MouseEvent> eventHandler;
    public AdminController(){
        connection= DBConnection.getConnection();
        reservations= Reservation.loadReservations(connection);
        flights= Flight.loadFlights(connection);
        reports= Report.loadReports(connection);
        eventHandler = e -> {
            if(stage==1){
                int index=table.getSelectionModel().getSelectedIndex();
                if(index!=-1)
                    editEmployee((Employee) admin.getEmployees().get(index),index);
                table.getSelectionModel().clearSelection();
            }
        };
    }
    @FXML
    private Label username,edit;
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
        table.setOnMouseClicked(eventHandler);

    }

    @FXML
    public void showFlights() {
        initialize(buttonFlights,"/sample/images/plane.png","Flights",null);
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
        initialize(buttonEmployees,"/sample/images/employee.png","Employees","click an employee for editing");
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
        initialize(buttonReservations,"/sample/images/reserve.png","Reservations",null);
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
        initialize(buttonReports,"/sample/images/report.png","Reports",null);
        stage=3;
        setAdd(false);
        TableColumn<Object, String> columnOne = new TableColumn<>(), columnTwo = new TableColumn<>(), columnThree = new TableColumn<>(), columnFour = new TableColumn<>(), columnFive = new TableColumn<>(), columnSix = new TableColumn<>();
        columnOne.setText("Year-Month");
        columnTwo.setText("Total Reservations");
        columnThree.setText("Total Winnings");
        columnFour.setText("Employee");
        columnFive.setText("Reservations");
        columnSix.setText("Winnings");

        columnOne.setCellValueFactory(new PropertyValueFactory<>("month"));
        columnTwo.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
        columnThree.setCellValueFactory(new PropertyValueFactory<>("totalWinnings"));
        columnFour.setCellValueFactory(new PropertyValueFactory<>("employee"));
        columnFive.setCellValueFactory(new PropertyValueFactory<>("count"));
        columnSix.setCellValueFactory(new PropertyValueFactory<>("winnings"));

        table.getColumns().addAll(columnOne, columnTwo, columnThree,new TableColumn<Object,String>("_________") , columnFour, columnFive, columnSix);
        table.setItems(reports);
    }
    public void editEmployee(Employee employee, int position){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/edit_employee.fxml"));
            Parent root = loader.load();
            EditEmployeeController employeeController = loader.getController();
            employeeController.setValues(admin,employee,position,this);
            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){e.printStackTrace();}
    }
    @FXML
    public void signOut(){
        stage=4;
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
        Stage newStage=new Stage();
        try {
            switch (stage) {
                case 0: //add flight
                    FXMLLoader loader1 =new FXMLLoader(getClass().getResource("/sample/view/add_flight.fxml"));
                    root=loader1.load();
                    AddFlightController fController=loader1.getController();
                    fController.setValues(admin,this);
                    break;
                case 1: //add employee
                    FXMLLoader loader2 =new FXMLLoader(getClass().getResource("/sample/view/add_employee.fxml"));
                    root=loader2.load();
                    AddEmployeeController eController=loader2.getController();
                    eController.setValues(admin,this);
            }
            newStage.setScene(new Scene(root));
            newStage.show();
        }catch (Exception e){e.printStackTrace();}
    }
    public void initialize(Button button,String imageSource, String name,String editText){ //function used to initialize the buttons and image and title
        buttonFlights.setEffect(null);
        buttonEmployees.setEffect(null);
        buttonReports.setEffect(null);
        buttonReservations.setEffect(null);
        table.getColumns().clear();
        table.setItems(null);
        button.setEffect(new InnerShadow());
        ((ImageView)buttonTitle.getGraphic()).setImage(new Image(imageSource));
        buttonTitle.setText(name);
        edit.setText(editText);
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

    public TableView<Object> getTable() {
        return table;
    }

    public ObservableList<Object> getFlights() {
        return flights;
    }

    public void setFlights(ObservableList<Object> flights) {
        this.flights = flights;
    }
}
