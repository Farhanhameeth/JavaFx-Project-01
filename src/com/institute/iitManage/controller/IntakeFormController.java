package com.institute.iitManage.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class IntakeFormController {
    public AnchorPane context;
    public TextField txtIntakeID;
    public TextField txtIntakeName;
    public ComboBox cmbCourse;
    public DatePicker txtDate;
    public Button btnSave;
    public TextField txtSearch;
    public TableView tblIntake;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colDate;
    public TableColumn colCourse;
    public TableColumn colStatus;
    public TableColumn colOption;

    public void newCourseOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void saveIntakeOnAction(ActionEvent actionEvent) {
    }
}
