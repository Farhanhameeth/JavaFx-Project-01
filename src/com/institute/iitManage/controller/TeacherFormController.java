package com.institute.iitManage.controller;

import com.institute.iitManage.db.Database;
import com.institute.iitManage.model.Teacher;
import com.institute.iitManage.model.Tm.TeacherTm;
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

public class TeacherFormController {
    public AnchorPane context;
    public TextField txtTeacherID;
    public TextField txtFullName;
    public Button btnSaveTeacher;
    public TextField txtSearch;
    public TableView<TeacherTm> tblTeacher;
    public TableColumn<TeacherTm,String> colTeacherID;
    public TableColumn<TeacherTm,String> colFullName;
    public TableColumn<TeacherTm,String> colAddress;
    public TableColumn<TeacherTm,String> colContact;
    public TableColumn<TeacherTm,Button> colOption;
    public TextField txtAddress;
    public TextField txtContact;

    String searchText = "";
    public void initialize() {

        colTeacherID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));

        generateTeacherID();
        setTableData(searchText);

        tblTeacher.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setTableDataValue(newValue);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });
    }

    public void newTeacherOnAction(ActionEvent actionEvent) {
        generateTeacherID();
        setTableData(searchText);
        clear();
        btnSaveTeacher.setText("Save Teacher");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }

    public void saveTeacherOnAction(ActionEvent actionEvent) {

        if (btnSaveTeacher.getText().equalsIgnoreCase("Save Teacher")) {
            Teacher teacher = new Teacher(
                    txtTeacherID.getText(),
                    txtFullName.getText(),
                    txtAddress.getText(),
                    txtContact.getText()
            );

            Database.teacherTable.add(teacher);
            generateTeacherID();
            clear();
            setTableData(searchText);
            new Alert(Alert.AlertType.INFORMATION, "Teacher has been Saved...!").show();
            System.out.println(teacher.toString());

        } else {

            for (Teacher teacher : Database.teacherTable) {
                if (teacher.getTeacherID().equals(txtTeacherID.getText())) {
                    teacher.setName(txtFullName.getText());
                    teacher.setAddress(txtAddress.getText());
                    teacher.setContact(txtContact.getText());

                    setTableData(searchText);
                    clear();
                    generateTeacherID();
                    new Alert(Alert.AlertType.INFORMATION,"Teacher have been Updated Successfully....!").show();
                    btnSaveTeacher.setText("Save Teacher");
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

    private void generateTeacherID(){

        if (!Database.teacherTable.isEmpty()) {
            Teacher lastTeacher = Database.teacherTable.get(Database.teacherTable.size()-1);
            String stringID = lastTeacher.getTeacherID();
            String[] split = stringID.split("-");
            String lastIDAsString = split[1];
            int lastIDAsInteger = Integer.parseInt(lastIDAsString);
            lastIDAsInteger++;
            String newID = "T-"+lastIDAsInteger;
            txtTeacherID.setText(newID);

        } else {

            txtTeacherID.setText("T-1");
        }
    }

    private void clear() {
        txtFullName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    private void setTableData(String name) {

        ObservableList<TeacherTm> oblist = FXCollections.observableArrayList();

        for (Teacher teacher : Database.teacherTable) {

            if (teacher.getName().contains(searchText)) {

                Button button = new Button("Delete");

                oblist.add(new TeacherTm(
                        teacher.getTeacherID(),
                        teacher.getName(),
                        teacher.getAddress(),
                        teacher.getContact(),
                        button
                ));

                button.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure....?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.teacherTable.remove(teacher);
                        new Alert(Alert.AlertType.INFORMATION, "Teacher has been Deleted...!").show();
                        setTableData(searchText);
                    }
                });
            }
        }
        tblTeacher.setItems(oblist);
    }
    private void setTableDataValue(TeacherTm teachertm) {
        txtTeacherID.setText(teachertm.getId());
        txtFullName.setText(teachertm.getName());
        txtAddress.setText(teachertm.getAddress());
        txtContact.setText(teachertm.getContact());
        btnSaveTeacher.setText("Update Teacher");
    }
}
