package Mod;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminPage implements Initializable {
    @FXML
    private Label msg,msg2;
    @FXML
    private Pane addPane,delPane;
    @FXML
    private TextField lname,fname;
    @FXML
    private PasswordField rpassword,password;
    @FXML
    private ComboBox s;
    ObservableList<String> list= FXCollections.observableArrayList("ADD","REMOVE");

    Connection connection=SQLConnection.Connector();
    PreparedStatement preparedStatement=null;
    ResultSet resultSet=null;

    public boolean ManagerPresent() throws SQLException {
        String query="select * from users where id=1";
        try {
            preparedStatement = connection.prepareStatement(query);

            //System.out.println(preparedStatement);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                //msg.setText("Manager present");
                return true;

            }
            else{
                //msg.setText("Manager not present");
                return false;}
        }
        catch (Exception e){
            //msg.setText("Manager not present");
            return false;
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public void Add() throws SQLException {
        AdminPage a=new AdminPage();
        if(a.ManagerPresent()){
            msg.setText("Manager present");
        }
        else{
            if(fname.getText().length()!=0 && password.getText().length()!=0 && lname.getText().length()!=0 && rpassword.getText().length()!=0){
                if(password.getText().equals(rpassword.getText())){
                    String username=fname.getText()+lname.getText();
                    String firstname=fname.getText();
                    String lastname=lname.getText();
                    String pass=password.getText();
                    String query="insert into users (id,fname,lname,username,password,auth) values (?,?,?,?,?,?)";
                    try {
                        if (connection != null) {
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setString(1,"1");
                            preparedStatement.setString(2,firstname);
                            preparedStatement.setString(3,lastname);
                            preparedStatement.setString(4,username);
                            preparedStatement.setString(5,pass);
                            preparedStatement.setString(6,"M");
                            //System.out.println(preparedStatement);
                            preparedStatement.executeUpdate();
                            //System.out.println("hiiiii");
                            msg.setTextFill(Color.rgb(2,201,54));
                            msg.setText("User Added");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else{
                    msg.setTextFill(Color.rgb(220,29,61));
                    msg.setText("The password and retyped password do not match");}
            }
            else{
                msg.setTextFill(Color.rgb(220,29,61));
                msg.setText("Please fill in the required");}
        }
    }

    public void DEL() throws SQLException {
        AdminPage a=new AdminPage();
        if(!a.ManagerPresent()){
            msg2.setTextFill(Color.rgb(220,29,61));
            msg2.setText("Manager not present ");
        }
        else{
            String query="delete from users where id=1";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            msg2.setTextFill(Color.rgb(2,201,54));
            msg2.setText("Manager Removed");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        s.setItems(list);
    }

    public void SelectOption(){
        if(s.getValue().equals("ADD")){
            addPane.setVisible(true);
            delPane.setVisible(false);
        }
        else {
            addPane.setVisible(false);
            delPane.setVisible(true);
        }
    }

    public void logout(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        LoginController lc=new LoginController();
        loader.setController(lc);
        Pane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../Mod/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
