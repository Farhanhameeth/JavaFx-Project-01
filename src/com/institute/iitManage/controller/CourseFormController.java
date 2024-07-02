package com.institute.iitManage.controller;

import com.institute.iitManage.db.Database;
import com.institute.iitManage.model.Course;
import com.institute.iitManage.model.Tm.CourseTm;
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
import java.util.Optional;

public class CourseFormController {
    public AnchorPane context;
    public TextField txtCourseID;
    public TextField txtCourseName;
    public Button btnSaveCourse;
    public TextField txtSearch;
    public TableView<CourseTm> tblCourse;
    public TableColumn<CourseTm,String> colCourseID;
    public TableColumn<CourseTm,String> colCourseName;
    public TableColumn<CourseTm,String[]> colSubjects;
    public TableColumn<CourseTm,String> colTeacherID;
    public TableColumn<CourseTm,Double> colCost;
    public TableColumn<CourseTm,Button> colOption;
    public TextField txtSubjects;
    public TextField txtTeacherID;
    public TextField txtCost;

    String searchText = "";
    public void initialize() {
        colCourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSubjects.setCellFactory(column -> new TableCell<CourseTm, String[]>() {
            @Override
            protected void updateItem(String[] subjects, boolean empty) {
                super.updateItem(subjects, empty);
                if (empty || subjects == null) {
                    setText(null);
                } else {
                    setText(String.join(", ", subjects));
                }
            }
        });
        colSubjects.setCellValueFactory(new PropertyValueFactory<>("subjects"));
        colTeacherID.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));

        generateCourseID();
        setTableData(searchText);

        tblCourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null!=newValue) {
                setTableDataValue(newValue);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });
    }
    public void newCourseOnAction(ActionEvent actionEvent) {
        generateCourseID();
        setTableData(searchText);
        clear();
        btnSaveCourse.setText("Save Course");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }

    public void saveCourseOnAction(ActionEvent actionEvent) {
        if (btnSaveCourse.getText().equalsIgnoreCase("Save Course")) {
            Course course = new Course(
                    txtCourseID.getText(),
                    txtCourseName.getText(),
                    txtSubjects.getText().split(","),
                    txtTeacherID.getText(),
                    Double.parseDouble(txtCost.getText())
            );

            Database.courseTable.add(course);
            generateCourseID();
            clear();
            setTableData(searchText);

        } else {

            for (Course course : Database.courseTable) {
                if (course.getCourseID().equals(txtCourseID.getText())) {
                    course.setCourseName(txtCourseName.getText());
                    course.setSubjects(txtSubjects.getText().split(","));
                    course.setTeacherID(txtTeacherID.getText());
                    course.setCost(Double.parseDouble(txtCost.getText()));

                    setTableData(searchText);
                    clear();
                    generateCourseID();
                    new Alert(Alert.AlertType.INFORMATION,"Course has been Updated Successfully...!").show();
                    btnSaveCourse.setText("Save Course");
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

    private void setTableData(String name) {

        ObservableList<CourseTm> oblist = FXCollections.observableArrayList();

        for (Course course : Database.courseTable) {

            if (course.getCourseName().contains(name)) {

                Button button = new Button("Delete");

                oblist.add(new CourseTm(
                        course.getCourseID(),
                        course.getCourseName(),
                        course.getSubjects(),
                        course.getTeacherID(),
                        course.getCost(),
                        button
                ));


                button.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure....?",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.courseTable.remove(course);
                        new Alert(Alert.AlertType.INFORMATION,"Course has been Deleted....!").show();
                        setTableData(searchText);
                    }
                });
            }
        }
        tblCourse.setItems(oblist);
    }

    private void setTableDataValue(CourseTm courseTm) {
        txtCourseID.setText(courseTm.getCourseID());
        txtCourseName.setText(courseTm.getName());
        txtSubjects.setText(String.join(",",courseTm.getSubjects()));
        txtTeacherID.setText(courseTm.getTeacherID());
        txtCost.setText(String.valueOf(courseTm.getCost()));
        btnSaveCourse.setText("Update Course");
    }

    private void generateCourseID() {
        if (!Database.courseTable.isEmpty()) {
            Course lastCourse = Database.courseTable.get(Database.courseTable.size()-1);
            String stringID = lastCourse.getCourseID();
            String[] split = stringID.split("-");
            String lastIDAsString = split[1];
            int lastIDAsInteger = Integer.parseInt(lastIDAsString);
            lastIDAsInteger++;
            String newID = "C-"+lastIDAsInteger;
            txtCourseID.setText(newID);
        } else {
            txtCourseID.setText("T-1");
        }
    }

    private void clear() {
        txtCourseName.clear();
        txtSubjects.clear();
        txtTeacherID.clear();
        txtCost.clear();
    }
}
