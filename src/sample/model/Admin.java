package sample.model;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/* COMPOSITION PATTERN IS USED, ADMIN AND EMPLOYEE EXTENDS THIS CLASS WHERE ADMIN CONTROLS OTHER EMPLOYEES OF THE COMPANY*/

public class Admin extends Agent {
    ObservableList<Agent> employees;
}
