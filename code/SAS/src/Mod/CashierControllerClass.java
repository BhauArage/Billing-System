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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CashierControllerClass implements Initializable {

    @FXML
    private TextField txt_order_id;

    @FXML
    private DatePicker txt_orderdate;

    @FXML
    private TextField txt_prod_id;

    @FXML
    private TextField txt_prod_name;

    @FXML
    private TextField txt_unit_price;

    @FXML
    private TextField txt_qty;

    @FXML
    private TableView<orderlist> table_order;

    @FXML
    private TableColumn<orderlist, Integer> col_sno;

    @FXML
    private TableColumn<orderlist, String> col_prod_id;

    @FXML
    private TableColumn<orderlist, String> col_prod_name;

    @FXML
    private TableColumn<orderlist, Integer> col_unit_price;

    @FXML
    private TableColumn<orderlist, Integer> col_qty;

    @FXML
    private TableColumn<orderlist, Double> col_amt;
    @FXML
    private Label lbl_total;
    @FXML
    private Button btn_print;

    @FXML
    private Label lbl_bal;

    @FXML
    private TextField txt_amt_paid;

    @FXML
    private Button btn_paid;


    private double total = 0.0;


    private ObservableList<orderlist> orderdata;

    int sno;
    String prod_name;
    int unit_price;
    String prod_id;
    int qty = 0;
    double amt = 0.0;


    Connection conn=SQLConnection.Connector();
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    public void handleproduct(KeyEvent event) throws SQLException {


        if (event.getCode() == KeyCode.ENTER) {
            String id = txt_prod_id.getText();

            pst = conn.prepareStatement("select * from stock where prod_id = ? ");
            pst.setString(1, id);
            rs = pst.executeQuery();


            //pst.setString(1,txt_prod_id.getText());
            //rs=pst.executeQuery();
            if (rs.next() == true) {


                prod_id = rs.getString(1);
                prod_name = rs.getString(2);
                unit_price = rs.getInt(3);

                txt_prod_id.setText(prod_id);
                txt_prod_name.setText(prod_name);
                txt_unit_price.setText("" + unit_price);


                txt_qty.requestFocus();


            }
            rs.close();

        }
    }

    @FXML
    void handleorder(ActionEvent event) {
        int qty = Integer.parseInt(txt_qty.getText());
        if (qty != 0) {
            amt = qty * unit_price;
            total += amt;


            orderdata.add(new orderlist(++sno, prod_id, prod_name, unit_price, qty, amt));

            table_order.setItems(orderdata);
            lbl_total.setText("" + total);
            cleartext();

        }


    }

    @FXML
    void handlebal(ActionEvent event) {

        double amtpaid = Double.parseDouble(txt_amt_paid.getText());
        double total = Double.parseDouble(lbl_total.getText());
        if (amtpaid < total) {
            lbl_bal.setText("inappropiate amt");
        }

        double bal = amtpaid - total;
        lbl_bal.setText("" + bal);


    }

    @FXML
    void handleprint(ActionEvent event) {
        String sql = "insert into orderprod (order_id , order_date) values (? , ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_order_id.getText());
            pst.setDate(2, Date.valueOf(txt_orderdate.getValue()));
            int i = pst.executeUpdate();
            if (i == 1) {
                sql = "insert into bill (order_id,prod_id,qty,amt) values(?,?,?,?)";
                for (orderlist ol : orderdata) {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, txt_order_id.getText());
                    pst.setString(2, ol.getProd_id());
                    pst.setInt(3, ol.getQty());
                    pst.setDouble(4, ol.getAmt());


                    int j = pst.executeUpdate();
                    if (j == 1) {
                        System.out.println("completed");

                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void handlenewbill(ActionEvent event) {

        for (int i = 0; i < table_order.getItems().size(); i++) {
            table_order.getItems().clear();
        }
        txt_amt_paid.clear();
        lbl_bal.setText("" + 0.0);
        lbl_total.setText("" + 0.0);
        txt_order_id.setText(autoorderid());


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //connect();
        col_sno.setCellValueFactory(new PropertyValueFactory<>("sno"));
        col_prod_id.setCellValueFactory(new PropertyValueFactory<>("prod_id"));
        col_prod_name.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
        col_unit_price.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        col_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        col_amt.setCellValueFactory(new PropertyValueFactory<>("amt"));
        orderdata = FXCollections.observableArrayList();

        txt_orderdate.setValue(LocalDate.now());
        txt_order_id.setText(autoorderid());

    }

    private void cleartext() {
        txt_prod_id.clear();
        txt_qty.clear();
        txt_unit_price.clear();
        txt_prod_name.clear();
        txt_prod_id.requestFocus();
    }

    private String autoorderid() {
        String orderid = "BNO0000";
        try {
            pst = conn.prepareStatement("select max(order_id) from orderprod");
            rs = pst.executeQuery();
            if (rs.next()) {


                orderid = rs.getString(1);
                int n = Integer.parseInt(orderid.substring(3)) + 1;
                int x = String.valueOf(n).length();
                orderid = orderid.substring(0, 8 - x) + String.valueOf(n);


            }


            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderid;
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
