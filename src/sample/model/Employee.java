package sample.model;

/* COMPOSITION PATTERN IS USED, ADMIN AND EMPLOYEE EXTENDS THIS CLASS WHERE ADMIN CONTROLS OTHER EMPLOYEES OF THE COMPANY*/

public class Employee extends Agent{
    public Employee(int id, int salary, int role, String fullName, String phone, String address, String username, String password) {
        super(id, salary, role, fullName, phone, address, username, password);
    }
}
