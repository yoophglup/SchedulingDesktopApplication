package Controllers;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
    public TableColumn Division;
    public Button AddNewButton;

    private ObservableList<ObservableList> data;


    public void initialize(){
        try {
            TableView tableView = customertable;
            tableView.setEditable(true);

            Customer_ID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("Customer_ID"));
            Customer_Name.setCellValueFactory(new PropertyValueFactory<Customer, String>("Customer_Name"));
            Customer_Name.setCellFactory(TextFieldTableCell.forTableColumn());
            Address.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
            Address.setCellFactory(TextFieldTableCell.forTableColumn());

            Postal_Code.setCellValueFactory(new PropertyValueFactory<Customer, String>("Postal_Code"));
            Postal_Code.setCellFactory(TextFieldTableCell.forTableColumn());

            Phone.setCellValueFactory(new PropertyValueFactory<Customer, String>("Phone"));
            Phone.setCellFactory(TextFieldTableCell.forTableColumn());

            Division.setCellValueFactory(new PropertyValueFactory<Customer, String>("Division"));
            Division.setCellFactory(TextFieldTableCell.forTableColumn());

            PreparedStatement ps = JDBC.getConnection().prepareStatement("select c.Customer_ID,c.Customer_Name,c.Address,c.Postal_Code,c.Phone,c.Division_ID,f.Division,f.Country_ID,t.Country from customers c join first_level_divisions f on c.Division_ID=f.Division_ID join countries t on f.Country_ID=t.Country_ID;\n");
            ObservableList<Customer> allcust = FXCollections.observableArrayList();
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
                String thisdivision=rs.getString("Division");
                Integer thisdivisionid=rs.getInt("Division_ID");
                System.out.println(thisid+" "+thiscust+" "+thisaddress+thisdivision+thiscountry+" "+thisPostal_Code+" "+thisphone+" "+thisdivision);
                Customer thiscustomer=new Customer(thisid,thiscust,thisaddress,thisPostal_Code,thisphone,thisdivision);
                allcust.add(thiscustomer);
                //tableView.getItems().add(new Customer(thisid,thiscust,thisaddress,thisPostal_Code,thisphone,thisdivision));
            }tableView.setItems(allcust);

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

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        ObservableList<Customer> thislist = customertable.getSelectionModel().getSelectedItems();
        for (Customer ct : thislist) {
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Appointment_ID from appointments join customers on appointments.Customer_ID="+ct.getCustomer_ID());
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean existingapointments=false;
            while (resultSet.next()){
                Integer thisapointmentid=resultSet.getInt("Appointment_ID");
                existingapointments=true;}
            if (existingapointments ==true) {
                System.out.println("Unable to Delete due to existing appointments");
            }else {
                System.out.println("Deleting...");
                try {
                    PreparedStatement del = JDBC.getConnection().prepareStatement("delete from customers where Customer_ID="+ct.getCustomer_ID());
                    del.executeUpdate();
                    System.out.println("Deleted");
                }catch(Exception e){
                    System.out.println(e);
                    System.out.println("Unable to Delete");

                }
                }

            }
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 750, 400);
            stage.setTitle("Edit Customer Records");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){

        }
        }



    public void saveAlteredData(ActionEvent actionEvent) {


    }

    public void editApointments(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/AppointmentEditor.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 400);
        stage.setTitle("Edit Appointments");
        stage.setScene(scene);
        stage.show();


    }
}
