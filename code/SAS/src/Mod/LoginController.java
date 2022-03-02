package Mod;

//import javafx.beans.Observable;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel= new LoginModel();
    String auth;
    @FXML
    private Label isConnected;
    @FXML
    public ComboBox<String> role;
    ObservableList<String>list= FXCollections.observableArrayList("Cashier","Staff","Manager","admin");
    @FXML
    private Label msg;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(loginModel.isDbConnected()){
            isConnected.setText("Welcome To SAS");
        }
        else
            isConnected.setText("Not Connected");
        role.setItems(list);
    }

    public void Login(ActionEvent event){
        try {
            if(loginModel.isLogin(txtUsername.getText(),txtPassword.getText(),auth)){
                //isConnected.setText("Username and password is correct");
                ((Node)event.getSource()).getScene().getWindow().hide();
                Stage primaryStage=new Stage();
                FXMLLoader loader=new FXMLLoader();
                if(auth=="M"){
                    Manager manager =new Manager();
                    loader.setController(manager);
                    Pane root = loader.load(getClass().getResource("ManagerPage.fxml"));
                    primaryStage.setTitle("Manager");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
                if(auth=="C"){
                    CashierControllerClass cashierController =new CashierControllerClass();
                    loader.setController(cashierController);
                    Pane root = loader.load(getClass().getResource("Cashier.fxml"));
                    primaryStage.setTitle("Billing");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
                if(auth=="admin"){
                    AdminPage AP = new AdminPage();
                    loader.setController(AP);
                    Pane root = loader.load(getClass().getResource("adminPage.fxml"));
                    primaryStage.setTitle("Admin");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
                if(auth=="S"){
                    StaffController S=new StaffController();
                    loader.setController(S);
                    Pane root = loader.load(getClass().getResource("Staff.fxml"));
                    primaryStage.setTitle("Staff");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
                //else
                  //  msg.setText("Username and password is wrong");
            }
            else
                msg.setText("Username and password is wrong");
        } catch (SQLException throwables) {
            msg.setText("Username and password is wrong");
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Exit(ActionEvent event){
        System.exit(0);
    }

    public void comboChanged(ActionEvent event){
        String r=role.getValue();
        //System.out.println(r);
        if(r=="Manager")
            auth="M";
        if(r=="Cashier")
            auth="C";
        if(r=="Staff")
            auth="S";
        if(r=="admin")
            auth="admin";
        //System.out.println(auth);
    }
}
