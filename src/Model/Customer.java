package Model;

import com.sun.javafx.image.IntPixelGetter;

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

    public Customer(Integer customer_ID, String customer_Name, String address, String postal_Code, String phone, String division, Integer division_ID, String create_Date, String created_By, String last_Update, String last_Updated_By) {
        Customer_ID = customer_ID;
        Customer_Name = customer_Name;
        Address = address;
        Postal_Code = postal_Code;
        Phone = phone;
        this.division = division;
        Division_ID = division_ID;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
    }

    public void setCustomer_ID(Integer customer_ID) {
        Customer_ID = customer_ID;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setDivision_ID(Integer division_ID) {
        Division_ID = division_ID;
    }

    public void setCreate_Date(String create_Date) {
        Create_Date = create_Date;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public void setLast_Update(String last_Update) {
        Last_Update = last_Update;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
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



























































