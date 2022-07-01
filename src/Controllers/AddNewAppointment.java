package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;

public class AddNewAppointment {
    public TextField DescriptionTextInput;
    public TextField TitletextInput;
    public TextField LocationTextInput;
    public TextField TypeTextInput;
    public ComboBox CustomerNameCbox;
    public ComboBox emailCbox;
    public ComboBox EndMinCbox;
    public ComboBox EndHourCbox;
    public ComboBox ContactNameCbox;
    public ComboBox StartHourCbox;
    public ComboBox StartMinCbox;
    public DatePicker StartDatePick;
    public DatePicker EndDatePick;
    public ComboBox UserCbox;

    public void initialize() throws SQLException {
        System.out.println(CustomerEditController.uservalue);
        System.out.println(CustomerEditController.selectedcustomerID);
        ObservableList<String> AllCustomerNames= FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Customer_Name from customers;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Customer_Name");
            AllCustomerNames.add(thisString);
        }
        CustomerNameCbox.setItems(AllCustomerNames);
        preparedStatement = JDBC.getConnection().prepareStatement("select Customer_Name from customers where Customer_ID="+CustomerEditController.selectedcustomerID+";");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Customer_Name");
            CustomerNameCbox.setValue(thisString);
        }
        ObservableList<String> AllEmails= FXCollections.observableArrayList();
        preparedStatement = JDBC.getConnection().prepareStatement("select Email from contacts;");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Email");
            AllEmails.add(thisString);
        }
        emailCbox.setItems(AllEmails);
        ObservableList<String> AllContacts= FXCollections.observableArrayList();
        preparedStatement = JDBC.getConnection().prepareStatement("select Contact_Name from contacts;");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Contact_Name");
            AllContacts.add(thisString);
        }
        ContactNameCbox.setItems(AllContacts);
        ObservableList<String> Allusers= FXCollections.observableArrayList();
        preparedStatement = JDBC.getConnection().prepareStatement("select User_ID from users;");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("User_ID");
            Allusers.add(thisString);
        }
        UserCbox.setItems(Allusers.sorted());
        ObservableList<String> hourlist=FXCollections.observableArrayList();
        ObservableList<String> minslist=FXCollections.observableArrayList();

        for (int x=8;x<22;x++){
            if (x<10) {
                hourlist.add("0"+String.valueOf(x));
            }else{
                hourlist.add(String.valueOf(x));

            }

        }
        for (int x=0;x<60;x++){
            if (x<10) {
                minslist.add("0"+String.valueOf(x));
            }else{
                minslist.add(String.valueOf(x));

            }

        }

        StartHourCbox.setItems(hourlist);
        EndHourCbox.setItems(hourlist);
        StartMinCbox.setItems(minslist);
        EndMinCbox.setItems(minslist);



    }

    public void cancel(ActionEvent actionEvent) throws Exception {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.show();

    }


    public void ContactNameComboBoxHasAction(ActionEvent actionEvent) throws SQLException {
        String newContactName=ContactNameCbox.getValue().toString();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Email from contacts where Contact_Name='" + newContactName + "';");
        System.out.println(preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Email");
            emailCbox.setValue(thisString);
        }

    }

    public void ContactEmailComboBoxHasAction(ActionEvent actionEvent) throws SQLException {
        String newmail = emailCbox.getValue().toString();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Contact_Name from contacts where Email='" + newmail + "';");
        System.out.println(preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Contact_Name");
            ContactNameCbox.setValue(thisString);
        }
    }

    public void submitNewAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Max(Appointment_ID) from appointments;");
        ResultSet resultSet = preparedStatement.executeQuery();
        Integer AppointmentID = null;
        while (resultSet.next()) {
            AppointmentID = resultSet.getInt("Max(Appointment_ID)") + 1;
        }
        String Title=TitletextInput.getText();
        String Description=DescriptionTextInput.getText();
        String Location= LocationTextInput.getText();
        String Type=TypeTextInput.getText();

        String Start=StartDatePick.getValue().toString()+" "+StartHourCbox.getValue().toString()+":"+StartMinCbox.getValue().toString();
        Start=Start+":00";
        LocalDate localcreateDate = LocalDate.parse(Start.substring(0,10));
        LocalTime localCreateTime = LocalTime.parse(Start.substring(11,19));
        ZoneId UTCZone= ZoneId.of("UTC");
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,localZoneId);
        Instant llc= ThisCreate_date.toInstant();
        ZonedDateTime ddc = ThisCreate_date.withZoneSameInstant(UTCZone);
        ZonedDateTime ddl = ThisCreate_date.withZoneSameInstant(localZoneId);
        Start=ddc.toString().replaceFirst("T"," ").substring(0,16)+":00";


        String End=EndDatePick.getValue().toString()+" "+EndHourCbox.getValue().toString()+":"+EndMinCbox.getValue().toString();
        End=End+":00";
        localcreateDate = LocalDate.parse(End.substring(0,10));
        localCreateTime = LocalTime.parse(End.substring(11,19));
        UTCZone= ZoneId.of("UTC");
        localZoneId = ZoneId.systemDefault();
        ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,localZoneId);
        llc= ThisCreate_date.toInstant();
        ddc = ThisCreate_date.withZoneSameInstant(UTCZone);
        ddl = ThisCreate_date.withZoneSameInstant(localZoneId);
        End=ddc.toString().replaceFirst("T"," ").substring(0,16)+":00";


        String Create_Date="NOW()";
        String Created_By=CustomerEditController.uservalue;
        String Last_Update="NOW()";
        String Last_Updated_By=CustomerEditController.uservalue;
        String Customer_ID="(Select Customer_ID from customers where Customer_Name='"+CustomerNameCbox.getValue().toString()+"')";
        String User_ID=UserCbox.getValue().toString();
        String Contact_ID="(Select Contact_ID from contacts where Email='"+emailCbox.getValue().toString()+"')";
        System.out.println(AppointmentID);
        System.out.println(Title);
        System.out.println(Description);
        System.out.println(Location);
        System.out.println(Type);
        System.out.println(Start);
        System.out.println(End);
        System.out.println(Create_Date);
        System.out.println(Created_By);
        System.out.println(Last_Update);
        System.out.println(Last_Updated_By);
        System.out.println(Customer_ID);
        System.out.println(User_ID);
        System.out.println(Contact_ID);
        String NewAppointmentSQL="Insert into appointments values ("+AppointmentID+",'"+Title+"','"+Description+"','"+Location+"','"+Type+"','"+Start+"','"+End+"',"+Create_Date+",'"+Created_By+"',"+Last_Update+",'"+Last_Updated_By+"',"+Customer_ID+","+User_ID+","+Contact_ID+")";
        System.out.println(NewAppointmentSQL);
        try {
            preparedStatement = JDBC.getConnection().prepareStatement(NewAppointmentSQL);
            preparedStatement.execute();
        }
        catch (Exception e){
            System.out.println("Unable to Add appointment");
            System.out.println(e);
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.show();


    }
}




