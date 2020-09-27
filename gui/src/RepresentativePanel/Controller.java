package RepresentativePanel;

import java.io.FileInputStream;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import com.google.cloud.storage.Blob;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Controller {

    @FXML
    private Pane anchor_Pane;

    @FXML
    private TextArea cust_Info;

    @FXML
    private TextArea service_Info;

    @FXML
    private Button check_Button;

    @FXML
    private Label date_Time;

    StorageOptions options;

    @FXML
    void load_Customer(MouseEvent event) throws SQLException{

        String PROJECT_ID = "shellhacks-2020";
        String PATH_TO_JSON_KEY = "D:\\ShellHacks2020\\gui\\src\\key.json";
        String BUCKET_NAME = "car-license-plate-data";
        String OBJECT_NAME = "2020-09-27-07-23-26.txt";

        {
            try {
                options = StorageOptions.newBuilder()
                        .setProjectId(PROJECT_ID)
                        .setCredentials(GoogleCredentials.fromStream(
                                new FileInputStream(PATH_TO_JSON_KEY))).build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Storage storage = options.getService();
        Bucket bucket = storage.get(BUCKET_NAME);
        Blob blob = bucket.get(OBJECT_NAME);
        String value = new String(blob.getContent());

        SQLDatabaseConnection sql_Test = new SQLDatabaseConnection();
        sql_Test.sql_Start(value);
        //String _first, String _last, String _sex, String _address, String _phone, String _email, String _DOB
        Customer cust = new Customer(sql_Test.data[1],sql_Test.data[2],sql_Test.data[4],sql_Test.data[5],sql_Test.data[6],sql_Test.data[7],sql_Test.data[3]);
        cust_Info.setText(
                "Name: " + cust.get_First() + " " + cust.get_Last()
                        + "\n\nSex: " + cust.get_Sex()
                        + "\n\nDOB: " + cust.get_DOB()
                        + "\n\nAddress: " + cust.get_Address()
                        + "\n\nPhone: " + cust.get_Phone()
                        + "\n\nEmail: " + cust.get_Email()
        );

        SQLDatabaseConnection service_Test = new SQLDatabaseConnection();
        service_Test.sql_Start(value);
        //String _vin, String _plate, String _color, String _make, String _model, String _year, String _state
        Car car = new Car(service_Test.data[12],service_Test.data[13],service_Test.data[14],service_Test.data[10],service_Test.data[11],
                service_Test.data[9], service_Test.data[15]);
        //String _time_Start, String _time_End, String _employee, String _invoice, String _discount, String _desc
        Service serv = new Service(service_Test.data[16],service_Test.data[17],service_Test.data[18],service_Test.data[19],service_Test.data[20],
                service_Test.data[21]);
        service_Info.setText(
                "Year: " + car.get_Year() +
                        "\nMake: " + car.get_Make() +
                        "\nModel: " + car.get_Model() +
                        "\nVIN#: " + car.get_Vin() +
                        "\nPlate: " + car.get_Plate() +
                        "\nColor: " + car.get_Color() +
                        "\nState: " + car.get_State() +
                        "\n\nTime Start: " + serv.getTime_Start() +
                        "\nTime End: " + serv.getTime_End() +
                        "\nEmployeeID: " + serv.getEmployee() +
                        "\nInvoice: $" + serv.getInvoice() +
                        "\nDiscount: " + serv.getDiscount() +
                        "%\nDescription: " + serv.getDescription()
        );
    }

    @FXML
    public void initialize() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM-dd-yyyy hh:mm:ss");
            date_Time.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();



    }

    public static class Customer{
        private String first_Name;
        private String last_Name;
        private String sex;
        private String phone;
        private String email;
        private String address;
        private String DOB;

        private Customer(String _first, String _last, String _sex, String _address, String _phone, String _email, String _DOB){
            this.first_Name = _first;
            this.last_Name = _last;
            this.sex = _sex;
            this.address = _address;
            this.phone = _phone;
            this.email = _email;
            this.DOB = _DOB;
        }

        private Customer(){
            this.first_Name = "N/A";
            this.last_Name = "N/A";
            this.sex = "N/A";
            this.address = "N/A";
            this.phone = "N/A";
            this.email = "N/A";
            this.DOB = "N/A";
        }

        public String get_First(){
            return this.first_Name;
        }

        public void set_First(String _first){
            this.first_Name = _first;
        }

        public String get_Last(){
            return this.last_Name;
        }

        public void set_Last(String _last){
            this.last_Name = _last;
        }

        public String get_Sex(){
            return this.sex;
        }

        public void set_Sex(String _sex){
            this.sex = _sex;
        }

        public String get_Address(){
            return this.address;
        }

        public void set_Address(String _address){
            this.address = _address;
        }

        public String get_Phone(){
            return this.phone;
        }

        public void set_Phone(String _phone){
            this.phone = _phone;
        }

        public String get_Email(){
            return this.email;
        }

        public void set_email(String _email){
            this.email = _email;
        }

        public String get_DOB(){
            return this.DOB;
        }

        public void set_DOB(String _DOB){
            this.DOB = _DOB;
        }
    }

    public static class Car{
        public String VIN;
        public String plate;
        public String color;
        public String make;
        public String model;
        public String year;
        public String state;

        private Car(String _vin, String _plate, String _color, String _make, String _model, String _year, String _state){
            this.VIN = _vin;
            this.plate = _plate;
            this.color = _color;
            this.make = _make;
            this.model = _model;
            this.year = _year;
            this.state = _state;
        }

        private Car(){
            this.VIN = "N/A";
            this.plate = "N/A";
            this.color = "N/A";
            this.make = "N/A";
            this.model = "N/A";
            this.year = "N/A";
            this.state = "N/A";
        }

        public String get_Vin(){
            return this.VIN;
        }

        public void set_Vin(String _vin){
            this.VIN = _vin;
        }

        public String get_Plate(){
            return this.plate;
        }

        public void set_Plate(String _plate){
            this.plate = _plate;
        }

        public String get_Color(){
            return this.color;
        }

        public void set_Color(String _color){
            this.color = _color;
        }

        public String get_Make(){
            return this.make;
        }

        public void set_Make(String _make){
            this.make = _make;
        }

        public String get_Model(){
            return this.model;
        }

        public void set_Model(String _model){
            this.model = _model;
        }

        public String get_Year(){
            return this.year;
        }

        public void set_Year(String _year){
            this.year = _year;
        }

        public String get_State(){
            return this.state;
        }

        public void set_State(String _state){
            this.state = _state;
        }

    }

    public static class Service{
        public String time_Start;
        public String time_End;
        public String employee;
        public String invoice;
        public String discount;
        public String description;

        public Service(){
            this.time_Start = "N/A";
            this.time_End = "N/A";
            this.employee = "N/A";
            this.invoice = "N/A";
            this.discount = "N/A";
            this.description = "N/A";
        }

        public Service(String _time_Start, String _time_End, String _employee, String _invoice, String _discount, String _desc){
            this.time_Start = _time_Start;
            this.time_End = _time_End;
            this.employee = _employee;
            this.invoice = _invoice;
            float dis = Float.parseFloat(_discount);
            dis = dis * 100;
            _discount = String.valueOf(dis);
            this.discount = _discount;
            this.description = _desc;
        }

        public void setTime_Start(String time_Start) {
            this.time_Start = time_Start;
        }

        public String getTime_Start() {
            return time_Start;
        }

        public void setTime_End(String time_End) {
            this.time_End = time_End;
        }

        public String getTime_End() {
            return time_End;
        }

        public String getEmployee() {
            return employee;
        }

        public void setEmployee(String employee) {
            this.employee = employee;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


    public class SQLDatabaseConnection {

        // Connect to your database.
        // Replace server name, username, and password with your credentials
        private ResultSet cust_Data;
        private String data[] = new String[23];

        public void sql_Start(String plate) {
            System.out.println(plate);
            String connectionUrl =
                    "jdbc:sqlserver://127.0.0.1:3306;"
                            + "database=AutoNation;"
                            + "user=sqlserver;"
                            + "password=5663003;";

            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 Statement statement = connection.createStatement()) {

                // Create and execute a SELECT SQL statement.
                String selectSql = "select top(1) cu.*, c.year as 'Year', c.make as 'Make', c.model as 'Model', c.vin as 'Vin#', c.license_plate as 'Plate', c.color as 'Color', c.state as 'State', a.start_time as 'Time Start', a.end_time as 'Time End', a.employee_id as 'EmployeeID', a.price_final as 'Invoice', a.discount as 'Discount', a.notes as 'Description' from car c join appointment a on c.id = a.car_id join customer cu on cu.id = a.customer_id where c.license_plate = '" + plate + "'";
                cust_Data = statement.executeQuery(selectSql);
                System.out.println(cust_Data);

                while (cust_Data.next()) {
                    for(int i = 0; i < 22; i++) {
                        data[i] = cust_Data.getString(i + 1);
                        System.out.println(data[i]);
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

