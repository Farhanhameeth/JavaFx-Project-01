package com.institute.iitManage.controller;

import com.institute.iitManage.db.Database;
import com.institute.iitManage.model.Course;
import com.institute.iitManage.model.Teacher;
import com.institute.iitManage.model.Tm.CourseTm;
import com.institute.iitManage.model.Tm.TechnologiesTm;
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
import java.util.ArrayList;
import java.util.Optional;

public class CourseFormController {
    public AnchorPane context;
    public TextField txtCourseID;
    public TextField txtCourseName;
    public Button btnSaveCourse;
    public TextField txtSearch;

    public TableView<CourseTm> tblCourse;
    public TableColumn<CourseTm,String> colCourseID;
    public TableColumn<CourseTm,String> colCourse;
    public TableColumn<CourseTm,Button> colTechOnCourse;
    public TableColumn<CourseTm,String> colTeacher;
    public TableColumn<CourseTm,Double> colCost;
    public TableColumn<CourseTm,Button> colOption;

    public TextField txtTechnologies;
    public TextField txtCost;
    public ComboBox<String> cmbTeachers;

    public TableView<TechnologiesTm> tblTechnologies;
    public TableColumn<TechnologiesTm,Integer> colTechID;
    public TableColumn<TechnologiesTm,String> colTechnology;
    public TableColumn<TechnologiesTm,Button> colRemove;

    String searchText = "";

    ArrayList<String> teachersArray = new ArrayList<>();

    ObservableList<TechnologiesTm>  tmList = FXCollections.observableArrayList();

    public void initialize() {
        generateCourseID();
        setTeachers();
        loadCourse(searchText);


        tblCourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null!=newValue) {
                setTableDataValue(newValue);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            loadCourse(searchText);
        });

        colTechID.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTechnology.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("button"));

        colCourseID.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        colTechOnCourse.setCellValueFactory(new PropertyValueFactory<>("btnTechList"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btnOption"));

    }

    public void newCourseOnAction(ActionEvent actionEvent) {
        generateCourseID();
        loadCourse(searchText);
        clear();
        btnSaveCourse.setText("Save Course");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }

    public void saveCourseOnAction(ActionEvent actionEvent) {

        String[] selectedTech = new String[tmList.size()];
        int pointer = 0;

        for (TechnologiesTm tech : tmList) {
            selectedTech[pointer] = tech.getName();
            pointer++;
        }

        if (btnSaveCourse.getText().equalsIgnoreCase("Save Course")) {
            Course course = new Course(
                    txtCourseID.getText(),
                    txtCourseName.getText(),
                    selectedTech,
                    cmbTeachers.getValue().split("\\.")[0],
                    Double.parseDouble(txtCost.getText().trim())
            );

            Database.courseTable.add(course);
            new Alert(Alert.AlertType.INFORMATION,"Course has been saved...!").show();
            generateCourseID();
            loadCourse(searchText);
            clear();
        } else {

            for (Course course : Database.courseTable) {
                if (course.getCourseID().equals(txtCourseID.getText())) {
                    course.setCourseName(txtCourseName.getText());
                    course.setSubjects(txtTechnologies.getText().split(","));
                    course.setTeacherID(cmbTeachers.getValue().split("\\.")[0]);
                    course.setCost(Double.parseDouble(txtCost.getText()));

                    loadCourse(searchText);
                    clear();
                    cmbTeachers.setValue(null);
                    generateCourseID();
                    new Alert(Alert.AlertType.INFORMATION,"Course has been Updated Successfully...!").show();
                    btnSaveCourse.setText("Save Course");
                    return;
                }
            }
        }

    }

    public void addTechOnAction(ActionEvent actionEvent) {

        if (!isExist(txtTechnologies.getText().trim())) {
            Button button = new Button("Remove");


            TechnologiesTm tech = new TechnologiesTm(tmList.size()+1, txtTechnologies.getText().trim(), button);
            tmList.add(tech);
            tblTechnologies.setItems(tmList);
            txtTechnologies.clear();

            button.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure....?",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.get() == ButtonType.YES) {
                    tmList.remove(tech);
                    tblTechnologies.setItems(tmList);
                }
            });
        } else {
            txtTechnologies.selectAll();
            new Alert(Alert.AlertType.INFORMATION,"This Technology On Course Already Exists...").show();
        }

    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }

    private void setTableDataValue(CourseTm courseTm) {
        txtCourseID.setText(courseTm.getCode());
        txtCourseName.setText(courseTm.getName());
        tblTechnologies.setItems(tmList);
        cmbTeachers.setValue(courseTm.getTeacher());
        txtCost.setText(String.valueOf(courseTm.getCost()));
        btnSaveCourse.setText("Update Course");
    }

    private void setTeachers() {

        for (Teacher teacher : Database.teacherTable) {
            teachersArray.add(teacher.getTeacherID() + ". " + teacher.getName());
        }

        ObservableList<String> oblist = FXCollections.observableArrayList(teachersArray);
        cmbTeachers.setItems(oblist);
    }

    private boolean isExist(String tech) {
        for (TechnologiesTm tm : tmList) {
            if (tm.getName().equalsIgnoreCase(tech)) {
                return true;
            }
        }
        return false;
    }

    private void loadCourse(String name) {
        ObservableList<CourseTm> courseList = FXCollections.observableArrayList();

        for (Course course : Database.courseTable) {

            if (course.getCourseName().contains(name)) {

                Button techButton = new Button("Show Tech");
                Button deleteButton = new Button("Delete");

                CourseTm courseTm = new CourseTm(
                        course.getCourseID(),
                        course.getCourseName(),
                        course.getTeacherID(),
                        techButton,
                        course.getCost(),
                        deleteButton
                );

                deleteButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure....?",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.courseTable.remove(course);
                        new Alert(Alert.AlertType.INFORMATION,"Course has been Deleted....!").show();
                        loadCourse(searchText);
                    }
                });

                techButton.setOnAction(event -> {
                    Stage stage = new Stage();
                    try {
                        stage.setScene(FXMLLoader.load(getClass().getResource("../view/TechnologiesForm.fxml")));
                        stage.centerOnScreen();
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                courseList.add(courseTm);
            }
        }
        tblCourse.setItems(courseList);
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
            txtCourseID.setText("C-1");
        }
    }

    private void clear() {
        txtCourseName.clear();
        txtTechnologies.clear();
        cmbTeachers.getSelectionModel().clearSelection();
        txtCost.clear();
        tmList.clear();
        tblTechnologies.setItems(tmList);
    }
}
