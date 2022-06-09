package Model;

public class Customer {
    private Integer Customer_ID;
    private String Customer_Name;
    private String Address;

    public void setCustomer_ID(Integer customer_ID) {
        Customer_ID = customer_ID;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public Customer(Integer Customer_ID, String Customer_Name,String Address){
        this.Customer_ID=Customer_ID;
        this.Customer_Name=Customer_Name;
        this.Address=Address;

    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public Integer getCustomer_ID() {
        return Customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }
}
