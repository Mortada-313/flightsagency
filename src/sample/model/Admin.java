package sample.model;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/* COMPOSITION PATTERN IS USED, ADMIN AND EMPLOYEE EXTENDS THIS CLASS WHERE ADMIN CONTROLS OTHER EMPLOYEES OF THE COMPANY*/

public class Admin extends Agent {
    ObservableList<Agent> employees;
    public Admin(Integer id, Integer salary, String fullName, String phone, String address, String username, String password, String role) {
        super(id, salary, fullName, phone, address, username, password, role);
    }
}
