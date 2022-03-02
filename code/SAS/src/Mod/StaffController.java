package Mod;

// javafx libraries
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

// java libraries
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

// controller initialization interface
public class StaffController implements Initializable {

    // declaring javafx components as defined in .fxml
    public TextField id_text;
    public TextField name_text;
    public TextField Quantity_text;
    public TableView main_table;
    public TableColumn id_column;
    public TableColumn name_column;
    public TableColumn Quantity_column;
    public Button create_btn;
    public Button update_btn;
    public Button delete_btn;
    public TextField get_text;
    public Button get_button;
    public Button revert_button;
    private Properties user;
    private Properties password;

    public ObservableList<Products> getProducts(){

        // Oracle documentation to process SQL statements with JDBC: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
        ObservableList<Products> vehicles = FXCollections.observableArrayList();
        Connection connect = SQLConnection.Connector();
        String sql_query = "SELECT * FROM stock";

        try(Statement statement = connect.createStatement()){
            ResultSet result_set = statement.executeQuery(sql_query);
            // iterating through resultant Vehicle objects from remote DB
            while(result_set.next()){
                Products pl = new Products(result_set.getString("prod_id"), result_set.getString("prod_name"),result_set.getInt("stock_qty"),result_set.getInt("unit_amt"));
                vehicles.add(pl);
            }
        }
        catch(Exception e){
            System.out.println("Error:" + e.getMessage());
        }
        return vehicles;
    }

    // implementing update from remote DB to Desktop GUI application onyl for Get Button
    public ObservableList<Products> getVehiclesForGetButton(){

        // Oracle documentation to process SQL statements with JDBC: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
        ObservableList<Products> pl = FXCollections.observableArrayList();
        Connection connect = SQLConnection.Connector();
        String sql_query = "SELECT * FROM stock WHERE prod_id = " + get_text.getText() + "";


        try(Statement statement = connect.createStatement()){
            ResultSet result_set = statement.executeQuery(sql_query);
            // iterating through resultant Vehicle objects from remote DB
            while(result_set.next()){
                Products vehicles_queried = new Products(result_set.getString("prod_id"), result_set.getString("prod_name"), result_set.getInt("stock_qty"),result_set.getInt("unit_amt"));
                pl.add(vehicles_queried);
            }
        }
        catch(Exception e){
            System.out.println("Error:" + e.getMessage());
        }
        return pl;
    }

    // updating data from MySQL DataBase into Desktop GUI application


    // updating data from MySQL DataBase into Desktop GUI application
    public void pushdataOntoTable(){

        // retrieving data from remote DB
        ObservableList<Products> pl = getProducts();

        // updating DB into GUI application
        id_column.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        Quantity_column.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        main_table.setItems(pl);
    }




    public void update() throws SQLException {

        if(id_text.getText().equals("") || name_text.getText().equals("") || Quantity_text.getText().equals("")) {
            // testing for invalid user input by means of Dialog
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all text fields!", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();

        }
        else {
            // updating productlist object based on id
            String sql_query = "UPDATE stock SET prod_name = '" + name_text.getText() +"',stock_qty = '" + Quantity_text.getText() + " 'WHERE prod_id = '"+ id_text.getText() + "'";
            establishSQLConnection(sql_query);
            pushdataOntoTable();
        }
    }

    // using SQL statement to make relevant query to update table accordingly
    // param: sql_query:String
    private void establishSQLConnection(String sql_query) throws SQLException {

        Connection connect_object = SQLConnection.Connector();

        try(Statement statement = connect_object.createStatement()){
            statement.executeUpdate(sql_query);
        }
        catch (Exception e){

        }
    }

    // event handler for button press
    // param: actionEvent: ActionEvent
    public void buttonPressed(javafx.event.ActionEvent actionEvent) throws SQLException
    {

        // calling relevant methods based on event source
        if(actionEvent.getSource() == update_btn){
            update();
        }
    }

    // event handler for mouse click on table cell
    // param: mouseEvent: MouseEvent
    public void mouseClicked(MouseEvent mouseEvent) {

        Products pl = (Products) main_table.getSelectionModel().getSelectedItem();

        // extracting data from selected row to be displayed into text fields
        id_text.setText((pl.getProductID()));
        name_text.setText(pl.getProductName());
        Quantity_text.setText(""+Integer.valueOf(pl.getQuantity()));
    }

    // delegate function for Initializable class
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        pushdataOntoTable();
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