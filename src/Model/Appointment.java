package Model;

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

    public Appointment(Integer appointment_ID, String title, String description, String location, String type, String start, String end, String create_Date, String create_By, String last_Update, String last_Updated_by, Integer customer_ID, Integer user_ID, Integer contact_ID) {
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
