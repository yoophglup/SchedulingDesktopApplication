package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;

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

    public void validate(ActionEvent actionEvent) throws IOException {

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement("select Password from users where User_Name=\""+uservalue.getText()+"\"");
            ResultSet rs = ps.executeQuery();
            Boolean passwordsmatch=false;
            while (rs.next()){
                String pword=rs.getString("Password");
                if (pword.equals(passvalue.getText())){
                    passwordsmatch=true;
                    outputbox.setText("Welcome\n"+uservalue.getText());
                    if (Locale.getDefault()!=Locale.US) {
                        outputbox.setText("Bienvenue\n"+uservalue.getText());
                    }
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 640, 350);
                    stage.setTitle("Edit Customer Records");
                    stage.setScene(scene);
                    stage.show();
                    }
            }

        if (passwordsmatch==false) {
        outputbox.setText("Username or \npasssword is \nincorrect.\nTry Again");
        if (Locale.getDefault()!=Locale.US){
            outputbox.setText("L'identifiant\n ou le mot de \npasse est incorrect. \nRéessayer.");
        }}} catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
    }
}
