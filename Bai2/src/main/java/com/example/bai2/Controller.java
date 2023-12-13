package com.example.bai2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    ConnectJDBC connectJDBC = new ConnectJDBC();
    Connection connection = connectJDBC.connection();

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField search;
    @FXML
    private TableView<MyDB> tableView;
    @FXML
    private TableColumn<MyDB, Integer> idColumn;
    @FXML
    private TableColumn<MyDB, String> nameColumn;
    @FXML
    private TableColumn<MyDB, Integer> ageColumn;
    @FXML
    private TableColumn<MyDB, Boolean> genderColumn;
    @FXML
    private TableColumn<MyDB, String> addressColumn;
    @FXML
    private ObservableList<MyDB> mydbObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mydbObservableList = FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<MyDB, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<MyDB, String>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<MyDB, Integer>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<MyDB, Boolean>("gender"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<MyDB, String>("address"));
        tableView.setItems(mydbObservableList);
        display();
    }

    public void display() {
        String query = "SELECT * FROM Student";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            setInformation(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void takeData(MyDB mydb) {
        mydb.setId(Integer.parseInt(idField.getText()));
        mydb.setName(nameField.getText());
        mydb.setAge(Integer.parseInt(ageField.getText()));
        mydb.setGender(Boolean.parseBoolean(genderField.getText()));
        mydb.setAddress(addressField.getText());
    }

    public void pushToSQL() {
        String query = "INSERT INTO Student values (?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(idField.getText()));
            preparedStatement.setString(2, nameField.getText());
            preparedStatement.setInt(3, Integer.parseInt(ageField.getText()));
            preparedStatement.setBoolean(4, Boolean.parseBoolean(genderField.getText()));
            preparedStatement.setString(5, addressField.getText());
            int row = preparedStatement.executeUpdate();
            if (row != 0) {
                System.out.println("Complete update: " + row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void push() {
        MyDB myDB = new MyDB();
        takeData(myDB);
        pushToSQL();
        mydbObservableList.add(myDB);
        tableView.refresh();
    }

    public void setInformation(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("stuID");
            String name = resultSet.getString("stuName");
            int age = resultSet.getInt("age");
            boolean gender = resultSet.getBoolean("gender");
            String address = resultSet.getString("address");
            MyDB displayInFormation = new MyDB(id, name, age, gender, address);
            mydbObservableList.add(displayInFormation);
        }
    }

    @FXML
    public void check() {
        String check = "'" + search.getText() + "'";
        String query = "SELECT * FROM Student WHERE stuName like" + check;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            setInformation(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}