package sample.model;

/* COMPOSITION PATTERN IS USED, ADMIN AND EMPLOYEE EXTENDS THIS CLASS WHERE ADMIN CONTROLS OTHER EMPLOYEES OF THE COMPANY*/

import java.sql.Connection;

public class Employee extends Agent{
    public Employee(int id, int salary, int role, String fullName, String phone, String address, String username, String password) {
        super(id, salary, role, fullName, phone, address, username, password);
    }
    public static void addEmployee(Connection connection, String fullName, String phone, String address, String salary, String username, String password){
        try{
            connection.createStatement().executeUpdate("insert into `agents` values(NULL,'"+fullName+"','"+phone+"','"+address+"','"+salary+"','"+username+"','"+password+"',1)");
        }catch(Exception e){e.printStackTrace();}
    }
}
