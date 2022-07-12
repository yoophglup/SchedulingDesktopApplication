package Controllers;

import Main.LambdaInterface;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
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

    /** This method Submits new values which the user selected from datepicker and combo boxes.
     * This Method also incorporates a Lambda Expression to improve code.
     * This Method also changes the scene by closing the Appointment picker window.
     * @param actionEvent
     */
    public void SubmitNewAppointment(ActionEvent actionEvent) {
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


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

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
}
