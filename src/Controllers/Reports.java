package Controllers;

import Main.LambdaInterface;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;

public class Reports {

    public PieChart TotalPie;


    public RadioButton totalRadio;
    public RadioButton contactsRadio;
    public RadioButton additionalRadio;
    public RadioButton typeRadio;
    public RadioButton monthRadio;
    public Label Label1;
    public Label Label2;
    public ToggleGroup TypeOrMonth;
    public TableView ContactsScheduleTable;
    public TableColumn AppointmentID;
    public TableColumn Contact_Name;
    public TableColumn Title;
    public TableColumn Type;
    public TableColumn Description;
    public TableColumn Start;
    public TableColumn End;
    public TableColumn CustomerID;
    public ToggleGroup MainRadios;

    public void initialize() throws SQLException {

    }

    public void totalRadioClicked(ActionEvent actionEvent) throws SQLException {
        ContactsScheduleTable.setVisible(false);

        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select count(*) as Total_Appointments from appointments;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Integer Total_Appointments = resultSet.getInt("Total_Appointments");
            Label1.setText(Total_Appointments.toString()+"\n Total \nAppointments");
        }

    }

    public void contactsRadioClicked(ActionEvent actionEvent) throws SQLException {
        Label1.setText("");
        Label2.setText("");
        typeRadio.setSelected(false);
        monthRadio.setSelected(false);
        TotalPie.setLegendVisible(false);
        try {
            TotalPie.setData(null);
        }
        catch (Exception e){        }
        ContactsScheduleTable.setVisible(true);
        //
        ObservableList<Contact> listofcontacts = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select Contact_Name,Appointment_ID,Title,Type,Description,Start,End,Customer_ID from appointments a join contacts c on a.Contact_ID=c.Contact_ID order by Contact_Name,Start;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String Contact_Name = resultSet.getString("Contact_Name");
            String Appointment_ID = resultSet.getString("Appointment_ID");
            String Title = resultSet.getString("Title");
            String Type = resultSet.getString("Type");
            String Description = resultSet.getString("Description");
            String Start = resultSet.getString("Start");

            LocalDate localcreateDate = LocalDate.parse(Start.substring(0,10));
            LocalTime localCreateTime = LocalTime.parse(Start.substring(11,19));
            ZoneId UTCZone= ZoneId.of("UTC");
            ZoneId localZoneId = ZoneId.systemDefault();
            ZonedDateTime ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,UTCZone);
            ZonedDateTime ddc = ThisCreate_date.withZoneSameInstant(localZoneId);
            Start=ddc.toString().replaceFirst("T"," ").substring(0,16)+":00";


            String End = resultSet.getString("End");
            localcreateDate = LocalDate.parse(End.substring(0,10));
            localCreateTime = LocalTime.parse(End.substring(11,19));
            UTCZone= ZoneId.of("UTC");
            localZoneId = ZoneId.systemDefault();
            ThisCreate_date = ZonedDateTime.of(localcreateDate,localCreateTime,UTCZone);
            ddc = ThisCreate_date.withZoneSameInstant(localZoneId);
            End=ddc.toString().replaceFirst("T"," ").substring(0,16)+":00";

            String Customer_ID = resultSet.getString("Customer_ID");
            Contact thisContact = new Contact(Contact_Name, Appointment_ID, Title, Type, Description, Start, End, Customer_ID);
            listofcontacts.add(thisContact);

        }
        Contact_Name.setCellValueFactory(new PropertyValueFactory<Contact, String>("Contact_Name"));
        AppointmentID.setCellValueFactory(new PropertyValueFactory<Contact, String>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<Contact, String>("Title"));
        Type.setCellValueFactory(new PropertyValueFactory<Contact, String>("Type"));
        Description.setCellValueFactory(new PropertyValueFactory<Contact, String>("Description"));
        Start.setCellValueFactory(new PropertyValueFactory<Contact, String>("Start"));
        End.setCellValueFactory(new PropertyValueFactory<Contact, String>("End"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<Contact, String>("Customer_ID"));




        ContactsScheduleTable.setItems(listofcontacts);

    }

    public void additionalRadioClicked(ActionEvent actionEvent) throws SQLException {
        ContactsScheduleTable.setVisible(false);
        monthRadio.setSelected(false);
        typeRadio.setSelected(false);
        totalRadio.setSelected(false);
        totalRadioClicked(actionEvent);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select u.User_Name,count(a.Appointment_ID) as total from appointments a join users u on a.User_ID=u.User_ID group by a.User_ID;");
        ResultSet resultSet = preparedStatement.executeQuery();
        TotalPie.setLabelsVisible(true);
        TotalPie.setLegendVisible(true);

        String labelstring="Totals by User Input\nNew Appointments"+"\n------------------------";
        while (resultSet.next()) {
            String typeofappointment = resultSet.getString("User_Name");
            Integer typetotal = resultSet.getInt("total");
            pieChartData.add(new PieChart.Data(typeofappointment+" "+typetotal, typetotal));
            labelstring=labelstring+"\n"+typeofappointment+" : "+typetotal;
        }
        resultSet.next();
        //TotalPie.setTitle("Total Appointments by Type");
        TotalPie.setData(pieChartData);
        Label2.setText(labelstring);
    }

    public void totalTypeRadioClicked(ActionEvent actionEvent) throws SQLException {
        ContactsScheduleTable.setVisible(false);

        totalRadio.setSelected(true);
        totalRadioClicked(actionEvent);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select type,count(*) as total from appointments group by Type;\n");
        ResultSet resultSet = preparedStatement.executeQuery();
        TotalPie.setLabelsVisible(true);
        TotalPie.setLegendVisible(true);

        String labelstring="Totals by Types"+"\n------------------------";
        while (resultSet.next()) {
            String typeofappointment = resultSet.getString("type");
            Integer typetotal = resultSet.getInt("total");
            pieChartData.add(new PieChart.Data(typeofappointment+" "+typetotal, typetotal));

            //Lambda Usage : add five strings together with anonymous method
            //Set the label display as the returned value
            LambdaInterface Lamda1 = (s1, s2,s3) -> s1 + "\n"+s2+" : "+s3;

            labelstring=Lamda1.concat(labelstring,typeofappointment,typetotal.toString());

        }

        TotalPie.setData(pieChartData);
        Label2.setText(labelstring);
    }

    public void totalMonthRadioClicked(ActionEvent actionEvent) throws SQLException {
        ContactsScheduleTable.setVisible(false);

        totalRadio.setSelected(true);
        totalRadioClicked(actionEvent);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("select monthname(start) as m,count(*) as total from appointments group by month(start);\n");
        ResultSet resultSet = preparedStatement.executeQuery();
        TotalPie.setLabelsVisible(true);
        TotalPie.setLegendVisible(true);
        String labelstring="Totals by Month "+"\n------------------------";
        while (resultSet.next()) {
            String typeofappointment = resultSet.getString("m");
            Integer typetotal = resultSet.getInt("total");
            pieChartData.add(new PieChart.Data(typeofappointment+" "+typetotal, typetotal));
            labelstring=labelstring+"\n"+typeofappointment+" : "+typetotal;
        }
        //TotalPie.setTitle("Total Appointments by Type");
        TotalPie.setData(pieChartData);
        Label2.setText(labelstring);
    }

    public void ReturntoMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Scenes/MainMenu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 350);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

}
