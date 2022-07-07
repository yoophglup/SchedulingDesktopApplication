package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;

public class MainMenuController {


    public Label AlertLabel;
    public void initialize() throws SQLException {

        AlertLabel.setText("");
        LocalDate localcreateDate = LocalDate.now();
        LocalTime localCreateTime = LocalTime.now();
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId UTCZone = ZoneId.of("UTC");
        ZonedDateTime LocalZonedNowDate = ZonedDateTime.of(localcreateDate, localCreateTime, localZoneId);
        ZonedDateTime UTCLocalZonedNowDate = LocalZonedNowDate.withZoneSameInstant(UTCZone);

        String StringUTCLocalZonedNowDate = UTCLocalZonedNowDate.toString().replaceFirst("T", " ").substring(0, 16) + ":00";
        PreparedStatement sqlappointment = JDBC.getConnection().prepareStatement("select * from appointments where User_ID=(select User_ID from users where User_Name='" + CustomerEditController.uservalue + "') and Start > Now() And Start < Now()+Interval 15 minute;");
        ResultSet apresults = sqlappointment.executeQuery();
        while (apresults.next()) {
            Integer thisappointmentID = apresults.getInt("Appointment_ID");
            String Starttime = apresults.getString("Start");
            LocalDate StarttimecreateDate = LocalDate.parse(Starttime.substring(0,10));
            LocalTime StarttimeCreateTime = LocalTime.parse(Starttime.substring(11,19));
            ZonedDateTime UTCStarttimeZonedNowDate =  ZonedDateTime.of(StarttimecreateDate, StarttimeCreateTime,UTCZone );
            ZonedDateTime LocalStarttimeZonedNowDate =  UTCStarttimeZonedNowDate.withZoneSameInstant(localZoneId);
            String StartString=LocalStarttimeZonedNowDate.toString().replaceFirst("T"," ").substring(0,16)+":00";
            Alert areyousure = new Alert(Alert.AlertType.CONFIRMATION);
            areyousure.setTitle("INFORMATION");
            areyousure.setHeaderText("An appointment assigned to your User ID is starting in less than 15 Minutes! ");
            areyousure.setContentText(""+"Appoinment #"+thisappointmentID+" Starts at "+StartString+" "+localZoneId);
            areyousure.showAndWait();
            AlertLabel.setText(" Alert: An appointment is starting in less than 15 Minutes! \n " + "Appoinment #"+thisappointmentID+" Starts at "+StartString+" "+localZoneId);
        }
        if (AlertLabel.getText()==""){
            AlertLabel.setText("There are no upcoming appointments.");
        }
    }

    public void LoadCustomerEditor(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void ExitApplication(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

    }

    public void Logout(ActionEvent actionEvent) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/loginscreen.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 340, 200);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    public void AddNewAppointment(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/AddNewAppointment.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 400);
        stage.setTitle("Add a new Appointment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void LoadScheduler(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/Scheduler.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void GenerateReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/Reports.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("Generate Reports");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
