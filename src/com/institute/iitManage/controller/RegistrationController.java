package com.institute.iitManage.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class RegistrationController {
    public AnchorPane context;
    public TextField txtRegID;
    public TextField txtStudent;
    public ComboBox cmbCourse;
    public RadioButton rdbPaid;
    public ToggleGroup payment;
    public RadioButton rdbPending;
    public TextField txtSearch;
    public TableView tblReg;
    public TableColumn colID;
    public TableColumn colStudent;
    public TableColumn colDate;
    public TableColumn colCourse;
    public TableColumn colPayment;
    public TableColumn colOption;

    public void newRegOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }
}
