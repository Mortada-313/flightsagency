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

public class EmployeeController implements Initializable {
    private Employee employee;
    private ObservableList<Object> reservations,flights;
    private Connection connection;

    public EmployeeController(){
        connection= DBConnection.getConnection();
    }
    @FXML
    private Label username;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button buttonTitle;
    @FXML
    private Button buttonReservations,buttonFlights,buttonEmployees,buttonReports,buttonSignOut;
    @FXML
    private TableView<Object> adminTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showReservations();
    }
    @FXML
    public void showReservations(){
        initialize();
        buttonReservations.setEffect(new InnerShadow());
        ((ImageView)buttonTitle.getGraphic()).setImage(new Image("/sample/images/reserve.png"));
        buttonTitle.setText("Reservations");
        TableColumn<Object,String> columnOne=new TableColumn<>(),columnTwo=new TableColumn<>(),columnThree=new TableColumn<>(),columnFour=new TableColumn<>(),columnFive=new TableColumn<>(),columnSix=new TableColumn<>();
        columnOne.setText("First Name");
        columnTwo.setText("Last Name");
        columnThree.setText("Phone");
        columnFour.setText("Cost($)");
        columnFive.setText("Departure Date");
        columnSix.setText("Destination");
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT c.first_name, c.last_name, c.phone, r.cost, f.departure_date, f.departure_time, f.destination\n" +
                    "FROM clients AS c,reservations AS r, flights AS f\n" +
                    "where c.id=r.clients_id AND f.id=r.flights_id");
            reservations = FXCollections.observableArrayList();
            while (resultSet.next()) {
                reservations.add(new Reservation(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5) + " " +
                        resultSet.getString(6), resultSet.getString(7)));
            }
            columnOne.setCellValueFactory(new PropertyValueFactory<>("fname"));
            columnTwo.setCellValueFactory(new PropertyValueFactory<>("lname"));
            columnThree.setCellValueFactory(new PropertyValueFactory<>("phone"));
            columnFour.setCellValueFactory(new PropertyValueFactory<>("Cost"));
            columnFive.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
            columnSix.setCellValueFactory(new PropertyValueFactory<>("destination"));
            adminTable.getColumns().addAll(columnOne, columnTwo, columnThree, columnFour, columnFive, columnSix);
            adminTable.setItems(reservations);
        } catch (Exception e) { e.printStackTrace(); }
    }
    @FXML
    public void showFlights(){
        initialize();
        buttonFlights.setEffect(new InnerShadow());
        ((ImageView)buttonTitle.getGraphic()).setImage(new Image("/sample/images/plane.png"));
        buttonTitle.setText("Flights");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String[] strings=dtf.format(now).split(" ");// at index 0: date years-months-days ... at index 1: time hours:minutes:seconds
        TableColumn<Object,String> columnOne=new TableColumn<>(),columnTwo=new TableColumn<>(),columnThree=new TableColumn<>(),columnFour=new TableColumn<>(),columnFive=new TableColumn<>();
        columnOne.setText("Destination");
        columnTwo.setText("Company Name");
        columnThree.setText("Departure Date");
        columnFour.setText("Cost($)");
        columnFive.setText("Trip Time");
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * \n" +
                    "FROM flights\n" +
                    "where departure_date>='"+strings[0]+"'");
            flights = FXCollections.observableArrayList();
            while (resultSet.next()) {
                flights.add(new Flight(resultSet.getString(2), resultSet.getString(4),
                        resultSet.getString(5)+ " " + resultSet.getString(6) ,
                        resultSet.getString(7), resultSet.getString(8)));
            }
            columnOne.setCellValueFactory(new PropertyValueFactory<>("destiantion"));
            columnTwo.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            columnThree.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
            columnFour.setCellValueFactory(new PropertyValueFactory<>("cost"));
            columnFive.setCellValueFactory(new PropertyValueFactory<>("tripTime"));
            adminTable.getColumns().addAll(columnOne, columnTwo, columnThree, columnFour, columnFive);
            adminTable.setItems(flights);
        } catch (Exception e) { e.printStackTrace(); }
    }
    @FXML
    public void showEmployees(){
        initialize();
        buttonEmployees.setEffect(new InnerShadow());
        ((ImageView)buttonTitle.getGraphic()).setImage(new Image("/sample/images/employee.png"));
        buttonTitle.setText("Employees");
    }
    @FXML
    public void showReports(){
        initialize();
        buttonReports.setEffect(new InnerShadow());
        ((ImageView)buttonTitle.getGraphic()).setImage(new Image("/sample/images/report.png"));
        buttonTitle.setText("Reports");
    }
    @FXML
    public void signOut(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));
            Stage stage = (Stage) buttonSignOut.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void initialize(){
        buttonFlights.setEffect(null);
        buttonEmployees.setEffect(null);
        buttonReports.setEffect(null);
        buttonReservations.setEffect(null);
        adminTable.getColumns().clear();
        adminTable.setItems(null);
    }
    public void setUsername(String fullName){
        username.setText(fullName);
    }
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
