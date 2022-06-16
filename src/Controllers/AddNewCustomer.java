package Controllers;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class AddNewCustomer {
    public TextField Customer_Name_input;
    public TextField Address_input;
    public TextField Postal_Code_input;
    public TextField Phone_input;
    public ComboBox Division_input;

    public void initialize() throws SQLException {
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Division from first_level_divisions;");
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String> all_divisions = FXCollections.observableArrayList();

        boolean existingapointments = false;
        while (resultSet.next()) {
            String thisDivision=resultSet.getString("Division");
            all_divisions.add(thisDivision);
        }
        Division_input.setItems(all_divisions);

    }

    public void cancel(ActionEvent actionEvent) throws Exception {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 400);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.show();

    }

    public void addNewCustomer(ActionEvent actionEvent) {

    }
}
