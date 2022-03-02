package Mod;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LoginModel {
    Connection connection;
    //String authority;
    public LoginModel(){
        connection=SQLConnection.Connector();
        if(connection==null) System.exit(1);
    }

    public boolean isDbConnected(){
        try {
           return !connection.isClosed();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean isLogin(String user,String pass,String auth) throws SQLException{
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String query="select * from users where username=? and password=? and auth=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,pass);
            preparedStatement.setString(3,auth);
            //System.out.println(preparedStatement);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else
                return false;
        }
        catch (Exception e){
            return false;
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

}
