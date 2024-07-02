package com.institute.iitManage.controller;

import com.institute.iitManage.db.Database;
import com.institute.iitManage.model.Student;
import com.institute.iitManage.model.Tm.StudentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class StudentFormController {
    public AnchorPane context;
    public TextField txtStudentID;
    public TextField txtFullName;
    public DatePicker txtDOB;
    public Button btnSaveStudent;
    public TextField txtSearch;
    public TableView<StudentTm> tblStudent;
    public TableColumn<StudentTm,String> colStudentID;
    public TableColumn<StudentTm,String> colFullName;
    public TableColumn<StudentTm,String> colDOB;
    public TableColumn<StudentTm,String> colAddress;
    public TableColumn<StudentTm,Button> colOption;
    public TextField txtAddress;

    String searchText = "";
    public void initialize(){

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));

        generateStudentID();
        setTableData(searchText);

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setTableDataValue(newValue);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });
    }

    public void newStudentOnAction(ActionEvent actionEvent) {

        generateStudentID();
        setTableData(searchText);
        clear();
        btnSaveStudent.setText("Save Student");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }

    public void saveStudentOnAction(ActionEvent actionEvent) {
        if (btnSaveStudent.getText().equalsIgnoreCase("Save Student")) {
            Student student = new Student(
                    txtStudentID.getText(),
                    txtFullName.getText(),
                    Date.from(txtDOB.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    txtAddress.getText()
            );

            Database.studentTable.add(student);
            generateStudentID();
            clear();
            setTableData(searchText);
             
        } else {

            for (Student student : Database.studentTable) {
                if (student.getId().equals(txtStudentID.getText())) {
                    student.setName(txtFullName.getText());
                    student.setAddress(txtAddress.getText());
                    student.setDob(Date.from(txtDOB.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                    setTableData(searchText);
                    clear();
                    generateStudentID();
                    new Alert(Alert.AlertType.INFORMATION,"Student has been updated Successfully....!").show();
                    btnSaveStudent.setText("Save Student");
                    return;
                }
            }
        }
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }

    private void generateStudentID() {
        if (!Database.studentTable.isEmpty()) {

            Student lastStudent = Database.studentTable.get(Database.studentTable.size() - 1);
            String stringID = lastStudent.getId();
            String[] split = stringID.split("-");
            String lastIDAsString = split[1];
            int lastIDAsInteger = Integer.parseInt(lastIDAsString);
            lastIDAsInteger++;
            String newID = "S-"+lastIDAsInteger;
            txtStudentID.setText(newID);

        } else {
            txtStudentID.setText("S-1");
        }
    }

    private void clear() {
        txtFullName.clear();
        txtDOB.setValue(null);
        txtAddress.clear();
    }

    private  void setTableData(String name) {

        ObservableList<StudentTm> oblist = FXCollections.observableArrayList();

        for (Student student : Database.studentTable) {

            if (student.getName().contains(name)) {
                Button button = new Button("Delete");

                oblist.add(new StudentTm(
                        student.getId(),
                        student.getName(),
                        new SimpleDateFormat("yyyy-MM-dd").format(student.getDob()),
                        student.getAddress(),
                        button
                ));
                button.setOnAction(event -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure....!",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.studentTable.remove(student);
                        new Alert(Alert.AlertType.INFORMATION,"Student has been Deleted....!").show();
                        setTableData(searchText);
                    }
                });
            }
        }
        tblStudent.setItems(oblist);
    }

    private void setTableDataValue(StudentTm studentTm) {
        txtStudentID.setText(studentTm.getId());
        txtFullName.setText(studentTm.getName());
        txtDOB.setValue(LocalDate.parse(studentTm.getDob()));
        txtAddress.setText(studentTm.getAddress());
        btnSaveStudent.setText("Update Student");
    }
}
