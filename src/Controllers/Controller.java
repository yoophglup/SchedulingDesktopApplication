package Controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
    public Text pwordtext;
    public Text unametext;
    public Text titletext;
    public Button exitbut;
    public TextField uservalue;
    public PasswordField passvalue;

    public void initialize(){
        //Locale.setDefault(new Locale("fr"));
        locationdata.setText("US-English");

        if (Locale.getDefault()!=Locale.US){
            gofrench();
        }
    }
    public void gofrench(){
        locationdata.setText("Français");
        System.out.println("french");
        pwordtext.setText("le mot de passe");
        unametext.setText("Nom d'utilisateur");
        titletext.setText("Veuillez vous connecter");
        login.setText("connexion");
        exitbut.setText("sortir");


    }
    public void quit_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void validate(ActionEvent actionEvent) {
        System.out.println("username: "+uservalue.getText());
        System.out.println("password: "+passvalue.getText());
        outputbox.setText("Username or \npasssword is \nincorrect.\nTry Again");
        if (Locale.getDefault()!=Locale.US){
            outputbox.setText("L'identifiant\n ou le mot de \npasse est incorrect. \nRéessayer.");
        }
    }
}
