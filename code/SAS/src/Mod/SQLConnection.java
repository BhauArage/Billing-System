package Mod;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {
    public static Connection Connector(){
        String dbName="sas";
        String user="root";
        String pass="";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,user,pass);
            return conn;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
