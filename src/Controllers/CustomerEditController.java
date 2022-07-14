package Controllers;

import Model.Appointment;
import Model.Customer;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.StringProperty;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.robot.Robot;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
/** CustomerEditController is designed to allow the User to edit customers and appointments.
 */
public class CustomerEditController {
    public static String uservalue;
    public static String newsqldate;
    public static String newdatefrompick;
    public static String selectedcustomerID;
    public static boolean isfromschedular;
    public static Integer schedularselectedcustomerID;
    public String ChangedData;
    public TableView customertable;
    public TableColumn Customer_Name;
    public TableColumn Customer_ID;
    public TableColumn Address;
    public TableColumn Postal_Code;
    public TableColumn Phone;
    public TableColumn DivisionCombobox;
    public TableColumn Create_Date;
    public TableColumn Create_By;
    public TableColumn Last_Update;
    public TableColumn Last_Updated_By;
    public ObservableList<String> ChangedatalistId=FXCollections.observableArrayList();
    public ObservableList<String> ChangedatalistRow=FXCollections.observableArrayList();
    public ObservableList<String> ChangedatelistColumn=FXCollections.observableArrayList();
    public ObservableList<String> Changedatalistvalue=FXCollections.observableArrayList();
    public Boolean DoNotLeave = false;
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
    public TableColumn Customer_IDComboBox;
    public TableColumn User_IDComboBox;
    public TableColumn ContactIDComboBox;
    public ObservableList<Customer> AllCustomers;
    public ObservableList<Appointment> AllAppointments;
    public ObservableList<String> DefaultDivisions = FXCollections.observableArrayList();
    public ObservableList<String> DefaultAppointmentsCustomer_ID = FXCollections.observableArrayList();
    public ObservableList<String> DefaultAppointmentsUser_ID = FXCollections.observableArrayList();
    public ObservableList<String> DefaultAppointmentsContact_ID = FXCollections.observableArrayList();
    public ObservableList<String> ModSqlCommandsSaved = FXCollections.observableArrayList();
    public ObservableList<String> AppointmentModSqlCommandsSaved = FXCollections.observableArrayList();
    public Text titletext;
    public TableColumn CountryCombobox;

    /** The initialize method loads the CustomerTable with data, if the value is set from Scheduler then this method
     * will also initialize the loadappointment method, which loads appointments.
     * @throws SQLException
     */
    public void initialize() throws SQLException {
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
            CountryCombobox.setCellValueFactory(new PropertyValueFactory<Customer, String>("CountryCombobox"));
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
                LocalDate localcreateDate = LocalDate.parse(thisCreate_Date.substring(0,10));
                LocalTime localCreateTime = LocalTime.parse(thisCreate_Date.substring(11,19));
                ZoneId UTCZone= ZoneId.of("UTC");
                ZoneId localZoneId = ZoneId.systemDefault();
                ZonedDateTime ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,UTCZone);
                ZonedDateTime ddc = ThisCreate_date.withZoneSameInstant(localZoneId);
                thisCreate_Date=ddc.toString().replaceFirst("T"," ").substring(0,19);


                String thisCreate_By = rs.getString("Created_By");

                String thisLast_Update = rs.getString("Last_Update");
                localcreateDate = LocalDate.parse(thisLast_Update.substring(0,10));
                localCreateTime = LocalTime.parse(thisLast_Update.substring(11,19));
                UTCZone= ZoneId.of("UTC");
                localZoneId = ZoneId.systemDefault();
                ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,UTCZone);
                ddc = ThisCreate_date.withZoneSameInstant(localZoneId);
                thisLast_Update=ddc.toString().replaceFirst("T"," ").substring(0,19);
                String thisLast_Updated_By = rs.getString("Last_Updated_By");
                Customer thiscustomer = new Customer(thisid, thiscust, thisaddress, thisPostal_Code, thisphone, thiscountry,thisdivision, thisdivisionid, thisCreate_Date, thisCreate_By, thisLast_Update, thisLast_Updated_By);
                allcust.add(thiscustomer);
            }
            AllCustomers = allcust;
            for (Customer Selectedcustomer : AllCustomers) {
                DefaultDivisions.add(Selectedcustomer.DivisionCombobox.getValue().toString());
            }

            tableView.setItems(allcust);

        } catch (SQLException e) {
        }
        loadAppointments(null);
    }

    /**This method switches the Scene to the AddNewCustomer Scene
     * This method is triggered by a button the user presses.
     * @param actionEvent
     * @throws Exception
     */
    public void switchSceneAddNewCustomer(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/AddNewCustomer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 640, 400);
        stage.setTitle("Add a New Customer");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();


    }

    /** This method Changeds the scene to the main menu
     * @param actionEvent
     * @throws Exception
     */
    public void LoadMainMenu(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/MainMenu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 350);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    /** This method deletes Customers if they do not have any appointments.
     *  stored in the database.
     * @param actionEvent
     * @throws SQLException
     */
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
                Alert ExistingAppointments = new Alert(Alert.AlertType.ERROR);
                ExistingAppointments.setTitle("Unable to Delete Customer");
                ExistingAppointments.setHeaderText("The customer has appoinments, you must first delete the appointments \nbefore you can delete the customer.");
                ExistingAppointments.setContentText("Remove associated appointments first.");
                ExistingAppointments.showAndWait();
            } else {
                Alert areyousure = new Alert(Alert.AlertType.CONFIRMATION);
                areyousure.setTitle("CONFIRMATION");
                areyousure.setHeaderText("You are about to delete the selected customer.");
                areyousure.setContentText("This cannot be undone. Are you sure?");
                areyousure.showAndWait();
                if (areyousure.getResult().getButtonData().toString() == "OK_DONE") {

                    try {
                        PreparedStatement del = JDBC.getConnection().prepareStatement("delete from customers where Customer_ID=" + ct.getCustomer_ID());
                        del.executeUpdate();
                        Alert deletedone = new Alert(Alert.AlertType.INFORMATION);
                        deletedone.setTitle("INFORMATION");
                        deletedone.setHeaderText("The selected customer "+ct.getCustomer_ID()+" has been deleted..");
                        deletedone.setContentText("Delete Complete!");
                        deletedone.showAndWait();
                    } catch (Exception e) {


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

    /**This method will Save all customer data which has been edited by the user.  Certain cells can
     * be edited by the user but are not saved until this method runs.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void SaveCustomerData(ActionEvent actionEvent) throws SQLException, IOException {
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
            PreparedStatement thisSQLstatement = JDBC.getConnection().prepareStatement(st);
            thisSQLstatement.executeUpdate();


        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.show();

    }

    /**This method will Save all appointment data which has been edited by the user.  Certain cells can
     * be edited by the user but are not saved until this method runs.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void saveAlteredAppointmentData(ActionEvent actionEvent) throws SQLException, IOException {
        ObservableList<String> hasCustomer_IDchanged = FXCollections.observableArrayList();
        ObservableList<String> hasUser_IDchanged = FXCollections.observableArrayList();
        ObservableList<String> hasContact_IDchanged = FXCollections.observableArrayList();

        ObservableList<Appointment> selectlist = AllAppointments;

        int x = 0;
        for (Appointment SelectedAppointment : selectlist) {
            hasUser_IDchanged.add(SelectedAppointment.Customer_IDComboBox.getValue().toString());
            hasCustomer_IDchanged.add(SelectedAppointment.Customer_IDComboBox.getValue().toString());
            hasContact_IDchanged.add(SelectedAppointment.ContactIDComboBox.getValue().toString());

            if (!DefaultAppointmentsCustomer_ID.get(x).equals(SelectedAppointment.Customer_IDComboBox.getValue().toString())) {
                String sqlstring = "update appointments set Customer_ID=(Select Customer_ID from customers where Customer_Name='" + SelectedAppointment.Customer_IDComboBox.getValue().toString() + "') where Appointment_ID=" + SelectedAppointment.getAppointment_ID() + ";";
                String updatedbysql = "update appointments set Last_Updated_By ='" + uservalue + "' where Appointment_ID=" + SelectedAppointment.getAppointment_ID() + ";";
                String last_updatesql = "update appointments set Last_Update = now() where Appointment_ID=" + SelectedAppointment.getAppointment_ID() + ";";
                AppointmentModSqlCommandsSaved.add(sqlstring);
                AppointmentModSqlCommandsSaved.add(updatedbysql);
                AppointmentModSqlCommandsSaved.add(last_updatesql);
            }
            if (!DefaultAppointmentsUser_ID.get(x).equals(SelectedAppointment.User_IDComboBox.getValue().toString())) {
                String sqlstring2 = "update appointments set User_ID=" + SelectedAppointment.User_IDComboBox.getValue().toString() + " where Appointment_ID=" + SelectedAppointment.getAppointment_ID() + ";";
                String updatedbysql2 = "update appointments set Last_Updated_By ='" + uservalue + "' where Appointment_ID=" + SelectedAppointment.getAppointment_ID() + ";";
                String last_updatesql2 = "update appointments set Last_Update = now() where Appointment_ID=" + SelectedAppointment.getAppointment_ID() + ";";
                AppointmentModSqlCommandsSaved.add(sqlstring2);
                AppointmentModSqlCommandsSaved.add(updatedbysql2);
                AppointmentModSqlCommandsSaved.add(last_updatesql2);
            }
            if (!DefaultAppointmentsContact_ID.get(x).equals(SelectedAppointment.ContactIDComboBox.getValue().toString())) {
                String sqlstring3 = "update appointments set Contact_ID=(Select Contact_ID from contacts where Contact_Name='" + SelectedAppointment.ContactIDComboBox.getValue().toString() + "') where Appointment_ID=" + SelectedAppointment.getAppointment_ID() + ";";
                String updatedbysql3 = "update appointments set Last_Updated_By ='" + uservalue + "' where Appointment_ID=" + SelectedAppointment.getAppointment_ID() + ";";
                String last_updatesql3 = "update appointments set Last_Update = now() where Appointment_ID=" + SelectedAppointment.getAppointment_ID() + ";";
                AppointmentModSqlCommandsSaved.add(sqlstring3);
                AppointmentModSqlCommandsSaved.add(updatedbysql3);
                AppointmentModSqlCommandsSaved.add(last_updatesql3);
            }
            x++;

        }
        for (String st : AppointmentModSqlCommandsSaved) {
            PreparedStatement thisSQLstatement = JDBC.getConnection().prepareStatement(st);
            thisSQLstatement.executeUpdate();


        }
        isfromschedular=true;
        CustomerEditController.schedularselectedcustomerID=Integer.parseInt(selectedcustomerID);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.show();
    }

    /** This method loads the appointments from the database to the appointments table.
     * It will show the appointments for the Customer which has been clicked in the customer table
     * by the user.  As the user clicks on differect customers, this method is called each time and the data
     * is shown in the appointment table.
     * @param mouseEvent
     * @throws SQLException
     */
    public void loadAppointments(MouseEvent mouseEvent) throws SQLException {
        appointmentsTable.refresh();
        appointmentsTable.requestFocus();
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
        Start1.setCellFactory(TextFieldTableCell.forTableColumn());

        End1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("End"));
        End1.setCellFactory(TextFieldTableCell.forTableColumn());

        Create_Date1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Create_Date"));
        Create_By1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Created_By"));
        Last_Update1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Last_Update"));
        Last_Updated_By1.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Last_Updated_By"));


        Customer_IDComboBox.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Customer_IDComboBox"));
        User_IDComboBox.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("User_IDComboBox"));
        ContactIDComboBox.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("ContactIDComboBox"));

        ObservableList<Appointment> Appointmentlist = FXCollections.observableArrayList();
        ObservableList<Customer> clicklist = customertable.getSelectionModel().getSelectedItems();

        Integer ClickedCustomer_ID = 0;
        for (Customer ct : clicklist) {
            ClickedCustomer_ID = ct.getCustomer_ID();
        }
        selectedcustomerID=ClickedCustomer_ID.toString();

        if (isfromschedular){
            ClickedCustomer_ID = schedularselectedcustomerID;
            isfromschedular=false;
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

            LocalDate localcreateDate = LocalDate.parse(thisStart.substring(0,10));
            LocalTime localCreateTime = LocalTime.parse(thisStart.substring(11,19));
            ZoneId UTCZone= ZoneId.of("UTC");
            ZoneId localZoneId = ZoneId.systemDefault();
            ZonedDateTime ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,UTCZone);
            Instant llc= ThisCreate_date.toInstant();
            ZonedDateTime ddc = ThisCreate_date.withZoneSameInstant(localZoneId);
            thisStart=ddc.toString().replaceFirst("T"," ").substring(0,16)+":00";

            String thisEnd = apresults.getString("End");
            localcreateDate = LocalDate.parse(thisEnd.substring(0,10));
            localCreateTime = LocalTime.parse(thisEnd.substring(11,19));
            UTCZone= ZoneId.of("UTC");
            localZoneId = ZoneId.systemDefault();
            ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,UTCZone);
            llc= ThisCreate_date.toInstant();
            ddc = ThisCreate_date.withZoneSameInstant(localZoneId);
            thisEnd=ddc.toString().replaceFirst("T"," ").substring(0,16)+":00";


            String thisCreate_date = apresults.getString("Create_Date");
            localcreateDate = LocalDate.parse(thisCreate_date.substring(0,10));
            localCreateTime = LocalTime.parse(thisCreate_date.substring(11,19));
            UTCZone= ZoneId.of("UTC");
            localZoneId = ZoneId.systemDefault();
            ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,UTCZone);
            llc= ThisCreate_date.toInstant();
            ddc = ThisCreate_date.withZoneSameInstant(localZoneId);
            thisCreate_date=ddc.toString().replaceFirst("T"," ").substring(0,19);

            String thisCreate_by = apresults.getString("Created_By");

            String thisLastUpdate = apresults.getString("Last_Update");
            localcreateDate = LocalDate.parse(thisLastUpdate.substring(0,10));
            localCreateTime = LocalTime.parse(thisLastUpdate.substring(11,19));
            UTCZone= ZoneId.of("UTC");
            localZoneId = ZoneId.systemDefault();
            ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,UTCZone);
            llc= ThisCreate_date.toInstant();
            ddc = ThisCreate_date.withZoneSameInstant(localZoneId);
            thisLastUpdate=ddc.toString().replaceFirst("T"," ").substring(0,19);

            String thisLastUpdatedby = apresults.getString("Last_Updated_By");
            Integer thisCustomer_ID = apresults.getInt("Customer_ID");
            Integer thisUser_ID = apresults.getInt("User_ID");
            Integer thisContact_ID = apresults.getInt("Contact_ID");
            Appointment thisappointment = new Appointment(thisappointmentID, thisTitle, thisDescription, thisLocation, thisType, thisStart, thisEnd, thisCreate_date, thisCreate_by, thisLastUpdate, thisLastUpdatedby, thisCustomer_ID, thisUser_ID, thisContact_ID);
            DefaultAppointmentsCustomer_ID.add(thisappointment.getCustomer_ID().toString());
            DefaultAppointmentsUser_ID.add(thisappointment.getUser_ID().toString());
            DefaultAppointmentsContact_ID.add(thisappointment.getContact_ID().toString());

            Appointmentlist.add(thisappointment);
        }
        appointmentsTable.setItems(Appointmentlist);
        AllAppointments = Appointmentlist;
    }

    /** This method will display a message and delete appointments.  It will then display
     * a message indicating the appointment has been deleted.
     * @param actionEvent
     */
    public void DeleteAppointment(ActionEvent actionEvent) {
        ObservableList<Appointment> selectlist = appointmentsTable.getSelectionModel().getSelectedItems();
        for (Appointment SelectedAppointment : selectlist) {

            Alert areyousure = new Alert(Alert.AlertType.CONFIRMATION);
            areyousure.setTitle("CONFIRMATION");
            areyousure.setHeaderText("You are about to delete the selected appointment.");
            areyousure.setContentText("This cannot be undone. Are you sure?");
            areyousure.showAndWait();
            if (areyousure.getResult().getButtonData().toString() == "OK_DONE") {

                try {
                    PreparedStatement deleteAppointment = JDBC.getConnection().prepareStatement("delete from appointments where Appointment_ID=" + SelectedAppointment.getAppointment_ID());
                    deleteAppointment.executeUpdate();
                    Alert deletedone = new Alert(Alert.AlertType.INFORMATION);
                    deletedone.setTitle("INFORMATION");
                    deletedone.setHeaderText("The selected appointment has been deleted..");
                    deletedone.setContentText("Appointment ID "+SelectedAppointment.getAppointment_ID()+" with the type " +SelectedAppointment.getType()+" has been canceled. Delete Complete!");
                    deletedone.showAndWait();
                    try {
                        isfromschedular=true;
                        schedularselectedcustomerID=Integer.parseInt(selectedcustomerID);
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 750, 450);
                        stage.setTitle("Edit Customer Records");
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                    }
                } catch (Exception e) {


                }
            }

        }


    }

    /** This method adds SQL commands to a list to be executed later on.
     *  This method stores the data to save for customers.
     * @param cellEditEvent
     */
    public void ValueChanged(TableColumn.CellEditEvent cellEditEvent) {
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
        System.out.println("Value Changed");
    }

    /** This method adds SQL commands to a list to be executed later on.
     *  This method stores the data to save for appointments.
     * @param cellEditEvent
     */
    public void ValueChangedAppointments(TableColumn.CellEditEvent cellEditEvent) {
        Object oldvalue = cellEditEvent.getOldValue();
        Object newvalue = cellEditEvent.getNewValue();
        String tablename = cellEditEvent.getTableColumn().getText();

        ObservableList<Appointment> clicklist = appointmentsTable.getSelectionModel().getSelectedItems();
        Integer ClickedAppointment_ID = 0;
        for (Appointment apt : clicklist) {
            ClickedAppointment_ID = apt.getAppointment_ID();
        }
        cellEditEvent.getTablePosition().getRow();
        ChangedData=String.valueOf(cellEditEvent.getTablePosition().getRow());
        ChangedatalistId.add(ClickedAppointment_ID.toString());
        ChangedatelistColumn.add(String.valueOf(cellEditEvent.getTableView().getEditingCell().getColumn()));
        Changedatalistvalue.add(cellEditEvent.getNewValue().toString());

        String updatedbysql = "update appointments set Last_Updated_By ='" + uservalue + "' where Appointment_ID=" + ClickedAppointment_ID + ";";
        String last_updatesql = "update appointments set Last_Update = now() where Appointment_ID=" + ClickedAppointment_ID + ";";
        String sqlstring = "update appointments set " + tablename + " = '" + newvalue + "' where Appointment_ID=" + ClickedAppointment_ID + ";";
        AppointmentModSqlCommandsSaved.add(updatedbysql);
        AppointmentModSqlCommandsSaved.add(last_updatesql);
        AppointmentModSqlCommandsSaved.add(sqlstring);
    }

    /** This method is called when Start cell is committed by the user.  This calls the AppointmentPicker scene
     * but loads it on top of the existing window.  Once the user completes the form, this method stores the new
     * date and time for Start in the SQL commands to be executed if the user clicks on save.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void appointmenteditCommit(TableColumn.CellEditEvent event) throws IOException, SQLException {
        Appointmentpicker.namebox = "Start";
        Appointmentpicker.olddatevalue = String.valueOf(event.getNewValue());
        ObservableList<Appointment> clicklist = appointmentsTable.getSelectionModel().getSelectedItems();
        Integer ClickedAppointment_ID = 0;


        clicklist.forEach(Appointment->Appointment.getAppointment_ID());


        for (Appointment SingleAppointment : clicklist) {
            ClickedAppointment_ID = SingleAppointment.getAppointment_ID();
        }
        Appointmentpicker.ClickedAppointment_ID = ClickedAppointment_ID;



        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/appointmentpicker.fxml")));
        Scene scene = new Scene(root, 380, 200);
        Stage stage2 = new Stage();
        stage2.setTitle("Pick a New Date");
        stage2.setScene(scene);
        stage2.setAlwaysOnTop(true);
        stage2.showAndWait();

        //ObservableList<Appointment> currentlist = appointmentsTable.getSelectionModel().getSelectedItems();
        ObservableList<Appointment> currentlist = appointmentsTable.getItems();
        ObservableList<Appointment> EditedAppointmentlist = FXCollections.observableArrayList();
        Integer count=0;
        //ChangedatalistId
        //ChangedatalistRow
        //ChangedatelistColumn
        //Changedatalistvalue

        for (Appointment SingleAppointment : currentlist) {
            Integer thisappointmentID = SingleAppointment.getAppointment_ID();
            String thisTitle = SingleAppointment.getTitle();
            String thisDescription = SingleAppointment.getDescription();
            String thisLocation = SingleAppointment.getLocation();
            String thisType = SingleAppointment.getType();
            String thisStart=SingleAppointment.getStart();


            if (event.getTablePosition().getRow()==count){
            thisStart = newdatefrompick;}

            String thisEnd = SingleAppointment.getEnd();
            String thisCreate_date = SingleAppointment.getCreate_Date();
            String thisCreate_by = SingleAppointment.getCreated_By();
            String thisLastUpdate = SingleAppointment.getLast_Update();
            String thisLastUpdatedby = SingleAppointment.getLast_Updated_By();
            Integer thisCustomer_ID = SingleAppointment.getCustomer_ID();
            Integer thisUser_ID = SingleAppointment.getUser_ID();
            Integer thisContact_ID = SingleAppointment.getContact_ID();
            Boolean hasthisIDbeenedited = ChangedatalistId.contains(thisappointmentID.toString());
            while (hasthisIDbeenedited){
            hasthisIDbeenedited = ChangedatalistId.contains(thisappointmentID.toString());
            if (hasthisIDbeenedited){
                int changedindex=ChangedatalistId.lastIndexOf(thisappointmentID.toString());
                int changedcolumn=Integer.parseInt(ChangedatelistColumn.get(changedindex));
                if (changedcolumn==1){
                    thisTitle = Changedatalistvalue.get(changedindex);
                }
                if (changedcolumn==2){
                    thisDescription = Changedatalistvalue.get(changedindex);
                }
                if (changedcolumn==3){
                    thisLocation = Changedatalistvalue.get(changedindex);
                }
                if (changedcolumn==4){
                    thisType = Changedatalistvalue.get(changedindex);
                }


                ChangedatalistId.remove(changedindex);
                ChangedatelistColumn.remove(changedindex);
                Changedatalistvalue.remove(changedindex);
            }}



            Appointment thisappointment = new Appointment(thisappointmentID, thisTitle, thisDescription, thisLocation, thisType, thisStart, thisEnd, thisCreate_date, thisCreate_by, thisLastUpdate, thisLastUpdatedby, thisCustomer_ID, thisUser_ID, thisContact_ID);
            EditedAppointmentlist.add(thisappointment);
            count++;
        }
        appointmentsTable.setItems(EditedAppointmentlist);
        AppointmentModSqlCommandsSaved.add(newsqldate);
    }

    /** Called when End cell is committed by the user.  This calls the AppointmentPicker scene
    * but loads it on top of the existing window.  Once the user completes the form, this method stores the new
    * date and time for End in the SQL commands to be executed if the user clicks on save.
    * @param event
    * @throws IOException
    * @throws SQLException
    **/
    public void appointmentEndeditCommit(TableColumn.CellEditEvent event) throws IOException, SQLException {
        Appointmentpicker.namebox = "End";
        Appointmentpicker.olddatevalue = String.valueOf(event.getNewValue());
        ObservableList<Appointment> clicklist = appointmentsTable.getSelectionModel().getSelectedItems();
        Integer ClickedAppointment_ID = 0;
        for (Appointment SingleAppointment : clicklist) {
            ClickedAppointment_ID = SingleAppointment.getAppointment_ID();
        }
        Appointmentpicker.ClickedAppointment_ID = ClickedAppointment_ID;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/appointmentpicker.fxml")));
        Scene scene = new Scene(root, 380, 200);
        Stage stage2 = new Stage();
        stage2.setTitle("Pick a New Date");
        stage2.setScene(scene);
        stage2.setAlwaysOnTop(true);
        stage2.showAndWait();

        ObservableList<Appointment> currentlist = appointmentsTable.getItems();
        ObservableList<Appointment> EditedAppointmentlist = FXCollections.observableArrayList();
        Integer count=0;

        for (Appointment SingleAppointment : currentlist) {
            Integer thisappointmentID = SingleAppointment.getAppointment_ID();
            String thisTitle = SingleAppointment.getTitle();
            String thisDescription = SingleAppointment.getDescription();
            String thisLocation = SingleAppointment.getLocation();
            String thisType = SingleAppointment.getType();
            String thisStart=SingleAppointment.getStart();
            String thisEnd = SingleAppointment.getEnd();


            if (event.getTablePosition().getRow()==count){
                thisEnd = newdatefrompick;}

            String thisCreate_date = SingleAppointment.getCreate_Date();
            String thisCreate_by = SingleAppointment.getCreated_By();
            String thisLastUpdate = SingleAppointment.getLast_Update();
            String thisLastUpdatedby = SingleAppointment.getLast_Updated_By();
            Integer thisCustomer_ID = SingleAppointment.getCustomer_ID();
            Integer thisUser_ID = SingleAppointment.getUser_ID();
            Integer thisContact_ID = SingleAppointment.getContact_ID();
            Boolean hasthisIDbeenedited = ChangedatalistId.contains(thisappointmentID.toString());
            while (hasthisIDbeenedited){
                hasthisIDbeenedited = ChangedatalistId.contains(thisappointmentID.toString());
                if (hasthisIDbeenedited){
                    int changedindex=ChangedatalistId.lastIndexOf(thisappointmentID.toString());
                    int changedcolumn=Integer.parseInt(ChangedatelistColumn.get(changedindex));
                    if (changedcolumn==1){
                        thisTitle = Changedatalistvalue.get(changedindex);
                    }
                    if (changedcolumn==2){
                        thisDescription = Changedatalistvalue.get(changedindex);
                    }
                    if (changedcolumn==3){
                        thisLocation = Changedatalistvalue.get(changedindex);
                    }
                    if (changedcolumn==4){
                        thisType = Changedatalistvalue.get(changedindex);
                    }


                    ChangedatalistId.remove(changedindex);
                    ChangedatelistColumn.remove(changedindex);
                    Changedatalistvalue.remove(changedindex);
                }}



            Appointment thisappointment = new Appointment(thisappointmentID, thisTitle, thisDescription, thisLocation, thisType, thisStart, thisEnd, thisCreate_date, thisCreate_by, thisLastUpdate, thisLastUpdatedby, thisCustomer_ID, thisUser_ID, thisContact_ID);
            EditedAppointmentlist.add(thisappointment);
            count++;
        }
        appointmentsTable.setItems(EditedAppointmentlist);
        AppointmentModSqlCommandsSaved.add(newsqldate);
    }

    /** Called when the user clicks the cell to edit Start or End.  This method will automaticly end the edit and call
     * the Commit action which will call the Appointmentpicker.
     * @param cellEditEvent
     * @throws InterruptedException
     */
    public void appointmentStartToEdit(TableColumn.CellEditEvent cellEditEvent) throws InterruptedException {
        Thread.sleep(20);
        Robot keyboard = new Robot();
        cellEditEvent.getTableView().requestFocus();
        keyboard.keyPress(KeyCode.ENTER);

    }

    /** Switches scene to add a new appointment.
     *
     * @param actionEvent
     * @throws Exception
     */
    public void AddNewAppointment(ActionEvent actionEvent) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/AddNewAppointment.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 550, 450);
        stage.setTitle("Add a new Appointment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /** Logs out the User and returns to the Log in scene
     *
     * @param actionEvent
     * @throws Exception
     */
    public void Logout(ActionEvent actionEvent) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/loginscreen.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 340, 200);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /** Exits the entire application
     *
     * @param actionEvent
     */
    public void ExitApplication(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

    }

    /** Changes the scene to the Desktop Scheduler for the logged in user.
     *
     * @param actionEvent
     * @throws Exception
     */
    public void LoadScheduler(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/Scheduler.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /** Changes the scene to the reports scene.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void GenerateReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/Reports.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("Generate Reports");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void CountryAction(ActionEvent actionEvent) {

    }

    public void CountryValueChanged(TableColumn.CellEditEvent cellEditEvent) {
        ValueChanged(cellEditEvent);

    }
}