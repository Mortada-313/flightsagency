package sample.model;

/* COMPOSITION PATTERN IS USED, ADMIN AND EMPLOYEE EXTENDS THIS CLASS WHERE ADMIN CONTROLS OTHER EMPLOYEES OF THE COMPANY*/

public class Employee extends Agent{
    public Employee(Integer id, Integer salary, String fullName, String phone, String address, String username, String password, String role) {
        super(id, salary, fullName, phone, address, username, password, role);
    }
}
