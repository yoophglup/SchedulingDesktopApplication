package Controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.time.ZoneId;
import java.util.Locale;

public class Controller {
    public Label outputbox;
    public Button login;
    public Label locationdata;

    public void initialize(){
        //Locale.setDefault(new Locale("fr"));

        locationdata.setText(Locale.getDefault().toString());
        String sql ="select * from users";

    }

    public void quit_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void validate(ActionEvent actionEvent) {

        outputbox.setText("Username or \npasssword is \nincorrect.\nTry Again");
    }
}
