package Controllers;
/**
 * class controller.AddNewAppointment.java
 */
/**
 * class AddNewAppointment.java
 */
/**
 * @author Chad Self
 */
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
/** AddNewAppointment is designed to allow the User to add New Appointments.
 */
public class AddNewAppointment {
    public Boolean DoNotLeave = false;
    public Integer Alertnumber=0;
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
    public ObservableList<String> hourlist = FXCollections.observableArrayList();
    public ObservableList<String> minslist = FXCollections.observableArrayList();

    /**
     * The initialize method initialize values, sets the choices for all comboBoxes, sets any known data
     * from previous scenes.
     *
     */
    public void initialize() throws SQLException {
        ObservableList<String> AllCustomerNames = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Customer_Name from customers;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Customer_Name");
            AllCustomerNames.add(thisString);
        }
        CustomerNameCbox.setItems(AllCustomerNames);
        preparedStatement = JDBC.getConnection().prepareStatement("select Customer_Name from customers where Customer_ID=" + CustomerEditController.selectedcustomerID + ";");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Customer_Name");
            CustomerNameCbox.setValue(thisString);
        }
        ObservableList<String> AllEmails = FXCollections.observableArrayList();
        preparedStatement = JDBC.getConnection().prepareStatement("select Email from contacts;");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Email");
            AllEmails.add(thisString);
        }
        emailCbox.setItems(AllEmails);
        ObservableList<String> AllContacts = FXCollections.observableArrayList();
        preparedStatement = JDBC.getConnection().prepareStatement("select Contact_Name from contacts;");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Contact_Name");
            AllContacts.add(thisString);
        }
        ContactNameCbox.setItems(AllContacts);
        ObservableList<String> Allusers = FXCollections.observableArrayList();
        preparedStatement = JDBC.getConnection().prepareStatement("select User_ID from users;");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("User_ID");
            Allusers.add(thisString);
        }
        UserCbox.setItems(Allusers.sorted());




        for (int x = 0; x < 60; x++) {
            if (x < 10) {
                minslist.add("0" + String.valueOf(x));
            } else {
                minslist.add(String.valueOf(x));

            }

            LocalDate NowDate= LocalDate.now();
            hourlist.clear();
            for (int y = 0; y < 14; y++) {
                LocalTime OpenTimeETC = LocalTime.of(8 + y, 00, 00);
                ZonedDateTime OpenDateETC = ZonedDateTime.of(NowDate, OpenTimeETC, ZoneId.of("America/New_York"));
                ZonedDateTime LocalOpenTime = OpenDateETC.withZoneSameInstant(ZoneId.systemDefault());
                String thishour="0"+String.valueOf(LocalOpenTime.getHour());
                hourlist.add(thishour.substring(thishour.length()-2));

            }


            }

        StartHourCbox.setItems(hourlist.sorted());
        EndHourCbox.setItems(hourlist.sorted());
        StartMinCbox.setItems(minslist);
        EndMinCbox.setItems(minslist);
        StartMinCbox.setValue("00");
        EndMinCbox.setValue("00");
        StartDatePick.setValue(LocalDate.now());
        EndDatePick.setValue(LocalDate.now());

        PreparedStatement quickSQL = JDBC.getConnection().prepareStatement("Select User_ID from users where User_Name='"+CustomerEditController.uservalue+"';");
        ObservableList<String> temp = FXCollections.observableArrayList();
        ResultSet quickResults = quickSQL.executeQuery();
        String UserID="";
        while (quickResults.next()) {
            UserID = quickResults.getString("User_ID");
        }
        UserCbox.setValue(UserID);
    }

    /**
     * This method changes the scene to the customer edit menu and does not save any data.
     * @param actionEvent changes the scene
     * @throws  IOException actionEvent
     */
    public void cancel(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    /** This Method checks to input to make sure the new dates do not overlapp existing dates.
     * When invalid data is entered a boolean is set which prevents the user from saving the data
     * or leaving the add new appointment scene.
     * @param Startstring
     * @param EndString
     * @param CustomerID
     * @throws SQLException
     */
    public void checkinput(String Startstring, String EndString, String CustomerID) throws SQLException {
        if (DoNotLeave == false) {
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select  ('" + Startstring + "' >= Start) as StartSData,('" + Startstring + "' < End) as StartEData,('"+  EndString + "' >= Start) as EndSData,('" + EndString + "' < End) as EndEData from appointments where Customer_ID="+CustomerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer St = resultSet.getInt("StartSData");
                Integer En = resultSet.getInt("StartEData");
                Integer EnSt = resultSet.getInt("EndSData");
                Integer EnEn = resultSet.getInt("EndEData");

                //System.out.println("St : "+St);
                //System.out.println("EN : "+En);
                if ((St == 1 & En == 1)|(EnSt == 1 & EnEn==1)) {
                    DoNotLeave = true;
                    Alert areyousure = new Alert(Alert.AlertType.ERROR);
                    areyousure.setTitle("Unable to Schedule Appointment");
                    areyousure.setHeaderText("The date chosen is Overlapping another Appointment.");
                    areyousure.setContentText("Please choose a new date");
                    System.out.println("Alert "+Alertnumber);
                    if (Alertnumber==0){
                    areyousure.showAndWait();
                    Alertnumber++;}
                }


            }

        }


    }

    /** This method sets the email combo box when the user selects the corresponding name combo box.
     * @param actionEvent
     * @throws SQLException
     */
    public void ContactNameComboBoxHasAction(ActionEvent actionEvent) throws SQLException {
        String newContactName=ContactNameCbox.getValue().toString();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Email from contacts where Contact_Name='" + newContactName + "';");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Email");
            emailCbox.setValue(thisString);
        }

    }

    /** This method sets the name combo box when the user selects the corresponding email combo box.
     * @param actionEvent
     * @throws SQLException
     */
    public void ContactEmailComboBoxHasAction(ActionEvent actionEvent) throws SQLException {
        String newmail = emailCbox.getValue().toString();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Contact_Name from contacts where Email='" + newmail + "';");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisString = resultSet.getString("Contact_Name");
            ContactNameCbox.setValue(thisString);
        }
    }

    /**This method saves the new appointment to the database.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void submitNewAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        System.out.println("Button Pressed setting Alertnumber "+Alertnumber );
        Alertnumber=0;
        DoNotLeave=false;
        System.out.println("Button Pressed setting Alertnumber "+Alertnumber);

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

        checkinput(Start,End,Customer_ID);





        String NewAppointmentSQL="Insert into appointments values ("+AppointmentID+",'"+Title+"','"+Description+"','"+Location+"','"+Type+"','"+Start+"','"+End+"',"+Create_Date+",'"+Created_By+"',"+Last_Update+",'"+Last_Updated_By+"',"+Customer_ID+","+User_ID+","+Contact_ID+")";
        System.out.println(DoNotLeave);
        System.out.println(NewAppointmentSQL);

        if (DoNotLeave==false){
            try {
                preparedStatement = JDBC.getConnection().prepareStatement(NewAppointmentSQL);
                preparedStatement.execute();
            }
            catch (Exception e){
            }
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 450);
            stage.setTitle("Edit Customer Records");
            stage.setScene(scene);
            stage.show();
        }

    }


}





