package com.institute.iitManage.controller;

import com.institute.iitManage.db.Database;
import com.institute.iitManage.model.User;
import com.institute.iitManage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpFormController {
    public AnchorPane context;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtPassword;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {

        String firstName = txtFirstName.getText().trim().toLowerCase();
        String lastName = txtLastName.getText().trim().toLowerCase();
        String email = txtEmail.getText().trim().toLowerCase();
        String password = txtPassword.getText().trim();

        Database.userTable.add(
                new User(firstName,lastName,email,new PasswordManager().encrypt(password))
        );
        new Alert(Alert.AlertType.CONFIRMATION,"Your Accout has been Created.....!").show();
        setUI("LoginForm");

    }

    public void alreadyHaveAnAccountOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        setUI("LoginForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }
}
