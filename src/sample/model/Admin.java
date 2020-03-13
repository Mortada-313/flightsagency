package sample.model;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/* COMPOSITION PATTERN IS USED, ADMIN AND EMPLOYEE EXTENDS THIS CLASS WHERE ADMIN CONTROLS OTHER EMPLOYEES OF THE COMPANY*/

public class Admin extends Agent {
    ObservableList<Object> employees;
    public Admin(int id, int salary, int role, String fullName, String phone, String address, String username, String password) {
        super(id, salary, role, fullName, phone, address, username, password);
    }

    public ObservableList<Object> getEmployees() {
        return employees;
    }

    public ObservableList<Object> loadEmployees(Connection connection) {
        employees= FXCollections.observableArrayList();
        try {
            ResultSet users=connection.createStatement().executeQuery("SELECT * FROM agents WHERE role=1");
            while(users.next())
                employees.add(new Employee(users.getInt(1),users.getInt(5),users.getInt(8),users.getString(2),users.getString(3),users.getString(4),users.getString(6),users.getString(7)));
            return employees;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
