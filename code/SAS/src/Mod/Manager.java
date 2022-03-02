package Mod;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Manager implements Initializable {

    Connection connection=SQLConnection.Connector();
    PreparedStatement preparedStatement=null;

    @FXML
    private Pane emp,stats,add,del,editPane;
    @FXML
    private Label msg,msg1,msg2;
    @FXML
    private TextField lname,fname,empid,username,empid1,username1;
    @FXML
    private PasswordField rpassword,password,password1,rpassword1;
    @FXML
    public ComboBox chooseAction;

    ObservableList<String> list= FXCollections.observableArrayList("ADD EMPLOYEE","REMOVE EMPLOYEE","EDIT EMPLOYEE DETAILS","VIEW EMPLOYEE DETAILS");
    @FXML
    public ComboBox<String> role;
    ObservableList<String>list1= FXCollections.observableArrayList("Cashier","Staff");

    String auth;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseAction.setItems(list);
        role.setItems(list1);
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

    public void addremoveemp(){
        emp.setVisible(true);
        stats.setVisible(false);
    }

    public void setChooseAction(ActionEvent event){
        if(chooseAction.getValue()=="ADD EMPLOYEE"){
            add.setVisible(true);
            del.setVisible(false);
            editPane.setVisible(false);
        }
        if(chooseAction.getValue()=="REMOVE EMPLOYEE"){
            add.setVisible(false);
            del.setVisible(true);
            editPane.setVisible(false);
        }
        if(chooseAction.getValue()=="EDIT EMPLOYEE DETAILS"){
            add.setVisible(false);
            del.setVisible(false);
            editPane.setVisible(true);
        }
    }

    public void RoleChanged(){
        String r=role.getValue();
        //System.out.println(r);
        if(r.equals("Cashier"))
            auth="C";
        if(r.equals("Staff"))
            auth="S";
        //System.out.println(auth);
    }

    public void Addemp() {
        if(fname.getText().length()!=0 && password.getText().length()!=0 && lname.getText().length()!=0 && rpassword.getText().length()!=0 && auth!=null){
            if(password.getText().equals(rpassword.getText())){
            String username=fname.getText()+lname.getText();
            String firstname=fname.getText();
            String lastname=lname.getText();
            String pass=password.getText();
            String query="insert into users (fname,lname,username,password,auth) values (?,?,?,?,?)";
                try {
                    if (connection != null) {
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1,firstname);
                        preparedStatement.setString(2,lastname);
                        preparedStatement.setString(3,username);
                        preparedStatement.setString(4,pass);
                        preparedStatement.setString(5,auth);
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

    public void Delemp()  {
        if(username.getText().length()!=0 && empid.getText().length()!=0){
            String user=username.getText();
            String id=empid.getText();
            if(Integer.parseInt(id)!=1 && Integer.parseInt(id)!=0 ){
                String query="delete from users where id=? and username=?";
                try {
                    preparedStatement=connection.prepareStatement(query);
                    preparedStatement.setString(1,id);
                    preparedStatement.setString(2,user);
                    preparedStatement.executeUpdate();
                    //System.out.println(preparedStatement);
                    msg1.setTextFill(Color.rgb(2,201,54));
                    msg1.setText("User Removed");

                }
                catch (SQLException throwables) {
                throwables.printStackTrace();
                }
            }
            else{
                msg1.setTextFill(Color.rgb(220,29,61));
                if(Integer.parseInt(id)==1)
                    msg1.setText("Cannot Delete Manager");
                else
                    msg1.setText("Cannot Delete Admin");}
        }
        else{
            msg1.setTextFill(Color.rgb(220,29,61));
            msg1.setText("Please fill in the required");
        }
    }

    public void Editemp() {
        if(username1.getText().length()!=0 && empid1.getText().length()!=0 && rpassword1.getText().length()!=0 && password1.getText().length()!=0){
           if(password1.getText().equals(rpassword1.getText())) {
               String user = username1.getText();
               String id = empid1.getText();
               String pass = password1.getText();
               String query = "update users set password=? where id=? and username=?";
               if(!id.equals("0")){
                   try {
                       preparedStatement = connection.prepareStatement(query);
                       preparedStatement.setString(1, pass);
                       preparedStatement.setString(2, id);
                       preparedStatement.setString(3, user);
                       preparedStatement.executeUpdate();
                       //System.out.println(preparedStatement);
                       msg2.setTextFill(Color.rgb(2,201,54));
                       msg2.setText("Updated Succesfully");
                   } catch (SQLException throwables) {
                       throwables.printStackTrace();
                   }
               }
               else{
                   msg2.setTextFill(Color.rgb(220,29,61));
                   msg2.setText("No change to admin");}
               }
           else{
               msg2.setTextFill(Color.rgb(220,29,61));
               msg2.setText("The password and retyped password do not match");}
        }
           else{
                msg2.setTextFill(Color.rgb(220,29,61));
                msg2.setText("Fill in every detail");}
        }

    public void setStats(){
        emp.setVisible(false);
        stats.setVisible(true);
    }
}
