package Controllers;

import Main.LambdaInterface;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import static java.lang.String.valueOf;
/** Appointmentpicker is designed to collect new dates and times from the user.
/* by utilizing datepicker and combo boxes data is required to be valid.
 */
public class Appointmentpicker {
    public static String olddatevalue;
    public static Integer ClickedAppointment_ID;
    public static String namebox;
    public Boolean DoNotLeave = false;
    public Integer Alertnumber=0;
    public DatePicker AppointmentDatepicker;
    public ComboBox apphour;
    public ComboBox appmins;
    public ObservableList<String> hourslist = FXCollections.observableArrayList();
    public ObservableList<String> hourlist = FXCollections.observableArrayList();
    public ObservableList<String> minslist = FXCollections.observableArrayList();

    /**
     * The initialize method initialize values, sets the choices for all comboBoxes, sets any known data
     * from previous scenes.
     *This also initializes the allowed time choices.  Only times which are within the business hours are
     * available for the user to choose.
     */
    public void initialize() {
        for (int i=0;i<24;i++){
            if (valueOf(i).length() < 2){
                hourslist.add('0'+valueOf(i));
            }else{
                hourslist.add(valueOf(i));

            }        }
        for (int i=0;i<60;i++){
            if (valueOf(i).length() < 2){
                minslist.add('0'+valueOf(i));
            }else{
                minslist.add(valueOf(i));

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
        apphour.setItems(hourlist.sorted());
        apphour.setValue(hourlist.get(0));
        String ddate=olddatevalue.substring(0,10);
        String dmins=olddatevalue.substring(14,16);
        AppointmentDatepicker.setValue(LocalDate.parse(ddate));
        appmins.setItems(minslist);
        appmins.setValue(dmins);
    }

    public void checkinput(String NewValue) throws SQLException {
        String EndValue="";
        String StartValue="";
        String StartString="";
        String EndString="";

        PreparedStatement GetCustomerIDFromAppointmentID = JDBC.getConnection().prepareStatement("select Customer_ID from appointments where Appointment_ID="+ClickedAppointment_ID);
        ResultSet CustomerIDFromAppointmentID = GetCustomerIDFromAppointmentID.executeQuery();
        String CustomerID="";
        while (CustomerIDFromAppointmentID.next()) {
                CustomerID=CustomerIDFromAppointmentID.getString("Customer_ID");
            }
        System.out.println("customerid "+CustomerID);
        System.out.println("Box: "+namebox);
        System.out.println("appointment "+ClickedAppointment_ID);
        if (namebox.toString().equals("Start")){
                StartString=NewValue;
                PreparedStatement getend=JDBC.getConnection().prepareStatement("select End from appointments where Appointment_ID="+ClickedAppointment_ID);
                ResultSet ResultsofEnd = getend.executeQuery();
                while (ResultsofEnd.next()) {
                    EndString=ResultsofEnd.getString("End");
                }
                System.out.println("Start : "+StartString);

                System.out.println("End zoned : "+EndString);
            }
        if (namebox.toString().equals("End")){
            EndString=NewValue;
            PreparedStatement getend=JDBC.getConnection().prepareStatement("select Start from appointments where Appointment_ID="+ClickedAppointment_ID);
            ResultSet ResultsofEnd = getend.executeQuery();
            while (ResultsofEnd.next()) {
                StartValue=ResultsofEnd.getString("Start");
            }

            StartString=StartValue;
            System.out.println("Start : "+StartString);
            System.out.println("End : "+EndString);
        }
        if (DoNotLeave == false) {
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select  ('" + StartString + "' >= Start) as StartSData,('" + StartString + "' < End) as StartEData,('"+  EndString + "' >= Start) as EndSData,('" + EndString + "' < End) as EndEData from appointments where Customer_ID="+CustomerID+" AND Appointment_ID!="+ClickedAppointment_ID );
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer St = resultSet.getInt("StartSData");
                Integer En = resultSet.getInt("StartEData");
                Integer EnSt = resultSet.getInt("EndSData");
                Integer EnEn = resultSet.getInt("EndEData");

                System.out.println("St : "+St);
                System.out.println("EN : "+En);
                System.out.println("EnSt : "+EnSt);
                System.out.println("EnEn : "+EnEn);
                System.out.println(((St == 1 & En == 1)|(EnSt == 1 & EnEn==1)));
                if ((St == 1 & En == 1)|(EnSt == 1 & EnEn==1)) {
                    System.out.println("Overlapping Appointment");
                    DoNotLeave = true;
                    Alert areyousure = new Alert(Alert.AlertType.ERROR);
                    areyousure.setTitle("Unable to Schedule Appointment");
                    areyousure.setHeaderText("The date chosen is Overlapping another Appointment.");
                    areyousure.setContentText("Please choose a new date");
                    System.out.println("Alert "+Alertnumber);
                    if (Alertnumber==0){
                        areyousure.setX(380);
                        areyousure.setY(400);
                        areyousure.showAndWait();
                        Alertnumber++;}

                }


            }

        }

    }





    /** This method Submits new values which the user selected from datepicker and combo boxes.
     * This Method also incorporates a Lambda Expression to improve code.
     * This Method also changes the scene by closing the Appointment picker window.
     * @param actionEvent
     */
    public void SubmitNewAppointment(ActionEvent actionEvent) throws SQLException {
        String sqlsubstring=AppointmentDatepicker.getValue()+" "+apphour.getValue()+":"+appmins.getValue()+":00";
        /** The following is the first Lamda Expression required to improve code
         * It uses the LamdaInterface.ZonedTime interface
         * It uses LamdaZone to define its functionality and uses the Lambda expression ->
         * It returens a Zoned date when a string date is passed from the user input.
         * the data can then be converted between local time or any other timezone.
         * It repaced the following code :
         * <code>  LocalDate localcreateDate = LocalDate.parse(sqlsubstring.substring(0,10));
         *         LocalTime localCreateTime = LocalTime.parse(sqlsubstring.substring(11,19));
         *         ZonedDateTime ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,localZoneId);
         *         ZonedDateTime ddc = ThisCreate_date.withZoneSameInstant(ZoneId.of("UTC"));
         *         ZonedDateTime ddl = ThisCreate_date.withZoneSameInstant(ZoneId.systemDefault());</code>
         * **/
        LambdaInterface.ZonedTime LamdaZone = (s,z) -> ZonedDateTime.of(LocalDate.parse(s.substring(0,10)),LocalTime.parse(s.substring(11,19)),z);
        ZonedDateTime ThisCreate_date = LamdaZone.String2Zoned(sqlsubstring,ZoneId.systemDefault());
        ZonedDateTime ddc = ThisCreate_date.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime ddl = ThisCreate_date.withZoneSameInstant(ZoneId.systemDefault());

        sqlsubstring=ddc.toString().replaceFirst("T"," ").substring(0,16)+":00";
        String formdata=ddl.toString().replaceFirst("T"," ").substring(0,16)+":00";
        String sqlstring="update appointments set "+namebox+"='"+sqlsubstring+"' where Appointment_ID="+ClickedAppointment_ID+";";
        CustomerEditController.newsqldate=sqlstring;
        CustomerEditController.newdatefrompick=formdata;
        DoNotLeave = false;
        checkinput(sqlsubstring);
        if (!DoNotLeave) {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    /**This method returns to the Customer\Appointment editor without gathering any new data
     * All data is discarded when this method runs.
     * @param actionEvent
     */
    public void CancelSetNewAppointment(ActionEvent actionEvent) {
        CustomerEditController.newdatefrompick=olddatevalue;

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void edittookplace(ActionEvent actionEvent) {
        Alertnumber=0;
        DoNotLeave = false;
    }
}
