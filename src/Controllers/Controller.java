package Controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller {
    public Label outputbox;
    public Button login;

    public void quit_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void validate(ActionEvent actionEvent) {
        outputbox.setText("Username or \npasssword is \nincorrect.\nTry Again");
    }
}
