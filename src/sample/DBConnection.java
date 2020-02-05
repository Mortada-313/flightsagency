package sample;

import java.sql.Connection;
import java.sql.DriverManager;

/* SINGLETON PATTERN needed to use ONE DATABASE CONNECTION OBJECT*/
public class DBConnection {
    private static Connection connection;
    private DBConnection(){}
    public static Connection getConnection(){
        if(connection==null){
            try{
                String url,user,pass;
                url="jdbc:mysql://localhost:3306/flightagency";
                user="root";
                pass="";
                Class.forName("com.mysql.jdbc.Driver");
                connection= DriverManager.getConnection(url,user,pass);
            }catch (Exception e) { e.printStackTrace(); }
        }
        return connection;
    }
}
