package RepresentativePanel;

import java.io.FileInputStream;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;


import com.google.cloud.storage.Blob;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<Car> service_Table;

    @FXML
    private TableColumn<Car, String> year_Column;

    @FXML
    private TableColumn<Car, String> make_Column;

    @FXML
    private TableColumn<Car, String> model_Column;

    @FXML
    private TableColumn<Car, String> vin_Column;

    @FXML
    private TableColumn<Car, String> descript_Column;

    @FXML
    private Button check_Button;

    @FXML
    private Button update_Button;

    @FXML
    private Label date_Time;

    StorageOptions options;

    @FXML
    void load_Customer(MouseEvent event) throws SQLException{

        String PROJECT_ID = "shellhacks-2020";
        String PATH_TO_JSON_KEY = "D:\\ShellHacks2020\\gui\\src\\key.json";
        String BUCKET_NAME = "car-license-plate-data";
        String OBJECT_NAME = "2020-09-26-22-54-55.txt";

        //StorageOptions options;
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
        Customer cust = new Customer(sql_Test.data[0],sql_Test.data[1],sql_Test.data[2],sql_Test.data[3],sql_Test.data[4],sql_Test.data[5],sql_Test.data[6]);
        cust_Info.setText(
                "Name: " + cust.get_First() + " " + cust.get_Last()
                        + "\n\nSex: " + cust.get_Sex()
                        + "\n\nDOB: " + cust.get_DOB()
                        + "\n\nAddress: " + cust.get_Address()
                        + "\n\nPhone: " + cust.get_Phone()
                        + "\n\nEmail: " + cust.get_Email()
        );


        final ObservableList<Car> table_Data = FXCollections.observableArrayList(
                new Car("VIN1", "PLATE1", "COLOR1", "MAKE1", "MODEL1", "YEAR1", "STATE1"),
                new Car("VIN2", "PLATE2", "COLOR2", "MAKE2", "MODEL2", "YEAR2", "STATE2"),
                new Car("VIN3", "PLATE3", "COLOR3", "MAKE3", "MODEL3", "YEAR3", "STATE3"),
                new Car("VIN4", "PLATE4", "COLOR4", "MAKE4", "MODEL4", "YEAR4", "STATE4"),
                new Car("VIN5", "PLATE5", "COLOR5", "MAKE5", "MODEL5", "YEAR5", "STATE5")
        );
        /*vin_Column.setCellValueFactory(
                new PropertyValueFactory<Car,String>("VIN")
        );
        year_Column.setCellValueFactory(
                new PropertyValueFactory<Car,String>("YEAR")
        );
        make_Column.setCellValueFactory(
                new PropertyValueFactory<Car,String>("MAKE")
        );
        model_Column.setCellValueFactory(
                new PropertyValueFactory<Car,String>("MODEL")
        );
        descript_Column.setCellValueFactory(
                new PropertyValueFactory<Car,String>("Description")
        );

        service_Table.setItems(table_Data);
        service_Table.getColumns().addAll(vin_Column,year_Column,make_Column,model_Column,descript_Column);*/
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
        private String vin;
        private String plate;
        private String color;
        private String make;
        private String model;
        private String year;
        private String state;

        private Car(String _vin, String _plate, String _color, String _make, String _model, String _year, String _state){
            this.vin = _vin;
            this.plate = _plate;
            this.color = _color;
            this.make = _make;
            this.model = _model;
            this.year = _year;
            this.state = _state;
        }

        private Car(){
            this.vin = "";
            this.plate = "";
            this.color = "";
            this.make = "";
            this.model = "";
            this.year = "";
            this.state = "";
        }

        public String get_Vin(){
            return this.vin;
        }

        public void set_Vin(String _vin){
            this.vin = _vin;
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

    public class SQLDatabaseConnection {

        // Connect to your database.
        // Replace server name, username, and password with your credentials
        private ResultSet cust_Data;
        private String data[] = new String[7];


        /*String PROJECT_ID = "shellhacks-2020";
        String PATH_TO_JSON_KEY = "D:\\ShellHacks2020\\gui\\src\\key.json";
        String BUCKET_NAME = "car-license-plate-data";
        String OBJECT_NAME = "2020-09-26-22-54-55.txt";

        StorageOptions options;
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
        public String value = new String(blob.getContent());*/

        public void sql_Start(String plate) {
            String connectionUrl =
                    "jdbc:sqlserver://127.0.0.1:3306;"
                            + "database=AutoNation;"
                            + "user=sqlserver;"
                            + "password=5663003;";

            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 Statement statement = connection.createStatement()) {

                // Create and execute a SELECT SQL statement.
                //String _first, String _last, String _sex, String _address, String _phone, String _email, String _DOB
                String selectSql = "select c.first_name,c.last_name,c.sex,c.address,c.contact_mobile,c.contact_email,c.date_of_birth from customer c join car on car.customer_id=c.id where license_plate = '" + plate + "'";
                cust_Data = statement.executeQuery(selectSql);
                System.out.println(cust_Data);

                while (cust_Data.next()) {
                    for(int i = 0; i < 7; i++) {
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

