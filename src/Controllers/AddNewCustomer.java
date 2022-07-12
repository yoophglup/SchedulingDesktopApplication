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

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
/** AddNewCustomer is designed to allow the User to add New Customers.
 */
public class AddNewCustomer {
    public TextField Customer_Name_input;
    public TextField Address_input;
    public TextField Postal_Code_input;
    public TextField Phone_input;
    public ComboBox Division_input;

    /**
     * The initialize method initialize values, sets the choices for all comboBoxes, sets any known data
     * from previous scenes.
     *
     */
    public void initialize() throws SQLException {
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Division from first_level_divisions;");
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String> all_divisionsList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            String thisStringDivision=resultSet.getString("Division");
            all_divisionsList.add(thisStringDivision);
        }
            //ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(all_divisionsList));
        Division_input.setItems(all_divisionsList);

    }

    /**
     * This method changes the scene to the customer edit menu and does not save any data.
     * @param actionEvent changes the scene
     * @throws  IOException actionEvent
     */
    public void cancel(ActionEvent actionEvent) throws Exception {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    /**This method saves the new customer to the database.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void addNewCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Max(Customer_ID) from customers;");
        ResultSet resultSet = preparedStatement.executeQuery();
        Integer CustomerID = null;
        while (resultSet.next()) {
            CustomerID = resultSet.getInt("Max(Customer_ID)") + 1;
        }
        System.out.println(CustomerID);
        String Customer_Name= Customer_Name_input.getText();
        String Address=Address_input.getText();
        String Postal=Postal_Code_input.getText();
        String Phone= Phone_input.getText();
        String Create_Date="NOW()";
        String Created_By=CustomerEditController.uservalue;
        String Last_Update="NOW()";
        String Last_Updated_BY=CustomerEditController.uservalue;
        String Division_ID="(select Division_ID from first_level_divisions where Division='"+Division_input.getValue().toString()+"')";
        String AddNewCustomerSQL="insert into customers values("+CustomerID+",\""+Customer_Name+"\",'"+Address+"','"+Postal+"','"+Phone+"',"+Create_Date+",'"+Created_By+"',"+Last_Update+",'"+Last_Updated_BY+"',"+Division_ID+");";
        System.out.println(AddNewCustomerSQL);
        preparedStatement = JDBC.getConnection().prepareStatement(AddNewCustomerSQL);
        preparedStatement.execute();

        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/CustomerEditor.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Edit Customer Records");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }
}
