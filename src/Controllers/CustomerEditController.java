package Controllers;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerEditController {
    public TableView customertable;
    public TableColumn Customer_Name;
    public TableColumn Customer_ID;
    public TableColumn Address;

    private ObservableList<ObservableList> data;


    public void initialize(){
        try {
            TableView tableView = customertable;
            Customer_ID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("Customer_ID"));
            Customer_Name.setCellValueFactory(new PropertyValueFactory<Customer, String>("Customer_Name"));
            Address.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));

            PreparedStatement ps = JDBC.getConnection().prepareStatement("select * from customers");
            ObservableList<String> allcust = FXCollections.observableArrayList();
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Integer thisid=rs.getInt("Customer_ID");
                String thiscust=rs.getString("Customer_Name");
                String thisaddress=rs.getString("Address");

                System.out.println(thisid+" "+thiscust+" "+thisaddress);
                tableView.getItems().add(new Customer(thisid, thiscust,thisaddress));
            }

        } catch (Exception e) {
            System.out.println("Error on Building Data");
        }


        }

}
