package Controllers;

import Model.Appointment;
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
import javafx.scene.input.MouseEvent;
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
    public TableView appointmentsTable;
    public TableColumn Appointment_ID1;
    public TableColumn Title1;
    public TableColumn Description1;
    public TableColumn Location1;
    public TableColumn Type1;
    public TableColumn Start1;
    public TableColumn End1;
    public TableColumn Create_Date1;
    public TableColumn Create_By1;
    public TableColumn Last_Update1;
    public TableColumn Last_Updated_By1;
    public TableColumn Customer_ID1;
    public TableColumn User_ID1;
    public TableColumn ContactID1;

    public void initialize() {
        try {
            TableView tableView = customertable;
            customertable.setEditable(true);

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

            Integer ct = 0;
            while (rs.next()) {
                ct = ct + 1;
                System.out.println("CT = " + ct);
                Integer thisid = rs.getInt("Customer_ID");
                String thiscust = rs.getString("Customer_Name");
                String thisaddress = rs.getString("Address");
                String thiscountry = rs.getString("Country");
                String thisPostal_Code = rs.getString("Postal_Code");
                String thisphone = rs.getString("Phone");
                String thisdivision = rs.getString("Division");
                Integer thisdivisionid = rs.getInt("Division_ID");
                System.out.println(thisid + " " + thiscust + " " + thisaddress + thisdivision + thiscountry + " " + thisPostal_Code + " " + thisphone + " " + thisdivision);
                Customer thiscustomer = new Customer(thisid, thiscust, thisaddress, thisPostal_Code, thisphone, thisdivision);
                allcust.add(thiscustomer);
                //tableView.getItems().add(new Customer(thisid,thiscust,thisaddress,thisPostal_Code,thisphone,thisdivision));
            }
            tableView.setItems(allcust);

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
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Appointment_ID from appointments join customers on appointments.Customer_ID=" + ct.getCustomer_ID());
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean existingapointments = false;
            while (resultSet.next()) {
                Integer thisapointmentid = resultSet.getInt("Appointment_ID");
                existingapointments = true;
            }
            if (existingapointments == true) {
                System.out.println("Unable to Delete due to existing appointments");
            } else {
                System.out.println("Deleting...");
                try {
                    PreparedStatement del = JDBC.getConnection().prepareStatement("delete from customers where Customer_ID=" + ct.getCustomer_ID());
                    del.executeUpdate();
                    System.out.println("Deleted");
                } catch (Exception e) {
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
        } catch (Exception e) {

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

    public void saveAlteredAppointmentData(ActionEvent actionEvent) {
    }

    public void loadAppointments(MouseEvent mouseEvent) throws SQLException {

        Appointment_ID1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Appointment_ID"));
        Title1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        Description1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
        Location1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Location"));
        Type1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Type"));
        Start1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Start"));
        End1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("End"));
        Create_Date1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Create_Date"));
        Create_By1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Created_By"));
        Last_Update1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Last_Update"));
        Last_Updated_By1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Last_Updated_By"));;
        Customer_ID1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Customer_ID"));
        User_ID1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("User_ID"));
        ContactID1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Contact_ID"));

        ObservableList<Customer> clicklist = customertable.getSelectionModel().getSelectedItems();
        ObservableList<Appointment> Appointmentlist = FXCollections.observableArrayList();

        Integer ClickedCustomer_ID = 0;
        for (Customer ct : clicklist) {
            ClickedCustomer_ID = ct.getCustomer_ID();
        }
        System.out.println("Loading Appointments from Clicked user" + ClickedCustomer_ID);
        PreparedStatement sqlappointment = JDBC.getConnection().prepareStatement("select * from appointments where Customer_ID=" + ClickedCustomer_ID);
        ResultSet apresults = sqlappointment.executeQuery();

        while (apresults.next()) {
            Integer thisappointmentID = apresults.getInt("Appointment_ID");
            String thisTitle=apresults.getString("Title");
            String thisDescription=apresults.getString("Description");

            String thisLocation=apresults.getString("Location");
            String thisType=apresults.getString("Type");
            String thisStart=apresults.getString("Start");
            String thisEnd=apresults.getString("End");

            String thisCreate_date=apresults.getString("Create_Date");

            String thisCreate_by=apresults.getString("Created_By");
            String thisLastUpdate=apresults.getString("Last_Update");
            String thisLastUpdatedby=apresults.getString("Last_Updated_By");
            Integer thisCustomer_ID=apresults.getInt("Customer_ID");
            Integer thisUser_ID=apresults.getInt("User_ID");
            Integer thisContact_ID=apresults.getInt("Contact_ID");
            Appointment thisappointment= new Appointment(thisappointmentID,thisTitle,thisDescription,thisLocation,thisType,thisStart,thisEnd,thisCreate_date,thisCreate_by,thisLastUpdate,thisLastUpdatedby,thisCustomer_ID,thisUser_ID,thisContact_ID);
            Appointmentlist.add(thisappointment);
            System.out.println(thisCreate_date);
        }appointmentsTable.setItems(Appointmentlist);

    }
}
