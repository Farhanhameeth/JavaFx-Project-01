package com.institute.iitManage.controller;

import com.institute.iitManage.db.Database;
import com.institute.iitManage.model.Course;
import com.institute.iitManage.model.Intake;
import com.institute.iitManage.model.Tm.IntakeTm;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class IntakeFormController {
    public AnchorPane context;
    public TextField txtIntakeID;
    public TextField txtIntakeName;
    public ComboBox<String> cmbCourse;
    public DatePicker txtDate;
    public Button btnSave;
    public TextField txtSearch;
    public TableView<IntakeTm> tblIntake;
    public TableColumn<IntakeTm, String> colID;
    public TableColumn<IntakeTm, String> colName;
    public TableColumn<IntakeTm, String> colDate;
    public TableColumn<IntakeTm, String> colCourse;
    public TableColumn<IntakeTm, Boolean> colStatus;
    public TableColumn<IntakeTm, Button> colOption;

    String searchText = "";

    ArrayList<String> coursesArray = new ArrayList<>();

    public void initialize() {
        generateIntakeID();
        setCourses();

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblIntake.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setTableDataValue(newValue);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
           searchText = newValue;
           setTableData(searchText);
        });
    }

    public void newCourseOnAction(ActionEvent actionEvent) {
        clear();
        btnSave.setText("Save Intake");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }

    public void saveIntakeOnAction(ActionEvent actionEvent) {

        if (btnSave.getText().equalsIgnoreCase("Save Intake")) {
            Intake intake = new Intake(
                    txtIntakeID.getText(),
                    txtIntakeName.getText(),
                    Date.from(txtDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    cmbCourse.getValue().split("\\.")[1],
                    true
            );

            Database.intakeTable.add(intake);
            new Alert(Alert.AlertType.INFORMATION,"Intake has been added...!").show();
            generateIntakeID();
            clear();
            setTableData(searchText);

        } else {

            for (Intake intake : Database.intakeTable) {
                if (intake.getId().equals(txtIntakeID.getText())) {
                    intake.setName(txtIntakeName.getText());
                    intake.setDate(Date.from(txtDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    intake.setCourse(cmbCourse.getValue().split("\\.")[1]);

                    setTableData(searchText);
                    clear();
                    generateIntakeID();
                    new Alert(Alert.AlertType.INFORMATION,"Intake has been Updated Successfully...!").show();
                    btnSave.setText("Save Intake");
                    return;

                }
            }
        }
    }

    private void generateIntakeID() {
        if (!Database.intakeTable.isEmpty()) {
            Intake lastCourse = Database.intakeTable.get(Database.intakeTable.size()-1);
            String stringID = lastCourse.getId();
            String[] split = stringID.split("-");
            String lastIDAsString = split[1];
            int lastIDAsInteger = Integer.parseInt(lastIDAsString);
            lastIDAsInteger++;
            String newID = "I-"+lastIDAsInteger;
            txtIntakeID.setText(newID);
        } else {
            txtIntakeID.setText("I-1");
        }
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }

    private void clear() {
        txtIntakeName.clear();
        cmbCourse.getSelectionModel().clearSelection();
        txtDate.setValue(null);
    }

    private void setCourses() {

        for (Course course : Database.courseTable) {
            coursesArray.add(course.getCourseID() + ". " + course.getCourseName());
        }

        ObservableList<String> oblist = FXCollections.observableArrayList(coursesArray);
        cmbCourse.setItems(oblist);
    }

    private void setTableData(String name) {

        ObservableList<IntakeTm> oblist = FXCollections.observableArrayList();

        for (Intake intake : Database.intakeTable) {
            if (intake.getName().contains(name)) {

                Button button = new Button("Delete");

                oblist.add(new IntakeTm(
                        intake.getId(),
                        intake.getName(),
                        new SimpleDateFormat("yyyy-MM-dd").format(intake.getDate()),
                        intake.getCourse(),
                        intake.isStatus(),
                        button
                ));
                button.setOnAction(event -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure....!",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.intakeTable.remove(intake);
                        new Alert(Alert.AlertType.INFORMATION,"Intake has been Deleted....!").show();
                        setTableData(searchText);
                    }
                });
            }
        }
        tblIntake.setItems(oblist);
    }

    private void setTableDataValue(IntakeTm intakeTm) {
        txtIntakeID.setText(intakeTm.getId());
        txtIntakeName.setText(intakeTm.getName());
        txtDate.setValue(LocalDate.parse(intakeTm.getDate()));
        cmbCourse.setValue(intakeTm.getCourse());
        btnSave.setText("Update Intake");
    }
}
