package Controllers;

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


        //AppointmentDatepicker.setValue(LocalDate.parse(olddatevalue));
        String ddate=olddatevalue.substring(0,10);
        String dhour=olddatevalue.substring(11,13);
        String dmins=olddatevalue.substring(14,16);
        AppointmentDatepicker.setValue(LocalDate.parse(ddate));
        //apphour.setItems(hourslist);
        appmins.setItems(minslist);
        appmins.setValue(dmins);
    }

    public void SubmitNewAppointment(ActionEvent actionEvent) {

        String sqlsubstring=AppointmentDatepicker.getValue()+" "+apphour.getValue()+":"+appmins.getValue()+":00";
        LocalDate localcreateDate = LocalDate.parse(sqlsubstring.substring(0,10));
        LocalTime localCreateTime = LocalTime.parse(sqlsubstring.substring(11,19));
        ZoneId UTCZone= ZoneId.of("UTC");
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,localZoneId);
        Instant llc= ThisCreate_date.toInstant();
        ZonedDateTime ddc = ThisCreate_date.withZoneSameInstant(UTCZone);
        ZonedDateTime ddl = ThisCreate_date.withZoneSameInstant(localZoneId);

        sqlsubstring=ddc.toString().replaceFirst("T"," ").substring(0,16)+":00";
        String formdata=ddl.toString().replaceFirst("T"," ").substring(0,16)+":00";
        String sqlstring="update appointments set "+namebox+"='"+sqlsubstring+"' where Appointment_ID="+ClickedAppointment_ID+";";
        CustomerEditController.newsqldate=sqlstring;
        CustomerEditController.newdatefrompick=formdata;


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

    }
    public void CancelSetNewAppointment(ActionEvent actionEvent) {
        CustomerEditController.newdatefrompick=olddatevalue;

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
