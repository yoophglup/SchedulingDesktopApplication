package Model;

public class Customer {
    private Integer Customer_ID;
    private String Customer_Name;
    private String Address;









    private String Postal_Code;
    private String Phone;
    public Customer(Integer Customer_ID, String Customer_Name,String Address,String Postal_Code,String Phone){
        this.Customer_ID=Customer_ID;
        this.Customer_Name=Customer_Name;
        this.Address=Address;
        this.Postal_Code=Postal_Code;
        this.Phone=Phone;
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
