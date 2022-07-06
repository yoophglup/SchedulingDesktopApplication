package Model;


public class Contact {
    private String Contact_Name;
    private String Appointment_ID;
    private String Title;
    private String Type;
    private String Description;
    private String Start;
    private String End;
    private String Customer_ID;

    public Contact(String contact_Name,String appointment_ID,String title,String type, String description, String start,String end,String customer_ID){
    Contact_Name=contact_Name;
    Appointment_ID=appointment_ID;
    Title=title;
    Type=type;
    Description=description;
    Start=start;
    End=end;
    Customer_ID=customer_ID;
    }

    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    public void setAppointment_ID(String appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setStart(String start) {
        Start = start;
    }

    public void setEnd(String end) {
        End = end;
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getContact_Name() {
        return Contact_Name;
    }

    public String getAppointment_ID() {
        return Appointment_ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getType() {
        return Type;
    }

    public String getDescription() {
        return Description;
    }

    public String getStart() {
        return Start;
    }

    public String getEnd() {
        return End;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }
}