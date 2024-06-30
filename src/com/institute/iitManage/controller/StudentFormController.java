package com.institute.iitManage.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class StudentFormController {
    public AnchorPane context;
    public TextField txtStudentID;
    public TextField txtFullName;
    public DatePicker txtDOB;
    public Button btnSaveStudent;
    public TextField txtSearch;
    public TableView tblStudent;
    public TableColumn colStudentID;
    public TableColumn colFullName;
    public TableColumn colDOB;
    public TableColumn colAddress;
    public TableColumn colOption;
    public TextField txtAddress;

    public void saveStudentOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void addNewStudentOnAction(ActionEvent actionEvent) {
    }
}
