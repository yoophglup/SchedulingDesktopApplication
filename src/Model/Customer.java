package Model;

public class Customer {
    private Integer Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private String division;
    public Customer(Integer Customer_ID, String Customer_Name,String Address,String Postal_Code,String Phone,String division){
        this.Customer_ID=Customer_ID;
        this.Customer_Name=Customer_Name;
        this.Address=Address;
        this.Postal_Code=Postal_Code;
        this.Phone=Phone;
        this.division=division;
    }

    public void setCustomer_ID(Integer customer_ID) {
        Customer_ID = customer_ID;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivision() {
        return division;
    }

    public String getPostal_Code() {
            return Postal_Code;
        }

        public void setPostal_Code(String postal_Code) {
            Postal_Code = postal_Code;
        }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }



    public Integer getCustomer_ID() {
        return Customer_ID;
    }

    public void setAddress(String address) {
        Address = address;
    }



    public String getAddress() {
        return Address;
    }


    public String getCustomer_Name() {
        return Customer_Name;
    }
}
