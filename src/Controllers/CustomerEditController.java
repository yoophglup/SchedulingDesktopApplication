package Controllers;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerEditController {
    public TableView customertable;
    public TableColumn Customer_Name;
    public TableColumn Customer_ID;
    public TableColumn Address;
    public TableColumn Postal_Code;
    public TableColumn Phone;

    private ObservableList<ObservableList> data;


    public void initialize(){
        try {
            TableView tableView = customertable;
            Customer_ID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("Customer_ID"));
            Customer_Name.setCellValueFactory(new PropertyValueFactory<Customer, String>("Customer_Name"));
            Address.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
            Postal_Code.setCellValueFactory(new PropertyValueFactory<Customer, String>("Postal_Code"));
            Phone.setCellValueFactory(new PropertyValueFactory<Customer, String>("Phone"));
            PreparedStatement ps = JDBC.getConnection().prepareStatement("select c.Customer_ID,c.Customer_Name,c.Address,c.Postal_Code,c.Phone,c.Division_ID,f.Division,f.Country_ID,t.Country from customers c join first_level_divisions f on c.Division_ID=f.Division_ID join countries t on f.Country_ID=t.Country_ID;\n");
            ObservableList<String> allcust = FXCollections.observableArrayList();
            ResultSet rs = ps.executeQuery();

            Integer ct=0;
            while (rs.next()){
                ct=ct+1;
                System.out.println("CT = "+ct);
                Integer thisid=rs.getInt("Customer_ID");
                String thiscust=rs.getString("Customer_Name");
                String thisaddress=rs.getString("Address");
                String thiscountry=rs.getString("Country");
                String thisPostal_Code=rs.getString("Postal_Code");
                String thisphone=rs.getString("Phone");
                String thisdivisionlabel=rs.getString("Division");
                Integer thisdivision=rs.getInt("Division_ID");
                System.out.println(thisid+" "+thiscust+" "+thisaddress+thisdivisionlabel+thiscountry+" "+thisPostal_Code+" "+thisphone+" "+thisdivision);
                tableView.getItems().add(new Customer(thisid,thiscust,thisaddress+", "+thisdivisionlabel,thisPostal_Code,thisphone));
            }

        } catch (SQLException e) {
            System.out.println("Error on Building Data");
            System.out.println(e);
        }


        }

    public void switchSceneAddNewCustomer(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/AddNewCustomer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 640, 400);
        stage.setTitle("Add a New Customer");
        stage.setScene(scene);
        stage.show();


    }

    public void LoadMainMenu(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/MainMenu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 340, 200);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();

    }
}
