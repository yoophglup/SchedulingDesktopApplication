package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class AddNewAppointment {
    public void initialize() throws SQLException {
        System.out.println(CustomerEditController.uservalue);
        System.out.println(CustomerEditController.selectedcustomerID);

    }

    public void cancel(ActionEvent actionEvent) throws Exception {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.show();

    }


}




