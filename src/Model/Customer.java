package Model;

import Controllers.CustomerEditController;
import Controllers.JDBC;
import com.sun.javafx.image.IntPixelGetter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private Integer Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private String division;
    private Integer Division_ID;
    private String Create_Date;
    private String Created_By;
    private String Last_Update;
    private String Last_Updated_By;
    public ComboBox DivisionCombobox;
    public Customer(Integer customer_ID, String customer_Name, String address, String postal_Code, String phone, String division, Integer division_ID, String create_Date, String created_By, String last_Update, String last_Updated_By) throws SQLException {
        Customer_ID = customer_ID;
        Customer_Name = customer_Name;
        Address = address;
        Postal_Code = postal_Code;
        Phone = phone;



        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Division from first_level_divisions;");
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String> all_divisionsList = FXCollections.observableArrayList();
        while (resultSet.next()) {
             String thisStringDivision=resultSet.getString("Division");
             all_divisionsList.add(thisStringDivision);
             }
        
        this.division = division;
        this.DivisionCombobox=new ComboBox(FXCollections.observableArrayList(all_divisionsList));
        this.DivisionCombobox.setValue(this.division);
        Division_ID = division_ID;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
    }

    public void setDivisionCombobox(ComboBox divisionComboBox) {
        DivisionCombobox = divisionComboBox;
    }

    public ComboBox getDivisionCombobox() {
        return DivisionCombobox;
    }

    public Integer getCustomer_ID() {
        return Customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public String getPhone() {
        return Phone;
    }

    public String getDivision() {
        return division;
    }

    public Integer getDivision_ID() {
        return Division_ID;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public String getLast_Update() {
        return Last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }
}



























































