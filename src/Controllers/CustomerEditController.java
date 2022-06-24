package Controllers;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class CustomerEditController {
    public static String uservalue;
    public TableView customertable;
    public TableColumn Customer_Name;
    public TableColumn Customer_ID;
    public TableColumn Address;
    public TableColumn Postal_Code;
    public TableColumn Phone;
    public TableColumn DivisionCombobox;
    //public TableColumn Division;
    public TableColumn Create_Date;
    public TableColumn Create_By;
    public TableColumn Last_Update;
    public TableColumn Last_Updated_By;


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
    public ObservableList<Customer> AllCustomers;
    public ObservableList<String> DefaultDivisions = FXCollections.observableArrayList();

    public ObservableList<String> ModSqlCommandsSaved = FXCollections.observableArrayList();


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
            DivisionCombobox.setCellValueFactory(new PropertyValueFactory<Customer, String>("DivisionCombobox"));


            //Division.setCellValueFactory(new PropertyValueFactory<Customer, String>("Division"));
            //Division.setCellFactory(TextFieldTableCell.forTableColumn());
            Create_Date.setCellValueFactory(new PropertyValueFactory<Customer, String>("Create_Date"));
            ;
            Create_By.setCellValueFactory(new PropertyValueFactory<Customer, String>("Created_By"));
            ;
            Last_Update.setCellValueFactory(new PropertyValueFactory<Customer, String>("Last_Update"));
            ;
            Last_Updated_By.setCellValueFactory(new PropertyValueFactory<Customer, String>("Last_Updated_By"));
            ;


            PreparedStatement ps = JDBC.getConnection().prepareStatement("select c.Customer_ID,c.Customer_Name,c.Address,c.Postal_Code,c.Phone,c.Division_ID,c.Create_Date,c.Created_By,c.Last_Update,c.Last_Updated_By,f.Division,f.Country_ID,t.Country from customers c join first_level_divisions f on c.Division_ID=f.Division_ID join countries t on f.Country_ID=t.Country_ID;\n");
            ObservableList<Customer> allcust = FXCollections.observableArrayList();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer thisid = rs.getInt("Customer_ID");
                String thiscust = rs.getString("Customer_Name");
                String thisaddress = rs.getString("Address");
                String thiscountry = rs.getString("Country");
                String thisPostal_Code = rs.getString("Postal_Code");
                String thisphone = rs.getString("Phone");
                String thisdivision = rs.getString("Division");
                Integer thisdivisionid = rs.getInt("Division_ID");
                String thisCreate_Date = rs.getString("Create_Date");
                String thisCreate_By = rs.getString("Created_By");
                String thisLast_Update = rs.getString("Last_Update");
                String thisLast_Updated_By = rs.getString("Last_Updated_By");
                Customer thiscustomer = new Customer(thisid, thiscust, thisaddress, thisPostal_Code, thisphone, thisdivision, thisdivisionid, thisCreate_Date, thisCreate_By, thisLast_Update, thisLast_Updated_By);
                allcust.add(thiscustomer);
            }
            AllCustomers = allcust;
            for (Customer Selectedcustomer : AllCustomers) {
                //System.out.println(Selectedcustomer.DivisionCombobox.getValue().toString());
                DefaultDivisions.add(Selectedcustomer.DivisionCombobox.getValue().toString());
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
                Alert ExistingAppointments = new Alert(Alert.AlertType.ERROR);
                ExistingAppointments.setTitle("Unable to Delete Customer");
                ExistingAppointments.setHeaderText("The customer has appoinments, you must first delete the appointments \nbefore you can delete the customer.");
                ExistingAppointments.setContentText("Remove associated appointments first.");
                ExistingAppointments.showAndWait();
            } else {
                System.out.println("Deleting...");
                Alert areyousure = new Alert(Alert.AlertType.CONFIRMATION);
                areyousure.setTitle("CONFIRMATION");
                areyousure.setHeaderText("You are about to delete the selected customer.");
                areyousure.setContentText("This cannot be undone. Are you sure?");
                areyousure.showAndWait();
                if (areyousure.getResult().getButtonData().toString() == "OK_DONE") {

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

        }
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 750, 450);
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
        Scene scene = new Scene(root, 750, 450);
        stage.setTitle("Edit Appointments");
        stage.setScene(scene);
        stage.show();


    }

    public void saveAlteredAppointmentData(ActionEvent actionEvent) {
    }

    public void loadAppointments(MouseEvent mouseEvent) throws SQLException {
        appointmentsTable.setEditable(true);

        Appointment_ID1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Appointment_ID"));

        Title1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        Title1.setCellFactory(TextFieldTableCell.forTableColumn());

        Description1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
        Description1.setCellFactory(TextFieldTableCell.forTableColumn());

        Location1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Location"));
        Location1.setCellFactory(TextFieldTableCell.forTableColumn());

        Type1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Type"));
        Type1.setCellFactory(TextFieldTableCell.forTableColumn());

        Start1.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDate>("Start"));
        //Start1.setCellFactory(TextFieldTableCell.forTableColumn());

        //Start1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Start"));
        //Start1.setCellFactory(TextFieldTableCell.forTableColumn());

        End1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("End"));
        End1.setCellFactory(TextFieldTableCell.forTableColumn());

        Create_Date1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Create_Date"));
        Create_By1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Created_By"));
        Last_Update1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Last_Update"));
        Last_Updated_By1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Last_Updated_By"));
        ;

        Customer_ID1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Customer_ID"));
        User_ID1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("User_ID"));
        ContactID1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Contact_ID"));

        ObservableList<Appointment> Appointmentlist = FXCollections.observableArrayList();
        ObservableList<Customer> clicklist = customertable.getSelectionModel().getSelectedItems();

        Integer ClickedCustomer_ID = 0;
        for (Customer ct : clicklist) {
            ClickedCustomer_ID = ct.getCustomer_ID();
        }
        System.out.println("Loading Appointments from Clicked user" + ClickedCustomer_ID);
        PreparedStatement sqlappointment = JDBC.getConnection().prepareStatement("select * from appointments where Customer_ID=" + ClickedCustomer_ID);
        ResultSet apresults = sqlappointment.executeQuery();

        while (apresults.next()) {
            Integer thisappointmentID = apresults.getInt("Appointment_ID");
            String thisTitle = apresults.getString("Title");
            String thisDescription = apresults.getString("Description");

            String thisLocation = apresults.getString("Location");
            String thisType = apresults.getString("Type");
            String thisStart = apresults.getString("Start");
            String thisEnd = apresults.getString("End");

            String thisCreate_date = apresults.getString("Create_Date");

            String thisCreate_by = apresults.getString("Created_By");
            String thisLastUpdate = apresults.getString("Last_Update");
            String thisLastUpdatedby = apresults.getString("Last_Updated_By");
            Integer thisCustomer_ID = apresults.getInt("Customer_ID");
            Integer thisUser_ID = apresults.getInt("User_ID");
            Integer thisContact_ID = apresults.getInt("Contact_ID");
            Appointment thisappointment = new Appointment(thisappointmentID, thisTitle, thisDescription, thisLocation, thisType, thisStart, thisEnd, thisCreate_date, thisCreate_by, thisLastUpdate, thisLastUpdatedby, thisCustomer_ID, thisUser_ID, thisContact_ID);
            Appointmentlist.add(thisappointment);
            System.out.println(thisCreate_date);
        }
        appointmentsTable.setItems(Appointmentlist);

    }

    public void DeleteAppointment(ActionEvent actionEvent) {
        ObservableList<Appointment> selectlist = appointmentsTable.getSelectionModel().getSelectedItems();
        for (Appointment SelectedAppointment : selectlist) {
            System.out.println(SelectedAppointment.getAppointment_ID());
            System.out.println("Deleting...");
            Alert areyousure = new Alert(Alert.AlertType.CONFIRMATION);
            areyousure.setTitle("CONFIRMATION");
            areyousure.setHeaderText("You are about to delete the selected appointment.");
            areyousure.setContentText("This cannot be undone. Are you sure?");
            areyousure.showAndWait();
            if (areyousure.getResult().getButtonData().toString() == "OK_DONE") {

                try {
                    PreparedStatement deleteAppointment = JDBC.getConnection().prepareStatement("delete from appointments where Appointment_ID=" + SelectedAppointment.getAppointment_ID());
                    deleteAppointment.executeUpdate();
                    System.out.println("Deleted");
                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 750, 450);
                        stage.setTitle("Edit Customer Records");
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Unable to Delete");

                }
            }

        }


    }

    public void ValueChanged(TableColumn.CellEditEvent cellEditEvent) {
        //System.out.println();
        //System.out.println(cellEditEvent.getTableColumn().getText());
        //System.out.println(cellEditEvent.getOldValue());
        //System.out.println(cellEditEvent.getNewValue());
        Object oldvalue = cellEditEvent.getOldValue();
        Object newvalue = cellEditEvent.getNewValue();
        String tablename = cellEditEvent.getTableColumn().getText();

        ObservableList<Customer> clicklist = customertable.getSelectionModel().getSelectedItems();
        Integer ClickedCustomer_ID = 0;
        for (Customer ct : clicklist) {
            ClickedCustomer_ID = ct.getCustomer_ID();
        }
        String updatedbysql = "update customers set Last_Updated_By ='" + uservalue + "' where Customer_ID=" + ClickedCustomer_ID + ";";
        String last_updatesql = "update customers set Last_Update = now() where Customer_ID=" + ClickedCustomer_ID + ";";
        String sqlstring = "update customers set " + tablename + " = '" + newvalue + "' where Customer_ID=" + ClickedCustomer_ID + ";";
        ModSqlCommandsSaved.add(updatedbysql);
        ModSqlCommandsSaved.add(last_updatesql);
        ModSqlCommandsSaved.add(sqlstring);
    }


    public void SaveCustomerData(MouseEvent mouseEvent) throws SQLException, IOException {
        ObservableList<String> hasDivisionschanged = FXCollections.observableArrayList();
        ObservableList<Customer> selectlist = AllCustomers;
        int x = 0;
        for (Customer Selectedcustomer : selectlist) {
            hasDivisionschanged.add(Selectedcustomer.DivisionCombobox.getValue().toString());
            if (!DefaultDivisions.get(x).equals(Selectedcustomer.DivisionCombobox.getValue().toString())) {
                String sqlstring = "update customers set Division_ID=(select Division_Id from first_level_divisions where Division='" + Selectedcustomer.DivisionCombobox.getValue().toString() + "') where Customer_ID=" + Selectedcustomer.getCustomer_ID() + ";";
                String updatedbysql = "update customers set Last_Updated_By ='" + uservalue + "' where Customer_ID=" + Selectedcustomer.getCustomer_ID() + ";";
                String last_updatesql = "update customers set Last_Update = now() where Customer_ID=" + Selectedcustomer.getCustomer_ID() + ";";
                ModSqlCommandsSaved.add(sqlstring);
                ModSqlCommandsSaved.add(updatedbysql);
                ModSqlCommandsSaved.add(last_updatesql);

            }

            x++;
        }
        for (String st : ModSqlCommandsSaved) {
            System.out.println(st);
            PreparedStatement thisSQLstatement = JDBC.getConnection().prepareStatement(st);
            thisSQLstatement.executeUpdate();
            System.out.println("Records Updated!");


        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.show();

    }

    public void apointmenteditStart(TableColumn.CellEditEvent event) throws IOException {
        ObservableList<Appointment> clicklist = appointmentsTable.getSelectionModel().getSelectedItems();
        Integer ClickedAppointment_ID = 0;
        for (Appointment SingleAppointment : clicklist) {
            ClickedAppointment_ID = SingleAppointment.getAppointment_ID();
            SingleAppointment.setStart(" ");
        }
        Appointmentpicker.ClickedAppointment_ID=ClickedAppointment_ID;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/appointmentpicker.fxml")));
        Scene scene = new Scene(root, 380, 200);
        Stage stage2= new Stage();
        stage2.setTitle("Pick a New Date");
        stage2.setScene(scene);
        stage2.setAlwaysOnTop(true);
        stage2.showAndWait();
        event.a
    }
}

