package Controllers;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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


    public void initialize() throws SQLException {



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
        User_ID_combo.setItems(combolist);
        User_ID_combo.setValue(1);
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
        }
        appointmentsTable.setItems(Appointmentlist);
    }

}
