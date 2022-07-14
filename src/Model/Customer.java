package Model;

import Controllers.AddNewCustomer;
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
    private String Division;
    private Integer Division_ID;
    private String Create_Date;
    private String Created_By;
    private String Last_Update;
    private String Last_Updated_By;
    public ComboBox CountryCombobox;
    public ComboBox DivisionCombobox;
    public Customer(Integer customer_ID, String customer_Name, String address, String postal_Code, String phone,String country,String division, Integer division_ID, String create_Date, String created_By, String last_Update, String last_Updated_By) throws SQLException {
        Customer_ID = customer_ID;
        Customer_Name = customer_Name;
        Address = address;
        Postal_Code = postal_Code;
        Phone = phone;

        PreparedStatement preparedStatementA = JDBC.getConnection().prepareStatement("select Country from countries;");
        ResultSet resultSetA = preparedStatementA.executeQuery();
        PreparedStatement preparedStatementB = JDBC.getConnection().prepareStatement("select f.Division_ID, f.Division, f.Country_ID, c.Country from first_level_divisions f join countries c on f.Country_ID=c.Country_ID where c.Country='"+country+"'");
        ResultSet resultSetB = preparedStatementB.executeQuery();

        ObservableList<String> all_divisionsList = FXCollections.observableArrayList();
        ObservableList<String> all_CountriesList = FXCollections.observableArrayList();

        while (resultSetA.next()) {
             String thisCountry=resultSetA.getString("Country");
                 all_CountriesList.add(thisCountry);
             }
        while (resultSetB.next()) {
            String thisdivision=resultSetB.getString("Division");
            all_divisionsList.add(thisdivision);
        }

        this.CountryCombobox=new ComboBox(FXCollections.observableArrayList(all_CountriesList));
        this.CountryCombobox.setValue(country);
        CountryCombobox.setOnAction(e-> {
            try {
                CountryChange();
            } catch (SQLException throwables) {
            }
        });
        this.DivisionCombobox=new ComboBox(FXCollections.observableArrayList(all_divisionsList));
        this.DivisionCombobox.setValue(division);

        Division = division;

        Division_ID = division_ID;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
    }

    private void CountryChange() throws SQLException {
        ObservableList<String> all_divisionsList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select f.Division_ID, f.Division, f.Country_ID, c.Country from first_level_divisions f join countries c on f.Country_ID=c.Country_ID where c.Country='"+CountryCombobox.getValue().toString()+"'");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thisdivision=resultSet.getString("Division");
            all_divisionsList.add(thisdivision);
        }
        DivisionCombobox.setItems(all_divisionsList);


    }

    public void setDivisionCombobox(ComboBox divisionComboBox) {
        DivisionCombobox = divisionComboBox;

    }

    public ComboBox getDivisionCombobox() {
        return DivisionCombobox;
    }

    public void setCountryCombobox(ComboBox countryCombobox) {
        CountryCombobox = countryCombobox;
    }

    public ComboBox getCountryCombobox() {return CountryCombobox; }

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
        return Division;
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



























































