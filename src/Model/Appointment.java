package Model;

import Controllers.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Appointment {
    private Integer appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private String Start;
    private String End;
    private String Create_Date;
    private String Created_By;
    private String Last_Update;
    private String Last_Updated_By;
    private Integer Customer_ID;
    private Integer User_ID;
    private Integer Contact_ID;
    public ComboBox Customer_IDComboBox;
    public ComboBox User_IDComboBox;
    public ComboBox ContactIDComboBox;

    public Appointment(Integer appointment_ID, String title, String description, String location, String type, String start, String end, String create_Date, String create_By, String last_Update, String last_Updated_by, Integer customer_ID, Integer user_ID, Integer contact_ID) throws SQLException {
        this.appointment_ID = appointment_ID;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Create_Date=create_Date;
        Created_By=create_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_by;
        Customer_ID = customer_ID;
        User_ID = user_ID;
        Contact_ID = contact_ID;

        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Customer_ID,Customer_Name from customers;");
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Integer> all_Customer_IDList = FXCollections.observableArrayList();
        ObservableList<String> all_Customer_NameList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Integer thisStringID=resultSet.getInt("Customer_ID");
            String thisCustomerName=resultSet.getString("Customer_Name");
            all_Customer_IDList.add(thisStringID);
            all_Customer_NameList.add(thisCustomerName);
        }
        this.Customer_IDComboBox=new ComboBox(FXCollections.observableArrayList(all_Customer_NameList).sorted());
        this.Customer_IDComboBox.setValue(all_Customer_NameList.get(all_Customer_IDList.indexOf(Customer_ID)));


        PreparedStatement preparedStatement2 = JDBC.getConnection().prepareStatement("select User_ID from users;");
        ResultSet resultSet2 = preparedStatement2.executeQuery();
        ObservableList<Integer> all_User_IDList = FXCollections.observableArrayList();
        while (resultSet2.next()) {
            Integer thisStringID=resultSet2.getInt("User_ID");
            all_User_IDList.add(thisStringID);
        }
        this.User_IDComboBox=new ComboBox(FXCollections.observableArrayList(all_User_IDList).sorted());
        this.User_IDComboBox.setValue(User_ID);



        PreparedStatement preparedStatement3 = JDBC.getConnection().prepareStatement("select Contact_Name,Contact_ID from contacts;");
        ResultSet resultSet3 = preparedStatement3.executeQuery();
        ObservableList<Integer> all_contact_IDList = FXCollections.observableArrayList();
        ObservableList<String> all_contact_namelist = FXCollections.observableArrayList();

        while (resultSet3.next()) {
            Integer thisStringID=resultSet3.getInt("Contact_ID");
            String thisContactName=resultSet3.getString("Contact_Name");
            all_contact_IDList.add(thisStringID);
            all_contact_namelist.add(thisContactName);
        }
        this.ContactIDComboBox=new ComboBox(FXCollections.observableArrayList(all_contact_namelist).sorted());
        this.ContactIDComboBox.setValue(all_contact_namelist.get(all_contact_IDList.indexOf(Contact_ID)));


    }

    public void setCustomer_IDComboBox(ComboBox customer_IDComboBox) {
        Customer_IDComboBox = customer_IDComboBox;
    }

    public void setUser_IDComboBox(ComboBox user_IDComboBox) {
        User_IDComboBox = user_IDComboBox;
    }

    public void setContactIDComboBox(ComboBox contactIDComboBox) {
        ContactIDComboBox = contactIDComboBox;
    }

    public ComboBox getCustomer_IDComboBox() {
        return Customer_IDComboBox;
    }

    public ComboBox getUser_IDComboBox() {
        return User_IDComboBox;
    }

    public ComboBox getContactIDComboBox() {
        return ContactIDComboBox;
    }

    public String getCreate_Date() {
        return Create_Date;
    }



    public Integer getAppointment_ID() {
        return appointment_ID;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setCreate_Date(String create_Date) {
        Create_Date = create_Date;
    }

    public void setLast_Update(String last_Update) {
        Last_Update = last_Update;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getLocation() {
        return Location;
    }

    public String getType() {
        return Type;
    }

    public String getStart() {
        return Start;
    }

    public String getEnd() {
        return End;
    }

    public String getCreate_date() {
        return Create_Date;
    }



    public String getLast_Update() {
        return Last_Update;
    }

    public String getLast_Updated_by() {
        return Last_Updated_By;
    }

    public Integer getCustomer_ID() {
        return Customer_ID;
    }

    public Integer getUser_ID() {
        return User_ID;
    }

    public Integer getContact_ID() {
        return Contact_ID;
    }

    public void setAppointment_ID(Integer appointment_ID) {
        this.appointment_ID = appointment_ID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setStart(String start) {
        Start = start;
    }

    public void setEnd(String end) {
        End = end;
    }

    public void setCreate_date(String create_date) {
        Create_Date = create_date;
    }

    public void setCustomer_ID(Integer customer_ID) {
        Customer_ID = customer_ID;
    }

    public void setUser_ID(Integer user_ID) {
        User_ID = user_ID;
    }

    public void setContact_ID(Integer contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }
}
