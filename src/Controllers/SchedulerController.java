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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.plaf.synth.SynthTabbedPaneUI;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class SchedulerController {
    public TableView appointmentsTable;
    public TableColumn Appointment_ID;
    public TableColumn Title;
    public TableColumn Description;
    public TableColumn Location;
    public TableColumn Type;
    public TableColumn Start;
    public TableColumn End;
    public TableColumn Customer_ID;
    public TableColumn User_ID;
    public ComboBox User_ID_combo;
    public RadioButton MonthRadio;
    public RadioButton WeekRadio;
    public Integer viewselected;
    public RadioButton AllViewRadio;

    public void initialize() throws SQLException {
        MonthRadio.setSelected(false);
        WeekRadio.setSelected(false);
        AllViewRadio.setSelected(true);
        ToggleGroup toggleGroup = new ToggleGroup();
        viewselected=1826250;
        MonthRadio.setToggleGroup(toggleGroup);
        WeekRadio.setToggleGroup(toggleGroup);
        AllViewRadio.setToggleGroup(toggleGroup);
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
        Location.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Location"));
        Type.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Type"));
        Start.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDate>("Start"));
        End.setCellValueFactory(new PropertyValueFactory<Appointment, String>("End"));
        Customer_ID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Customer_ID"));
        User_ID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("User_ID"));
        ObservableList<String> combolist = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select User_ID from users;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String AppointmentUserID = resultSet.getString("User_ID") ;
            combolist.add(AppointmentUserID);

        }
        User_ID_combo.setItems(combolist.sorted());
        preparedStatement = JDBC.getConnection().prepareStatement("select User_ID from users where User_Name='"+CustomerEditController.uservalue+"';");
        String UserIDofUser=null;
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserIDofUser = resultSet.getString("User_ID") ;
        }

        User_ID_combo.setValue(UserIDofUser);
        System.out.println("Set the combobox");
        ObservableList<Appointment> Appointmentlist = FXCollections.observableArrayList();
        String ClickedUser_ID=User_ID_combo.getValue().toString();
        System.out.println("Loading Appointments from User" + ClickedUser_ID);
        PreparedStatement sqlappointment = JDBC.getConnection().prepareStatement("select * from appointments where User_ID=" + ClickedUser_ID);
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


            Appointmentlist.add(thisappointment);
        }
        appointmentsTable.setItems(Appointmentlist);
    }

    public void IDChanged(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> Appointmentlist = FXCollections.observableArrayList();
        String ClickedUser_ID = User_ID_combo.getValue().toString();

        System.out.println("Loading Appointments from User" + ClickedUser_ID);
        PreparedStatement sqlappointment = JDBC.getConnection().prepareStatement("select * from appointments where User_ID=" + ClickedUser_ID);
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


            int inputyear = Integer.parseInt(thisappointment.getStart().substring(0, 4));
            int inputmonth = Integer.parseInt(thisappointment.getStart().substring(5, 7));
            int inputdays = Integer.parseInt(thisappointment.getStart().substring(8, 10));
            int compareyear = Integer.parseInt(LocalDateTime.now().toString().substring(0, 4));
            int comparemonth = Integer.parseInt(LocalDateTime.now().toString().substring(5, 7));
            int comparedat = Integer.parseInt(LocalDateTime.now().toString().substring(8, 10));
            LocalDate date1=LocalDate.parse(thisappointment.getStart().substring(0, 10));
            LocalDate date2= LocalDate.now();
            System.out.println("Compared");
            long daysapart=365;
            if (date1.isAfter(date2)) {
                daysapart = date2.datesUntil(date1).count();
            }else{
                daysapart = date1.datesUntil(date2).count();
            }
            if (daysapart < viewselected){
                System.out.println(thisappointment.getAppointment_ID()+" is "+daysapart+" days apart, which is less than "+viewselected+"days away.");
                Appointmentlist.add(thisappointment);
                System.out.println("Added to appointment list : "+thisappointment.getCustomer_ID() );
            }else {
                System.out.println(thisappointment.getAppointment_ID()+" is "+daysapart+" days apart, which is more than "+viewselected+"days away.");
            }
        }
        appointmentsTable.setItems(Appointmentlist);
    }

    public void MonthRadioClicked(ActionEvent actionEvent) throws SQLException {
        MonthRadio.setSelected(true);
        viewselected=31;
        IDChanged(actionEvent);

    }

    public void WeekRadioClicked(ActionEvent actionEvent) throws SQLException {
        WeekRadio.setSelected(true);
        viewselected=7;
        IDChanged(actionEvent);

    }

    public void AllRadioClicked(ActionEvent actionEvent) throws SQLException {

        AllViewRadio.setSelected(true);

        viewselected=1826250;
        IDChanged(actionEvent);

    }

    public void ReturntoMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/MainMenu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 350);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void EditAppointment(ActionEvent actionEvent) throws IOException {
        CustomerEditController.isfromschedular=true;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void AddNewAppointment(ActionEvent actionEvent) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/AddNewAppointment.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 550, 450);
        stage.setTitle("Add a new Appointment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void selectappointment(MouseEvent mouseEvent) {
        ObservableList<Appointment> clicklist = appointmentsTable.getSelectionModel().getSelectedItems();

        Integer ClickedCustomer_ID = 0;
        for (Appointment ct : clicklist) {
            ClickedCustomer_ID = ct.getUser_ID();
            CustomerEditController.schedularselectedcustomerID=ClickedCustomer_ID;
            System.out.println(CustomerEditController.schedularselectedcustomerID);
        }

    }
}
