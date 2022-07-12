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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class LogInController {
    public Label outputbox;
    public Button login;
    public Label locationdata;
    public Text pwordtext;
    public Text unametext;
    public Text titletext;
    public Button exitbut;
    public TextField uservalue;
    public PasswordField passvalue;

    /** Initialize checks if the Locale language is in English, if not then it calls a method to change
     * all the text to french.
     */
    public void initialize(){
        //Locale.setDefault(new Locale("fr"));
        locationdata.setText("US-English");

        if (Locale.getDefault()!=Locale.US){
            gofrench();
        }
    }

    /** Changes all text to french
     * Changes the log in scene only to french in the user is not US-English
     */
    public void gofrench(){
        locationdata.setText("Français");
        pwordtext.setText("le mot de passe");
        unametext.setText("Nom d'utilisateur");
        titletext.setText("Veuillez vous connecter");
        login.setText("connexion");
        exitbut.setText("sortir");
    }

    /** Quits the application
     * Exits
     * @param actionEvent
     */
    public void quit_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**Saves all log in attempts and whether the log in was successful to a file
     * If the file does not exist, then it will create it. If it exist, it will append.
     * The file is login_activity.txt store in the root folder of the Scheduler Application
     * @param Username
     * @param Password
     * @throws IOException
     */
    public void saveloginattempt(String Username, Boolean Password) throws IOException {
        String logthis="LogIn Attempt: "+"UserID: "+Username+","+"Success: "+Password+" ZoneDateTimeStamp: "+ZonedDateTime.now().toString().replaceFirst("T"," ")+"\n";
        System.out.println(logthis);
        Path path = Path.of("login_activity.txt");
        byte[] StringBytes = logthis.getBytes(StandardCharsets.UTF_8);
        Files.write(path,StringBytes,CREATE,APPEND);
    }

    /** Checks if the password and username matches
     * Retrieves the data from the database and compares to the data entered by the user.
     * @param actionEvent
     * @throws IOException
     */
    public void validate(ActionEvent actionEvent) throws IOException {

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement("select Password from users where User_Name=\""+uservalue.getText()+"\"");
            ResultSet rs = ps.executeQuery();
            Boolean passwordsmatch=false;
            while (rs.next()){
                String pword=rs.getString("Password");
                if (pword.equals(passvalue.getText())){
                    passwordsmatch=true;
                    saveloginattempt(uservalue.getText(),passwordsmatch);
                    outputbox.setText("Welcome\n"+uservalue.getText());
                    if (Locale.getDefault()!=Locale.US) {
                        outputbox.setText("Bienvenue\n"+uservalue.getText());
                    }
                    CustomerEditController.uservalue=uservalue.getText();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/MainMenu.fxml")));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 400, 350);
                    stage.setTitle("Main Menu");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                    }
            }

        if (!passwordsmatch) {
            saveloginattempt(uservalue.getText(),passwordsmatch);
            outputbox.setText("Username or \npasssword is \nincorrect.\nTry Again");
        if (Locale.getDefault()!=Locale.US){
            outputbox.setText("L'identifiant\n ou le mot de \npasse est incorrect. \nRéessayer.");
        }}} catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
    }
}
