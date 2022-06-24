package Controllers;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import static java.lang.String.valueOf;

public class Appointmentpicker {
    public static Integer ClickedAppointment_ID;
    public DatePicker AppointmentDatepicker;
    public ComboBox apphour;
    public ComboBox appmins;
    public ObservableList<String> hourslist = FXCollections.observableArrayList();
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

        }
        apphour.setItems(hourslist);
        appmins.setItems(minslist);
    }
    //2020-05-28 12:00:00
    public void SubmitNewAppointment(ActionEvent actionEvent) {

        String sqlsubstring=AppointmentDatepicker.getValue()+" "+apphour.getValue()+":"+appmins.getValue()+":00";

        String sqlstring="update appointments set start='"+sqlsubstring+"' where Appointment_ID="+ClickedAppointment_ID+";";
        System.out.println(sqlstring);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

    }

    public void CancelSetNewAppointment(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
